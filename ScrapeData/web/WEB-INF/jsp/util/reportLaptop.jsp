<%-- 
    Document   : reportLaptop
    Created on : Feb 28, 2014, 9:39:18 AM
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

<sd:TitlePane id="reportLaptop"  key="Báo Cáo Crawl Laptop">
    <s:form action="reportLaptop" id="reportLaptopForm"  name="reportLaptopForm"  method="POST" theme="simple">
        <table width ="100%">
            <tr>
                <td>
                    <font>Ngày lấy báo cáo: </font><font style="padding-right:15% ; color:red" >(*)</font><sj:datepicker value="today" id="reportDate" name="reportDate" displayFormat="dd/mm/yy" cssStyle="width:80px"/>
                   
                </td>
                <td>
                    <sd:CheckBox id="notebook.brand" checked="true" name="notebook.brand" key="Brand"/>
                </td>
                <td>
                    <sd:CheckBox id="notebook.serial" checked="true"  name="notebook.serial" key="Serial"/>
                </td>
                <td>
                    <sd:CheckBox id="notebook.chip" checked="true" name="notebook.chip" key="Chip"/>
                </td>
                <td>
                    <sd:CheckBox id="notebook.ram" checked="true" name="notebook.ram" key="Ram"/>
                </td>
                <td>
                    <sd:CheckBox id="notebook.hdd" checked="true" name="notebook.hdd" key="HDD"/>
                </td>
                <td>
                    <sd:CheckBox id="notebook.screen" checked="true" name="notebook.screen" key="Screen"/>
                </td>
                <td>
                    <sd:CheckBox id="notebook.vga" checked="false" name="notebook.vga" key="VGA"/>
                </td>
                <td>
                    <sd:CheckBox id="notebook.os" checked="false" name="notebook.os" key="os"/>
                </td>
                <td>
                    <sd:CheckBox id="notebook.speed" checked="false" name="notebook.speed" key="Speed"/>
                </td>
            </tr>
            <tr>
                <td colspan="10" align="center">
                    <sd:Button key="Report" onclick="page.report()">
                            <img src="share/Img/Icon/export.png" height="17" width="20">
                    </sd:Button>
                </td>

            </tr>
            
        </table>
    </s:form>
    <div id="result"></div>
</sd:TitlePane>

<script>
    page.report = function(){
//        window.location = "laptopCrawl!report.do";
        sd.connector.post("testReport!report.do","result","reportLaptopForm");
    }
    
</script>s