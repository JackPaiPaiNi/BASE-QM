/**
 *
 * 信息管理部软件开发科Bootstrap插件系列
 * 
 * jquery.sky.main 登录页面和主页面框架
 *
 * 王歌 2016年11月
 */
function sumWidth(objs) {
	var width = 0;
	$(objs).each(function() {
		width += $(this).outerWidth(true)
	});
	return width
}
function setActive(tabLi) {
	var preWidth = sumWidth($(tabLi).prevAll()), nextWidth = sumWidth($(tabLi).nextAll());
	var otherWidth = sumWidth($(".content-tabs").children().not(".mtabs"));
	var thisWidth = $(".content-tabs").outerWidth(true) - otherWidth;
	var left = 0;
	if ($(".page-tabs-content").outerWidth() < thisWidth) {
		left = 0
	} else {
		if (nextWidth <= (thisWidth - $(tabLi).outerWidth(true) - $(tabLi).next().outerWidth(true))) {
			if ((thisWidth - $(tabLi).next().outerWidth(true)) > nextWidth) {
				left = preWidth;
				var _this = tabLi;
				while ((left - $(_this).outerWidth()) > ($(".page-tabs-content").outerWidth() - thisWidth)) {
					left -= $(_this).prev().outerWidth();
					_this = $(_this).prev()
				}
			}
		} else {
			if (preWidth > (thisWidth - $(tabLi).outerWidth(true) - $(tabLi).prev().outerWidth(true))) {
				left = preWidth - $(tabLi).prev().outerWidth(true)
			}
		}
	}
	$(".page-tabs-content").animate({
		marginLeft : 0 - left + "px"
	}, "fast")
}

function generateSlimScroll(e) {
	var t = $(e).attr("data-height");
	t = !t ? $(e).height() : t;
	var n = {
		height : t,
		alwaysVisible : true
	};
	if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
		n.wheelStep = 1;
		n.touchScrollStep = 100
	}
	$(e).slimScroll(n)
}
	
$(function() {
	$(".sidebar li[mlevel]").children("a").click(function(e) {
		if ($(".page-sidebar-minified").length === 0) {
			var thisLi = $(this).closest("li[mlevel]");
			
			if(!thisLi.hasClass("active")){
				var active = thisLi.siblings("li[mlevel].active");
				active.children(".sub-menu").slideUp(250);
				active.removeClass("active");
				active.find("li[mlevel].active").children(".sub-menu").slideUp(250);
				active.find("li[mlevel].active").removeClass("active");
				if(thisLi.hasClass("has-sub")){
					$(this).next(".sub-menu").slideToggle(250, function() {	
						thisLi.addClass("active")
					})
				}else{
					thisLi.addClass("active")
				}
			}else{
				if(thisLi.hasClass("has-sub")){
					$(this).next(".sub-menu").slideToggle(250)
				}
			}
		}
	});
	
	$(".sidebar-minify-btn").click(function(e) {
		e.preventDefault();
		if ($(".page-container").hasClass("page-sidebar-minified")) {
			$(".page-container").removeClass("page-sidebar-minified");
			if ($(".page-container").hasClass("page-sidebar-fixed")) {
				generateSlimScroll($('.sidebar [data-scrollbar="true"]'))
			}
		} else {
			$(".page-container").addClass("page-sidebar-minified");
			if ($(".page-container").hasClass("page-sidebar-fixed")) {
				$('.sidebar [data-scrollbar="true"]').slimScroll({
					destroy : true
				});
				$('.sidebar [data-scrollbar="true"]').removeAttr("style")
			}
			$(".sidebar [data-scrollbar=true]").trigger("mouseover")
		}
		$(window).trigger("resize")
	});
	
	$(".sidebar").on("click", "li a[menuId]", function () {
		var url = $(this).attr("url"), menuId = $(this).attr("menuId"), text = $(this).attr("text"), flag = true;
		if (url == undefined || $.trim(url).length == 0) {
			return false
		}else{
			if(url.indexOf("?")>-1){
				url += "&";
			}else{
				url += "?";
			}
			url += "cdid="+menuId;
		}
		$(".mtab").each(function() {
			if ($(this).attr("menuId") == menuId) {
				if (!$(this).hasClass("active")) {
					$(this).addClass("active").siblings(".mtab").removeClass("active");
					setActive(this);
					$(".mtabContent .mtabIframe").each(function() {
						if ($(this).attr("menuId") == menuId) {
							$(this).show().siblings(".mtabIframe").hide();
							return false
						}
					})
				}
				flag = false;
				return false
			}
		});
		if (flag) {
			var tabA = '<a href="javascript:;" class="active mtab" menuId="' + menuId + '">' + text + ' <i class="fa fa-times-circle"></i></a>';
			$(".mtab").removeClass("active");
			var tabIframe = '<iframe class="mtabIframe" width="100%" height="' + getIframeHeight() + '" src="' + url + '" frameborder="0" menuId="' + menuId + '"></iframe>';
			$(".mtabContent").find("iframe.mtabIframe").hide().parents(".mtabContent").append(tabIframe);
			$(".mtabs .page-tabs-content").append(tabA);
			setActive($(".mtab.active"))
		}
		return false
	});
	
	$(".mtabs").on("click", ".mtab i", function() {
		var tabId = $(this).parents(".mtab").attr("menuId");
		var tabWidth = $(this).parents(".mtab").width();
		if ($(this).parents(".mtab").hasClass("active")) {
			if ($(this).parents(".mtab").next(".mtab").size()) {
				var nextId = $(this).parents(".mtab").next(".mtab:eq(0)").attr("menuId");
				$(this).parents(".mtab").next(".mtab:eq(0)").addClass("active");
				$(".mtabContent .mtabIframe").each(function() {
					if ($(this).attr("menuId") == nextId) {
						$(this).show().siblings(".mtabIframe").hide();
						return false
					}
				});
				var marginLeft = parseInt($(".page-tabs-content").css("margin-left"));
				if (marginLeft < 0) {
					$(".page-tabs-content").animate({
						marginLeft : (marginLeft + tabWidth) + "px"
					}, "fast")
				}
				$(this).parents(".mtab").remove();
				$(".mtabContent .mtabIframe").each(function() {
					if ($(this).attr("menuId") == tabId) {
						$(this).remove();
						return false
					}
				})
			}
			if ($(this).parents(".mtab").prev(".mtab").size()) {
				var nextId = $(this).parents(".mtab").prev(".mtab:last").attr("menuId");
				$(this).parents(".mtab").prev(".mtab:last").addClass("active");
				$(".mtabContent .mtabIframe").each(function() {
					if ($(this).attr("menuId") == nextId) {
						$(this).show().siblings(".mtabIframe").hide();
						return false
					}
				});
				$(this).parents(".mtab").remove();
				$(".mtabContent .mtabIframe").each(function() {
					if ($(this).attr("menuId") == tabId) {
						$(this).remove();
						return false
					}
				})
			}
		} else {
			$(this).parents(".mtab").remove();
			$(".mtabContent .mtabIframe").each(function() {
				if ($(this).attr("menuId") == tabId) {
					$(this).remove();
					return false
				}
			});
		}
		setActive($(".mtab.active"));
		return false
	});
	$(".mtabs").on("click", ".mtab", function() {
		if (!$(this).hasClass("active")) {
			var tabId = $(this).attr("menuId");
			$(".mtabContent .mtabIframe").each(function() {
				if ($(this).attr("menuId") == tabId) {
					$(this).show().siblings(".mtabIframe").hide();
					return false
				}
			});
			$(this).addClass("active").siblings(".mtab").removeClass("active");
			setActive(this);

			var thisA = $(".sidebar a[menuId=" + tabId + "]");
			var mlevel = thisA.closest("li[mlevel]").attr("mlevel");
			for(var i=1; i < parseInt(mlevel) + 1; i++){
				thisA.parents("[mlevel=" + i + "]").not(".active").children("a").trigger("click");
			}
		}
	});
	$(".mtabLeft").on("click", function() {
		var marginLeft = Math.abs(parseInt($(".page-tabs-content").css("margin-left")));
		var otherWidth = sumWidth($(".content-tabs").children().not(".mtabs"));
		var thisWidth = $(".content-tabs").outerWidth(true) - otherWidth;
		var left = 0;
		if ($(".page-tabs-content").width() < thisWidth) {
			return false
		} else {
			var firstTab = $(".mtab:first");
			var tmp = 0;
			while ((tmp + $(firstTab).outerWidth(true)) <= marginLeft) {
				tmp += $(firstTab).outerWidth(true);
				firstTab = $(firstTab).next()
			}
			tmp = 0;
			if (sumWidth($(firstTab).prevAll()) > thisWidth) {
				while ((tmp + $(firstTab).outerWidth(true)) < (thisWidth) && firstTab.length > 0) {
					tmp += $(firstTab).outerWidth(true);
					firstTab = $(firstTab).prev()
				}
				left = sumWidth($(firstTab).prevAll())
			}
		}
		$(".page-tabs-content").animate({
			marginLeft : 0 - left + "px"
		}, "fast")
	});
	$(".mtabRight").on("click", function() {
		var marginLeft = Math.abs(parseInt($(".page-tabs-content").css("margin-left")));
		var otherWidth = sumWidth($(".content-tabs").children().not(".mtabs"));
		var thisWidth = $(".content-tabs").outerWidth(true) - otherWidth;
		var left = 0;
		if ($(".page-tabs-content").width() < thisWidth) {
			return false
		} else {
			var firstTab = $(".mtab:first");
			var tmp = 0;
			while ((tmp + $(firstTab).outerWidth(true)) <= marginLeft) {
				tmp += $(firstTab).outerWidth(true);
				firstTab = $(firstTab).next()
			}
			tmp = 0;
			while ((tmp + $(firstTab).outerWidth(true)) < (thisWidth) && firstTab.length > 0) {
				tmp += $(firstTab).outerWidth(true);
				firstTab = $(firstTab).next()
			}
			left = sumWidth($(firstTab).prevAll());
			if (left > 0) {
				$(".page-tabs-content").animate({
					marginLeft : 0 - left + "px"
				}, "fast")
			}
		}
	});
	$(".mtabCloseAll").on("click", function() {
		$(".page-tabs-content").children("[menuId]").not(":first").each(function() {
			$('.mtabIframe[menuId="' + $(this).attr("menuId") + '"]').remove();
			$(this).remove()
		});
		$(".page-tabs-content").children("[menuId]:first").each(function() {
			$('.mtabIframe[menuId="' + $(this).attr("menuId") + '"]').show();
			$(this).addClass("active")
		});
		$(".page-tabs-content").css("margin-left", "0")
	});
	$(".mtabCloseOther").on("click", function() {
		$(".page-tabs-content").children("[menuId]").not(":first").not(".active").each(function() {
			$('.mtabIframe[menuId="' + $(this).attr("menuId") + '"]').remove();
			$(this).remove()
		});
		$(".page-tabs-content").css("margin-left", "0")
	});

	$("[data-scrollbar=true]").each(function() {
		generateSlimScroll($(this))
	});

	$(document).scroll(function() {
		var e = $(document).scrollTop();
		if (e >= 200) {
			$("[data-click=scroll-top]").addClass("in")
		} else {
			$("[data-click=scroll-top]").removeClass("in")
		}
	});
	$("[data-click=scroll-top]").click(function(e) {
		e.preventDefault();
		$("html, body").animate({
			scrollTop : $("body").offset().top
		}, 500)
	})
});
