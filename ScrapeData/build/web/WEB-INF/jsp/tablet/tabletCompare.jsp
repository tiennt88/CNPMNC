<%--
    Document   : tabletCompare
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

<sd:TitlePane id="tabletCompare"  key="Tra Cứu Thông Tin tablet">
    <s:form  id="tabletCompareForm"  name="tabletCompareForm"  method="POST">
        <table width="100%">
            <tr>
                <s:hidden name="tablet.id" id="id"/>
               <td>
                    <sd:TextBox id="itemCode" name="tablet.itemCode"  key="Mã Sản Phẩm" trim="true"  maxlength="50" required="true" />
               </td>
               <td colspan="2">
                   <table width="100%">
                       <tr>
                           <td>
                               <sd:CheckBox id="tabletCheck.brand" checked="true" name="tabletCheck.brand" key="Brand"/>
                           </td>
                           <td>
                               <sd:CheckBox id="tabletCheck.model" checked="true"  name="tabletCheck.model" key="Model"/>
                           </td>
                           <td>
                               <sd:CheckBox id="tabletCheck.storage" checked="false" name="tabletCheck.storage" key="Storage"/>
                           </td>
                           <td>
                               <sd:CheckBox id="tabletCheck.ram" checked="false" name="tabletCheck.ram" key="Ram"/>
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
                    <sd:TextBox id="web"   name="tablet.web" key="Web" trim="true" readonly="true" maxlength="50"/>
                </td>
                <td>
                    <sd:TextBox id="type"   name="tablet.type" key="Loại" trim="true" readonly="true" maxlength="50"/>
                </td>
                <td>
                    <sd:TextBox id="brand"   name="tablet.brand" key="Hãng" trim="true" readonly="true" maxlength="50"/>
                </td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="name"   name="tablet.name" key="Tên" trim="true" readonly="true" maxlength="250"/>
                </td>
                <td>
                    <sd:TextBox id="model"   name="tablet.model" key="Model" trim="true" maxlength="250" readonly="true"/>
                </td>

                <td>
                    <sd:TextBox id="partno" name="tablet.partno" key="PartNo" trim="true" readonly="true" maxlength="50"/>
                </td>
            </tr>

            <tr>
                <td>
                    <sd:TextBox id="price"   name="tablet.price" key="Giá" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:TextBox id="storage"  name="tablet.storage" key="Bộ nhớ trong" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:TextBox id="ram"   name="tablet.ram" key="Ram" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="screen"   name="tablet.screen" key="Màn Hình" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:TextBox id="cpu"   name="tablet.cpu" key="CPU" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:TextBox id="os"   name="tablet.os" key="OS" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="backCamera"   name="tablet.backCamera" key="Camera Chính" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:TextBox id="frontCamera"   name="tablet.frontCamera" key="Camera Phụ" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:TextBox id="battery"   name="tablet.battery" key="Pin" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="sim"   name="tablet.sim" key="Sim" trim="true" maxlength="50" readonly="true" />
                </td>
                
                <td>
                    <sd:TextBox id="promotion"   name="tablet.promotion" key="Khuyến Mại" trim="true" maxlength="500" readonly="true" />
                </td>
            </tr>
        </table>

    </s:form>
</sd:TitlePane>
    <br>
<sd:TitlePane key="Danh Sách Các Web Bán" id="tabletCompareList">

    <form id="tabletCompareListForm" name="tabletCompareListForm">
        <div id="tabletCompareListDiv" >
            <sd:DataGrid clientSort="true" getDataUrl="tabletCompareSearch!onSearch.do" id="tabletCompareListGird" style="width: 100%; height: 100%;" rowsPerPage="50">
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
                <sd:ColumnDataGrid editable="true" field="screen" key="Màn hình" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="cpu" key="Cpu" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="backCamera" key="Camera chính" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="frontCamera" key="Camera Phụ" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="os" key="Hệ điều hành" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="battery" key="Pin" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="sim" key="Sim" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="promotion" key="Khuyến Mại" width="15%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true"  field="lastUpdate" key="Ngày Cập Nhật" format="dd/MM/yyyy" width="6%" type="date" headerStyles="text-align: center;"/>
            </sd:DataGrid>
        </div>
    </form>
</sd:TitlePane>

<script>

    page.onSearch = function(){
        var xhrArgs = {
            url: "tabletCompareBeforeSearch.do",
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
                document.getElementById("web").value = data.tablet.web;
                document.getElementById("id").value = data.tablet.id;
                document.getElementById("type").value = data.tablet.type;
                document.getElementById("brand").value = data.tablet.brand;
                document.getElementById("name").value = data.tablet.name;
                document.getElementById("model").value = data.tablet.model;
                document.getElementById("storage").value = data.tablet.storage;
                document.getElementById("ram").value = data.tablet.ram;
                document.getElementById("partno").value = data.tablet.partno;
                document.getElementById("price").value = data.tablet.price;
                document.getElementById("screen").value = data.tablet.screen;
                document.getElementById("cpu").value = data.tablet.cpu;
                document.getElementById("os").value = data.tablet.os;
                document.getElementById("backCamera").value = data.tablet.backCamera;
                document.getElementById("frontCamera").value = data.tablet.frontCamera;
                document.getElementById("battery").value = data.tablet.battery;
                document.getElementById("sim").value = data.tablet.sim;
                document.getElementById("promotion").value = data.tablet.promotion;
                dijit.byId("tabletCompareListGird").vtReload("tabletCompareSearch!onSearch.do","tabletCompareForm",null,page.showMessage) ;
            },
            error: function(error) {
                notification("ERROR",error.result);
            }
        };
        dojo.xhrGet(xhrArgs);
        
    }

    dojo.connect(dijit.byId("tabletCompareListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("tabletCompareListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "web"){
            var win = window.open(grid.getCell(2).get(idx, item), '_blank');
            win.focus();
        }
    });

</script>