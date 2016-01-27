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