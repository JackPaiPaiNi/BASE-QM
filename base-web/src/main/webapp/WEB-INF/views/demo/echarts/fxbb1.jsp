<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/views/index/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<jsp:include page="/WEB-INF/views/index/js.bootstrap.jsp" />
<!-- 引入 echarts.js -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/echarts/echarts.min.js"></script>
<style>
.flex {
	width: 100%;
	display: flex;
	flex-direction: row-reverse;
	align-items: center;
}

.left-box {
	width: 50%;
	display: flex;
	flex-direction: column;
	height: 100%
}

.main-box {
	width: 100%;
	height: 100%;
	display: flex;
	flex-direction: row;
	display: flex;
}

.riex {
	width: 50%;
	height: 100%;
}

.dd {
	margin: 0px;
	paading: 0px;
}

.map1 {
	height: 510px;
	width: 100%;
}

.SingleRow {
	background-color: #00FFFF;
}

.DoubleRow {
	background-color: #1E90FF;
}
</style>
</head>
<body>
	<div class="wrapper wrapper-content animated fadeInRight full-height">
		<div class="ibox full-height">
			<div class="ibox-title">
				<h5>Echarts Map Demo</h5>
			</div>
			<div class="ibox-content" style="margin: 0px; padding: 0px;">
				<div class="main-box">
					<div class="left-box">
						<div class="flex">
							<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
							<div class="dd" id="data1" style="width: 30%; height: 170px;"></div>
							<div class="dd" id="data2" style="width: 30%; height: 170px;"></div>
							<div class="dd" id="data3" style="width: 30%; height: 170px;"></div>
						</div>
						<div class="map1">
							<div style="width: 100%; height: 100%;">
								<jsp:include page="/WEB-INF/views/demo/echarts/map-sky.jsp" />
							</div>
						</div>
					</div>
					<div class="riex">
						<div class="dd paddingx" id="chart1"
							style="width: 100%; height: 120px;"></div>
						<div class="dd paddingx" id="chart2"
							style="width: 100%; height: 120px;"></div>
						<div class="dd paddingx" id="chart3"
							style="width: 100%; height: 140px;"></div>
						<div class="dd paddingx" id="chart4"
							style="width: 100%; height: 300px;">
							<div
								style="text-align: center; overflow: auto; width: 520px; height: 300px; margin-left: 115px;">
								<table id="dt" style="width: 100%; height: 100%; color: #fff;">
									<tr style="background-color: #191970">
										<td>渠道</td>
										<td>渠道销售额</td>
										<td>同比增长率</td>
										<td>累计毛利率</td>
										<td>同比增减</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var myChart1 = echarts.init(document.getElementById('data1'));
		var myChart2 = echarts.init(document.getElementById('data2'));
		var myChart3 = echarts.init(document.getElementById('data3'));
		var e1 = 3;
		var option1 = {
			title : {
				show : true,
				text : e1 + '%',
				x : 'center',
				y : 'center',
				textStyle : {
					fontSize : '15',
					color : '#000',
					fontWeight : 'normal'
				}
			},
			tooltip : {
				trigger : 'item',
				formatter : "{d}%",
				show : false
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				show : false
			},
			series : {
				name : '',
				type : 'pie',
				radius : [ '60%', '65%' ],
				avoidLabelOverlap : true,
				hoverAnimation : false,
				label : {
					normal : {
						show : false,
						position : 'center'
					},
					emphasis : {
						show : false
					}
				},
				labelLine : {
					normal : {
						show : false
					}
				},
				data : [ {
					value : e1,
					name : e1 + '%',
					itemStyle : {
						normal : {
							color : '#1E90FF',
							borderColor : '#f0f0f0'
						},
						emphasis : {
							color : '#1E90FF',
							borderColor : '#f0f0f0'
						}

					}
				}, {
					value : 100 - e1,
					name : (100 - e1) + '%',
					itemStyle : {
						normal : {
							color : '#f1f1f1',
							borderColor : '#A8A8A8'
						},
						emphasis : {
							color : '#f1f1f1',
							borderColor : '#A8A8A8'
						}
					}
				} ]
			}
		};
		myChart1.setOption(option1);

		var e2 = 5;
		var option2 = {
			title : {
				show : true,
				text : e2 + '%',
				x : 'center',
				y : 'center',
				textStyle : {
					fontSize : '15',
					color : '#000',
					fontWeight : 'normal'
				}
			},
			tooltip : {
				trigger : 'item',
				formatter : "{d}%",
				show : false
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				show : false
			},
			series : {
				name : '',
				type : 'pie',
				radius : [ '60%', '65%' ],
				avoidLabelOverlap : true,
				hoverAnimation : false,
				label : {
					normal : {
						show : false,
						position : 'center'
					},
					emphasis : {
						show : false
					}
				},
				labelLine : {
					normal : {
						show : false
					}
				},
				data : [ {
					value : e2,
					name : e2 + '%',
					itemStyle : {
						normal : {
							color : '#1E90FF',
							borderColor : '#f0f0f0'
						},
						emphasis : {
							color : '#1E90FF',
							borderColor : '#f0f0f0'
						}

					}
				}, {
					value : 100 - e2,
					name : (100 - e2) + '%',
					itemStyle : {
						normal : {
							color : '#f2f2f2',
							borderColor : '#A8A8A8'
						},
						emphasis : {
							color : '#f2f2f2',
							borderColor : '#A8A8A8'
						}
					}
				} ]
			}
		};
		myChart2.setOption(option2);
		var e3 = 34;
		var option3 = {
			title : {
				show : true,
				text : e3 + '%',
				x : 'center',
				y : 'center',
				textStyle : {
					fontSize : '15',
					color : '#000',
					fontWeight : 'normal'
				}
			},
			tooltip : {
				trigger : 'item',
				formatter : "{d}%",
				show : false
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				show : false
			},
			series : {
				name : '',
				type : 'pie',
				radius : [ '60%', '65%' ],
				avoidLabelOverlap : true,
				hoverAnimation : false,
				label : {
					normal : {
						show : false,
						position : 'center'
					},
					emphasis : {
						show : false
					}
				},
				labelLine : {
					normal : {
						show : false
					}
				},
				data : [ {
					value : e3,
					name : e3 + '%',
					itemStyle : {
						normal : {
							color : '#1E90FF',
							borderColor : '#f0f0f0'
						},
						emphasis : {
							color : '#1E90FF',
							borderColor : '#f0f0f0'
						}

					}
				}, {
					value : 100 - e3,
					name : (100 - e3) + '%',
					itemStyle : {
						normal : {
							color : '#f2f2f2',
							borderColor : '#A8A8A8'
						},
						emphasis : {
							color : '#f2f2f2',
							borderColor : '#A8A8A8'
						}
					}
				} ]
			}
		};
		myChart3.setOption(option3);

		var chart1 = echarts.init(document.getElementById('chart1'));
		var chart2 = echarts.init(document.getElementById('chart2'));
		var chart3 = echarts.init(document.getElementById('chart3'));
		//var chart4 = echarts.init(document.getElementById('chart4'));
		var chartOp1 = {
			xAxis : {
				type : 'category',
				data : [ '4月', '5月', '6月', '7月', '8月' ]
			},
			grid : {
				y : '5%',
				bottom : '3%',
				containLabel : true

			},
			yAxis : [ {
				type : 'value',
				axisLine : {
					show : false
				},
				axisTick : {
					show : false
				},

			}, {
				type : 'value',
				scale : true,
				axisLabel : {
					formatter : '{value} %'
				},
				max : "50",
				min : "0",
				boundaryGap : [ 0.2, 0.2 ],
				axisLine : {
					show : false
				},
				axisTick : {
					show : false
				},
				splitLine : {
					show : false
				}
			} ],
			series : [ {
				type : 'bar',
				itemStyle : {
					normal : {
						color : '#4169E1'
					}
				},
				data : [ 99000, 61000, 59000, 81000, 3000 ]

			}, {
				type : 'line',
				itemStyle : {
					normal : {
						color : '#00BFFF'
					}
				},
				data : [ 40000, 22000, 22000, 31000, 1500 ]
			} ]
		};
		chart1.setOption(chartOp1);
		var chartOp2 = {
			grid : {
				y : '5%',
				bottom : '3%',
				containLabel : true
			},
			xAxis : {
				type : 'category',
				data : [ '4月', '5月', '6月', '7月', '8月' ]
			},
			yAxis : [ {
				type : 'value',
				axisLine : {
					show : false
				},
				axisTick : {
					show : false
				},
			}, {
				type : 'value',
				max : 3000,
				min : 0,
				axisLine : {
					show : false
				},
				axisTick : {
					show : false
				},
				scale : true,
				splitLine : {
					show : false
				}
			} ],
			series : [ {
				type : 'bar',
				itemStyle : {
					normal : {
						color : '#4169E1'
					}
				},
				data : [ 40000, 22000, 22000, 31000, 1500 ]

			}, {
				type : 'line',
				itemStyle : {
					normal : {
						color : '#00BFFF'
					}
				},
				data : [ 40000, 22000, 22000, 31000, 1500 ]
			} ]
		};
		chart2.setOption(chartOp2);
		
		var chartOp3 = {
			grid : {
				y : '15%',
				bottom : '3%',
				containLabel : true
			},
			legend : {
				show : true,
				left : 'center',
				top : 'top',
				data : [ {
					name : '差距',
					textStyle : {}
				}, {
					name : '完成',
					textStyle : {}
				}, {
					name : '销量占比',
					textStyle : {}
				} ]
			},
			xAxis : {
				type : 'category',
				data : [ '65寸及以上', 'G6B/G3', 'OLED', '大Q6' ]
			},
			yAxis : [ {
				type : 'value',
				axisLine : {
					show : false
				},
				axisTick : {
					show : false
				}
			}, {
				type : 'value',
				axisLine : {
					show : false
				},
				max : 7,
				min : 0,
				axisLabel : {
					formatter : '{value} %'
				},
				axisTick : {
					show : false
				},
				splitLine : {
					show : false
				}
			} ],
			series : [ {
				name : '差距',
				type : 'bar',
				itemStyle : {
					normal : {
						color : '#00BFFF '
					}
				},
				data : [ 40000, 22000,'-','-' ]
			}, {
				name : '完成',
				type : 'bar',
				itemStyle : {
					normal : {
						color : '#4169E1 '
					}
				},
				data : [ '-','-',22000, 31000 ]
			}, {
				name : '销量占比',
				type : 'line',
				itemStyle : {
					normal : {
						color : '#E9967A'
					}
				},
				data : [ 40000, 22000, 22000, 31000 ]
			} ]
		};
		chart3.setOption(chartOp3);

		/*
			加载数据table 
			param：data
				type：json array
		 */
		function loadTable(data) {
			initTable();
			if (typeof (data) != "object") {
				return;
			}
			if (data == null || data == undefined) {
				return;
			}
			var bgColorClass,domTr,domTd;
			var dt = document.getElementById("dt");
			for (var i = 0, length = data.length; i < length; i++) {
				if (i % 2 == 0) {
					bgColorClass = "DoubleRow";
				} else {
					bgColorClass = "SingleRow";
				}
				var newTr = dt.insertRow();
				newTr.className = bgColorClass;
				for ( var temp in data[i]) {
					var newTd = newTr.insertCell();
					newTd.innerHTML = data[i][temp];
				}
			}
		}

		//初始化删除datagrid数据
		function initTable() {
			var dt = document.getElementById("dt");
			for (var i = 1, length = dt.rows.length; i < length; i++) {
				dt.deleteRow(1);
			}
		}
		var testData = [ {
			qd : "qq",
			qq : "q",
			dd : "ww",
			ss : "ee",
			ff : "rr"
		}, {
			qd : "qq1",
			qq : "q1",
			dd : "ww1",
			ss : "ee1",
			ff : "rr1"
		}, {
			qd : "qq13",
			qq : "q13",
			dd : "ww13",
			ss : "ee13",
			ff : "rr13"
		}, {
			qd : "qq",
			qq : "q",
			dd : "ww",
			ss : "ee",
			ff : "rr"
		}, {
			qd : "qq1",
			qq : "q1",
			dd : "ww1",
			ss : "ee1",
			ff : "rr1"
		}, {
			qd : "qq13",
			qq : "q13",
			dd : "ww13",
			ss : "ee13",
			ff : "rr13"
		}, {
			qd : "qq",
			qq : "q",
			dd : "ww",
			ss : "ee",
			ff : "rr"
		}, {
			qd : "qq1",
			qq : "q1",
			dd : "ww1",
			ss : "ee1",
			ff : "rr1"
		}, {
			qd : "qq13",
			qq : "q13",
			dd : "ww13",
			ss : "ee13",
			ff : "rr13"
		}, {
			qd : "qq",
			qq : "q",
			dd : "ww",
			ss : "ee",
			ff : "rr"
		}, {
			qd : "qq1",
			qq : "q1",
			dd : "ww1",
			ss : "ee1",
			ff : "rr1"
		} ];
		loadTable(testData);
	</script>
</body>
</html>