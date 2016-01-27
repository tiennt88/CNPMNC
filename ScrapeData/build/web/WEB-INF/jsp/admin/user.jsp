<%-- 
    Document   : user
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
        var grid = dijit.byId('userListGird');
        return grid.currentRow + index + 1;
    }

</script>
    
<sd:TitlePane id="userPane"  key="Thông tin User">
    
    <s:form  id="userForm"  name="userForm"  method="POST">
        <table width="100%">
            <s:hidden name="user.id" id="id"/>
            <tr>
                <td>
                    <sd:TextBox id="fullname"   name="user.fullname" key="Tên đầy đủ" trim="true" required="true" maxlength="250"/>
                </td>
                <td>
                    <sd:TextBox id="username"   name="user.username" key="Tài khoản" trim="true" required="true" maxlength="250"/>
                </td>
                <td>
                    <sd:TextBox id="password" type="password" name="user.password" key="Mật khẩu" trim="true" required="true" maxlength="250"/>
                </td>
            </tr>
            <tr>
                <td>
                    <sd:TextBox id="passwordHint"  name="user.passwordHint" key="Gợi ý mật khẩu" trim="true" required="true" maxlength="250"/>
                </td>
                <td>
                    <sd:TextBox id="email"   name="user.email" key="Email" trim="true" required="true" maxlength="250"/>
                </td>
                <td>
                    <sd:CheckBox id="systemPasswordEnable" checked="true" name="systemPasswordEnable" key="Sử dụng Pass hệ thống"/>
                    <sd:CheckBox id="isactive" checked="true" name="isactive" key="Hoạt động" cssStyle="margin-left:15%"/>
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

<sd:TitlePane key="Danh sách User" id="userList">
    <form id="userListForm" name="userListForm">
        <div id="userListDiv" >
            <sd:DataGrid clientSort="true"   getDataUrl="userSearch!onSearch.do" id="userListGird" style="width: 100%; height: 100%;" rowsPerPage="10">
                <sd:ColumnDataGrid editable="true" key="STT" get="page.getIndex" width="2%" styles="text-align: center;" headerStyles="text-align: center;"/>
                <sd:ColumnDataGrid editable="false" field="id" key="id" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="true" field="fullname" key="Tên đầy đủ" width="15%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="username" key="Tài khoản" width="15%" headerStyles="text-align: center;"  styles="color:blue; font-weight:bold; text-decoration:  underline;"/>
                <sd:ColumnDataGrid editable="false" field="password" key="password" styles="display: none;visibility: hidden;" />
                <sd:ColumnDataGrid editable="true" field="passwordHint" key="Gợi ý pass" width="15%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="email" key="Email" width="15%" headerStyles="text-align: center;"  />
                <sd:ColumnDataGrid editable="true" field="systemPasswordEnable" key="System Pass" width="8%" headerStyles="text-align: center;"  styles="text-align: center; color:blue; font-weight:bold; text-decoration:  underline;"/>
                <sd:ColumnDataGrid editable="true" field="isactive" key="Hoạt Động" width="6%" headerStyles="text-align: center;" styles="text-align: center; color:blue; font-weight:bold; text-decoration:  underline;"/>
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
        dijit.byId('userListGird').vtReload("userSearch!onSearch.do","userForm",null,callback) ;
    }

    page.reset = function(){
        document.getElementById("id").value = "";
        document.getElementById("fullname").value = "";
        document.getElementById("username").value = "";
        document.getElementById("password").value = "";
        document.getElementById("passwordHint").value = "";
        document.getElementById("email").value = "";
        dijit.byId("isactive").setAttribute('checked', true);
        dijit.byId("systemPasswordEnable").setAttribute('checked', true);
    }

    dojo.connect(dijit.byId("userListGird"), "onCellClick", function (e) {
        var grid = dijit.byId("userListGird");
        var colField = e.cell.field; // field name
        var idx = e.rowIndex; // row index
        var item = grid.getItem(idx);
        if(colField == "_R_D_"){
            if(item){
                page.reset();
                dijit.byId("edit").setAttribute('disabled', false);
                dijit.byId("delete").setAttribute('disabled', false);
                document.getElementById("id").value = grid.getCell(1).get(idx, item);
                document.getElementById("fullname").value = grid.getCell(2).get(idx, item);
                document.getElementById("username").value = grid.getCell(3).get(idx, item);
                document.getElementById("password").value = grid.getCell(4).get(idx, item);
                document.getElementById("passwordHint").value = grid.getCell(5).get(idx, item);
                document.getElementById("email").value = grid.getCell(6).get(idx, item);
                if(grid.getCell(7).get(idx, item) == "Y"){
                    dijit.byId("systemPasswordEnable").setAttribute('checked', true);
                }else{
                    dijit.byId("systemPasswordEnable").setAttribute('checked', false);
                }
                if(grid.getCell(8).get(idx, item) == "Y"){
                    dijit.byId("isactive").setAttribute('checked', true);
                }else{
                    dijit.byId("isactive").setAttribute('checked', false);
                }
            }
        }else if(colField == "systemPasswordEnable"){
            var xhrArgs = {
                url: "userSystemPassword.do",
                handleAs: "json",
                content: {
                    id: grid.getCell(1).get(idx, item),
                    active: grid.getCell(7).get(idx, item)
                },
                load: function(data) {
                    notification(data.label,data.customInfo);
                    dijit.byId("userListGird").vtReload("userSearch!onSearch.do","userForm",null,page.showMessage) ;
                }
            };
            dojo.xhrGet(xhrArgs);
        }else if(colField == "isactive"){
            var xhrArgs = {
                url: "userActive.do",
                handleAs: "json",
                content: {
                    id: grid.getCell(1).get(idx, item),
                    active: grid.getCell(8).get(idx, item)
                },
                load: function(data) {
                    notification(data.label,data.customInfo);
                    dijit.byId("userListGird").vtReload("userSearch!onSearch.do","userForm",null,page.showMessage) ;
                }
            };
            dojo.xhrGet(xhrArgs);
        }else if(colField == "delete"){
            if(confirm("Bạn chắc chắn muốn xóa User : "+grid.getCell(3).get(idx, item))){
                var xhrArgs = {
                    url: "userDelete!onDelete.do",
                    handleAs: "json",
                    content: {
                        id: grid.getCell(1).get(idx, item)
                    },
                    load: function(data) {
                        notification(data.label,data.customInfo);
                        dijit.byId("userListGird").vtReload("userSearch!onSearch.do","userForm",null,page.showMessage) ;
                    }
                };
                dojo.xhrGet(xhrArgs);
            }
        }else if(colField == "username"){
            openPopup("userRole.do?userID="+grid.getCell(1).get(idx, item)+"&name="+grid.getCell(3).get(idx, item),800,400);
        }
        
    });

    validate = function(){
        
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;
        
        if(username == ""){
            notification("ERROR","Chưa điền thông tin tài khoản!");
            document.getElementById("username").focus();
            return false;
        }
        if(password == ""){
            notification("ERROR","Chưa điền thông tin password!");
            document.getElementById("password").focus();
            return false;
        }
        return true;
    }

    page.onInsert = function(){
        if(validate() == true){
            var callback = function(data) {
                notification(data.label,data.customInfo);
            }
            dijit.byId('userListGird').vtReload("userInsert!onInsert.do","userForm",null,callback) ;
        }
    }

    page.onEdit = function(){
        if(validate() == true){
            var callback = function(data) {
                notification(data.label,data.customInfo);
            }
            dijit.byId('userListGird').vtReload("userUpdate!onUpdate.do","userForm",null,callback) ;
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
            dijit.byId('userListGird').vtReload("userDelete!onDelete.do","userForm",null,callback) ;
            dijit.byId("delete").setAttribute('disabled', true);
            page.reset();
        }
    }

</script>
