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
            alert('?');
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

        if(!waitScreenDiv) {
            console.log("sd.operator.displayWaitScreen says: \n\twaitScreenDiv is not found.");
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
