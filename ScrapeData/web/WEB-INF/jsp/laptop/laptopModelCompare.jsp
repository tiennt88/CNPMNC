<%-- 
    Document   : laptopModelCompare
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
<sd:TitlePane id="laptopModelCompare"  key="So sánh Model">
    <s:form  id="laptopModelCompareForm"  name="laptopModelCompareForm"  method="POST">
        <table width="100%">
            <tr>
                <td>
                    <sd:MultiSelect id="web" key="Web" name="webs" required="true" data="lstWeb" labelField="name" valueField="value"/>
                </td>
                <td>
                    <table width="100%">
                        <tr>
                            <td>
                                <sd:CheckBox id="brand" checked="true" name="laptop.brand" key="Hãng" readonly="true"/>
                            </td>
                            <td>
                                <sd:CheckBox id="model" checked="true" name="laptop.model" key="Model" readonly="true"/>
                            </td>
                            <td>
                                <sd:CheckBox id="chip" checked="false" name="laptop.chip" key="Chip"   />
                            </td>
                            <td>
                                <sd:CheckBox id="storage" checked="false" name="laptop.storage" key="Storage"/>
                            </td>

                        </tr>
                        <tr>
                            <td>
                                <sd:CheckBox id="ram" checked="false" name="laptop.ram" key="Ram"/>
                            </td>
                            <td>
                                <sd:CheckBox id="vga" checked="false" name="laptop.vga" key="VGA"/>
                            </td>
                            <td>
                                <sd:CheckBox id="screen" checked="false" name="laptop.screen" key="Màn hình"/>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>
                                <sd:CheckBox id="check" checked="false" name="check" key="Có/Không Có" />
                            </td>
                            <td colspan="3">
                                <sd:DatePicker id="date" name="date" key="Ngày lấy dữ liệu"  required="true" format="dd/MM/yyyy"/>
                            </td>
                            
                        </tr>
                    </table>
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
<sd:TitlePane key="Danh Sách So Sánh" id="laptopModelCompareList">
    <form id="laptopModelCompareListForm" name="laptopModelCompareListForm">
        <div id="laptopModelCompareListDiv" >
            <sd:DataGrid clientSort="true" getDataUrl="laptopModelCompareSearch!onSearch.do" id="laptopModelCompareListGird" style="width: 100%; height: 100%;" rowsPerPage="10">
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
                <sd:ColumnDataGrid editable="true"  field="lastUpdate" key="Ngày Cập Nhật" format="dd/MM/yyyy" width="6%" type="date" />.
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
        dijit.byId('laptopModelCompareListGird').vtReload("laptopModelCompareSearch!onSearch.do","laptopModelCompareForm",null,callback) ;
    }

    dojo.connect(dijit.byId("laptopModelCompareListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("laptopModelCompareListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "web"){
            var win = window.open(grid.getCell(2).get(idx, item), '_blank');
            win.focus();
        }
    });

    page.exportExcel = function(){
//        window.location = "laptopModelCompareExport!exportExcel.do";
        sd.connector.post("laptopModelCompareExport!exportExcel.do","result","laptopModelCompareForm");
    }

</script>