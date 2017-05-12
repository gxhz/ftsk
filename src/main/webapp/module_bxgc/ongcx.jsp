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
    	
    	$('#bxgcname').combobox('setValue', "hoffset");
    	$('#year').combobox('setValue', year);
    	$('#dtype').combobox('setValue', 'bx_his_wyl');
    	
    	//初始化echarts实例
        var chart=document.getElementById('chart1');
        myChart = echarts.init(chart);
    	
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
		var name,bname,dtype;
		
		year = $('#year').combobox('getValue');
		name = $('#dev').combobox('getValue');
		bname = $('#bxgcname').combobox('getValue');
		dtype = $('#dtype').combobox('getValue');
		
		if(year == '') {
			$.messager.alert('系统提示','请选择年份!','error');
			return;
		}
		if(name == '') {
			$.messager.alert('系统提示','请选择观测点!','error');
			return;
		}
		if(dtype == '') {
			$.messager.alert('系统提示','请选择数据类型!','error');
			return;
		}
		
		sdate = year+'-01-01';
		edate = year+'-12-31';
		
		var strname="横向水平位移";
        if(bname == 'hoffset') {
        	strname="横向水平位移";
        } else if(bname == 'voffset') {
        	strname="纵向水平位移";
        } else if(bname == 'zoffset') {
        	strname="竖向位移";
        }
		
		load('正在获取数据，请稍候...');
		
		$.ajax({
            type:'post',
            dataType:'json',
            data:{names:name,sdate:sdate,edate:edate,bname:bname,dtype:dtype},
            url:'GetBXNGCXData.action',
            success:function(data) {
            	disload();  //关闭等待框
            	
            	var strtime=[];
            	var hoffset=[];
            	
            	var seriesStr=[];
            	
            	var seriesTitle={};
  				seriesTitle.name = name;
  				seriesTitle.type = 'line';
  				seriesTitle.smooth = true;
            	
            	for(var i=0;i<data.length;i++) {
            		strtime[i] = data[i]['time'];
            		hoffset[i] = Number(data[i]['hoffset']);
            	}
            	
            	seriesTitle.data = hoffset;
  				seriesStr[0] = seriesTitle;
            	
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
      	                data: [ name ]
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
      	                    name: '位移量（mm）',
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
		
		$('#myspan').html($('#year').combobox('getText')+' 那板水库 '+$('#dev').combobox('getText')+' '+strname+' 年过程线');
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
                                <td width="90">观测类型选择：</td>                         
                                <td width="180">
                                    <input id="bxgcname" class="easyui-combobox" style="width:120px;height:28px"
                                        data-options="
                                                url: '../jsonfile/bxgcname.json',
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
                                                editable:false
                                        ">
                                </td>
                                <td width="80">观测点选择：</td>                         
                                <td width="180">
                                    <input id="dev" class="easyui-combobox" style="width:120px;height:28px"
                                        data-options="
                                                url: 'GetBXRTData',
                                                method:'get',
                                                valueField:'name',
                                                textField:'name',
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

