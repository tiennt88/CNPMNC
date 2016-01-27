<%--
    Document   : tabletComparePopUp
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
                    <sd:Textarea id="web"  rows="1" name="tablet.web" key="Web" trim="true" readonly="true" maxlength="50"/>
                </td>
                <td>
                    <sd:Textarea id="type" rows="1"  name="tablet.type" key="Loại" trim="true" readonly="true" maxlength="50"/>
                </td>
                <td>
                    <sd:Textarea id="brand" rows="1"  name="tablet.brand" key="Hãng" trim="true" readonly="true" maxlength="50"/>
                </td>
            </tr>
            <tr>
                <td>
                    <sd:Textarea id="name"  rows="1" name="tablet.name" key="Tên" trim="true" readonly="true" maxlength="250"/>
                </td>
                <td>
                    <sd:Textarea id="model" rows="1"  name="tablet.model" key="Model" trim="true" maxlength="250" readonly="true"/>
                </td>

                <td>
                    <sd:Textarea id="partno" rows="1" name="tablet.partno" key="PartNo" trim="true" readonly="true" maxlength="50"/>
                </td>
            </tr>

            <tr>
                <td>
                    <sd:Textarea id="price"  rows="1" name="tablet.price" key="Giá" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="storage" rows="1"  name="tablet.storage" key="Bộ nhớ trong" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="ram" rows="1"  name="tablet.ram" key="Ram" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:Textarea id="screen"  rows="1" name="tablet.screen" key="Màn Hình" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="cpu" rows="1"  name="tablet.cpu" key="CPU" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="os" rows="1"  name="tablet.os" key="OS" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:Textarea id="backCamera" rows="1"  name="tablet.backCamera" key="Camera Chính" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="frontCamera" rows="1"  name="tablet.frontCamera" key="Camera Phụ" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:Textarea id="battery" rows="1"  name="tablet.battery" key="Pin" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:Textarea id="sim" rows="1"  name="tablet.sim" key="Sim" trim="true" maxlength="50" readonly="true" />
                </td>
                
                <td>
                    <sd:Textarea id="promotion" rows="1"  name="tablet.promotion" key="Khuyến Mại" trim="true" maxlength="500" readonly="true" />
                </td>
            </tr>
        </table>

    </s:form>
</sd:TitlePane>
    <br>
<sd:TitlePane key="Danh Sách Các Web Bán" id="tabletCompareList">

    <form id="tabletCompareListForm" name="tabletCompareListForm">
        <div id="tabletCompareListDiv" >
            <sd:DataGrid clientSort="true" getDataUrl="tabletCompareSearch!onSearch.do" id="tabletCompareListGird" style="width: 100%; height: 100%;" rowsPerPage="500">
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
                <sd:ColumnDataGrid editable="true" field="promotion" key="Khuyến Mại" width="auto" headerStyles="text-align: center;"  />
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
        dijit.byId("tabletCompareListGird").vtReload("tabletCompareSearch!onSearch.do","tabletCompareForm",null,page.showMessage) ;
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