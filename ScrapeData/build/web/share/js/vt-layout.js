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