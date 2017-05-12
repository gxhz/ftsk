<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表统计</title>

<%@include file="../head1.jsp" %>

<script language="javascript">
    //初始化界面数据
    window.onload = function () {
    	var date = new Date();
    	var year = date.getFullYear();
    	var month = date.getMonth()+1;
    	
    	$('#year').combobox('setValue', year);
    	if(month > 9) {
    		$('#month').combobox('setValue', month);
    	} else {
    		$('#month').combobox('setValue', '0'+month);
    	}
    	
    	doSearch();  //默认查询当天数据
    };
	
    //根据条件查询
    function doSearch(){
        var sdate,edate;
        
        year = $('#year').combobox('getValue');
        month = $('#month').combobox('getValue');
        if((year == '')||(month == '')) {
        	$.messager.alert('系统提示','请选择年份或月份!','error');
        	return;
        }
        
        var firstday=new Date(year,month,0);
        sdate = year+'-'+month+'-01';
        edate = year+'-'+month+'-'+firstday.getDate();
        
        $('#tt').datagrid('load',{
			sdate: sdate,
			edate: edate
		});
        
        $('#myspan').html(year+'年'+month+'月 那板水库库水位月报表统计');
    }
    
    //导出Excel
    function exportExcel(){
		var sdate,edate;
        
        year = $('#year').combobox('getValue');
        month = $('#month').combobox('getValue');
        if((year == '')||(month == '')) {
        	$.messager.alert('系统提示','请选择年份或月份!','error');
        	return;
        }
        
        var firstday=new Date(year,month,0);
        sdate = year+'-'+month+'-01';
        edate = year+'-'+month+'-'+firstday.getDate();
        
		window.location.href="ExportKSWSDBBTJToExcel.action?Flag=3&sdate="+sdate+"&edate="+edate;
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
                    <td width="70">年份选择：</td>                         
                    <td width="180">
                        <input id="year" class="easyui-combobox" style="width:120px;height:28px"
                                data-options="
                                        url: '../jsonfile/year.json',
                                        method:'get',
                                        valueField:'value',
                                        textField:'text',
                                        panelHeight:'auto',
                                        editable:false
                                ">
                    </td>
                    <td width="80">月份选择：</td>                         
                    <td width="180">
                        <input id="month" class="easyui-combobox" style="width:120px;height:28px"
                                data-options="
                                        url: '../jsonfile/month.json',
                                        method:'get',
                                        valueField:'value',
                                        textField:'text',
                                        panelHeight:'auto',
                                        editable:false
                                ">
                    </td>
                    <td width="150" align="left"> <a href="#" class="easyui-linkbutton" style="width:120px;height:35px" onclick="doSearch()" >查询</a></td>
                    <td width="150" align="left"> <a href="#" class="easyui-linkbutton" style="width:120px;height:35px" onclick="exportExcel()" >导出Excel</a></td>
                </tr>
            </table>
        </div>
    </div>
	
</body>

</html>


