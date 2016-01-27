<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="java.util.ResourceBundle"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Locale"%>
<%
    ResourceBundle rb = ResourceBundle.getBundle("config");
    request.setAttribute("RDWFisCryptParameter", rb.getString("RDWF.isCryptParameter"));
%>
<script type="text/javascript">
    sd.operator.keyString = "${keyString}";
    sd.operator.ivString = "${ivString}";
    sd.operator.isCryptParameter = ${RDWFisCryptParameter};
</script>