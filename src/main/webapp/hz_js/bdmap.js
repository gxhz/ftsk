var _mymap;
var _searchInfo;
var _flag;
var _flag1;

showbdmap = function(showdiv){
	  //var cityname = "广西壮族自治区水利厅那板水库";
	  var cityname = "广西壮族自治区上思县那包";
	  //var mapType = new BMap.MapTypeControl({mapTypes: [BMAP_HYBRID_MAP,BMAP_NORMAL_MAP]});
	  var map = new BMap.Map(showdiv, {mapType:BMAP_HYBRID_MAP});  //创建Map实例
	  map.centerAndZoom(cityname, 13);  //初始化地图,设置中心点坐标和地图级别
	  map.enableScrollWheelZoom();
	  //map.addControl(mapType);          //2D图，卫星图
	  map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
	  map.addControl(new BMap.ScaleControl());  //添加默认比例尺控件
	  map.addControl(new BMap.MapTypeControl());  //添加地图类型控件

	  //单击获取点击的经纬度
	  map.addEventListener("click",function(e){
		//alert(e.point.lng + "," + e.point.lat);
		//点击百度地图空白处，退出百度弹窗
		if((_flag1 == 'show')&&(_flag != '1')) {
		    _flag1 = 'hide';
			_searchInfo.close();
		}
		if(_flag == '1') {
			_flag = '0';
			_flag1 = 'show';
		}
	  });
	  
	  _mymap = map;

	  cityname = "广西壮族自治区上思县板嫩";

	  //创建地址解析器实例
	  var myGeo = new BMap.Geocoder();
	  //将地址解析结果显示在地图上,并调整地图视野
	  myGeo.getPoint(cityname, function(point) {
	      //if (point) {
		  	  var pt = new BMap.Point(108.030689,22.038188);
	    	  _mymap.centerAndZoom(pt, 13);  //初始化地图,设置中心点坐标和地图级别
	      //}
	  });

	  freshMap(map);
	  setInterval(function(){freshMap(map);},60*1000);
};

createOverlay = function(map, pt, showname, value, index) {
	//-----------------------------------------  自定义覆盖物  -----------------------------------------
	function ComplexCustomOverlay(point, text, value){
	      this._point = point;
	      this._text = text;
	      this._value = value;
    }
	
    ComplexCustomOverlay.prototype = new BMap.Overlay();
    ComplexCustomOverlay.prototype.initialize = function(map){
	      this._map = map;
	      var div = this._div = document.createElement("div");
	      div.style.position = "absolute";
	      div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
	      if(this._value.waterline >= 175) {
	    	  div.style.backgroundColor = "red";
		      div.style.border = "1px solid red";
	      } else {
	    	  div.style.backgroundColor = "green";
		      div.style.border = "1px solid green";
	      }
	      div.style.color = "white";
	      div.style.width = "38px";
	      div.style.height = "18px";
	      div.style.padding = "2px";
	      div.style.lineHeight = "18px";
	      div.style.whiteSpace = "nowrap";
	      div.style.MozUserSelect = "none";
	      div.style.fontSize = "12px";
		  div.style.cursor="pointer";

	      var span = this._span = document.createElement("span");
	      span.id = "showrtdata"+index;
	      span.style.textalign = "center";
	      div.appendChild(span);
	      span.appendChild(document.createTextNode(this._value));  
	      
	      var stext= document.createElement("div");
	      stext.style.position = "absolute";
	      stext.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
	      if(this._value.waterline >= 175) {
	    	  stext.style.backgroundColor = "white";
		      stext.style.border = "1px solid red";
	      } else {
	    	  stext.style.backgroundColor = "white";
		      stext.style.border = "1px solid green";
	      }
	      stext.style.color = "black";
	      stext.style.height = "18px";
	      stext.style.left = "42px";
	      stext.style.top = "-1px";
	      stext.style.padding = "2px";
	      stext.style.lineHeight = "18px";
	      stext.style.whiteSpace = "nowrap";
	      stext.style.MozUserSelect = "none";
	      stext.style.fontSize = "12px";
	      var span = this._span = document.createElement("span");
	      stext.appendChild(span);
	      span.appendChild(document.createTextNode(this._text));
	      div.appendChild(stext);
	
	      //下面的箭头
	      var arrow = this._arrow = document.createElement("div");
	      arrow.style.background = 
	    	  "url(http://map.baidu.com/fwmap/upload/r/map/fwmap/static/house/images/label.png) no-repeat";
	      arrow.style.position = "absolute";
	      arrow.style.width = "11px";
	      arrow.style.height = "10px";
	      arrow.style.top = "23px";
	      arrow.style.left = "10px";
	      arrow.style.overflow = "hidden";
	      div.appendChild(arrow);
	      
	      map.getPanes().labelPane.appendChild(div);
	      
	      return div;
    };
    ComplexCustomOverlay.prototype.draw = function(){
	      var map = this._map;
	      var pixel = map.pointToOverlayPixel(this._point);
	      this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
	      this._div.style.top  = pixel.y - 30 + "px";
    };
    ComplexCustomOverlay.prototype.addEventListener = function(event,fun){//点击事件
  	  this._div['on'+event] = fun;
    };
    
    
    
	//-----------------------------------------  复杂的自定义覆盖物  -----------------------------------------
    var content = 
		'<style type="text/css">'+
			'table.gridtable {'+
				'font-family: verdana,arial,sans-serif;'+
				'font-size:15px;'+
				'color:#333333;'+
				'border-width: 1px;'+
				'border-color: #666666;'+
				'border-collapse: collapse;'+
				'width:320px;'+
			'}'+
			'table.gridtable td {'+
				'border-width: 1px;'+
				'padding: 5px;'+
				'border-style: solid;'+
				'border-color: #666666;'+
				'background-color: #ffffff;'+
			'}'+
		'</style>'+
		'<div style="margin:0;line-height:30px;padding:2px;font-size:14px;">' +
			'位置：经度-'+ pt.lng +'，纬度-'+ pt.lat + '<br/>'+
		'</div>'+
		'<div style="margin:0;line-height:30px;padding:2px;font-size:18px;color:#3A5FCD;">'+
			'<table class="gridtable">'+
				'<tr>'+
					'<td>库容量：10.37 亿m³</td>'+
					'<td>水位：'+ value.waterline +' m</td>'+
				'</tr>'+
				'<tr>'+
					'<td> 风向：'+value.fx+'</td>'+
					'<td>风速：'+value.fs+'m/s</td>'+
				'</tr>'+	
				'<tr>'+
					'<td> 时雨量：'+ value.hourlyrainfall +' mm </td>'+
					'<td>日雨量：'+ value.daylyrainfall +' mm</td>'+
				'</tr>'+
				'<tr>'+
					'<td>蒸发量：'+ value.evaporation +' mm</td>'+
					'<td>温度：'+value.wd+'℃</td>'+
				'</tr>'+
			'</table>'+
		'</div>'+
		'<div style="margin:0;line-height:30px;padding:2px;">'+
			'<video width="320" height="240" controls="controls" autoplay="autoplay" loop="loop">'+
				'<source src="video/movie.mp4" type="video/mp4">'+
				'Your browser does not support the video tag.'+
			'</video>'+
		'</div>';
    
		    var searchInfo = new BMapLib.SearchInfoWindow(map, content, {
				title  : showname,      //标题
				width  : 323,             //宽度
				height : 455,              //高度
				panel  : "panel",         //查询结果面板
				enableAutoPan : true,     //自动平移
				searchTypes : []
			});
				
			var myCompOverlay = new ComplexCustomOverlay(pt, showname, value);
			map.addOverlay(myCompOverlay);
					
			/*
			myCompOverlay.addEventListener("click", function(){
				_flag = '1';
				searchInfo.open(new BMap.Point(pt.lng+0.007, pt.lat+0.004));
				_searchInfo = searchInfo;
			});
			*/
};

freshMap = function(map){
	$.ajax({
		type: "get",            
		url: 'GetJYLRTData',
		dataType: "json",
	    ContentType: "application/json; charset=utf-8",
		cache: true,            
		async: true,            
		success: function(data){
			//那板水库监测点
			var pt1 = new BMap.Point(data[0].jd,data[0].wd);
			var pt2 = new BMap.Point(data[1].jd,data[1].wd);
			var pt3 = new BMap.Point(data[2].jd,data[2].wd);
			var pt4 = new BMap.Point(data[3].jd,data[3].wd);
			var pt5 = new BMap.Point(data[4].jd,data[4].wd);
			var pt6 = new BMap.Point(data[5].jd,data[5].wd);
			var pt7 = new BMap.Point(data[6].jd,data[6].wd);
			var pt8 = new BMap.Point(data[7].jd,data[7].wd);
			
			createOverlay(map, pt1, data[0].name, data[0].jyl_day, 0);  //创建覆盖物
			createOverlay(map, pt2, data[1].name, data[1].jyl_day, 1);  //创建覆盖物
			createOverlay(map, pt3, data[2].name, data[2].jyl_day, 2);  //创建覆盖物
			createOverlay(map, pt4, data[3].name, data[3].jyl_day, 3);  //创建覆盖物
			createOverlay(map, pt5, data[4].name, data[4].jyl_day, 4);  //创建覆盖物
		 	createOverlay(map, pt6, data[5].name, data[5].jyl_day, 5);  //创建覆盖物
		 	createOverlay(map, pt7, data[6].name, data[6].jyl_day, 6);  //创建覆盖物
		 	createOverlay(map, pt8, data[7].name, data[7].jyl_day, 7);  //创建覆盖物
		}  
  });
};

