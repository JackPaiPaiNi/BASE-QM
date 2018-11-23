/**
 *
 * 信息管理部软件开发科Bootstrap插件系列
 * 
 * jquery.sky.jqgrid 基于jqGrid、jquery.sky.form、layer
 *
 * 王歌 2016年11月
 */
(function($) {
	/**
	 * 控件主体声明
	 */
	$.fn.skyGrid = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.skyGrid.methods[options](this, param);
		}
		var grid = this;
		var skyOptions = $.extend(true, {}, $.fn.skyGrid.skyOptions, options.sky);
		var gridOptions = $.extend(true, {}, $.fn.skyGrid.defaults, options);
		gridOptions.sky = null;
		// 编辑表不能翻页
		if (skyOptions.editable) {
			// 编辑表不能锁定列
			skyOptions.frozen = false;
			gridOptions.pager = null;
			$(grid).data('editable', true);
			
			var multiple = skyOptions.edit.multiple;
			$(grid).data('multiple', multiple);
			if(!multiple){
				// 单行编辑模式时双击可编辑
				gridOptions.ondblClickRow = function(rowid,iRow,iCol,e){
					editInlineRow(grid, rowid);
					var tr = $(grid).jqGrid("getGridRowById",rowid);
					setTimeout(function(){ 
						var fe = $("td:eq("+iCol+") :input:visible",tr).not(":disabled"); 
						if(fe.length > 0) {
							fe.focus();
						}
					},0);
				}
			}
		}
		if (skyOptions.pager) {
			gridOptions.pager = skyOptions.pager;
		}else{
			gridOptions.rowNum = 0;
		}
		// 将form查询条件带入url
		if (skyOptions.search.form) {
			gridOptions.serializeGridData = function(postData) {
				var formData = $(skyOptions.search.form).getFormData();
				$.extend(postData, formData);
				return postData;
			}
		}
		// 初始化jqGrid
		$(grid).jqGrid(gridOptions);
		// 合并表头
		if (skyOptions.groupHeaders.length > 0){
			var groupHeaders = [];
			$.each(skyOptions.groupHeaders, function(index, groupHeader){
				$(grid).setGroupHeaders({
					useColSpanStyle: true,
					groupHeaders:groupHeader
				});
				groupHeaders.push({index : index, groupHeaderList : groupHeader});
			});
			$(grid).data('groupHeaders', groupHeaders);
		}
		// 根据parent设置jqGrid高度
		if (skyOptions.parent) {
			var pagerHeight = 32;
			var headerHeight = 0;
			if (skyOptions.groupHeaders.length > 0){
				headerHeight = skyOptions.groupHeaders.length * 28;
			}
			if(!options.height){
				if(skyOptions.pager){
					pagerHeight = 0;
				}
				$(grid).setGridHeight($(skyOptions.parent).height() + pagerHeight - headerHeight);
			}else{
				$(grid).setGridHeight(options.height);
			}
			$(grid).setGridWidth($(skyOptions.parent).width());
			// 窗口大小改变重置jqGrid宽度、高度
			$(window).resize(function() {
				// 锁定列resize会错位，先取消锁定，resize后再锁定
				if (skyOptions.frozen) {
					$(grid).jqGrid('destroyFrozenColumns');
				}
				$(grid).setGridWidth($(skyOptions.parent).width());
				if(!options.height){
					if(skyOptions.pager){
						pagerHeight = 0;
					}
					$(grid).setGridHeight($(skyOptions.parent).height() + pagerHeight - headerHeight);
				}else{
					$(grid).setGridHeight(options.height);
				}
				if (skyOptions.frozen) {
					$(grid).jqGrid('setFrozenColumns');
				}
			});
		}
		// 只显示刷新按钮
		if (skyOptions.pager) {
			if(gridOptions.url){
				$(grid).navGrid(skyOptions.pager, {
					search : false,
					add : false,
					edit : false,
					del : false,
					refreshtext : "刷新",
					refreshicon : "fa fa-refresh text-success"
				});
			}
		}
		// 固定列
		if (skyOptions.frozen) {
			$(grid).jqGrid('setFrozenColumns');
		}
		// 查询按钮事件
		if (skyOptions.search.searchBtn) {
			$(skyOptions.search.searchBtn).click(function() {
				var flag = true;
				if(skyOptions.search.beforeSearch){
					flag = skyOptions.search.beforeSearch();
				}
				if(!flag){
					return;
				}
				if(skyOptions.search.validate){					
					if(!$(skyOptions.search.form).valid()){
			    		return;
			    	}
				}
				if (skyOptions.search.searchFunction) {
					skyOptions.search.searchFunction();
				} else {
					// 点按钮查询时将页码置为1
					$(grid).setGridParam({
						datatype : "json"
					}).trigger("reloadGrid", [{page : 1}]);
				}
			});
		}
		// 导出按钮事件
		if (skyOptions.search.exportBtn && skyOptions.search.exportUrl && skyOptions.search.form && skyOptions.search.exportName) {
			$(skyOptions.search.exportBtn).click(function() {
				var flag = true;
				if(skyOptions.search.beforeExport){
					flag = skyOptions.search.beforeExport();
				}
				if(!flag){
					return;
				}
				if(skyOptions.search.validate){					
					if(!$(skyOptions.search.form).valid()){
			    		return;
			    	}
				}
				if (skyOptions.search.exportFunction) {
					skyOptions.search.exportFunction();
				} else {
					var exportList = [];
					exportList.push({fileName : skyOptions.search.exportName, grid : grid});
					$.exportExcel(skyOptions.search.form, skyOptions.search.exportUrl, exportList, skyOptions.search.exportName);
				}
			});
		}
	};
	
	/**
	 * 编辑表新增行
	 */
	function addInlineRow(grid){
		if(!$(grid).data('editable')){
			return;
		}
		
		// 单行编辑模式需要记录lastEdit
		var result = true;
		var multiple = $(grid).data('multiple');
		if(!multiple){
			var lastEdit = $(grid).data('lastEdit');
			if (lastEdit) {
				/*验证通过返回true*/
				result = $(grid).jqGrid('saveRow', lastEdit, false, 'clientArray');
			}
		}	
		
		if(result){
			var id = new Date().getTime();
			$(grid).jqGrid('addRow', {
				rowID : id,
				initdata : {id : id},
				position : "last",
				useDefValues : true,
				useFormatter : false,
				addRowParams : {
					extraparam : {}
				}
			});
			if(!multiple){
				$(grid).data('lastEdit', id);
			}
		}
	}
	
	/**
	 * 编辑表编辑行
	 */
	function editInlineRow(grid, rowid){
		// 只支持有单行编辑
		if(!$(grid).data('editable') || $(grid).data('multiple')){
			return;
		}
		var result = true;
		var lastEdit = $(grid).data('lastEdit');
		if(lastEdit == rowid){
			return;
		} 
		if(lastEdit){
			/*验证通过返回true*/
			result = $(grid).jqGrid('saveRow', lastEdit, false, 'clientArray');
		}
		if(result){
			$(grid).jqGrid('editRow', rowid);
			$(grid).data('lastEdit', rowid);
		}
	}
	
	/**
	 * 编辑表删除行
	 */
	function deleteInlineRow(grid, rowid){
		if(!$(grid).data('editable')){
			return;
		}
		var lastEdit = $(grid).data('lastEdit');
		$(grid).jqGrid('delRowData', rowid);
		
		if(lastEdit == rowid){
			$(grid).removeData('lastEdit');
		}
	}
	
	/**
	 * 编辑表结束编辑
	 */
	function endEdit(grid){
		if(!$(grid).data('editable')){
			return true;
		}
		var result = true;
		if(!$(grid).data('multiple')){
			// 单行编辑模式 只剩最后一行
			var lastEdit = $(grid).data('lastEdit');
			if(lastEdit){
				/*验证通过返回true*/
				result = $(grid).jqGrid('saveRow', lastEdit, false, 'clientArray');
			}
			if(result){
				$(grid).removeData('lastEdit');
			}
		}else{
			// 多行编辑模式 所有行
			var ids = $(grid).jqGrid("getDataIDs");
			var err = [], succ = [];
			var msg = "";
			$.each(ids, function(index, rowid){
				//saveRow : function(rowid, successfunc, url, extraparam, aftersavefunc,errorfunc, afterrestorefunc)
				var result = $(grid).jqGrid('saveRow', rowid, {
					url : "clientArray",
					errorfunc : function(rowid, rowIndex, message){
						var tmpErr = {rowid : rowid, rowIndex : rowIndex, msg : message};
						err.push(tmpErr);
						if(msg != ""){
							msg += "<br>";
						}
						msg += "第" + rowIndex + "行：" + message;
					}
				});
				if(result){
					succ.push(rowid);
				}
			});
			if(err.length > 0){
				result = false;
				if(succ.length > 0){
					$.each(succ, function(index, rowid){
						$(grid).jqGrid('editRow', rowid);
					});
				}
				layer.alert(msg, {
    				shadeClose: true,
    				title: "校验不通过",
    				icon: 2
    			});
			}
		}
		return result;
	}

	/**
	 * 控件方法声明
	 */
	$.fn.skyGrid.methods = {
		addInlineRow : function(jq) {
			return addInlineRow(jq[0]);
		},
		editInlineRow : function(jq, rowid) {
			return editInlineRow(jq[0], rowid);
		},
		deleteInlineRow : function(jq, rowid) {
			return deleteInlineRow(jq[0], rowid);
		},
		endEdit : function(jq) {
			return endEdit(jq[0]);
		}/*,
		getValueData : function(jq) {
			return getValueData(jq[0]);
		},
		getValue : function(jq, field) {
			return getValue(jq[0], field);
		},
		setValue : function(jq, value) {
			return jq.each(function() {
				setValue(this, value);
			});
		}*/
	};

	/**
	 * 控件默认值
	 */
	$.fn.skyGrid.skyOptions = {
		parent : null,
		pager : null,
		frozen : false,
		search : {
			form : null,
			validate : false,
			searchBtn : null,
			searchFunction : null,
			beforeSearch : null,
			exportBtn : null,
			exportUrl : null,
			exportName : null,
			exportFunction : null,
			beforeExport : null
		},
		groupHeaders : [],
		editable : false,
		edit : {
			multiple : false,
			addBtn : null,
			completeBtn : null
		}
	};
	$.fn.skyGrid.defaults = {
		styleUI : "Bootstrap",
		loadtext : "正在加载数据，请稍候……",
		rownumbers : true,
		jsonReader : {
			root : "rows",
			total : "totalPage",
			records : "total",
			repeatitems : true
		},
		// caption : "信息",
		viewrecords : true,
		// hidegrid : false,
		url : null,
		mtype : "POST",
		datatype : "json",
		height : "auto",
		autowidth : true,
		shrinkToFit : false,
		autoScroll : false,
		altRows : true,// 设置为交替行表格
		cmTemplate : {
			sortable : false
		},//这里定义的属性覆盖默认的colModel配置值
		rowNum : 20,
		rowList : [ 10, 20, 30, 50 ],
		loadError: function(XMLHttpRequest, textStatus, errorThrown) {  
			var subStr = XMLHttpRequest.responseText.substring(0,14);
			var msg = "";
			if(subStr.indexOf("success:false") != -1){
				msg = XMLHttpRequest.responseText.substring(24,XMLHttpRequest.responseText.length-2);
				layer.alert("数据加载失败！"+msg, {
					shadeClose: true,
					title: "提示",
					icon: 2
				});
			}
		}
	};
})(jQuery);

jQuery.extend($.fn.fmatter, {
	btn : function(cellvalue, options, rowData) {
		var ops = {
			title : "",
			btnClass : "",
			func : "",
			args : [],
			text : "",
			icon : "",
			enable : {name : "", value : []}
		};		
		var btn = '<div style="margin:-2px">';
		if (options.colModel.formatoptions !== undefined) {
			ops = $.extend(ops, options.colModel.formatoptions);
			var disabled = 'disabled="disabled"';
			if(ops.enable.name){
				$.each(ops.enable.value, function(index, value){
					if(rowData[ops.enable.name] == value){
						disabled = "";
						return false;
					}
				});
				if(disabled != ""){
					ops.btnClass = "btn-default";
				}
			}else{
				disabled = "";
			}
			btn += '<button ' + disabled + ' title="' + ops.title + '" class="btn btn-outline btn-xs ' + ops.btnClass + '" type="button" onclick="javascript:' + ops.func + '(';
			for (var i = 0; i < ops.args.length; i++) {
				btn += '\'' + rowData[ops.args[i]] + '\'';
				if (i < ops.args.length - 1) {
					btn += ',';
				}
			}
			btn += ');">';
			btn += '<i class="fa ' + ops.icon + '"></i>' + ops.text + '</button>';
		} else {
			btn += '<button title="" class="btn btn-outline btn-primary" type="button" onclick="javascript:;"><i class="fa fa-pencil-square-o"></i></button>';
		}
		btn += '</div>';
		return btn;
	},
	skyDate : function(cellvalue, options, rowData) {
		var ops = {
				format : "yyyy-MM-dd HH:mm:ss"
			};
		if (options.colModel.formatoptions !== undefined) {
			ops = $.extend(ops, options.colModel.formatoptions);
			if(cellvalue != null){
				return new Date(Date.parse(cellvalue.replace(/-/g, "/"))).format(ops.format);
			}else{
				return "";
			}
		}
		return cellvalue;
	},
	skyNumber : function(cellvalue, options, rowData) {
		var ops = {
				format : "0",
				unit : 1
			};
		if (options.colModel.formatoptions !== undefined) {
			ops = $.extend(ops, options.colModel.formatoptions);
			if(cellvalue != null && !isNaN(cellvalue)){
				var value = Number(cellvalue);
				if(ops.unit){
					value = value/ops.unit;
				}
				return numeral(value).format(ops.format);
			}else{
				return "";
			}
		}
		return cellvalue;
	}
});

