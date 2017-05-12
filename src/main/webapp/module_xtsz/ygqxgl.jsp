<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>员工权限管理</title>
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

</head>

<body class="easyui-layout">

	<div data-options="region:'west',split:true" title="员工" style="width:300px;">
		<table id="tt" class="easyui-datagrid" data-options="singleSelect: true" style="width:100%;height:100%" 
            url="GetStaffNameAction" fitColumns="true">
		<thead>
			<tr>
			  <th field="ck" checkbox="true"></th>
              <th data-options="field:'id',width:80,align:'center',hidden:true">ID</th>
			  <th field="name" width="160">姓名</th>
			</tr>
		</thead>
		</table>
	</div>
	
	<div data-options="region:'center',title:'功能模块'">
		<ul id="mktree" class="easyui-tree"></ul>
	</div>
	
	<div data-options="region:'east',split:true" title="操作权限" style="width:300px;">
		<table id="ttt" class="easyui-datagrid" style="width:100%;height:100%" url="GetOperationNameAction"
            fitColumns="true" data-options="onClickRow: onClickRow">
		<thead>
			<tr>
			  <th field="ck" checkbox="true"></th>
              <th data-options="field:'id',width:80,align:'center',hidden:true">ID</th>
			  <th field="name" width="160">操作名称</th>
			</tr>
		</thead>
		</table>
	</div>
    
    <!-- 
    <div data-options="region:'south',split:true" style="height:80px;text-align:center;padding:8px">
        <a href="#" class="easyui-linkbutton" style="width:160px;height:45px" onclick="saveqx()" >保存权限修改</a>
    </div>
     -->
	
</body>
	
<script language="javascript">
	var url="";
	var mkid="";
	
	$('#ttt').datagrid({onLoadSuccess: function() {
    	if(mkid == '') return;
		//获取员工ID
		var ygids="";
		var ygrows = $('#tt').datagrid('getSelections');
		for(var i=0; i<ygrows.length; i++) {
			ygids += ygrows[i].id+',';
		}
		//根据员工ID和模块ID获取权限
		$.ajax({
            type:'post',
            dataType:'json',
            data:{ygid:ygids,mkid:mkid},
            url:'GeYGQX.action',
            success:function(data) {
            	if(data.success) {
            		var datas=data.msg.split("");
            		for(var i=0;i<datas.length;i++) {
            			var odata=datas[i];
            			if(odata == '1') {
            				$('#ttt').datagrid('selectRow', i);
            			}
            		}
            	}
        	}
        });}   
	});  
	
	$(function(){
		$('#mktree').tree({
			url:'../jsonfile/mklx.json',
			onClick:function(node) {
				var id=node.id;
				if((id == 1)||(id == 2)||(id == 3)||(id == 301)||(id == 302)
					||(id == 303)||(id == 304)||(id == 7)) {
					mkid = "";
				} else {
					mkid = id;
				}
				
				//查询模块操作
				$('#ttt').datagrid('load',{
					mkid: id
				});
				
			}
		});
	});
	
	//表格单击事件
	function onClickRow(index){
		saveqx();  //保存权限
	}
	
	//保存员工权限
	function saveqx() {
		if(mkid == '') return;
		
		//获取员工ID
		var ygids="";
		var ygrows = $('#tt').datagrid('getSelections');
		for(var i=0; i<ygrows.length; i++){
			ygids += ygrows[i].id+',';
		}
		
		//获取选中操作ID
		var szzids = [];
		var szzrows = $('#ttt').datagrid('getSelections');
		for(var i=0; i<szzrows.length; i++){
			szzids.push(szzrows[i].id);
		}
		
		//获取操作ID
		var zzids="";
		var zzrows = $('#ttt').datagrid('getRows');
		for(var i=0; i<zzrows.length; i++){
			var flag=false;
			
			for(var j=0;j<szzids.length;j++) {
				if(szzids[j] == zzrows[i].id) {
					flag = true;
					break;
				}
			}
			
			if(flag) {
				zzids += '1';
			} else {
				zzids += '0';
			}
		}
		
		$.ajax({
            type:'post',
            dataType:'json',
            data:{ygid:ygids,mkid:mkid,zzid:zzids},
            url:'SaveYGQX.action',
            success:function(data) {
            	//alert(data.msg);
        	}
        });
	}
	
</script>
</html>