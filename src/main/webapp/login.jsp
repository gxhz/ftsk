<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>那板水库安全监测系统</title>

    <%@include file="head.jsp" %>
    
    <script type="text/javascript">
        function login() {
            var username = $('#username').textbox('getValue');
            var passwd = $('#passwd').textbox('getValue');
            
            load('正在登录，请稍候...');
            
            $.ajax({
                type:'post',
                dataType:'json',
                data:{'username': username, 'userpass': passwd},
                url:'MakeLogin.action',
                success:function(msg) {
                    disload();  //关闭等待框
                    
                    if(msg.msg == 'failed') {
                        $.messager.alert('系统提示','用户名或密码错误!','error');
                    }else {
                        $("#loginFrm").submit();
                    }
                }
            });
        }
    </script>
    
</head>

<body class="easyui-layout">

<!-- 顶部 -->
<div data-options="region:'north',border:false" style="height:90px;
    background-image:url('images/menu_01.png'); background-size:cover; text-align: center;">
    <span style="font:bold 42px '微软雅黑'; color:#ffffff; position: relative;top:15px">那板水库安全监测系统</span>
</div>

<!-- 中间内容区 -->
<div data-options="region:'center',border:false" style="padding:10px;background-color: #326487; padding:80px 0;">
    <form id="loginFrm" style="margin:0 auto; width:450px;height: 300px; 
        background: url('images/login_03_03.png') 0 0 no-repeat; padding: 5px 10px;" method="post" action="index.jsp">
        <div style="margin: 10px;">
            <div style="margin-top: 30px;">
                <input class="easyui-textbox" style="width:100%;height:50px;padding:12px" id="username" name="username"
                    data-options="prompt:'工号',iconCls:'icon-man',iconWidth:38">
            </div>
            <div style="margin-top: 20px;">
                <input class="easyui-textbox" type="password" style="width:100%;height:50px;padding:12px" id="passwd" name="passwd"
                    data-options="prompt:'密码',iconCls:'icon-lock',iconWidth:38">
            </div>
            <div style="margin-top: 30px; ">
                <a href="#" class="easyui-linkbutton" style="padding:5px 0px;width:100%;height:50px;" onclick="login();">
                    <span style="font-size:20px;">登录</span>
                </a>
            </div>
        </div>
        
    </form>
</div>

<!-- 底部 -->
<div data-options="region:'south',border:false" style="height:60px;text-align:center;
    background-image: url('images/menu_03.png'); background-size:cover;">
    <span style="font:normal 20px '微软雅黑'; color:#ffffff; position: relative;top:15px">Copyright 广西宏智科技有限公司</span>
</div>

</body>

</html>
