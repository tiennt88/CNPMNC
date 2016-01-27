<%-- 
    Document   : download
    Created on : Mar 10, 2014, 11:35:12 AM
    Author     : KeyLove
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="sd" uri="struts-dojo-tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:if test="#request.attachFile != null">
     <s:url id="urlDownload" action="download">
        <s:param name = "filePath"><s:property value="#request.attachFile"/></s:param>
     </s:url>
    <s:a id="linkDown" href="%{urlDownload}">Click vào đây để download nếu không thấy tự động tải file về...!</s:a>
 </s:if>
<script type="text/javascript">
    document.getElementById("linkDown").click();
</script>