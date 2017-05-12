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

    <div style="position:absolute;border:0px solid #000000;left:134px;top:446px;width:600px;height:203px;background:#ffffff;z-index:1">
        <div id="ksw" style="position:absolute;bottom:0;width:600px;height:190px;background:#63B8FF;">
            <div id="kswstr" style="position:absolute;width: 150px;left:15px;bottom:230px">
                <span id="sw" style="color:#0000FF;">库水位：0.00 m</span>
            </div>
        </div>
    </div>
    
    <div id="wswstr" style="position:absolute;width: 150px;right:20px;bottom:160px;">
        <span id="wsw" style="color:#0000FF;">尾水位：0.00 m</span>
    </div>

    <div style="position:absolute;width:1692px;height:815px;background:url(../images/dm1_20.png);overflow:auto;z-index:2">
        <div data-options="region:'north',border:false" style="padding:3px;height:38px;text-align:center;background:#fffff">
            <span id="myspan" style="font:normal 25px '微软雅黑'; ">大坝安全 断面I 数据监控</span>
        </div>
    
        <div class="easyui-layout" style="width:1692px;height:250px">
            <table id="tt" class="easyui-datagrid" url="GetDBRTData?dbdm=1" fitColumns="true"
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
        <div id="PA1-1-H" style="position:absolute;border:1px solid #b8d0d6;left:430px;top:541px;width:10px;height:130px;background:#ffffff;">
            <div id="PA1-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PA1-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PA1-1</div>
            </div>
        </div>
        
        <div id="PA1-2-H" style="position:absolute;border:1px solid #b8d0d6;left:443px;top:541px;width:10px;height:60px;background:#ffffff;">
            <div id="PA1-2" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PA1-2-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PA1-2</div>
            </div>
        </div>
        
        <div id="PA2-1-H" style="position:absolute;border:1px solid #b8d0d6;left:819px;top:472px;width:10px;height:180px;background:#ffffff;">
            <div id="PA2-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PA2-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PA2-1</div>
            </div>
        </div>
        
        <div id="PA2-2-H" style="position:absolute;border:1px solid #b8d0d6;left:832px;top:476px;width:10px;height:105px;background:#ffffff;">
            <div id="PA2-2" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PA2-2-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PA2-2</div>
            </div>
        </div>
        
        <div id="PA3-1-H" style="position:absolute;border:1px solid #b8d0d6;left:1092px;top:569px;width:10px;height:140px;background:#ffffff;">
            <div id="PA3-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PA3-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PA3-1</div>
            </div>
        </div>
        
        <div id="PA3-2-H" style="position:absolute;border:1px solid #b8d0d6;left:1105px;top:573px;width:10px;height:80px;background:#ffffff;">
            <div id="PA3-2" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PA3-2-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PA3-2</div>
            </div>
        </div>
        
        <div id="PA4-1-H" style="position:absolute;border:1px solid #b8d0d6;left:1437px;top:687px;width:10px;height:92px;background:#ffffff;">
            <div id="PA4-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PA4-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PA4-1</div>
            </div>
        </div>
        
        <div id="PA4-2-H" style="position:absolute;border:1px solid #b8d0d6;left:1448px;top:691px;width:10px;height:40px;background:#ffffff;">
            <div id="PA4-2" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PA4-2-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PA4-2</div>
            </div>
        </div>
         -->
         
        <div id="PA1-1-H" style="position:absolute;left:430px;top:541px;height:130px;background:#ffffff;">
            <div id="PA1-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PA1-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PA1-1</div>
            </div>
        </div>
        
        <div id="PA1-2-H" style="position:absolute;left:443px;top:541px;height:60px;background:#ffffff;">
            <div id="PA1-2" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:3px;bottom:15px"><span id="PA1-2-V" style="color:#8B0000;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:3px;bottom:-5px">PA1-2</div>
            </div>
        </div>
        
        <div id="PA2-1-H" style="position:absolute;left:819px;top:472px;height:180px;background:#ffffff;">
            <div id="PA2-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:18px;bottom:17px"><span id="PA2-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:18px;bottom:-3px">PA2-1</div>
            </div>
        </div>
        
        <div id="PA2-2-H" style="position:absolute;left:832px;top:476px;height:105px;background:#ffffff;">
            <div id="PA2-2" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PA2-2-V" style="color:#8B0000;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PA2-2</div>
            </div>
        </div>
        
        <div id="PA3-1-H" style="position:absolute;left:1092px;top:569px;height:140px;background:#ffffff;">
            <div id="PA3-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:38px;bottom:0px"><span id="PA3-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:38px;bottom:-20px">PA3-1</div>
            </div>
        </div>
        
        <div id="PA3-2-H" style="position:absolute;left:1105px;top:573px;height:80px;background:#ffffff;">
            <div id="PA3-2" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:25px;bottom:15px"><span id="PA3-2-V" style="color:#8B0000;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:25px;bottom:-5px">PA3-2</div>
            </div>
        </div>
        
        <div id="PA4-1-H" style="position:absolute;left:1437px;top:687px;height:92px;background:#ffffff;font-family:宋体;">
            <div id="PA4-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:18px;bottom:0px"><span id="PA4-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:18px;bottom:-20px">PA4-1</div>
            </div>
        </div>
        
        <div id="PA4-2-H" style="position:absolute;left:1448px;top:691px;height:40px;background:#ffffff;">
            <div id="PA4-2" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:10px;bottom:10px"><span id="PA4-2-V" style="color:#8B0000;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:10px;bottom:-10px">PA4-2</div>
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
    		    	var show_sksw=(sksw - 212.00) / (232.56-212.00) * 203.00;
    		    	if(show_sksw < 0) show_sksw = 0.0;
    		    	document.getElementById('ksw').style.height = show_sksw+'px';  //水位
    		    	//document.getElementById('kswstr').style.bottom = (show_sksw+10)+'px';  //水位显示位置
    		    	
    		    	document.getElementById('sw').innerText = '库水位：'+sksw+' m';  //库水位
    		    	document.getElementById('wsw').innerText = '尾水位：'+xysw+' m';  //尾水位
    			}
    		}
      	});
    }
    
</script>

</html>