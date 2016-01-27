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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
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
public class LaptopPriceHistoryDAO extends BaseHibernateDAOMDB{
    private DojoJSON jsonDataGrid = new DojoJSON();
    private Laptop laptop = new Laptop();
    private int startval;
    private int count;
    private String sort;
    private String mapping;
    private String result;
    private List<Laptop> listLaptop = new ArrayList<Laptop>();
    private String fromDate;
    private String toDate;
    private InputStream inputStream;
    private String filename;

    List<ComboBox> webs = new ArrayList<ComboBox>();
    List<ComboBox> brands = new ArrayList<ComboBox>();
    List<ComboBox> storages = new ArrayList<ComboBox>();
    List<ComboBox> rams = new ArrayList<ComboBox>();
    List<ComboBox> chips = new ArrayList<ComboBox>();
    List<ComboBox> vgas = new ArrayList<ComboBox>();

    public String prepare(){
        ComboBoxDAO c = new ComboBoxDAO();
        webs = c.combobox("Web","All");
        brands = c.combobox("Brand","Laptop");
        storages = c.combobox("Storage","Laptop");
        rams = c.combobox("Ram","Laptop");
        chips = c.combobox("Chip","Laptop");
        vgas = c.combobox("VGA","Laptop");
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
            sql.append(" Select a.id,b.id id1, link,web,type,brand,price,name,model, item_code itemCode, partno, ");
            sql.append(" chip,SPEED, storage,HDD_TYPE hddType, ");
            sql.append(" ram, vga,screen,");
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

                if(laptop.getItemCode() != null && !"".equals(laptop.getItemCode())){
                    sql.append(" AND itemCode = ? ");
                    param.add(laptop.getItemCode());
                }

                if(laptop.getPartno() != null && !"".equals(laptop.getPartno())){
                    sql.append(" AND lower(partno) like ? ");
                    param.add(laptop.getPartno().toLowerCase());
                }

                if(laptop.getChip() != null && !"".equals(laptop.getChip())){
                    if("null".equals(laptop.getChip())){
                        sql.append(" AND chip is null ");
                    }else{
                        sql.append(" AND chip = ?");
                        param.add(laptop.getChip());
                    }
                }
                
                if(laptop.getStorage() != null && !"".equals(laptop.getStorage())){
                    if("null".equals(laptop.getStorage())){
                        sql.append(" AND storage is null ");
                    }else{
                        sql.append(" AND storage = ?");
                        param.add(laptop.getStorage());
                    }
                }

                if(laptop.getRam() != null && !"".equals(laptop.getRam())){
                    if("null".equals(laptop.getRam())){
                        sql.append(" AND ram is null ");
                    }else{
                        sql.append(" AND ram = ?");
                        param.add(laptop.getRam());
                    }
                }

                if(laptop.getVga() != null && !"".equals(laptop.getVga())){
                    if("null".equals(laptop.getVga())){
                        sql.append(" AND vga is null ");
                    }else{
                        sql.append(" AND vga = ?");
                        param.add(laptop.getVga());
                    }
                }

            }
            
            if(fromDate != null && !"".equals(fromDate)){
                sql.append(" AND CAST(a.LAST_UPDATE as DATE) >= ?");
                param.add(fromDate);
            }
            
            if(toDate != null && !"".equals(toDate)){
                sql.append(" AND CAST(a.LAST_UPDATE as DATE) <= ?");
                param.add(toDate);
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
                    .addScalar("itemCode",Hibernate.STRING)
                    .addScalar("partno",Hibernate.STRING)
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

                if(laptop.getItemCode() != null && !"".equals(laptop.getItemCode())){
                    sql.append(" AND itemCode = ? ");
                    param.add(laptop.getItemCode());
                }

                if(laptop.getPartno() != null && !"".equals(laptop.getPartno())){
                    sql.append(" AND lower(partno) like ? ");
                    param.add(laptop.getPartno().toLowerCase());
                }

                if(laptop.getChip() != null && !"".equals(laptop.getChip())){
                    if("null".equals(laptop.getChip())){
                        sql.append(" AND chip is null ");
                    }else{
                        sql.append(" AND chip = ? ");
                        param.add(laptop.getChip());
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

                if(laptop.getStorage() != null && !"".equals(laptop.getStorage())){
                    if("null".equals(laptop.getStorage())){
                        sql.append(" AND storage is null ");
                    }else{
                        sql.append(" AND storage = ? ");
                        param.add(laptop.getStorage());
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
                
            }

            if(fromDate != null && !"".equals(fromDate)){
                sql.append(" AND CAST(a.LAST_UPDATE as DATE) >= ?");
                param.add(fromDate);
            }

            if(toDate != null && !"".equals(toDate)){
                sql.append(" AND CAST(a.LAST_UPDATE as DATE) <= ?");
                param.add(toDate);
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
            System.out.println(this.mapping);
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

    public String priceHistory(){
        try{
            if(listLaptop != null && listLaptop.size() > 0){
                filename = "LaptopPriceHistory.xlsx";
//                String tempFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.TEMPLATE_FILE_PATH) + filename);
                String tempFile = rb.getString(Constans.TEMPLATE_FILE_PATH) + filename;
                Random random = new Random();
//                String downFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.DOWNLOAD_FILE_PATH) + "LaptopPriceHistory_"+random.nextInt(1000)+".xlsx");
                String downFile = rb.getString(Constans.DOWNLOAD_FILE_PATH) + "LaptopPriceHistory_"+random.nextInt(1000)+".xlsx";
                FileInputStream file = new FileInputStream(new File(tempFile));
    //          Create Workbook instance holding reference to .xlsx file
                XSSFWorkbook workbook = null;
                workbook = new XSSFWorkbook(file);
                //Get first/desired sheet from the workbook
                XSSFSheet sheet1 = workbook.getSheetAt(0);
                //print infomation in sheet1
                fillData(this.listLaptop,workbook,sheet1);
                XSSFSheet sheet2 = workbook.getSheetAt(1);
                //print price
                StringBuilder sql = new StringBuilder();
                String listID1 = "";
                String listID2 = "";
                ArrayList param = new ArrayList();
                
                //print header
                XSSFCellStyle style1 = workbook.createCellStyle();
                style1.setFillForegroundColor(HSSFColor.AQUA.index);
                style1.setFillPattern(CellStyle.SOLID_FOREGROUND);

                XSSFCreationHelper createHelper = workbook.getCreationHelper();
                XSSFCellStyle style2 = workbook.createCellStyle();
                style2.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
                
                Row row = sheet2.createRow(0);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue("Date");
                cell1.setCellStyle(style1);
                for(int i=0;i<listLaptop.size();i++){
                    Cell cell = row.createCell(i+1);
                    cell.setCellValue(listLaptop.get(i).getId() + "("+listLaptop.get(i).getWeb()+ " - "+ listLaptop.get(i).getName() +")");
                    cell.setCellStyle(style1);
                    
                    if(i==listLaptop.size()-1){
                        listID1 = listID1 + listLaptop.get(i).getId() ;
                        listID2 = listID2 + "[" + listLaptop.get(i).getId() +"]" ;
                    }else{
                        listID1 = listID1 + listLaptop.get(i).getId() +",";
                        listID2 = listID2 + "["+ listLaptop.get(i).getId() +"],";
                    }
                }

                sql.append(" select * from ");
                sql.append(" (select price_number,LAPTOP_DATA_ID,convert(nvarchar,LAST_UPDATE,103) LAST_UPDATE ");
                sql.append(" from laptop_price where LAPTOP_DATA_ID in (").append(listID1).append(")");
                if(fromDate != null && !"".equals(fromDate)){
                    sql.append(" AND CAST(LAST_UPDATE as DATE) >= ?");
                    param.add(fromDate);
                }

                if(toDate != null && !"".equals(toDate)){
                    sql.append(" AND CAST(LAST_UPDATE as DATE) <= ?");
                    param.add(toDate);
                }
                sql.append(" ) a ");
                sql.append(" pivot( ");
                sql.append(" min(price_number) ");
                sql.append(" for LAPTOP_DATA_ID in (").append(listID2).append(") ");
                sql.append(" ) b ");
                sql.append(" order by convert(date,LAST_UPDATE,103) desc ");
                
                SQLQuery query = getSession().createSQLQuery(sql.toString());
                for (int i = 0; i < param.size(); i++) {
                    query.setParameter(i, param.get(i));
                }
                List list = query.list();
                int rowIndex = 1;
                
                for(Object object : list) {
                    Object[] obj = (Object[])object;
                    Row row1 = sheet2.createRow(rowIndex);
                    int cellIndex = 0;
                    for(Object o : obj ){
                        if(o != null){
                            Cell cell = row1.createCell(cellIndex);
                            cell.setCellValue(o.toString());
                        }
                        cellIndex++;
                    }
                    rowIndex++;
                }

//                ByteArrayOutputStream boas = new ByteArrayOutputStream();
//                workbook.write(boas);
//                setInputStream(new ByteArrayInputStream(boas.toByteArray()));

                FileOutputStream out = new FileOutputStream(new File(downFile));
                workbook.write(out);
                out.close();
                getRequest().setAttribute("attachFile", downFile);
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return SUCCESS;
    }
    
    public void fillData(List<Laptop> list, XSSFWorkbook workbook ,XSSFSheet sheet ){
        int j=0;
        int rowIndex = 3;
        int stt = 1;
//        XSSFCellStyle style1 = workbook.createCellStyle();
//        style1.setFillForegroundColor(HSSFColor.AQUA.index);
//        style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
//
//        XSSFCreationHelper createHelper = workbook.getCreationHelper();
//        XSSFCellStyle style2 = workbook.createCellStyle();
//        style2.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        for(int i = 0 ; i< list.size(); i++){
            Row row = sheet.createRow(rowIndex++);
            j=0;
            row.createCell(j++).setCellValue(stt++);
            row.createCell(j++).setCellValue(list.get(i).getId());
            row.createCell(j++).setCellValue(list.get(i).getWeb());
            row.createCell(j++).setCellValue(list.get(i).getType());
            row.createCell(j++).setCellValue(list.get(i).getBrand());
            row.createCell(j++).setCellValue(list.get(i).getPrice());
            row.createCell(j++).setCellValue(list.get(i).getPartno());
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

    public List<Laptop> getListLaptop() {
        return listLaptop;
    }

    public void setListLaptop(List<Laptop> listLaptop) {
        this.listLaptop = listLaptop;
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

    public List<ComboBox> getChips() {
        return chips;
    }

    public void setChips(List<ComboBox> chips) {
        this.chips = chips;
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
