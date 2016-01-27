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
import com.scrape.common.ExcelUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
public class TabletDataDAO extends BaseHibernateDAOMDB{
    private DojoJSON jsonDataGrid = new DojoJSON();
    private Tablet tablet = new Tablet();
    private int startval;
    private int count;
    private String sort;
    private String result;
    List<ComboBox> webs = new ArrayList<ComboBox>();
    List<ComboBox> brands = new ArrayList<ComboBox>();
    List<ComboBox> storages = new ArrayList<ComboBox>();
    List<ComboBox> rams = new ArrayList<ComboBox>();
    
    public String prepare(){
        ComboBoxDAO c = new ComboBoxDAO();
        webs = c.combobox("Web","All");
        brands = c.combobox("Brand","Tablet");
        storages = c.combobox("Storage","Tablet");
        rams = c.combobox("Ram","Tablet");
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
            sql.append(" Select a.id,b.id id1, a.link,a.web,a.type,a.brand,a.price,a.name,b.model, ");
            sql.append(" storage,ram,screen ,cpu ,b_camera backCamera, ");
            sql.append(" f_camera frontCamera, os,battery, ");
            sql.append(" sim, a.promotion, a.last_Update lastUpdate, a.create_date createDate, approve");
            sql.append(" from TABLET_DATA a, TABLET_CONFIGURATION b ");
            sql.append(" WHERE a.id = b.TABLET_DATA_ID");

            if(tablet != null){
                if(tablet.getWeb() != null && !"".equals(tablet.getWeb())){
                    sql.append(" AND web = ? ");
                    param.add(tablet.getWeb());
                }

                if(tablet.getBrand() != null && !"".equals(tablet.getBrand())){
                    if("null".equals(tablet.getBrand())){
                        sql.append(" AND brand is null ");
                    }else{
                        sql.append(" AND brand = ? ");
                        param.add(tablet.getBrand());
                    }

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
                        param.add(tablet.getModel().toLowerCase());
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
                
                if(tablet.getRam() != null && !"".equals(tablet.getRam())){
                    if("null".equals(tablet.getRam())){
                        sql.append(" AND ram is null ");
                    }else{
                        sql.append(" AND ram = ? ");
                        param.add(tablet.getRam());
                    }
                }

                if(tablet.getApprove() != null && "on".equals(tablet.getApprove())){
                    sql.append(" and approve = 'N' ");
                }
                if(tablet.getToday() != null && "on".equals(tablet.getToday())){
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
                    .addScalar("storage",Hibernate.STRING)
                    .addScalar("ram",Hibernate.STRING)
                    .addScalar("screen",Hibernate.STRING)
                    .addScalar("cpu",Hibernate.STRING)
                    .addScalar("backCamera",Hibernate.STRING)
                    .addScalar("frontCamera",Hibernate.STRING)
                    .addScalar("os",Hibernate.STRING)
                    .addScalar("battery",Hibernate.STRING)
                    .addScalar("sim",Hibernate.STRING)
                    .addScalar("promotion",Hibernate.STRING)
                    .addScalar("lastUpdate",Hibernate.DATE)
                    .addScalar("approve",Hibernate.STRING)
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
            StringBuilder sql = new StringBuilder("select count(*) from TABLET_DATA a, TABLET_CONFIGURATION b where a.ID = b.TABLET_DATA_ID AND 1=1 ");
            ArrayList param = new ArrayList();
            if(tablet != null){
                if(tablet.getWeb() != null && !"".equals(tablet.getWeb())){
                    sql.append(" AND web = ? ");
                    param.add(tablet.getWeb());
                }

                if(tablet.getBrand() != null && !"".equals(tablet.getBrand())){
                    if("null".equals(tablet.getBrand())){
                        sql.append(" AND brand is null ");
                    }else{
                        sql.append(" AND brand = ? ");
                        param.add(tablet.getBrand());
                    }
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
                        param.add(tablet.getModel().toLowerCase());
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
                
                if(tablet.getRam() != null && !"".equals(tablet.getRam())){
                    if("null".equals(tablet.getRam())){
                        sql.append(" AND ram is null ");
                    }else{
                        sql.append(" AND ram = ? ");
                        param.add(tablet.getRam());
                    }
                }

                if(tablet.getApprove() != null && "on".equals(tablet.getApprove())){
                    sql.append(" and approve = 'N' ");
                }
                if(tablet.getToday() != null && "on".equals(tablet.getToday())){
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
            if( id1 != null && 1 > 0){
                TabletConfiguration temp1 = new TabletConfiguration();
                temp1 = (TabletConfiguration) session.get(TabletConfiguration.class, id1);
                temp1.setModel(tablet.getModel());
                if("null".equals(tablet.getRam())){
                    tablet.setRam(null);
                }
                temp1.setRam(tablet.getRam());
                if("null".equals(tablet.getStorage())){
                    tablet.setStorage(null);
                }
                if(tablet.getApprove() != null && "on".equals(tablet.getApprove())){
                    temp1.setApprove("N");
                }else{
                    temp1.setApprove("Y");
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

    public String tabletAnalysis(){
        CallableStatement cs = null;
        Connection conn  = null;
        try {
            conn = getSession().connection();
            //call store insight
            String sql = "{call TABLET_ANALYSIS (?)}";
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

    public List<Tablet> takeTabletsReport() {
        List<Tablet> lst = null;
        ArrayList param = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" Select a.id,b.id id1,link,web,type,brand,PRICE_NUMBER priceNumber,name,model, item_code itemCode, partno,");
            sql.append(" storage_text storageText,storage,ram_text ramText, ram,screen_text screenText, screen ,cpu_text cpuText, cpu, speed ,b_camera_text backCameraText,b_camera backCamera , ");
            sql.append(" f_camera_text frontCameraText, f_camera frontCamera, os_text osText, os,battery_text batteryText, battery,color, ");
            sql.append(" sim_text simText, sim, a.last_Update lastUpdate, approve");
            sql.append(" from TABLET_DATA a, TABLET_CONFIGURATION b ");
            sql.append(" WHERE a.id = b.TABLET_DATA_ID ");

            if(tablet != null){
                if(tablet.getWeb() != null && !"".equals(tablet.getWeb())){
                    sql.append(" AND web = ? ");
                    param.add(tablet.getWeb());
                }

                if(tablet.getBrand() != null && !"".equals(tablet.getBrand())){
                    if("null".equals(tablet.getBrand())){
                        sql.append(" AND brand is null ");
                    }else{
                        sql.append(" AND brand = ? ");
                        param.add(tablet.getBrand());
                    }

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
                        param.add(tablet.getModel().toLowerCase());
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

                if(tablet.getRam() != null && !"".equals(tablet.getRam())){
                    if("null".equals(tablet.getRam())){
                        sql.append(" AND ram is null ");
                    }else{
                        sql.append(" AND ram = ? ");
                        param.add(tablet.getRam());
                    }
                }


                if(tablet.getApprove() != null && "on".equals(tablet.getApprove())){
                    sql.append(" and approve = 'N' ");
                }
                if(tablet.getToday() != null && "on".equals(tablet.getToday())){
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
                    .addScalar("storageText",Hibernate.STRING)
                    .addScalar("storage",Hibernate.STRING)
                    .addScalar("ramText",Hibernate.STRING)
                    .addScalar("ram",Hibernate.STRING)
                    .addScalar("screenText",Hibernate.STRING)
                    .addScalar("screen",Hibernate.STRING)
                    .addScalar("cpuText",Hibernate.STRING)
                    .addScalar("cpu",Hibernate.STRING)
                    .addScalar("speed",Hibernate.STRING)
                    .addScalar("backCameraText",Hibernate.STRING)
                    .addScalar("backCamera",Hibernate.STRING)
                    .addScalar("frontCameraText",Hibernate.STRING)
                    .addScalar("frontCamera",Hibernate.STRING)
                    .addScalar("osText",Hibernate.STRING)
                    .addScalar("os",Hibernate.STRING)
                    .addScalar("batteryText",Hibernate.STRING)
                    .addScalar("battery",Hibernate.STRING)
                    .addScalar("simText",Hibernate.STRING)
                    .addScalar("sim",Hibernate.STRING)
                    .addScalar("color",Hibernate.STRING)
                    .addScalar("approve",Hibernate.STRING)
                    .addScalar("lastUpdate",Hibernate.DATE)
                    .setResultTransformer(Transformers.aliasToBean(Tablet.class));

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
//            String tempFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.TEMPLATE_FILE_PATH) + "TabletReport.xlsx");
            String tempFile = rb.getString(Constans.TEMPLATE_FILE_PATH) + "TabletDataReport.xlsx";
            Random random = new Random();
//            String downFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.DOWNLOAD_FILE_PATH) + "TabletReport_"+random.nextInt(1000)+".xlsx");
            String downFile = rb.getString(Constans.DOWNLOAD_FILE_PATH) + "TabletDataReport_"+random.nextInt(1000)+".xlsx";

            FileInputStream file = new FileInputStream(new File(tempFile));
//          Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = null;
            workbook = new XSSFWorkbook(file);
            //Get first/desired sheet from the workbook
            XSSFSheet sheet1 = workbook.getSheetAt(0);

            List<Tablet> list1 = new ArrayList<Tablet>();
            list1 = takeTabletsReport();
            fillData(list1,workbook,sheet1);

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

    public void fillData(List<Tablet> list, XSSFWorkbook workbook ,XSSFSheet sheet ){
        int j=0;
        int rowIndex = 2;
        int stt = 1;
//        XSSFCellStyle style1 = workbook.createCellStyle();
//        style1.setFillForegroundColor(HSSFColor.AQUA.index);
//        style1.setFillPattern(CellStyle.SOLID_FOREGROUND);

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
            row.createCell(j++).setCellValue(list.get(i).getStorageText());
            row.createCell(j++).setCellValue(list.get(i).getStorage());
            row.createCell(j++).setCellValue(list.get(i).getRamText());
            row.createCell(j++).setCellValue(list.get(i).getRam());
            row.createCell(j++).setCellValue(list.get(i).getScreenText());
            row.createCell(j++).setCellValue(list.get(i).getScreen());
            row.createCell(j++).setCellValue(list.get(i).getCpuText());
            row.createCell(j++).setCellValue(list.get(i).getCpu());
            row.createCell(j++).setCellValue(list.get(i).getSpeed());
            row.createCell(j++).setCellValue(list.get(i).getBackCameraText());
            row.createCell(j++).setCellValue(list.get(i).getBackCamera());
            row.createCell(j++).setCellValue(list.get(i).getFrontCameraText());
            row.createCell(j++).setCellValue(list.get(i).getFrontCamera());
            row.createCell(j++).setCellValue(list.get(i).getOsText());
            row.createCell(j++).setCellValue(list.get(i).getOs());
            row.createCell(j++).setCellValue(list.get(i).getBatteryText());
            row.createCell(j++).setCellValue(list.get(i).getBattery());
            row.createCell(j++).setCellValue(list.get(i).getSimText());
            row.createCell(j++).setCellValue(list.get(i).getSim());
            row.createCell(j++).setCellValue(list.get(i).getColor());
            row.createCell(j++).setCellValue(list.get(i).getLink());
            ex.formatDate(row, j++, list.get(i).getLastUpdate(), ex.date);
            row.createCell(j++).setCellValue(list.get(i).getApprove());
        }
    }

     public String upload(File file){
        String sql = "update Tablet_Configuration set item_Code = ?, partno = ?, model = ?, screen = ?, cpu = ?, ram = ?, os = ?,"
                + " B_Camera = ?, F_Camera = ?,storage = ?,battery = ?,sim = ?, color = ?, speed = ?, approve = 'Y' where id = ? ";
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
                Tablet tablet = new Tablet();
                int j=1;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                     tablet.setId((long)row.getCell(j).getNumericCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                     tablet.setId1((long)row.getCell(j).getNumericCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     tablet.setWeb(row.getCell(j).getStringCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     tablet.setType(row.getCell(j).getStringCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     tablet.setBrand(row.getCell(j).getStringCellValue());
                }
                j++;
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     tablet.setItemCode(row.getCell(j).getStringCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     tablet.setPartno(row.getCell(j).getStringCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     tablet.setName(row.getCell(j).getStringCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     tablet.setModel(row.getCell(j).getStringCellValue());
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        tablet.setStorage(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        tablet.setStorage(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        tablet.setRam(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        tablet.setRam(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        tablet.setScreen(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        tablet.setScreen(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        tablet.setCpu(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        tablet.setCpu(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        tablet.setSpeed(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        tablet.setSpeed(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        tablet.setBackCamera(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        tablet.setBackCamera(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        tablet.setFrontCamera(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        tablet.setFrontCamera(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     tablet.setOs(row.getCell(j).getStringCellValue());
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        tablet.setBattery(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        tablet.setBattery(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        tablet.setSim(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        tablet.setSim(String.valueOf(row.getCell(28).getNumericCellValue()));
                    }
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                    tablet.setColor(row.getCell(j).getStringCellValue());
                }
                tablet.setApprove("Y");
                int k = 1;
                pre.setString(k++, tablet.getItemCode() );
                pre.setString(k++, tablet.getPartno() );
                pre.setString(k++, tablet.getModel());
                pre.setString(k++, tablet.getScreen());
                pre.setString(k++, tablet.getCpu());
                pre.setString(k++, tablet.getRam());
                pre.setString(k++, tablet.getOs());
                pre.setString(k++, tablet.getBackCamera());
                pre.setString(k++, tablet.getFrontCamera());
                pre.setString(k++, tablet.getStorage());
                pre.setString(k++, tablet.getBattery());
                pre.setString(k++, tablet.getSim());
                pre.setString(k++, tablet.getColor());
                pre.setString(k++, tablet.getSpeed());
                pre.setLong(k++, tablet.getId1());
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
            } catch (SQLException ex1) {
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
                TabletConfiguration temp = new TabletConfiguration();
                temp = (TabletConfiguration) session.get(TabletConfiguration.class, Long.parseLong(getRequest().getParameter("id")));
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

    public List<ComboBox> getWebs() {
        return webs;
    }

    public void setWebs(List<ComboBox> webs) {
        this.webs = webs;
    }

}
