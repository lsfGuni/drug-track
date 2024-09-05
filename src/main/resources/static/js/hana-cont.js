$(function() {
	$('.tbl_List tr').mouseover(function() {
		$(this).addClass('over');
	});
	$('.tbl_List tr').mouseout(function() {
		$(this).removeClass('over');
	});
	$('.btn-closepopup').click(function() {
		window.close();
	});
});