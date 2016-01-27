/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.DAO.mobile;
import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.DAO.ComboBoxDAO;
import com.scrape.client.form.ComboBox;
import com.scrape.client.form.Mobile;
import com.scrape.common.Constans;
import com.scrape.common.DojoJSON;
import java.io.InputStream;
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
public class MobileSegmentationDAO extends BaseHibernateDAOMDB{
    private DojoJSON jsonDataGrid = new DojoJSON();
    private int startval;
    private int count;
    private String sort;
    private String result;
    private Mobile mobile = new Mobile();
    private String check;
    private String date;
    private List<String> webs = new ArrayList<String>();
    ArrayList param = new ArrayList();
    private InputStream inputStream;
    private String filename;

    List<ComboBox> lstWeb = new ArrayList<ComboBox>();
    List<ComboBox> storageClass = new ArrayList<ComboBox>();
    List<ComboBox> ramClass = new ArrayList<ComboBox>();
    List<ComboBox> cpus = new ArrayList<ComboBox>();
    List<ComboBox> speedClass = new ArrayList<ComboBox>();
    List<ComboBox> screenClass = new ArrayList<ComboBox>();
    List<ComboBox> sims = new ArrayList<ComboBox>();
    List<ComboBox> backCameraClass = new ArrayList<ComboBox>();
    List<ComboBox> frontCameraClass = new ArrayList<ComboBox>();
    List<ComboBox> oss = new ArrayList<ComboBox>();
    List<ComboBox> batteryClass = new ArrayList<ComboBox>();
    List<ComboBox> priceClass = new ArrayList<ComboBox>();

    public String prepare(){
        
        ComboBoxDAO c = new ComboBoxDAO();
        lstWeb = c.takeWebNotRoot(rb.getString(Constans.WEB_ROOT));
        storageClass = c.combobox("StorageClass","Mobile");
        ramClass = c.combobox("RamClass","Mobile");
        cpus = c.combobox("CPU","Mobile");
        speedClass = c.combobox("SpeedClass","Mobile");
        screenClass = c.combobox("ScreenClass","Mobile");
        sims = c.combobox("Sim","Mobile");
        backCameraClass = c.combobox("BackCameraClass","Mobile");
        frontCameraClass = c.combobox("FrontCameraClass","Mobile");
        oss = c.combobox("OS","Mobile");
        batteryClass = c.combobox("BatteryClass","Mobile");
        priceClass = c.combobox("PriceClass","Mobile");
        return SUCCESS;
    }

    public String takeSQL(String type){
        param.clear();
        StringBuilder sql = new StringBuilder();
        if("count".equals(type)){
            sql.append(" select count(*) ");
        }else{
            sql.append(" SELECT link,WEB, TYPE, BRAND,PRICE,ITEM_CODE itemCode,NAME,model,STORAGE,RAM,SCREEN,CPU,B_CAMERA backCamera,"
                    + " F_CAMERA frontCamera,os,BATTERY,SIM,COLOR, a.LAST_UPDATE lastUpdate ");
        }
        sql.append(" FROM MOBILE_DATA a,  mobile_configuration b ");
        sql.append(" where a.ID = b.MOBILE_DATA_ID ");
        if(date != null && !"".equals(date)){
            sql.append(" AND CAST(a.LAST_UPDATE as DATE) = ?");
            param.add(date);
        }else{
            sql.append(" AND CAST(a.LAST_UPDATE AS DATE) = CAST(getdate() AS DATE)");
        }
        if(webs != null && webs.size() > 0){
            String listWeb = "'";
            for(int i=0;i<webs.size();i++){
                if(i==webs.size()-1){
                    listWeb = listWeb + webs.get(i) +"'";
                }else{
                    listWeb = listWeb + webs.get(i) +"','";
                }

            }
//            System.out.println(listWeb);
            sql.append(" and WEB in (").append(listWeb).append(") ");
        }
        
        if(mobile != null){
            if( mobile.getStorage() != null && !"".equals(mobile.getStorage())){
                sql.append(" and convert(float,storage) ");
                sql.append(mobile.getStorage());
//                param.add(mobile.getStorage());
            }
            if( mobile.getRam() != null && !"".equals(mobile.getRam())){
                sql.append(" and convert(float,ram) ");
                sql.append(mobile.getRam());
//                param.add(mobile.getRam());
            }
            if( mobile.getCpu() != null && !"".equals(mobile.getCpu())){
                sql.append(" and cpu = ? ");
                param.add(mobile.getCpu());
            }
            if( mobile.getSpeed() != null && !"".equals(mobile.getSpeed())){
                if(mobile.getSpeed().indexOf("and") >= 0){
                    sql.append(" and convert(float,speed) between ");
                }else{
                    sql.append(" and convert(float,speed) ");

                }
                sql.append(mobile.getSpeed());
//                param.add(mobile.getSpeed());
            }
            if( mobile.getScreen() != null && !"".equals(mobile.getScreen())){
                if(mobile.getScreen().indexOf("and") >= 0){
                    sql.append(" and convert(float,screen) between ");
                }else{
                    sql.append(" and convert(float,screen) ");
                }
                sql.append(mobile.getScreen());
//                param.add(mobile.getScreen());
            }
            if( mobile.getSim() != null && !"".equals(mobile.getSim())){
                sql.append(" and sim = ? ");
                param.add(mobile.getSim());
            }
            if( mobile.getBackCamera() != null && !"".equals(mobile.getBackCamera())){
                sql.append(" and convert(float, b_camera) ");
                sql.append(mobile.getBackCamera());
//                param.add(mobile.getBackCamera());
            }
            if( mobile.getFrontCamera() != null && !"".equals(mobile.getFrontCamera())){
                sql.append(" and convert(float, f_camera) ");
                sql.append(mobile.getFrontCamera());
//                param.add(mobile.getFrontCamera());
            }
            if( mobile.getOs() != null && !"".equals(mobile.getOs())){
                sql.append(" and os = ? ");
                param.add(mobile.getOs());
            }
            if( mobile.getBattery() != null && !"".equals(mobile.getBattery())){
                if(mobile.getBattery().indexOf("and") >= 0){
                    sql.append(" and convert(float, battery) between ");
                }else{
                    sql.append(" and convert(float, battery) ");
                }
                sql.append(mobile.getBattery());
//                param.add(mobile.getBattery());
            }
            if( mobile.getPrice() != null && !"".equals(mobile.getPrice())){
                if(mobile.getPrice().indexOf("and") >= 0){
                    sql.append(" and CONVERT(float, price_number/1000000) between ");
                }else{
                    sql.append(" and CONVERT(float, price_number/1000000) ");
                }
                sql.append(mobile.getPrice());
//                param.add(mobile.getPrice());
            }
            
        }
        return sql.toString();
    }

    public String onSearch() {
        try {
//            UserToken userToken = (UserToken) getRequest().getSession().getAttribute("userToken");
            setMaxResult();
            List<Mobile> lstResult = new ArrayList<Mobile>();
            lstResult = takeMobiles();

            jsonDataGrid.setItems(lstResult);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "jsonDataGrid";
    }

    public void setMaxResult() {
        try {
            String sql = takeSQL("count");

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

    public List<Mobile> takeMobiles() {
        List<Mobile> lst = null;
        try {
            Session session = getSession();
            String sql = takeSQL("getData");
            Query query = session.createSQLQuery(sql.toString())
                .addScalar("link",Hibernate.STRING)
                .addScalar("web",Hibernate.STRING)
                .addScalar("type",Hibernate.STRING)
                .addScalar("brand",Hibernate.STRING)
                .addScalar("price",Hibernate.STRING)
                .addScalar("itemCode",Hibernate.STRING)
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
                .addScalar("color",Hibernate.STRING)
                .addScalar("lastUpdate",Hibernate.DATE)
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

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lst;
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

    public Mobile getMobile() {
        return mobile;
    }

    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getWebs() {
        return webs;
    }

    public void setWebs(List<String> webs) {
        this.webs = webs;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<ComboBox> getBackCameraClass() {
        return backCameraClass;
    }

    public void setBackCameraClass(List<ComboBox> backCameraClass) {
        this.backCameraClass = backCameraClass;
    }

    public List<ComboBox> getBatteryClass() {
        return batteryClass;
    }

    public void setBatteryClass(List<ComboBox> batteryClass) {
        this.batteryClass = batteryClass;
    }

    public List<ComboBox> getCpus() {
        return cpus;
    }

    public void setCpus(List<ComboBox> cpus) {
        this.cpus = cpus;
    }

    public List<ComboBox> getFrontCameraClass() {
        return frontCameraClass;
    }

    public void setFrontCameraClass(List<ComboBox> frontCameraClass) {
        this.frontCameraClass = frontCameraClass;
    }

    public List<ComboBox> getLstWeb() {
        return lstWeb;
    }

    public void setLstWeb(List<ComboBox> lstWeb) {
        this.lstWeb = lstWeb;
    }

    public List<ComboBox> getOss() {
        return oss;
    }

    public void setOss(List<ComboBox> oss) {
        this.oss = oss;
    }

    public List<ComboBox> getPriceClass() {
        return priceClass;
    }

    public void setPriceClass(List<ComboBox> priceClass) {
        this.priceClass = priceClass;
    }

    public List<ComboBox> getRamClass() {
        return ramClass;
    }

    public void setRamClass(List<ComboBox> ramClass) {
        this.ramClass = ramClass;
    }

    public List<ComboBox> getScreenClass() {
        return screenClass;
    }

    public void setScreenClass(List<ComboBox> screenClass) {
        this.screenClass = screenClass;
    }

    public List<ComboBox> getSims() {
        return sims;
    }

    public void setSims(List<ComboBox> sims) {
        this.sims = sims;
    }

    public List<ComboBox> getSpeedClass() {
        return speedClass;
    }

    public void setSpeedClass(List<ComboBox> speedClass) {
        this.speedClass = speedClass;
    }

    public List<ComboBox> getStorageClass() {
        return storageClass;
    }

    public void setStorageClass(List<ComboBox> storageClass) {
        this.storageClass = storageClass;
    }
    
}
