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
				<h5>用户兼职信息绑定</h5>
				<div class="ibox-tools">
					<button id="btn_search" class="btn btn-primary btn-xs"><i class="fa fa-search"></i>&nbsp;查询</button>
					<button id="btn_add" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>&nbsp;新增</button>
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
						<input type="text" name="gh" class="form-control">
                    </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">姓名</span>
						<input type="text" name="xm" class="form-control skydisabled" disabled="disabled">
                   </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">岗位</span>
						<input type="text" name="gw" class="form-control skydisabled" disabled="disabled">
                    </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">部门</span>
						<input type="text" name="bm" class="form-control skydisabled" disabled="disabled">
                    </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">兼职岗位</span>
						<input type="text" name="jzgwmc" class="form-control">
						<input type="hidden" name="jzgwid" class="form-control">
                    </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">兼职部门</span>
						<input type="text" name="jzbmmc" class="form-control">
						<input type="hidden" name="jzbmid" class="form-control">
                    </div>
				</div>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
$(function() {
	$(".skyselect").select2();
	// 用户兼职信息绑定
	var skyOptions = {
		parent :"#grid_parent", 
		pager : "#grid_pager",
		//frozen : true,
		search : {
			form : "#search_form", 
			searchBtn : "#btn_search"
		}
	};
	// 初始化表单
	$("#grid").skyGrid({
		sky : skyOptions,
		url : "<c:url value='/mdm/yhjzxx/search'/>",
		sortname: 'zdsj',
		sortorder: 'desc',
		colModel : [ 
            {name : "id", index : "id", label : "ID", frozen : true,  width : 80,hidden:true},
            {name : "btn", index : "", label : "取消",  frozen : true, align : "center", width : 38, formatter : "btn",
	        	formatoptions : {title : "【编辑】非有效状态数据不允许取消！", btnClass : "btn-danger", icon : "fa fa-times", func : "cancel", args : ["id","sjc"], enable : {name : "zt", value : [1]}, text : ""}},
	        {name : "gh", index : "gh", label : "工号", width : 120},
		    {name : "xm", index : "xm", label : "姓名", width : 120},
		    {name : "jzgwmc", index : "jzgwmc", label : "兼职岗位名称", width : 120},
		    {name : "jzbmmc", index : "jzbmmc", label : "兼职部门名称", width : 120},
		    {name : "zdr", index : "zdr", label : "制单人", width : 60},
		    {name : "zdsj", index : "zdsj",  label : "制单时间", width : 140},
		    {name : "zt", index : "zt", label : "状态", width : 45, hidden : true},
		    {name : "ztmc", index : "ztmc", label : "状态名称", width : 80},
		    {name : "sjc", index : "sjc",  label : "时间戳", width :800, hidden : true},
		   
		]
	});
	//新增
	$("#btn_add").click(function(){
		edit();
	});
	//用户查询
	$("#edit_form [name=gh]").skySelect({
		title : "用户选择",
		multiple : false,
		gridMode : false,
		url : "<c:url value='/core/user/query'/>",
		valueColumn : "gh",
		textColumn : "xm",
		valueElem : "#edit_form [name=gh]",
		textElem : "#edit_form [name=xm]",
		autoComplete : true, // 自动完成
		displayText: ["gh", "xm", "gw", "bm"], //自动完成下拉框显示字段
		columns : [ {field : "gh", title : "工号", search : true}, 
		            {field : "xm", title : "姓名", search : true},
		            {field : "bm", title : "部门", search : false},
		            {field : "hrgw", title : "岗位", search : false}],
        confirm : function(data, value, text){
        	$("#edit_form [name=gh]").val(data.gh);
        	$("#edit_form [name=xm]").val(data.xm);
        	$("#edit_form [name=bm]").val(data.bm);
        	$("#edit_form [name=gw]").val(data.hrgw);
		},
	});
	//兼职岗位查询
	$("#edit_form [name=jzgwmc]").skySelect({
		title : "兼职岗位选择",
		multiple : false,
		gridMode : false,
		url : "<c:url value='/core/actor/query'/>",
		valueColumn : "id",
		textColumn : "gw",
		valueElem : "#edit_form [name=jzgwid]",
		textElem : "#edit_form [name=jzgwmc]",
		autoComplete : true, // 自动完成
		displayText: ["jzgwid", "jzgwmc"], //自动完成下拉框显示字段
		columns : [ {field : "id", title : "岗位ID", search : false}, 
		            {field : "gw", title : "岗位名称", search : true} ]
	});
	//兼职部门查询
	$("#edit_form [name=jzbmmc]").skySelect({
		title : "兼职部门选择",
		multiple : false,
		gridMode : false,
		url : "<c:url value='/mdm/yhjzxx/jzbmQuery'/>",
		valueColumn : "bmid",
		textColumn : "bm",
		valueElem : "#edit_form [name=jzbmid]",
		textElem : "#edit_form [name=jzbmmc]",
		autoComplete : true, // 自动完成
		displayText: ["jzbmid", "jzbmmc"], //自动完成下拉框显示字段
		columns : [ {field : "bmid", title : "部门ID", search : false}, 
		            {field : "bm", title : "部门名称", search : true} ]
	});
});

function edit(){
	layer.open({
	    type: 1, //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
	    title: "兼职信息绑定", //默认“信息”
	    area: ['600px'], //宽
	    content: $("#div_edit"),
		end: function(){
			//无论是确认还是取消，只要层被销毁了，end都会执行，不携带任何参数。
			//$("#edit_form").reset(); //恢复默认值
			$("#edit_form").clear(); //全部清除
		},
		btn: ['提交', '取消'],
		btn1: function(index, layero){
			//按钮1的回调（保存）
			if(!$('#edit_form').valid()){
				return false;
			}
			$.post("<c:url value='/mdm/yhjzxx/save'/>", $("#edit_form").getFormData(), function(result){
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
}
//取消
function cancel(id,sjc){
	layer.confirm('确定要取消吗？', {
		title: "确认信息", //默认“信息”
	    btn: ['确定','取消'], // 按钮
	    icon: 3
	}, function(){
		$.post("<c:url value='/mdm/yhjzxx/cancel'/>", {id:id,sjc:sjc}, function(result){
			var icon = 2;
			if(result.result == "000000"){
				icon = 1;
				$("#grid").trigger("reloadGrid");
			}
			layer.alert(result.message, {
				shadeClose: true,
				title: "提示",
				icon: icon
			});
	    },"json");
	});
}
</script>
</html>
