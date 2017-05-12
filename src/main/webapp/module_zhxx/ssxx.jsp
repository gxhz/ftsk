<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="../head1.jsp" %> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实时信息</title>

</head>

<body>
    <div style="position:relative;border:1px solid #b8d0d6;width:20px;height:100px;">
        <div id="mydiv" style="position:absolute;bottom:0;width:20px;height:10px;background:red;">
        </div>
    </div>
    <a href="#" class="easyui-linkbutton" onclick="makevalue();" data-options="toggle:true,group:'g1',size:'large'" style="width:120px;">测试</a>
</body>

<script type="text/javascript">

    function GetRandomNum(Min,Max) {
        var Range = Max - Min;
        var Rand = Math.random();
        return(Min + Math.round(Rand * Range));
    }   
    
    function makevalue() {
    	var num = GetRandomNum(1,100);
        document.getElementById('mydiv').style.height = num+'px';
    }
    
</script>

</html>