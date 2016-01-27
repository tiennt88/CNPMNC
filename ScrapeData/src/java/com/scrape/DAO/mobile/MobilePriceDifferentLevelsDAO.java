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
public class MobilePriceDifferentLevelsDAO extends BaseHibernateDAOMDB{
    private DojoJSON jsonDataGrid = new DojoJSON();
    private int startval;
    private int count;
    private String sort;
    private String result;
    private Mobile mobile = new Mobile();
    private String check;
    private List<String> webs = new ArrayList<String>();
    ArrayList param = new ArrayList();
    private InputStream inputStream;
    private String filename;
    
    private List<ComboBox> lstWeb = new ArrayList<ComboBox>();
    private Long priceDifferent;
    
    public String prepare(){
        ComboBoxDAO c = new ComboBoxDAO();
        lstWeb = c.takeWebNotRoot(rb.getString(Constans.WEB_ROOT));
        return SUCCESS;
    }

    public String preparePopUp(){
        if (getRequest().getParameter("brand") != null &&  getRequest().getParameter("model") != null) {
            mobile.setBrand(getRequest().getParameter("brand"));
            mobile.setModel(getRequest().getParameter("model"));
        }
        if (getRequest().getParameter("storage") != null) {
            mobile.setStorage(getRequest().getParameter("storage"));
        }
        if (getRequest().getParameter("ram") != null) {
            mobile.setRam(getRequest().getParameter("ram"));
        }
        if (getRequest().getParameter("date") != null) {
            mobile.setDate(getRequest().getParameter("date"));
        }
        if(mobile != null){
            getRequest().getSession().setAttribute("mobile", mobile);
        }
        
        return SUCCESS;
    }

    public List<Mobile> takeMobilesPopup() {
        List<Mobile> lst = null;
        String sortType = null;
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
            sql.append(" Select  a.link,a.web,a.type,a.brand,a.price,a.name,b.model, ");
            sql.append(" storage,ram,screen ,cpu ,b_camera backCamera, ");
            sql.append(" f_camera frontCamera, os,battery,color, ");
            sql.append(" sim, a.promotion, a.last_Update lastUpdate");
            sql.append(" from MOBILE_DATA a, MOBILE_CONFIGURATION b ");
            sql.append(" WHERE a.id = b.MOBILE_DATA_ID");

            if(mobile != null){
                param.clear();
                if(mobile.getBrand() != null && !"".equals(mobile.getBrand())){
                    sql.append(" AND brand = ? ");
                    param.add(mobile.getBrand());
                }

                if(mobile.getModel() != null && !"".equals(mobile.getModel())){
                    sql.append(" AND model= ? ");
                    param.add(mobile.getModel());
                }

                if(mobile.getRam() != null && !"null".equals(mobile.getRam())){
                    sql.append(" AND ram = ? ");
                    param.add(mobile.getRam());
                }

                if(mobile.getStorage() != null && !"null".equals(mobile.getStorage())){
                    sql.append(" AND storage = ? ");
                    param.add(mobile.getStorage());
                }

//                if(mobile.getDate() != null && !"".equals(mobile.getDate())){
//                    sql.append(" AND cast(a.last_update as date) = ? ");
//                    param.add(mobile.getDate());
//                }else{
                    sql.append(" AND cast(a.last_update as date) = cast(getdate() as date) ");
//                }

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
    public void setMaxResultPopup() {
        try {
            StringBuilder sql = new StringBuilder("select count(*) from MOBILE_DATA a, MOBILE_CONFIGURATION b where a.ID = b.MOBILE_DATA_ID AND 1=1 ");
            if(mobile != null){
                param.clear();
                if(mobile.getBrand() != null && !"".equals(mobile.getBrand())){
                    sql.append(" AND brand = ? ");
                    param.add(mobile.getBrand());
                }

                if(mobile.getModel() != null && !"".equals(mobile.getModel())){
                    sql.append(" AND model= ? ");
                    param.add(mobile.getModel());
                }

                if(mobile.getRam() != null && !"null".equals(mobile.getRam())){
                    sql.append(" AND ram = ? ");
                    param.add(mobile.getRam());
                }

                if(mobile.getStorage() != null && !"null".equals(mobile.getStorage())){
                        sql.append(" AND storage = ? ");
                        param.add(mobile.getStorage());
                }

//                if(mobile.getDate() != null && !"".equals(mobile.getDate())){
//                        sql.append(" AND cast(a.last_update as date) = ? ");
//                        param.add(mobile.getDate());
//                }else{
                    sql.append(" AND cast(a.last_update as date) = cast(getdate() as date) ");
//                }

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

    public String onSearchPopUp() {
        try {
            if(getRequest().getSession().getAttribute("mobile") !=null){
                mobile = (Mobile) getRequest().getSession().getAttribute("mobile");
                getRequest().getSession().removeAttribute("mobile");
            }
            setMaxResultPopup();
            List<Mobile> lstResult = new ArrayList<Mobile>();
            lstResult = takeMobilesPopup();
            jsonDataGrid.setItems(lstResult);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "jsonDataGrid";
    }

    public String takeSQL(String type){
        param.clear();
        StringBuilder sql = new StringBuilder();

        if("export".equals(type)){
            sql.append(" select z1.link,z1.web,z1.type,z1.brand,z1.price_number priceNumber,z1.name,z2.model,z2.storage, ");
            sql.append(" z2.ram,screen ,cpu ,b_camera backCamera, ");
            sql.append(" f_camera frontCamera, os,battery,color,sim,  ");
            sql.append(" promotion, z1.last_Update lastUpdate ");
            sql.append(" from mobile_data z1, mobile_configuration z2, ");
            sql.append(" ( ");
        }

        if("count".equals(type)){
            sql.append(" select count(*) from (select distinct x.* from ");
        }else{
           sql.append(" select distinct '").append(rb.getString(Constans.WEB_ROOT)).append("' web,'Mobile' type , x.* from ");
        }

        sql.append(" (select ");
        if(check != null && "on".equals(check)){
            sql.append(" min(PRICE_NUMBER) mi, ");
        }else{
            sql.append(" max(PRICE_NUMBER) ma, ");
        }
        if(mobile.getStorage() != null && !"".equals(mobile.getStorage())){
            sql.append(" storage, ");
        }
        if(mobile.getRam() != null && !"".equals(mobile.getRam())){
            sql.append(" RAM, ");
        }
        sql.append(" model,brand, CAST(a.LAST_UPDATE AS DATE) lastUpDate ");
        sql.append(" from mobile_data a, mobile_configuration b ");
        sql.append(" where a.ID = b.MOBILE_DATA_ID ");
        sql.append(" and model is not null ");
        sql.append(" and PRICE_NUMBER is not null ");
        sql.append(" and WEB = '").append(rb.getString(Constans.WEB_ROOT)).append("'");
//        if(mobile.getDate() != null && !"".equals(mobile.getDate())){
//            sql.append(" AND CAST(a.LAST_UPDATE as DATE) = ?");
//            param.add(mobile.getDate());
//        }else{
            sql.append(" AND CAST(a.LAST_UPDATE AS DATE) = CAST(getdate() AS DATE)");
//        }
        sql.append(" group by ");
        if(mobile.getStorage() != null && !"".equals(mobile.getStorage())){
            sql.append(" storage, ");
        }
        if(mobile.getRam() != null && !"".equals(mobile.getRam())){
            sql.append(" RAM, ");
        }
        sql.append(" model,brand, CAST(a.LAST_UPDATE AS DATE)");
        sql.append(" )x inner join  ");
        sql.append(" (select min(PRICE_NUMBER) mi,max(PRICE_NUMBER) ma,");
        if(mobile.getStorage() != null && !"".equals(mobile.getStorage())){
            sql.append(" storage, ");
        }
        if(mobile.getRam() != null && !"".equals(mobile.getRam())){
            sql.append(" RAM, ");
        }
        sql.append(" model,brand, CAST(a.LAST_UPDATE AS DATE) lastUpDate ");
        sql.append(" from mobile_data a, mobile_configuration b ");
        sql.append(" where a.ID = b.MOBILE_DATA_ID ");
        sql.append(" and model is not null ");
        sql.append(" and PRICE_NUMBER is not null ");
        sql.append(" and WEB != '").append(rb.getString(Constans.WEB_ROOT)).append("'");
//        if(mobile.getDate() != null && !"".equals(mobile.getDate())){
//            sql.append(" AND CAST(a.LAST_UPDATE as DATE) = ?");
//            param.add(mobile.getDate());
//        }else{
            sql.append(" AND CAST(a.LAST_UPDATE AS DATE) = CAST(getdate() AS DATE)");
//        }
        sql.append(" group by ");
        if(mobile.getStorage() != null && !"".equals(mobile.getStorage())){
            sql.append(" storage, ");
        }
        if(mobile.getRam() != null && !"".equals(mobile.getRam())){
            sql.append(" RAM, ");
        }
        sql.append(" model,brand,CAST(a.LAST_UPDATE AS DATE)");
        sql.append(" ) y ");
        sql.append(" on x.brand = y.brand and x.model = y.model ");
        
        if(mobile.getStorage() != null && !"".equals(mobile.getStorage())){
            sql.append(" and x.STORAGE = y.STORAGE ");
        }
        if(mobile.getRam() != null && !"".equals(mobile.getRam())){
            sql.append(" and x.RAM = y.RAM ");
        }
        sql.append(" where ");
        if(check != null && "on".equals(check)){
            sql.append(" x.mi <= y.mi - ? and x.mi < y.ma ");
        }else{
            sql.append(" x.ma >= y.ma + ? and x.ma > y.mi ");
        }
        
        if(priceDifferent != null){
            param.add(priceDifferent * 1000);
        }else{
            param.add(0);
        }

        if("count".equals(type)){
            sql.append(" ) z ");
        }

        if("export".equals(type)){
            sql.append(" ) z3 ");
            sql.append(" where z1.ID = z2.MOBILE_DATA_ID ");
            sql.append(" and z1.BRAND = z3.BRAND ");
            sql.append(" and z2.MODEL = z3.MODEL ");

            if(mobile.getStorage() != null && !"".equals(mobile.getStorage())){
                sql.append(" and z2.STORAGE = z3.STORAGE ");
            }
            if(mobile.getRam() != null && !"".equals(mobile.getRam())){
                sql.append(" and z2.RAM = z3.RAM ");
            }
        }
        return sql.toString();
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

    public List<Mobile> takeMobiles() {
        List<Mobile> lst = null;
        try {
            Session session = getSession();
            String sql = takeSQL("getData");
            SQLQuery query = session.createSQLQuery(sql.toString());
            query.addScalar("web",Hibernate.STRING);
            query.addScalar("type",Hibernate.STRING);
            query.addScalar("brand",Hibernate.STRING);
            query.addScalar("model",Hibernate.STRING);
            
            if(check != null && "on".equals(check)){
                query.addScalar("mi",Hibernate.LONG);
            }else{
                query.addScalar("ma",Hibernate.LONG);
            }
            if(mobile.getStorage() != null && !"".equals(mobile.getStorage())){
                query.addScalar("storage",Hibernate.STRING);
            }
            if(mobile.getRam() != null && !"".equals(mobile.getRam())){
                query.addScalar("ram",Hibernate.STRING);
            }
            
            query.addScalar("lastUpdate",Hibernate.DATE);
            query.setResultTransformer(Transformers.aliasToBean(Mobile.class));

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

    public List<Mobile> takeMobilesDetail() {
        List<Mobile> lst = null;
        try{
            String sql = takeSQL("export");

            Query query = getSession().createSQLQuery(sql.toString())
                    .addScalar("link",Hibernate.STRING)
                    .addScalar("web",Hibernate.STRING)
                    .addScalar("type",Hibernate.STRING)
                    .addScalar("brand",Hibernate.STRING)
                    .addScalar("priceNumber",Hibernate.LONG)
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
            filename = "MobilePriceDifferent.xlsx";
//            String tempFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.TEMPLATE_FILE_PATH) + filename);
            String tempFile = rb.getString(Constans.TEMPLATE_FILE_PATH) + filename;
            Random random = new Random();
//            String downFile = ServletActionContext.getServletContext().getRealPath(rb.getString(Constans.DOWNLOAD_FILE_PATH) + "MobilePriceDifferent_"+random.nextInt(1000)+".xlsx");
            String downFile = rb.getString(Constans.DOWNLOAD_FILE_PATH) + "MobilePriceDifferent_"+random.nextInt(1000)+".xlsx";
            FileInputStream file = new FileInputStream(new File(tempFile));
//          Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = null;
            workbook = new XSSFWorkbook(file);
            //Get first/desired sheet from the workbook
            XSSFSheet sheet1 = workbook.getSheetAt(0);
            XSSFSheet sheet2 = workbook.getSheetAt(1);

            startval = -1;
            count = -1;
            
            List<Mobile> list1 = new ArrayList<Mobile>();
            check = "off";
            list1 = takeMobilesDetail();
            fillData(list1,workbook,sheet1);
            check = "on";
            List<Mobile> list2 = new ArrayList<Mobile>();
            list2 = takeMobilesDetail();
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

    public void fillData(List<Mobile> list, XSSFWorkbook workbook ,XSSFSheet sheet ){
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

    public Mobile getMobile() {
        return mobile;
    }

    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
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

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public List<ComboBox> getLstWeb() {
        return lstWeb;
    }

    public void setLstWeb(List<ComboBox> lstWeb) {
        this.lstWeb = lstWeb;
    }

    public Long getPriceDifferent() {
        return priceDifferent;
    }

    public void setPriceDifferent(Long priceDifferent) {
        this.priceDifferent = priceDifferent;
    }
}
