/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.DAO.laptop;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class LaptopModelNewDAO extends BaseHibernateDAOMDB{
     private DojoJSON jsonDataGrid = new DojoJSON();
    private int startval;
    private int count;
    private String sort;
    private String result;
    private Laptop laptop = new Laptop();
    private String fromDate;
    private String toDate;
    private InputStream inputStream;
    private String filename;
    List<ComboBox> webs = new ArrayList<ComboBox>();
    List<ComboBox> brands = new ArrayList<ComboBox>();
    
    public String prepare(){
        ComboBoxDAO c = new ComboBoxDAO();
        webs = c.combobox("web","All");
        brands = c.combobox("Brand","Laptop");
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
            sql.append(" Select a.id,b.id id1, link,web,type,brand,price,price_number priceNumber ,name,model, ");
            sql.append(" chip,SPEED, storage,HDD_TYPE hddType, ");
            sql.append(" ram, vga,screen, ");
            sql.append(" touchscreen, os, dvd, ");
            sql.append(" battery,PROMOTION, a.last_Update lastUpdate, a.create_date createDate");
            sql.append(" from LAPTOP_DATA a, LAPTOP_CONFIGURATION b ");
            sql.append(" WHERE a.id = b.LAPTOP_DATA_ID");

            if(laptop != null){
                if(laptop.getWeb() != null && !"".equals(laptop.getWeb())){
                    sql.append(" AND web = ? ");
                    param.add(laptop.getWeb());
                }

                if(laptop.getBrand() != null && !"".equals(laptop.getBrand())){
                    sql.append(" AND brand = ? ");
                    param.add(laptop.getBrand());
                }
            }
            
            if(fromDate != null && !"".equals(fromDate) && toDate != null && !"".equals(toDate) ){
                sql.append(" AND CAST(a.CREATE_DATE AS DATE) >= ?");
                sql.append(" AND CAST(a.CREATE_DATE AS DATE) <= ?");
                param.add(fromDate);
                param.add(toDate);
            }else{
                sql.append(" AND CAST(a.CREATE_DATE AS DATE) = CAST(getdate() AS DATE)");
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
                    .addScalar("priceNumber",Hibernate.LONG)
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
                    sql.append(" AND brand = ? ");
                    param.add(laptop.getBrand());
                }
            }
            
            if(fromDate != null && !"".equals(fromDate) && toDate != null && !"".equals(toDate) ){
                sql.append(" AND CAST(a.CREATE_DATE AS DATE) >= ?");
                sql.append(" AND CAST(a.CREATE_DATE AS DATE) <= ?");
                param.add(fromDate);
                param.add(toDate);
            }else{
                sql.append(" AND CAST(a.CREATE_DATE AS DATE) = CAST(getdate() AS DATE)");
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

    public String exportExcel(){
        try{
            filename = "LaptopModelNew.xlsx";
//            String tempFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.TEMPLATE_FILE_PATH) + filename);
            String tempFile = rb.getString(Constans.TEMPLATE_FILE_PATH) + filename;
            Random random = new Random();
//            String downFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.DOWNLOAD_FILE_PATH) + "LaptopModelNew_"+random.nextInt(1000)+".xlsx");
            String downFile = rb.getString(Constans.DOWNLOAD_FILE_PATH) + "LaptopModelNew_"+random.nextInt(1000)+".xlsx";
            FileInputStream file = new FileInputStream(new File(tempFile));
//          Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = null;
            workbook = new XSSFWorkbook(file);
            //Get first/desired sheet from the workbook
            XSSFSheet sheet1 = workbook.getSheetAt(0);
            
            startval = -1;
            count = -1;
            List<Laptop> list = new ArrayList<Laptop>();
            list = takeLaptops();
            fillData(list,workbook,sheet1);

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
                ex.formatNumber(row, j++, list.get(i).getPriceNumber(), ex.number);
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
            ex.formatDate(row, j++, list.get(i).getCreateDate(), ex.date);
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

    public List<ComboBox> getBrands() {
        return brands;
    }

    public void setBrands(List<ComboBox> brands) {
        this.brands = brands;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public List<ComboBox> getWebs() {
        return webs;
    }

    public void setWebs(List<ComboBox> webs) {
        this.webs = webs;
    }

}
