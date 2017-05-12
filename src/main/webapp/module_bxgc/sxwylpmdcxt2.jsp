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
        <!-- 
        <div data-options="region:'north',border:false" style="padding:3px;height:38px;text-align:center;background:#fffff">
            <span id="myspan" style="font:normal 25px '微软雅黑'; ">竖向位移量平面等值线图</span>
        </div>
         -->
         
        <canvas id="canvas" width="1690px" height="1100px" style="z-index:999" >
        
    </div>
    
    <div style="position: fixed;left:0px;right:800px;bottom:40px;z-index: 888;padding: 5px;margin-left:auto;margin-right:auto;width:800px">
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
				for(var i=0;i<data.length;i++) {
					ctx.font="13px Verdana";
					ctx.strokeStyle = '#000000';
					ctx.fillStyle = '#000000';
					if(i < 17)
					{
						ctx.fillText(data[i].zoffset+" mm", xValue[i]+10, yValue[i] - 8);
					}
					else
					{
						ctx.fillText(data[i].zoffset+" mm", xValue[i]+10, yValue[i] + 2);
					}
					
				}
			 }
         });
		
		$.ajax({
			type:'post',
            dataType:'json',
            data:{sdate:sdate,dtype:dtype},
            url:'GetSXWYDCXData1.action',
			 success:function(data){
				for(var i=0;i<data.length;i++) {
					var xValue1=[];
					var yValue1=[];
					var index=0;
					
					//根据观测点名称获取X、Y坐标
					for(var j=0;j<data[i].nlist.length;j++) {
						var name=data[i].nlist[j].name;
						if(name == 'W11') {
							xValue1[index] = xValue[0];
							yValue1[index] = yValue[0];
						} else if(name == 'W12') {
							xValue1[index] = xValue[1];
							yValue1[index] = yValue[1];
						} else if(name == 'W13') {
							xValue1[index] = xValue[2];
							yValue1[index] = yValue[2];
						} else if(name == 'W21') {
							xValue1[index] = xValue[3];
							yValue1[index] = yValue[3];
						} else if(name == 'W22') {
							xValue1[index] = xValue[4];
							yValue1[index] = yValue[4];
						} else if(name == 'W23') {
							xValue1[index] = xValue[5];
							yValue1[index] = yValue[5];
						} else if(name == 'W31') {
							xValue1[index] = xValue[6];
							yValue1[index] = yValue[6];
						} else if(name == 'W32') {
							xValue1[index] = xValue[7];
							yValue1[index] = yValue[7];
						} else if(name == 'W33') {
							xValue1[index] = xValue[8];
							yValue1[index] = yValue[8];
						} else if(name == 'W41') {
							xValue1[index] = xValue[9];
							yValue1[index] = yValue[9];
						} else if(name == 'W42') {
							xValue1[index] = xValue[10];
							yValue1[index] = yValue[10];
						} else if(name == 'W43') {
							xValue1[index] = xValue[11];
							yValue1[index] = yValue[11];
						} else if(name == 'W51') {
							xValue1[index] = xValue[12];
							yValue1[index] = yValue[12];
						} else if(name == 'W52') {
							xValue1[index] = xValue[13];
							yValue1[index] = yValue[13];
						} else if(name == 'W53') {
							xValue1[index] = xValue[14];
							yValue1[index] = yValue[14];
						} else if(name == 'W61') {
							xValue1[index] = xValue[15];
							yValue1[index] = yValue[15];
						} else if(name == 'W62') {
							xValue1[index] = xValue[16];
							yValue1[index] = yValue[16];
						} else if(name == 'W63') {
							xValue1[index] = xValue[17];
							yValue1[index] = yValue[17];
						} else if(name == 'WA1') {
							xValue1[index] = xValue[18];
							yValue1[index] = yValue[18];
						} else if(name == 'WA2') {
							xValue1[index] = xValue[19];
							yValue1[index] = yValue[19];
						} else if(name == 'WA3') {
							xValue1[index] = xValue[20];
							yValue1[index] = yValue[20];
						} else if(name == 'WB1') {
							xValue1[index] = xValue[21];
							yValue1[index] = yValue[21];
						} else if(name == 'WB2') {
							xValue1[index] = xValue[22];
							yValue1[index] = yValue[22];
						} else if(name == 'WB3') {
							xValue1[index] = xValue[23];
							yValue1[index] = yValue[23];
						} else if(name == 'WC1') {
							xValue1[index] = xValue[24];
							yValue1[index] = yValue[24];
						} else if(name == 'WC2') {
							xValue1[index] = xValue[25];
							yValue1[index] = yValue[25];
						} else if(name == 'WC3') {
							xValue1[index] = xValue[26];
							yValue1[index] = yValue[26];
						}
						
						index++;
					}
					
					drawDZX2(canvas, xValue1, yValue1, '#'+Math.floor(Math.random()*0xffffff).toString(16));  //画线
				}
			 }
        });

	}
    
</script>

</html>