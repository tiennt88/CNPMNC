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
import com.scrape.common.ExcelUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
public class LaptopReportDAO extends BaseHibernateDAOMDB{

    private Laptop laptop = new Laptop();
    private String date;
    List<ComboBox> webs = new ArrayList<ComboBox>();

    public String prepare(){
        ComboBoxDAO c = new ComboBoxDAO();
        webs = c.combobox("Web","All");
        return SUCCESS;
    }

    public List<Laptop> takeLaptops() {
        List<Laptop> lst = null;
        ArrayList param = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" Select link,web,type,brand,PRICE_NUMBER priceNumber,name,model, item_code itemCode, partno,");
            sql.append(" chip,SPEED, storage,HDD_TYPE hddType, ");
            sql.append(" ram,vga,screen, ");
            sql.append(" touchscreen,os, dvd,");
            sql.append(" battery,PROMOTION, a.last_Update lastUpdate, a.create_date createDate");
            sql.append(" from LAPTOP_DATA a, LAPTOP_CONFIGURATION b ");
            sql.append(" WHERE a.id = b.LAPTOP_DATA_ID");

            if(laptop != null){
                if(laptop.getWeb() != null && !"".equals(laptop.getWeb())){
                    sql.append(" AND web = ? ");
                    param.add(laptop.getWeb());
                }
            }
            if(date != null && !"".equals(date)){
                sql.append(" AND  exists (select 1 from LAPTOP_PRICE where a.id = LAPTOP_DATA_ID AND CAST(LAST_UPDATE AS DATE) = ?)");
                param.add(date);
            }
            
            sql.append(" Order by web, brand, model ");

            Query query = getSession().createSQLQuery(sql.toString())
                    .addScalar("link",Hibernate.STRING)
                    .addScalar("web",Hibernate.STRING)
                    .addScalar("type",Hibernate.STRING)
                    .addScalar("brand",Hibernate.STRING)
                    .addScalar("priceNumber",Hibernate.LONG)
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
            String tempFile = rb.getString(Constans.TEMPLATE_FILE_PATH) + "LaptopReport.xlsx";
            Random random = new Random();
//            String downFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.DOWNLOAD_FILE_PATH) + "LaptopReport_"+random.nextInt(1000)+".xlsx");
            String downFile = rb.getString(Constans.DOWNLOAD_FILE_PATH) + "LaptopReport_"+random.nextInt(1000)+".xlsx";

            FileInputStream file = new FileInputStream(new File(tempFile));
//          Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = null;
            workbook = new XSSFWorkbook(file);
            //Get first/desired sheet from the workbook
            XSSFSheet sheet1 = workbook.getSheetAt(0);

            List<Laptop> list1 = new ArrayList<Laptop>();
            list1 = takeLaptops();
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
            ex.formatDate(row, j++, list.get(i).getLastUpdate(), ex.date);
            row.createCell(j++).setCellValue(list.get(i).getLink());
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public List<ComboBox> getWebs() {
        return webs;
    }

    public void setWebs(List<ComboBox> webs) {
        this.webs = webs;
    }

}
