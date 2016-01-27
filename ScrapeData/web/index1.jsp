<%-- 
    Document   : index
    Created on : Dec 7, 2013, 11:35:36 PM
    Author     : KeyLove
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <sx:head/>
    </head>
    <body>
        <h1>Hello World!</h1>
        <s:form>
      <sx:autocompleter label="Favourite Colour"
         list="{'red','green','blue'}" />
      <br />
      <sx:datetimepicker name="deliverydate" label="Delivery Date"
         displayFormat="dd/MM/yyyy" value="%{'today'}"/>
      
      <br/>
      <sx:tabbedpanel id="tabContainer">
         <sx:div label="Tab 1">Tab 1</sx:div>
         <sx:div label="Tab 2">Tab 2</sx:div>
      </sx:tabbedpanel>
   </s:form>

         <table>
        <tr>
            <td width="40%">Ngày lấy báo cáo:<font color="red">(*)</font></td>
            <td>
                <sx:datetimepicker id="reportDate" name="reportDate" displayFormat="dd/MM/yyyy"/>
            </td>
            <td>
                <sx:submit  parseContent="true" executeScripts="false"
                            formId="reportLaptopForm" loadingText="Đang thực hiện..."
                            targets="bodyContent" afterNotifyTopics="reportLaptop/after"
                            value="Report" beforeNotifyTopics="reportLaptop/report"/>
            </td>
        </tr>

    </table>
    </body>
</html>
