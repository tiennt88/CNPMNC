/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.selenium.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author KeyLove
 */
public class DatabaseDAO {

    public Connection conn;
    
    public void open() throws Exception {
        if ((this.conn != null) && (!this.conn.isClosed())) {
            return;
        }
        this.conn = getConnection();
    }

    public Connection getConnection(){
            try {
                  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                  String url = "jdbc:sqlserver://localhost;user=sa;password=kimbaobao;database=Freelancer";
                  conn = DriverManager.getConnection(url);
            } catch (Exception e) {
                  e.printStackTrace();
            }
            return conn;
      }

     public static void closeObject(CallableStatement obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeObject(Statement obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeObject(ResultSet obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void closeObject(Connection obj) {
        try {
            if ((obj != null)
                    && (!obj.isClosed())) {
                if (!obj.getAutoCommit()) {
                    obj.rollback();
                }
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void close(){
        if (this.conn != null) {
            closeObject(this.conn);
            this.conn = null;
        }
    }
    
    public static void main(String[] args) {
        DatabaseDAO test = new DatabaseDAO();
        test.getConnection();
    }
}
