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

    <div style="position:absolute;border:0px solid #000000;left:80px;top:386px;width:900px;height:263px;background:#ffffff;z-index:1">
        <div id="ksw" style="position:absolute;bottom:0;width:900px;height:190px;background:#63B8FF;">
            <div id="kswstr" style="position:absolute;width: 150px;left:25px;bottom:150px">
                <span id="sw" style="color:#0000FF;">库水位：0.00 m</span>
            </div>
        </div>
    </div>
    
    <div id="wswstr" style="position:absolute;width: 150px;right:20px;bottom:320px;">
        <span id="wsw" style="color:#0000FF;">尾水位：0.00 m</span>
    </div>

    <div style="position:absolute;width:1692px;height:815px;background:url(../images/dm3_140.png);overflow:auto;z-index:2">
        <div data-options="region:'north',border:false" style="padding:3px;height:38px;text-align:center;background:#fffff">
            <span id="myspan" style="font:normal 25px '微软雅黑'; ">大坝安全 断面III 数据监控</span>
        </div>
    
        <div class="easyui-layout" style="width:1692px;height:250px">
            <table id="tt" class="easyui-datagrid" url="GetDBRTData?dbdm=3" fitColumns="true"
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
        <div id="PC1-1-H" style="position:absolute;border:1px solid #b8d0d6;left:839px;top:426px;width:10px;height:250px;background:#ffffff;">
            <div id="PC1-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:15px"><span id="PC1-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:-5px">PC1-1</div>
            </div>
        </div>
        
        <div id="PC1-2-H" style="position:absolute;border:1px solid #b8d0d6;left:852px;top:426px;width:10px;height:180px;background:#ffffff;">
            <div id="PC1-2" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PC1-2-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PC1-2</div>
            </div>
        </div>
        
        <div id="PC2-1-H" style="position:absolute;border:1px solid #b8d0d6;left:1024px;top:393px;width:10px;height:275px;background:#ffffff;">
            <div id="PC2-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:8px"><span id="PC2-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:-12px">PC2-1</div>
            </div>
        </div>
        
        <div id="PC2-2-H" style="position:absolute;border:1px solid #b8d0d6;left:1037px;top:397px;width:10px;height:216px;background:#ffffff;">
            <div id="PC2-2" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PC2-2-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PC2-2</div>
            </div>
        </div>
        
        <div id="PC3-1-H" style="position:absolute;border:1px solid #b8d0d6;left:1145px;top:436px;width:10px;height:245px;background:#ffffff;">
            <div id="PC3-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PC3-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PC3-1</div>
            </div>
        </div>
        
        <div id="PC3-2-H" style="position:absolute;border:1px solid #b8d0d6;left:1158px;top:440px;width:10px;height:185px;background:#ffffff;">
            <div id="PC3-2" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PC3-2-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PC3-2</div>
            </div>
        </div>
        
        <div id="PC4-1-H" style="position:absolute;border:1px solid #b8d0d6;left:1295px;top:489px;width:10px;height:190px;background:#ffffff;">
            <div id="PC4-1" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:20px"><span id="PC4-1-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:0px">PC4-1</div>
            </div>
        </div>
        
        <div id="PC4-2-H" style="position:absolute;border:1px solid #b8d0d6;left:1308px;top:493px;width:10px;height:143px;background:#ffffff;">
            <div id="PC4-2" style="position:absolute;bottom:0;width:10px;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:50px"><span id="PC4-2-V" style="color:#0000ff;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:30px">PC4-2</div>
            </div>
        </div>
        
         -->
         
        <div id="PC1-1-H" style="position:absolute;left:839px;top:426px;height:250px;background:#ffffff;">
            <div id="PC1-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:25px;bottom:15px"><span id="PC1-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:25px;bottom:-5px">PC1-1</div>
            </div>
        </div>
        
        <div id="PC1-2-H" style="position:absolute;left:852px;top:426px;height:180px;background:#ffffff;">
            <div id="PC1-2" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:15px;bottom:10px"><span id="PC1-2-V" style="color:#8B0000;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:15px;bottom:-10px">PC1-2</div>
            </div>
        </div>
        
        <div id="PC2-1-H" style="position:absolute;left:1024px;top:393px;height:275px;background:#ffffff;">
            <div id="PC2-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:25px;bottom:8px"><span id="PC2-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:25px;bottom:-12px">PC2-1</div>
            </div>
        </div>
        
        <div id="PC2-2-H" style="position:absolute;left:1037px;top:397px;height:216px;background:#ffffff;">
            <div id="PC2-2" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:13px;bottom:12px"><span id="PC2-2-V" style="color:#8B0000;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:13px;bottom:-8px">PC2-2</div>
            </div>
        </div>
        
        <div id="PC3-1-H" style="position:absolute;left:1145px;top:436px;height:245px;background:#ffffff;">
            <div id="PC3-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:35px;bottom:20px"><span id="PC3-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:35px;bottom:0px">PC3-1</div>
            </div>
        </div>
        
        <div id="PC3-2-H" style="position:absolute;left:1158px;top:440px;height:185px;background:#ffffff;">
            <div id="PC3-2" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:20px;bottom:15px"><span id="PC3-2-V" style="color:#8B0000;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:20px;bottom:-5px">PC3-2</div>
            </div>
        </div>
        
        <div id="PC4-1-H" style="position:absolute;left:1295px;top:489px;height:190px;background:#ffffff;">
            <div id="PC4-1" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:35px;bottom:18px"><span id="PC4-1-V" style="color:#FF00FF;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:35px;bottom:-2px">PC4-1</div>
            </div>
        </div>
        
        <div id="PC4-2-H" style="position:absolute;left:1308px;top:493px;height:143px;background:#ffffff;">
            <div id="PC4-2" style="position:absolute;bottom:0;height:0px;background:#63B8FF;">
                <div style="position:absolute;width: 150px;left:18px;bottom:43px"><span id="PC4-2-V" style="color:#8B0000;">0.00 m</span></div>
                <div style="position:absolute;width: 50px;left:18px;bottom:23px">PC4-2</div>
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
    		    	var show_sksw=(sksw - 173.00) / (232.56-173.00) * 263.0;
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