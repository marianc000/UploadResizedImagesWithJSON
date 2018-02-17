'use strict';

requirejs.config({
    //By default load any module IDs from js/lib
    baseUrl: 'js/lib',
    //except, if the module ID starts with "app", load it from the js/app directory. 
    //paths config is relative to the baseUrl, and never includes a ".js" extension since the paths config could be for a directory.
    paths: {
        app: '../app'
    }
});

// Start the main app logic.
requirejs(['jquery', 'app/resize', 'app/fileUtils'], function ($, resize, upload) {

    var uploadUrl = 'api/upload';

    $(function () {
        var selectedFiles = []; // the array with the unique resized files that will be uploaded
        var $firstNameInput = $('input[name="firstName"]');
        var $lastNameInput = $('input[name="lastName"]');
        var $previewDiv = $('div.preview');
        var $noFilesMessage = $previewDiv.find('p.emptyMessage');
        var $someFilesMessage = $previewDiv.find('p.nonEmptyMessage');
        var $previewList = $previewDiv.children('ul');
        var $loadedList = $('div.loadedImages ul');

        $('input[type=file]').change(function () {
            resizeAndShowThumbs(this.files);
        });


// dragging
        $('#dropbox').on("dragenter", onDragEnter).on("dragover", onDragOver).on("drop", onDrop);

        function onDragEnter(e) {
            e.stopPropagation();
            e.preventDefault();
        }

        function onDragOver(e) {
            e.stopPropagation();
            e.preventDefault();
        }

        function onDrop(e) {
            e.stopPropagation();
            e.preventDefault();
            resizeAndShowThumbs(e.originalEvent.dataTransfer.files);
        }

        function showMessage() {
            if (selectedFiles.length === 0) {
                $noFilesMessage.show();
                $someFilesMessage.hide();
            } else {
                $noFilesMessage.hide();
                $someFilesMessage.show();
            }
        }
        showMessage();

        function showThumb(file) {
            selectedFiles.push(file);
            showMessage();
            $previewList.append('<li><p>' + file.originalNameSize.name + '</p><img src="' + URL.createObjectURL(file)
                    + '"  onload="window.URL.revokeObjectURL(this.src);"/></li>');
        }

        function resizeAndShowThumbs(files) {
            for (var c = 0; c < files.length; c++) {
                var file = files[c];
                if (file.type.startsWith("image/") && isFileNotYetIncluded(file)) {
                    resize(file, showThumb);
                }
            }
        }

        function isFileNotYetIncluded(file) {
            for (var c = 0; c < selectedFiles.length; c++) {
                if (selectedFiles[c].originalNameSize.equals(file)) { // file has name and size read-only properties
                    return false;
                }
            }
            return true;
        }

        // submit
        $('button').click(function (event) {
            var firstName = $firstNameInput.val();
            var lastName = $lastNameInput.val();
            var inputData = {firstName: firstName, lastName: lastName};

            upload(uploadUrl, inputData, selectedFiles, function (data) {
                data.photos.forEach(displayLinkToUploadedImage);
            });
        });

        function displayLinkToUploadedImage(photo) {
            $loadedList.append('<li><p>' + photo.fullSizeUrl + '</p><a href="' + photo.fullSizeUrl + '"><img src="' + photo.thumbUrl + '"/></a></li>');
        }
    });
});
