<%-- 
    Document   : laptopRoot
    Created on : Mar 22, 2015, 3:02:53 AM
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

<script>

    page.getIndex = function (index) {
        var grid = dijit.byId('laptopRootListGird');
        return grid.currentRow + index + 1;
    }

</script>

<sd:TitlePane id="laptopRoot"  key="Dữ liệu Laptop FTG">
    <s:form  id="laptopRootForm"  name="laptopRootForm"  method="POST">
        <table width="100%">
            <s:hidden name="laptop.id" id="id"/>
            <s:hidden name="laptop.id1" id="id1"/>
            <tr>
                <td>
                    <sd:SelectBox id="brand" key="Hãng" name="laptop.brand" data="brands" valueField="value" labelField="name" required="true" />
                </td>
                <td>
                    <sd:TextBox id="itemCode" name="laptop.itemCode" key="Mã sản phẩm" trim="true" required="true" maxlength="50"/>
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
                    <sd:TextBox id="partno"   name="laptop.partno" key="PartNo" trim="true" maxlength="50"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td>
                   <sd:SelectBox id="chip" key="Chip" name="laptop.chip" data="chips" valueField="value" labelField="name" required="true" />
                </td>
                <td>
                    <sd:TextBox id="speed"   name="laptop.speed" key="Speed" trim="true" required="true" maxlength="50"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td>
                    <sd:SelectBox id="storage" key="HDD" name="laptop.storage" data="storages" valueField="value" labelField="name" required="true"/>
                </td>
                <td>
                    <sd:SelectBox id="hddType" key="Loại HDD" name="laptop.hddType" data="hddTypes" valueField="value" labelField="name" required="true"/>
                </td>
                <td>
                    <sd:SelectBox id="ram" key="RAM" name="laptop.ram" data="rams" valueField="value" labelField="name" required="true"/>
                </td>
            </tr>
            <tr>
                <td>
                    <sd:SelectBox id="vga" key="VGA" name="laptop.vga" data="vgas" valueField="value" labelField="name" required="true"/>
                </td>
                <td>
                    <sd:TextBox id="screen"   name="laptop.screen" key="Screen" trim="true" required="true" maxlength="50"/>
                </td>
                <td>
                    <sd:SelectBox id="touchscreen" key="Cảm ứng" name="laptop.touchscreen" data="touchscreens" valueField="value" labelField="name" required="true"/>
                </td>
            </tr>
            <tr>
                <td>
                    <sd:SelectBox id="os" key="Hệ điều hành" name="laptop.os" data="oss" valueField="value" labelField="name" required="true"/>
                </td>
                <td>
                    <sd:SelectBox id="dvd" key="Ổ DVD" name="laptop.dvd" data="dvds" valueField="value" labelField="name" required="true"/>
                </td>
                <td>
                    <sd:SelectBox id="battery" key="Pin" name="laptop.battery" data="batterys" valueField="value" labelField="name" required="true"/>
                </td>
            </tr>

            <tr>
                <td>
                    <sd:CheckBox id="mapping" checked="false" name="mapping" key="Match"/>
                </td>
                <td>
                    <sd:CheckBox id="today" checked="false" name="laptop.today" key="today"/>
                </td>
                <td></td>
            </tr>
            
            <tr>
                <td colspan ="3" align="center">
                    <sd:Button id="Search" key="Search"
                                   onclick="page.onSearch()">
                        <img alt="Search"  src="share/Img/icons/search.png" height="17" width="20">
                    </sd:Button>
                    <sd:Button id="edit" key="Sửa" disabled="true"
                               onclick="page.onEdit()">
                        <img alt="edit"  src="share/Img/icons/edit_1.png" height="17" width="20">
                    </sd:Button>
                    <sd:Button id="delete" key="Xóa" disabled="true"
                               onclick="page.onDelete()">
                        <img alt="delete"  src="share/Img/icons/a4.png" height="17" width="20">
                    </sd:Button>
                    <sd:Button id="reset" key="Reset"
                               onclick="page.reset()">
                        <img alt="reset"  src="share/Img/icons/reset.png" height="17" width="20">
                    </sd:Button>
                </td>
            </tr>
        </table>
    </s:form>
</sd:TitlePane>

    <br>
  <div id="notification" class= "notifications bottom-right"></div>

<sd:TitlePane key="Danh sách sản phẩm laptop lấy dữ liệu" id="laptopRootList">
    <form id="laptopRootListForm" name="laptopRootListForm">
        <div id="laptopRootListDiv" >
            <sd:DataGrid clientSort="true"   getDataUrl="laptopRootSearch!onSearch.do" id="laptopRootListGird" style="width: 100%; height: 100%;" rowsPerPage="10">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="false" field="id" key="id" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="id1" key="id1" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="link" key="link" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="true" field="web" key="Web" width="5%" headerStyles="text-align: center;"  styles="text-align: center; color:blue; font-weight:bold; text-decoration:  underline;"/>
                <sd:ColumnDataGrid editable="true" field="type" key="Loại" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="brand" key="Hãng" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="price" key="Giá" width="7%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="itemCode" key="Mã Sản Phẩm" width="6%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="partno" key="PartNo" width="5%" headerStyles="text-align: center;"  />
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
                <sd:ColumnDataGrid editable="true" field="promotion" key="Khuyến Mại" width="10%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="lastUpdate" key="Ngày Cập Nhật" format="dd/MM/yyyy" width="6%" type="date" />
                <sd:ColumnDataGrid editable="true" field="match" key="Match" width="2%" headerStyles="text-align: center;" styles="text-align: center; color:blue; font-weight:bold;"  />
                <sd:ColumnDataGrid editable="true" field=""  key="Chọn" type="radio"  width="3%" headerStyles="text-align: center;" styles="text-align: center;" />
            </sd:DataGrid>
        </div>
    </form>
</sd:TitlePane>

<script>

    dijit.byId("brand").setAttribute('value', '');
    dijit.byId("chip").setAttribute('value', '');
    dijit.byId("ram").setAttribute('value', '');
    dijit.byId("storage").setAttribute('value', '');
    dijit.byId("hddType").setAttribute('value', '');
    dijit.byId("os").setAttribute('value', '');
    dijit.byId("dvd").setAttribute('value', '');
    dijit.byId("touchscreen").setAttribute('value', '');
    dijit.byId("vga").setAttribute('value', '');
    
    page.onSearch = function(){
//	notification('error','xin chao');
        var callback = function(data) {
                notification(data.label,data.customInfo);
            }
        dijit.byId('laptopRootListGird').vtReload("laptopRootSearch!onSearch.do","laptopRootForm",null,callback) ;
    }

    page.reset = function(){
        document.getElementById("id").value = "";
        document.getElementById("id1").value = "";
        document.getElementById("itemCode").value = "";
        document.getElementById("partno").value = "";

        dijit.byId("brand").setAttribute('value', '');
        document.getElementById("name").value = "";
        document.getElementById("model").value = "";
        dijit.byId("chip").setAttribute('value', '');
        document.getElementById("speed").value = "";
        dijit.byId("storage").setAttribute('value', '');
        dijit.byId("hddType").setAttribute('value', '');
        dijit.byId("ram").setAttribute('value', '');
        dijit.byId("vga").setAttribute('value', '');
        document.getElementById("screen").value = "";
        dijit.byId("touchscreen").setAttribute('value', '');
        dijit.byId("os").setAttribute('value', '');
        dijit.byId("dvd").setAttribute('value', '');
        document.getElementById("battery").value = "";
        dijit.byId("edit").setAttribute('disabled', true);
        dijit.byId("delete").setAttribute('disabled', true);
        
        dijit.byId("mapping").setAttribute('checked', false);
        dijit.byId("today").setAttribute('checked', false);
    }

    dojo.connect(dijit.byId("laptopRootListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("laptopRootListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "_R_D_"){
            if(item){
                page.reset();
                dijit.byId("edit").setAttribute('disabled', false);
                dijit.byId("delete").setAttribute('disabled', false);
                document.getElementById("id").value = grid.getCell(1).get(idx, item);
                document.getElementById("id1").value = grid.getCell(2).get(idx, item);
                dijit.byId("brand").setAttribute('value', grid.getCell(6).get(idx, item));
                document.getElementById("itemCode").value = grid.getCell(8).get(idx, item);
                document.getElementById("partno").value = grid.getCell(9).get(idx, item);
                document.getElementById("name").value = grid.getCell(10).get(idx, item);
                document.getElementById("model").value = grid.getCell(11).get(idx, item);
                dijit.byId("chip").setAttribute('value', grid.getCell(12).get(idx, item));
                document.getElementById("speed").value = grid.getCell(13).get(idx, item);
                dijit.byId("storage").setAttribute('value', grid.getCell(14).get(idx, item));
                dijit.byId("hddType").setAttribute('value', grid.getCell(15).get(idx, item));
                dijit.byId("ram").setAttribute('value', grid.getCell(16).get(idx, item));
                dijit.byId("vga").setAttribute('value', grid.getCell(17).get(idx, item));
                document.getElementById("screen").value = grid.getCell(18).get(idx, item);
                dijit.byId("touchscreen").setAttribute('value', grid.getCell(19).get(idx, item));
                dijit.byId("os").setAttribute('value', grid.getCell(20).get(idx, item));
                dijit.byId("dvd").setAttribute('value', grid.getCell(21).get(idx, item));
                document.getElementById("battery").value = grid.getCell(22).get(idx, item);
            }
        }
        if(colField == "match"){
            openTab("laptopComparePopUp.do?id="+grid.getCell(1).get(idx, item));
//            openPopup("laptopComparePopUp.do?id="+grid.getCell(1).get(idx, item),1000,550);
//            javascript:doGoToMenu("laptopCompare.do?id="+grid.getCell(1).get(idx, item), "0.2.0");
        }
        if(colField == "web"){
            var win = window.open(grid.getCell(3).get(idx, item), '_blank');
            win.focus();
        }
    });

    validate = function(){
        var brand = dijit.byId("brand").value;
        var name = document.getElementById("name").value;
        var model = document.getElementById("model").value;
        
        if(brand == ""){
            notification("ERROR","Chưa điền thông tin hãng!");
            document.getElementById("brand").focus();
            return false;
        }
        if(name == ""){
            notification("ERROR","Chưa điền thông tin tên sản phẩm!");
            document.getElementById("name").focus();
            return false;
        }
        if(model == ""){
            notification("ERROR","Chưa điền thông tin model!");
            document.getElementById("model").focus();
            return false;
        }

        return true;
    }

    page.onEdit = function(){
        if(validate() == true){
            var callback = function(data) {
                notification(data.label,data.customInfo);
            }
            dijit.byId('laptopRootListGird').vtReload("laptopRootUpdate!onUpdate.do","laptopRootForm",null,callback) ;
            dijit.byId("edit").setAttribute('disabled', true);
        }
    }

    page.onDelete = function(){
        if(document.getElementById("id").value == "" || document.getElementById("id1").value == ""){
            notification("ERROR","Xin vui lòng chọn 1 bản ghi!");
            return;
        }
        if(confirm("Bạn thực sự muốn xóa?")){
            var callback = function(data) {
                notification(data.label,data.customInfo);
            }
            dijit.byId('laptopRootListGird').vtReload("laptopRootDelete!onDelete.do","laptopRootForm",null,callback) ;
            dijit.byId("delete").setAttribute('disabled', true);
            page.reset();
        }
    }

</script>