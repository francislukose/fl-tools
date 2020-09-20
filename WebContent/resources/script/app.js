$(function () {
  initToolTips();
  initClassDiagramModal();
});

function initClassDiagramModal(){
	startSpinner('class-diagram-spinner');
	$('#exampleModalCenter').on('show.bs.modal', function () {
	  startSpinner('class-diagram-spinner');
	  callPlantUML();
	});
}

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

function searchInList(e,l) {
	var filter = $(e).val().toUpperCase();
	$('#' + l).children().each(function(index){
		var textValue =  $(this).text();
		if (textValue.toUpperCase().indexOf(filter) > -1) {
	      	$(this).css("display", "");
	    } else {
	      	$(this).css("display", "none");
	    }
	});
}

function openClassDiagramInNewWindow( e ){
	window.open($('#' + e).attr("src"), 'popupWindowName', 'dependent=yes, menubar=no, toolbar=no, location=no'); 
	return false;
}

$('#exampleModalCenter').on('show.bs.modal', function () {
  startSpinner('class-diagram-spinner');
  callPlantUML();
})

function startSpinner(e){
	$('#' + e).show();
}
function stopSpinner(e){
	$('#' + e).hide();
}
