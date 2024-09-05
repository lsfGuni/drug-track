$(function() {
	/* 사이트 메뉴 마우스 오버 / 아웃 */
	$('.sitemenu2nd-list').mouseover(function() {
		$(this).addClass('hover').find('.sitemenu3rd').show();
	});
	$('.sitemenu2nd-list').mouseout(function() {
		$(this).removeClass('hover').find('.sitemenu3rd').hide();
	});
	$('.sitemenu3rd-list').mouseover(function() {
		if ($(this).find('.sitemenu4th').length>0) {
			$(this).find('.sitemenu4th').show();
		}
	});
	$('.sitemenu3rd-list').mouseout(function() {
		if ($(this).find('.sitemenu4th').length>0) {
			$(this).find('.sitemenu4th').hide();
		}
	});
	/* 북마크 마우스 오버 / 아웃 */
	$('button.bookmark').mouseover(function() {
		$(this).next().show();
	});
	/* 메뉴 검색 결과 오버 / 아웃 */
	$('.helpsearch-box').mouseover(function() {
		$(this).next().show();
	});
	/* 메뉴 톱 레이어 닫기 */
	$('button.layer-close-btn').click(function() {
		$(this).parent('.helpsearch-result-layer, .bookmark-layer').hide();
	});
	
	$('.layerpopwrap').draggable();
	
	$('.close-layerpop').click(function() {
		$(this).parent().hide();
	});
});