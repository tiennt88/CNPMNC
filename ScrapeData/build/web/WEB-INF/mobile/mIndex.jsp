<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjm" uri="/struts-jquery-mobile-tags"%>
<jsp:include page="inc.header.jsp" />
<sjm:div role="page" id="start">
    <sjm:div role="header">
        <h1>WMS MOBILE REPORT</h1>
    </sjm:div>

    <sjm:div role="content">
        <tiles:insertAttribute name="mbody"/>
    </sjm:div>

    <jsp:include page="inc.footer.jsp" />
</sjm:div>
</body>
</html>
