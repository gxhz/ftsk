<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>特定库水位下渗压水位过程线</title>

<%@include file="../head1.jsp" %>

<script language="javascript">
	var myChart;
    
    //初始化界面数据
    window.onload = function () { 
    	$('#dtype').combobox('setValue', 'db_his_sysw');
    	
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
            }
        }); 
    	
    };
    
    //查询
    function doSearch(){
    	var name,ksw1,ksw2,ksw3,ksw4,ksw5,ksw6,ksw7,ksw8,ksws;
    	var dtype;
    	
    	name = $('#dev').combobox('getValue');
    	ksw1 = $('#ksw1').val();
    	ksw2 = $('#ksw2').val();
    	ksw3 = $('#ksw3').val();
    	ksw4 = $('#ksw4').val();
    	ksw5 = $('#ksw5').val();
    	ksw6 = $('#ksw6').val();
    	ksw7 = $('#ksw7').val();
    	ksw8 = $('#ksw8').val();
    	ksws = '';
    	
    	dtype = $('#dtype').combobox('getValue');
    	
    	if(name == '') {
			$.messager.alert('系统提示','请选择传感器!','error');
			return;
		}
    	if((ksw1 == '')&&(ksw2 == '')&&(ksw3 == '')&&(ksw4 == '')&&(ksw5 == '')&&(ksw6 == '')&&(ksw7 == '')&&(ksw8 == '')) {
    		$.messager.alert('系统提示','请输入特定库水位!','error');
    		return;
    	}
    	if(dtype == '') {
			$.messager.alert('系统提示','请选择数据类型!','error');
			return;
		}
    	
    	if(ksw1 != '') ksws = ksws + ',' + ksw1;
    	if(ksw2 != '') ksws = ksws + ',' + ksw2;
    	if(ksw3 != '') ksws = ksws + ',' + ksw3;
    	if(ksw4 != '') ksws = ksws + ',' + ksw4;
    	if(ksw5 != '') ksws = ksws + ',' + ksw5;
    	if(ksw6 != '') ksws = ksws + ',' + ksw6;
    	if(ksw7 != '') ksws = ksws + ',' + ksw7;
    	if(ksw8 != '') ksws = ksws + ',' + ksw8;
    	
    	ksws = ksws.substring(1, ksws.length);
    	var kswss=ksws.split(",");
    	var len=kswss.length;
    	
    	var legenddata=[];
        
        for(var i=0;i<len;i++) {
        	legenddata[i] = '特定库水位 '+kswss[i]+' m';
        }
        
        
		$('#myspan').html('查询不到数据！请选择查询条件重新查询。');
		
		load('正在获取数据，请稍候...');
		
		$.ajax({
            type:'post',
            dataType:'json',
            data:{Name:name,names:ksws,dtype:dtype},
            url:'GetFBTData.action',
            success:function(data) {
            	disload();  //关闭等待框
            	
            	var seriesStr=[];
            	var strtime=[];
            	
            	for(var i=0;i<data.length;i++) {
                	var sy=[];
                	
        			var len=data[i]['timelist'].length;
        			if(len > 0) $('#myspan').html('特定库水位下渗流压力水位过程线');
        			
        			for(var j=0;j<len;j++) {
        				if(i == 0) strtime[j] = data[i]['timelist'][j];  //日期
        				sy[j] = data[i]['syswlist'][j];  //渗压水位
        			}
        			
        			var seriesTitle={};
        			seriesTitle.name = legenddata[i];
        			seriesTitle.type = 'line';
        			seriesTitle.smooth = true;
        			seriesTitle.data = sy;
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
    	                    name: '渗压水位（m）',
    	                    position: 'left',
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
    }
    
</script>

</head>

<body class="easyui-layout">
    <div data-options="region:'west'" style="width:155px;padding:5px;">
        <div class="easyui-layout" style="width:145px;" fit="true">
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
    				<td align="left" style="padding-top:25px;">渗压计：</td>
                </tr>
                <tr>
    				<td align="left" style="padding-top:5px;">
                        <input id="dev" class="easyui-combobox" style="width:120px;height:28px"
                            data-options="
                                    url: 'GetCGQList?type=1',
                                    method:'get',
                                    valueField:'name',
                                    textField:'name',
                                    editable:false
                            ">
                    </td>
                </tr>
                <tr>
    				<td align="left" style="padding-top:25px;">特定库水位1：</td>
                </tr>
                <tr>
    				<td align="left" style="padding-top:5px;">
                        <input id="ksw1" name="ksw1" class="easyui-textbox" style="width:120px;height:28px">
    				</td>
                </tr>
                <tr>
                    <td align="left" style="padding-top:5px;">特定库水位2：</td>
                </tr>
                <tr>
                    <td align="left" style="padding-top:5px;">
                        <input id="ksw2" name="ksw2" class="easyui-textbox" style="width:120px;height:28px">
                    </td>
                </tr>
                <tr>
                    <td align="left" style="padding-top:5px;">特定库水位3：</td>
                </tr>
                <tr>
                    <td align="left" style="padding-top:5px;">
                        <input id="ksw3" name="ksw3" class="easyui-textbox" style="width:120px;height:28px">
                    </td>
                </tr>
                <tr>
                    <td align="left" style="padding-top:5px;">特定库水位4：</td>
                </tr>
                <tr>
                    <td align="left" style="padding-top:5px;">
                        <input id="ksw4" name="ksw4" class="easyui-textbox" style="width:120px;height:28px">
                    </td>
                </tr>
                <tr>
                    <td align="left" style="padding-top:5px;">特定库水位5：</td>
                </tr>
                <tr>
                    <td align="left" style="padding-top:5px;">
                        <input id="ksw5" name="ksw5" class="easyui-textbox" style="width:120px;height:28px">
                    </td>
                </tr>
                <tr>
                    <td align="left" style="padding-top:5px;">特定库水位6：</td>
                </tr>
                <tr>
                    <td align="left" style="padding-top:5px;">
                        <input id="ksw6" name="ksw6" class="easyui-textbox" style="width:120px;height:28px">
                    </td>
                </tr>
                <tr>
                    <td align="left" style="padding-top:5px;">特定库水位7：</td>
                </tr>
                <tr>
                    <td align="left" style="padding-top:5px;">
                        <input id="ksw7" name="ksw7" class="easyui-textbox" style="width:120px;height:28px">
                    </td>
                </tr>
                <tr>
                    <td align="left" style="padding-top:5px;">特定库水位8：</td>
                </tr>
                <tr>
                    <td align="left" style="padding-top:5px;">
                        <input id="ksw8" name="ksw8" class="easyui-textbox" style="width:120px;height:28px">
                    </td>
                </tr>
                <tr>
    				<td align="left" style="padding-top:25px;"> 
                        <a href="#" class="easyui-linkbutton" style="width:120px;height:35px"onclick="doSearch();">查询</a>
                    </td>
    		    </tr>
    		</table>
        </div>
    </div>
    
    <!-- 
    <div data-options="region:'east'" style="width:300px;padding:5px;">
        <table id="nametable" class="easyui-datagrid" url="GetCGQList?type=1" fit="true" fitColumns="true">
        <thead>
            <tr>
              <th field="time" width="10">时间</th>
              <th field="name" width="10">库水位</th>
              <th field="sysw" width="10">渗压水位</th>
            </tr>
        </thead>
        </table>
    </div>
     -->
    
    <div data-options="region:'center'" style="padding:5px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="padding:6px;height:50px;text-align:center;background:#fffff">
                <span id="myspan" style="font:normal 25px '微软雅黑'; ">查询不到数据！请选择查询条件重新查询。</span>
            </div>
        
            <div data-options="region:'center',border:false" style="padding:5px; width: 100%;" fit="true">
                <div id="chart1" style="width: 100%;height:90% "></div>
            </div>
        </div>
    </div>
	
</body>

</html>


