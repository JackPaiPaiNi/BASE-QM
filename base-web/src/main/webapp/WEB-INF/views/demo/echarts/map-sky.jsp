<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/index/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/index/js.bootstrap.jsp" />
<!-- 引入 echarts.js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/echarts.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/china-sky.js"></script>
</head>
<body>
	<div class="wrapper wrapper-content animated fadeInRight full-height">	
		<div class="ibox full-height">
			<div class="ibox-title">
				<h5>Echarts Map Demo</h5>
			</div>
			<div class="ibox-content">
				<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
				<div id="main" style="width: 100%;height:100%;"></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function() {
	// 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
	var option = {
		title: {
            subtext: '创维国内营销分公司分布图'
        },
        // 鼠标经过弹窗提示
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
            show : false,
            inRange: {
                color: ['orangered', 'yellow', 'lightskyblue']
            }
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
            data:[
                  {name:'甘肃', value:14},
                  {name:'贵州', value:2},
                  {name:'新疆', value:15},
                  {name:'云南', value:4},
                  {name:'重庆', value:5},
                  {name:'吉林', value:6},
                  {name:'晋蒙', value:7},
                  {name:'天津', value:8},
                  {name:'江西', value:9},
                  {name:'广西', value:10},
                  {name:'陕西', value:11},
                  {name:'黑龙江', value:12},
                  {name:'安徽', value:13},
                  {name:'北京', value:1},
                  {name:'福建', value:2},
                  {name:'上海', value:16},
                  {name:'湖北', value:17},
                  {name:'湖南', value:18},
                  {name:'四川', value:19},
                  {name:'辽宁', value:20},
                  {name:'河北', value:21},
                  {name:'河南', value:22},
                  {name:'豫南', value:23},
                  {name:'浙江', value:24},
                  {name:'宁波', value:25},
                  {name:'山东', value:26},
                  {name:'青岛', value:27},
                  {name:'江苏', value:28},
                  {name:'苏南', value:29},
                  {name:'广东', value:30/* , selected:true */},
                  {name:'深圳', value:31/* , itemStyle:{normal:{borderColor:'#FF3030',areaColor:'#FF3030'}} */}
             ]
		}]
	};
	// 地图点击事件
	myChart.on('click', function(param){
		alert(param.data.index + "-" + param.data.name + ":" + param.data.value);
	});
    myChart.setOption(option);

});
</script>
</html>
