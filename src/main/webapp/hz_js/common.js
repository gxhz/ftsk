function getQueryStr(str) {
	//alert(window.liveParams);
	var result = "";
	if(window.liveParams) {
		for(var i=0; i<window.liveParams.length; i++) {
			var param = window.liveParams[i];
			if(param.key === str) {
				result = param.value;
				break;
			}
		}
	} 
	if(result == ""){
		var LocString = String(window.document.location.href);
	    var rs = new RegExp("(^|)" + str + "=([^&]*)(&|$)", "gi").exec(LocString), tmp;
	    if (tmp = rs) {
	        result = decodeURIComponent(tmp[2]);
	    }
	}
	return result;
}

//优化画线的质量
CanvasRenderingContext2D.prototype.onePixelLineTo = function(fromX, fromY, toX, toY, backgroundColor, vertical) { 
	var currentStrokeStyle = this.strokeStyle; 
	this.beginPath(); 
	this.moveTo(fromX, fromY); 
	this.lineTo(toX, toY); 
	this.closePath(); 
	this.lineWidth=2; 
	this.stroke(); 
	this.beginPath(); 
	if(vertical) { 
	this.moveTo(fromX+1, fromY); 
	this.lineTo(toX+1, toY); 
	} else { 
	this.moveTo(fromX, fromY+1); 
	this.lineTo(toX, toY+1); 
	} 
	this.closePath(); 
	this.lineWidth=2; 
	this.strokeStyle=backgroundColor; 
	this.stroke(); 
	this.strokeStyle = currentStrokeStyle; 
}; 

CanvasRenderingContext2D.prototype.dashedLine  = function (x, y, x2, y2, dashArray) {        
    if (!dashArray) dashArray = [10, 2];        
    var dashCount = dashArray.length;        
    this.moveTo(x, y);        
    var dx = (x2 - x), dy = (y2 - y);        
    var slope = dy/dx;
    var distRemaining = Math.sqrt(dx * dx + dy * dy);        
    var dashIndex = 0, draw = true;        
    while (distRemaining >= 0.1) {            
        var dashLength = dashArray[dashIndex++ % dashCount];            
        if (dashLength > distRemaining) dashLength = distRemaining;
        if(dx == 0){
            var signal = (y2 > y ? 1 : -1);
            y += dashLength * signal;
        }else{
            var xStep = Math.sqrt(dashLength * dashLength / (1 + slope * slope));             
            var signal = (x2 > x ? 1 : -1);             
            x += xStep * signal;            
            y += slope * xStep * signal;       
        }
        this[draw ? 'lineTo' : 'moveTo'](x, y);            
        distRemaining -= dashLength;            
        draw = !draw;       
    }    
};
	
	function drawRule(canvas, yRules, x, yTop, yUnit, maxValue) {
		var h = canvas.offsetHeight;
	    var ctx = canvas.getContext("2d");
	    ctx.fillStyle = "#000000";
	    ctx.font = 'bold 12px consolas';
	    ctx.fillText('高程(标尺)', x-25, yTop + (maxValue-yRules[0])*yUnit - 10);
	    
	   	for(var i = 0; i< yRules.length; i++) {
	   		ctx.fillStyle = "#000000";
	   		ctx.fillText(i==0? "" : yRules[i], x-30,yTop + (maxValue-yRules[i])*yUnit);
	   		ctx.beginPath(); 
	   		ctx.lineWidth  = 3;
	   		if(i % 2 == 0) {
	   			ctx.strokeStyle = "#000000";
	   			ctx.fillStyle = "#000000";//设置填充颜色
	   		} else {
	   			ctx.strokeStyle = "#B7B7B7";
	   			ctx.fillStyle = "#B7B7B7";//设置填充颜色
	   		}
	   		
	   		ctx.moveTo(x, yTop + (maxValue-yRules[i])*yUnit);
		    ctx.lineTo(x, yTop + (maxValue-yRules[i+1])*yUnit);
		    ctx.stroke();//画线
		    ctx.closePath(); 
	   	}
	   
		
	}

	function drawDesign(canvas, design, xValues, yTop, yUnit, maxValue) {
		//暂时先不画设计线
		/*
		var h = canvas.offsetHeight;
	    var ctx = canvas.getContext("2d");
	    ctx.strokeStyle = "#FF0000";
	    ctx.fillStyle = "#FF0000";//设置填充颜色
	   	ctx.lineWidth  = 1;
	   	ctx.beginPath(); 
	   	
	   	//ctx.moveTo(xValues[0], h-  yUnits[0] * design[0]);
	   	for(var i = 0; i< design.length-1; i++) {
	   		ctx.dashedLine(xValues[i], yTop + (maxValue-design[i])*yUnit, xValues[i+1], yTop + (maxValue-design[i+1])*yUnit);
		    //ctx.moveTo(xValues[1], h - yUnits[1] * design[1]);
		    //ctx.dashedLine(xValues[1], h - yUnits[1] * design[1], xValues[2], h - yUnits[2] * design[2]);
		    
		    //ctx.moveTo(xValues[2], h - yUnits[2] * design[2]);
		    //ctx.dashedLine(xValues[2], h - yUnits[2] * design[2], xValues[3], h - yUnits[3] * design[3]);
	   	}
	   
	    ctx.stroke();//画线
		ctx.closePath(); */
	}
	
//	//画设计线，里面的颜色请调整。
//	function drawDesign(canvas, design, xValues, yUnits) {
//		var h = canvas.offsetHeight;
//	    var ctx = canvas.getContext("2d");
//	    ctx.strokeStyle = "#FF0000";
//	    ctx.fillStyle = "#FF0000";//设置填充颜色
//	   	ctx.lineWidth  = 1;
//	   	ctx.beginPath(); 
//	   	
//	   	//ctx.moveTo(xValues[0], h-  yUnits[0] * design[0]);
//	   	for(var i = 0; i< yUnits.length-1; i++) {
//	   		ctx.dashedLine(xValues[i], h  - yUnits[i] * design[i], xValues[i+1], h - yUnits[i+1] * design[i+1]);
//		  	
//		    //ctx.moveTo(xValues[1], h - yUnits[1] * design[1]);
//		    //ctx.dashedLine(xValues[1], h - yUnits[1] * design[1], xValues[2], h - yUnits[2] * design[2]);
//		    
//		    //ctx.moveTo(xValues[2], h - yUnits[2] * design[2]);
//		    //ctx.dashedLine(xValues[2], h - yUnits[2] * design[2], xValues[3], h - yUnits[3] * design[3]);
//	   	}
//	   
//	    ctx.stroke();//画线
//		ctx.closePath(); 
//	}
	
	//画实际线，里面的颜色请调整。
	function drawReal(canvas, real, xValues, yTop, yUnit, maxValue, colour, wordOffset) {
		var h = canvas.offsetHeight;
	    var ctx = canvas.getContext("2d");
	    ctx.strokeStyle = colour;
	    ctx.fillStyle = colour;
	   	ctx.lineWidth  = 3;
	   	ctx.beginPath(); 
	   	for(var i = 0; i< real.length-1; i++) {
	   		ctx.moveTo(xValues[i], yTop + (maxValue-real[i])*yUnit);
		    ctx.lineTo(xValues[i+1], yTop + (maxValue-real[i+1])*yUnit);
	   	}
	    ctx.stroke();//画线
		ctx.closePath(); 
   		ctx.font="13px Verdana"
	   	for(var i = 0; i < real.length; i++)
   		{
	   		ctx.fillText(parseFloat(real[i]).toFixed(2)+" m"
	   				, xValues[i], yTop + (maxValue-real[i])*yUnit + wordOffset);
	   		//ctx.fillText(dataName.substring(0, 2)+(i+1)+"-"+dataName.substring(2)
	   		//		, xValues[i], yTop + (maxValue-real[i])*yUnit + wordOffset);
   		}
	}
	
	//画渗流压力平面等势线
	function drawDSX(canvas, real, xValues, heightData, name, colour) {
		var nameArr = ["A", "B", "C", "D", "E"];
	    var ctx = canvas.getContext("2d");
	    ctx.strokeStyle = colour;
	    ctx.fillStyle = colour;
	   	ctx.lineWidth  = 3;
	   	ctx.beginPath(); 
	   	for(var i = 0; i< real.length-1; i++) {
	   		//底图进行过调整，对点位置进行缩放
	   		var x = afterTransX(xValues[i] );
	   		var x1 = afterTransX(xValues[i+1] );
	   		ctx.moveTo(x, calcDSXPointY(real[i], heightData));
		    ctx.lineTo(x1, calcDSXPointY(real[i+1], heightData));
	   	}
	    ctx.stroke();//画线
		ctx.closePath(); 
		//画数值和名字
   		//ctx.fillStyle = "#000000";
   		ctx.font="13px Verdana"
	   	for(var i = 0; i < real.length; i++)
   		{
	   		//底图进行过调整，对点位置进行缩放
	   		var x = afterTransX(xValues[i] );
	   		ctx.fillText("P"+nameArr[i]+name+" "+real[i].toFixed(2)+"m"
	   				, x - 45, calcDSXPointY(real[i], heightData)+ 20);
   		}
	}
	//画渗流压力平面等势线
	function drawDSX2(canvas, real, xValues, heightData, name, watchHeight, colour) {
		var nameArr = ["A", "B", "C", "D", "E"];
	    var ctx = canvas.getContext("2d");
	    drawDashLine(ctx, afterTransX(xValues[0]) - 200, calcDSXPointY(watchHeight, heightData)
	    		, afterTransX(xValues[xValues.length - 1] ) + 20, calcDSXPointY(watchHeight, heightData)
	    		, "#000000", 10);
	   	var avarage = 0;
	   	for(var i = 0; i < real.length; i++)
   		{
	   		avarage += real[i];
   		}
	   	avarage = avarage / real.length;
   		ctx.font="13px Verdana";
	    ctx.fillText(avarage.toFixed(2)+" m"
   				, afterTransX(xValues[0]) - 260, calcDSXPointY(watchHeight, heightData) + 3);
	    ctx.strokeStyle = colour;
	    ctx.fillStyle = colour;
	   	ctx.lineWidth  = 3;
	   	ctx.beginPath(); 
	   	for(var i = 0; i< real.length-1; i++) {
	   		//底图进行过调整，对点位置进行缩放
	   		var x = afterTransX(xValues[i]);
	   		var x1 = afterTransX(xValues[i+1] );
	   		ctx.moveTo(x, calcDSXPointY(real[i] - (avarage - watchHeight), heightData));
		    ctx.lineTo(x1, calcDSXPointY(real[i+1] - (avarage - watchHeight), heightData));
	   	}
	    ctx.stroke();//画线
		ctx.closePath(); 
		//画数值和名字
   		//ctx.fillStyle = "#000000";
	   	for(var i = 0; i < real.length; i++)
   		{
	   		//底图进行过调整，对点位置进行缩放
	   		var x = afterTransX(xValues[i] );
	   		ctx.fillText("P"+nameArr[i]+name+" "+real[i].toFixed(2)+" m"
	   				, x - 45, calcDSXPointY(real[i] - (avarage - watchHeight), heightData)+ 20);
   		}
	}
	//计算渗流压力平面等势线图某点y轴的值
	function calcDSXPointY(dataValue, heightData){
		for(var i = 0; i < heightData.length; i++)
		{
			//如果正好在平面上的值，取平面上下两条线的中间的位置
			if(dataValue == heightData[i][0])
			{
				var ret = ((heightData[i][1] + heightData[i][2]) / 2);
				//底图进行过调整，对点位置进行缩放
				return afterTransY(ret);
			}
			//如果值大于某个平面的值了，说明该点y轴的值取在这个平面与上个平面之间的区域
			else if(dataValue > heightData[i][0])
			{
				//做个保护，如果值大于坝顶的值，y轴的值做多也是在坝顶的中间
				if(i == 0)
				{
					var ret = ((heightData[0][1] + heightData[0][2]) / 2);
					return afterTransY(ret);
				}
				else
				{
					//计算一个单位比例，表示1m在屏幕中是多少个像素
					var yUnit = (heightData[i - 1][1] - heightData[i][2]) / (heightData[i - 1][0] - heightData[i][0]);
					var ret = (((dataValue - heightData[i][0]) * yUnit) + heightData[i][2]);
					return afterTransY(ret);
				}
			}
		}
		//如果都没return，说明值取在最后一个平面以后的位置
		//如果突破最低点，单位按一个从坝顶到最低的平均单位算
		var yUnit = (heightData[0][1] - heightData[heightData.length - 1][2]) / (heightData[0][0] - heightData[heightData.length - 1][0]);
		var ret = (((dataValue - heightData[heightData.length - 1][0]) * yUnit) + heightData[heightData.length - 1][2]);
		return afterTransY(ret);
	}
	
	function afterTransX(x)
	{
		return x* 1.15 - 226.5;
	}
	function afterTransY(y)
	{
		return y * 1.15 - 110;
	}
	
	//画竖向位移量平面等值线图上的点
	function drawSXWY(canvas, data, positoinData, circleCenter, deltaRadius, unitRule)
	{
		var ctx = canvas.getContext("2d");
		//动态算每个圈上升对应的数值
		var maxData = data[0];
		for(var i = 0; i < positoinData.length; i++)
		{
			if(data[i] > maxData)
			{
				maxData = data[i];
			}
		}
		unitRule = maxData.toFixed(2) / 10;
		//画圈上的数字
		for(var i = 0; i < 10; i++)
		{
			var rule = ((i + 1)*unitRule);
			ctx.fillText(rule.toFixed(2), circleCenter[0] - 13, circleCenter[1] - ((i + 1)*deltaRadius) +8);
		}
		//计算像素/mm的比例尺（注意这里是距离圆心距离的位置，后面还要根据改点的斜率计算出x, y的值）
		var rUnit = deltaRadius / unitRule;
		
		var pointImg = new Image();
		pointImg.src = "../images/sxwy/point.png";
		//要先把图像加载了才可以开始画，要不然部分浏览器是看不到图的
		pointImg.onload = function(){
			for(var i = 0; i < positoinData.length; i++)
			{
				//计算与圆心的距离
				var pointRadius = data[i] * rUnit;
				var x = 0; 
				var y = 0;
				if(positoinData[i][0] == 0)
				{
					y = pointRadius;
				}
				else if(positoinData[i][1] == 0)
				{
					x = pointRadius;
				}
				else
				{
					//根据距离和斜率，计算实际的x和y
					var k = positoinData[i][0] / positoinData[i][1];
					x = Math.sqrt(k * k * pointRadius * pointRadius / ((k * k) +1));
					if(positoinData[i][0] < 0)
					{
						x = -1 * x;
					}
					y = x / k;
				}
				//-4是因为图片是9*9的，把中心贴过去
				ctx.drawImage(pointImg, x + circleCenter[0] - 4, (y * -1) + circleCenter[1] - 4);	//y为负数因为屏幕的坐标系y轴是向下的
				var watchPointName = "W" + parseInt(i / 3 + 1) + (i % 3 + 1);
				if(parseInt(i / 3 + 1) == 7)
				{
					watchPointName = "WA" + (i % 3 + 1);
				}
				else if(parseInt(i / 3 + 1) == 8)
				{
					watchPointName = "WB" + (i % 3 + 1);
				}
				else if(parseInt(i / 3 + 1) == 9)
				{
					watchPointName = "WC" + (i % 3 + 1);
				}
				ctx.fillText(watchPointName, x + circleCenter[0] + 10, (y * -1) + circleCenter[1] + 5);
			}
		};
	}
	
	
	//画竖向位移量平面等值线图
	function drawDZX2(canvas, xValue, yValue, colour) {
	    var ctx = canvas.getContext("2d");
	    ctx.strokeStyle = colour;
	    ctx.fillStyle = colour;
	   	ctx.lineWidth  = 3;
	   	ctx.beginPath(); 
	   	for(var i = 0; i< xValue.length-1; i++) {
	   		var x = xValue[i];
	   		var y = yValue[i];
	   		
	   		var x1 = xValue[i+1];
	   		var y1 = yValue[i+1];
	   		
	   		ctx.moveTo(x, y);
		    ctx.lineTo(x1, y1);
	   		//ctx.bezierCurveTo(x, y+50, x1, y1+50, x1, y1);
	   	}
	    ctx.stroke();//画线
		ctx.closePath(); 
	}
	
	//画竖向位移量平面等值线图
	function drawDZX3(canvas, Value, xValue, yValue) {
	    var ctx = canvas.getContext("2d");
	   	ctx.lineWidth  = 3;
	   	ctx.beginPath(); 
	   	
	   	for(var i = 0; i< xValue.length-1; i++) {
	   		var x = xValue[i];
	   		var y = yValue[i];
	   		
	   		var x1 = xValue[i+1];
	   		var y1 = yValue[i+1];
	   		
	   		ctx.moveTo(x, y);
		    ctx.lineTo(x1, y1);
	   		//ctx.bezierCurveTo(x, y+50, x1, y1+50, x1, y1);
	   	}
	   	
	    ctx.stroke();//画线
		ctx.closePath(); 
	}
	
	function drawDashLine(ctx, startPosX, startPosY, endPosX, endPosY, colour, seperate)
	{
		ctx.strokeStyle = colour;
	    ctx.fillStyle = colour;
		ctx.lineWidth = 1;
		ctx.beginPath();
		//calculate the distance between start and end point
		var deltaX = endPosX - startPosX;
		var deltaY = endPosY - startPosY;
		var distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
		var dashNum = distance / (seperate * 2);
		for(var i = 0; i < dashNum; i++)
		{
			var dashX = startPosX + ((deltaX / distance * seperate) * (i * 2));
			var dashY = startPosY + ((deltaY / distance * seperate) * (i * 2));
			var nextDashX = startPosX + ((deltaX / distance * seperate) * ((i * 2) + 1));
			var nextDashY = startPosY + ((deltaY / distance * seperate) * ((i * 2) + 1));
			if(deltaX == 0)
			{
				dashX = startPosX;
				nextDashX = startPosX;
			}
			if(deltaY == 0)
			{
				dashY = startPosY;
				nextDashY = startPosY;
			}
			ctx.moveTo(dashX, dashY);
			ctx.lineTo(nextDashX, nextDashY);
		}
		ctx.stroke();
		ctx.closePath();
	}
	
	//cookie操作方法
	function setCookie(name,value)
	{
		var exp = new Date();
		exp.setTime(exp.getTime() + 1*60*60*1000);
		document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
	}
	function getCookie(name)
	{
		var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		if(arr=document.cookie.match(reg))
		return unescape(arr[2]);
		else
		return null;
	}
	function delCookie(name)
	{
		var exp = new Date();
		exp.setTime(exp.getTime() - 1);
		var cval=getCookie(name);
		if(cval!=null)
		document.cookie= name + "="+cval+";expires="+exp.toGMTString();
	}
