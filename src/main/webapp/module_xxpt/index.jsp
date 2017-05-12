<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信息平台</title>

<%@include file="../head1.jsp" %>

<style type="text/css">  
    a{color:#333;text-decoration:none;display:block;height:10px;} 
    a:hover {color:#CC3300;text-decoration:underline;display:block;height:10px;}
</style> 

<script language="javascript">
    //初始化界面数据
    window.onload = function () {
    	openTab('80','wxpt.jsp');     
    };
</script>

</head>
<body class="easyui-layout">
    <div data-options="region:'west'" style="width:207px;padding:5px;background:#2D6AC7;">
        <div class="easyui-panel" title="信息平台" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('80','wxpt.jsp')">微信平台</a><br>
            <a href="#" onclick="openTab('81','sendmail.jsp')">邮件平台</a><br>
            <a href="#" onclick="openTab('82','appmsgpt.jsp')">APP消息推送平台</a><br>
            <a href="#" onclick="openTab('83','sendsms.jsp')">短信平台</a>
        </div>
    </div>
    
    <div data-options="region:'center'">
        <iframe id='myframe' frameborder='0' scrolling='auto' style='width:100%;height:100%'></iframe>
    </div>
</body>
</html>
 					