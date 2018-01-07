function sendImageToServer (canvas, url) {

  function onBlob (blob) {
    var request = new XMLHttpRequest();
    request.open('POST', url);
    request.onload = function (evt) {
      // Blob sent to server.
    }

    request.send(blob);
  }

  canvas.toBlob(onBlob);
}