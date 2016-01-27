<%-- 
    Document   : user
    Created on : Aug 28, 2015, 1:49:54 PM
    Author     : KeyLove
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="sd" uri="struts-dojo-tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<sd:TitlePane id="userPopup"  key="Thông tin User">
    <s:form  id="userPopupForm"  name="userPopupForm"  method="POST">
        <table width="100%">
            <s:hidden name="user.id" id="id"/>
            <tr>
                <td>
                    <s:textfield  id="fullname"  name="user.fullname" key="Tên đầy đủ" trim="true" required="true" maxlength="250"/>
                </td>
            </tr>
            <tr>
                <td>
                    <s:textfield id="username"   name="user.username" key="Tài khoản" trim="true" required="true" maxlength="250" readonly="true"/>
                </td>
            </tr>

            <tr>
                <td>
                    <s:password id="password" type="password"  name="user.password" key="Mật khẩu" trim="true" required="true" maxlength="250"/>
                </td>
                
            </tr>
            <tr>
                <td>
                    <s:password id="passwordConfirm" type="password" name="user.passwordConfirm" key="Xác nhận mật khẩu" trim="true" required="true" maxlength="250"/>
                </td>
            </tr>
            <tr>
                <td>
                    <s:textfield id="email"   name="user.email" key="Email" trim="true" required="true" maxlength="250"/>
                </td>
            </tr>

            <tr>
                <td>
                    <s:textfield id="passwordHint"  name="user.passwordHint" key="Gợi ý mật khẩu" trim="true" maxlength="250"/>
                </td>
                
            </tr>

            <tr>
                <td colspan ="3" align="center">
                    <sd:Button id="edit" key="Sửa"
                               onclick="page.onEdit()">
                        <img alt="edit"  src="share/Img/icons/edit_1.png" height="17" width="20">
                    </sd:Button>
                    <sd:Button id="close" key="Thoát"
                               onclick="page.onExit()">
                        <img src="share/Img/Icon/Close-2-icon.png" height="17" width="20">
                    </sd:Button>
                </td>
            </tr>
        </table>
    </s:form>
</sd:TitlePane>

    <br>
  <div id="notification" class= "notifications bottom-right"></div>

<script>
    
    validate = function(){
        
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;
        var passwordConfirm = document.getElementById("passwordConfirm").value;
        
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
        if(passwordConfirm == ""){
            notification("ERROR","Chưa điền thông tin xác nhận password!");
            document.getElementById("passwordConfirm").focus();
            return false;
        }
        if(password != passwordConfirm){
            notification("ERROR","Xác nhận lại mật khẩu sai!");
            document.getElementById("passwordConfirm").focus();
            return false;
        }
        return true;
    }

    page.onEdit = function(){
        if(validate() == true){
            var callback = function(data) {
                notification(data.label,data.customInfo);
            }
            var xhrArgs = {
                url: "userUpdate.do",
                handleAs: "json",
                form: document.getElementById("userPopupForm"),
                load: function(data) {
                    //alert(data);//json tra ve tren JS o dang string. Can phai parse thanh doi tuong json tren JS
                    notification(data.label,data.customInfo);
                }
            };
            dojo.xhrPost(xhrArgs);
        }
    }

</script>
