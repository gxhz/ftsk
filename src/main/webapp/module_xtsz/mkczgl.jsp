<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模块操作管理</title>

<%@include file="../head1.jsp" %>

</head>

<body class="easyui-layout">

    <div data-options="region:'west',split:true" style="width:230px;padding:0px;">
        <ul id="mktree" class="easyui-tree" style="padding:5px;"></ul>
    </div>
    <div data-options="region:'center'" style="padding:5px;">
        <table id="tt" class="easyui-datagrid" pagination="true" toolbar="#tb"
             url="GetOperationAction" rownumbers="true" singleSelect="true" pageSize="20" fitColumns="true" fit="true">
            <thead>
                <tr>
                  <th field="name" width="180">名称</th>
                  <th field="des" width="360">描述</th>
                    <th field="unuseflag" width="160">禁用状态</th>
                </tr>
            </thead>
        </table>
    </div>

	<div id="tb" style="padding:3px">				
		<div style="padding:0px;">
			<a href="javascript:void(0)" onclick="addCz();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
			<a href="javascript:void(0)" onclick="editCz();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">修改</a>
			<a href="javascript:void(0)" onclick="delCz();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
		</div>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveCz()"  style="width:80px">保存</a>
	   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
			style="width:80px;">取消</a>
	</div>
	<div id="dlg" class="easyui-dialog" title="操作设置" data-options="closed:true,buttons:'#dlg-buttons'" 
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
							<td height="30px">禁用状态:</td>
							<td>
								<input id="Unuseflag" name="Unuseflag" class="easyui-combobox" style="width:100%;"
									data-options="
											valueField: 'label',
										textField: 'value',
										data: [{
											label: '启用',
											value: '启用'
										},{
											label: '禁用',
											value: '禁用'
										}]
									">
							</td>
						</tr>
						<tr>
							<td height="30px">备注:</td>
							<td><input class="easyui-validatebox" id="Des" name="Des" style="width:99%"></input></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	
</body>
	
<script language="javascript">
	var url="";
	var mkid="";
	
	$(function(){
		$('#mktree').tree({
			url:'../jsonfile/mklx.json',
			onClick:function(node){
				var id=node.id;
				if(id < 0) {
					mkid = "";
				} else {
					mkid = id;
				}
				//查询模块操作
				$('#tt').datagrid('load',{
					mkid: id
				});
			}
		})
	});
	
	function addCz() {
		if(mkid == '') return;
		$('#dlg').dialog('open').dialog('setTitle','添加模块操作信息');
		$('#ff').form('clear');
		
		$('#Unuseflag').combobox('setValue', '启用');
		url = 'AddOperationAction?Mkid='+mkid;
	}
	
	function editCz() {
		var row = $('#tt').datagrid('getSelected');
		if (row){
			$('#dlg').dialog('open').dialog('setTitle','修改模块操作信息');
			$('#ff').form('load',row);
			
			$('#Name').val(row.name);
			$('#Unuseflag').combobox('setValue', row.unuseflag);
			$('#Des').val(row.des);
			url = 'EditOperationAction?Id='+row.id;
		} else {
			alert('没有选择要修改的操作！');
		}
	}
	
	function saveCz() {
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
	
	function delCz() {
		var row = $('#tt').datagrid('getSelected');
		if (row){
			$.messager.confirm('系统提示','确定要删除所选模块操作?',function(r){
				if (r){
					$.post('DelOperationAction',{Id:row.id},function(result){
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
	
</html>