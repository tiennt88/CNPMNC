<%-- 
    Document   : mobileData
    Created on : Mar 22, 2015, 8:36:57 PM
    Author     : KeyLove
--%>

<%@page import="java.util.ResourceBundle"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="sd" uri="struts-dojo-tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<script>

    page.getIndex = function (index) {
        var grid = dijit.byId('mobileDataListGird');
        return grid.currentRow + index + 1;
    }

</script>

<sd:TitlePane id="mobileData"  key="Thông tin">
    <s:form  id="mobileDataForm"  name="mobileDataForm"  method="POST">
        <table width="100%">
            <s:hidden name="mobile.id" id="id"/>
            <s:hidden name="mobile.id1" id="id1"/>
            <tr>
                <td>
                    <sd:SelectBox id="web" key="Web" name="mobile.web" data="webs" valueField="value" labelField="name" required="true"/>
                </td>

                <td>
                    <sd:SelectBox id="brand" key="Brand" name="mobile.brand" data="brands" valueField="value" labelField="name" required="true" />
                </td>
                <td>
                    <sd:TextBox id="name"   name="mobile.name" key="Tên Sản Phẩm" trim="true" required="true" maxlength="250"/>
                </td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="model"   name="mobile.model" key="Model" trim="true" required="true" maxlength="50"/>
                </td>
                <td>
                    <sd:SelectBox id="storage" key="Bộ nhớ trong" name="mobile.storage" data="storages" valueField="value" labelField="name" />
                </td>
                <td >
                    <sd:SelectBox id="ram" key="Ram" name="mobile.ram" data="rams" valueField="value" labelField="name" />
                </td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="color" name="mobile.color" key="Mầu" trim="true"  maxlength="50"/>
                </td>
                <td>
                    <sd:CheckBox id="approve" checked="false" name="mobile.approve" key="Chưa Duyệt"/>
                </td>
                <td>
                    <sd:CheckBox id="today" checked="false" name="mobile.today" key="today"/>
                </td>
            </tr>

            <tr>
                <td colspan ="3" align="center">
                    <sd:Button id="Search" key="Search"
                               onclick="page.onSearch()">
                        <img alt="search"  src="share/Img/icons/search.png" height="17" width="20">
                    </sd:Button>
                    <sd:Button id="edit" key="Sửa" disabled="true"
                               onclick="page.onEdit()">
                        <img alt="edit"  src="share/Img/icons/edit_1.png" height="17" width="20">
                    </sd:Button>
                    <sd:Button id="delete" key="Xóa" disabled="true"
                               onclick="page.onDelete()">
                        <img alt="delete"  src="share/Img/icons/a4.png" height="17" width="20">
                    </sd:Button>
                    <sd:Button id="reset" key="Reset"
                               onclick="page.reset()">
                        <img alt="reset"  src="share/Img/icons/reset.png" height="17" width="20">
                    </sd:Button>
                    <sd:Button id="exportExcel" key="Export Excel"
                               onclick="page.exportExcel()">
                        <img alt="export"  src="share/Img/Icon/export.png" height="17" width="20">
                    </sd:Button>
                    <sd:Button id="import" key="Import File"
                               onclick="page.importFile()">
                        <img alt="import"  src="share/Img/icons/import-export.png" height="17" width="20">
                    </sd:Button>
                    <sd:Button id="template" key="Template"
                           onclick="page.template()">
                        <img alt="template"  src="share/Img/icons/excel.png" height="17" width="20">
                    </sd:Button>
                   
                    <sd:Button id="mobileAnalysis" key="Analysis"
                               onclick="page.mobileAnalysis()">
                        <img alt="Analysis"  src="share/Img/Icon/three_cirling.png" height="17" width="20">
                    </sd:Button>
                </td>
            </tr>
        </table>
    </s:form>
    <div id="result" ></div>
</sd:TitlePane>
<br>
<div id="notification" class= "notifications bottom-right"></div>

<sd:TitlePane key="Dữ Liệu" id="mobileDataList">
    <form id="mobileDataListForm" name="mobileDataListForm">
        <div id="mobileDataListDiv" >
            <sd:DataGrid clientSort="true"   getDataUrl="mobileDataSearch!onSearch.do" id="mobileDataListGird" style="width: 100%; height: 100%;" rowsPerPage="10">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="false" field="id" key="id" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="id1" key="id1" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="link" key="Link" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="true" field="web" key="Web" width="4%" headerStyles="text-align: center;"  styles="text-align: left; color:blue; font-weight:bold; text-decoration:  underline;"/>
                <sd:ColumnDataGrid editable="true" field="type" key="Loại" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="brand" key="Hãng" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="price" key="Giá" width="7%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="name" key="Tên SP" width="10%" headerStyles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="model" key="Model" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="storage" key="Bộ nhớ trong" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="ram" key="Ram" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="screen" key="Màn Hình" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="cpu" key="Cpu" width="5%" headerStyles="text-align: left;"  />
                <sd:ColumnDataGrid editable="true" field="backCamera" key="Camera chính" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="frontCamera" key="Camera Phụ" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="os" key="Hệ điều hành" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="battery" key="Pin" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="sim" key="Sim" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="color" key="Mầu" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="promotion" key="Khuyến Mại" width="10%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true"  field="lastUpdate" key="Ngày Cập Nhật" format="dd/MM/yyyy" width="6%" type="date" />
                <sd:ColumnDataGrid editable="true" field="approve" key="Duyệt" width="2%" headerStyles="text-align: center;" styles="text-align: center; color:blue; font-weight:bold; text-decoration:  underline;" />
                <sd:ColumnDataGrid editable="true" field=""  key="Chọn" type="radio"  width="3%" headerStyles="text-align: center;" styles="text-align: center;" />
            </sd:DataGrid>
        </div>
    </form>
</sd:TitlePane>

<script>
    
    dijit.byId("web").setAttribute('value', '');
    dijit.byId("brand").setAttribute('value', '');
    dijit.byId("ram").setAttribute('value', '');
    dijit.byId("storage").setAttribute('value', '');
    
    
    page.onSearch = function(){
        //	notification('error','xin chao');
        var callback = function(data) {
            notification(data.label,data.customInfo);
        }
        dijit.byId('mobileDataListGird').vtReload("mobileDataSearch!onSearch.do","mobileDataForm",null,callback) ;
    }

    page.reset = function(){
        
        document.getElementById("id").value = "";
        document.getElementById("id1").value = "";
        dijit.byId("web").setAttribute('value', '');
        dijit.byId("brand").setAttribute('value', '');
        document.getElementById("name").value = "";
        document.getElementById("model").value = "";
        dijit.byId("storage").setAttribute('value', '');
        dijit.byId("ram").setAttribute('value', '');
        document.getElementById("color").value  = "";
        dijit.byId("edit").setAttribute('disabled', true);
        dijit.byId("delete").setAttribute('disabled', true);
        dijit.byId("approve").setAttribute('checked', false);
        dijit.byId("today").setAttribute('checked', false);
    }

    dojo.connect(dijit.byId("mobileDataListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("mobileDataListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "_R_D_"){
            if(item){
                page.reset();
                document.getElementById("id").value = grid.getCell(1).get(idx, item);
                document.getElementById("id1").value = grid.getCell(2).get(idx, item);
                dijit.byId("web").setAttribute('value', grid.getCell(4).get(idx, item));
                dijit.byId("brand").setAttribute('value', grid.getCell(6).get(idx, item));
                document.getElementById("name").value = grid.getCell(8).get(idx, item);
                document.getElementById("model").value = grid.getCell(9).get(idx, item);
                dijit.byId("storage").setAttribute('value', grid.getCell(10).get(idx, item));
                dijit.byId("ram").setAttribute('value', grid.getCell(11).get(idx, item));
                document.getElementById("color").value = grid.getCell(19).get(idx, item);
                var approve = grid.getCell(22).get(idx, item);
                if(approve == "Y"){
                    dijit.byId("approve").setAttribute('checked', false);
                }else{
                    dijit.byId("approve").setAttribute('checked', true);
                }
                dijit.byId("edit").setAttribute('disabled', false);
                dijit.byId("delete").setAttribute('disabled', false);
            }
        }else if(colField == "web"){
            var win = window.open(grid.getCell(3).get(idx, item), '_blank');
            win.focus();
        }else if(colField == "approve"){
            var xhrArgs = {
                url: "mobileApprove.do",
                handleAs: "json",
                content: {
                    id: grid.getCell(1).get(idx, item)
                },
                load: function(data) {
                    notification(data.label,data.customInfo);
                    dijit.byId("mobileDataListGird").vtReload("mobileDataSearch!onSearch.do","mobileDataForm",null,page.showMessage) ;
                }
            };
            dojo.xhrGet(xhrArgs);
        }
    });

    validate = function(){

        var web = dijit.byId("web").value;
        var brand = dijit.byId("brand").value;
        var name = document.getElementById("name").value;
        var model = document.getElementById("model").value;
//        var ram = dijit.byId("ram").value;
//        var storage = dijit.byId("storage").value;

        if(web == ""){
            notification("ERROR","Chưa điền thông tin web!");
            document.getElementById("web").focus();
            return false;
        }
        if(brand == ""){
            notification("ERROR","Chưa điền thông tin hãng!");
            document.getElementById("brand").focus();
            return false;
        }
        if(name == ""){
            notification("ERROR","Chưa điền thông tin tên sản phẩm!");
            document.getElementById("name").focus();
            return false;
        }
        if(model == ""){
            notification("ERROR","Chưa điền thông tin model!");
            document.getElementById("model").focus();
            return false;
        }
//        if(storage == ""){
//            notification("ERROR","Chưa điền thông tin bộ nhớ trong!");
//            document.getElementById("storage").focus();
//            return false;
//        }
//        if(ram == ""){
//            notification("ERROR","Chưa điền thông tin Ram!");
//            document.getElementById("ram").focus();
//            return false;
//        }

        return true;
    }

    page.onEdit = function(){
        if(validate() == true){
            var callback = function(data) {
                notification(data.label,data.customInfo);
            }
            dijit.byId('mobileDataListGird').vtReload("mobileDataUpdate!onUpdate.do","mobileDataForm",null,callback) ;
            dijit.byId("edit").setAttribute('disabled', true);
        }
    }

    page.onDelete = function(){
        if(document.getElementById("id").value == "" || document.getElementById("id1").value == ""){
            notification("ERROR","Xin vui lòng chọn 1 bản ghi để xóa!");
            return;
        }
        if(confirm("Bạn thực sự muốn xóa?")){
            var callback = function(data) {
                notification(data.label,data.customInfo);
            }
            dijit.byId('mobileDataListGird').vtReload("mobileDataDelete!onDelete.do","mobileDataForm",null,callback) ;
            dijit.byId("delete").setAttribute('disabled', true);
            page.reset();
        }

    }

    page.mobileAnalysis = function(){

        var xhrArgs = {
            url: "mobileAnalysis.do",
            form: document.getElementById("mobileDataForm"),
            load: function(data) {
//                alert(data);//json tra ve tren JS o dang string. Can phai parse thanh doi tuong json tren JS
                notification("SUCCESS",JSON.parse(data).customInfo);
            },
            error: function(data) {
                notification("ERROR",data.customInfo);
            }
        };
        dojo.xhrPost(xhrArgs);
    }

    page.exportExcel = function(){
        sd.connector.post("mobileDataExport!exportExcel.do","result","mobileDataForm");
    }

    page.importFile = function(){
        openPopup("upload.do?type=Mobile",500,170);
    }

    page.template = function(){
        <%
            ResourceBundle rb = ResourceBundle.getBundle("config");
            String template = rb.getString("template_file_path")+"MobileDataReport.xlsx";
            request.setAttribute("template", template);
            //request.setAttribute("template", request.getContextPath() + "/share/template/"+"LaptopTemplate.xlsx");
        %>
        window.location = "download.do?filePath=${template}";
    }
    
</script>
