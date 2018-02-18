define(['underscore', 'backbone', 'app/PhotoProcessing'],
        function (_, Backbone, PhotoProcessing) {
            var properties = {// maybe it's better to put them to settings so that they can be used by collection
                id: 'someId',
                firstName: 'firstName',
                lastName: 'lastName',
                photoUrls: 'photos'
            };
            return Backbone.Model.extend({
                idAttribute: properties.id, // this causes the model to be old
                urlRoot: 'api/upload',

                initialize: function ( ) {
                    this.on('all', function (eventName) {
                        console.log(">MODEL EVENT: id=" + this.id + "; event=" + eventName);
                    });
                    this.photoProcessing = new PhotoProcessing(this.getSelectedFiles());
                },
                getFirstName: function () {
                    return this.get(properties.firstName);
                },
                setFirstName: function (val) {
                    this.set(properties.firstName, val);
                },
                getLastName: function () {
                    return this.get(properties.lastName);
                },
                setLastName: function (val) {
                    this.set(properties.lastName, val);
                },
                getUrls: function () {
                    return this.get(properties.photoUrls);
                },
                setUrls: function (val) {
                    this.set(properties.photoUrls, val);
                },
                selectedFiles: [], // the photos to be uploaded
                getSelectedFiles: function () {
                    return   this.selectedFiles;
                },
                resizeAndShowThumbs: function (files) {// rename
                    console.log("> resizeAndShowThumbs: ");
                    this.photoProcessing.process(files, this.addFile.bind(this));
                },
                addFile: function (file) {
                    this.selectedFiles.push(file);
                    this.trigger('fileAdded', file);
                },
                saveMultipart: function (options) {
                    if (!options)
                        options = {};
                    var formData = new FormData();
                    this.getSelectedFiles().forEach(function (photo) {
                        //  formData.append(name, value, filename); you can append multiple values with the same name.
                        formData.append('photo', photo, photo.originalNameSize.nameWithoutExtension);
                    });
                    formData.append('dataObject', JSON.stringify(this.toJSON( )));
                    options.data = formData;
                    options.contentType = false;

                    this.save(null, options);
                }
            });
        });
