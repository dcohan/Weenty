var nowTemp = new Date();
var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
var files;
var storedFiles = [];
var upc = 0;

$(function () {

    var $file_uploader = $("input[id^='fileToUpload']");
    $file_uploader.prop('title', '');

    $file_uploader.change(function (e) {
        doReCreate(e);
    });

});


function doReCreate(e) {
    upc = upc + 1;
    handleFileSelect(e);

    if (one_image) { return; }

    $("input[id^='fileToUpload']").hide();

    $('<input>').attr({
        type: 'file',
        multiple: 'multiple',
        id: 'fileToUpload' + upc,
        class: 'fUpload',
        name: 'fileUpload',
        style: 'float: left',
        title: '  ',
        onchange: "doReCreate(event)"

    }).appendTo('#uploaders');
}


function handleFileSelect(e) {
    $selDiv = $("#selectedFiles");

    if (!e.target.files) return;

    files = e.target.files;

    if (one_image) {
        $selDiv.empty();
        storedFiles = [];
    }

    for (var i = 0; i < files.length; i++) {
        var f = files[i];
        $selDiv.innerHTML += "<div>" + f.name + "<a onclick='removeAtt(this)'> X </a></div>";
        storedFiles.push(f.name);
    }
}

function removeAtt(t) {
    var serEle = $(t).parent().text().slice(0, -3);
    var index = storedFiles.indexOf(serEle);
    if (index !== -1) {
        storedFiles.splice(index, 1);
    }
    $(t).parent().remove();
}