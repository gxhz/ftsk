<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据查询</title>

<%@include file="../head1.jsp" %>

<script language="javascript">
	//初始化界面数据
	window.onload = function() {	
    	var date = new Date();
    	var year = date.getFullYear();
    	
    	$('#year_start').combobox('setValue', year);
    	$('#year_end').combobox('setValue', year);
    	
    	$('#dtype').combobox('setValue', 'db_his_sysw');
    	
		$('#dev').combobox({
			onLoadSuccess : function() { //加载完成后,设置选中第一项
				var val = $(this).combobox("getData");
				for ( var item in val[0]) {
					if (item == "name") {
						$(this).combobox("select", val[0][item]);
					}
				}
				
				doSearch();  //查询
			}
		});

	};
	
	//查询
	function doSearch() {
		var yearstart,yearend,name,years,dtype;

		yearstart = $('#year_start').combobox('getValue');
		yearend = $('#year_end').combobox('getValue');
		name = $('#dev').combobox('getValue');
		dtype = $('#dtype').combobox('getValue');
		var count = parseInt(yearend) - parseInt(yearstart);	
		if(count < 0){
			$.messager.alert('系统提示','开始时间不能大于结束时间!','error');
			return;
		}else if(count == 0){
			years= yearstart;
		}else{
			years = yearstart;
			for(var j = 0; j < count; j++){
				years = years + "," +  (parseInt(yearstart)+j+1);
			}
		}
		if(dtype == '') {
			$.messager.alert('系统提示','请选择数据类型!','error');
			return;
		}
	
		var aa = [];

		$('#chart1').empty();

		$('#myspan').html('查询不到数据！请选择查询条件重新查询。');

		load('正在获取数据，请稍候...');

		$.ajax({
			type : 'post',
			dataType : 'json',
			data : {
				names : years,
				Name : name,
				dtype : dtype
			},
			url : 'GetSYSWKSWGXTData.action',
			success : function(data) {
				disload(); //关闭等待框

				var sineRenderer = function() {
					var arr = new Array();

					var index=0;
					for ( var i = 0; i < data.length; i++) {
						var len = data[i]['kswlist'].length;
						if(len > 0) {
							var seriesTitle = {};
							seriesTitle.label = data[i]['year'];
							seriesTitle.showLine = false;
							
							//多层json
							var mov={};
							if(i == 0) {
								mov.style = 'diamond';
							} else if(i == 1) {
								mov.size = 7;
								mov.style = 'x';
							} else if(i == 2) {
								mov.style = 'circle';
							} else if(i == 3) {
								mov.size = 10;
								mov.style = 'filledSquare';
							} else {
								mov.style = 'circle';
							}
							
							seriesTitle.markerOptions = mov;
							aa[index] = seriesTitle;

							var arrdata = new Array();
							
							$('#myspan').html('渗压水位与上下游水位差关系图');

							for ( var j = 0; j < len; j++) {
								arrdata[j] = new Array();
								arrdata[j][0] = data[i]['kswlist'][j];
								arrdata[j][1] = data[i]['syswlist'][j];
							}

						    arr.push(arrdata);
						    
						    index++;
						};
					};

					return arr;
				};

				$.jqplot('chart1', {
					title : '',
					dataRenderer : sineRenderer,
					legend : {
						show : true,
						placement : 'outsideGrid',
						location : 'e'
					},
					seriesDefaults : {
						rendererOptions: {
			                smooth: true
			            },
			            trendline:{show:true}
					},
					axes : {
						xaxis : {
							label : '上下游水位差（m）',
							tickOptions : {
								showGridline : true
							}
						},
						yaxis : {
							label : '渗压水位(m)'
						}
					},
					highlighter : {
						show : true,
						sizeAdjust : 10
					},
					cursor : {
						show : true
					},
					series : aa
				});

			},
			error : function(e) {
				disload(); //关闭等待框
			}
		});

	}
</script>

</head>
<body class="easyui-layout">
	<div data-options="region:'center'" style="padding:5px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="padding:6px;height:120px;text-align:center;background:#fffff">
                <span id="myspan" style="font:normal 25px '微软雅黑'; ">查询不到数据！请选择查询条件重新查询。</span>
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
                                <td width="120">
                                    <input id="year_start" class="easyui-combobox" style="width:120px;height:28px"
                                        data-options="
                                                url: '../jsonfile/year.json',
                                                method:'get',
                                                valueField:'value',
                                                textField:'text',
                                                editable:false
                                        ">
                                </td>
                                <td width="15">至</td>                         
                                <td width="180">
                                    <input id="year_end" class="easyui-combobox" style="width:120px;height:28px"
                                        data-options="
                                                url: '../jsonfile/year.json',
                                                method:'get',
                                                valueField:'value',
                                                textField:'text',
                                                editable:false
                                        ">
                                </td>
                                
                                <td width="80">渗流量计：</td>                         
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
                                <td width="150" align="left"> 
                                    <a href="#" class="easyui-linkbutton" style="width:120px;height:35px" onclick="doSearch()" >查询</a>
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

