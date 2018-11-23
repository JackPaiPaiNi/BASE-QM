<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/views/index/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>国内营销总部分公司业绩情况</title>
<jsp:include page="/WEB-INF/views/index/js.bootstrap.jsp" />
<!-- 引入 echarts.js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/echarts.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/china-sky.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/theme/dark.js"></script>
<style type="text/css">
body{
	background:url(${pageContext.request.contextPath}/css/images/dpbg.jpg);
	background-size:cover;
	/* background: rgba(7,34,93,0.96); */
	overflow: hidden;
	color: #fff;
}
.c1{
	height: 250px;
}
.btn-group .active{
	font-weight: bold;
}
.btn:not(.active){
	color: #000;
}
</style>
</head>
<body>
	<input id="qj" type="hidden" />
	<input id="qjlx" type="hidden" value="当月"/>
	<input id="sjly" type="hidden" value="签收"/>
	<input id="fgs" type="hidden" value="整体"/>
	<div class="row">
		<div class="col-md-7 col-sm-7">
			<div class="row">
				<div id="dc1" class="col-md-3 col-sm-3 c1"></div>
				<div id="dc2" class="col-md-3 col-sm-3 c1"></div>
				<div id="dc3" class="col-md-3 col-sm-3 c1"></div>
				<div class="col-md-3 col-sm-3 text-center">
					<h2>国内营销总部</h2>
					<h2 class=" m-t-xs">分公司业绩情况</h2>
					<p>数据截止时间：<label id="label_qj"></label>&nbsp;24:00</p>
					<div class="btn-group">
						<button class="btn btn-xs btn-success active" onclick="javascript:changeSjly(this,'签收');">签收</button>
						<button class="btn btn-xs btn-success" onclick="javascript:changeSjly(this,'出库');">出库</button>
						<button class="btn btn-xs btn-success" onclick="javascript:changeSjly(this,'零售');">零售</button>
					</div>
					<div class="btn-group">
						<button class="btn btn-xs btn-info" onclick="javascript:changeQjlx(this,'当日');">当日</button>
						<button class="btn btn-xs btn-info active" onclick="javascript:changeQjlx(this,'当月');">累计</button>
					</div>
					<div class="btn-group m-t-xl">
						<button class="btn btn-xs btn-warning active" onclick="javascript:mapDataChange(this,1);">销量达成</button>
						<button class="btn btn-xs btn-warning" onclick="javascript:mapDataChange(this,2);">规模达成</button>
						<button class="btn btn-xs btn-warning" onclick="javascript:mapDataChange(this,3);">大Q6任务达成</button>
					</div>
					<h1 id="label_fgs" class="text-warning font-bold">整体</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div id="map" style="width: 100%; height: 780px;"></div>
				</div>
			</div>
		</div>
		<div class="col-md-5 col-sm-5">
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div id="chart1" style="width: 100%; height: 230px;"></div>
				</div>
			</div>
			<div class="row m-t-none">
				<div class="col-md-12 col-sm-12">
					<div id="chart2" style="width: 100%; height: 230px;"></div>
				</div>
			</div>
			<div class="row m-t-none">
				<div class="col-md-12 col-sm-12">
					<div id="chart3" style="width: 100%; height: 230px;"></div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<h3 style="width: 86%; margin-left: 7%; margin-right: 7%; text-align: center;"><b>渠道结构</b></h3>
					<table id="dt" class="table" style="width: 86%; height: 100%; margin-left: 7%; margin-right: 7%; color: #fff;">
						<tr>
							<td><b>渠道</b></td>
							<td><b>渠道销售额</b></td>
							<td><b>同比增长率</b></td>
							<td><b>累计毛利率</b></td>
							<td><b>同比增减</b></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var arrayXL = null;
var arrayXE = null;
var arrayDQ6 = null;

var dcChart1 = echarts.init(document.getElementById('dc1'),'dark');
var dcChart2 = echarts.init(document.getElementById('dc2'),'dark');
var dcChart3 = echarts.init(document.getElementById('dc3'),'dark');

var mapChart = echarts.init(document.getElementById('map'),'dark');

var chart1 = echarts.init(document.getElementById('chart1'),'dark');
var chart2 = echarts.init(document.getElementById('chart2'),'dark');
var chart3 = echarts.init(document.getElementById('chart3'),'dark');

$(function(){
	// 初始化期间
	initDate();
	// 取地图数据
	loadMapData();
	// 地图点击事件
	mapChart.on('click', function(param){
		$("#fgs").val(param.data.name);
		$("#label_fgs").html(param.data.name);
		loadData();
	});
	// 分公司图表取数
	loadData();
});

function initDate(){
	var qjlx = $("#qjlx").val();
	var dateNow = new Date();
	var year = dateNow.getFullYear();    //获取完整的年份(4位,1970-????)
	var month = dateNow.getMonth() + 1;       //获取当前月份(0-11,0代表1月)
	var day = dateNow.getDate() - 1;        //获取当前日(1-31)的前一天
	if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (day >= 0 && day <= 9) {
    	day = "0" + day;
    }
	if(qjlx == "当月"){
		$("#qj").val(year + "" + month);
	}else if(qjlx == "当日"){
		$("#qj").val(year + "" + month + "" + day);
	}
	$("#label_qj").html(year+"年"+month+"月"+day+"日");
}

// 初始化图表通用方法
function loadData(){
	var _index = layer.load(1, {
		shade: [0.5,'#fff'] //0.1透明度的白色背景
	});
	
	var qj = $("#qj").val();
	var qjlx = $("#qjlx").val();
	var sjly = $("#sjly").val();
	var fgs = $("#fgs").val();
	$.post("<c:url value='/dashboard/dp/searchFgs'/>", {qjlx:qjlx,qj:qj,sjly:sjly,fgs:fgs}, function(result){
		// 指针
		dcChart1.setOption(initGaugeOption(result.listWCL[0].zb, result.listWCL[0].ys, result.listWCL[0].sj, result.listWCL[0].pm, result.listWCL[0].wcl));
		dcChart2.setOption(initGaugeOption(result.listWCL[1].zb, result.listWCL[1].ys, result.listWCL[1].sj, result.listWCL[1].pm, result.listWCL[1].wcl));
		dcChart3.setOption(initGaugeOption(result.listWCL[2].zb, result.listWCL[2].ys, result.listWCL[2].sj, result.listWCL[2].pm, result.listWCL[2].wcl));
		
		chart1.setOption(initAxisOption1("收入", "毛利率", result.listQsX, result.listQsXe, result.listQsMll));
		chart2.setOption(initAxisOption2("销量", "均价", result.listQsX, result.listQsXl, result.listQsJj));
		chart3.setOption(initStackAxisOption(result.listXsjgX, "销量", "占比", result.listXsjgWc, result.listXsjgCj, result.listXsjgZb));
		loadTable(result.listQDJG);
		
		layer.close(_index);
    },"json");
}

// 初始化地图通用方法
function loadMapData(){
	var _index = layer.load(1, {
		shade: [0.5,'#fff'] //0.1透明度的白色背景
	});
	var qj = $("#qj").val();
	var qjlx = $("#qjlx").val();
	var sjly = $("#sjly").val();
	$.post("<c:url value='/dashboard/dp/searchMap'/>", {qjlx:qjlx,qj:qj,sjly:sjly}, function(result){
		arrayXL = []; arrayXE = []; arrayDQ6 = [];
		$.each(result.listXL, function(i,n){
			var tempObj = {};
			tempObj.name = n.fgs;
			tempObj.value = n.pm;
			arrayXL.push(tempObj);
		});
		$.each(result.listXE, function(i,n){
			var tempObj = {};
			tempObj.name = n.fgs;
			tempObj.value = n.pm;
			arrayXE.push(tempObj);
		});
		$.each(result.listDQ6, function(i,n){
			var tempObj = {};
			tempObj.name = n.fgs;
			tempObj.value = n.pm;
			arrayDQ6.push(tempObj);
		});
		mapChart.setOption(initMapOption(arrayXL));
		
		layer.close(_index);
    },"json");
}

// 数据来源change事件
function changeSjly(el, sjly){
	var _this = $(el);
	if(!_this.hasClass("active")){
		_this.siblings(".active").removeClass("active");
		_this.addClass("active");
		//取数
		$("#sjly").val(sjly);
		loadData();
		loadMapData();
	}
}

// 期间类型change事件
function changeQjlx(el, qjlx){
	var _this = $(el);
	if(!_this.hasClass("active")){
		_this.siblings(".active").removeClass("active");
		_this.addClass("active");
		//取数
		$("#qjlx").val(qjlx);
		initDate();
		loadData();
		loadMapData();
	}
}

// 地图指标change事件
function mapDataChange(el, type){
	var _this = $(el);
	if(!_this.hasClass("active")){
		_this.siblings(".active").removeClass("active");
		_this.addClass("active");
		//取数
		if(type == 1){
			mapChart.setOption(initMapOption(arrayXL));
		}else if(type == 2){
			mapChart.setOption(initMapOption(arrayXE));
		}else if(type == 3){
			mapChart.setOption(initMapOption(arrayDQ6));
		}
	}
}

// 表格数据加载
function loadTable(data) {
	clearTable();
	if (typeof (data) != "object") {
		return;
	}
	if (data == null || data == undefined) {
		return;
	}
	var bgColorClass;
	var dt = document.getElementById("dt");
	if($("#qjlx").val() == "当日"){
		dt.innerHTML = '<tr style="background-color: #759aa0; color: white;">' +
							'<td><b>渠道</b></td>' +
							'<td><b>销售额（万元）</b></td>' +
							'<td><b>渠道占比</b></td>' +
							'<td><b>毛利率</b></td>' +
							'<td><b>累计销售额（万元）</b></td>' +
							'<td><b>累计渠道占比</b></td>' +
							'<td><b>累计毛利率</b></td>' +
						'</tr>';
	} else if($("#qjlx").val() == "当月"){
		dt.innerHTML = '<tr style="background-color: #759aa0; color: white;">' +
							'<td><b>渠道</b></td>' +
							'<td><b>销售额（万元）</b></td>' +
							'<td><b>同比增长率</b></td>' +
							'<td><b>毛利率</b></td>' +
							'<td><b>同比增减</b></td>' +
						'</tr>';
	}
	for (var i = 0, length = data.length; i < length; i++) {
		if (i % 2 == 0) {
			bgColorClass = "DoubleRow";
		} else {
			bgColorClass = "SingleRow";
		}
		var newTr = dt.insertRow();
		newTr.className = bgColorClass;
		for (var temp in data[i]) {
			if ($("#qjlx").val() == "当日" && (temp == "qd" || temp == "xsje" || temp == "xsjezb" || temp == "mll" || temp == "ljxsje" || temp == "ljxsjezb" || temp == "ljmll") 
					|| ($("#qjlx").val() == "当月" && (temp == "qd" || temp == "xsje" || temp == "xsjetbzll" || temp == "mll" || temp == "mlltbzj"))) {
				var newTd = newTr.insertCell();
				var bfb = "";
				if (temp == "xsjezb" || temp == "mll" || temp == "ljxsjezb" || temp == "ljmll" || temp == "xsjetbzll" || temp == "mll" || temp == "mlltbzj") {
					bfb = "%";
				}
				newTd.innerHTML = data[i][temp] + bfb;
			}
		}
	}
}

// 表格清空
function clearTable() {
	var dt = document.getElementById("dt");
	for (var i = 0, length = dt.rows.length; i < length; i++) {
		dt.deleteRow(0);
	}
}

//驾驶舱指针图表
function initGaugeOption(name, mb, sj, qgpm, dcl){
	return {
	    tooltip : {
	        formatter: "达成率：{c}%"
	    },
	    series: [{
	        name: name,
	        type: 'gauge',
	        splitNumber: 5,
	        axisLine: { // 坐标轴线
	            lineStyle: { // 属性lineStyle控制线条样式
	                color: [
	                    [0.5, '#7bd9a5'],
	                    [0.8, '#3fb1e3'],
	                    [1, '#d95850']
	                ],
	                width: 5,
	                shadowBlur: 8
	            }
	        },
	        axisLabel: { // 坐标轴小标记
	            textStyle: { // 属性lineStyle控制线条样式
	                fontWeight: 'bolder',
	                color: '#fff',
	                shadowBlur: 8
	            }
	        },
	        axisTick: { // 坐标轴小标记
	            length: 8, // 属性length控制线长
	            lineStyle: { // 属性lineStyle控制线条样式
	                color: 'auto',
	                shadowBlur: 8
	            }
	        },
	        splitLine: { // 分隔线
	            length: 13, // 属性length控制线长
	            lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
	                width: 3,
	                color: '#fff',
	                shadowBlur: 8
				}
	        },
	        pointer: { // 分隔线
	            shadowBlur: 5
	        },
	        title: {
	            textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                fontWeight: 'bolder',
	                fontStyle: 'italic',
	                color: '#fff',
	                shadowBlur: 8
	            }
	        },
	        detail: {
	        	offsetCenter: [0, 85],
	        	formatter: ['{t|'+dcl+'%}', '{a|目标：'+mb+'}', '{b|实际：'+sj+'}', '{c|全国排名：'+qgpm+'}' ].join('\n'),
	   	        rich: {
	   	            t: {
	   	                fontSize: 14,
	   	                fontWeight: 'bolder',
	   	                color:'#fff',
	   	                padding: 5
	   	            },
	   	            a: {
	   	                lineHeight: 18,
	   	                color:'#fff',
	   	                padding: 3
	   	            },
	   	            b: {
	   	                lineHeight: 18,
	   	                color:'#fff',
	   	                padding: 3
	   	            },
	   	            c: {
	   	                lineHeight: 18,
	   	                color:'#fff',
	   	                padding: 3
	   	            }
	   	        }
	        },
	        data: [{value: dcl, name: name}]
	    }]
	};
}

// 柱状、折线图表
function initAxisOption1(name1, name2, x, data1, data2){
	return {
		title: {
	        text: '收入&毛利率走势',
	        x:'center'
	    },
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    xAxis: {
            data: x
        },
	    yAxis: [
	        {
	            type: 'value',
	            name: name1,
		        splitLine: {
		            show: false
		        }
	        },
	        {
	            type: 'value',
	            name: name2,
		        splitLine: {
		            show: false
		        }
	        }
	    ],
	    series: [
	        {
	            name:name1,
	            type:'bar',
	            smooth: true,
	            data:data1
	        },
	        {
	            name:name2,
	            type:'line',
	            smooth: true,
	            yAxisIndex: 1,
	            label: {
	                normal: {
	                    show: true,
	                    position: 'top'
	                }
	            },
	            data:data2
	        }
	    ]
	};
}

function initAxisOption2(name1, name2, x, data1, data2){
	return {
		title: {
	        text: '销量&均价走势',
	        x:'center'
	    },
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    xAxis: {
            data: x
        },
	    yAxis: [
	        {
	            type: 'value',
	            name: name1,
		        splitLine: {
		            show: false
		        }
	        },
	        {
	            type: 'value',
	            name: name2,
		        splitLine: {
		            show: false
		        }
	        }
	    ],
	    series: [
	        {
	            name:name1,
	            type:'bar',
	            smooth: true,
	            label: {
	                normal: {
	                    show: true,
	                    position: 'top'
	                }
	            },
	            data:data1
	        },
	        {
	            name:name2,
	            type:'line',
	            smooth: true,
	            yAxisIndex: 1,
	            data:data2
	        }
	    ]
	};
}

// 堆叠柱状图
function initStackAxisOption(x, name1, name2, data1, data2, data3){
	return {
		title: {
	        text: '销售结构',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    xAxis : {
            data : x
        },
	    yAxis : [
 	        {
	            type: 'value',
	            name: name1,
		        splitLine: {
		            show: false
		        }
	        },
	        {
	            type: 'value',
	            name: name2,
		        splitLine: {
		            show: false
		        }
	        }
	    ],
	    series : [
	        {
	            name:'完成（台）',
	            type:'bar',
	            stack: 'stack',
	            data:data1
	        },
	        {
	            name:'差距（台）',
	            type:'bar',
	            stack: 'stack',
	            data:data2
	        },
	        {
	            name:'占比',
	            type:'line',
	            smooth: true,
	            yAxisIndex: 1,
	            data:data3
	        }
	    ]
	};
}

function initMapOption(data){
	return {
        tooltip: {
            trigger: 'item',
            formatter: '{b}分公司<br/>排名：{c}'
        },
        visualMap: {
            min: 1,
            max: 35,
            text:['高','低'],
            realtime: false,
            calculable: false,
            show : false/*,
            inRange: {
                color: ['orangered', 'yellow', 'lightskyblue']
            }*/
        },
		series : [{
			type: 'map',
	        mapType: 'china',
			zoom: 1.2,
			roam: true,
			scaleLimit: {min:0.8, max:1.5},
			selectedMode: 'single',
			label: {
                normal: {
                    show: true,//显示省份标签
                    textStyle: {color:"#c71585"}//省份标签字体颜色
                },    
                emphasis: {//对应的鼠标悬浮效果
                    show: true,
                    textStyle: {color:"#800080"}
                } 
    		},
            itemStyle: {
                normal: {
                    borderWidth: 0,//区域边框宽度
                    //borderColor: '#009fe8',//区域边框颜色
                    //areaColor: "#87CEFA",//区域颜色
                },
                emphasis: {
                    borderWidth: .5,
                    borderColor: 'Grey',
                    areaColor:"Grey",
                }
            },
            data: data
    	}]
	};
}
</script>
</html>