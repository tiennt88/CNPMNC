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