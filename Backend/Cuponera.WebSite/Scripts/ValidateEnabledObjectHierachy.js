function AddWarningOnInactiveObjects(idObject, typeObject, divid) {

    $('#'+divid).addClass('hidden');

    //If is a combo
    $("#" + idObject).change(function() {
        triggerValidation(idObject, typeObject, divid);
    });

    triggerValidation(idObject, typeObject, divid);
}

function triggerValidation(idObject, typeObject, divid) {
    var id;

    if($("#"+idObject).val() > 0) {
        id = $("#"+idObject).val();
    } else {
        if($("#"+idObject).text().trim().length > 0) {
            id = $("#"+idObject).text();
        }
    }

    if (id) {
        $.get('/Validator/ValidateEntity?IdObject=' + id + "&TypeObject=" + typeObject, null, function (data, textStatus, jqXHR) {
            data = $.parseJSON(data);

            $('#' + divid).addClass('hidden');
            if (data.length) {
                $('#inactiveElements').html(translate(data).join(', '));
                $('#'+divid).removeClass('hidden');
            }
        });
    }
}


function translate(entities) {
    var translatedEntities = [];
    $(entities).each(function (k, elem) {
        switch (elem) {
            case 'company':
                translatedEntities.push('la empresa');
                break;
            case 'store':
                translatedEntities.push('la sucursal');
                break;
            case 'product':
                translatedEntities.push('el producto');
                break;
            default:
                translatedEntities.push('');
        }
    });

    return translatedEntities;
}