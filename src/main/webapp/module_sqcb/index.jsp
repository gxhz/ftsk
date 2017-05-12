<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>水情测报</title>

<%@include file="../head1.jsp" %> 

<style type="text/css">  
    a{color:#333;text-decoration:none;display:block;height:10px;} 
    a:hover {color:#CC3300;text-decoration:underline;display:block;height:10px;}
</style> 

<script language="javascript">
    //初始化界面数据
    window.onload = function () {
    	openTab('101','ssxx.jsp');     
    };
</script>

</head>

<body class="easyui-layout">
	<div data-options="region:'west'" style="width:210px;padding:5px;background:#2D6AC7;">
    
        <div class="easyui-panel" title="水雨情实时数据" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('101','ssxx.jsp')">水情实时数据表</a>
        </div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="库水位报表统计" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('111','ksw_sdbbtj.jsp')">时段报表统计</a><br>
            <a href="#" onclick="openTab('112','ksw_ybbtj.jsp')">月报表统计</a><br>
            <a href="#" onclick="openTab('113','ksw_onbbtj.jsp')">单点年报表统计</a>
        </div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="库水位过程线" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('121','ksw_sdgcx.jsp')">时段过程线</a><br>
            <a href="#" onclick="openTab('122','ksw_ygcx.jsp')">月过程线</a><br>
            <a href="#" onclick="openTab('123','ksw_ongcx.jsp')">单点年过程线</a>
        </div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="降雨量报表统计" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('131','jyl_sdbbtj.jsp')">时段报表统计</a><br>
            <a href="#" onclick="openTab('132','jyl_ybbtj.jsp')">月报表统计</a><br>
            <a href="#" onclick="openTab('133','jyl_onbbtj.jsp')">单点年报表统计</a>
        </div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="降雨量过程线" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('141','jyl_sdgcx.jsp')">时段过程线</a><br>
            <a href="#" onclick="openTab('142','jyl_ygcx.jsp')">月过程线</a><br>
            <a href="#" onclick="openTab('143','jyl_ongcx.jsp')">单点年过程线</a>
        </div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="报警信息" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('151','syqalarm.jsp')">报警查询</a>
        </div>
    
    	<!-- 
        <div class="easyui-panel" title="当前测站信息" collapsible="true" collapsed="false" style="width:180px;height:auto;padding:10px;">
    		<a href="#" onclick="openTab('水情实时数据表','ssxx.jsp')">水情实时数据表</a><br>
    		<a href="#" onclick="openTab('当前站点状态表','sjcx.jsp')">当前站点状态表</a><br>
    		<a href="#" onclick="openTab('当前站点信息表','qxzzt.jsp')">当前站点信息表</a><br>
            <a href="#" onclick="openTab('当前报文直方图','bbtj.jsp')">当前报文直方图</a><br>
            <a href="#">当前原始报文表</a><br>
            <a href="#">流域组网分布图</a>
    	</div>
    	<div style="padding:3px;"></div>
    	<div class="easyui-panel" title="当前水情信息" collapsible="true" collapsed="false" style="width:180px;height:auto;padding:10px;">
    		<a href="#">当前降雨量报表</a><br>
            <a href="#">当前时段雨量报表</a><br>
            <a href="#">当前雨量直方图</a><br>
            <a href="#">当前水位、库容/流量表</a><br>
            <a href="#">当前时段水位、库容/流量表</a><br>
            <a href="#">当前水位库容/流量过程线图</a>
    	</div>
    	<div style="padding:3px;"></div>
    	<div class="easyui-panel" title="时段水情信息" collapsible="true" collapsed="false" style="width:180px;height:auto;padding:10px;">
    		<a href="#">时段降雨量报表</a><br>
            <a href="#">时段雨量直方图</a><br>
            <a href="#">时段水位、库容/流量表</a>
    	</div>
    	<div style="padding:3px;"></div>
    	<div class="easyui-panel" title="日水情信息" collapsible="true" collapsed="false" style="width:180px;height:auto;padding:10px;">
    		<a href="#">日降雨量报表</a><br>
            <a href="#">日降雨量直方图</a><br>
            <a href="#">日水位、库容/流量表</a><br>
            <a href="#">日水位库容/流量过程线图</a><br>
            <a href="#">日原始报文表</a><br>
            <a href="#">日来报计数表</a><br>
            <a href="#">日来报直方图</a>
    	</div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="月水情信息" collapsible="true" collapsed="false" style="width:180px;height:auto;padding:10px;">
            <a href="#">月降雨量报表</a><br>
            <a href="#">月降雨量直方图</a><br>
            <a href="#">多站月水位、库容/流量表</a><br>
            <a href="#">单站月水位、库容/流量表</a><br>
            <a href="#">月水位库容/流量过程线图</a><br>
            <a href="#">月来报计数表</a><br>
            <a href="#">月来报直方图</a>
        </div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="年水情信息" collapsible="true" collapsed="false" style="width:180px;height:auto;padding:10px;">
            <a href="#">多站年降雨量报表</a><br>
            <a href="#">单站年降雨量报表</a><br>
            <a href="#">年降雨量直方图</a><br>
            <a href="#">多站年水位、库容/流量表</a><br>
            <a href="#">单站平均水位库容/流量表</a><br>
            <a href="#">单站瞬时水位库容/流量表</a><br>
            <a href="#">年水位库容/流量过程线图</a><br>
            <a href="#">年来报计数表</a><br>
            <a href="#">年来报直方图</a>
        </div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="水情综合信息" collapsible="true" collapsed="false" style="width:180px;height:auto;padding:10px;">
            <a href="#">水情综合报表</a><br>
            <a href="#">全国24小时降雨量实况图</a><br>
            <a href="#">全国24小时降雨量预报图</a><br>
            <a href="#">全国48小时降雨量预报图</a>
        </div>
         -->
         
	</div>	
	
	<div data-options="region:'center'">
        <iframe id='myframe' frameborder='0' scrolling='auto' style='width:100%;height:100%'></iframe>
    </div>
</body>

</html>