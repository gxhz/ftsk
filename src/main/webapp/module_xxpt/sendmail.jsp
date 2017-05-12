<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../head1.jsp" %> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script language="javascript">

    function sendMail(){
        var msg=$('#textbox').textbox('getValue');
        if(msg == '') {
        	$.messager.alert('系统提示','发送邮件内容不能为空!','error');
        	return;
        }
        
      	//获取选中的邮箱
		var mails='';
		var ygrows = $('#tt').datagrid('getSelections');
		for(var i=0; i<ygrows.length; i++){
			if(mails == '') {
				mails = ygrows[i].email;
			} else {
				mails += ','+ygrows[i].email;
			}
		}
		
		if(mails == '') {
			$.messager.alert('系统提示','没有选择要发送的邮箱!','error');
        	return;
		}
        
        $.messager.progress({  
            title:'系统信息',  
            msg:'正在发送邮件，请稍候！',  
        });  
        
        $.ajax({
    		type: 'post',
    		url: 'SendMail',
    		dataType: 'json',
    	    ContentType: 'application/json; charset=utf-8',
    		cache: true,
    		async: true,
    		data: {'mailmsg': encodeURIComponent(msg, "UTF-8"), 'mails': mails},
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
        <table id="tt" class="easyui-datagrid" pagination="true" url="GetStaffAction" 
            rownumbers="true" pageSize="50" fitColumns="true" fit="true">
            <thead>
                <tr>
                  <th field="ck" checkbox="true"></th>
                  <th field="gh" width="100">工号</th>
                  <th field="name" width="80">姓名</th>
				  <th field="sex" width="80">性别</th>
                  <th field="email" width="120">邮箱</th>
                </tr>
            </thead>
        </table>
    </div>
    <div data-options="region:'center'" style="padding:10px">
        <input id="textbox" class="easyui-textbox" data-options="multiline:true" value="" style="width:100%;height:100%">
    </div>
    <div data-options="region:'south'" style="height:110px;text-align:center;padding:16px">
        <a href="#" class="easyui-linkbutton" style="width:250px;height:75px"onclick="sendMail();" >发送邮件</a>
    </div>

</body>
</html>