<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表统计</title>

<%@include file="../head1.jsp" %>

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
    	
    	doSearch();  //默认查询当天数据
	};
	
    //根据条件查询
    function doSearch(){
        var sdate,edate;
        
        sdate = $('#sdate').datebox('getValue');
        edate = $('#edate').datebox('getValue');
        if((sdate == '')||(edate == '')) {
        	$.messager.alert('系统提示','请输入开始时间或结束时间!','error');
        	return;
        }
        
        $('#tt').datagrid('load',{
			sdate: sdate,
			edate: edate
		});
        
        $('#myspan').html(sdate+'至'+edate+' 那板水库库水位时段报表统计');
    }
    
    //导出Excel
    function exportExcel(){
        var sdate,edate;
        
        sdate = $('#sdate').datebox('getValue');
        edate = $('#edate').datebox('getValue');
        if((sdate == '')||(edate == '')) {
        	$.messager.alert('系统提示','请输入开始时间或结束时间!','error');
        	return;
        }
        
		window.location.href="ExportKSWSDBBTJToExcel.action?Flag=1&sdate="+sdate+"&edate="+edate;
    }
    
</script>

</head>

<body class="easyui-layout">
    
    <div data-options="region:'center'" style="padding:5px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="padding:6px;height:50px;text-align:center;background:#fffff">
                <span id="myspan" style="font:normal 25px '微软雅黑'; "></span>
            </div>
        
            <div data-options="region:'center',border:false">
                <table id="tt" class="easyui-datagrid" pagination="false" toolbar="#tb" 
                     url="GetKSWSDBBTJData" rownumbers="false" singleSelect="true" pageSize="200" fit="true">
                    <thead>
                        <tr>
                          <th field="time" width="120">时间</th>
                          <th field="syksw" width="120">水库水位</th>
                          <th field="rkll" width="120">入库流量</th>
                          <th field="yhll" width="120">排洪流量</th>
                          <th field="xyksw" width="120">尾水位</th>
                          <th field="fdll" width="120">发电流量</th>
                          <th field="krl" width="120">水库库容</th>
                          <th field="jzzcl" width="120">机组总出力</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
    
    <div id="tb" style="padding:3px">
        <div style="border:0px solid #b8d0d6;padding:8px;width:100%">
            <table class="jgdstyle">
                <tr>
                    <td width="70">开始时间：</td>                         
                    <td width="180">
                        <input id="sdate" name="sdate" data-options="editable:false" class="easyui-datebox" required style="width:120px;height:28px">
                    </td>
                    <td width="80">结束时间：</td>                         
                    <td width="180">
                        <input id="edate" name="edate" data-options="editable:false" class="easyui-datebox" required style="width:120px;height:28px">
                    </td>
                    <td width="150" align="left"> <a href="#" class="easyui-linkbutton" style="width:120px;height:35px" onclick="doSearch()" >查询</a></td>
                    <td width="150" align="left"> <a href="#" class="easyui-linkbutton" style="width:120px;height:35px" onclick="exportExcel()" >导出Excel</a></td>
                </tr>
            </table>
        </div>
    </div>
	
</body>

</html>


