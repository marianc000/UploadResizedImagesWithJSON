define(function () {
    var MAX_SIZE = 1200, MIME = 'image/jpeg', JPEG_QUALITY = 0.95;
    var acceptableTypes = ["image/gif", "image/png", "image/jpeg", "image/bmp"];

    function size(size) {
        var i = Math.floor(Math.log(size) / Math.log(1024));
        return (size / Math.pow(1024, i)).toFixed(2) * 1 + ' ' + ['B', 'kB', 'MB', 'GB', 'TB'][i];
    }

    function resizePhoto(file, callback) {
        var image = new Image();
        image.onload = function ( ) {
            URL.revokeObjectURL(this.src);
            var canvas = document.createElement('canvas');
            console.log("before=" + file.name + "; " + this.width + "; " + this.height + "; " + this.naturalWidth + "; " + this.naturalHeight);
            var width = this.width;
            var height = this.height;

            if (width > height) {
                if (width > MAX_SIZE) {
                    height *= MAX_SIZE / width;
                    width = MAX_SIZE;
                }
            } else {
                if (height > MAX_SIZE) {
                    width *= MAX_SIZE / height;
                    height = MAX_SIZE;
                }
            }

            canvas.width = width;
            canvas.height = height;
            canvas.getContext('2d').drawImage(image, 0, 0, width, height);
            canvas.toBlob(callback.bind(null, this.width, this.height, width, height), MIME, JPEG_QUALITY);
        };
        image.src = URL.createObjectURL(file);
    }


    function onResized(file, resizedFile, callback) {
        resizedFile.myName = file.name; // name is erased in the resized file
        if (file.size > resizedFile.size) {
            console.log('the resized file is smaller');
            callback(resizedFile);
        } else {
            // resized is bigger than the original
            // however, java ImageIO supports only  jpg, bmp, gif, png, which perferctly match mime types, the front-end should send only those types
            // if the file type is none of image/gif, image/png, image/jpeg, image/bmp use the bigger resized file
            console.warn('resized is bigger the the original');
            if (acceptableTypes.indexOf(file.type) >= 0) {
                callback(file);
            } else {
                console.warn('but the source file type is unacceptable: ' + file.type);
                callback(resizedFile);
            }
        }
    }
    return function (file, callback) {
        resizePhoto(file, function (originalWidth, originalHeight, resizedWidth, resizedHeight, resizedFile) {
            console.log('filename=' + file.name + '; initial/resized size=' + size(file.size) + '/' + size(resizedFile.size)
                    + '; initial picture dimensions=' + originalWidth + '/' + originalHeight + '; new dimensions=' + resizedWidth + '/' + resizedHeight);
            onResized(file, resizedFile, callback);
        });
    };
});
