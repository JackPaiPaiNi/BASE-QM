<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/index/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/index/js.bootstrap.jsp" />
</head>
<body>
	<div class="wrapper wrapper-content animated fadeInRight full-height">
		<div class="ibox full-height">
			<div class="ibox-title">
				<h5>用户信息</h5>
				<div class="ibox-tools">
					<button id="btn_search" class="btn btn-primary btn-xs"><i class="fa fa-search"></i>&nbsp;查询</button>
					<button id="btn_export" class="btn btn-info btn-xs"><i class="fa fa-file-excel-o"></i>&nbsp;导出</button>
					<!-- <a class="collapse-link"> <i class="fa fa-chevron-up"></i></a> -->
				</div>
			</div>
			<div class="ibox-content">
				<form class="form-horizontal" id="search_form">
					<div class="row ibox-form">
						<div class="col-md-3 col-sm-3">
							<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">工号/姓名</span>
								<input type="text" name="xm" class="form-control">
							</div>
						</div>
						<div class="col-md-3 col-sm-3">
							<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">部门 </span>
							     <input type="hidden" name="bmid"/>
								<input type="text" name="bm" class="form-control">
							</div>
						</div>
					</div>
				</form>
				<div class="ibox-grid" id="grid_parent">
					<table id="grid"></table>
					<div id="grid_pager"></div>
				</div>
			</div>
		</div>
	</div>
	<div id="div_edit" class="wrapper wrapper-content animated fadeInRight" style="display:none;">
		<form class="form-horizontal" id="edit_form">
			<div class="row">
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">工号</span>
						<input type="text" name="gh" class="form-control skydisabled" disabled="disabled">
                    </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">姓名</span>
						<input type="text" name="xm" class="form-control skydisabled" disabled="disabled">
                   </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">岗位</span>
						<input type="text" name="gw" class="form-control">
						<input type="hidden" name="gwids" class="form-control">
                    </div>
				</div>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
$(function() {
	var skyOptions = {
		parent :"#grid_parent", 
		pager : "#grid_pager", 
		frozen : true, 
		search : {
			form : "#search_form", 
			searchBtn : "#btn_search", 
			exportBtn : "#btn_export", 
			exportUrl : "<c:url value='/core/user/export'/>", 
			exportName : "用户信息表"
		}
	};
	
	$("#grid").skyGrid({
		sky : skyOptions,
		url : "<c:url value='/core/user/search'/>",
		colModel : [
			{name : "btn", index : "", label : "岗位", frozen : true, align : "center", width : 38, formatter : "btn", 
		    	formatoptions : {title : "岗位绑定", btnClass : "btn-primary", icon : "fa-pencil-square-o", func : "edit", args : ["gh"]}},
		    {name : "hrzt", index : "hrzt", label : "HR状态", width : 50, frozen : true},
		    {name : "gh", index : "gh", label : "工号", width : 80, frozen : true},
		    {name : "xm", index : "xm", label : "姓名", width : 80, frozen : true},
		    {name : "bmid", index : "bmid", label : "部门ID", hidden : true, width : 80},
		    {name : "bm", index : "bm", label : "部门", width : 200},
		    {name : "hrzw", index : "hrzw", label : "HR职务", width : 200},
		    {name : "hrgw", index : "hrgw", label : "HR岗位", width : 200},
		    {name : "ywlx", index : "ywlx", label : "业务类型", width : 80},
		    {name : "bmjc", index : "bmjc", label : "部门级次", width : 80},
		    {name : "mrgwid", index : "gmrwid", label : "默认岗位ID", hidden : true, width : 80},
		    {name : "mrgw", index : "mrgw", label : "默认岗位", width : 80}
		]
	});
	
	$("#edit_form [name=gw]").skySelect({
		title : "岗位选择",
		multiple : true,
		gridMode : false,
		url : "<c:url value='/core/actor/query'/>",
		valueColumn : "id",
		textColumn : "gw",
		valueElem : "#edit_form [name=gwids]",
		textElem : "#edit_form [name=gw]",
		columns : [ {field : "id", title : "岗位ID", search : false}, 
		            {field : "gw", title : "岗位名称", search : true} ]
	}); 
	
	//取部门缓存数据
	$.getLocalCache("bm", '<c:url value="/bi/pub/findBmByGh"/>', function(data) {
		initBm(data.rows);
	});
});

//初始化部门
function initBm(data){
	$('#search_form [name=bm').skySelect({
		title : "部门选择",
		multiple : true,
		gridMode : false,
		data:data,
		autoComplete : true, // 自动完成
		displayText: ["bmid","bm"], //自动完成下拉框显示字段
		valueColumn : "bmid",
		textColumn : "bm",
		valueElem : "#search_form [name=bmid]",
		textElem : "#search_form [name=bm]",
		columns : [ {field : "bmid", title : "部门编码", search : false},
		            {field : "bm", title : "部门名称", search : true},
		            {field : "cwgs", title : "公司全称", search : true}]
	}); 
}
function edit(gh){
	$.post("<c:url value='/core/user/findByGh'/>", {gh:gh}, function(data){
		layer.open({
		    type: 1, //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
		    title: "岗位绑定", //默认“信息”
		    area: ['600px'], //宽
		    content: $("#div_edit"),
			end: function(){
				//无论是确认还是取消，只要层被销毁了，end都会执行，不携带任何参数。
				//$("#edit_form").reset(); //恢复默认值
				$("#edit_form").clear(); //全部清除
			},
			btn: ['保存', '取消'],
			btn1: function(index, layero){
				//按钮1的回调（保存）
				if(!$('#edit_form').valid()){
					return false;
				}
				$.post("<c:url value='/core/user/save'/>", $("#edit_form").getFormData(), function(result){
					var icon = 2;
					if(result.result == "000000"){
						icon = 1;
						layer.close(index);
						$("#grid").trigger("reloadGrid");
					}
					layer.alert(result.message, {
						shadeClose: true,
						title: "提示",
						icon: icon
					});
		        },"json");
			}
		});
		$("#edit_form").setFormData(data);
	},"json");
}
</script>
</html>