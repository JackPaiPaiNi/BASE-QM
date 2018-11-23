/**
 * 
 * 信息管理部软件开发科jQuery插件系列
 * 
 * jquery.sky.form from组件
 * 
 * ecportExcel 根据sky.jqgrid导出Excel
 * 
 * skyDate 基于myDate97
 * 
 * getLocalCache,clearAllCache,isSupportCache浏览器本地缓存
 * 
 * numToRMB 人民表小写转大写
 *
 * 王歌 2016年11月
 */
jQuery.fn.extend({
	getFormData : function() {
		var form = $(this);
		form.removeDisabled();
		var formArray = form.serializeArray();
		form.addDisabled();
		var data = {};
		$.each(formArray, function(i, n){
			if (n.name) {
				if (typeof (data[n.name]) == 'undefined') {
					data[n.name] = n.value;
				} else {
					if(n.value != ""){
						data[n.name] += "," + n.value;
					}
				}
			}
		});
		return data;
	},
	setFormData : function(data){
		var form = $(this);
		form.find(".skycheckbox").prop("checked","checked");
		var values = form.getFormData();
		form.find(".skycheckbox").removeProp("checked").removeAttr("checked");
		$.each(values, function(name, value){
			var val = data[name];
			var element = form.find("[name="+name+"]");			
			
			val = (val != null) ? val+"" : "";
			
			if(element.hasClass("skycheckbox")){
				if(val.indexOf(",") > -1){
					var temp = val.split(",");
					$.each(temp, function(i, n){
						form.find("[name="+name+"][value="+n+"]").prop("checked","checked").trigger("change");
					})
				}else if(val == 1){
					form.find("[name="+name+"][value="+val+"]").prop("checked","checked").trigger("change");
				}else{
					element.removeProp("checked").removeAttr("checked").trigger("change");
				}
			}else if(element.hasClass("skyselect")){
				if(val.indexOf(",") > -1){
					element.val(val.split(",")).trigger("change");
				}else{
					element.val(val).trigger("change");
				}
			}else if(element.hasClass("skyradio")){
				if(val != ""){
					form.find("[name="+name+"][value="+val+"]").prop("checked","checked").trigger("change");
				}else{
					element.removeProp("checked").removeAttr("checked").trigger("change");
				}
			}else {
				element.val(val);
			}
		});
	},
	addDisabled : function(){
		var form = $(this);
		form.find(".skydisabled").prop("disabled","disabled");
	},
    removeDisabled : function(){
    	var form = $(this);
    	form.find(".skydisabled").removeProp("disabled").removeAttr("disabled");
	},
    reset : function(){
    	$(this)[0].reset();
	},
    clear : function(){
    	var form = $(this);
    	$('.input-group', form).removeClass('has-error');
    	form.setFormData({});
	},
	skyDate : function(options){
		var $this = $(this);
		$this.addClass("Wdate");
		
		var tmp = $.extend(true,{
			format:'yyyy-MM-dd',
			maxDateID:null,
			minDateID:null,
			maxDate:null,
			minDate:null
		}, options);
		
		var ops = {};
		
		if(tmp.format){
			ops.dateFmt = tmp.format
		}
		
		if(tmp.maxDateID){
			//动态根据指定元素ID设置日期范围
			ops.maxDate = '#F{$dp.$D(\''+tmp.maxDateID+'\',{d:0});}'
		}else if(tmp.maxDate){
			//根据指定日期值设定日期范围
			ops.maxDate = tmp.maxDate
		}
		if(tmp.minDateID){
			ops.minDate = '#F{$dp.$D(\''+tmp.minDateID+'\',{d:0});}'
		}else if(tmp.minDate){
			ops.minDate = tmp.minDate
		}
		
		$this.bind("click.skyDate", function(){
			WdatePicker(ops);
		});
	},
	removeSkyDate : function(){
		$(this).unbind(".skyDate").removeClass("Wdate");
		$dp.unbind($(this)[0]);
	}
});
jQuery.extend({
	// 导出excel
	exportExcel : function(form, url, exportList, fileName){
		//exportList=[{filename : "", grid : ""}]
		var index = layer.load(1, {
			shade: [0.1,'#fff'] //0.1透明度的白色背景
		});

		// 查询条件
		var params = $(form).getFormData();
		var exportParamList = [];
		$.each(exportList, function(i, exportGrid){
			// 合并列
			var groupHeadersList = $(exportGrid.grid).data('groupHeaders')||[];		
			// 表头
			var colModel = $(exportGrid.grid).getGridParam("colModel");
			var columns = [];
			var columnNum = 0;
			$.each(colModel, function(i, column) {
				if(column.isExcel == undefined){
					column.isExcel = true;
				}
				var flag = false;
				if(column.hidden && column.isHiddenExcel){
					flag = true;
				}else if (column.name != 'cb' && column.name != 'subgrid' && column.name != 'rn' 
					&& column.name != 'btn' && column.name != 'id' && !column.hidden && column.isExcel) {
					flag = true;
				}
				if (flag) {
					var col = {};
					col.label = column.label;
					col.name = column.name;
					if(column.formatter){
						if(column.formatter == "skyDate"){
							col.skyFormatType = "date";
							col.skyFormat = column.formatoptions.format;
						}else if(column.formatter == "skyNumber"){
							col.skyFormatType = "number";
							col.skyFormat = column.formatoptions.format;
							col.skyFormatUnit = column.formatoptions.unit;
						}
					}
					columns.push(col);
					
					$.each(groupHeadersList, function(index, groupHeaders){
						$.each(groupHeaders.groupHeaderList, function(j, groupHeader){
							if(groupHeader.startColumnName == column.name){
								groupHeader.startColumn = columnNum;
								return false;
							}
						});
					});
					
					columnNum++;
				}else{					
					$.each(groupHeadersList, function(index, groupHeaders){
						$.each(groupHeaders.groupHeaderList, function(j, groupHeader){
							if(groupHeader.startColumnName == column.name){
								groupHeaders.groupHeaderList.splice(j, 1);
								return false;
							}
						});
					});
				}
			});
			
			exportParamList.push({fileName : exportGrid.fileName, columns : columns, groupHeaders : groupHeadersList});
		});
		
		if(exportList.length == 1){
			// 单个文件导出
			params["exportParamList"] = JSON.stringify(exportParamList[0]);
		}else{
			params["exportParamList"] = JSON.stringify(exportParamList);
		}
		// 生成文件
		$.post(url, params, function(data){
			window.location.href = encodeURI($.locationpath
					+ '/core/file/download?realName=' + data + '&fileName=' + fileName);
			layer.close(index);
		}, "text");
	},
	// 获取缓存数据
	getLocalCache : function(key,url,callBakFn) {
		var _flag = 1;// 1支持缓存 2强制刷新3浏览器不支持缓存
		var timestamp = "";
		var usercode = "";
		if ($.isSupportCache()) {
			if (localStorage.getItem(key)) {
				var _data = JSON.parse(localStorage.getItem(key));
				if (_data.timestamp) {
					timestamp = _data.timestamp;
				}else{
					_flag = 2;// 强制刷新
					timestamp = "-1";//强制取最新
				}
				if (_data.usercode) {
					usercode = _data.usercode;
				}else{
					_flag = 2;// 强制刷新
					timestamp = "-1";//强制取最新
				}
			}
		} else {
			_flag = 3;// 浏览器不支持缓存
			timestamp = "-1";//强制取最新
		}		
	
		$.ajax({
			url : url,
			type : 'POST',
			data : {
				timestamp : timestamp,
				usercode : usercode
			},
			dataType : 'json',
			timeout : 100000,
			error : function() {
				return [];
			},
			success : function(result) {
				if (_flag == 3) {
					if (callBakFn) {
						callBakFn(result.rows);
					}
				} else {
					if ((_flag == 1 && (timestamp != result.timestamp || usercode != result.usercode)) || _flag == 2) {
						localStorage.removeItem(key);
						localStorage.setItem(key, JSON.stringify(result));
					}
					if (callBakFn) {					
						callBakFn(JSON.parse(localStorage.getItem(key)));							
					}
				}
			}
		});
	},
	// 清除本地所有缓存
	clearAllCache : function() {
		localStorage.clear();
	},
	// 判断浏览器是否支持storage缓存机制
	isSupportCache : function() {
		try {
			return 'localStorage' in window && window['localStorage'] !== null;
		} catch (e) {
			return false;
		}
	},
	/**
	 * 人民币小写转大写
	 * @param num
	 * @returns {String}
	 */
	numToRMB : function (num) {
		// Constants:
		var MAXIMUM_NUMBER = 99999999999.99;
		// Predefine the radix characters and currency symbols for output:
		var CN_ZERO = "零";
		var CN_ONE = "壹";
		var CN_TWO = "贰";
		var CN_THREE = "叁";
		var CN_FOUR = "肆";
		var CN_FIVE = "伍";
		var CN_SIX = "陆";
		var CN_SEVEN = "柒";
		var CN_EIGHT = "捌";
		var CN_NINE = "玖";
		var CN_TEN = "拾";
		var CN_HUNDRED = "佰";
		var CN_THOUSAND = "仟";
		var CN_TEN_THOUSAND = "万";
		var CN_HUNDRED_MILLION = "亿";
		var CN_DOLLAR = "元";
		var CN_TEN_CENT = "角";
		var CN_CENT = "分";
		var CN_INTEGER = "整";

		// Variables:
		var integral; // Represent integral part of digit number.
		var decimal; // Represent decimal part of digit number.
		var outputCharacters; // The output result.
		var parts;
		var digits, radices, bigRadices, decimals;
		var zeroCount;
		var i, p, d;
		var quotient, modulus;

		// Validate input string:
		num = num.toString();
		if (num == "") {
			alert("Empty input!");
			return "";
		}
		if (num.match(/[^,.\d]/) != null) {
			alert("Invalid characters in the input string!");
			return "";
		}
		if ((num)
				.match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
			alert("Illegal format of digit number!");
			return "";
		}

		// Normalize the format of input digits:
		num = num.replace(/,/g, ""); // Remove comma
															// delimiters.
		num = num.replace(/^0+/, ""); // Trim zeros at the
		// beginning.
		// Assert the number is not greater than the maximum number.
		if (Number(num) > MAXIMUM_NUMBER) {
			alert("Too large a number to convert!");
			return "";
		}

		// Process the coversion from currency digits to characters:
		// Separate integral and decimal parts before processing coversion:
		parts = num.split(".");
		if (parts.length > 1) {
			integral = parts[0];
			decimal = parts[1];
			// Cut down redundant decimal digits that are after the second.
			decimal = decimal.substr(0, 2);
		} else {
			integral = parts[0];
			decimal = "";
		}
		// Prepare the characters corresponding to the digits:
		digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE,
				CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
		radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
		bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
		decimals = new Array(CN_TEN_CENT, CN_CENT);
		// Start processing:
		outputCharacters = "";
		// Process integral part if it is larger than 0:
		if (Number(integral) > 0) {
			zeroCount = 0;
			for (i = 0; i < integral.length; i++) {
				p = integral.length - i - 1;
				d = integral.substr(i, 1);
				quotient = p / 4;
				modulus = p % 4;
				if (d == "0") {
					zeroCount++;
				} else {
					if (zeroCount > 0) {
						outputCharacters += digits[0];
					}
					zeroCount = 0;
					outputCharacters += digits[Number(d)] + radices[modulus];
				}
				if (modulus == 0 && zeroCount < 4) {
					outputCharacters += bigRadices[quotient];
				}
			}
			outputCharacters += CN_DOLLAR;
		}
		// Process decimal part if there is:
		if (decimal != "") {
			for (i = 0; i < decimal.length; i++) {
				d = decimal.substr(i, 1);
				if (d != "0") {
					outputCharacters += digits[Number(d)] + decimals[i];
				}
			}
		}
		// Confirm and return the final output string:
		if (outputCharacters == "") {
			outputCharacters = CN_ZERO + CN_DOLLAR;
		}
		if (decimal == "") {
			outputCharacters += CN_INTEGER;
		}
		return outputCharacters;
	}

});