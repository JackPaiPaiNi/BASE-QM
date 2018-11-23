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
						</div>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal" id="search_form_menu">
							<input type="hidden" name="zt" value=1 />
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
						<h5>操作说明</h5>
						<div class="ibox-tools">
							<button id="btn_save" class="btn btn-primary btn-xs"><i class="fa fa-save"></i>&nbsp;保存</button>
						</div>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal" id="edit_form">
							<div class="ibox-form">
								<div class="row">
									<div class="col-md-12 col-sm-12">
										<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">菜单</span>
											<input type="hidden" name="id">
											<input type="text" name="cd" class="form-control skydisabled" disabled="disabled">
										</div>
									</div>
								</div>
								<h4 class="header">操作说明</h4>
								<div class="row">
									<div class="col-md-12 col-sm-12">
										<textarea name="czsm" class="form-control" rows="20"></textarea>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function() {
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
		url : "<c:url value='/core/menuCzsm/query'/>",
		rownumbers : false,
		colModel : [ 
		    {name : "id", index : "id", label : "ID", hidden : true, width : 80, frozen : true},
		    {name : "btn", index : "", label : "编辑", align : "center", width : 38, frozen : true, formatter : "btn", 
		    	formatoptions : {title : "编辑", btnClass : "btn-primary", icon : "fa-pencil-square-o", func : "edit", args : ["id","sfmj"], enable : {name : "sfmj", value : [1]}}},
		    {name : "zt", index : "zt", label : "状态", width : 45, formatter: "select", editoptions:{value:"${fns:loadDictJson('SJZT')}"}},
		    {name : "xh", index : "xh", label : "序号", width : 70},
		    {name : "tb", index : "tb", label : "图标", align : "center", width : 38, formatter : tbFormatter},
		    {name : "cd", index : "cd", label : "菜单", width : 300}
		],
		ondblClickRow: function (rowid, iRow, iCol, e){
			var rowData = $("#grid_menu").jqGrid("getRowData", rowid);
			if(rowData.sfmj == 1){
				edit(rowid, rowData.sfmj);
			}else{
				layer.alert("请选择一行末级菜单！", {
					shadeClose: true,
					title: "提示",
					icon: 0
				});
			}
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
	
	// 保存
	$("#btn_save").click(function(){
		save();
	});

});

function tbFormatter(cellValue, options, rowData){
	var tb = '<i class="'+cellValue+'"></i>';
	return tb;
}

function edit(id, sfmj){
	$.post("<c:url value='/core/menuCzsm/findById'/>", {id:id}, function(data){
		$("#edit_form").setFormData(data);
	},"json");
}

function save(){
	if(!$('#edit_form').valid()){
		return false;
	} 
	$.post("<c:url value='/core/menuCzsm/save'/>", $("#edit_form").getFormData(), function(result){
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
