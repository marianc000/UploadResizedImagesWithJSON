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

    var uploadUrl = 'upload';
// drag and drop

    $(function () {
        var selectedFiles = [];
        var $filesInput = $('form input[type=file]');
        var $firstNameInput = $('form input[name="firstName"]');
        var $lastNameInput = $('form input[name="lastName"]');
        var $imageTable = $('table.images tbody');
        var $previewDiv = $('div.preview');
        var $noLoadedFilesMessage = $previewDiv.children('p');
        var $previewList = $previewDiv.children('ul');
        var $dropBox = $('#dropbox');

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
            $previewList.append('<li><p>' + file.myName + '</p><img src="' + URL.createObjectURL(file) + '"  onload="window.URL.revokeObjectURL( this.src);console.log(\'releaseURL2\');"/></li>');
            //URL.revokeObjectURL( src);
        }

        function showThumbs(files) {
            for (var i = 0; i < files.length; i++) {
                var photo = files[i];

                if (photo.type.startsWith("image/")) {
                    //  showThumb(photo);  
                    //  console.log("=" + photo.name + "; " + photo.type);
                    resizePhoto(photo);
                }
            }
        }
        function resizePhoto(file) {
            console.log('resizing filename=' + file.name + '; initial size=' + file.size);
            resize(file, 1200, function (resizedFile) {
                console.log('resized o.filename=' + file.name + '; r.filename=' + resizedFile.name + '; initial size=' + file.size + '; resized=' + resizedFile.size);
                resizedFile.myName = file.name; // name is erased in the resized file
                showThumb(resizedFile);

            });
        }

        $('input[type=submit]').click(function (event) {
            console.log('submit');
            event.preventDefault();
            var firstName = $firstNameInput.val();
            var lastName = $lastNameInput.val();
            console.log("names: "+firstName + " " + lastName);
            var inputData = {firstName: firstName, lastName: lastName};

            fileUtils.upload(uploadUrl, inputData, selectedFiles, function (data) {
                data.photos.forEach(displayLinkToUploadedImage);
            });

        });

        function displayLinkToUploadedImage(url) {
            $imageTable.append('<tr><td>' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds() + '</td><td>'
                    + '</td><td>' + '</td><td>'
                    + '</td><td><a href="' + url + '">view image</a></td></tr>');
        }



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





}
);




