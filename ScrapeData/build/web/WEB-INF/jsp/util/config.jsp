<%-- 
    Document   : config
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
        var grid = dijit.byId('configListGird');
        return grid.currentRow + index + 1;
    }

</script>

<sd:TitlePane id="config"  key="Cấu hình combobox">
    <s:form  id="configForm"  name="configForm"  method="POST">
        <table width="100%">
            <s:hidden name="config.id" id="id"/>
            <tr>
                <td>

                    <sd:TextBox id="type"   name="config.type" key="Loại" trim="true" required="true" maxlength="250"/>
                </td>
                <td>
                    <sd:TextBox id="value"   name="config.value" key="Value" trim="true" required="true" maxlength="250"/>
                </td>
                <td>
                    <sd:TextBox id="name"   name="config.name" key="Tên" trim="true" required="true" maxlength="250"/>
                </td>
            </tr>
            <tr>
                <td>
                    <sd:NumberSpinner id="priority" key="Độ ưu tiên" name="config.priority" required="true" />
                </td>
                <td>
                    <sd:TextBox id="usefor"   name="config.usefor" key="Dùng Cho" trim="true" required="true" maxlength="250"/>
                </td>
                <td>
                    <sd:CheckBox id="isactive" checked="true" name="isactive" key="Hoạt động"/>
                </td>
                
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

<sd:TitlePane key="Danh sách tham số Combobox" id="configList">
    <form id="configListForm" name="configListForm">
        <div id="configListDiv" >
            <sd:DataGrid clientSort="true"   getDataUrl="configSearch!onSearch.do" id="configListGird" style="width: 100%; height: 100%;" rowsPerPage="10">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="false" field="id" key="id" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="true" field="type" key="Loại" width="5%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="value" key="Giá trị" width="15%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="name" key="Tên" width="15%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="priority" key="Độ ưu tiên" width="4%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="usefor" key="Dùng cho" width="4%" headerStyles="text-align: center;"  />
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
        dijit.byId('configListGird').vtReload("configSearch!onSearch.do","configForm",null,callback) ;
    }

    page.reset = function(){
        document.getElementById("id").value = "";
        document.getElementById("type").value = "";
        document.getElementById("value").value = "";
        document.getElementById("name").value = "";
        document.getElementById("priority").value = "";
        document.getElementById("usefor").value = "";
        dijit.byId("isactive").setAttribute('checked', true);
    }

    dojo.connect(dijit.byId("configListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("configListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "_R_D_"){
            if(item){
                page.reset();
                dijit.byId("edit").setAttribute('disabled', false);
                dijit.byId("delete").setAttribute('disabled', false);
                document.getElementById("id").value = grid.getCell(1).get(idx, item);
                document.getElementById("type").value = grid.getCell(2).get(idx, item);
                document.getElementById("value").value = grid.getCell(3).get(idx, item);
                document.getElementById("name").value = grid.getCell(4).get(idx, item);
                document.getElementById("priority").value = grid.getCell(5).get(idx, item);
                document.getElementById("usefor").value = grid.getCell(6).get(idx, item);
                if(grid.getCell(7).get(idx, item) == "Y"){
                    dijit.byId("isactive").setAttribute('checked', true);
                }else{
                    dijit.byId("isactive").setAttribute('checked', false);
                }
            }
        }else if(colField == "isactive"){
            var xhrArgs = {
                url: "configActive.do",
                handleAs: "json",
                content: {
                    id: grid.getCell(1).get(idx, item),
                    active: grid.getCell(7).get(idx, item)
                },
                load: function(data) {
                    notification(data.label,data.customInfo);
                    dijit.byId("configListGird").vtReload("configSearch!onSearch.do","configForm",null,page.showMessage) ;
                }
            };
            dojo.xhrGet(xhrArgs);
        }else if(colField == "delete"){
            if(confirm("Bạn chắc chắn muốn xóa tham số : "+grid.getCell(4).get(idx, item))){
                var xhrArgs = {
                    url: "configDelete!onDelete.do",
                    handleAs: "json",
                    content: {
                        id: grid.getCell(1).get(idx, item)
                    },
                    load: function(data) {
                        notification(data.label,data.customInfo);
                        dijit.byId("configListGird").vtReload("configSearch!onSearch.do","configForm",null,page.showMessage) ;
                    }
                };
                dojo.xhrGet(xhrArgs);
            }
        }
        
    });

    validate = function(){
        
        var type = document.getElementById("type").value;
//        var value = document.getElementById("value").value;
        var name = document.getElementById("name").value;
//        var priority = document.getElementById("priority").value;

        
        if(type == ""){
            notification("ERROR","Chưa điền thông tin Loại!");
            document.getElementById("type").focus();
            return false;
        }
//        if(value == ""){
//            notification("ERROR","Chưa điền thông tin giá trị!");
//            document.getElementById("value").focus();
//            return false;
//        }
        if(name == ""){
            notification("ERROR","Chưa điền thông tin tên!");
            document.getElementById("name").focus();
            return false;
        }
        
        return true;
    }

    page.onInsert = function(){
        if(validate() == true){
            var callback = function(data) {
                notification(data.label,data.customInfo);
            }
            dijit.byId('configListGird').vtReload("configInsert!onInsert.do","configForm",null,callback) ;
        }
    }

    page.onEdit = function(){
        if(validate() == true){
            var callback = function(data) {
                notification(data.label,data.customInfo);
            }
            dijit.byId('configListGird').vtReload("configUpdate!onUpdate.do","configForm",null,callback) ;
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
            dijit.byId('configListGird').vtReload("configDelete!onDelete.do","configForm",null,callback) ;
            dijit.byId("delete").setAttribute('disabled', true);
            page.reset();
        }
    }

</script>
