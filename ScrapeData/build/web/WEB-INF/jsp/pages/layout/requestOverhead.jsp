<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.ResourceBundle"%>
<%@taglib prefix="sd" uri="struts-dojo-tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


<div id="RequestMonitorNoticeBox" style="margin:30px; padding:20px;border:2px solid #755426; background:#efc080;color:red;font-size:large; font-family:Tahoma,Helvetica;text-align:center;">
    ${notice}
</div>
<script>
    alert( document.getElementById( "RequestMonitorNoticeBox" ).innerHTML );
</script>
