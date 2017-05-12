<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="../head1.jsp" %> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>溢洪道纵剖面图</title>

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
		bgImg.src = "../images/hpm/1000.jpg";
		bgImg.onload = function(){
			ctx.drawImage(bgImg, 0, 0);
			getData();
		};
	};
	
	function onSelect(date){
		getData();
	}

	var xValues = [271, 447, 706, 968, 1230];
	var yTop =  192.44; //219.06f的位置
	var yTop2 =  326.7; //188.1的位置
	var yUnit = (yTop2 - yTop)/(219.06-188.1); //以232.56为参照，找222.3f或其它任意一点算出来的单位高度
	
	var yRules = [240, 230, 220, 210, 200, 190, 180, 170, 160, 150, 140]; //值越大，越往上
	var designData = [222.3, 232.6, 222.57, 212.57, 183.01];
	var realData = [222.3, 229, 252, 180, 90.57];
	
	var gkgc = [217.2, 213.8, 193, 181.5, 181.6];	//管口高程
	var msgc1 = [217.4, 213.95, 193.1, 181.65, 181.7];	//埋设高程
	
	function getData() {
		var sdate,dtype;
		var sname='PY1-1,PY2-1,PY3-1,PY4-1,PY5-1';
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
            	realData[4] = data[4];
            	ksw = data[8];  //库水位
				//draw the line
				var canvas = document.getElementById("canvas");
				drawDesign(canvas, designData, xValues, yTop, yUnit, 219.06);
				drawReal(canvas, realData, xValues, yTop, yUnit, 219.06, "#EA0000", -10);
             }
         }); 

		drawRule(canvas, yRules, 230, yTop, yUnit, 219.06);
		drawRule(canvas, yRules, canvas.offsetWidth - 150, yTop, yUnit, 219.06);
		
		ctx.fillStyle = "#000000";
		ctx.font="20px Verdana";
		ctx.textAlign = "center";
		ctx.fillText("溢洪道纵剖面图", canvas.width / 2, 600);
		ctx.textAlign = "left";
		
		//drawReal(canvas, gkgc, xValues, yTop, yUnit, 232.56, "#EA0000");
		//drawReal(canvas, msgc1, xValues, yTop, yUnit, 232.56, "#0066CC");
	}
</script>

</html>