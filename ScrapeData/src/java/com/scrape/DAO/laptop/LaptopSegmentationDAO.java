/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.DAO.laptop;
import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.DAO.ComboBoxDAO;
import com.scrape.client.form.ComboBox;
import com.scrape.client.form.Laptop;
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
public class LaptopSegmentationDAO extends BaseHibernateDAOMDB{
    private DojoJSON jsonDataGrid = new DojoJSON();
    private int startval;
    private int count;
    private String sort;
    private String result;
    private Laptop laptop = new Laptop();
    private String check;
    private String date;
    private List<String> webs = new ArrayList<String>();
    ArrayList param = new ArrayList();
    private InputStream inputStream;
    private String filename;

    List<ComboBox> lstWeb = new ArrayList<ComboBox>();
    List<ComboBox> storageClass = new ArrayList<ComboBox>();
    List<ComboBox> rams = new ArrayList<ComboBox>();
    List<ComboBox> chips = new ArrayList<ComboBox>();
    List<ComboBox> speedClass = new ArrayList<ComboBox>();
    List<ComboBox> screenClass = new ArrayList<ComboBox>();

    List<ComboBox> hddTypes = new ArrayList<ComboBox>();
    List<ComboBox> vgas = new ArrayList<ComboBox>();
    List<ComboBox> touchscreens = new ArrayList<ComboBox>();
    List<ComboBox> oss = new ArrayList<ComboBox>();
    List<ComboBox> dvds = new ArrayList<ComboBox>();
    List<ComboBox> priceClass = new ArrayList<ComboBox>();

    public String prepare(){
        ComboBoxDAO c = new ComboBoxDAO();
        lstWeb = c.takeWebNotRoot(rb.getString(Constans.WEB_ROOT));
        storageClass = c.combobox("StorageClass","Laptop");
        rams = c.combobox("Ram","Laptop");
        chips = c.combobox("Chip","Laptop");
        speedClass = c.combobox("SpeedClass","Laptop");
        screenClass = c.combobox("ScreenClass","Laptop");
        hddTypes = c.combobox("HDDType","Laptop");
        vgas = c.combobox("VGA","Laptop");
        touchscreens = c.combobox("TouchScreen","Laptop");
        oss = c.combobox("OS","Laptop");
        dvds = c.combobox("DVD","Laptop");
        priceClass = c.combobox("PriceClass","Laptop");
        return SUCCESS;
    }

    public String takeSQL(String type){
        param.clear();
        StringBuilder sql = new StringBuilder();
        if("count".equals(type)){
            sql.append(" select count(*) ");
        }else{
            sql.append(" SELECT link,WEB, TYPE, BRAND,PRICE,ITEM_CODE itemCode,NAME,model,chip,speed,STORAGE,hdd_Type hddType,RAM,VGA,SCREEN,touchscreen,"
                    + " os,dvd,BATTERY,promotion ,a.LAST_UPDATE lastUpdate ");
        }
        sql.append(" FROM LAPTOP_DATA a,  laptop_configuration b ");
        sql.append(" where a.ID = b.LAPTOP_DATA_ID ");
        
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
        
        if(laptop != null){
            if( laptop.getChip() != null && !"".equals(laptop.getChip())){
                sql.append(" and chip = ? ");
                param.add(laptop.getChip());
            }

            if( laptop.getSpeed() != null && !"".equals(laptop.getSpeed())){
                if(laptop.getSpeed().indexOf("and") >= 0){
                    sql.append(" and convert(float,speed) between ");
                }else{
                    sql.append(" and convert(float,speed) ");

                }
                sql.append(laptop.getSpeed());
            }
            
            if( laptop.getStorage() != null && !"".equals(laptop.getStorage())){
                sql.append(" and convert(float,storage) ");
                sql.append(laptop.getStorage());
//                param.add(laptop.getStorage());
            }
            if( laptop.getHddType() != null && !"".equals(laptop.getHddType())){
                sql.append(" and hdd_type = ? ");
                param.add(laptop.getHddType());
            }
            if( laptop.getRam() != null && !"".equals(laptop.getRam())){
                sql.append(" and ram = ? ");
                param.add(laptop.getRam());
            }
            if( laptop.getVga() != null && !"".equals(laptop.getVga())){
                sql.append(" and vga = ? ");
                param.add(laptop.getVga());
            }
            
            if( laptop.getScreen() != null && !"".equals(laptop.getScreen())){
                if(laptop.getScreen().indexOf("and") >= 0){
                    sql.append(" and convert(float,screen) between ");
                }else{
                    sql.append(" and convert(float,screen) ");
                }
                sql.append(laptop.getScreen());
            }

            if( laptop.getTouchscreen() != null && !"".equals(laptop.getTouchscreen())){
                sql.append(" and touchscreen = ? ");
                param.add(laptop.getTouchscreen());
            }

            if( laptop.getOs() != null && !"".equals(laptop.getOs())){
                sql.append(" and os = ? ");
                param.add(laptop.getOs());
            }

            if( laptop.getDvd() != null && !"".equals(laptop.getDvd())){
                sql.append(" and dvd = ? ");
                param.add(laptop.getDvd());
            }
            if( laptop.getBattery() != null && !"".equals(laptop.getBattery())){
                sql.append(" and battery = ? ");
                param.add(laptop.getBattery());
            }

            if( laptop.getPrice() != null && !"".equals(laptop.getPrice())){
                if(laptop.getPrice().indexOf("and") >= 0){
                    sql.append(" and CONVERT(float, price_number/1000000) between ");
                }else{
                    sql.append(" and CONVERT(float, price_number/1000000) ");
                }
                sql.append(laptop.getPrice());
            }
        }
        
        return sql.toString();
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

    public List<Laptop> takeLaptops() {
        List<Laptop> lst = null;
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

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
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

    public List<ComboBox> getRams() {
        return rams;
    }

    public void setRams(List<ComboBox> rams) {
        this.rams = rams;
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

    public List<ComboBox> getPriceClass() {
        return priceClass;
    }

    public void setPriceClass(List<ComboBox> priceClass) {
        this.priceClass = priceClass;
    }

}
