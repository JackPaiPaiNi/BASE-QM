/**
 *
 * 信息管理部软件开发科Bootstrap插件系列
 * 
 * jquery.sky.main-v2 主页面框架-客户端版
 *
 * 王歌 2016年11月
 */

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
	/*菜单不折叠*/
	$(".sidebar .nav .sub-menu").show();
	$(".sidebar .nav li[mlevel=1]").unbind().eq(0).addClass("active");
	$(".sidebar li[mlevel]").not("[mlevel=1]").children("a").click(function(e) {
		$(".sidebar li[mlevel]").removeClass("active");
		$(this).parents("li[mlevel].has-sub").addClass("active");
	});
	
	$(".sidebar").on("click", "li a[url]", function () {
		var url = $(this).attr("url");
		if (url == undefined || $.trim(url).length == 0) {
			return false
		}else {
			var index = layer.load(1, {
				shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
			$('#myframe').attr("src", url);
			$('#myframe').load(function(){ 
				layer.close(index)
			})
		}
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
