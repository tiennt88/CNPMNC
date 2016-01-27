/**
 * Author: TienNT
 * Description: Cung cấp các API log
 * Date: 10/07/2011
 * FWVersion: 3.3
 **/

sd.log = {
    TRACE: 0,
    INFO: 1,
    WARN: 2,
    ERROR: 3,
    level: 0,
    logPrefix: "",

    trace: function(message){
        if(this.level <= this.TRACE){
            this._print(message);
        }
    },

    info: function(message){
        if(this.level <= this.INFO){
            this._print(message);
        }
    },

    warn: function(message){
        if(this.level <= this.WARN){
            this._print(message);
        }
    },

    error: function(message){
        if(this.level <= this.ERROR){
            this._print(message);
        }
    },

    _print: function(message){
        //console.log((this.logPrefix ? (this.logPrefix + " ") : "") + message);
        console.log(this.logPrefix + message);
    }
};

var logger = sd.log; // previous coding compatible
