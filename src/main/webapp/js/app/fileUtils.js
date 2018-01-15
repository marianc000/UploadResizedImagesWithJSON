define(function () {

    return {
        upload: function (uploadUrl, inputData, selectedFiles, callback) {
            var formData = new FormData();
            //array.forEach(function(currentValue, index, arr), thisValue)

            console.log(JSON.stringify(inputData));
            selectedFiles.forEach(function (photo) {
                formData.append('photo', photo, photo.myName); // formData.append(name, value, filename); you can append multiple values with the same name.
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
                        var location = jqXHR.getResponseHeader('Location');
                        console.log("done textStatus=" + textStatus + "; location=" + location + "; data=" + data);
                        console.table(data);
                        callback(data);
                    })
                    .fail(function (jqXHR, textStatus, errorThrown) {
                        console.log("error textStatus=" + textStatus + "; errorThrown=" + errorThrown);
                    });
        }
    };
});