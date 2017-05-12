<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

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
<body class="easyui-layout" onload="openTab('水位特征信息','dd_swtzxx.jsp')">

    <div data-options="region:'west'" style="width:207px;padding:5px;background:#2D6AC7;">
        <div class="easyui-panel" title="基本信息" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('水位特征信息','dd_swtzxx.jsp')">水位特征信息</a><br>
            <a href="#" onclick="openTab('水位库容信息','dd_swtzxx.jsp')">水位库容信息</a><br>
            <a href="#" onclick="openTab('水位设计调洪信息','dd_swtzxx.jsp')">水位设计调洪信息</a><br>
            <a href="#" onclick="openTab('水位流量关系','dd_swtzxx.jsp')">水位流量关系</a>
        </div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="模型参数" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('降雨径流预报','dd_swtzxx.jsp')">降雨径流预报</a><br>
            <a href="#" onclick="openTab('单位线维护','dd_swtzxx.jsp')">单位线维护</a>
        </div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="洪水预报调度" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('洪水预报','dd_swtzxx.jsp')">洪水预报</a><br>
            <a href="#" onclick="openTab('预报结果查询','dd_swtzxx.jsp')">预报结果查询</a><br>
            <a href="#" onclick="openTab('洪水调度','dd_swtzxx.jsp')">洪水调度</a><br>
            <a href="#" onclick="openTab('调度结果查询','dd_swtzxx.jsp')">调度结果查询</a><br>
            <a href="#" onclick="openTab('调度运用规程查询','dd_swtzxx.jsp')">调度运用规程查询</a>
        </div>
        <div style="padding:3px;"></div>
        <div class="easyui-panel" title="水资源信息管理" collapsible="true" collapsed="false" style="width:195px;height:auto;padding:10px;">
            <a href="#" onclick="openTab('水库水量信息管理','dd_swtzxx.jsp')">水库水量信息管理</a><br>
            <a href="#" onclick="openTab('水库代表年来水量特征值','dd_swtzxx.jsp')">水库代表年来水量特征值</a><br>
            <a href="#" onclick="openTab('水土平衡成果信息','dd_swtzxx.jsp')">水土平衡成果信息</a><br>
            <a href="#" onclick="openTab('水库调度运行方案','dd_swtzxx.jsp')">水库调度运行方案</a>
        </div>
    </div>

    <div data-options="region:'center'">
        <iframe id='myframe' frameborder='0' scrolling='auto' style='width:100%;height:100%'></iframe>
    </div>

</body>

</html>