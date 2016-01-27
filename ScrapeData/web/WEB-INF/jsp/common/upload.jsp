<%--
    Document   : upload
    Created on : Jul 9, 2014, 3:04:46 PM
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

<!--tiennt add-->
<link href="${Upload}/css/jquery.fileupload.css" type="text/css" rel="stylesheet" />
<link href="${Upload}/css/jquery.fileupload-ui.css" type="text/css" rel="stylesheet" />
<script src="${Upload}/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
<script src="${Upload}/js/jquery.iframe-transport.js" type="text/javascript"></script>
<script src="${Upload}/js/jquery.fileupload.js" type="text/javascript"></script>
<script src="${Upload}/js/jquery.fileupload-process.js" type="text/javascript"></script>
<link href="${bootstrap}/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script src="${bootstrap}/js/bootstrap.min.js" type="text/javascript"></script>
<!--end tien add-->
<style type="text/css">
    body {
        font-family: Helvetica,Arial,sans-serif;
        font-size: 12px;
        font-style:normal;
        font-variant:normal;
        font-weight:normal;
    }
    label {
        display: inline-block;
        max-width: 100%;
        margin-bottom: 5px;
        font-weight: 100;
    }
</style>

<sd:TitlePane id="upload"  key="Upload File">
    <s:form  id="uploadForm"  name="uploadForm"  method="POST">
        <table width="100%">
            <tr>
                <td colspan="2">
                    <sd:Textarea id="type" name="type"  rows="1" key="Loại" readonly="true" required="true"/>
                </td>
            </tr>
            <tr>
                <td colspan="2"><br/></td>
            </tr>
            <tr>
                <td width="10%">
                    <div class="controls btn btn-success fileinput-button" >
                        <i class="glyphicon glyphicon-plus"></i>
                        <span>Add files...</span>
                        <!-- The name=files mapping to files in server -->
                        <input type="file" name="fileUpload" onchange="return validateFileExtension(this)">
                    </div>
                </td>
                <td>
                    <div class="progress" id="progress" style="height: 34px; margin-bottom: 10px " ><div class="bar progress-bar bar-success"></div></div>
                </td>
            </tr>

            <tr>
                <td  align="center" colspan="2">
                    <%--sd:Button id="complete" key="Upload File"
                               onclick="page.onUpload()">
                        <img alt="Upload File"  src="share/Img/icons/data-upload-icon.png" height="17" width="20">
                    </sd:Button--%>

                    <sd:Button id="close" key="Thoát"
                               onclick="page.onExit()">
                        <img alt="Thoát"  src="share/Img/Icon/Close-2-icon.png" height="17" width="20">
                    </sd:Button>

                </td>
            </tr>
        </table>
    </s:form>
</sd:TitlePane>

<script>

    function validateFileExtension(fld) {
        if(!/(\.xlsx)$/i.test(fld.value)) {
            $('#_notification').notify({ message: { text: 'Please input xlsx file!' }}).show();
            fld.form.reset();
            fld.focus();
            $('#progress .progress-bar').css(
            'width',
            0 + '%'
        );
            return false;
        }
        return true;
    }


    $(function () {
        'use strict';
        var url = 'onUpload.do';
        var totalFile = 0;
        var totalComplete = 0;
        var totalSuccess = 0;
        $('#upload').fileupload({
            url: url,
            dataType: 'json',
            add: function (e, data) {
                $.each(data.files, function (index, file) {
                    $('#progress>div').html(file.name);
                    $('#_fileName').val(file.name);
                    totalFile++;
                    data.submit();
                    $('#progress .progress-bar').css('width','0%');
                });
            },
            always: function(e, data) {
                $.each(data.files, function (index, file) {
                    var result = data.result.customInfo;
                    if (result == 'Success') {
                        notification("SUCCESS",result);
                    } else {
                        notification("ERROR",result);
                    }

                    totalComplete++;
                });
            },
            done: function (e, data) {
                $.each(data.files, function (index, file) {
                    totalSuccess++;
                });
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progress .progress-bar').css(
                'width',
                progress + '%'
            );
            }
        }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');
    });

</script>
