<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="../head1.jsp" %> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>0+020横剖面图</title>

</head>

<body>

	<div style="position: fixed;left:0px;right:0px;z-index: 888;padding: 5px;margin-left:auto;margin-right:auto;width:800px">
    	<a href="bthpmslylfbt.jsp">0+020横剖面图</a>
		<a href="bthpmslylfbt_1.jsp">0+080横剖面图</a>
		<a href="bthpmslylfbt_2.jsp">0+140横剖面图</a>  
		<a href="bthpmslylfbt_3.jsp">0+200横剖面图</a>  
		<a href="bthpmslylfbt_4.jsp">0+260横剖面图</a>  
		<a href="bthpmslylfbt_5.jsp">溢洪道纵剖面图</a>  
    </div>
    
	<div id="content" style="width:1600;height:800">
	<canvas id="canvas" width="1600px" height="800px" style="z-index:999" >
	</div> 
	
	<div style="position: fixed;left:0px;right:0px;bottom:80px;z-index: 888;padding: 5px;margin-left:auto;margin-right:auto;width:800px">
    	<table align="center"  border="0" cellpadding="0" cellspacing="0" style="padding:5px;">
			<tr>
				<td>数据类型：</td>                         
                <td width="150">
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
                <td>日期选择：</td>
				<td>
                    <input id="sdate" name="sdate" data-options="editable:false,onSelect:onSelect" 
                        class="easyui-datebox" required style="width:120px;height:28px">
                </td>
                <td width="80px"></td>
                <td width="120px">
                    <span style="font:normal 18px '微软雅黑'; color:#EA0000; position: relative; top:2px;">红线：坝体</span>
                </td>
                <td width="120px">
                    <span style="font:normal 18px '微软雅黑'; color:#0066CC; position: relative; top:2px;">蓝线：坝基</span>
                </td>
		    </tr>
		</table>
    </div>

</body>


<script>
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
		bgImg.src = "../images/hpm/20.jpg";
		bgImg.onload = function(){
			ctx.drawImage(bgImg, 0, 0);
			getData();
		};
	};
	
	function onSelect(date){
		getData();
	}

	var xValues = [602, 852, 1033, 1236];
	var yTop =  185.44; //232.56f的位置
	var yTop2 =  248.7; //222.3的位置
	var yUnit = (yTop2 - yTop)/(232.56-222.30); //以232.56为参照，找222.3f或其它任意一点算出来的单位高度
	
	var yRules = [240, 230, 220, 210, 200, 190, 180, 170, 160, 150, 140]; //值越大，越往上
	var designData = [222.3, 232.6, 222.57, 212.57];
	var realData = [183.6, 179.8, 184, 172.2];
	var realData2 = [183.8, 180.8, 183.06, 174.25];
	
	var gkgc = [183.6, 179.8, 184, 172.2];	//管口高程
	var msgc1 = [183.8, 180.8, 183.06, 174.25];	//埋设高程
	var msgc2 = [185.6, 182.8, 185.15, 176.14];	//埋设高程
	
	function getData() {
		var sdate,dtype;
		var sname='PA1-1,PA2-1,PA3-1,PA4-1,PA1-2,PA2-2,PA3-2,PA4-2';
		var ksw=0.0;
		
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
		
		var ctx = canvas.getContext("2d");
		ctx.clearRect(0,0,canvas.width,canvas.height);
		ctx.drawImage(bgImg, 0, 0);
		$.ajax({
            type:'post',
            dataType:'json',
            data:{SDate:sdate,names:sname,dtype:dtype},
            url:'GetSYSWJRXData.action',
            success:function(data) { 
            	realData[0] = data[0];
            	realData[1] = data[1];
            	realData[2] = data[2];
            	realData[3] = data[3];
            	
            	realData2[0] = data[4];
            	realData2[1] = data[5];
            	realData2[2] = data[6];
            	realData2[3] = data[7];
            	
            	ksw = data[8];  //库水位
				//draw the line
				var canvas = document.getElementById("canvas");
				
				drawDesign(canvas, designData, xValues, yTop, yUnit, 232.56);
				drawReal(canvas, realData, xValues, yTop, yUnit, 232.56, "#EA0000", -30);
				drawReal(canvas, realData2, xValues, yTop, yUnit, 232.56, "#0066CC", -10);
				
				ctx.fillStyle = "#0000FF";
				ctx.font="13px Verdana";
				ctx.fillText("库水位:"+ksw+" m", 400, 200);
            }
         }); 

		drawRule(canvas, yRules, 360, yTop, yUnit, 232.56);
		drawRule(canvas, yRules, canvas.offsetWidth -180, yTop, yUnit, 232.56);
		
		ctx.fillStyle = "#000000";
		ctx.font="20px Verdana";
		ctx.textAlign = "center";
		ctx.fillText("0+020横剖面图", canvas.width / 2, 600);
		ctx.textAlign = "left";
		
		//drawReal(canvas, gkgc, xValues, yTop, yUnit, 232.56, "#EA0000");
		//drawReal(canvas, msgc1, xValues, yTop, yUnit, 232.56, "#0066CC");
		//drawReal(canvas, msgc2, xValues, yTop, yUnit, 232.56, "#006600");
	}
</script>

</html>