$(function(){
	iboxFullHeight();
	$(window).resize(function(){
		iboxFullHeight();
	});
	iboxCollapseLeft();
	$("[data-toggle=popover]").popover({container: "body"});
	$("[data-toggle=tooltip]").tooltip({container: "body"});
});
//ibox内jqgrid全屏
function iboxFullHeight(){
	$(".ibox.full-height").each(function(i, n){
		var iboxheight = $(this).height();
		var iboxdivheight = iboxheight - 54 - $(this).children(".ibox-content").find(".ibox-form").height();
		var iboxdivtoolsheight = 0;
		var iboxdivtools = $(this).children(".ibox-content").find(".ibox-grid-tools");
		if(iboxdivtools.length == 1){
			iboxdivtoolsheight = iboxdivtools.height() + 10;
			var headerLength = $(this).children(".ibox-content").find(".ibox-form .header").length;
			iboxdivtoolsheight += headerLength * 5;
		}
		$(this).children(".ibox-content").height(iboxheight - 54);
		$(this).children(".ibox-content").find(".ibox-grid").height(iboxdivheight - iboxdivtoolsheight - 64);
		$(this).children(".ibox-content").find(".ibox-div").height(iboxdivheight);
	});
	$(".tabs-container.full-height-with-tab").each(function(i, n){
		var fullheight = $(this).closest(".full-height").height();
		var iboxheight = $(this).prev(".ibox.full-height-with-tab").height();
		var tabsheight = fullheight - iboxheight - 68;
		var tabswidth = $(this).find(".tab-pane.active>.panel-body").width();
		$(this).find(".tab-pane>.panel-body").height(tabsheight);
		$(this).find(".ibox-grid").height(tabsheight - 64);
		$(this).find(".ibox-grid").width(tabswidth);
	});
}
//ibox左右结构时关闭左侧ibox
function iboxCollapseLeft(){
	$(".ibox-center .ibox-tools-collapse a[leftcol]").click(function(){
		var leftcol = Number($(this).attr("leftcol")||0);
		var centercol = 12 - leftcol;
		var leftsm = "col-sm-" + leftcol;
		var leftmd = "col-md-" + leftcol;
		var centersm = "col-sm-" + centercol;
		var centermd = "col-md-" + centercol;
		if($(".ibox-left").is(':visible')){
			$(".ibox-left").removeClass(leftsm).removeClass(leftmd).hide();
			$(".ibox-center").removeClass(centersm).removeClass(centermd).addClass("col-sm-12").addClass("col-md-12");
			$(this).find(".fa").removeClass("fa-chevron-left").addClass("fa-chevron-right");
		}else{
			$(".ibox-left").addClass(leftsm).addClass(leftmd).show();
			$(".ibox-center").removeClass("col-sm-12").removeClass("col-md-12").addClass(centersm).addClass(centermd);
			$(this).find(".fa").removeClass("fa-chevron-right").addClass("fa-chevron-left");
		}
		$(window).resize();
	});
}
//layer默认参数
layer.config({
	extend:'moon/style.css',
	skin:'layer-ext-moon',
	zIndex: 1000
});
//jquery.validate默认参数
$.validator.setDefaults({
	onfocusout: false,
	onkeyup: false,
	onclick: false,
	focusInvalid: false,
	focusCleanup: false,
	ignore: 'input[type=hidden]',
    showErrors: function(errorMap, errorList) { 
    	$('.input-group').removeClass('has-error');
        if(errorList.length != 0){
            var msg = "";  
            $.each(errorList, function(i,v){ 
            	if(msg != ""){
            		msg += "<br>";
            	}
            	msg += (v.message); 
            	$(v.element).closest('.input-group').addClass('has-error');
            });
            if(msg != ""){
            	// msg太长，默认不显示
    			layer.alert("请检查红色字段！", {
    				shadeClose: true,
    				title: "校验不通过",
    				icon: 2
    			});
            }
        }
    }
});
//ajax默认参数
$.ajaxSetup({
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		if(XMLHttpRequest.status == "401"){
			layer.alert("会话超时，请重新登录！", {
				shadeClose: true,
				title: "出错",
				icon: 2
			});
		}else if(XMLHttpRequest.status == "200"){
			var subStr = XMLHttpRequest.responseText.substring(0,14);
			if(subStr.indexOf("success:false") != -1){
				var msg = XMLHttpRequest.responseText.substring(24,XMLHttpRequest.responseText.length-2);
				layer.alert(msg, {
					shadeClose: true,
					title: "提示",
					icon: 2
				});
			}
        }
	}
});
//日期格式化扩展
Date.prototype.format = function(fmt) { 
    var o = { 
       "M+" : this.getMonth()+1,                 //月份 
       "d+" : this.getDate(),                    //日 
       "h+" : this.getHours(),                   //小时 
       "m+" : this.getMinutes(),                 //分 
       "s+" : this.getSeconds(),                 //秒 
       "q+" : Math.floor((this.getMonth()+3)/3), //季度 
       "S"  : this.getMilliseconds()             //毫秒 
   }; 
   if(/(y+)/.test(fmt)) {
           fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
   }
    for(var k in o) {
       if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
   return fmt; 
}