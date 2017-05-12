<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="../head1.jsp" %>  

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>竖向位移量平面等值线图</title>

</head>

<body>

    <div style="position:absolute;width:1300px;height:1050;overflow:auto;z-index:2">
        <!-- 
        <div data-options="region:'north',border:false" style="padding:3px;height:38px;text-align:center;background:#fffff">
            <span id="myspan" style="font:normal 25px '微软雅黑'; ">竖向位移量平面等值线图</span>
        </div>
         -->
    
        <canvas id="canvas" width="1250" height="1000px" style="z-index:999" >
    </div>
    
    <div class="easyui-layout" style="position: fixed;width:300px;height:800px;left:1300px;">
        <table id="tt" class="easyui-datagrid" pagination="false" rownumbers="false" singleSelect="true" pageSize="200" fit="true">
            <thead>
                <tr>
                    <td align="left" style="padding-top:5px;">数据类型：</td>
                    <td align="left" style="padding-top:5px;">
                        <input id="dtype" class="easyui-combobox" style="width:120px;height:28px"
                            data-options="
                                    url: '../jsonfile/bxdatatype.json',
                                    method:'get',
                                    valueField:'value',
                                    textField:'text',
                                    panelHeight:'auto',
                                    editable:false,
                                    onSelect:onSelect
                            ">
                    </td>
                </tr>
                <tr>
                  <th field="name" width="120">观测点</th>
                  <th field="zoffset" width="120">竖向位移量</th>
                </tr>
            </thead>
        </table>
    </div>
    
    <div style="position: fixed;left:0px;right:800px;bottom:80px;z-index: 888;padding: 5px;margin-left:auto;margin-right:auto;width:800px">
        <table align="left"  border="0" cellpadding="0" cellspacing="0" style="padding:5px;">
            <tr>
                <td>日期选择：</td>
                <td>
                    <input id="sdate" name="sdate" data-options="editable:false,onSelect:onSelect" 
                        class="easyui-datebox" required style="width:120px;height:28px">
                </td>
            </tr>
        </table>
    </div>

</body>

<script type="text/javascript">

    //得到当前日期
    formatterDate = function(date) {
        var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
        var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
        + (date.getMonth() + 1);
        return date.getFullYear() + '-' + month + '-' + day;
    };

    var bgImg = new Image();
    //初始化界面数据
    window.onload = function () { 
    	$('#sdate').datebox('setValue', formatterDate(new Date()));
    	$('#dtype').combobox('setValue', 'bx_his_wyl');
    	
    	var ctx = canvas.getContext("2d");
		bgImg.src = "../images/sxwy/sxwy.jpg";
		bgImg.onload = function(){
			ctx.drawImage(bgImg, 0, 0);
			getData();
		};
    };
    
    //日期选择事件
    function onSelect(date){
    	getData();  //获取数据并显示
	}
    
    //观测点阵列相对位置数据
    var watchPointPosition = [
          [-1, 2.5], [0, 2.5], [1, 2.5],
          [-1, 1.5], [0, 1.5], [1, 1.5],
          [-1, 0.5], [0, 0.5], [1, 0.5],
          [-1, -0.5], [0, -0.5], [1, -0.5],
          [-1, -1.5], [0, -1.5], [1, -1.5],
          [-1, -2.5], [0, -2.5], [1, -2.5],
          //山上观测站
          [-1, -3.5], [0, -3.5], [1, -3.5],
          [-1, -4.5], [0, -4.5], [1, -4.5],
          [-1, -5.5], [0, -5.5], [1, -5.5],
    ];
    //同心圆每个圆增加的半径
    var deltaRadius = 35;
    //圆心
    var circleCenter = [620, 453];
    
    //获取数据并显示
    function getData() {
		var sdate,dtype;
		
		sdate = $('#sdate').datebox('getValue');
		dtype = $('#dtype').combobox('getValue');
		
		if(sdate == '') {
        	$.messager.alert('系统提示','请选择日期!','error');
        	return;
        }
		if(dtype == '') {
			$.messager.alert('系统提示','请选择数据类型!','error');
			return;
		}
		
		//清除画布
		var ctx = canvas.getContext("2d");
		ctx.clearRect(0,0,canvas.width,canvas.height);
		ctx.drawImage(bgImg, 0, 0);
		 $.ajax({
			 type:'post',
             dataType:'json',
             data:{sdate:sdate,dtype:dtype},
             url:'GetSXWYDCXData.action',
			 success:function(data){
				var wv=[];
				
				for(var i=0;i<27;i++) wv[i] = 0.0;
				
				//赋值顺序：W11,W12,W13,W21,W22,W23,W31,W32,W33,W41,W42,W43,W51,W52,W53,W61,W62,W63,WA1,WA2,WA3,WB1,WB2,WB3,WC1,WC2,WC3
				for(var i=0;i<data.length;i++) {
					var name=data[i].name;
					
					if(name == 'W11') wv[0] = data[i].zoffset;
					if(name == 'W12') wv[1] = data[i].zoffset;
					if(name == 'W13') wv[2] = data[i].zoffset;
					
					if(name == 'W21') wv[3] = data[i].zoffset;
					if(name == 'W22') wv[4] = data[i].zoffset;
					if(name == 'W23') wv[5] = data[i].zoffset;
					
					if(name == 'W31') wv[6] = data[i].zoffset;
					if(name == 'W32') wv[7] = data[i].zoffset;
					if(name == 'W33') wv[8] = data[i].zoffset;
					
					if(name == 'W41') wv[9] = data[i].zoffset;
					if(name == 'W42') wv[10] = data[i].zoffset;
					if(name == 'W43') wv[11] = data[i].zoffset;
					
					if(name == 'W51') wv[12] = data[i].zoffset;
					if(name == 'W52') wv[13] = data[i].zoffset;
					if(name == 'W53') wv[14] = data[i].zoffset;
					
					if(name == 'W61') wv[15] = data[i].zoffset;
					if(name == 'W62') wv[16] = data[i].zoffset;
					if(name == 'W63') wv[17] = data[i].zoffset;
					
					if(name == 'WA1') wv[18] = data[i].zoffset;
					if(name == 'WA2') wv[19] = data[i].zoffset;
					if(name == 'WA3') wv[20] = data[i].zoffset;
					
					if(name == 'WB1') wv[21] = data[i].zoffset;
					if(name == 'WB2') wv[22] = data[i].zoffset;
					if(name == 'WB3') wv[23] = data[i].zoffset;
					
					if(name == 'WC1') wv[24] = data[i].zoffset;
					if(name == 'WC2') wv[25] = data[i].zoffset;
					if(name == 'WC3') wv[26] = data[i].zoffset;
					
				}
				
				//画线或画点
				if(data.length > 0) {
					drawSXWY(canvas, wv, watchPointPosition, circleCenter, deltaRadius, 0.3);
					//表格填数据
					var dataRow = [];
					for(var i = 0; i < 27; i++)
					{
						var name = "W" + parseInt(i / 3 + 1) + (i % 3 + 1);
						if(parseInt(i / 3 + 1) == 7)
						{
							name = "WA" + (i % 3 + 1);
						}
						else if(parseInt(i / 3 + 1) == 8)
						{
							name = "WB" + (i % 3 + 1);
						}
						else if(parseInt(i / 3 + 1) == 9)
						{
							name = "WC" + (i % 3 + 1);
						}
						dataRow[i] = {"name":name, "zoffset":wv[i]+" mm"};
					}
					$('#tt').datagrid('loadData',{total:27,rows:dataRow});
				}
				else
				{
					//清空表格数据
					$('#tt').datagrid('loadData',{total:0,rows:[]});
				}
			 }
         });

	}
    
</script>

</html>