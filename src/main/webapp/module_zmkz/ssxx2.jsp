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
    <div style="position:absolute;width:1692px;height:815px;background:url(../images/zmjk.jpg);overflow:auto;">
        <div style="position:absolute;width:99%;height:25px;text-align:center;padding: 5px">
            <span style="font:normal 25px '宋体'; color:#ff0000;line-height:25px;text-align:center; ">2#闸门</span>
        </div>
        
        <div style="position:absolute;left:260px;top:80px;">
            <span style="font:normal 25px '宋体'; color:#ffff00;">2#工作闸门</span><br>
            <span id="gzycjd1" style="font:normal 25px '宋体'; color:#ffff00;">远程</span>&nbsp;
            <span id="gzycjd2" style="font:normal 25px '宋体'; color:#ff0000;">就地</span>
            <div id="gzycstate" style="position:absolute;left:20px;width:100px;height:100px;background:url(../images/yc.png);overflow:auto;"></div>
        </div>
        
        <div style="position:absolute;left:1350px;top:80px;">
            <span style="font:normal 25px '宋体'; color:#ffff00;">2#检修闸门</span><br>
            <span id="jxycjd1" style="font:normal 25px '宋体'; color:#ffff00;">远程</span>&nbsp;
            <span id="jxycjd2" style="font:normal 25px '宋体'; color:#ff0000;">就地</span>
            <div id="jxycstate" style="position:absolute;left:20px;width:100px;height:100px;background:url(../images/yc.png);overflow:auto;"></div>
        </div>
        
        <div style="position:absolute;left:700px;top:120px;">
            <span style="font:normal 25px '宋体'; color:#ffff00;">2#工作闸门</span>
        </div>
        
        <div style="position:absolute;left:860px;top:120px;">
            <span style="font:normal 25px '宋体'; color:#ffff00;">2#检修闸门</span>
        </div>
        
        <div style="position:absolute;left:160px;top:320px;">
            <span style="font:normal 25px '宋体'; color:#ffff00;">启动/停止状态</span>&nbsp;
            <div id="gzss" style="width:20px;height:20px;background:#00ff00;display:-moz-inline-box; display:inline-block;"></div>
        </div>
        
        <div style="position:absolute;left:160px;top:380px;">
            <span style="font:normal 25px '宋体'; color:#ffff00;">上升/停止状态</span>&nbsp;
            <div id="gzxj" style="top:8px;width:20px;height:20px;background:#00ff00;display:-moz-inline-box; display:inline-block;"></div>
        </div>
        
        <div style="position:absolute;left:160px;top:440px;">
            <span style="font:normal 25px '宋体'; color:#ffff00;">下降/停止状态</span>&nbsp;
            <div id="gztz" style="top:8px;width:20px;height:20px;background:#00ff00;display:-moz-inline-box; display:inline-block;"></div>
        </div>
        
        <div style="position:absolute;left:272px;top:560px;">
            <span style="font:normal 25px '宋体'; color:#ffff00;">上限</span>&nbsp;
            <div id="gzsx" style="top:8px;width:20px;height:20px;background:#00ff00;display:-moz-inline-box; display:inline-block;"></div>
        </div>
        
        <div style="position:absolute;left:272px;top:620px;">
            <span style="font:normal 25px '宋体'; color:#ffff00;">下限</span>&nbsp;
            <div id="gzxx" style="top:8px;width:20px;height:20px;background:#00ff00;display:-moz-inline-box; display:inline-block;"></div>
        </div>
        
        <div style="position:absolute;left:600px;top:680px;">
            <span style="font:normal 25px '宋体'; color:#ffff00;">闸位：</span>
            <span id="gzzw" style="font:normal 25px '宋体'; color:#ffffff; display:-moz-inline-box; display:inline-block; width:80px; text-align:right;">0.0</span>
            <span style="font:normal 25px '宋体'; color:#ff00ff;">%</span>
        </div>
        
        <div style="position:absolute;left:930px;top:680px;">
            <span style="font:normal 25px '宋体'; color:#ffff00;">闸位：</span>
            <span id="jxzw" style="font:normal 25px '宋体'; color:#ffffff; display:-moz-inline-box; display:inline-block; width:80px; text-align:right;">0.0</span>
            <span style="font:normal 25px '宋体'; color:#ff00ff;">%</span>
        </div>
        
        <div style="position:absolute;left:1250px;top:320px;">
            <span style="font:normal 25px '宋体'; color:#ffff00;">启动/停止状态</span>&nbsp;
            <div id="jxss" style="top:18px;width:20px;height:20px;background:#00ff00;display:-moz-inline-box; display:inline-block;"></div>
        </div>
        
        <div style="position:absolute;left:1250px;top:380px;">
            <span style="font:normal 25px '宋体'; color:#ffff00;">上升/停止状态</span>&nbsp;
            <div id="jxxj" style="top:8px;width:20px;height:20px;background:#00ff00;display:-moz-inline-box; display:inline-block;"></div>
        </div>
        
        <div style="position:absolute;left:1250px;top:440px;">
            <span style="font:normal 25px '宋体'; color:#ffff00;">下降/停止状态</span>&nbsp;
            <div id="jxtz" style="top:8px;width:20px;height:20px;background:#00ff00;display:-moz-inline-box; display:inline-block;"></div>
        </div>
        
        <div style="position:absolute;left:1362px;top:560px;">
            <span style="font:normal 25px '宋体'; color:#ffff00;">上限</span>&nbsp;
            <div id="jxsx" style="top:8px;width:20px;height:20px;background:#00ff00;display:-moz-inline-box; display:inline-block;"></div>
        </div>
        
        <div style="position:absolute;left:1362px;top:620px;">
            <span style="font:normal 25px '宋体'; color:#ffff00;">下限</span>&nbsp;
            <div id="jxxx" style="top:8px;width:20px;height:20px;background:#00ff00;display:-moz-inline-box; display:inline-block;"></div>
        </div>
        
        
        
        <div style="position:absolute;border:1px solid #b8d0d6;left:700px;top:465px;width:118px;height:165px;background:#969696;">
            <div id="gzzwdiv" style="position:absolute;bottom:0;width:118px;height:0px;background:#ffff00;">
            </div>
        </div>
        
        <div style="position:absolute;border:1px solid #b8d0d6;left:871px;top:445px;width:118px;height:155px;background:#969696;">
            <div id="jxzwdiv" style="position:absolute;bottom:0;width:118px;height:0px;background:#ffff00;">
            </div>
        </div>
        
    </div>
          
</body>

<script type="text/javascript">

    $.ajax({
    	type: 'post',
    	url: 'GetZMMsg',
    	dataType: 'json',
        ContentType: 'application/json; charset=utf-8',
    	cache: true,
    	async: true,
    	data: {'zmindex':'2'},
    	success:function(data){
    		if(data.length > 0) {
    			//工作闸门远程/就地
    			if(data[0].gzzmycjd == 0) {  //远程
    				document.getElementById('gzycjd1').style.color = "#ff0000";
    				document.getElementById('gzycjd2').style.color = "#ffff00";
    				document.getElementById('gzycstate').style.background = "url(../images/yc.png)";
    			} else {  //就地
    				document.getElementById('gzycjd1').style.color = "#ffff00";
    				document.getElementById('gzycjd2').style.color = "#ff0000";
    				document.getElementById('gzycstate').style.background = "url(../images/jd.png)";
    			}
    			
    			//工作闸门启动/停止状态
    			if(data[0].gzzmqt == 0) {  //启止
    				document.getElementById('gzss').style.background = "#ff0000";
    			} else {  //停止
    				document.getElementById('gzss').style.background = "#00ff00";
    			}
    			
    			//工作闸门上升/停止状态
    			if(data[0].gzzmss == 0) {  //上升
    				document.getElementById('gzxj').style.background = "#ff0000";
    			} else {  //停止
    				document.getElementById('gzxj').style.background = "#00ff00";
    			}
    			
    			//工作闸门上降/停止状态
    			if(data[0].gzzmxj == 0) {  //下降
    				document.getElementById('gztz').style.background = "#ff0000";
    			} else {  //停止
    				document.getElementById('gztz').style.background = "#00ff00";
    			}
    			
    			//工作闸门上限
    			if(data[0].gzzmsx == 0) {  //上限
    				document.getElementById('gzsx').style.background = "#ff0000";
    			} else {
    				document.getElementById('gzsx').style.background = "#00ff00";
    			}
    			
    			//工作闸门下限
    			if(data[0].gzzmxx == 0) {  //下限
    				document.getElementById('gzxx').style.background = "#ff0000";
    			} else {
    				document.getElementById('gzxx').style.background = "#00ff00";
    			}
    			
    			document.getElementById('gzzw').innerHTML = data[0].gzzw;  //工作闸闸位
    			document.getElementById('jxzw').innerHTML = data[0].jxzw;  //检修闸闸位
    			document.getElementById('gzzwdiv').style.height = (data[0].gzzw / 100.0 * 165.0)+'px';
    			document.getElementById('jxzwdiv').style.height = (data[0].jxzw / 100.0 * 155.0)+'px';
    			
    			//检修闸门远程/就地
    			if(data[0].jxzmycjd == 0) {  //远程
    				document.getElementById('jxycjd1').style.color = "#ff0000";
    				document.getElementById('jxycjd2').style.color = "#ffff00";
    				document.getElementById('jxycstate').style.background = "url(../images/yc.png)";
    			} else {  //就地
    				document.getElementById('jxycjd1').style.color = "#ffff00";
    				document.getElementById('jxycjd2').style.color = "#ff0000";
    				document.getElementById('jxycstate').style.background = "url(../images/jd.png)";
    			}
    			
    			//检修闸门启动/停止状态
    			if(data[0].jxzmqt == 0) {  //启动
    				document.getElementById('jxss').style.background = "#ff0000";
    			} else {  //停止
    				document.getElementById('jxss').style.background = "#00ff00";
    			}
    			
    			//检修闸门上升/停止状态
    			if(data[0].jxzmss == 0) {  //上升
    				document.getElementById('jxxj').style.background = "#ff0000";
    			} else {  //停止
    				document.getElementById('jxxj').style.background = "#00ff00";
    			}
    			
    			//检修闸门下降/停止状态
    			if(data[0].jxzmxj == 0) {  //下降
    				document.getElementById('jxtz').style.background = "#ff0000";
    			} else {  //停止
    				document.getElementById('jxtz').style.background = "#00ff00";
    			}
    			
    			//检修闸门上限
    			if(data[0].jxzmsx == 0) {  //上限
    				document.getElementById('jxsx').style.background = "#ff0000";
    			} else {
    				document.getElementById('jxsx').style.background = "#00ff00";
    			}
    			
    			//检修闸门下限
    			if(data[0].jxzmxx == 0) {  //下限
    				document.getElementById('jxxx').style.background = "#ff0000";
    			} else {
    				document.getElementById('jxxx').style.background = "#00ff00";
    			}
    			
    		}
    	}
	});

</script>

</html>