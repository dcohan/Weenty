function AddWarningOnInactiveObjects(idObject, typeObject) {

    document.getElementById("inactive").style.display = "none";

    $("#" + idObject).change(function () {
        if ($("#"+idObject).val() > 0) {
            $.get('/Validator/ValidateStore?IdObject=' + $("#" + idObject).val()+"&TypeObject="+typeObject, null, function (data, textStatus, jqXHR) {
                if (data.toLowerCase().trim() == 'true') {
                    document.getElementById("inactive").style.display = "none";
                } else {
                    document.getElementById("inactive").style.display = "inline";
                }
            });
        }
    });

    $("#" + idObject).trigger('change');
}