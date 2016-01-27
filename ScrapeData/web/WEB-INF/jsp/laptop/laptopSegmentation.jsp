<%-- 
    Document   : laptopSegmentation
    Created on : Apr 23, 2015, 1:24:11 AM
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
<sd:TitlePane id="laptopSegmentation"  key="So sánh Model">
    <s:form  id="laptopSegmentationForm"  name="laptopSegmentationForm"  method="POST">
        <table width="100%">
            <tr>
                <td>
                    <sd:MultiSelect id="web" key="Web" name="webs" required="true" data="lstWeb" labelField="name" valueField="value"/>
                </td>
                <td colspan ="2">
                    <table width="100%">
                        <tr>
                            <td>
                                <sd:SelectBox id="chip" key="Chip" name="laptop.chip" data="chips" valueField="value" labelField="name" />
                            </td>
                            <td>
                                <sd:SelectBox id="speed" key="Speed" name="laptop.speed" data="speedClass" valueField="value" labelField="name" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <sd:SelectBox id="storage" key="Bộ nhớ trong" name="laptop.storage" data="storageClass" valueField="value" labelField="name" />
                            </td>
                            <td>
                                <sd:SelectBox id="hddType" key="Loại HDD" name="laptop.hddType" data="hddTypes" valueField="value" labelField="name" required="true"/>
                            </td>
                            
                        </tr>
                        <tr>
                            <td>
                                <sd:SelectBox id="ram" key="Ram" name="laptop.ram" data="rams" valueField="value" labelField="name" />
                            </td>
                            <td>
                                <sd:SelectBox id="vga" key="VGA" name="laptop.vga" data="vgas" valueField="value" labelField="name" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <sd:SelectBox id="screen" key="Screen" name="laptop.screen" data="screenClass" valueField="value" labelField="name" />
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
                            
                        </tr>
                        
                        <tr>
                            <td>
                                <sd:SelectBox id="price" key="Giá" name="laptop.price" data="priceClass" valueField="value" labelField="name" />
                            </td>
                            <td>
                                <sd:DatePicker id="date" name="date" key="Ngày lấy dữ liệu"  required="true" format="dd/MM/yyyy"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            
            <tr>
                <td colspan ="3" align="center">
                    <sd:Button key="Search" onclick="page.onSearch()">
                            <img src="share/Img/icons/search.png" height="17" width="20">
                    </sd:Button>
                </td>
            </tr>
        </table>
        <div id ="result">

        </div>
    </s:form>
</sd:TitlePane>
    <br>
<sd:TitlePane key="Danh Sách So Sánh" id="laptopSegmentationList">
    <form id="laptopSegmentationListForm" name="laptopSegmentationListForm">
        <div id="laptopSegmentationListDiv" >
            <sd:DataGrid clientSort="true" getDataUrl="laptopSegmentationSearch!onSearch.do" id="laptopSegmentationListGird" style="width: 100%; height: 100%;" rowsPerPage="10">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
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
                <sd:ColumnDataGrid editable="true" field="promotion" key="Khuyến mại" width="15%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true"  field="lastUpdate" key="Ngày Cập Nhật" format="dd/MM/yyyy" width="6%" type="date" headerStyles="text-align: center;"/>.
            </sd:DataGrid>
        </div>
    </form>
    <div id="result"></div>
</sd:TitlePane>

<script>
    dijit.byId('date').attr('value',new Date());
    dijit.byId("chip").setAttribute('value', '');
    dijit.byId("speed").setAttribute('value', '');
    dijit.byId("storage").setAttribute('value', '');
    dijit.byId("hddType").setAttribute('value', '');
    dijit.byId("ram").setAttribute('value', '');
    dijit.byId("vga").setAttribute('value', '');
    dijit.byId("screen").setAttribute('value', '');
    dijit.byId("touchscreen").setAttribute('value', '');
    dijit.byId("os").setAttribute('value', '');
    dijit.byId("dvd").setAttribute('value', '');
    dijit.byId("price").setAttribute('value', '');

    page.onSearch = function(){
//	notification('error','xin chao');
        var callback = function(data) {
                notification(data.label,data.customInfo);
            }
        dijit.byId('laptopSegmentationListGird').vtReload("laptopSegmentationSearch!onSearch.do","laptopSegmentationForm",null,callback) ;
    }

    dojo.connect(dijit.byId("laptopSegmentationListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("laptopSegmentationListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "web"){
            var win = window.open(grid.getCell(1).get(idx, item), '_blank');
            win.focus();
        }
    });

</script>