define(function () {
//    function getFileNameWithoutExtension(fileName) {
//        return fileName.replace(/\.[^/.]+$/, "");
//    }

    return  function (uploadUrl, inputData, selectedFiles, callback) {
        var formData = new FormData();
        selectedFiles.forEach(function (photo) {
            // formData.append(name, value, filename); you can append multiple values with the same name.
            formData.append('photo', photo, photo.originalNameSize.nameWithoutExtension);
        });
        formData.append('dataObject', JSON.stringify(inputData));
        $.ajax({
            url: uploadUrl,
            data: formData,
            processData: false,
            type: 'POST',
            contentType: false,
            dataType: 'json'
        })
                .done(function (data, textStatus, jqXHR) {
                    console.table(data);
                    callback(data);
                })
                .fail(function (jqXHR, textStatus, errorThrown) {
                    console.log("error textStatus=" + textStatus + "; errorThrown=" + errorThrown);
                });
    }
});