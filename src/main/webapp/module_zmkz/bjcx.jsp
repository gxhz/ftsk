<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../head1.jsp" %> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script language="javascript">
	var myChart;

    //初始化界面数据
    window.onload = function () { 
    	$('#sdate').datebox('setValue', formatterDate(new Date()));
    	$('#edate').datebox('setValue', formatterDate(new Date()));
    	
    	//初始化echarts实例
        var chart=document.getElementById('chart1');
        myChart = echarts.init(chart);
    	
    	doSearch();
    	
    	$('#tt').datagrid({onLoadSuccess : function(data){
    		var av=[0, 0, 0];
    		
    		av[0] = data.sxsum;
    		av[1] = data.xxsum;
    		
    		// 指定图表的配置项和数据
	        myChart.setOption({
	        	tooltip : {
	                trigger: 'item',
	                formatter: "{a} <br/>{b} : {c} ({d}%)"
	            },
	            toolbox: {
	                feature: {
	                    dataView: {show: true, readOnly: false},
	                    restore: {show: true},
	                    saveAsImage: {show: true}
	                }
	            },
	            legend: {
	            	orient: 'vertical',
	                left: 'left',
	                data: ["上限【次数】","下限【次数】"]
	            },
	            series: [
                        {
                            type: 'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data: [ {value:av[0],name:'上限【次数】'},{value:av[1],name:'下限【次数】'} ],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
	                     ]
	        }, true);
    		
    	}});
    };

    function doSearch(){
        $('#tt').datagrid('load',{
        	zmindex: $('#zmindex').combobox('getValue'),
        	zmflag: $('#zmflag').combobox('getValue'),
            sdate: $('#sdate').datebox('getValue'),
            edate: $('#edate').datebox('getValue')
        });
    }
    
    function clearSearch(){
    	$('#zmindex').combobox('setValue', '1,2');
    	$('#zmflag').combobox('setValue', '1,2');
    	$('#sdate').datebox('setValue', '');
    	$('#edate').datebox('setValue', '');
    }
    
  	//导出Excel
    function exportExcel() {
    	var zmindex=$('#zmindex').combobox('getValue');
    	var zmflag=$('#zmflag').combobox('getValue');
        var sdate=$('#sdate').datebox('getValue');
        var edate=$('#edate').datebox('getValue');
		
    	window.location.href="ExportZMAlarmToExcel.action?zmindex="+zmindex+"&zmflag="+zmflag+"&sdate="+sdate+"&edate="+edate;
    }  
    
</script>

</head>
<body class="easyui-layout">

    <div data-options="region:'north',border:false" style="padding:3px;height:40px;text-align:center;background:#fffff">
        <span id="myspan" style="font:normal 25px '微软雅黑'; ">闸门监控报警查询</span>
    </div>

    <div data-options="region:'center',border:false" style="padding:5px;">
        <table id="tt" class="easyui-datagrid" pagination="true" toolbar="#tb" 
             url="GetZMAlarmData" rownumbers="true" singleSelect="true" pageSize="20" fitColumns="true" fit="true">
            <thead>
                <tr>
                    <th data-options="field:'zmmc',width:120">闸门名称</th>
                    <th data-options="field:'zmmc1',width:120">闸门名称1</th>
                    <th data-options="field:'zmzt',width:100">报警类型</th>
                    <th data-options="field:'kssj',width:100">开始时间</th>
                    <th data-options="field:'jssj',width:100">结束时间</th>
                    <th data-options="field:'sz',width:100">时长</th>
                    <th data-options="field:'szh',width:100,hidden:true">小时</th>
                    <th data-options="field:'bjzt',width:100">报警状态</th>
                </tr>
            </thead>
        </table>
    </div>
    
    <div data-options="region:'south',border:false" style="padding:25px;height:400px;">
        <div id="chart1" style="width: 100%;height:100%;"></div>
    </div>
    
    <div id="tb" style="padding:3px">
        <div style="border:0px solid #b8d0d6;padding:8px;width:width:100%">
            <table class="jgdstyle">
                <tr>
                    <td width=60 align="right">闸门选择：<td>
                    <td width=110>
                        <select id="zmindex" name="zmindex" class="easyui-combobox" style="width:180px;"
                            data-options="
                                        panelHeight:'auto',
                                        editable:false
                                ">
                            <option value="1,2">全部</option>
                            <option value="1">1#闸门</option>
                            <option value="2">2#闸门</option>
                        </select>
                    <td>
                    <td width=70 align="right">闸门选择2：<td>
                    <td width=110>
                        <select id="zmflag" name="zmflag" class="easyui-combobox" style="width:180px;"
                            data-options="
                                        panelHeight:'auto',
                                        editable:false
                                ">
                            <option value="1,2">全部</option>
                            <option value="1">工作闸门</option>
                            <option value="2">检修闸门</option>
                        </select>
                    <td>
                    <td width=60 align="right">起始时间：<td>
                    <td width=110><input id="sdate" name="sdate" data-options="editable:false" class="easyui-datebox" required style="width:180px;height:28px"><td>
                    <td width=60 align="right">结束时间：<td>
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