//弹出加载层
function load(msg) {  
    $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");  
    $("<div class=\"datagrid-mask-msg\"></div>").html(msg).appendTo("body").css({ display: "block", 
    	left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });  
}  
  
//取消加载层  
function disload() {  
    $(".datagrid-mask").remove();  
    $(".datagrid-mask-msg").remove();  
}

//根据左边菜单栏显示相应页面
function openTab(qxid,url){
	$('#myframe').attr('src',url);
	
	/*
	//操作权限限制功能
	$.ajax({
        type:'post',
        dataType:'json',
        data:{qxid: qxid},
        url:'GetZCQX.action',
        success:function(data) {
        	if(data.success) {
        		$('#myframe').attr('src',url);
        	} else {
        		$.messager.show({
        			title: '系统提示',
        			msg: '没有操作权限，请联系管理员！',
        			timeout: 5000,
        			showType: 'slide'
        		});
        	}
    	}
    });
    */
}

//得到当前日期
formatterDate = function(date) {
    var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
    var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
    + (date.getMonth() + 1);
    return date.getFullYear() + '-' + month + '-' + day;
};

