/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.selenium.DAO;

import com.selenium.BO.Laptop;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author KeyLove
 */
public class LaptopDAO extends DatasourceC3p0{
    private Connection conn;

    public LaptopDAO() {
    }

    public LaptopDAO(Connection conn) {
        this.conn = conn;
    }

    public void saveLaptop(Laptop item) {
        PreparedStatement pre = null;
        int i = 1;
        try {
            pre = conn.prepareStatement("INSERT INTO LAPTOP_CONFIGURATION(LAPTOP_DATA_ID,CPU_TEXT,STORAGE_TEXT,RAM_TEXT,VGA_TEXT,SCREEN_TEXT,OS_TEXT,TOUCHSCREEN_TEXT,DVD_TEXT,BATTERY_TEXT) "
                    + " VALUES(?,?,?,?,?,?,"
                    + " ?,?,?,?) ");
            pre.setLong(i++, item.getLaptopDataId());
            pre.setString(i++, item.getCpu());
            pre.setString(i++, item.getHdd());
            pre.setString(i++, item.getRam());
            pre.setString(i++, item.getVga());
            pre.setString(i++, item.getScreen());
            pre.setString(i++, item.getOs());
            pre.setString(i++, item.getTouchScreen());
            pre.setString(i++, item.getDvd());
            pre.setString(i++, item.getBattery());
            pre.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(pre);
        }

    }

}
