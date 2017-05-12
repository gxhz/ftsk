<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表统计</title>

<%@include file="../head1.jsp" %>

<script language="javascript">
    //得到当前日期
    formatterDate = function(date) {
        var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
        var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
        + (date.getMonth() + 1);
        return date.getFullYear() + '-' + month + '-' + day;
    };
    
    //初始化界面数据
    window.onload = function () { 
    	$('#sdate').datebox('setValue', formatterDate(new Date()));
    	
    	$('#nametable').datagrid({onLoadSuccess : function(data){
    	    $('#nametable').datagrid('selectAll');
    	    
    	    doSearch();  //默认查询当天数据
    	}});
    };
    
    //查询
    function doSearch(){
        var syjname='';
        var sdate,edate;
        
        var aa=[];
        var seriesTitle={};
        
        seriesTitle.label = '上游水位';
        aa[0] = seriesTitle;
        seriesTitle.label = '下游水位';
        aa[1] = seriesTitle;
        
        sdate = $('#sdate').datebox('getValue');
        edate = '-1';
        if((sdate == '')||(edate == '')) {
        	$.messager.alert('系统提示','请输入查询日期!','error');
        	return;
        }
        
        //获取选中的渗压计名称
		var selrows = $('#nametable').datagrid('getSelections');
		for(var i=0; i<selrows.length; i++){
			var str=selrows[i].name;
			
			var seriesTitle1={};
			seriesTitle1.label = str;
	        aa[i+2] = seriesTitle1;
			
			str = str.replace('#','');
			
			if (syjname != '') syjname += ',';
			syjname += str;
		}
        
		if(syjname == '') {
        	$.messager.alert('系统提示','请选择传感器!','error');
        	return;
        }
		
		$('#chart1').empty();
		load('正在获取数据，请稍候...');
		
		$.ajax({
            type:'post',
            dataType:'json',
            data:{CGQType:2,Flag:2,names:syjname,SDate:sdate,EDate:edate},
            url:'GetSDBBTJData.action',
            success:function(data) {
            	disload();  //关闭等待框
            	
            	var sineRenderer = function() {
            		var arr = new Array();
            	    
            		//上游水位
            		var arrdata = new Array();
        			for(var j=0;j<data.length;j++) {
        				arrdata[j] = new Array();
        				arrdata[j][0] = data[j]['time'];
        				arrdata[j][1] = Number(data[j]['syksw']);
                	}
        			arr.push(arrdata);
        			
        			//下游水位
            		var arrdata = new Array();
        			for(var j=0;j<data.length;j++) {
        				arrdata[j] = new Array();
        				arrdata[j][0] = data[j]['time'];
        				arrdata[j][1] = Number(data[j]['xyksw']);
                	}
        			arr.push(arrdata);
            		
        			//渗流量
        			for(var i=0; i<selrows.length; i++){
            			var arrdata = new Array();
            			for(var j=0;j<data.length;j++) {
            				arrdata[j] = new Array();
            				arrdata[j][0] = data[j]['time'];
            				arrdata[j][1] = Number(data[j]['sysw'+(i+1)]);
                    	}
            			arr.push(arrdata);
            		}
            	    
            	    return arr;
            	  };
            	  
            	  $.jqplot ('chart1', {
            		  title: '',
            		  dataRenderer: sineRenderer,
            		  legend: { show: true,  
            	            placement: 'outsideGrid',  
            	            location: 'e'
            	      },
            		  axes: {
            			  xaxis: {
            				 label: '日期（天）',
            				 renderer: $.jqplot.DateAxisRenderer,
            				 rendererOptions:{
        	                    tickRenderer:$.jqplot.CanvasAxisTickRenderer
        	                 },
            	             tickOptions: {
            	                 formatString: '%y/%#m/%#d'
            	             },
            	           	 numberTicks: data.length
                		  },
                		  yaxis:{
                			  label: '渗流量（L/s）',
                			  tickOptions: {
                                  formatString: '%.2f'
                              }
                		  }
            		  },
            		  highlighter: {
            				show: true,
            				sizeAdjust: 10
            		  },
            		  cursor: {
            				show: true
            		  },
            		  series:aa
            	  });
            	  
        	}, 
			error: function(e) { 
				disload();  //关闭等待框
			} 
        });

      	$('#myspan').html(sdate+' 那板水库渗流日过程线');
    }
    
</script>

</head>

<body class="easyui-layout">
    <div data-options="region:'west'" style="width:155px;padding:5px;">
        <div class="easyui-layout" style="width:145px;" fit="true">
            <div data-options="region:'center'" style="padding:0px;border:0px;">
    			<table id="nametable" class="easyui-datagrid" url="GetCGQList?type=2" fit="true" fitColumns="true">
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
        				<td align="left" style="padding-top:5px;">日期选择：</td>
                    </tr>
                    <tr>
        				<td align="left" style="padding-top:5px;">
                            <input id="sdate" name="sdate" data-options="editable:false" class="easyui-datebox" required style="width:120px;height:28px">
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
                <span id="myspan" style="font:normal 25px '微软雅黑'; ">那板水库渗流日过程线</span>
            </div>
        
            <div data-options="region:'center',border:false" style="padding:5px; width: 100%;" fit="true">
                <div id="chart1" style="width: 100%;height:90% "></div>
            </div>
        </div>
    </div>
	
</body>

</html>


