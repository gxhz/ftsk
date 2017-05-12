<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预报调度结果表</title>

<%@include file="../head1.jsp" %>

<script language="javascript">
    
    //初始化界面数据
    window.onload = function () { 
    	$('#sdate').datebox('setValue', formatterDate(new Date()));
    	$('#edate').datebox('setValue', formatterDate(new Date()));
    	
    	/*
    	var sdate,edate;
    	sdate = getCookie("HSYB_SDate");
    	edate = getCookie("HSYB_EDate");
    	if(sdate == null || edate == null || sdate.length <= 0 || edate.length <= 0) {
    		$('#sdate').datebox('setValue', formatterDate(new Date()));
        	$('#edate').datebox('setValue', formatterDate(new Date()));
   		} else {
    		$('#sdate').datebox('setValue', sdate);
        	$('#edate').datebox('setValue', edate);
   		}
    	*/
    	
   	    doSearch();  //默认查询当天数据
    };
    
    //查询
    function doSearch(){
        var sdate,edate;
        var array=[];
        var columns=[];
        sdate = $('#sdate').datebox('getValue');
        edate = $('#edate').datebox('getValue');
        if((sdate == '')||(edate == '')) {
        	$.messager.alert('系统提示','请输入开始时间或结束时间!','error');
        	return;
        }
        if(sdate  > edate) {
        	$.messager.alert('系统提示','开始时间必须在结束时间之前!','error');
        	return;
        }
        
        //setCookie("HSYB_SDate", sdate);
        //setCookie("HSYB_EDate", edate);
        
        //动态设置表格绑定信息
        array.push({field:'time',title:'时间',width:'130'});
        
        array.push({field:'yb_jyl',title:'预报降雨量(mm)',width:'130'});
        array.push({field:'yb_rkll',title:'预报入库流量(m³/s)',width:'130'});
        array.push({field:'yb_ksw',title:'预报库水位(m)',width:'130'});
        array.push({field:'yb_krl',title:'预报库容(百万m³)',width:'130'});
        array.push({field:'yb_ckll',title:'预报出库流量(m³/s)',width:'130'});
        
        array.push({field:'sc_jyl',title:'实测降雨量(mm)',width:'130'});
        array.push({field:'sc_rkll',title:'实测入库流量(m³/s)',width:'130'});
        array.push({field:'sc_ksw',title:'实测库水位(m)',width:'130'});
        array.push({field:'sc_krl',title:'实测库容(百万m³)',width:'130'});
        array.push({field:'sc_ckll',title:'实测出库流量(m³/s)',width:'130'});
        
        columns.push(array);
        
        //重新设置表格列
        $('#tt').datagrid({
        	url:"GetHSYBJGBData",
            columns:columns,
            queryParams:{SDate: sdate,EDate: edate}
        });
    }
    
</script>

</head>

<body class="easyui-layout">
    <div data-options="region:'center'" style="padding:5px;">
    	<div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="padding:6px;height:125px;text-align:center;background:#fffff">
                <span id="myspan" style="font:normal 25px '微软雅黑'; ">洪水预报调度结果表</span>
            	
            	<div class="easyui-panel" style="padding:3px;">
            	<div style="border:0px solid #b8d0d6;padding:1px;width:100%">
	            <table border="0" cellpadding="0" cellspacing="0" style="padding:5px;">
	    			<tr>
	    				<td width="70">开始时间：</td>
	    				<td width="180">
	                        <input id="sdate" name="sdate" data-options="editable:false" class="easyui-datebox" required style="width:120px;height:28px">
	                    </td>
	    				<td width="80">结束时间：</td>
	    				<td width="180">
	                        <input id="edate" name="edate" data-options="editable:false" class="easyui-datebox" required style="width:120px;height:28px">
	    				</td>
	    				<td width="150" align="left" style="padding-top:5px;"> 
	                        <a href="#" class="easyui-linkbutton" style="width:120px;height:35px"onclick="doSearch();">查询</a>
	                    </td>
	    		    </tr>
	    		</table>
	    		</div>
	    		</div>
	    	</div>
	    	
	    	<div data-options="region:'center',border:false" style="padding:5px; width: 100%;" fit="true">
                <table id="tt" pagination="false" 
                     rownumbers="false" singleSelect="true" pageSize="200" fit="true">
                    <thead>
                        <tr>
                          <th field="time" width="150">时间</th>
                          <th field="pjyl" width="150">流域平均雨量P(mm)</th>
                          <th field="ybjyl" width="150">预报时段净雨量R(mm)</th>
                          <th field="rk" width="150">入库流量(m3/s)</th>
                          <th field="ybrk" width="150">预报入库流量(m3/s)</th>
                          <th field="sw" width="150">库水位(m)</th>
                          <th field="ybsw" width="150">预报库水位(m)</th>
                          <th field="kr" width="150">库容(百万m3)</th>
                          <th field="ybkr" width="150">预报库容(百万m3)</th>
                          <th field="ck" width="150">出库流量(m3/s)</th>
                          <th field="ybck" width="150">预报出库流量(m3/s)</th>
                        </tr>
                    </thead>
                </table>
            </div>
       </div>
    </div>
    
</body>

</html>


