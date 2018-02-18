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
requirejs(['jquery', 'app/Model/SomeModel'], function ($, Model) {

    $(function () {
        var selectedFiles = []; // the array with the unique resized files that will be uploaded
        var $firstNameInput = $('input[name="firstName"]');
        var $lastNameInput = $('input[name="lastName"]');
        var $previewDiv = $('div.preview');
        var $noFilesMessage = $previewDiv.find('p.emptyMessage');
        var $someFilesMessage = $previewDiv.find('p.nonEmptyMessage');
        var $previewList = $previewDiv.children('ul');
        var $loadedList = $('div.loadedImages ul');
        var $loadedOwner = $('div.loadedImages p');
        
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
            model.resizeAndShowThumbs(files);
        }

        // submit
        $('button').click(function (event) {

            model.setFirstName($firstNameInput.val());
            model.setLastName($lastNameInput.val());
            model.saveMultipart();
        });
        var model = new Model();
        model.on('sync', onFetch);
        model.on('fileAdded', showThumb);

        function onFetch(model) {
            console.log('onFetch: ' + JSON.stringify(model));
            $loadedOwner.text("Images loaded by " + model.getFirstName() + " " + model.getLastName());
            model.getUrls().forEach(displayLinkToUploadedImage);
        }

        function displayLinkToUploadedImage(photo) {
            $loadedList.append('<li><p>' + photo.fullSizeUrl + '</p><a href="' + photo.fullSizeUrl + '"><img src="' + photo.thumbUrl + '"/></a></li>');
        }
    });
});
