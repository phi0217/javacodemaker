$(function() {
	//-----------------------------模态框宽度及定位--------------------------------
	var modalWidth = $(window).width();
	$(".modal-body").width(parseInt(modalWidth * 0.6));

	function resize() {
		var width = $(window).width();
		var left = parseInt((width - modalWidth * 0.6) / 2);
		var top = parseInt(($(window).height() - $(".modal-body").height()) / 6);
		if(top <= 30) {
			top = 30;
		}
		$(".modal").css({
			left: left,
			top: top
		});
	}
	resize();
	$(window).resize(resize);
	//----------------------------模态框功能实现---------------------------------
	var modal;
	$('a[data-toggle]').click(showModal); //点击按钮，根据按钮的data-id让相应id的模态框显示出来
	$('button[data-toggle]').click(showModal);
	$("[data-close]").click(removeMask); //点击提交、关闭、取消等按钮时模态框消失，行内需要绑定data-close="modal"属性
	function showModal() {
		var id = $(this).attr('data-id');
		modal = $("#" + id + "");
		var mask = $('<div class="mask"></div>').appendTo($("body"));
		$(".mask").click(function() {
			removeMask();
		});
		modal.fadeIn();
		$('.mask').fadeIn();
	}

	function removeMask() {
		$(".mask").fadeOut(function() { //先隐藏在清除dom节点
			$(this).remove();
		});
		modal.fadeOut();
	}
	
	
	$(".revise").click(function () {
				var tr = $(this).parents('tr');
				var len = tr.find('td').length;
				for(var i = 0 ;i<len ; i++){
					$(".revise-modal .panel-body p").eq(i).find('input').val(tr.find('td').eq(i).html());
				}
			});
	// 		$(".add").click(function () {
	// 				$(".modal .panel-body p input").val("");
	// })
})