'use strict';
var uploadUrl = 'upload';
// drag and drop

$(function () {
    var selectedFiles = [];
    var $filesInput = $('form input[type=file]');

    var $previewDiv = $('div.preview');
    var $noLoadedFilesMessage = $previewDiv.children('p');
    var $previewList = $previewDiv.children('ul');
    var $dropBox = $('#dropbox');

    // Upload photo
    var upload = function (photo, fileName, callback) {
        var formData = new FormData();
        formData.append('photo', photo, fileName);
        $.ajax({
            url: uploadUrl,
            data: formData,
            processData: false,
            type: 'POST',
            contentType: false
        })
                .done(function (data, textStatus, jqXHR) {
                    console.log("done textStatus=" + textStatus);
                    var location = jqXHR.getResponseHeader('Location');
                    console.log("done textStatus=" + textStatus + "; location=" + location);
                    callback(location);
                })
                .fail(function (jqXHR, textStatus, errorThrown) {
                    console.log("error textStatus=" + textStatus + "; errorThrown=" + errorThrown);
                });
    };

    var fileSize = function (size) {
        var i = Math.floor(Math.log(size) / Math.log(1024));
        return (size / Math.pow(1024, i)).toFixed(2) * 1 + ' ' + ['B', 'kB', 'MB', 'GB', 'TB'][i];
    };
    console.log('setting the listeners');



    $filesInput.change(function (event) {
        console.log("on change");
        clear();
        showThumbs($filesInput[0].files);
    });

    function clear() {
        selectedFiles = [];
        $previewList.empty();
        showMessage();
    }

    function showMessage() {
        if (selectedFiles.length === 0) {
            $noLoadedFilesMessage.show();
        } else {
            $noLoadedFilesMessage.hide();
        }
    }

    function showThumb(file) {
        selectedFiles.push(file);
        showMessage();
        $previewList.append('<li><p>' + file.name + '</p><img src="' + URL.createObjectURL(file) + '"  onload="window.URL.revokeObjectURL( this.src);console.log(\'releaseURL2\');"/></li>');
        //URL.revokeObjectURL( src);
    }

    function showThumbs(files) {
        for (var i = 0; i < files.length; i++) {
            var photo = files[i];
            console.log("=" + photo.name + "; " + photo.type);
            if (photo.type.startsWith("image/")) {
                showThumb(photo);
            }
        }
    }


    $('input[type=submit]').click(function (event) {
        console.log('submit');
        event.preventDefault();

        var files = selectedFiles;

        for (var i = 0; i < files.length; i++) {
            (function () {
                var fileName = files[i].name;
                console.log('filename=' + fileName);
                var initialSize = files[i].size;

                resize(files[i], 1200, function (resizedFile) {
                    console.log('resized=' + resizedFile.size);
                    var resizedSize = resizedFile.size;
                    
// upload all toghether
                    upload(resizedFile, fileName, function (createdUrl) {

                        var rowElement = document.createElement('tr');
                        rowElement.innerHTML = '<td>' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds() + '</td><td>'
                                + fileSize(initialSize) + '</td><td>' + fileSize(resizedSize) + '</td><td>'
                                + Math.round((initialSize - resizedSize) / initialSize * 100) + '%</td><td><a href="' + createdUrl + '">view image</a></td>';
                        document.querySelector('table.images tbody').appendChild(rowElement);
                    });
                });
            }());

        }

    });
    $dropBox.on("dragenter", onDragEnter).on("dragover", onDragOver).on("drop", onDrop);

    function onDragEnter(e) {
        //    console.log('onDragEnter');
        e.stopPropagation();
        e.preventDefault();
    }

    function onDragOver(e) {
        //   console.log('onDragOver');
        e.stopPropagation();
        e.preventDefault();
    }
    function onDrop(e) {
        console.log('onDrop');
        e.stopPropagation();
        e.preventDefault();

        showThumbs(e.originalEvent.dataTransfer.files);

    }
});
