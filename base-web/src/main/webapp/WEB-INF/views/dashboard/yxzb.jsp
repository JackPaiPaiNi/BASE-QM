<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/index/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>国内营销分业务部门业绩情况</title>
<jsp:include page="/WEB-INF/views/index/js.bootstrap.jsp" />
<!-- 引入 echarts.js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/echarts.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/theme/dark.js"></script>
<style>
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
	<input id="ywbm" type="hidden" value="整体"/>
	<div class="row">
		<div class="col-md-12 col-sm-12 text-center">
			<h2>国内营销分业务部门业绩情况</h2>
			<p>数据截止时间：<label id="label_qj"></label>&nbsp;24:00</p>
			<div class="btn-group">
				<button class="btn btn-xs btn-warning active" ywbm="整体" onclick="javascript:changeYwbm(this,'整体');">整体</button>
				<button class="btn btn-xs btn-warning" ywbm="线下" onclick="javascript:changeYwbm(this,'线下');">线下</button>
				<button class="btn btn-xs btn-warning" ywbm="电商" onclick="javascript:changeYwbm(this,'电商');">电商</button>
				<button class="btn btn-xs btn-warning" ywbm="B2B" onclick="javascript:changeYwbm(this,'B2B');">B2B</button>
			</div>
			<div class="btn-group">
				<button class="btn btn-xs btn-success active" onclick="javascript:changeSjly(this,'签收');">签收</button>
				<button class="btn btn-xs btn-success" onclick="javascript:changeSjly(this,'出库');">出库</button>
				<button class="btn btn-xs btn-success" onclick="javascript:changeSjly(this,'零售');">零售</button>
			</div>
			<div class="btn-group">
				<button class="btn btn-xs btn-info" onclick="javascript:changeQjlx(this,'当日');">当日</button>
				<button class="btn btn-xs btn-info active" onclick="javascript:changeQjlx(this,'当月');">累计</button>
			</div>
		</div>
	</div>
	<br>
	<div class="row">
		<div class="col-lg-4 col-md-6 col-sm-12" id="chart1" style="height: 400px;"></div>
		<div class="col-lg-4 col-md-6 col-sm-12" id="chart2" style="height: 400px;"></div>
		<div class="col-lg-4 col-md-6 col-sm-12" id="chart3" style="height: 400px;"></div>
	</div>
	<div class="row">
		<div class="col-lg-4 col-md-6 col-sm-12" id="chart4" style="height: 400px;"></div>
		<div class="col-lg-4 col-md-6 col-sm-12" id="chart5" style="height: 400px;"></div>
		<div class="col-lg-4 col-md-6 col-sm-12" id="chart6" style="height: 400px;"></div>
	</div>
</body>
<script type="text/javascript">
// 基于准备好的dom，初始化echarts实例
var chart1 = echarts.init(document.getElementById('chart1'),'dark');
var chart2 = echarts.init(document.getElementById('chart2'),'dark');
var chart3 = echarts.init(document.getElementById('chart3'),'dark');
var chart4 = echarts.init(document.getElementById('chart4'),'dark');
var chart5 = echarts.init(document.getElementById('chart5'),'dark');
var chart6 = echarts.init(document.getElementById('chart6'),'dark');

$(function() {
	chart1.on('click', onClickEvent);
	chart2.on('click', onClickEvent);
	chart3.on('click', onClickEvent);
	// 初始化期间
	initDate();
	// 图表取数
	loadData();
});

function onClickEvent(param){
	$(".btn[ywbm="+param.name+"]").trigger("click");
}

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
	var ywbm = $("#ywbm").val();
	$.post("<c:url value='/dashboard/dp/searchYwbm'/>", {qjlx:qjlx,qj:qj,sjly:sjly,ywbm:ywbm}, function(result){
		chart1.setOption(initChart12("销量达成", result.listDcX, result.listDcXlYs, result.listDcXlWc, result.listDcXlWcl));
		chart2.setOption(initChart12("规模达成", result.listDcX, result.listDcXeYs, result.listDcXeWc, result.listDcXeWcl));
		chart3.setOption(initChart3(result.listDcX, result.listDcMllYs, result.listDcMllWc));
		chart4.setOption(initChart4(result.listCcjgX, result.listCcjg1, result.listCcjg2, result.listCcjg3, result.listCcjg4, result.listCcjg5, result.listCcjg6));
		chart5.setOption(initChart5(result.listXljgX, result.listXljgXl, result.listXljgYs));
		chart6.setOption(initChart6(result.listQDJG));
		layer.close(_index);
    },"json");
}

// 业务部门change事件
function changeYwbm(el, ywbm){
	var _this = $(el);
	if(!_this.hasClass("active")){
		_this.siblings(".active").removeClass("active");
		_this.addClass("active");
		//取数
		$("#ywbm").val(ywbm);
		loadData();
	}
}

//数据来源change事件
function changeSjly(el, sjly){
	var _this = $(el);
	if(!_this.hasClass("active")){
		_this.siblings(".active").removeClass("active");
		_this.addClass("active");
		//取数
		$("#sjly").val(sjly);
		loadData();
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
	}
}

function initChart4(x, data1, data2, data3, data4, data5, data6){
	return {
	    title: {
	        text: '尺寸结构',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    legend: {
	    	bottom: 10,
	        left: 'center',
	        data:['32"及以下','39-43"','49/50"','55"','58/60"','65"及以上']
	    },
	    xAxis : {
            boundaryGap : false,
            data : x
	    },
	    yAxis : {
            type : 'value',
	        splitLine: {
	            show: false
	        },
            axisLabel: {
                formatter: '{value}%'
            }
        },
	    series : [
	        {
	            name:'32"及以下',
	            type:'line',
	            stack: '尺寸',
	            /* label: {
	                normal: {
	                    show: true,
	                    position: 'top'
	                }
	            }, */
	            areaStyle: {normal: {}},
	            data:data1
	        },
	        {
	            name:'39-43"',
	            type:'line',
	            stack: '尺寸',
	            /* label: {
	                normal: {
	                    show: true,
	                    position: 'top'
	                }
	            }, */
	            areaStyle: {normal: {}},
	            data:data2
	        },
	        {
	            name:'49/50"',
	            type:'line',
	            stack: '尺寸',
	            /* label: {
	                normal: {
	                    show: true,
	                    position: 'top'
	                }
	            }, */
	            areaStyle: {normal: {}},
	            data:data3
	        },
	        {
	            name:'55"',
	            type:'line',
	            stack: '尺寸',
	            /* label: {
	                normal: {
	                    show: true,
	                    position: 'top'
	                }
	            }, */
	            areaStyle: {normal: {}},
	            data:data4
	        },
	        {
	            name:'58/60"',
	            type:'line',
	            stack: '尺寸',
	            /* label: {
	                normal: {
	                    show: true,
	                    position: 'top'
	                }
	            }, */
	            areaStyle: {normal: {}},
	            data:data5
	        },
	        {
	            name:'65"及以上',
	            type:'line',
	            stack: '尺寸',
	            /* label: {
	                normal: {
	                    show: true,
	                    position: 'top'
	                }
	            }, */
	            areaStyle: {normal: {}},
	            data:data6
	        }
	    ]
	};
}

function initChart5(y, data1, data2){
	return {
	    title: {
	        text: '系列结构',
	        x:'center'
	    },
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    legend: {
	    	bottom: 10,
	        left: 'center',
	        data:['销量','预算']
	    },
	    xAxis: {
	        type: 'value',
	        position: 'top',
	        splitLine: {
	            show: false
	        }
	    },
	    yAxis: {
	        type: 'category',
	        data: y
	    },
	    series: [
	        {
	            name: '销量',
	            type: 'bar',
	            data: data1
	        },
	        {
	            name: '预算',
	            type: 'bar',
	            data: data2
	        }
	    ]
	};
}

function initChart12(title, x, data1, data2, data3){
	return {
	    title : {
	        text: title,
	        x:'center'
	    },
	    tooltip: {
	    	trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    xAxis: {
            data: x
	    },
	    yAxis: [
	        {
	            type: 'value',
		        splitLine: {
		            show: false
		        }
	        },
	        {
	            type: 'value',
		        splitLine: {
		            show: false
		        },
	            axisLabel: {
	                formatter: '{value}%'
	            }
	        }
	    ],
	    series: [
	        {
	            name:'预算',
	            type:'bar',
	            label: {
	                normal: {
	                    show: true,
	                    position: 'top'
	                }
	            },
	            data:data1
	        },
	        {
	            name:'完成',
	            type:'bar',
	            label: {
	                normal: {
	                    show: true,
	                    position: 'top'
	                }
	            },
	            data:data2
	        },
	        {
	            name:'完成率',
	            type:'line',
	            smooth: true,
	            yAxisIndex: 1,
	            label: {
	                normal: {
	                    show: true,
	                    position: 'top'
	                }
	            },
	            data:data3
	        }
	    ]
	};
}

function initChart3(x, data1, data2){
	return {
	    title : {
	        text: '毛利率达成',
	        x:'center'
	    },
	    tooltip: {
	    	trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    legend: {
	    	bottom: 10,
	        left: 'center',
	        data:['毛利率目标','实际毛利率']
	    },
	    xAxis: {
	        data: x
	    },
	    yAxis: {
	    	type: 'value',
	        splitLine: {
	            show: false
	        }
	    },
	    series: [
	        {
	            name:'毛利率目标',
	            type:'bar',
	            label: {
	                normal: {
	                    show: true,
	                    position: 'top',
	                    formatter: '{c}%'
	                }
	            },
	            data:data1
	        },
	        {
	            name:'实际毛利率',
	            type:'bar',
	            label: {
	                normal: {
	                    show: true,
	                    position: 'top',
	                    formatter: '{c}%'
	                }
	            },
	            data:data2
	        }
	    ]
	};
}

function initChart6(data){
	return {
	    title : {
	        text: '渠道结构',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{b}：{d}%"
	    },
	    series : [
	        {
	            type: 'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            label: {
	                normal: {
	                    formatter: '{b}{d}%'
	                }
	            },
	            data:data,
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};
}
</script>
</html>
