<%-- 
    Document   : scrapeData
    Created on : Mar 23, 2015, 4:53:22 AM
    Author     : KeyLove
--%>

<%@page import="java.util.ResourceBundle"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="sd" uri="struts-dojo-tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<sd:TitlePane id="scrapeData"  key="Thông tin">

    <s:form  id="scrapeDataForm"  name="scrapeDataForm"  method="POST">
        <table width="100%">
            <tr>
                <td>
                    <sd:SelectBox id="web" key="Web" name="web" >
                        <sd:Option value="" >All</sd:Option>
                        <sd:Option value="FPTShop" >FPT Shop</sd:Option>
                        <sd:Option value="MediaMart" >Media Mart</sd:Option>
                        <sd:Option value="TGDD">Thế Giới Di Động</sd:Option>
                        <sd:Option value="TranAnh" >Trần Anh</sd:Option>
                        <sd:Option value="VienThongA" >Viễn Thông A</sd:Option>
                        <sd:Option value="Viettel" >Viettel</sd:Option>
                    </sd:SelectBox>
                </td>
                <td>
                     <sd:SelectBox id="type" key="Type" name="type" >
                        <sd:Option value="All" >All</sd:Option>
                        <sd:Option value="Mobile" >Mobile</sd:Option>
                        <sd:Option value="Tablet" >Tablet</sd:Option>
                        <sd:Option value="Laptop" >Laptop</sd:Option>
                    </sd:SelectBox>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <sd:Button key="Run" onclick="page.getData()">
                            <img src="share/Img/icons/search.png" height="17" width="20">
                    </sd:Button>
                </td>
            </tr>
        </table>
    </s:form>
</sd:TitlePane>
<script>
    page.getData = function(){
        var xhrArgs = {
            url: "runScrapeData.do",
            form: document.getElementById("scrapeDataForm"),
            load: function(data) {
                //alert(data);//json tra ve tren JS o dang string. Can phai parse thanh doi tuong json tren JS
                notification("SUCCESS",JSON.parse(data).customInfo);
            }
        };
        dojo.xhrPost(xhrArgs);
    }

</script>