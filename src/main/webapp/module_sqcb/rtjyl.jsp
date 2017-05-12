<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="../head1.jsp" %> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body class="easyui-layout">

    <div data-options="region:'center',border:false" style="padding:5px;">
        <table id="tt1" class="easyui-datagrid" style="width: 100%; height: auto" pagination="false" 
            url="GetJYLAction" rownumbers="false" singleSelect="true" fitColumns="true" fit="true"
            data-options="
                rowStyler: function(index,row){
                    if (index % 2 == 1){
                        return 'background-color:#EAEAEA;';
                    }
                }
            ">
            <thead>
                <tr>
                    <th field="time" width="10px" data-options="align:'center'">日期</th>
                    <th field="bs" width="10px" data-options="align:'center'">坝首</th>
                    <th field="nj" width="10px" data-options="align:'center'">那禁</th>
                    <th field="nd" width="10px" data-options="align:'center'">那荡</th>
                    <th field="gl" width="10px" data-options="align:'center'">枯蒌</th>
                    <th field="hm" width="10px" data-options="align:'center'">汪门</th>
                    <th field="bl" width="10px" data-options="align:'center'">婆利</th>
                    <th field="ph" width="10px" data-options="align:'center'">平何</th>
                    <th field="bx" width="10px" data-options="align:'center'">百姓</th>
                </tr>
            </thead>
        </table>
    </div>

</body>
</html>