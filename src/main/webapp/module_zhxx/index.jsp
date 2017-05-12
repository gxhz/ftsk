<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>大坝监测</title>

<%@include file="../head1.jsp" %> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
    function openTab(text,url){
        $('#myframe').attr('src',url);
    }
</script>

<style type="text/css">  
    a{color:#333;text-decoration:none;display:block;height:10px;} 
    a:hover {color:#CC3300;text-decoration:underline;display:block;height:10px;}
</style> 

</head>
<body class="easyui-layout" onload="openTab('水平位移','ssxx.jsp')">

    <div data-options="region:'west'" style="width:207px;padding:5px;background:#2D6AC7;">
    	<div class="easyui-panel" title="数据整编" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
    		<a href="#" onclick="openTab('水平位移','ssxx.jsp')">水平位移</a><br>
    		<a href="#" onclick="openTab('沉降位移','sjcx.jsp')">沉降位移</a><br>
    		<a href="#" onclick="openTab('库水位','qxzzt.jsp')">库水位</a><br>
            <a href="#" onclick="openTab('渗压水位','bbtj.jsp')">渗压水位</a><br>
            <a href="#" onclick="openTab('渗流量','bbtj.jsp')">渗流量</a><br>
            <a href="#" onclick="openTab('降雨量','bbtj.jsp')">降雨量</a>
    	</div>
    </div>
    <div data-options="region:'center'">
        <iframe id='myframe' frameborder='0' scrolling='auto' style='width:100%;height:100%'></iframe>
    </div>
    
</body>
</html>

<script language="javascript">
    resizeCanvas();
    
    window.onresize=function(){resizeCanvas()};
    function resizeCanvas(){
    	var oh=parent.document.body.offsetHeight-90-25-43;
        document.getElementById('right').style.height = oh+'px';
        document.getElementById('menudiv').style.height = oh-10+'px';
    }
</script>
