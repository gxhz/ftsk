<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title>那板水库安全监测系统</title>

<style type="text/css">  
    #container{height:100%}  
</style> 

<%@include file="head.jsp" %> 

<script type="text/javascript" src="dwr/util.js"></script>       
<script type="text/javascript" src="dwr/engine.js"></script >     
<script type="text/javascript" src="dwr/interface/MessagePush.js"></script>    

<script type="text/javascript">
	
    dwr.engine.setActiveReverseAjax(true);  
    dwr.engine.setNotifyServerOnPageUnload(true,true);  
      
    //显示DWR推送消息
    function showPushMsg(content){   
    	$.messager.show({
			title:'系统信息',
			msg:content,
			height:180,
			timeout:0,
			showType:'slide'
		});
    }  

	function openTab(text,url){
		if($("#tabs").tabs('exists',text)){
			$("#tabs").tabs('select',text);
		}else{
			if(text == '防洪调度') {
				$.messager.show({
					title:'系统信息',
					msg:'此功能暂未开放！'
				});
			} else {
				var content="<iframe frameborder='0' scrolling='no' style='width:100%;height:99.6%' src="+url+"></iframe>";
				$("#tabs").tabs('add',{
					title:text,
					closable:true,
					content:content
				});
			}
		}
	}
	
</script>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=SNl8YmwdeK2RHUjKsv3yKAY6">
//v1.5版本的引用方式：src="http://api.map.baidu.com/api?v=1.5&ak=您的密钥"
//v1.4版本及以前版本的引用方式：src="http://api.map.baidu.com/api?v=1.4&key=您的密钥&callback=initialize"
</script>

<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />

<script src="hz_js/bdmap.js" type="text/javascript"></script>

</head>
<body class="easyui-layout">
	<div region="north" style="height: 87px;background-color: #dedede;background-image:url('images/index/top_bg.jpg')">
		<!-- logo -->
		<div style="float:left">
			<img src="images/index/logo.png" style="height: 80px;"></img>
		</div>
		<!-- 菜单项图标 -->
		<div id="menudiv" style="position:absolute;right:20px;top:-5px;width:auto;">
			<table>
				<tr>
                    <td style="width: 80px;"><a href="javascript:void(0);" onclick="openTab('水雨情监测','module_sqcb/index.jsp')"><img src="images/index/menu_sqcb.png" ></img></a></td>		
					<td style="width: 80px;"><a href="javascript:void(0);" onclick="openTab('大坝监测','module_dbjc/index.jsp')"><img src="images/index/menu_dbjc.png" ></img></a></td>
					<td style="width: 80px;"><a href="javascript:void(0);" onclick="openTab('变形观测','module_bxgc/index.jsp')"><img src="images/index/menu_bxgc.png" ></img></a></td>
					<td style="width: 80px;"><a href="javascript:void(0);" onclick="openTab('闸门监控','module_zmkz/index.jsp')"><img src="images/index/menu_zmjc.png" ></img></a></td>
                    <td style="width: 80px;"><a href="javascript:void(0);" onclick="openTab('预报调度','module_hsyb/index.jsp')"><img src="images/index/menu_hsyb.png" ></img></a></td>
					<!--
                    <td style="width: 80px;"><a href="javascript:void(0);" onclick="openTab('预报调度','module_fhdd/index.jsp')"><img src="images/index/menu_fhdd.png" ></img></a></td>
                     -->
                    <td style="width: 80px;"><a href="javascript:void(0);" onclick="openTab('报警信息','module_bjxx/index.jsp')"><img src="images/index/menu_bjxx.png" ></img></a></td>
					<td style="width: 80px;"><a href="javascript:void(0);" onclick="openTab('大坝视频监控','http://192.168.0.213/')"><img src="images/index/menu_dbvideo.png" ></img></a></td>
                    <td style="width: 80px;"><a href="javascript:void(0);" onclick="openTab('门卫视频监控','http://192.168.0.67/')"><img src="images/index/menu_mwvideo.png" ></img></a></td>
                    <!-- 
                    <td style="width: 80px;"><a href="javascript:void(0);" onclick="openTab('信息平台','module_xxpt/index.jsp')"><img src="images/index/menu_xxpt.png" ></img></a></td>
					<td style="width: 80px;"><a href="javascript:void(0);" onclick="openTab('系统设置','module_xtsz/index.jsp')"><img src="images/index/menu_setting.png" ></img></a></td>
				     -->
                </tr>
			</table>
		</div>
	</div>

	<div region="center">
		<div class="easyui-tabs" data-options="tabWidth:120" fit="true" border="false" id="tabs">
			<div title="地 图">
				<div id="container"></div>
				<script type="text/javascript">
					showbdmap("container");
				</script>
			</div>
		</div>
	</div>
	
	<div region="south" style="height: 25px;background-image:url('images/index/top_bg.jpg');" align="center">
        <span style="font:normal 13px '微软雅黑'; color:#ffffff; position: relative; top:2px;">Copyright
            广西宏智科技有限公司</span>
    </div>
</body>
</html>