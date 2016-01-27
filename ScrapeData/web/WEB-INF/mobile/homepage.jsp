<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjm" uri="/struts-jquery-mobile-tags"%>

<sjm:list inset="true" filter="true">
    <sjm:listItem divider="true"><s:text name="test"/></sjm:listItem>
    <s:url var="url_checkboxlist" action="checkboxlist"/>
    <sjm:listItem divider="true">Tồn Kho</sjm:listItem>
    <sjm:listItem id="ajaxform_link" href="%{url_checkboxlist}">Báo cáo tồn kho</sjm:listItem>
    <sjm:listItem divider="true">Xuất kho</sjm:listItem>

    <%--sjm:listItem divider="true">Form Elements</sjm:listItem>
					<s:url var="url_textfield" action="textfield"/>
					<sjm:listItem id="textfield_link" href="%{url_textfield}">Textfield</sjm:listItem>
					<s:url var="url_textarea" action="textarea"/>
					<sjm:listItem id="textarea_link" href="%{url_textarea}">Textarea</sjm:listItem>
					<s:url var="url_password" action="password"/>
					<sjm:listItem id="password_link" href="%{url_password}">Password</sjm:listItem>
					<s:url var="url_searchfield" action="searchfield"/>
					<sjm:listItem id="searchfield_link" href="%{url_searchfield}">Searchfield</sjm:listItem>
					<s:url var="url_checkbox" action="checkbox"/>
					<sjm:listItem id="checkbox_link" href="%{url_checkbox}">Checkbox</sjm:listItem>
					<s:url var="url_checkboxlist" action="checkboxlist"/>
					<sjm:listItem id="checkboxlist_link" href="%{url_checkboxlist}">Checkbox List</sjm:listItem>
					<s:url var="url_radio" action="radio"/>
					<sjm:listItem id="radio_link" href="%{url_radio}">Radio Buttons</sjm:listItem>
					<s:url var="url_select" action="select"/>
					<sjm:listItem id="select_link" href="%{url_select}">Select</sjm:listItem>
    <s:url var="url_slider" action="slider"/>
    <sjm:listItem id="slider_link" href="%{url_slider}">Slider</sjm:listItem>
    <s:url var="url_flip" action="flip-switch"/>
    <sjm:listItem id="flip_link" href="%{url_flip}">Flip Switch</sjm:listItem>

					<sjm:listItem divider="true">List View</sjm:listItem>
					<s:url var="url_listview" action="list-view"/>
					<sjm:listItem id="listview_link" href="%{url_listview}">List View</sjm:listItem>
					<s:url var="url_listviewcounter" action="list-view-counter"/>
					<sjm:listItem id="listviewcounter_link" href="%{url_listviewcounter}">List View with Counter</sjm:listItem--%>
</sjm:list>