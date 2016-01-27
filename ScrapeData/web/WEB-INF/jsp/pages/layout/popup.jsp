<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="java.util.Locale"%>
<%@page import="java.util.ResourceBundle"%>


<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sd" uri="struts-dojo-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>

<%

    ResourceBundle rb = ResourceBundle.getBundle("config");
    //String projectVersion = rb.getString("version");
    //String RDWFVersion = rb.getString("version");
    String RDWFTheme = rb.getString("theme");

    String requestTheme = request.getParameter("request_theme");

    request.setAttribute("vt_locale", request.getParameter("request_locale"));
    request.setAttribute("JSLibTheme", (requestTheme != null) ? requestTheme : RDWFTheme);
    //request.setAttribute("projectVersion", projectVersion);
    //request.setAttribute("RDWFVersion", RDWFVersion);
    request.setAttribute("contextPath", request.getContextPath());
    request.setAttribute("CSS_JS_contextPath", request.getContextPath());
%>

<s:i18n name="com/scrape/config/language/Language">

    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
    <html>
        <head>
            <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
            <meta name="description" content="<s:property value="PopUp"/>FTG Group" />
            <title><s:property value="PopUp"/></title>
            <sj:head />
            <script type="text/javascript">
                var g_contextPath = "${contextPath}";

            </script>
            <!--[ TienNT says: Main static resources (JS/CSS/IMG/HTML)-->
            <tiles:insertTemplate template="/WEB-INF/jsp/pages/layout/include.jsp" />
            <!--] TienNT says: Main static resources (JS/CSS/IMG/HTML)-->
        </head>

        <body class="${JSLibTheme}" <%--onkeydown="checkToRefresh(event);"--%> >
            <div id="token">
            </div>

            <!--           Tiennt add Notification -->
        <div id="errorNotification" class="notification"></div>
        <div id="infoNotification" class="notification"></div>
        <div id="successNotification" class="notification"></div>

            <!--[ TienNT says: RDFW features-->
            <div id="message-holder"></div>
            <!--] TienNT says: RDFW features-->

            <!--[ TienNT says: Main middle-->
            <div id="vt-content-wrapper-popup" class="no-space clear-both">
                <div id="vt-content" class="no-space">
                    <div id="bodyContent">
                        <tiles:insertAttribute name="body"/>
                    </div>
                    <div class="clear-both"></div>
                </div>
            </div>
            <!--] TienNT says: Main middle-->

            <!--[ TienNT says: RDFW features-->
            <div id="vt-loading-background"><div id="vt-loading-icon"></div></div>
            <div id="componentBody" style="display:none;"></div>
            <!--] TienNT says: RDFW features-->

            <script type="text/javascript">
                sd.operator.waitScreenDivId = "vt-loading-background";
                sd.operator.getWaitScreenDiv();

                page.onExit = function(){
                   window.close();
                }
            </script>
        </body>
    </html>
</s:i18n>

