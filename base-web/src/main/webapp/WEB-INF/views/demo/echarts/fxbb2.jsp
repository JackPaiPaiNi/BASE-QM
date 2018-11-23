<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/index/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/index/js.bootstrap.jsp" />
<!-- 引入 echarts.js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/echarts.min.js"></script>
<style>
	.box{
		height:300px;
	}
	.ibox-content{
		overflow-y:scroll;
		overflow-x:hidden;
	}
	.ibox-content-row{
		margin:0;
	}
</style>
</head>
<body>
	<div class="wrapper wrapper-content animated fadeInRight full-height">	
		<div class="ibox full-height">
			<div class="ibox-title">
				<h5>国内营销分业务部门业绩情况(单日签收)</h5>
				<div class="ibox-tools">
					<button id="btn_search" class="btn btn-primary btn-xs"><i class="fa fa-search"></i>&nbsp;签收</button>
					<button id="btn_add" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>&nbsp;出库</button>
				</div>
			</div>
			<div class="ibox-content">
					<div class="row ibox-content-row">
						<div class="col-lg-4 col-md-6 col-sm-12  box" id="box01">
							
						</div>
						<div class="col-lg-4 col-md-6 col-sm-12 box" id="box02">
							
						</div>
						<div class="col-lg-4 col-md-6 col-sm-12 box" id="box03">
							
						</div>
						<div class="col-lg-4 col-md-6 col-sm-12 box" id="box04">
							
						</div>
						<div class="col-lg-4 col-md-6 col-sm-12 box" id="box05">
							
						</div>
						<div class="col-lg-4 col-md-6 col-sm-12 box" id="box06">
							
						</div>
					</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function() {
	// 基于准备好的dom，初始化echarts实例
    var box01 = echarts.init(document.getElementById('box01'));
	var box02= echarts.init(document.getElementById('box02'));
	var box03= echarts.init(document.getElementById('box03'));
	var box04= echarts.init(document.getElementById('box04'));
	var box05= echarts.init(document.getElementById('box05'));
	var box06= echarts.init(document.getElementById('box06'));
	
	
	
	
	var xsdc_initData=function(callback){
		var arr_xsdc_mbdc=[];
		var arr_xsdc_wcl=[];
		var arr_xsdc_sj=[];
		$.getJSON("${pageContext.request.contextPath}/jsonData/data1.json",function(rs){
			$.each(rs,function(index,obj){
				var arr=[];
				for(var prop in obj){
					arr.push(obj[prop]);
					if(index==0){
						arr_xsdc_mbdc=arr;	
					}else if(index==1){
						arr_xsdc_wcl=arr;
					}else{
						arr_xsdc_sj=arr;
					}
				}
			});

			if(callback){
				callback.call(this,arr_xsdc_mbdc,arr_xsdc_wcl,arr_xsdc_sj);
			}
			
		});
	}
	
	var gmdc_initData=function(callback){
		var arr_gmdc_mbdc=[];
		var arr_gmdc_wcl=[];
		var arr_gmdc_sj=[];
		$.getJSON("${pageContext.request.contextPath}/jsonData/data2.json",function(rs){
			$.each(rs,function(index,obj){
				var arr=[];
				for(var prop in obj){
					arr.push(obj[prop]);
					if(index==0){
						arr_gmdc_mbdc=arr;	
					}else if(index==1){
						arr_gmdc_wcl=arr;
					}else{
						arr_gmdc_sj=arr;
					}
				}
			});

			if(callback){
				callback.call(this,arr_gmdc_mbdc,arr_gmdc_wcl,arr_gmdc_sj);
			}
			
		});
	}
	
	
	var mlldc_initData=function(callback){
		var arr_mlldc_mbdc=[];
		var arr_mlldc_sj=[];
		$.getJSON("${pageContext.request.contextPath}/jsonData/data3.json",function(rs){
			$.each(rs,function(index,obj){
				var arr=[];
				for(var prop in obj){
					arr.push(obj[prop]);
					if(index==0){
						arr_mlldc_mbdc=arr;	
					}else{
						arr_mlldc_sj=arr;
					}
				}
			});
			if(callback){
				callback.call(this,arr_mlldc_mbdc,arr_mlldc_sj);
			}
			
		});
	}
	
	var xljg_initData=function(callback){
		var arr_xljg_mbdc=[];
		var arr_xljg_sj=[];
		$.getJSON("${pageContext.request.contextPath}/jsonData/data4.json",function(rs){
			$.each(rs,function(index,obj){
				var arr=[];
				for(var prop in obj){
					arr.push(obj[prop]);
					if(index==0){
						arr_xljg_mbdc=arr;	
					}else if(index==1){
						arr_xljg_sj=arr;
					}
				}
			});

			if(callback){
				callback.call(this,arr_xljg_mbdc,arr_xljg_sj);
			}
			
		});
	}
	
	var ccjg_initData=function(callback){
		var arr_ccjg_01=[];
		var arr_ccjg_02=[];
		var arr_ccjg_03=[];
		var arr_ccjg_04=[];
		var arr_ccjg_05=[];
		$.getJSON("${pageContext.request.contextPath}/jsonData/data5.json",function(rs){
			$.each(rs,function(index,obj){
				var arr=[];
				for(var prop in obj){
					arr.push(obj[prop]);
					if(index==0){
						arr_ccjg_01=arr;	
					}else if(index==1){
						arr_ccjg_02=arr;
					}else if(index==2){
						arr_ccjg_03=arr;
					}else if(index==3){
						arr_ccjg_04=arr;
					}else if(index==4){
						arr_ccjg_05=arr;
					}
				}
			});

			if(callback){
				callback.call(this,arr_ccjg_01,arr_ccjg_02,arr_ccjg_03,arr_ccjg_04,arr_ccjg_05);
			}
			
		});
	}
	
	var qdjg_initData=function(callback){
		var arr_ccjg_01=[];
		var arr_ccjg_02=[];
		var arr_ccjg_03=[];
		var arr_ccjg_04=[];
		var arr_ccjg_05=[];
		$.getJSON("${pageContext.request.contextPath}/jsonData/data6.json",function(rs){
			$.each(rs,function(index,obj){
				var arr=[];
				for(var prop in obj){
					arr.push(obj[prop]);
					if(index==0){
						arr_ccjg_01=arr;	
					}else if(index==1){
						arr_ccjg_02=arr;
					}else if(index==2){
						arr_ccjg_03=arr;
					}else if(index==3){
						arr_ccjg_04=arr;
					}else if(index==4){
						arr_ccjg_05=arr;
					}
				}
			});

			if(callback){
				callback.call(this,arr_ccjg_01,arr_ccjg_02,arr_ccjg_03,arr_ccjg_04,arr_ccjg_05);
			}
			
		});
	}
	
	var qdjg_initData=function(callback){
		$.getJSON("${pageContext.request.contextPath}/jsonData/data6.json",function(rs){

			if(callback){
				callback.call(this,rs);
			}
			
		});
	}
	
	var Action_XSDC=function(arr_xsdc_mbdc,arr_xsdc_wcl,arr_xsdc_sj){
		var box01_option = {
				tooltip:{
					trigger:'axis'
				},
		    	title:{
		    		text:"销量达成",
		    		left:'left'
		    	},	
		    	legend: {
		            // icon: 'rect',
		            itemWidth: 20,
		            itemHeight: 10,
		            itemGap: 10,
		            // data: ['南宁-曼芭', '桂林-曼芭', '南宁-甲米'],
		            data:[  {name:'目标达成',icon:'rect'},
		                    {name:'完成率',icon:'rect'},
		                    {name:'实际',icon:'rect'}],//分别修改legend格式
		            right: '4%',
		            textStyle: {
		                fontSize: 12,
		                color: '#333'
		            },
		            show:true
		        },
				grid:{
					left: '4%',
			        right: '4%',
			        bottom: '3%',
			        containLabel:true
				},
				xAxis : [
			         {
			             type : 'category',
			             data : ['B2B','电商','线下','整体'],
			             axisTick: {
			                 alignWithLabel: true
			             }
			         }
			     ],
			     yAxis : [
		              {
		                  type : 'value',
		                  axisLine:{
		                	  show:false
		                  },
		                  axisTick:{
		                	  show:false
		                  }
		                  ,
		                  yAxisIndex:1
		              },
		              {
		            	  type:'value',
		            	  min:0,
		            	  max:6,
		                  axisLine:{
		                	  show:false
		                  },
		                  axisTick:{
		                	  show:false
		                  },
		                  axisLabel: {  
		                      show: true,  
		                      interval: 'auto',  
		                      formatter: '{value}%'  
		                  },
		                  splitLine:{
		                	  show:false
		                  },
		                  yAxisIndex:0
		              }
		          ],
		        series : [
		                  {
		                      name:'目标达成',
		                      type:'bar',
		                      data:arr_xsdc_mbdc
		                  },
		                  {
		                      name:'实际',
		                      type:'bar',
		                      data:arr_xsdc_sj
		                  },
		                  {
		                	  name:'完成率',
		                      type:'line',
		                      itemStyle : {  
		                    	  normal: {  /*设置图表颜色*/
		                    		  color:'#4ad2ff'  
		                          } 
		                      },
		                      smooth:true,  //这个是把线变成曲线
		                      symbol:'none',//去掉点
		                      data:arr_xsdc_wcl
		                  }
		              ]
		    }
		box01.setOption(box01_option);
	}
	
	var Action_GMDC=function(arr_gmdc_mbdc,arr_gmdc_wcl,arr_gmdc_sj){
		var box02_option = {
				tooltip:{
					trigger:'axis'
				},
		    	title:{
		    		text:"规模达成",
		    		left:'left'
		    	},	
		    	legend: {
		            // icon: 'rect',
		            itemWidth: 20,
		            itemHeight: 10,
		            itemGap: 10,
		            // data: ['南宁-曼芭', '桂林-曼芭', '南宁-甲米'],
		            data:[  {name:'目标达成',icon:'rect'},
		                    {name:'完成率',icon:'rect'},
		                    {name:'实际',icon:'rect'}],//分别修改legend格式
		            right: '4%',
		            textStyle: {
		                fontSize: 12,
		                color: '#333'
		            },
		            show:true
		        },
				grid:{
					left: '4%',
			        right: '4%',
			        bottom: '3%',
			        containLabel:true
				},
				xAxis : [
			         {
			             type : 'category',
			             data : ['B2B', '电商', '线下', '整体'],
			             axisTick: {
			                 alignWithLabel: true
			             }
			         }
			     ],
			     yAxis : [
		              {
		                  type : 'value',
		                  axisLine:{
		                	  show:false
		                  },
		                  axisTick:{
		                	  show:false
		                  }
		                  
		              },
		              {
		            	  type:'value',
		            	  min: '0',
		                  max:'6',
		                  axisLine:{
		                	  show:false
		                  },
		                  axisTick:{
		                	  show:false
		                  },
		                  axisLabel: {  
		                      show: true,  
		                      interval: 'auto',  
		                      formatter: '{value}%'  
		                  },
		                  splitLine:{
		                	  show:false
		                  }  
		              }
		          ],
		        series : [
		                  {
		                      name:'目标达成',
		                      type:'bar',
		                      data:arr_gmdc_mbdc
		                  },
		                  {
		                      name:'实际',
		                      type:'bar',
		                      data:arr_gmdc_sj
		                  },
		                  {
		                	  name:'完成率',
		                      type:'line',
		                      itemStyle : {  
		                    	  normal: {  /*设置图表颜色*/
		                    		  color:'#4ad2ff'  
		                          } 
		                      },
		                      smooth:true,  //这个是把线变成曲线
		                      symbol:'none',//去掉点
		                      data:arr_gmdc_wcl
		                  }
		              ]
		    }
	    
		box02.setOption(box02_option);
	}
	
	var Action_MLLDC=function (arr_mlldc_mbdc,arr_mlldc_sj){
		
		var box03_option = {
				tooltip:{
					trigger:'axis'
				},
		    	title:{
		    		text:"毛利率达成",
		    		left:'left'
		    	},	
		    	legend: {
		            // icon: 'rect',
		            itemWidth: 20,
		            itemHeight: 10,
		            itemGap: 10,
		            // data: ['南宁-曼芭', '桂林-曼芭', '南宁-甲米'],
		            data:[  {name:'目标达成',icon:'rect'},
		                    {name:'完成率',icon:'rect'},
		                    {name:'实际',icon:'rect'}],//分别修改legend格式
		            right: '4%',
		            textStyle: {
		                fontSize: 12,
		                color: '#333'
		            },
		            show:true
		        },
				grid:{
					left: '4%',
			        right: '4%',
			        bottom: '3%',
			        containLabel:true
				},
				xAxis : [
			         {
			             type : 'category',
			             data : ['B2B', '电商', '线下', '整体'],
			             axisTick: {
			                 alignWithLabel: true
			             }
			         }
			     ],
			     yAxis : [
		              {
		                  type : 'value',
		                  axisLine:{
		                	  show:false
		                  },
		                  axisTick:{
		                	  show:false
		                  },
		                  axisLabel: {  
		                      show: true,  
		                      interval: 'auto',  
		                      formatter: function(value){
		                    	  return (value/10000)+'%' ;
		                      } 
		                  },
		                  
		              }
		          ],
		        series : [
		                  {
		                      name:'目标达成',
		                      type:'bar',
		                      data:arr_mlldc_mbdc
		                  },
		                  {
		                      name:'实际',
		                      type:'bar',
		                      data:arr_mlldc_sj
		                  }
		              ]
		    }
		box03.setOption(box03_option);
	}
	
	var Action_XLJG=function(arr_xljg_mbdc,arr_xljg_sj){
		var box04_option = {
				tooltip:{
					trigger:'axis'
				},
		    	title:{
		    		text:"系列结构",
		    		left:'left'
		    	},	
		    	legend: {
		    		orient:'vertical',
		            // icon: 'rect',
		            itemWidth: 20,
		            itemHeight: 10,
		            itemGap: 10,
		            // data: ['南宁-曼芭', '桂林-曼芭', '南宁-甲米'],
		            data:[  {name:'目标达成',icon:'rect'},
		                    {name:'完成率',icon:'rect'},
		                    {name:'实际',icon:'rect'}],//分别修改legend格式
		            right: '4%',
		            textStyle: {
		                fontSize: 12,
		                color: '#333'
		            },
		            show:true
		        },
				grid:{
					left: '4%',
			        right: '4%',
			        bottom: '3%',
			        containLabel:true
				},
				xAxis : [
			         {
			             type : 'value',
			             axisLine:{
		                	  show:false
		                  },
		                  axisTick:{
		                	  show:false
		                  },
		                  min:-50000
			         }
			     ],
			     yAxis : [
		              {
		                 type : 'category',
	                     data : ['B2B', '电商', '线下', '整体'],
	                     axisLine:{
		                	  show:true
		                  },
		                  axisTick:{
		                	  show:true
		                  },
		              }
		          ],
		        series : [
		                  {
		                      name:'目标达成',
		                      type:'bar',
		                      data:arr_xljg_mbdc
		                  },
		                  {
		                      name:'实际',
		                      type:'bar',
		                      data:arr_xljg_sj
		                  }
		              ]
		    }
		box04.setOption(box04_option);
	}
	
	
	var Action_CCJG=function(arr_ccjg_01,arr_ccjg_02,arr_ccjg_03,arr_ccjg_04,arr_ccjg_05){
		var box05_option = {
				tooltip:{
					trigger:'axis'
				},
		    	title:{
		    		text:"尺寸结构",
		    		left:'left'
		    	},	
		    	legend: {
		            // icon: 'rect',
		            itemWidth: 20,
		            itemHeight: 10,
		            itemGap: 10,
		            // data: ['南宁-曼芭', '桂林-曼芭', '南宁-甲米'],
		            data:[  {name:'目标达成',icon:'rect'},
		                    {name:'完成率',icon:'rect'},
		                    {name:'实际',icon:'rect'}],//分别修改legend格式
		            right: '4%',
		            textStyle: {
		                fontSize: 12,
		                color: '#333'
		            },
		            show:true
		        },
				grid:{
					left: '4%',
			        right: '4%',
			        bottom: '3%',
			        containLabel:true
				},
				xAxis : [
			         {
			             type : 'category',
	                     data : ['B2B', '电商', '线下', '整体'],
	                     boundaryGap:false
			         }
			     ],
			     yAxis : [
		              {
		                 type : 'value',
	                     axisLine:{
		                	  show:true
		                  },
		                  axisTick:{
		                	  show:true
		                  },
		              }
		          ],
		        series : [
		                  {
		                      type:'line',
		                      stack:'总量',
		                      data:arr_ccjg_01,
		                      areaStyle: {normal: {}},
		                      symbol:'none',//去掉点
		                  },
		                  {
		                      type:'line',
		                      stack:'总量',
		                      data:arr_ccjg_02,
		                      areaStyle: {normal: {}},
		                      symbol:'none',//去掉点
		                  },
		                  {
		                      type:'line',
		                      stack:'总量',
		                      data:arr_ccjg_03,
		                      areaStyle: {normal: {}},
		                      symbol:'none',//去掉点
		                  },
		                  {
		                      type:'line',
		                      stack:'总量',
		                      data:arr_ccjg_04,
		                      areaStyle: {normal: {}},
		                      symbol:'none',//去掉点
		                  },
		                  {
		                      type:'line',
		                      stack:'总量',
		                      data:arr_ccjg_05,
		                      areaStyle: {normal: {}},
		                      symbol:'none',//去掉点
		                  }
		              ]
		    }
		box05.setOption(box05_option);
	}
	
	var Action_QDJG=function(obj){
		var box06_option = {
				tooltip:{
					trigger:'item'
				},
		    	title:{
		    		text:"渠道结构",
		    		left:'left'
		    	},	
		    	legend: {
		            // icon: 'rect',
		            itemWidth: 20,
		            itemHeight: 10,
		            itemGap: 10,
		            // data: ['南宁-曼芭', '桂林-曼芭', '南宁-甲米'],
		            data:[  {name:'目标达成',icon:'rect'},
		                    {name:'完成率',icon:'rect'},
		                    {name:'实际',icon:'rect'}],//分别修改legend格式
		            right: '4%',
		            textStyle: {
		                fontSize: 12,
		                color: '#333'
		            },
		            show:true
		        },
		        series : [
		                  {
		                      type:'pie',
		                      center:['50%','50%'],
		                      radius:'50%',
		                      data:[
		                       { name:'电商',value:obj.ds},
		                       { name:'全国超市连锁',value:obj.qgcsls},
		                       { name:'零售专户',value:obj.lszh},
		                       { name:'运营商',value:obj.yys},
		                       { name:'区域家电连锁',value:obj.qyjdls},
		                       { name:'会员客户',value:obj.hykh},
		                       { name:'城市巷战',value:obj.csxz},
		                       { name:'区域超市连锁',value:obj.qycsls},
		                       { name:'商用专户',value:obj.syzh},
		                       { name:'全国家电连锁',value:obj.qgjdls}
		                       
		                      ],
		                      itemStyle: {
		                          emphasis: {
		                              shadowBlur: 10,
		                              shadowOffsetX: 0,
		                              shadowColor: 'rgba(0, 0, 0, 0.5)'
		                          }
		                      }
		                  }
		              ]
		    }
		box06.setOption(box06_option);
	}
	
	xsdc_initData(Action_XSDC);
	gmdc_initData(Action_GMDC);
	mlldc_initData(Action_MLLDC);
	xljg_initData(Action_XLJG);
	ccjg_initData(Action_CCJG);
	qdjg_initData(Action_QDJG);
	
	
	
    
});
</script>
</html>
