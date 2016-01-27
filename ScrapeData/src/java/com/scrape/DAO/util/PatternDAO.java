/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.DAO.util;

import com.scrape.BO.Pattern;
import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.common.DojoJSON;
import java.util.ArrayList;
import java.util.Date;
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
public class PatternDAO extends BaseHibernateDAOMDB {

    private DojoJSON jsonDataGrid = new DojoJSON();
    private Pattern pattern = new Pattern();
    private int startval;
    private int count;
    private String sort;
    private String result;
    private String isactive;

    public String prepare() {
        getRequest().getSession().setAttribute("isactive", "Y");
        return SUCCESS;
    }

    public List<Pattern> takePatterns() {
        List<Pattern> lst = null;
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

            Criteria cri = session.createCriteria(Pattern.class);

            if (pattern != null) {
                
                if (pattern.getType() != null && !"".equals(pattern.getType())) {
                    cri.add(Restrictions.eq("type", pattern.getType()));
                }
                if (pattern.getPattern() != null && !"".equals(pattern.getPattern())) {
                    cri.add(Restrictions.eq("pattern", pattern.getPattern()));
                }
                if (pattern.getValue() != null && !"".equals(pattern.getValue())) {
                    cri.add(Restrictions.eq("value", pattern.getValue()));
                }
                if (pattern.getPriority() != null) {
                    cri.add(Restrictions.eq("priority", pattern.getPriority()));
                }
                if (pattern.getUseFor() != null && !"".equals(pattern.getUseFor())) {
                    cri.add(Restrictions.eq("usefor", pattern.getUseFor()));
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
            StringBuilder sql = new StringBuilder("select count(*) from Pattern where 1=1 ");
            ArrayList param = new ArrayList();


            if (pattern != null) {
                
                if (pattern.getType() != null && !"".equals(pattern.getType())) {
                    sql.append(" AND type = ? ");
                    param.add(pattern.getType());
                }
                if (pattern.getPattern() != null && !"".equals(pattern.getPattern())) {
                    sql.append(" AND pattern = ? ");
                    param.add(pattern.getPattern());
                }
                if (pattern.getValue() != null && !"".equals(pattern.getValue())) {
                    sql.append(" AND value = ? ");
                    param.add(pattern.getValue());
                }
                if (pattern.getPriority() != null) {
                    sql.append(" AND priority = ? ");
                    param.add(pattern.getPriority());
                }
                if (pattern.getUseFor() != null && !"".equals(pattern.getUseFor())) {
                    sql.append(" AND usefor = ? ");
                    param.add(pattern.getUseFor());
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
            List<Pattern> lstResult = new ArrayList<Pattern>();
            lstResult = takePatterns();
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
            Long id = pattern.getId();
            if (id != null && id > 0) {
                Pattern temp = new Pattern();
                temp = (Pattern) session.get(Pattern.class, id);
                temp.setType(pattern.getType());
                temp.setPattern(pattern.getPattern());
                temp.setValue(pattern.getValue());
                temp.setPriority(pattern.getPriority());
                temp.setUseFor(pattern.getUseFor());
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
            if(pattern.getId() > 0){
                id = pattern.getId();
            }else if(getRequest().getParameter("id") != null){
                id = Long.parseLong(getRequest().getParameter("id"));
            }
            if (id != null && id > 0) {
                Pattern temp = new Pattern();
                temp = (Pattern) session.get(Pattern.class, id);
                session.delete(temp);
                session.flush();
            }

            jsonDataGrid.setLabel("SUCCESS");
            jsonDataGrid.setCustomInfo("Delete Thành Công!");
            if(getRequest().getParameter("id") == null){
                pattern = new Pattern();
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
            map.put("type", pattern.getType());
            map.put("pattern", pattern.getPattern());
            map.put("value", pattern.getValue());
            List checks = findByProperty(Pattern.class,map);
            if (checks != null && checks.size() > 0) {
                jsonDataGrid.setLabel("ERROR");
                jsonDataGrid.setCustomInfo("Đã tồn tại bộ giá trị trên!");
            } else {
                if(pattern.getIsactive() == null) pattern.setIsactive('Y');
                Date date =new Date();
                pattern.setCreateDate(date);
                pattern.setLastUpdate(date);
                session.save(pattern);
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
                Pattern temp = new Pattern();
                temp = (Pattern) session.get(Pattern.class, Long.parseLong(getRequest().getParameter("id")));
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

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }
}
