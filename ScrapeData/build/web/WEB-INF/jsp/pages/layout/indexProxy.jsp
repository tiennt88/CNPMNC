<%-- 
    Document   : indexProxy
    Created on : Apr 1, 2011, 2:41:35 PM
    Author     : cn_TienNT
--%>

<%@page import="java.util.ResourceBundle"%>
<%
            ResourceBundle rb = ResourceBundle.getBundle("config");
            String defLocale = rb.getString("def.Locale");
            response.sendRedirect(request.getContextPath() + "/Index.do?request_locale=" + defLocale);
%>

