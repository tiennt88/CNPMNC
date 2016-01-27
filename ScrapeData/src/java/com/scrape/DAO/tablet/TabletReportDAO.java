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
import com.scrape.common.ExcelUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
/**
 *
 * @author KeyLove
 */
public class TabletReportDAO extends BaseHibernateDAOMDB{

    private Tablet tablet = new Tablet();
    private String date;

    List<ComboBox> webs = new ArrayList<ComboBox>();
    
    public String prepare(){
        ComboBoxDAO c = new ComboBoxDAO();
        webs = c.combobox("Web","All");
        return SUCCESS;
    }

    public List<Tablet> takeTablets() {
        List<Tablet> lst = null;
        ArrayList param = new ArrayList();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" Select link,web,type,brand,PRICE_NUMBER priceNumber,name,model, item_code itemCode, partno,");
            sql.append(" storage,ram,screen ,cpu ,b_camera backCamera, ");
            sql.append(" f_camera frontCamera, os,battery, ");
            sql.append(" sim, promotion, a.last_Update lastUpdate, a.create_date createDate");
            sql.append(" from TABLET_DATA a, TABLET_CONFIGURATION b ");
            sql.append(" WHERE a.id = b.TABLET_DATA_ID");

            if(tablet != null){
                if(tablet.getWeb() != null && !"".equals(tablet.getWeb())){
                    sql.append(" AND web = ? ");
                    param.add(tablet.getWeb());
                }
            }
            if(date != null && !"".equals(date)){
                sql.append(" AND  exists (select 1 from TABLET_PRICE where a.id = TABLET_DATA_ID AND CAST(LAST_UPDATE AS DATE) = ?)");
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
            String tempFile = rb.getString(Constans.TEMPLATE_FILE_PATH) + "TabletReport.xlsx";
            Random random = new Random();
//            String downFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.DOWNLOAD_FILE_PATH) + "TabletReport_"+random.nextInt(1000)+".xlsx");
            String downFile = rb.getString(Constans.DOWNLOAD_FILE_PATH) + "TabletReport_"+random.nextInt(1000)+".xlsx";

            FileInputStream file = new FileInputStream(new File(tempFile));
//          Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = null;
            workbook = new XSSFWorkbook(file);
            //Get first/desired sheet from the workbook
            XSSFSheet sheet1 = workbook.getSheetAt(0);

            List<Tablet> list1 = new ArrayList<Tablet>();
            list1 = takeTablets();
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

        XSSFCreationHelper createHelper = workbook.getCreationHelper();
        XSSFCellStyle style2 = workbook.createCellStyle();
        style2.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
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
            row.createCell(j++).setCellValue(list.get(i).getStorage());
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
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Tablet getTablet() {
        return tablet;
    }

    public void setTablet(Tablet tablet) {
        this.tablet = tablet;
    }

    public List<ComboBox> getWebs() {
        return webs;
    }

    public void setWebs(List<ComboBox> webs) {
        this.webs = webs;
    }
    
}
