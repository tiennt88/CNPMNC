/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.DAO.tablet;

import com.scrape.BO.TabletConfiguration;
import com.scrape.BO.TabletData;
import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.DAO.ComboBoxDAO;
import com.scrape.client.form.ComboBox;
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
public class TabletRootDAO  extends BaseHibernateDAOMDB {
    private DojoJSON jsonDataGrid = new DojoJSON();
    private Tablet tablet = new Tablet();
    private int startval;
    private int count;
    private String sort;
    private String mapping;
    private String result;
    List<ComboBox> brands = new ArrayList<ComboBox>();
    List<ComboBox> storages = new ArrayList<ComboBox>();
    List<ComboBox> rams = new ArrayList<ComboBox>();
    
    public String prepare(){
        ComboBoxDAO c = new ComboBoxDAO();
        brands = c.combobox("BRAND","Tablet");
        storages = c.combobox("STORAGE","Tablet");
        rams = c.combobox("RAM","Tablet");
        return SUCCESS;
    }

    public List<Tablet> takeTablets() {
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
            sql.append(" SELECT * FROM (SELECT ");
            sql.append(" a.ID, ");
            sql.append(" b.ID id1, ");
            sql.append(" link, ");
            sql.append(" web, ");
            sql.append(" ITEM_CODE itemCode, ");
            sql.append(" name, ");
            sql.append(" BRAND, ");
            sql.append(" type, ");
            sql.append(" model, ");
            sql.append(" partno, ");
            sql.append(" storage, ");
            sql.append(" ram, ");
            sql.append(" screen, ");
            sql.append(" cpu, ");
            sql.append(" os, ");
            sql.append(" B_CAMERA backCamera, ");
            sql.append(" F_CAMERA frontCamera, ");
            sql.append(" battery, ");
            sql.append(" sim, ");
            sql.append(" price, ");
            sql.append(" promotion, ");
            sql.append(" a.create_date createDate, ");
            sql.append(" a.last_update lastUpdate, ");
            sql.append(" (select COUNT(*) from TABLET_DATA c, TABLET_CONFIGURATION d ");
            sql.append(" where c.ID = d.Tablet_DATA_ID AND c.WEB != '").append(rb.getString(Constans.WEB_ROOT)).append("' AND d.model is not null and c.brand = a.brand ");
            sql.append(" and b.model = d.model");
            sql.append(" ) match  ");
            sql.append(" from TABLET_DATA a,TABLET_CONFIGURATION b WHERE a.ID = b.TABLET_DATA_ID AND a.WEB = '").append(rb.getString(Constans.WEB_ROOT)).append("' ");

            if(tablet != null){
                if(tablet.getBrand() != null && !"".equals(tablet.getBrand())){
                    if("null".equals(tablet.getBrand())){
                        sql.append(" AND brand is null ");
                    }else{
                        sql.append(" AND upper(brand) = ? ");
                        param.add(tablet.getBrand());
                    }
                }
                
                if(tablet.getItemCode() != null && !"".equals(tablet.getItemCode())){
                    sql.append(" AND itemCode = ? ");
                    param.add(tablet.getItemCode());
                }

                if(tablet.getPartno() != null && !"".equals(tablet.getPartno())){
                    sql.append(" AND lower(partno) like ? ");
                    param.add("%" + tablet.getPartno().toLowerCase() + "%");
                }

                if(tablet.getName() != null && !"".equals(tablet.getName())){
                    sql.append(" AND lower(name) like ? ");
                    param.add("%" + tablet.getName().toLowerCase() + "%");
                }

                if(tablet.getModel() != null && !"".equals(tablet.getModel())){
                    if("null".equals(tablet.getModel().toLowerCase())){
                        sql.append(" AND model is null ");
                    }else{
                        sql.append(" AND lower(model) like ? ");
                        param.add("%" + tablet.getModel().toLowerCase() + "%");
                    }
                }
                
                if(tablet.getRam() != null && !"".equals(tablet.getRam())){
                    if("null".equals(tablet.getRam())){
                        sql.append(" AND ram is null ");
                    }else{
                        sql.append(" AND ram = ? ");
                        param.add(tablet.getRam());
                    }
                }

                if(tablet.getStorage() != null && !"".equals(tablet.getStorage())){
                    if("null".equals(tablet.getStorage())){
                        sql.append(" AND storage is null ");
                    }else{
                        sql.append(" AND storage = ? ");
                        param.add(tablet.getStorage());
                    }

                }
                if(tablet.getToday() != null && "on".equals(tablet.getToday())){
                    sql.append(" and Cast(a.last_update as date) = CAST(getdate() as date) ");
                }

            }

            sql.append(" )z where 1=1 ");

            if(this.mapping != null && "on".equals(this.mapping)){
                sql.append(" and match > 0 ");
            }

            if (sort != null) {
                if (sortType != null && sortType.equals("asc")) {
                    sql.append(" Order by ").append(sort);
                } else if (sortType != null && sortType.equals("desc")) {
                    sql.append(" Order by ").append(sort).append(" desc ");
                }
            }else{
                sql.append(" Order by createDate desc ");
            }
            
            Query query = session.createSQLQuery(sql.toString())
                    .addScalar("id",Hibernate.LONG)
                    .addScalar("id1",Hibernate.LONG)
                    .addScalar("link",Hibernate.STRING)
                    .addScalar("web",Hibernate.STRING)
                    .addScalar("type",Hibernate.STRING)
                    .addScalar("brand",Hibernate.STRING)
                    .addScalar("itemCode",Hibernate.STRING)
                    .addScalar("partno",Hibernate.STRING)
                    .addScalar("name",Hibernate.STRING)
                    .addScalar("model",Hibernate.STRING)
                    .addScalar("price",Hibernate.STRING)
                    .addScalar("storage",Hibernate.STRING)
                    .addScalar("ram",Hibernate.STRING)
                    .addScalar("screen",Hibernate.STRING)
                    .addScalar("cpu",Hibernate.STRING)
                    .addScalar("os",Hibernate.STRING)
                    .addScalar("backCamera",Hibernate.STRING)
                    .addScalar("frontCamera",Hibernate.STRING)
                    .addScalar("battery",Hibernate.STRING)
                    .addScalar("sim",Hibernate.STRING)
                    .addScalar("promotion",Hibernate.STRING)
                    .addScalar("match",Hibernate.LONG)
                    .addScalar("createDate",Hibernate.DATE)
                    .addScalar("lastUpdate",Hibernate.DATE)
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

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lst;
    }

//    Lay so luong ban ghi tra ve grid de phan trang tung phan
    public void setMaxResult() {
        try {
            StringBuilder sql = new StringBuilder("select count(*) from TABLET_DATA a, TABLET_CONFIGURATION b where a.ID = b.TABLET_DATA_ID AND WEB = '").append(rb.getString(Constans.WEB_ROOT)).append("' ");
            ArrayList param = new ArrayList();
            if(tablet != null){
                if(tablet.getBrand() != null && !"".equals(tablet.getBrand())){
                    if("Null".equals(tablet.getBrand())){
                        sql.append(" AND brand is null ");
                    }else{
                        sql.append(" AND upper(brand) = ? ");
                        param.add(tablet.getBrand());
                    }
                }

                if(tablet.getItemCode() != null && !"".equals(tablet.getItemCode())){
                    sql.append(" AND item_Code = ? ");
                    param.add(tablet.getItemCode());
                }

                if(tablet.getPartno() != null && !"".equals(tablet.getPartno())){
                    sql.append(" AND lower(partno) like ? ");
                    param.add("%" + tablet.getPartno().toLowerCase() + "%");
                }

                if(tablet.getName() != null && !"".equals(tablet.getName())){
                    sql.append(" AND lower(name) like ? ");
                    param.add("%" + tablet.getName().toLowerCase() + "%");
                }

                if(tablet.getModel() != null && !"".equals(tablet.getModel())){
                    if("null".equals(tablet.getModel().toLowerCase())){
                        sql.append(" AND model is null ");
                    }else{
                        sql.append(" AND lower(model) like ? ");
                        param.add("%" + tablet.getModel().toLowerCase() + "%");
                    }
                }

                if(tablet.getRam() != null && !"".equals(tablet.getRam())){
                    if("null".equals(tablet.getRam())){
                        sql.append(" AND ram is null ");
                    }else{
                        sql.append(" AND ram = ? ");
                        param.add(tablet.getRam());
                    }
                }

                if(tablet.getStorage() != null && !"".equals(tablet.getStorage())){
                    if("null".equals(tablet.getStorage())){
                        sql.append(" AND storage is null ");
                    }else{
                        sql.append(" AND storage = ?");
                        param.add(tablet.getStorage());
                    }

                }

                if(tablet.getToday() != null && "on".equals(tablet.getToday())){
                    sql.append(" and Cast(a.last_update as date) = CAST(getdate() as date) ");
                }

                if(this.mapping != null && "on".equals(this.mapping)){
                    sql.append(" and exists (select d.ID from TABLET_DATA c, TABLET_CONFIGURATION d ");
                    sql.append(" where c.ID = d.TABLET_DATA_ID AND WEB != '").append(rb.getString(Constans.WEB_ROOT)).append("' AND a.brand = c.brand and b.model =d.model )");
                }
            }

            SQLQuery query = getSession().createSQLQuery(sql.toString());
            for (int i = 0; i < param.size(); i++) {
                query.setParameter(i, param.get(i));
            }
            List lst = query.list();
            Integer countRecord =  (Integer) lst.get(0);
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
//            UserToken userToken = (UserToken) getRequest().getSession().getAttribute("userToken");
//            System.out.println(this.mapping);
            setMaxResult();
            List<Tablet> lstResult = new ArrayList<Tablet>();
            lstResult = takeTablets();
            jsonDataGrid.setItems(lstResult);

        } catch (Exception ex) {
            ex.printStackTrace();
            jsonDataGrid.setLabel("ERROR");
            jsonDataGrid.setCustomInfo(ex.getMessage());
        }
        return "jsonDataGrid";
    }

    public String onUpdate() {
        try {
            Session session = getSession();
            Long id = tablet.getId();
            if( id != null && id > 0){
                TabletData temp = new TabletData();
                temp = (TabletData) session.get(TabletData.class, id);
                temp.setBrand(tablet.getBrand());
                temp.setName(tablet.getName());
                session.update(temp);
                session.flush();
            }

            Long id1 = tablet.getId1();
            if( id1 != null && id1 > 0){
                TabletConfiguration temp1 = new TabletConfiguration();
                temp1 = (TabletConfiguration) session.get(TabletConfiguration.class, id1);
                temp1.setItemCode(tablet.getItemCode());
                temp1.setModel(tablet.getModel());
                temp1.setPartno(tablet.getPartno());
                if("null".equals(tablet.getRam())){
                    tablet.setRam(null);
                }
                temp1.setRam(tablet.getRam());
                if("null".equals(tablet.getStorage())){
                    tablet.setStorage(null);
                }
                temp1.setStorage(tablet.getStorage());
                session.update(temp1);
                session.flush();
            }

            jsonDataGrid.setLabel("SUCCESS");
            jsonDataGrid.setCustomInfo("Update Thành Công!");
            onSearch();
        } catch (Exception ex) {
            ex.printStackTrace();
            jsonDataGrid.setLabel("ERROR");
            jsonDataGrid.setCustomInfo(ex.getMessage());
        }
        return "jsonDataGrid";
    }

    public String onDelete() {
        try {
            Session session = getSession();
//            Long id = tablet.getId();
//            if( id != null && id > 0){
//                TabletData temp = new TabletData();
//                temp = (TabletData) session.get(TabletData.class, id);
//                session.delete(temp);
//                session.flush();
//            }

            Long id1 = tablet.getId1();
            if( id1 != null && id1 > 0){
                TabletConfiguration temp1 = new TabletConfiguration();
                temp1 = (TabletConfiguration) session.get(TabletConfiguration.class, id1);
                session.delete(temp1);
                session.flush();
            }
            jsonDataGrid.setLabel("SUCCESS");
            jsonDataGrid.setCustomInfo("Delete Thành Công!");
            tablet = new Tablet();
            onSearch();
        } catch (Exception ex) {
            ex.printStackTrace();
            jsonDataGrid.setLabel("ERROR");
            jsonDataGrid.setCustomInfo(ex.getMessage());
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

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
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

    public List<ComboBox> getBrands() {
        return brands;
    }

    public void setBrands(List<ComboBox> brands) {
        this.brands = brands;
    }

    public List<ComboBox> getRams() {
        return rams;
    }

    public void setRams(List<ComboBox> rams) {
        this.rams = rams;
    }

    public List<ComboBox> getStorages() {
        return storages;
    }

    public void setStorages(List<ComboBox> storages) {
        this.storages = storages;
    }

}
