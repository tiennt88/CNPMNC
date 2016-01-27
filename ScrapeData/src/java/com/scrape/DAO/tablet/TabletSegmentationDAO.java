/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.DAO.tablet;
import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.DAO.ComboBoxDAO;
import com.scrape.client.form.ComboBox;
import com.scrape.client.form.Tablet;
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
public class TabletSegmentationDAO extends BaseHibernateDAOMDB{
    private DojoJSON jsonDataGrid = new DojoJSON();
    private int startval;
    private int count;
    private String sort;
    private String result;
    private Tablet tablet = new Tablet();
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
    List<ComboBox> backCameraClass = new ArrayList<ComboBox>();
    List<ComboBox> frontCameraClass = new ArrayList<ComboBox>();
    List<ComboBox> oss = new ArrayList<ComboBox>();
    List<ComboBox> batteryClass = new ArrayList<ComboBox>();
    List<ComboBox> priceClass = new ArrayList<ComboBox>();

    public String prepare(){
        ComboBoxDAO c = new ComboBoxDAO();
        lstWeb = c.takeWebNotRoot(rb.getString(Constans.WEB_ROOT));
        storageClass = c.combobox("StorageClass","Tablet");
        ramClass = c.combobox("RamClass","Tablet");
        cpus = c.combobox("CPU","Tablet");
        speedClass = c.combobox("SpeedClass","Tablet");
        screenClass = c.combobox("ScreenClass","Tablet");
        backCameraClass = c.combobox("BackCameraClass","Tablet");
        frontCameraClass = c.combobox("FrontCameraClass","Tablet");
        oss = c.combobox("OS","Tablet");
        batteryClass = c.combobox("BatteryClass","Tablet");
        priceClass = c.combobox("PriceClass","Tablet");
        return SUCCESS;
    }

    public String takeSQL(String type){
        param.clear();
        StringBuilder sql = new StringBuilder();
        if("count".equals(type)){
            sql.append(" select count(*) ");
        }else{
            sql.append(" SELECT link,WEB, TYPE, BRAND,PRICE,ITEM_CODE itemCode,NAME,model,STORAGE,RAM,SCREEN,CPU,B_CAMERA backCamera,"
                    + " F_CAMERA frontCamera,os,BATTERY,SIM, a.LAST_UPDATE lastUpdate ");
        }
        sql.append(" FROM TABLET_DATA a,  tablet_configuration b ");
        sql.append(" where a.ID = b.TABLET_DATA_ID ");
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
        
        if(tablet != null){
            if( tablet.getStorage() != null && !"".equals(tablet.getStorage())){
                sql.append(" and convert(float,storage) ");
                sql.append(tablet.getStorage());
//                param.add(tablet.getStorage());
            }
            if( tablet.getRam() != null && !"".equals(tablet.getRam())){
                sql.append(" and convert(float,ram) ");
                sql.append(tablet.getRam());
//                param.add(tablet.getRam());
            }
            if( tablet.getCpu() != null && !"".equals(tablet.getCpu())){
                sql.append(" and cpu = ? ");
                param.add(tablet.getCpu());
            }
            if( tablet.getSpeed() != null && !"".equals(tablet.getSpeed())){
                if(tablet.getSpeed().indexOf("and") >= 0){
                    sql.append(" and convert(float,speed) between ");
                }else{
                    sql.append(" and convert(float,speed) ");

                }
                sql.append(tablet.getSpeed());
//                param.add(tablet.getSpeed());
            }
            if( tablet.getScreen() != null && !"".equals(tablet.getScreen())){
                if(tablet.getScreen().indexOf("and") >= 0){
                    sql.append(" and convert(float,screen) between ");
                }else{
                    sql.append(" and convert(float,screen) ");
                }
                sql.append(tablet.getScreen());
//                param.add(tablet.getScreen());
            }
            if( tablet.getSim() != null && !"".equals(tablet.getSim())){
                sql.append(" and sim = ? ");
                param.add(tablet.getSim());
            }
            if( tablet.getBackCamera() != null && !"".equals(tablet.getBackCamera())){
                sql.append(" and convert(float, b_camera) ");
                sql.append(tablet.getBackCamera());
//                param.add(tablet.getBackCamera());
            }
            if( tablet.getFrontCamera() != null && !"".equals(tablet.getFrontCamera())){
                sql.append(" and convert(float, f_camera) ");
                sql.append(tablet.getFrontCamera());
//                param.add(tablet.getFrontCamera());
            }
            if( tablet.getOs() != null && !"".equals(tablet.getOs())){
                sql.append(" and os = ? ");
                param.add(tablet.getOs());
            }
            if( tablet.getBattery() != null && !"".equals(tablet.getBattery())){
                if(tablet.getBattery().indexOf("and") >= 0){
                    sql.append(" and convert(float, battery) between ");
                }else{
                    sql.append(" and convert(float, battery) ");
                }
                sql.append(tablet.getBattery());
//                param.add(tablet.getBattery());
            }
            if( tablet.getPrice() != null && !"".equals(tablet.getPrice())){
                if(tablet.getPrice().indexOf("and") >= 0){
                    sql.append(" and CONVERT(float, price_number/1000000) between ");
                }else{
                    sql.append(" and CONVERT(float, price_number/1000000) ");
                }
                sql.append(tablet.getPrice());
//                param.add(tablet.getPrice());
            }
            
        }
        return sql.toString();
    }

    public String onSearch() {
        try {
//            UserToken userToken = (UserToken) getRequest().getSession().getAttribute("userToken");
            setMaxResult();
            List<Tablet> lstResult = new ArrayList<Tablet>();
            lstResult = takeTablets();

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

    public List<Tablet> takeTablets() {
        List<Tablet> lst = null;
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

    public Tablet getTablet() {
        return tablet;
    }

    public void setTablet(Tablet tablet) {
        this.tablet = tablet;
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
