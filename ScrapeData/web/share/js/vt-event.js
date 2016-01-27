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