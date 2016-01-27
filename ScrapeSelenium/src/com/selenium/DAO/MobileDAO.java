/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.selenium.DAO;

import com.selenium.BO.Mobile;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author KeyLove
 */
public class MobileDAO extends DatasourceC3p0{

    private Connection conn;

    public MobileDAO() {
    }

    public MobileDAO(Connection conn) {
        this.conn = conn;
    }

    public void saveMobile(Mobile item) {
        PreparedStatement pre = null;
        int i = 1;
        try {
            pre = conn.prepareStatement("INSERT INTO MOBILE_CONFIGURATION(MOBILE_DATA_ID,SCREEN_TEXT,CPU_TEXT,RAM_TEXT,OS_TEXT,B_CAMERA_TEXT,F_CAMERA_TEXT,STORAGE_TEXT,BATTERY_TEXT,SIM_TEXT) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?) ");
           
            pre.setLong(i++, item.getMobileDataId());
            pre.setString(i++, item.getScreen());
            pre.setString(i++, item.getCpu());
            pre.setString(i++, item.getRam());
            pre.setString(i++, item.getOs());
            pre.setString(i++, item.getBackCamera());
            pre.setString(i++, item.getFrontCamera());
            pre.setString(i++, item.getStorage());
            pre.setString(i++, item.getBattery());
            pre.setString(i++, item.getSim());
            pre.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(pre);
        }

    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

}
