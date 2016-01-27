<%-- 
    Document   : tabletPriceDiffirentLevels
    Created on : Apr 24, 2015, 1:02:18 AM
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

    page.formatterNumber = function(number){
        if(number!=null){
           return Number(number.toFixed()).toLocaleString() + "₫";
        }
        return null;
    }

</script>
<sd:TitlePane id="tabletPriceDifferentLevels"  key="So sánh Model">
    <s:form  id="tabletPriceDifferentLevelsForm"  name="tabletPriceDifferentLevelsForm"  method="POST">
        <table width="100%">
            <tr>
                <td>
                    <sd:MultiSelect id="web" key="Web" name="webs" required="true" data="lstWeb" labelField="name" valueField="value"/>
                </td>
                <td colspan="2">
                    <table width="100%">
                        <tr>
                            <td>
                                <sd:CheckBox id="brand" checked="true" name="tablet.brand" key="Brand" readonly="true"/>
                            </td>
                            <td>
                                <sd:CheckBox id="model" checked="true"  name="tablet.model" key="Model" readonly="true"/>
                            </td>
                            <td>
                                <sd:CheckBox id="storage" checked="false" name="tablet.storage" key="Storage"/>
                            </td>
                            <td>
                                <sd:CheckBox id="ram" checked="false" name="tablet.ram" key="Ram"/>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>
                                <sd:CheckBox id="check" name="check" key="Thấp nhất/Cao nhất"/>
                            </td>
                            <td colspan="3">
                                <sd:NumberSpinner id="priceDifferent" key="Chênh lệch giá" name="priceDifferent" value="0" />
                            </td>
<!--                            <td >-->
                                <%--sd:DatePicker id="date" name="tablet.date" key="Ngày"  required="true" format="dd/MM/yyyy"/--%>
<!--                            </td>-->
                        </tr>
                    </table>
                </td>

            </tr>

            <tr>
                <td colspan ="3" align="center">
                    <sd:Button key="Search" onclick="page.onSearch()">
                        <img src="share/Img/icons/search.png" height="17" width="20">
                    </sd:Button>
                    <sd:Button id="export" key="Export Excel"
                               onclick="page.exportExcel()">
                        <img alt="reset"  src="share/Img/Icon/export.png" height="17" width="20">
                    </sd:Button>
                </td>
            </tr>
        </table>
        <div id ="result">

        </div>
    </s:form>
</sd:TitlePane>
<br>
<sd:TitlePane key="Danh Sách So Sánh" id="tabletPriceDifferentLevelsList">
    <form id="tabletPriceDifferentLevelsListForm" name="tabletPriceDifferentLevelsListForm">
        <div id="tabletPriceDifferentLevelsListDiv" >
            <sd:DataGrid clientSort="true" getDataUrl="tabletPriceDifferentLevelsSearch!onSearch.do" id="tabletPriceDifferentLevelsListGird" style="width: 100%; height: 100%;" rowsPerPage="10">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="true" field="web" key="Web" width="4%" headerStyles="text-align: center;" styles="text-align: left; color:blue; font-weight:bold; text-decoration:  underline;" />
                <sd:ColumnDataGrid editable="true" field="type" key="Loại" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="brand" key="Hãng" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="model" key="Model" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="mi" key="Min" width="7%" headerStyles="text-align: left;" formatter="page.formatterNumber"/>
                <sd:ColumnDataGrid editable="true" field="ma" key="Max" width="7%" headerStyles="text-align: left;" formatter="page.formatterNumber"/>
                <sd:ColumnDataGrid editable="true" field="storage" key="Bộ nhớ trong" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="ram" key="Ram" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true"  field="lastUpdate" key="Ngày Cập Nhật" format="dd/MM/yyyy" width="6%" type="date" headerStyles="text-align: center;" />.
                <sd:ColumnDataGrid editable="true" field=""  key="Chi Tiết" type="radio"  width="3%" headerStyles="text-align: center;" styles="text-align: center;" />
            </sd:DataGrid>
        </div>
    </form>
    <div id="result"></div>
</sd:TitlePane>

<script>
//    dijit.byId('date').attr('value',new Date());

    page.onSearch = function(){
        //	notification('error','xin chao');
        var callback = function(data) {
            notification(data.label,data.customInfo);
        }
        dijit.byId('tabletPriceDifferentLevelsListGird').vtReload("tabletPriceDifferentLevelsSearch!onSearch.do","tabletPriceDifferentLevelsForm",null,callback) ;
    }

    dojo.connect(dijit.byId("tabletPriceDifferentLevelsListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("tabletPriceDifferentLevelsListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "_R_D_"){
            openPopup("tabletPriceDifferentLevelPopUp.do?brand="+grid.getCell(3).get(idx, item)+"&model="+grid.getCell(4).get(idx, item)+"&storage="+grid.getCell(7).get(idx, item)+"&ram="+grid.getCell(8).get(idx, item)+"&date="+grid.getCell(9).get(idx, item).substring(0,10),1000,550);
//            javascript:doGoToMenu("tabletCompare.do?id="+grid.getCell(1).get(idx, item), "0.2.0");
        }
    });

    function openPopup(path,width,height) {
        var left = (screen.width/2)-(width/2);
        var top = (screen.height/2)-(height/2);
        my_window = window.open(path,'',"location=" + (screen.width) + ",status=1, scrollbars=1, titlebar=0,top="+top+", left="+left+", width=" +width + ",height=" + height);
        my_window.focus();
        return false;
    }

    page.exportExcel = function(){
        //        window.location = "tabletPriceDifferentLevelsExport!exportExcel.do";
        sd.connector.post("tabletPriceDifferentLevelsExport!exportExcel.do","result","tabletPriceDifferentLevelsForm");
    }

</script>