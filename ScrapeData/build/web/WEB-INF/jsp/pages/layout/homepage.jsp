<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="sd" uri="struts-dojo-tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>

<% request.setAttribute("contextPath", request.getContextPath());%>

<script>
    doGoto = function(action) {
        sd.connector.post(action,"bodyContent",null,null);
    }
    
</script>

<div style=" height: 450px; clear: both;">
    <p style="font-size: 13px; font-weight: bold; padding-left: 15px; color: #0068c0;">
        <a id="1" href="">1. Giới Thiệu FTG Crawl Site Retail<a/>
        <br>
        <a id="2" href="">2. Hướng Dẫn Sử Dụng</a>
        <br>
        <a id="3" href="">3. Ghi Chú</a>
    </p>
</div>
