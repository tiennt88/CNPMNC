<%-- 
    Document   : webData
    Created on : Aug 28, 2015, 1:49:54 PM
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

    page.getIndex = function (index) {
        var grid = dijit.byId('webDataListGird');
        return grid.currentRow + index + 1;
    }

</script>

<sd:TitlePane id="webData"  key="Cấu hình link các Web chạy lấy dữ liệu">
    <s:form  id="webDataForm"  name="webDataForm"  method="POST">
        <table width="100%">
            <s:hidden name="webData.id" id="id"/>
            <tr>
                <td>
                    <sd:TextBox id="web"   name="webData.web" key="Web" trim="true" required="true" maxlength="250"/>
                </td>
                <td>
                    <sd:TextBox id="type"   name="webData.type" key="Loại" trim="true" required="true" maxlength="250"/>
                </td>
                <td>
                    <sd:TextBox id="brand"   name="webData.brand" key="Hãng" trim="true" required="true" maxlength="250"/>
                </td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="link"   name="webData.link" key="Link" trim="true" required="true" maxlength="250"/>
                </td>
                <td>
                    <sd:CheckBox id="isactive" checked="true" name="isactive" key="Hoạt động"/>
                </td>
                <td></td>
            </tr>

            <tr>
                <td colspan ="3" align="center">
                    <sd:Button id="Search" key="Search"
                                   onclick="page.onSearch()">
                        <img alt="Search"  src="share/Img/icons/search.png" height="17" width="20">
                    </sd:Button>
                    <sd:Button id="insert" key="Thêm Mới" 
                               onclick="page.onInsert()">
                        <img alt="edit"  src="share/Img/Icon/add-icon.png" height="17" width="20">
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
                </td>
            </tr>
        </table>
    </s:form>
</sd:TitlePane>

    <br>
  <div id="notification" class= "notifications bottom-right"></div>

<sd:TitlePane key="Danh sách link các Web lấy dữ liệu" id="webDataList">
    <form id="webDataListForm" name="webDataListForm">
        <div id="webDataListDiv" >
            <sd:DataGrid clientSort="true"   getDataUrl="webDataSearch!onSearch.do" id="webDataListGird" style="width: 100%; height: 100%;" rowsPerPage="10">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="false" field="id" key="id" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="true" field="web" key="Web" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="type" key="Loại" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="brand" key="Hãng" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="link" key="Link" width="25%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="lastUpdate" key="Ngày Cập Nhật" format="dd/MM/yyyy" width="6%" type="date" />
                <sd:ColumnDataGrid editable="true" field="isactive" key="Hoạt Động" width="4%" headerStyles="text-align: center;" styles="text-align: center; color:blue; font-weight:bold; text-decoration:  underline;"/>
                <sd:ColumnDataGrid editable="true" field=""  key="Chọn" type="radio"  width="3%" headerStyles="text-align: center;" styles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="delete" value="Xóa"  key="Delete"  width="4%" headerStyles="text-align: center;" styles="text-align: center; color:blue; font-weight:bold; text-decoration:  underline;" />
            </sd:DataGrid>
        </div>
    </form>
</sd:TitlePane>

<script>

    page.onSearch = function(){
//	notification('error','xin chao');
        var callback = function(data) {
                notification(data.label,data.customInfo);
            }
        dijit.byId('webDataListGird').vtReload("webDataSearch!onSearch.do","webDataForm",null,callback) ;
    }

    page.reset = function(){
        document.getElementById("id").value = "";
        document.getElementById("web").value = "";
        document.getElementById("type").value = "";
        document.getElementById("brand").value = "";
        document.getElementById("link").value = "";
        dijit.byId("isactive").setAttribute('checked', true);
    }

    dojo.connect(dijit.byId("webDataListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("webDataListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "_R_D_"){
            if(item){
                page.reset();
                dijit.byId("edit").setAttribute('disabled', false);
                dijit.byId("delete").setAttribute('disabled', false);
                document.getElementById("id").value = grid.getCell(1).get(idx, item);
                document.getElementById("web").value = grid.getCell(2).get(idx, item);
                document.getElementById("type").value = grid.getCell(3).get(idx, item);
                document.getElementById("brand").value = grid.getCell(4).get(idx, item);
                document.getElementById("link").value = grid.getCell(5).get(idx, item);
                if(grid.getCell(7).get(idx, item) == "Y"){
                    dijit.byId("isactive").setAttribute('checked', true);
                }else{
                    dijit.byId("isactive").setAttribute('checked', false);
                }
            }
        }else if(colField == "isactive"){
            var xhrArgs = {
                url: "webDataActive.do",
                handleAs: "json",
                content: {
                    id: grid.getCell(1).get(idx, item),
                    active: grid.getCell(7).get(idx, item)
                },
                load: function(data) {
                    notification(data.label,data.customInfo);
                    dijit.byId("webDataListGird").vtReload("webDataSearch!onSearch.do","webDataForm",null,page.showMessage) ;
                }
            };
            dojo.xhrGet(xhrArgs);
        }else if(colField == "delete"){
            if(confirm("Bạn chắc chắn muốn xóa Link : "+grid.getCell(5).get(idx, item))){
                var xhrArgs = {
                    url: "webDataDelete!onDelete.do",
                    handleAs: "json",
                    content: {
                        id: grid.getCell(1).get(idx, item)
                    },
                    load: function(data) {
                        notification(data.label,data.customInfo);
                        dijit.byId("webDataListGird").vtReload("webDataSearch!onSearch.do","webDataForm",null,page.showMessage) ;
                    }
                };
                dojo.xhrGet(xhrArgs);
            }
        }
        
    });

    validate = function(){
        var web = document.getElementById("web").value;
        var type = document.getElementById("type").value;
        var brand = document.getElementById("brand").value;
        var link = document.getElementById("link").value;

        if(web == ""){
            notification("ERROR","Chưa điền thông tin Web!");
            document.getElementById("web").focus();
            return false;
        }
        if(type == ""){
            notification("ERROR","Chưa điền thông tin Loại!");
            document.getElementById("type").focus();
            return false;
        }
        if(brand == ""){
            notification("ERROR","Chưa điền thông tin hãng!");
            document.getElementById("brand").focus();
            return false;
        }
        if(link == ""){
            notification("ERROR","Chưa điền thông tin Link!");
            document.getElementById("link").focus();
            return false;
        }
        return true;
    }

    page.onInsert = function(){
        if(validate() == true){
            var callback = function(data) {
                notification(data.label,data.customInfo);
            }
            dijit.byId('webDataListGird').vtReload("webDataInsert!onInsert.do","webDataForm",null,callback) ;
        }
    }

    page.onEdit = function(){
        if(validate() == true){
            var callback = function(data) {
                notification(data.label,data.customInfo);
            }
            dijit.byId('webDataListGird').vtReload("webDataUpdate!onUpdate.do","webDataForm",null,callback) ;
            dijit.byId("edit").setAttribute('disabled', true);
        }
    }

    page.onDelete = function(){
        if(document.getElementById("id").value == ""){
            notification("ERROR","Xin vui lòng chọn 1 bản ghi!");
            return;
        }
        if(confirm("Bạn thực sự muốn xóa?")){
            var callback = function(data) {
                notification(data.label,data.customInfo);
            }
            dijit.byId('webDataListGird').vtReload("webDataDelete!onDelete.do","webDataForm",null,callback) ;
            dijit.byId("delete").setAttribute('disabled', true);
            page.reset();
        }
    }

</script>
