/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.process;

import com.scrape.DAO.BaseHibernateDAOMDB;
import java.sql.CallableStatement;
import java.sql.Connection;
import org.hibernate.SQLQuery;

/**
 *
 * @author KeyLove
 */
public class LaptopAnalysis extends BaseHibernateDAOMDB{
    
    public int runLaptopAnalysis(String itemCode){
        CallableStatement cs2 = null;
        Integer records = 0;
        try {
            Connection conn = getSession().connection();
            //call store insight
            String sql = "{call LAPTOP_ANALYSIS_PRO(?,?)}";
            cs2 = conn.prepareCall(sql);
            cs2.setString(1, itemCode);
            cs2.registerOutParameter(2, java.sql.Types.INTEGER);
            cs2.execute();
            records = cs2.getInt(2);
            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return records;
    }
    
    public static void main(String[] args) {
        try {
            SQLQuery query = getSession().createSQLQuery("EXEC LAPTOP_ANALYSIS '' ");
            query.executeUpdate();
            getSession().flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
