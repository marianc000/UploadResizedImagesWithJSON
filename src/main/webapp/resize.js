
function resize(file, maxSize,  callback) {

    console.log('initial size=' + file.size);
    var image = new Image();
    image.onload = function ( ) {
        URL.revokeObjectURL(this.src);
        // Resize image
        var canvas = document.createElement('canvas'),
                width = image.width,
                height = image.height;

        if (width > height) {
            if (width > maxSize) {
                height *= maxSize / width;
                width = maxSize;
            }
        } else {
            if (height > maxSize) {
                width *= maxSize / height;
                height = maxSize;
            }
        }
        canvas.width = width;
        canvas.height = height;
        canvas.getContext('2d').drawImage(image, 0, 0, width, height);
        //void canvas.toBlob(callback, mimeType, qualityArgument);
        canvas.toBlob(function (blob) {
            callback(blob);
        }, 'image/jpeg', 0.8);

    }
    image.src = URL.createObjectURL(file);
}
