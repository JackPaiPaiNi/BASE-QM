/**
 *
 * 信息管理部软件开发科Bootstrap插件系列
 * 
 * jquery.sky.tree 树选择框-基于zTree、layer、skyAutoComplete
 *
 * 王歌 2016年11月
 */
(function($) {
	/**
	 * 创建框架 OK
	 */
	function initPanel(target, options) {
		var fa = options.multiple ? "fa-list" : "fa-indent";
		if(options.gridMode){
			$(target).wrap('<span style="cursor:pointer;" class="input-icon input-icon-right"></span>');
			$(target).parent("span").append('<i class="fa '+fa+' openBtn primary"></i>');
		}else{
			$('<span class="input-group-addon openBtn primary"><i class="fa '+fa+'"></i></span>').insertAfter(target).css({"cursor" : "pointer"});
		}
		var treeboxid = new Date().getTime();
		var panel = $('<div id="' + treeboxid + '" class="wrapper wrapper-content animated fadeInRight" style="display:none;">'
				+'<div id="tree_' + treeboxid + '" class="ztree"></div></div>').appendTo('body');

		panel.data("options", options);
		
		var span = $(target).parent();

		span.find(".openBtn").bind("click.skyTree", function() {
			open(target);
		});
		
		var append = span;
		if(options.gridMode){
			/*autoComplete定位默认与input-icon包围的input不兼容，故此处启用了appendTo，正常情况下可不用此属性*/
			append = $(target).closest("td");
		}
		
		if(options.autoComplete && !options.multiple){
			$(target).skyAutoComplete({
				displayText: options.displayText,
				text: options.tree.name,
				appendTo: append,
				afterSelect: function (data){
					if(options.confirm){
						setValue(target, data[options.tree.idKey])
						selectConfirm(target);
					}
				}
			});
		}
		
		return panel;
	}

	/**
	 * 打开弹出框 OK
	 */
	function open(target) {
		var panel = $(target).data("skyTree");
		var options = panel.data("options");
		
		var layerOptions = {
			id: "layer_" + panel.attr("id"),
			type : 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
			title : options.title, // 默认“信息”
			area : [ options.width, options.height ], // 宽高
			content : panel,
			success : function(layero, index) {
				// 当你需要在层创建完毕时即执行一些语句，可以通过该回调。success会携带两个参数，分别是当前层DOM当前层索引
				$(target).data("index", index);
			},
			end : function() {
				// 无论是确认还是取消，只要层被销毁了，end都会执行，不携带任何参数。
				$(target).removeData("index");
			},
			btn : [ "确定", "取消" ],
			yes : function(index, layero) {
				// 按钮1的回调:此处的yes可以写成btn1
				layer.close(index);
				selectConfirm(target);
			},
			btn2 : function(index, layero) {
				// 按钮2的回调
				if (options.cancel) {
					options.cancel();
				}
			},
			cancel : function() {
				// 右上角关闭回调:此处可以不写
			}
		};	

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
	 * 确认按钮 OK
	 */
	function selectConfirm(target) {
		var panel = $(target).data("skyTree");
		var options = panel.data("options");
		var treeId = "tree_" + panel.attr("id");

		var value = "", text = "";

		var zTree = $.fn.zTree.getZTreeObj(treeId);
		var checkedNode;

		if (options.multiple) {
			checkedNodeTemp = zTree.getCheckedNodes() || [];
			checkedNode = [];
			$.each(checkedNodeTemp, function(i, node){
				if(!(node.isParent && options.returnLeaf)){
					if (value != "") {
						value += options.separator;
						text += options.separator;
					}
					value += node[options.tree.idKey];
					text += node[options.tree.name];
					checkedNode.push(node);
				}
			});
		} else {
			checkedNode = zTree.getSelectedNodes() || [];
			value = checkedNode[0][options.tree.idKey];
			text = checkedNode[0][options.tree.name];
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

		$(target).data("selectedData", checkedNode);

		if (options.confirm) {
			if (!options.multiple) {
				options.confirm(checkedNode[0], value, text);
			}else{
				options.confirm(checkedNode, value, text);
			}
		}
	}

	/**
	 * 获取当前选的记录 OK
	 */
	function getValueData(target) {
		var panel = $(target).data("skyTree");
		var options = panel.data("options");
		var selectedData = $(target).data("selectedData")||[];

		if (!options.multiple) {
			selectedData = selectedData[0] || {};
		}
		return selectedData;
	}

	/**
	 * 给控件赋值并设置选择项，便于展示已选择记录，数据格式为"1,2,3" OK
	 */
	function setValue(target, value) {
		var panel = $(target).data("skyTree");
		var options = panel.data("options");
		var treeId = "tree_" + panel.attr("id");
		
		if (value == null) {
			value = "";
		} else {
			value += "";
		}
		var zTree = $.fn.zTree.getZTreeObj(treeId);

		var toMatch = value.split(options.separator);

		$.each(toMatch, function(i, n) {
			var treeNode = zTree.getNodeByParam(options.tree.idKey, n, null);

			if (options.multiple) {
				zTree.checkNode(treeNode, true, true);
			}else{
				zTree.selectNode(treeNode, true, true);
			}
		});
	}

	/**
	 * 取值 "1,2,3"
	 */
	function getValue(target, field) {
		if (field) {
			var valueData = getValueData(target);
			if (!options.multiple) {
				return valueData[field] || "";
			}else{
				var options = $(target).data("skyTree").options;

				var value = "";
				for (var i = 0; i < valueData.length; i++) {
					value += valueData[i][field] + options.separator;
					if (i != valueData.length) {
						value = value.substring(0, value.lastIndexOf(options.separator));
					}
				}
				return value;
			}
		} else {
			return $(target).data("value") || "";
		}
	}

	/**
	 * 锁定控件 OK
	 */
	function setDisabled(target, disabled) {
		var panel = $(target).data("skyTree");
		var options = panel.data("options");
		var span = $(target).parent();
		options.disabled = disabled;

		$(target).addClass("skydisabled").prop('disabled', 'disabled');
		span.find(".openBtn").removeClass("primary").unbind("click.skyTree");
		
		if (!disabled) {
			$(target).removeClass("skydisabled").removeProp('disabled').removeAttr('disabled');
			span.find(".openBtn").addClass("primary").bind("click.skyTree", function() {
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
		
		var panel = $(target).data("skyTree");
		var options = panel.data("options");
		options.data = data;
		var treeId = "tree_" + panel.attr("id");
		
		if(options.autoComplete && !options.multiple && !options.gridMode){
			$(target).skyAutoComplete("setSource", data);
		}
		
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		if(zTree){
			zTree.destroy();
		}

		var setting = {// zTree配置
			data : {
				key : {
					name : "name"// 显示字段
				},
				simpleData : {// 后台不用返回递归结构数据配置
					enable : true,
					idKey : "", // ID字段
					pIdKey : "", // 父节点ID字段
					rootPId : 0
				// 根节点的父节点默认值
				}
			},
			view : {
				dblClickExpand : false,
				selectedMulti : false
			// 无checkbox时不允许多选
			},
			check : {
				enable : false
			// 无checkbox
			},
			callback : {
				onClick : null,
				onDblClick : null,
				onCheck : null
			}
		};
		if(options.tree.name){
			setting.data.key.name = options.tree.name;
		}
		if(options.tree.idKey){
			setting.data.simpleData.idKey = options.tree.idKey;
		}
		if(options.tree.pIdKey){
			setting.data.simpleData.pIdKey = options.tree.pIdKey;
		}
		if(options.tree.rootPId){
			setting.data.simpleData.rootPId = options.tree.rootPId;
		}
		if(options.multiple){
			setting.check.enable = true;
			setting.view.selectedMulti = true;
			setting.callback.onClick = function(event, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				zTree.checkNode(treeNode, true, true);
			};
			setting.callback.onCheck = function(event, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				if(treeNode.checked ){
					zTree.checkNode(treeNode, true, true);
				}
			};
		}else{
			setting.callback.beforeClick = function(treeId, treeNode, clickFlag){
				return (!(treeNode.isParent && !options.returnLeaf));
			}
			setting.check.enable = false;
			setting.view.selectedMulti = false;
			setting.callback.onDblClick = function(event, treeId, treeNode) {
				if(!(treeNode.isParent && !options.returnLeaf)){
					selectConfirm(target);
					layer.close($(target).data("index"));
				}
			}
		}

		$.fn.zTree.init($("#"+treeId), setting, data);
		if(options.expand){
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			zTree.expandAll(true);
		}
		layer.close(index);
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
	 * 控件主体声明
	 */
	$.fn.skyTree = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.skyTree.methods[options](this, param);
		}
		
		return this.each(function() {
			var panel = $(this).data("skyTree");
			if (panel) {
				var oldOptions = panel.data("options");
				// 如果url有变化，重新加载数据
				if(options.url || !$.isEmptyObject(oldOptions.param)){
					if(options.url != oldOptions.url || !isObjectValueEqual(options.param, oldOptions.param)){
						options.data = loadUrl(options.url, options.param);
					}
				}
				$.extend(oldOptions, options);
			} else {
				options = $.extend({}, $.fn.skyTree.defaults, options);
				
				if (options.url) {
					// 如果有url，加载数据
					options.data = loadUrl(options.url, options.param);
				}

				var panel = initPanel(this, options);
				$(this).data("skyTree", panel);
			}
			if(options.data){
				loadData(this, options.data);
			}
			setDisabled(this, options.disabled);
		});
	};

	/**
	 * 控件方法声明
	 */
	$.fn.skyTree.methods = {
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
		}
	};

	/**
	 * 控件默认值
	 */
	$.fn.skyTree.defaults = {
		title : "选择框",
		width : "400px",
		height : "500px",
		url : "",
		param : {},//url参数
		valueElem : "",
		textElem : "",
		autoComplete : false,
		displayText : [],
		separator : ",",
		disabled : false,
		expand : true,
		data : [],
		multiple : false,
		returnLeaf: false,// 单选时只能选中叶子节点，多选时只返回叶子节点
		gridMode : false,
		tree : {
			name : "name",//显示字段
			idKey: "id", //ID字段
			pIdKey: "parent", //父节点ID字段
			rootPId: 0//根节点的父节点默认值
		},
		confirm : function(data) {

		},
		cancel : function() {

		}
	};
})(jQuery);
