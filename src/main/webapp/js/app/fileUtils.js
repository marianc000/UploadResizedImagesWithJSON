define(function () {
    return {
        firstName: "John",
        lastName: "Black",
        fileSize: function (size) {
            var i = Math.floor(Math.log(size) / Math.log(1024));
            return (size / Math.pow(1024, i)).toFixed(2) * 1 + ' ' + ['B', 'kB', 'MB', 'GB', 'TB'][i];
        },
        upload: function (uploadUrl, photos, callback) {
            var formData = new FormData();
            //array.forEach(function(currentValue, index, arr), thisValue)
            photos.forEach(function (photo) {
                formData.append('photo', photo, photo.myName); // formData.append(name, value, filename); you can append multiple values with the same name.
            });
            $.ajax({
                url: uploadUrl,
                data: formData,
                processData: false,
                type: 'POST',
                contentType: false
            })
                    .done(function (data, textStatus, jqXHR) {
                        console.log("done textStatus=" + textStatus);
                        var location = jqXHR.getResponseHeader('Location');
                        console.log("done textStatus=" + textStatus + "; location=" + location);
                        callback(location);
                    })
                    .fail(function (jqXHR, textStatus, errorThrown) {
                        console.log("error textStatus=" + textStatus + "; errorThrown=" + errorThrown);
                    });
        }
    };
});