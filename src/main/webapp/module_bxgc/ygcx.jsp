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
    	
    	$('#bxgcname').combobox('setValue', "hoffset");
    	$('#year').combobox('setValue', year);
    	$('#dtype').combobox('setValue', 'bx_his_wyl');
    	if(month > 9) {
    		$('#month').combobox('setValue', month);
    	} else {
    		$('#month').combobox('setValue', '0'+month);
    	}
    	
    	//初始化echarts实例
        var chart=document.getElementById('chart1');
        myChart = echarts.init(chart);
    	
    	$('#nametable').datagrid({onLoadSuccess : function(data){
    	    $('#nametable').datagrid('selectRow', 0);
    	    $('#nametable').datagrid('selectRow', 1);
    	    
    	    doSearch();  //默认查询当天数据
    	}});
    };
    
    //查询
    function doSearch(){
        var syjname='';
        var year,month;
        var sdate,edate,bname,dtype;
        var legenddata=[];
        
        year = $('#year').combobox('getValue');
        month = $('#month').combobox('getValue');
        bname = $('#bxgcname').combobox('getValue');
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
        
        //获取选中的水平位移计名称
		var selrows = $('#nametable').datagrid('getSelections');
		for(var i=0; i<selrows.length; i++){
			var str=selrows[i].name;
			
			legenddata[i] = str;
			
			if (syjname != '') syjname += ',';
			syjname += str;
		}
        
		if(syjname == '') {
        	$.messager.alert('系统提示','请选择观测点!','error');
        	return;
        }
		
		var strname="横向水平位移";
        if(bname == 'hoffset') {
        	strname="横向水平位移";
        } else if(bname == 'voffset') {
        	strname="纵向水平位移";
        } else if(bname == 'zoffset') {
        	strname="竖向位移";
        }
		
		$('#myspan').html('查询不到数据！请选择查询条件重新查询。');
		
		load('正在获取数据，请稍候...');
		
		$.ajax({
            type:'post',
            dataType:'json',
            data:{flag:3,names:syjname,sdate:sdate,edate:edate,bname:bname,dtype:dtype},
            url:'GetBXSDBBTJData.action',
            success:function(data) {
            	disload();  //关闭等待框
            		
        		if(data.length > 4) $('#myspan').html(year+'年'+month+'月 那板水库 '+strname+' 月过程线');
            	  
            	var strtime=[];
            	var seriesStr=[];
            	
            	for(var i=0; i<selrows.length; i++){
    				var str=selrows[i].name;
      				
      				var seriesTitle={};
      				seriesTitle.name = str;
      				seriesTitle.type = 'line';
      				seriesTitle.smooth = true;
      				
      				var hoffset=[];
      				
          			for(var j=0;j<data.length-4;j++) {
          				if(i == 0) strtime[j] = data[j]['time'];  //日期
          				hoffset[j] = Number(data[j]['hoffset'+(i+1)]);
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
    }
    
</script>

</head>

<body class="easyui-layout">
    <div data-options="region:'west'" style="width:155px;padding:5px;">
        <div class="easyui-layout" style="width:145px;" fit="true">
            <div data-options="region:'center'" style="padding:0px;border:0px;">
    			<table id="nametable" class="easyui-datagrid" url="GetBXRTData" fit="true" fitColumns="true">
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
                                        url: '../jsonfile/bxdatatype.json',
                                        method:'get',
                                        valueField:'value',
                                        textField:'text',
                                        panelHeight:'auto',
                                        editable:false
                                ">
                        </td>
                    </tr>
                    <tr>
                        <td align="left">观测类型选择：</td>
                    </tr>
                    <tr>
                        <td align="left" style="padding-top:5px;">
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
                <span id="myspan" style="font:normal 25px '微软雅黑'; ">那板水库水平位移月过程线</span>
            </div>
        
            <div data-options="region:'center',border:false" style="padding:5px; width: 100%;" fit="true">
                <div id="chart1" style="width: 100%;height:90% "></div>
            </div>
        </div>
    </div>
	
</body>

</html>


