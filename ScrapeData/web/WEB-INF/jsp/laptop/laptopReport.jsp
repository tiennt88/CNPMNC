<%-- 
    Document   : laptopReport
    Created on : Apr 12, 2015, 3:48:18 AM
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

<sd:TitlePane id="laptopReport"  key="Thông tin">
    <s:form  id="laptopReportForm"  name="laptopReportForm"  method="POST">
        <table width="100%">
            <tr>
                <td>
                    <sd:SelectBox id="web" key="Web" name="laptop.web" data="webs" valueField="value" labelField="name" required="true"/>
                </td>
                <td>
                    <sd:DatePicker id="date" name="date" key="Ngày cập nhật"  required="true" format="dd/MM/yyyy"/>
                </td>
                <td width="40%"></td>
            </tr>

            <tr>
                <td colspan ="3" align="center">
                    <sd:Button key="Export" onclick="page.exportExcel()">
                            <img src="share/Img/Icon/export.png" height="17" width="20">
                    </sd:Button>
                </td>
            </tr>
        </table>
    </s:form>
    <div id="result" ></div>
</sd:TitlePane>
<br>



<script>
    dijit.byId('date').attr('value',new Date());
    dijit.byId("web").setAttribute('value', '');

    page.exportExcel = function(){
//        window.location = "laptopModelCompareExport!exportExcel.do";
        sd.connector.post("laptopReportExport!exportExcel.do","result","laptopReportForm");
    }

</script>
