<%--
    Document   : laptopComparePopUp
    Created on : Mar 23, 2015, 2:31:28 AM
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

<sd:TitlePane id="laptopCompare"  key="Tra Cứu Thông Tin laptop">
    <s:form  id="laptopCompareForm"  name="laptopCompareForm"  method="POST">
        <table width="100%">
            <tr>
                <s:hidden name="laptop.id" id="id"/>
               <td>
                   <sd:Textarea rows="1" id="itemCode" name="laptop.itemCode"  key="Mã Sản Phẩm" trim="true"  maxlength="50" required="true" />
               </td>
                <td colspan="2">
                   <table width="100%">
                       <tr>
                           <td>
                               <sd:CheckBox id="laptopCheck.brand" checked="true" name="laptopCheck.brand" key="Brand"/>
                           </td>
                           <td>
                               <sd:CheckBox id="laptopCheck.model" checked="true"  name="laptopCheck.model" key="Model"/>
                           </td>
                           <td>
                               <sd:CheckBox id="laptopCheck.screen" checked="false" name="laptopCheck.screen" key="Màn Hình"/>
                           </td>
                           <td>
                               <sd:CheckBox id="laptopCheck.chip" checked="false" name="laptopCheck.chip" key="Chip"/>
                           </td>
                           <td>
                               <sd:CheckBox id="laptopCheck.storage" checked="false" name="laptopCheck.storage" key="HDD"/>
                           </td>
                       </tr>
                       <tr>
                           <td>
                               <sd:CheckBox id="laptopCheck.hddType" checked="false" name="laptopCheck.hddType" key="Loại HDD"/>
                           </td>
                           <td>
                               <sd:CheckBox id="laptopCheck.ram" checked="false" name="laptopCheck.ram" key="Ram"/>
                           </td>
                           <td>
                               <sd:CheckBox id="laptopCheck.vga" checked="false" name="laptopCheck.vga" key="VGA"/>
                           </td>
                           <td>
                               <sd:CheckBox id="laptopCheck.os" checked="false" name="laptopCheck.os" key="Hệ Điều Hành"/>
                           </td>
                           <td>
                               <sd:CheckBox id="laptopCheck.touchscreen" checked="false" name="laptopCheck.touchscreen" key="Cảm Ứng"/>
                           </td>
                           <td>
                               <sd:CheckBox id="laptopCheck.dvd" checked="false" name="laptopCheck.dvd" key="Ổ DVD"/>
                           </td>
                       </tr>
                   </table>
                </td>
            </tr>
            
            <tr>
                <td colspan="6" align="center">
                   <sd:Button id="Search" key="Search"
                                   onclick="page.onSearch()">
                            <img src="share/Img/icons/search.png" height="17" width="20">
                    </sd:Button>
                    <sd:Button id="close" key="Thoát"
                               onclick="page.onExit()">
                        <img src="share/Img/Icon/Close-2-icon.png" height="17" width="20">
                    </sd:Button>
               </td>
            </tr>
            <tr>
                <td><br></td>
            </tr>
<!--                <h1>Thông Tin Chi Tiết :</h1>-->
            <tr>
                <td>
                    <sd:Textarea id="web"  rows="1" name="laptop.web" key="Web" trim="true" readonly="true" maxlength="50"/>
                </td>
                <td>
                    <sd:Textarea id="type" rows="1"  name="laptop.type" key="Loại" trim="true" readonly="true" maxlength="50"/>
                </td>
                <td>
                    <sd:Textarea id="brand" rows="1"  name="laptop.brand" key="Hãng" trim="true" readonly="true" maxlength="50"/>
                </td>
            </tr>
            <tr>
                <td>
                    <sd:Textarea id="name"  rows="1" name="laptop.name" key="Tên" trim="true" readonly="true" maxlength="250"/>
                </td>
                <td>
                    <sd:Textarea id="model" rows="1"  name="laptop.model" key="Model" trim="true" maxlength="250" readonly="true"/>
                </td>

                <td>
                    <sd:Textarea id="partno" rows="1" name="laptop.partno" key="PartNo" trim="true" readonly="true" maxlength="50"/>
                </td>
            </tr>

            <tr>
                <td>
                    <sd:Textarea id="price"  rows="1" name="laptop.price" key="Giá" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="chip" rows="1"  name="laptop.chip" key="Chip" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="speed" rows="1"  name="laptop.speed" key="Speed" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:Textarea id="storage" rows="1"  name="laptop.storage" key="HDD" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="hddType" rows="1"  name="laptop.hddType" key="Loại HDD" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="ram" rows="1"  name="laptop.ram" key="Ram" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:Textarea id="vga" rows="1"  name="laptop.vga" key="VGA" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="screen"  rows="1" name="laptop.screen" key="Màn Hình" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="touchscreen" rows="1"  name="laptop.touchscreen" key="Cảm ứng" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:Textarea id="os" rows="1"  name="laptop.os" key="OS" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="dvd" rows="1"  name="laptop.dvd" key="Ổ DVD" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="battery" rows="1"  name="laptop.battery" key="Pin" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:Textarea id="promotion" rows="1"  name="laptop.promotion" key="Khuyến Mại" trim="true" maxlength="500" readonly="true" />
                </td>
            </tr>
        </table>

    </s:form>
</sd:TitlePane>
    <br>
<sd:TitlePane key="Danh Sách Các Web Bán" id="laptopCompareList">

    <form id="laptopCompareListForm" name="laptopCompareListForm">
        <div id="laptopCompareListDiv" >
            <sd:DataGrid clientSort="true" getDataUrl="laptopCompareSearch!onSearch.do" id="laptopCompareListGird" style="width: 100%; height: 100%;" rowsPerPage="500">
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
                <sd:ColumnDataGrid editable="true"  field="lastUpdate" key="Ngày Cập Nhật" format="dd/MM/yyyy" width="6%" type="date" headerStyles="text-align: center;"/>
            </sd:DataGrid>
        </div>
    </form>
</sd:TitlePane>

<script>

    page.onExit = function(){
       window.close();
    }

    page.onSearch = function(){
        var callback = function(data) {
                notification(data.label,data.customInfo);
            }
        dijit.byId("laptopCompareListGird").vtReload("laptopCompareSearch!onSearch.do","laptopCompareForm",null,page.showMessage) ;
    }

    dojo.connect(dijit.byId("laptopCompareListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("laptopCompareListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "web"){
            var win = window.open(grid.getCell(2).get(idx, item), '_blank');
            win.focus();
        }
    });

</script>