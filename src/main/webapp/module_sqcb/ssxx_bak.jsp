<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="../head1.jsp" %> 

<style type="text/css">
    .datagrid-mask{
      opacity:0;
      filter:alpha(opacity=0);
    }
    .datagrid-mask-msg{
      opacity:0;
      filter:alpha(opacity=0);
    }
</style>

<title>实时信息</title>


</head>

<style type="text/css">
  
    .datagrid-header-row td{background-color:#6B6B6B;color:#ffffff;}
    
</style>

<script type="text/javascript">
    function cellStyler(value,row,index){
    	return 'background-color:#B8B8B8;';
    }
    
    function cellStyler1(value,row,index){
    	return 'background-color:#E0EEEE;';
    }
</script>

<body>
    <div style="border:0px solid #b8d0d6;padding:12px;width:width:100%;text-align:center;background:#fffff">
        <span style="font:normal 25px '微软雅黑'; ">那板水库水情实时数据表</span>
    </div>
    <div id="time" style="border:1px solid #b8d0d6;padding:8px;width:width:100%;text-align:center;background:#FAFAD2;">
        2016年07月13日 16:48:10      星期三
    </div>
    <div id="syqdiv" style="width:100%;height:102px">
		<table id="tt" class="easyui-datagrid" style="width: 100%; height: auto" pagination="false"
			url="GetSWAction" rownumbers="false" singleSelect="true" fitColumns="true" fit="true">
			<thead>
				<tr>
					<th field="swt" width="10px" data-options="align:'center',styler:cellStyler">名称</th>
					<th field="swv" width="10px" data-options="align:'center',styler:cellStyler1">值</th>
					<th field="grt" width="10px" data-options="align:'center',styler:cellStyler">名称</th>
					<th field="grv" width="10px" data-options="align:'center',styler:cellStyler1"">值</th>
					<th field="llt" width="10px" data-options="align:'center',styler:cellStyler">名称</th>
					<th field="llv" width="10px" data-options="align:'center',styler:cellStyler1"">值</th>
				</tr>
			</thead>
		</table>
	</div>
    <div style="border:1px solid #b8d0d6;padding:8px;width:width:100%;text-align:center;background:#FAFAD2;">
        2016年07月13日八时至今库区遥测站降雨量(mm)
    </div>
    <div id="syqdiv1" style="width:100%;height:152px">
        <table id="tt1" class="easyui-datagrid" style="width: 100%; height: auto" pagination="false" toolbar="#tb1"
            url="GetYLAction" rownumbers="false" singleSelect="true" fitColumns="true" fit="true">
            <thead>
                <tr>
                    <th field="name1" width="10px" data-options="align:'center',styler:cellStyler"">站名</th>
                    <th field="gprsv1" width="10px" data-options="align:'center',styler:cellStyler1"">降雨量</th>
                    <!-- <th field="value1" width="10px" data-options="align:'center',styler:cellStyler1"">(超短波)</th> -->
                    <th field="name2" width="10px" data-options="align:'center',styler:cellStyler"">站名</th>
                    <th field="gprsv2" width="10px" data-options="align:'center',styler:cellStyler1"">降雨量</th>
                    <!-- <th field="value2" width="10px" data-options="align:'center',styler:cellStyler1"">(超短波)</th> -->
                </tr>
            </thead>
        </table>
    </div>
    <div style="border:1px solid #b8d0d6;padding:8px;width:width:100%;text-align:center;background:#FAFAD2;">
        公告栏
    </div>
    <div style="padding:16px;background:#E0EEEE;">
        <div style="border:0px solid #b8d0d6;padding:8px;width:width:100%;text-align:center;">
            <a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1',size:'large'" style="width:120px;">预报调度</a>
            <a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1',size:'large'" style="width:120px;">实测雨量</a>
            <a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1',size:'large'" style="width:120px;">实测水位流量</a>
            <a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1',size:'large'" style="width:120px;">综合报表</a>
        </div>
        
        <div style="border:0px solid #b8d0d6;padding:8px;width:width:100%;text-align:center;">
            <table id="tt1" class="easyui-datagrid" style="width: 100%; height: auto" pagination="false" 
                url="GetKSWAction" rownumbers="false" singleSelect="true" fitColumns="true" 
                data-options="
                    rowStyler: function(index,row){
                        if (index % 2 == 1){
                            return 'background-color:#EAEAEA;';
                        }
                    }
                ">
                <thead>
                    <tr>
                        <th field="time" width="10px" data-options="align:'center'">日期</th>
                        <th field="ksw" width="10px" data-options="align:'center'">水库水位</th>
                        <th field="rkll" width="10px" data-options="align:'center'">入库流量</th>
                        <th field="phll" width="10px" data-options="align:'center'">排洪流量</th>
                        <th field="wsw" width="10px" data-options="align:'center'">尾水位</th>
                        <th field="fdll" width="10px" data-options="align:'center'">发电流量</th>
                        <th field="skkr" width="10px" data-options="align:'center'">水库库容</th>
                        <th field="jzzcl" width="10px" data-options="align:'center'">(机组总出力)</th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
    
</body>

<script language="javascript">
	var url = "";
	function doSearch() {
		$('#tt').datagrid('load', {
			id_mp : $('#monitoringpoint').combobox('getValue'),
			id_reservoir : $('#reservoir').combobox('getValue'),
			time_start : $('#date_0').val(),
			time_end : $('#date_1').val()
		});
	}

	var sec = 0;
	timerID = setInterval("show_cur_times()",1000);
	function Reflash(){
		/*$('#tt').datagrid('reload');*/
	}
	
	//合并单元格
	$('#tt1').datagrid({    
	    onLoadSuccess:function(){    
	    	var merges = [{
                field:'gprsv1',
                index: 4,
                colspan: 3
            },
            {
                field:'gprsv2',
                index: 4,
                colspan: 2
            }];    
	    	for (var i = 0; i < merges.length; i++)
                $('#tt1').datagrid('mergeCells', {
                    index: merges[i].index,
                    field: merges[i].field,
                    colspan: merges[i].colspan
                });   
	    }    
	});   
	
	function show_cur_times(){
        //获取当前日期
        var date_time = new Date();
        //定义星期
        var week;
        //switch判断
        switch (date_time.getDay()){
        case 1: week="星期一"; break;
        case 2: week="星期二"; break;
        case 3: week="星期三"; break;
        case 4: week="星期四"; break;
        case 5: week="星期五"; break;
        case 6: week="星期六"; break;
        default:week="星期天"; break;
         }
         
         //年
         var year = date_time.getFullYear();
         	//判断小于10，前面补0
           if(year<10){
         	year="0"+year;
         }
         
         //月
         var month = date_time.getMonth()+1;
         	//判断小于10，前面补0
          if(month<10){
        month="0"+month;
         }
         
         //日
         var day = date_time.getDate();
         	//判断小于10，前面补0
           if(day<10){
         	day="0"+day;
         }
         
         //时
         var hours =date_time.getHours();
         	//判断小于10，前面补0
            if(hours<10){
         	hours="0"+hours;
         }
         
         //分
         var minutes =date_time.getMinutes();
         	//判断小于10，前面补0
            if(minutes<10){
         	minutes="0"+minutes;
         }
         
         //秒
         var seconds=date_time.getSeconds();
         	//判断小于10，前面补0
            if(seconds<10){
         	seconds="0"+seconds;
         }
         
         //拼接年月日时分秒
         var date_str = year+"年"+month+"月"+day+"日 "+hours+":"+minutes+":"+seconds+" "+week;
         
         //显示在id为showtimes的容器里
         document.getElementById("time").innerHTML= date_str;
        }
	
</script>

</html>