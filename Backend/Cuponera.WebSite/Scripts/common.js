function deleteElement(args) {
    if (!args.id || !args.controller) {
        return false;
    }

    $.ajax({
        url: '/' + args.controller + '/Delete/' + args.id,
        type: "POST",
        data: { '__RequestVerificationToken': getAntiForgeryToken() }
    })
        .success(function () {
            location.href = args.controller;
        });
}

function getAntiForgeryToken() {
    return $('#__AjaxAntiForgeryForm input[name=__RequestVerificationToken]').val();
};