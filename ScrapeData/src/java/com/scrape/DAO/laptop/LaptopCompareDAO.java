/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.DAO.laptop;

import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.client.form.Laptop;
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
public class LaptopCompareDAO extends BaseHibernateDAOMDB {

    private DojoJSON jsonDataGrid = new DojoJSON();
    private int startval;
    private int count;
    private String sort;
    private Laptop laptop = new Laptop();
    private Laptop laptopCheck = new Laptop();
    private String result;
    private Long id;

    public LaptopCompareDAO() {
    }

    public String prepare() {
        try {
            if (getRequest().getParameter("id") != null) {
                id = Long.parseLong(getRequest().getParameter("id"));
                Session session = getSession();
                StringBuilder sql = new StringBuilder();
                sql.append(" Select a.id,item_code itemCode, link,web,type,brand,price,name,model, ");
                sql.append(" chip,SPEED, storage,HDD_TYPE hddType,  ");
                sql.append(" ram, vga,screen, ");
                sql.append(" touchscreen,os, dvd,");
                sql.append(" battery,PROMOTION ");
                sql.append(" from LAPTOP_DATA a, LAPTOP_CONFIGURATION b ");
                sql.append(" WHERE a.ID = b.LAPTOP_DATA_ID and a.ID = ? ");

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
                    .addScalar("chip",Hibernate.STRING)
                    .addScalar("speed",Hibernate.STRING)
                    .addScalar("storage",Hibernate.STRING)
                    .addScalar("hddType",Hibernate.STRING)
                    .addScalar("ram",Hibernate.STRING)
                    .addScalar("vga",Hibernate.STRING)
                    .addScalar("screen",Hibernate.STRING)
                    .addScalar("touchscreen",Hibernate.STRING)
                    .addScalar("os",Hibernate.STRING)
                    .addScalar("dvd",Hibernate.STRING)
                    .addScalar("battery",Hibernate.STRING)
                    .addScalar("promotion",Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(Laptop.class));
                query.setParameter(0, id);
                List lst = query.list();
                if(lst != null && lst.size() > 0){
                    laptop = (Laptop) query.list().get(0);
                    getRequest().getSession().setAttribute("laptop", laptop);
                }
                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return SUCCESS;
    }

    public List<Laptop> takeLaptopCompares() {
        List<Laptop> lst = null;
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
            sql.append(" select b.* from (select LAPTOP_DATA.ID,BRAND,model,storage,ram, ");
            sql.append(" screen, chip, speed, hdd_type hddType, vga,os,  ");
            sql.append(" dvd, touchscreen ");
            sql.append(" from LAPTOP_DATA , LAPTOP_CONFIGURATION ");
            sql.append(" where LAPTOP_DATA.ID = LAPTOP_DATA_ID and WEB = '").append(rb.getString(Constans.WEB_ROOT)).append("') a join ");
            sql.append(" (select LAPTOP_DATA.ID,WEB,TYPE,BRAND,model,storage, hdd_type hddType,ram, vga,link,NAME,PRICE,PRICE_NUMBER,PROMOTION, ");
            sql.append(" chip,speed,screen,battery,os,dvd, touchscreen ,LAPTOP_DATA.LAST_UPDATE lastUpdate ");
            sql.append(" from LAPTOP_DATA , LAPTOP_CONFIGURATION where LAPTOP_DATA.ID = LAPTOP_DATA_ID and WEB != '").append(rb.getString(Constans.WEB_ROOT)).append("') b  ");
            sql.append(" on 1=1 ");
            if (laptop != null) {
                if (laptop.getId() > 0) {
                    sql.append(" and a.ID = ? ");
                    param.add(laptop.getId());
                }
            }

            if (laptopCheck.getBrand() != null && "on".equals(laptopCheck.getBrand())) {
                sql.append(" and a.BRAND = b.BRAND  ");
            }
            if (laptopCheck.getModel() != null && "on".equals(laptopCheck.getModel())) {
                sql.append(" and a.MODEL = b.MODEL ");
            }
            if (laptopCheck.getScreen() != null && "on".equals(laptopCheck.getScreen())) {
                sql.append(" and isnull(a.screen,'') = isnull(b.screen,'')  ");
            }
            if (laptopCheck.getChip() != null && "on".equals(laptopCheck.getChip())) {
                sql.append(" and isnull(a.chip,'') = isnull(b.chip,'')  ");
            }
            if (laptopCheck.getStorage() != null && "on".equals(laptopCheck.getStorage())) {
                sql.append(" and isnull(a.STORAGE,'') = isnull(b.STORAGE,'')  ");
            }
            if (laptopCheck.getStorage() != null && "on".equals(laptopCheck.getStorage())) {
                sql.append(" and isnull(a.hddType,'') = isnull(b.hddType,'')  ");
            }
            if (laptopCheck.getRam() != null && "on".equals(laptopCheck.getRam())) {
                sql.append(" and isnull(a.RAM,'') = isnull(b.RAM,'') ");
            }
            if (laptopCheck.getVga() != null && "on".equals(laptopCheck.getVga())) {
                sql.append(" and isnull(a.vga,'') = isnull(b.vga,'') ");
            }
            if (laptopCheck.getOs() != null && "on".equals(laptopCheck.getOs())) {
                sql.append(" and isnull(a.os,'') = isnull(b.os,'') ");
            }
            if (laptopCheck.getTouchscreen() != null && "on".equals(laptopCheck.getTouchscreen())) {
                sql.append(" and isnull(a.touchscreen,'') = isnull(b.touchscreen,'') ");
            }
            if (laptopCheck.getDvd() != null && "on".equals(laptopCheck.getDvd())) {
                sql.append(" and isnull(a.dvd,'') = isnull(b.dvd,'') ");
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
                    .addScalar("link",Hibernate.STRING)
                    .addScalar("web",Hibernate.STRING)
                    .addScalar("type",Hibernate.STRING)
                    .addScalar("brand",Hibernate.STRING)
                    .addScalar("price",Hibernate.STRING)
                    .addScalar("name",Hibernate.STRING)
                    .addScalar("model",Hibernate.STRING)
                    .addScalar("chip",Hibernate.STRING)
                    .addScalar("speed",Hibernate.STRING)
                    .addScalar("storage",Hibernate.STRING)
                    .addScalar("hddType",Hibernate.STRING)
                    .addScalar("ram",Hibernate.STRING)
                    .addScalar("vga",Hibernate.STRING)
                    .addScalar("screen",Hibernate.STRING)
                    .addScalar("touchscreen",Hibernate.STRING)
                    .addScalar("os",Hibernate.STRING)
                    .addScalar("dvd",Hibernate.STRING)
                    .addScalar("battery",Hibernate.STRING)
                    .addScalar("promotion",Hibernate.STRING)
                    .addScalar("lastUpdate", Hibernate.DATE)
                    .setResultTransformer(Transformers.aliasToBean(Laptop.class));

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
            sql.append(" (select LAPTOP_DATA.ID,BRAND,model,STORAGE,RAM from LAPTOP_DATA , LAPTOP_CONFIGURATION  ");
            sql.append(" where LAPTOP_DATA.ID = LAPTOP_DATA_ID and WEB = '").append(rb.getString(Constans.WEB_ROOT)).append("') a join ");
            sql.append(" (select LAPTOP_DATA.ID,BRAND,model,STORAGE,RAM from LAPTOP_DATA , LAPTOP_CONFIGURATION ");
            sql.append(" where LAPTOP_DATA.ID = LAPTOP_DATA_ID and WEB != '").append(rb.getString(Constans.WEB_ROOT)).append("') b ");
            sql.append(" on 1=1 ");

            if (laptop != null) {
                if (laptop.getId() > 0) {
                    sql.append(" and a.ID = ? ");
                    param.add(laptop.getId());
                }
            }

            if (laptopCheck.getBrand() != null && "on".equals(laptopCheck.getBrand())) {
                sql.append(" and a.BRAND =b.BRAND ");
            }
            if (laptopCheck.getModel() != null && "on".equals(laptopCheck.getModel())) {
                sql.append(" and a.model =b.model ");
            }
            if (laptopCheck.getScreen() != null && "on".equals(laptopCheck.getScreen())) {
                sql.append(" and isnull(a.screen,'') = isnull(b.screen,'')  ");
            }
            if (laptopCheck.getChip() != null && "on".equals(laptopCheck.getChip())) {
                sql.append(" and isnull(a.chip,'') = isnull(b.chip,'')  ");
            }
            if (laptopCheck.getStorage() != null && "on".equals(laptopCheck.getStorage())) {
                sql.append(" and isnull(a.STORAGE,'') = isnull(b.STORAGE,'')  ");
            }
            if (laptopCheck.getStorage() != null && "on".equals(laptopCheck.getStorage())) {
                sql.append(" and isnull(a.hddType,'') = isnull(b.hddType,'')  ");
            }
            if (laptopCheck.getRam() != null && "on".equals(laptopCheck.getRam())) {
                sql.append(" and isnull(a.RAM,'') = isnull(b.RAM,'') ");
            }
            if (laptopCheck.getVga() != null && "on".equals(laptopCheck.getVga())) {
                sql.append(" and isnull(a.vga,'') = isnull(b.vga,'') ");
            }
            if (laptopCheck.getOs() != null && "on".equals(laptopCheck.getOs())) {
                sql.append(" and isnull(a.os,'') = isnull(b.os,'') ");
            }
            if (laptopCheck.getTouchscreen() != null && "on".equals(laptopCheck.getTouchscreen())) {
                sql.append(" and isnull(a.touchscreen,'') = isnull(b.touchscreen,'') ");
            }
            if (laptopCheck.getDvd() != null && "on".equals(laptopCheck.getDvd())) {
                sql.append(" and isnull(a.dvd,'') = isnull(b.dvd,'') ");
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
            if(getRequest().getSession().getAttribute("laptop") !=null){
                laptop = (Laptop) getRequest().getSession().getAttribute("laptop");
                getRequest().getSession().removeAttribute("laptop");
                laptopCheck.setBrand("on");
                laptopCheck.setModel("on");
            }
            if (laptop.getId() > 0) {
                setMaxResult();
                List<Laptop> lstResult = new ArrayList<Laptop>();
                lstResult = takeLaptopCompares();
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
                laptop.setItemCode(getRequest().getParameter("itemCode"));
                Session session = getSession();
                StringBuilder sql = new StringBuilder();
                sql.append(" Select a.id,"
//                        + "item_code itemCode ,"
                        + " link,web,type,brand,price,name,model, ");
                sql.append(" chip,SPEED, storage,HDD_TYPE hddType,");
                sql.append(" ram, vga,screen, ");
                sql.append(" touchscreen,os,dvd, ");
                sql.append(" battery,PROMOTION ");
                sql.append(" from LAPTOP_DATA a, LAPTOP_CONFIGURATION b ");
                sql.append(" WHERE a.ID = b.LAPTOP_DATA_ID and b.item_code = ? ");

                Query query = session.createSQLQuery(sql.toString())
                    .addScalar("id",Hibernate.LONG)
                    .addScalar("link",Hibernate.STRING)
//                    .addScalar("itemCode",Hibernate.STRING)
                    .addScalar("web",Hibernate.STRING)
                    .addScalar("type",Hibernate.STRING)
                    .addScalar("brand",Hibernate.STRING)
                    .addScalar("price",Hibernate.STRING)
                    .addScalar("name",Hibernate.STRING)
                    .addScalar("model",Hibernate.STRING)
                    .addScalar("chip",Hibernate.STRING)
                    .addScalar("speed",Hibernate.STRING)
                    .addScalar("storage",Hibernate.STRING)
                    .addScalar("hddType",Hibernate.STRING)
                    .addScalar("ram",Hibernate.STRING)
                    .addScalar("vga",Hibernate.STRING)
                    .addScalar("screen",Hibernate.STRING)
                    .addScalar("touchscreen",Hibernate.STRING)
                    .addScalar("os",Hibernate.STRING)
                    .addScalar("dvd",Hibernate.STRING)
                    .addScalar("battery",Hibernate.STRING)
                    .addScalar("promotion",Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(Laptop.class));
                query.setParameter(0, laptop.getItemCode());
                List lst = query.list();
                if(lst != null && lst.size() > 0){
                    laptop = (Laptop) query.list().get(0);
                } else {
                    result = "Không có mã sản phẩm tương ứng của Laptop!";
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

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public Laptop getLaptopCheck() {
        return laptopCheck;
    }

    public void setLaptopCheck(Laptop laptopCheck) {
        this.laptopCheck = laptopCheck;
    }


}
