<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>闸门监控</title>

<%@include file="../head1.jsp" %> 

<style type="text/css">  
    a{color:#333;text-decoration:none;display:block;height:10px;} 
    a:hover {color:#CC3300;text-decoration:underline;display:block;height:10px;}
</style> 

<script language="javascript">
    //初始化界面数据
    window.onload = function () {
    	openTab('401','ssxx1.jsp');     
    };
</script>

</head>
<body class="easyui-layout">

    <div data-options="region:'west'" style="width:207px;padding:5px;background:#2D6AC7;">
    	<div class="easyui-panel" title="闸门监控" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
    		<a href="#" onclick="openTab('401','ssxx1.jsp')">1#闸门实时监控</a><br>
            <a href="#" onclick="openTab('402','ssxx2.jsp')">2#闸门实时监控</a><br>
    	</div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="报表统计" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('411','bbtj.jsp')">报表统计</a>
        </div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="报警信息" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('421','bjcx.jsp')">报警查询</a>
        </div>
    </div>
    
    <div data-options="region:'center'">
        <iframe id='myframe' frameborder='0' scrolling='auto' style='width:100%;height:100%'></iframe>
    </div>
    
</body>
</html>

