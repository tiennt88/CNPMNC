/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.DAO.mobile;

import com.scrape.BO.MobileConfiguration;
import com.scrape.BO.MobileData;
import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.DAO.ComboBoxDAO;
import com.scrape.client.form.Mobile;
import com.scrape.client.form.ComboBox;
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
public class MobileDataDAO extends BaseHibernateDAOMDB{
    private DojoJSON jsonDataGrid = new DojoJSON();
    private Mobile mobile = new Mobile();
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
        brands = c.combobox("Brand","Mobile");
        storages = c.combobox("Storage","Mobile");
        rams = c.combobox("Ram","Mobile");
//        getRequest().setAttribute("webs", webs);
        return SUCCESS;
    }

    public List<Mobile> takeMobiles() {
        List<Mobile> lst = null;
        String sortType = null;
        ArrayList param = new ArrayList();
        try {
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
            sql.append(" f_camera frontCamera, os,battery,color, ");
            sql.append(" sim, a.promotion, a.last_Update lastUpdate, a.create_date createDate, approve");
            sql.append(" from MOBILE_DATA a, MOBILE_CONFIGURATION b ");
            sql.append(" WHERE a.id = b.MOBILE_DATA_ID");

            if(mobile != null){
                if(mobile.getWeb() != null && !"".equals(mobile.getWeb())){
                    sql.append(" AND web = ? ");
                    param.add(mobile.getWeb());
                }

                if(mobile.getBrand() != null && !"".equals(mobile.getBrand())){
                    if("null".equals(mobile.getBrand())){
                        sql.append(" AND brand is null ");
                    }else{
                        sql.append(" AND brand = ? ");
                        param.add(mobile.getBrand());
                    }

                }

                if(mobile.getName() != null && !"".equals(mobile.getName())){
                    sql.append(" AND lower(name) like ? ");
                    param.add("%" + mobile.getName().toLowerCase() + "%");
                }

                if(mobile.getModel() != null && !"".equals(mobile.getModel())){
                    if("null".equals(mobile.getModel().toLowerCase())){
                        sql.append(" AND model is null ");
                    }else{
                        sql.append(" AND lower(model) like ? ");
                        param.add(mobile.getModel().toLowerCase());
                    }
                }

                if(mobile.getStorage() != null && !"".equals(mobile.getStorage())){
                    if("null".equals(mobile.getStorage())){
                        sql.append(" AND storage is null ");
                    }else{
                        sql.append(" AND storage = ? ");
                        param.add(mobile.getStorage());
                    }
                }

                if(mobile.getRam() != null && !"".equals(mobile.getRam())){
                    if("null".equals(mobile.getRam())){
                        sql.append(" AND ram is null ");
                    }else{
                        sql.append(" AND ram = ? ");
                        param.add(mobile.getRam());
                    }
                }
                if(mobile.getColor() != null && !"".equals(mobile.getColor())){
                    sql.append(" AND lower(color) = ? ");
                    param.add(mobile.getColor().toLowerCase());
                }

                if(mobile.getApprove() != null && "on".equals(mobile.getApprove())){
                    sql.append(" and approve = 'N' ");
                }
                if(mobile.getToday() != null && "on".equals(mobile.getToday())){
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

            Query query = getSession().createSQLQuery(sql.toString())
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
                    .addScalar("color",Hibernate.STRING)
                    .addScalar("lastUpdate",Hibernate.DATE)
                    .addScalar("createDate",Hibernate.DATE)
                    .addScalar("approve",Hibernate.STRING)
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

//    Lay so luong ban ghi tra ve grid de phan trang tung phan
    public void setMaxResult() {
        try {
            StringBuilder sql = new StringBuilder("select count(*) from MOBILE_DATA a, MOBILE_CONFIGURATION b where a.ID = b.MOBILE_DATA_ID AND 1=1 ");
            ArrayList param = new ArrayList();
            if(mobile != null){
                if(mobile.getWeb() != null && !"".equals(mobile.getWeb())){
                    sql.append(" AND web = ? ");
                    param.add(mobile.getWeb());
                }

                if(mobile.getBrand() != null && !"".equals(mobile.getBrand())){
                    if("null".equals(mobile.getBrand())){
                        sql.append(" AND brand is null ");
                    }else{
                        sql.append(" AND brand = ? ");
                        param.add(mobile.getBrand());
                    }
                }

                if(mobile.getName() != null && !"".equals(mobile.getName())){
                    sql.append(" AND lower(name) like ? ");
                    param.add("%" + mobile.getName().toLowerCase() + "%");
                }

                if(mobile.getModel() != null && !"".equals(mobile.getModel())){
                    if("null".equals(mobile.getModel().toLowerCase())){
                        sql.append(" AND model is null ");
                    }else{
                        sql.append(" AND lower(model) like ? ");
                        param.add(mobile.getModel().toLowerCase());
                    }
                }

                if(mobile.getStorage() != null && !"".equals(mobile.getStorage())){
                    if("null".equals(mobile.getStorage())){
                        sql.append(" AND storage is null ");
                    }else{
                        sql.append(" AND storage = ? ");
                        param.add(mobile.getStorage());
                    }

                }

                if(mobile.getRam() != null && !"".equals(mobile.getRam())){
                    if("null".equals(mobile.getRam())){
                        sql.append(" AND ram is null ");
                    }else{
                        sql.append(" AND ram = ? ");
                        param.add(mobile.getRam());
                    }
                }
                
                if(mobile.getColor() != null && !"".equals(mobile.getColor())){
                    sql.append(" AND lower(color) = ? ");
                    param.add(mobile.getColor().toLowerCase());
                }
                if(mobile.getApprove() != null && "on".equals(mobile.getApprove())){
                    sql.append(" and approve = 'N' ");
                }
                if(mobile.getToday() != null && "on".equals(mobile.getToday())){
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
            List<Mobile> lstResult = new ArrayList<Mobile>();
            lstResult = takeMobiles();
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
            Long id = mobile.getId();
            if( id != null && id > 0){
                MobileData temp = new MobileData();
                temp = (MobileData) session.get(MobileData.class, id);
                temp.setBrand(mobile.getBrand());
                temp.setName(mobile.getName());
                session.update(temp);
                session.flush();
            }
            Long id1 = mobile.getId1();
            if( id1 != null && 1 > 0){
                MobileConfiguration temp1 = new MobileConfiguration();
                temp1 = (MobileConfiguration) session.get(MobileConfiguration.class, id1);
                temp1.setModel(mobile.getModel());
                if("null".equals(mobile.getRam())){
                    mobile.setRam(null);
                }
                temp1.setRam(mobile.getRam());
                if("null".equals(mobile.getStorage())){
                    mobile.setStorage(null);
                }
                temp1.setStorage(mobile.getStorage());
                temp1.setColor(mobile.getColor());
                if(mobile.getApprove() != null && "on".equals(mobile.getApprove())){
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
//            Long id = mobile.getId();
//            if( id != null && id > 0){
//                MobileData temp = new MobileData();
//                temp = (MobileData) session.get(MobileData.class, id);
//                session.delete(temp);
//                session.flush();
//            }
            Long id1 = mobile.getId1();
            if( id1 != null && id1 > 0){
                MobileConfiguration temp1 = new MobileConfiguration();
                temp1 = (MobileConfiguration) session.get(MobileConfiguration.class, id1);
                session.delete(temp1);
                session.flush();
            }
            jsonDataGrid.setLabel("SUCCESS");
            jsonDataGrid.setCustomInfo("Delete Thành Công!");
            mobile = new Mobile();
            onSearch();
        } catch (Exception ex) {
            ex.printStackTrace();
            jsonDataGrid.setLabel("ERROR");
            jsonDataGrid.setCustomInfo(ex.getMessage());
        }
        return "jsonDataGrid";
    }

    public String mobileAnalysis(){
        CallableStatement cs = null;
        Connection conn = null;
        try {
            conn = getSession().connection();
            //call store insight
            String sql = "{call MOBILE_ANALYSIS (?)}";
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

    public List<Mobile> takeMobilesReport() {
        List<Mobile> lst = null;
        ArrayList param = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" Select a.id,b.id id1,link,web,type,brand,PRICE_NUMBER priceNumber,name,model, item_code itemCode, partno,");
            sql.append(" storage_text storageText,storage,ram_text ramText, ram,screen_text screenText, screen ,cpu_text cpuText, cpu, speed ,b_camera_text backCameraText,b_camera backCamera , ");
            sql.append(" f_camera_text frontCameraText, f_camera frontCamera, os_text osText, os,battery_text batteryText, battery,color, ");
            sql.append(" sim_text simText, sim, a.last_Update lastUpdate, approve");
            sql.append(" from MOBILE_DATA a, MOBILE_CONFIGURATION b ");
            sql.append(" WHERE a.id = b.MOBILE_DATA_ID ");

            if(mobile != null){
                if(mobile.getWeb() != null && !"".equals(mobile.getWeb())){
                    sql.append(" AND web = ? ");
                    param.add(mobile.getWeb());
                }

                if(mobile.getBrand() != null && !"".equals(mobile.getBrand())){
                    if("null".equals(mobile.getBrand())){
                        sql.append(" AND brand is null ");
                    }else{
                        sql.append(" AND brand = ? ");
                        param.add(mobile.getBrand());
                    }

                }

                if(mobile.getName() != null && !"".equals(mobile.getName())){
                    sql.append(" AND lower(name) like ? ");
                    param.add("%" + mobile.getName().toLowerCase() + "%");
                }

                if(mobile.getModel() != null && !"".equals(mobile.getModel())){
                    if("null".equals(mobile.getModel().toLowerCase())){
                        sql.append(" AND model is null ");
                    }else{
                        sql.append(" AND lower(model) like ? ");
                        param.add(mobile.getModel().toLowerCase());
                    }
                }

                if(mobile.getStorage() != null && !"".equals(mobile.getStorage())){
                    if("null".equals(mobile.getStorage())){
                        sql.append(" AND storage is null ");
                    }else{
                        sql.append(" AND storage = ? ");
                        param.add(mobile.getStorage());
                    }
                }

                if(mobile.getRam() != null && !"".equals(mobile.getRam())){
                    if("null".equals(mobile.getRam())){
                        sql.append(" AND ram is null ");
                    }else{
                        sql.append(" AND ram = ? ");
                        param.add(mobile.getRam());
                    }
                }
                if(mobile.getColor() != null && !"".equals(mobile.getColor())){
                    sql.append(" AND lower(color) = ? ");
                    param.add(mobile.getColor().toLowerCase());
                }

                if(mobile.getApprove() != null && "on".equals(mobile.getApprove())){
                    param.add(mobile.getApprove());
                }
                if(mobile.getToday() != null && "on".equals(mobile.getToday())){
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
                    .setResultTransformer(Transformers.aliasToBean(Mobile.class));

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
//            String tempFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.TEMPLATE_FILE_PATH) + "MobileReport.xlsx");
            String tempFile = rb.getString(Constans.TEMPLATE_FILE_PATH) + "MobileDataReport.xlsx";
            Random random = new Random();
//            String downFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.DOWNLOAD_FILE_PATH) + "MobileReport_"+random.nextInt(1000)+".xlsx");
            String downFile = rb.getString(Constans.DOWNLOAD_FILE_PATH) + "MobileDataReport_"+random.nextInt(1000)+".xlsx";

            FileInputStream file = new FileInputStream(new File(tempFile));
//          Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = null;
            workbook = new XSSFWorkbook(file);
            //Get first/desired sheet from the workbook
            XSSFSheet sheet1 = workbook.getSheetAt(0);

            List<Mobile> list1 = new ArrayList<Mobile>();
            list1 = takeMobilesReport();
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

    public void fillData(List<Mobile> list, XSSFWorkbook workbook ,XSSFSheet sheet ){
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
//        String sql = "update MobileConfiguration set itemCode = :itemCode, partno = :partno, model = :model, screen = :screen, cpu = :cpu, ram = :ram, os = :os,"
//                + " BCamera = :BCamera, FCamera = :FCamera,storage = :storage,battery = :battery,sim = :sim, color = :color, speed = :speed, approve = 'Y' where id = :id ";
        String sql = "update Mobile_Configuration set item_Code = ?, partno = ?, model = ?, screen = ?, cpu = ?, ram = ?, os = ?,"
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
                Mobile mobile = new Mobile();
                int j=1;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                     mobile.setId((long)row.getCell(j).getNumericCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                     mobile.setId1((long)row.getCell(j).getNumericCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     mobile.setWeb(row.getCell(j).getStringCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     mobile.setType(row.getCell(j).getStringCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     mobile.setBrand(row.getCell(j).getStringCellValue());
                }
                j++;
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     mobile.setItemCode(row.getCell(j).getStringCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     mobile.setPartno(row.getCell(j).getStringCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     mobile.setName(row.getCell(j).getStringCellValue());
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     mobile.setModel(row.getCell(j).getStringCellValue());
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        mobile.setStorage(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        mobile.setStorage(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        mobile.setRam(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        mobile.setRam(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        mobile.setScreen(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        mobile.setScreen(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        mobile.setCpu(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        mobile.setCpu(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        mobile.setSpeed(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        mobile.setSpeed(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        mobile.setBackCamera(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        mobile.setBackCamera(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        mobile.setFrontCamera(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        mobile.setFrontCamera(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                     mobile.setOs(row.getCell(j).getStringCellValue());
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        mobile.setBattery(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        mobile.setBattery(String.valueOf(row.getCell(j).getNumericCellValue()));
                    }
                }
                j++;
                j++;
                if(row.getCell(j) != null){
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                        mobile.setSim(row.getCell(j).getStringCellValue());
                    }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC){
                        mobile.setSim(String.valueOf(row.getCell(28).getNumericCellValue()));
                    }
                }
                j++;
                if(row.getCell(j) != null && row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING){
                    mobile.setColor(row.getCell(j).getStringCellValue());
                }
                mobile.setApprove("Y");
//                session.createQuery(sql)
//                    .setString("itemCode", mobile.getItemCode() )
//                    .setString("partno", mobile.getPartno() )
//                    .setString("model", mobile.getModel())
//                    .setString("screen", mobile.getScreen())
//                    .setString("cpu", mobile.getCpu())
//                    .setString("ram", mobile.getRam())
//                    .setString("os", mobile.getOs())
//                    .setString("BCamera", mobile.getBackCamera())
//                    .setString("FCamera", mobile.getFrontCamera())
//                    .setString("storage", mobile.getStorage())
//                    .setString("battery", mobile.getBattery())
//                    .setString("sim", mobile.getSim())
//                    .setString("color", mobile.getColor())
//                    .setString("speed", mobile.getSpeed())
//                    .setLong("id", mobile.getId1())
//                    .executeUpdate();
                
                int k = 1;
                pre.setString(k++, mobile.getItemCode() );
                pre.setString(k++, mobile.getPartno() );
                pre.setString(k++, mobile.getModel());
                pre.setString(k++, mobile.getScreen());
                pre.setString(k++, mobile.getCpu());
                pre.setString(k++, mobile.getRam());
                pre.setString(k++, mobile.getOs());
                pre.setString(k++, mobile.getBackCamera());
                pre.setString(k++, mobile.getFrontCamera());
                pre.setString(k++, mobile.getStorage());
                pre.setString(k++, mobile.getBattery());
                pre.setString(k++, mobile.getSim());
                pre.setString(k++, mobile.getColor());
                pre.setString(k++, mobile.getSpeed());
                pre.setLong(k++, mobile.getId1());
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
                MobileConfiguration temp = new MobileConfiguration();
                temp = (MobileConfiguration) session.get(MobileConfiguration.class, Long.parseLong(getRequest().getParameter("id")));
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

    public Mobile getMobile() {
        return mobile;
    }

    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
    }

    public List<ComboBox> getWebs() {
        return webs;
    }

    public void setWebs(List<ComboBox> webs) {
        this.webs = webs;
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
