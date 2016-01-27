
function updatePageInfo( userName, actionTitle ) {
        try {
            updateActionInfo( actionTitle );
            profileIcon.innerHTML = userName;
        } catch(e) {
            alert("JSException in updatePageInfo: \n" + e.message);
        }
    }

    function updateDocumentTitle( docTitle ) {
        document.title = docTitle;
    }

    function updateActionInfo( actionTitle ) {
        sd.$( "vt-titleAction" ).innerHTML = actionTitle;
    }

    function makeMenu( menuModel ) {
        try {
            cmDraw ('myMenuID', menuModel, 'hbr', cmThemePanel);
        } catch(e) {
            alert("JSException when create the menu: \n" + e.message);
        }
    }

    function checkToRefresh(event) {
        var keyID = (window.event) ? window.event.keyCode : event.keyCode;
        if (keyID == 116) {
            try{
                cancelEvent(event);
                doGoToMenu(g_latestClickedMenu);
                return false;
            }catch(ex){
                alert("Exception in checkToRefresh: \n" + ex.message);
            }
        }
    }

    function cancelEvent(evt) {
        var e = evt ? evt : window.event;
        if(e.stopPropagation)
            e.stopPropagation();
        if(e.preventDefault)
            e.preventDefault();
        e.cancelBubble = true;
        e.cancel = true;
        e.returnValue = false;
        return false;
    }

    //[ TienNT says: shortcuts to [sd/vt].[operator/connector] members @4Apr11
    function doGoToMenu() {
        var url, menuItemKey;
//        alert('co vao day ko');
        switch(arguments.length) {
            case 1:
                url = arguments[0];
                menuItemKey = "";
                break;
            case 2:
                url = arguments[0];
                menuItemKey = arguments[1];
                break;
        }

        if( url && url.length > 0 ) {
            sd.operator.execMenu.call(sd.operator, url, menuItemKey);
        }
    }

    function doPost() {
        sd.connector.post.apply(sd.connector, arguments);
    }

    function doUpdateArea(inputParam) {
        sd.connector.updateArea.call(sd.connector, inputParam);
    }

    function doGetJSON(inputParam) {
        sd.connector.getJSON.call(sd.connector, inputParam);
    }

    function getCKE(/*String*/ id) {
        var ed = CKEDITOR.instances[id];
        return ed;
    }

    function getCKEValue(/*String*/ id) {
        var ed = getCKE(id);
        if(ed != null && ed != undefined) {
            return ed.getData();
        } else {
            return undefined;
        }
    }

    function deployCKE(/*String*/ id) {
        try {
            CKEDITOR.replace(id,
            {
                language:
                    'vi',
                filebrowserBrowseUrl :
                    sd.operator.path["PlugInFolder"] + '/ckfinder/ckfinder.html',
                filebrowserImageBrowseUrl :
                    sd.operator.path["PlugInFolder"] + '/ckfinder/ckfinder.html?type=Images',
                filebrowserFlashBrowseUrl :
                    sd.operator.path["PlugInFolder"] + '/ckfinder/ckfinder.html?type=Flash',
                filebrowserUploadUrl :
                    sd.operator.path["context"] + '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
                filebrowserImageUploadUrl :
                    sd.operator.path["context"] + '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
                filebrowserFlashUploadUrl :
                    sd.operator.path["context"] + '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
            });
        } catch(e) {
            alert("JSEx, include.jsp, deployCKE: " + e.message);
        }
    }

    function delCKE(/*String*/ id) {
        var ed = getCKE(id);
        if(ed != null && ed != undefined) {
            try{
                ed.destroy();
            } catch(e) {
                ed.destroy();
            }
        }
    }