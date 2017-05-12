<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="../head1.jsp" %> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style type="text/css">  
    a{color:#333;text-decoration:none;display:block;height:10px;} 
    a:hover {color:#CC3300;text-decoration:underline;display:block;height:10px;}
</style> 

<script language="javascript">
    //初始化界面数据
    window.onload = function () {
    	openTab('701','../module_sqcb/syqalarm.jsp');     
    };
</script>

</head>
<body class="easyui-layout">

    <div data-options="region:'west'" style="width:207px;padding:5px;background:#2D6AC7;">
        <div class="easyui-panel" title="水雨情报警信息" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('701','../module_sqcb/syqalarm.jsp')">报警查询</a>
        </div>
        <div style="padding:3px;"></div>
    	<div class="easyui-panel" title="大坝安全报警信息" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
    		<a href="#" onclick="openTab('711','../module_dbjc/dbalarm.jsp')">报警查询</a>
    	</div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="变形观测报警信息" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('721','../module_bxgc/bxalarm.jsp')">报警查询</a>
        </div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="闸门监控报警信息" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('731','../module_zmkz/bjcx.jsp')">报警查询</a>
        </div>
    </div>
    
    <div data-options="region:'center'">
        <iframe id='myframe' frameborder='0' scrolling='auto' style='width:100%;height:100%'></iframe>
    </div>

</body>
</html>