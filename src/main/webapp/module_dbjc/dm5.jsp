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

    <div style="position:absolute;border:0px solid #000000;left:135px;top:435px;width:640px;height:214px;background:#ffffff;z-index:1">
        <div id="ksw" style="position:absolute;bottom:0;width:640px;height:150px;background:#63B8FF;">
            <div id="kswstr" style="position:absolute;width: 150px;left:25px;bottom:240px">
                <span id="sw" style="color:#0000FF;">库水位：0.00 m</span>
            </div>
        </div>
    </div>
    
    <div id="wswstr" style="position:absolute;width: 150px;right:20px;bottom:220px;">
        <span id="wsw" style="color:#0000FF;">尾水位：0.00 m</span>
    </div>

    <div style="position:absolute;width:1692px;height:815px;background:url(../images/dm5_260.png);overflow:auto;z-index:2">
        <div data-options="region:'north',border:false" style="padding:3px;height:38px;text-align:center;background:#fffff">
            <span id="myspan" style="font:normal 25px '微软雅黑'; ">大坝安全 断面V 数据监控</span>
        </div>
    
        <div class="easyui-layout" style="width:1692px;height:250px">
            <table id="tt" class="easyui-datagrid" url="GetDBRTData?dbdm=5" fitColumns="true"
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
        <div id="PE1-1-H" style="position:absolute;border:1px solid #b8d0d6;left:463px;top:531px;width:10px;height:180px;background:#ffffff;">
            <div id="PE1-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PE1-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PE1-1</div>
            </div>
        </div>
        
        <div id="PE1-2-H" style="position:absolute;border:1px solid #b8d0d6;left:476px;top:531px;width:10px;height:110px;background:#ffffff;">
            <div id="PE1-2" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:80px"><span id="PE1-2-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:60px">PE1-2</div>
            </div>
        </div>
        
        <div id="PE2-1-H" style="position:absolute;border:1px solid #b8d0d6;left:857px;top:460px;width:10px;height:256px;background:#ffffff;">
            <div id="PE2-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:10px"><span id="PE2-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:-10px">PE2-1</div>
            </div>
        </div>
        
        <div id="PE2-2-H" style="position:absolute;border:1px solid #b8d0d6;left:870px;top:464px;width:10px;height:182px;background:#ffffff;">
            <div id="PE2-2" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:40px"><span id="PE2-2-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:20px">PE2-2</div>
            </div>
        </div>
        
        <div id="PE3-1-H" style="position:absolute;border:1px solid #b8d0d6;left:1130px;top:559px;width:10px;height:167px;background:#ffffff;">
            <div id="PE3-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:5px"><span id="PE3-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:-15px">PE3-1</div>
            </div>
        </div>
        
        <div id="PE3-2-H" style="position:absolute;border:1px solid #b8d0d6;left:1143px;top:563px;width:10px;height:93px;background:#ffffff;">
            <div id="PE3-2" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PE3-2-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PE3-2</div>
            </div>
        </div>
        
        <div id="PE4-1-H" style="position:absolute;border:1px solid #b8d0d6;left:1430px;top:661px;width:10px;height:65px;background:#ffffff;">
            <div id="PE4-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:0px"><span id="PE4-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:-20px">PE4-1</div>
            </div>
        </div>
        
        <div id="PE4-2-H" style="position:absolute;border:1px solid #b8d0d6;left:1443px;top:665px;width:10px;height:23px;background:#ffffff;">
            <div id="PE4-2" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PE4-2-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PE4-2</div>
            </div>
        </div>
         -->
        
        <div id="PE1-1-H" style="position:absolute;left:463px;top:531px;height:180px;background:#ffffff;">
            <div id="PE1-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:35px;bottom:20px"><span id="PE1-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:35px;bottom:0px">PE1-1</div>
            </div>
        </div>
        
        <div id="PE1-2-H" style="position:absolute;left:476px;top:531px;height:110px;background:#ffffff;">
            <div id="PE1-2" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:35px;bottom:10px"><span id="PE1-2-V" style="color:#8B0000;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:35px;bottom:-10px">PE1-2</div>
            </div>
        </div>
        
        <div id="PE2-1-H" style="position:absolute;left:857px;top:460px;height:256px;background:#ffffff;">
            <div id="PE2-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:5px"><span id="PE2-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:-15px">PE2-1</div>
            </div>
        </div>
        
        <div id="PE2-2-H" style="position:absolute;left:870px;top:464px;height:182px;background:#ffffff;">
            <div id="PE2-2" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:10px;bottom:0px"><span id="PE2-2-V" style="color:#8B0000;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:10px;bottom:-20px">PE2-2</div>
            </div>
        </div>
        
        <div id="PE3-1-H" style="position:absolute;left:1130px;top:559px;height:167px;background:#ffffff;">
            <div id="PE3-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:20px;bottom:0px"><span id="PE3-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:20px;bottom:-20px">PE3-1</div>
            </div>
        </div>
        
        <div id="PE3-2-H" style="position:absolute;left:1143px;top:563px;height:93px;background:#ffffff;">
            <div id="PE3-2" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:-10px"><span id="PE3-2-V" style="color:#8B0000;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:-30px">PE3-2</div>
            </div>
        </div>
        
        <div id="PE4-1-H" style="position:absolute;left:1430px;top:661px;height:65px;background:#ffffff;">
            <div id="PE4-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:25px;bottom:0px"><span id="PE4-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:25px;bottom:-20px">PE4-1</div>
            </div>
        </div>
        
        <div id="PE4-2-H" style="position:absolute;left:1443px;top:665px;height:23px;background:#ffffff;">
            <div id="PE4-2" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PE4-2-V" style="color:#8B0000;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PE4-2</div>
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
    		    	var show_sksw=(sksw - 211.00) / (232.56-211.00) * 214.0;
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