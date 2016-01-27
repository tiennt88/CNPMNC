<%-- 
    Document   : roleUser
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
        var grid = dijit.byId('roleUserListGird');
        return grid.currentRow + index + 1;
    }

</script>

<sd:TitlePane id="roleUser"  key="Thông tin Role User">
    <s:form  id="roleUserForm"  name="roleUserForm"  method="POST">
        <table width="100%">
            <s:hidden name="roleUser.id" id="id"/>
            <s:hidden name="roleUser.roleUserType" id="roleUserType" value="%{#request.roleUserType}"/>
            <tr>
                <s:if test="#request.roleUserType == 'RoleAddUser' ">
                    <s:hidden name="roleUser.roleID" id="roleID"/>
                    <td>
                        <sd:Textarea id="roleName" rows="1" name="roleUser.roleName" key="Role" trim="true" required="true" readonly="true" maxlength="250"/>
                    </td>
                    <td>
                        <sd:SelectBox id="userID" key="User" name="roleUser.userID" data="users" valueField="id" labelField="name" required="true"/>
                    </td>
                </s:if>
                <s:if test="#request.roleUserType == 'UserAddRole'">
                    <s:hidden name="roleUser.userID" id="userID"/>
                    <td>
                        <sd:Textarea id="userName" rows="1"  name="roleUser.userName" key="User" trim="true" required="true" readonly="true" maxlength="250"/>
                    </td>
                    <td>
                        <sd:SelectBox id="roleID" key="Role" name="roleUser.roleID" data="roles" valueField="id" labelField="name" required="true"/>
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

<sd:TitlePane key="Danh sách Role User" id="roleUserList">
    <form id="roleUserListForm" name="roleUserListForm">
        <div id="roleUserListDiv" >
            <sd:DataGrid clientSort="true"   getDataUrl="userRoleSearch!onSearch.do" id="roleUserListGird" style="width: 100%; height: 100%;" rowsPerPage="500">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="false" field="id" key="id" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="roleID" key="roleID" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="false" field="userID" key="userID" styles="display: none;visibility: hidden;" />
                <s:if test="#request.roleUserType == 'RoleAddUser' ">
                    <sd:ColumnDataGrid editable="true" field="roleName" key="Role" width="15%" headerStyles="text-align: center;"  />
                    <sd:ColumnDataGrid editable="true" field="userName" key="User" width="15%" headerStyles="text-align: center;"  />
                </s:if>
                <s:if test="#request.roleUserType == 'UserAddRole'">
                    <sd:ColumnDataGrid editable="true" field="userName" key="User" width="15%" headerStyles="text-align: center;"  />
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

//    var roleUserType = "<s:property value ='#request.roleUserType'/>" ;
//    alert(roleUserType);
//    alert(document.getElementById("roleUserType").value);
//    alert(document.getElementById("roleName").value);

    dojo.connect(dijit.byId("roleUserListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("roleUserListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "_R_D_"){
            if(item){
                
                dijit.byId("edit").setAttribute('disabled', false);
                document.getElementById("id").value = grid.getCell(1).get(idx, item);
                if(document.getElementById("roleUserType").value =="RoleAddUser"){
                    dijit.byId("userID").setAttribute('value', grid.getCell(3).get(idx, item));
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
                url: "userRoleActive.do",
                handleAs: "json",
                content: {
                    id: grid.getCell(1).get(idx, item),
                    active: grid.getCell(6).get(idx, item)
                },
                load: function(data) {
                    notification(data.label,data.customInfo);
                    dijit.byId("roleUserListGird").vtReload("userRoleSearch!onSearch.do","roleUserForm",null,page.showMessage) ;
                }
            };
            dojo.xhrGet(xhrArgs);
        }else if(colField == "delete"){
            if(confirm("Bạn chắc chắn muốn xóa "+grid.getCell(4).get(idx, item) +" -  "+ grid.getCell(5).get(idx, item))){
                var xhrArgs = {
                    url: "userRoleDelete.do",
                    handleAs: "json",
                    content: {
                        id: grid.getCell(1).get(idx, item)
                    },
                    load: function(data) {
                        notification(data.label,data.customInfo);
                        dijit.byId("roleUserListGird").vtReload("userRoleSearch!onSearch.do","roleUserForm",null,page.showMessage) ;
                    }
                };
                dojo.xhrGet(xhrArgs);
            }

        }
        
    });

    validate = function(){

        if(document.getElementById("roleUserType").value =="RoleAddUser"){
            var user = dijit.byId("userID").value;
            if(user == ""){
                notification("ERROR","Chưa điền thông tin User!");
                document.getElementById("userID").focus();
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
            dijit.byId('roleUserListGird').vtReload("userRoleInsert.do","roleUserForm",null,callback) ;
        }
    }

    page.onEdit = function(){
        if(validate() == true){
            var callback = function(data) {
                notification(data.label,data.customInfo);
            }
            dijit.byId('roleUserListGird').vtReload("userRoleUpdate.do","roleUserForm",null,callback) ;
            dijit.byId("edit").setAttribute('disabled', true);
        }
    }

</script>
