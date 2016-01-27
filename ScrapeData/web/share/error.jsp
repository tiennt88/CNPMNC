<%-- 
    Document   : error
    Created on : Jun 22, 2011, 2:33:02 PM
    Author     : cn_TienNT
--%>

<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.ResourceBundle"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
            ResourceBundle bundle = ResourceBundle.getBundle("cas");
            request.setAttribute("logoutUrl", bundle.getString("logoutUrl") + "?appCode=" + bundle.getString("domainCode") + "&service=" + URLEncoder.encode(bundle.getString("service"), "UTF-8"));
            request.setAttribute("loginUrl", bundle.getString("loginUrl") + "?appCode=" + bundle.getString("domainCode") + "&service=" + URLEncoder.encode(bundle.getString("service"), "UTF-8"));
            request.setAttribute("errorCode", request.getParameter("errorCode"));

            if(request.getAttribute("errorCode").equals("InvalidToken")) {
                response.setHeader("VSA-Flag", "NewPageRedirect");
                response.setHeader("VSA-Location", "share/error.jsp?errorCode=" + request.getAttribute("errorCode"));
            }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>VSA Error JSP Page</title>
        <script>
            function $(id) {
                return document.getElementById(id);
            }
            
            function getTopParent(child) {
                var oldestParent;
                if(child === child.parent) {
                    oldestParent = child;
                } else {
                    oldestParent = getTopParent(child.parent);
                }
                return oldestParent;
            }
        </script>
    </head>
    <body>
        <h1 id="content">${errorCode}</h1>
        <script type="text/javascript">
            var errorCode = "${errorCode}";//SessionTimeout
            var _parent = getTopParent(window);
            var contentH1 = $("content");

            switch(errorCode) {
                case "SessionTimeout":
                    contentH1.innerHTML = "Redirecting...";
                    _parent.document.location.href = "${loginUrl}";
                    break;
                case "InvalidToken":
                case "AuthenticateFailure":
                case "NotPermissionAction":
                    if(window !== _parent) {
                        _parent.open(document.location.href, "_blank", "width=800, height=600");
                    }
                    break;
                default:
                    break;
                }
        </script>
    </body>
</html>
