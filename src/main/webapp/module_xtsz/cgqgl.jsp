<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="../head1.jsp" %>

</head>

<body class="easyui-layout">
    <div data-options="region:'center'" style="padding:5px;">
        <table id="tt" class="easyui-datagrid" pagination="true" toolbar="#tb"
             url="GetAllCGQMsg" rownumbers="true" singleSelect="true" pageSize="20" fitColumns="true" fit="true">
        <thead>
            <tr>
              <th field="name" width="100">名称</th>
              <th field="type" width="80">类型</th>
            </tr>
        </thead>
		</table>
	</div>
	
	<div id="tb" style="padding:3px">
		<div style="border-top:0px solid #b8d0d6;padding:0px;">
			<a href="javascript:void(0)" onclick="addCGQ();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
			<a href="javascript:void(0)" onclick="editCGQ();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">修改</a>
			<a href="javascript:void(0)" onclick="delCGQ();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
		</div>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveCGQ()"  style="width:80px">保存</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
			style="width:80px;">取消</a>
    </div>
	<div id="dlg" class="easyui-dialog" title="传感器参数设置" data-options="modal:true,closed:true,buttons:'#dlg-buttons'" 
			style="width:500px;height:200px;padding:5px;">
	   <div class="easyui-panel" style="width:100%;height:100%;padding:10px">
			<form id="ff" method="post">
				<table border="0" cellpadding="0" cellspacing="0" style="width:100%;">
					<tr>
						<td height="30px">名称:</td>
						<td><input class="easyui-validatebox" id="name" name="name" required="true" style="width:99%"></input></td>
					</tr>
					<tr>
						<td height="30px">类型:</td>
						<td>
							<input id="type" name="type" class="easyui-combobox" fit="true" required="true"
								data-options="
									valueField: 'value',
									textField: 'label',
                                    panelHeight:'auto',
                                    editable:false,
									data: [{
										label: '渗压计',
										value: '1'
									},{
										label: '量水堰',
										value: '2'
									}]"
								>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
</body>
	
<script language="javascript">
	var url="";

	function addCGQ() {
		$('#dlg').dialog('open').dialog('setTitle','添加传感器参数信息');
		$('#ff').form('clear');
		
		url = 'AddCGQMsg';
	}
	
	function editCGQ() {
		$('#ff').form('clear');
		var row = $('#tt').datagrid('getSelected');
		if (row){
			$('#dlg').dialog('open').dialog('setTitle','修改传感器参数信息');
			$('#name').val(row.name);
			
			if(row.type == '渗压计') $('#type').combobox('setValue',1);
			if(row.type == '量水堰') $('#type').combobox('setValue',2);
			
			url = 'EditCGQMsg?Id='+row.id;
		} else {
			alert('没有选择要修改的传感器！');
		}
	}
	
	function saveCGQ() {
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
	
	function delCGQ() {
		var row = $('#tt').datagrid('getSelected');
		if (row){
			$.messager.confirm('系统提示','确定要删除所选传感器?',function(r){
				if (r){
					$.post('DelCGQMsg',{Id:row.id},function(result){
						if (result.success){
							$('#tt').datagrid('reload');	// reload the user data
						} else {
							$.messager.show({	// show error message
								title: 'Error',
								msg: result.msg
							});
						}
					},'json');
				}
			});
		}
	}
	
</script>
	
</html>