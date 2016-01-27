<%-- 
    Document   : laptopPriceHistory
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
        var grid = dijit.byId('laptopPriceHistoryListGird');
        return grid.currentRow + index + 1;
    }

</script>

<sd:TitlePane id="laptopPriceHistory"  key="Tra cứu lịch sử giá">
    <s:form  id="laptopPriceHistoryForm"  name="laptopPriceHistoryForm"  method="POST">
        <table width="100%">
            <tr>
                <td>
                    <sd:SelectBox id="web" key="Web" name="laptop.web" data="webs" valueField="value" labelField="name" required="true"/>
                </td>
                <td>
                    <sd:SelectBox id="brand" key="Brand" name="laptop.brand" data="brands" valueField="value" labelField="name" required="true" />
                </td>
                <td>
                    <sd:TextBox id="name"   name="laptop.name" key="Tên Sản Phẩm" trim="true" required="true" maxlength="250"/>
                </td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="model"   name="laptop.model" key="Model" trim="true" required="true" maxlength="50"/>
                </td>
                <td>
                    <sd:TextBox id="itemCode"   name="laptop.itemCode" key="Mã SP" trim="true" required="true" maxlength="50"/>
                </td>
                <td>
                    <sd:TextBox id="partno"   name="laptop.partno" key="Part No" trim="true" required="true" maxlength="50"/>
                </td>
            </tr>
            <tr>
                <td>
                    <sd:SelectBox id="chip" key="Chip" name="laptop.chip" data="chips" valueField="value" labelField="name" />
                </td>
                <td>
                    <sd:SelectBox id="storage" key="Bộ nhớ trong" name="laptop.storage" data="storages" valueField="value" labelField="name" />
                </td>
                <td >
                    <sd:SelectBox id="ram" key="Ram" name="laptop.ram" data="rams" valueField="value" labelField="name" />
                </td>
                
            </tr>
            <tr>
                <td>
                    <sd:SelectBox id="vga" key="VGA" name="laptop.chip" data="vgas" valueField="value" labelField="name" />
                </td>
                <td>
                    <sd:DatePicker id="fromDate" name="fromDate" key="Từ Ngày"  required="true" format="dd/MM/yyyy"/>
                </td>
                <td>
                    <sd:DatePicker id="toDate" name="toDate" key="Đến Ngày"  required="true" format="dd/MM/yyyy"/>
                </td>
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

<sd:TitlePane key="Dữ Liệu" id="laptopPriceHistoryList">
    <form id="laptopPriceHistoryListForm" name="laptopPriceHistoryListForm">
        <div id="laptopPriceHistoryListDiv" >
            <sd:DataGrid clientSort="true"   getDataUrl="laptopPriceHistorySearch!onSearch.do" id="laptopPriceHistoryListGird" style="width: 100%; height: 100%;" rowsPerPage="10">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="false" field="id" key="id" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="id1" key="id1" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="link" key="Link" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="true" field="web" key="Web" width="4%" headerStyles="text-align: center;" styles="text-align: left; color:blue; font-weight:bold; text-decoration:  underline;" />
                <sd:ColumnDataGrid editable="true" field="type" key="Loại" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="brand" key="Hãng" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="price" key="Giá" width="7%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="name" key="Tên SP" width="10%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="model" key="Model" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="itemCode" key="Mã SP" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="partno" key="PartNo" width="5%" headerStyles="text-align: center;"  />
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
                <sd:ColumnDataGrid editable="true" field="promotion" key="Khuyến Mại" width="10%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true"  field="lastUpdate" key="Ngày Cập Nhật" format="dd/MM/yyyy" width="6%" type="date" headerStyles="text-align: center;"/>
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
        dijit.byId('laptopPriceHistoryListGird').vtReload("laptopPriceHistorySearch!onSearch.do","laptopPriceHistoryForm",null,callback) ;
    }

    page.PriceHistory = function(){
        //        window.location = "laptopPriceHistoryExport!priceHistory.do";
        if(dijit.byId("laptopPriceHistoryListGird").vtIsChecked()){
            var content = dijit.byId("laptopPriceHistoryListGird").vtGetCheckedData("listLaptop");
            sd.connector.post("laptopPriceHistoryExport!priceHistory.do","result","laptopPriceHistoryForm",content);
        }
        else{
            notification('ERROR','Chưa có checkbox nào được chọn!');
        }
    }

    page.reset = function(){
        
        document.getElementById("id").value = "";
        document.getElementById("id1").value = "";
        dijit.byId("web").setAttribute('value', '');
        dijit.byId("brand").setAttribute('value', '');
        document.getElementById("name").value = "";
        document.getElementById("model").value = "";
        document.getElementById("itemCode").value = "";
        document.getElementById("partno").value = "";
        dijit.byId("chip").setAttribute('value', '');
        dijit.byId("storage").setAttribute('value', '');
        dijit.byId("ram").setAttribute('value', '');
        dijit.byId("vga").setAttribute('value', '');
        dijit.byId('fromDate').reset();
        dijit.byId('toDate').reset();
    }

    dojo.connect(dijit.byId("laptopPriceHistoryListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("laptopPriceHistoryListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "web"){
            var win = window.open(grid.getCell(3).get(idx, item), '_blank');
            win.focus();
        }
    });


</script>
