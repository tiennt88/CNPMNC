/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.DAO.admin;

import com.scrape.BO.Menu;
import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.client.form.MenuForm;
import com.scrape.common.DojoJSON;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author KeyLove
 */
public class MenuDAO extends BaseHibernateDAOMDB {

    private DojoJSON jsonDataGrid = new DojoJSON();
    private MenuForm menu = new MenuForm();
    private int startval;
    private int count;
    private String sort;
    private String result;
    ArrayList param = new ArrayList();
    private String isactive;

    public String prepare() {
        getRequest().getSession().setAttribute("isactive", "Y");
        return SUCCESS;
    }

    public String takeSQL(String type) {
        param.clear();
        StringBuilder sql = new StringBuilder();

        sql.append(" WITH items AS ( ");
        sql.append(" SELECT id, name,VALUE, PARENT_ID, action, isactive,PRIORITY ");
        sql.append(" , 0 AS Level ");
        sql.append(" , CAST(PRIORITY AS VARCHAR(255)) AS Path ");
        sql.append(" FROM menu  ");
        sql.append(" WHERE parent_id IS NULL ");
        sql.append(" UNION ALL ");
        sql.append(" SELECT i.id, i.name, i.VALUE, i.PARENT_ID, i.action, i.isactive, i.PRIORITY ");
        sql.append(" , Level + 1 ");
        sql.append(" , CAST(Path + '.' + CAST(i.PRIORITY AS VARCHAR(255)) AS VARCHAR(255)) ");
        sql.append(" FROM menu i ");
        sql.append(" INNER JOIN items itms ON itms.id = i.parent_id ");
        sql.append(" ) ");

        if ("count".equals(type)) {
            sql.append(" select count(*) ");
        } else {

            sql.append(" SELECT id,parent_Id parentId ,name, value,action, priority, ");
            sql.append(" (select VALUE from MENU where ID = a.PARENT_ID) parent, level,Path,isactive ");
        }

        sql.append(" FROM items a where 1=1");


        if (menu != null) {
            if (menu.getParent() != null && !"".equals(menu.getParent())) {
                if ("null".equals(menu.getParent().toLowerCase())) {
                    sql.append(" AND PARENT_ID is null ");
                } else {
                    sql.append(" AND (select VALUE from MENU where ID = a.PARENT_ID) = ? ");
                    param.add(menu.getParent());
                }
            }

            if (menu.getValue() != null && !"".equals(menu.getValue())) {
                sql.append(" AND value = ? ");
                param.add(menu.getValue());
            }

            if (menu.getName() != null && !"".equals(menu.getName())) {
                sql.append(" AND lower(name) like ? ");
                param.add("%" + menu.getName().toLowerCase() + "%");
            }

            if (menu.getAction() != null && !"".equals(menu.getAction())) {
                if ("null".equals(menu.getAction().toLowerCase())) {
                    sql.append(" AND action is null ");
                } else {
                    sql.append(" AND lower(action) like ? ");
                    param.add(menu.getAction().toLowerCase());
                }
            }

            if (menu.getPriority() != null) {
                sql.append(" AND priority = ? ");
                param.add(menu.getPriority());
            }

            if(isactive != null){
                sql.append(" AND isactive = 'Y' ");
            }else{
                sql.append(" AND isactive = 'N' ");
            }
        }
        
        if (!"count".equals(type)) {
            String sortType = null;
            if (sort != null) {
                if (sort.indexOf('-') != -1) {
                    sortType = "asc";
                    sort = sort.substring(1);
                } else {
                    sortType = "desc";
                }
            }

            if (sort != null) {
                if (sortType != null && sortType.equals("asc")) {
                    sql.append(" Order by ").append(sort);
                } else if (sortType != null && sortType.equals("desc")) {
                    sql.append(" Order by ").append(sort).append(" desc ");
                }
            } else {
                sql.append(" order by Path ");
            }
        }

        return sql.toString();
    }

    public List<MenuForm> takeMenus() {
        List<MenuForm> lst = null;

        try {

            String sql = takeSQL("getData");

            Query query = getSession().createSQLQuery(sql.toString()).addScalar("id", Hibernate.LONG).addScalar("parentId", Hibernate.LONG).addScalar("parent", Hibernate.STRING).addScalar("value", Hibernate.STRING).addScalar("name", Hibernate.STRING).addScalar("action", Hibernate.STRING).addScalar("level", Hibernate.INTEGER).addScalar("priority", Hibernate.INTEGER).addScalar("isactive", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(MenuForm.class));

            for (int i = 0; i < param.size(); i++) {
                query.setParameter(i, param.get(i));
            }
            if (startval >= 0) {
                query.setFirstResult(startval);
            }
            if (count >= 0) {
                query.setMaxResults(count);
            }
            lst = query.list();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lst;
    }

//    Lay so luong ban ghi tra ve grid de phan trang tung phan
    public void setMaxResult() {
        try {
            String sql = takeSQL("count");

            SQLQuery query = getSession().createSQLQuery(sql.toString());
            for (int i = 0; i < param.size(); i++) {
                query.setParameter(i, param.get(i));
            }

            List lst = query.list();
            Integer countRecord = (Integer) lst.get(0);
            if (String.valueOf(count).length() >= 6) {
                count = countRecord.intValue();
            }
            jsonDataGrid.setTotalRows(countRecord.intValue());
        } catch (Exception ex) {
            jsonDataGrid.setTotalRows(0);
            ex.printStackTrace();
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
            List<MenuForm> lstResult = new ArrayList<MenuForm>();
            lstResult = takeMenus();
            jsonDataGrid.setItems(lstResult);

        } catch (Exception ex) {
            ex.printStackTrace();
            jsonDataGrid.setLabel("ERROR");
            jsonDataGrid.setCustomInfo(ex.getMessage());
        }
        return "jsonDataGrid";
    }

    public String onUpdate() {
        try {
            Session session = getSession();
            Long id = menu.getId();
            if (id != null && id > 0) {
                Menu temp = new Menu();
                temp = (Menu) session.get(Menu.class, id);
                if(menu.getParent() != null && menu.getParentId() != temp.getParentId()){
                    HashMap<String, Object> map1 =new HashMap<String, Object>();
                    map1.put("value", menu.getParent());
                    List checksParent = findByProperty(Menu.class,map1);
                    if (checksParent != null && checksParent.size() > 0) {
                        Menu parent = (Menu) checksParent.get(0);
                        temp.setParentId(parent.getId());
                    }else{
                        jsonDataGrid.setLabel("ERROR");
                        jsonDataGrid.setCustomInfo("Không tồn tại thư mục trên!");
                        return "jsonDataGrid";
                    }
                }
                temp.setValue(menu.getValue());
                temp.setName(menu.getName());
                temp.setAction(menu.getAction());
                temp.setPriority(menu.getPriority());
                
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
            if(menu.getId() != null){
                id = menu.getId();
            }else if(getRequest().getParameter("id") != null){
                id = Long.parseLong(getRequest().getParameter("id"));
            }
            
            if (id != null && id > 0) {
                Menu temp = new Menu();
                temp = (Menu) session.get(Menu.class, id);
                session.delete(temp);
                session.flush();
            }

            jsonDataGrid.setLabel("SUCCESS");
            jsonDataGrid.setCustomInfo("Delete Thành Công!");
            if(getRequest().getParameter("id") == null){
                menu = new MenuForm();
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
            map.put("value", menu.getValue());
            List checks = findByProperty(Menu.class,map);
            if (checks != null && checks.size() > 0) {
                jsonDataGrid.setLabel("ERROR");
                jsonDataGrid.setCustomInfo("Đã tồn tại Menu trên!");
            } else {
                Menu temp = new Menu();
                if(menu.getParent() != null && !"".equals(menu.getParent())){
                    HashMap<String, Object> map1 =new HashMap<String, Object>();
                    map1.put("value", menu.getParent());
                    List checksParent = findByProperty(Menu.class,map1);
                    if (checksParent != null && checksParent.size() > 0) {
                        Menu parent = (Menu) checksParent.get(0);
                        temp.setParentId(parent.getId());
                    }else{
                        jsonDataGrid.setLabel("ERROR");
                        jsonDataGrid.setCustomInfo("Không tồn tại thư mục trên!");
                        return "jsonDataGrid";
                    }
                }

                temp.setName(menu.getName());
                temp.setValue(menu.getValue());
                temp.setPriority(menu.getPriority());
                temp.setAction(menu.getAction());

                session.save(temp);
                session.flush();
                jsonDataGrid.setLabel("SUCCESS");
                jsonDataGrid.setCustomInfo("Insert Thành Công!");
            }
            onSearch();
        } catch (Exception ex) {
            jsonDataGrid.setLabel("ERROR");
            jsonDataGrid.setCustomInfo(ex.getMessage());
            ex.printStackTrace();
        }
        return "jsonDataGrid";
    }


    public String onActive() {
        try {
            Session session = getSession();
            if(getRequest().getParameter("id") != null && getRequest().getParameter("active") != null){
                Menu temp = new Menu();
                temp = (Menu) session.get(Menu.class, Long.parseLong(getRequest().getParameter("id")));
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public MenuForm getMenu() {
        return menu;
    }

    public void setMenu(MenuForm menu) {
        this.menu = menu;
    }

}
