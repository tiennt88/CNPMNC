<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib  prefix="s" uri="/struts-tags" %>

<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<s:i18n name="com/scrape/config/language/Language">
    <tiles:insertAttribute name="body"/>
</s:i18n>