/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.DAO.util;

import com.scrape.BO.Config;
import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.common.DojoJSON;
import java.util.ArrayList;
import java.util.HashMap;
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
public class ConfigDAO extends BaseHibernateDAOMDB {

    private DojoJSON jsonDataGrid = new DojoJSON();
    private Config config = new Config();
    private int startval;
    private int count;
    private String sort;
    private String result;
    private String isactive;

    public String prepare() {
        getRequest().getSession().setAttribute("isactive", "Y");
        return SUCCESS;
    }

    public List<Config> takeConfigs() {
        List<Config> lst = null;
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

            Criteria cri = session.createCriteria(Config.class);

            if (config != null) {
                
                if (config.getType() != null && !"".equals(config.getType())) {
                    cri.add(Restrictions.eq("type", config.getType()));
                }
                if (config.getValue() != null && !"".equals(config.getValue())) {
                    cri.add(Restrictions.eq("value", config.getValue()));
                }
                if (config.getName() != null && !"".equals(config.getName())) {
                    cri.add(Restrictions.eq("name", config.getName()));
                }
                if (config.getPriority() != null) {
                    cri.add(Restrictions.eq("priority", config.getPriority()));
                }
                if (config.getUsefor() != null && !"".equals(config.getUsefor())) {
                    cri.add(Restrictions.eq("usefor", config.getUsefor()));
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
                cri.addOrder(Order.desc("type").ignoreCase());
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
            StringBuilder sql = new StringBuilder("select count(*) from Config where 1=1 ");
            ArrayList param = new ArrayList();


            if (config != null) {
                
                if (config.getType() != null && !"".equals(config.getType())) {
                    sql.append(" AND type = ? ");
                    param.add(config.getType());
                }
                if (config.getValue() != null && !"".equals(config.getValue())) {
                    sql.append(" AND value = ? ");
                    param.add(config.getValue());
                }
                if (config.getName() != null && !"".equals(config.getName())) {
                    sql.append(" AND name = ? ");
                    param.add(config.getName());
                }
                if (config.getPriority() != null) {
                    sql.append(" AND priority = ? ");
                    param.add(config.getPriority());
                }
                if (config.getUsefor() != null && !"".equals(config.getUsefor())) {
                    sql.append(" AND usefor = ? ");
                    param.add(config.getUsefor());
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
            List<Config> lstResult = new ArrayList<Config>();
            lstResult = takeConfigs();
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
            Long id = config.getId();
            if (id != null && id > 0) {
                Config temp = new Config();
                temp = (Config) session.get(Config.class, id);
                temp.setType(config.getType());
                temp.setValue(config.getValue());
                temp.setName(config.getName());
                temp.setUsefor(config.getUsefor());
                temp.setPriority(config.getPriority());
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
            if(config.getId() > 0){
                id = config.getId();
            }else if(getRequest().getParameter("id") != null){
                id = Long.parseLong(getRequest().getParameter("id"));
            }
            if (id != null && id > 0) {
                Config temp = new Config();
                temp = (Config) session.get(Config.class, id);
                session.delete(temp);
                session.flush();
            }

            jsonDataGrid.setLabel("SUCCESS");
            jsonDataGrid.setCustomInfo("Delete Thành Công!");
            if(getRequest().getParameter("id") == null){
                config = new Config();
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
            HashMap<String, Object> map =new HashMap<String, Object>();
            map.put("type", config.getType());
            map.put("value", config.getValue());
            map.put("name", config.getName());
            List checks = findByProperty(Config.class,map);
            if (checks != null && checks.size() > 0) {
                jsonDataGrid.setLabel("ERROR");
                jsonDataGrid.setCustomInfo("Đã tồn tại bộ giá trị trên!");
            } else {
                if(config.getIsactive() == null) config.setIsactive('Y');
                session.save(config);
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
                Config temp = new Config();
                temp = (Config) session.get(Config.class, Long.parseLong(getRequest().getParameter("id")));
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

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }
    
}
