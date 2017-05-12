<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>时段过程线</title>

<%@include file="../head1.jsp" %>

<script language="javascript">
	var myChart;
    
    //初始化界面数据
    window.onload = function () { 
    	$('#sdate').datebox('setValue', formatterDate(new Date()));
    	$('#edate').datebox('setValue', formatterDate(new Date()));
    	
    	//初始化echarts实例
        var chart=document.getElementById('chart1');
        myChart = echarts.init(chart);
    	
    	$('#nametable').datagrid({onLoadSuccess : function(data){
    	    $('#nametable').datagrid('selectAll');
    	    //$('#nametable').datagrid('selectRow', 0);
    	    
    	    doSearch();  //默认查询当天数据
    	}});
    };
    
    //查询
    function doSearch(){
        var syjcode='';
        var sdate,edate;
        var legenddata=[];
        
        sdate = $('#sdate').datebox('getValue');
        edate = $('#edate').datebox('getValue');
        if((sdate == '')||(edate == '')) {
        	$.messager.alert('系统提示','请输入开始时间或结束时间!','error');
        	return;
        }
        
        //获取选中的雨量站编号
		var selrows = $('#nametable').datagrid('getSelections');
		for(var i=0; i<selrows.length; i++){
			var str=selrows[i].value;
			if (syjcode != '') syjcode += ',';
			syjcode += str;
		}
        
		if(syjcode == '') {
        	$.messager.alert('系统提示','请选择雨量站!','error');
        	return;
        }
		
		load('正在获取数据，请稍候...');
		
		$.ajax({
            type:'post',
            dataType:'json',
            data:{code: syjcode,sdate:sdate,edate:edate},
            url:'GetJYLSDGCXData.action',
            success:function(data) {
            	disload();  //关闭等待框
            	
            	var strtime=[];
            	var seriesStr=[];
            	
            	for(var i=0; i<selrows.length; i++){
					var str=selrows[i].text;
      				
      				var seriesTitle={};
      				seriesTitle.name = str;
      				seriesTitle.type = 'bar';
      				seriesTitle.smooth = true;
      				
      				var hoffset=[];
      				
      				legenddata[i] = str;
      				
        			for(var j=0;j<data.length;j++) {
        				if(i == 0) strtime[j] = data[j]['time'];
        				hoffset[j] = Number(data[j]['rjyl'+(i+1)]);
                	}
        			
        			seriesTitle.data = hoffset;
      				seriesStr[i] = seriesTitle;
        		}
            	
            	// 指定图表的配置项和数据
      	        myChart.setOption({
      	            tooltip: {
      	                trigger: 'axis',
      	                axisPointer: {
      	                    type: 'cross'
      	                }
      	            },
      	            grid: {
      	                right: '10%'
      	            },
      	            toolbox: {
      	                feature: {
      	                    dataView: {show: true, readOnly: false},
      	                    restore: {show: true},
      	                    saveAsImage: {show: true}
      	                }
      	            },
      	            legend: {
      	                data: legenddata
      	            },
      	            xAxis: [
      	                {
      	                    type: 'category',
      	                    axisTick: {
      	                        alignWithLabel: true
      	                    },
      	                    data: strtime
      	                }
      	            ],
      	            yAxis: [
      	                {
      	                	type: 'value',
      	                    name: '降雨量（mm）',
      	                    position: 'left',
      	                    axisLabel: {
      	                        formatter: '{value} mm'
      	                    }
      	                }
      	            ],
      	            series: seriesStr
      	        }, true);
            	  
        	}, 
			error: function(e) { 
				disload();  //关闭等待框
			} 
        });
		
        $('#myspan').html(sdate+'至'+edate+' 那板水库降雨量时段过程线');
    }
    
</script>

</head>

<body class="easyui-layout">
    <div data-options="region:'west'" style="width:155px;padding:5px;">
        <div class="easyui-layout" style="width:145px;" fit="true">
            <div data-options="region:'center'" style="padding:0px;border:0px;">
    			<table id="nametable" class="easyui-datagrid" url="../jsonfile/jylname.json" fit="true" fitColumns="true">
    			<thead>
    				<tr>
    				  <th field="ck" checkbox="true"></th>
    				  <th field="text" width="10">名称</th>
                      <th field="value" data-options="hidden: true">名称</th>
    				</tr>
    			</thead>
    			</table>
    		</div>
            <div data-options="region:'south'" style="padding:5px;overflow:hidden;">
                <table border="0" cellpadding="0" cellspacing="0" style="padding:5px;">
        			<tr>
        				<td align="left">开始时间：</td>
                    </tr>
                    <tr>
        				<td align="left" style="padding-top:5px;">
                            <input id="sdate" name="sdate" data-options="editable:false" class="easyui-datebox" required style="width:120px;height:28px">
                        </td>
                    </tr>
                    <tr>
        				<td align="left" style="padding-top:5px;">结束时间：</td>
                    </tr>
                    <tr>
        				<td align="left" style="padding-top:5px;">
                            <input id="edate" name="edate" data-options="editable:false" class="easyui-datebox" required style="width:120px;height:28px">
        				</td>
                    </tr>
                    <tr>
        				<td align="left" style="padding-top:5px;"> 
                            <a href="#" class="easyui-linkbutton" style="width:120px;height:35px"onclick="doSearch();">查询</a>
                        </td>
        		    </tr>
        		</table>
            </div>
        </div>
    </div>
    
    <div data-options="region:'center'" style="padding:5px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="padding:6px;height:50px;text-align:center;background:#fffff">
                <span id="myspan" style="font:normal 25px '微软雅黑'; ">那板水库渗压时段过程线</span>
            </div>
        
            <div data-options="region:'center',border:false" style="padding:5px; width: 100%;" fit="true">
                <div id="chart1" style="width: 100%;height:90% "></div>
            </div>
        </div>
    </div>
	
</body>

</html>


