$(function() {
	$(".panel-body .title").keyup(function() { //标题
		$(".preview").css('display','block');
		var title = $(this).val();
		$(".preview .news_title").html(title);
	});
	var time = new Date();
	var year = time.getFullYear();
	var month = time.getMonth()+ 1;
	var day = time.getDate();
	var timeStr = year + "-" + month + "-" + day ;

	$(".from").keyup(function() { //文章来源
		$(".preview").css('display','block');
		$(".preview .title span").eq(0).html("文章来源：");
		$(".preview .title .currentTime").html("发布时间 ：");
		$(".preview .title .time").html(timeStr);
		var from = $(this).val();
		$(".preview .from").html(from);

	});
	$(".text").keyup(function() {
		$(".preview").css('display','block');
		var text = $(this).val();
		$(".preview .textPreview").html(text);
		$(".preview .company").html("深圳前海钻盾资产管理有限公司");
		$(".preview .timeLast").html(timeStr);
	})

})