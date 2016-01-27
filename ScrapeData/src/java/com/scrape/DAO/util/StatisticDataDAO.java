/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.DAO.util;

import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.client.form.StatisticData;
import com.scrape.common.DojoJSON;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

/**
 *
 * @author KeyLove
 */
public class StatisticDataDAO extends BaseHibernateDAOMDB {

    private DojoJSON jsonDataGrid = new DojoJSON();
    private int startval;
    private int count;
    private String sort;
    private StatisticData statistic = new StatisticData();
    ArrayList param = new ArrayList();

    public StatisticDataDAO() {
    }

    public String prepare() {
        return SUCCESS;
    }

    public String takeSQL() {
        param.clear();

        String sortType = null;
        if (sort != null) {
            if (sort.indexOf('-') != -1) {
                sortType = "asc";
                sort = sort.substring(1);
            } else {
                sortType = "desc";
            }
        }

        StringBuilder sql = new StringBuilder();

        sql.append(" select date ,count(*) qty,type");

        if (statistic != null && "on".equals(statistic.getWeb())) {
            sql.append(" ,web ");
        }
        if (statistic != null && "on".equals(statistic.getBrand())) {
            sql.append(" ,brand ");
        }
        sql.append("  from ( ");

        sql.append(" select type, web, brand,CAST(getdate() as DATE) date from MOBILE_DATA where CAST(last_update as DATE) = CAST(getdate() as DATE) ");
        sql.append(" union all ");
        sql.append(" select type, web, brand,CAST(getdate() as DATE) date from TABLET_DATA where CAST(last_update as DATE) = CAST(getdate() as DATE) ");
        sql.append(" union all ");
        sql.append(" select type, web, brand,CAST(getdate() as DATE) date from LAPTOP_DATA where CAST(last_update as DATE) = CAST(getdate() as DATE) ");
        sql.append(" ) z ");
        sql.append(" where 1=1 ");
        sql.append(" group by date,type ");
        if (statistic != null && "on".equals(statistic.getWeb())) {
            sql.append(" ,web ");
        }
        if (statistic != null && "on".equals(statistic.getBrand())) {
            sql.append(" ,brand ");
        }

        if (sort != null) {
            if (sortType != null && sortType.equals("asc")) {
                sql.append(" Order by ").append(sort);
            } else if (sortType != null && sortType.equals("desc")) {
                sql.append(" Order by ").append(sort).append(" desc ");
            }
        } else {
            sql.append(" Order by type ");
        }

        return sql.toString();
    }

    public List<StatisticData> takeStatistic() {
        List<StatisticData> lst = null;
        try {
            String sql = takeSQL();
            SQLQuery query = getSession().createSQLQuery(sql.toString());
            query.addScalar("type", Hibernate.STRING);
            if (statistic != null && "on".equals(statistic.getWeb())) {
                query.addScalar("web", Hibernate.STRING);
            }
            if (statistic != null && "on".equals(statistic.getBrand())) {
                query.addScalar("brand", Hibernate.STRING);
            }
            query.addScalar("qty", Hibernate.LONG);
            query.addScalar("date", Hibernate.DATE);
            query.setResultTransformer(Transformers.aliasToBean(StatisticData.class));

            for (int i = 0; i < param.size(); i++) {
                query.setParameter(i, param.get(i));
            }

//            if (startval >= 0) {
//                query.setFirstResult(startval);
//            }
//            if (count >= 0) {
//                query.setMaxResults(count);
//            }
            
            lst = query.list();
            System.out.println(lst.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lst;
    }


    public String onSearch() {
        try {
//            UserToken userToken = (UserToken) getRequest().getSession().getAttribute("userToken");
            List<StatisticData> lstResult = new ArrayList<StatisticData>();
            jsonDataGrid.setTotalRows(lstResult.size());
            lstResult = takeStatistic();

            jsonDataGrid.setItems(lstResult);

        } catch (Exception ex) {
            ex.printStackTrace();
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

    public StatisticData getStatistic() {
        return statistic;
    }

    public void setStatistic(StatisticData statistic) {
        this.statistic = statistic;
    }
}
