<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="java.util.ResourceBundle"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.net.URLEncoder"%>

<%
    ResourceBundle bundle = ResourceBundle.getBundle("cas");
    request.setAttribute("logoutUrl", bundle.getString("logoutUrl") + "?service=" + URLEncoder.encode(bundle.getString("service"), "UTF-8"));
    String logoutUrl = bundle.getString("logoutUrl") + "?service=" + URLEncoder.encode(bundle.getString("service"), "UTF-8");
    request.getSession().invalidate();
    response.sendRedirect(logoutUrl);
%>