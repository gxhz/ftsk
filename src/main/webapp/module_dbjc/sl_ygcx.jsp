<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表统计</title>

<%@include file="../head1.jsp" %>

<script language="javascript">
    var myChart;

    //初始化界面数据
    window.onload = function () {
    	var date = new Date();
    	var year = date.getFullYear();
    	var month = date.getMonth()+1;
    	
        //初始化echarts实例
        var chart=document.getElementById('chart1');
        myChart = echarts.init(chart);
    	
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
        var year,month;
        var sdate,edate,dtype;
        var legenddata=[];
        
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
			
			legenddata[i] = str;
			
			if (syjname != '') syjname += ',';
			syjname += str;
		}
		
		legenddata[selrows.length] = '降雨量';
		legenddata[selrows.length+1] = '上游水位';
		legenddata[selrows.length+2] = '下游水位';
        
		if(syjname == '') {
        	$.messager.alert('系统提示','请选择传感器!','error');
        	return;
        }
		
		load('正在获取数据，请稍候...');
		
		$.ajax({
            type:'post',
            dataType:'json',
            data:{CGQType:2,Flag:3,names:syjname,SDate:sdate,EDate:edate,dtype:dtype},
            url:'GetSDBBTJData.action',
            success:function(data) {
            	disload();  //关闭等待框
            	
            	var strtime=[];
            	var rjyl=[];
            	var sysw=[];
            	var xysw=[];
            	
            	var seriesStr=[];
        	    
        		//降雨量
    			for(var i=0;i<data.length-5;i++) {
    				strtime[i] = data[i]['time'];
    				rjyl[i] = Number(data[i]['rjyl']);  //降雨量
    				sysw[i] = Number(data[i]['syksw']);  //上游水位
    				xysw[i] = Number(data[i]['xyksw']);  //下游水位
            	}
        		
    			for(var i=0; i<selrows.length; i++){
					var str=selrows[i].name;
    				
    				var seriesTitle={};
    				seriesTitle.name = str;
    				seriesTitle.type = 'line';
    				seriesTitle.smooth = true;
    				
    				var sll=[];
    				
        			for(var j=0;j<data.length-5;j++) {
        				sll[j] = Number(data[j]['sysw'+(i*2+1)]);  //渗流量
                	}
        			
        			seriesTitle.data = sll;
    				seriesStr[i] = seriesTitle;
        		}
    			
    			var seriesTitle={};
				seriesTitle.name = '降雨量';
				seriesTitle.type = 'bar';
				seriesTitle.yAxisIndex = 1;
				seriesTitle.data = rjyl;
				seriesStr[selrows.length] = seriesTitle;
				
				var seriesTitle={};
				seriesTitle.name = '上游水位';
				seriesTitle.type = 'line';
				seriesTitle.smooth = true;
				seriesTitle.yAxisIndex = 2;
				seriesTitle.data = sysw;
				seriesStr[selrows.length+1] = seriesTitle;
				
				var seriesTitle={};
				seriesTitle.name = '下游水位';
				seriesTitle.type = 'line';
				seriesTitle.smooth = true;
				seriesTitle.yAxisIndex = 2;
				seriesTitle.data = xysw;
				seriesStr[selrows.length+2] = seriesTitle;
    			
    			// 指定图表的配置项和数据
    	        myChart.setOption({
    	            tooltip: {
    	                trigger: 'axis',
    	                axisPointer: {
    	                    type: 'cross'
    	                }
    	            },
    	            grid: {
    	                right: '20%'
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
    	                    name: '渗流量（L/s）',
    	                    position: 'left',
    	                    axisLabel: {
    	                        formatter: '{value} L/s'
    	                    }
    	                },
    	                {
    	                    type: 'value',
    	                    name: '降雨量（mm）',
    	                    position: 'right',
    	                    offset: 80,
    	                    axisLabel: {
    	                        formatter: '{value} mm'
    	                    },
    	                    inverse: true
    	                },
    	                {
    	                    type: 'value',
    	                    name: '水位（m）',
    	                    position: 'right',
    	                    axisLabel: {
    	                        formatter: '{value} m'
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
        
        $('#myspan').html(year+'年'+month+'月 那板水库渗流月过程线');
    }
    
</script>

</head>

<body class="easyui-layout">
    <div data-options="region:'west'" style="width:155px;padding:5px;">
        <div class="easyui-layout" style="width:145px;" fit="true">
            <div data-options="region:'center'" style="padding:0px;border:0px;">
    			<table id="nametable" class="easyui-datagrid" url="GetCGQList?type=2" fit="true" fitColumns="true">
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
        		</table>
            </div>
        </div>
    </div>
    
    <div data-options="region:'center'" style="padding:5px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="padding:6px;height:50px;text-align:center;background:#fffff">
                <span id="myspan" style="font:normal 25px '微软雅黑'; ">那板水库渗流月过程线</span>
            </div>
        
            <div data-options="region:'center',border:false" style="padding:5px; width: 100%;" fit="true">
                <div id="chart1" style="width: 100%;height:90% "></div>
            </div>
        </div>
    </div>
	
</body>

</html>


