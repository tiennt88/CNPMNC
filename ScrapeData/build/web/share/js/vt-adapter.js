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
    
    var arrUrl=args.url.split("?");
    
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