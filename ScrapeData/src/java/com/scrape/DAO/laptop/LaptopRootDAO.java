/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.DAO.laptop;

import com.scrape.BO.LaptopConfiguration;
import com.scrape.BO.LaptopData;
import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.DAO.ComboBoxDAO;
import com.scrape.client.form.ComboBox;
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
public class LaptopRootDAO  extends BaseHibernateDAOMDB {
    private DojoJSON jsonDataGrid = new DojoJSON();
    private Laptop laptop = new Laptop();
    private int startval;
    private int count;
    private String sort;
    private String mapping;
    private String result;

    List<ComboBox> brands = new ArrayList<ComboBox>();
    List<ComboBox> chips = new ArrayList<ComboBox>();
    List<ComboBox> storages = new ArrayList<ComboBox>();

    List<ComboBox> hddTypes = new ArrayList<ComboBox>();
    List<ComboBox> rams = new ArrayList<ComboBox>();
    List<ComboBox> vgas = new ArrayList<ComboBox>();
    List<ComboBox> touchscreens = new ArrayList<ComboBox>();
    List<ComboBox> oss = new ArrayList<ComboBox>();
    List<ComboBox> dvds = new ArrayList<ComboBox>();
    List<ComboBox> batterys = new ArrayList<ComboBox>();


    public String prepare(){
        ComboBoxDAO c = new ComboBoxDAO();
        brands = c.combobox("Brand","Laptop");
        chips = c.combobox("Chip","Laptop");
        storages = c.combobox("Storage","Laptop");
        hddTypes = c.combobox("HDDType","Laptop");
        rams = c.combobox("Ram","Laptop");
        vgas = c.combobox("VGA","Laptop");
        touchscreens = c.combobox("TouchScreen","Laptop");
        oss = c.combobox("OS","Laptop");
        dvds = c.combobox("DVD","Laptop");
        batterys = c.combobox("Battery","Laptop");
        return SUCCESS;
    }

    public List<Laptop> takeLaptops() {
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
            sql.append(" chip, ");
            sql.append(" speed, ");
            sql.append(" storage, ");
            sql.append(" hdd_type hddType, ");
            sql.append(" ram, ");
            sql.append(" vga, ");
            sql.append(" screen, ");
            sql.append(" touchscreen, ");
            sql.append(" os, ");
            sql.append(" dvd, ");
            sql.append(" battery, ");
            sql.append(" price, ");
            sql.append(" promotion, ");
            sql.append(" a.create_date createDate, ");
            sql.append(" a.last_update lastUpdate, ");
            sql.append(" (select COUNT(*) from LAPTOP_DATA c, LAPTOP_CONFIGURATION d ");
            sql.append(" where c.ID = d.Laptop_DATA_ID AND c.WEB != '").append(rb.getString(Constans.WEB_ROOT)).append("' AND d.model is not null and c.brand = a.brand ");
            sql.append(" and b.model = d.model");
            sql.append(" ) match  ");
            sql.append(" from LAPTOP_DATA a,LAPTOP_CONFIGURATION b WHERE a.ID = b.LAPTOP_DATA_ID AND a.WEB = '").append(rb.getString(Constans.WEB_ROOT)).append("' ");

            if(laptop != null){
                if(laptop.getBrand() != null && !"".equals(laptop.getBrand())){
                    if("null".equals(laptop.getBrand())){
                        sql.append(" AND brand is null ");
                    }else{
                        sql.append(" AND upper(brand) = ? ");
                        param.add(laptop.getBrand());
                    }
                }
                
                if(laptop.getItemCode() != null && !"".equals(laptop.getItemCode())){
                    sql.append(" AND itemCode = ? ");
                    param.add(laptop.getItemCode());
                }

                if(laptop.getPartno() != null && !"".equals(laptop.getPartno())){
                    sql.append(" AND lower(partno) like ? ");
                    param.add(laptop.getPartno().toLowerCase());
                }

                if(laptop.getName() != null && !"".equals(laptop.getName())){
                    sql.append(" AND lower(name) like ? ");
                    param.add("%" + laptop.getName().toLowerCase() + "%");
                }

                if(laptop.getModel() != null && !"".equals(laptop.getModel())){
                    if("null".equals(laptop.getModel().toLowerCase())){
                        sql.append(" AND model is null ");
                    }else{
                        sql.append(" AND lower(model) like ? ");
                        param.add(laptop.getModel().toLowerCase());
                    }
                }
                if(laptop.getChip() != null && !"".equals(laptop.getChip())){
                    if("null".equals(laptop.getChip())){
                        sql.append(" AND chip is null ");
                    }else{
                        sql.append(" AND chip = ? ");
                        param.add(laptop.getChip());
                    }
                }
                
                if(laptop.getSpeed() != null && !"".equals(laptop.getSpeed())){
                    if("null".equals(laptop.getSpeed())){
                        sql.append(" AND speed is null ");
                    }else{
                        sql.append(" AND speed = ? ");
                        param.add(laptop.getSpeed());
                    }
                }
                if(laptop.getStorage() != null && !"".equals(laptop.getStorage())){
                    if("null".equals(laptop.getStorage())){
                        sql.append(" AND storage is null ");
                    }else{
                        sql.append(" AND storage = ? ");
                        param.add(laptop.getStorage());
                    }

                }
                if(laptop.getHddType() != null && !"".equals(laptop.getHddType())){
                    if("null".equals(laptop.getHddType())){
                        sql.append(" AND hdd_type is null ");
                    }else{
                        sql.append(" AND hdd_type = ? ");
                        param.add(laptop.getHddType());
                    }

                }
                if(laptop.getRam() != null && !"".equals(laptop.getRam())){
                    if("null".equals(laptop.getRam())){
                        sql.append(" AND ram is null ");
                    }else{
                        sql.append(" AND ram = ? ");
                        param.add(laptop.getRam());
                    }
                }
                if(laptop.getVga() != null && !"".equals(laptop.getVga())){
                    if("null".equals(laptop.getVga())){
                        sql.append(" AND vga is null ");
                    }else{
                        sql.append(" AND vga = ? ");
                        param.add(laptop.getVga());
                    }
                }
                if(laptop.getScreen() != null && !"".equals(laptop.getScreen())){
                    if("null".equals(laptop.getScreen())){
                        sql.append(" AND screen is null ");
                    }else{
                        sql.append(" AND screen = ? ");
                        param.add(laptop.getScreen());
                    }
                }
                if(laptop.getTouchscreen() != null && !"".equals(laptop.getTouchscreen())){
                    if("null".equals(laptop.getTouchscreen())){
                        sql.append(" AND touchscreen is null ");
                    }else{
                        sql.append(" AND touchscreen = ? ");
                        param.add(laptop.getTouchscreen());
                    }
                }
                if(laptop.getOs() != null && !"".equals(laptop.getOs())){
                    if("null".equals(laptop.getOs())){
                        sql.append(" AND os is null ");
                    }else{
                        sql.append(" AND os = ? ");
                        param.add(laptop.getOs());
                    }
                }
                if(laptop.getDvd() != null && !"".equals(laptop.getDvd())){
                    if("null".equals(laptop.getDvd())){
                        sql.append(" AND dvd is null ");
                    }else{
                        sql.append(" AND dvd = ? ");
                        param.add(laptop.getDvd());
                    }
                }
                if(laptop.getBattery() != null && !"".equals(laptop.getBattery())){
                    if("null".equals(laptop.getBattery())){
                        sql.append(" AND battery is null ");
                    }else{
                        sql.append(" AND battery = ? ");
                        param.add(laptop.getBattery());
                    }
                }
                if(laptop.getToday() != null && "on".equals(laptop.getToday())){
                    sql.append(" and Cast(a.last_update as date) = CAST(getdate() as date) ");
                    param.add(laptop.getApprove());
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
                    .addScalar("match",Hibernate.LONG)
                    .addScalar("createDate",Hibernate.DATE)
                    .addScalar("lastUpdate",Hibernate.DATE)
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

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lst;
    }

//    Lay so luong ban ghi tra ve grid de phan trang tung phan
    public void setMaxResult() {
        try {
            StringBuilder sql = new StringBuilder("select count(*) from LAPTOP_DATA a, LAPTOP_CONFIGURATION b where a.ID = b.LAPTOP_DATA_ID AND WEB = '").append(rb.getString(Constans.WEB_ROOT)).append("' ");
            ArrayList param = new ArrayList();
            if(laptop != null){
                if(laptop.getBrand() != null && !"".equals(laptop.getBrand())){
                    if("Null".equals(laptop.getBrand())){
                        sql.append(" AND brand is null ");
                    }else{
                        sql.append(" AND upper(brand) = ? ");
                        param.add(laptop.getBrand());
                    }
                }

                if(laptop.getItemCode() != null && !"".equals(laptop.getItemCode())){
                    sql.append(" AND item_Code = ? ");
                    param.add(laptop.getItemCode());
                }

                if(laptop.getPartno() != null && !"".equals(laptop.getPartno())){
                    sql.append(" AND lower(partno) like ? ");
                    param.add(laptop.getPartno().toLowerCase());
                }

                if(laptop.getName() != null && !"".equals(laptop.getName())){
                    sql.append(" AND lower(name) like ? ");
                    param.add("%" + laptop.getName().toLowerCase() + "%");
                }

                if(laptop.getModel() != null && !"".equals(laptop.getModel())){
                    if("null".equals(laptop.getModel().toLowerCase())){
                        sql.append(" AND model is null ");
                    }else{
                        sql.append(" AND lower(model) like ? ");
                        param.add(laptop.getModel().toLowerCase());
                    }
                }

                if(laptop.getChip() != null && !"".equals(laptop.getChip())){
                    if("null".equals(laptop.getChip())){
                        sql.append(" AND chip is null ");
                    }else{
                        sql.append(" AND chip = ? ");
                        param.add(laptop.getChip());
                    }
                }

                if(laptop.getSpeed() != null && !"".equals(laptop.getSpeed())){
                    if("null".equals(laptop.getSpeed())){
                        sql.append(" AND speed is null ");
                    }else{
                        sql.append(" AND speed = ? ");
                        param.add(laptop.getSpeed());
                    }
                }
                if(laptop.getStorage() != null && !"".equals(laptop.getStorage())){
                    if("null".equals(laptop.getStorage())){
                        sql.append(" AND storage is null ");
                    }else{
                        sql.append(" AND storage = ? ");
                        param.add(laptop.getStorage());
                    }

                }
                if(laptop.getHddType() != null && !"".equals(laptop.getHddType())){
                    if("null".equals(laptop.getHddType())){
                        sql.append(" AND hdd_type is null ");
                    }else{
                        sql.append(" AND hdd_type = ? ");
                        param.add(laptop.getHddType());
                    }

                }
                if(laptop.getRam() != null && !"".equals(laptop.getRam())){
                    if("null".equals(laptop.getRam())){
                        sql.append(" AND ram is null ");
                    }else{
                        sql.append(" AND ram = ? ");
                        param.add(laptop.getRam());
                    }
                }
                if(laptop.getVga() != null && !"".equals(laptop.getVga())){
                    if("null".equals(laptop.getVga())){
                        sql.append(" AND vga is null ");
                    }else{
                        sql.append(" AND vga = ? ");
                        param.add(laptop.getVga());
                    }
                }
                if(laptop.getScreen() != null && !"".equals(laptop.getScreen())){
                    if("null".equals(laptop.getScreen())){
                        sql.append(" AND screen is null ");
                    }else{
                        sql.append(" AND screen = ? ");
                        param.add(laptop.getScreen());
                    }
                }
                if(laptop.getTouchscreen() != null && !"".equals(laptop.getTouchscreen())){
                    if("null".equals(laptop.getTouchscreen())){
                        sql.append(" AND touchscreen is null ");
                    }else{
                        sql.append(" AND touchscreen = ? ");
                        param.add(laptop.getTouchscreen());
                    }
                }
                if(laptop.getOs() != null && !"".equals(laptop.getOs())){
                    if("null".equals(laptop.getOs())){
                        sql.append(" AND os is null ");
                    }else{
                        sql.append(" AND os = ? ");
                        param.add(laptop.getOs());
                    }
                }
                if(laptop.getDvd() != null && !"".equals(laptop.getDvd())){
                    if("null".equals(laptop.getDvd())){
                        sql.append(" AND dvd is null ");
                    }else{
                        sql.append(" AND dvd = ? ");
                        param.add(laptop.getDvd());
                    }
                }
                if(laptop.getBattery() != null && !"".equals(laptop.getBattery())){
                    if("null".equals(laptop.getBattery())){
                        sql.append(" AND battery is null ");
                    }else{
                        sql.append(" AND battery = ? ");
                        param.add(laptop.getBattery());
                    }
                }
                
                if(this.mapping != null && "on".equals(this.mapping)){
                    sql.append(" and exists (select d.ID from LAPTOP_DATA c, LAPTOP_CONFIGURATION d ");
                    sql.append(" where c.ID = d.LAPTOP_DATA_ID AND WEB != '").append(rb.getString(Constans.WEB_ROOT)).append("' AND a.brand = c.brand and b.model =d.model )");
                }

                if(laptop.getToday() != null && "on".equals(laptop.getToday())){
                    sql.append(" and Cast(a.last_update as date) = CAST(getdate() as date) ");
                    param.add(laptop.getApprove());
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
            List<Laptop> lstResult = new ArrayList<Laptop>();
            lstResult = takeLaptops();
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
            Long id = laptop.getId();
            if( id != null && id > 0){
                LaptopData temp = new LaptopData();
                temp = (LaptopData) session.get(LaptopData.class, id);
                temp.setBrand(laptop.getBrand());
                temp.setName(laptop.getName());
                session.update(temp);
                session.flush();
            }

            Long id1 = laptop.getId1();
            if( id1 != null && id1 > 0){
                LaptopConfiguration temp1 = new LaptopConfiguration();
                temp1 = (LaptopConfiguration) session.get(LaptopConfiguration.class, id1);
                temp1.setItemCode(laptop.getItemCode());
                temp1.setModel(laptop.getModel());
                temp1.setPartno(laptop.getPartno());
                if("null".equals(laptop.getRam())){
                    temp1.setRam(null);
                }else{
                    temp1.setRam(laptop.getRam());
                }
                
                if("null".equals(laptop.getStorage())){
                    temp1.setStorage(null);
                }else{
                    temp1.setStorage(laptop.getStorage());
                }
                
                if("null".equals(laptop.getChip())){
                    temp1.setChip(null);
                }else{
                    temp1.setChip(laptop.getChip());
                }

                if("null".equals(laptop.getHddType())){
                    temp1.setHddType(null);
                }else{
                    temp1.setHddType(laptop.getHddType());
                }
                
                if("null".equals(laptop.getVga())){
                    temp1.setVga(null);
                }else{
                    temp1.setVga(laptop.getVga());
                }

                temp1.setScreen(laptop.getScreen());

                if("null".equals(laptop.getTouchscreen())){
                    temp1.setTouchscreen(null);
                }else{
                    temp1.setTouchscreen(laptop.getTouchscreen());
                }
                
                if("null".equals(laptop.getOs())){
                    temp1.setOs(null);
                }else{
                    temp1.setOs(laptop.getOs());
                }

                if("null".equals(laptop.getDvd())){
                    temp1.setDvd(null);
                }else{
                    temp1.setDvd(laptop.getDvd());
                }

                if("null".equals(laptop.getBattery())){
                    temp1.setBattery(null);
                }else{
                    temp1.setBattery(laptop.getBattery());
                }
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
//            Long id = laptop.getId();
//            if( id != null && id > 0){
//                LaptopData temp = new LaptopData();
//                temp = (LaptopData) session.get(LaptopData.class, id);
//                session.delete(temp);
//                session.flush();
//            }

            Long id1 = laptop.getId1();
            if( id1 != null && id1 > 0){
                LaptopConfiguration temp1 = new LaptopConfiguration();
                temp1 = (LaptopConfiguration) session.get(LaptopConfiguration.class, id1);
                session.delete(temp1);
                session.flush();
            }
            jsonDataGrid.setLabel("SUCCESS");
            jsonDataGrid.setCustomInfo("Delete Thành Công!");
            laptop = new Laptop();
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

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public List<ComboBox> getBatterys() {
        return batterys;
    }

    public void setBatterys(List<ComboBox> batterys) {
        this.batterys = batterys;
    }

    public List<ComboBox> getBrands() {
        return brands;
    }

    public void setBrands(List<ComboBox> brands) {
        this.brands = brands;
    }

    public List<ComboBox> getChips() {
        return chips;
    }

    public void setChips(List<ComboBox> chips) {
        this.chips = chips;
    }

    public List<ComboBox> getDvds() {
        return dvds;
    }

    public void setDvds(List<ComboBox> dvds) {
        this.dvds = dvds;
    }

    public List<ComboBox> getHddTypes() {
        return hddTypes;
    }

    public void setHddTypes(List<ComboBox> hddTypes) {
        this.hddTypes = hddTypes;
    }

    public List<ComboBox> getOss() {
        return oss;
    }

    public void setOss(List<ComboBox> oss) {
        this.oss = oss;
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

    public List<ComboBox> getTouchscreens() {
        return touchscreens;
    }

    public void setTouchscreens(List<ComboBox> touchscreens) {
        this.touchscreens = touchscreens;
    }

    public List<ComboBox> getVgas() {
        return vgas;
    }

    public void setVgas(List<ComboBox> vgas) {
        this.vgas = vgas;
    }

}
