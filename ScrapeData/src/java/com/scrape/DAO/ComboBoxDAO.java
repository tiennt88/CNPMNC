/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.DAO;

import com.scrape.client.form.ComboBox;
import com.scrape.client.form.ComboBoxID;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author KeyLove
 */
public class ComboBoxDAO extends BaseHibernateDAOMDB{

    public ComboBoxDAO() {
    }

    public List<ComboBox> combobox (String type, String usefor){
        List<ComboBox> lst = new ArrayList<ComboBox>();
        StringBuilder sql = new StringBuilder();
        sql.append("select value, name from config where isactive = 'Y' and type = ? and usefor = ? ");
        if("Brand".equals(type) || "Web".equals(type)){
            sql.append(" order by name");
        }else{
            sql.append(" order by priority");
        }
        
        try{
            Query query = getSession().createSQLQuery(sql.toString())
                    .addScalar("value",Hibernate.STRING)
                    .addScalar("name",Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(ComboBox.class));
            query.setParameter(0, type);
            query.setParameter(1, usefor);
            lst = query.list();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return lst;
    }

    public List<ComboBox> takeWebNotRoot (String root){
        List<ComboBox> lst = new ArrayList<ComboBox>();
        String sql = "select value, name from config where isactive = 'Y' and type = 'Web' and value != ? and value != '' order by name";
        try{
            Query query = getSession().createSQLQuery(sql)
                    .addScalar("value",Hibernate.STRING)
                    .addScalar("name",Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(ComboBox.class));
            query.setParameter(0, root);
            lst = query.list();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return lst;
    }

    public List<ComboBoxID> comboboxID (String table, String property){
        List<ComboBoxID> lst = new ArrayList<ComboBoxID>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select id, ").append(property).append(" name from ").append(table).append(" where isactive = 'Y' order by name ");

        try{
            Query query = getSession().createSQLQuery(sql.toString())
                    .addScalar("id",Hibernate.LONG)
                    .addScalar("name",Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(ComboBoxID.class));
            lst = query.list();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return lst;
    }
}
