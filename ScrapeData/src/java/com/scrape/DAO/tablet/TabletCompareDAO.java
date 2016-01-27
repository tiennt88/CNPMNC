/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.DAO.tablet;

import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.client.form.Tablet;
import com.scrape.common.Constans;
import com.scrape.common.DojoJSON;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author KeyLove
 */
public class TabletCompareDAO extends BaseHibernateDAOMDB {

    private DojoJSON jsonDataGrid = new DojoJSON();
    private int startval;
    private int count;
    private String sort;
    private Tablet tablet = new Tablet();
    private Tablet tabletCheck = new Tablet();
    private String result;
    private Long id;

    public TabletCompareDAO() {
    }

    public String prepare() {
        try {
            if (getRequest().getParameter("id") != null) {
                id = Long.parseLong(getRequest().getParameter("id"));
                Session session = getSession();
                StringBuilder sql = new StringBuilder();
                sql.append(" Select a.id,item_code itemCode, link,web,type,brand,price,name,model, ");
                sql.append(" storage, ram,screen ,cpu ,b_camera backCamera, ");
                sql.append(" f_camera frontCamera, os,battery, ");
                sql.append(" sim, promotion");
                sql.append(" from TABLET_DATA a, TABLET_CONFIGURATION b ");
                sql.append(" WHERE a.ID = b.TABLET_DATA_ID and a.ID = ? ");

                Query query = session.createSQLQuery(sql.toString())
                    .addScalar("id",Hibernate.LONG)
                    .addScalar("link",Hibernate.STRING)
                    .addScalar("itemCode",Hibernate.STRING)
                    .addScalar("web",Hibernate.STRING)
                    .addScalar("type",Hibernate.STRING)
                    .addScalar("brand",Hibernate.STRING)
                    .addScalar("price",Hibernate.STRING)
                    .addScalar("name",Hibernate.STRING)
                    .addScalar("model",Hibernate.STRING)
                    .addScalar("storage",Hibernate.STRING)
                    .addScalar("ram",Hibernate.STRING)
                    .addScalar("screen",Hibernate.STRING)
                    .addScalar("cpu",Hibernate.STRING)
                    .addScalar("backCamera",Hibernate.STRING)
                    .addScalar("frontCamera",Hibernate.STRING)
                    .addScalar("os",Hibernate.STRING)
                    .addScalar("battery",Hibernate.STRING)
                    .addScalar("sim",Hibernate.STRING)
                    .addScalar("promotion",Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(Tablet.class));
                query.setParameter(0, id);
                List lst = query.list();
                if(lst != null && lst.size() > 0){
                    tablet = (Tablet) query.list().get(0);
                    getRequest().getSession().setAttribute("tablet", tablet);
                }
                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return SUCCESS;
    }

    public List<Tablet> takeTabletCompares() {
        List<Tablet> lst = null;
        String sortType = null;
        ArrayList param = new ArrayList();
        try {
            Session session = getSession();
            if (sort != null) {
                if (sort.indexOf('-') != -1) {
                    sortType = "asc";
                    sort = sort.substring(1);
                } else {
                    sortType = "desc";
                }
            }

            StringBuilder sql = new StringBuilder();
            sql.append(" select b.* from (select TABLET_DATA.ID,BRAND,model,storage,ram from TABLET_DATA , TABLET_CONFIGURATION  ");
            sql.append(" where TABLET_DATA.ID = TABLET_DATA_ID and WEB = '").append(rb.getString(Constans.WEB_ROOT)).append("') a join ");
            sql.append(" (select TABLET_DATA.ID,BRAND,model,storage,ram,link,WEB,TYPE,NAME,PRICE,PRICE_NUMBER,PROMOTION, ");
            sql.append("  cpu,screen,battery,b_camera backCamera,f_camera frontCamera,os,sim, TABLET_DATA.LAST_UPDATE lastUpdate ");
            sql.append(" from TABLET_DATA , TABLET_CONFIGURATION where TABLET_DATA.ID = TABLET_DATA_ID and WEB != '").append(rb.getString(Constans.WEB_ROOT)).append("') b  ");
            sql.append(" on 1=1 ");
            if (tablet != null) {
                if (tablet.getId() > 0) {
                    sql.append(" and a.ID = ? ");
                    param.add(tablet.getId());
                }
            }

            if (tabletCheck.getBrand() != null && "on".equals(tabletCheck.getBrand())) {
                sql.append(" and a.BRAND = b.BRAND  ");
            }
            if (tabletCheck.getModel() != null && "on".equals(tabletCheck.getModel())) {
                sql.append(" and a.MODEL = b.MODEL ");
            }
            if (tabletCheck.getStorage() != null && "on".equals(tabletCheck.getStorage())) {
                sql.append(" and isnull(a.STORAGE,'') = isnull(b.STORAGE,'')  ");
            }
            if (tabletCheck.getRam() != null && "on".equals(tabletCheck.getRam())) {
                sql.append(" and isnull(a.RAM,'') = isnull(b.RAM,'') ");
            }
            

            if (sort != null) {
                if (sortType != null && sortType.equals("asc")) {
                    sql.append(" Order by ").append(sort);
                } else if (sortType != null && sortType.equals("desc")) {
                    sql.append(" Order by ").append(sort).append(" desc ");
                }
            } else {
                sql.append(" Order by PRICE_NUMBER ");
            }

            Query query = session.createSQLQuery(sql.toString())
                    .addScalar("id", Hibernate.LONG)
                    .addScalar("link", Hibernate.STRING)
                    .addScalar("web", Hibernate.STRING)
                    .addScalar("type", Hibernate.STRING)
                    .addScalar("brand", Hibernate.STRING)
                    .addScalar("price", Hibernate.STRING)
                    .addScalar("name", Hibernate.STRING)
                    .addScalar("model", Hibernate.STRING)
                    .addScalar("storage", Hibernate.STRING)
                    .addScalar("ram", Hibernate.STRING)
                    .addScalar("screen", Hibernate.STRING)
                    .addScalar("cpu", Hibernate.STRING)
                    .addScalar("backCamera", Hibernate.STRING)
                    .addScalar("frontCamera", Hibernate.STRING)
                    .addScalar("os", Hibernate.STRING)
                    .addScalar("battery", Hibernate.STRING)
                    .addScalar("sim", Hibernate.STRING)
                    .addScalar("promotion", Hibernate.STRING)
                    .addScalar("lastUpdate", Hibernate.DATE)
                    .setResultTransformer(Transformers.aliasToBean(Tablet.class));

            for (int i = 0; i < param.size(); i++) {
                query.setParameter(i, param.get(i));
            }

            if (startval >= 0) {
                query.setFirstResult(startval);
            }
            if (count >= 0) {
                query.setMaxResults(count);
            }
            lst = query.list();

            System.out.println(lst.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lst;
    }

//    Lay so luong ban ghi tra ve grid de phan trang tung phan
    public void setMaxResult() {
        try {
            StringBuilder sql = new StringBuilder();
            ArrayList param = new ArrayList();
            sql.append(" select count(*) FROM ");
            sql.append(" (select TABLET_DATA.ID,BRAND,model,STORAGE,RAM from TABLET_DATA , TABLET_CONFIGURATION  ");
            sql.append(" where TABLET_DATA.ID = TABLET_DATA_ID and WEB = '").append(rb.getString(Constans.WEB_ROOT)).append("') a join ");
            sql.append(" (select TABLET_DATA.ID,BRAND,model,STORAGE,RAM from TABLET_DATA , TABLET_CONFIGURATION ");
            sql.append(" where TABLET_DATA.ID = TABLET_DATA_ID and WEB != '").append(rb.getString(Constans.WEB_ROOT)).append("') b ");
            sql.append(" on 1=1 ");

            if (tablet != null) {
                if (tablet.getId() > 0) {
                    sql.append(" and a.ID = ? ");
                    param.add(tablet.getId());
                }
            }

            if (tabletCheck.getBrand() != null && "on".equals(tabletCheck.getBrand())) {
                sql.append(" and a.BRAND =b.BRAND ");
            }
            if (tabletCheck.getModel() != null && "on".equals(tabletCheck.getModel())) {
                sql.append(" and a.model =b.model ");
            }
            if (tabletCheck.getStorage() != null && "on".equals(tabletCheck.getStorage())) {
                sql.append(" and isnull(a.STORAGE,'') = isnull(b.STORAGE,'') ");
            }
            if (tabletCheck.getRam() != null && "on".equals(tabletCheck.getRam())) {
                sql.append(" and isnull(a.RAM,'') = isnull(b.RAM,'') ");
            }

            SQLQuery query = getSession().createSQLQuery(sql.toString());
            for (int i = 0; i < param.size(); i++) {
                query.setParameter(i, param.get(i));
            }
            List lst = query.list();
            Integer countRecord = (Integer) lst.get(0);
            if (String.valueOf(count).length() >= 6) {
                count = countRecord.intValue();
            }
            jsonDataGrid.setTotalRows(countRecord.intValue());
        } catch (Exception ex) {
            jsonDataGrid.setTotalRows(0);
            ex.printStackTrace();
        }
    }

    public String onSearch() {
        try {
            if(getRequest().getSession().getAttribute("tablet") !=null){
                tablet = (Tablet) getRequest().getSession().getAttribute("tablet");
                getRequest().getSession().removeAttribute("tablet");
                tabletCheck.setBrand("on");
                tabletCheck.setModel("on");
            }
            if (tablet.getId() > 0) {
                setMaxResult();
                List<Tablet> lstResult = new ArrayList<Tablet>();
                lstResult = takeTabletCompares();
                jsonDataGrid.setItems(lstResult);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "jsonDataGrid";
    }

    public String beforeSearch() {
        try {
            if (getRequest().getParameter("itemCode") != null) {
                tablet.setItemCode(getRequest().getParameter("itemCode"));
                Session session = getSession();
                StringBuilder sql = new StringBuilder();
                sql.append(" Select a.id, link,web,type,brand,price,name,model, ");
                sql.append(" storage, ram,screen ,cpu ,b_camera backCamera,");
                sql.append(" f_camera frontCamera, os,battery, ");
                sql.append(" sim, promotion");
                sql.append(" from TABLET_DATA a, TABLET_CONFIGURATION b ");
                sql.append(" WHERE a.ID = b.TABLET_DATA_ID and b.item_code = ? ");

                Query query = session.createSQLQuery(sql.toString())
                    .addScalar("id",Hibernate.LONG)
                    .addScalar("link",Hibernate.STRING)
                    .addScalar("web",Hibernate.STRING)
                    .addScalar("type",Hibernate.STRING)
                    .addScalar("brand",Hibernate.STRING)
                    .addScalar("price",Hibernate.STRING)
                    .addScalar("name",Hibernate.STRING)
                    .addScalar("model",Hibernate.STRING)
                    .addScalar("storage",Hibernate.STRING)
                    .addScalar("ram",Hibernate.STRING)
                    .addScalar("screen",Hibernate.STRING)
                    .addScalar("cpu",Hibernate.STRING)
                    .addScalar("backCamera",Hibernate.STRING)
                    .addScalar("frontCamera",Hibernate.STRING)
                    .addScalar("os",Hibernate.STRING)
                    .addScalar("battery",Hibernate.STRING)
                    .addScalar("sim",Hibernate.STRING)
                    .addScalar("promotion",Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(Tablet.class));
                query.setParameter(0, tablet.getItemCode());
                List lst = query.list();
                if(lst != null && lst.size() > 0){
                    tablet = (Tablet) query.list().get(0);
                } else {
                    result = "Không có mã sản phẩm tương ứng của Tablet!";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            result = ex.getMessage();
        }
        return "success";
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Tablet getTablet() {
        return tablet;
    }

    public void setTablet(Tablet tablet) {
        this.tablet = tablet;
    }

    public Tablet getTabletCheck() {
        return tabletCheck;
    }

    public void setTabletCheck(Tablet tabletCheck) {
        this.tabletCheck = tabletCheck;
    }


}
