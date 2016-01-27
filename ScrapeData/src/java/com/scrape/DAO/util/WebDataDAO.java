/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.DAO.util;

import com.scrape.BO.WebData;
import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.common.DojoJSON;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author KeyLove
 */
public class WebDataDAO extends BaseHibernateDAOMDB {

    private DojoJSON jsonDataGrid = new DojoJSON();
    private WebData webData = new WebData();
    private int startval;
    private int count;
    private String sort;
    private String result;
    private String isactive;

    public String prepare() {
        getRequest().getSession().setAttribute("isactive", "Y");
        return SUCCESS;
    }

    public List<WebData> takeWebDatas() {
        List<WebData> lst = null;
        String sortType = null;
        try {
            Session session = getSession();
            if (sort != null) {
                if (sort.indexOf('-') != -1) {
                    sortType = "asc";
                    sort = sort.substring(1);
                } else {
                    sortType = "desc";
                }
            }

            Criteria cri = session.createCriteria(WebData.class);

            if (webData != null) {
                if (webData.getWeb() != null && !"".equals(webData.getWeb())) {
                    cri.add(Restrictions.eq("web", webData.getWeb()));
                }
                if (webData.getType() != null && !"".equals(webData.getType())) {
                    cri.add(Restrictions.eq("type", webData.getType()));
                }
                if (webData.getBrand() != null && !"".equals(webData.getBrand())) {
                    cri.add(Restrictions.eq("brand", webData.getBrand()));
                }
                if (webData.getLink() != null && !"".equals(webData.getLink())) {
                    cri.add(Restrictions.eq("link", webData.getLink()));
                }
                if (isactive != null) {
                    cri.add(Restrictions.eq("isactive", 'Y'));
                } else {
                    cri.add(Restrictions.eq("isactive", 'N'));
                }
            }

            if (sort != null) {
                if (sortType != null && sortType.equals("asc")) {
                    cri.addOrder(Order.asc(sort).ignoreCase());
                } else if (sortType != null && sortType.equals("desc")) {
                    cri.addOrder(Order.desc(sort).ignoreCase());
                }
            } else {
                cri.addOrder(Order.desc("lastUpdate").ignoreCase());
            }
            if (startval >= 0) {
                cri.setFirstResult(startval);
            }
            if (count >= 0) {
                cri.setMaxResults(count);
            }
            lst = cri.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lst;
    }

    public void setMaxResult() {
        try {
            StringBuilder sql = new StringBuilder("select count(*) from WebData where 1=1 ");
            ArrayList param = new ArrayList();


            if (webData != null) {
                if (webData.getWeb() != null && !"".equals(webData.getWeb())) {
                    sql.append(" AND web = ? ");
                    param.add(webData.getWeb());
                }
                if (webData.getType() != null && !"".equals(webData.getType())) {
                    sql.append(" AND type = ? ");
                    param.add(webData.getType());
                }
                if (webData.getBrand() != null && !"".equals(webData.getBrand())) {
                    sql.append(" AND brand = ? ");
                    param.add(webData.getBrand());
                }
                if (webData.getLink() != null && !"".equals(webData.getLink())) {
                    sql.append(" AND link = ? ");
                    param.add(webData.getLink());
                }
                if (isactive != null) {
                    sql.append(" AND isactive = 'Y' ");
                } else {
                    sql.append(" AND isactive = 'N' ");
                }
            }

            Query query = getSession().createQuery(sql.toString());
            for (int i = 0; i < param.size(); i++) {
                query.setParameter(i, param.get(i));
            }
            List lst = query.list();
            Long countRecord = (Long) lst.get(0);
            if (String.valueOf(count).length() >= 6) {
                count = countRecord.intValue();
            }
            jsonDataGrid.setTotalRows(countRecord.intValue());
        } catch (Exception ex) {
            jsonDataGrid.setTotalRows(0);
        }
    }

    public String onSearch() {
        try {
//            UserToken userToken = (UserToken) getRequest().getSession().getAttribute("userToken");
            if(getRequest().getSession().getAttribute("isactive") != null){
                isactive = getRequest().getSession().getAttribute("isactive").toString();
                getRequest().getSession().removeAttribute("isactive");
            }
            setMaxResult();
            List<WebData> lstResult = new ArrayList<WebData>();
            lstResult = takeWebDatas();
            jsonDataGrid.setItems(lstResult);

        } catch (Exception ex) {
            jsonDataGrid.setLabel("ERROR");
            jsonDataGrid.setCustomInfo(ex.getMessage());
        }
        return "jsonDataGrid";
    }

    public String onUpdate() {
        try {
            Session session = getSession();
            Long id = webData.getId();
            if (id != null && id > 0) {
                WebData temp = new WebData();
                temp = (WebData) session.get(WebData.class, id);
                temp.setWeb(webData.getWeb());
                temp.setType(webData.getType());
                temp.setBrand(webData.getBrand());
                temp.setLink(webData.getLink());
                if (isactive != null) {
                    temp.setIsactive('Y');
                } else {
                    temp.setIsactive('N');
                }

                session.update(temp);
                session.flush();
            }
            jsonDataGrid.setLabel("SUCCESS");
            jsonDataGrid.setCustomInfo("Update Thành Công!");
            onSearch();
        } catch (Exception ex) {
            jsonDataGrid.setLabel("ERROR");
            jsonDataGrid.setCustomInfo(ex.getMessage());
        }
        return "jsonDataGrid";
    }

    public String onDelete() {
        try {
            Session session = getSession();
            Long id = 0l;
            if(webData.getId() > 0){
                id = webData.getId();
            }else if(getRequest().getParameter("id") != null){
                id = Long.parseLong(getRequest().getParameter("id"));
            }
            if (id != null && id > 0) {
                WebData temp = new WebData();
                temp = (WebData) session.get(WebData.class, id);
                session.delete(temp);
                session.flush();
            }

            jsonDataGrid.setLabel("SUCCESS");
            jsonDataGrid.setCustomInfo("Delete Thành Công!");
            if(getRequest().getParameter("id") == null){
                webData = new WebData();
                onSearch();
            }
        } catch (Exception ex) {
            jsonDataGrid.setLabel("ERROR");
            jsonDataGrid.setCustomInfo(ex.getMessage());
        }
        return "jsonDataGrid";
    }

    public String onInsert() {
        try {
            Session session = getSession();
            List checkLinks = findByProperty("WebData", "Link", webData.getLink());
            if (checkLinks != null && checkLinks.size() > 0) {
                jsonDataGrid.setLabel("ERROR");
                jsonDataGrid.setCustomInfo("Đã tồn tại link trên!");
            } else {
                if(webData.getIsactive() == null) webData.setIsactive('Y');
                Date date =new Date();
                webData.setCreateDate(date);
                webData.setLastUpdate(date);
                session.save(webData);
                session.flush();
                jsonDataGrid.setLabel("SUCCESS");
                jsonDataGrid.setCustomInfo("Insert Thành Công!");
            }
            onSearch();
        } catch (Exception ex) {
            jsonDataGrid.setLabel("ERROR");
            jsonDataGrid.setCustomInfo(ex.getMessage());
        }
        return "jsonDataGrid";
    }

    public String onActive() {
        try {
            Session session = getSession();
            if(getRequest().getParameter("id") != null && getRequest().getParameter("active") != null){
                WebData temp = new WebData();
                temp = (WebData) session.get(WebData.class, Long.parseLong(getRequest().getParameter("id")));
                if("Y".equals(getRequest().getParameter("active"))){
                    temp.setIsactive('N');
                }else{
                    temp.setIsactive('Y');
                }
                session.update(temp);
                session.flush();
            }
            jsonDataGrid.setLabel("SUCCESS");
            jsonDataGrid.setCustomInfo("Cập Nhật Trạng Thái Thành Công!");
//            onSearch();
        } catch (Exception ex) {
            jsonDataGrid.setLabel("ERROR");
            jsonDataGrid.setCustomInfo(ex.getMessage());
        }
        return "jsonDataGrid";
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DojoJSON getJsonDataGrid() {
        return jsonDataGrid;
    }

    public void setJsonDataGrid(DojoJSON jsonDataGrid) {
        this.jsonDataGrid = jsonDataGrid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getStartval() {
        return startval;
    }

    public void setStartval(int startval) {
        this.startval = startval;
    }

    public WebData getWebData() {
        return webData;
    }

    public void setWebData(WebData webData) {
        this.webData = webData;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }
}
