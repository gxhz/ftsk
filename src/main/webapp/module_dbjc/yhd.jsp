<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="../head1.jsp" %> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实时信息</title>

</head>

<body>

    <div id="kswstr" style="position:absolute;width: 150px;left:280px;top:240px;z-index:12">
        <span id="sw" style="color:#0000FF;">库水位：0.00 m</span>
    </div>
    
    <div id="wswstr" style="position:absolute;width: 150px;right:30px;bottom:380px;z-index:12">
        <span id="wsw" style="color:#0000FF;">尾水位：0.00 m</span>
    </div>

    <div style="position:absolute;width:1692px;height:815px;background:url(../images/yhd.jpg);overflow:auto;z-index:2">
        <div data-options="region:'north',border:false" style="padding:3px;height:38px;text-align:center;background:#fffff">
            <span id="myspan" style="font:normal 25px '微软雅黑'; ">大坝安全 溢洪道 数据监控</span>
        </div>
    
        <div class="easyui-layout" style="width:1692px;height:160px">
            <table id="tt" class="easyui-datagrid" url="GetDBRTData?dbdm=6" fitColumns="true"
                pagination="false" rownumbers="false" singleSelect="true" pageSize="200" fit="true">
                <thead>
                    <tr>
                      <th field="name" width="120">名称</th>
                      <th field="sysw" width="120">渗压水位</th>
                      <th field="msgc" width="120">埋设高程</th>
                      <th field="gkgc" width="120">管口高程</th>
                      <th field="plms" width="120">频率模数</th>
                      <th field="wd" width="120">温度</th>
                      <th field="mcubh" width="120">MCU编号</th>
                      <th field="mcutd" width="120">MCU通道</th>
                    </tr>
                </thead>
            </table>
        </div>
    
        <!-- 
        <div id="PY1-1-H" style="position:absolute;border:1px solid #b8d0d6;left:305px;top:340px;width:10px;height:60px;background:#ffffff;">
            <div id="PY1-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:-15px;bottom:-40px"><span id="PY1-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:-15px;bottom:-20px">PY1-1</div>
            </div>
        </div>
        
        <div id="PY2-1-H" style="position:absolute;border:1px solid #b8d0d6;left:510px;top:368px;width:10px;height:60px;background:#ffffff;">
            <div id="PY2-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PY2-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PY2-1</div>
            </div>
        </div>
        
        <div id="PY3-1-H" style="position:absolute;border:1px solid #b8d0d6;left:810px;top:474px;width:10px;height:70px;background:#ffffff;">
            <div id="PY3-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:35px"><span id="PY3-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:15px">PY3-1</div>
            </div>
        </div>
        
        <div id="PY4-1-H" style="position:absolute;border:1px solid #b8d0d6;left:1100px;top:525px;width:10px;height:35px;background:#ffffff;">
            <div id="PY4-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:-15px;bottom:-30px"><span id="PY4-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:-15px;bottom:-50px">PY4-1</div>
            </div>
        </div>
        
        <div id="PY5-1-H" style="position:absolute;border:1px solid #b8d0d6;left:1400px;top:523px;width:10px;height:35px;background:#ffffff;">
            <div id="PY5-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:-15px;bottom:-30px"><span id="PY5-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:-15px;bottom:-50px">PY5-1</div>
            </div>
        </div>
        -->
        
        <div id="PY1-1-H" style="position:absolute;left:305px;top:340px;height:60px;background:#ffffff;">
            <div id="PY1-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:-15px;bottom:-40px"><span id="PY1-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:-15px;bottom:-20px">PY1-1</div>
            </div>
        </div>
        
        <div id="PY2-1-H" style="position:absolute;left:510px;top:368px;height:60px;background:#ffffff;">
            <div id="PY2-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PY2-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PY2-1</div>
            </div>
        </div>
        
        <div id="PY3-1-H" style="position:absolute;left:810px;top:474px;height:70px;background:#ffffff;">
            <div id="PY3-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:35px"><span id="PY3-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:15px">PY3-1</div>
            </div>
        </div>
        
        <div id="PY4-1-H" style="position:absolute;left:1100px;top:525px;height:35px;background:#ffffff;">
            <div id="PY4-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:25px;bottom:20px"><span id="PY4-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:25px;bottom:0px">PY4-1</div>
            </div>
        </div>
        
        <div id="PY5-1-H" style="position:absolute;left:1400px;top:523px;height:35px;background:#ffffff;">
            <div id="PY5-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:35px;bottom:20px"><span id="PY5-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:35px;bottom:0px">PY5-1</div>
            </div>
        </div>
        
    </div>

</body>

<script type="text/javascript">


    //初始化界面数据
    window.onload = function () { 
    	$('#tt').datagrid({onLoadSuccess : function(data){
    		//显示渗压水位
    		var selrows = $('#tt').datagrid('getRows');
    		for(var i=0; i<selrows.length; i++){
    			document.getElementById(selrows[i].name+'-V').innerText = selrows[i].sysw +' m';
    			
    			//计算公式：(渗压水位-管口低程) / (管口高程-管口低程) * 柱形高度
    			/*
    			var h = document.getElementById(selrows[i].name+'-H').style.height;
    			h = h.substr(0, h.length-2);
    			var show_sysw=(selrows[i].sysw - selrows[i].msgc) / (selrows[i].gkgc-selrows[i].msgc) * h;
    			document.getElementById(selrows[i].name).style.height = show_sysw + 'px';
    			*/
    		}
    		
    		showKSW();  //显示库水位
    	}});
    };
    
    function showKSW() {
    	//获取库水位、库容量
    	$.ajax({
    		type: 'post',
    		url: 'GetDBRTData',
    		dataType: 'json',
    	    ContentType: 'application/json; charset=utf-8',
    		cache: true,
    		async: true,
    		data: {'dbdm':'ksw'},
    		success:function(data){
    			if(data.length > 0) {
    				var sksw=data[0].sysw;
    				var xysw=data[0].xysw;
    		    	
    		    	document.getElementById('sw').innerText = '库水位：'+sksw+' m';  //库水位
    		    	document.getElementById('wsw').innerText = '尾水位：'+xysw+' m';  //尾水位
    			}
    		}
      	});
    }
    
</script>

</html>