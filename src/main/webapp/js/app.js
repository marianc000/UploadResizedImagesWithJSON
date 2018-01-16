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
// drag and drop

    $(function () {
        var selectedFiles = [];
        var $filesInput = $('input[type=file]');
        var $firstNameInput = $('input[name="firstName"]');
        var $lastNameInput = $('input[name="lastName"]');
        //  var $imageTable = $('table.images tbody');
        var $previewDiv = $('div.preview');
        var $noFilesMessage = $previewDiv.find('p.emptyMessage');
        var $someFilesMessage = $previewDiv.find('p.nonEmptyMessage');
        var $previewList = $previewDiv.children('ul');
        var $loadedList = $('div.loadedImages ul');
        var $dropBox = $('#dropbox');

        $filesInput.change(function () {
            clear();
            showThumbs($filesInput[0].files);
        });

        function clear() {
            selectedFiles = [];
            $previewList.empty();
            $loadedList.empty();
            showMessage();
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
            $previewList.append('<li><p>' + file.myName + '</p><img src="' + URL.createObjectURL(file) + '"  onload="window.URL.revokeObjectURL( this.src);"/></li>');
        }

        function showThumbs(files) {
            for (var i = 0; i < files.length; i++) {
                var photo = files[i];

                if (photo.type.startsWith("image/")) {
                    resizePhoto(photo);
                }
            }
        }
        function resizePhoto(file) {
            resize(file, 1200, function (resizedFile) {
                console.log('resized original filename=' + file.name + '; resided file name=' + resizedFile.name + '; initial size=' + file.size + '; resized size=' + resizedFile.size);
                resizedFile.myName = file.name; // name is erased in the resized file
                showThumb(resizedFile);
            });
        }

        $('button').click(function (event) {
            var firstName = $firstNameInput.val();
            var lastName = $lastNameInput.val();
            var inputData = {firstName: firstName, lastName: lastName};

            fileUtils.upload(uploadUrl, inputData, selectedFiles, function (data) {
                data.photos.forEach(displayLinkToUploadedImage);
            });

        });

        function displayLinkToUploadedImage(url) {
            $loadedList.append('<li><p>' + url + '</p><a href="' + url + '"><img src="' + url + '"/></a></li>');
        }

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
            showThumbs(e.originalEvent.dataTransfer.files);
        }
    });





}
);




