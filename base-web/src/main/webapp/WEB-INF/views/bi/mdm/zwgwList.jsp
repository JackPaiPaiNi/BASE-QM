<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/index/js.bootstrap.jsp" />
</head>
<body>
	<div class="wrapper wrapper-content animated fadeInRight full-height">
		<div class="ibox full-height">
			<div class="ibox-title">
				<h5>职务岗位管理</h5>
				<div class="ibox-tools">
					<button id="btn_search" class="btn btn-primary btn-xs"><i class="fa fa-search"></i>&nbsp;查询</button>
					<button id="btn_export" class="btn btn-info btn-xs"><i class="fa fa-file-excel-o"></i>&nbsp;导出</button>
				</div>
			</div>
			<div class="ibox-content">
				<form class="form-horizontal" id="search_form">
					<div class="row ibox-form">
						<div class="col-md-3 col-sm-3">
							<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">部门类型</span>
								<select name="bmjc" class="form-control skyselect">
									<option value="" selected="selected">--请选择--</option>
									<option role="option" value="总部">总部</option>
									<option role="option" value="分公司">分公司</option>
									<option role="option" value="办事处">办事处</option>
								</select>
							</div>
						</div>
						<div class="col-md-3 col-sm-3">
							<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">职务/岗位</span>
								<input type="text" placeholder="职务/岗位" name="gw" class="form-control">
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
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">部门类型</span>
						<input type="text" placeholder="部门类型" name="bmjc" class="form-control skydisabled" disabled="disabled">
                    </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">职务</span>
						<input type="text" placeholder="职务" name="hrzw" class="form-control skydisabled" disabled="disabled">
                    </div>
				</div>
				<div class="col-md-6 col-sm-6">
					<div class="input-group input-group-sm m-b-sm"><span class="input-group-addon">岗位</span>
						<input type="hidden" name="gwid" class="form-control">
						<input type="text" placeholder="岗位" name="gw" class="form-control">
                    </div>
				</div>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
$(function() {
	$(".skyselect").select2();
	
	var skyOptions = {
		parent :"#grid_parent", 
		pager : "#grid_pager",
		search : {
			form : "#search_form", 
			searchBtn : "#btn_search", 
			exportBtn : "#btn_export", 
			exportUrl : "<c:url value='/mdm/zwgw/export'/>", 
			exportName : "岗位信息表"
		}
	};

	$("#grid").skyGrid({
		sky : skyOptions,
		url : "<c:url value='/mdm/zwgw/search'/>",
		colModel : [ 
		    {name : "id", index : "id", label : "ID", hidden : true, width : 80},
		    {name : "btn", index : "", label : "编辑", align : "center", width : 38, formatter : "btn",
				formatoptions : {title : "编辑", btnClass : "btn-primary", icon : "fa-pencil-square-o", func : "edit", args : ["bmjc", "hrzw", "gwid"], text : ""}},
			{name : "btn", index : "", label : "删除", align : "center", width : 38, formatter : "btn",
				formatoptions : {title : "删除", btnClass : "btn-danger", icon : "fa-times", func : "del", args : ["bmjc", "hrzw"], text : ""}},
			{name : "bmjc", index : "bmjc", label : "部门类型", width : 100},
		    {name : "hrzw", index : "hrzw", label : "HR职务", width : 200},
		    {name : "gwid", index : "gwid", label : "岗位ID", hidden : true, width : 80},
		    {name : "gw", index : "gw", label : "岗位", width : 120}
		],
		//按照"xh"字段排序
		sortname : "bmjc",
		sortorder : "asc"
	});
	
	// 新增
	$("#btn_add").click(function(){
		add();
	});
	
	$("#edit_form [name=gw]").skySelect({
		title : "岗位选择",
		multiple : false,
		gridMode : false,
		url : "<c:url value='/core/actor/query'/>",
		valueColumn : "id",
		textColumn : "gw",
		valueElem : '#edit_form [name=gwid]',
		textElem : '#edit_form [name=gw]',
		autoComplete : true, // 自动完成
		displayText: ["id", "gw"], //自动完成下拉框显示字段
		columns : [ {field : "id", title : "序号", search : false}, 
		            {field : "gw", title : "岗位", search : true} ]
	});
	
	//职务岗位对照关系校验
	$('#edit_form').validate({
		rules: {
			bmjc : 'required',
			hrzw : 'required',
			gw : 'required'		
		},
		messages: {
			bmjc : '部门不能为空！',
			hrgw :'HR职务不能为空！',
			gw : '岗位不能为空！'
		}
	});
	
});

function add(){
	layer.open({
	    type: 1, //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
	    title: "新增职务岗位对照关系", //默认“信息”
	    area: ['600px'], //宽
	    content: $("#div_edit"),
		end: function(){
			//无论是确认还是取消，只要层被销毁了，end都会执行，不携带任何参数。
			$("#edit_form").clear(); //全部清除
		},
		btn: ['保存', '取消'],
		
		btn1: function(index, layero){
			//按钮1的回调（保存）
			if(!$('#edit_form').valid()){
				return false;
			}
			$.post("<c:url value='/mdm/zwgw/save'/>", $("#edit_form").getFormData(), function(result){
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

function edit(bmjc,hrzw,gwid){
	add();
	$('#edit_form [name=bmjc]').val(bmjc);
	$('#edit_form [name=hrzw]').val(hrzw);
	$('#edit_form [name=gw]').skySelect('setValue',gwid);
}

 function del(bmjc,hrzw){
	layer.confirm('确定要删除吗？', {
		title: "确认信息", //默认“信息”
	    btn: ['确定','取消'], // 按钮
	    icon: 0
	}, function(){
		$.post("<c:url value='/mdm/zwgw/delete'/>", { bmjc : bmjc, hrzw : hrzw }, function(result){
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
