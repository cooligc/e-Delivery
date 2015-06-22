$(document).ready(function(){
	var persentage = $('#completed-status').text();
	$("#persentage").text(persentage);
	$('.progress-bar').attr("style","width:"+persentage+"%");

	var productName=$("#pName").text();
	$("#productName").val(productName);
	

	var totallength = 255;

	$('#comment').on('keydown, keyup', function(e) {
		 var totalCount = $("#comment").val().length;
		 if((totallength - totalCount)<=0){

		 }
	     $('span#character-count').text((totallength - totalCount));
	});
});