/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.DAO.admin;

import com.scrape.BO.Roles;
import com.scrape.BO.UserRole;
import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.DAO.ComboBoxDAO;
import com.scrape.client.form.ComboBoxID;
import com.scrape.client.form.UserRoleForm;
import com.scrape.common.DojoJSON;
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
public class UserRoleDAO extends BaseHibernateDAOMDB {

    private DojoJSON jsonDataGrid = new DojoJSON();
    private Roles role = new Roles();
    private int startval;
    private int count;
    private String sort;
    private String result;
    private String isactive;
    private List<ComboBoxID> users = new ArrayList<ComboBoxID>();
    private List<ComboBoxID> roles = new ArrayList<ComboBoxID>();
    private UserRoleForm roleUser = new UserRoleForm();

    public String prepare(){
        ComboBoxDAO c = new ComboBoxDAO();
        if (getRequest().getParameter("roleID") != null && getRequest().getParameter("name") != null) {
            roleUser.setRoleID(Long.parseLong(getRequest().getParameter("roleID")));
            roleUser.setRoleName(getRequest().getParameter("name"));
            users = c.comboboxID("Users", "USERNAME");
            getRequest().setAttribute("roleUserType", "RoleAddUser");
            roleUser.setRoleUserType("RoleAddUser");
        }else if (getRequest().getParameter("userID") != null && getRequest().getParameter("name") != null) {
            roleUser.setUserID(Long.parseLong(getRequest().getParameter("userID")));
            roleUser.setUserName(getRequest().getParameter("name"));
            roles = c.comboboxID("Roles", "Name");
            getRequest().setAttribute("roleUserType", "UserAddRole");
            roleUser.setRoleUserType("UserAddRole");
        }
        getRequest().getSession().setAttribute("UserRole1",roleUser);
        return SUCCESS;
    }

    public String onSearch() {
        List<Roles> lst = null;
        String sortType = null;
        try {
            if(getRequest().getSession().getAttribute("UserRole1") != null){
                roleUser = (UserRoleForm) getRequest().getSession().getAttribute("UserRole1");
                getRequest().getSession().removeAttribute("UserRole1");
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
            sql.append(" SELECT id,user_id userID,role_id roleID,isactive,  ");
                sql.append(" (select name from roles where id = role_id) roleName, ");
                sql.append(" (select username from users where id = user_id) userName ");
            sql.append(" FROM user_role ");
            sql.append(" WHERE 1=1 ");

            if("RoleAddUser".equals(roleUser.getRoleUserType())){
                sql.append(" AND role_id = ? ");
            }else{
                sql.append(" AND user_id = ? ");
            }


            if (sort != null) {
                if (sortType != null && sortType.equals("asc")) {
                    sql.append(" Order by ").append(sort);
                } else if (sortType != null && sortType.equals("desc")) {
                    sql.append(" Order by ").append(sort).append(" desc ");
                }
            } else {
                sql.append(" Order by userName ");
            }

            Query query = session.createSQLQuery(sql.toString())
                    .addScalar("id", Hibernate.LONG)
                    .addScalar("userID", Hibernate.LONG)
                    .addScalar("roleID", Hibernate.LONG)
                    .addScalar("userName", Hibernate.STRING)
                    .addScalar("roleName", Hibernate.STRING)
                    .addScalar("isactive", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(UserRoleForm.class));

            if("RoleAddUser".equals(roleUser.getRoleUserType())){
                query.setParameter(0, roleUser.getRoleID());
            }else{
                query.setParameter(0, roleUser.getUserID());
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
        try {
            Session session = getSession();
            HashMap<String, Object> map =new HashMap<String, Object>();
            map.put("roleId", roleUser.getRoleID());
            map.put("userId", roleUser.getUserID());
            List checks = findByProperty(UserRole.class,map);
            if (checks != null && checks.size() > 0) {
                jsonDataGrid.setLabel("ERROR");
                jsonDataGrid.setCustomInfo("Đã tồn tại User với Role trên!");
            } else {
                UserRole temp = new UserRole();
                temp.setRoleId(roleUser.getRoleID());
                temp.setUserId(roleUser.getUserID());
                temp.setIsactive("Y");
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

    public String onUpdate() {
        try {
            Session session = getSession();
            Long id = roleUser.getId();
            if (id != null && id > 0) {
                UserRole temp = new UserRole();
                temp = (UserRole) session.get(UserRole.class, id);
                temp.setRoleId(roleUser.getRoleID());
                temp.setUserId(roleUser.getUserID());
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
            if(roleUser.getId() != null ){
                id = roleUser.getId();
            }else if(getRequest().getParameter("id") != null){
                id = Long.parseLong(getRequest().getParameter("id"));
            }
            if (id != null && id > 0) {
                UserRole temp = new UserRole();
                temp = (UserRole) session.get(UserRole.class, id);
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
                UserRole temp = new UserRole();
                temp = (UserRole) session.get(UserRole.class, Long.parseLong(getRequest().getParameter("id")));
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

    public List<ComboBoxID> getUsers() {
        return users;
    }

    public void setUsers(List<ComboBoxID> users) {
        this.users = users;
    }

    public UserRoleForm getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(UserRoleForm roleUser) {
        this.roleUser = roleUser;
    }

    public List<ComboBoxID> getRoles() {
        return roles;
    }

    public void setRoles(List<ComboBoxID> roles) {
        this.roles = roles;
    }

}
