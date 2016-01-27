<%-- 
    Document   : tabletModelCompare
    Created on : Mar 30, 2015, 1:29:12 AM
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
<sd:TitlePane id="tabletModelCompare"  key="So sánh Model">
    <s:form  id="tabletModelCompareForm"  name="tabletModelCompareForm"  method="POST">
        <table width="100%">
            <tr>
                <td>
                    <sd:MultiSelect id="web" key="Web" name="webs" required="true">
                        <sd:Option value="FPTShop" >FPT Shop</sd:Option>
                        <sd:Option value="MediaMart" >Media Mart</sd:Option>
                        <sd:Option value="TGDD">Thế Giới Di Động</sd:Option>
                        <sd:Option value="TranAnh" >Trần Anh</sd:Option>
                        <sd:Option value="VienThongA" >Viễn Thông A</sd:Option>
                    </sd:MultiSelect>
                </td>
                <td>
                    <sd:CheckBox id="brand" checked="true" name="tablet.brand" key="Hãng" readonly="true"/>
                </td>
                <td>
                    <sd:CheckBox id="model" checked="true" name="tablet.model" key="Model" readonly="true"/>
                </td>
                <td>
                    <sd:CheckBox id="storage" checked="false" name="tablet.storage" key="Storage"/>
                </td>
                <td>
                    <sd:CheckBox id="ram" checked="false" name="tablet.ram" key="Ram"/>
                </td>
                <td>
                    <sd:CheckBox id="check" checked="false" name="check" key="Có/Không Có" />
                </td>
                <td>
                    <sd:DatePicker id="date" name="date" key="Ngày lấy dữ liệu"  required="true" format="dd/MM/yyyy"/>
                </td>
                
            </tr>
            <tr>
                <td colspan ="7" align="center">
                    <sd:Button key="Search" onclick="page.onSearch()">
                            <img src="share/Img/icons/search.png" height="17" width="20">
                    </sd:Button>
                    
                    <sd:Button key="Export" onclick="page.exportExcel()">
                            <img src="share/Img/Icon/export.png" height="17" width="20">
                    </sd:Button>
                </td>
            </tr>
        </table>
        <div id ="result">

        </div>
    </s:form>
</sd:TitlePane>
    <br>
<sd:TitlePane key="Danh Sách So Sánh" id="tabletModelCompareList">
    <form id="tabletModelCompareListForm" name="tabletModelCompareListForm">
        <div id="tabletModelCompareListDiv" >
            <sd:DataGrid clientSort="true" getDataUrl="tabletModelCompareSearch!onSearch.do" id="tabletModelCompareListGird" style="width: 100%; height: 100%;" rowsPerPage="10">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="false" field="id" key="id" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="link" key="Link" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="true" field="web" key="Web" width="4%" headerStyles="text-align: center;" styles="text-align: left; color:blue; font-weight:bold; text-decoration:  underline;" />
                <sd:ColumnDataGrid editable="true" field="type" key="Loại" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="brand" key="Hãng" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="price" key="Giá" width="7%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="itemCode" key="Mã SP" width="7%" headerStyles="text-align: center;" />
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
                <sd:ColumnDataGrid editable="true"  field="lastUpdate" key="Ngày Cập Nhật" format="dd/MM/yyyy" width="6%" type="date" headerStyles="text-align: center;"/>.
            </sd:DataGrid>
        </div>
    </form>
    <div id="result"></div>
</sd:TitlePane>

<script>
    dijit.byId('date').attr('value',new Date());

    page.onSearch = function(){
//	notification('error','xin chao');
        var callback = function(data) {
                notification(data.label,data.customInfo);
            }
        dijit.byId('tabletModelCompareListGird').vtReload("tabletModelCompareSearch!onSearch.do","tabletModelCompareForm",null,callback) ;
    }

    dojo.connect(dijit.byId("tabletModelCompareListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("tabletModelCompareListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "web"){
            var win = window.open(grid.getCell(2).get(idx, item), '_blank');
            win.focus();
        }
    });

    page.exportExcel = function(){
//        window.location = "tabletModelCompareExport!exportExcel.do";
        sd.connector.post("tabletModelCompareExport!exportExcel.do","result","tabletModelCompareForm");
    }

</script>