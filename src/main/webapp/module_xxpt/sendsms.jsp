<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../head1.jsp" %> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script language="javascript">

    function sendSMS(){
        var msg=$('#textbox').textbox('getValue');
        if(msg == '') {
        	$.messager.alert('系统提示','发送短信内容不能为空!','error');
        	return;
        }
        
      	//获取选中的手机号
		var tels='';
		var ygrows = $('#tt').datagrid('getSelections');
		for(var i=0; i<ygrows.length; i++){
			if(tels == '') {
				tels = ygrows[i].phone;
			} else {
				tels += ','+ygrows[i].phone;
			}
		}
		
		if(tels == '') {
			$.messager.alert('系统提示','没有选择要发送的手机号!','error');
        	return;
		}
        
        $.messager.progress({  
            title:'系统信息',  
            msg:'正在发送短信，请稍候！',  
        });  
        
        $.ajax({
    		type: 'post',
    		url: 'SendSMS',
    		dataType: 'json',
    	    ContentType: 'application/json; charset=utf-8',
    		cache: true,
    		async: true,
    		data: {'smsmsg': encodeURIComponent(msg, "UTF-8"), 'tels': tels},
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
                  <th field="phone" width="120">手机号</th>
                </tr>
            </thead>
        </table>
    </div>
    <div data-options="region:'center'" style="padding:10px">
        <input id="textbox" class="easyui-textbox" data-options="multiline:true" value="" style="width:100%;height:100%">
    </div>
    <div data-options="region:'south'" style="height:110px;text-align:center;padding:16px">
        <a href="#" class="easyui-linkbutton" style="width:250px;height:75px"onclick="sendSMS();" >发送短信</a>
    </div>

</body>
</html>