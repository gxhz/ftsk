<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预报调度过程图</title>

<%@include file="../head1.jsp" %>

<script language="javascript">
	var myChart;
    
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
    	
    	
    	var chart=document.getElementById('chart1');
        myChart = echarts.init(chart);
        
   	    doSearch();  //默认查询当天数据
    };
    
    //查询
    function doSearch(){
        var sdate,edate;
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
        
        $.ajax({
			type:'post',
            dataType:'json',
            data:{SDate: sdate,EDate: edate},
            url:'GetHSYBJGBData',
			 success:function(data){
				var strtime=[];
				 
        		var sc_jyl = [];  //实测降雨量
        		var yb_jyl = [];  //预报降雨量
        		var sc_rkll = [];  //实测入库流量
        		var yb_rkll = [];  //预报入库流量
        		var sc_ksw = [];  //实测库水位
        		var yb_ksw = [];  //预报库水位
        		var sc_ckll = [];  //实测出库流量
        		var yb_ckll = [];  //预报出库流量
        		
        		var yb_rkll_1=[];
		        var yb_ksw_1=[];
		        var yb_ckll_1=[];
        		
        		var len=data.length;
        		len = len-11;
        		
		        for(var i = 0; i < len; i++) {
		        	strtime[i] = data[i].time;
		        	
		        	sc_jyl[i] = data[i].sc_jyl;
		        	sc_rkll[i] = data[i].sc_rkll;
		        	sc_ksw[i] = data[i].sc_ksw;
		        	sc_ckll[i] = data[i].sc_ckll;
		        	
		        	yb_jyl[i] = data[i].yb_jyl;
		        	yb_rkll[i] = data[i].yb_rkll;
		        	yb_ksw[i] = data[i].yb_ksw;
		        	yb_ckll[i] = data[i].yb_ckll;
	        	}
		        
		        
		        for(var i=len-1;i<data.length;i++) {
		        	strtime[i] = data[i].time;
		        	
		        	yb_rkll_1[i] = data[i].yb_rkll;
		        	yb_ksw_1[i] = data[i].yb_ksw;
		        	yb_ckll_1[i] = data[i].yb_ckll;
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
		                data:['实测降雨量','预报降雨量','实测入库流量','预报入库流量','实测库水位','预报库水位','实测出库流量','预报出库流量']
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
		                    name: '流量（m³/s）',
		                    position: 'left',
		                    max:20,
		                    axisLabel: {
		                        formatter: '{value} m³/s'
		                    }
		                },
		                {
		                    type: 'value',
		                    name: '雨量（mm）',
		                    position: 'right',
		                    offset: 80,
		                    axisLabel: {
		                        formatter: '{value} mm'
		                    },
		                    max:40,
		                    inverse: true
		                },
		                {
		                    type: 'value',
		                    name: '库水位（m）',
		                    position: 'right',
		                    max:20,
		                    axisLabel: {
		                        formatter: '{value} m'
		                    }
		                }
		            ],
		            series: [
		                {
		                    name:'实测降雨量',
		                    type:'bar',
		                    yAxisIndex: 1,
		                    data: sc_jyl
		                },
		                {
		                    name:'预报降雨量',
		                    type:'bar',
		                    yAxisIndex: 1,
		                    data: yb_jyl
		                },
		                {
		                    name:'实测入库流量',
		                    type:'line',
		                    smooth: true,
		                    itemStyle : {    
		                        normal : {    
		                            lineStyle:{    
		                                color:'#000000'    
		                            }   
		                        }    
		                    },
		                    data: sc_rkll,
		                    markLine:{
		                    	animation: false,
		                    	lineStyle: {normal:{type:'solid'}},
		                    	itemStyle : {    
			                        normal : {    
			                            lineStyle:{    
			                                color:'#FF0000'    
			                            }   
			                        }    
			                    },
		                    	data:[
		                    	      {xAxis: strtime[12], symbol: 'none'}
		                    	      ]
		                    }
		                },
		                {
		                    name:'预报入库流量',
		                    type:'line',
		                    smooth: true,
		                    itemStyle : {    
		                        normal : {    
		                            lineStyle:{    
		                                color:'#0000FF'    
		                            }   
		                        }    
		                    },
		                    data: yb_rkll
		                },
		                {
		                    name:'实测库水位',
		                    type:'line',
		                    smooth: true,
		                    itemStyle : {    
		                        normal : {    
		                            lineStyle:{    
		                                color:'#00FFFF'    
		                            }   
		                        }    
		                    },
		                    yAxisIndex: 2,
		                    data: sc_ksw
		                },
		                {
		                    name:'预报库水位',
		                    type:'line',
		                    smooth: true,
		                    itemStyle : {    
		                        normal : {    
		                            lineStyle:{    
		                                color:'#5D478B'    
		                            }   
		                        }    
		                    },
		                    yAxisIndex: 2,
		                    data: yb_ksw
		                },
		                {
		                    name:'实测出库流量',
		                    type:'line',
		                    itemStyle : {    
		                        normal : {    
		                            lineStyle:{    
		                                color:'#FF0000'    
		                            }   
		                        }    
		                    },
		                    data: sc_ckll
		                },
		                {
		                    name:'预报出库流量',
		                    type:'line',
		                    itemStyle : {    
		                        normal : {    
		                            lineStyle:{    
		                                color:'#FF00FF'    
		                            }   
		                        }    
		                    },
		                    data: yb_ckll
		                },{  //预报入库流量
		                    type:'line',
		                    lineStyle:{normal:{type:'dashed'}},
		                    itemStyle : {    
		                        normal : {    
		                            lineStyle:{    
		                                color:'#0000FF'    
		                            }   
		                        }    
		                    },
		                    data: yb_rkll_1
		                } ,
		                {  //预报库水位
		                    type:'line',
		                    yAxisIndex: 2,
		                    lineStyle:{normal:{type:'dashed'}},
		                    itemStyle : {    
		                        normal : {    
		                            lineStyle:{    
		                                color:'#5D478B'    
		                            }   
		                        }    
		                    },
		                    data: yb_ksw_1
		                },
		                {  //预报出库流量
		                    type:'line',
		                    lineStyle:{normal:{type:'dashed'}},
		                    itemStyle : {    
		                        normal : {    
		                            lineStyle:{    
		                                color:'#FF00FF'    
		                            }   
		                        }    
		                    },
		                    data: yb_ckll_1
		                }
		            ]
		        });
			 }
            
        });
    }
    
</script>

</head>

<body class="easyui-layout">
    <div data-options="region:'center'" style="padding:5px;">
    	<div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="padding:6px;height:125px;text-align:center;background:#fffff">
                <span id="myspan" style="font:normal 25px '微软雅黑'; ">洪水预报调度过程图</span>
            	
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
                <div id="chart1" style="width: 100%;height:80% "></div>
            </div>
       </div>
    </div>
	
</body>

</html>


