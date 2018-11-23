<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/index/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/index/js.bootstrap.jsp" />
</head>
<body>
	<div class="wrapper wrapper-content animated fadeInRight full-height">
		<div class="row full-height">
			<div class="col-md-6 col-sm-6 full-height" style="padding-right: 0;">
				<div class="ibox full-height">
					<div class="ibox-title">
						<h5>岗位管理</h5>
						<div class="ibox-tools">
							<button id="btn_search" class="btn btn-primary btn-xs"><i class="fa fa-search"></i>&nbsp;查询</button>
							<button id="btn_export" class="btn btn-info btn-xs"><i class="fa fa-file-excel-o"></i>&nbsp;导出</button>
							<button id="btn_add" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>&nbsp;新增</button>
							<!-- <a class="collapse-link"> <i class="fa fa-chevron-up"></i></a> -->
						</div>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal" id="search_form">
							<div class="row ibox-form">
								<div class="col-md-6 col-sm-6">
									<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">岗位</span>
										<input type="hidden" name="oper" value="search">
										<input type="text" name="gw" class="form-control">
									</div>
								</div>
								<div class="col-md-6 col-sm-6">
									<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">状态</span>
										<select name="zt" class="form-control skyselect">
											${fns:loadDictOption('SJZT')}
										</select>
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
			<div class="col-md-6 col-sm-6 full-height">			
				<div class="ibox full-height">
					<div class="ibox-title">
						<h5>权限管理</h5>
						<div class="ibox-tools">
							<button id="btn_save_qx" class="btn btn-primary btn-xs"><i class="fa fa-save"></i>&nbsp;保存</button>
						</div>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal" id="search_form_qx">
							<input type="hidden" name="oper" value="search">
							<div class="row ibox-form">
								<div class="col-md-8 col-sm-8">
									<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">岗位</span>
										<input type="hidden" name="gwid">
										<input type="text" name="gw" class="form-control skydisabled" disabled="disabled">
									</div>
								</div>
							</div>
						</form>
						<div class="ibox-div" style="overflow-x:hidden;overflow-y:scroll;">
							<div id="menuTree" class="ztree"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="div_edit" class="wrapper wrapper-content animated fadeInRight" style="display:none;">
		<form class="form-horizontal" id="edit_form">
			<div class="row">
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">岗位</span>
						<input type="hidden" name="id">
						<input type="text" name="gw" class="form-control">
                    </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">状态</span>
						<select name="zt" class="form-control skyselect">
							${fns:loadDictOption('SJZT')}
						</select>
                   </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">备注信息</span>
						<input type="text" name="bzxx" class="form-control">
                   </div>
				</div>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
var menuSetting = {// zTree配置
		data : {
			key : {
				checked : "checked",
				name : "cd"// 显示字段
			},
			simpleData : {// 后台不用返回递归结构数据配置
				enable : true,
				idKey : "id", // ID字段
				pIdKey : "sjcdid", // 父节点ID字段
				rootPId : 0
			// 根节点的父节点默认值
			}
		},
		view : {
			dblClickExpand : false,
			selectedMulti : false // 无checkbox时不允许多选
		},
		check : {
			enable : true
		}
	};

$(function() {
	$(".skyselect").select2();
	
	var skyOptions = {
		parent :"#grid_parent", 
		pager : "#grid_pager",
		search : {
			form : "#search_form", 
			searchBtn : "#btn_search", 
			exportBtn : "#btn_export", 
			exportUrl : "<c:url value='/core/actor/export'/>", 
			exportName : "岗位信息表"
		}
	};
	$("#grid").skyGrid({
		sky : skyOptions,
		url : "<c:url value='/core/actor/search'/>",
		colModel : [ 
		    {name : "id", index : "id", label : "ID", hidden : true, width : 80},
		    {name : "btn", index : "", label : "编辑", align : "center", width : 38, formatter : "btn", 
		    	formatoptions : {title : "编辑", btnClass : "btn-primary", icon : "fa-pencil-square-o", func : "edit", args : ["id"]}},
			{name : "btn", index : "", label : "删除", align : "center", width : 38, formatter : "btn", 
			    	formatoptions : {title : "删除", btnClass : "btn-danger", icon : "fa-times", func : "del", args : ["id"]}},
		    {name : "zt", index : "zt", label : "状态", width : 45, formatter: "select", editoptions:{value:"${fns:loadDictJson('SJZT')}"}},
		    {name : "gw", index : "gw", label : "岗位", width : 200},
		    {name : "bzxx", index : "bzxx", label : "备注信息", width : 120}
		],
		ondblClickRow : function (rowid, iRow, iCol, e){
			var rowData = $("#grid").jqGrid("getRowData", rowid);
			$("#search_form_qx [name=gwid]").val(rowData.id);
			$("#search_form_qx [name=gw]").val(rowData.gw);
			$.post("<c:url value='/core/actor/findMenuQx'/>", {gwid : rowData.id}, function(data) {
				$.fn.zTree.init($("#menuTree"), menuSetting, data);
			}, "json");
		},
		sortname : 'id',
		sortorder : 'asc'
	});
	
	// 新增岗位
	$("#btn_add").click(function(){
		add();
	});
	
	//岗位校验
	$('#edit_form').validate({
		rules: {
			gw : 'required',
			zt: 'required'			
		},
		messages: {
			gw:'岗位不能为空！',
		    zt :'状态不能为空！',
		}
	});
	
	// 权限保存
	$("#btn_save_qx").click(function(){
		saveQx();
	});
	
});

function add(){
	layer.open({
	    type: 1, //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
	    title: "新增岗位", //默认“信息”
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
			$.post("<c:url value='/core/actor/save'/>", $("#edit_form").getFormData(), function(result){
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


function edit(id){
	$.post("<c:url value='/core/actor/findById'/>", {id:id}, function(data){
		add();
		$("#edit_form").setFormData(data);
	},"json");
}

function del(id){
	layer.confirm('确定要删除吗？', {
		title: "确认信息", //默认“信息”
	    btn: ['确定','取消'], // 按钮
	    icon: 0
	}, function(){
		$.post("<c:url value='/core/actor/delete'/>", {id:id}, function(result){
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

//获取选中的菜单权限节点
function getCheckedPermission(){
	var zTree = $.fn.zTree.getZTreeObj("menuTree");
	var checkedNode = zTree.getCheckedNodes() || [];
	var value = new Array();
	var menus = "";
	var permissions = "";
	for (var i = 0; i < checkedNode.length; i++) {
		if(checkedNode[i].lx == 0){
			if (menus != ''){
				menus += ',';
			}
			menus += checkedNode[i].id;
		} else {
			if (permissions != ''){
				permissions += ',';
			}
			permissions += checkedNode[i].id;
		}
	}
	value.push(menus);
	value.push(permissions);
	return value;
}

// 权限保存
function saveQx(){
	var gwid = $("#search_form_qx [name=gwid]").val();
	var qxArray = getCheckedPermission();
	var menus = qxArray[0];
	var permissions = qxArray[1];
	$.post("<c:url value='/core/actor/saveQx'/>", {gwid:gwid, cdid:menus, qxid:permissions}, function(result){
		var icon = 2;
		if(result.result == "000000"){
			icon = 1;
		}
		layer.alert(result.message, {
			shadeClose: true,
			title: "提示",
			icon: icon
		});
    },"json");
}
</script>
</html>
