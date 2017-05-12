<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="../head1.jsp" %> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script language="javascript">
    
    //得到当前日期
    formatterDate = function(date) {
        var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
        var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
        + (date.getMonth() + 1);
        return date.getFullYear() + '-' + month + '-' + day;
    };
    
    //初始化界面数据
    window.onload = function () {
    	$('#sdate').datebox('setValue', formatterDate(new Date()));
    	$('#edate').datebox('setValue', formatterDate(new Date()));
    	
    	//doSearch();  //默认查询当天数据
    };

    function doSearch(){
    	$('#tt').datagrid('load',{
    		sdate: $('#sdate').datebox('getValue'),
    		edate: $('#edate').datebox('getValue')
		});
    }
    
    function clearSearch(){
    	$('#sdate').datebox('setValue', formatterDate(new Date()));
    	$('#edate').datebox('setValue', formatterDate(new Date()));
    }
    
  	//导出Excel
    function exportExcel() {
        var sdate=$('#sdate').datebox('getValue');
        var edate=$('#edate').datebox('getValue');
		
    	window.location.href="ExportAlarmToExcel.action?bjlb=2&sdate="+sdate+"&edate="+edate;
    }
    
</script>

</head>
<body class="easyui-layout">

    <div data-options="region:'north',border:false" style="padding:3px;height:40px;text-align:center;background:#fffff">
        <span id="myspan" style="font:normal 25px '微软雅黑'; ">水雨情报警查询</span>
    </div>

    <div data-options="region:'center',border:false" style="padding:5px;">
        <table id="tt" class="easyui-datagrid" pagination="true" toolbar="#tb"
             url='GetAlarmMsg?bjlb=2' rownumbers="true" singleSelect="true" pageSize="20" fitColumns="true" fit="true">
            <thead>
                <tr>
                    <th data-options="field:'bjsj',width:120">报警时间</th>
                    <th data-options="field:'bjxx',width:400">报警信息</th>
                    <th data-options="field:'bjzt',width:100">报警状态</th>
                </tr>
            </thead>
        </table>
    </div>
    
    <div id="tb" style="padding:3px">
        <div style="border:0px solid #b8d0d6;padding:8px;width:width:100%">
            <table class="jgdstyle">
                <tr>
                    <td width=60>起始时间：<td>
                    <td width=10><input id="sdate" name="sdate" data-options="editable:false" class="easyui-datebox" required style="width:180px;height:28px"><td>
                    <td width=60>结束时间：<td>
                    <td width=110><input id="edate" name="edate" data-options="editable:false" class="easyui-datebox" required style="width:180px;height:28px"><td>
                    <td width="100" align="right"> <a href="#" class="easyui-linkbutton" style="width:80px;height:35px"onclick="doSearch();" >查询</a></td>
                    <td width="100" align="left"> <a href="#" class="easyui-linkbutton" style="width:80px;height:35px"onclick="clearSearch()" >重置</a></td>
                    <td width="150" align="left"> <a href="#" class="easyui-linkbutton" style="width:100px;height:35px" onclick="exportExcel()" >导出Excel</a></td>
                </tr>
            </table>
        </div>
        
    </div>

</body>
</html>