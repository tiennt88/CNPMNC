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
import com.scrape.common.ExcelUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
public class LaptopModelCompareDAO extends BaseHibernateDAOMDB{
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
    
    public String prepare(){
        ComboBoxDAO c = new ComboBoxDAO();
        lstWeb = c.takeWebNotRoot(rb.getString(Constans.WEB_ROOT));
        return SUCCESS;
    }

    public String takeSQL(String type){
        param.clear();
        StringBuilder sql = new StringBuilder();
        if("count".equals(type)){
            sql.append(" select count(*) from ");
        }else{
            if(check != null && "on".equals(check)){
                sql.append(" select x.* from ");
            }else{
                sql.append(" select y.* from ");
            }
        }
        sql.append(" (select a.id,WEB,TYPE,BRAND,LINK,ITEM_CODE itemCode,NAME,MODEL,PRICE, price_number priceNumber,");
        sql.append(" chip,SPEED, storage,HDD_TYPE hddType, ");
        sql.append(" ram, vga,screen, ");
        sql.append(" touchscreen, os, dvd, ");
        sql.append(" battery,PROMOTION, a.last_update lastUpdate from LAPTOP_DATA a, LAPTOP_CONFIGURATION b ");
        sql.append(" where a.ID = b.LAPTOP_DATA_ID and a.WEB = '").append(rb.getString(Constans.WEB_ROOT)).append("' ");
        if(date != null && !"".equals(date)){
            sql.append(" AND CAST(a.LAST_UPDATE as DATE) = ?");
            param.add(date);
        }else{
            sql.append(" AND CAST(a.LAST_UPDATE AS DATE) = CAST(getdate() AS DATE)");
        }
        sql.append(") x ");
        sql.append(" full join ");
        sql.append(" (select c.id,WEB,TYPE,BRAND,LINK,ITEM_CODE itemCode,NAME,MODEL,PRICE,price_Number priceNumber,");
        sql.append(" chip,SPEED, storage,HDD_TYPE hddType, ");
        sql.append(" ram, vga, screen, ");
        sql.append(" touchscreen, os, dvd, ");
        sql.append(" battery,PROMOTION, c.last_update lastUpdate from  LAPTOP_DATA c, LAPTOP_CONFIGURATION d ");
        sql.append(" where c.ID = d.LAPTOP_DATA_ID and c.WEB != '").append(rb.getString(Constans.WEB_ROOT)).append("' ");
        if(webs != null && webs.size() > 0){
            String listWeb = "'";
            for(int i=0;i<webs.size();i++){
                if(i==webs.size()-1){
                    listWeb = listWeb + webs.get(i) +"'";
                }else{
                    listWeb = listWeb + webs.get(i) +"','";
                }
                
            }
            System.out.println(listWeb);
            sql.append(" and c.WEB in (").append(listWeb).append(") ");
        }
        if(date != null && !"".equals(date)){
            sql.append(" AND CAST(c.LAST_UPDATE AS DATE) = ?");
            param.add(date);
        }else{
            sql.append(" AND CAST(c.LAST_UPDATE AS DATE) = CAST(getdate() AS DATE)");
        }
        sql.append(" ) y ");
        sql.append(" on x.BRAND = y.BRAND and x.MODEL = y.MODEL   ");

        if(laptop != null){
            if( laptop.getStorage() != null && "on".equals(laptop.getStorage()) ){
                sql.append(" and isnull(x.STORAGE,'') = isnull(y.STORAGE,'') ");
            }
            if( laptop.getRam() != null && "on".equals(laptop.getRam()) ){
                sql.append(" and isnull(x.RAM,'') = isnull(y.RAM,'') ");
            }
            if( laptop.getChip() != null && "on".equals(laptop.getChip()) ){
                sql.append(" and isnull(x.chip,'') = isnull(y.chip,'') ");
            }
            if( laptop.getVga() != null && "on".equals(laptop.getVga()) ){
                sql.append(" and isnull(x.VGA,'') = isnull(y.VGA,'') ");
            }
            if( laptop.getScreen() != null && "on".equals(laptop.getScreen()) ){
                sql.append(" and isnull(x.screen,'') = isnull(y.screen,'') ");
            }
        }
        
        if(check != null && "on".equals(check)){
            sql.append(" where y.ID is null ");
        }else{
            sql.append(" where x.ID is null ");
        }
//        System.out.println(sql.toString());
        
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
                .addScalar("id",Hibernate.LONG)
                .addScalar("link",Hibernate.STRING)
                .addScalar("web",Hibernate.STRING)
                .addScalar("type",Hibernate.STRING)
                .addScalar("brand",Hibernate.STRING)
                .addScalar("price",Hibernate.STRING)
                .addScalar("priceNumber",Hibernate.LONG)
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

    public String exportExcel(){
        try{
            filename = "LaptopCompareModel.xlsx";
//            String tempFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.TEMPLATE_FILE_PATH) + filename);
            String tempFile = rb.getString(Constans.TEMPLATE_FILE_PATH) + filename;
            Random random = new Random();
//            String downFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.DOWNLOAD_FILE_PATH) + "LaptopCompareModel_"+random.nextInt(1000)+".xlsx");
            String downFile = rb.getString(Constans.DOWNLOAD_FILE_PATH) + "LaptopCompareModel_"+random.nextInt(1000)+".xlsx";
            FileInputStream file = new FileInputStream(new File(tempFile));
//          Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = null;
            workbook = new XSSFWorkbook(file);
            //Get first/desired sheet from the workbook
            XSSFSheet sheet1 = workbook.getSheetAt(0);
            XSSFSheet sheet2 = workbook.getSheetAt(1);

            startval = -1;
            count = -1;
            check = "off";
            List<Laptop> list1 = new ArrayList<Laptop>();
            list1 = takeLaptops();
            fillData(list1,workbook,sheet1);
            check = "on";
            List<Laptop> list2 = new ArrayList<Laptop>();
            list2 = takeLaptops();
            fillData(list2,workbook,sheet2);

//            ByteArrayOutputStream boas = new ByteArrayOutputStream();
//            workbook.write(boas);
//            setInputStream(new ByteArrayInputStream(boas.toByteArray()));

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
            row.createCell(j++).setCellValue(list.get(i).getWeb());
            row.createCell(j++).setCellValue(list.get(i).getType());
            row.createCell(j++).setCellValue(list.get(i).getBrand());
            if(list.get(i).getPriceNumber()!=null){
                row.createCell(j++).setCellValue(list.get(i).getPriceNumber());
            }else{
                j++;
            }
            row.createCell(j++).setCellValue(list.get(i).getItemCode());
            row.createCell(j++).setCellValue(list.get(i).getName());
            row.createCell(j++).setCellValue(list.get(i).getModel());
            row.createCell(j++).setCellValue(list.get(i).getChip());
            row.createCell(j++).setCellValue(list.get(i).getSpeed());
            row.createCell(j++).setCellValue(list.get(i).getStorage());
            row.createCell(j++).setCellValue(list.get(i).getHddType());
            row.createCell(j++).setCellValue(list.get(i).getRam());
            row.createCell(j++).setCellValue(list.get(i).getVga());
            row.createCell(j++).setCellValue(list.get(i).getScreen());
            row.createCell(j++).setCellValue(list.get(i).getTouchscreen());
            row.createCell(j++).setCellValue(list.get(i).getOs());
            row.createCell(j++).setCellValue(list.get(i).getDvd());
            row.createCell(j++).setCellValue(list.get(i).getBattery());
            row.createCell(j++).setCellValue(list.get(i).getPromotion());
            ex.formatDate(row, j++, list.get(i).getLastUpdate(), ex.date);
            row.createCell(j++).setCellValue(list.get(i).getLink());
        }
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

    public List<ComboBox> getLstWeb() {
        return lstWeb;
    }

    public void setLstWeb(List<ComboBox> lstWeb) {
        this.lstWeb = lstWeb;
    }

}
