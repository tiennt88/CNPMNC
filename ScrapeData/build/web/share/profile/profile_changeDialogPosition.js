/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

dojo.require("vt.dialog.vtDialog");

vt.dialog.vtDialog.prototype._position = function(){
        // summary:
        //		Position modal dialog in the viewport. If no relative offset
        //		in the viewport has been determined (by dragging, for instance),
        //		center the node. Otherwise, use the Dialog's stored relative offset,
        //		and position the node to top: left: values based on the viewport.
        // tags:
        //		private
        if(!dojo.hasClass(dojo.body(),"dojoMove")){
            var node = this.domNode,
            viewport = dijit.getViewport(),
            p = this._relativePosition,
            bb = p ? null : dojo._getBorderBox(node),
            l = Math.floor(viewport.l + (p ? p.x : (viewport.w - bb.w) / 2)),
            t = Math.floor(viewport.t + (p ? p.y : (viewport.h - bb.h) / 2))
            ;

            // [ TienNT @ 08Jan10
            var vt_t, vt_l, vt_scrXY;
            vt_scrXY = sd.util.getScrollXY();

            vt_l = vt_scrXY[0] + l;
            vt_t = vt_scrXY[1] + 150;

            l = vt_l;
            t = vt_t;
            // ] TienNT @ 08Jan10

            dojo.style(node,{
                left: l + "px",
                top: t + "px"
            });
        }
    };

