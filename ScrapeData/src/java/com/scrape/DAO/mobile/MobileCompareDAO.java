/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.DAO.mobile;

import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.client.form.Mobile;
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
public class MobileCompareDAO extends BaseHibernateDAOMDB {

    private DojoJSON jsonDataGrid = new DojoJSON();
    private int startval;
    private int count;
    private String sort;
    private Mobile mobile = new Mobile();
    private Mobile mobileCheck = new Mobile();
    private String result;
    private Long id;

    public MobileCompareDAO() {
    }

    public String prepare() {
        try {
            if (getRequest().getParameter("id") != null) {
                id = Long.parseLong(getRequest().getParameter("id"));
                Session session = getSession();
                StringBuilder sql = new StringBuilder();
                sql.append(" Select a.id,item_code itemCode, link,web,type,brand,price,name,model, ");
                sql.append(" storage, ram,screen ,cpu ,b_camera backCamera, ");
                sql.append(" f_camera frontCamera, os,battery, color,");
                sql.append(" sim_text sim, promotion");
                sql.append(" from MOBILE_DATA a, MOBILE_CONFIGURATION b ");
                sql.append(" WHERE a.ID = b.MOBILE_DATA_ID and a.ID = ? ");

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
                    .addScalar("color",Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(Mobile.class));
                query.setParameter(0, id);
                List lst = query.list();
                if(lst != null && lst.size() > 0){
                    mobile = (Mobile) query.list().get(0);
                    getRequest().getSession().setAttribute("mobile", mobile);
                }
                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return SUCCESS;
    }

    public List<Mobile> takeMobileCompares() {
        List<Mobile> lst = null;
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
            sql.append(" select b.* from (select MOBILE_DATA.ID,BRAND,model,storage,ram,color from MOBILE_DATA , MOBILE_CONFIGURATION  ");
            sql.append(" where MOBILE_DATA.ID = MOBILE_DATA_ID and WEB = '").append(rb.getString(Constans.WEB_ROOT)).append("') a join ");
            sql.append(" (select MOBILE_DATA.ID,BRAND,model,storage,ram,color,link,WEB,TYPE,NAME,PRICE,PRICE_NUMBER,PROMOTION, ");
            sql.append("  cpu,screen,battery,b_camera backCamera,f_camera frontCamera,os, sim, MOBILE_DATA.LAST_UPDATE lastUpdate ");
            sql.append(" from MOBILE_DATA , MOBILE_CONFIGURATION where MOBILE_DATA.ID = MOBILE_DATA_ID and WEB != '").append(rb.getString(Constans.WEB_ROOT)).append("') b  ");
            sql.append(" on 1=1 ");
            if (mobile != null) {
                if (mobile.getId() > 0) {
                    sql.append(" and a.ID = ? ");
                    param.add(mobile.getId());
                }
            }

            if (mobileCheck.getBrand() != null && "on".equals(mobileCheck.getBrand())) {
                sql.append(" and a.BRAND = b.BRAND  ");
            }
            if (mobileCheck.getModel() != null && "on".equals(mobileCheck.getModel())) {
                sql.append(" and a.MODEL = b.MODEL ");
            }
            if (mobileCheck.getStorage() != null && "on".equals(mobileCheck.getStorage())) {
                sql.append(" and isnull(a.STORAGE,'') = isnull(b.STORAGE,'')  ");
            }
            if (mobileCheck.getRam() != null && "on".equals(mobileCheck.getRam())) {
                sql.append(" and isnull(a.RAM,'') = isnull(b.RAM,'') ");
            }
            if (mobileCheck.getColor() != null && "on".equals(mobileCheck.getColor())) {
                sql.append(" and isnull(a.COLOR,'') = isnull(b.COLOR,'') ");
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
                    .addScalar("color", Hibernate.STRING)
                    .addScalar("promotion", Hibernate.STRING)
                    .addScalar("lastUpdate", Hibernate.DATE)
                    .setResultTransformer(Transformers.aliasToBean(Mobile.class));

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
            sql.append(" (select MOBILE_DATA.ID,BRAND,model,STORAGE,RAM,color from MOBILE_DATA , MOBILE_CONFIGURATION  ");
            sql.append(" where MOBILE_DATA.ID = MOBILE_DATA_ID and WEB = '").append(rb.getString(Constans.WEB_ROOT)).append("') a join ");
            sql.append(" (select MOBILE_DATA.ID,BRAND,model,STORAGE,RAM,color from MOBILE_DATA , MOBILE_CONFIGURATION ");
            sql.append(" where MOBILE_DATA.ID = MOBILE_DATA_ID and WEB != '").append(rb.getString(Constans.WEB_ROOT)).append("') b ");
            sql.append(" on 1=1 ");

            if (mobile != null) {
                if (mobile.getId() > 0) {
                    sql.append(" and a.ID = ? ");
                    param.add(mobile.getId());
                }
            }

            if (mobileCheck.getBrand() != null && "on".equals(mobileCheck.getBrand())) {
                sql.append(" and a.BRAND =b.BRAND ");
            }
            if (mobileCheck.getModel() != null && "on".equals(mobileCheck.getModel())) {
                sql.append(" and a.model =b.model ");
            }
            if (mobileCheck.getStorage() != null && "on".equals(mobileCheck.getStorage())) {
                sql.append(" and isnull(a.STORAGE,'') = isnull(b.STORAGE,'') ");
            }
            if (mobileCheck.getRam() != null && "on".equals(mobileCheck.getRam())) {
                sql.append(" isnull(a.RAM,'') = isnull(b.RAM,'') ");
            }
            if (mobileCheck.getColor() != null && "on".equals(mobileCheck.getColor())) {
                sql.append(" and isnull(a.COLOR,'') = isnull(b.COLOR,'') ");
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
            if(getRequest().getSession().getAttribute("mobile") !=null){
                mobile = (Mobile) getRequest().getSession().getAttribute("mobile");
                getRequest().getSession().removeAttribute("mobile");
                mobileCheck.setBrand("on");
                mobileCheck.setModel("on");
            }
            if (mobile.getId() > 0) {
                setMaxResult();
                List<Mobile> lstResult = new ArrayList<Mobile>();
                lstResult = takeMobileCompares();
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
                mobile.setItemCode(getRequest().getParameter("itemCode"));
                Session session = getSession();
                StringBuilder sql = new StringBuilder();
                sql.append(" Select a.id, link,web,type,brand,price,name,model, ");
                sql.append(" storage, ram,screen ,cpu ,b_camera backCamera,");
                sql.append(" f_camera frontCamera, os,battery, ");
                sql.append(" sim, promotion, color");
                sql.append(" from MOBILE_DATA a, MOBILE_CONFIGURATION b ");
                sql.append(" WHERE a.ID = b.MOBILE_DATA_ID and b.item_code = ? ");

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
                    .addScalar("color",Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(Mobile.class));
                query.setParameter(0, mobile.getItemCode());
                List lst = query.list();
                if(lst != null && lst.size() > 0){
                    mobile = (Mobile) query.list().get(0);
                } else {
                    result = "Không có mã sản phẩm tương ứng của Mobile!";
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

    public Mobile getMobile() {
        return mobile;
    }

    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
    }

    public Mobile getMobileCheck() {
        return mobileCheck;
    }

    public void setMobileCheck(Mobile mobileCheck) {
        this.mobileCheck = mobileCheck;
    }


}
