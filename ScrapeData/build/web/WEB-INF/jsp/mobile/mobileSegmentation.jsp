<%-- 
    Document   : mobileSegmentation
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
<sd:TitlePane id="mobileSegmentation"  key="So sánh Model">
    <s:form  id="mobileSegmentationForm"  name="mobileSegmentationForm"  method="POST">
        <table width="100%">
            <tr>
                <td>
                    <sd:MultiSelect id="web" key="Web" name="webs" required="true" data="lstWeb" labelField="name" valueField="value"/>
                </td>
                <td colspan ="2">
                    <table width="100%">
                        <tr>
                            <td>
                                <sd:SelectBox id="storage" key="Bộ nhớ trong" name="mobile.storage" data="storageClass" valueField="value" labelField="name" />
                            </td>
                            <td>
                                <sd:SelectBox id="ram" key="Ram" name="mobile.ram" data="ramClass" valueField="value" labelField="name" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <sd:SelectBox id="cpu" key="CPU" name="mobile.cpu" data="cpus" valueField="value" labelField="name" />
                            </td>
                            <td>
                                <sd:SelectBox id="speed" key="Speed" name="mobile.speed" data="speedClass" valueField="value" labelField="name" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <sd:SelectBox id="screen" key="Screen" name="mobile.screen" data="screenClass" valueField="value" labelField="name" />
                            </td>
                            <td>
                                <sd:SelectBox id="sim" key="Sim" name="mobile.sim" data="sims" valueField="value" labelField="name" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <sd:SelectBox id="backCamera" key="Camera Sau" name="mobile.backCamera" data="backCameraClass" valueField="value" labelField="name" />
                            </td>
                            <td>
                                <sd:SelectBox id="frontCamera" key="Camera Trước" name="mobile.frontCamera" data="frontCameraClass" valueField="value" labelField="name" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <sd:SelectBox id="os" key="Hệ điều hành" name="mobile.os" data="oss" valueField="value" labelField="name" />
                            </td>
                            <td>
                                <sd:SelectBox id="battery" key="Pin" name="mobile.battery" data="batteryClass" valueField="value" labelField="name" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <sd:SelectBox id="price" key="Giá" name="mobile.price" data="priceClass" valueField="value" labelField="name" />
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
<sd:TitlePane key="Danh Sách So Sánh" id="mobileSegmentationList">
    <form id="mobileSegmentationListForm" name="mobileSegmentationListForm">
        <div id="mobileSegmentationListDiv" >
            <sd:DataGrid clientSort="true" getDataUrl="mobileSegmentationSearch!onSearch.do" id="mobileSegmentationListGird" style="width: 100%; height: 100%;" rowsPerPage="10">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="false" field="link" key="Link" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="true" field="web" key="Web" width="4%" headerStyles="text-align: center;" styles="text-align: left; color:blue; font-weight:bold; text-decoration:  underline;" />
                <sd:ColumnDataGrid editable="true" field="type" key="Loại" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="brand" key="Hãng" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="price" key="Giá" width="7%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="itemCode" key="Mã SP" width="7%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="name" key="Tên SP" width="10%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="model" key="Model" width="8%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="storage" key="Bộ nhớ trong" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="ram" key="Ram" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="screen" key="Màn hình" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="cpu" key="Cpu" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="backCamera" key="Camera chính" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="frontCamera" key="Camera Phụ" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="os" key="Hệ điều hành" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="battery" key="Pin" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="sim" key="Sim" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="color" key="Mầu" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true"  field="lastUpdate" key="Ngày Cập Nhật" format="dd/MM/yyyy" width="6%" type="date"  headerStyles="text-align: center;" />.
            </sd:DataGrid>
        </div>
    </form>
    <div id="result"></div>
</sd:TitlePane>

<script>
    dijit.byId('date').attr('value',new Date());
    dijit.byId("storage").setAttribute('value', '');
    dijit.byId("ram").setAttribute('value', '');
    dijit.byId("cpu").setAttribute('value', '');
    dijit.byId("speed").setAttribute('value', '');
    dijit.byId("screen").setAttribute('value', '');
    dijit.byId("sim").setAttribute('value', '');
    dijit.byId("os").setAttribute('value', '');
    dijit.byId("price").setAttribute('value', '');
    dijit.byId("battery").setAttribute('value', '');
    dijit.byId("backCamera").setAttribute('value', '');
    dijit.byId("frontCamera").setAttribute('value', '');


    page.onSearch = function(){
//	notification('error','xin chao');
        var callback = function(data) {
                notification(data.label,data.customInfo);
            }
        dijit.byId('mobileSegmentationListGird').vtReload("mobileSegmentationSearch!onSearch.do","mobileSegmentationForm",null,callback) ;
    }

    dojo.connect(dijit.byId("mobileSegmentationListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("mobileSegmentationListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "web"){
            var win = window.open(grid.getCell(1).get(idx, item), '_blank');
            win.focus();
        }
    });

</script>