<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="java.util.ResourceBundle"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.net.URLEncoder"%>


<%
    Locale locale = new Locale("en","US");
    ResourceBundle bundle = ResourceBundle.getBundle("cas",locale);
    request.setAttribute("loginUrl", bundle.getString("loginUrl") + "?service=" + URLEncoder.encode(bundle.getString("service"), "UTF-8"));
%>
<script type="text/javascript">
    document.location.href = "${loginUrl}";
</script>