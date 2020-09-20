$(function() {
    initToolTips();
    initClassDiagramModal();
});

function initClassDiagramModal() {
    $('#exampleModalCenter').on('show.bs.modal', function() {
        PlantUML.sendToPlantUML('basic-img-class-diagram');
    });
    
    $('#inheritanceModalCenter').on('show.bs.modal', function() {
        PlantUML.sendToPlantUML('inheritance-img-class-diagram');
    });
}

function onPostAjaxCall(data) {
    if (data.status === 'success') {
        initToolTips();
    }
}
function initToolTips() {
    $('[data-toggle="tooltip"]').tooltip();
}

function selectListItem(e) {
    $('.sidebar-list-group-item').removeClass("active");
    $(e).addClass("active");
}

function searchInList(e, l) {
    var filter = $(e).val().toUpperCase();
    $('#' + l).children().each(function(index) {
        var textValue = $(this).text();
        if (textValue.toUpperCase().indexOf(filter) > -1) {
            $(this).css("display", "");
        } else {
            $(this).css("display", "none");
        }
    });
}

function openClassDiagramInNewWindow(e) {
    window.open($('#' + e).attr("src"), 'popupWindowName', 'dependent=yes, menubar=no, toolbar=no, location=no');
    return false;
}


