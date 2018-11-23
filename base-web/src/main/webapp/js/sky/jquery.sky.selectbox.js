/**
 *
 * 信息管理部软件开发科Bootstrap插件系列
 * 
 * jquery.sky.skySelect 本地数据选择框-基于layer skyAutoComplete
 *
 * 王歌 2016年11月
 */
(function($) {
	/**
	 * 本地数据选择框 创建框架
	 * @param target
	 * @param options
	 */
	function initPanel(target, options) {
		var selectboxid = new Date().getTime();
		var panel = $('<div id="'+selectboxid+'" class="wrapper wrapper-content animated fadeInRight" style="display:none;"></div>').appendTo('body');
		var panelHtml = '<div class="row">';
		$.each(options.columns, function(i, n) {
			if(n.search){
				panelHtml += '<div class="col-md-6 col-sm-6"><div class="input-group input-group-sm m-b-sm"><span class="input-group-addon" style="height:26px;padding:3px 5px">'
					+n.title+'</span><input type="text" name="'+n.field+'" class="form-control selectboxinput" style="height:26px;padding:3px 5px"></div></div>';
			}
		});
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
	 * 初始化绑定位置
	 * @param target
	 */
	function initTargetElem(target){
		var panel = $(target).data("skySelect");
		var options = panel.data("options");
		var fa = options.multiple ? "fa-search-plus" : "fa-search";
		$('<span class="input-group-addon openBtn primary"><i class="fa '+fa+'"></i></span>').insertAfter(target).css({"cursor" : "pointer"});
		
		var span = $(target).parent();

		span.find(".openBtn").bind("click.skySelect", function(){
			open(target);
		});
		
		if(options.autoComplete && !options.multiple){
			$(target).skyAutoComplete({
				displayText: options.displayText,
				text: options.textColumn,
				appendTo: span,
				afterSelect: function (data){
					var selectedData = [];
					selectedData.push(data);
					setResult(target, selectedData);
					selectConfirm(target);
				}
			});
		}
		
	}

	/**
	 * 绑定事件
	 * @param target
	 */
	function bindEvents(target) {
		var panel = $(target).data("skySelect");
		var options = panel.data("options");

		panel.find("*").unbind(".skySelect");

		$.each(options.columns, function(i, n) {
			if(n.search){
				panel.find("input[name="+n.field+"]").bind("keyup.skySelect", function() {
					filterData(target);
				});
			}
		});

		panel.find(".page-first").bind("click.skySelect", function() {
			loadPageData(target, 1);
		});
		panel.find(".page-previous").bind("click.skySelect", function() {
			var current = panel.data("currentPage");
			var pagenum = current > 1 ? current - 1 : 1;
			loadPageData(target, pagenum);
		});
		panel.find(".page-next").bind("click.skySelect", function() {
			var current = panel.data("currentPage");
			var total = panel.data("totalPage");
			var pagenum = current < total ? current + 1 : total;
			loadPageData(target, pagenum);
		});
		panel.find(".page-last").bind("click.skySelect", function() {
			var pagenum = panel.data("totalPage");
			loadPageData(target, pagenum);
		});
		
		panel.find(".selectboxcheckall").bind("click.skySelect", function() {
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
	 * 打开弹出框
	 * @param target
	 */
	function open(target){
		var panel = $(target).data("skySelect");
		var options = panel.data("options");
		
		var layerOptions = {
			id: "layer_" + panel.attr("id"),
		    type: 1, //0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
		    title: options.title, //默认“信息”
		    area: [options.width], //宽高
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
					setResult(target, []);
				},
				btn3: function(index, layero){
					//按钮3的回调
					if(options.cancel){
						options.cancel();
					}
				}
			});
		}		

		if(options.valueElem){
			var value = $(options.valueElem).val()||"";
			var thisValue = $(target).data("value")||"";
			
			if(value != thisValue){
				setValue(target, value);
			}
		}
		
		layer.open(layerOptions);
	}

	/**
	 * 确认按钮
	 * @param target
	 */
	function selectConfirm(target) {
		var panel = $(target).data("skySelect");
		var options = panel.data("options");

		var selectedData = $(target).data("selectedData")||[];
		
		var value = "", text = "";
		for (var i = 0; i < selectedData.length; i++) {
			value += selectedData[i][options.valueColumn] + options.separator;
			text += selectedData[i][options.textColumn] + options.separator;
		}
		if (value != "") {
			value = value.substring(0, value.lastIndexOf(options.separator));
			text = text.substring(0, text.lastIndexOf(options.separator));
		}
		if(options.valueElem){
			$(options.valueElem).val(value);
		}
		if(options.textElem){
			$(options.textElem).val(text);
		}else{
			$(target).val(text);
		}
		$(target).data("value",value);
		$(target).data("text",text);
		
		if(options.gridMode){
			// 取当前子控件复制，触发子控件回调函数
			var gridSelect = $(target).data("gridSelect");
			var gridOptions = $(gridSelect).data("gridSelect").data("options");
			$(gridSelect).data("selectedData", selectedData);
			$(gridSelect).data("value", value);
			$(gridSelect).data("text", text);
			if(gridOptions.confirm){
				if(!options.multiple){
					gridOptions.confirm(selectedData[0], value, text);
				}else{
					gridOptions.confirm(selectedData, value, text);
				}
			}
		}else{
			if(options.confirm){
				if(!options.multiple){
					options.confirm(selectedData[0], value, text);
				}else{
					options.confirm(selectedData, value, text);
				}
			}
		}
	}

	/**
	 * 根据数据设置选择框
	 * @param target
	 * @param resultData
	 */
	function setResult(target, resultData) {
		var panel = $(target).data("skySelect");
		var options = panel.data("options");

		$(target).data('selectedData', resultData);
		panel.find(".selectboxinput").val("");
		panel.data("filterData", options.data);
		loadPageData(target, 1);

		var trs = panel.find(".selectboxmain>tbody>tr").not(".nodata");
		trs.removeClass("active").find("input:checkbox").removeProp("checked").removeAttr("checked");
		
		var tbody = panel.find(".selectboxresult>tbody");
		if(options.multiple){
			tbody.empty();
		}

		$.each(resultData, function(index, rowData) {
			$.each(trs, function(j, tr) {
				var thisRowData = $(tr).data("data");
				if (rowData[options.valueColumn] == thisRowData[options.valueColumn]) {
					$(tr).addClass("active").find("input:checkbox").prop("checked", "checked");
				}
			});
			if(options.multiple){
				addResultRowMul(target, rowData)
			}
		})
	}
	
	/**
	 * 主表增加一行数据
	 * @param target
	 * @param rowData
	 */
	function addRow(target, rowData) {
		var panel = $(target).data("skySelect");
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

		tr.not(".nodata").bind("dblclick.skySelect", function() {
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

		tr.find("input:checkbox").bind("click.skySelect", function() {
			mainCheck(target, $(this));
		});
		
	}
	
	/**
	 * 设置全选按钮
	 * @param target
	 */
	function setCheckAll(target) {
		var panel = $(target).data("skySelect");
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
		var panel = $(target).data("skySelect");
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

		tr.addClass("active").bind("dblclick.skySelect", function() {
			resultCheck(target, $(this));
		});

		tr.find("input:checkbox").bind("click.skySelect", function() {
			resultCheck(target, $(this).parents("tr"));
		});
		tr.children("td").css({
			"padding" : "2px",
			"cursor" : "pointer"
		});
		tr.appendTo(tbody);
		
	}

	/**
	 * 过滤数据
	 * @param target
	 */
	function filterData(target) {
		var panel = $(target).data("skySelect");
		var options = panel.data("options");
		var data = options.data;
		
		var searchFiled = [];
		var values = "";
		$.each(options.columns, function(i, n) {
			if(n.search){
				var value = panel.find("input[name="+n.field+"]").val() + "";
				values += value;
				searchFiled.push({field : n.field, value : value});
			}
		});

		var filterData = [];
		if(values.length > 0){
			$.each(data, function(i, n) {
				var flag = true;
				$.each(searchFiled, function(j, m) {
					if(m.value.length > 0){
						if (n[m.field].toUpperCase().indexOf(m.value.toUpperCase()) > -1) {
							flag = flag && true;
						} else {
							flag = flag && false;
						}
					}
				});
				if(flag){
					filterData.push(n);
				}
			});
		}else{
			filterData = data;
		}
		panel.data("filterData", filterData);

		var total = Math.ceil(filterData.length / options.pageSize);
		panel.data("totalPage", total);

		loadPageData(target, 1);
	}

	/**
	 * 主表行选中、取消事件
	 * @param target
	 * @param checkbox
	 */
	function mainCheck(target, checkbox) {
		var panel = $(target).data("skySelect");
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
	 * 结果表行选中、取消事件--多选控件
	 * @param target
	 * @param tr
	 */
	function resultCheck(target, tr) {
		var panel = $(target).data("skySelect");
		var options = panel.data("options");

		var selectedData = $(target).data("selectedData")||[];
		
		var tbody = panel.find(".selectboxmain>tbody");
		var rowData = tr.data("data");
		tr.remove();

		$.each(selectedData, function(i, n) {
			if (n[options.valueColumn] == rowData[options.valueColumn]) {
				selectedData.splice(i, 1);
				return false;
			}
		});
		$.each(tbody.children("tr").not(".nodata"), function(i, n) {
			var thisRowData = $(n).data("data");
			if (thisRowData[options.valueColumn] == rowData[options.valueColumn]) {
				$(n).find("input:checkbox").removeProp("checked").removeAttr("checked");
				$(n).removeClass("active");
				setCheckAll(target);
				return false;
			}
		});
		$(target).data("selectedData", selectedData);
	}

	/**
	 * 获取当前选的记录
	 * @param target
	 */
	function getValueData(target) {
		var panel = $(target).data("skySelect");
		var options = panel.data("options");

		var selectedData = $(target).data("selectedData")||[];

		if(!options.multiple){
			selectedData = selectedData[0] || {};
		}
		return selectedData;
	}

	/**
	 * 给控件赋值并设置选择项，便于展示已选择记录，数据格式为"1,2,3"
	 * @param target
	 * @param value
	 */
	function setValue(target, value) {
		var panel = $(target).data("skySelect");
		var options = panel.data("options");
		var data = options.data;
		
		if (value == null) {
			value = "";
		} else {
			value += "";
		}

		var toMatch = value.split(options.separator);
		var resultData = [];
		var intj = 0;
		$.each(toMatch, function(i, n) {
			$.each(data, function(index, rowData) {
				if (rowData[options.valueColumn] == n) {
					resultData.push(rowData);
					intj++;
					if (toMatch.length == intj) {
						return false;
					}
				}
			});
		});
		setResult(target, resultData);
	}

	/**
	 * 取值 "1,2,3"
	 * @param target
	 * @param field
	 */
	function getValue(target, field) {
		var panel = $(target).data("skySelect");
		var options = panel.data("options");

		if (field) {
			if (field == options.textColumn) {
				return getText(target);
			}
			var valueData = getValueData(target);
			if(!options.multiple){
				return valueData[field] || "";
			}else{
				var value = "";
				for (var i = 0; i < valueData.length; i++) {
					value += valueData[i][field] || "";
					value += options.separator;
				}
				if (value != "") {
					value = value.substring(0, value.lastIndexOf(options.separator));
				}
				return value;
			}
		} else {
			return $(target).data("value") || "";
		}
	}

	/**
	 * 取值 "1,2,3"
	 * @param target
	 */
	function getText(target) {
		return $(target).data("text") || "";
	}

	/**
	 * 锁定控件
	 * @param target
	 * @param disabled
	 */
	function setDisabled(target, disabled) {
		var panel = $(target).data("skySelect");
		var options = panel.data("options");
		var span = $(target).parent();
		
		options.disabled = disabled;		
		$(target).addClass("skydisabled").prop('disabled', 'disabled');
		span.find(".openBtn").removeClass("primary").unbind("click.skySelect");
		if (!disabled) {
			$(target).removeClass("skydisabled").removeProp('disabled').removeAttr('disabled');
			span.find(".openBtn").addClass("primary").bind("click.skySelect", function() {
				open(target);
			});
		}
	}

	/**
	 * 加载数据
	 * @param target
	 * @param data
	 */
	function loadData(target, data) {
		var index = layer.load(1, {
			shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
		
		var panel = $(target).data("skySelect");
		var options = panel.data("options");
		panel.data("filterData", data);
		options.data = data;
		
		if(options.autoComplete && !options.multiple && !options.gridMode){
			$(target).skyAutoComplete("setSource", data);
		}
		if(options.gridMode){
			// 根据gridTargetID找到所有子控件，设置autoComplete数据源
			$.each($("[gridTargetID="+options.gridTargetID.replace("#","")+"]"), function(){
				var gridSelect = $(this).data("gridSelect");
				var gridOptions = $(gridSelect).data("options");
				
				if(!options.multiple && gridOptions.autoComplete){
					$(this).skyAutoComplete("setSource", data);
				}
			});
		}

		var total = Math.ceil(data.length / options.pageSize);
		panel.data("totalPage", total);
		loadPageData(target, 1);
		
		if(options.onComplete){
			options.onComplete();
		}
		
		layer.close(index);
	}

	/**
	 * 根据页码加载表格
	 * @param target
	 * @param pageNum
	 */
	function loadPageData(target, pageNum) {
		var panel = $(target).data("skySelect");
		var options = panel.data("options");
		var data = panel.data("filterData");

		var tbody = panel.find(".selectboxmain>tbody");
		tbody.empty();

		var start = options.pageSize * (pageNum - 1) + 1;

		for (var i = 0; i < options.pageSize; i++) {
			var index = start + i;
			if (index < data.length + 1) {
				addRow(target, data[index - 1]);
			} else {
				addRow(target, null);
			}
		}

		var pager = panel.find(".page-div");
		var total = Math.ceil(data.length / options.pageSize);
		panel.data("totalPage", total);

		var start = (pageNum - 1) * options.pageSize + 1;
		var tmp = start + options.pageSize - 1;
		var end = tmp < data.length ? tmp : data.length
		pager.find(".page-num-start").html(start);
		pager.find(".page-num-stop").html(end);
		pager.find(".page-num-total").html(data.length);
		pager.find(".page-num").html(pageNum);
		
		panel.data("currentPage", pageNum);

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
	 * 根据url和参数取数
	 * @param url
	 * @param param
	 */
	function loadUrl(url, param){
		var index = layer.load(1, {
			shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
		var remoteData = [];
		$.ajax({  
			url : url,
			data : param,
			async : false,
			type : "POST",  
			dataType : "json",  
			success : function(data){
				remoteData = data;
			}  
		});
		layer.close(index);
		return remoteData;
	}
	
	/**
	 * 判断两个简单对象是否相等
	 * @param a
	 * @param b
	 */
	function isObjectValueEqual(a, b) {
		var aProps = Object.getOwnPropertyNames(a);
		var bProps = Object.getOwnPropertyNames(b);
		
		if(aProps.length != bProps.length){
			return false;
		}

		for(var i = 0; i < aProps.length; i++){
			var propName = aProps[i];
			if(a[propName] !== b[propName]){
				return false;
            }
        }
		return true;
    }

	/**
	 * skySelect控件主体声明
	 */
	$.fn.skySelect = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.skySelect.methods[options](this, param);
		}

		return this.each(function() {
			var panel = $(this).data("skySelect");
			if (panel) {
				var oldOptions = panel.data("options");
				// 如果url有变化，重新加载数据
				if(options.url || !$.isEmptyObject(oldOptions.param)){
					if(options.url != oldOptions.url || !isObjectValueEqual(options.param, oldOptions.param)){
						options.data = loadUrl(options.url, options.param);
					}
				}
				$.extend(oldOptions, options);
				options.gridMode = oldOptions.gridMode;
			} else {
				options = $.extend({}, $.fn.skySelect.defaults, options);
				if(options.multiple){
					options.pageSize = options.pageSize ? options.pageSize : 5;
				}else{
					options.pageSize = options.pageSize ? options.pageSize : 10;
				}				
				if (options.url) {
					// 如果有url，加载数据
					options.data = loadUrl(options.url, options.param);
				}				
				panel = initPanel(this, options);
				
				$(this).data("skySelect", panel);
				bindEvents(this);
				
				if(!options.gridMode){
					initTargetElem(this);
				}
			}
			if(options.data){
				loadData(this, options.data);
			}
			if(!options.gridMode){
				setDisabled(this, options.disabled);
			}
		});
	};

	/**
	 * skySelect控件方法声明
	 */
	$.fn.skySelect.methods = {
		loadData : function(jq, data) {
			return jq.each(function() {
				loadData(this, data);
			});
		},
		disable : function(jq) {
			return jq.each(function() {
				setDisabled(this, true);
			});
		},
		enable : function(jq) {
			return jq.each(function() {
				setDisabled(this, false);
			});
		},
		open : function(jq) {
			return jq.each(function() {
				open(this);
			});
		},
		getValueData : function(jq) {
			return getValueData(jq[0]);
		},
		getValue : function(jq, field) {
			return getValue(jq[0], field);
		},
		getText : function(jq) {
			return getText(jq[0]);
		},
		setValue : function(jq, value) {
			return jq.each(function() {
				setValue(this, value);
			});
		}
	};

	/**
	 * skySelect控件默认值
	 */
	$.fn.skySelect.defaults = {
		title : "选择框",
		width : "600px",
		url : "",
		param : {},//url参数
		valueColumn : "",
		textColumn : "",
		valueElem : "",
		textElem : "",
		autoComplete : false,
		displayText : [],
		columns : [],
		separator : ",",
		disabled : false,
		multiple : false,
		gridMode : false, //grid模式
		gridTargetID : null, //grid模式下父子控件绑定ID（当前控件ID）
		pageSize : 0,
		data : [],//直接配置数据源，url优先
		confirm : function(data, value, text){
			
		},
		cancel : function(){
			
		},
		onComplete : function(){
			
		}
	};
})(jQuery);
