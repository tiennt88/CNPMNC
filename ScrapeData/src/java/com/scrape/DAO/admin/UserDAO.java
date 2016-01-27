/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.DAO.admin;

import com.scrape.BO.Users;
import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.common.DojoJSON;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author KeyLove
 */
public class UserDAO extends BaseHibernateDAOMDB {

    private DojoJSON jsonDataGrid = new DojoJSON();
    private Users user = new Users();
    private int startval;
    private int count;
    private String sort;
    private String result;
    private String isactive;

    public String prepare() {
        getRequest().getSession().setAttribute("isactive", "Y");
        return SUCCESS;
    }

    public String preparePopup(){
        Users userToken = (Users) getRequest().getSession().getAttribute("userToken");
        if(userToken != null){
            Session session = getSession();
            user = (Users) session.get(Users.class, userToken.getId());
        }
        return SUCCESS;
    }

    public List<Users> takeAppusers() {
        List<Users> lst = null;
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

            Criteria cri = session.createCriteria(Users.class);

            if (user != null) {
                
                if (user.getFullname() != null && !"".equals(user.getFullname())) {
                    cri.add(Restrictions.ilike("fullname", user.getFullname(),MatchMode.ANYWHERE));
                }
                if (user.getUsername() != null && !"".equals(user.getUsername())) {
                    cri.add(Restrictions.eq("username", user.getUsername()));
                }
                
                if (isactive != null) {
                    cri.add(Restrictions.eq("isactive", "Y"));
                } else {
                    cri.add(Restrictions.eq("isactive", "N"));
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
            StringBuilder sql = new StringBuilder("select count(*) from Users where 1=1 ");
            ArrayList param = new ArrayList();


            if (user != null) {
                
                if (user.getFullname() != null && !"".equals(user.getFullname())) {
                    sql.append(" AND fullname like ? ");
                    param.add("%"+user.getFullname()+"%");
                }
                if (user.getUsername() != null && !"".equals(user.getUsername())) {
                    sql.append(" AND username = ? ");
                    param.add(user.getUsername());
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
            List<Users> lstResult = new ArrayList<Users>();
            lstResult = takeAppusers();
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
            Long id = user.getId();
            if (id != null && id > 0) {
                Users temp = new Users();
                temp = (Users) session.get(Users.class, id);
                temp.setUsername(user.getUsername());
                temp.setFullname(user.getFullname());
                temp.setPassword(DigestUtils.md5Hex(user.getPassword()));
                temp.setPasswordHint(user.getPasswordHint());
                temp.setEmail(user.getEmail());
                
                if (isactive != null) {
                    temp.setIsactive("Y");
                } else {
                    temp.setIsactive("N");
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
            if(user.getId() > 0){
                id = user.getId();
            }else if(getRequest().getParameter("id") != null){
                id = Long.parseLong(getRequest().getParameter("id"));
            }
            if (id != null && id > 0) {
                Users temp = new Users();
                temp = (Users) session.get(Users.class, id);
                session.delete(temp);
                session.flush();
            }

            jsonDataGrid.setLabel("SUCCESS");
            jsonDataGrid.setCustomInfo("Delete Thành Công!");
            if(getRequest().getParameter("id") == null){
                user = new Users();
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
            map.put("username", user.getUsername());
            List checks = findByProperty(Users.class,map);
            if (checks != null && checks.size() > 0) {
                jsonDataGrid.setLabel("ERROR");
                jsonDataGrid.setCustomInfo("Đã tồn tại tài khoản trên!");
            } else {
                user.setPassword(DigestUtils.md5Hex(user.getPassword()));
                if(user.getIsactive() == null) user.setIsactive("Y");
                if(user.getSystemPasswordEnable() == null) user.setSystemPasswordEnable("Y");
                Date date = new Date();
                user.setCreateDate(date);
                user.setLastUpdate(date);
                session.save(user);
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
                Users temp = new Users();
                temp = (Users) session.get(Users.class, Long.parseLong(getRequest().getParameter("id")));
                if("Y".equals(getRequest().getParameter("active"))){
                    temp.setIsactive("N");
                }else{
                    temp.setIsactive("Y");
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

    public String onSystemPassWord() {
        try {
            Session session = getSession();
            if(getRequest().getParameter("id") != null && getRequest().getParameter("active") != null){
                Users temp = new Users();
                temp = (Users) session.get(Users.class, Long.parseLong(getRequest().getParameter("id")));
                if("Y".equals(getRequest().getParameter("active"))){
                    temp.setSystemPasswordEnable("N");
                }else{
                    temp.setSystemPasswordEnable("Y");
                }
                session.update(temp);
                session.flush();
            }
            jsonDataGrid.setLabel("SUCCESS");
            jsonDataGrid.setCustomInfo("Cập Nhật Trạng Thái System PassWord Thành Công!");
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

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

}
