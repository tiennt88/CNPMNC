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
                                alert( "Error in post.load, \nsd.operator.freeWidgets, dojo.parser.parse:\n" + e.message );
                            }
                        }else{
                            console.log("sd.connector.post: The area with areaId = '" + _area + "' is null");
                        }

                        try {
                            alert('?');
                            var mixed = sd.operator.parse( response );
                            sd.operator.allowedToExecJS = true;
                            //alert('truoc---');
                            //alert(mixed.scripts);
                            sd.operator.execScript( mixed.scripts );
                            //alert('sau---');
                        } catch(e) {
                            alert( "Error in post.load, \nsd.operator.parse, sd.operator.execScript:\n" + e.message );
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