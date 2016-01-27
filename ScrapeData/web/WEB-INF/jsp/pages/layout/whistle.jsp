<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("CSS_JS_contextPath", request.getContextPath());
%>

<img src="${contextPath}/share/images/layout/err_404_2.jpg" />
<div class="global-error">
    Oops! You have just access to undefined action. <br/>
    Please check your action-configuration again.
</div>
