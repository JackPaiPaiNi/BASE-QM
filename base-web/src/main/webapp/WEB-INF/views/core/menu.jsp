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
			<div class="col-md-7 col-sm-7 full-height" style="padding-right: 0;">				
				<div class="ibox full-height">
					<div class="ibox-title">
						<h5>菜单树</h5>
						<div class="ibox-tools">
							<button id="btn_search_menu" class="btn btn-primary btn-xs"><i class="fa fa-search"></i>&nbsp;查询</button>
							<button id="btn_menu_add" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>&nbsp;新增一级菜单</button>
						</div>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal" id="search_form_menu">
							<div class="row ibox-form">
								<div class="col-md-6 col-sm-6">
									<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">菜单</span>
										<input type="text" placeholder="菜单名称" name="cd" class="form-control">
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
						<div class="ibox-grid" id="grid_parent_menu">
							<table id="grid_menu"></table>
							<div id="grid_pager_menu"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-5 col-sm-5 full-height">			
				<div class="ibox full-height">
					<div class="ibox-title">
						<h5>操作权限</h5>
						<div class="ibox-tools">
							<button id="btn_permission_add" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>&nbsp;新增权限</button>
						</div>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal" id="search_form_permission">
							<div class="row ibox-form">
								<div class="col-md-8 col-sm-8">
									<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">菜单</span>
										<input type="hidden" name="sfmj">
										<input type="hidden" name="cdid">
										<input type="text" name="cd" class="form-control skydisabled" disabled="disabled">
									</div>
								</div>
							</div>
						</form>
						<div class="ibox-grid" id="grid_parent_permission">
							<table id="grid_permission"></table>
							<!-- <div id="grid_pager_permission"></div> -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 菜单编辑框 -->
	<div id="div-editMenu" class="wrapper wrapper-content animated fadeInRight" style="display:none;">
		<form class="form-horizontal" id="edit_formMenu">
			<div class="row">
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">菜单</span>
						<input type="hidden" name="id">
						<input type="text" placeholder="菜单名称" name="cd" class="form-control">
                    </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">上级菜单</span>
						<input type="hidden" name="sjcdid" class="form-control">
						<input type="text" name="sjcd" class="form-control">
                    </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">序号</span>
						<input type="text" name="xh" class="form-control">
                   </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">链接地址</span>
						<input type="text" name="ljdz" class="form-control">
                   </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">是否不控制权限</span>
						<select name="sfbkzqx" class="form-control skyselect">
							${fns:loadDictDefOption('YESNO', '0')}
						</select>
                   </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">图标</span>
						<input type="text" name="tb" class="form-control">
                   </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">状态</span>
						<select name="zt" class="form-control skyselect">
							${fns:loadDictDefOption('SJZT', '1')}
						</select>
                   </div>
				</div>
			</div>
		</form>
	</div>
	
	<!-- 菜单权限编辑框 -->
	<div id="div-editPermission" class="wrapper wrapper-content animated fadeInRight" style="display:none;">
		<form class="form-horizontal" id="edit_formPermission">
			<div class="row">
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">菜单</span>
						<input type="hidden" name="id">
						<input type="hidden" name="cdid">
						<input type="text" name="cd" class="form-control skydisabled" disabled="disabled">
                    </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">权限编码</span>
						<input type="text" name="qxbm" class="form-control">
                    </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">权限</span>
						<input type="text" name="qx" class="form-control">
                   </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">序号</span>
						<input type="text" name="xh" class="form-control">
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
$(function() {
	// select2控件绑定
    $(".skyselect").select2();
	
	// 菜单
	var skyOptions_menu = {
		parent :"#grid_parent_menu", 
		pager : "#grid_pager_menu",
		frozen : true,
		search : {
			form : "#search_form_menu", 
			searchBtn : "#btn_search_menu"
		}
	};
	$("#grid_menu").skyGrid({
		sky : skyOptions_menu,
		url : "<c:url value='/core/menu/search'/>",
		rownumbers : false,
		colModel : [ 
		    {name : "id", index : "id", label : "ID", hidden : true, width : 80, frozen : true},
		    {name : "btn", index : "", label : "编辑", align : "center", width : 38, frozen : true, formatter : "btn", 
		    	formatoptions : {title : "编辑", btnClass : "btn-primary", icon : "fa-pencil-square-o", func : "editMenu", args : ["id"]}},
		    {name : "btn", index : "", label : "删除", align : "center", width : 38, frozen : true, formatter : "btn", 
			    	formatoptions : {title : "删除", btnClass : "btn-danger", icon : "fa-times", func : "delMenu", args : ["id"]}},
		    {name : "btn", index : "", label : "子菜单", align : "center", width : 50, frozen : true, formatter : "btn", 
				    	formatoptions : {title : "子菜单", btnClass : "btn-success", icon : "fa-plus", func : "addMenu", args : ["id", "cd"]}},
		    {name : "zt", index : "zt", label : "状态", width : 45, formatter: "select", editoptions:{value:"${fns:loadDictJson('SJZT')}"}},
		    {name : "xh", index : "xh", label : "序号", width : 70},
		    {name : "tb", index : "tb", label : "图标", align : "center", width : 38, formatter : tbFormatter},
		    {name : "cd", index : "cd", label : "菜单", width : 200},
		    {name : "ljdz", index : "ljdz", label : "链接地址", width : 130},
		    {name : "sjcdid", index : "sjcdid", label : "上级菜单ID", hidden : true, width : 80},
		    {name : "sfmj", index : "sfmj", label : "是否末级", hidden : true, width : 80},
		    {name : "sfbkzqx", index : "sfbkzqx", label : "是否不控制权限", hidden : true, width : 80}
		],
		ondblClickRow: function (rowid, iRow, iCol, e){
			var rowData = $("#grid_menu").jqGrid("getRowData", rowid);
			$("#search_form_permission [name=cdid]").val(rowid);
			$("#search_form_permission [name=cd]").val(rowData.cd);
			$("#search_form_permission [name=sfmj]").val(rowData.sfmj);
			reloadPermission();
		},
		/*--------------------treeGrid开始--------------------*/
		treeGrid : true,
		treeGridModel: 'adjacency', //treeGrid模式，跟json元数据有关 
		tree_root_level : 0,
		ExpandColClick : true,
		ExpandColumn : 'cd',
		treeReader : {
			level_field : "level",
			parent_id_field : "sjcdid",
			leaf_field : "sfmj",
			expanded_field : "open"
		},
		treeIcons : {
			plus : "fa fa-folder",
			minus : "fa fa-folder-open",
			leaf : "fa fa-file"
		},
		/*--------------------treeGrid结束--------------------*/
		sortname : "xh",
		sortorder : "asc",
		gridComplete : function(){
			// 默认关闭，只显示一级
			var rootRows = $("#grid_menu").jqGrid('getRootNodes');
			for (var i = 0; i < rootRows.length; i++) {
				$("#grid_menu").jqGrid('collapseNode', rootRows[i]); 
				$("#grid_menu").jqGrid('collapseRow', rootRows[i]); 
			}
		}
	});
	
	// 权限
	var skyOptions_permission = {
		parent :"#grid_parent_permission"
	};
	$("#grid_permission").skyGrid({
		sky : skyOptions_permission,
		datatype : "json",
		colModel : [ 
		    {name : "id", index : "id", label : "ID", hidden : true, width : 80},
		    {name : "btn", index : "", label : "编辑", align : "center", width : 38, frozen : true, formatter : "btn", 
		    	formatoptions : {title : "编辑", btnClass : "btn-primary", icon : "fa-pencil-square-o", func : "editPermission", args : ["id"]}},
		    {name : "btn", index : "", label : "删除", align : "center", width : 38, frozen : true, formatter : "btn", 
			    	formatoptions : {title : "删除", btnClass : "btn-danger", icon : "fa-times", func : "delPermission", args : ["id"]}},
		    {name : "xh", index : "xh", label : "序号", width : 38},
		    {name : "cdid", index : "cdid", label : "菜单ID", hidden : true, width : 80},
		    {name : "qxbm", index : "qxbm", label : "权限编码", width : 160},
		    {name : "qx", index : "qx", label : "权限", width : 50},
		    {name : "bzxx", index : "bzxx", label : "备注信息", width : 150}
		]
	});
	
	// 菜单新增（一级菜单）
	$("#btn_menu_add").click(function(){
		addMenu(0, null);
	});
	
	// 权限新增
	$("#btn_permission_add").click(function(){
		addPermission();
	});
	
	//菜单校验
	$('#edit_formMenu').validate({
		rules: {
			cd : 'required',
			sjcdid : 'required',
			sfmj :  'required',
			zt: 'required'
		},
		messages: {
			cd:'菜单不能为空！',
			sjcdid : '上级菜单不能为空！',
			sfmj : '上级菜单不能为空！',
			zt : '状态不能为空！'
		}
	});
	
	//菜单权限校验
	$('#edit_formPermission').validate({
		rules: {
			cdid : 'required',
			qxbm : 'required',
			qx : 'required'
		},
		messages: {
			cdid:'菜单不能为空！',
			qxbm : '权限编码不能为空！',
			qx : '权限不能为空！'
		}
	});
	
	$('#edit_formMenu [name=sjcd]').skyTree({
		title : "菜单选择",
		returnLeaf : true,
		url : "<c:url value='/core/menu/query'/>",
		multiple : false,
		tree : {
			name : "cd",//显示字段
			idKey: "id", //ID字段
			pIdKey: "sjcdid", //父节点ID字段
			rootPId: 0//根节点的父节点默认值
		},
		confirm : function(data, value, text){
			$('#edit_formMenu [name=sjcdid]').val(data.id);
			$('#edit_formMenu [name=sjcd]').val(data.cd);
		}
	});

});

/**
 * 菜单树管理（js代码）
 */

function tbFormatter(cellValue, options, rowData){
	var tb = '<i class="'+cellValue+'"></i>';
	return tb;
}

function addMenu(sjcdid, sjcd){
	if(sjcdid != null){
		$("#edit_formMenu [name=sjcdid]").val(sjcdid);
		$("#edit_formMenu [name=sjcd]").val(sjcd);
		$("#edit_formMenu [name=sfbkzqx]").select2("val", 0);
		$("#edit_formMenu [name=zt]").select2("val", 1);
		//$('#edit_formMenu [name=sjcd]').skyTree("disable");
	}
	layer.open({
	    type: 1, //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
	    title: "菜单信息", //默认“信息”
	    area: ['600px'], //宽
	    content: $("#div-editMenu"),
	    end: function(){
			//无论是确认还是取消，只要层被销毁了，end都会执行，不携带任何参数。
			//$("#edit_formMenu").reset(); //恢复默认值
			$("#edit_formMenu").clear(); //全部清除
		},
		btn: ['保存', '取消'],
		btn1: function(index, layero){
			if(!$('#edit_formMenu').valid()){
				return false;
			} 
			//按钮1的回调（保存）
			$.post("<c:url value='/core/menu/save'/>", $("#edit_formMenu").getFormData(), function(result){
				var icon = 2;
				if(result.result == "000000"){
					icon = 1;
					layer.close(index);
					$("#grid_menu").trigger("reloadGrid");
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

function editMenu(id){
	$.post("<c:url value='/core/menu/findById'/>", {id:id}, function(data){
		addMenu(null, null);
		$('#edit_formMenu [name=sjcd]').skyTree("enable");
		$("#edit_formMenu").setFormData(data);
		$('#edit_formMenu [name=sjcd]').skyTree("setValue", data.sjcdid);
	},"json");
}

function delMenu(id){
	layer.confirm('确定要删除吗？', {
		title: "确认信息", //默认“信息”
	    btn: ['确定','取消'], // 按钮
	    icon: 0
	}, function(){
		$.post("<c:url value='/core/menu/delete'/>", {id:id}, function(result){
			var icon = 2;
			if(result.result == "000000"){
				icon = 1;
				$("#grid_menu").trigger("reloadGrid");
			}
			layer.alert(result.message, {
				shadeClose: true,
				title: "提示",
				icon: icon
			});
	    },"json");
	});
}

/**
 * 菜单权限管理（js代码）
 */
//重新加载权限数据
function reloadPermission(){
	var cdid = $("#search_form_permission [name=cdid]").val();
	$.post("<c:url value='/menu/permission/search'/>", {cdid : cdid, sort : "xh", order : "ASC"}, function(data){
		$("#grid_permission")[0].addJSONData({rows : data.rows});
	}, "json");
}

function addPermission(){
	if($("#search_form_permission [name=sfmj]").val() != "1" || $("#search_form_permission [name=cdid]").val() == ""){
		layer.alert("请在左侧先选择一个末级菜单！", {
			shadeClose: true,
			title: "提示",
			icon: 2
		});
		return;
	}
	$("#edit_formPermission [name=cdid]").val($("#search_form_permission [name=cdid]").val());
	$("#edit_formPermission [name=cd]").val($("#search_form_permission [name=cd]").val());
	layer.open({
	    type: 1, //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
	    title: "操作权限信息", //默认“信息”
	    area: ['600px'], //宽
	    content: $("#div-editPermission"),
	    end: function(){
			//无论是确认还是取消，只要层被销毁了，end都会执行，不携带任何参数。
			//$("#edit_formPermission").reset(); //恢复默认值
			$("#edit_formPermission").clear(); //全部清除
		},
		btn: ['保存', '取消'],
		btn1: function(index, layero){
			//按钮1的回调（保存）
			if(!$('#edit_formPermission').valid()){
				return false;
			} 
			$.post("<c:url value='/menu/permission/save'/>", $("#edit_formPermission").getFormData(), function(result){
				var icon = 2;
				if(result.result == "000000"){
					icon = 1;
					layer.close(index);
					reloadPermission();
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

function editPermission(id){
	$.post("<c:url value='/menu/permission/findById'/>", {id:id}, function(data){
		addPermission();
		$("#edit_formPermission").setFormData(data);
		$("#edit_formPermission [name=cd]").val($("#search_form_permission [name=cd]").val());
	},"json");
}

function delPermission(id){
	layer.confirm('确定要删除吗？', {
		title: "确认信息", //默认“信息”
	    btn: ['确定','取消'], // 按钮
	    icon: 0
	}, function(){
		$.post("<c:url value='/menu/permission/delete'/>", {id:id}, function(result){
			var icon = 2;
			if(result.result == "000000"){
				icon = 1;
				reloadPermission();
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
