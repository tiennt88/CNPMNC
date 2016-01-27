<%--
    Document   : MobileComparePopUp
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

<sd:TitlePane id="mobileCompare"  key="Tra Cứu Thông Tin Mobile">
    <s:form  id="mobileCompareForm"  name="mobileCompareForm"  method="POST">
        <table width="100%">
            <tr>
                <s:hidden name="mobile.id" id="id"/>
               <td>
                    <sd:TextBox id="itemCode" name="mobile.itemCode"  key="Mã Sản Phẩm" trim="true"  maxlength="50" required="true" />
               </td>
                <td colspan="2">
                   <table width="100%">
                       <tr>
                           <td>
                               <sd:CheckBox id="mobileCheck.brand" checked="true" name="mobileCheck.brand" key="Brand"/>
                           </td>
                           <td>
                               <sd:CheckBox id="mobileCheck.model" checked="true"  name="mobileCheck.model" key="Model"/>
                           </td>
                           <td>
                               <sd:CheckBox id="mobileCheck.storage" checked="false" name="mobileCheck.storage" key="Storage"/>
                           </td>
                           <td>
                               <sd:CheckBox id="mobileCheck.ram" checked="false" name="mobileCheck.ram" key="Ram"/>
                           </td>
                           <td>
                               <sd:CheckBox id="mobileCheck.color" checked="false" name="mobileCheck.color" key="Mầu"/>
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
                    <sd:Textarea id="web"  rows="1" name="mobile.web" key="Web" trim="true" readonly="true" maxlength="50"/>
                </td>
                <td>
                    <sd:Textarea id="type" rows="1"  name="mobile.type" key="Loại" trim="true" readonly="true" maxlength="50"/>
                </td>
                <td>
                    <sd:Textarea id="brand" rows="1"  name="mobile.brand" key="Hãng" trim="true" readonly="true" maxlength="50"/>
                </td>
            </tr>
            <tr>
                <td>
                    <sd:Textarea id="name"  rows="1" name="mobile.name" key="Tên" trim="true" readonly="true" maxlength="250"/>
                </td>
                <td>
                    <sd:Textarea id="model" rows="1"  name="mobile.model" key="Model" trim="true" maxlength="250" readonly="true"/>
                </td>

                <td>
                    <sd:Textarea id="partno" rows="1" name="mobile.partno" key="PartNo" trim="true" readonly="true" maxlength="50"/>
                </td>
            </tr>

            <tr>
                <td>
                    <sd:Textarea id="price"  rows="1" name="mobile.price" key="Giá" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="storage" rows="1"  name="mobile.storage" key="Bộ nhớ trong" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="ram" rows="1"  name="mobile.ram" key="Ram" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:Textarea id="screen"  rows="1" name="mobile.screen" key="Màn Hình" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="cpu" rows="1"  name="mobile.cpu" key="CPU" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="os" rows="1"  name="mobile.os" key="OS" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:Textarea id="backCamera" rows="1"  name="mobile.backCamera" key="Camera Chính" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="frontCamera" rows="1"  name="mobile.frontCamera" key="Camera Phụ" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="battery" rows="1"  name="mobile.battery" key="Pin" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:Textarea id="sim" rows="1"  name="mobile.sim" key="Sim" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="color" rows="1"  name="mobile.color" key="Màu" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="promotion" rows="1"  name="mobile.promotion" key="Khuyến Mại" trim="true" maxlength="500" readonly="true" />
                </td>
            </tr>
        </table>

    </s:form>
</sd:TitlePane>
    <br>
<sd:TitlePane key="Danh Sách Các Web Bán" id="mobileCompareList">

    <form id="mobileCompareListForm" name="mobileCompareListForm">
        <div id="mobileCompareListDiv" >
            <sd:DataGrid clientSort="true" getDataUrl="mobileCompareSearch!onSearch.do" id="mobileCompareListGird" style="width: 100%; height: 100%;" rowsPerPage="500">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="false" field="id" key="id" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="link" key="Link" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="true" field="web" key="Web" width="4%" headerStyles="text-align: center;" styles="text-align: left; color:blue; font-weight:bold; text-decoration:  underline;" />
                <sd:ColumnDataGrid editable="true" field="type" key="Loại" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="brand" key="Hãng" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="price" key="Giá" width="7%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="name" key="Tên SP" width="10%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="model" key="Model" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="storage" key="Bộ nhớ trong" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="ram" key="Ram" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="cpu" key="Cpu" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="backCamera" key="Camera chính" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="frontCamera" key="Camera Phụ" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="os" key="Hệ điều hành" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="battery" key="Pin" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="sim" key="Sim" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="color" key="Color" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="promotion" key="Khuyến Mại" width="auto" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true"  field="lastUpdate" key="Ngày Cập Nhật" format="dd/MM/yyyy" width="6%" type="date" headerStyles="text-align: center;" />
            </sd:DataGrid>
        </div>
    </form>
</sd:TitlePane>

<script>

    page.onExit = function(){
       window.close();
    }

    page.onSearch = function(){
//        var xhrArgs = {
//            url: "mobileCompareBeforeSearch.do",
//            // Handle the result as JSON data
//            handleAs: "json",
//            content: {
//                id: document.getElementById("id").value
//            },
//            load: function(data) {
//                if(data.result != null){
//                    notification("ERROR",data.result);
//                    return;
//                }
//                document.getElementById("web").value = data.mobile.web;
//                document.getElementById("id").value = data.mobile.id;
//                document.getElementById("type").value = data.mobile.type;
//                document.getElementById("brand").value = data.mobile.brand;
//                document.getElementById("name").value = data.mobile.name;
//                document.getElementById("model").value = data.mobile.model;
//                document.getElementById("storage").value = data.mobile.storage;
//                document.getElementById("ram").value = data.mobile.ram;
//                document.getElementById("partno").value = data.mobile.partno;
//                document.getElementById("price").value = data.mobile.price;
//                document.getElementById("screen").value = data.mobile.screen;
//                document.getElementById("cpu").value = data.mobile.cpu;
//                document.getElementById("os").value = data.mobile.os;
//                document.getElementById("backCamera").value = data.mobile.backCamera;
//                document.getElementById("frontCamera").value = data.mobile.frontCamera;
//                document.getElementById("battery").value = data.mobile.battery;
//                document.getElementById("sim").value = data.mobile.sim;
//                document.getElementById("promotion").value = data.mobile.promotion;
//                document.getElementById("color").value = data.mobile.color;
//                dijit.byId("mobileCompareListGird").vtReload("mobileCompareSearch!onSearch.do","mobileCompareForm",null,page.showMessage) ;
//            },
//            error: function(error) {
//                notification("ERROR",error.result);
//            }
//        };
//        dojo.xhrGet(xhrArgs);
        var callback = function(data) {
                notification(data.label,data.customInfo);
            }
        dijit.byId("mobileCompareListGird").vtReload("mobileCompareSearch!onSearch.do","mobileCompareForm",null,page.showMessage) ;
    }

    dojo.connect(dijit.byId("mobileCompareListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("mobileCompareListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "web"){
            var win = window.open(grid.getCell(2).get(idx, item), '_blank');
            win.focus();
        }
    });

</script>