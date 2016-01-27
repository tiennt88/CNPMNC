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
