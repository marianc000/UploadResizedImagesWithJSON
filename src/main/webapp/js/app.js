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
requirejs(['jquery', 'app/resize', 'app/fileUtils'], function ($, resize, fileUtils) {

    var uploadUrl = 'api/upload';

    $(function () {
        var selectedFiles = [];
        var $filesInput = $('input[type=file]');
        var $firstNameInput = $('input[name="firstName"]');
        var $lastNameInput = $('input[name="lastName"]');
        var $previewDiv = $('div.preview');
        var $noFilesMessage = $previewDiv.find('p.emptyMessage');
        var $someFilesMessage = $previewDiv.find('p.nonEmptyMessage');
        var $previewList = $previewDiv.children('ul');
        var $loadedList = $('div.loadedImages ul');
        var $dropBox = $('#dropbox');

        $filesInput.change(function () {
            // clear();
            resizeAndShowThumbs($filesInput[0].files);
        });


// dragging
        $dropBox.on("dragenter", onDragEnter).on("dragover", onDragOver).on("drop", onDrop);

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
// common to display photos before submit

//        function clear() {
//            selectedFiles = [];
//            $previewList.empty();
//            $loadedList.empty();
//            showMessage();
//        }

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
                    + '"  onload="window.URL.revokeObjectURL(this.src);console.log(this.width+\'; \'+this.height+\'; \'+this.naturalWidth+\'; \'+this.naturalHeight);"/></li>');
        }

        function resizeAndShowThumbs(files) {
            for (var c = 0; c < files.length; c++) {
                var file = files[c];
                if (file.type.startsWith("image/") && isFileNotYetIncluded(file)) {
                    console.log('resizing ' + file.name);
                    resize(file, showThumb);
                }
            }
        }

        function isFileNotYetIncluded(file) {
            console.log('checking ' + file.name);
            for (var c = 0; c < selectedFiles.length; c++) {
                if (selectedFiles[c].originalNameSize.equals(file)) { // file has name and size read-only properties
                    console.log('file ' + file.name + " is already included");
                    return false;
                }
            }
            console.log('adding ' + file.name);
            return true;
        }

        // submit
        $('button').click(function (event) {
            var firstName = $firstNameInput.val();
            var lastName = $lastNameInput.val();
            var inputData = {firstName: firstName, lastName: lastName};

            fileUtils.upload(uploadUrl, inputData, selectedFiles, function (data) {
                data.photos.forEach(displayLinkToUploadedImage);
            });
        });

        function displayLinkToUploadedImage(photo) {
            $loadedList.append('<li><p>' + photo.fullSizeUrl + '</p><a href="' + photo.fullSizeUrl + '"><img src="' + photo.thumbUrl + '"/></a></li>');
        }
    });
});
