<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="../head1.jsp" %>  

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>竖向位移量平面等值线图</title>

</head>

<body>

    <div style="position:absolute;width:1690px;height:810px;overflow:auto;z-index:2">
        <canvas id="canvas" width="1690px" height="1100px" style="z-index:999" >
    </div>
    
    <div style="position: fixed;left:0px;right:800px;bottom:80px;z-index: 888;padding: 5px;margin-left:auto;margin-right:auto;width:800px">
        <table align="left"  border="0" cellpadding="0" cellspacing="0" style="padding:5px;">
            <tr>
                <td align="left" style="padding-top:5px;">数据类型：</td>
                <td align="left" style="padding-top:5px;">
                    <input id="dtype" class="easyui-combobox" style="width:120px;height:28px"
                        data-options="
                                url: '../jsonfile/bxdatatype.json',
                                method:'get',
                                valueField:'value',
                                textField:'text',
                                panelHeight:'auto',
                                editable:false,
                                onSelect:onSelect
                        ">
                </td>
            </tr>
            <tr>
                <td style="padding-top:15px;">日期选择：</td>
                <td style="padding-top:15px;">
                    <input id="sdate" name="sdate" data-options="editable:false,onSelect:onSelect" 
                        class="easyui-datebox" required style="width:120px;height:28px">
                </td>
            </tr>
        </table>
    </div>

</body>

<script type="text/javascript">

    //得到当前日期
    formatterDate = function(date) {
        var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
        var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
        + (date.getMonth() + 1);
        return date.getFullYear() + '-' + month + '-' + day;
    };

    var bgImg = new Image();
    //初始化界面数据
    window.onload = function () { 
    	$('#sdate').datebox('setValue', formatterDate(new Date()));
    	$('#dtype').combobox('setValue', 'bx_his_wyl');
    	
    	var ctx = canvas.getContext("2d");
		bgImg.src = "../images/sxwy/sxwy2.jpg";
		bgImg.onload = function(){
			ctx.drawImage(bgImg, 0, 0);
			getData();
		};
    };
    
    //日期选择事件
    function onSelect(date){
    	getData();  //获取数据并显示
	}
    
    //获取数据并显示
    function getData() {
		var sdate,dtype;
		
		sdate = $('#sdate').datebox('getValue');
		dtype = $('#dtype').combobox('getValue');
		
		if(sdate == '') {
        	$.messager.alert('系统提示','请选择日期!','error');
        	return;
        }
		if(dtype == '') {
			$.messager.alert('系统提示','请选择数据类型!','error');
			return;
		}
		
		//顺序：W11,W12,W13,W21,W22,W23,W31,W32,W33,W41,W42,W43,W51,W52,W53,W61,W62,W63,WA1,WA2,WA3,WB1,WB2,WB3,WC1,WC2,WC3
		var xValue=[
		            500,634,802,
		            500,634,802,
		            500,634,802,
		            500,634,802,
		            500,634,802,
		            500,634,802,
		            1323,1336,1351,
		            1392,1405,1420,
		            1449,1462,1477];
		var yValue=[447,447,447,
		            517,517,517,
		            592,592,592,
		            671,671,671,
		            743,743,743,
		            821,821,821,
		            198,229,262,
		            166,196,228,
		            142,173,205];
		
		var colorList = ["#0000FF","#00008B","#BF3EFF","#FF00FF","#FF0000","#A020F0","#A52A2A","#228B22","#2F4F4F"]; 
		
		//清除画布
		var ctx = canvas.getContext("2d");
		ctx.clearRect(0,0,canvas.width,canvas.height);
		ctx.drawImage(bgImg, 0, 0);
		$.ajax({
			 type:'post',
             dataType:'json',
             data:{sdate:sdate,dtype:dtype},
             url:'GetSXWYDCXData.action',
			 success:function(data){
				 
				ctx.font="13px Verdana";
				
				//取最大值
				var max1=-20.0,max2=-20.0;
				for(var i=0;i<data.length;i++) {
					var value=data[i].zoffset;
					if(i < 18) {
						if(value > max1) max1 = Math.ceil(value)+1;
					} else {
						if(value > max2) max2 = Math.ceil(value);
					}
				}
				
				//画线
				for(var i=0;i<9;i++) {
					var Value=[];
					var xValue1=[];
					var yValue1=[];
					
					//var color='#'+Math.floor(Math.random()*0xffffff).toString(16);
					//var color='#'+('00000'+(Math.random()*0x1000000<<0).toString(16)).substr(-6);
					//var color='#'+(Math.random()*0xffffff<<0).toString(16);
					
					var color=getColorByRandom(colorList);
					  
					ctx.strokeStyle = color;
					ctx.fillStyle = color;
					
					for(var j=0;j<3;j++) {
						var index=i*3+j;  //数组下标
						
						Value[j] = data[index].zoffset;
						
						//计算X、Y轴坐标，像素(0-30pdi)对应值(0-Max mm)
						if(i < 6) {
							xValue1[j] = xValue[index];
							yValue1[j] = yValue[index]+40*(Value[j] / max1);
							
							ctx.fillText(Value[j]+" mm", xValue1[j]-10, yValue1[j]+18);
						} else {
							ctx.fillText(Value[j]+" mm", xValue[index]+10, yValue[index] + 2);
							
							xValue1[j] = xValue[index]-20*(Value[j] / max2);
							yValue1[j] = yValue[index]+20*(Value[j] / max2);
						}
					}
					
					//画线
					drawDZX3(canvas, Value, xValue1, yValue1);
				}
				
			 }
         });
		
		//获取指定数组中颜色，而且不重复
		function getColorByRandom(colorList){  
		    var colorIndex = Math.floor(Math.random()*colorList.length);  
		    var color = colorList[colorIndex];  
		    colorList.splice(colorIndex,1);  
		    return color;  
		}  
		
	}
    
</script>

</html>