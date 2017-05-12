<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统日志</title>
</head>

<%@include file="../head1.jsp" %>

<script language="javascript">
    function doSearch(){
        $('#tt').datagrid('load',{
            STime: $('#STime').val(),
            ETime: $('#ETime').val()
        });
    }
    
    function clearSearch(){
        $("#STime").val("");
        $("#ETime").val("");
    }
    
    function delZw() {
        var row = $('#tt').datagrid('getSelected');
        if (row){
            $.messager.confirm('系统提示','确定要删除所选日志？',function(r){
                if (r){
                    $.post('DelRzAction',{Id:row.id},function(result){
                        if (result.success){
                            $('#tt').datagrid('reload');    // reload the user data
                        } else {
                            $.messager.show({   // show error message
                                title: 'Error',
                                msg: result.errorMsg
                            });
                        }
                    },'json');
                    
                    //添加日志
                    $.post('AddRzAction', {memo: '删除日志'}, 'json');
                }
            });
        }
    }
    
</script>

</head>

<body class="easyui-layout">
    <div data-options="region:'center'" style="padding:5px;">
        <table id="tt" class="easyui-datagrid" pagination="true" toolbar="#tb"
             url="GetRzAction" rownumbers="true" singleSelect="true" pageSize="20" fitColumns="true" fit="true">
            <thead>
                <tr>
                    <th data-options="field:'memo',width:400">日志内容</th>
                    <th data-options="field:'time',width:150">时间</th>
                    <th data-options="field:'ygname',width:150">操作员</th>
                </tr>
            </thead>
        </table>
    </div>

    <div id="tb" style="padding:3px">
            <div style="border:0px solid #b8d0d6;padding:8px;width:width:100%">
                <table class="jgdstyle">
                    <tr>
                        <td width=80>起始时间：<td>
                        <td width=110><input type="text" name="STime" id="STime" onclick="WdatePicker()" style="width:180px;"><td>
                        <td width=80>结束时间：<td>
                        <td width=110><input type="text" name="ETime" id="ETime" onclick="WdatePicker()" style="width:180px;"><td>
                        <td width="100" align="left"> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px;height:25px"onclick="doSearch();" >查询</a></td>
                        <td width="100" align="left"> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px;height:25px"onclick="clearSearch()" >重置</a></td>
                    </tr>
                </table>
            </div>
            
            <div style="border-top:1px solid #b8d0d6;padding:0px;">
                <a href="javascript:void(0)" onclick="delZw();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
            </div>
        </div>

    
</body>

</html>

