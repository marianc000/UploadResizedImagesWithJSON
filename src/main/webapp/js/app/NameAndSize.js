
define(function ( ) {

    function NameAndSize(name, size) {
        this.name = name;
        this.size = size;
    }
    NameAndSize.prototype.toString = function () {
        return    ('{' + this.name + "; " + this.size + '}');
    };
    NameAndSize.prototype.equals = function (other) {
        return    (this.name === other.name && this.size === other.size);
    };
    return NameAndSize;
});