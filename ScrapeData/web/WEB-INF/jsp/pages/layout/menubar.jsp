<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
    //[ TienNT says: menu-model testing purpose
    var myMenu;
    useMenuTest=false;
    if(useMenuTest) {
        myMenu = [
            [null, 'MOBILE', null, 'target', '0',   // a folder item
                [null, 'FTG Mobile', 'javascript:doGoToMenu("mobileRoot.do", "0.0.0");', '_self', '0.0'],
                [null, 'Kiểm soát và chuẩn hóa dữ liệu Mobile', 'javascript:doGoToMenu("mobileData.do", "0.1.0");', '_self', '0.1'],
                [null, 'Tra cứu Mobile', 'javascript:doGoToMenu("mobileCompare.do", "0.2.0");', '_self', '0.2'],
                [null, 'So sánh Model', 'javascript:doGoToMenu("mobileModelCompare.do", "0.3.0");', '_self', '0.3'],
                [null, 'Tra cứu lịch sử giá', 'javascript:doGoToMenu("mobilePriceHistory.do", "0.4.0");', '_self', '0.4'],
                [null, 'Tra cứu Model mới', 'javascript:doGoToMenu("mobileModelNew.do", "0.5.0");', '_self', '0.5'],
                [null, 'Báo cáo Mobile', 'javascript:doGoToMenu("mobileReport.do", "0.6.0");', '_self', '0.6'],
                [null, 'Tra cứu mobile theo phân khúc', 'javascript:doGoToMenu("mobileSegmentation.do", "0.7.0");', '_self', '0.7'],
                [null, 'Tra cứu chênh lệch giá', 'javascript:doGoToMenu("mobilePriceDifferentLevels.do", "0.8.0");', '_self', '0.8']
            ],
            [null, 'TABLET', null, 'target', '1',   // a folder item
                [null, 'FTG Tablet', 'javascript:doGoToMenu("tabletRoot.do", "1.0.0");', '_self', '1.0'],
                [null, 'Kiểm soát và chuẩn hóa dữ liệu Tablet', 'javascript:doGoToMenu("tabletData.do", "1.1.0");', '_self', '1.1'],
                [null, 'Tra cứu Tablet', 'javascript:doGoToMenu("tabletCompare.do", "1.2.0");', '_self', '1.2'],
                [null, 'So sánh Model', 'javascript:doGoToMenu("tabletModelCompare.do", "1.3.0");', '_self', '1.3'],
                [null, 'Tra cứu lịch sử giá', 'javascript:doGoToMenu("tabletPriceHistory.do", "1.4.0");', '_self', '1.4'],
                [null, 'Tra cứu Model mới', 'javascript:doGoToMenu("tabletModelNew.do", "1.5.0");', '_self', '1.5'],
                [null, 'Báo cáo Tablet', 'javascript:doGoToMenu("tabletReport.do", "1.6.0");', '_self', '1.6'],
                [null, 'Tra cứu Tablet theo phân khúc', 'javascript:doGoToMenu("tabletSegmentation.do", "1.7.0");', '_self', '1.7'],
                [null, 'Tra cứu chênh lệch giá', 'javascript:doGoToMenu("tabletPriceDifferentLevels.do", "1.8.0");', '_self', '1.8']
            ],
            [null, 'LAPTOP', null, 'target', '2',   // a folder item
                [null, 'FTG Laptop', 'javascript:doGoToMenu("laptopRoot.do", "2.0.0");', '_self', '2.0'],
                [null, 'Kiểm soát và chuẩn hóa dữ liệu Laptop', 'javascript:doGoToMenu("laptopData.do", "2.1.0");', '_self', '2.1'],
                [null, 'Tra cứu Laptop', 'javascript:doGoToMenu("laptopCompare.do", "2.2.0");', '_self', '2.2'],
                [null, 'So sánh Model', 'javascript:doGoToMenu("laptopModelCompare.do", "2.3.0");', '_self', '2.3'],
                [null, 'Tra cứu lịch sử giá', 'javascript:doGoToMenu("laptopPriceHistory.do", "2.4.0");', '_self', '2.4'],
                [null, 'Tra cứu Model mới', 'javascript:doGoToMenu("laptopModelNew.do", "2.5.0");', '_self', '2.5'],
                [null, 'Báo cáo Laptop', 'javascript:doGoToMenu("laptopReport.do", "2.6.0");', '_self', '2.6'],
                [null, 'Tra cứu Laptop theo phân khúc', 'javascript:doGoToMenu("laptopSegmentation.do", "2.7.0");', '_self', '2.7'],
                [null, 'Tra cứu chênh lệch giá', 'javascript:doGoToMenu("laptopPriceDifferentLevels.do", "2.8.0");', '_self', '2.8']
            ],
            [null, 'Tiện ích', null, 'target', '3',   // a folder item
                [null, 'Lấy dữ liệu', 'javascript:doGoToMenu("scrapeData.do", "3.0.0");', '_self', '3.0'],
                [null, 'Thống kê dữ liệu', 'javascript:doGoToMenu("statisticData.do", "3.1.0");', '_self', '3.1'],
                [null, 'Cấu hình Web lấy dữ liệu', 'javascript:doGoToMenu("webData.do", "3.2.0");', '_self', '3.2'],
                [null, 'Cấu hình tham số Combobox', 'javascript:doGoToMenu("config.do", "3.3.0");', '_self', '3.3'],
                [null, 'Cấu hình Pattern', 'javascript:doGoToMenu("pattern.do", "3.4.0");', '_self', '3.4']
            ]
            ,[null, 'Quản trị', null, 'target', '4',   // a folder item
                [null, 'Danh mục User', 'javascript:doGoToMenu("user.do", "4.0.0");', '_self', '4.0'],
                [null, 'Danh mục Role', 'javascript:doGoToMenu("role.do", "4.1.0");', '_self', '4.1'],
                [null, 'Danh mục Menu', 'javascript:doGoToMenu("menu.do", "4.2.0");', '_self', '4.2']
            ]
        ];
    }

    
    <s:if test="#session.menus != null">
        <%
                    int parentCounter = 0, childCounter = 0, grandChildCounter = 0;
                    String sep = ".";
                    request.setAttribute("menuItemKey", "");
        %>
            sd.operator.menuModel = [null
        <s:iterator id="permission" value="#session.menus">
            <%
                        request.setAttribute("menuItemKey", parentCounter + "");
                        childCounter = 0;
            %>
                    ,[null, '<s:property value="getText(#permission.name)"/>', null, '_self', '${menuItemKey}'
            <s:iterator id="childFunction" value="#permission.functions">
                <%
                            request.setAttribute("menuItemKey", parentCounter + sep + childCounter);
                            grandChildCounter = 0;
                %>
                <s:if test=" #childFunction.childFunctions == null ||#childFunction.childFunctions.size()==0">
                    <s:set name="url" value="#childFunction.action" scope="request"/>
                                ,( "${url}" == "_" ) ? parent._cmSplit : [null, '&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="getText(#childFunction.name)"/>', 'javascript:doGoToMenu( "${url}", "${menuItemKey}" );', '_self', '${menuItemKey}']
                </s:if>
                <s:else>
                            ,[null, '<s:property value="getText(#childFunction.name)"/>', null, '_self', '${menuItemKey}'
                    <s:iterator id="child2Function" value="#childFunction.childFunctions">
                        <%
                                    request.setAttribute("menuItemKey", parentCounter + sep + childCounter + sep + grandChildCounter);
                        %>
                        <s:set name="url2" value="#child2Function.action" scope="request"/>
                                        ,( "${url}" == "_" ) ? parent._cmSplit : [null, '&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="getText(#child2Function.name)"/>', 'javascript:doGoToMenu( \"${url2}\", \"${menuItemKey}\" );', '_self', '${menuItemKey}']
                        <%
                                    grandChildCounter++;
                        %>
                    </s:iterator>
                                ]
                </s:else>
                <%
                            childCounter++;
                %>
            </s:iterator>
                    ]
            <%
                        parentCounter++;
            %>
        </s:iterator>
            ];
    </s:if>

        try {
            if(useMenuTest) {
                makeMenu(myMenu); // Testing purpose
            } else {
                makeMenu(sd.operator.menuModel);
            }
        } catch(e) {
            sd.log.error("In menubar.jsp::makeMenu: \n" + e.message);
        }
</script>