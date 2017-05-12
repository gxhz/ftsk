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
            url="GetKSWAction" rownumbers="false" singleSelect="true" fitColumns="true" fit="true"
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
                    <th field="ksw" width="10px" data-options="align:'center'">水库水位</th>
                    <th field="rkll" width="10px" data-options="align:'center'">入库流量</th>
                    <th field="phll" width="10px" data-options="align:'center'">排洪流量</th>
                    <th field="wsw" width="10px" data-options="align:'center'">尾水位</th>
                    <th field="fdll" width="10px" data-options="align:'center'">发电流量</th>
                    <th field="skkr" width="10px" data-options="align:'center'">水库库容</th>
                    <th field="jzzcl" width="10px" data-options="align:'center'">(机组总出力)</th>
                </tr>
            </thead>
        </table>
    </div>

</body>
</html>