<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据查询</title>

<%@include file="../head1.jsp" %>

<script language="javascript">
	var myChart;

    //初始化界面数据
    window.onload = function () {
    	var date = new Date();
    	var year = date.getFullYear();
    	
    	$('#year').combobox('setValue', year);
    	
    	//初始化echarts实例
        var chart=document.getElementById('chart1');
        myChart = echarts.init(chart);
    	
    	$('#dev').combobox({ 
            onLoadSuccess: function () { //加载完成后,设置选中第一项
                var val = $(this).combobox("getData");
                for (var item in val[0]) {
                    if (item == "value") {
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
		var code,name;
		
		year = $('#year').combobox('getValue');
		code = $('#dev').combobox('getValue');
		name = $('#dev').combobox('getText');
		
		if(year == '') {
			$.messager.alert('系统提示','请选择年份!','error');
			return;
		}
		if(code == '') {
			$.messager.alert('系统提示','请选择过程点!','error');
			return;
		}
		
		sdate = year+'-01-01';
		edate = year+'-12-31';
		
		load('正在获取数据，请稍候...');
		
		$.ajax({
            type:'post',
            dataType:'json',
            data:{code: code,sdate: sdate,edate: edate},
            url:'GetJYLSDGCXData.action',
            success:function(data) {
            	disload();  //关闭等待框
            	
            	var seriesStr=[];
            	var yAxisStr=[];
      		  	var strtime=[];
            	var rjyl=[];
            	var sysw=[];
            	var xysw=[];
            	
            	var legenddata=[];
        	    
    			for(var i=0;i<data.length;i++) {
    				strtime[i] = data[i]['time'];
    				rjyl[i] = Number(data[i]['rjyl1']);  //日降雨量
    				sysw[i] = Number(data[i]['syksw']);  //上游库水位
    				xysw[i] = Number(data[i]['xyksw']);  //下游库水位
            	}
    			
    			//降雨量
    			var seriesTitle={};
				seriesTitle.name = name;
				seriesTitle.type = 'bar';
				seriesTitle.smooth = true;
				seriesTitle.data = rjyl;
				seriesStr[0] = seriesTitle;
				
				legenddata[0] = name;
				
				var yAxisTitle={};
				yAxisTitle.type = 'value';
				yAxisTitle.name = '降雨量（mm）';
				yAxisTitle.position = 'left';
				yAxisTitle.axisLabel = { formatter: '{value} mm' };
				yAxisStr[0] = yAxisTitle;
				
				if(document.getElementById('showksw').checked) {
					var seriesTitle={};
					seriesTitle.name = '上游水位';
					seriesTitle.type = 'line';
					seriesTitle.smooth = true;
					seriesTitle.yAxisIndex = 1;
					seriesTitle.data = sysw;
					seriesStr[1] = seriesTitle;
					
					legenddata[1] = '上游水位';
					
					var seriesTitle={};
					seriesTitle.name = '下游水位';
					seriesTitle.type = 'line';
					seriesTitle.smooth = true;
					seriesTitle.yAxisIndex = 1;
					seriesTitle.data = xysw;
					seriesStr[2] = seriesTitle;
					
					legenddata[2] = '下游水位';
					
					var yAxisTitle={};
					yAxisTitle.type = 'value';
					yAxisTitle.name = '水位（m）';
					yAxisTitle.position = 'right';
					yAxisTitle.axisLabel = { formatter: '{value} m' };
					yAxisStr[1] = yAxisTitle;
					
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
    	            yAxis: yAxisStr,
    	            series: seriesStr
    	        }, true);
            	  
        	}, 
			error: function(e) { 
				disload();  //关闭等待框
			} 
        });
		
		$('#myspan').html($('#year').combobox('getText')+' 那板水库 '+$('#dev').combobox('getText')+' 年过程线');
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
                                                editable:false
                                        ">
                                </td>
                                <td width="80">雨量站选择：</td>                         
                                <td width="180">
                                    <input id="dev" class="easyui-combobox" style="width:120px;height:28px"
                                        data-options="
                                                url: '../jsonfile/jylname.json',
                                                method:'get',
                                                valueField:'value',
                                                textField:'text',
                                                editable:false
                                        ">
                                </td>
                                <td width="150" align="left"> 
                                    <a href="#" class="easyui-linkbutton" style="width:120px;height:35px" onclick="doSearch()" >查询</a>
                                </td>
                                <td width="80"></td>
                                <td width="180">
                                    <input type="checkbox" id="showksw" checked="true">显示库水位</input>
                                </td>
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

