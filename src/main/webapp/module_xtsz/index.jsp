<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统设置</title>

<%@include file="../head1.jsp" %>

<style type="text/css">  
    a{color:#333;text-decoration:none;display:block;height:10px;} 
    a:hover {color:#CC3300;text-decoration:underline;display:block;height:10px;}
</style> 

<script language="javascript">
    //初始化界面数据
    window.onload = function () {
    	openTab('90','yggl.jsp');     
    };
</script>

</head>
<body class="easyui-layout">
    <div data-options="region:'west'" style="width:207px;padding:5px;background:#2D6AC7;">
        <!-- 
        <div class="easyui-panel" title="基础数据" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('传感器管理','cgqgl.jsp')">传感器管理</a>
        </div>
        <div style="padding:3px;"></div>
         -->
        <div class="easyui-panel" title="系统设置" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <!-- 
            <a href="#" onclick="openTab('部门管理','bmgl.jsp')">部门管理</a><br>
            <a href="#" onclick="openTab('职务管理','zwgl.jsp')">职务管理</a><br>
            -->
            <a href="#" onclick="openTab('90','yggl.jsp')">员工管理</a><br>
            <a href="#" onclick="openTab('91','mkczgl.jsp')">模块操作管理</a><br>
            <a href="#" onclick="openTab('92','ygqxgl.jsp')">员工权限管理</a><br>
            <a href="#" onclick="openTab('93','xtrz.jsp')">系统日志</a><br>
            <!-- <a href="#" onclick="openTab('基础数据管理','jcsjgl.jsp')">基础数据管理</a><br> -->
        </div>
    </div>
    
    <div data-options="region:'center'">
        <iframe id='myframe' frameborder='0' scrolling='auto' style='width:100%;height:100%'></iframe>
    </div>
</body>
</html>
 					