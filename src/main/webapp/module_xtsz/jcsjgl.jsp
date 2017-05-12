<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统日志</title>
</head>
<link rel="stylesheet" type="text/css" href="../jquery-easyui/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="../jquery-easyui/themes/icon.css">

<script type="text/javascript" src="../jquery-easyui/jquery.min.js"></script>
<script type="text/javascript" src="../jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

<link rel="stylesheet" type="text/css" href="../common/css/module.css">

<style type="text/css">
	table.jgdstyle {
		font-family: verdana,arial,sans-serif;
		font-size:15px;
		color:#333333;
		border-width: 1px;
		border-collapse: collapse;
	}
	table.jgdstyle td {
		border-width: 0px;
		padding-left: 1px;
		padding-right: 1px;
		border-style: solid;
	}
	
</style>

    <div class="easyui-tabs" fit="true" id="tabs">
        <div title="水库管理" style="padding:5px;">
            <iframe frameborder="0" scrolling="no" width="100%" height="100%" src="skgl.jsp"></iframe>
        </div>
        <div title="水雨情监测点管理" style="padding:5px;">
            <iframe frameborder="0" scrolling="no" width="100%" height="100%" src="syqcdgl.jsp"></iframe>
        </div>
        <div title="断面管理" style="padding:5px;">
            <iframe frameborder="0" scrolling="no" width="100%" height="100%" src="dmgl.jsp"></iframe>
        </div>
        <div title="大坝监测点管理" style="padding:5px;">
            <iframe frameborder="0" scrolling="no" width="100%" height="100%" src="dbcdgl.jsp"></iframe>
        </div>
        <div title="设备管理" style="padding:5px;">
            <iframe frameborder="0" scrolling="no" width="100%" height="100%" src="sbgl.jsp"></iframe>
        </div>
        <div title="参数记录管理" style="padding:5px;">
            <iframe frameborder="0" scrolling="no" width="100%" height="100%" src="paragl.jsp"></iframe>
        </div>
    </div>
	
	