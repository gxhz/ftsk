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
             url="GetStaffAction" rownumbers="true" singleSelect="true" pageSize="20" fitColumns="true" fit="true">
        <thead>
            <tr>
                <th field="gh" width="100">工号</th>
                <th field="name" width="80">姓名</th>
			    <th field="sex" width="80">性别</th>
				<th field="phone" width="120">手机号码</th>
				<th field="email" width="120">电子邮件</th>
				<th field="des" width="150">备注</th>
				<th field="unuseflag" width="100">禁用状态</th>
                <th field="userType" width="100">员工类型</th>
            </tr>
        </thead>
		</table>
	</div>
	
	<div id="tb" style="padding:3px">
        <form id="fm" method="post">
			<div style="border:0px solid #b8d0d6;padding:8px;width:width:100%">
				<table class="jgdstyle">
					<tr>
						<td width="50">工号：</td>							
						<td width="150">		
							<input type="text" name="gh" id="gh" style="width:120px;">
						</td>
						<td width="50">姓名：</td>							
						<td width="150">
							<input type="text" name="name" id="name" style="width:120px;">
						</td>
						<td width="100" align="left"> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px;height:25px"onclick="doSearch()" >查询</a></td>
						<td width="100" align="left"> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px;height:25px"onclick="clearSearch()" >重置</a></td>
					</tr>
				</table>
			</div>
							
			<div style="border-top:1px solid #b8d0d6;padding:0px;">
				<a href="javascript:void(0)" onclick="addYG();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
				<a href="javascript:void(0)" onclick="editYG();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">修改</a>
				<a href="javascript:void(0)" onclick="delYG();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
                <a href="javascript:void(0)" onclick="initQX();" class="easyui-linkbutton" data-options="plain:true">初始化权限</a>
			</div>
        </form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveYG()"  style="width:80px">保存</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
			style="width:80px;">取消</a>
    </div>
	<div id="dlg" class="easyui-dialog" title="员工设置" data-options="closed:true,buttons:'#dlg-buttons'" 
			style="left:350px;top:20px;width:500px;height:490px;padding:5px;">
	   <div class="easyui-panel" style="width:100%;height:100%;padding:10px">
			<div style="padding:5px 0 10px 0;height:250px">
				<form id="ff" method="post">
					<table border="0" cellpadding="0" cellspacing="0" style="width:100%;">
						<tr>
							<td height="30px" width="90px">工号:</td>
							<td><input class="easyui-validatebox" id="Gh" name="Gh" required="true" style="width:99%"></input></td>
						</tr>
						<tr>
							<td height="30px" width="80px">密码:</td>
							<td><input class="easyui-validatebox" id="Password" name="Password" required="true" style="width:99%" type="password"></input></td>
						</tr>
						<tr>
							<td height="30px" width="80px">确订密码:</td>
							<td><input class="easyui-validatebox" id="Confirmpassword" name="Confirmpassword" required="true" style="width:99%" type="password"></input></td>
						</tr>
						<tr>
							<td height="30px">姓名:</td>
							<td><input class="easyui-validatebox" id="Name" name="Name" required="true" style="width:99%"></input></td>
						</tr>
						<tr>
							<td height="30px">性别:</td>
							<td>				
								<input id="Sex" name="Sex" class="easyui-combobox" style="width:99%;"
									data-options="
											url:'combobox_sex.json',
											method:'get',
											valueField:'text',
											textField:'text',
											panelHeight:'auto',
											editable:false
									">
							</td>                  
						</tr>
						<tr>
							<td height="30px">员工类型:</td>
							<td>
								<input id="Sysuser" id="Sysuser" name="Sysuser" class="easyui-combobox" style="width:99%;"
									data-options="
										valueField: 'value',
										textField: 'label',
                                        editable:false,
										data: [{
											label: '管理员',
											value: '1'
										},{
											label: '普通员工',
											value: '2'
										}]"
									">
							</td>
						</tr>
						<tr>
							<td height="30px">手机号码:</td>
							<td><input class="easyui-validatebox" id="Phone" name="Phone" style="width:99%"></input></td>
						</tr>
						<tr>
							<td height="30px">电子邮件:</td>
							<td><input class="easyui-validatebox" id="Email" name="Email" style="width:99%" ></input></td>
						</tr>
						<tr>
							<td height="30px">禁用状态:</td>
							<td>
								<input id="Unuseflag" name="Unuseflag" class="easyui-combobox" style="width:99%;"
									data-options="
										valueField: 'label',
										textField: 'value',
                                        editable:false,
										data: [{
											label: '启用',
											value: '启用'
										},{
											label: '禁用',
											value: '禁用'
										}]"
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

	function doSearch(){
		$('#tt').datagrid('load',{
			gh: $('#gh').val(),
			name: $('#name').val()
		});
	}
	
	function clearSearch(){
		$("#gh").val("");
		$("#name").val("");
	}
	
	function addYG() {
		$('#dlg').dialog('open').dialog('setTitle','添加员工信息');
		$('#ff').form('clear');
		
		$('#Sex').combobox('setValue', '男');
		$('#Sysuser').combobox('setValue', '2');
		$('#Unuseflag').combobox('setValue', '启用');
		
		url = 'AddStaffAction';
	}
	
	function editYG() {
		$('#ff').form('clear');
		var row = $('#tt').datagrid('getSelected');
		if (row){
			$('#dlg').dialog('open').dialog('setTitle','修改员工信息');
			$('#ff').form('load',row);
			$('#Gh').val(row.gh);
			$('#Password').val(row.password);
			$('#Confirmpassword').val(row.password);
			$('#Name').val(row.name);
			$('#Sex').combobox('setValue',row.sex);
			if(row.bm_id > 0)	
				$('#Bm_id').combobox('setValue', row.bm_id);
			if(row.zw_id > 0)
				$('#Zw_id').combobox('setValue', row.zw_id);
			$('#Sysuser').combobox('setValue',row.sysuser);
			$('#Phone').val(row.phone);
			$('#Email').val(row.email);
			$('#Unuseflag').combobox('setValue', row.unuseflag);
			$('#Des').val(row.des);

			url = 'EditStaffAction?Id='+row.id;
		} else {
			$.messager.alert('系统提示','没有选择要修改的员工！','error');
		}
	}
	
	function saveYG() {
		//提交信息
		if($('#Password').val()==$('#Confirmpassword').val()){
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
			
		} else{
			$.messager.alert('系统提示','密码不一致，请重新输入密码！','error');
			return;
		}
		
	}
	
	function delYG() {
		var row = $('#tt').datagrid('getSelected');
		if (row){
			if(row.userType == '管理员') {
				$.messager.alert('系统提示','管理员不能删除！','error');
				return;
			} else {
				$.messager.confirm('系统提示','确定要删除所选员工?',function(r){
					if (r){
						$.post('DelStaffAction',{Id:row.id},function(result){
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
	}
	
	function initQX() {
		var row = $('#tt').datagrid('getSelected');
		if (row){
			if(row.userType == '管理员') {
				$.messager.alert('系统提示','管理员不能初始化权限！','error');
				return;
			} else {
				$.messager.confirm('系统提示','确定要初始化所选员工的权限吗?',function(r){
					if (r){
						$.post('InitYGQX',{qxyggh: row.id},function(result){
							$.messager.show({
								title: 'Error',
								msg: result.msg
							});
						},'json');
					}
				});
			}
		}
	}
	
</script>
	
</html>