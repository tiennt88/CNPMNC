<%@page import="java.util.ResourceBundle"%>
<%
            ResourceBundle rb = ResourceBundle.getBundle("config");
            String defLocale = rb.getString("def.Locale");
            response.sendRedirect(request.getContextPath() + "/checkboxlist.do");
%>
