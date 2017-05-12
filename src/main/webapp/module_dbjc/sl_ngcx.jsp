<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>渗流年过程线</title>

<%@include file="../head1.jsp" %>

<script language="javascript">
	var myChart;

    //初始化界面数据
    window.onload = function () {
    	var date = new Date();
    	var year = date.getFullYear();
    	
    	//初始化echarts实例
        var chart=document.getElementById('chart1');
        myChart = echarts.init(chart);
    	
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
		
		load('正在获取数据，请稍候...');
		
		$.ajax({
            type:'post',
            dataType:'json',
            data:{CGQType:2,names:name,SDate:sdate,EDate:edate,dtype:dtype},
            url:'GetNGCXData.action',
            success:function(data) {
            	disload();  //关闭等待框
            	
            	var strtime=[];
              	var rjyl=[];
              	var sysw=[];
              	var xysw=[];
              	var sll=[];
          	    
      			for(var i=0;i<data.length;i++) {
      				strtime[i] = data[i]['time'];
      				rjyl[i] = Number(data[i]['rjyl']);  //日降雨量
      				sysw[i] = Number(data[i]['syksw']);  //上游库水位
      				xysw[i] = Number(data[i]['xyksw']);  //下游库水位
              	}
      			
      			for(var i=0;i<data.length;i++) {
      				sll[i] = Number(data[i]['sysw']);  //渗流量
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
      	                data:[name,'降雨量','上游水位','下游水位']
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
      	            series: [
      	                {
      	                    name:name,
      	                    type:'line',
      	                    smooth: true,
      	                    data: sll
      	                },
      	                {
      	                    name:'降雨量',
      	                    type:'bar',
      	                    yAxisIndex: 1,
      	                    data: rjyl
      	                },
      	                {
      	                    name:'上游水位',
      	                    type:'line',
      	                    smooth: true,
      	                    yAxisIndex: 2,
      	                    data: sysw
      	                },
      	                {
      	                    name:'下游水位',
      	                    type:'line',
      	                    smooth: true,
      	                    yAxisIndex: 2,
      	                    data: xysw
      	                }
      	            ]
      	        }, true);
            	  
        	}, 
			error: function(e) { 
				disload();  //关闭等待框
			} 
        });
		
		$('#myspan').html($('#year').combobox('getText')+' 那板水库 '+$('#dev').combobox('getText')+' 渗流年过程线');
	}
	
</script>

</head>
<body class="easyui-layout">
	<div data-options="region:'center'" style="padding:5px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="padding:6px;height:120px;text-align:center;background:#fffff">
                <span id="myspan" style="font:normal 25px '微软雅黑'; ">那板水库年过程线</span>
                <div style="padding:5px;"></div>
                <div class="easyui-panel" style="padding:3px;">
                    <div style="border:0px solid #b8d0d6;padding:1px;width:100%">
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
                                                panelHeight:'auto',
                                                editable:false
                                        ">
                                </td>
                                <td width="80">传感器选择：</td>                         
                                <td width="180">
                                    <input id="dev" class="easyui-combobox" style="width:120px;height:28px"
                                        data-options="
                                                url: 'GetCGQList?type=2',
                                                method:'get',
                                                valueField:'name',
                                                textField:'name',
                                                panelHeight:'auto',
                                                editable:false
                                        ">
                                </td>
                                <td width="150" align="left"> <a href="#" class="easyui-linkbutton" style="width:120px;height:35px" onclick="doSearch()" >查询</a></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        
            <div data-options="region:'center',border:false" style="padding:5px; width: 100%;" fit="true">
                <div id="chart1" style="width: 100%;height:80% "></div>
            </div>
        </div>
    </div>
</body>

</html>

