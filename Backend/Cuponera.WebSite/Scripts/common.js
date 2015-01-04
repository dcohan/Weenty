function confirmDelete(id) {
    deleteElement({ controller: globals.controller, id: id });
}

function confirmActivate(id) {
    activateElement({ controller: globals.controller, id: id });
}

function redirect(url) {
    document.location.href = url;
}

function getAntiForgeryToken() {
    return $('#__AjaxAntiForgeryForm input[name=__RequestVerificationToken]').val();
};

function callServer(args) {
    if (args.includeAntiForgeryToken) {
        if (!args.data) { args.data = {}; }

        args.data['__RequestVerificationToken'] = getAntiForgeryToken();
    }
    var qs = args.qs ? '?' + buildQS(args.qs) : '';
    var id = args.id ? '/' + args.id : '';

    $.blockUI({ message: '<img class="loading-image" src="Images/loading.gif" style="width: 50px; height: 50px;"/> Por favor espere mientras se recargan los datos...' });

    $.ajax({
        url: '/' + args.controller + '/' + args.action + id + qs,
        type: args.method,
        data: args.data
    })
   .success(function (data, textStatus, jqXHR) {
       if (args.success) {
           if (data) {
               try {
                   data = JSON.parse(data);
                   args.success(data, textStatus, jqXHR);
               } catch (err) {
                   alert('No tiene permisos para borrar el elemento.')
                   
               }
           } else {
               args.success(data, textStatus, jqXHR);
           }
           
       }

       $.unblockUI();
   })
    .complete(function () {
        $.unblockUI();
    });
}

function buildQS(qs) {
    var qsOut = '';
    for (var key in qs) {
        qsOut += key + '=' + qs[key] + '&';
    }

    if (qsOut.length > 0) {
        qsOut = qsOut.substring(0, qsOut.length - 1);
    }
    return qsOut;
}


function getElements(args) {
    if (!args.controller) {
        return false;
    }

    args.method = 'GET';
    args.success = function (response) {
        globals.data = response;
        refreshTable();
    };
    callServer(args);
}

function deleteElement(args) {
    if (!args.id || !args.controller) {
        return false;
    }

    args.action = 'Delete';
    args.includeAntiForgeryToken = true;
    args.method = 'DELETE';
    args.success = function () { if (reload && typeof reload === 'function') { reload(); } };
    callServer(args);
}

function activateElement(args) {
    if (!args.id || !args.controller) {
        return false;
    }

    args.action = 'Activate';
    args.includeAntiForgeryToken = true;
    args.method = 'POST';
    args.success = function () { if (reload && typeof reload === 'function') { reload(); } };
    callServer(args);
}