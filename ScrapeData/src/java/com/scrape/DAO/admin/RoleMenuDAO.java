/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.DAO.admin;

import com.scrape.BO.Roles;
import com.scrape.BO.RoleMenu;
import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.DAO.ComboBoxDAO;
import com.scrape.client.form.ComboBoxID;
import com.scrape.client.form.RoleMenuForm;
import com.scrape.common.DojoJSON;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author KeyLove
 */
public class RoleMenuDAO extends BaseHibernateDAOMDB {

    private DojoJSON jsonDataGrid = new DojoJSON();
    private Roles role = new Roles();
    private int startval;
    private int count;
    private String sort;
    private String result;
    private String isactive;
    private List<ComboBoxID> menus = new ArrayList<ComboBoxID>();
    private List<ComboBoxID> roles = new ArrayList<ComboBoxID>();
    private RoleMenuForm roleMenu = new RoleMenuForm();

    public String prepare(){
        ComboBoxDAO c = new ComboBoxDAO();
        if (getRequest().getParameter("roleID") != null && getRequest().getParameter("name") != null) {
            roleMenu.setRoleID(Long.parseLong(getRequest().getParameter("roleID")));
            roleMenu.setRoleName(getRequest().getParameter("name"));
            menus = c.comboboxID("Menu", "Name");
            getRequest().setAttribute("roleMenuType", "RoleAddMenu");
            roleMenu.setRoleMenuType("RoleAddMenu");
        }else if (getRequest().getParameter("menuID") != null && getRequest().getParameter("name") != null) {
            roleMenu.setMenuID(Long.parseLong(getRequest().getParameter("menuID")));
            roleMenu.setMenuName(getRequest().getParameter("name"));
            roles = c.comboboxID("Roles", "Name");
            getRequest().setAttribute("roleMenuType", "MenuAddRole");
            roleMenu.setRoleMenuType("MenuAddRole");
        }
        getRequest().getSession().setAttribute("RoleMenu1",roleMenu);
        return SUCCESS;
    }

    public String onSearch() {
        List<Roles> lst = null;
        String sortType = null;
        try {
            if(getRequest().getSession().getAttribute("RoleMenu1") != null){
                roleMenu = (RoleMenuForm) getRequest().getSession().getAttribute("RoleMenu1");
                getRequest().getSession().removeAttribute("RoleMenu1");
            }

            Session session = getSession();
            if (sort != null) {
                if (sort.indexOf('-') != -1) {
                    sortType = "asc";
                    sort = sort.substring(1);
                } else {
                    sortType = "desc";
                }
            }

            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT id,menu_id menuID,role_id roleID,isactive,  ");
                sql.append(" (select name from roles where id = role_id) roleName, ");
                sql.append(" (select name from menu where id = menu_id) menuName ");
            sql.append(" FROM role_menu ");
            sql.append(" WHERE 1=1 ");

            if("RoleAddMenu".equals(roleMenu.getRoleMenuType())){
                sql.append(" AND role_id = ? ");
            }else{
                sql.append(" AND menu_id = ? ");
            }


            if (sort != null) {
                if (sortType != null && sortType.equals("asc")) {
                    sql.append(" Order by ").append(sort);
                } else if (sortType != null && sortType.equals("desc")) {
                    sql.append(" Order by ").append(sort).append(" desc ");
                }
            } else {
                sql.append(" Order by menuName ");
            }

            Query query = session.createSQLQuery(sql.toString())
                    .addScalar("id", Hibernate.LONG)
                    .addScalar("menuID", Hibernate.LONG)
                    .addScalar("roleID", Hibernate.LONG)
                    .addScalar("menuName", Hibernate.STRING)
                    .addScalar("roleName", Hibernate.STRING)
                    .addScalar("isactive", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(RoleMenuForm.class));

            if("RoleAddMenu".equals(roleMenu.getRoleMenuType())){
                query.setParameter(0, roleMenu.getRoleID());
            }else{
                query.setParameter(0, roleMenu.getMenuID());
            }

            lst = query.list();
            jsonDataGrid.setTotalRows(lst.size());
            jsonDataGrid.setItems(lst);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "jsonDataGrid";
    }


    public String onInsert() {
        CallableStatement cs = null;
        Connection conn = null;
        try {
            Session session = getSession();
            HashMap<String, Object> map =new HashMap<String, Object>();
            map.put("roleId", roleMenu.getRoleID());
            map.put("menuId", roleMenu.getMenuID());
            List checks = findByProperty(RoleMenu.class,map);
            if (checks != null && checks.size() > 0) {
                jsonDataGrid.setLabel("ERROR");
                jsonDataGrid.setCustomInfo("Đã tồn tại Menu với Role trên!");
            } else {
//                RoleMenu temp = new RoleMenu();
//                temp.setRoleId(roleMenu.getRoleID());
//                temp.setMenuId(roleMenu.getMenuID());
//                temp.setIsactive("Y");
//                session.save(temp);
//                session.flush();
                conn = session.connection();
                //call store insight
                String sql = "{call addMenu (?,?)}";
                cs = conn.prepareCall(sql);
                cs.setLong(1, roleMenu.getMenuID());
                cs.setLong(2, roleMenu.getRoleID());
                cs.execute();
                jsonDataGrid.setLabel("SUCCESS");
                jsonDataGrid.setCustomInfo("SUCCESS");
            }
            onSearch();
        } catch (Exception ex) {
            jsonDataGrid.setLabel("ERROR");
            jsonDataGrid.setCustomInfo(ex.getMessage());
            ex.printStackTrace();
        }finally
        {
            closeObject(cs);
            closeObject(conn);
        }
        return "jsonDataGrid";
    }

    public String onUpdate() {
        try {
            Session session = getSession();
            Long id = roleMenu.getId();
            if (id != null && id > 0) {
                RoleMenu temp = new RoleMenu();
                temp = (RoleMenu) session.get(RoleMenu.class, id);
                temp.setRoleId(roleMenu.getRoleID());
                temp.setMenuId(roleMenu.getMenuID());
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
            if(roleMenu.getId() != null ){
                id = roleMenu.getId();
            }else if(getRequest().getParameter("id") != null){
                id = Long.parseLong(getRequest().getParameter("id"));
            }
            if (id != null && id > 0) {
                RoleMenu temp = new RoleMenu();
                temp = (RoleMenu) session.get(RoleMenu.class, id);
                session.delete(temp);
                session.flush();
            }
            jsonDataGrid.setLabel("SUCCESS");
            jsonDataGrid.setCustomInfo("Delete Thành Công!");
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
                RoleMenu temp = new RoleMenu();
                temp = (RoleMenu) session.get(RoleMenu.class, Long.parseLong(getRequest().getParameter("id")));
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

    public Roles getApprole() {
        return role;
    }

    public void setApprole(Roles role) {
        this.role = role;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }


    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public List<ComboBoxID> getMenus() {
        return menus;
    }

    public void setMenus(List<ComboBoxID> menus) {
        this.menus = menus;
    }

    public RoleMenuForm getRoleMenu() {
        return roleMenu;
    }

    public void setRoleMenu(RoleMenuForm roleMenu) {
        this.roleMenu = roleMenu;
    }

    public List<ComboBoxID> getRoles() {
        return roles;
    }

    public void setRoles(List<ComboBoxID> roles) {
        this.roles = roles;
    }

}
