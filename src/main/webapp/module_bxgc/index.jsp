<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>变形观测</title>

<%@include file="../head1.jsp" %> 

<style type="text/css">  
    a{color:#333;text-decoration:none;display:block;height:10px;} 
    a:hover {color:#CC3300;text-decoration:underline;display:block;height:10px;}
</style> 

<script language="javascript">
    //初始化界面数据
    window.onload = function () {
    	openTab('301','ssxx.jsp');     
    };
</script>

</head>
<body class="easyui-layout">

    <div data-options="region:'west'" style="width:207px;padding:5px;background:#2D6AC7;">
    	<div class="easyui-panel" title="变形监测" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
    		<a href="#" onclick="openTab('301','ssxx.jsp')">实时监测</a>
    	</div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="报表统计" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('311','sdbbtj.jsp')">时段报表统计</a><br>
            <a href="#" onclick="openTab('312','ybbtj.jsp')">月报表统计</a><br>
            <a href="#" onclick="openTab('313','nbbtj.jsp')">年报表统计</a><br>
            <a href="#" onclick="openTab('314','onbbtj.jsp')">单点年报表统计</a>
        </div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="过程线" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('321','sdgcx.jsp')">时段过程线</a><br>
            <a href="#" onclick="openTab('322','ygcx.jsp')">月过程线</a><br>
            <a href="#" onclick="openTab('323','ngcx.jsp')">年过程线</a><br>
            <a href="#" onclick="openTab('324','ongcx.jsp')">单点年过程线</a>
        </div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="数据整编" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('341','zdmsxwyfbt.jsp')">纵断面竖向位移分布图</a><br>
            <a href="#" onclick="openTab('342','zdmspwyfbt.jsp')">纵断面水平位移分布图</a><br>
            <a href="#" onclick="openTab('343','hdmsxwyfbt.jsp')">横断面竖向位移分布图</a><br>
            <a href="#" onclick="openTab('344','sxwylpmdcxt3.jsp')">竖向位移量平面等值线图</a><br>
            <a href="#" onclick="openTab('345','sxwylpmdcxt2.jsp')">竖向位移量平面等值线图-2</a>
        </div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="报警信息" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('331','bxalarm.jsp')">报警查询</a>
        </div>
    </div>
    
    <div data-options="region:'center'">
        <iframe id='myframe' frameborder='0' scrolling='auto' style='width:100%;height:100%'></iframe>
    </div>
    
</body>
</html>

