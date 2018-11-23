<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/index/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/index/js.bootstrap.jsp" />
<!-- 引入 echarts.js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/echarts.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/china.js"></script>
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
            subtext: '中国省市分布图'
        },
        // 鼠标经过弹窗提示
        tooltip: {
            trigger: 'item',
            formatter: '{b}'
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
                    borderWidth: .5,//区域边框宽度
                    //borderColor: '#009fe8',//区域边框颜色
                    //areaColor: "#87CEFA",//区域颜色
                },
                emphasis: {
                    borderWidth: .5,
                    borderColor: '#FF7F24',
                    areaColor:"#FF7F24",
                }
            },
            data:[
                  {index:'xz', name:'西藏', value:605.83, itemStyle:{normal:{borderColor:'#B4EEB4',areaColor:'#B4EEB4'}}},
                  {name:'青海', value:1670.44, itemStyle:{normal:{borderColor:'#EED2EE',areaColor:'#EED2EE'}}},
                  {name:'宁夏', value:2102.21, itemStyle:{normal:{borderColor:'#FFFACD',areaColor:'#FFFACD'}}},
                  {name:'海南', value:2522.66, itemStyle:{normal:{borderColor:'#B4EEB4',areaColor:'#B4EEB4'}}},
                  {name:'甘肃', value:5020.37, itemStyle:{normal:{borderColor:'#EECBAD',areaColor:'#EECBAD'}}},
                  {name:'贵州', value:5701.84, itemStyle:{normal:{borderColor:'#B4EEB4',areaColor:'#B4EEB4'}}},
                  {name:'新疆', value:6610.05, itemStyle:{normal:{borderColor:'#FFFACD',areaColor:'#FFFACD'}}},
                  {name:'云南', value:8893.12, itemStyle:{normal:{borderColor:'#EECBAD',areaColor:'#EECBAD'}}},
                  {name:'重庆', value:10011.37, itemStyle:{normal:{borderColor:'#EECBAD',areaColor:'#EECBAD'}}},
                  {name:'吉林', value:10568.83, itemStyle:{normal:{borderColor:'#EED2EE',areaColor:'#EED2EE'}}},
                  {name:'山西', value:11237.55, itemStyle:{normal:{borderColor:'#FFFACD',areaColor:'#FFFACD'}}},
                  {name:'天津', value:11307.28, itemStyle:{normal:{borderColor:'#FFFACD',areaColor:'#FFFACD'}}},
                  {name:'江西', value:11702.82, itemStyle:{normal:{borderColor:'#B4EEB4',areaColor:'#B4EEB4'}}},
                  {name:'广西', value:11720.87, itemStyle:{normal:{borderColor:'#FFFACD',areaColor:'#FFFACD'}}},
                  {name:'陕西', value:12512.3, itemStyle:{normal:{borderColor:'#EED2EE',areaColor:'#EED2EE'}}},
                  {name:'黑龙江', value:12582, itemStyle:{normal:{borderColor:'#FFFACD',areaColor:'#FFFACD'}}},
                  {name:'内蒙古', value:14359.88, itemStyle:{normal:{borderColor:'#B4EEB4',areaColor:'#B4EEB4'}}},
                  {name:'安徽', value:15300.65, itemStyle:{normal:{borderColor:'#EED2EE',areaColor:'#EED2EE'}}},
                  {name:'北京', value:16251.93, itemStyle:{normal:{borderColor:'#FF3030',areaColor:'#FF3030'}}},
                  {name:'福建', value:17560.18, itemStyle:{normal:{borderColor:'#FFFACD',areaColor:'#FFFACD'}}},
                  {name:'上海', value:19195.69, itemStyle:{normal:{borderColor:'#B4EEB4',areaColor:'#B4EEB4'}}},
                  {name:'湖北', value:19632.26, itemStyle:{normal:{borderColor:'#FFFACD',areaColor:'#FFFACD'}}},
                  {name:'湖南', value:19669.56, itemStyle:{normal:{borderColor:'#EED2EE',areaColor:'#EED2EE'}}},
                  {name:'四川', value:21026.68, itemStyle:{normal:{borderColor:'#FFFACD',areaColor:'#FFFACD'}}},
                  {name:'辽宁', value:22226.7, itemStyle:{normal:{borderColor:'#EECBAD',areaColor:'#EECBAD'}}},
                  {name:'河北', value:24515.76, itemStyle:{normal:{borderColor:'#EED2EE',areaColor:'#EED2EE'}}},
                  {name:'河南', value:26931.03, itemStyle:{normal:{borderColor:'#EECBAD',areaColor:'#EECBAD'}}},
                  {name:'浙江', value:32318.85, itemStyle:{normal:{borderColor:'#EECBAD',areaColor:'#EECBAD'}}},
                  {name:'山东', value:45361.85, itemStyle:{normal:{borderColor:'#B4EEB4',areaColor:'#B4EEB4'}}},
                  {name:'江苏', value:49110.27, itemStyle:{normal:{borderColor:'#FFFACD',areaColor:'#FFFACD'}}},
                  {name:'广东', value:53210.28, itemStyle:{normal:{borderColor:'#EECBAD',areaColor:'#EECBAD'}}/* , selected:true */},
                  {name:'台湾', value:49110.27, itemStyle:{normal:{borderColor:'#B4EEB4',areaColor:'#B4EEB4'}}},
                  {name:'香港', value:49110.27, itemStyle:{normal:{borderColor:'#B4EEB4',areaColor:'#B4EEB4'}}},
                  {name:'澳门', value:49110.27, itemStyle:{normal:{borderColor:'#B4EEB4',areaColor:'#B4EEB4'}}}
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
