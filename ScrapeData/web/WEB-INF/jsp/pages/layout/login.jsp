<%-- 
    Document   : login
    Created on : Sep 4, 2015, 10:37:47 AM
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

<%
    request.setAttribute("bootstrap", request.getContextPath() + "/share/bootstrap");
%>

<html lang="en">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Login</title>

<!--    <link href="/ScrapeData/share/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />-->
    <link href="${bootstrap}/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <style type="text/css">
        body {
          padding-top: 40px;
          padding-bottom: 40px;
          background-color: #eee;
          background-repeat: no-repeat;*/
        }

        .form-signin {
          max-width: 330px;
          padding: 15px;
          margin: 0 auto;
        }
        .form-signin .form-signin-heading,
        .form-signin .checkbox {
          margin-bottom: 10px;
        }
        .form-signin .checkbox {
          font-weight: normal;
        }
        .form-signin .form-control {
          position: relative;
          height: auto;
          -webkit-box-sizing: border-box;
             -moz-box-sizing: border-box;
                  box-sizing: border-box;
          padding: 10px;
          font-size: 14px;
        }
        .form-signin .form-control:focus {
          z-index: 2;
        }
        .form-signin input[type="input"] {
          margin-bottom: -1px;
          border-bottom-right-radius: 0;
          border-bottom-left-radius: 0;
        }
        .form-signin input[type="password"] {
          margin-bottom: 10px;
          border-top-left-radius: 0;
          border-top-right-radius: 0;
        }
    </style>
  </head>

  <body>

    <div class="container">

      <form class="form-signin" id="login" name="login" action="login.do?request_locale=vi_VN" method="post">
        <h2 class="form-signin-heading">Login</h2>
        <label for="inputUsername" class="sr-only">Tài khoản</label>
<!--        <input type="email" id="inputEmail" name="username" class="form-control" placeholder="Tài khoản" required autofocus>-->
        <input type="input" id="inputUsername" name="username" class="form-control" placeholder="Tài khoản" required autofocus>
        <label for="inputPassword" class="sr-only">Mật khẩu</label>
        <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Mật khẩu" required>
        <div class="row" style="padding-bottom:5px">
            <div class="col-md-6">
                <input type="checkbox" value="remember-me"> Ghi nhớ
            </div>
           <div class="col-md-6 forgot-pass-content">
               <a href="" >Quên mật khẩu</a>
            </div>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit" >Đăng nhập</button>

        <s:if test="%{#request.checkLogin == 'error'}">
              <h5 style="margin-top : 10px; text-align: center; color: red">Sai thông tin tài khoản và mật khẩu</h5>
        </s:if>

      </form>

    </div> <!-- /container -->

<!--    <script>
        login =function(){
            var xhrArgs = {
                url: "login.do",
                form: document.getElementById("login"),
                load: function(data) {
                    //alert(data);//json tra ve tren JS o dang string. Can phai parse thanh doi tuong json tren JS
                    notification(JSON.parse(data).label,JSON.parse(data).customInfo);
                }
            };
            dojo.xhrPost(xhrArgs);
        }

    </script>-->

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<!--    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>-->
  </body>
</html>
