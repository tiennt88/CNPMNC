<%--
    Document   : laptopCompare
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
                    <sd:TextBox id="itemCode" name="laptop.itemCode"  key="Mã Sản Phẩm" trim="true"  maxlength="50" required="true" />
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
               </td>
            </tr>
<!--                <h1>Thông Tin Chi Tiết :</h1>-->
            <tr>
                <td><br></td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="web"   name="laptop.web" key="Web" trim="true" readonly="true" maxlength="50"/>
                </td>
                <td>
                    <sd:TextBox id="type"   name="laptop.type" key="Loại" trim="true" readonly="true" maxlength="50"/>
                </td>
                <td>
                    <sd:TextBox id="brand"   name="laptop.brand" key="Hãng" trim="true" readonly="true" maxlength="50"/>
                </td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="name"   name="laptop.name" key="Tên" trim="true" readonly="true" maxlength="250"/>
                </td>
                <td>
                    <sd:TextBox id="model"   name="laptop.model" key="Model" trim="true" maxlength="250" readonly="true"/>
                </td>
                <td>
                    <sd:TextBox id="partno" name="laptop.partno" key="PartNo" trim="true" readonly="true" maxlength="50"/>
                </td>
            </tr>

            <tr>
                <td>
                    <sd:TextBox id="price"   name="laptop.price" key="Giá" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:TextBox id="chip"   name="laptop.chip" key="Chip" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:TextBox id="speed"  name="laptop.speed" key="Speed" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="storage"  name="laptop.storage" key="HDD" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:TextBox id="hddType"  name="laptop.hddType" key="Loại HDD" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:TextBox id="ram"   name="laptop.ram" key="Ram" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="vga"   name="laptop.vga" key="VGA" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:TextBox id="screen"   name="laptop.screen" key="Màn Hình" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:TextBox id="touchscreen"   name="laptop.touchscreen" key="Cảm Ứng" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="os"   name="laptop.os" key="OS" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:TextBox id="dvd"   name="laptop.dvd" key="Ổ DVD" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:TextBox id="battery"   name="laptop.battery" key="Pin" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="promotion"   name="laptop.promotion" key="Khuyến Mại" trim="true" maxlength="500" readonly="true" />
                </td>
            </tr>
        </table>

    </s:form>
</sd:TitlePane>
    <br>
<sd:TitlePane key="Danh Sách Các Web Bán" id="laptopCompareList">

    <form id="laptopCompareListForm" name="laptopCompareListForm">
        <div id="laptopCompareListDiv" >
            <sd:DataGrid clientSort="true" getDataUrl="laptopCompareSearch!onSearch.do" id="laptopCompareListGird" style="width: 100%; height: 100%;" rowsPerPage="50">
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
                <sd:ColumnDataGrid editable="true"  field="lastUpdate" key="Ngày Cập Nhật" format="dd/MM/yyyy" width="6%" type="date" />
            </sd:DataGrid>
        </div>
    </form>
</sd:TitlePane>

<script>

    page.onSearch = function(){
        var xhrArgs = {
            url: "laptopCompareBeforeSearch.do",
            // Handle the result as JSON data
            handleAs: "json",
            content: {
                itemCode: document.getElementById("itemCode").value
            },
            load: function(data) {
                if(data.result != null){
                    notification("ERROR",data.result);
                    return;
                }
                document.getElementById("web").value = data.laptop.web;
                document.getElementById("id").value = data.laptop.id;
                document.getElementById("type").value = data.laptop.type;
                document.getElementById("brand").value = data.laptop.brand;
                document.getElementById("name").value = data.laptop.name;
                document.getElementById("model").value = data.laptop.model;
                document.getElementById("partno").value = data.laptop.partno;
                document.getElementById("price").value = data.laptop.price;
                document.getElementById("chip").value = data.laptop.chip;
                document.getElementById("speed").value = data.laptop.speed;
                document.getElementById("storage").value = data.laptop.storage;
                document.getElementById("hddType").value = data.laptop.hddType;
                document.getElementById("ram").value = data.laptop.ram;
                document.getElementById("vga").value = data.laptop.vga;
                document.getElementById("screen").value = data.laptop.screen;
                document.getElementById("touchscreen").value = data.laptop.touchscreen;
                document.getElementById("os").value = data.laptop.os;
                document.getElementById("battery").value = data.laptop.battery;
                document.getElementById("promotion").value = data.laptop.promotion;
                dijit.byId("laptopCompareListGird").vtReload("laptopCompareSearch!onSearch.do","laptopCompareForm",null,page.showMessage) ;
            },
            error: function(error) {
                notification("ERROR",error.result);
            }
        };
        dojo.xhrGet(xhrArgs);
        
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