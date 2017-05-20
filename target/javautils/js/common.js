$(function () {
	//导航
	$(".nav .nav-item").mouseenter(function () {
		$(this).find('ul').stop().slideDown();
	});
	$(".nav .nav-item").mouseleave(function () {
		$(this).find('ul').stop().slideUp();
	});
	$(".nav .nav-item .inner").mouseenter(function () {
		$(this).css('display','block');
	});
	$(".nav .nav-item .inner").mouseleave(function () {
		$(this).stop().slideUp();
	});
	$(".nav-item").mouseenter(function () {
		$(this).css('background','#cb2934').siblings('.nav-item').css('background','transparent');
	})
	
})
