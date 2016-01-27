/*
 *  Author: Hoang Long
 *
 **/

var sd = {
    // [ Shortcuts
    $ : function( /*String*/ id ) {
        return dojo.byId( id );
    },

    $style : function( /*String*/ id ) {
        return dojo.byId( id ).style;
    },

    _ : function( /*String*/ id ) {
        return dijit.byId( id );
    },

    _style : function( /*String*/ id ) {
        return dijit.byId( id ).domNode.style;
    },

    f : function( /*DOMNode||String*/ formNode ) {
        return dojo.formToObject( formNode );
    },
    // ]

    // PageContext (use to contain page-scope functions and variables)
    page : {},

    // ApplicationContext (use to contain app-scope functions and variables)
    app : {}
};
/**
 * Author: TienNT
 * Description: Cung cấp các API xử lý widget
 * Date: 10/07/2011
 * FWVersion: 3.3
 **/

sd.widget = {
    $ : function( /*String*/ id ) {
        return dijit.byId( id );
    },

    getStyle : function( /*String*/ id ) {
        return dijit.byId( id ).domNode.style;
    },

    __display : function( /*String*/ id, /*Boolean*/ state ) {
        sd._style( id ).display = ( state ) ? "" : "none";
    },

    __setDisabled : function( /*String*/ id, /*Boolean*/ state ) {
        sd._( id ).attr( "disabled", state );
    },

    __setReadOnly : function( /*String*/ id, /*Boolean*/ state ) {
        sd._( id ).attr( "readOnly", state );
    },

    
    setDisplay : function( /*String*/ id, /*Boolean*/ state ) {
        var labelDiv = sd.$( "autoLabelWrapper-" + id );
        var inputDiv = sd.$( "autoInputWrapper-" + id );

        if( labelDiv ) {
            if( state ) {
                labelDiv.style.display = "";
                inputDiv.style.display = "";
            } else {
                labelDiv.style.display = "none";
                inputDiv.style.display = "none";
            }
        } else {
            //this.__display( id, state );
        }

        this.__display( id, state );
    },

    setDisabled : function( /*String*/ id, /*Boolean*/ state ) {
        this.__setDisabled( id, state );
    },

    setReadOnly : function( /*String*/ id, /*Boolean*/ state ) {
        this.__setReadOnly( id, state );
    },


    show : function( /*String*/ id ) {
        var labelDiv = sd.$( "autoLabelWrapper-" + id );
        var inputDiv = sd.$( "autoInputWrapper-" + id );

        if( labelDiv ) {
            labelDiv.style.display = "";
            inputDiv.style.display = "";
        } else {
            //this.__display( id, true );
        }

        this.__display( id, true );
    },

    hide : function( /*String*/ id ) {
        var labelDiv = sd.$( "autoLabelWrapper-" + id );
        var inputDiv = sd.$( "autoInputWrapper-" + id );

        if( labelDiv ) {
            labelDiv.style.display = "none";
            inputDiv.style.display = "none";
        } else {
            //this.__display( id, false );
        }

        this.__display( id, false );
    },

    disable : function( /*String*/ id ) {
        this.__setDisabled( id, true );
    },

    enable : function( /*String*/ id ) {
        this.__setDisabled( id, false );
    },

    lock : function( /*String*/ id ) {
        this.__setReadOnly( id, true );
    },

    unlock : function( /*String*/ id ) {
        this.__setReadOnly( id, false );
    },
    
    setRequired: function(id, bRequired){
        var labelDiv = sd.$( "autoLabelWrapper-" + id );

        if( labelDiv ) {
            var label = labelDiv.getElementsByTagName( "label" )[0];
            if( bRequired ) {
                label.className = "label-red";
            } else {
                label.className = "label-blue";
            }
        }

        sd._( id ).vt_required = bRequired;
    },

    //[ TienNT: CKEditor utilities @13Aug11
    getCKE: function(/*String*/ id) {
        var ed = CKEDITOR.instances[id];
        return ed;
    },

    getCKEValue: function(/*String*/ id) {
        var ed = this.getCKE(id);
        if(ed != null && ed != undefined) {
            return ed.getData();
        } else {
            return undefined;
        }
    },

    delCKE: function(/*String*/ id) {
        var ed = this.getCKE(id);
        if(ed != null && ed != undefined) {
            try{
                ed.destroy(true);
            } catch(e) {
                alert("JSEX in delCKE:\n" + e.message);
            }
        }
    }
    //] TienNT: CKEditor utilities @13Aug11
};
/**
 *
 *  MD5 (Message-Digest Algorithm)
 *  http://www.webtoolkit.info/
 *
 **/
var MD5;
(function() {
    'use strict';

    if (typeof String.prototype.toWordArray === "undefined") {
        String.prototype.toWordArray = function() {
            var string = this,
            lWordCount,
            lMessageLength = string.length,
            lNumberOfWords_temp1=lMessageLength + 8,
            lNumberOfWords_temp2=(lNumberOfWords_temp1-(lNumberOfWords_temp1 % 64))/64,
            lNumberOfWords = (lNumberOfWords_temp2+1)*16,
            lWordArray= new Array(lNumberOfWords-1),
            lBytePosition = 0,
            lByteCount = 0;
            while ( lByteCount < lMessageLength ) {
                lWordCount = (lByteCount-(lByteCount % 4))/4;
                lBytePosition = (lByteCount % 4)*8;
                lWordArray[lWordCount] = (lWordArray[lWordCount] | (string.charCodeAt(lByteCount)<<lBytePosition));
                lByteCount++;
            }
            lWordCount = (lByteCount-(lByteCount % 4))/4;
            lBytePosition = (lByteCount % 4)*8;
            lWordArray[lWordCount] = lWordArray[lWordCount] | (0x80<<lBytePosition);
            lWordArray[lNumberOfWords-2] = lMessageLength<<3;
            lWordArray[lNumberOfWords-1] = lMessageLength>>>29;
            return lWordArray;
        };
    }

    var rotateLeft = function(lValue, iShiftBits) {
        return (lValue<<iShiftBits) | (lValue>>>(32-iShiftBits));
    },

    addUnsigned = function(lX,lY) {
        var lX4,lY4,lX8,lY8,lResult;
        lX8 = (lX & 0x80000000);
        lY8 = (lY & 0x80000000);
        lX4 = (lX & 0x40000000);
        lY4 = (lY & 0x40000000);
        lResult = (lX & 0x3FFFFFFF)+(lY & 0x3FFFFFFF);
        if (lX4 & lY4) {
            return (lResult ^ 0x80000000 ^ lX8 ^ lY8);
        }
        if (lX4 | lY4) {
            if (lResult & 0x40000000) {
                return (lResult ^ 0xC0000000 ^ lX8 ^ lY8);
            } else {
                return (lResult ^ 0x40000000 ^ lX8 ^ lY8);
            }
        } else {
            return (lResult ^ lX8 ^ lY8);
        }
    },

    f = function(x,y,z) { return (x & y) | ((~x) & z); },
    g = function(x,y,z) { return (x & z) | (y & (~z)); },
    h = function(x,y,z) { return (x ^ y ^ z); },
    i = function(x,y,z) { return (y ^ (x | (~z))); },

    ff = function(a,b,c,d,x,s,ac) {
        a = addUnsigned(a, addUnsigned(addUnsigned(f(b, c, d), x), ac));
        return addUnsigned(rotateLeft(a, s), b);
    },

    gg = function(a,b,c,d,x,s,ac) {
        a = addUnsigned(a, addUnsigned(addUnsigned(g(b, c, d), x), ac));
        return addUnsigned(rotateLeft(a, s), b);
    },

    hh = function(a,b,c,d,x,s,ac) {
        a = addUnsigned(a, addUnsigned(addUnsigned(h(b, c, d), x), ac));
        return addUnsigned(rotateLeft(a, s), b);
    },

    ii = function(a,b,c,d,x,s,ac) {
        a = addUnsigned(a, addUnsigned(addUnsigned(i(b, c, d), x), ac));
        return addUnsigned(rotateLeft(a, s), b);
    },


    wordToHex = function(lValue) {
        var WordToHexValue="", WordToHexValue_temp="", lByte, lCount;
        for (lCount = 0;lCount<=3;lCount++) {
            lByte = (lValue>>>(lCount*8)) & 255;
            WordToHexValue_temp = "0" + lByte.toString(16);
            WordToHexValue = WordToHexValue + WordToHexValue_temp.substr(WordToHexValue_temp.length-2,2);
        }
        return WordToHexValue;
    },

    utf8Encode = function(string) {
        string = string.replace(/\r\n/g,"\n");
        var n, c, utftext = "";

        for (n = 0; n < string.length; n++) {

            c = string.charCodeAt(n);

            if (c < 128) {
                utftext += String.fromCharCode(c);
            }
            else if((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            }
            else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }
        }

        return utftext;
    };

    MD5 = {
        getDigest : function(string) {

            var x = [],
            k, AA, BB, CC, DD, a, b, c, d,
            S11=7, S12=12, S13=17, S14=22,
            S21=5, S22=9 , S23=14, S24=20,
            S31=4, S32=11, S33=16, S34=23,
            S41=6, S42=10, S43=15, S44=21;

            string = utf8Encode(string);

            x = string.toWordArray();

            a = 0x67452301; b = 0xEFCDAB89; c = 0x98BADCFE; d = 0x10325476;

            for (k=0;k<x.length;k+=16) {
                AA=a; BB=b; CC=c; DD=d;
                a=ff(a,b,c,d,x[k+0], S11,0xD76AA478);
                d=ff(d,a,b,c,x[k+1], S12,0xE8C7B756);
                c=ff(c,d,a,b,x[k+2], S13,0x242070DB);
                b=ff(b,c,d,a,x[k+3], S14,0xC1BDCEEE);
                a=ff(a,b,c,d,x[k+4], S11,0xF57C0FAF);
                d=ff(d,a,b,c,x[k+5], S12,0x4787C62A);
                c=ff(c,d,a,b,x[k+6], S13,0xA8304613);
                b=ff(b,c,d,a,x[k+7], S14,0xFD469501);
                a=ff(a,b,c,d,x[k+8], S11,0x698098D8);
                d=ff(d,a,b,c,x[k+9], S12,0x8B44F7AF);
                c=ff(c,d,a,b,x[k+10],S13,0xFFFF5BB1);
                b=ff(b,c,d,a,x[k+11],S14,0x895CD7BE);
                a=ff(a,b,c,d,x[k+12],S11,0x6B901122);
                d=ff(d,a,b,c,x[k+13],S12,0xFD987193);
                c=ff(c,d,a,b,x[k+14],S13,0xA679438E);
                b=ff(b,c,d,a,x[k+15],S14,0x49B40821);
                a=gg(a,b,c,d,x[k+1], S21,0xF61E2562);
                d=gg(d,a,b,c,x[k+6], S22,0xC040B340);
                c=gg(c,d,a,b,x[k+11],S23,0x265E5A51);
                b=gg(b,c,d,a,x[k+0], S24,0xE9B6C7AA);
                a=gg(a,b,c,d,x[k+5], S21,0xD62F105D);
                d=gg(d,a,b,c,x[k+10],S22,0x2441453);
                c=gg(c,d,a,b,x[k+15],S23,0xD8A1E681);
                b=gg(b,c,d,a,x[k+4], S24,0xE7D3FBC8);
                a=gg(a,b,c,d,x[k+9], S21,0x21E1CDE6);
                d=gg(d,a,b,c,x[k+14],S22,0xC33707D6);
                c=gg(c,d,a,b,x[k+3], S23,0xF4D50D87);
                b=gg(b,c,d,a,x[k+8], S24,0x455A14ED);
                a=gg(a,b,c,d,x[k+13],S21,0xA9E3E905);
                d=gg(d,a,b,c,x[k+2], S22,0xFCEFA3F8);
                c=gg(c,d,a,b,x[k+7], S23,0x676F02D9);
                b=gg(b,c,d,a,x[k+12],S24,0x8D2A4C8A);
                a=hh(a,b,c,d,x[k+5], S31,0xFFFA3942);
                d=hh(d,a,b,c,x[k+8], S32,0x8771F681);
                c=hh(c,d,a,b,x[k+11],S33,0x6D9D6122);
                b=hh(b,c,d,a,x[k+14],S34,0xFDE5380C);
                a=hh(a,b,c,d,x[k+1], S31,0xA4BEEA44);
                d=hh(d,a,b,c,x[k+4], S32,0x4BDECFA9);
                c=hh(c,d,a,b,x[k+7], S33,0xF6BB4B60);
                b=hh(b,c,d,a,x[k+10],S34,0xBEBFBC70);
                a=hh(a,b,c,d,x[k+13],S31,0x289B7EC6);
                d=hh(d,a,b,c,x[k+0], S32,0xEAA127FA);
                c=hh(c,d,a,b,x[k+3], S33,0xD4EF3085);
                b=hh(b,c,d,a,x[k+6], S34,0x4881D05);
                a=hh(a,b,c,d,x[k+9], S31,0xD9D4D039);
                d=hh(d,a,b,c,x[k+12],S32,0xE6DB99E5);
                c=hh(c,d,a,b,x[k+15],S33,0x1FA27CF8);
                b=hh(b,c,d,a,x[k+2], S34,0xC4AC5665);
                a=ii(a,b,c,d,x[k+0], S41,0xF4292244);
                d=ii(d,a,b,c,x[k+7], S42,0x432AFF97);
                c=ii(c,d,a,b,x[k+14],S43,0xAB9423A7);
                b=ii(b,c,d,a,x[k+5], S44,0xFC93A039);
                a=ii(a,b,c,d,x[k+12],S41,0x655B59C3);
                d=ii(d,a,b,c,x[k+3], S42,0x8F0CCC92);
                c=ii(c,d,a,b,x[k+10],S43,0xFFEFF47D);
                b=ii(b,c,d,a,x[k+1], S44,0x85845DD1);
                a=ii(a,b,c,d,x[k+8], S41,0x6FA87E4F);
                d=ii(d,a,b,c,x[k+15],S42,0xFE2CE6E0);
                c=ii(c,d,a,b,x[k+6], S43,0xA3014314);
                b=ii(b,c,d,a,x[k+13],S44,0x4E0811A1);
                a=ii(a,b,c,d,x[k+4], S41,0xF7537E82);
                d=ii(d,a,b,c,x[k+11],S42,0xBD3AF235);
                c=ii(c,d,a,b,x[k+2], S43,0x2AD7D2BB);
                b=ii(b,c,d,a,x[k+9], S44,0xEB86D391);
                a=addUnsigned(a,AA);
                b=addUnsigned(b,BB);
                c=addUnsigned(c,CC);
                d=addUnsigned(d,DD);
            }

            var temp = wordToHex(a) + wordToHex(b) + wordToHex(c) + wordToHex(d);

            return temp.toLowerCase();
        }
    };
}());
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
/**
 * Author: TienNT
 * Description: Cung cấp các API để can thiệp cơ chế xử lý lỗi vào quá trình request/response XHR của dojo toolkit
 * Date: 10/07/2011
 * FWVersion: 3.3
 **/

//[!important TienNT says: Handle session timeout redirect @7Apr11
dojo._ioSetArgs = function(
    /*dojo.__IoArgs*/ args,
    /*Function*/ canceller,
    /*Function*/ okHandler,
    /*Function*/ errHandler){

    var _d = dojo, cfg = _d.config;
    
    var arrUrl=args.url.toString().split("?");
    
    var ioArgs;
    if (sd.operator.isCryptParameter){
        ioArgs = {
            args: args,
            url: arrUrl[0]
        };
    }else {
        ioArgs = {
            args: args,
            url: args.url
        };
    }
    //Get values from form if requestd.
    var formObject = null;
    if(args.form){
        var form = _d.byId(args.form);
        //IE requires going through getAttributeNode instead of just getAttribute in some form cases,
        //so use it for all.  See #2844
        var actnNode = form.getAttributeNode("action");
        ioArgs.url = ioArgs.url || (actnNode ? actnNode.value : null);
        formObject = _d.formToObject(form);
    }

    // set up the query params
    var miArgs = [{}];

    if(formObject){
        // potentially over-ride url-provided params w/ form values
        miArgs.push(formObject);
    }
    if(args.content){
        // stuff in content over-rides what's set by form
        miArgs.push(args.content);
    }

    //cuongnx ma hoa tham so
    if (sd.operator.isCryptParameter){
        ioArgs.query=crypt.encodeParams(_d,miArgs,args.preventCache,arrUrl);
    }else {
        if(args.preventCache){
            miArgs.push({
                "dojo.preventCache": new Date().valueOf()
            });
        }
        ioArgs.query= _d.objectToQuery(_d.mixin.apply(null, miArgs));
    }
    // .. and the real work of getting the deferred in order, etc.
    ioArgs.handleAs = args.handleAs || "text";
    var d = new _d.Deferred(canceller);
    d.addCallbacks(okHandler, function(error){
        // TienNT TimeoutProb@29Mar11
        if(!sd.operator.checkHTTPStatus(d.ioArgs, error)) {
        //return null;
        }
        return errHandler(error, d);
    });

    var ld = args.load;
    if(ld && _d.isFunction(ld)){
        d.addCallback(function(value){
            //alert("callback: " + d.ioArgs.xhr.status + "\n" + d.ioArgs.xhr.status.constructor);
            // TienNT TimeoutProb@29Mar11
            if(!sd.operator.checkHTTPStatus(d.ioArgs, value)) {
            //return null;
            }

            return ld.call(args, value, ioArgs);
        });
    }
    var err = args.error;
    if(err && _d.isFunction(err)){
        d.addErrback(function(value){
            //alert("errback: " + d.ioArgs.xhr.status);
            // TienNT TimeoutProb@29Mar11
            if(!sd.operator.checkHTTPStatus(d.ioArgs, value)) {
            //return null;
            }

            return err.call(args, value, ioArgs);
        });
    }
    var handle = args.handle;
    if(handle && _d.isFunction(handle)){
        d.addBoth(function(value){
            //alert("handler: " + d.ioArgs.xhr.status);
            // TienNT TimeoutProb@29Mar11
            if(!sd.operator.checkHTTPStatus(d.ioArgs, value)) {
            //return null;
            }

            return handle.call(args, value, ioArgs);
        });
    }

    if(cfg.ioPublish && _d.publish && ioArgs.args.ioPublish !== false){
        d.addCallbacks(
            function(res){
                _d.publish("/dojo/io/load", [d, res]);
                return res;
            },
            function(res){
                _d.publish("/dojo/io/error", [d, res]);
                return res;
            }
            );
        d.addBoth(function(res){
            _d.publish("/dojo/io/done", [d, res]);
            return res;
        });
    }

    d.ioArgs = ioArgs;
    return d;
}
//]!important TienNT says: Handle session timeout redirect @7Apr11
/**
 * Author: TienNT
 * Description: Cung cấp các API cho phép xử lý sự kiện next/back của browser
 * Date: 10/07/2011
 * FWVersion: 3.3
 **/

ApplicationState = function(action, areaId){
    this.action = action;
    this.areaId = areaId;

    this.preGo = undefined; //Function
    this.postGo = undefined; //Function
    this.param = undefined; //Object

    this.changeUrl = true; //?
};

dojo.extend(ApplicationState, {
    back: function(){
        this.go();
    },

    forward: function(){
        this.go();
    },
    
    go: function(){
        try{
            if(this.action && this.action.length > 0) {
                if(this.preGo) {
                    this.preGo();
                }

                var arg = {
                    url : this.action,
                    areaId : this.areaId
                };

                if(this.postGo) {
                    arg.onSuccess = this.postGo;
                }

                sd.connector.updateArea(arg);
            }
        } catch(e) {
            alert("vt-appstate.js, go:\n" + e.message);
        }
    }
});
/**
 * Author: TienNT
 * Description: Cung cấp các API gửi request
 * Date: 10/07/2011
 * FWVersion: 3.3
 **/

sd.connector = {

    /**
     * Author: TienNT
     * Description: Bổ sung tham số sync, cho phép gửi request ajax đồng bộ
     * Date: 16/06/2011
     * FWVersion: 3.3
     **/
    post : function( /*String url, String area, String formId, Object param, Function callback, Function errback, String responseType, Boolean sync*/ ) {
        var _url, _area, _param, _form, _callback, _errback, _responseType, _sync;
        var args = arguments;
        // [ Collect arguments
        _url = args[0];

        _param = null;
        _form = null;
        _responseType = "text";

        if( args.length >= 2 ) {
            _area = args[1];
            if( args.length >= 3 ) {
                _form = dojo.byId( args[2] );
                if( args.length >= 4 ) {
                    _param = args[3];
                }
                if( args.length >= 5 ) {
                    _callback = args[4];
                    if( args.length >= 6 ) {
                        _errback = args[5];
                        if( args.length >= 7 ) {
                            _responseType = args[6];
                            if( args.length >= 8 ) {
                                _sync = args[7];
                            }
                        }
                    }
                }
            }
        }

        //]Datbt

        var parameter = {
            url : _url,
            preventCache : true,
            
            load : function( response, ioArgs ) {
                try {
                    if( _responseType == "text" ) {
                        var node = null;
                        
                        if(_area) {
                            node = dojo.byId( _area );
                        }

                        if(node != null && node != undefined) {
                            try {
                                sd.operator.freeWidgets( node );
                                sd.operator.allowedToExecJS = false;
                                node.innerHTML = response;
                              
                                dojo.parser.parse( node );

                            } catch(e) {
//                                alert( "Error in post.load, \nsd.operator.freeWidgets, dojo.parser.parse:\n" + e.message );
                            }
                        }else{
                            console.log("sd.connector.post: The area with areaId = '" + _area + "' is null");
                        }

                        try {
//                            alert(response);
                            var mixed = sd.operator.parse( response );
                            sd.operator.allowedToExecJS = true;
                            //alert('truoc---');
                            //alert(mixed.scripts);
                            sd.operator.execScript( mixed.scripts );
                            //alert('sau---');
                        } catch(e) {
//                            alert( "Error in post.load, \nsd.operator.parse, sd.operator.execScript:\n" + e.message );
                        }
                    }
                } catch( e ) {
                    alert( "Error in post.load:\n" + e.message );
                }

                if( _callback ) {
                    try {
                        _callback.call( this, response, ioArgs );
                    } catch( e ) {
                        alert( "Error in your onSuccess:\n" + e.message );
                    }
                }
                sd.operator.decreaseXHRCounter();
            },
            
            error : function( error, ioArgs ) {
                sd.operator.decreaseXHRCounter();
                //sd.operator.displayErrorWhileSendingRequest(error);
                //console.log( "Inside post.error:\n" + sd.util.dump(error, 1) );

                if( _errback ) {
                    try {
                        _errback.call( this, error, ioArgs );
                    } catch( e ) {
                        alert( "Error in your onFail:\n" + e.message );
                    }
                }
            },
            
            handleAs : _responseType
        };

        if( _param ) {
            parameter.content = _param;
        }
        if( _form ) {
            parameter.form = _form;
        }

        if(_sync === true || _sync === false) {
            parameter.sync = _sync;
        }

        if( sd.operator.timeout ) {
            parameter.timeout = sd.operator.timeout;
        }
        
        sd.operator.increaseXHRCounter();
        
        dojo.xhrPost( parameter );
        
    },

    updateArea : function(/*{String url, String areaId, String formId, Object param, Function onSuccess, Function onFail, Boolean sync}*/ inputParam) {
        var url, areaId, formId, param, callback, errback, sync,
        responseType = "text";

        url = inputParam.url;
        areaId = inputParam.areaId;
        formId = inputParam.formId;
        param = inputParam.param;
        callback = inputParam.onSuccess;
        errback = inputParam.onFail;
        sync = inputParam.sync;

        sd.connector.post(url, areaId, formId, param, callback, errback, responseType, sync);
    },

    getJSON : function(/*{String url, String formId, Object param, Function onSuccess, Function onFail}*/ inputParam) {
        var url, formId, param, callback, errback, sync,
        responseType = "json";

        url = inputParam.url;
        formId = inputParam.formId;
        param = inputParam.param;
        callback = inputParam.onSuccess;
        errback = inputParam.onFail;
        sync = inputParam.sync;

        sd.connector.post(url, null, formId, param, callback, errback, responseType, sync);
    }
};
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
crypt = {
    encodeParams:function(_d,miArgs,preventCache,arrUrl){
        var pSplit='';
        if (arrUrl.length>1) pSplit=arrUrl[1];
        if (sd.operator.isCryptParameter){
            var plaintext = pSplit+'&'+_d.objectToQuery(_d.mixin.apply(null, miArgs));
            if (plaintext!=null && plaintext!=""){
                var keystring = sd.operator.keyString;
                var ivstring = sd.operator.ivString;
                var md5String = MD5.getDigest(keystring),
                keybytes = cryptoHelpers.toNumbers(md5String),
                iv =cryptoHelpers.convertStringToByteArray(ivstring),
                keysize, key = cryptoHelpers.toHex(keybytes),
                bytesToEncrypt, mode, result,
                decrypted, recoveredText, stopwatch;
                stopwatch = new Stopwatch();
                stopwatch.start();
                keysize = slowAES.aes.keySize.SIZE_128;
                bytesToEncrypt = cryptoHelpers.convertStringToByteArray(plaintext);
                mode = slowAES.modeOfOperation.CBC;
                result = slowAES.encrypt(bytesToEncrypt, mode, keybytes, keysize, iv);
                return "vt="+cryptoHelpers.toHex(result.cipher)+"&dojo.preventCache="+new Date().valueOf();
            }
            else return"";
        }else {
            if(preventCache){
                miArgs.push({
                    "dojo.preventCache": new Date().valueOf()
                });
            }
            return pSplit+'&'+_d.objectToQuery(_d.mixin.apply(null, miArgs));
        }
    },
    encode:function(keystring,ivstring,plaintext){

        if (sd.operator.isCryptParameter){
            if (plaintext!=null && plaintext!=""){
                var md5String = MD5.getDigest(keystring),
                keybytes = cryptoHelpers.toNumbers(md5String),
                iv =cryptoHelpers.convertStringToByteArray(ivstring),
                keysize, key = cryptoHelpers.toHex(keybytes),
                bytesToEncrypt, mode, result,
                decrypted, recoveredText, stopwatch;
                stopwatch = new Stopwatch();
                stopwatch.start();
                keysize = slowAES.aes.keySize.SIZE_128;
                bytesToEncrypt = cryptoHelpers.convertStringToByteArray(plaintext);
                mode = slowAES.modeOfOperation.CBC;
                result = slowAES.encrypt(bytesToEncrypt, mode, keybytes, keysize, iv);
                return "vt="+cryptoHelpers.toHex(result.cipher)+"&dojo.preventCache="+new Date().valueOf();
            }
            else return"";
        }else return plaintext;
    }
};
/**
 * Author: TienNT
 * Description: Cung cấp các API xử lý ràng buộc event
 * Date: 10/07/2011
 * FWVersion: 3.3
 **/

sd.event = {
    
    bind : function( /*String|DOMNode*/ _node, /*String*/ _eventName, /*Function*/ _callbackObj ) {
        var node = sd.util.getObj( _node );
        var eventName = _eventName.toLowerCase();
        var handle, callback;

        if( eventName == "onkeyup" && node.__sd_masking_onkeyup ) {
            callback = function( e ) {
                if( e.target && e.target.__sd_masking_onkeyup ) {
                    e.target.__sd_masking_onkeyup.call( this, e );
                }
                _callbackObj.call( this, e );
            };
        } else {
            callback = _callbackObj;
        }

        handle = dojo.connect( node, _eventName, callback );
        return handle;
    },

    unbind : function( /*Dojo.Handle*/ _handle ) {
        dojo.disconnect( _handle );
    }
};
slowAES = {
    /*
     * START AES SECTION
     */
    aes:{
        // structure of valid key sizes
        keySize:{
            SIZE_128:16,
            SIZE_192:24,
            SIZE_256:32
        },

        // Rijndael S-box
        sbox:[
            0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
            0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
            0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
            0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
            0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
            0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
            0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
            0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
            0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
            0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
            0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
            0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
            0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
            0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
            0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
            0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16 ],

        // Rijndael Inverted S-box
        rsbox:
        [ 0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb
          , 0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb
          , 0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e
          , 0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25
          , 0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92
          , 0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84
          , 0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06
          , 0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b
          , 0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73
          , 0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e
          , 0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b
          , 0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4
          , 0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f
          , 0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef
          , 0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61
          , 0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d ],


        /* rotate the word eight bits to the left */
        rotate : function(word) {
            'use strict';
            var c = word[0], i;
            for (i = 0; i < 3; i++) {
                word[i] = word[i+1];
            }
            word[3] = c;

            return word;
        },

        // Rijndael Rcon
        Rcon:[
            0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
            0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
            0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f,
            0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d,
            0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab,
            0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d,
            0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25,
            0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01,
            0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d,
            0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa,
            0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a,
            0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02,
            0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a,
            0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
            0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94,
            0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04,
            0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f,
            0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5,
            0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33,
            0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb ],

        G2X: [
            0x00, 0x02, 0x04, 0x06, 0x08, 0x0a, 0x0c, 0x0e, 0x10, 0x12, 0x14, 0x16,
            0x18, 0x1a, 0x1c, 0x1e, 0x20, 0x22, 0x24, 0x26, 0x28, 0x2a, 0x2c, 0x2e,
            0x30, 0x32, 0x34, 0x36, 0x38, 0x3a, 0x3c, 0x3e, 0x40, 0x42, 0x44, 0x46,
            0x48, 0x4a, 0x4c, 0x4e, 0x50, 0x52, 0x54, 0x56, 0x58, 0x5a, 0x5c, 0x5e,
            0x60, 0x62, 0x64, 0x66, 0x68, 0x6a, 0x6c, 0x6e, 0x70, 0x72, 0x74, 0x76,
            0x78, 0x7a, 0x7c, 0x7e, 0x80, 0x82, 0x84, 0x86, 0x88, 0x8a, 0x8c, 0x8e,
            0x90, 0x92, 0x94, 0x96, 0x98, 0x9a, 0x9c, 0x9e, 0xa0, 0xa2, 0xa4, 0xa6,
            0xa8, 0xaa, 0xac, 0xae, 0xb0, 0xb2, 0xb4, 0xb6, 0xb8, 0xba, 0xbc, 0xbe,
            0xc0, 0xc2, 0xc4, 0xc6, 0xc8, 0xca, 0xcc, 0xce, 0xd0, 0xd2, 0xd4, 0xd6,
            0xd8, 0xda, 0xdc, 0xde, 0xe0, 0xe2, 0xe4, 0xe6, 0xe8, 0xea, 0xec, 0xee,
            0xf0, 0xf2, 0xf4, 0xf6, 0xf8, 0xfa, 0xfc, 0xfe, 0x1b, 0x19, 0x1f, 0x1d,
            0x13, 0x11, 0x17, 0x15, 0x0b, 0x09, 0x0f, 0x0d, 0x03, 0x01, 0x07, 0x05,
            0x3b, 0x39, 0x3f, 0x3d, 0x33, 0x31, 0x37, 0x35, 0x2b, 0x29, 0x2f, 0x2d,
            0x23, 0x21, 0x27, 0x25, 0x5b, 0x59, 0x5f, 0x5d, 0x53, 0x51, 0x57, 0x55,
            0x4b, 0x49, 0x4f, 0x4d, 0x43, 0x41, 0x47, 0x45, 0x7b, 0x79, 0x7f, 0x7d,
            0x73, 0x71, 0x77, 0x75, 0x6b, 0x69, 0x6f, 0x6d, 0x63, 0x61, 0x67, 0x65,
            0x9b, 0x99, 0x9f, 0x9d, 0x93, 0x91, 0x97, 0x95, 0x8b, 0x89, 0x8f, 0x8d,
            0x83, 0x81, 0x87, 0x85, 0xbb, 0xb9, 0xbf, 0xbd, 0xb3, 0xb1, 0xb7, 0xb5,
            0xab, 0xa9, 0xaf, 0xad, 0xa3, 0xa1, 0xa7, 0xa5, 0xdb, 0xd9, 0xdf, 0xdd,
            0xd3, 0xd1, 0xd7, 0xd5, 0xcb, 0xc9, 0xcf, 0xcd, 0xc3, 0xc1, 0xc7, 0xc5,
            0xfb, 0xf9, 0xff, 0xfd, 0xf3, 0xf1, 0xf7, 0xf5, 0xeb, 0xe9, 0xef, 0xed,
            0xe3, 0xe1, 0xe7, 0xe5
        ],

        G3X: [
            0x00, 0x03, 0x06, 0x05, 0x0c, 0x0f, 0x0a, 0x09, 0x18, 0x1b, 0x1e, 0x1d,
            0x14, 0x17, 0x12, 0x11, 0x30, 0x33, 0x36, 0x35, 0x3c, 0x3f, 0x3a, 0x39,
            0x28, 0x2b, 0x2e, 0x2d, 0x24, 0x27, 0x22, 0x21, 0x60, 0x63, 0x66, 0x65,
            0x6c, 0x6f, 0x6a, 0x69, 0x78, 0x7b, 0x7e, 0x7d, 0x74, 0x77, 0x72, 0x71,
            0x50, 0x53, 0x56, 0x55, 0x5c, 0x5f, 0x5a, 0x59, 0x48, 0x4b, 0x4e, 0x4d,
            0x44, 0x47, 0x42, 0x41, 0xc0, 0xc3, 0xc6, 0xc5, 0xcc, 0xcf, 0xca, 0xc9,
            0xd8, 0xdb, 0xde, 0xdd, 0xd4, 0xd7, 0xd2, 0xd1, 0xf0, 0xf3, 0xf6, 0xf5,
            0xfc, 0xff, 0xfa, 0xf9, 0xe8, 0xeb, 0xee, 0xed, 0xe4, 0xe7, 0xe2, 0xe1,
            0xa0, 0xa3, 0xa6, 0xa5, 0xac, 0xaf, 0xaa, 0xa9, 0xb8, 0xbb, 0xbe, 0xbd,
            0xb4, 0xb7, 0xb2, 0xb1, 0x90, 0x93, 0x96, 0x95, 0x9c, 0x9f, 0x9a, 0x99,
            0x88, 0x8b, 0x8e, 0x8d, 0x84, 0x87, 0x82, 0x81, 0x9b, 0x98, 0x9d, 0x9e,
            0x97, 0x94, 0x91, 0x92, 0x83, 0x80, 0x85, 0x86, 0x8f, 0x8c, 0x89, 0x8a,
            0xab, 0xa8, 0xad, 0xae, 0xa7, 0xa4, 0xa1, 0xa2, 0xb3, 0xb0, 0xb5, 0xb6,
            0xbf, 0xbc, 0xb9, 0xba, 0xfb, 0xf8, 0xfd, 0xfe, 0xf7, 0xf4, 0xf1, 0xf2,
            0xe3, 0xe0, 0xe5, 0xe6, 0xef, 0xec, 0xe9, 0xea, 0xcb, 0xc8, 0xcd, 0xce,
            0xc7, 0xc4, 0xc1, 0xc2, 0xd3, 0xd0, 0xd5, 0xd6, 0xdf, 0xdc, 0xd9, 0xda,
            0x5b, 0x58, 0x5d, 0x5e, 0x57, 0x54, 0x51, 0x52, 0x43, 0x40, 0x45, 0x46,
            0x4f, 0x4c, 0x49, 0x4a, 0x6b, 0x68, 0x6d, 0x6e, 0x67, 0x64, 0x61, 0x62,
            0x73, 0x70, 0x75, 0x76, 0x7f, 0x7c, 0x79, 0x7a, 0x3b, 0x38, 0x3d, 0x3e,
            0x37, 0x34, 0x31, 0x32, 0x23, 0x20, 0x25, 0x26, 0x2f, 0x2c, 0x29, 0x2a,
            0x0b, 0x08, 0x0d, 0x0e, 0x07, 0x04, 0x01, 0x02, 0x13, 0x10, 0x15, 0x16,
            0x1f, 0x1c, 0x19, 0x1a
        ],

        G9X: [
            0x00, 0x09, 0x12, 0x1b, 0x24, 0x2d, 0x36, 0x3f, 0x48, 0x41, 0x5a, 0x53,
            0x6c, 0x65, 0x7e, 0x77, 0x90, 0x99, 0x82, 0x8b, 0xb4, 0xbd, 0xa6, 0xaf,
            0xd8, 0xd1, 0xca, 0xc3, 0xfc, 0xf5, 0xee, 0xe7, 0x3b, 0x32, 0x29, 0x20,
            0x1f, 0x16, 0x0d, 0x04, 0x73, 0x7a, 0x61, 0x68, 0x57, 0x5e, 0x45, 0x4c,
            0xab, 0xa2, 0xb9, 0xb0, 0x8f, 0x86, 0x9d, 0x94, 0xe3, 0xea, 0xf1, 0xf8,
            0xc7, 0xce, 0xd5, 0xdc, 0x76, 0x7f, 0x64, 0x6d, 0x52, 0x5b, 0x40, 0x49,
            0x3e, 0x37, 0x2c, 0x25, 0x1a, 0x13, 0x08, 0x01, 0xe6, 0xef, 0xf4, 0xfd,
            0xc2, 0xcb, 0xd0, 0xd9, 0xae, 0xa7, 0xbc, 0xb5, 0x8a, 0x83, 0x98, 0x91,
            0x4d, 0x44, 0x5f, 0x56, 0x69, 0x60, 0x7b, 0x72, 0x05, 0x0c, 0x17, 0x1e,
            0x21, 0x28, 0x33, 0x3a, 0xdd, 0xd4, 0xcf, 0xc6, 0xf9, 0xf0, 0xeb, 0xe2,
            0x95, 0x9c, 0x87, 0x8e, 0xb1, 0xb8, 0xa3, 0xaa, 0xec, 0xe5, 0xfe, 0xf7,
            0xc8, 0xc1, 0xda, 0xd3, 0xa4, 0xad, 0xb6, 0xbf, 0x80, 0x89, 0x92, 0x9b,
            0x7c, 0x75, 0x6e, 0x67, 0x58, 0x51, 0x4a, 0x43, 0x34, 0x3d, 0x26, 0x2f,
            0x10, 0x19, 0x02, 0x0b, 0xd7, 0xde, 0xc5, 0xcc, 0xf3, 0xfa, 0xe1, 0xe8,
            0x9f, 0x96, 0x8d, 0x84, 0xbb, 0xb2, 0xa9, 0xa0, 0x47, 0x4e, 0x55, 0x5c,
            0x63, 0x6a, 0x71, 0x78, 0x0f, 0x06, 0x1d, 0x14, 0x2b, 0x22, 0x39, 0x30,
            0x9a, 0x93, 0x88, 0x81, 0xbe, 0xb7, 0xac, 0xa5, 0xd2, 0xdb, 0xc0, 0xc9,
            0xf6, 0xff, 0xe4, 0xed, 0x0a, 0x03, 0x18, 0x11, 0x2e, 0x27, 0x3c, 0x35,
            0x42, 0x4b, 0x50, 0x59, 0x66, 0x6f, 0x74, 0x7d, 0xa1, 0xa8, 0xb3, 0xba,
            0x85, 0x8c, 0x97, 0x9e, 0xe9, 0xe0, 0xfb, 0xf2, 0xcd, 0xc4, 0xdf, 0xd6,
            0x31, 0x38, 0x23, 0x2a, 0x15, 0x1c, 0x07, 0x0e, 0x79, 0x70, 0x6b, 0x62,
            0x5d, 0x54, 0x4f, 0x46
        ],

        GBX: [
            0x00, 0x0b, 0x16, 0x1d, 0x2c, 0x27, 0x3a, 0x31, 0x58, 0x53, 0x4e, 0x45,
            0x74, 0x7f, 0x62, 0x69, 0xb0, 0xbb, 0xa6, 0xad, 0x9c, 0x97, 0x8a, 0x81,
            0xe8, 0xe3, 0xfe, 0xf5, 0xc4, 0xcf, 0xd2, 0xd9, 0x7b, 0x70, 0x6d, 0x66,
            0x57, 0x5c, 0x41, 0x4a, 0x23, 0x28, 0x35, 0x3e, 0x0f, 0x04, 0x19, 0x12,
            0xcb, 0xc0, 0xdd, 0xd6, 0xe7, 0xec, 0xf1, 0xfa, 0x93, 0x98, 0x85, 0x8e,
            0xbf, 0xb4, 0xa9, 0xa2, 0xf6, 0xfd, 0xe0, 0xeb, 0xda, 0xd1, 0xcc, 0xc7,
            0xae, 0xa5, 0xb8, 0xb3, 0x82, 0x89, 0x94, 0x9f, 0x46, 0x4d, 0x50, 0x5b,
            0x6a, 0x61, 0x7c, 0x77, 0x1e, 0x15, 0x08, 0x03, 0x32, 0x39, 0x24, 0x2f,
            0x8d, 0x86, 0x9b, 0x90, 0xa1, 0xaa, 0xb7, 0xbc, 0xd5, 0xde, 0xc3, 0xc8,
            0xf9, 0xf2, 0xef, 0xe4, 0x3d, 0x36, 0x2b, 0x20, 0x11, 0x1a, 0x07, 0x0c,
            0x65, 0x6e, 0x73, 0x78, 0x49, 0x42, 0x5f, 0x54, 0xf7, 0xfc, 0xe1, 0xea,
            0xdb, 0xd0, 0xcd, 0xc6, 0xaf, 0xa4, 0xb9, 0xb2, 0x83, 0x88, 0x95, 0x9e,
            0x47, 0x4c, 0x51, 0x5a, 0x6b, 0x60, 0x7d, 0x76, 0x1f, 0x14, 0x09, 0x02,
            0x33, 0x38, 0x25, 0x2e, 0x8c, 0x87, 0x9a, 0x91, 0xa0, 0xab, 0xb6, 0xbd,
            0xd4, 0xdf, 0xc2, 0xc9, 0xf8, 0xf3, 0xee, 0xe5, 0x3c, 0x37, 0x2a, 0x21,
            0x10, 0x1b, 0x06, 0x0d, 0x64, 0x6f, 0x72, 0x79, 0x48, 0x43, 0x5e, 0x55,
            0x01, 0x0a, 0x17, 0x1c, 0x2d, 0x26, 0x3b, 0x30, 0x59, 0x52, 0x4f, 0x44,
            0x75, 0x7e, 0x63, 0x68, 0xb1, 0xba, 0xa7, 0xac, 0x9d, 0x96, 0x8b, 0x80,
            0xe9, 0xe2, 0xff, 0xf4, 0xc5, 0xce, 0xd3, 0xd8, 0x7a, 0x71, 0x6c, 0x67,
            0x56, 0x5d, 0x40, 0x4b, 0x22, 0x29, 0x34, 0x3f, 0x0e, 0x05, 0x18, 0x13,
            0xca, 0xc1, 0xdc, 0xd7, 0xe6, 0xed, 0xf0, 0xfb, 0x92, 0x99, 0x84, 0x8f,
            0xbe, 0xb5, 0xa8, 0xa3
        ],

        GDX: [
            0x00, 0x0d, 0x1a, 0x17, 0x34, 0x39, 0x2e, 0x23, 0x68, 0x65, 0x72, 0x7f,
            0x5c, 0x51, 0x46, 0x4b, 0xd0, 0xdd, 0xca, 0xc7, 0xe4, 0xe9, 0xfe, 0xf3,
            0xb8, 0xb5, 0xa2, 0xaf, 0x8c, 0x81, 0x96, 0x9b, 0xbb, 0xb6, 0xa1, 0xac,
            0x8f, 0x82, 0x95, 0x98, 0xd3, 0xde, 0xc9, 0xc4, 0xe7, 0xea, 0xfd, 0xf0,
            0x6b, 0x66, 0x71, 0x7c, 0x5f, 0x52, 0x45, 0x48, 0x03, 0x0e, 0x19, 0x14,
            0x37, 0x3a, 0x2d, 0x20, 0x6d, 0x60, 0x77, 0x7a, 0x59, 0x54, 0x43, 0x4e,
            0x05, 0x08, 0x1f, 0x12, 0x31, 0x3c, 0x2b, 0x26, 0xbd, 0xb0, 0xa7, 0xaa,
            0x89, 0x84, 0x93, 0x9e, 0xd5, 0xd8, 0xcf, 0xc2, 0xe1, 0xec, 0xfb, 0xf6,
            0xd6, 0xdb, 0xcc, 0xc1, 0xe2, 0xef, 0xf8, 0xf5, 0xbe, 0xb3, 0xa4, 0xa9,
            0x8a, 0x87, 0x90, 0x9d, 0x06, 0x0b, 0x1c, 0x11, 0x32, 0x3f, 0x28, 0x25,
            0x6e, 0x63, 0x74, 0x79, 0x5a, 0x57, 0x40, 0x4d, 0xda, 0xd7, 0xc0, 0xcd,
            0xee, 0xe3, 0xf4, 0xf9, 0xb2, 0xbf, 0xa8, 0xa5, 0x86, 0x8b, 0x9c, 0x91,
            0x0a, 0x07, 0x10, 0x1d, 0x3e, 0x33, 0x24, 0x29, 0x62, 0x6f, 0x78, 0x75,
            0x56, 0x5b, 0x4c, 0x41, 0x61, 0x6c, 0x7b, 0x76, 0x55, 0x58, 0x4f, 0x42,
            0x09, 0x04, 0x13, 0x1e, 0x3d, 0x30, 0x27, 0x2a, 0xb1, 0xbc, 0xab, 0xa6,
            0x85, 0x88, 0x9f, 0x92, 0xd9, 0xd4, 0xc3, 0xce, 0xed, 0xe0, 0xf7, 0xfa,
            0xb7, 0xba, 0xad, 0xa0, 0x83, 0x8e, 0x99, 0x94, 0xdf, 0xd2, 0xc5, 0xc8,
            0xeb, 0xe6, 0xf1, 0xfc, 0x67, 0x6a, 0x7d, 0x70, 0x53, 0x5e, 0x49, 0x44,
            0x0f, 0x02, 0x15, 0x18, 0x3b, 0x36, 0x21, 0x2c, 0x0c, 0x01, 0x16, 0x1b,
            0x38, 0x35, 0x22, 0x2f, 0x64, 0x69, 0x7e, 0x73, 0x50, 0x5d, 0x4a, 0x47,
            0xdc, 0xd1, 0xc6, 0xcb, 0xe8, 0xe5, 0xf2, 0xff, 0xb4, 0xb9, 0xae, 0xa3,
            0x80, 0x8d, 0x9a, 0x97
        ],

        GEX: [
            0x00, 0x0e, 0x1c, 0x12, 0x38, 0x36, 0x24, 0x2a, 0x70, 0x7e, 0x6c, 0x62,
            0x48, 0x46, 0x54, 0x5a, 0xe0, 0xee, 0xfc, 0xf2, 0xd8, 0xd6, 0xc4, 0xca,
            0x90, 0x9e, 0x8c, 0x82, 0xa8, 0xa6, 0xb4, 0xba, 0xdb, 0xd5, 0xc7, 0xc9,
            0xe3, 0xed, 0xff, 0xf1, 0xab, 0xa5, 0xb7, 0xb9, 0x93, 0x9d, 0x8f, 0x81,
            0x3b, 0x35, 0x27, 0x29, 0x03, 0x0d, 0x1f, 0x11, 0x4b, 0x45, 0x57, 0x59,
            0x73, 0x7d, 0x6f, 0x61, 0xad, 0xa3, 0xb1, 0xbf, 0x95, 0x9b, 0x89, 0x87,
            0xdd, 0xd3, 0xc1, 0xcf, 0xe5, 0xeb, 0xf9, 0xf7, 0x4d, 0x43, 0x51, 0x5f,
            0x75, 0x7b, 0x69, 0x67, 0x3d, 0x33, 0x21, 0x2f, 0x05, 0x0b, 0x19, 0x17,
            0x76, 0x78, 0x6a, 0x64, 0x4e, 0x40, 0x52, 0x5c, 0x06, 0x08, 0x1a, 0x14,
            0x3e, 0x30, 0x22, 0x2c, 0x96, 0x98, 0x8a, 0x84, 0xae, 0xa0, 0xb2, 0xbc,
            0xe6, 0xe8, 0xfa, 0xf4, 0xde, 0xd0, 0xc2, 0xcc, 0x41, 0x4f, 0x5d, 0x53,
            0x79, 0x77, 0x65, 0x6b, 0x31, 0x3f, 0x2d, 0x23, 0x09, 0x07, 0x15, 0x1b,
            0xa1, 0xaf, 0xbd, 0xb3, 0x99, 0x97, 0x85, 0x8b, 0xd1, 0xdf, 0xcd, 0xc3,
            0xe9, 0xe7, 0xf5, 0xfb, 0x9a, 0x94, 0x86, 0x88, 0xa2, 0xac, 0xbe, 0xb0,
            0xea, 0xe4, 0xf6, 0xf8, 0xd2, 0xdc, 0xce, 0xc0, 0x7a, 0x74, 0x66, 0x68,
            0x42, 0x4c, 0x5e, 0x50, 0x0a, 0x04, 0x16, 0x18, 0x32, 0x3c, 0x2e, 0x20,
            0xec, 0xe2, 0xf0, 0xfe, 0xd4, 0xda, 0xc8, 0xc6, 0x9c, 0x92, 0x80, 0x8e,
            0xa4, 0xaa, 0xb8, 0xb6, 0x0c, 0x02, 0x10, 0x1e, 0x34, 0x3a, 0x28, 0x26,
            0x7c, 0x72, 0x60, 0x6e, 0x44, 0x4a, 0x58, 0x56, 0x37, 0x39, 0x2b, 0x25,
            0x0f, 0x01, 0x13, 0x1d, 0x47, 0x49, 0x5b, 0x55, 0x7f, 0x71, 0x63, 0x6d,
            0xd7, 0xd9, 0xcb, 0xc5, 0xef, 0xe1, 0xf3, 0xfd, 0xa7, 0xa9, 0xbb, 0xb5,
            0x9f, 0x91, 0x83, 0x8d
        ],

        // Key Schedule Core
        core:function(word,iteration) {
            'use strict';
            var i;
            /* rotate the 32-bit word 8 bits to the left */
            word = this.rotate(word);
            /* apply S-Box substitution on all 4 parts of the 32-bit word */
            for (i = 0; i < 4; ++i) {
                word[i] = this.sbox[word[i]];
            }
            /* XOR the output of the rcon operation with i to the first part (leftmost) only */
            word[0] = word[0] ^ this.Rcon[iteration];
            return word;
        },

        /* Rijndael's key expansion
         * expands an 128,192,256 key into an 176,208,240 bytes key
         *
         * expandedKey is a pointer to an char array of large enough size
         * key is a pointer to a non-expanded key
         */
        expandKey:function(key,size) {
            'use strict';
            var expandedKeySize = (16*(this.numberOfRounds(size)+1)),
            /* current expanded keySize, in bytes */
            currentSize = 0, rconIteration = 1,
            t = [],   // temporary 4-byte variable
            expandedKey = [],
            i, j, k;
            for(i = 0; i < expandedKeySize; i++) {
                expandedKey[i] = 0;
            }

            /* set the 16,24,32 bytes of the expanded key to the input key */
            for (j = 0; j < size; j++) {
                expandedKey[j] = key[j];
            }
            currentSize += size;

            while (currentSize < expandedKeySize) {
                /* assign the previous 4 bytes to the temporary value t */
                for (k = 0; k < 4; k++) {
                    t[k] = expandedKey[(currentSize - 4) + k];
                }

                /* every 16,24,32 bytes we apply the core schedule to t
                 * and increment rconIteration afterwards
                 */
                if(currentSize % size === 0) {
                    t = this.core(t, rconIteration++);
                }

                /* For 256-bit keys, we add an extra sbox to the calculation */
                if(size === this.keySize.SIZE_256 && ((currentSize % size) === 16)) {
                    for(k = 0; k < 4; k++) {
                        t[k] = this.sbox[t[k]];
                    }
                }

                /* We XOR t with the four-byte block 16,24,32 bytes before the new expanded key.
                 * This becomes the next four bytes in the expanded key.
                 */
                for(k = 0; k < 4; k++) {
                    expandedKey[currentSize] = expandedKey[currentSize - size] ^ t[k];
                    currentSize++;
                }
            }
            return expandedKey;
        },

        // Adds (XORs) the round key to the state
        addRoundKey:function(state,roundKey) {
            'use strict';
            var i;
            for (i = 0; i < 16; i++) {
                state[i] ^= roundKey[i];
            }
            return state;
        },

        // Creates a round key from the given expanded key and the
        // position within the expanded key.
        createRoundKey:function(expandedKey,roundKeyPointer) {
            'use strict';
            var roundKey = [], i, j;
            for (i = 0; i < 4; i++) {
                for (j = 0; j < 4; j++) {
                    roundKey[j*4+i] = expandedKey[roundKeyPointer + i*4 + j];
                }
            }
            return roundKey;
        },

        /* substitute all the values from the state with the value in the SBox
         * using the state value as index for the SBox
         */
        subBytes : function(state,isInv) {
            'use strict';
            var i;
            for (i = 0; i < 16; i++) {
                state[i] = isInv ? this.rsbox[state[i]] : this.sbox[state[i]];
            }
            return state;
        },

        /* iterate over the 4 rows and call shiftRow() with that row */
        shiftRows : function(state,isInv) {
            'use strict';
            var i;
            for (i = 0; i < 4; i++) {
                state = this.shiftRow(state,i*4, i,isInv);
            }
            return state;
        },

        /* each iteration shifts the row to the left by 1 */
        shiftRow:function(state,statePointer,nbr,isInv) {
            'use strict';
            var i, j, tmp;
            for (i = 0; i < nbr; i++) {
                if(isInv) {
                    tmp = state[statePointer + 3];
                    for (j = 3; j > 0; j--) {
                        state[statePointer + j] = state[statePointer + j-1];
                    }
                    state[statePointer] = tmp;
                }
                else {
                    tmp = state[statePointer];
                    for (j = 0; j < 3; j++)
                        state[statePointer + j] = state[statePointer + j+1];
                    state[statePointer + 3] = tmp;
                }
            }
            return state;
        },

        // galois multiplication of 8 bit characters a and b
        galois_multiplication:function(a,b) {
            'use strict';
            var p = 0, counter, hi_bit_set;
            for(counter = 0; counter < 8; counter++) {
                if((b & 1) == 1) {
                    p ^= a;
                }
                if(p > 0x100) p ^= 0x100;
                hi_bit_set = (a & 0x80); //keep p 8 bit
                a <<= 1;
                if(a > 0x100) a ^= 0x100; //keep a 8 bit
                if(hi_bit_set == 0x80)
                    a ^= 0x1b;
                if(a > 0x100) a ^= 0x100; //keep a 8 bit
                b >>= 1;
                if(b > 0x100) b ^= 0x100; //keep b 8 bit
            }
            return p;
        },

        // galois multipication of the 4x4 matrix
        mixColumns:function(state,isInv) {
            'use strict';
            var column = [], i, j;
            /* iterate over the 4 columns */
            for (i = 0; i < 4; i++) {
                /* construct one column by iterating over the 4 rows */
                for (j = 0; j < 4; j++) {
                    column[j] = state[(j*4)+i];
                }
                /* apply the mixColumn on one column */
                column = this.mixColumn(column,isInv);
                /* put the values back into the state */
                for (j = 0; j < 4; j++) {
                    state[(j*4)+i] = column[j];
                }
            }
            return state;
        },

        // galois multipication of 1 column of the 4x4 matrix
        mixColumn : function(column,isInv) {
            'use strict';
            var mult, cpy = [], i;
            mult = (isInv) ? [14,9,13,11] :  [2,1,1,3];

            for(i = 0; i < 4; i++) {
                cpy[i] = column[i];
            }
            column[0] =     this.galois_multiplication(cpy[0],mult[0]) ^
                this.galois_multiplication(cpy[3],mult[1]) ^
                this.galois_multiplication(cpy[2],mult[2]) ^
                this.galois_multiplication(cpy[1],mult[3]);
            column[1] =     this.galois_multiplication(cpy[1],mult[0]) ^
                this.galois_multiplication(cpy[0],mult[1]) ^
                this.galois_multiplication(cpy[3],mult[2]) ^
                this.galois_multiplication(cpy[2],mult[3]);
            column[2] =     this.galois_multiplication(cpy[2],mult[0]) ^
                this.galois_multiplication(cpy[1],mult[1]) ^
                this.galois_multiplication(cpy[0],mult[2]) ^
                this.galois_multiplication(cpy[3],mult[3]);
            column[3] =     this.galois_multiplication(cpy[3],mult[0]) ^
                this.galois_multiplication(cpy[2],mult[1]) ^
                this.galois_multiplication(cpy[1],mult[2]) ^
                this.galois_multiplication(cpy[0],mult[3]);
            return column;
        },

        // applies the 4 operations of the forward round in sequence
        round : function(state, roundKey) {
            'use strict';
            state = this.subBytes(state,false);
            state = this.shiftRows(state,false);
            state = this.mixColumns(state,false);
            state = this.addRoundKey(state, roundKey);
            return state;
        },

        // applies the 4 operations of the inverse round in sequence
        invRound : function(state,roundKey) {
            'use strict';
            state = this.shiftRows(state,true);
            state = this.subBytes(state,true);
            state = this.addRoundKey(state, roundKey);
            state = this.mixColumns(state,true);
            return state;
        },

        /*
         * Perform the initial operations, the standard round, and the final operations
         * of the forward aes, creating a round key for each round
         */
        main:function(state,expandedKey,nbrRounds) {
            'use strict';
            var i;
            state = this.addRoundKey(state, this.createRoundKey(expandedKey,0));
            for (i = 1; i < nbrRounds; i++) {
                state = this.round(state, this.createRoundKey(expandedKey,16*i));
            }
            state = this.subBytes(state,false);
            state = this.shiftRows(state,false);
            state = this.addRoundKey(state, this.createRoundKey(expandedKey,16*nbrRounds));
            return state;
        },

        /*
         * Perform the initial operations, the standard round, and the final operations
         * of the inverse aes, creating a round key for each round
         */
        invMain : function(state, expandedKey, nbrRounds) {
            'use strict';
            var i;
            state = this.addRoundKey(state, this.createRoundKey(expandedKey,16*nbrRounds));
            for (i = nbrRounds-1; i > 0; i--) {
                state = this.invRound(state, this.createRoundKey(expandedKey,16*i));
            }
            state = this.shiftRows(state,true);
            state = this.subBytes(state,true);
            state = this.addRoundKey(state, this.createRoundKey(expandedKey,0));
            return state;
        },

        numberOfRounds : function(size) {
            'use strict';
            var nbrRounds;
            switch (size) { /* set the number of rounds */
            case this.keySize.SIZE_128:
                nbrRounds = 10;
                break;
            case this.keySize.SIZE_192:
                nbrRounds = 12;
                break;
            case this.keySize.SIZE_256:
                nbrRounds = 14;
                break;
            default:
                return null;
            }
            return nbrRounds;
        },

        // encrypts a 128 bit input block against the given key of size specified
        encrypt : function(input,key,size) {
            'use strict';
            var output = [],
            block = [], /* the 128 bit block to encode */
            nbrRounds = this.numberOfRounds(size),
            expandedKey,
            i, j;
            /* Set the block values, for the block:
             * a0,0 a0,1 a0,2 a0,3
             * a1,0 a1,1 a1,2 a1,3
             * a2,0 a2,1 a2,2 a2,3
             * a3,0 a3,1 a3,2 a3,3
             * the mapping order is a0,0 a1,0 a2,0 a3,0 a0,1 a1,1 ... a2,3 a3,3
             */
            for (i = 0; i < 4; i++) {/* iterate over the columns */
                for (j = 0; j < 4; j++) {/* iterate over the rows */
                    block[(i+(j*4))] = input[(i*4)+j];
                }
            }

            /* expand the key into an 176, 208, 240 bytes key */
            expandedKey = this.expandKey(key, size); /* the expanded key */
            /* encrypt the block using the expandedKey */
            block = this.main(block, expandedKey, nbrRounds);
            for (i = 0; i < 4; i++) { /* unmap the block again into the output */
                for (j= 0; j < 4; j++) { /* iterate over the rows */
                    output[(i*4)+j] = block[(i+(j*4))];
                }
            }
            return output;
        },

        // decrypts a 128 bit input block against the given key of size specified
        decrypt : function(input, key, size) {
            'use strict';
            var output = [],
            block = [], /* the 128 bit block to decode */
            nbrRounds = this.numberOfRounds(size),
            expandedKey,
            i, j;
            /* Set the block values, for the block:
             * a0,0 a0,1 a0,2 a0,3
             * a1,0 a1,1 a1,2 a1,3
             * a2,0 a2,1 a2,2 a2,3
             * a3,0 a3,1 a3,2 a3,3
             * the mapping order is a0,0 a1,0 a2,0 a3,0 a0,1 a1,1 ... a2,3 a3,3
             */
            for (i = 0; i < 4; i++) { /* iterate over the columns */
                for (j = 0; j < 4; j++) { /* iterate over the rows */
                    block[(i+(j*4))] = input[(i*4)+j];
                }
            }
            /* expand the key into an 176, 208, 240 bytes key */
            expandedKey = this.expandKey(key, size);
            /* decrypt the block using the expandedKey */
            block = this.invMain(block, expandedKey, nbrRounds);
            for (i = 0; i < 4; i++) { /* unmap the block again into the output */
                for (j = 0; j < 4; j++) { /* iterate over the rows */
                    output[(i*4)+j] = block[(i+(j*4))];
                }
            }
            return output;
        }
    },
    /*
     * END AES SECTION
     */

    /*
     * START MODE OF OPERATION SECTION
     */
    //structure of supported modes of operation
    modeOfOperation:{
        OFB:0,
        CFB:1,
        CBC:2,
        ECB:3, // DPC
        CTR:4  // DPC
    },

    // gets a properly padded block
    getPaddedBlock : function(bytesIn,start,end,mode) {
        'use strict';
        var array, cpad;
        if(end - start > 16) {
            end = start + 16;
        }

        array = bytesIn.slice(start, end);

        if (mode === this.modeOfOperation.CBC) {
            // PKCS#7 padding
            cpad = 16 - array.length;
            while(array.length < 16) {
                array.push(cpad);
            }
        }

        return array;
    },

    getBlock: function(bytesIn,start,end,mode) {
        'use strict';
        if(end - start > 16) {
            end = start + 16;
        }
        return bytesIn.slice(start, end);
    },

    fillCounter : function(nonce, counter) {
        'use strict';
        var i = 4;
        do {
            counter[--i] = nonce & 0xFF;
            nonce >>= 8;
        } while ( i );
        return null;
    },

    /*
     * Mode of Operation Encryption
     * bytesIn - Input String as array of bytes
     * mode - mode of type modeOfOperation
     * key - a number array of length 'size'
     * size - the bit length of the key
     * iv - the 128 bit number array Initialization Vector
     */
    encrypt : function (bytesIn, mode, key, size, iv) {
        'use strict';
        var byteArray = [], input = [], output = [],
        ciphertext = [], cipherOut = [], nonce = 1,
        firstRound = true, padLength = 0,
        i, j, k, start, end;
        if(key.length%size) {
            throw 'Key length does not match specified size.';
        }
        if(iv.length%16 && (mode !== this.modeOfOperation.ECB)
           && (mode !== this.modeOfOperation.CTR)) {
            throw 'iv length must be 128 bits.';
        }

        if (mode === this.modeOfOperation.CBC) {
            padLength = 16 - (bytesIn.length % 16);
        }
        // the AES input/output
        if (bytesIn !== null) {
            for (j = 0; j < Math.ceil((bytesIn.length + padLength)/16); j++) {
                start = j*16;
                end = j*16+16;
                if(j*16+16 > bytesIn.length) {
                    end = bytesIn.length;
                }
                byteArray = this.getPaddedBlock(bytesIn,start,end,mode);
                if (mode === this.modeOfOperation.CFB) {
                    if (firstRound) {
                        output = this.aes.encrypt(iv, key, size);
                        firstRound = false;
                    }
                    else {
                        output = this.aes.encrypt(input, key, size);
                    }
                    for (i = 0; i < 16; i++) {
                        ciphertext[i] = byteArray[i] ^ output[i];
                    }
                    for(k = 0; k < end-start; k++) {
                        cipherOut.push(ciphertext[k]);
                    }
                    input = ciphertext;
                }
                else if (mode === this.modeOfOperation.OFB) {
                    if (firstRound) {
                        output = this.aes.encrypt(iv, key, size);
                        firstRound = false;
                    }
                    else {
                        output = this.aes.encrypt(input, key, size);
                    }
                    for (i = 0; i < 16; i++) {
                        ciphertext[i] = byteArray[i] ^ output[i];
                    }
                    for( k = 0;k < end-start;k++) {
                        cipherOut.push(ciphertext[k]);
                    }
                    input = output;
                }
                else if (mode === this.modeOfOperation.CBC) {
                    for (i = 0; i < 16; i++) {
                        input[i] = byteArray[i] ^ ((firstRound) ? iv[i] : ciphertext[i]);
                    }
                    firstRound = false;
                    ciphertext = this.aes.encrypt(input, key, size);
                    // always 16 bytes because of the padding added for CBC
                    for(k = 0; k < 16; k++) {
                        cipherOut.push(ciphertext[k]);
                    }
                }
                else if (mode === this.modeOfOperation.ECB) {
                    // DPC - Sun, 07 Aug 2011  13:04
                    // This ECB mode hasn't been tested.
                    input = byteArray;
                    ciphertext = this.aes.encrypt(input, key, size);
                    for(k = 0; k < end-start; k++) {
                        cipherOut.push(ciphertext[k]);
                    }
                }
                else if (mode === this.modeOfOperation.CTR) {
                    // DPC - Sun, 07 Aug 2011  13:04
                    // This CTR mode hasn't been tested.
                    if (firstRound) {
                        // set the CTR to zeros
                        for (i = 0; i < 16; i++) { input.push(0); }
                        firstRound = false;
                    }

                    fillCounter(nonce++, input);
                    output = this.aes.encrypt(input, key, size);

                    for (i = 0; i < 16; i++) {
                        ciphertext[i] = byteArray[i] ^ output[i];
                    }

                    for(k = 0; k < end-start; k++) {
                        cipherOut.push(ciphertext[k]);
                    }
                }
            }
        }
        return {mode:mode,originalsize:bytesIn.length,cipher:cipherOut};
    },

    /*
     * Mode of Operation Decryption
     * cipherIn - Encrypted String as array of bytes
     * mode - mode of type modeOfOperation
     * key - a number array of length 'size'
     * size - the bit length of the key
     * iv - the 128 bit number array Initialization Vector
     */
    decrypt : function(cipherIn,mode,key,size,iv) {
        'use strict';
        var ciphertext = [], input = [], output = [],
        byteArray = [], bytesOut = [], firstRound = true,
        i, j, k, start, end, N, numPadBytes = 0;

        if(key.length%size) {
            throw 'Key length does not match specified size.';
        }
        if(iv.length%16) {
            throw 'iv length must be 128 bits.';
        }
        // the AES input/output
        if (cipherIn !== null) {
            N = Math.ceil(cipherIn.length/16);
            for (j = 0; j < N; j++) {
                start = j*16;
                end = j*16+16;
                if(j*16+16 > cipherIn.length) {
                    end = cipherIn.length;
                }
                ciphertext = this.getBlock(cipherIn,start,end);
                if (mode === this.modeOfOperation.CFB) {
                    if (firstRound) {
                        output = this.aes.encrypt(iv, key, size);
                        firstRound = false;
                    }
                    else {
                        output = this.aes.encrypt(input, key, size);
                    }
                    for (i = 0; i < 16; i++) {
                        byteArray[i] = output[i] ^ ciphertext[i];
                    }
                    for(k = 0; k < end-start; k++) {
                        bytesOut.push(byteArray[k]);
                    }
                    input = ciphertext;
                }
                else if (mode === this.modeOfOperation.OFB) {
                    if (firstRound) {
                        output = this.aes.encrypt(iv, key, size);
                        firstRound = false;
                    }
                    else {
                        output = this.aes.encrypt(input, key, size);
                    }
                    for (i = 0; i < 16; i++) {
                        byteArray[i] = output[i] ^ ciphertext[i];
                    }
                    for(k = 0; k < end-start; k++) {
                        bytesOut.push(byteArray[k]);
                    }
                    input = output;
                }
                else if(mode === this.modeOfOperation.CBC) {
                    output = this.aes.decrypt(ciphertext, key, size);
                    for (i = 0; i < 16; i++) {
                        byteArray[i] = ((firstRound) ? iv[i] : input[i]) ^ output[i];
                    }
                    firstRound = false;

                    if (j+1 === N) { // is last block?
                        // do not copy through the PKCS#7 padding
                        numPadBytes = byteArray[15];
                    }
                    for (k = 0; k < end-start-numPadBytes; k++) {
                        bytesOut.push(byteArray[k]);
                    }
                    input = ciphertext;
                }
                else if (mode === this.modeOfOperation.ECB) {
                    // DPC - Sun, 07 Aug 2011  13:04
                    // This ECB mode hasn't been tested.
                    output = this.aes.decrypt(ciphertext, key, size);
                    for(k = 0; k < end-start; k++) {
                        bytesOut.push(output[k]);
                    }
                }
                else {
                    throw "Unsupported mode.";
                }
            }
        }
        return bytesOut;
    }
    /*
     * END MODE OF OPERATION SECTION
     */
};
/**
 * Author: TienNT
 * Description: Cung cấp các API xử lý các sự kiện của các đối tượng LH-built layout widget
 * Date: 10/07/2011
 * FWVersion: 3.3
 **/

sd.layout = {
    /*
     * DungDV add for find all DataGrid in a domNode
     **/
    findGridInside : function(/*DomNode*/ root){
        // summary:
        //		Search subtree under root returning widgets found.
        //		Doesn't search for nested widgets (ie, widgets inside other widgets).
        var outAry = [];
        function getChildrenHelper(root){
            for(var node = root.firstChild; node; node = node.nextSibling){
                if(node.nodeType == 1){
                    var widgetId = node.getAttribute("widgetId");
                    if(widgetId ){
                        var widget = {};
                        if (widgetId){
                            widget = dijit.byId(widgetId);
                            if (widget){
                                if (widget.declaredClass == "vt.dataGrid.vtDataGrid"){
                                    outAry.push(widget);
                                }
                            }
                        }
                    }else{
                        getChildrenHelper(node);
                    }
                }
            }
        }
        getChildrenHelper(root);
        return outAry;
    },
    // [ FieldSet
    toggleDisplayFS : function( iconSpan ) {
        var fs = iconSpan.parentNode.parentNode;// fs = fieldset
        var tbl = fs.getElementsByTagName( 'table' )[0];

        if( tbl.style.display == 'none' ) {
            tbl.style.display = '';
            iconSpan.style.backgroundPosition = "-15px -60px";
            // [ DungDV for fix bug: DataGrid in hidden div
            var gridAry = this.findGridInside(fs);
            for (var i = 0; i <gridAry.length; i++){
                gridAry[i].grid.renderNoReload();
            }
            // ]
        } else {
            tbl.style.display = 'none';
            iconSpan.style.backgroundPosition = "-15px -75px";
        }
    },

    displayFS : function() {
        var id, state;

        id = arguments[0];

        var fs = document.getElementById( id );
        var iconSpan = fs.getElementsByTagName( 'span' )[0];
        var tbl = fs.getElementsByTagName( 'table' )[0];

        switch( arguments.length ) {
            case 1:
                return ( tbl.style.display == 'none' ) ? false : true;
                break;
            case 2:
                state = arguments[1];

                if( state ) {
                    tbl.style.display = '';
                    iconSpan.style.backgroundPosition = "-15px -60px";
                } else {
                    tbl.style.display = 'none';
                    iconSpan.style.backgroundPosition = "-15px -75px";
                }

                return state;
                break;
        }
    },

    titleFS : function() {
        var dlg = document.getElementById( arguments[0] );
        var spanTitle = sd.util.getElementsByClass( dlg, 'titleFS' )[0];
        var title;

        switch( arguments.length ) {
            case 1:
                break;
            case 2:
                spanTitle.innerHTML = arguments[1];
                break;
        }

        title = spanTitle.innerHTML;
        return title;
    }
    // ] FieldSet

    
};
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
/**
 * Author: TienNT
 * Description: Cung cấp các API dialog-notification
 * Date: 10/07/2011
 * FWVersion: 3.3
 **/

MasterTooltip = function(){
    sd.$("message-holder").innerHTML = this.templateString;
    this.domNode = sd.$("message-holder").childNodes[0];
    this.fadeIn = dojo.fadeIn({
        node: this.domNode,
        duration: this.duration,
        onEnd: dojo.hitch(this, "_onShow")
    });
    this.fadeOut = dojo.fadeOut({
        node: this.domNode,
        duration: this.duration,
        onEnd: dojo.hitch(this, "_onHide")
    });
};

MasterTooltip.prototype.templateString = "" +
"<div class='vtTooltip x-region' id='vtTooltip' style=\"background:#e5e5e5;\">"+
    "<div class='dijitTooltipContainer'>"+
        "<table cellspacing='0'>"+
            "<tr>"+
                "<td class='img'>"+
                    "<img id='informer-icon' class='informer-icon ' src='share/images/icons/s.gif'>"+
                "</td>"+
                "<td>"+
                    "<div id='containerNode' class='vtTooltipContents' waiRole='alert' style='margin-left:5px;'></div>"+
                "</td>"+
            "</tr>"+
        "</table>"+
    "</div>"+
"</div>";

MasterTooltip.prototype.duration = 400;
MasterTooltip.prototype.type = "info";
MasterTooltip.prototype.messageContent = "this is content";
MasterTooltip.prototype.domNode = null;
MasterTooltip.prototype._onHide = function(){
    // summary:
    //		Called at end of fade-out operation
    // tags:
    //		protected

    //    this.domNode.style.cssText="";
    //    if(this._onDeck){
    //        // a show request has been queued up; do it now
    //        this.show.apply(this, this._onDeck);
    //        this._onDeck=null;
    //    }
    };
MasterTooltip.prototype._onShow = function(){
    // summary:
    //		Called at end of fade-in operation
    // tags:
    //		protected
    if(dojo.isIE){
        // the arrow won't show up on a node w/an opacity filter
        this.domNode.style.filter="";
    }
};

MasterTooltip.prototype.constructor = function(/*String*/type, /*String */ messageContent){
    this.type = type;
    this.messageContent = messageContent;
};

MasterTooltip.prototype.show = function(/*{type:'', content:''}*/messageObject){
    if(this.fadeOut.status() == "playing"){
        // previous tooltip is being hidden; wait until the hide completes then show new one
        this._onDeck=arguments;
        return;
    }
    sd.$("containerNode").innerHTML = messageObject.content;
    sd.$("informer-icon").className = "informer-icon informer-icon-" + messageObject.type;

    var scrollXY = sd.util.getScrollXY();
    var pageW = ( window.innerWidth ) ? window.innerWidth : ( document.documentElement.offsetWidth );

    var initX, initY;

    initX = scrollXY[0] + Math.round( ( pageW - this.domNode.offsetWidth ) / 2 );
    initY = scrollXY[1]; // by TienNT

    this.domNode.style.zIndex = sd.util.getMaxZIndex() + 1; // by TienNT
    this.domNode.style.top = (this.domNode.offsetTop + 1) + "px";

    this.domNode.style.top = initY; // by TienNT
    this.domNode.style.left = initX;

    // show it
    dojo.style(this.domNode, "opacity", 0);
    this.fadeIn.play();
    this.isShowingNow = true;
};

MasterTooltip.prototype.hide = function(){
    // this hide request is for the currently displayed tooltip
    this.fadeIn.stop();
    this.isShowingNow = false;
    this.fadeOut.play();
};


sd.showAlerter = function(/*{type:'', content:''}*/messageObject){
    // summary:
    //		Display tooltip w/specified contents in specified position.
    //		See description of dijit.Tooltip.defaultPosition for details on position parameter.
    //		If position is not specified then dijit.Tooltip.defaultPosition is used.
    if(!sd._masterAleter){
        sd._masterAleter = new MasterTooltip();
    }
    return sd._masterAleter.show(/*{type:'', content:''}*/messageObject);
};

sd.hideAlerter = function(){
    // summary:
    //		Hide the tooltip
    if(!sd._masterAleter){
        sd._masterAleter = new MasterTooltip();
    }
    return sd._masterAleter.hide();
};


Alerter = function(messageObject) {
    this.messageObject = messageObject;
    if (messageObject.showDuration){
        this.hideDelay = messageObject.showDuration;
    }
};

Alerter.prototype.messageObject = "";
Alerter.prototype.hideDelay = 3000;
Alerter._hideTimer = null;


Alerter.prototype.show = function(){

    if (Alerter._hideTimer){
        clearTimeout(Alerter._hideTimer);
        delete Alerter._hideTimer;
    }
    this._open();
    this.hide();

};

Alerter.prototype.hide = function(){

    if (!Alerter._hideTimer){
        Alerter._hideTimer = setTimeout(dojo.hitch(this, function(){
            this._close()
        }), this.hideDelay);
    }
};

Alerter.prototype._open = function(){

    sd.showAlerter(this.messageObject);

    if(Alerter._hideTimer){
        clearTimeout(Alerter._hideTimer);
        delete Alerter._hideTimer;
    }
};

Alerter.prototype._close=function(){

    if(Alerter._hideTimer){
        clearTimeout(Alerter._hideTimer);
        delete Alerter._hideTimer;
    }
    sd.hideAlerter();
};
/**
 * Author: TienNT
 * Description: Cung cấp các API xử lý các flow/object core của client-side RDWF
 * Date: 10/07/2011
 * FWVersion: 3.3
 **/

sd.operator = {

    path : {
        context : "",
        externalResources : "",
        PlugInFolder : "",
        ImgFolder : "",
        CSSFolder : "",
        JSVTFolder : "",
        JSLibFolder : ""
    },

    loginURL : "",
    logoutURL : "",

    menuModel : [],

    timeout : null,
    debugMode : true,
    locale : "",

    menuTargetId : "",
    menuTarget : null,
    waitScreenDivId : "",
    waitScreenDiv : null,
    waitScreenInterval : null,
    messageScreenInterval : null,

    breadCumbDivId : "",
    breadCumbDiv : null,
    errorDisplayDialogId : "",
    errorDisplayDialog : null,
    errorDisplayIframeId : "",
    errorDisplayIframe : null,
    errorDisplayDivId : "",
    errorDisplayDiv : null,

    allowedToExecJS : true,
    wildWidgetArray : [],
    parseArray : [],
    
    xhrCounter : 0,
    popupZIndex : 0,
    isWaitScreenShown : false,

    checkHTTPStatus : function(/*d.ioArgs*/ ioArgs, /*Var*/ sth) {
        try {
            var xhr = ioArgs.xhr;
            var status = xhr.status
            var continueToGo = true;

            var url;
            var ifr, div, dlg;
            
            if(status == 200) {
                if(xhr.getResponseHeader("VSA-Flag") == "NewPageRedirect") {
                    url = xhr.getResponseHeader("VSA-Location");
                    window.open(url, "_blank", "width=800, height=600");
                }
                else if(xhr.getResponseHeader("VSA-Flag") == "InPageRedirect") {
                    url = xhr.getResponseHeader("VSA-Location");
                    document.location.href = url;
                }
                else if(xhr.getResponseHeader("RDWF-Flag") == "ActionNotFound") {
                    ifr = this.getErrorDisplayIframe();
                    div = this.getErrorDisplayDiv();
                    dlg = this.getErrorDisplayDialog();
                    url = ioArgs.url;

                    ifr.style.display = "none";
                    div.innerHTML = "Error occurs when " + url + " is requested<br/>" + xhr.responseText;
                    dlg.domNode.style.width = "auto";
                    dlg.domNode.style.height = "auto";
                    dlg.show();
                    dlg.vt_disableSwitchSizeFeature();
                }
            }
            else if(status == 0) {
                ifr = this.getErrorDisplayIframe();
                div = this.getErrorDisplayDiv();
                dlg = this.getErrorDisplayDialog();

                ifr.style.display = "none";
                div.innerHTML = sth.message;
                dlg.domNode.style.width = "auto";
                dlg.domNode.style.height = "auto";
                dlg.show();
                dlg.vt_disableSwitchSizeFeature();
            }
            else {
                if(sth.name && sth.name == "Error") {
                    this.displayErrorWhileSendingRequest(sth);
                }
            }

            return continueToGo;
        } catch(e) {
            alert("JSException in sd.operator.checkHTTPStatus: \n" + e.message);
            return true;
        }
    },

    execMenu : function(/*String*/ url, /*String*/ menuItemKey) {
        try {
            g_latestClickedMenu = url;
            
            var _this = this;
            var appState = new ApplicationState(url, this.getMenuTargetId());
            appState.preGo = function() {
                var menuTarget = _this.getMenuTarget();

                _this.freePageContext();
                sd.util.stopKeepAlive();

                if(menuTarget.onkeyup) {
                    try {
                        delete menuTarget.onkeyup;
                    } catch(e) {
                        menuTarget.onkeyup = undefined;
                    }
                }
            };
            appState.postGo = function() {
            
                _this.updateBreadCumb(menuItemKey);
            
            }
            appState.go();
            dojo.back.addToHistory(appState);            
        } catch(e) {
            alert("vt-operator.js, execMenu:\n" + e.message);
            throw e;
        }
    },

    // Clear members inside sd.app object
    freeAppContext : function() {
        var appProp;

        for( appProp in sd.app ) {
            try {
                delete sd.app[appProp];
            } catch( e ) {
                sd.app[appProp] = undefined;
            }
        }
    },

    // Clear members inside sd.page object
    freePageContext : function() {
        var pageProp;

        for( pageProp in sd.page ) {
            try {
                delete sd.page[pageProp];
            } catch( e ) {
                sd.page[pageProp] = undefined;
            }
        }
    },

    collectWildWidget : function( /*Dijit*/ wid ) {
        sd.operator.wildWidgetArray.push( wid );
    },

    freeWidgets : function( /*DOM node*/ root ) {
        var i, widgets = [];
        this.freeWidgetsRecursive(root, widgets);
        
        for(i = 0; i < widgets.length; i++) {
            widgets[i].destroyRecursive();
        }
    },

    freeWidgetsRecursive : function( /*DOM node*/ root, /*&Array*/ outArr ) {
        var widget, widgetId, dialogWidgetId, editorWidgetId;
        
        for(var node = root.firstChild; node; node = node.nextSibling){
            if(node.nodeType == 1){ // Node is Element
                widgetId = node.getAttribute("widgetId");
                dialogWidgetId = node.getAttribute("dialogWidgetId");
                editorWidgetId = node.getAttribute("editorWidgetId");
                
                if(widgetId || dialogWidgetId || editorWidgetId) {
                    widget = null;
                    if(widgetId) {
                        widget = dijit.byId(widgetId);
                        if(widget){
                            if(widget._close) {
                                widget._close();
                            }
                            outArr.push(widget);
                        //widget.destroyRecursive();
                        }
                    }
                    else if(dialogWidgetId) {
                        //console.log("destroy widget in the fly : " + dialogWidgetId);
                        widget = dijit.byId(dialogWidgetId);
                        if(widget) {
                            widget.hide();
                            outArr.push(widget);
                        //widget.destroyRecursive();
                        }
                    }
                    else if(editorWidgetId) {
                        sd.widget.delCKE(editorWidgetId, true);
                    }
                }else {
                    this.freeWidgets(node);
                }
            }
        }
    },

    parse : function( s ) {
        var match = [];
        var tmp = [];
        var scripts = [];
        while(match){
            match = s.match(/<script([^>]*)>([\s\S]*?)<\/script>/i);
            if(!match){
                break;
            }
            if(match[1]){
                sd.operator.parseArray = match[1].match(/src=(['"]?)([^"']*)\1/i);
                if( sd.operator.parseArray ){
                    // remove a dojo.js or dojo.js.uncompressed.js from remoteScripts
                    // we declare all files with dojo.js as bad, regardless of folder
                    var tmp2 = sd.operator.parseArray[2].search(/.*(\bdojo\b(?:\.uncompressed)?\.js)$/);
                    if(tmp2 > -1){
                //this.log("Security note! inhibit:"+attr[2]+" from  beeing loaded again.");
                }
                }
                // [ Datbt
                // bo qua nhung doan script co type = dojo/method hoac dojo/connect
                sd.operator.parseArray = match[1].match(/type=(['"]dojo\/method)(["'])/);
                if ( sd.operator.parseArray ){
                    match[2]="";
                }
                sd.operator.parseArray = match[1].match(/type=(['"]dojo\/connect)(["'])/);
                if ( sd.operator.parseArray ){
                    match[2]="";
                }
            // ] Datbt
            }
            if(match[2]){
                // strip out all djConfig variables from script tags nodeValue
                // this is ABSOLUTLY needed as reinitialize djConfig after dojo is initialised
                // makes a dissaster greater than Titanic, update remove writeIncludes() to
                var sc = match[2].replace(/(?:var )?\bdjConfig\b(?:[\s]*=[\s]*\{[^}]+\}|\.[\w]*[\s]*=[\s]*[^;\n]*)?;?|dojo\.hostenv\.writeIncludes\(\s*\);?/g, "");
                if(!sc){
                    continue;
                }

                // cut out all dojo.require (...) calls, if we have execute
                // scripts false widgets dont get there require calls
                // does suck out possible widgetpackage registration as well
                //[Datbt không bỏ những script như require...
                //                    tmp = [];
                //                    while(tmp){
                //                        tmp = sc.match(/dojo\.(?:(?:require(?:After)?(?:If)?)|(?:widget\.(?:manager\.)?registerWidgetPackage)|(?:(?:hostenv\.)?setModulePrefix))\((['"]).*?\1\)\s*;?/);
                //                        if(!tmp){
                //                            break;
                //                        }
                //                        sc = sc.replace(tmp[0], "");
                //                    }
                //]Datbt không bỏ những script như require...
                scripts.push(sc);
            }
            s = s.replace(/<script[^>]*>[\s\S]*?<\/script>/i, "");
        }

        return {
            text: s,
            scripts: scripts
        };
    },

    execScript : function( scripts ) {
        eval(scripts.join(""));
    },

    updateBreadCumb : function(/*String*/ menuItemKey) {
        try {
            var joiner = "&nbsp;/&nbsp;";
            var arr;
            var breadCumbStr;
            var breadCumbDiv = this.getBreadCumbDiv();

            if(!(menuItemKey && menuItemKey.length > 0)) {
                return;
            }

            if(menuItemKey == "-1") {
                breadCumbStr = "Root";
            } else {
                arr = this.getBreadCumb(menuItemKey);
                breadCumbStr = arr.join(joiner);
            }
            
            breadCumbDiv.innerHTML = breadCumbStr;
        } catch(e) {
            alert("vt-operator.js, updateBreadCump:\n" + e.message);
        }
    },

    /**
     * Author: TienNT
     * Description: Bo sung phuong thuc lay BreadCump
     * Date: 09/06/2011
     * FWVersion: 3.3
     **/
    getBreadCumb : function(/*String*/ menuItemKey) {
        var menuModel;
        var objLev = {
            level : 1
        };
        var outArr = [];

        if(useMenuTest) {
            menuModel = myMenu;
        } else {
            menuModel = this.menuModel;
        }

        this.getBreadCumbRecursive(menuItemKey, menuModel, objLev, outArr);
        return outArr;
    },

    /**
     * Author: TienNT
     * Description: Goi de qui trong getBreadCump
     * Date: 09/06/2011
     * FWVersion: 3.3
     **/
    getBreadCumbRecursive : function(/*String*/ menuItemKey, /*Array*/ menuModel, /*&Object level*/ objLev, /*&Array*/ outArr) {
        try{
            var keyPosition = 4;
            var labelPosition = 1;
            var onClickPosition = 2;
            var childPosition = 5;
            var keySegmentSpliter = ".";
            var nextMenuModel;
            var menuItem, i;
            var keySegment, targetKey;
            var menuItemContent, menuItemLabel, menuItemOnClick;
            
            keySegment = menuItemKey.split(keySegmentSpliter);
            targetKey = (keySegment.splice(0, objLev.level)).join(keySegmentSpliter);
            
            for(i = 0; i < menuModel.length; i++) {
                menuItem = menuModel[i];
                if(menuItem) {
                    if(menuItem[keyPosition] == targetKey) {
                        menuItemOnClick = (menuItem[onClickPosition] && menuItem[onClickPosition].length > 0) ? menuItem[onClickPosition] : "";
                        menuItemLabel = menuItem[labelPosition].replace(/^(\&nbsp\;)+|(\&nbsp\;)+$/g, "");
                        
                        if(menuItemOnClick.length > 0) {
                            menuItemContent = "<a href='" + menuItemOnClick + "'>" + menuItemLabel + "</a>";
                        } else {
                            menuItemContent = menuItemLabel;
                        }

                        outArr.push(menuItemContent);
                        if(menuItem[childPosition]) {
                            objLev.level++;
                            nextMenuModel = menuItem.slice(childPosition, menuItem.length);
                            this.getBreadCumbRecursive(menuItemKey, nextMenuModel, objLev, outArr);
                        }
                        break;
                    }
                }
            }
        } catch(e) {
            alert('vt-operator.js, getBreadCumbRecursive:\n' + e.message);
        }
    },

    /**
     * Author: TienNT
     * Description: Hien thi thong bao loi tu server
     * Date: 18/06/2011
     * FWVersion: 3.3
     **/
    displayErrorWhileSendingRequest : function(/*Dojo.Error*/ err) {
        try{
            if(this.getDebugMode() == true) {
                var ifr, dlg, div, ifrDoc;
                
                dlg = this.getErrorDisplayDialog();
                ifr = this.getErrorDisplayIframe();
                div = this.getErrorDisplayDiv();

                ifrDoc = ifr.contentWindow.document;

                div.innerHTML = err.message;

                //[ Show entire error content
                ifrDoc.close(); //TienNT says: clear existed content
                ifrDoc.write(err.responseText);
                //] Show entire error content

                dlg.show();
                dlg.vt_gotoFullViewableScreenSizeMode();

                var adjustWSize = 25, adjustHSize = 65;
                var ifrW = dlg.domNode.offsetWidth - adjustWSize;
                var ifrH = dlg.domNode.offsetHeight - adjustHSize;
                ifr.style.display = "block";
                ifr.style.width = ifrW;
                ifr.style.height = ifrH;
            }
        } catch(e) {
            console.log("sd.operator.displayErrorWhileSendingRequest says: \n\t" + e.message);
        }
    },

    displayWaitScreen : function() {
        var waitScreenDiv = this.getWaitScreenDiv();
//        console.log(waitScreenDiv);
        if(!waitScreenDiv) {
//            console.log("sd.operator.displayWaitScreen says: \n\twaitScreenDiv is not found.");
            return;
        };
      
        // Show wait screen
        if( this.getXHRCounter() > 0 ) {                      
            if( this.isWaitScreenShown === false ) {
                var scrollXY = sd.util.getScrollXY();
                var pageW = ( window.innerWidth ) ? window.innerWidth : ( document.documentElement.offsetWidth );
                var pageH = ( window.innerHeight ) ? window.innerHeight : ( document.documentElement.offsetHeight );
                var initX, initY;

                initX = scrollXY[0] + Math.round( ( pageW - 321 ) / 2 );
                initY = scrollXY[1] + Math.round( ( pageH - 55 ) / 3.5 );
            
                waitScreenDiv.style.left = initX;
                waitScreenDiv.style.top = initY;
            
                //waitScreenDiv.style.zIndex = sd.util.getMaxZIndex();
                this.isWaitScreenShown = true;
            }
            waitScreenDiv.style.zIndex = this.popupZIndex + 1;
        }
        // Hide wait screen
        else if( this.getXHRCounter() < 1 ) {
            waitScreenDiv.style.left = -321;
            waitScreenDiv.style.top = -55;
            this.isWaitScreenShown = false;
        }
    },
    
    getXHRCounter : function() {
        return this.xhrCounter;
    },
    
    increaseXHRCounter : function() {
        this.xhrCounter++;
        this.displayWaitScreen();
    },
    
    decreaseXHRCounter : function() {
        this.xhrCounter--;
        this.displayWaitScreen();
    },
    
    getMenuTarget : function() {
        this.menuTarget = sd.$(this.getMenuTargetId());
        return this.menuTarget;
    },

    getWaitScreenDiv : function() {
        if(!this.waitScreenDiv) {
            this.waitScreenDiv = sd.$(this.waitScreenDivId);
        }
        return this.waitScreenDiv;
    },

    getBreadCumbDiv : function() {
        if(!this.breadCumbDiv) {
            this.breadCumbDiv = sd.$(this.breadCumbDivId);
        }
        return this.breadCumbDiv;
    },

    getErrorDisplayDialog : function() {
        if(!this.errorDisplayDialog) {
            this.errorDisplayDialog = sd._(this.errorDisplayDialogId);
        }
        return this.errorDisplayDialog;
    },

    getErrorDisplayIframe : function() {
        if(!this.errorDisplayIframe) {
            this.errorDisplayIframe = sd.$(this.errorDisplayIframeId);
        }
        return this.errorDisplayIframe;
    },

    getErrorDisplayDiv : function() {
        if(!this.errorDisplayDiv) {
            this.errorDisplayDiv = sd.$(this.errorDisplayDivId);
        }
        return this.errorDisplayDiv;
    },

    /**
     * Author: TienNT
     * Description: Lay ve id vung duoc cap nhat tu menu
     * Date: 13/06/2011
     * FWVersion: 3.3
     **/
    getMenuTargetId : function() {
        return this.menuTargetId;
    },

    /**
     * Author: TienNT
     * Description: Thiet lap id vung duoc cap nhat tu menu
     * Date: 13/06/2011
     * FWVersion: 3.3
     **/
    setMenuTargetId : function(/*String*/ areaId) {
        this.menuTargetId = areaId;
    },

    getDebugMode : function() {
        return this.debugMode;
    },

    setDebugMode : function(/*Boolean*/ isBeingInDebugMode) {
        this.debugMode = isBeingInDebugMode;
    },

    getTimeout : function() {
        return this.timeout;
    },

    setTimeout : function(/*Number-In-Secs*/ timeoutNum) {
        if(timeoutNum != undefined && timeoutNum != null) {
            this.timeout = timeoutNum * 1000;
        }
    },

    getLocale : function() {
        return this.locale;
    },

    setLocale : function(/*String*/ locale) {
        if(sd.util.isValidS(locale)) {
            var arr = locale.split("_");
            this.locale = arr[0];
        }
    },

    /****************************************************************/

    //[ TienNT says: not in used anymore @25may11
    getMessageObject : function(/*String*/ serverResponseText){
        var data = serverResponseText
        if(data.indexOf("/* {") == 0) {
            return eval("( " + data.substring(2, data.length - 2) + " )");
        } else {
            return null;
        }
    },

    displayServerMessage : function (state,/*jsonObject*/ message){
        var messageHolderDiv = sd.$( "message-holder" );
        var messageTextDiv = sd.$( "message-content" );
        var speed = 15;

        messageHolderDiv.style.zIndex = sd.util.getMaxZIndex();
        if (message){
            messageTextDiv.innerHTML = message.content;
        }
        // Show wait screen
        if( state ) {
            //if( sd.util.getOpacity( waitScreenDiv ) <= 0 ) {
            var scrollXY = sd.util.getScrollXY();
            var pageW = ( window.innerWidth ) ? window.innerWidth : ( document.documentElement.offsetWidth );
            var initX;

            initX = scrollXY[0] + Math.round( ( pageW) / 2 - messageHolderDiv.offsetWidth/2 );


            sd.util.setOpacity( messageHolderDiv, 25 );

            messageHolderDiv.style.left = initX;
            messageHolderDiv.style.top = -3;

            if( sd.operator.messageScreenInterval ) {
                clearInterval( sd.operator.waitScreenInterval );
            }

            sd.operator.messageScreenInterval = setInterval( "sd.operator.__showMessageAnimation();", speed );
        //}
        }
        // Hide wait screen
        else {
            //if( sd.util.getOpacity( waitScreenDiv ) >= 100 ) {
            if( sd.operator.messageScreenInterval ) {
                clearInterval( sd.operator.messageScreenInterval );
            }

            sd.operator.messageScreenInterval = setInterval( "sd.operator.__hideMessageAnimation();", speed );
        //}
        }
    },

    // Datbt 08/12/09 overwrite dijit.findWidgets, tim them dialogWidgetId
    findWidgets : function(/*DomNode*/ root){
        // summary:
        //		Search subtree under root returning widgets found.
        //		Doesn't search for nested widgets (ie, widgets inside other widgets).

        var outAry = [];
        function getChildrenHelper(root){
            for(var node = root.firstChild; node; node = node.nextSibling){
                if(node.nodeType == 1){
                    var widgetId = node.getAttribute("widgetId");
                    var dialogWidgetId = node.getAttribute("dialogWidgetId");
                    if(widgetId || dialogWidgetId){
                        var widget = {};
                        if (widgetId){
                            widget = dijit.byId(widgetId);
                            if (widget){
                                if (widget.declearedClass == "vt.dataPicker.dataPicker"){
                                    widget._close();
                                }
                            }
                            outAry.push(widget);
                        } else if (dialogWidgetId){
                            widget = dijit.byId(dialogWidgetId);
                            outAry.push(widget);
                        }
                    }else{
                        getChildrenHelper(node);
                    }
                }
            }
        }

        getChildrenHelper(root);
        return outAry;
    },

    // ThienKQ - find the target id to return response
    findTarget : function(response){
        var target = [];
        var divId = "";

        target = response.match(/targetDivInBodyLayout=['"]([\s\S]*?)["']/);

        if(target){
            divId = target[0].split("=")[1];
            divId = divId.substring(1, divId.length - 1);
        }

        return divId;
    },

    findReturnType : function(response){
        var type = [];
        var strType = "";

        type = response.match(/returnPageType=['"]([\s\S]*?)["']/);

        if(type){
            strType = type[0].split("=")[1];
            strType = strType.substring(1, strType.length - 1);
        }

        return strType;
    },

    showRelevantArea : function(area, returnType){

        if( area == "bodyContent"){
            //show main page with left, right pane
            dojo.style(dojo.byId("componentBody"), {
                display:"none"
            });
            dojo.style(dojo.byId("mainBody"), {
                display:""
            });

            // return page without leftpane or rightpane of fullLayout
            var leftNode = dijit.byId("mainExpandoLeft");
            var rightNode = dijit.byId("mainExpandoRight");

            if(leftNode != null && rightNode != null){
                if( returnType == "noLeft" ){
                    dojo.style(dojo.byId("mainExpandoLeft"), {
                        display:"none"
                    });
                }
                else if( returnType == "noRight" ){

                    dojo.style(dojo.byId("mainExpandoRight"), {
                        display:"none"
                    });

                }else{

                    dojo.style(leftNode.domNode, {
                        display:""
                    });

                    dojo.style(rightNode.domNode, {
                        display:""
                    });
                }

                dijit.byId("mainBorderContainer").resize();
            }

        }else{
            // return page without both left and right pane
            dojo.style(dojo.byId("mainBody"), {
                display:"none"
            });
            dojo.style(dojo.byId("componentBody"), {
                display:""
            });
        }
    },

    // ThienKQ@23June2010
    findSelectBoxWidget : function(/*DomNode*/ root){
        // summary:
        //		Search subtree under root returning widgets found.
        //		Doesn't search for nested widgets (ie, widgets inside other widgets).
        var outAry = [];
        function getChildrenHelper(root){
            for(var node = root.firstChild; node; node = node.nextSibling){
                if(node.nodeType == 1){
                    var widgetId = node.getAttribute("widgetId");
                    if(widgetId ){
                        var widget = {};
                        if (widgetId){
                            widget = dijit.byId(widgetId);
                            if (widget){
                                if (widget.declaredClass == "vt.simpleSelectBox.vtSimpleSelectBox" || widget.declaredClass ==  "vt.selectBox.vtFilteringSelect"){
                                    outAry.push(widget);
                                }
                            }
                        }
                    }else{
                        getChildrenHelper(node);
                    }
                }
            }
        }

        getChildrenHelper(root);
        return outAry;
    },

    __hideWaitScreenAnimation : function() {
        var waitScreenDiv = sd.$( "vt-loading-background" );
        waitScreenDiv.style.top = parseInt( waitScreenDiv.style.top ) + 1;
        sd.util.setOpacity( waitScreenDiv, sd.util.getOpacity( waitScreenDiv ) - 3 );

        if( sd.util.getOpacity( waitScreenDiv ) <= 30 ) {
            clearInterval( sd.operator.waitScreenInterval );
            sd.operator.waitScreenInterval = null;

            waitScreenDiv.style.left = -321;
            waitScreenDiv.style.top = -55;
        }
    },

    __showWaitScreenAnimation : function() {
        var waitScreenDiv = sd.$( "vt-loading-background" );
        waitScreenDiv.style.top = parseInt( waitScreenDiv.style.top ) - 1;
        sd.util.setOpacity( waitScreenDiv, sd.util.getOpacity( waitScreenDiv ) + 3 );

        if( sd.util.getOpacity( waitScreenDiv ) >= 100 ) {
            clearInterval( sd.operator.waitScreenInterval );
            sd.operator.waitScreenInterval = null;
        }
    },

    __showMessageAnimation : function() {
        var messageHolderDiv = sd.$( "message-holder" );
        if (parseInt( messageHolderDiv.style.top ) < 0){
            messageHolderDiv.style.top = parseInt( messageHolderDiv.style.top ) + 1;
        }
        sd.util.setOpacity( messageHolderDiv, sd.util.getOpacity( messageHolderDiv ) + 3 );

        if( sd.util.getOpacity( messageHolderDiv ) >= 100 ) {
            clearInterval( sd.operator.messageScreenInterval );
            sd.operator.messageScreenInterval = null;
        }
    },

    __hideMessageAnimation : function() {
        var messageHolderDiv = sd.$( "message-holder" );
        messageHolderDiv.style.top = parseInt( messageHolderDiv.style.top ) - 1;
        sd.util.setOpacity( messageHolderDiv, sd.util.getOpacity( messageHolderDiv ) - 3 );

        if( sd.util.getOpacity( messageHolderDiv ) <= 30 ) {
            clearInterval( sd.operator.messageScreenInterval );
            sd.operator.messageScreenInterval = null;
            messageHolderDiv.style.left = -321;
            messageHolderDiv.style.top = -55;
        }
    }
//] TienNT says: not in used anymore @25may11
};
token = {
    reloadToken : function() {
        var getArgs = {
            url: "token!reloadToken.do",
            handleAs: "text",
            preventCache: this.urlPreventCache,
            sync: true
        };
        
        
        var getHandler = dojo.xhrGet(getArgs);
        getHandler.addCallback(function(data){
            try{
                dojo.byId("token").innerHTML = data;
            }catch(e){
                console.log(e);
                throw e;
            }
        });
        getHandler.addErrback(function(error){
            throw error;
        });  
        
    },
    getTokenParam:function(){
        var myParam;
        try{
            switch(arguments.length) {
                case 1:
                    myParam = arguments[0];
                    break;
                default:
                    myParam = new Object();
                    break;
            }

            this.reloadToken();
        
            var tokenDiv = document.getElementById("token");
            var els0 = tokenDiv.children[0];
            var els1 = tokenDiv.children[1];
            myParam[els0.name] = els0.value;
            myParam[els1.name] = els1.value;
        }catch(e){}
        return myParam;
    },
    getTokenParamString:function(){
        var myParam;
        try{
            this.isLoaded = false;
            this.reloadToken();
        
            var tokenDiv = document.getElementById("token");
            var els0 = tokenDiv.children[0];
            var els1 = tokenDiv.children[1];
        
            myParam=els0.name+"="+els0.value;
            myParam+="&"+els1.name +"="+ els1.value;
        }catch(e){}
        return myParam;
    }    
}
/**
 * Author: TienNT
 * Description: Cung cấp các API util
 * Date: 10/07/2011
 * FWVersion: 3.3
 **/

sd.util = {
    
    specKeyCodes : [8, 16, 17, 18, 19, 20, 27, 33, 34, 35, 36, 37, 38, 39, 40, 45, 46],
    keepAliveThread : null,
    _dumpRecursiveDeep : 1,
    _dumpRecursiveDeepCounter : 0,

    getObj : function( /*String|DOMNode*/ node ) {
        var obj = node;
        var type = ( typeof node ).toLowerCase();
        if( type == "string" ) {
            obj = dojo.byId( node );
        }
        return obj;
    },

    /*public void*/
    dump : function(/*Object*/ iObj, /*Number*/ deep) {
        var _deep = parseInt(deep);
        if(_deep != null && _deep != undefined && _deep >= 0) {
            this._dumpRecursiveDeep = _deep;
        } else {
            this._dumpRecursiveDeep = 0;
        }
        this._dumpRecursiveDeepCounter = 0;

        return this._dumpRecursive(iObj, "");
    },

    /*private void*/
    _dumpRecursive : function(/*Object*/ iObj, /*String*/ iObjName) {
        var prop;
        var str = "";
        var accessor = ".";

        this._dumpRecursiveDeepCounter++;

        for(prop in iObj) {
            try {
                if(dojo.isObject(iObj[prop]) || dojo.isArray(iObj[prop])) {
                    if(this._dumpRecursiveDeepCounter > this._dumpRecursiveDeep) {
                        str += iObjName + accessor + prop + "  =  " + iObj[prop] + "\n";
                    }
                    else {
                        str += this._dumpRecursive(iObj[prop], iObjName + accessor + prop) + "\n";
                    }
                } else {
                    str += iObjName + accessor + prop + "  =  " + iObj[prop] + "\n";
                }
            } catch(e) {
                str += "-JSExp when get " + iObjName + accessor + prop + " :" + e.message + "\n";
            }
        }

        //console.log(str);
        //console(["in recursive", prop, iObj[prop], dojo.isObject(iObj[prop]), dojo.isArray(iObj[prop]), str].join("\n"));

        this._dumpRecursiveDeepCounter--;

        return str;
    },

    /*String|DOMNode _node, Boolean _isShownInHTML*/
    dumpObj : function() {
        var node;
        var _node, _isShownInHTML;
        var prop, sep, str = "";

        switch( arguments.length ) {
            case 1:
                _node = arguments[0];
                _isShownInHTML = false;
                break;
            case 2:
                _node = arguments[0];
                _isShownInHTML = arguments[1];
                break;
        }

        sep = ( _isShownInHTML ) ? "<br />" : "\n";

        node = sd.util.getObj( _node );
        for( prop in node ) {
            str += prop + ": " + node[prop] + sep;
        }

        return str;
    },

    isValid : function(value) {
        var res = true;

        if(value == undefined || value == null || value == "null" || value == "undefined") {
            res = false;
        }

        return res;
    },

    isValidS : function(value) {
        var res = true;

        if(value == undefined || value == null || value == "null" || value == "undefined" || value.length < 1) {
            res = false;
        }

        return res;
    },

    idxArray : function( val, arr ) {
        if( val == null || arr == null ) return -1;
        if( arr.length == 0 ) return -1;

        for( var i = 0; i < arr.length; i++ ) {
            if( arr[i] == val ) return i;
        }

        return -1;
    },

    isSpecKeyCode : function( keyCode ) {
        return ( sd.util.idxArray( keyCode, sd.util.specKeyCodes ) == -1 ) ? false : true;
    },

    /**
     * Author: DungDV5
     * Description: Cho phép người dùng sử dụng phìm enter như tab
     * Date: 10/06/2011
     * FWVersion: 3.3
     **/
    makeEnterDoAsTab : function() {
        sd.$( "bodyContent" ).onkeyup = function( e ) {
            var evt = ( window.event ) ? window.event : e;
            var target = ( evt.srcElement ) ? evt.srcElement : evt.target;
            var nextEle;
            var tagName = target.tagName.toString().toLowerCase();
            var tagType;

            if( evt.keyCode == 13 ) {
                if( tagName != "button" ) {
                    nextEle = sd.util.getNextSibling( target );
                    if( nextEle ) {
                        nextEle.focus();
                    }
                }
            }
        }
    },

    getElementsByClass : function()
    {
        var rootObj, className;

        switch( arguments.length ) {
            case 1:
                rootObj = document;
                className = arguments[0];
                break;
            case 2:
                rootObj = arguments[0];
                className = arguments[1];
                break;
        }

        var eles = rootObj.getElementsByTagName( "*" );
        var res = [];
        for( var i = 0; i < eles.length; i++ ) {
            var obj = eles[i];
            if( obj.className && obj.className == className ) {
                res.push( obj );
            }
        }

        return res;
    },

    getNextSibling : function( node ) {
        var inputs = sd.$( "bodyContent" ).getElementsByTagName( "*" );
        var childs = inputs;

        var nextSibling = null;
        var tagName;
        var i;
        var isFind = false;

        for( i = 0; i < childs.length; i++ ) {
            if( node === childs[i] || isFind ) {
                isFind = true;
                try {
                    nextSibling = childs[i + 1];
                    if( nextSibling.style && nextSibling.style.display != "none" ) {
                        tagName = nextSibling.tagName.toString().toLowerCase();
                        if(
                            ( tagName == "input" && nextSibling.type.toString().toLowerCase() != "hidden" ) ||
                            ( tagName == "button" || tagName == "select" )
                            ) {
                            break;
                        }
                    }
                } catch( e ) {
                    console.debug("getNextSibling: " +  e.message );
                    nextSibling = null;
                    break;
                }
            }
        }

        return nextSibling;
    },

    getMaxZIndex : function() {
        var allElems = document.getElementsByTagName ? document.getElementsByTagName("*") : document.all;
        var maxZIndex = 0;

        for(var i=0;i<allElems.length;i++) {
            var elem = allElems[i];
            var cStyle = null;

            if (elem.currentStyle) {
                cStyle = elem.currentStyle;
            }
            else if (document.defaultView && document.defaultView.getComputedStyle)
            {
                cStyle = document.defaultView.getComputedStyle(elem,"");
            }

            var sNum;
            if (cStyle) {
                sNum = Number(cStyle.zIndex);
            } else {
                sNum = Number(elem.style.zIndex);
            }

            if (!isNaN(sNum)) {
                maxZIndex = Math.max(maxZIndex,sNum);
            }
        }

        return maxZIndex;
    },

    getScrollXY : function() {
        var scrOfX = 0, scrOfY = 0;
        if( typeof( window.pageYOffset ) == 'number' ) {
            //Netscape compliant
            scrOfY = window.pageYOffset;
            scrOfX = window.pageXOffset;
        } else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) ) {
            //DOM compliant
            scrOfY = document.body.scrollTop;
            scrOfX = document.body.scrollLeft;
        } else if( document.documentElement && ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) ) {
            //IE6 standards compliant mode
            scrOfY = document.documentElement.scrollTop;
            scrOfX = document.documentElement.scrollLeft;
        }
        //alert( scrOfX + ',' + scrOfY );
        return [ scrOfX, scrOfY ];
    },

    getPageWH : function() {
        var pageW = ( window.innerWidth ) ? window.innerWidth : ( document.documentElement.offsetWidth );
        var pageH = ( window.innerHeight ) ? window.innerHeight : ( document.documentElement.offsetHeight );
        var body = document.getElementsByTagName( 'body' )[0];

        var actualW;
        var actualH;

        if( body.scrollHeight > pageH ) {
            actualH = body.scrollHeight;
            actualW = body.scrollWidth;
        } else {
            actualH = pageH;
            actualW = pageW;

            // [ Stupid IE
            if( document.all ) {
                actualW = ( body.scrollWidth > pageW ) ? pageW : body.scrollWidth;
            }
        // ] Stupid IE
        }
        //alert( body.scrollWidth );
        return [ pageW, pageH, actualW, actualH ];
    },

    setOpacity : function( obj, opacityAsInt ) {
        // Source: http://www.akxl.net/
        var elem = sd.util.getObj( obj );
        var opacityAsDecimal = opacityAsInt;

        if( !elem ) return false;

        if( opacityAsInt > 100 )
            opacityAsInt = opacityAsDecimal = 100;
        else if( opacityAsInt < 0 )
            opacityAsInt = opacityAsDecimal = 0;

        opacityAsDecimal /= 100;
        if ( opacityAsInt < 1 ) {
            opacityAsInt = 1; // IE7 bug, text smoothing cuts out if 0
        }

        elem.style.opacity = ( opacityAsDecimal );
        elem.style.MozOpacity = ( opacityAsDecimal );
        elem.style.filter  = "alpha(opacity=" + opacityAsInt + ")";

        return true;
    },

    getOpacity : function( obj ) {
        var elem = sd.util.getObj( obj );
        if( !elem ) return 0;

        if( elem.style.opacity ) {
            return elem.style.opacity * 100;
        }
        else if( elem.style.MozOpacity ) {
            return elem.style.MozOpacity * 100;
        }

        return 100;
    },

    keepAlive: function(/*Int-seconds*/ duration){
        this.keepAliveThread = setInterval("sd.util.connectServerForKeepAlive()", duration * 1000);
    },

    stopKeepAlive: function(){
        if(sd.operator.keepAliveThread){
            clearInterval(this.keepAliveThread);
        }
    },

    connectServerForKeepAlive : function(){
        var parameter = {
            url : "index!keepAlive.do",
            preventCache : true
        };
        dojo.xhrPost( parameter );
    },

    convertVNString: function (sValue){
        var s = "";
        for (var i=0; i < sValue.length; i++){
            switch (sValue[i]) {
                case 'a':
                    s += String.fromCharCode(58);
                    break;
                case 'A':
                    s += String.fromCharCode(58);
                    break;
                case 'à':
                    s += String.fromCharCode(59);
                    break;
                case 'ả':
                    s += String.fromCharCode(60);
                    break;
                case 'ã':
                    s += String.fromCharCode(61);
                    break;
                case 'á':
                    s += String.fromCharCode(62);
                    break;
                case 'ạ':
                    s += String.fromCharCode(63);
                    break;
                case 'ă':
                    s += String.fromCharCode(64);
                    break;
                case 'Ǎ':
                    s += String.fromCharCode(64);
                    break;
                case 'ằ':
                    s += String.fromCharCode(65);
                    break;
                case 'ắ':
                    s += String.fromCharCode(66);
                    break;
                case 'ã':
                    s += String.fromCharCode(67);
                    break;
                case 'ắ':
                    s += String.fromCharCode(68);
                    break;
                case 'ặ':
                    s += String.fromCharCode(69);
                    break;
                case 'â':
                    s += String.fromCharCode(70);
                    break;
                case 'Â':
                    s += String.fromCharCode(70);
                    break;
                case 'ầ':
                    s += String.fromCharCode(71);
                    break;
                case 'ẩ':
                    s += String.fromCharCode(72);
                    break;
                case 'ẫ':
                    s += String.fromCharCode(73);
                    break;
                case 'ấ':
                    s += String.fromCharCode(74);
                    break;
                case 'ậ':
                    s += String.fromCharCode(75);
                    break;
                case 'b':
                    s += String.fromCharCode(76);
                    break;
                case'B':
                    s += String.fromCharCode(76);
                    break;
                case 'c':
                    s += String.fromCharCode(77);
                    break;
                case 'C':
                    s += String.fromCharCode(77);
                    break;
                case 'd':
                    s += String.fromCharCode(78);
                    break;
                case 'D':
                    s += String.fromCharCode(78);
                    break;
                case 'đ':
                    s += String.fromCharCode(79);
                    break;
                case 'Đ':
                    s += String.fromCharCode(79);
                    break;
                case 'e':
                    s += String.fromCharCode(80);
                    break;
                case 'E':
                    s += String.fromCharCode(80);
                    break;
                case 'è':
                    s += String.fromCharCode(81);
                    break;
                case 'ẻ':
                    s += String.fromCharCode(82);
                    break;
                case 'ẽ':
                    s += String.fromCharCode(83);
                    break;
                case 'é':
                    s += String.fromCharCode(84);
                    break;
                case 'ẹ':
                    s += String.fromCharCode(85);
                    break;
                case 'ê':
                    s += String.fromCharCode(86);
                    break;
                case 'Ê':
                    s += String.fromCharCode(86);
                    break;
                case 'ề':
                    s += String.fromCharCode(87);
                    break;
                case 'ể':
                    s += String.fromCharCode(88);
                    break;
                case 'ễ':
                    s += String.fromCharCode(89);
                    break;
                case 'ế':
                    s += String.fromCharCode(90);
                    break;
                case 'ệ':
                    s += String.fromCharCode(91);
                    break;
                case 'f':
                    s += String.fromCharCode(92);
                    break;
                case 'F':
                    s += String.fromCharCode(92);
                    break;
                case 'g':
                    s += String.fromCharCode(93);
                    break;
                case 'G':
                    s += String.fromCharCode(93);
                    break;
                case 'h':
                    s += String.fromCharCode(94);
                    break;
                case 'H':
                    s += String.fromCharCode(94);
                    break;
                case 'i':
                    s += String.fromCharCode(95);
                    break;
                case 'I':
                    s += String.fromCharCode(95);
                    break;
                case 'ì':
                    s += String.fromCharCode(97);
                    break;
                case 'ỉ':
                    s += String.fromCharCode(98);
                    break;
                case 'ĩ':
                    s += String.fromCharCode(99);
                    break;
                case 'í':
                    s += String.fromCharCode(100);
                    break;
                case 'ị':
                    s += String.fromCharCode(101);
                    break;
                case 'j':
                    s += String.fromCharCode(102);
                    break;
                case 'J':
                    s += String.fromCharCode(102);
                    break;
                case 'k':
                    s += String.fromCharCode(103);
                    break;
                case 'K':
                    s += String.fromCharCode(103);
                    break;
                case 'l':
                    s += String.fromCharCode(104);
                    break;
                case 'L':
                    s += String.fromCharCode(104);
                    break;
                case 'm':
                    s += String.fromCharCode(105);
                    break;
                case 'M':
                    s += String.fromCharCode(105);
                    break;
                case 'n':
                    s += String.fromCharCode(106);
                    break;
                case 'N':
                    s += String.fromCharCode(106);
                    break;
                case 'o':
                    s += String.fromCharCode(107);
                    break;
                case 'O':
                    s += String.fromCharCode(107);
                    break;
                case 'ò':
                    s += String.fromCharCode(108);
                    break;
                case 'ỏ':
                    s += String.fromCharCode(109);
                    break;
                case 'õ':
                    s += String.fromCharCode(110);
                    break;
                case 'ó':
                    s += String.fromCharCode(111);
                    break;
                case 'ọ':
                    s += String.fromCharCode(112);
                    break;
                case 'ô':
                    s += String.fromCharCode(113);
                    break;
                case 'Ô':
                    s += String.fromCharCode(113);
                    break;
                case 'ồ':
                    s += String.fromCharCode(114);
                    break;
                case 'ổ':
                    s += String.fromCharCode(115);
                    break;
                case 'ỗ':
                    s += String.fromCharCode(116);
                    break;
                case 'ố':
                    s += String.fromCharCode(117);
                    break;
                case 'ộ':
                    s += String.fromCharCode(118);
                    break;
                case 'p':
                    s += String.fromCharCode(119);
                    break;
                case 'P':
                    s += String.fromCharCode(119);
                    break;
                case 'q':
                    s += String.fromCharCode(120);
                    break;
                case 'Q':
                    s += String.fromCharCode(120);
                    break;
                case 'r':
                    s += String.fromCharCode(121);
                    break;
                case 'R':
                    s += String.fromCharCode(121);
                    break;
                case 's':
                    s += String.fromCharCode(122);
                    break;
                case 'S':
                    s += String.fromCharCode(122);
                    break;
                case 't':
                    s += String.fromCharCode(123);
                    break;
                case 'T':
                    s += String.fromCharCode(123);
                    break;
                case 'u':
                    s += String.fromCharCode(126);
                    break;
                case 'U':
                    s += String.fromCharCode(126);
                    break;
                case 'ù':
                    s += String.fromCharCode(127);
                    break;
                case 'ủ':
                    s += String.fromCharCode(128);
                    break;
                case 'ũ':
                    s += String.fromCharCode(129);
                    break;
                case 'ú':
                    s += String.fromCharCode(130);
                    break;
                case 'ụ':
                    s += String.fromCharCode(131);
                    break;
                case 'ư':
                    s += String.fromCharCode(132);
                    break;
                case 'Ư':
                    s += String.fromCharCode(132);
                    break;
                case 'ừ':
                    s += String.fromCharCode(133);
                    break;
                case 'ử':
                    s += String.fromCharCode(134);
                    break;
                case 'ữ':
                    s += String.fromCharCode(135);
                    break;
                case 'ứ':
                    s += String.fromCharCode(136);
                    break;
                case 'ự':
                    s += String.fromCharCode(137);
                    break;
                case 'v':
                    s += String.fromCharCode(138);
                    break;
                case 'V':
                    s += String.fromCharCode(138);
                    break;
                case 'w':
                    s += String.fromCharCode(139);
                    break;
                case 'W':
                    s += String.fromCharCode(139);
                    break;
                case 'x':
                    s += String.fromCharCode(140);
                    break;
                case 'X':
                    s += String.fromCharCode(140);
                    break;
                case 'y':
                    s += String.fromCharCode(141);
                    break;
                case 'Y':
                    s += String.fromCharCode(141);
                    break;
                case 'ỳ':
                    s += String.fromCharCode(142);
                    break;
                case 'ỷ':
                    s += String.fromCharCode(143);
                    break;
                case 'ỹ':
                    s += String.fromCharCode(144);
                    break;
                case 'ý':
                    s += String.fromCharCode(145);
                    break;
                case 'ỵ':
                    s += String.fromCharCode(146);
                    break;
                case 'z':
                    s += String.fromCharCode(147);
                    break;
                case 'Z':
                    s += String.fromCharCode(147);
                    break;
                default:
                    s += sValue[i];
            }
        }
        return s;
    }
};
/**
 * Author: TienNT
 * Description: Cung cấp các API validator
 * Date: 10/07/2011
 * FWVersion: 3.3
 **/

sd.validator = {

    stack : {},
    stackPrefix : "",

    initStack : function( stackId ) {
        this.stack[this.stackPrefix + stackId] = [];
    },

    getStack : function( stackId ) {
        return this.stack[this.stackPrefix + stackId];
    },

    clearStack : function( stackId ) {
        try {
            delete this.stack[this.stackPrefix + stackId];
        }
        catch( e ) {
            this.stack[this.stackPrefix + stackId] = undefined;
        }
    },

    clearStacks : function() {
        try {
            delete this.stack;
        }
        catch( e ) {

        }
        this.stack = {};
    },

    testStack : function( stackId ) {
        var validatorArr = this.getStack( stackId );
        var validator, i;
        if( validatorArr ) {

            for( i = 0; i < validatorArr.length; i++ ) {
                validator = validatorArr[i];
                if( !validator.call() ) {
                    return false;
                }
            }
            return true;
        }
        else {
            alert( "TienNT says: ValidatorStack is not available." );
        }
        return false;
    },

    trim : function( stringToTrim ) {
        return stringToTrim.replace( /^\s+|\s+$/g, "" );
    },

    ltrim : function( stringToTrim ) {
        return stringToTrim.replace( /^\s+/, "" );
    },

    rtrim : function( stringToTrim ) {
        return stringToTrim.replace( /\s+$/, "" );
    },

    isHTMLText : function( html ) {
        var reDoubleTag = /<.+>.*<\/.+>/;
        var reSingleTag = /<.+\/?>?/;
        var res = ( reDoubleTag.test( html ) || reSingleTag.test( html ) );

        return res;
    },

    isValidInput : function( ) {
        var sText = "";
        var bEmptyAllowed = true;
        var bSpaceAllowed = true;
        var re;

        switch( arguments.length ) {
            case 1:
                sText = arguments[0];
                break;
            case 2:
                sText = arguments[0];
                bEmptyAllowed = arguments[1];
                break;
            case 3:
                sText = arguments[0];
                bEmptyAllowed = arguments[1];
                bSpaceAllowed = arguments[2];
                break;
        }

        if( bEmptyAllowed ) {
            if( bSpaceAllowed )
                re = /^[a-zA-Z0-9_\-\s]*$/;
            else
                re = /^[a-zA-Z0-9_\-]*/;
        }
        else {
            if( bSpaceAllowed )
                re = /^[a-zA-Z0-9_\-\s]+$/;
            else
                re = /^[a-zA-Z0-9_\-]+$/;
        }

        return re.test( sText );

    },

    isNaturalNumber : function( sText ) {
        var re = /^[\d]+$/;
        return re.test( sText );
    },

    isIntegerNumber : function( sText ) {
        var re = /^\-?[\d]+$/;
        return re.test( sText );
    },

    isFloatNumber : function( sText ) {
        if( sText.toString( ) == '-0' ) return false;

        var re = /^\-?[\d]+$/;
        if( re.test( sText ) ) return true;
        re = /^\-?[\d]+\.[\d]+$/;
        return re.test( sText );
    },

    isNumberFormat : function( sText, sFormat ) {
        var specCharPattern = /[\D]/;
        var specChar = sFormat.match( specCharPattern );
        var aNum = sFormat.split( specChar );

        var sCmd = "var re = /^";
        for( var iC = 0; iC < aNum.length; iC++ ) {
            if( iC != 0 )
                sCmd += "\\" + specChar;
            sCmd += "[0-9]{" + aNum[iC] + "}";
        }
        sCmd += "$/;";
        eval( sCmd );
        return re.test( sText );
    },

    isEmail : function( sText ) {
        var str = sText;
        if( str == "" ) {
            //alert("Verify the email address format.");
            return false;
        }
        var re = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;
        if ( !str.match( re ) ) {
            //alert("Verify the email address format.");
            return false;
        } else {
            return true;
        }
    },

    isDateFormat : function( value ) {

        var date=value.substr(0,10);



        // Regular expression used to check if date is in correct format

        var pattern = new RegExp(/^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/);



        // kiem tra date

        if(date.match(pattern)){

            var date_array = date.split('/');

            var day = date_array[0];



            // Attention! Javascript consider months in the range 0 - 11

            var month = date_array[1] - 1;

            var year = date_array[2];



            // This instruction will create a date object

            source_date = new Date(year,month,day);



            if(year != source_date.getFullYear())

            {

                return false;

            }



            if(month != source_date.getMonth())

            {

                return false;

            }



            if(day != source_date.getDate())

            {

                return false;

            }

        }else {

        return false;

    }



    // kiem tra time

    if(value.length>10){

        var time=value.substr(11);

        if(time.length ==8){

            var hour=time.substr(0,2);

            var minute=time.substr(3,2);

            var second=time.substr(6);



            if(parseInt(hour) > 23 && parseInt(hour) < 0){

                return false;

            }

            if(parseInt(minute) > 60 && parseInt(minute) < 0){

                return false;

            }

            if(parseInt(second) > 60 && parseInt(second) < 0){

                return false;

            }

        }else{

        return false;

    }

    }



    return true;

    },

    isDate : function( value )
    {
        var date=value.substr(0,10);

        // Regular expression used to check if date is in correct format
        var pattern = new RegExp(/^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/);

        // kiem tra date
        if(date.match(pattern)){
            var date_array = date.split('/');
            var day = date_array[0];

            // Attention! Javascript consider months in the range 0 - 11
            var month = date_array[1] - 1;
            var year = date_array[2];

            // This instruction will create a date object
            source_date = new Date(year,month,day);

            if(year != source_date.getFullYear())
            {
                    return false;
            }

            if(month != source_date.getMonth())
            {
            return false;
            }

            if(day != source_date.getDate())
            {
            return false;
            }
        }else {
            return false;
        }

        // kiem tra time
        if(value.length>10){
            var time=value.substr(11);
            if(time.length ==8){
                var hour=time.substr(0,2);
                var minute=time.substr(3,2);
                var second=time.substr(6);

                if(parseInt(hour) > 23 && parseInt(hour) < 0){
                    return false;
                }
                if(parseInt(minute) > 60 && parseInt(minute) < 0){
                    return false;
                }
                if(parseInt(second) > 60 && parseInt(second) < 0){
                     return false;
                }
            }else{
                return false;
            }
        }

        return true;
    },

    //ham so sanh xem date1 co nho hon date2 ko? date
    //pattern = "dd/MM/yyyy HH:mm:ss"
    compareDates : function( date1,date2 ) {

        var arrayDate1 = date1.split("/");

        var arrayDate2 = date2.split("/");

        var arrayTime1 = date1.split(":");
        var arrayTime2 = date2.split(":");

        var nam1   = parseFloat(arrayDate1[2]);

        var nam2   = parseFloat(arrayDate2[2]);

        var thang1 = parseFloat(arrayDate1[1]);

        var thang2 = parseFloat(arrayDate2[1]);

        var ngay1  = parseFloat(arrayDate1[0]);

        var ngay2  = parseFloat(arrayDate2[0]);


        var h1 = parseFloat(arrayTime1[0]);
        var h2 = parseFloat(arrayTime2[0]);

        var p1 = parseFloat(arrayTime1[1]);
        var p2 = parseFloat(arrayTime2[1]);

        var s1 = parseFloat(arrayTime1[2]);
        var s2 = parseFloat(arrayTime2[2]);


        if(nam1 < nam2){
            return true;
        }
        if(nam1 == nam2){
            if(thang1 < thang2){
                return true;
            }
            if(thang1 == thang2){
                if(ngay1 < ngay2){
                    return true;
                }
                if( ngay1 == ngay2) {
                    if( h1 < h2){
                        return true;
                    }
                    if( p1 < p2){
                        return true;
                    }
                    if( s1 < s2){
                        return true;
                    }
               }
            }
        }
        return false;

    },

    daysBetween : function(date1, date2) {

        var arrayDate1 = date1.split("/");

        var arrayDate2 = date2.split("/");



        var startYear   = parseFloat(arrayDate1[2]);

        var endYear   = parseFloat(arrayDate2[2]);

        var startMonth = parseFloat(arrayDate1[1]);

        var endMonth = parseFloat(arrayDate2[1]);

        var startDay  = parseFloat(arrayDate1[0]);

        var endDay  = parseFloat(arrayDate2[0]);



        var start = new Date(startYear,startMonth-1,startDay);

        var stop  = new Date(endYear,endMonth-1,endDay);

        var diff  = (stop - start) / (1000 * 60 * 60 * 24);

        return diff;

    }

};
/**
 * Author: TienNT
 * Description: Cung cấp các API xử lý quá trình init/load các widget trong các TagHandler
 * Date: 10/07/2011
 * FWVersion: 3.3
 **/

sd.widget.loader = {
    sortableArray: null,
    structure: null,
    structure_noScroll: null,
    structure_scroll: null,
    
    gridFormatDate: function (inDatum) {
        if ((/[0-9]T[0-9]+/).test(inDatum)) {
            var temp = inDatum.split("T");
            var inDatum_1 = temp[0];
            if (inDatum_1.indexOf('-')!=-1) {
                var tmp = inDatum_1.split('-');
                inDatum = tmp[1]+'/'+tmp[2]+'/'+ tmp[0];
                inDatum += ' ' + temp[1];
            }
        //"  var constr = this.constraint['datePattern'];" +
        } else {}
        if (inDatum != null) {
            if (inDatum != " ")return dojo.date.locale.format(new Date(inDatum), this.constraint);
            else return '';
        } else {
            return '';
        }
    },

    cloneArray : function (arr) {
        var arr1 = new Array();
        for (var property in arr) {
            arr1[property] = typeof (arr[property]) == 'object' ? arr[property].clone() : arr[property]
        }
        return arr1;
    },

    loadAjaxTree : function(
        getTopLevelUrl, getChildrenUrl, 
        topLevelUrlForm, topLevelUrlParam, childrenUrlForm, childrenUrlParam,
        rootLabel, checkboxStrict, delayLoading, delayLoadTopLevelTime, delayLoadChildrenTime,
        id, persist, onClick, onNodeChecked, onNodeUnchecked, treeContainer
        ) {
        try {
            if(sd.operator.allowedToExecJS == false) {
                return;
            }

            dojo.require("vt.tree.ajaxTree.dataStore");
            dojo.require("vt.tree.ajaxTree.treeModel");
            dojo.require("vt.tree.ajaxTree.tree");

            //[ Create store
            var store = new vt.tree.ajaxTree.dataStore({
                url : getTopLevelUrl,
                getChildrenUrl : getChildrenUrl
            });

            if(sd.util.isValidS(topLevelUrlForm)) {
                store.vtTopUrlForm = topLevelUrlForm;
            }
            if(sd.util.isValid(topLevelUrlParam)) {
                store.vtTopUrlParam = topLevelUrlParam;
            }
            if(sd.util.isValidS(childrenUrlForm)) {
                store.vtChildrenUrlForm = childrenUrlForm;
            }
            if(sd.util.isValid(childrenUrlParam)) {
                store.vtChildrenUrlParam = childrenUrlParam;
            }
            //] Create store

            //[ Create model
            var model = new vt.tree.ajaxTree.treeModel({
                store : store,
                rootLabel : rootLabel
            });

            if(sd.util.isValid(checkboxStrict)) {
                model.checkboxStrict = checkboxStrict;
            }
            if(sd.util.isValid(delayLoading)) {
                model.vtDelayLoading = delayLoading;
            }
            if(sd.util.isValid(delayLoadTopLevelTime)) {
                model.vtDelayLoadTopLevelTime = delayLoadTopLevelTime;
            }
            if(sd.util.isValid(delayLoadChildrenTime)) {
                model.vtDelayLoadChildrenTime = delayLoadChildrenTime;
            }
            //] Create model

            //[ Create tree
            var tree = new vt.tree.ajaxTree.tree({
                id : id,
                model : model
            });

            if(sd.util.isValid(persist)) {
                tree.persist = persist;
            }
            
            if(sd.util.isValidS(onClick)) {
                tree.onClick = function(item,node,domElement){
                    try {
                        eval(onClick + "(item, node,domElement);");
                    }
                    catch(e){
                        alert("VTTree,onNodeClick:" +  e.message);
                    }
                };
            }
            if(sd.util.isValidS(onNodeChecked)) {
                tree.onNodeChecked = function(item,node){
                    try {
                        eval(onNodeChecked + "(item, node);");
                    }
                    catch(e){
                        alert("VTTree,onNodeChecked:" +  e.message);
                    }
                };
            }
            if(sd.util.isValidS(onNodeUnchecked)) {
                tree.onNodeUnchecked = function(item,node){
                    try {
                        eval(onNodeUnchecked + "(item, node);");
                    }
                    catch(e){
                        alert("VTTree,onNodeUnchecked:" +  e.message);
                    }
                };
            }
            //] Create tree

            //[ Make it alive
            dojo.byId(treeContainer).appendChild(tree.domNode);
        //] Make it alive
        }
        catch(e) {
            alert("Error in sd.widget.loader.loadAjaxTree:\n" + e.message);
        }
    },

    loadTree : function(
        getDataUrl,
        rootLabel, checkboxStrict,
        id, persist, onClick, onNodeChecked, onNodeUnchecked, treeContainer
        ) {
        try {
            if(sd.operator.allowedToExecJS == false) {
                return;
            }

            dojo.require("dojo.data.ItemFileWriteStore");
            dojo.require("vt.tree.checkboxTree.CheckBoxStoreModel");
            dojo.require("vt.tree.checkboxTree.CheckBoxTree");

            //[ Create store
            var store = new dojo.data.ItemFileWriteStore({
                url : getDataUrl
            });
            //] Create store

            //[ Create model
            var model = new vt.tree.checkboxTree.CheckBoxStoreModel({
                store : store,
                rootLabel : rootLabel
            });

            if(sd.util.isValid(checkboxStrict)) {
                model.checkboxStrict = checkboxStrict;
            }
            //] Create model

            //[ Create tree
            var tree = new vt.tree.checkboxTree.CheckBoxTree({
                id : id,
                model : model
            });

            if(sd.util.isValid(persist)) {
                tree.persist = persist;
            }

            if(sd.util.isValidS(onClick)) {
                tree.onClick = function(item,node,domElement){
                    try {
                        eval(onClick + "(item, node,domElement);");
                    }
                    catch(e){
                        alert("VTTree,onNodeClick:" +  e.message);
                    }
                };
            }
            if(sd.util.isValidS(onNodeChecked)) {
                tree.onNodeChecked = function(item,node){
                    try {
                        eval(onNodeChecked + "(item, node);");
                    }
                    catch(e){
                        alert("VTTree,onNodeChecked:" +  e.message);
                    }
                };
            }
            if(sd.util.isValidS(onNodeUnchecked)) {
                tree.onNodeUnchecked = function(item,node){
                    try {
                        eval(onNodeUnchecked + "(item, node);");
                    }
                    catch(e){
                        alert("VTTree,onNodeUnchecked:" +  e.message);
                    }
                };
            }
            //] Create tree

            //[ Make it alive
            dojo.byId(treeContainer).appendChild(tree.domNode);
        //] Make it alive
        }
        catch(e) {
            alert("Error in sd.widget.loader.loadTree:\n" + e.message);
        }
    },
    /**
     * Author: DungDV
     * Description: them ham initGrid, loadGrid, loadColumnGrid, initDataPicker, loadDataPicker
     * thuoc tinh sortableArray, structure, structure_noScroll, structure_scroll cho qua trinh khoi tao va load Grid, DataPicker
     * sortableArray danh cho thiet lap cot nao la cot sort duoc
     * Date: 10/07/2011
     * FWVersion: 3.3
     **/
    initGrid: function (id) {
        try {
            if(sd.operator.allowedToExecJS == false) {
                return;
            }

            dojo.require("vt.dataGrid.cells._base");
            this.structure = [];
            this.structure_noScroll = {
                noscroll: true,
                cells:[]
            };
            this.sortableArray = [];
            this.structure_scroll = [];
            this.structure.push(this.structure_noScroll);
            this.structure.push(this.structure_scroll);
        } catch (e) {
            alert("Error in sd.widget.loader.initGrid:\n" + e.message);
        }
    },

    loadGrid: function (
        id, getDataUrl, style, clientSort, query, rowSelector,
        container, autoWidth, rowsPerPage, serverPaging, selectable,
        pageText, rowCountText, rowPerPageText, noDataMessageText,
        idVTGrid, idDiv
        ) {
        try {
            if(sd.operator.allowedToExecJS == false) {
                return;
            }
            // DungDV for fix bug: khong doi mau item duoc chon tren Select
            if (page.enableGridMouseEvent == null || page.enableGridMouseEvent == undefined) {
                page.enableGridMouseEvent = true;
            }
            dojo.require("vt.dataGrid.vtDataGrid");
            var attrs = {};
            if(sd.util.isValid(style)) {
                attrs.style = style;
            }
            if(sd.util.isValid(clientSort)) {
                attrs.clientSort = clientSort;
            }
            if(sd.util.isValid(rowSelector)) {
                attrs.rowSelector = rowSelector;
            }
            if(sd.util.isValid(query)) {
                attrs.query = query;
            }
            if(sd.util.isValid(query)) {
                attrs.query = query;
            }
            if(sd.util.isValid(rowsPerPage)) {
                attrs.rowsPerPage = rowsPerPage;
                attrs.autoHeight = rowsPerPage;
            }
            if(sd.util.isValid(autoWidth)) {
                attrs.autoWidth = autoWidth;
            }
            if(sd.util.isValid(serverPaging)) {
                attrs.serverPaging = serverPaging;
            }
            if(sd.util.isValid(selectable)) {
                attrs.selectable = selectable;
            } else {
                attrs.selectable = true;
            }
            attrs.noDataMessage = noDataMessageText;
            if(sd.util.isValid(id)) {
                attrs.id = id;
            }
            var gridParam = {};
            gridParam.gridAttr = attrs;
            gridParam.sortableArray = this.sortableArray;
            gridParam.structure = this.structure;
            gridParam.getDataUrl = getDataUrl;
            if(sd.util.isValid(container)) {
                attrs.container = container;
            }
            gridParam.id = idVTGrid;
            var vtGrid = new vt.dataGrid.vtDataGrid(gridParam);
            if(sd.util.isValid(container)) {
                dojo.byId(container).appendChild(vtGrid.domNode);
            } else {
                dojo.byId(idDiv).appendChild(vtGrid.domNode);
            }
            vtGrid.titleRowCount.innerHTML = rowCountText;
            vtGrid.titlePageSelector.innerHTML = pageText;
            vtGrid.titleRpp.innerHTML = rowPerPageText;
            vtGrid.startup();
            try {
                delete this.sortableArray;
                delete this.structure_noScroll;
                delete this.structure_scroll;
                delete this.structure;
            } catch (e) {
                this.sortableArray = undefined;
                this.structure_noScroll = undefined;
                this.structure_scroll = undefined;
                this.structure = undefined;
            }
        } catch (e) {
            alert("Error in sd.widget.loader.loadGrid:\n" + e.message);
        }
    },

    loadColumnGrid: function (
        parenId, editable, field, colName, width, type, arrOption, arrValue, formatter, styles,
        cellStyles, headerStyles, get, value, alwaysEditing, format,
        onclick, setDisabled, setChecked, headerCheckbox, wrapping,
        sortType, noScroll, enableSort
        ) {
        try {
            
            //cuongnx
             var sortable = true;
            if(sd.util.isValid(enableSort)) {
                sortable = enableSort;
            }
           // end cuongnx
            if(sd.operator.allowedToExecJS == false) {
                return;
            }
            var attrs = {};
            if(sd.util.isValid(arrOption)) {
                attrs.options = arrOption;
            }
            if(sd.util.isValid(arrValue)) {
                attrs.values = arrValue;
            }
            if(sd.util.isValid(formatter)) {
                attrs.formatter = formatter;
//                sortable = false;
            }
            if(sd.util.isValid(field)) {
                attrs.field = field;
            }
            if(sd.util.isValid(alwaysEditing)) {
                attrs.alwaysEditing = alwaysEditing;
            }
            if(sd.util.isValid(value)) {
                attrs.value = value;
            }
            if(sd.util.isValid(get)) {
                attrs.get = get;
            }
            if(sd.util.isValid(styles)) {
                attrs.styles = styles;
            }
            if(sd.util.isValid(cellStyles)) {
                attrs.cellStyles = cellStyles;
            }
            if(sd.util.isValid(headerStyles)) {
                attrs.headerStyles = headerStyles;
            }
            if(sd.util.isValid(width)) {
                attrs.width = width;
            }
            if(sd.util.isValid(type)) {
                var typeLow = type.toLowerCase();
                if (typeLow == "checkbox") {
                    attrs.type = dojox.grid.cells.vtBool;
                    attrs.editable = true;
                    attrs.gridId = parenId;
                    if(sd.util.isValid(field)) {
                        attrs.field = "_R_D_";
                    }
                    editable = null;
                    //sortable = false;
                } else if (typeLow == "select") {
                    attrs.type = dojox.grid.cells.vtSelect;
                    attrs.editable = true;
                    editable = null;
                } else if (typeLow == "radio") {
                    attrs.type = dojox.grid.cells.Radio;
                    attrs.editable = true;
                    attrs.gridId = parenId;
                    if(sd.util.isValid(field)) {
                        attrs.field = "_R_D_";
                    }
                    editable = null;
                    //sortable = false;
                } else if (typeLow == "date") {
                    attrs.type = dojox.grid.cells.vtDateTextBox;
                    attrs.formatter = this.gridFormatDate;
                    attrs.constraint = {};
                    attrs.constraint.formatLength = 'full';
                    if(sd.util.isValidS(format)) {
                        attrs.constraint.datePattern = format;
                        if (format.toLowerCase().indexOf("ss") == -1) {
                            attrs.constraint.selector = 'date';
                        }
                    } else {
                        attrs.constraint.datePattern = 'dd/MM/yyyy';
                    }
                } else {
                    attrs.type = type;
                }
            }
            if(sd.util.isValidS(wrapping)) {
                if (wrapping.indexOf("true") != -1) {
                    attrs.wrapping = true;
                }
            }
            if(sd.util.isValidS(onclick)) {
                attrs.onclick = onclick;
            }
            if(sd.util.isValidS(setDisabled)) {
                //                if ((/\(*(,?)*\)/).test(setDisabled)) {
                //                    console.error(setDisabled);
                //                    attrs.setDisabled = "\"" + setDisabled + "\"";
                //                } else {
                //                    attrs.setDisabled = setDisabled;
                //                }
                attrs.setDisabled = setDisabled;
            }
            if(sd.util.isValidS(setChecked)) {
                //                if ((/\(*(,?)*\)/).test(setChecked)) {
                //                    console.error(setChecked);
                //                    attrs.setDisabled = "\"" + setChecked + "\"";
                //                } else {
                //                    attrs.setDisabled = setChecked;
                //                }
                attrs.setChecked = setChecked;
            }
            if(sd.util.isValidS(headerCheckbox)) {
                attrs.headerCheckbox = headerCheckbox;
                //sortable = false;
            }
            if(sd.util.isValid(editable)) {
                attrs.editable = editable;
            }
            if(sd.util.isValid(sortType)) {
                attrs.sortType = sortType;
            }
            attrs.name = colName;
            this.sortableArray.push(sortable);
            if(sd.util.isValid(noScroll) && (noScroll == "true" || noScroll == true)) {
                this.structure_noScroll.cells.push(attrs);
            } else {
                this.structure_scroll.push(attrs);
            }
        } catch (e) {
            alert("Error in sd.widget.loader.loadColumnGrid:\n" + e.message);
        }
    },

    initDataPicker: function (id) {
        try {
            if(sd.operator.allowedToExecJS == false) {
                return;
            }
            dojo.require("vt.dataPicker.dataPicker");
            this.structure = [];
            this.structure_noScroll = {
                noscroll: true,
                cells:[]
            };
            this.sortableArray = [];
            this.structure_scroll = [];
            this.structure.push(this.structure_noScroll);
            this.structure.push(this.structure_scroll);
        } catch (e) {
            alert("Error in sd.widget.loader.initDataPicker:\n" + e.message);
        }
    },

    loadDataPicker: function (
        id, searchParams, searchUrl, bServerSearch, searchNumCol, gridUrl,
        valueField, hiddenFields, useCache, name,
        required, cssStyle, maxLength, btnSearchLabelText, idDiv,
        jsObjectItem
        ) {
        try {
            if(sd.operator.allowedToExecJS == false) {
                return;
            }
            var pickerParams = {};
            pickerParams.id = id;
            pickerParams.name = (sd.util.isValid(name) ? name : "");
            pickerParams.selectedItem = jsObjectItem;
            if (sd.util.isValid(cssStyle)) {
                pickerParams.style = cssStyle;
            }
            pickerParams.searchParams = searchParams;
            if (sd.util.isValid(searchNumCol)) {
                pickerParams.searchNumCol = searchNumCol;
            }
            if (sd.util.isValid(useCache)) {
                pickerParams.useCache = useCache;
            }
            if (sd.util.isValid(bServerSearch)) {
                pickerParams.bServerSearch = bServerSearch;
            }
            if (sd.util.isValid(btnSearchLabelText)) {
                pickerParams.btnSearchLabel = btnSearchLabelText;
            }
            if (sd.util.isValid(searchUrl)) {
                pickerParams.searchUrl = searchUrl;
            }
            pickerParams.gridUrl = gridUrl;
            pickerParams.valueField = valueField;
            if (sd.util.isValid(hiddenFields)) {
                pickerParams.hiddenFields = hiddenFields.split(",");
            }
            pickerParams.gridStructure = this.structure;
            if (sd.util.isValid(required) && (required == "true" || required == true)) {
                pickerParams.vt_required = true;
            }
            var dataPopup = new vt.dataPicker.dataPicker(pickerParams);
            dojo.byId(idDiv).appendChild(dataPopup.domNode);
            if (sd.util.isValid(maxLength)) {
                dojo.byId(id).maxLength = maxLength;
            }
        } catch (e) {
            alert("Error in sd.widget.loader.loadDataPicker:\n" + e.message);
        }
    },

    loadCKEditor : function(/*String*/ id) {
        try {
            if(sd.operator.allowedToExecJS == false) {
                return;
            }

            var locale = sd.util.isValidS(sd.operator.getLocale()) ? sd.operator.getLocale() : "en";
            
            CKEDITOR.replace(id,
            {
                language:
                    locale,
                filebrowserBrowseUrl :
                    sd.operator.path["PlugInFolder"] + '/ckfinder/ckfinder.html',
                filebrowserImageBrowseUrl :
                    sd.operator.path["PlugInFolder"] + '/ckfinder/ckfinder.html?type=Images',
                filebrowserFlashBrowseUrl :
                    sd.operator.path["PlugInFolder"] + '/ckfinder/ckfinder.html?type=Flash&currentFolder=uploadFlash',
                filebrowserUploadUrl :
                    sd.operator.path["context"] + '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
                filebrowserImageUploadUrl :
                    sd.operator.path["context"] + '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
                filebrowserFlashUploadUrl :
                    sd.operator.path["context"] + '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
            });
        } catch(e) {
            alert("Error in sd.widget.loader.loadCKEditor: " + e.message);
        }
    }
};
/*
 * cryptoHelpers.js: implements AES - Advanced Encryption Standard
 * from the SlowAES project, http://code.google.com/p/slowaes/
 *
 * Copyright (c) 2008   Josh Davis ( http://www.josh-davis.org ),
 *                                              Mark Percival ( http://mpercival.com ),
 *                                              Johan Sundstrom ( http://ecmanaut.blogspot.com ),
 *                                              John Resig ( http://ejohn.org )
 *
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/
 */

cryptoHelpers = {
    
    
    // encodes a unicode string to UTF8 (8 bit characters are critical to AES functioning properly)
    encode_utf8:function(s) {
        try{
            return unescape(encodeURIComponent(s));
        }
        catch(e){
            throw 'error during utf8 encoding: cryptoHelpers.encode_utf8.';
        }
    },

    // decodes a UTF8 string back to unicode
    decode_utf8:function(s) {
        try{
            return decodeURIComponent(escape(s));
        }
        catch(e){
            throw('error during utf8 decoding: cryptoHelpers.decode_utf8.');
        }
    },

    //convert a array of bytes to a hex string
    toHex:function() {
        var array = [];
        if(arguments.length == 1 && arguments[0].constructor == Array)
            array = arguments[0];
        else
            array = arguments;
        var ret = '';
        for(var i = 0;i < array.length;i++)
            ret += (array[i] < 16 ? '0' : '') + array[i].toString(16);
        return ret.toLowerCase();
    },

    //convert a hex string to an array of bytes
    toNumbers:function(s) {
        var ret = [];
        s.replace(/(..)/g,function(s){
            ret.push(parseInt(s,16));
        });
        return ret;
    },

    // get a random number in the range [min,max]
    getRandom:function(min,max) {
        if(min === null)
            min = 0;
        if(max === null)
            max = 1;
        return Math.floor(Math.random()*(max+1)) + min;
    },

    generateSharedKey:function(len)
    {
        if(len === null)
            len = 16;
        var key = [];
        for(var i = 0; i < len*2; i++)
            key.push(this.getRandom(0,255));
        return key;
    },

    generatePrivateKey:function(s,size)
    {
        var sha = jsHash.sha2.arr_sha256(s);
        return sha.slice(0,size);
    },

    convertStringToByteArray: function(s)
    {
        var byteArray = [];
        for(var i = 0;i < s.length;i++)
        {
            byteArray.push(s.charCodeAt(i));
        }
        return byteArray;
    },

    convertByteArrayToString: function(byteArray)
    {
        var s = '';
        for(var i = 0;i < byteArray.length;i++)
        {
            s += String.fromCharCode(byteArray[i])
        }
        return s;
    },

    base64: {
        // Takes a Nx16x1 byte array and converts it to Base64
        chars: [
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z', '0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9', '+', '/',
        '=', // for decoding purposes
        ],

        encode_line: function(flatArr){
            var b64 = '';

            for (var i = 0; i < flatArr.length; i += 3){
                b64 += this.chars[flatArr[i] >> 2];
                b64 += this.chars[((flatArr[i] & 3) << 4) | (flatArr[i + 1] >> 4)];
                if (!(flatArr[i + 1] == null)){
                    b64 += this.chars[((flatArr[i + 1] & 15) << 2) | (flatArr[i + 2] >> 6)];
                }else{
                    b64 += '=';
                }
                if (!(flatArr[i + 2] == null)){
                    b64 += this.chars[flatArr[i + 2] & 63];
                }else{
                    b64 += '=';
                }
            }
            return b64;
        },

        encode: function(flatArr)
        {
            var b64 = this.encode_line(flatArr);
            // OpenSSL is super particular about line breaks
            var broken_b64 = b64.slice(0, 64) + '\n';
            for (var i = 1; i < (Math.ceil(b64.length / 64)); i++)
            {
                broken_b64 += b64.slice(i * 64, i * 64 + 64) + (Math.ceil(b64.length / 64) == i + 1 ? '': '\n');
            }
            return broken_b64;
        },

        decode: function(string)
        {
            string = string.replace(/[\r\n\t ]+/g, '') + '===='; // drop all whitespaces and pad with '=' (end of b64 marker)
            var flatArr = [];
            var c = [];
            var b = [];
            for (var i = 0; ; i = i + 4){
                c[0] = this.chars.indexOf(string.charAt(i));
                if(c[0] == 64){
                    return flatArr;
                }
                c[1] = this.chars.indexOf(string.charAt(i + 1));
                c[2] = this.chars.indexOf(string.charAt(i + 2));
                c[3] = this.chars.indexOf(string.charAt(i + 3));

                if(
                    (c[0] < 0) || // char1 is wrong
                    (c[1] < 0) || (c[1] == 64) || // char2 is wrong
                    (c[2] < 0) || // char3 is neither an valid char nor '='
                    (c[3] < 0)    // char4 is neither an valid char nor '='
                    ){
                    throw 'error during base64 decoding at pos '+i+': cryptoHelpers.base64.decode.';
                }

                flatArr.push((c[0] << 2) | (c[1] >> 4));
                if(c[2] >= 0 && c[2] < 64){
                    flatArr.push(((c[1] & 15) << 4) | (c[2] >> 2));
                    if(c[3] >= 0 && c[2] < 64){
                        flatArr.push(((c[2] & 3) << 6) | c[3]);
                    }
                }
            }
        }

    }

};
function updatePageInfo( userName, actionTitle ) {
        try {
            updateActionInfo( actionTitle );
            profileIcon.innerHTML = userName;
        } catch(e) {
            alert("JSException in updatePageInfo: \n" + e.message);
        }
    }

    function updateDocumentTitle( docTitle ) {
        document.title = docTitle;
    }

    function updateActionInfo( actionTitle ) {
        sd.$( "vt-titleAction" ).innerHTML = actionTitle;
    }

    function makeMenu( menuModel ) {
        try {
          
            cmDraw ('myMenuID', menuModel, 'hbr', cmThemePanel);
        } catch(e) {
            alert("JSException when create the menu: \n" + e.message);
        }
    }

    function checkToRefresh(event) {
        var keyID = (window.event) ? window.event.keyCode : event.keyCode;
        if (keyID == 116) {
            try{
                cancelEvent(event);
                doGoToMenu(g_latestClickedMenu);
                return false;
            }catch(ex){
                alert("Exception in checkToRefresh: \n" + ex.message);
            }
        }
    }

    function cancelEvent(evt) {
        var e = evt ? evt : window.event;
        if(e.stopPropagation)
            e.stopPropagation();
        if(e.preventDefault)
            e.preventDefault();
        e.cancelBubble = true;
        e.cancel = true;
        e.returnValue = false;
        return false;
    }

    //[ TienNT says: shortcuts to [sd/vt].[operator/connector] members @4Apr11
    function doGoToMenu() {
        var url, menuItemKey;
        switch(arguments.length) {
            case 1:
                url = arguments[0];
                menuItemKey = "";
                break;
            case 2:
                url = arguments[0];
                menuItemKey = arguments[1];
                break;
        }

        if( url && url.length > 0 ) {
            sd.operator.execMenu.call(sd.operator, url, menuItemKey);
        }
    }

    function doPost() {
        sd.connector.post.apply(sd.connector, arguments);
    }

    function doUpdateArea(inputParam) {
        sd.connector.updateArea.call(sd.connector, inputParam);
    }

    function doGetJSON(inputParam) {
        sd.connector.getJSON.call(sd.connector, inputParam);
    }

    function getCKE(/*String*/ id) {
        var ed = CKEDITOR.instances[id];
        return ed;
    }

    function getCKEValue(/*String*/ id) {
        var ed = getCKE(id);
        if(ed != null && ed != undefined) {
            return ed.getData();
        } else {
            return undefined;
        }
    }

    function deployCKE(/*String*/ id) {
        try {
            CKEDITOR.replace(id,
            {
                language:
                    'vi',
                filebrowserBrowseUrl :
                    sd.operator.path["PlugInFolder"] + '/ckfinder/ckfinder.html',
                filebrowserImageBrowseUrl :
                    sd.operator.path["PlugInFolder"] + '/ckfinder/ckfinder.html?type=Images',
                filebrowserFlashBrowseUrl :
                    sd.operator.path["PlugInFolder"] + '/ckfinder/ckfinder.html?type=Flash',
                filebrowserUploadUrl :
                    sd.operator.path["context"] + '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
                filebrowserImageUploadUrl :
                    sd.operator.path["context"] + '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
                filebrowserFlashUploadUrl :
                    sd.operator.path["context"] + '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
            });
        } catch(e) {
            alert("JSEx, include.jsp, deployCKE: " + e.message);
        }
    }

    function delCKE(/*String*/ id) {
        var ed = getCKE(id);
        if(ed != null && ed != undefined) {
            try{
                ed.destroy();
            } catch(e) {
                ed.destroy();
            }
        }
    }
