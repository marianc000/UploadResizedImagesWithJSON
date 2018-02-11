define(function () {
    return  function (file, maxSize, callback) {
        var image = new Image();
        image.onload = function ( ) {
            URL.revokeObjectURL(this.src);
            var canvas = document.createElement('canvas');
            console.log("before=" + file.name + "; " + this.width + "; " + this.height + "; " + this.naturalWidth + "; " + this.naturalHeight);
            var width = this.width;
            var height = this.height;

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
            //     canvas.toBlob(callback);// huge
             canvas.toBlob(callback, 'image/jpeg', 0.95);
          //  canvas.toBlob(callback, 'image/jpeg', 0.90);
        };
        image.src = URL.createObjectURL(file);
    };
});
