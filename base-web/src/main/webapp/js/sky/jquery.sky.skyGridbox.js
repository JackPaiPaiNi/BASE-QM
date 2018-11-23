/**
 *
 * 信息管理部软件开发科Bootstrap插件系列
 * 
 * jquery.sky.gridSelect 本地数据选择框-基于gridSelect
 *
 * 王歌 2016年11月
 */
(function($) {
	
	/**
	 * 初始化绑定位置
	 * @param target
	 */
	function initTargetElem(target, options){
		$(target).attr("gridTargetID", options.gridTargetID.replace("#",""));
		$(target).wrap('<span style="cursor:pointer;" class="input-icon input-icon-right"></span>');
		var span = $(target).parent("span");
		var fa = options.multiple ? "fa-search-plus" : "fa-search";
		span.append('<i class="fa '+fa+' openBtn primary"></i>');

		span.find(".openBtn").bind("click.gridSelect", function() {
			open(target);
		});
		
		var panel = $(options.gridTargetID).data("skySelect");
		var panelOptions = panel.data("options");
		
		if(panelOptions.autoComplete && !panelOptions.multiple){
			$(target).skyAutoComplete({
				data: panelOptions.data,
				displayText: panelOptions.displayText,
				text: panelOptions.textColumn,
				appendTo: $(target).closest("td"),/*autoComplete定位默认与input-icon包围的input不兼容，故此处启用了appendTo，正常情况下可不用此属性*/
				afterSelect: function (data){
					$(options.gridTargetID).data("gridSelect", target)
					$(options.gridTargetID).skySelect("setValue", data[panelOptions.valueColumn]);
					if(options.confirm){
						options.confirm(data, data[panelOptions.valueColumn], data[panelOptions.textColumn]);
					}
				}
			});
		}
		
		span.data("options", options);
		return span;
	}

	/**
	 * 打开弹出框
	 * @param target
	 */
	function open(target){
		var span = $(target).data("gridSelect");
		var options = span.data("options");
		// 打开时用当前控件值设置父控件的值
		var value = $(target).data("value");
		$(options.gridTargetID).skySelect("setValue", value);
		// 打开时记录是那个子控件打开
		$(options.gridTargetID).data("gridSelect", target);
		$(options.gridTargetID).skySelect("open");
	}

	/**
	 * 锁定控件
	 * @param target
	 * @param disabled
	 */
	function setDisabled(target, disabled) {
		var span = $(target).data("gridSelect");
		var options = span.data("options");
		var span = $(target).parent();
		
		options.disabled = disabled;
		$(target).addClass("skydisabled").prop('disabled', 'disabled');
		span.find(".openBtn").removeClass("primary").unbind("click.gridSelect");
		
		if (!disabled) {
			$(target).removeClass("skydisabled").removeProp('disabled').removeAttr('disabled');
			span.find(".openBtn").addClass("primary").bind("click.gridSelect", function() {
				open(target);
			});
		}
	}

	/**
	 * gridSelect控件主体声明
	 */
	$.fn.gridSelect = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.gridSelect.methods[options](this, param);
		}

		return this.each(function() {
			var span = $(this).data("gridSelect");
			
			if (!span) {
				var span = initTargetElem(this, options);
				$(this).data("gridSelect", span);
			}
			setDisabled(this, options.disabled);
		});
	};

	/**
	 * gridSelect控件方法声明
	 */
	$.fn.gridSelect.methods = {
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
	 * gridSelect控件默认值
	 */
	$.fn.gridSelect.defaults = {
		gridTargetID : null, //父子控件绑定ID（父控件ID）
		confirm : function(data, value, text){
			
		},
		cancel : function(){
			
		}
	};
})(jQuery);
