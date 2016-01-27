<%--
    Document   : roleMenu
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
        var grid = dijit.byId('roleMenuListGird');
        return grid.currentRow + index + 1;
    }

</script>

<sd:TitlePane id="roleMenu"  key="Thông tin Role Menu">
    <s:form  id="roleMenuForm"  name="roleMenuForm"  method="POST">
        <table width="100%">
            <s:hidden name="roleMenu.id" id="id"/>
            <s:hidden name="roleMenu.roleMenuType" id="roleMenuType" value="%{#request.roleMenuType}"/>
            <tr>
                <s:if test="#request.roleMenuType == 'RoleAddMenu' ">
                    <s:hidden name="roleMenu.roleID" id="roleID"/>
                    <td>
                        <sd:Textarea id="roleName"  rows="1" name="roleMenu.roleName" key="Role" trim="true" required="true" readonly="true" maxlength="250"/>
                    </td>
                    <td>
                        <sd:SelectBox id="menuID" key="Menu" name="roleMenu.menuID" data="menus" valueField="id" labelField="name" required="true"/>
                    </td>
                </s:if>
                <s:if test="#request.roleMenuType == 'MenuAddRole'">
                    <s:hidden name="roleMenu.menuID" id="menuID"/>
                    <td>
                        <sd:Textarea id="menuName" rows="1" name="roleMenu.menuName" key="Menu" trim="true" required="true" readonly="true" maxlength="250"/>
                    </td>
                    <td>
                        <sd:SelectBox id="roleID" key="Role" name="roleMenu.roleID" data="roles" valueField="id" labelField="name" required="true"/>
                    </td>
                </s:if>
                <td>
                    <sd:CheckBox id="isactive" checked="true" name="isactive" key="Hoạt động"/>
                </td>
            </tr>

            <tr>
                <td colspan ="3" align="center">
                    <sd:Button id="insert" key="Thêm Mới"
                               onclick="page.onInsert()">
                        <img alt="edit"  src="share/Img/Icon/add-icon.png" height="17" width="20">
                    </sd:Button>
                    <sd:Button id="edit" key="Sửa" disabled="true"
                               onclick="page.onEdit()">
                        <img alt="edit"  src="share/Img/icons/edit_1.png" height="17" width="20">
                    </sd:Button>
                </td>
            </tr>
        </table>
    </s:form>
</sd:TitlePane>

    <br>
  <div id="notification" class= "notifications bottom-right"></div>

<sd:TitlePane key="Danh sách Role Menu" id="roleMenuList">
    <form id="roleMenuListForm" name="roleMenuListForm">
        <div id="roleMenuListDiv" >
            <sd:DataGrid clientSort="true"   getDataUrl="roleMenuSearch!onSearch.do" id="roleMenuListGird" style="width: 100%; height: 100%;" rowsPerPage="500">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="false" field="id" key="id" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="roleID" key="roleID" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="menuID" key="menuID" styles="display: none;visibility: hidden;" />
                <s:if test="#request.roleMenuType == 'RoleAddMenu' ">
                    <sd:ColumnDataGrid editable="true" field="roleName" key="Role" width="15%" headerStyles="text-align: center;"  />
                    <sd:ColumnDataGrid editable="true" field="menuName" key="Menu" width="15%" headerStyles="text-align: center;"  />
                </s:if>
                <s:if test="#request.roleMenuType == 'MenuAddRole'">
                    <sd:ColumnDataGrid editable="true" field="menuName" key="Menu" width="15%" headerStyles="text-align: center;"  />
                    <sd:ColumnDataGrid editable="true" field="roleName" key="Role" width="15%" headerStyles="text-align: center;"  />
                </s:if>
                <sd:ColumnDataGrid editable="true" field="isactive" key="Hoạt Động" width="4%" headerStyles="text-align: center;" styles="text-align: center; color:blue; font-weight:bold; text-decoration:  underline;"/>
                <sd:ColumnDataGrid editable="true" field=""  key="Chọn" type="radio"  width="5%" headerStyles="text-align: center;" styles="text-align: center;" />
                <sd:ColumnDataGrid editable="true" field="delete" value="Xóa"  key="Remove"  width="4%" headerStyles="text-align: center;" styles="text-align: center; color:blue; font-weight:bold; text-decoration:  underline;" />
            </sd:DataGrid>
        </div>
    </form>
</sd:TitlePane>

<script>

//    var roleMenuType = "<s:property value ='#request.roleMenuType'/>" ;
//    alert(roleMenuType);
//    alert(document.getElementById("roleMenuType").value);

    dojo.connect(dijit.byId("roleMenuListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("roleMenuListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "_R_D_"){
            if(item){
                dijit.byId("edit").setAttribute('disabled', false);
                document.getElementById("id").value = grid.getCell(1).get(idx, item);
                if(document.getElementById("roleMenuType").value =="RoleAddMenu"){
                    dijit.byId("menuID").setAttribute('value', grid.getCell(3).get(idx, item));
                }else{
                    dijit.byId("roleID").setAttribute('value', grid.getCell(2).get(idx, item));
                }
                if(grid.getCell(6).get(idx, item) == "Y"){
                    dijit.byId("isactive").setAttribute('checked', true);
                }else{
                    dijit.byId("isactive").setAttribute('checked', false);
                }
            }
        }else if(colField == "isactive"){
            var xhrArgs = {
                url: "roleMenuActive.do",
                handleAs: "json",
                content: {
                    id: grid.getCell(1).get(idx, item),
                    active: grid.getCell(6).get(idx, item)
                },
                load: function(data) {
                    notification(data.label,data.customInfo);
                    dijit.byId("roleMenuListGird").vtReload("roleMenuSearch!onSearch.do","roleMenuForm",null,page.showMessage) ;
                }
            };
            dojo.xhrGet(xhrArgs);
        }else if(colField == "delete"){
            if(confirm("Bạn chắc chắn muốn xóa "+grid.getCell(4).get(idx, item) +" -  "+ grid.getCell(5).get(idx, item))){
                var xhrArgs = {
                    url: "roleMenuDelete.do",
                    handleAs: "json",
                    content: {
                        id: grid.getCell(1).get(idx, item)
                    },
                    load: function(data) {
                        notification(data.label,data.customInfo);
                        dijit.byId("roleMenuListGird").vtReload("roleMenuSearch!onSearch.do","roleMenuForm",null,page.showMessage) ;
                    }
                };
                dojo.xhrGet(xhrArgs);
            }

        }

    });

    validate = function(){

        if(document.getElementById("roleMenuType").value =="RoleAddMenu"){
            var menu = dijit.byId("menuID").value;
            if(menu == ""){
                notification("ERROR","Chưa điền thông tin Menu!");
                document.getElementById("menuID").focus();
                return false;
            }
        }else{
            var role = dijit.byId("roleID").value;
            if(role == ""){
                notification("ERROR","Chưa điền thông tin Role!");
                document.getElementById("roleID").focus();
                return false;
            }
        }


        return true;
    }

    page.onInsert = function(){
        if(validate() == true){
            var callback = function(data) {
                notification(data.label,data.customInfo);
            }
            dijit.byId('roleMenuListGird').vtReload("roleMenuInsert.do","roleMenuForm",null,callback) ;
        }
    }

    page.onEdit = function(){
        if(validate() == true){
            var callback = function(data) {
                notification(data.label,data.customInfo);
            }
            dijit.byId('roleMenuListGird').vtReload("roleMenuUpdate.do","roleMenuForm",null,callback) ;
            dijit.byId("edit").setAttribute('disabled', true);
        }
    }

</script>
