$(function () {
  initToolTips();
});

function onPostAjaxCall(data){
	if (data.status === 'success') { 
        initToolTips();
    }
}
function initToolTips(){
	$('[data-toggle="tooltip"]').tooltip();
}

function selectListItem(e) {
	$('.sidebar-list-group-item').removeClass("active");
	$(e).addClass("active");
}