<%@page import="java.util.ResourceBundle"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Locale"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>


<%
    request.setAttribute("externalResourcesPath", request.getAttribute("contextPath"));
    request.setAttribute("contextPath", request.getAttribute("contextPath"));
    request.setAttribute("PlugInFolderName", request.getAttribute("externalResourcesPath") + "/struts/share/plug-in");
    request.setAttribute("ImgFolderName", request.getAttribute("externalResourcesPath") + "/share/images");
    request.setAttribute("CSSFolderName", request.getAttribute("externalResourcesPath") + "/share/css");
    request.setAttribute("JSVTFolderName", request.getAttribute("externalResourcesPath") + "/share/js");
    request.setAttribute("JSLibFolderName", request.getAttribute("externalResourcesPath") + "/struts/share/plug-in/dojo-release-1.4.0");

    //request.setAttribute("JSLibFolderName", request.getAttribute("externalResourcesPath") + "/share/plug-in/dojosrc/release/dojo");
    request.setAttribute("UploadFolderName", request.getAttribute("externalResourcesPath") + "/share/upload");
    request.setAttribute("UploadImageFolderName", "share/uploadImage");
    request.setAttribute("UploadFlashFolderName", request.getAttribute("externalResourcesPath") + "/share/uploadFlash");
    //tiennt add plug-in
    request.setAttribute("Plugin", request.getAttribute("externalResourcesPath") + "/share/plug-in");
    request.setAttribute("Upload", request.getAttribute("externalResourcesPath") + "/share/blueimp-upload");
    request.setAttribute("bootstrap", request.getAttribute("externalResourcesPath") + "/share/bootstrap");
    //ResourceBundle bundle = ResourceBundle.getBundle("cas");
    //request.setAttribute("logoutUrl", bundle.getString("logoutUrl") + "?appCode=" + bundle.getString("domainCode") + "&service=" + URLEncoder.encode(bundle.getString("service"), "UTF-8"));
    //request.setAttribute("loginUrl", bundle.getString("loginUrl") + "?appCode=" + bundle.getString("domainCode") + "&service=" + URLEncoder.encode(bundle.getString("service"), "UTF-8"));
%>

<!--[ RDWF css -->
<style type="text/css">
    @font-face {
        font-family: myFont;
        src: url(${CSSFolderName}/banque.ttf);
    }

    @font-face {
        font-family: slogan;
        src: url(${CSSFolderName}/pie.ttf);
    }
    
</style>

<link href="${ImgFolderName}/layout/new_3_3/favicon.ico" type="image/x-icon" rel="shortcut icon" />

<link href="${CSSFolderName}/layout.css" charset="UTF-8" type="text/css" rel="stylesheet" />
<link href="${CSSFolderName}/inner-layout.css" charset="UTF-8" type="text/css" rel="stylesheet" />
<link href="${CSSFolderName}/CustomTree.css" type="text/css" rel="stylesheet" />
<!--] -->

<link href="${JSLibFolderName}/dojox/layout/resources/ExpandoPane.css" type="text/css" rel="stylesheet" />
<link href="${JSLibFolderName}/dojo/resources/dojo.css" type="text/css" rel="stylesheet" />
<link href="${JSLibFolderName}/dojox/grid/resources/${JSLibTheme}Grid.css" type="text/css" rel="stylesheet" />
<link href="${JSLibFolderName}/dijit/themes/${JSLibTheme}/${JSLibTheme}.css" type="text/css" rel="stylesheet" />
<link href="${JSLibFolderName}/dijit/themes/${JSLibTheme}/${JSLibTheme}Mod.css" type="text/css" rel="stylesheet" />

<link href="${JSLibFolderName}/vt/css/dataPicker.css" type="text/css" rel="stylesheet" />
<link href="${JSLibFolderName}/vt/css/treePicker.css" type="text/css" rel="stylesheet" />

<!--[ JSCookMenu -->
<link href="${PlugInFolderName}/jscookmenu-2.0.4/ThemePanel/theme.css" type="text/css" rel="stylesheet" />
<script src="${PlugInFolderName}/jscookmenu-2.0.4/JSCookMenu_min.js" type="text/javascript"></script>
<script src="${PlugInFolderName}/jscookmenu-2.0.4/effect_min.js" type="text/javascript"></script>
<script src="${PlugInFolderName}/jscookmenu-2.0.4/ThemePanel/theme_min.js" type="text/javascript"></script>
<!--] -->
<!--TienNT confirm : confick with struts_dojo.js-->
<script src="${JSLibFolderName}/dojo/dojo.js" djConfig="parseOnLoad:true"  type="text/javascript"></script>
<script src="${JSLibFolderName}/dijit/dijit.js" type="text/javascript"></script>
<script src="${JSLibFolderName}/dojox/dojox.js" type="text/javascript"></script>
<script src="${JSLibFolderName}/vt/vt-all.js" type="text/javascript"></script>
<script src="${JSVTFolderName}/vt-ext.js" type="text/javascript"></script>

<!--tiennt add-->
<link href="${Plugin}/emergejs.css" type="text/css" rel="stylesheet" />
<script src="${Plugin}/emerge.js" type="text/javascript"></script>
<!--end tien add-->

<script type="text/javascript">
    sd.operator.setLocale("${vt_locale}");
</script>

<script type="text/javascript">
    //var vt, page, app, vtSecurity;
    var page, app, vtSecurity;
    function parallel() { 
        sd.operator.setMenuTargetId("bodyContent");
        sd.operator.setTimeout(500);
        sd.operator.setDebugMode(true);
        //] Project team modify here

        sd.operator.path["context"] = "${contextPath}";
        //alert(sd.operator.path["context"]);
        sd.operator.path["externalResources"] = "${externalResourcesPath}";
        sd.operator.path["PlugInFolder"] = "${PlugInFolderName}";
        sd.operator.path["ImgFolder"] = "${ImgFolderName}";
        sd.operator.path["CSSFolder"] = "${CSSFolderName}";
        sd.operator.path["JSVTFolder"] = "${JSVTFolderName}";
        sd.operator.path["JSLibFolder"] = "${JSLibFolderName}";

        sd.operator.path["UploadFolder"] = "${UploadFolderName}";
        sd.operator.path["UploadImageFolder"] = "${UploadImageFolderName}";
        sd.operator.path["UploadFlashFolder"] = "${UploadFlashFolderName}";

        sd.operator.logoutURL = "${logoutUrl}";
        sd.operator.loginURL = "${loginUrl}";

        sd.log.level = sd.log.TRACE;
        sd.log.logPrefix = "JSException has been thrown \n";
        sd.validator.stackPrefix = "vt-validator-stack-";
    
        page = sd.page; // previous code compatible
        app = sd.app;

       
        fwlog = function(id,message,e){
            console.log("[Control ID:"+id+". "+message);
            if (e) console.log(e);
            console.log("]");
        }
    }
    parallel();
</script>