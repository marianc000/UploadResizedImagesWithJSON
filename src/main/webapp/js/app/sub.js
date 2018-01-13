define(function () {
    console.log('define sub');
    return function print(msg) {
        console.log(msg);
    };
});