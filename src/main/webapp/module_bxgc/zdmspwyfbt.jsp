<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>纵断面水平位移分布图</title>

<%@include file="../head1.jsp" %>

<script language="javascript">
	var myChart;

    //初始化界面数据
    window.onload = function () {
    	$('#sdate').datebox('setValue', formatterDate(new Date()));
    	$('#edate').datebox('setValue', formatterDate(new Date()));
    	$('#bxgcname').combobox('setValue', "hoffset");
    	$('#dtype').combobox('setValue', 'bx_his_wyl');
    	
    	//初始化echarts实例
        var chart=document.getElementById('chart1');
        myChart = echarts.init(chart);
    	
    	doSearch();  //默认查询当天数据
    };

	//查询
	function doSearch(){
		var sdate,edate,bname,dtype;
		
		sdate = $('#sdate').datebox('getValue');
        edate = $('#edate').datebox('getValue');
        bname = $('#bxgcname').combobox('getValue');
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
            data:{names:'W11,W12,W13,W21,W22,W23,W31,W32,W33,W41,W42,W43,W51,W52,W53,W61,W62,W63',
            	sdate:sdate,edate:edate,bname:bname,dtype:dtype},
            url:'GetBXFBTData.action',
            success:function(data) {
            	disload();  //关闭等待框
            	
            	if(data.length > 0) $('#myspan').html(sdate+'至'+edate+' 那板水库 纵断面水平位移分布图');
            	
            	var seriesStr=[];
        	    var legenddata=[];
        	    var xvalue=[];
        	    
        	    var ymax=[0,0,0,0,0,0];
        	    var ymin=[0,0,0,0,0,0];
        	    
        	    xvalue[0] = 85;
        	    xvalue[1] = 135;
        	    xvalue[2] = 190;
        	    
        	    //获取Y轴最大、最小值
        	    for(var i=0;i<data.length;i++) {
					for(var j=0;j<6;j++) {
						var value=[];
						
						for(var k=0;k<3;k++) {
							value[k] = data[i]['zoffset'+(j*3+k+1)];
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
					                   [{"value": "W"+(i+1)+"1", "coord": [0, ymin[i]], "symbol": 'none'},{"coord": [0, ymax[i]], "symbol": 'none'}],
					                   [{"value": "W"+(i+1)+"2", "coord": [1, ymin[i]], "symbol": 'none'},{"coord": [1, ymax[i]], "symbol": 'none'}],
					                   [{"value": "W"+(i+1)+"3", "coord": [2, ymin[i]], "symbol": 'none'},{"coord": [2, ymax[i]], "symbol": 'none'}]
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
					
					//分六组，每组3个数
					for(var j=0;j<6;j++) {
						var value=[];
						
						for(var k=0;k<3;k++) {
							value[k] = data[i]['zoffset'+(j*3+k+1)];  //竖向位移量
						}
						
		              	var seriesTitle={};
						seriesTitle.name = time;
						seriesTitle.animation = false;
						seriesTitle.type = 'line';
						seriesTitle.smooth = false;
						seriesTitle.xAxisIndex = j;
						seriesTitle.yAxisIndex = j;
						seriesTitle.data = value;
						seriesStr[len+i*6+j] = seriesTitle;
					}
					
				};
				
				// 指定图表的配置项和数据
    	        myChart.setOption({
    	        	title:[
    	                   {
    	                       text:'迎水坡 上0-025.5 高程',
    	                       left:'25%',
    	                       top:'5%',
    	                       textAlign:'center'
    	                   },
    	                   {
    	                       text:'坝体 0+000 高程',
    	                       left:'73%',
    	                       top:'5%',
    	                       textAlign:'center'
    	                   },
    	                   {
    	                       text:'背水坡 下0+034.5 高程',
    	                       left:'25%',
    	                       top:'37%',
    	                       textAlign:'center'
    	                   },
    	                   {
    	                       text:'背水坡 下0+060.5 高程',
    	                       left:'73%',
    	                       top:'37%',
    	                       textAlign:'center'
    	                   },
    	                   {
    	                       text:'背水坡 下0+085.5 高程',
    	                       left:'25%',
    	                       top:'70%',
    	                       textAlign:'center'
    	                   },
    	                   {
    	                       text:'背水坡 下0+108.5 高程',
    	                       left:'73%',
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
    	                    {x:80,   y:75,   width:650,  height:125},
                            {x2:160, y:75,   width:650,  height:125},
                            {x:80,   y2:240, width:650,  height:125},
                            {x2:160, y2:240, width:650,  height:125},
                            {x:80,   y2:20,  width:650,  height:125},
                            {x2:160, y2:20,  width:650,  height:125}
    	               ],
	               tooltip: {
     	                trigger: 'axis',
     	                axisPointer: {
     	                    type: 'cross'
     	                }
     	            },
    	            xAxis: [
    	                {gridIndex: 0, type: 'category', data: xvalue, axisLabel: { formatter: '0+{value} 桩号' }},
                        {gridIndex: 1, type: 'category', data: xvalue, axisLabel: { formatter: '0+{value} 桩号' }},
                        {gridIndex: 2, type: 'category', data: xvalue, axisLabel: { formatter: '0+{value} 桩号' }},
                        {gridIndex: 3, type: 'category', data: xvalue, axisLabel: { formatter: '0+{value} 桩号' }},
                        {gridIndex: 4, type: 'category', data: xvalue, axisLabel: { formatter: '0+{value} 桩号' }},
                        {gridIndex: 5, type: 'category', data: xvalue, axisLabel: { formatter: '0+{value} 桩号' }}
    	            ],
    	            yAxis: [
    	                {gridIndex: 0,name: '水平位移量（mm）'},
                        {gridIndex: 1,name: '水平位移量（mm）'},
                        {gridIndex: 2,name: '水平位移量（mm）'},
                        {gridIndex: 3,name: '水平位移量（mm）'},
                        {gridIndex: 4,name: '水平位移量（mm）'},
                        {gridIndex: 5,name: '水平位移量（mm）'}
    	            ],
    	            series: seriesStr
    	        }, true);
            	
        	}, 
			error: function(e) { 
				disload();  //关闭等待框
			} 
        });
		
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
                                <td width="70">位移选择：</td>                         
                                <td width="180">
                                    <input id="bxgcname" class="easyui-combobox" style="width:120px;height:28px"
                                        data-options="
                                                url: '../jsonfile/bxgcname1.json',
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
        
            <div data-options="region:'center',border:false" style="padding:5px; width: 100%;">
                <div id="chart1" style="width: 1660px;height:660px "></div>
            </div>
        </div>
    </div>
</body>

</html>

