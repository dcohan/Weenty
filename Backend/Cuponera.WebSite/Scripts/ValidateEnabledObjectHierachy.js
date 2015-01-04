function AddWarningOnInactiveObjects(idObject, typeObject, divid) {

    $('#'+divid).addClass('hidden');

    //If is a combo
    $("#" + idObject).change(function() {
        triggerValidation(idObject, typeObject, divid);
    });

    triggerValidation(idObject, typeObject, divid);
}

function triggerValidation(idObject, typeObject, divid) {

    if($("#"+idObject).val() > 0) {
        id = $("#"+idObject).val();
    } else {
        if($("#"+idObject).text().trim().length > 0) {
            id = $("#"+idObject).text();
        }
    }

    if (id) {
        $.get('/Validator/ValidateStore?IdObject=' +id + "&TypeObject=" +typeObject, null, function (data, textStatus, jqXHR) {
            if (data.toLowerCase().trim() == 'true') {
                $('#'+divid).addClass('hidden');
            } else {
                $('#'+divid).removeClass('hidden');
            }
        });
    }
}