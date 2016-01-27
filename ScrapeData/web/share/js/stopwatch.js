function say(x){ alert(x); }

(function() {

    Stopwatch = function() {
        var instance = this,
        startTime, stopTime;

        this.start = function(){
            var d = new Date();
            instance.startTime = d.getTime(); // in ms
        };

        this.stop = function() {
            var d = new Date();
            instance.stopTime = d.getTime(); // in ms
        };

        this.elapsed = function() {
            return instance.stopTime - instance.startTime;
        };
    };

}());