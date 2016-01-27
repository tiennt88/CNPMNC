/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.client.form;

/**
 *
 * @author KeyLove
 */
public class RoleMenuForm {
    private Long id;
    private Long menuID;
    private Long roleID;
    private String menuName;
    private String roleName;
    private String isactive;
    private String roleMenuType;


    public RoleMenuForm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public Long getMenuID() {
        return menuID;
    }

    public void setMenuID(Long menuID) {
        this.menuID = menuID;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleMenuType() {
        return roleMenuType;
    }

    public void setRoleMenuType(String roleMenuType) {
        this.roleMenuType = roleMenuType;
    }

}
