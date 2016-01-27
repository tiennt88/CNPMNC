/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.selenium.DAO;

import com.selenium.BO.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KeyLove
 */
public class DataDAO extends DatasourceC3p0 {

    private Connection conn;

    public DataDAO(Connection conn) {
        this.conn = conn;
    }

    public DataDAO() {
        if(conn==null){
            try {
                conn = getConnection();
            } catch (SQLException ex) {
                Logger.getLogger(DataDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    

    public void saveOrUpDateModel(Data item) {
        PreparedStatement pre = null;
        String sql = " MERGE " + item.getType() + "_DATA AS T "
                + " USING (SELECT ? as WEB, ? as TYPE, ? as BRAND, ? as NAME, ? as LINK , ? as PRICE, ? as PROMOTION ,? as DESCRIPTION where DBO.RemoveNonNumericCharacters(?) > 0) AS S "
                + " ON (T.web = S.WEB and T.TYPE = S.TYPE AND T.BRAND = S.BRAND AND T.NAME = S.NAME) "
                + " WHEN NOT MATCHED BY TARGET "
                + " THEN INSERT(WEB,TYPE,BRAND, NAME, LINK, PRICE, PROMOTION ,DESCRIPTION, PRICE_NUMBER) "
                + " VALUES(S.WEB,S.TYPE,S.BRAND, S.NAME, S.LINK, S.PRICE, S.PROMOTION , S.DESCRIPTION, DBO.RemoveNonNumericCharacters(S.PRICE)) "
//                + " WHEN MATCHED AND (T.PRICE != S.PRICE OR T.PROMOTION != S.PROMOTION)"
                + " WHEN MATCHED "
                + " THEN UPDATE SET T.LINK = S.LINK ,T.PRICE = S.PRICE,T.PRICE_NUMBER = DBO.RemoveNonNumericCharacters(S.PRICE), T.PROMOTION = S.PROMOTION ,T.DESCRIPTION = S.DESCRIPTION,LAST_UPDATE = GETDATE() ;";
//        System.out.println(sql);
        
        try {
            if(item.getName() != null && !"".equals(item.getName())){
                pre = conn.prepareStatement(sql);
                pre.setString(1, item.getWeb());
                pre.setString(2, item.getType());
                pre.setString(3, item.getBrand());
                pre.setString(4, item.getName());
                pre.setString(5, item.getLink());
                pre.setString(6, item.getPrice());
                pre.setString(7, item.getPromotion());
                pre.setString(8, item.getDescription());
                pre.setString(9, item.getPrice());
                pre.execute();
            }
        } catch (Exception ex) {
            System.out.println("ERROR SaveOrUpdate ITEM:"+item.getLink()+','+item.getPrice());
            ex.printStackTrace();
        } finally {
            closeObject(pre);
        }
    }

    public PreparedStatement getListNew(String web, String type, String brand) {
        PreparedStatement pre = null;
        StringBuilder sql = new StringBuilder();
        sql.append(" select a.ID,a.LINK, a.TYPE from ").append(type).append("_DATA a ");
        sql.append(" where isactive = 'Y' and a.LAST_UPDATE > GETDATE() - 1 ");//
        sql.append(" and a.WEB = ? ");
        if (type != null) {
            sql.append(" and a.TYPE = ? ");
        }
        if (brand != null) {
            sql.append(" and a.BRAND = ? ");
        }
        sql.append(" and NOT EXISTS (SELECT 1 FROM ").append(type).append("_CONFIGURATION WHERE ").append(type).append("_DATA_ID = a.ID) ");
        System.out.println(sql.toString());
        try {
            pre = conn.prepareStatement(sql.toString());
            int i = 1;
            pre.setString(i++, web);
            if (type != null) {
                pre.setString(i++, type);
            }
            if (brand != null) {
                pre.setString(i++, brand);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pre;
    }

    public void updatePrice(String web, String type, String brand) {
        StringBuilder sql = new StringBuilder();
        sql.append(" MERGE ").append(type).append("_PRICE AS T ");
        sql.append(" USING (select * from ").append(type).append("_DATA where CAST(LAST_UPDATE AS DATE) = cast(GETDATE() as DATE) and DBO.RemoveNonNumericCharacters(PRICE) > 0 ");
        if (web != null) {
            sql.append(" AND WEB = ? ");
        }
        if (brand != null) {
            sql.append(" AND BRAND = ? ");
        }
        sql.append(" ) AS S");
        sql.append(" ON (T.").append(type).append("_DATA_ID = S.ID and cast(T.LAST_UPDATE As Date) = cast(S.LAST_UPDATE As Date) ) ");
        sql.append(" WHEN NOT MATCHED BY TARGET ");
        sql.append(" THEN INSERT(").append(type).append("_DATA_ID,PRICE,PRICE_NUMBER,CREATE_DATE,LAST_UPDATE) ");
        sql.append(" VALUES(S.ID,S.PRICE,S.PRICE_NUMBER,S.LAST_UPDATE,S.LAST_UPDATE) ");
        sql.append(" WHEN MATCHED AND (T.PRICE != S.PRICE) AND cast(T.LAST_UPDATE as date)  = cast(getdate() as date)");
        sql.append(" THEN UPDATE SET T.PRICE = S.PRICE,T.PRICE_NUMBER = S.PRICE_NUMBER, T.LAST_UPDATE = S.LAST_UPDATE ;");

        PreparedStatement pre = null;
//        System.out.println(sql.toString());

        try {
            pre = conn.prepareStatement(sql.toString());
            int i = 1;
            if (web != null) {
                pre.setString(i++, web);
            }
            if (brand != null) {
                pre.setString(i++, brand);
            }
            pre.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(pre);
        }
    }

    public void updatePrice(String web, String type, String brand, String date) {//'yyyy/mm/dd'
        StringBuilder sql = new StringBuilder();
        sql.append(" MERGE ").append(type).append("_PRICE AS T ");
        sql.append(" USING (select * from ").append(type).append("_DATA where CAST(LAST_UPDATE AS DATE) = '").append(date).append("' and DBO.RemoveNonNumericCharacters(PRICE) > 0 ");
        if (web != null) {
            sql.append(" AND WEB = ? ");
        }
        if (brand != null) {
            sql.append(" AND BRAND = ? ");
        }
        sql.append(" ) AS S");
        sql.append(" ON (T.").append(type).append("_DATA_ID = S.ID and cast(T.LAST_UPDATE As Date) = cast(S.LAST_UPDATE As Date) ) ");
        sql.append(" WHEN NOT MATCHED BY TARGET ");
        sql.append(" THEN INSERT(").append(type).append("_DATA_ID,PRICE,PRICE_NUMBER,CREATE_DATE,LAST_UPDATE) ");
        sql.append(" VALUES(S.ID,S.PRICE,S.PRICE_NUMBER,S.LAST_UPDATE,S.LAST_UPDATE) ");
        sql.append(" WHEN MATCHED AND (T.PRICE != S.PRICE) ");
        sql.append(" THEN UPDATE SET T.PRICE = S.PRICE,T.PRICE_NUMBER = S.PRICE_NUMBER, T.LAST_UPDATE = S.LAST_UPDATE ;");

        PreparedStatement pre = null;
//        System.out.println(sql.toString());

        try {
            pre = conn.prepareStatement(sql.toString());
            int i = 1;
            if (web != null) {
                pre.setString(i++, web);
            }
            if (brand != null) {
                pre.setString(i++, brand);
            }
            pre.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(pre);
        }
    }

    public PreparedStatement getListURL(String web, String type, String brand) {
        PreparedStatement pre = null;
        ArrayList param = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append(" select type,web,brand,link from web_data ");
        sql.append(" where isactive = 'Y' ");
        if (web != null && !"All".equals(web)) {
            sql.append(" and web = ? ");
            param.add(web);
        }
        if (type != null && !"All".equals(type)) {
            sql.append(" and TYPE = ? ");
            param.add(type);
        }
        if (brand != null && !"All".equals(brand)) {
            sql.append(" and brand = ? ");
            param.add(brand);
        }
        System.out.println(sql.toString());
        try {
            pre = conn.prepareStatement(sql.toString());
            for (int i = 0; i < param.size(); i++) {
                pre.setObject(i+1, param.get(i));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pre;
    }

    public void disableLink(String web,String type, String brand){
        PreparedStatement pre = null;
        try {
            pre = conn.prepareStatement("update web_Data set isactive = 'N' where web = ? and type = ? and brand = ?");
            pre.setString(1, web);
            pre.setString(2, type);
            pre.setString(3, brand);
            pre.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(pre);
        }
    }

    public void saveURL(String web, String type, String brand, String link) {
        PreparedStatement pre = null;
        int i = 1;
        try {
            pre = conn.prepareStatement("INSERT INTO web_data(web, type, brand, link) "
                    + " VALUES(?,?,?,?) ");
            pre.setString(i++, web);
            pre.setString(i++, type);
            pre.setString(i++, brand);
            pre.setString(i++, link);
            pre.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(pre);
        }

    }

    public void configurationByHand(String type) {
        StringBuilder sql = new StringBuilder();
        sql.append(" MERGE ").append(type).append("_CONFIGURATION AS T ");
        sql.append(" USING ").append(type).append("_DATA  AS S");
        sql.append(" ON (T.").append(type).append("_DATA_ID = S.ID ");
        sql.append(" WHEN NOT MATCHED BY TARGET ");
        sql.append(" THEN INSERT(").append(type).append("_DATA_ID) ");
        sql.append(" VALUES(S.ID) ;");

        PreparedStatement pre = null;
//        System.out.println(sql.toString());

        try {
            pre = conn.prepareStatement(sql.toString());
            pre.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(pre);
        }
    }


    public static void main(String[] args) {
        DataDAO a =new DataDAO();
        a.updatePrice(null, "Mobile", null,"2015-08-17");
        a.updatePrice(null, "Tablet", null,"2015-08-17");
        a.updatePrice(null, "Laptop", null,"2015-08-17");
    }
}
