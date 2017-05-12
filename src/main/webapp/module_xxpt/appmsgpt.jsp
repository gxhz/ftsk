<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../head1.jsp" %> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script language="javascript">

    function sendMsg(){
        var msg=$('#textbox').textbox('getValue');
        if(msg == '') {
        	$.messager.alert('系统提示','发布信息不能为空!','error');
        	return;
        }
        
        $.messager.progress({
            title:'系统信息',
            msg:'正在发布信息，请稍候！',
        });  
        
        $.ajax({
    		type: 'post',
    		url: 'SendAPPMsg',
    		dataType: 'json',
    	    ContentType: 'application/json; charset=utf-8',
    		cache: true,
    		async: true,
    		data: {'wxmsg': encodeURIComponent(msg, "UTF-8")},
    		success:function(data){
    			$('#tt').datagrid('load');
    			
    			$.messager.progress('close');
    			$.messager.alert('系统消息', data.msg);
    		}
      	});
    }
    
    
</script>

</head>
<body class="easyui-layout">

    <div data-options="region:'north'" style="height:450px;padding:10px">
        <table id="tt" class="easyui-datagrid" url="GetAPPMsg" fitColumns="true"
            pagination="false" rownumbers="true" singleSelect="true" pageSize="200" fit="true">
            <thead>
                <tr>
                  <th field="fbsj" width="120">发布时间</th>
                  <th field="fbr" width="120">发布人</th>
                  <th field="fbnr" width="120">发布内容</th>
                  <th field="fbzt" width="120">发布状态</th>
                </tr>
            </thead>
        </table>
    </div>
    <div data-options="region:'center'" style="padding:10px">
        <input id="textbox" class="easyui-textbox" data-options="multiline:true" value="" style="width:100%;height:100%">
    </div>
    <div data-options="region:'south'" style="height:110px;text-align:center;padding:16px">
        <a href="#" class="easyui-linkbutton" style="width:250px;height:75px"onclick="sendMsg();" >推送消息</a>
    </div>

</body>
</html>