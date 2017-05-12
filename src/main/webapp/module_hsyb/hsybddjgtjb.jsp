<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预报调度结果统计表</title>

<%@include file="../head1.jsp" %>

<style>
 
 #div1 .datagrid-header{ position: absolute; visibility: hidden; }
 
</style>

<script language="javascript">
    
    //初始化界面数据
    window.onload = function () { 
    	$('#sdate').datebox('setValue', formatterDate(new Date()));
    	$('#edate').datebox('setValue', formatterDate(new Date()));
    	
    	/*
    	var sdate,edate;
    	sdate = getCookie("HSYB_SDate");
    	edate = getCookie("HSYB_EDate");
    	if(sdate == null || edate == null
    			|| sdate.length <= 0 || edate.length <= 0)
   		{
    		$('#sdate').datebox('setValue', formatterDate(new Date()));
        	$('#edate').datebox('setValue', formatterDate(new Date()));
   		}
    	else
   		{
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
        
        //重新设置表格列
        $('#tt').datagrid({
        	url:"GetHSYBJGTJBData0",
            queryParams:{SDate: sdate,EDate: edate}
        });
      	//重新设置表格列
        $('#tt1').datagrid({
        	url:"GetHSYBJGTJBData1",
            queryParams:{SDate: sdate,EDate: edate}
        });
      	//重新设置表格列
        $('#tt2').datagrid({
        	url:"GetHSYBJGTJBData2",
            queryParams:{SDate: sdate,EDate: edate}
        });
    }
    
</script>

</head>

<body class="easyui-layout">
    <div data-options="region:'center'" style="padding:5px;">
    	<div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="padding:6px;height:125px;text-align:center;background:#fffff">
                <span id="myspan" style="font:normal 25px '微软雅黑'; ">洪水预报调度结果统计表</span>
            	
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
                <div style="border:1px solid #b8d0d6;padding:8px;width:width:100%;text-align:center;background:#FAFAD2;">
				    预报方案说明
				</div>
				<div id="div1" style="width:100%;height:102px">
		         <table id="tt" pagination="false" style="width: 100%; height: auto"
		               rownumbers="false" singleSelect="true" fitColumns="true">
                       <thead>
                            <tr>
                                <th field="key0" width="10px"></th>
                                <th field="value0" width="10px"></th>
                                <th field="key1" width="10px"></th>
                                <th field="value1" width="10px"></th>
                                <th field="key2" width="10px"></th>
                                <th field="value2" width="10px"></th>
                            </tr>
                        </thead>
		         </table>
		         </div>
		         <div style="border:1px solid #b8d0d6;padding:8px;width:width:100%;text-align:center;background:#FAFAD2;">
				    当前状态
				</div>
				<div id="div1" style="width:100%;height:102px">
		         <table id="tt1" pagination="false" style="width: 100%; height: auto"
		               rownumbers="false" singleSelect="true" fitColumns="true" >
                       <thead>
                            <tr>
                                <th field="key0" width="10px"></th>
                                <th field="value0" width="10px"></th>
                                <th field="key1" width="10px"></th>
                                <th field="value1" width="10px"></th>
                                <th field="key2" width="10px"></th>
                                <th field="value2" width="10px"></th>
                            </tr>
                        </thead>
		         </table>
		         </div>
		         <div style="border:1px solid #b8d0d6;padding:8px;width:width:100%;text-align:center;background:#FAFAD2;">
				    预报结果(未来)
				 </div>
				 <div id="div1" style="width:100%;height:122px">
				 <table id="tt2" pagination="false" style="width: 100%; height: auto"
		             rownumbers="false" singleSelect="true" fitColumns="true">
                     <thead>
                            <tr>
                                <th field="key0" width="10px"></th>
                                <th field="value0" width="10px"></th>
                                <th field="key1" width="10px"></th>
                                <th field="value1" width="10px"></th>
                                <th field="key2" width="10px"></th>
                                <th field="value2" width="10px"></th>
                            </tr>
                        </thead>
		         </table>
		         </div>
            </div>
       </div>
    </div>
    
	
</body>

</html>


