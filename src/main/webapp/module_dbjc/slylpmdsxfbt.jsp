<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="../head1.jsp" %>  

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>渗流压力平面等势线分布图</title>

</head>

<body>

    <div style="position:absolute;width:1300px;height:1010px;overflow:auto;z-index:2">
        <!-- 
        <div data-options="region:'north',border:false" style="padding:3px;height:38px;text-align:center;background:#fffff">
            <span id="myspan" style="font:normal 25px '微软雅黑'; ">渗流压力平面等势线分布图</span>
        </div>
         -->
    
        <canvas id="canvas" width="1250px" height="1000px" style="z-index:999" >
        
    </div>
    
    <div style="position: fixed;left:0px;right:800px;bottom:40px;z-index: 888;padding: 5px;margin-left:auto;margin-right:auto;width:800px">
        <table align="left"  border="0" cellpadding="0" cellspacing="0" style="padding:5px;">
            <tr>
                <td align="left" style="padding-top:5px;">数据类型：</td>
                <td align="left" style="padding-top:5px;">
                    <input id="dtype" class="easyui-combobox" style="width:120px;height:28px"
                        data-options="
                                url: '../jsonfile/dbdatatype.json',
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
    	$('#dtype').combobox('setValue', 'db_his_sysw');
    	
    	var ctx = canvas.getContext("2d");
		bgImg.src = "../images/slyl/slyl.jpg";
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
		
		//清除画布
		var ctx = canvas.getContext("2d");
		ctx.clearRect(0,0,canvas.width,canvas.height);
		ctx.drawImage(bgImg, 0, 0);
		var xValues1 = [445, 586, 725, 866, 1006];
		var xValues2 = [445, 586, 725, 866, 1006];
		var xValues3 = [445, 586, 725, 866, 1006];
		var xValues4 = [522, 586, 726, 866, 973];
		//平面高度， 上面的线的屏幕位置， 下面的线的屏幕位置
		var heightData1 = [
		      [232.57, 478, 495],           
		      [222.30, 422, 428],             
		      [208.57, 319, 324], 
		      [200.57, 260, 265], 
		      [188.57, 148, 161]
		];
		var heightData2 = [
   		      [232.57, 478, 495],           
   		      [222.57, 542, 544],             
   		      [212.57, 604, 608], 
   		      [204.57, 660, 664], 
   		      [196.57, 719, 730],
   		   	  [184.57, 773, 773]
   		];
		var watchHeight = [222.30, 226.48, 218, 206.63];
		
		var xYHD = [1180, 1146, 1086, 1024, 962];
		var yYHD = [466, 554, 678, 804, 928];
		 $.ajax({
			 type:'post',
             dataType:'json',
             data:{SDate:sdate,dtype:dtype},
             url:'GetSYSWDSXData.action',
			 success:function(data){
				var pa1=[0.0, 0.0, 0.0, 0.0, 0.0];
				var pa2=[0.0, 0.0, 0.0, 0.0, 0.0];
				var pa3=[0.0, 0.0, 0.0, 0.0, 0.0];
				var pa4=[0.0, 0.0, 0.0, 0.0, 0.0];
				var py=[0.0, 0.0, 0.0, 0.0, 0.0];
				
				for(var i=0;i<data.length;i++) {
					var name=data[i].name;
					
					if(name == 'PA1-1') pa1[0] = data[i].sysw;
					if(name == 'PB1-1') pa1[1] = data[i].sysw;
					if(name == 'PC1-1') pa1[2] = data[i].sysw;
					if(name == 'PD1-1') pa1[3] = data[i].sysw;
					if(name == 'PE1-1') pa1[4] = data[i].sysw;
					
					if(name == 'PA2-1') pa2[0] = data[i].sysw;
					if(name == 'PB2-1') pa2[1] = data[i].sysw;
					if(name == 'PC2-1') pa2[2] = data[i].sysw;
					if(name == 'PD2-1') pa2[3] = data[i].sysw;
					if(name == 'PE2-1') pa2[4] = data[i].sysw;
					
					if(name == 'PA3-1') pa3[0] = data[i].sysw;
					if(name == 'PB3-1') pa3[1] = data[i].sysw;
					if(name == 'PC3-1') pa3[2] = data[i].sysw;
					if(name == 'PD3-1') pa3[3] = data[i].sysw;
					if(name == 'PE3-1') pa3[4] = data[i].sysw;
					
					if(name == 'PA4-1') pa4[0] = data[i].sysw;
					if(name == 'PB4-1') pa4[1] = data[i].sysw;
					if(name == 'PC4-1') pa4[2] = data[i].sysw;
					if(name == 'PD4-1') pa4[3] = data[i].sysw;
					if(name == 'PE4-1') pa4[4] = data[i].sysw;
					
					if(name == 'PY1-1') py[0] = data[i].sysw;
					if(name == 'PY2-1') py[1] = data[i].sysw;
					if(name == 'PY3-1') py[2] = data[i].sysw;
					if(name == 'PY4-1') py[3] = data[i].sysw;
					if(name == 'PY5-1') py[4] = data[i].sysw;
				}
				
				if(data.length > 0)
				{
					//画拆线
					drawDSX2(canvas, pa1, xValues1, heightData1, 1, watchHeight[0], "#EA0000");
					drawDSX2(canvas, pa2, xValues2, heightData2, 2, watchHeight[1], "#0000C6");
					drawDSX2(canvas, pa3, xValues3, heightData2, 3, watchHeight[2], "#D26900");
					drawDSX2(canvas, pa4, xValues4, heightData2, 4, watchHeight[3], "#AE57A4");
					//画溢洪道的名称和水位
					ctx.fillStyle = "#000000";
			   		ctx.font="13px Verdana";
					for(var i = 0; i < py.length; i++)
			   		{
						if(i == py.length-1) {  //最后一个点画在上面
							ctx.fillText(py[i].toFixed(2)+"m", afterTransX(xYHD[i]) - 25, afterTransY(yYHD[i]) - 15);
						} else {
							ctx.fillText(py[i].toFixed(2)+"m", afterTransX(xYHD[i]) - 25, afterTransY(yYHD[i]) + 25);
						}
				   		//ctx.fillText("PY"+(i+1)+"-1 "+py[i].toFixed(2)+"m", afterTransX(xYHD[i]) - 55, afterTransY(yYHD[i]) + 25);
				   		//ctx.fillText(py[i].toFixed(2)+"m", xYHD[i] - 35, yYHD[i] + 35);
			   		}
				}
			 }
         });

	}
    
</script>

</html>