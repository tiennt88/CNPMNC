<%-- 
    Document   : tabletPriceHistory
    Created on : Apr 11, 2015, 2:23:12 PM
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

    page.getIndex = function (index) {
        var grid = dijit.byId('tabletPriceHistoryListGird');
        return grid.currentRow + index + 1;
    }

</script>

<sd:TitlePane id="tabletPriceHistory"  key="Tra cứu lịch sử giá">
    <s:form  id="tabletPriceHistoryForm"  name="tabletPriceHistoryForm"  method="POST">
        <table width="100%">
            <tr>
                <td>
                    <sd:SelectBox id="web" key="Web" name="tablet.web" data="webs" valueField="value" labelField="name" required="true"/>
                </td>
                <td>
                    <sd:SelectBox id="brand" key="Brand" name="tablet.brand" data="brands" valueField="value" labelField="name" required="true" />
                </td>
                <td>
                    <sd:TextBox id="name"   name="tablet.name" key="Tên Sản Phẩm" trim="true" required="true" maxlength="250"/>
                </td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="model"   name="tablet.model" key="Model" trim="true" required="true" maxlength="50"/>
                </td>
                <td>
                    <sd:TextBox id="itemCode"   name="tablet.itemCode" key="Mã SP" trim="true" required="true" maxlength="50"/>
                </td>
                <td>
                    <sd:TextBox id="partno"   name="tablet.partno" key="Part No" trim="true" required="true" maxlength="50"/>
                </td>
            </tr>
            <tr>
                <td>
                    <sd:SelectBox id="storage" key="Bộ nhớ trong" name="tablet.storage" data="storages" valueField="value" labelField="name" />
                </td>
                <td >
                    <sd:SelectBox id="ram" key="Ram" name="tablet.ram" data="rams" valueField="value" labelField="name" />
                </td>
                <td>
                </td>
            </tr>
            <tr>
                <td>
                    <sd:DatePicker id="fromDate" name="fromDate" key="Từ Ngày"  required="true" format="dd/MM/yyyy"/>
                </td>
                <td>
                    <sd:DatePicker id="toDate" name="toDate" key="Đến Ngày"  required="true" format="dd/MM/yyyy"/>
                </td>
                <td width="40%"></td>
            </tr>
            <tr>
                <td colspan="3" align="center">
                    <sd:Button id="Search" key="Search"
                               onclick="page.onSearch()">
                        <img alt="search"  src="share/Img/icons/search.png" height="17" width="20">
                    </sd:Button>
                    <sd:Button id="PriceHistory" key="Lịch sử giá"
                               onclick="page.PriceHistory()">
                        <img alt="reset"  src="share/Img/Icon/export.png" height="17" width="20">
                    </sd:Button>
                    <sd:Button id="reset" key="Reset"
                               onclick="page.reset()">
                        <img alt="reset"  src="share/Img/icons/reset.png" height="17" width="20">
                    </sd:Button>

                </td>
            </tr>

        </table>
    </s:form>
    <div id="result"></div>
</sd:TitlePane>
<br>

<sd:TitlePane key="Dữ Liệu" id="tabletPriceHistoryList">
    <form id="tabletPriceHistoryListForm" name="tabletPriceHistoryListForm">
        <div id="tabletPriceHistoryListDiv" >
            <sd:DataGrid clientSort="true"   getDataUrl="tabletPriceHistorySearch!onSearch.do" id="tabletPriceHistoryListGird" style="width: 100%; height: 100%;" rowsPerPage="10">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="false" field="id" key="id" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="id1" key="id1" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="link" key="Link" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="true" field="web" key="Web" width="4%" headerStyles="text-align: center;"  styles="text-align: left; color:blue; font-weight:bold; text-decoration:  underline;"/>
                <sd:ColumnDataGrid editable="true" field="type" key="Loại" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="brand" key="Hãng" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="price" key="Giá" width="7%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="name" key="Tên SP" width="10%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="model" key="Model" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="itemCode" key="Mã SP" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="partno" key="Part No" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="storage" key="Bộ nhớ trong" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="ram" key="Ram" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="screen" key="Màn Hình" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="cpu" key="Cpu" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="backCamera" key="Camera chính" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="frontCamera" key="Camera Phụ" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="os" key="Hệ điều hành" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="battery" key="Pin" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="sim" key="Sim" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="promotion" key="Khuyến Mại" width="10%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true"  field="lastUpdate" key="Ngày Cập Nhật" format="dd/MM/yyyy" width="6%" type="date"  headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="true" key="Chọn" type="checkbox"  width="3%" headerCheckbox="true"  headerStyles="text-align: center;" styles="text-align: center;" />
            </sd:DataGrid>
        </div>
    </form>
</sd:TitlePane>

<script>
    dijit.byId("web").setAttribute('value', '');
    dijit.byId("brand").setAttribute('value', '');
    dijit.byId("ram").setAttribute('value', '');
    dijit.byId("storage").setAttribute('value', '');
    var date = new Date();
    var firstDateOfCurrentMonth = new Date(date.getFullYear(), date.getMonth(), 1);
    dijit.byId('toDate').attr('value',date);
    dijit.byId('fromDate').attr('value',firstDateOfCurrentMonth);

    page.onSearch = function(){
        //	notification('error','xin chao');
        var callback = function(data) {
            notification(data.label,data.customInfo);
        }
        dijit.byId('tabletPriceHistoryListGird').vtReload("tabletPriceHistorySearch!onSearch.do","tabletPriceHistoryForm",null,callback) ;
    }

    page.PriceHistory = function(){
        //        window.location = "tabletPriceHistoryExport!priceHistory.do";
        if(dijit.byId("tabletPriceHistoryListGird").vtIsChecked()){
            var content = dijit.byId("tabletPriceHistoryListGird").vtGetCheckedData("listTablet");
            sd.connector.post("tabletPriceHistoryExport!priceHistory.do","result","tabletPriceHistoryForm",content);
        }
        else{
            notification('ERROR','Chưa có checkbox nào được chọn!');
        }
    }

    page.reset = function(){
        
        document.getElementById("id").value = "";
        document.getElementById("id1").value = "";
        dijit.byId("web").setAttribute('value', '');
        document.getElementById("name").value = "";
        document.getElementById("model").value = "";
        dijit.byId("storage").setAttribute('value', '');
        dijit.byId("ram").setAttribute('value', '');
        dijit.byId("brand").setAttribute('value', '');
        document.getElementById("partno").value = "";
        ocument.getElementById("itemCode").value = "";
        dijit.byId('fromDate').reset();
        dijit.byId('toDate').reset();
    }

    dojo.connect(dijit.byId("tabletPriceHistoryListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("tabletPriceHistoryListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "web"){
            var win = window.open(grid.getCell(3).get(idx, item), '_blank');
            win.focus();
        }
    });


</script>
