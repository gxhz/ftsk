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
    	$('#sdate').datebox('setValue', formatterDate(new Date()));
    	$('#edate').datebox('setValue', formatterDate(new Date()));
    	$('#dtype').combobox('setValue', 'bx_his_wyl');
    	
    	//初始化echarts实例
        var chart=document.getElementById('chart1');
        myChart = echarts.init(chart);
    	
    	doSearch();  //默认查询当天数据
    };

	//查询
	function doSearch(){
		var bname,sdate,edate,dtype;
		
		sdate = $('#sdate').datebox('getValue');
        edate = $('#edate').datebox('getValue');
        bname = 'zoffset';
        dtype = $('#dtype').combobox('getValue');
        if((sdate == '')||(edate == '')) {
        	$.messager.alert('系统提示','请输入开始时间或结束时间!','error');
        	return;
        }
        if(dtype == '') {
			$.messager.alert('系统提示','请选择数据类型!','error');
			return;
		}
        
		$('#myspan').html('查询不到数据！请选择查询条件重新查询。');
		
		load('正在获取数据，请稍候...');
		
		$.ajax({
            type:'post',
            dataType:'json',
            data:{names:'W11,W21,W31,W41,W51,W61,W12,W22,W32,W42,W52,W62,W13,W23,W33,W43,W53,W63',
            	sdate:sdate,edate:edate,bname:bname,dtype:dtype},
            url:'GetBXFBTData.action',
            success:function(data) {
            	disload();  //关闭等待框
            	
            	if(data.length > 0) $('#myspan').html(sdate+'至'+edate+' 那板水库 横断面竖向位移分布图');
            	
            	var seriesStr=[];
        	    var legenddata=[];
        	    var xvalue=[];
        	    
        	    var ymax=[0,0,0];
        	    var ymin=[0,0,0];
        	    
        	    xvalue[0] = -25.5;
        	    xvalue[1] = 0.0;
        	    xvalue[2] = 34.5;
        	    xvalue[3] = 60.5;
        	    xvalue[4] = 85.5;
        	    xvalue[5] = 108.5;
        	    
        	    //获取Y轴最大、最小值
        	    for(var i=0;i<data.length;i++) {
					for(var j=0;j<3;j++) {
						var value=[];
						
						for(var k=0;k<6;k++) {
							value[k] = data[i]['zoffset'+(j*6+k+1)];
						}
						
						for(var k=0;k<value.length;k++) {
							if(ymax[j] < value[k]) ymax[j] = value[k];
							if(ymin[j] > value[k]) ymin[j] = value[k];
						}
					}
					
				};
				
				if(data.length > 0) {
					//画基准线
					for(var i=0;i<ymax.length;i++) {
						var jml1 = {
								"animation": false,
								"itemStyle": {
			                        "normal": {
			                            "lineStyle":{
			                                "color": '#FF0000'    
			                            }
			                        }
			                    },
			                    "lineStyle": {"normal":{"type":'solid'}},
								"data":[
					                   [{"value": "W1"+(i+1), "coord": [0, ymin[i]], "symbol": 'none'},{"coord": [0, ymax[i]], "symbol": 'none'}],
					                   [{"value": "W2"+(i+1), "coord": [1, ymin[i]], "symbol": 'none'},{"coord": [1, ymax[i]], "symbol": 'none'}],
					                   [{"value": "W3"+(i+1), "coord": [2, ymin[i]], "symbol": 'none'},{"coord": [2, ymax[i]], "symbol": 'none'}],
					                   [{"value": "W4"+(i+1), "coord": [3, ymin[i]], "symbol": 'none'},{"coord": [3, ymax[i]], "symbol": 'none'}],
					                   [{"value": "W5"+(i+1), "coord": [4, ymin[i]], "symbol": 'none'},{"coord": [4, ymax[i]], "symbol": 'none'}],
					                   [{"value": "W6"+(i+1), "coord": [5, ymin[i]], "symbol": 'none'},{"coord": [5, ymax[i]], "symbol": 'none'}]
					                   ]
				                };
						
						var seriesTitle={};
						seriesTitle.name = i;  //对于多条基准线，此属性必须设置，且值必须不同
						seriesTitle.type = 'line';
						seriesTitle.smooth = false;
						seriesTitle.markLine = jml1;
						seriesTitle.xAxisIndex = i;
						seriesTitle.yAxisIndex = i;
						seriesStr[i] = seriesTitle;
					}
				}
				
				//画曲线
				var len=ymax.length;
				for(var i=0;i<data.length;i++) {
					var time=data[i]['time'];  //日期
					legenddata[i] = time;
					
					//分三组，每组6个数
					for(var j=0;j<3;j++) {
						var value=[];
						
						for(var k=0;k<6;k++) {
							value[k] = data[i]['zoffset'+(j*6+k+1)];  //竖向位移量
						}
						
		              	var seriesTitle={};
						seriesTitle.name = time;
						seriesTitle.animation = false;
						seriesTitle.type = 'line';
						seriesTitle.smooth = false;
						seriesTitle.xAxisIndex = j;
						seriesTitle.yAxisIndex = j;
						seriesTitle.data = value;
						seriesStr[len+i*3+j] = seriesTitle;
					}
					
				};
				
				// 指定图表的配置项和数据
    	        myChart.setOption({
    	        	title:[
    	                   {
    	                       text:'0+085 桩号',
    	                       left:'50%',
    	                       top:'5%',
    	                       textAlign:'center'
    	                   },
    	                   {
    	                       text:'0+135 桩号',
    	                       left:'50%',
    	                       top:'37%',
    	                       textAlign:'center'
    	                   },
    	                   {
    	                       text:'0+190 桩号',
    	                       left:'50%',
    	                       top:'70%',
    	                       textAlign:'center'
    	                   }
    	                   
    	               ],
    	            toolbox: {
    	                feature: {
    	                    dataView: {show: true, readOnly: false},
    	                    restore: {show: true},
    	                    saveAsImage: {show: true}
    	                }
    	            },
    	            legend: {
    	                data: legenddata,
    	                left: 'center'
    	            },
    	            grid: [
                            {x2:100, y:75,   width:1080,  height:125},
                            {x2:100, y2:250, width:1080,  height:125},
                            {x2:100, y2:30,  width:1080,  height:125}
    	               ],
	               tooltip: {
     	                trigger: 'axis',
     	                axisPointer: {
     	                    type: 'cross'
     	                }
     	            },
    	            xAxis: [
    	                {gridIndex: 0, type: 'category', data: xvalue, axisLabel: { formatter: formatterfun }},
                        {gridIndex: 1, type: 'category', data: xvalue, axisLabel: { formatter: formatterfun }},
                        {gridIndex: 2, type: 'category', data: xvalue, axisLabel: { formatter: formatterfun }},
    	            ],
    	            yAxis: [
    	                {gridIndex: 0,name: '竖向位移量（mm）'},
                        {gridIndex: 1,name: '竖向位移量（mm）'},
                        {gridIndex: 2,name: '竖向位移量（mm）'},
    	            ],
    	            series: seriesStr
    	        }, true);
            	
        	}, 
			error: function(e) { 
				disload();  //关闭等待框
			} 
        });
		
	}
	
	//X轴坐标数据格式化
	function formatterfun(value) {
		var str=value.toString();
		if(value < 0) {
			str = "上0"+value;
		} else if(value == 0) {
			str = "0+"+value;
		} else {
			str = "下0+"+value;
		}
        return str;
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
                                                    url: '../jsonfile/bxdatatype.json',
                                                    method:'get',
                                                    valueField:'value',
                                                    textField:'text',
                                                    panelHeight:'auto',
                                                    editable:false
                                            ">
                                </td>
                                <td width="70">开始时间：</td>
                                <td width="180">
                                    <input id="sdate" name="sdate" data-options="editable:false" class="easyui-datebox" required style="width:120px;height:28px">
                                </td>
                                <td width="70">结束时间：</td>
                                <td width="180">
                                    <input id="edate" name="edate" data-options="editable:false" class="easyui-datebox" required style="width:120px;height:28px">
                                </td>
                                <td width="150" align="left"> <a href="#" class="easyui-linkbutton" style="width:120px;height:35px" onclick="doSearch()" >查询</a></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        
            <div data-options="region:'center',border:false" style="position:relative; padding:5px;">
                <div style="position:absolute;left: 10px;top: 50px;width: 400px;height:150px;background:url(../images/W11.png);"></div>
                <div style="position:absolute;left: 10px;top: 270px;width: 400px;height:150px;background:url(../images/W12.png);"></div>
                <div style="position:absolute;left: 10px;top: 490px;width: 400px;height:150px;background:url(../images/W13.png);"></div>
                <div id="chart1" style="position:absolute;left: 430px;top: 10px;width: 1250px;height:660px "></div>
            </div>
        </div>
    </div>
</body>

</html>

