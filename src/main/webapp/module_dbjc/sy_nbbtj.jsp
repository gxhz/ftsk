<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据查询</title>

<%@include file="../head1.jsp" %>

<script language="javascript">
    //初始化界面数据
    window.onload = function () {
    	var date = new Date();
    	var year = date.getFullYear();
    	
    	$('#year').combobox('setValue', year);
    	$('#dtype').combobox('setValue', 'db_his_sysw');
    	
    	$('#dev').combobox({ 
            onLoadSuccess: function () { //加载完成后,设置选中第一项
                var val = $(this).combobox("getData");
                for (var item in val[0]) {
                    if (item == "name") {
                        $(this).combobox("select", val[0][item]);
                    }
                }
                
                doSearch();  //默认查询当天数据
            }
        }); 
    	
    };

	//查询
	function doSearch(){
		var year;
		var sdate,edate;
		var name,dtype;
		
		year = $('#year').combobox('getValue');
		name = $('#dev').combobox('getValue');
		dtype = $('#dtype').combobox('getValue');
		
		if(year == '') {
			$.messager.alert('系统提示','请选择年份!','error');
			return;
		}
		if(name == '') {
			$.messager.alert('系统提示','请选择传感器!','error');
			return;
		}
		if(dtype == '') {
			$.messager.alert('系统提示','请选择数据类型!','error');
			return;
		}
		
		sdate = year+'-01-01';
		edate = year+'-12-31';
		
		$('#tt').datagrid('load',{
			names: name,
			SDate: sdate,
			EDate: edate,
			dtype: dtype
		});
		
		$('#myspan').html($('#year').combobox('getText')+' 那板水库 '+$('#dev').combobox('getText')+' 渗压年报表统计');
	}
	
	//导出Excel
    function exportExcel() {
    	var year;
		var sdate,edate;
		var name,dtype;
		
		year = $('#year').combobox('getValue');
		name = $('#dev').combobox('getValue');
		dtype = $('#dtype').combobox('getValue');
		
		if(year == '') {
			$.messager.alert('系统提示','请选择年份!','error');
			return;
		}
		if(name == '') {
			$.messager.alert('系统提示','请选择传感器!','error');
			return;
		}
		if(dtype == '') {
			$.messager.alert('系统提示','请选择数据类型!','error');
			return;
		}
		
		sdate = year+'-01-01';
		edate = year+'-12-31';
		
    	window.location.href="ExportNBBTJToExcel.action?CGQType=1&Flag=4&names="+name+"&SDate="+sdate+"&EDate="+edate+"&dtype="+dtype;
    }  
	
</script>

</head>
<body class="easyui-layout">
	<div data-options="region:'center'" style="padding:5px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="padding:6px;height:50px;text-align:center;background:#fffff">
                <span id="myspan" style="font:normal 25px '微软雅黑'; ">那板水库年报表统计</span>
            </div>
        
            <div data-options="region:'center',border:false">
                <table id="tt" class="easyui-datagrid" pagination="false" toolbar="#tb" fitColumns="true"
                     url="GetNBBTJData?CGQType=1&Flag=4" rownumbers="false" singleSelect="true" pageSize="200" fit="true"
                     data-options="
                        onLoadSuccess: function (data){
                            var merges = [{
                                field:'sysw1',
                                index: 36,
                                colspan: 2
                            },
                            {
                                field:'sysw3',
                                index: 36,
                                colspan: 2
                            },
                            {
                                field:'sysw5',
                                index: 36,
                                colspan: 2
                            },
                            {
                                field:'sysw7',
                                index: 36,
                                colspan: 2
                            },
                            {
                                field:'sysw9',
                                index: 36,
                                colspan: 2
                            },
                            {
                                field:'sysw11',
                                index: 36,
                                colspan: 2
                            }];    
                	    	for (var i = 0; i < merges.length; i++)
                                $('#tt').datagrid('mergeCells', {
                                    index: merges[i].index,
                                    field: merges[i].field,
                                    colspan: merges[i].colspan
                                });
                            for (var i = 0; i < merges.length; i++)
                                $('#tt').datagrid('mergeCells', {
                                    index: merges[i].index+1,
                                    field: merges[i].field,
                                    colspan: merges[i].colspan
                                });
                                
                            merges = [{
                                field:'time',
                                index: 36,
                                rowspan: 2
                            }];
                            for (var i = 0; i < merges.length; i++)
                                $('#tt').datagrid('mergeCells', {
                                    index: merges[i].index,
                                    field: merges[i].field,
                                    rowspan: merges[i].rowspan
                                });
                                
                        }
                    ">
                    <thead>
                        <tr>
                          <th field="time" width="150">日期</th>
                          <th field="sysw1" width="120">一月</th>
                          <th field="sysw2" width="120">二月</th>
                          <th field="sysw3" width="120">三月</th>
                          <th field="sysw4" width="120">四月</th>
                          <th field="sysw5" width="120">五月</th>
                          <th field="sysw6" width="120">六月</th>
                          <th field="sysw7" width="120">七月</th>
                          <th field="sysw8" width="120">八月</th>
                          <th field="sysw9" width="120">九月</th>
                          <th field="sysw10" width="120">十月</th>
                          <th field="sysw11" width="120">十一月</th>
                          <th field="sysw12" width="120">十二月</th>
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
                    <td width="70">数据类型：</td>                         
                    <td width="180">
                        <input id="dtype" class="easyui-combobox" style="width:120px;height:28px"
                            data-options="
                                    url: '../jsonfile/dbdatatype.json',
                                    method:'get',
                                    valueField:'value',
                                    textField:'text',
                                    panelHeight:'auto',
                                    editable:false
                            ">
                    </td>
                    <td width="70">年份选择：</td>                         
                    <td width="180">
                        <input id="year" class="easyui-combobox" style="width:120px;height:28px"
                            data-options="
                                    url: '../jsonfile/year.json',
                                    method:'get',
                                    valueField:'value',
                                    textField:'text',
                                    editable:false
                            ">
                    </td>
                    <td width="80">传感器选择：</td>                         
                    <td width="180">
                        <input id="dev" class="easyui-combobox" style="width:120px;height:28px"
                            data-options="
                                    url: 'GetCGQList?type=1',
                                    method:'get',
                                    valueField:'name',
                                    textField:'name',
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

