/**
 * 
 * 信息管理部软件开发科Bootstrap插件系列
 * 
 * jquery.sky.skyFile html5文件上传美化
 * 
 * jquery.sky.uploader html5文件上传插件 基于layer
 * 
 * jquery.importFile 文件导入组件 基于layer jquery.sky.skyFile
 * 
 * jquery.fn.getUpfileListHtml 获取附件列表html，去掉删除按钮。与jquery.sky.uploader配套使用
 * 
 * jquery.fn.setUpfileListDelete 给附件列表设置删除按钮。与jquery.sky.uploader配套使用
 * 
 * 王歌 2016年12月
 */
(function($) {	
	/**
	 * 创建框架 OK
	 * @param target
	 * @param options
	 */
	function initPanel(target, options) {
		var uploaderid = new Date().getTime();		
		var panel = $('<div id="'+uploaderid+'" class="wrapper wrapper-content animated fadeInRight" style="display:none;"></div>').appendTo('body');

		panel.append('<div class="ibox m-t"><div class="ibox-content no-padding">\
			<table class="table table-hover">\
			<thead><tr><th>文件</th><th style="width:100px;">大小</th><th style="width:60px;">状态</th><th style="width:60px;">操作</th></tr></thead>\
			<tbody class="skyuploader-queue"></tbody></table>\
			</div></div>');

		panel.data("options", options);

		return panel;
	}
	
	/**
	 * 初始化控件 OK
	 * @param target
	 * @param options
	 */
	function initTargetElem(target, options){
		if(options.multiple){
			$(target).prop("multiple", "multiple");
		}
		$(target).prop("accept", getFileTypes(options.fileTypeExts).join(","));
		
		var input = '<input class="form-control skyFileInput '+(options.size == "large" ? 'input-large' : '')+'" type="text">';
		var btn = '<span class="input-group-btn"><a class="btn btn-info skyFileBtn">' + options.text + '</a></span>';
		var html = input + btn;
		if(options.position == "left"){
			html = btn + input;
		}
		
		if(!options.skyUploader){
			$(target).wrap('<div></div>');
			$(target).after('<div class="input-group '+(options.size == "large" ? '' : 'input-group-sm')+'">'+html+'</div>');
		}else{
			$(target).after(html);
		}
		var span = $(target).parent();
		$(target).hide();
		
		span.data("options", options);

		return span;
	}


	/**
	 * 绑定控件事件 OK
	 * @param target
	 */
	function bindEvents(target) {
		var span = $(target).data("skyFile");
		var options = span.data("options");

		span.find("*").unbind(".skyFile");
		
		span.find(".skyFileBtn,.skyFileInput").bind("click.skyFile", function(){
			span.find("input[type='file']").trigger("click");
		});
		
		span.find("input[type='file']").bind("change.skyFile", function(){
			var files = $(this)[0].files, info = '';

			if (files.length == 0)
				return false;
			if (!options.multiple || files.length == 1) {
				var path = $(this).val().split('\\');
				info = path[path.length - 1];
			} else if (files.length > 1) {
				info = files.length + ' 个文件已选择';
			}

			span.find(".skyFileInput").val(info);
			
			if(options.skyUploader){	
				uploadFiles(target, files);	
			}
		});
	}

	/**
	 * 打开弹出框 OK
	 * @param target
	 */
	function open(target) {
		var panel = $(target).data("skyUploader");
		var options = panel.data("options");
		
		var layerOptions = {
			id: "layer_" + panel.attr("id"),
			type : 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
			title : options.title, // 默认“信息”
			closeBtn: 0,
			area : [ "600px", "400px" ], // 宽高
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
				var result = confirm(target);
				if(result){
					layer.close(index);
				}
			},
			btn2 : function(index, layero) {
				// 按钮2的回调
				clear(target);
			},
			cancel : function() {
				// 右上角关闭回调:此处可以不写
				clear(target);
			}
		};
		layer.open(layerOptions);
		
		$(options.fileList).css({"height":"auto", "min-height":"28px"});
		if($(options.fileList).find(".upfile-list").length == 0){
			$(options.fileList).append('<ul class="upfile-list"></ul>');
		}
	}


	/**
	 * 上传全部文件 OK
	 * @param target
	 * @param files
	 */
	function uploadFiles(target, files){
		var span = $(target).data("skyFile");
		var options = span.data("options");
		// 过滤选择文件组
		var msg = "";
		var typeArray = getFileTypes(options.fileTypeExts);
		if (typeArray.length > 0) {
			for (var i = 0, len = files.length; i < len; i++) {
				var f = files[i];
				if (parseInt(formatFileSize(f.size, true)) <= options.fileSizeLimit) {
					if ($.inArray('*', typeArray) >= 0 || $.inArray(f.name.split('.').pop(), typeArray) >= 0) {
					} else {
						msg = '文件 "' + f.name + '" 类型不允许！只允许上传' + options.fileTypeExts;
						break;
					}
				} else {
					msg = '文件 "' + f.name + '" 大小超过'+ options.fileSizeLimit +'KB！';
					break;
				}
			}
		}
		if(msg != ""){
			layer.alert(msg, {
				shadeClose: true,
				title: "提示",
				icon: 2
			});
			clear(target);
			return;
		}
		
		open(target);

		for (var i = 0, len = files.length; i < len; i++) {
			files[i].index = i;
			files[i].status = 0;// 标记为未开始上传

			renderFile(target, files[i]);
		}
	}

	/**
	 * 根据选择的文件，渲染DOM节点 OK
	 * @param target
	 * @param file
	 */
	function renderFile(target, file) {
		var panel = $(target).data("skyUploader");
		var options = panel.data("options");
		
		// 上传队列显示的模板
		var itemTemplate = '<tr fileID="${fileID}" class="skyuploader-queue-item">\
			<td><div class="progress progress-striped progress-mini skyuploader-progress">\
				<div class="progress-bar progress-bar-default skyuploader-progress-bar" style="width: 0%;"></div>\
			</div>${fileName}</td>\
			<td>${fileSize}</td>\
			<td class="uploadedsize">上传中</td>\
			<td><a class="btn btn-minier btn-danger delfilebtn" href="javascript:void(0);"><i class="fa fa-remove"></i></a></td>\
		</tr>';

		var queueItem = $(itemTemplate.replace(/\${fileID}/g, file.index).replace(/\${fileName}/g, file.name).replace(/\${fileSize}/g,
				formatFileSize(file.size)).replace(/\${fileSize}/g, formatFileSize(file.size)));
		
		// 为删除文件按钮绑定删除文件事件
		queueItem.find(".delfilebtn").on("click.skyUploader", function() {
			deleteFile(target, file);
		});
		
		panel.find(".skyuploader-queue").append(queueItem);

		uploadFile(target, file);
	}

	/**
	 * 将文件的单位由bytes转换为KB或MB，若第二个参数指定为true，则永远转换为KB
	 */
	function formatFileSize(size, withKB) {
		if (size > 1024 * 1024 && !withKB) {
			size = (Math.round(size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
		} else {
			size = (Math.round(size * 100 / 1024) / 100).toString() + 'KB';
		}
		return size;
	}

	/**
	 * 将输入的文件类型字符串转化为数组,原格式为*.jpg;*.png
	 */
	function getFileTypes(str) {
		var result = [];
		var arr1 = str.split(",");
		for (var i = 0, len = arr1.length; i < len; i++) {
			result.push(arr1[i].split(".").pop());
		}
		return result;
	}

	/**
	 * 确定 OK
	 * @param target
	 */
	function confirm(target) {
		var span = $(target).data("skyFile");
		var files = span.find("input[type='file']")[0].files;
		
		var panel = $(target).data("skyUploader");
		var options = panel.data("options");
		
		if(!options.multiple){
			$(options.fileList).find('.upfile-list').empty();
		}
		
		var flag = true;
		
		for (var i = 0, len = files.length; i < len; i++) {
			var file = files[i];
			if(file.status == 0 || file.status == 1){
				flag = false;
				break;
			}else if(file.status == 2){
				// 只取上传成功的
				var result = file.result;
				
				if(result.status == "SUCCESS"){

					var li = $('<li><a href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI(\''+result.downloadurl+'\');">'
							+'<i class="fa fa-file-archive-o"></i> ' + result.filename + '</a>'
							+'<a class="upfile-delete" href="javascript:void(0);"><i class="fa fa-remove"></i></a></li>');
					li.find(".upfile-delete").click(function(){
						$(this).parent().remove();
					});

					$(options.fileList).find('.upfile-list').append(li);
				}
			}
		}
		
		if(!flag){
			layer.alert("文件未上传完毕！", {
				shadeClose: true,
				title: "提示",
				icon: 0
			});
		}else{
			clear(target);
		}
		
		return flag;
	}


	/**
	 * 清空 OK
	 * @param target
	 */
	function clear(target){
		var span = $(target).data("skyFile");
		var panel = $(target).data("skyUploader");
		
		panel.find(".skyuploader-queue").empty();
		span.find("input[type='file']").val("");
		span.find(".skyFileInput").val("");		
	}

	/**
	 * 删除文件 OK
	 * @param target
	 * @param file
	 */
	function deleteFile(target, file) {
		var span = $(target).data("skyFile");
		var files = span.find("input[type='file']")[0].files;
		
		var panel = $(target).data("skyUploader");
		var options = panel.data("options");

		for (var i = 0, len = files.length; i < len; i++) {
			var f = files[i];
			if (f.index == file.index) {
				panel.find("[fileID="+file.index+"]").remove();
				break;
			}
		}
	}

	/**
	 * 上传文件 OK
	 * @param target
	 * @param file
	 */
	function uploadFile(target, file) {
		var panel = $(target).data("skyUploader");
		var options = panel.data("options");
		
		var xhr = null;
		try {
			xhr = new XMLHttpRequest();
		} catch (e) {
			xhr = ActiveXobject("Msxml12.XMLHTTP");
		}
		if (xhr.upload) {
			// 上传中
			xhr.upload.onprogress = function(e) {
				// 更新进度条				
				var eleProgress = panel.find("[fileID="+file.index+"] .skyuploader-progress");
				var percent = (e.loaded / e.total * 100).toFixed(2) + '%';
				eleProgress.children('.skyuploader-progress-bar').css('width', percent);
				//panel.find("[fileID="+file.index+"] .uploadedsize").text(formatFileSize(e.loaded));
			};

			xhr.onreadystatechange = function(e) {
				if (xhr.readyState == 4) {
					var thisfile = panel.find("[fileID="+file.index+"]");
					if (xhr.status == 200) {
						// 校正上传完成后的进度条误差
						thisfile.find('.skyuploader-progress-bar').css('width', '100%');
						
						file.status = 2;// 标记为上传成功
						file.result = JSON.parse(xhr.responseText);
						// 在指定的间隔时间后删掉进度条
						setTimeout(function() {
							panel.find("[fileID="+file.index+"] .skyuploader-progress").remove();
						}, 1000);
					} else {
						file.status = 3;// 标记为上传失败
						var result = {};
						result.status = "ERROR";
						result.msg = xhr.responseText;
						file.result = result;
					}
					if(file.result.status != "SUCCESS"){
						thisfile.find('.uploadedsize').html("失败");
						thisfile.closest("tr").attr("title", file.result.msg);
					}else{
						thisfile.find('.uploadedsize').html("成功");
					}

					// 清除文件选择框中的已有值
					panel.find(".skyuploader-file").val("");
				}
			}

			if (file.status === 0) {
				file.status = 1;// 标记为正在上传
				// 开始上传
				xhr.open("post", options.url, true);
				xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
				var fd = new FormData();
				fd.append(options.fileObjName, file);
				if (options.formData) {
					for (key in options.formData) {
						fd.append(key, options.formData[key]);
					}
				}
				xhr.send(fd);
			}

		}
	}

	/**
	 * skyFile美化控件锁定控件 OK
	 */
	function setDisabled(target, disabled) {
		var span = $(target).data("skyFile");
		var options = span.data("options");

		options.disabled = disabled;
		span.find("*").unbind(".skyFile");
		span.find(".skyFileBtn").prop("disabled","disabled");
		span.find(".skyFileFile").val("").prop("disabled","disabled");
		
		if (!disabled) {
			span.find(".skyFileBtn,.skyFileFile").removeProp("disabled").removeAttr("disabled");

			bindEvents(target);
		}
	}

	/**
	 * skyUploader上传控件主体声明 OK
	 */
	$.fn.skyUploader = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.skyUploader.methods[options](this, param);
		}

		return this.each(function() {
			var panel = $(this).data("skyUploader");
			if (panel) {
				var oldOptions = panel.data("options");
				$.extend(oldOptions, options);
			} else {
				options = $.extend(true, {}, $.fn.skyUploader.defaults, options);
				var skyFileOptions = $.extend(true, {}, $.fn.skyFile.defaults,{
					text : options.text,
					multiple : options.multiple,
					fileTypeExts : options.fileTypeExts,
					fileSizeLimit : options.fileSizeLimit,
					disabled : options.disabled,
					skyUploader : true
				});				
				panel = initPanel(this, options);
				$(this).data("skyUploader", panel);
				
				var span = initTargetElem(this, skyFileOptions);
				$(this).data("skyFile", span);
				bindEvents(this);
			}
			setDisabled(this, skyFileOptions.disabled);
		});
	};

	/**
	 * skyUploader上传控件方法声明 OK
	 */
	$.fn.skyUploader.methods = {
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
	 * skyUploader上传控件默认值 OK
	 */
	$.fn.skyUploader.defaults = {
		title : "文件上传",
		fileList : null,
		text : "请选择文件",
		fileTypeExts : "*.*",// 允许上传的文件类型，格式"*.jpg,*.doc"
		url : "",// 文件提交的地址
		multiple : true,// 是否允许选择多个文件,
		disabled : false,
		formData : null,// 发送给服务端的参数，格式：{key1:value1,key2:value2}
		fileObjName : "file",// 在后端接受文件的参数名称，如PHP中的$_FILES["file"]
		fileSizeLimit : 102400// 允许上传的文件大小，单位KB
	};

	/**
	 * skyFile美化控件主体声明 OK
	 */
	$.fn.skyFile = function(options) {
		return this.each(function() {
			options = $.extend(true, {}, $.fn.skyFile.defaults, options);			
			var span = initTargetElem(this, options);
			$(this).data("skyFile", span);
			bindEvents(this);
		});
	};

	/**
	 * skyFile美化控件默认值 OK
	 */
	$.fn.skyFile.defaults = {
		text : "请选择文件",
		size : "small",
		position : "right",
		fileTypeExts : "*.*",// 允许上传的文件类型，格式"*.jpg,*.doc"
		fileSizeLimit : 102400,// 允许上传的文件大小，单位KB
		multiple : false,
		skyUploader : false,
		disabled : false
	};
})(jQuery);


jQuery.extend({
	// 文件导入
	importFile : function(options) {
		var defaultOptions = {title : "文件导入", url : "", param : {}, templateUrl : "", confirm : function (data){}};
		options = $.extend(true, defaultOptions, options); 
		var panel = $(
				'<div class="wrapper wrapper-content animated fadeInRight" style="display:none;">\
				<p><input type="file" name="file"><p>\
				</div>')
				.appendTo('body');
		panel.find("input[type=file]").skyFile({size:"large", position:"left", fileTypeExts:"*.xls,*.xlsx"});
		
		if(options.templateUrl != ""){
			panel.prepend('<p><button class="btn btn-primary btn-xs btn-outline btn_download"><i class="fa fa-file-excel-o"></i>&nbsp;模板下载</button>\
					<span class="text-primary">请确认导入数据格式与模板一致!</span></p>');
			panel.find(".btn_download").click(function(){
				 window.location.href=encodeURI(options.templateUrl);
			});
		}

		layer.open({
			type : 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
			title : options.title, // 默认“信息”
			area : [ '400px' ], // 宽
			content : panel,
			end : function() {
				// 无论是确认还是取消，只要层被销毁了，end都会执行，不携带任何参数。
				panel.remove();
			},
			btn : [ '确定', '取消' ],
			btn1 : function(index, layero) {
				// 按钮1的回调（保存）
				var indexLoading = layer.load(1, {
					shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
				var data = new FormData();
				data.append("file", panel.find("input[type=file]")[0].files[0]);
				if (options.param) {
					$.each(options.param, function(name, value) {
						data.append(name, value);
					});
				}
				layer.close(index);
				setTimeout(function(){
					$.ajax({
						url : options.url,
						type : 'POST',
						data : data,
						async : false,
						cache : false,
						contentType : false,
						processData : false,
						dataType : 'json',
						complete : function (XMLHttpRequest, textStatus) {
							layer.close(indexLoading);
						},
						success : function(data) {
							if (options.confirm) {
								options.confirm(data);
							}
						}
					});					
				},2000);
			}
		});
	}
});

jQuery.fn.extend({
	getUpfileListHtml : function(){
		var upfileList = $(this).clone();
		upfileList.find(".upfile-delete").remove();
		var html = upfileList.html();
		upfileList.remove();
		return html;
	},
	setUpfileListDelete : function(){
		var upfileList = $(this).find('.upfile-list');
		$.each(upfileList.find("li"), function(index, li){
			$(li).append('<a class="upfile-delete" href="javascript:void(0);"><i class="fa fa-remove"></i></a>');
		});
		upfileList.find(".upfile-delete").click(function(){
			$(this).parent().remove();
		});
	}
});
