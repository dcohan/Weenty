function callServer(args) {
    if (args.includeAntiForgeryToken) {
        if (!args.data) { args.data = {}; }

        args.data['__RequestVerificationToken'] = getAntiForgeryToken();
    }

    $.ajax({
        url: '/' + args.controller + '/' + args.action + '/' + args.id,
        type: args.method,
        data: args.data
    })
   .success(function () {
        if(args.success) {
            args.success();
        }
   });
}





function deleteElement(args) {
    if (!args.id || !args.controller) {
        return false;
    }

    args.action = 'Delete';
    args.includeAntiForgeryToken = true;
    args.method = 'DELETE';
    args.success = function () { location.href = args.controller };
    callServer(args);
}

function activateElement(args) {
    if (!args.id || !args.controller) {
        return false;
    }

    args.action = 'Activate';
    args.includeAntiForgeryToken = true;
    args.method = 'POST';
    args.success = function () { location.href = args.controller };
    callServer(args);
}

function getAntiForgeryToken() {
    return $('#__AjaxAntiForgeryForm input[name=__RequestVerificationToken]').val();
};