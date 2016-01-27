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