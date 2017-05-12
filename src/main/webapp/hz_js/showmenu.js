function showMenuDiv(divid,htlmname){
	var contentstr =
		'<!-- 透明层，可放图版为背景 -->'+
		'<div style="position:absolute;right:-1226px;top:105px;width:100px;z-index:1111">'+
			'<a href="javascript:void(0);" onclick="hideDiv()">'+
				'<img src="image/close.png" style="position:absolute;left:6px;top:2px;width:25px;height:25px;">'+
			'</a>'+
		'</div>'+
		
		'<!-- 显示等待界面 -->'+
		'<div id="showwaite" style="position:absolute;left:35px;top:315px;width:100px;">'+
			'<img src="image/loadwaite.png">'+
		'</div>'+
		
		'<!-- 自动控制显示 -->'+
		'<div style="position:absolute;left:5px;top:105px;">'+
			'<iframe frameborder="0" scrolling="no" width="1155px" height="583px" src="'+htlmname+'"></iframe>'+
			
		'</div>'+
		
		'<!-- 隐藏等待界面 -->'+
		'<script type="text/javascript">'+
			'$("#showwaite").hide();'+
		'</script>';
	
	document.getElementById(divid).innerHTML = contentstr;
}