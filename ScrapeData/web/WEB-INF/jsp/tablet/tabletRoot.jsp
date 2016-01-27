<%-- 
    Document   : tabletRoot
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
        var grid = dijit.byId('tabletRootListGird');
        return grid.currentRow + index + 1;
    }

</script>

<sd:TitlePane id="tabletRoot"  key="Dữ liệu Tablet FTG">
    <s:form  id="tabletRootForm"  name="tabletRootForm"  method="POST">
        <table width="100%">
            <s:hidden name="tablet.id" id="id"/>
            <s:hidden name="tablet.id1" id="id1"/>
            <tr>
                <td>
                    <sd:SelectBox id="brand" key="Hãng" name="tablet.brand" data="brands" valueField="value" labelField="name" required="true" />
                </td>
                <td>
                    <sd:TextBox id="itemCode" name="tablet.itemCode" key="Mã sản phẩm" trim="true" required="true" maxlength="50"/>
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
                    <sd:TextBox id="partno"   name="tablet.partno" key="PartNo" trim="true" maxlength="50"/>
                </td>
                <td>
                     <sd:SelectBox id="storage" key="Bộ nhớ trong" name="tablet.storage" data="storages" valueField="value" labelField="name" />
                </td>
            </tr>
            
            <tr>
                <td>
                    <sd:SelectBox id="ram" key="Ram" name="tablet.ram" data="rams" valueField="value" labelField="name" />
                </td>
                
                <td>
                    <sd:CheckBox id="mapping" checked="false" name="mapping" key="Match"/>
                    <sd:CheckBox id="today" checked="false" name="tablet.today" key="today" cssStyle="margin-left:15%"/>
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

<sd:TitlePane key="Danh sách sản phẩm laptop lấy dữ liệu" id="tabletRootList">
    <form id="tabletRootListForm" name="tabletRootListForm">
        <div id="tabletRootListDiv" >
            <sd:DataGrid clientSort="true"   getDataUrl="tabletRootSearch!onSearch.do" id="tabletRootListGird" style="width: 100%; height: 100%;" rowsPerPage="10">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="false" field="id" key="id" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="id1" key="id1" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="link" key="link" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="true" field="web" key="Web" width="5%" headerStyles="text-align: center;"  styles="text-align: left; color:blue; font-weight:bold; text-decoration:  underline;"/>
                <sd:ColumnDataGrid editable="true" field="type" key="Loại" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="brand" key="Hãng" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="itemCode" key="Mã Sản Phẩm" width="6%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="partno" key="PartNo" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="name" key="Tên Sản Phẩm" width="15%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="model" key="Model" width="8%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="price" key="Giá" width="7%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="storage" key="Bộ nhớ trong" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="ram" key="RAM" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="screen" key="Màn Hình" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="cpu" key="CPU" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="os" key="OS" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="backCamera" key="Camera Chính" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="frontCamera" key="Camera Phụ" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="battery" key="Pin" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="sim" key="Sim" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="promotion" key="Khuyến Mại" width="10%" headerStyles="text-align: center;"  />
                <!--sd:ColumnDataGrid editable="true" field="createDate" key="Ngày Tạo" format="dd/MM/yyyy" width="6%" type="date" /-->
                <sd:ColumnDataGrid editable="true" field="lastUpdate" key="Ngày Cập Nhật" format="dd/MM/yyyy" width="6%" type="date" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="true" field="match" key="Match" width="2%" headerStyles="text-align: center;" styles="text-align: center; color:blue; font-weight:bold;"  />
                <sd:ColumnDataGrid editable="true" field=""  key="Chọn" type="radio"  width="3%" headerStyles="text-align: center;" styles="text-align: center;" />
            </sd:DataGrid>
        </div>
    </form>
</sd:TitlePane>

<script>

    dijit.byId("brand").setAttribute('value', '');
    dijit.byId("ram").setAttribute('value', '');
    dijit.byId("storage").setAttribute('value', '');
    
    page.onSearch = function(){
//	notification('error','xin chao');
        var callback = function(data) {
                notification(data.label,data.customInfo);
            }
        dijit.byId('tabletRootListGird').vtReload("tabletRootSearch!onSearch.do","tabletRootForm",null,callback) ;
    }

    page.reset = function(){
        document.getElementById("id").value = "";
        document.getElementById("id1").value = "";
        document.getElementById("itemCode").value = "";
        document.getElementById("partno").value = "";
        document.getElementById("name").value = "";
        document.getElementById("model").value = "";
        dijit.byId("storage").setAttribute('value', '');
        dijit.byId("ram").setAttribute('value', '');
        dijit.byId("brand").setAttribute('value', '');
        dijit.byId("edit").setAttribute('disabled', true);
        dijit.byId("delete").setAttribute('disabled', true);
        dijit.byId("mapping").setAttribute('checked', false);
        dijit.byId("today").setAttribute('checked', false);
    }

    dojo.connect(dijit.byId("tabletRootListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("tabletRootListGird");
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
                document.getElementById("itemCode").value = grid.getCell(7).get(idx, item);
                document.getElementById("partno").value = grid.getCell(8).get(idx, item);
                document.getElementById("name").value = grid.getCell(9).get(idx, item);
                document.getElementById("model").value = grid.getCell(10).get(idx, item);
                dijit.byId("storage").setAttribute('value', grid.getCell(12).get(idx, item));
                dijit.byId("ram").setAttribute('value', grid.getCell(13).get(idx, item));
                
                
            }
        }
        if(colField == "match"){
            openPopup("tabletComparePopUp.do?id="+grid.getCell(1).get(idx, item),1000,550);
//            javascript:doGoToMenu("tabletCompare.do?id="+grid.getCell(1).get(idx, item), "0.2.0");
        }
        if(colField == "web"){
            var win = window.open(grid.getCell(3).get(idx, item), '_blank');
            win.focus();
        }
    });

    validate = function(){
        var brand = dijit.byId("brand").value;
//        var itemCode = document.getElementById("itemCode").value;
        var name = document.getElementById("name").value;
        var model = document.getElementById("model").value;
//        var ram = dijit.byId("ram").value;
//        var storage = dijit.byId("storage").value;
        
        if(brand == ""){
            notification("ERROR","Chưa điền thông tin hãng!");
            document.getElementById("brand").focus();
            return false;
        }
//        if(itemCode == ""){
//            notification("ERROR","Chưa điền thông tin mã sản phẩm!");
//            document.getElementById("itemCode").focus();
//            return false;
//        }
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
//        if(storage == ""){
//            notification("ERROR","Chưa điền thông tin bộ nhớ trong!");
//            document.getElementById("storage").focus();
//            return false;
//        }
//        if(ram == ""){
//            notification("ERROR","Chưa điền thông tin Ram!");
//            document.getElementById("ram").focus();
//            return false;
//        }

        return true;
    }

    page.onEdit = function(){
        if(validate() == true){
            var callback = function(data) {
                notification(data.label,data.customInfo);
            }
            dijit.byId('tabletRootListGird').vtReload("tabletRootUpdate!onUpdate.do","tabletRootForm",null,callback) ;
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
            dijit.byId('tabletRootListGird').vtReload("tabletRootDelete!onDelete.do","tabletRootForm",null,callback) ;
            dijit.byId("delete").setAttribute('disabled', true);
            page.reset();
        }
    }

</script>