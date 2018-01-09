document.addEventListener('DOMContentLoaded', function (event) {

    'use strict';

    // Initialise resize library
    var resize = new window.resize();
    resize.init();

    // Upload photo
    var upload = function (photo,fileName, callback) {
        var formData = new FormData();
        formData.append('photo', photo,fileName);
        var request = new XMLHttpRequest();
        request.onreadystatechange = function () {
            if (this.readyState === 4) {
                var status = request.status;
                console.log("request.status=" + status);
                if (status === 201) {
                    var location = this.getResponseHeader('Location');
                    console.log("getResponseHeader('Location')=" + location);
                    callback(location);
                }
            }
        }
        request.open('POST', './upload');
        request.responseType = 'json';
        request.send(formData);
    };

    var fileSize = function (size) {
        var i = Math.floor(Math.log(size) / Math.log(1024));
        return (size / Math.pow(1024, i)).toFixed(2) * 1 + ' ' + ['B', 'kB', 'MB', 'GB', 'TB'][i];
    };

    document.querySelector('form input[type=file]').addEventListener('change', function (event) {
        event.preventDefault();

        var files = event.target.files;

        for (var i in files) {

            if (typeof files[i] !== 'object')
                return false;

            (function () {
                var fileName = files[i].name;
                console.log('filename=' + fileName);
                var initialSize = files[i].size;

                resize.photo(files[i], 1200, 'file', function (resizedFile) {

                    var resizedSize = resizedFile.size;

                    upload(resizedFile,fileName, function (createdUrl) {

                        var rowElement = document.createElement('tr');
                        rowElement.innerHTML = '<td>' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds() + '</td><td>'
                                + fileSize(initialSize) + '</td><td>' + fileSize(resizedSize) + '</td><td>'
                                + Math.round((initialSize - resizedSize) / initialSize * 100) + '%</td><td><a href="' + createdUrl + '">view image</a></td>';
                        document.querySelector('table.images tbody').appendChild(rowElement);
                    });

                    // This is not used in the demo, but an example which returns a data URL so yan can show the user a thumbnail before uploading th image.
                    resize.photo(resizedFile, 600, 'dataURL', function (thumbnail) {
                        console.log('Display the thumbnail to the user: ', thumbnail);
                    });

                });

            }());

        }

    });

});
