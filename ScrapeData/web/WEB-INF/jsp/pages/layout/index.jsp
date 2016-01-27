<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="java.util.Locale"%>
<%@page import="java.util.ResourceBundle"%>

<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@taglib prefix="sd" uri="struts-dojo-tags"%>
<%--<%@taglib prefix="sjm" uri="/struts-jquery-mobile-tags"%>--%>


<%
            ResourceBundle rb = ResourceBundle.getBundle("config");

            String version = rb.getString("version");
            String theme = rb.getString("theme");

            String requestTheme = request.getParameter("request_theme");

            request.setAttribute("vt_locale", request.getParameter("request_locale"));
            request.setAttribute("JSLibTheme", (requestTheme != null) ? requestTheme : theme);
            request.setAttribute("version", version);
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("CSS_JS_contextPath", request.getContextPath());
            
%>

<s:i18n name="com/scrape/config/language/Language">
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
    <html>
        <head>
            <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
            <!--            <meta name="description" content="FPT Store" />-->
            <title>FPT TRADING GROUP - FTG</title>
            <sj:head />
            <script type="text/javascript">
                var g_contextPath = "${contextPath}";
                var g_latestClickedMenu = "";
                var targetDivInBodyLayout;
                var returnPageType;
                //[ Testing purpose
                var useMenuTest = true;
                var userProfileTest = false;
                //] Testing purprose
            </script>

            <!--[ TienNT says: Main static resources (JS/CSS/IMG/HTML)-->
            <tiles:insertTemplate template="/WEB-INF/jsp/pages/layout/include.jsp" />
            <!--] TienNT says: Main static resources (JS/CSS/IMG/HTML)-->
        </head>

        <body class="${JSLibTheme}" >
            <!--[ TienNT says: RDFW features-->
            <div id="message-holder"></div>
            <!--] TienNT says: RDFW features-->
            <div id="token"></div>
            <!--[ TienNT says: Main header-->
            <div id="vt-header-wrapper" class="no-space">
                <div id="vt-projectBar" class="no-space">
                    <div id="vt-projectName" class="no-space float-left">Hệ thống phân tích giá thị trường</div>

                    <!--[ Language bar -->
                    <div id="vt-languageBar" class="no-space float-right">
                        <s:url id="url1" action="Index">
                            <s:param name="request_locale">en</s:param>
                        </s:url>
                        <s:url id="url2" action="Index">
                            <s:param name="request_locale">vi_VN</s:param>
                        </s:url>
                        <s:a href="%{url1}"><div id="vt-lang-en" class="float-right cursor-pointer vt-lang"></div></s:a>
                        <s:a href="%{url2}"><div id="vt-lang-vn" class="float-right cursor-pointer vt-lang"></div></s:a>
                    </div>
                    <!--] Language bar -->

                    <div id="vt-projectInfo" class="no-space no-float">
                        <div id="vt-slogan" class="no-space float-left">Vession 1.0</div>
                        <div id="vt-projectVersion" class="no-space float-right"><span id="date_time"></span></div>
                    </div>
                </div>

                <div id="vt-menuBar" class="no-space no-float">
                    <div id="myMenuID" class="float-left"></div>
                </div>

                <div id="vt-titleBar" class="no-space">
                    <div id="vt-iconAction" class="no-space">
                        <div id="vt-titleAction" class="no-space float-left"><s:property value="getText('loadingText')"/></div>

                        <!--] Dashboard (User bar)-->
                        <div id="vt-logout" class="float-right cursor-pointer vt-icon">Logout</div>
                        <div id="vt-profile" class="float-right cursor-pointer vt-icon">User</div>
                        <div id="vt-home" class="float-right cursor-pointer vt-icon">Home</div>
                        <!--] Dashboard (User bar)-->

                        <div id="vt-breadCumb" class="no-float no-space">Root</div>
                    </div>
                </div>
            </div>
            <!--] TienNT says: Main header-->

            <!--[ TienNT says: Main middle-->
            <div id="vt-content-wrapper" class="no-space clear-both">
                <div id="vt-content" class="no-space">
                    <div id="bodyContent">
                        <tiles:insertAttribute name="body"/>
                    </div>
                    <div class="clear-both"></div>
                </div>
            </div>
            <!--] TienNT says: Main middle-->

            <!--[ TienNT says: Main footer-->
            <div id="vt-footer" class="no-space clear-both">
                <div id="vt-copyRite" class="float-left">
                    &copy; BAN CÔNG NGHỆ FTG
                </div>
                <div id="vt-poweredByIcon" class="float-right">
                    <div id="vt-poweredByText">&copy;FPT TRADING GROUP - Copyright 2014</div>
                </div>
            </div>
            <!--] TienNT says: Main footer-->

            <!--[ TienNT says: RDFW features-->
            <div id="vt-loading-background"><div id="vt-loading-icon"></div></div>
            <div id="componentBody" style="display:none;"></div>
            <!--] TienNT says: RDFW features-->

            <!--[ TienNT says: Make menu-feed script-->
            <tiles:insertAttribute name="menu"/>
            <!--] TienNT says: Make menu-feed script-->
            <%--sd:Dialog id="vt-errorDisplayDialog" key="errorDisplayDialogDefaultTitle">
                <div id="vt-errorDisplayDiv"></div>
                <iframe name="vt-errorDisplayIframe" id="vt-errorDisplayIframe" frameborder="0"></iframe>
            </sd:Dialog--%>
            <!--           Tiennt add Notification -->
            <div id="errorNotification" class="notification"></div>
            <div id="infoNotification" class="notification"></div>
            <div id="successNotification" class="notification"></div>

            <!--[ TienNT says: After-page-rendered script-->
            <script type="text/javascript">
                sd.operator.waitScreenDivId = "vt-loading-background";
                sd.operator.breadCumbDivId = "vt-breadCumb";
//                sd.operator.errorDisplayDialogId = "vt-errorDisplayDialog";
//                sd.operator.errorDisplayIframeId = "vt-errorDisplayIframe";
//                sd.operator.errorDisplayDivId = "vt-errorDisplayDiv";

                sd.operator.getWaitScreenDiv();

                var homeIcon = sd.$( "vt-home" );
                var logoutIcon = sd.$( "vt-logout" );
                var profileIcon = sd.$( "vt-profile" );

                var actionTitle = "<sd:Property><tiles:getAsString name='title'/></sd:Property>";
                var userName;

            //            if(use    rProfileTest) {
                userName = "<s:property value='#session.username'/>";
                //            } else {
                //                userName = "<s:property value='#session.userToken'/>";
                //            }

                homeIcon.onclick = function() {
                    document.location.href = "${contextPath}/Index.do?request_locale=${fn:escapeXml(vt_locale)}";
                };

                logoutIcon.onclick = function() {
                    document.location.href = "${contextPath}/logout.do";
                };

                profileIcon.onclick = function() {
                    //                document.location.href = "${contextPath}/userPopup.do";
                    openPopup("userPopup.do",450,220);
                };

                updatePageInfo(userName, actionTitle);

                try{
                    var msg = new vt.dialog.innerDialog();
                    var appState = new ApplicationState("", "");
                    dojo.back.setInitialState(appState);
                }catch(e){
                    alert("Exception in loading vt.dialog.vtDialog\n" + e.message);
                }

            </script>
            <!--] TienNT says: After-page-rendered script-->
            <script>
                function openPopup(path,width,height) {
                    var left = (screen.width/2)-(width/2);
                    var top = (screen.height/2)-(height/2);
                    my_window = window.open(path,'',"location=" + (screen.width) + ",status=1, scrollbars=1, titlebar=0,top="+top+", left="+left+", width=" +width + ",height=" + height);
                    my_window.focus();
                    return false;
                }

                function openTab(path) {
                    my_window = window.open(path);
                    my_window.focus();
                    return false;
                }

                window.onload = date_time('date_time');
                function date_time(id)
                {
                    date = new Date;
                    year = date.getFullYear();
                    month = date.getMonth();
                    //                    months = new Array('January', 'February', 'March', 'April', 'May', 'June', 'Jully', 'August', 'September', 'October', 'November', 'December');
                    months = new Array('01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12');
                    d = date.getDate();
                    if(d<=9){
                        d = "0" + d;
                    }
                    day = date.getDay();
                    //                    days = new Array('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday');
                    days = new Array('Chủ Nhật', 'Thứ 2', 'Thứ 3', 'Thứ 4', 'Thứ 5', 'Thứ 6', 'Thứ 7');
                    h = date.getHours();
                    if(h<10)
                    {
                        h = "0"+h;
                    }
                    m = date.getMinutes();
                    if(m<10)
                    {
                        m = "0"+m;
                    }
                    s = date.getSeconds();
                    if(s<10)
                    {
                        s = "0"+s;
                    }
                    //                    result = ''+days[day]+' '+months[month]+' '+d+' '+year+' '+h+':'+m+':'+s;
                    result = ''+days[day]+', '+d+'/'+months[month]+'/'+year+' '+h+':'+m+':'+s;
                    document.getElementById(id).innerHTML = result;
                    setTimeout('date_time("'+id+'");','1000');
                    return true;
                }
            </script>
        </body>
    </html>
</s:i18n>
