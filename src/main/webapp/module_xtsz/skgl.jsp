<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>水库管理</title>
<link rel="stylesheet" type="text/css" href="../jquery-easyui/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="../jquery-easyui/themes/icon.css">

<script type="text/javascript" src="../jquery-easyui/jquery.min.js"></script>
<script type="text/javascript" src="../jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

<link rel="stylesheet" type="text/css" href="../common/css/module.css">

<script language="javascript">
	var url="";

	function doSearch(){
		$('#tt').datagrid('load',{
			gh: $('#gh').val(),
			Name: $('#name').val()
		});
	}
	
	function clearSearch(){
		$("#gh").val("");
		$("#name").val("");
	}
	
	function addSK() {
		$('#dlg').dialog('open').dialog('setTitle','添加水库信息');
		$('#ff').form('clear');
		
		url = 'AddReservoirsAction';
	}
	
	function editSK() {
		var row = $('#tt').datagrid('getSelected');
		if (row){
			$('#dlg').dialog('open').dialog('setTitle','修改水库信息');
			$('#ff').form('load',row);
			$('#Name').val(row.name);
			$('#Des').val(row.des);
			url = 'EditReservoirsAction?Id='+row.id;
		} else {
			alert('没有选择要修改的水库！');
		}
	}
	
	function saveSK() {
		//提交信息
		$('#ff').form('submit',{
			url: url,
			onSubmit: function(){
				return $(this).form('validate');
			},
			success: function(result){
				var result = eval('('+result+')');
				if (result.errorMsg){
					$.messager.show({
						title: 'Error',
						msg: result.errorMsg
					});
				} else {
					$('#dlg').dialog('close');		// close the dialog
					$('#tt').datagrid('reload');	// reload the user data
				}
			}
		});
	}
	
	function delSK() {
		var row = $('#tt').datagrid('getSelected');
		if (row){
			$.messager.confirm('系统提示','确定要删除所选水库?',function(r){
				if (r){
					$.post('DelReservoirsAction',{Id:row.id},function(result){
						if (result.success){
							$('#tt').datagrid('reload');	// reload the user data
						} else {
							$.messager.show({	// show error message
								title: 'Error',
								msg: result.errorMsg
							});
						}
					},'json');
				}
			});
		}
	}
	
</script>

</head>
<body>
		<table id="tt" class="easyui-datagrid" style="width:100%;" pagination="true" toolbar="#tb"
			url="GetReservoirsAction" rownumbers="true" singleSelect="true" pageSize="20">
        <thead>
            <tr>
              <th field="name" width="260">名称</th>
              <th field="des" width="460">描述</th>
            </tr>
        </thead>
		</table>
	
	<form id="fm" method="post">
		<div id="tb" style="padding:3px">
			<div style="border:0px solid #b8d0d6;padding:8px;width:width:100%">
				<table class="jgdstyle">
					<tr>
						<td width="50">名称：</td>							
						<td width="150">
							<input type="text" name="name" id="name" style="width:120px;">
						</td>
						<td width="100" align="left"> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px;height:25px"onclick="doSearch()" >查询</a></td>
						<td width="100" align="left"> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px;height:25px"onclick="clearSearch()" >重置</a></td>
					</tr>
				</table>
			</div>
							
			<div style="border-top:1px solid #b8d0d6;padding:0px;">
				<a href="javascript:void(0)" onclick="addSK();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
				<a href="javascript:void(0)" onclick="editSK();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">修改</a>
				<a href="javascript:void(0)" onclick="delSK();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
			</div>
		</div>
	</form>
	
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveSK()"  style="width:80px">保存</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
			style="width:80px;">取消</a>
    </div>
	<div id="dlg" class="easyui-dialog" title="水库参数设置" data-options="closed:true,buttons:'#dlg-buttons'" 
			style="left:350px;top:20px;width:500px;height:490px;padding:5px;">
	   <div class="easyui-panel" style="width:100%;height:100%;padding:10px">
			<div style="padding:5px 0 10px 0;height:250px">
				<form id="ff" method="post">
					<table border="0" cellpadding="0" cellspacing="0" style="width:100%;">
						<tr>
							<td height="30px" width="70px">名称:</td>
							<td><input class="easyui-validatebox" id="Name" name="Name" required="true" style="width:99%"></input></td>
						</tr>
						<tr>
							<td height="30px">描述:</td>
							<td><input class="easyui-validatebox" id="Des" name="Des" style="width:99%"></input></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

<script language="javascript">
    resizeCanvas();

    window.onresize=function(){resizeCanvas();};
    function resizeCanvas(){
        var oh=parent.parent.parent.document.body.offsetHeight-250;
        document.getElementById('tt').style.height = oh+'px';
    }
</script>

