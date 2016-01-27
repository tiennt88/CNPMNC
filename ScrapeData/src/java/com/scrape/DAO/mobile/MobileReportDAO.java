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
import com.scrape.common.ExcelUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author KeyLove
 */
public class MobileReportDAO extends BaseHibernateDAOMDB {

    private Mobile mobile = new Mobile();
    private String date;
    List<ComboBox> webs = new ArrayList<ComboBox>();

    public String prepare() {
        ComboBoxDAO c = new ComboBoxDAO();
        webs = c.combobox("Web", "All");
        return SUCCESS;
    }

    public List<Mobile> takeMobiles() {
        List<Mobile> lst = null;
        ArrayList param = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" Select link,web,type,brand,c.PRICE_NUMBER priceNumber,name,model, item_code itemCode, partno,");
            sql.append(" storage,ram, screen ,cpu ,b_camera backCamera, ");
            sql.append(" f_camera frontCamera, os, battery,color, ");
            sql.append(" sim, promotion, c.last_Update lastUpdate, c.create_date createDate");
            sql.append(" from MOBILE_DATA a, MOBILE_CONFIGURATION b, MOBILE_PRICE c ");
            sql.append(" WHERE a.id = b.MOBILE_DATA_ID and a.id = c.MOBILE_DATA_ID");

            if (mobile != null) {
                if (mobile.getWeb() != null && !"".equals(mobile.getWeb())) {
                    sql.append(" AND web = ? ");
                    param.add(mobile.getWeb());
                }
            }
            if (date != null && !"".equals(date)) {
                sql.append(" AND CAST(c.LAST_UPDATE AS DATE) = ?)");
                param.add(date);
            }

            sql.append(" Order by web, brand, model ");

            Query query = getSession().createSQLQuery(sql.toString()).addScalar("link", Hibernate.STRING).addScalar("web", Hibernate.STRING).addScalar("type", Hibernate.STRING).addScalar("brand", Hibernate.STRING).addScalar("priceNumber", Hibernate.LONG).addScalar("name", Hibernate.STRING).addScalar("model", Hibernate.STRING).addScalar("itemCode", Hibernate.STRING).addScalar("partno", Hibernate.STRING).addScalar("storage", Hibernate.STRING).addScalar("ram", Hibernate.STRING).addScalar("screen", Hibernate.STRING).addScalar("cpu", Hibernate.STRING).addScalar("backCamera", Hibernate.STRING).addScalar("frontCamera", Hibernate.STRING).addScalar("os", Hibernate.STRING).addScalar("battery", Hibernate.STRING).addScalar("sim", Hibernate.STRING).addScalar("promotion", Hibernate.STRING).addScalar("color", Hibernate.STRING).addScalar("lastUpdate", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(Mobile.class));

            for (int i = 0; i < param.size(); i++) {
                query.setParameter(i, param.get(i));
            }

            lst = query.list();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lst;
    }

    public String exportExcel() {
        try {
//            String tempFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.TEMPLATE_FILE_PATH) + "MobileReport.xlsx");
            String tempFile = rb.getString(Constans.TEMPLATE_FILE_PATH) + "MobileReport.xlsx";
            Random random = new Random();
//            String downFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.DOWNLOAD_FILE_PATH) + "MobileReport_"+random.nextInt(1000)+".xlsx");
            String downFile = rb.getString(Constans.DOWNLOAD_FILE_PATH) + "MobileReport_" + random.nextInt(1000) + ".xlsx";

            FileInputStream file = new FileInputStream(new File(tempFile));
//          Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = null;
            workbook = new XSSFWorkbook(file);
            //Get first/desired sheet from the workbook
            XSSFSheet sheet1 = workbook.getSheetAt(0);

            List<Mobile> list1 = new ArrayList<Mobile>();
            list1 = takeMobiles();
            fillData(list1, workbook, sheet1);

//            ByteArrayOutputStream boas = new ByteArrayOutputStream();
//            workbook.write(boas);
//            setInputStream(new ByteArrayInputStream(boas.toByteArray()));

            FileOutputStream out = new FileOutputStream(new File(downFile));
            workbook.write(out);
            out.close();
            getRequest().setAttribute("attachFile", downFile);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return SUCCESS;
    }

    public void fillData(List<Mobile> list, XSSFWorkbook workbook, XSSFSheet sheet) {
        int j = 0;
        int rowIndex = 2;
        int stt = 1;

        ExcelUtil ex = new ExcelUtil(workbook);
        for (int i = 0; i < list.size(); i++) {
            Row row = sheet.createRow(rowIndex++);
            j = 0;
            row.createCell(j++).setCellValue(stt++);
            row.createCell(j++).setCellValue(list.get(i).getWeb());
            row.createCell(j++).setCellValue(list.get(i).getType());
            row.createCell(j++).setCellValue(list.get(i).getBrand());
            if (list.get(i).getPriceNumber() != null) {
                ex.formatNumber(row, j++, list.get(i).getPriceNumber(), ex.number);
            } else {
                j++;
            }
            row.createCell(j++).setCellValue(list.get(i).getItemCode());
            row.createCell(j++).setCellValue(list.get(i).getPartno());
            row.createCell(j++).setCellValue(list.get(i).getName());
            row.createCell(j++).setCellValue(list.get(i).getModel());
            row.createCell(j++).setCellValue(list.get(i).getStorage());
            row.createCell(j++).setCellValue(list.get(i).getRam());
            row.createCell(j++).setCellValue(list.get(i).getScreen());
            row.createCell(j++).setCellValue(list.get(i).getCpu());
            row.createCell(j++).setCellValue(list.get(i).getBackCamera());
            row.createCell(j++).setCellValue(list.get(i).getFrontCamera());
            row.createCell(j++).setCellValue(list.get(i).getOs());
            row.createCell(j++).setCellValue(list.get(i).getBattery());
            row.createCell(j++).setCellValue(list.get(i).getSim());
            row.createCell(j++).setCellValue(list.get(i).getColor());
            row.createCell(j++).setCellValue(list.get(i).getPromotion());
            ex.formatDate(row, j++, list.get(i).getLastUpdate(), ex.date);
            row.createCell(j++).setCellValue(list.get(i).getLink());
        }
    }

    public List<Mobile> takeMobileSames() {
        List<Mobile> lst = null;
        ArrayList param = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" Select link,case web when 'FTG' then ' FTG' else web end as web,type,isnull(brand,'') brand,c.PRICE_NUMBER priceNumber,name,isnull(model,'') model, item_code itemCode, partno,");
            sql.append(" isnull(storage,'') storage,ram, screen ,cpu ,b_camera backCamera, ");
            sql.append(" f_camera frontCamera, os, battery,case COLOR when 'Gold' then 'Gold' else '' end as color, ");
            sql.append(" sim, promotion, c.last_Update lastUpdate, c.create_date createDate");
            sql.append(" from MOBILE_DATA a, MOBILE_CONFIGURATION b, MOBILE_PRICE c ");
            sql.append(" WHERE a.id = b.MOBILE_DATA_ID and a.ID = c.MOBILE_DATA_ID ");

            if (date != null && !"".equals(date)) {
                sql.append(" AND CAST(c.CREATE_DATE AS DATE) = ?");
                param.add(date);
            }

            sql.append(" Order by brand, model,storage, color, web, priceNumber  ");

            Query query = getSession().createSQLQuery(sql.toString()).addScalar("link", Hibernate.STRING).addScalar("web", Hibernate.STRING).addScalar("type", Hibernate.STRING).addScalar("brand", Hibernate.STRING).addScalar("priceNumber", Hibernate.LONG).addScalar("name", Hibernate.STRING).addScalar("model", Hibernate.STRING).addScalar("itemCode", Hibernate.STRING).addScalar("partno", Hibernate.STRING).addScalar("storage", Hibernate.STRING).addScalar("ram", Hibernate.STRING).addScalar("screen", Hibernate.STRING).addScalar("cpu", Hibernate.STRING).addScalar("backCamera", Hibernate.STRING).addScalar("frontCamera", Hibernate.STRING).addScalar("os", Hibernate.STRING).addScalar("battery", Hibernate.STRING).addScalar("sim", Hibernate.STRING).addScalar("promotion", Hibernate.STRING).addScalar("color", Hibernate.STRING).addScalar("lastUpdate", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(Mobile.class));

            for (int i = 0; i < param.size(); i++) {
                query.setParameter(i, param.get(i));
            }

            lst = query.list();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lst;
    }

    public void fillDataSame(List<Mobile> list, XSSFWorkbook workbook, XSSFSheet sheet) {
        int j = 0;
        int rowIndex = 2;
        int stt = 1;

        ExcelUtil ex = new ExcelUtil(workbook);
        String brand = "";
        String model = "";
        String storage = "";
        String color = "";
        Row rowold = null;
        for (int i = 0; i < list.size(); i++) {
            if (!brand.equals(list.get(i).getBrand())
                    || !model.equals(list.get(i).getModel())
                    || !storage.equals(list.get(i).getStorage())
                    || !color.equals(list.get(i).getColor())) {
                Row row = sheet.createRow(rowIndex++);
                rowold = row;
                j = 0;
                row.createCell(j++).setCellValue(stt++);
                row.createCell(j++).setCellValue(list.get(i).getWeb());
                row.createCell(j++).setCellValue(list.get(i).getType());
                row.createCell(j++).setCellValue(list.get(i).getBrand());
                row.createCell(j++).setCellValue(list.get(i).getModel());
                row.createCell(j++).setCellValue(list.get(i).getStorage());
                row.createCell(j++).setCellValue(list.get(i).getColor());
                if (list.get(i).getPriceNumber() != null) {
                    int column = -1;
                    if("FTG".equals(list.get(i).getWeb().trim())){
                        column = 7;
                    }else if("FPTShop".equals(list.get(i).getWeb())){
                        column = 8;
                    }else if("MediaMart".equals(list.get(i).getWeb())){
                        column = 9;
                    }if("TGDD".equals(list.get(i).getWeb())){
                        column = 10;
                    }if("TranAnh".equals(list.get(i).getWeb())){
                        column = 11;
                    }if("VienThongA".equals(list.get(i).getWeb())){
                        column = 12;
                    }
                    ex.formatNumber(row, column, list.get(i).getPriceNumber(), ex.number);
                }

                j=13;
//                row.createCell(j++).setCellValue(list.get(i).getItemCode());
                row.createCell(j++).setCellValue(list.get(i).getPartno());
                row.createCell(j++).setCellValue(list.get(i).getName());
                
                row.createCell(j++).setCellValue(list.get(i).getRam());
                row.createCell(j++).setCellValue(list.get(i).getScreen());
                row.createCell(j++).setCellValue(list.get(i).getCpu());
                row.createCell(j++).setCellValue(list.get(i).getBackCamera());
                row.createCell(j++).setCellValue(list.get(i).getFrontCamera());
                row.createCell(j++).setCellValue(list.get(i).getOs());
                row.createCell(j++).setCellValue(list.get(i).getBattery());
                row.createCell(j++).setCellValue(list.get(i).getSim());
                
                row.createCell(j++).setCellValue(list.get(i).getPromotion());
                ex.formatDate(row, j++, list.get(i).getLastUpdate(), ex.date);
                row.createCell(j++).setCellValue(list.get(i).getLink());

                brand = list.get(i).getBrand();
                model = list.get(i).getModel();
                storage = list.get(i).getStorage();
                color = list.get(i).getColor();
            } else {
                if (list.get(i).getPriceNumber() != null) {
                    int column = -1;
                    if("FTG".equals(list.get(i).getWeb().trim())){
                        column = 7;
                    }else if("FPTShop".equals(list.get(i).getWeb())){
                        column = 8;
                    }else if("MediaMart".equals(list.get(i).getWeb())){
                        column = 9;
                    }else if("TGDD".equals(list.get(i).getWeb())){
                        column = 10;
                    }else if("TranAnh".equals(list.get(i).getWeb())){
                        column = 11;
                    }else if("VienThongA".equals(list.get(i).getWeb())){
                        column = 12;
                    }
                    ex.formatNumber(rowold, column, list.get(i).getPriceNumber(), ex.number);
                }
            }
        }
    }

    public String exportExcelSame() {
        try {
            String tempFile = rb.getString(Constans.TEMPLATE_FILE_PATH) + "MobileReportSame.xlsx";
            Random random = new Random();
            String downFile = rb.getString(Constans.DOWNLOAD_FILE_PATH) + "MobileReportSame_" + random.nextInt(1000) + ".xlsx";

            FileInputStream file = new FileInputStream(new File(tempFile));
            XSSFWorkbook workbook = null;
            workbook = new XSSFWorkbook(file);
            XSSFSheet sheet1 = workbook.getSheetAt(0);

            List<Mobile> list1 = new ArrayList<Mobile>();
            list1 = takeMobileSames();
            fillDataSame(list1, workbook, sheet1);

            int numrows = sheet1.getLastRowNum();
            for (int i = 1; i <= numrows; i++) {
                Row row = sheet1.getRow(i);
                String check = "";
                if(row.getCell(7) != null){
                    if (row.getCell(7).getCellType() == Cell.CELL_TYPE_STRING) {
                        check = row.getCell(7).getStringCellValue();
                    } else if (row.getCell(7).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        check = String.valueOf((long) row.getCell(7).getNumericCellValue());
                    }
                }
                if("".equals(check)){
//                    sheet1.shiftRows(i+1, i+1,-1 );
//                    i--;
                    sheet1.removeRow(row);
                }
            }

            FileOutputStream out = new FileOutputStream(new File(downFile));
            workbook.write(out);
            out.close();
            getRequest().setAttribute("attachFile", downFile);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return SUCCESS;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
    
}
