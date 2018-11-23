/**
 *
 * 信息管理部软件开发科Bootstrap插件系列
 * 
 * jquery.sky.skyRemoteSelect 远程数据选择框-基于layer
 *
 * 王歌 2016年11月
 */
(function($) {
	/**
	 * 远程数据选择框 创建框架 OK
	 */
	function initPanel(target, options) {
		var fa = options.multiple ? "fa-search-plus" : "fa-search";
		if(options.gridMode){
			$(target).wrap('<span style="cursor:pointer;" class="input-icon input-icon-right"></span>');
			$(target).parent("span").append('<i class="fa '+fa+' openBtn primary"></i>');
		}else{
			$('<span class="input-group-addon openBtn primary"><i class="fa '+fa+'"></i></span>').insertAfter(target).css({"cursor" : "pointer"});
		}
		var selectboxid = new Date().getTime();
		var panel = $('<div id="'+selectboxid+'" class="wrapper wrapper-content animated fadeInRight" style="display:none;"></div>').appendTo('body');
		var panelHtml = '<div class="row">';
		var isSearch = false;
		$.each(options.columns, function(i, n) {
			if(n.search){
				isSearch = true;
				panelHtml += '<div class="col-md-5 col-sm-5"><div class="input-group input-group-sm m-b-sm"><span class="input-group-addon" style="height:26px;padding:3px 5px">'
					+n.title+'</span><input type="text" name="'+n.field+'" class="form-control selectboxinput" style="height:26px;padding:3px 5px"></div></div>';
			}
		});
		if(isSearch){
			panelHtml += '<div class="col-md-2 col-sm-2"><button class="btn btn-primary btn-xs selectboxbtn"><i class="fa fa-search"></i>&nbsp;查询</button></div>';
		}
		panelHtml += '<div class="col-md-12 col-sm-12"><table class="selectboxmain table table-hover table-bordered m-b-none"></table>';
		if(options.multiple){
			panelHtml += '<div class="m-t-xs" style="height:150px;overflow-y:scroll;">'
				+'<table class="selectboxresult table table-hover table-bordered m-b-none"></table></div>';
		}
		panelHtml += '</div></div></div>';
		panel.append(panelHtml);

		var thead = $("<thead></thead>"),tr = $("<tr></tr>"), checkth=$('<th style="width:10px;"></th>'),tfoot = $("<tfoot></tfoot>");
		if (options.multiple) {
			$('<input type="checkbox" class="selectboxcheckall" />').appendTo(checkth);
		}
		checkth.appendTo(tr);
		$.each(options.columns, function(i, n) {
			var th = $("<th></th>").css({
				"white-space" : "nowrap"
			});
			if (n.title) {
				th.html(n.title);
			}
			if (n.field) {
				th.prop("field", n.field);
			}

			th.appendTo(tr);
		});
		tr.appendTo(thead);
		thead.children("tr").addClass("active");
		panel.find(".selectboxmain").append(thead);
		
		var tbody = $("<tbody></tbody>");
		panel.find(".selectboxmain").append(tbody);
		
		tfoot.append('<tr><td colspan="'+tr.children().length+'">'
			+'<table class="page-div" style="width:100%;"><tr><td style="width:300px;"><div>'
			+'第<label class="page-num-start">0</label>-<label class="page-num-stop">0</label>条 ,共<label class="page-num-total">0</label>条</div></td>'
			+'<td><div style="width:100%;text-align:right;">'
			+'<button class="btn btn-xs btn-white btn-bitbucket disabled page-first" type="button"><i class="fa fa-step-backward"></i></button>'
			+'&emsp;<button class="btn btn-xs btn-white btn-bitbucket disabled page-previous" type="button"><i class="fa fa-backward"></i></button>'
			+'&emsp;第<label class="page-num">1</label>页'
			+'&emsp;<button class="btn btn-xs btn-white btn-bitbucket disabled page-next" type="button"><i class="fa fa-forward"></i></button>'
			+'&emsp;<button class="btn btn-xs btn-white btn-bitbucket disabled page-last" type="button"><i class="fa fa-step-forward"></i></button>'
			+'<div></td></tr></table></td></tr>');
		panel.find(".selectboxmain").append(tfoot);
		
		if (options.multiple) {
			var _thead = thead.clone();
			_thead.find(".selectboxcheckall").remove()
			panel.find(".selectboxresult").append(_thead);
			panel.find(".selectboxresult").append(tbody.clone());
		}

		panel.find(".table>thead th").css({
			"padding" : "3px 5px"
		});
		panel.find(".table>tbody>tr>td").css({
			"padding" : "2px 5px"
		});

		panel.data("options", options);

		return panel;
	}

	/**
	 * 绑定事件 OK
	 */
	function bindEvents(target) {
		var panel = $(target).data("skyRemoteSelect");
		var options = panel.data("options");

		panel.find("*").unbind(".skyRemoteSelect");

		panel.find(".page-first").bind("click.skyRemoteSelect", function() {
			loadPageData(target, 1);
		});
		panel.find(".page-previous").bind("click.skyRemoteSelect", function() {
			var current = $(target).data("currentPage");
			var pagenum = current > 1 ? current - 1 : 1;
			loadPageData(target, pagenum);
		});
		panel.find(".page-next").bind("click.skyRemoteSelect", function() {
			var current = $(target).data("currentPage");
			var total = $(target).data("totalPage");
			var pagenum = current < total ? current + 1 : total;
			loadPageData(target, pagenum);
		});
		panel.find(".page-last").bind("click.skyRemoteSelect", function() {
			var pagenum = $(target).data("totalPage");
			loadPageData(target, pagenum);
		});
		panel.find(".selectboxbtn").bind("click.skyRemoteSelect", function() {
			loadPageData(target, 1);
		});
		
		var span = $(target).parent();

		span.find(".openBtn").bind("click.skyRemoteSelect", function() {
			open(target);
		});
		
		panel.find(".selectboxcheckall").bind("click.skyRemoteSelect", function() {
			var checkall = $(this).prop("checked");
			if(checkall){
				var trs = panel.find(".selectboxmain>tbody>tr").not(".nodata").not(".active");
				$.each(trs, function(i,n){
					var checkbox = $(this).find("input:checkbox");
					if (checkbox.prop("checked")) {
						checkbox.removeProp("checked").removeAttr("checked");
					} else {
						checkbox.prop("checked", "checked");
					}

					mainCheck(target, checkbox);
				});
			}else{
				var trs = panel.find(".selectboxmain>tbody>tr.active");
				$.each(trs, function(i,n){
					var checkbox = $(this).find("input:checkbox");
					if (checkbox.prop("checked")) {
						checkbox.removeProp("checked").removeAttr("checked");
					} else {
						checkbox.prop("checked", "checked");
					}

					mainCheck(target, checkbox);
				});
			}
		});
	}

	/**
	 * 打开弹出框 OK
	 */
	function open(target){		
		loadPageData(target, 0);
		var panel = $(target).data("skyRemoteSelect");
		var options = panel.data("options");
		var layerOptions = {
			id: "layer_" + panel.attr("id"),
		    type: 1, //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
		    title: options.title, //默认“信息”
		    area: ["600px"], //宽高
		    content: panel,
		    success: function(layero, index){
		    	//当你需要在层创建完毕时即执行一些语句，可以通过该回调。success会携带两个参数，分别是当前层DOM当前层索引
				$(target).data("index", index);
		    },
			end: function(){
				//无论是确认还是取消，只要层被销毁了，end都会执行，不携带任何参数。
				$(target).removeData("index");
			},
			btn: ["确定", "取消"],
			yes: function(index, layero){
				//按钮1的回调:此处的yes可以写成btn1
				layer.close(index);
				selectConfirm(target);
			},
			btn2: function(index, layero){
				//按钮2的回调
				if(options.cancel){
					options.cancel();
				}
			},
			cancel: function(){ 
				//右上角关闭回调:此处可以不写
			}				
		};
		if (options.multiple) {
			$.extend(layerOptions, {
				btn: ["确定", "重新选择", "取消"],
				yes: function(index, layero){
					//按钮1的回调:此处的yes可以写成btn1
					layer.close(index);
					selectConfirm(target);
				},
				btn2: function(index, layero){
					//按钮2的回调
					selectClear(target);
				},
				btn3: function(index, layero){
					//按钮3的回调
					if(options.cancel){
						options.cancel();
					}
				}
			});
		}
		layer.open(layerOptions);
	}
	
	/**
	 * 更新翻页 OK
	 */
	function updatePage(target, pageNum) {
		var panel = $(target).data("skyRemoteSelect");
		var total = $(target).data("totalPage");

		if (pageNum == 1) {
			panel.find(".page-first").addClass("disabled");
			panel.find(".page-previous").addClass("disabled");
			panel.find(".page-next").removeClass("disabled");
			panel.find(".page-last").removeClass("disabled");
		} else if (pageNum == total) {
			panel.find(".page-first").removeClass("disabled");
			panel.find(".page-previous").removeClass("disabled");
			panel.find(".page-next").addClass("disabled");
			panel.find(".page-last").addClass("disabled");
		} else if (pageNum > 1 && pageNum < total) {
			panel.find(".page-first").removeClass("disabled");
			panel.find(".page-previous").removeClass("disabled");
			panel.find(".page-next").removeClass("disabled");
			panel.find(".page-last").removeClass("disabled");
		}
		if (total == 1) {
			panel.find(".page-first").addClass("disabled");
			panel.find(".page-previous").addClass("disabled");
			panel.find(".page-next").addClass("disabled");
			panel.find(".page-last").addClass("disabled");
		}

	}

	/**
	 * 确认按钮 OK
	 */
	function selectConfirm(target) {
		var panel = $(target).data("skyRemoteSelect");
		var options = panel.data("options");
		var selectedData = $(target).data("selectedData") || [];

		if(options.confirm){
			if(!options.multiple){
				selectedData = selectedData[0];
			}
			options.confirm(selectedData);
		}
	}

	/**
	 * 重新选择按钮 OK
	 */
	function selectClear(target) {
		var panel = $(target).data("skyRemoteSelect");
		$(target).removeData("selectedData");
		panel.find(".selectboxinput").val("");
		loadPageData(target, 1);
	}

	/**
	 * 根据页码加载表格 OK
	 */
	function loadPageData(target, pageNum) {
		var index = layer.load(1, {
			shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
		var panel = $(target).data("skyRemoteSelect");
		var options = panel.data("options");
		var selectedData = $(target).data("selectedData");
		
		if(pageNum == 0){
			pageNum = 1;
			panel.find(".selectboxinput").val("");
		}

		var tbody = panel.find(".selectboxmain>tbody");
		tbody.empty();
		
		var param = $.extend({}, options.param, {page:pageNum, rows:options.pageSize});
		$.each(options.columns, function(i, n) {
			if(n.search){
				param[n.field] = panel.find(".selectboxinput[name='"+n.field+"']").val();
			}
		});
		
		$.ajax({  
			url : options.url,
			data : param,
			async : false,
			type : "POST",  
			dataType : "json",  
			success : function(data){
				var rows = data.rows
				for (var i = 0; i < options.pageSize; i++) {
					if (i < rows.length) {
						addRow(target, rows[i]);
					} else {
						addRow(target, null);
					}
				}

				var pager = panel.find(".page-div");
				var total = Math.ceil(data.total / options.pageSize);
				$(target).data("totalPage", total);

				var start = (pageNum - 1) * options.pageSize + 1;
				var tmp = start + options.pageSize - 1;
				var end = tmp < data.total ? tmp : data.total
				pager.find(".page-num-start").html(start);
				pager.find(".page-num-stop").html(end);
				pager.find(".page-num-total").html(data.total);
				pager.find(".page-num").html(pageNum);
				
				updatePage(target, pageNum);
				$(target).data("currentPage", pageNum);
			}  
		});
		layer.close(index);
	}
	
	/**
	 * 主表增加一行数据
	 * @param target
	 * @param rowData
	 */
	function addRow(target, rowData) {
		var panel = $(target).data("skyRemoteSelect");
		var options = panel.data("options");

		var selectedData = $(target).data("selectedData")||[];
		var tbody = panel.find(".selectboxmain>tbody");
		var tr = $("<tr></tr>");
		if(rowData){
			tr.append('<td><input type="checkbox"/></td>');
			$.each(options.columns, function(index, column) {
				var td = $("<td></td>");
				td.html(rowData[column.field]);
				td.appendTo(tr);
			});
			tr.data("data", rowData);
			
			$.each(selectedData, function(index, sData) {
				if (sData[options.valueColumn] == rowData[options.valueColumn]) {
					tr.find("input:checkbox").prop("checked", "checked");
					tr.addClass("active");
					return false;
				}
			});
		}else{
			tr.addClass("nodata");
			tr.append('<td>&nbsp;</td>');
			$.each(options.columns, function(index, column) {
				var td = $("<td></td>");
				td.appendTo(tr);
			});
		}
		tr.children("td").css({
			"padding" : "2px 5px",
			"cursor" : "pointer"
		});
		tr.appendTo(tbody);		
		setCheckAll(target);

		tr.not(".nodata").bind("dblclick.skyRemoteSelect", function() {
			var checkbox = $(this).find("input:checkbox");
			if (checkbox.prop("checked")) {
				checkbox.removeProp("checked").removeAttr("checked");
			} else {
				checkbox.prop("checked", "checked");
			}

			mainCheck(target, checkbox);
			
			if(!options.multiple && $(this).find("input:checkbox").prop("checked")){
				var index = $(target).data("index");
				layer.close(index);
				selectConfirm(target);
			}
		});

		tr.find("input:checkbox").bind("click.skyRemoteSelect", function() {
			mainCheck(target, $(this));
		});
		
	}
	
	/**
	 * 设置全选按钮
	 * @param target
	 */
	function setCheckAll(target) {
		var panel = $(target).data("skyRemoteSelect");
		var options = panel.data("options");

		var tbody = panel.find(".selectboxmain>tbody");

		var checkLength = tbody.find("tr.active").length;
		if(checkLength == options.pageSize){
			panel.find(".selectboxcheckall").prop("checked", "checked");
		}else{
			panel.find(".selectboxcheckall").removeProp("checked").removeAttr("checked");
		}
		
	}
	
	/**
	 * 结果表增加一行数据--多选控件
	 * @param target
	 * @param rowData
	 */
	function addResultRowMul(target, rowData) {
		var panel = $(target).data("skyRemoteSelect");
		var options = panel.data("options");
		
		var tbody = panel.find(".selectboxresult>tbody");
		var tr = $("<tr></tr>");
		$('<td><input type="checkbox" checked="checked" /></td>').appendTo(tr);
		$.each(options.columns, function(index, column) {
			var td = $("<td></td>");
			td.html(rowData[column.field]);
			td.appendTo(tr);
		});
		tr.data("data", rowData);

		tr.addClass("active").bind("dblclick.skyRemoteSelect", function() {
			resultCheck(target, $(this));
		});

		tr.find("input:checkbox").bind("click.skyRemoteSelect", function() {
			resultCheck(target, $(this).parents("tr"));
		});
		tr.children("td").css({
			"padding" : "2px",
			"cursor" : "pointer"
		});
		tr.appendTo(tbody);
		
	}

	/**
	 * 主表行选中、取消事件
	 * @param target
	 * @param checkbox
	 */
	function mainCheck(target, checkbox) {
		var panel = $(target).data("skyRemoteSelect");
		var options = panel.data("options");
		var tr = checkbox.closest("tr");
		var checked = checkbox.prop("checked");
		
		var selectedData = $(target).data("selectedData")||[];
		var rowData = tr.data("data");
		
		if (options.multiple) {
			//多选
			if (checked) {
				var isExists = false;
				$.each(selectedData, function(i, n) {
					if (n[options.valueColumn] == rowData[[options.valueColumn]]) {
						isExists = true;
						return false;
					}
				});
				if(!isExists){
					selectedData.push(rowData);					
					addResultRowMul(target, rowData);
					tr.addClass("active");
					setCheckAll(target);
				}
			} else {
				tr.removeClass("active");
				$.each(selectedData, function(i, n) {
					if (n[options.valueColumn] == rowData[options.valueColumn]) {
						selectedData.splice(i, 1);
						return false;
					}
				});
				
				var tbody = panel.find(".selectboxresult>tbody");
				$.each(tbody.children("tr"), function(i, n) {
					var thisRowData = $(n).data("data");
					if (thisRowData[options.valueColumn] == rowData[options.valueColumn]) {
						$(n).remove();
						return false;
					}
				});
			}
		} else {
			//单选
			tr.removeClass("active").siblings().removeClass("active").find("input:checkbox").removeProp("checked").removeAttr("checked");
			selectedData = [];
			if(checked){
				tr.addClass("active");
				selectedData.push(rowData);
			}
		}
		$(target).data('selectedData', selectedData);
	}

	/**
	 * 结果表行选中、取消事件--多选控件 OK
	 */
	function resultCheck(target, tr) {
		var panel = $(target).data("skyRemoteSelect");
		var options = panel.data("options");
		var selectedData = $(target).data("selectedData");
		var tbody = panel.find(".selectboxmain>tbody");

		selectedData = selectedData || [];
		var checkdata = tr.data("data");
		tr.remove();

		$.each(selectedData, function(i, n) {
			if (n[options.valueColumn] == checkdata[options.valueColumn]) {
				selectedData.splice(i, 1);
				return false;
			}
		});
		$.each(tbody.children("tr").not(".nodata"), function(i, n) {
			var rowData = $(n).data("data");
			if (rowData[options.valueColumn] == checkdata[options.valueColumn]) {
				$(n).find("input:checkbox").removeProp("checked").removeAttr("checked");
				$(n).removeClass("active");
				return false;
			}	
		});		
		panel.find(".selectboxcheckall").removeProp("checked").removeAttr("checked");
		$(target).data("selectedData", selectedData);
	}

	/**
	 * 获取当前选的记录 OK
	 */
	function getValueData(target) {
		var panel = $(target).data("skyRemoteSelect");
		var options = panel.data("options");
		var selectedData = $(target).data("selectedData");
		selectedData = selectedData || [];
		if(!options.multiple){
			selectedData = selectedData[0] || {};
		}
		return selectedData;
	}

	/**
	 * 锁定控件
	 */
	function setDisabled(target, disabled) {
		var panel = $(target).data("skyRemoteSelect");
		var options = panel.data("options");
		var span = $(target).parent();
		
		options.disabled = disabled;
		$(target).addClass("skydisabled").prop('disabled', 'disabled');
		span.find(".openBtn").removeClass("primary").unbind("click.skyRemoteSelect");
		if (!disabled) {
			$(target).removeClass("skydisabled").removeProp('disabled').removeAttr('disabled');
			span.find(".openBtn").addClass("primary").bind("click.skyRemoteSelect", function() {
				open(target);
			});
		}
	}

	/**
	 * 控件主体声明 OK
	 */
	$.fn.skyRemoteSelect = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.skyRemoteSelect.methods[options](this, param);
		}

		return this.each(function() {
			var panel = $(this).data("skyRemoteSelect");
			if (panel) {
				var oldOptions = panel.data("options");
				$.extend(oldOptions, options);
			} else {
				if(options.multiple){
					options.pageSize = options.pageSize ? options.pageSize : 5;
				}else{
					options.pageSize = options.pageSize ? options.pageSize : 10;
				}
				options = $.extend({}, $.fn.skyRemoteSelect.defaults, options);
				panel = initPanel(this, options);
				
				$(this).data("skyRemoteSelect", panel);
				bindEvents(this);
			}
			setDisabled(this, options.disabled);
		});
	};

	/**
	 * 控件方法声明
	 */
	$.fn.skyRemoteSelect.methods = {
		disable : function(jq) {
			return jq.each(function() {
				setDisabled(this, true);
			});
		},
		enable : function(jq) {
			return jq.each(function() {
				setDisabled(this, false);
			});
		}
	};

	/**
	 * 控件默认值
	 */
	$.fn.skyRemoteSelect.defaults = {
		title : "选择框",
		url : "",
		param : {},//url参数
		gridMode : false,
		valueColumn : "",
		columns : [],
		separator : ",",
		disabled : false,
		multiple : false,
		pageSize : 0,
		confirm : function(data){
			
		},
		cancel : function(){
			
		}
	};
})(jQuery);
