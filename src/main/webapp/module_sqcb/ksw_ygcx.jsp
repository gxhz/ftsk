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
    	
    	$('#year').combobox('setValue', year);
    	if(month > 9) {
    		$('#month').combobox('setValue', month);
    	} else {
    		$('#month').combobox('setValue', '0'+month);
    	}
    	
    	//初始化echarts实例
        var chart=document.getElementById('chart1');
        myChart = echarts.init(chart);
    	
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
        
		load('正在获取数据，请稍候...');
		
		$.ajax({
            type:'post',
            dataType:'json',
            data:{sdate: sdate, edate: edate},
            url:'GetKSWSDBBTJData.action',
            success:function(data) {
            	disload();  //关闭等待框
            	
            	var strtime=[];
              	var rjyl=[];
              	var sysw=[];
              	var xysw=[];
          	    
      			for(var i=0;i<data.length-5;i++) {
      				strtime[i] = data[i]['time'];
      				rjyl[i] = Number(data[i]['rjyl']);  //日降雨量
      				sysw[i] = Number(data[i]['syksw']);  //上游库水位
      				xysw[i] = Number(data[i]['xyksw']);  //下游库水位
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
      	                data:['上游水位','下游水位','降雨量']
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
      	                    position: 'right',
      	                    axisLabel: {
      	                        formatter: '{value} mm'
      	                    },
      	                    inverse: true
      	                },
      	                {
      	                    type: 'value',
      	                    name: '水位（m）',
      	                    position: 'left',
      	                    axisLabel: {
      	                        formatter: '{value} m'
      	                    }
      	                }
      	            ],
      	            series: [
      	                {
      	                    name:'降雨量',
      	                    type:'bar',
      	                    data: rjyl
      	                },
      	                {
      	                    name:'上游水位',
      	                    type:'line',
      	                    smooth: true,
      	                    yAxisIndex: 1,
      	                    data: sysw
      	                },
      	                {
      	                    name:'下游水位',
      	                    type:'line',
      	                    smooth: true,
      	                    yAxisIndex: 1,
      	                    data: xysw
      	                }
      	            ]
      	        }, true);
            	  
        	}, 
			error: function(e) { 
				disload();  //关闭等待框
			} 
        });
        
        $('#myspan').html(year+'年'+month+'月 那板水库库水位时段过程线');
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


