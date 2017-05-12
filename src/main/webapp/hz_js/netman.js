window.WebSocket = window.WebSocket || window.MozWebSocket;  
    
if (!window.WebSocket) {  
	alert("您的浏览器不支持WebSocket！请使用Chrome浏览器。");
} else {
	var websocket = new WebSocket("ws://192.168.200.37:8090/websocket");
	
	setInterval("send('rtdata,1,1|PV,2|PV,3|PV,4|PV,5|PV,xxx')", 1000);

	function send(cmd) {
		if (!window.WebSocket) { return; }
		if((cmd != "")&&(websocket.readyState == WebSocket.OPEN)) {
			websocket.send(cmd);
		}
	}
	
	websocket.onmessage = function(evt) {
		var obj=JSON.parse(evt.data);
		document.getElementById('showrtdata0').innerHTML = obj.value1;
		document.getElementById('showrtdata1').innerHTML = obj.value2;
		document.getElementById('showrtdata2').innerHTML = obj.value3;
		document.getElementById('showrtdata3').innerHTML = obj.value4;
		document.getElementById('showrtdata4').innerHTML = obj.value5;
	};
	
	websocket.onopen = function(evt) {
		//document.getElementById('showrtdata0').innerHTML = "Web Socket opened";
	};
	
	websocket.onclose = function(evt) {
		//document.getElementById('showrtdata0').innerHTML = "Web Socket closed";
	};
	  
} 
