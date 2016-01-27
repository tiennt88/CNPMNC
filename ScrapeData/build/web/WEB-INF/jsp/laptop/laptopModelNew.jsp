<%-- 
    Document   : laptopModelNew
    Created on : Apr 12, 2015, 2:58:07 AM
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
<sd:TitlePane id="laptopModelNew"  key="Thông tin">
    <s:form  id="laptopModelNewForm"  name="laptopModelNewForm"  method="POST">
        <table width="100%">
            <tr>
                <td>
                    <sd:SelectBox id="web" key="Web" name="laptop.web" data="webs" valueField="value" labelField="name" required="true"/>
                </td>
                <td>
                    <sd:SelectBox id="brand" key="Brand" name="laptop.brand" data="brands" valueField="value" labelField="name" required="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:DatePicker id="fromDate" name="fromDate" key="Ngày"  required="true" format="dd/MM/yyyy"/>
                </td>
                <td>
                    <sd:DatePicker id="toDate" name="toDate" key="Ngày"  required="true" format="dd/MM/yyyy"/>
                </td>
            </tr>
            <tr>
                <td colspan ="3" align="center">
                    <sd:Button key="Search" onclick="page.onSearch()">
                        <img alt="Search"  src="share/Img/icons/search.png" height="17" width="20">
                    </sd:Button>

                    <sd:Button key="Export" onclick="page.exportExcel()">
                        <img alt="Export" src="share/Img/Icon/export.png" height="17" width="20">
                    </sd:Button>
                </td>
            </tr>
        </table>
        <div id ="result">

        </div>
    </s:form>
</sd:TitlePane>
    <br>
<sd:TitlePane key="Danh Sách Model Mới" id="laptopModelNewList">
    <form id="laptopModelNewListForm" name="laptopModelNewListForm">
        <div id="laptopModelNewListDiv" >
            <sd:DataGrid clientSort="true" getDataUrl="laptopModelNewSearch!onSearch.do" id="laptopModelNewListGird" style="width: 100%; height: 100%;" rowsPerPage="10">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="false" field="id" key="id" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="link" key="Link" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="true" field="web" key="Web" width="4%" headerStyles="text-align: center;" styles="text-align: left; color:blue; font-weight:bold; text-decoration:  underline;" />
                <sd:ColumnDataGrid editable="true" field="type" key="Loại" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="brand" key="Hãng" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="price" key="Giá" width="7%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="name" key="Tên SP" width="10%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="model" key="Model" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="chip" key="Chip" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="speed" key="Speed" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="storage" key="HDD" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="hddType" key="Loại HDD" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="ram" key="Ram" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="vga" key="VGA" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="screen" key="Màn hình" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="touchscreen" key="Cảm Ứng" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="os" key="Hệ điều hành" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="dvd" key="Ổ DVD" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="battery" key="Pin" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="promotion" key="Khuyến Mại" width="15%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true"  field="createDate" key="Ngày tạo" format="dd/MM/yyyy" width="6%" type="date" headerStyles="text-align: center;"/>.
            </sd:DataGrid>
        </div>
    </form>
    <div id="result"></div>
</sd:TitlePane>

<script>
    dijit.byId('toDate').attr('value',new Date());
    dijit.byId('fromDate').attr('value',new Date());
    dijit.byId("web").setAttribute('value', '');
    dijit.byId("brand").setAttribute('value', '');
    
    page.onSearch = function(){
        var callback = function(data) {
                notification(data.label,data.customInfo);
            }
        dijit.byId('laptopModelNewListGird').vtReload("laptopModelNewSearch!onSearch.do","laptopModelNewForm",null,callback) ;
    }

    dojo.connect(dijit.byId("laptopModelNewListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("laptopModelNewListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "web"){
            var win = window.open(grid.getCell(2).get(idx, item), '_blank');
            win.focus();
        }
    });

    page.exportExcel = function(){
//        window.location = "laptopModelNewExport!exportExcel.do";
        sd.connector.post("laptopModelNewExport!exportExcel.do","result","laptopModelNewForm");
    }

</script>