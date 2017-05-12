<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>参数记录管理</title>
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
			Sbid: $('#sbid').combobox('getValue'),
		});
	}
	
	function clearSearch(){
		$('#sbid').combobox('setValue', '');
	}
	
	function addSYQCD() {
		$('#dlg').dialog('open').dialog('setTitle','添加参数记录信息');
		$('#ff').form('clear');
		
		url = 'AddParametersAction';
	}
	
	function editSYQCD() {
		var row = $('#tt').datagrid('getSelected');
		if (row){
			$('#dlg').dialog('open').dialog('setTitle','修改参数记录信息');
			$('#ff').form('load',row);
			$('#Sbid').combobox('setValue', row.cdid);
			$('#sw').val(row.alarm_level);
			$('#xdg').val(row.wide_b);
			$('#zzxs').val(row.coefficient_rag);
			$('#dbxs').val(row.coefficient_bs);
			$('#pbxs1').val(row.coefficient_ss1);
			$('#pbxs2').val(row.coefficient_ss2);
			url = 'EditParametersAction?Id='+row.id;
		} else {
			alert('没有选择要修改的参数记录！');
		}
	}
	
	function saveSYQCD() {
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
	
	function delSYQCD() {
		var row = $('#tt').datagrid('getSelected');
		if (row){
			$.messager.confirm('系统提示','确定要删除所选参数记录?',function(r){
				if (r){
					$.post('DelParametersAction',{Id:row.id},function(result){
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
			url="GetParametersAction" rownumbers="true" singleSelect="true" pageSize="20">
        <thead>
            <tr>
                <th field="cdname" width="200">监测点名称</th>
                <th field="alarm_level" width="100">报警水位</th>
                <th field="wide_b" width="100">下底宽</th>
                <th field="coefficient_rag" width="100">粗糙系数</th>
                <th field="coefficient_bs" width="100">底坡系数</th>
                <th field="coefficient_ss1" width="100">边坡系数1</th>
                <th field="coefficient_ss2" width="100">边坡系数2</th>
                <th data-options="field:'cdid',sortable:true,hidden:true">所属测点ID</th>
            </tr>
        </thead>
		</table>
	
	<form id="fm" method="post">
		<div id="tb" style="padding:3px">
			<div style="border:0px solid #b8d0d6;padding:8px;width:width:100%">
				<table class="jgdstyle">
					<tr>
                        <td width="70">所属测点：</td>                         
                        <td width="180">
                            <input id="sbid" name="sbid" class="easyui-combobox" style="width:150px;"
									data-options="
											url:'GetCDMsgAction',
											method:'get',
											valueField:'id',
											textField:'name',
											panelHeight:'auto',
											editable:false
									">
                        </td>
						<td width="100" align="left"> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px;height:25px"onclick="doSearch()" >查询</a></td>
						<td width="100" align="left"> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px;height:25px"onclick="clearSearch()" >重置</a></td>
					</tr>
				</table>
			</div>
							
			<div style="border-top:1px solid #b8d0d6;padding:0px;">
				<a href="javascript:void(0)" onclick="addSYQCD();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
				<a href="javascript:void(0)" onclick="editSYQCD();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">修改</a>
				<a href="javascript:void(0)" onclick="delSYQCD();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
			</div>
		</div>
	</form>
	
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveSYQCD()"  style="width:80px">保存</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
			style="width:80px;">取消</a>
    </div>
	<div id="dlg" class="easyui-dialog" title="参数记录设置" data-options="closed:true,buttons:'#dlg-buttons'" 
			style="left:350px;top:20px;width:500px;height:490px;padding:5px;">
	   <div class="easyui-panel" style="width:100%;height:100%;padding:10px">
			<div style="padding:5px 0 10px 0;height:250px">
				<form id="ff" method="post">
					<table border="0" cellpadding="0" cellspacing="0" style="width:100%;">
                        <tr>
                            <td height="30px" width="70px">所属测点:</td>
                            <td><input id="Sbid" name="Sbid" class="easyui-combobox" style="width:99%;"
									data-options="
											url:'GetCDMsgAction',
											method:'get',
											valueField:'id',
											textField:'name',
											panelHeight:'auto',
											editable:false
									">
                                </td>
                        </tr>
						<tr>
							<td height="30px" width="70px">报警水位:</td>
							<td><input class="easyui-validatebox" id="sw" name="sw" required="true" style="width:99%"></input></td>
						</tr>
                        <tr>
                            <td height="30px" width="70px">下底宽:</td>
                            <td><input class="easyui-validatebox" id="xdg" name="xdg" required="true" style="width:99%"></input></td>
                        </tr>
                        <tr>
                            <td height="30px" width="70px">粗糙系数:</td>
                            <td><input class="easyui-validatebox" id="zzxs" name="zzxs" required="true" style="width:99%"></input></td>
                        </tr>
                        <tr>
                            <td height="30px" width="70px">底坡系数:</td>
                            <td><input class="easyui-validatebox" id="dbxs" name="dbxs" required="true" style="width:99%"></input></td>
                        </tr>
                        <tr>
                            <td height="30px" width="70px">边坡系数1:</td>
                            <td><input class="easyui-validatebox" id="pbxs1" name="pbxs1" required="true" style="width:99%"></input></td>
                        </tr>
                        <tr>
                            <td height="30px" width="70px">边坡系数2:</td>
                            <td><input class="easyui-validatebox" id="pbxs2" name="pbxs2" required="true" style="width:99%"></input></td>
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
    
    $('#tt').datagrid('hideColumn','name');

    window.onresize=function(){resizeCanvas();};
    function resizeCanvas(){
        var oh=parent.parent.parent.document.body.offsetHeight-250;
        document.getElementById('tt').style.height = oh+'px';
    }
</script>

