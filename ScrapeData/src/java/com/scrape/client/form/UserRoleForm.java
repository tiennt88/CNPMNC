/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.client.form;

/**
 *
 * @author KeyLove
 */
public class UserRoleForm {
    private Long id;
    private Long userID;
    private Long roleID;
    private String userName;
    private String roleName;
    private String isactive;
    private String roleUserType;

    public UserRoleForm() {
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

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleUserType() {
        return roleUserType;
    }

    public void setRoleUserType(String roleUserType) {
        this.roleUserType = roleUserType;
    }

}
