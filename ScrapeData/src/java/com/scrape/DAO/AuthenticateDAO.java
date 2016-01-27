/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.DAO;

import com.scrape.BO.Users;
import com.scrape.client.form.ChildFunction;
import com.scrape.client.form.Function;
import com.scrape.client.form.Menu;
import com.scrape.client.form.MenuForm;
import com.scrape.common.DojoJSON;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import javax.xml.namespace.QName;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author KeyLove
 */
public class AuthenticateDAO extends BaseHibernateDAOMDB {

    private String username;
    private String password;
    private Users user;
    private DojoJSON jsonDataGrid = new DojoJSON();

    public String getIndexPage() {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();

        String forwardPage = "indexSuccess";
        try {
            session.setAttribute("isValidate", "true");
//            session.setAttribute("userToken", "TienNT");
            session.setAttribute("contextPath", req.getContextPath());

        } catch (Exception ex) {
            ex.printStackTrace();
            this.addActionError(ex.getMessage());
            forwardPage = "error";
        }

        return forwardPage;
    }

    public String login() {
        String forward = ERROR;
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        StringBuilder sql = new StringBuilder();


        try {
            if (username != null && password != null) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("username", username);
//                map.put("password", password);
                map.put("isactive", "Y");
                List<Users> lst = findByProperty(Users.class, map);
                if (lst != null && lst.size() > 0) {

                    user = lst.get(0);
                    Boolean check = false;
                    if ("N".equals(user.getSystemPasswordEnable())) {
                        String endpoint = "http://login.ho.fpt.vn/fpter";
                        Service service = new Service();
                        Call call = (Call) service.createCall();
                        call.setTargetEndpointAddress(new java.net.URL(endpoint));
                        //	NamespaceUri và Service Name
                        call.setOperationName(new QName("http://login.ho.fpt.vn", "authentication"));
                        //	Truy?n Email và Password vào
                        check = (Boolean) call.invoke(new Object[]{user.getEmail(), password});
//                        System.out.println(check);
                    } else {
                        check = (user.getPassword().equals(DigestUtils.md5Hex(password)) || user.getPassword().equals(password));
                    }

                    if (check) {
                        session.setAttribute("username", username);
                        session.setAttribute("userToken", user);
                        session.setAttribute("contextPath", req.getContextPath());
                        forward = SUCCESS;

                        sql.append(" WITH items AS ( ");
                        sql.append(" SELECT id, name, ACTION ");
                        sql.append(" , 0 AS Level ");
                        sql.append(" , CAST(PRIORITY AS VARCHAR(255)) AS Path ");
                        sql.append(" FROM menu ");
                        sql.append(" WHERE parent_id IS NULL and isactive = 'Y' ");
                        sql.append(" UNION ALL ");
                        sql.append(" SELECT i.id, i.name,i.ACTION ");
                        sql.append(" , Level + 1 ");
                        sql.append(" , CAST(Path + '.' + CAST(i.PRIORITY AS VARCHAR(255)) AS VARCHAR(255)) ");
                        sql.append(" FROM menu i ");
                        sql.append(" INNER JOIN items itms ON itms.id = i.parent_id and i.isactive = 'Y' ");
                        sql.append(" ) ");
                        sql.append(" SELECT * FROM items a ");
                        sql.append(" where ID in (select distinct MENU_id from ROLE_MENU where isactive = 'Y' AND ROLE_ID in (select ROLE_ID from USER_ROLE where USER_ID = ? and isactive = 'Y') ) ");
                        sql.append(" ORDER BY Path; ");

                        Query query = getSession().createSQLQuery(sql.toString()).addScalar("name", Hibernate.STRING).addScalar("action", Hibernate.STRING).addScalar("level", Hibernate.INTEGER).setResultTransformer(Transformers.aliasToBean(MenuForm.class));

                        query.setParameter(0, user.getId());

                        List<MenuForm> list = new ArrayList<MenuForm>();
                        list = query.list();
                        if (list != null && list.size() > 0) {
                            List<ChildFunction> childfunctions = new ArrayList<ChildFunction>();
                            List<Function> functions = new ArrayList<Function>();
                            List<Menu> menus = new ArrayList<Menu>();
                            String preMenu = null;
                            String preFunction = null;
                            String preAction = null;

                            for (MenuForm m : list) {
                                if (m.getLevel() == 0) {
                                    if (preMenu != null) {
                                        functions.add(new Function(preFunction, preAction, childfunctions));
                                        childfunctions = new ArrayList<ChildFunction>();
                                        preFunction = null;
                                        menus.add(new Menu(preMenu, functions));
                                        functions = new ArrayList<Function>();
                                    }
                                    preMenu = m.getName();

                                } else if (m.getLevel() == 1) {
                                    if (preFunction != null) {
                                        functions.add(new Function(preFunction, preAction, childfunctions));
                                        childfunctions = new ArrayList<ChildFunction>();
                                    }
                                    preFunction = m.getName();
                                    preAction = m.getAction();
                                } else if (m.getLevel() == 2) {
                                    childfunctions.add(new ChildFunction(m.getName(), m.getAction()));
                                }
                            }
                            //add last menu
                            functions.add(new Function(preFunction, preAction, childfunctions));
                            menus.add(new Menu(preMenu, functions));

//                        for (Menu parent : menus) {
//                            System.out.println(parent.getName());
//                            for (Function child : parent.getFunctions()) {
//                                System.out.println("   " + child.getName());
//                                if (child.getChildFunctions() != null && child.getChildFunctions().size() > 0) {
//                                    for (ChildFunction child2 : child.getChildFunctions()) {
//                                        System.out.println("       " + child2.getName());
//                                    }
//                                }
//
//                            }
//                        }

                            session.setAttribute("menus", menus);

                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        req.setAttribute("checkLogin", forward);
        return forward;
    }

    public String logout() {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        try {
            session.removeAttribute("username");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return SUCCESS;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public DojoJSON getJsonDataGrid() {
        return jsonDataGrid;
    }

    public void setJsonDataGrid(DojoJSON jsonDataGrid) {
        this.jsonDataGrid = jsonDataGrid;
    }
}
