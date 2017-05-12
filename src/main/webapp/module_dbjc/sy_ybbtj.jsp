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
    	
    	$('#dtype').combobox('setValue', 'db_his_sysw');
    	
    	$('#nametable').datagrid({onLoadSuccess : function(data){
    	    $('#nametable').datagrid('selectAll');
    	    
    	    doSearch();  //默认查询当天数据
    	}});
    };
    
    //查询
    function doSearch(){
        var syjname='';
        var namearray=[];
        var array=[];
        var columns=[];
        var year,month;
        var sdate,edate,dtype;
        
        year = $('#year').combobox('getValue');
        month = $('#month').combobox('getValue');
        dtype = $('#dtype').combobox('getValue');
        if((year == '')||(month == '')) {
        	$.messager.alert('系统提示','请选择年份或月份!','error');
        	return;
        }
        if(dtype == '') {
			$.messager.alert('系统提示','请选择数据类型!','error');
			return;
		}
        
        var firstday=new Date(year,month,0);
        sdate = year+'-'+month+'-01';
        edate = year+'-'+month+'-'+firstday.getDate();
        
        //获取选中的渗压计名称
		var selrows = $('#nametable').datagrid('getSelections');
		for(var i=0; i<selrows.length; i++){
			var str=selrows[i].name;
			namearray.push(str);
			
			if (syjname != '') syjname += ',';
			syjname += str;
		}
        
		if(syjname == '') {
        	$.messager.alert('系统提示','请选择传感器!','error');
        	return;
        }
		
		//动态设置表格绑定信息
        array.push({field:'time',title:'时间',width:'120'});
        array.push({field:'syksw',title:'上游水位',width:'120'});
        array.push({field:'xyksw',title:'下游水位',width:'120'});
        for(var i=0;i<namearray.length;i++) {
        	array.push({field:'sysw'+(i+1),title:namearray[i],width:'120'});
        }
        columns.push(array);
        
        
        //重新设置表格列
        $('#tt').datagrid({
            columns:columns,
            queryParams:{names: syjname,SDate: sdate,EDate: edate,dtype: dtype}
        });
        
        $('#myspan').html(year+'年'+month+'月 那板水库渗压月报表统计');
    }
    
    //导出Excel
    function exportExcel(){
    	var syjname='';
        var namearray=[];
        var year,month;
        var sdate,edate,dtype;
        
        year = $('#year').combobox('getValue');
        month = $('#month').combobox('getValue');
        dtype = $('#dtype').combobox('getValue');
        if((year == '')||(month == '')) {
        	$.messager.alert('系统提示','请选择年份或月份!','error');
        	return;
        }
        if(dtype == '') {
			$.messager.alert('系统提示','请选择数据类型!','error');
			return;
		}
        
        var firstday=new Date(year,month,0);
        sdate = year+'-'+month+'-01';
        edate = year+'-'+month+'-'+firstday.getDate();
        
        //获取选中的渗压计名称
		var selrows = $('#nametable').datagrid('getSelections');
		for(var i=0; i<selrows.length; i++){
			var str=selrows[i].name;
			namearray.push(str);
			
			if (syjname != '') syjname += ',';
			syjname += str;
		}
        
		if(syjname == '') {
        	$.messager.alert('系统提示','请选择传感器!','error');
        	return;
        }
		
		window.location.href="ExportSDBBTJToExcel.action?CGQType=1&Flag=3&names="+syjname+"&SDate="+sdate+"&EDate="+edate+"&dtype="+dtype;
    }
    
</script>

</head>

<body class="easyui-layout">
    <div data-options="region:'west'" style="width:155px;padding:5px;">
        <div class="easyui-layout" style="width:145px;" fit="true">
            <div data-options="region:'center'" style="padding:0px;border:0px;">
    			<table id="nametable" class="easyui-datagrid" url="GetCGQList?type=1" fit="true" fitColumns="true">
    			<thead>
    				<tr>
    				  <th field="ck" checkbox="true"></th>
    				  <th field="name" width="10">名称</th>
    				</tr>
    			</thead>
    			</table>
    		</div>
            <div data-options="region:'south'" style="padding:5px;overflow:hidden;">
                <table border="0" cellpadding="0" cellspacing="0" style="padding:5px;">
        			<tr>
                        <td align="left" style="padding-top:5px;">数据类型：</td>
                    </tr>
                    <tr>
                        <td align="left" style="padding-top:5px;">
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
                    </tr>
                    <tr>
        				<td align="left" style="padding-top:5px;">年份选择：</td>
                    </tr>
                    <tr>
        				<td align="left" style="padding-top:5px;">
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
                    </tr>
                    <tr>
        				<td align="left" style="padding-top:5px;">月份选择：</td>
                    </tr>
                    <tr>
        				<td align="left" style="padding-top:5px;">
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
                    </tr>
                    <tr>
        				<td align="left" style="padding-top:5px;"> 
                            <a href="#" class="easyui-linkbutton" style="width:120px;height:35px"onclick="doSearch();">查询</a>
                        </td>
        		    </tr>
                    <tr>
                        <td style="padding-top:5px;" align="left"> 
                            <a href="#" class="easyui-linkbutton" style="width:120px;height:35px"onclick="exportExcel();">导出Excel</a>
                        </td>
        			</tr>
        		</table>
            </div>
        </div>
    </div>
    
    <div data-options="region:'center'" style="padding:5px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="padding:6px;height:50px;text-align:center;background:#fffff">
                <span id="myspan" style="font:normal 25px '微软雅黑'; ">那板水库渗压时段报表统计</span>
            </div>
        
            <div data-options="region:'center',border:false">
                <table id="tt" class="easyui-datagrid" pagination="false" 
                     url="GetSDBBTJData?CGQType=1&Flag=3" rownumbers="false" singleSelect="true" pageSize="200" fit="true">
                    <thead>
                        <tr>
                          <th field="time" width="120">时间</th>
                          <th field="a" width="120">上游水位</th>
                          <th field="b" width="120">下游水位</th>
                          <th field="c" width="120">渗压水位</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
	
</body>

</html>


