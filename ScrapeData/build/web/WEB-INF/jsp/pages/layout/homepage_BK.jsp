<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="sd" uri="struts-dojo-tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>

<% request.setAttribute("contextPath", request.getContextPath());%>

<script>
    doGoto = function(action) {
        sd.connector.post(action,"bodyContent",null,null);
    }

</script>

<!--div style=" height: 450px; clear: both;">
    <p style="font-size: 13px; font-weight: bold; padding-left: 15px; color: #0068c0;">
    1. Giới Thiệu FTG Crawl Site Retail
    <br>
    2. Hướng Dẫn Sử Dụng
    <br>
    3. Ghi Chú
    </p>
</div-->

<script>
    page.getIndex = function(inRowIndex){
        return inRowIndex+1;
    }

</script>
<s:i18n name="com/scrape/config/language/Language">

<sd:TitlePane id="laptopRoot"  key="Tra Cứu Thông Tin Laptop">
    <s:form  id="laptopCompareForm"  name="laptopCompareForm"  method="POST">
        <table width="100%">
            <tr>
                <s:hidden name="notebook.id" id="id"/>
                <td>
                    <sd:TextBox id="itemCode" name="notebook.itemCode"  key="Mã Sản Phẩm" trim="true"  maxlength="50" required="true"  />
               </td>
               <td>
                    <sd:CheckBox id="laptopCompare.brand" checked="true" name="laptopCompare.brand" key="Brand"/>
                </td>
                <td>
                    <sd:CheckBox id="laptopCompare.serial" checked="true"  name="laptopCompare.serial" key="Serial"/>
                </td>
                <td>
                    <sd:CheckBox id="laptopCompare.chip" checked="true" name="laptopCompare.chip" key="Chip"/>
                </td>
                <td>
                    <sd:CheckBox id="laptopCompare.ram" checked="true" name="laptopCompare.ram" key="Ram"/>
                </td>
                <td>
                    <sd:CheckBox id="laptopCompare.hdd" checked="true" name="laptopCompare.hdd" key="HDD"/>
                </td>
                <td>
                    <sd:CheckBox id="laptopCompare.screen" checked="true" name="laptopCompare.screen" key="Screen"/>
                </td>
                <td>
                    <sd:CheckBox id="laptopCompare.vga" checked="false" name="laptopCompare.vga" key="VGA"/>
                </td>
                <td>
                    <sd:CheckBox id="laptopCompare.os" checked="false" name="laptopCompare.os" key="os"/>
                </td>
                <td>
                    <sd:CheckBox id="laptopCompare.speed" checked="false" name="laptopCompare.speed" key="Speed"/>
                </td>
                <td>
                    <sd:CheckBox id="laptopCompare.hddType" checked="false" name="laptopCompare.hddType" key="Loại Ổ Cứng"/>
                </td>
            </tr>

            <tr>
                <td colspan="11" align="center">
                   <sd:Button id="Search" key="Search"
                                   onclick="page.onSearch()">
                            <img src="share/Img/icons/search.png" height="17" width="20">
                    </sd:Button>
               </td>
            </tr>
        </table>
<!--                <h1>Thông Tin Chi Tiết :</h1>-->
                <br>
        <table width="100%">
            <tr>
                <td>

                </td>
                <td>
                    <sd:TextBox id="itemName"   name="notebook.itemName" key="Tên Sản Phẩm" trim="true" readonly="true" maxlength="50"/>
                </td>
                <td>
                    <sd:TextBox id="brand"   name="notebook.brand" key="Hãng" trim="true" readonly="true" maxlength="50"/>
                </td>



            </tr>
            <tr>
                <td>
                    <sd:TextBox id="serial"   name="notebook.serial" key="Serial" trim="true" readonly="true" maxlength="50"/>
                </td>
                <td>
                    <sd:TextBox id="chip"   name="notebook.chip" key="Chip" trim="true" maxlength="50" readonly="true"/>
                </td>

                <td>
                    <sd:TextBox id="feature" name="notebook.feature" key="Feature" trim="true" readonly="true" maxlength="50"/>
                </td>
            </tr>

            <tr>
                <td>
                    <sd:TextBox id="ram"   name="notebook.ram" key="Ram" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <sd:TextBox id="hdd"   name="notebook.hdd" key="Ổ Cứng" trim="true" maxlength="50" readonly="true" />

                </td>
                <td>
                    <sd:TextBox id="screen"   name="notebook.screen" key="Màn hình" trim="true" maxlength="50" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="vga"   name="notebook.vga" key="Đồ Họa" trim="true" maxlength="300" readonly="true" />
                </td>
                <td>
                    <sd:TextBox id="os"   name="notebook.os" key="Hệ Điều Hành" trim="true" maxlength="50" readonly="true" />
                </td>
                <td>
                    <%--sd:TextBox id="ultra"   name="notebook.ultra" key="ultra" trim="true" maxlength="50" readonly="true" /--%>
                    <sd:TextBox id="hddType"   name="notebook.hddType" key="Loại Ổ Cứng" trim="true" maxlength="50" readonly="true" />
                </td>

            </tr>
            <tr>
                <td colspan="2">
                    <sd:Textarea id="description" key="Mô Tả" name="notebook.description" trim="true" rows="2"  cssStyle="width:105%" labelWidth="13.5%" maxlength="500" readonly="true"/>
                </td>
                <td>
                    <sd:TextBox id="speed"   name="notebook.speed" key="Speed" trim="true" maxlength="50" readonly="true"/>
                </td>
            </tr>
            <%--tr>
                <td colspan="3" align="center"><sd:Button id="Remove" key="Remove"
                                   onclick="page.onRemove()">
                            <img src="share/Img/icons/a4.png" height="17" width="20">
                    </sd:Button></td>
            </tr--%>
        </table>

    </s:form>
</sd:TitlePane>
    <br>
<sd:TitlePane key="Danh Sách Các Web Bán" id="laptopCompareList">

    <form id="laptopCompareListForm" name="laptopCompareListForm">
        <div id="laptopCompareListDiv" >
            <sd:DataGrid clientSort="true" getDataUrl="laptopCompareSearch!onSearch.do" id="laptopCompareListGird" style="width: 100%; height: 100%;" rowsPerPage="10">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="false" field="id" key="id" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="link" key="link" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="true" field="web" key="Web" width="8%" headerStyles="text-align: center; " styles="text-align: center; color:blue; font-weight:bold; text-decoration:  underline;" />
                <sd:ColumnDataGrid editable="true" field="brand" key="Hãng" width="6%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="itemName" key="Tên Sản Phẩm" width="25%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="price" key="Giá" width="8%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="serial" key="Model" width="8%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="chip" key="Chip" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="speed" key="Speed" width="3%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="feature" key="Feature" width="3%" styles="display: none;visibility: hidden;"  headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="ram" key="Ram" width="3%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="hdd" key="HDD" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="hddType" key="Loại Ổ Cứng" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="screen" key="Screen" width="3%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="vga" key="VGA" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="os" key="OS" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="information" key="Thông Tin" width="15%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="promotion" key="Khuyến Mại" width="15%" headerStyles="text-align: center;"  />
                <%--sd:ColumnDataGrid editable="true"  key="Remove" field="checkbox" type="checkbox" headerStyles="text-align: center;" styles="text-align: center;" headerCheckbox="true" width="20px"/--%>
            </sd:DataGrid>
        </div>
    </form>
</sd:TitlePane>
</s:i18n>

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
                document.getElementById("itemName").value = data.notebook.itemName;
                document.getElementById("brand").value = data.notebook.brand;
                document.getElementById("chip").value = data.notebook.chip;
                document.getElementById("feature").value = data.notebook.feature;
                document.getElementById("hdd").value = data.notebook.hdd;
                document.getElementById("hddType").value = data.notebook.hddType;
                document.getElementById("os").value = data.notebook.os;
                document.getElementById("speed").value = data.notebook.speed;
                document.getElementById("ram").value = data.notebook.ram;
                document.getElementById("screen").value = data.notebook.screen;
                document.getElementById("serial").value = data.notebook.serial;
//                document.getElementById("ultra").value = data.notebook.ultra;
                document.getElementById("vga").value = data.notebook.vga;
                document.getElementById("description").value = data.notebook.description;
            },
            error: function(error) {
                notification("ERROR",error.result);
            }
        };
        dojo.xhrGet(xhrArgs);
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


//    page.onRemove = function(){
//        var grid = dijit.byId("laptopCompareListGird");
//        var index = 0;
//        var json = {"mappings": [] };
//        var itemCode = document.getElementById("itemCode").value;
//        dojo.forEach(grid.vtGetCheckedItems(), function (item,i) {
//             var id = grid.store.getValue(item,"id");
//            json.mappings.push({"id":id,"itemCode":itemCode});
//            index++;
//        });
//        if(index == 0){
//            notification("ERROR","Bạn phải chọn ít nhất 1 bản ghi để remove!");
//            return;
//        }
//        if(confirm("Bạn thực sự muốn remove?")){
//            var xhrArgs = {
//                url: "laptopCompareRemove.do",
//                headers: { "Content-Type": "application/json; charset=utf-8"},
//                postData: dojo.toJson(json),
//                handleAs: "json",
//                load: function(data) {
//                    notification("SUCCESS",data.result);
//                },
//                error: function(error) {
//                    notification("ERROR",error.result);
//                }
//            };
//            dojo.xhrPost(xhrArgs);
//            reloadGrid();
//        }
//    }

    reloadGrid =function(){
        dijit.byId("laptopCompareListGird").vtReload("laptopCompareSearch!onSearch.do","laptopCompareForm",null,page.showMessage) ;
    }


</script>