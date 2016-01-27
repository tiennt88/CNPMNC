<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib  prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sd" uri="struts-dojo-tags" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>


<s:i18n name="com/scrape/config/language/Language">
    <tiles:insertAttribute name="body"/>

    <script type="text/javascript">
        targetDivInBodyLayout="bodyContent";
        returnPageType="<sd:Property><tiles:getAsString name='type'/></sd:Property>";
        updateActionInfo( '<sd:Property><tiles:getAsString name="title"/></sd:Property>' );
        document.title = '<sd:Property><tiles:getAsString name="title"/></sd:Property>';
    </script>
</s:i18n>