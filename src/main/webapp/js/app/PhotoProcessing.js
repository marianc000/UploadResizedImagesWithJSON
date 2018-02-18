
define(['app/resize'], function (resize) {

    return  function (includedFileArray) {

        function isFileNotYetIncluded(file) {
            console.log(">isFileNotYetIncluded: " + file.name + "; " + file.size + "; " + includedFileArray.length);
            for (var c = 0; c < includedFileArray.length; c++) {
                console.log(c + " " + JSON.stringify(includedFileArray[c]));
                if (includedFileArray[c].originalNameSize.equals(file)) { // file has name and size read-only properties
                    return false;
                }
            }
            return true;
        }

        function getFileIndex(file) {
            console.log(">getFileIndex");
            var i = includedFileArray.indexOf(file);
            if (i < 0)
                throw 'This is impossible';
            console.log("<getFileIndex: " + i);
            return i;
        }

        this.removeFile = function (file) {
            var index = getFileIndex(file);
            includedFileArray.splice(index, 1);
        };

        this.process = function (files, callback) {// rename
            console.log("> resizeAndShowThumbs: ");
            for (var c = 0; c < files.length; c++) {
                var file = files[c];
                if (file.type.startsWith("image/") && isFileNotYetIncluded(file)) {
                    resize(file, callback);
                }
            }
        };
    };
});