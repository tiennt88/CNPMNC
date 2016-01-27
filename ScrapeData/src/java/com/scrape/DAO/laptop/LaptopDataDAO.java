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
import com.scrape.common.ExcelUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author KeyLove
 */
public class LaptopDataDAO extends BaseHibernateDAOMDB{
    private DojoJSON jsonDataGrid = new DojoJSON();
    private Laptop laptop = new Laptop();
    private int startval;
    private int count;
    private String sort;
    private String result;

    List<ComboBox> webs = new ArrayList<ComboBox>();
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
        webs = c.combobox("Web","All");
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
            sql.append(" Select a.id,b.id id1, link,web,type,brand,price,name,model, ");
            sql.append(" chip,speed, storage,HDD_TYPE hddType, ");
            sql.append(" ram, vga,screen, ");
            sql.append(" touchscreen,os,dvd, ");
            sql.append(" battery,PROMOTION, a.last_Update lastUpdate, a.create_date createDate");
            sql.append(" from LAPTOP_DATA a, LAPTOP_CONFIGURATION b ");
            sql.append(" WHERE a.id = b.LAPTOP_DATA_ID");

            if(laptop != null){
                if(laptop.getWeb() != null && !"".equals(laptop.getWeb())){
                    sql.append(" AND web = ? ");
                    param.add(laptop.getWeb());
                }

                if(laptop.getBrand() != null && !"".equals(laptop.getBrand())){
                    if("null".equals(laptop.getBrand())){
                        sql.append(" AND brand is null ");
                    }
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
                    if("null".equals(laptop.getSpeed().toLowerCase())){
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
                        sql.append(" AND hdd_Type is null ");
                    }else{
                        sql.append(" AND hdd_Type = ? ");
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
                    if("null".equals(laptop.getScreen().toLowerCase())){
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

                if(laptop.getApprove() != null && "on".equals(laptop.getApprove())){
                    sql.append(" and approve = 'N' ");
                }
                if(laptop.getToday() != null && "on".equals(laptop.getToday())){
                    sql.append(" and Cast(a.last_update as date) = CAST(getdate() as date) ");
                }
            }

            if (sort != null) {
                if (sortType != null && sortType.equals("asc")) {
                    sql.append(" Order by ").append(sort);
                } else if (sortType != null && sortType.equals("desc")) {
                    sql.append(" Order by ").append(sort).append(" desc ");
                }
            }else{
                sql.append(" Order by web ");
            }

            Query query = session.createSQLQuery(sql.toString())
                    .addScalar("id",Hibernate.LONG)
                    .addScalar("id1",Hibernate.LONG)
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
                    .addScalar("lastUpdate",Hibernate.DATE)
                    .addScalar("createDate",Hibernate.DATE)
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
            StringBuilder sql = new StringBuilder("select count(*) from LAPTOP_DATA a, LAPTOP_CONFIGURATION b where a.ID = b.LAPTOP_DATA_ID AND 1=1 ");
            ArrayList param = new ArrayList();
            if(laptop != null){
                if(laptop.getWeb() != null && !"".equals(laptop.getWeb())){
                    sql.append(" AND web = ? ");
                    param.add(laptop.getWeb());
                }

                if(laptop.getBrand() != null && !"".equals(laptop.getBrand())){
                    if("null".equals(laptop.getBrand())){
                        sql.append(" AND brand is null ");
                    }else{
                        sql.append(" AND brand = ? ");
                        param.add(laptop.getBrand());
                    }
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
                    if("null".equals(laptop.getSpeed().toLowerCase())){
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
                        sql.append(" AND hdd_Type is null ");
                    }else{
                        sql.append(" AND hdd_Type = ? ");
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
                    if("null".equals(laptop.getScreen().toLowerCase())){
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

                if(laptop.getApprove() != null && "on".equals(laptop.getApprove())){
                    sql.append(" and approve = 'N' ");
                }
                if(laptop.getToday() != null && "on".equals(laptop.getToday())){
                    sql.append(" and Cast(a.last_update as date) = CAST(getdate() as date) ");
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
            if( id1 != null && 1 > 0){
                LaptopConfiguration temp1 = new LaptopConfiguration();
                temp1 = (LaptopConfiguration) session.get(LaptopConfiguration.class, id1);
                temp1.setModel(laptop.getModel());
                if("null".equals(laptop.getChip())){
                    temp1.setChip(null);
                }else{
                    temp1.setChip(laptop.getChip());
                }
                temp1.setSpeed(laptop.getSpeed());
                if("null".equals(laptop.getStorage())){
                    temp1.setStorage(null);
                }else{
                    temp1.setStorage(laptop.getStorage());
                }
                if("null".equals(laptop.getHddType())){
                    temp1.setHddType(null);
                }else{
                    temp1.setHddType(laptop.getHddType());
                }
                if("null".equals(laptop.getRam())){
                    temp1.setRam(null);
                }else{
                    temp1.setRam(laptop.getRam());
                }
                if("null".equals(laptop.getVga())){
                    temp1.setVga(null);
                }else{
                    temp1.setVga(laptop.getVga());
                }
                if("null".equals(laptop.getScreen())){
                    temp1.setScreen(null);
                }else{
                    temp1.setScreen(laptop.getScreen());
                }
                if("null".equals(laptop.getTouchscreen())){
                    temp1.setTouchscreen(null);
                }else{
                    temp1.setTouchscreen(laptop.getTouchscreen());
                }
                temp1.setOs(laptop.getOs());
                temp1.setDvd(laptop.getDvd());
                temp1.setBattery(laptop.getBattery());

                if(laptop.getApprove() != null && "on".equals(laptop.getApprove())){
                    temp1.setApprove("N");
                }else{
                    temp1.setApprove("Y");
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

    public String laptopAnalysis(){
        CallableStatement cs = null;
        Connection conn = null;
        try {
            conn = getSession().connection();
            //call store insight
            String sql = "{call LAPTOP_ANALYSIS (?)}";
            cs = conn.prepareCall(sql);
            cs.setString(1, "ALL");
            cs.execute();
            conn.commit();
            jsonDataGrid.setCustomInfo("Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            jsonDataGrid.setCustomInfo(ex.getMessage());
        }finally{
            closeObject(cs);
            closeObject(conn);
        }
        return "jsonDataGrid";
    }

    public List<Laptop> takeLaptopsReport() {
        List<Laptop> lst = null;
        ArrayList param = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" Select a.id,b.id id1,link,web,type,brand,PRICE_NUMBER priceNumber,name,model, item_code itemCode, partno,");
            sql.append(" CPU_TEXT cpuText,chip,SPEED, storage_text storageText , storage,HDD_TYPE hddType, ");
            sql.append(" ram_text ramText, ram, vga_Text vgaText, vga,SCREEN_TEXT screenText, screen, ");
            sql.append(" TOUCHSCREEN_TEXT touchscreenText, touchscreen,os_text osText,os, DVD_TEXT dvdText,dvd,");
            sql.append(" BATTERY_TEXT batteryText, battery, a.last_Update lastUpdate, approve");
            sql.append(" from LAPTOP_DATA a, LAPTOP_CONFIGURATION b ");
            sql.append(" WHERE a.id = b.LAPTOP_DATA_ID");

            if(laptop != null){
                if(laptop.getWeb() != null && !"".equals(laptop.getWeb())){
                    sql.append(" AND web = ? ");
                    param.add(laptop.getWeb());
                }

                if(laptop.getBrand() != null && !"".equals(laptop.getBrand())){
                    if("null".equals(laptop.getBrand())){
                        sql.append(" AND brand is null ");
                    }
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
                    if("null".equals(laptop.getSpeed().toLowerCase())){
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
                        sql.append(" AND hdd_Type is null ");
                    }else{
                        sql.append(" AND hdd_Type = ? ");
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
                    if("null".equals(laptop.getScreen().toLowerCase())){
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

                if(laptop.getApprove() != null && "on".equals(laptop.getApprove())){
                    sql.append(" and approve = 'N' ");
                }
                if(laptop.getToday() != null && "on".equals(laptop.getToday())){
                    sql.append(" and Cast(a.last_update as date) = CAST(getdate() as date) ");
                }
            }

            sql.append(" Order by web, brand, model ");

            Query query = getSession().createSQLQuery(sql.toString())
                    .addScalar("id",Hibernate.LONG)
                    .addScalar("id1",Hibernate.LONG)
                    .addScalar("link",Hibernate.STRING)
                    .addScalar("web",Hibernate.STRING)
                    .addScalar("type",Hibernate.STRING)
                    .addScalar("brand",Hibernate.STRING)
                    .addScalar("priceNumber",Hibernate.LONG)
                    .addScalar("name",Hibernate.STRING)
                    .addScalar("model",Hibernate.STRING)
                    .addScalar("itemCode",Hibernate.STRING)
                    .addScalar("partno",Hibernate.STRING)
                    .addScalar("cpuText",Hibernate.STRING)
                    .addScalar("chip",Hibernate.STRING)
                    .addScalar("speed",Hibernate.STRING)
                    .addScalar("storageText",Hibernate.STRING)
                    .addScalar("storage",Hibernate.STRING)
                    .addScalar("hddType",Hibernate.STRING)
                    .addScalar("ramText",Hibernate.STRING)
                    .addScalar("ram",Hibernate.STRING)
                    .addScalar("vgaText",Hibernate.STRING)
                    .addScalar("vga",Hibernate.STRING)
                    .addScalar("screenText",Hibernate.STRING)
                    .addScalar("screen",Hibernate.STRING)
                    .addScalar("touchscreenText",Hibernate.STRING)
                    .addScalar("touchscreen",Hibernate.STRING)
                    .addScalar("osText",Hibernate.STRING)
                    .addScalar("os",Hibernate.STRING)
                    .addScalar("dvdText",Hibernate.STRING)
                    .addScalar("dvd",Hibernate.STRING)
                    .addScalar("batteryText",Hibernate.STRING)
                    .addScalar("battery",Hibernate.STRING)
                    .addScalar("approve",Hibernate.STRING)
                    .addScalar("lastUpdate",Hibernate.DATE)
                    .setResultTransformer(Transformers.aliasToBean(Laptop.class));

            for (int i = 0; i < param.size(); i++) {
                query.setParameter(i, param.get(i));
            }

            lst = query.list();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lst;
    }

    public String exportExcel(){
        try{
//            String tempFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.TEMPLATE_FILE_PATH) + "LaptopReport.xlsx");
            String tempFile = rb.getString(Constans.TEMPLATE_FILE_PATH) + "LaptopDataReport.xlsx";
            Random random = new Random();
//            String downFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.DOWNLOAD_FILE_PATH) + "LaptopReport_"+random.nextInt(1000)+".xlsx");
            String downFile = rb.getString(Constans.DOWNLOAD_FILE_PATH) + "LaptopDataReport_"+random.nextInt(1000)+".xlsx";

            FileInputStream file = new FileInputStream(new File(tempFile));
//          Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = null;
            workbook = new XSSFWorkbook(file);
            //Get first/desired sheet from the workbook
            XSSFSheet sheet1 = workbook.getSheetAt(0);

            List<Laptop> list1 = new ArrayList<Laptop>();
            list1 = takeLaptopsReport();
            fillData(list1,workbook,sheet1);

            FileOutputStream out = new FileOutputStream(new File(downFile));
            workbook.write(out);
            out.close();
            getRequest().setAttribute("attachFile", downFile);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return SUCCESS;
    }

    public void fillData(List<Laptop> list, XSSFWorkbook workbook ,XSSFSheet sheet ){
        int j=0;
        int rowIndex = 2;
        int stt = 1;

        ExcelUtil ex = new ExcelUtil(workbook);
        for(int i = 0 ; i< list.size(); i++){
            Row row = sheet.createRow(rowIndex++);
            j=0;
            row.createCell(j++).setCellValue(stt++);
            row.createCell(j++).setCellValue(list.get(i).getId());
            row.createCell(j++).setCellValue(list.get(i).getId1());
            row.createCell(j++).setCellValue(list.get(i).getWeb());
            row.createCell(j++).setCellValue(list.get(i).getType());
            row.createCell(j++).setCellValue(list.get(i).getBrand());
            if(list.get(i).getPriceNumber()!=null){
                ex.formatNumber(row, j++, list.get(i).getPriceNumber(), ex.number);
            }else{
                j++;
            }
            row.createCell(j++).setCellValue(list.get(i).getItemCode());
            row.createCell(j++).setCellValue(list.get(i).getPartno());
            row.createCell(j++).setCellValue(list.get(i).getName());
            row.createCell(j++).setCellValue(list.get(i).getModel());
            row.createCell(j++).setCellValue(list.get(i).getCpuText());
            row.createCell(j++).setCellValue(list.get(i).getChip());
            row.createCell(j++).setCellValue(list.get(i).getSpeed());
            row.createCell(j++).setCellValue(list.get(i).getStorageText());
            row.createCell(j++).setCellValue(list.get(i).getStorage());
            row.createCell(j++).setCellValue(list.get(i).getHddType());
            row.createCell(j++).setCellValue(list.get(i).getRamText());
            row.createCell(j++).setCellValue(list.get(i).getRam());
            row.createCell(j++).setCellValue(list.get(i).getVgaText());
            row.createCell(j++).setCellValue(list.get(i).getVga());
            row.createCell(j++).setCellValue(list.get(i).getScreenText());
            row.createCell(j++).setCellValue(list.get(i).getScreen());
            row.createCell(j++).setCellValue(list.get(i).getTouchscreenText());
            row.createCell(j++).setCellValue(list.get(i).getTouchscreen());
            row.createCell(j++).setCellValue(list.get(i).getOsText());
            row.createCell(j++).setCellValue(list.get(i).getOs());
            row.createCell(j++).setCellValue(list.get(i).getDvdText());
            row.createCell(j++).setCellValue(list.get(i).getDvd());
            row.createCell(j++).setCellValue(list.get(i).getBatteryText());
            row.createCell(j++).setCellValue(list.get(i).getBattery());
            row.createCell(j++).setCellValue(list.get(i).getLink());
            ex.formatDate(row, j++, list.get(i).getLastUpdate(), ex.date);
            row.createCell(j++).setCellValue(list.get(i).getApprove());
        }
    }

    public String upload(File file){
        String sql = "update Laptop_Configuration set item_Code = ?, partno = ?, model = ? ,screen = ?, chip = ?, speed = ?, storage = ?,"
                + " hdd_type = ?, ram = ?,vga = ?,os = ?,battery = ?, touchscreen = ?, dvd = ?, approve = 'Y' where id = ? ";
        Connection conn = null;
        PreparedStatement pre = null;
        try{
            XSSFWorkbook workbook = null;
            workbook = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheet = workbook.getSheetAt(0);
            int numrows = sheet.getLastRowNum();
            int index = 1;
            conn = getSession().connection();
            conn.setAutoCommit(false);
            pre = conn.prepareStatement(sql);

            for (int i = 2; i <= numrows; i++) {
                Row row = sheet.getRow(i);
                Laptop laptop = new Laptop();
                int j=1;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                     laptop.setId((long)row.getCell(j).getNumericCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                     laptop.setId1((long)row.getCell(j).getNumericCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     laptop.setWeb(row.getCell(j).getStringCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     laptop.setType(row.getCell(j).getStringCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     laptop.setBrand(row.getCell(j).getStringCellValue());
                }
                j++;
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     laptop.setItemCode(row.getCell(j).getStringCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     laptop.setPartno(row.getCell(j).getStringCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     laptop.setName(row.getCell(j).getStringCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     laptop.setModel(row.getCell(j).getStringCellValue());
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        laptop.setChip(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        laptop.setChip(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        laptop.setSpeed(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        laptop.setSpeed(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        laptop.setStorage(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        laptop.setStorage(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        laptop.setHddType(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        laptop.setHddType(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        laptop.setRam(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        laptop.setRam(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        laptop.setVga(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        laptop.setVga(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        laptop.setScreen(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        laptop.setScreen(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        laptop.setTouchscreen(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        laptop.setTouchscreen(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        laptop.setOs(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        laptop.setOs(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        laptop.setDvd(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        laptop.setDvd(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        laptop.setBattery(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        laptop.setBattery(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                
                laptop.setApprove("Y");
                int k = 1;
                pre.setString(k++, laptop.getItemCode() );
                pre.setString(k++, laptop.getPartno() );
                pre.setString(k++, laptop.getModel());
                pre.setString(k++, laptop.getScreen());
                pre.setString(k++, laptop.getChip());
                pre.setString(k++, laptop.getSpeed());
                pre.setString(k++, laptop.getStorage());
                pre.setString(k++, laptop.getHddType());
                pre.setString(k++, laptop.getRam());
                pre.setString(k++, laptop.getVga());
                pre.setString(k++, laptop.getOs());
                pre.setString(k++, laptop.getBattery());
                pre.setString(k++, laptop.getTouchscreen());
                pre.setString(k++, laptop.getDvd());
                pre.setLong(k++, laptop.getId1());
                pre.addBatch();
                if(index%3000==0){
                    pre.executeBatch();
                    conn.commit();
                }
                index++;
            }
            int a[] = pre.executeBatch();
            System.out.println(a.length);
            conn.commit();
        }catch(Exception ex){
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception ex1) {
                ex.printStackTrace();
            }
            return ex.getMessage();
        }finally{
            closeObject(pre);
            closeObject(conn);
        }
        return "Success";
    }

    public String onApprove() {
        try {
            Session session = getSession();
            if(getRequest().getParameter("id") != null){
                LaptopConfiguration temp = new LaptopConfiguration();
                temp = (LaptopConfiguration) session.get(LaptopConfiguration.class, Long.parseLong(getRequest().getParameter("id")));
                if("Y".equals(temp.getApprove())){
                    temp.setApprove("N");
                }else{
                    temp.setApprove("Y");
                }
                session.update(temp);
                session.flush();
            }
            jsonDataGrid.setLabel("SUCCESS");
            jsonDataGrid.setCustomInfo("Cập Nhật Trạng Thái Thành Công!");
//            onSearch();
        } catch (Exception ex) {
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

    public List<ComboBox> getWebs() {
        return webs;
    }

    public void setWebs(List<ComboBox> webs) {
        this.webs = webs;
    }

}
