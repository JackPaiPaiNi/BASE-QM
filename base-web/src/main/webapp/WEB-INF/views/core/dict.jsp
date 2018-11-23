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
			<div class="col-md-5 col-sm-5 full-height" style="padding-right: 0;">				
				<div class="ibox full-height">
					<div class="ibox-title">
						<h5>字典类型</h5>
						<div class="ibox-tools">
							<button id="btn_search_type" class="btn btn-primary btn-xs"><i class="fa fa-search"></i>&nbsp;查询</button>
							<button id="btn_type_add" class="btn btn-success btn-xs"><i class="fa fa-plus"></i>&nbsp;新增</button>
						</div>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal" id="search_form_type">
							<input type="hidden" name="oper" value="search">
							<div class="row ibox-form">
								<div class="col-md-6 col-sm-6">
									<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">字典类型</span>
										<input type="text" name="zdlx" class="form-control">
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
						<div class="ibox-grid" id="grid_parent_type">
							<table id="grid_type"></table>
							<div id="grid_pager_type"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-7 col-sm-7 full-height">			
				<div class="ibox full-height">
					<div class="ibox-title">
						<h5>数据字典值集</h5>
						<div class="ibox-tools">
							<button id="btn_search_dict" class="btn btn-primary btn-xs"><i class="fa fa-search"></i>&nbsp;查询</button>
						</div>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal" id="search_form_dict">
							<input type="hidden" name="oper" value="search">
							<div class="row ibox-form">
								<div class="col-md-6 col-sm-6">
									<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">字典类型</span>
										<input type="hidden" name="zdlxbm">
										<input type="text" placeholder="字典类型" name="zdlx" class="form-control">
									</div>
								</div>
								<div class="col-md-6 col-sm-6">
									<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">编码/名称</span>
										<input type="text" placeholder="编码/名称" name="mc" class="form-control">
									</div>
								</div>
							</div>
						</form>
						<div class="ibox-grid" id="grid_parent_dict">
							<table id="grid_dict"></table>
							<div id="grid_pager_dict"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 数据字典类型编辑框 -->
	<div id="div-editType" class="wrapper wrapper-content animated fadeInRight" style="display:none;">
		<form class="form-horizontal" id="edit_formType">
			<div class="row">
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">字典类型编码</span>
						<input type="hidden" name="id">
						<input type="text" name="zdlxbm" class="form-control">
                    </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">字典类型</span>
						<input type="text" name="zdlx" class="form-control">
                    </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">备注信息</span>
						<input type="text" name="bzxx" class="form-control">
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
	
	<!-- 数据字典编辑框 -->
	<div id="div-editDict" class="wrapper wrapper-content animated fadeInRight" style="display:none;">
		<form class="form-horizontal" id="edit_formDict">
			<div class="row">
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">编码</span>
						<input type="hidden" name="id">
						<input type="text" name="bm" class="form-control">
                    </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">名称</span>
						<input type="text" name="mc" class="form-control">
                    </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">字典类型</span>
						<input type="hidden" name="zdlxbm">
						<input type="text" name="zdlx" class="form-control skydisabled" disabled="disabled">
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
</body>
<script type="text/javascript">
$(function() {
	// select2控件绑定
    $(".skyselect").select2();
	
	// 字典类型
	var skyOptions_type = {
		parent :"#grid_parent_type", 
		pager : "#grid_pager_type",
		search : {
			form : "#search_form_type", 
			searchBtn : "#btn_search_type"
		}
	};
	$("#grid_type").skyGrid({
		sky : skyOptions_type,
		url : "<c:url value='/dict/dictType/search'/>",
		colModel : [ 
		    {name : "id", index : "id", label : "ID", hidden : true, width : 80},
		    {name : "btn", index : "", label : "编辑", align : "center", width : 38, formatter : "btn", 
		    	formatoptions : {title : "编辑", btnClass : "btn-primary", icon : "fa-pencil-square-o", func : "editType", args : ["id"]}},
		    {name : "btn", index : "", label : "删除", align : "center", width : 38, formatter : "btn", 
		    	formatoptions : {title : "删除", btnClass : "btn-danger", icon : "fa-times", func : "delType", args : ["id"]}},
		    {name : "btn", index : "", label : "值集", align : "center", width : 38, formatter : "btn", 
			    	formatoptions : {title : "添加值集", btnClass : "btn-success", icon : "fa-plus", func : "addDict", args : ["zdlxbm", "zdlx"]}},
		    {name : "zdlxbm", index : "zdlxbm", label : "字典类型编码", width : 80},
		    {name : "zdlx", index : "zdlx", label : "字典类型", width : 120},
		    {name : "zt", index : "zt", label : "状态", width : 45, formatter: "select", editoptions:{value:"${fns:loadDictJson('SJZT')}"}},
		    {name : "bzxx", index : "bzxx", label : "备注信息"}
		],
		ondblClickRow: function (rowid, iRow, iCol, e){
			var rowData = $("#grid_type").jqGrid("getRowData", rowid);
			$("#search_form_dict [name=zdlxbm]").val(rowData.zdlxbm);
			$("#search_form_dict [name=zdlx]").val(rowData.zdlx);
			$("#grid_dict").setGridParam({datatype : "json"}).trigger("reloadGrid", [{page : 1}]);
			$("#search_form_dict [name=zdlxbm]").val("");
		},
		sortname : "zdlxbm",
		sortorder : "asc"
	});
	
	// 数据字典
	var skyOptions_dict = {
		parent :"#grid_parent_dict", 
		pager : "#grid_pager_dict",
		search : {
			form : "#search_form_dict",
			searchBtn : "#btn_search_dict"
		}
	};
	$("#grid_dict").skyGrid({
		sky : skyOptions_dict,
		url : "<c:url value='/core/dict/search'/>",
		datatype : "local",
		colModel : [ 
		    {name : "id", index : "id", label : "ID", hidden : true, width : 80},
		    {name : "btn", index : "", label : "编辑", align : "center", width : 38, formatter : "btn", 
		    	formatoptions : {title : "编辑", btnClass : "btn-primary", icon : "fa-pencil-square-o", func : "editDict", args : ["id"]}},
		    {name : "btn", index : "", label : "删除", align : "center", width : 38, formatter : "btn", 
			    	formatoptions : {title : "删除", btnClass : "btn-danger", icon : "fa-times", func : "delDict", args : ["id","zdlxbm"]}},
		    {name : "bm", index : "bm", label : "编码", width : 50},
		    {name : "mc", index : "mc", label : "名称", width : 150},
		    {name : "xh", index : "xh", label : "序号", width : 50},
		    {name : "zdlxbm", index : "zdlxbm", label : "字典类型编码", hidden : true, width : 90},
		    {name : "zdlx", index : "zdlx", label : "字典类型", width : 120},
		    {name : "zt", index : "zt", label : "状态", width : 45, formatter: "select", editoptions:{value:"${fns:loadDictJson('SJZT')}"}},
		    {name : "bzxx", index : "bzxx", label : "备注信息"}
		]
	});
	
	// 字典类型新增
	$("#btn_type_add").click(function(){
		addType();
	});
	
	//字典类型校验
	$('#edit_formType').validate({
		rules: {
			zdlxbm : 'required',
			zdlx: 'required',
			zt : 'required'
		},
		messages: {
			zdlxbm : '字典了类型编码不能为空！',
			zdlx : '字典类型编码不能为空！',
			zt : '状态不能为空！'
		}
	}); 
	
	//字典校验
	$('#edit_formDict').validate({
		rules: {
			bm : 'required',
			mc: 'required',
			zdlxbm : 'required',
			zt : 'required'
		},
		messages: {
			bm : '编码不能为空！',
			mc: '名称不能为空！',
			zdlxbm : '字典了类型编码不能为空！',
			zt : '状态不能为空！'
		}
	});  
});

/**
 * 字典类型管理（js代码）
 */
function addType(){
	layer.open({
	    type: 1, //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
	    title: "字典类型信息", //默认“信息”
	    area: ['600px'], //宽
	    content: $("#div-editType"),
	    end: function(){
			//无论是确认还是取消，只要层被销毁了，end都会执行，不携带任何参数。
			//$("#edit_formType").reset(); //恢复默认值
			$("#edit_formType").clear(); //全部清除
		},
		btn: ['保存', '取消'],
		btn1: function(index, layero){
			//按钮1的回调（保存）
			if(!$('#edit_formType').valid()){
				return false;
			} 
			//按钮1的回调（保存）
			$.post("<c:url value='/dict/dictType/save'/>", $("#edit_formType").getFormData(), function(result){
				var icon = 2;
				if(result.result == "000000"){
					icon = 1;
					layer.close(index);
					$("#grid_type").trigger("reloadGrid");
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

function editType(id){
	$.post("<c:url value='/dict/dictType/findById'/>", {id:id}, function(data){
		addType();
		$("#edit_formType").setFormData(data);
	},"json");
}

function delType(id){
	layer.confirm('确定要删除吗？', {
		title: "确认信息", //默认“信息”
	    btn: ['确定','取消'], // 按钮
	    icon: 0
	}, function(){
		$.post("<c:url value='/dict/dictType/delete'/>", {id:id}, function(result){
			var icon = 2;
			if(result.result == "000000"){
				icon = 1;
				$("#grid_type").trigger("reloadGrid");
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
 * 数据字典管理（js代码）
 */
function addDict(zdlxbm, zdlx){
	// 字典类型点击添加数据时
	if(zdlxbm != null && zdlx != null){
		$("#edit_formDict [name=zdlxbm]").val(zdlxbm);
		$("#edit_formDict [name=zdlx]").val(zdlx);
	}
	layer.open({
	    type: 1, //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
	    title: "数据字典信息", //默认“信息”
	    area: ['600px'], //宽
	    content: $("#div-editDict"),
	    end: function(){
			//无论是确认还是取消，只要层被销毁了，end都会执行，不携带任何参数。
			//$("#edit_formDict").reset(); //恢复默认值
			$("#edit_formDict").clear(); //全部清除
		},
		btn: ['保存', '取消'],
		btn1: function(index, layero){
			//按钮1的回调（保存）
			if(!$('#edit_formDict').valid()){
				return false;
			}
			$.post("<c:url value='/core/dict/save'/>", $("#edit_formDict").getFormData(), function(result){
				var icon = 2;
				if(result.result == "000000"){
					icon = 1;
					layer.close(index);
					$("#grid_dict").trigger("reloadGrid");
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

function editDict(id){
	$.post("<c:url value='/core/dict/findById'/>", {id:id}, function(data){
		addDict(null, null);
		$("#edit_formDict").setFormData(data);
	},"json");
}

function delDict(id, zdlxbm){
	layer.confirm('确定要删除吗？', {
		title: "确认信息", //默认“信息”
	    btn: ['确定','取消'], // 按钮
	    icon: 0
	}, function(){
		$.post("<c:url value='/core/dict/delete'/>", {id:id, zdlxbm:zdlxbm}, function(result){
			var icon = 2;
			if(result.result == "000000"){
				icon = 1;
				$("#grid_dict").trigger("reloadGrid");
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
