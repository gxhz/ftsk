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

    <div style="position:absolute;border:0px solid #000000;left:80px;top:385px;width:890px;height:266px;background:#ffffff;z-index:1">
        <div id="ksw" style="position:absolute;bottom:0;width:890px;height:190px;background:#63B8FF;">
            <div id="kswstr" style="position:absolute;width: 150px;left:35px;bottom:150px">
                <span id="sw" style="color:#0000FF;">库水位：0.00 m</span>
            </div>
        </div>
    </div>
    
    <div id="wswstr" style="position:absolute;width: 150px;right:20px;bottom:320px;">
        <span id="wsw" style="color:#0000FF;">尾水位：0.00 m</span>
    </div>

    <div style="position:absolute;width:1692px;height:815px;background:url(../images/dm4_200.png);overflow:auto;z-index:2">
        <div data-options="region:'north',border:false" style="padding:3px;height:38px;text-align:center;background:#fffff">
            <span id="myspan" style="font:normal 25px '微软雅黑'; ">大坝安全 断面IV 数据监控</span>
        </div>
    
        <div class="easyui-layout" style="width:1692px;height:250px">
            <table id="tt" class="easyui-datagrid" url="GetDBRTData?dbdm=4" fitColumns="true"
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
        <div id="PD1-1-H" style="position:absolute;border:1px solid #b8d0d6;left:820px;top:433px;width:10px;height:250px;background:#ffffff;">
            <div id="PD1-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PD1-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PD1-1</div>
            </div>
        </div>
        
        <div id="PD1-2-H" style="position:absolute;border:1px solid #b8d0d6;left:833px;top:433px;width:10px;height:180px;background:#ffffff;">
            <div id="PD1-2" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PD1-2-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PD1-2</div>
            </div>
        </div>
        
        <div id="PD2-1-H" style="position:absolute;border:1px solid #b8d0d6;left:1023px;top:397px;width:10px;height:285px;background:#ffffff;">
            <div id="PD2-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PD2-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PD2-1</div>
            </div>
        </div>
        
        <div id="PD2-2-H" style="position:absolute;border:1px solid #b8d0d6;left:1036px;top:401px;width:10px;height:216px;background:#ffffff;">
            <div id="PD2-2" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PD2-2-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PD2-2</div>
            </div>
        </div>
        
        <div id="PD3-1-H" style="position:absolute;border:1px solid #b8d0d6;left:1166px;top:447px;width:10px;height:235px;background:#ffffff;">
            <div id="PD3-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PD3-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PD3-1</div>
            </div>
        </div>
        
        <div id="PD3-2-H" style="position:absolute;border:1px solid #b8d0d6;left:1179px;top:451px;width:10px;height:172px;background:#ffffff;">
            <div id="PD3-2" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PD3-2-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PD3-2</div>
            </div>
        </div>
        
        <div id="PD4-1-H" style="position:absolute;border:1px solid #b8d0d6;left:1326px;top:501px;width:10px;height:180px;background:#ffffff;">
            <div id="PD4-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PD4-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PD4-1</div>
            </div>
        </div>
        
        <div id="PD4-2-H" style="position:absolute;border:1px solid #b8d0d6;left:1339px;top:505px;width:10px;height:123px;background:#ffffff;">
            <div id="PD4-2" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PD4-2-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PD4-2</div>
            </div>
        </div>
         -->
         
         <div id="PD1-1-H" style="position:absolute;left:820px;top:433px;height:250px;background:#ffffff;">
            <div id="PD1-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:25px;bottom:20px"><span id="PD1-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:25px;bottom:0px">PD1-1</div>
            </div>
        </div>
        
        <div id="PD1-2-H" style="position:absolute;left:833px;top:433px;height:180px;background:#ffffff;">
            <div id="PD1-2" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:10px"><span id="PD1-2-V" style="color:#8B0000;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:-10px">PD1-2</div>
            </div>
        </div>
        
        <div id="PD2-1-H" style="position:absolute;left:1023px;top:397px;height:285px;background:#ffffff;">
            <div id="PD2-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:18px;bottom:15px"><span id="PD2-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:18px;bottom:-5px">PD2-1</div>
            </div>
        </div>
        
        <div id="PD2-2-H" style="position:absolute;left:1036px;top:401px;height:216px;background:#ffffff;">
            <div id="PD2-2" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:10px;bottom:5px"><span id="PD2-2-V" style="color:#8B0000;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:10px;bottom:-15px">PD2-2</div>
            </div>
        </div>
        
        <div id="PD3-1-H" style="position:absolute;left:1166px;top:447px;height:235px;background:#ffffff;">
            <div id="PD3-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:18px;bottom:15px"><span id="PD3-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:18px;bottom:-5px">PD3-1</div>
            </div>
        </div>
        
        <div id="PD3-2-H" style="position:absolute;left:1179px;top:451px;height:172px;background:#ffffff;">
            <div id="PD3-2" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:10px;bottom:10px"><span id="PD3-2-V" style="color:#8B0000;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:10px;bottom:-10px">PD3-2</div>
            </div>
        </div>
        
        <div id="PD4-1-H" style="position:absolute;left:1326px;top:501px;height:180px;background:#ffffff;">
            <div id="PD4-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:18px;bottom:15px"><span id="PD4-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:18px;bottom:-5px">PD4-1</div>
            </div>
        </div>
        
        <div id="PD4-2-H" style="position:absolute;left:1339px;top:505px;height:123px;background:#ffffff;">
            <div id="PD4-2" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:10px;bottom:10px"><span id="PD4-2-V" style="color:#8B0000;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:10px;bottom:-10px">PD4-2</div>
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
    	}});
    	
    	showKSW();  //显示库水位
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
    		    	//计算公式：(库水位-管口低程) / (管口高程-管口低程) * 柱形高度
    		    	var show_sksw=(sksw - 180.00) / (232.56-180.00) * 266.00;
    		    	if(show_sksw < 0) show_sksw = 0.0;
    		    	document.getElementById('ksw').style.height = show_sksw+'px';  //水位
    		    	document.getElementById('kswstr').style.bottom = (show_sksw+10)+'px';  //水位显示位置
    		    	
    		    	document.getElementById('sw').innerText = '库水位：'+sksw+' m';  //库水位
    		    	document.getElementById('wsw').innerText = '尾水位：'+xysw+' m';  //尾水位
    			}
    		}
      	});
    }
    
</script>

</html>