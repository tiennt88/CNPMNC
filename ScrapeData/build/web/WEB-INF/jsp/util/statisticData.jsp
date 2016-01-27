<%-- 
    Document   : statisticData
    Created on : Apr 23, 2015, 1:24:11 AM
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

<script>
    page.getIndex = function(inRowIndex){
        return inRowIndex+1;
    }

</script>
<sd:TitlePane id="statisticData"  key="Thống kê dữ liệu">
    <s:form  id="statisticDataForm"  name="statisticDataForm"  method="POST">
        <table width="100%">
            <tr>
                <td>
                    <sd:CheckBox id="type" checked="true" name="statistic.type" key="Loại" readonly = "true"/>
                    <sd:CheckBox id="web" checked="false" name="statistic.web" key="Web" cssStyle="margin-left:15%"/>
                    <sd:CheckBox id="brand" checked="false" name="statistic.brand" key="Hãng" cssStyle="margin-left:15%"/>
                </td>
            </tr>
            
            <tr>
                <td colspan ="3" align="center">
                    <sd:Button key="Search" onclick="page.onSearch()">
                        <img alt="Search"  src="share/Img/icons/search.png" height="17" width="20">
                    </sd:Button>
                </td>
            </tr>
        </table>
        <div id ="result">

        </div>
    </s:form>
</sd:TitlePane>
    <br>
<sd:TitlePane key="Thống kê dữ liệu" id="statisticDataList">
    <form id="statisticDataListForm" name="statisticDataListForm">
        <div id="statisticDataListDiv" >
            <sd:DataGrid clientSort="true" getDataUrl="statisticDataSearch!onSearch.do" id="statisticDataListGird" style="width: 100%; height: 100%;" rowsPerPage="500">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="false" field="type" key="Loại" width="10%" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="true" field="web" key="Web" width="10%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="brand" key="Hãng" width="10%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="qty" key="Số lượng" width="10%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true"  field="date" key="Ngày Cập Nhật" format="dd/MM/yyyy" width="10%" type="date" />.
            </sd:DataGrid>
        </div>
    </form>
    <div id="result"></div>
</sd:TitlePane>

<script>

    page.onSearch = function(){
//	notification('error','xin chao');
        var callback = function(data) {
                notification(data.label,data.customInfo);
            }
        dijit.byId('statisticDataListGird').vtReload("statisticDataSearch!onSearch.do","statisticDataForm",null,callback) ;
    }

</script>