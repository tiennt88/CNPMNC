/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.selenium.action;

import com.selenium.BO.VatGia;
import com.selenium.DAO.DatasourceC3p0;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author KeyLove
 */
public class VatGiaAction extends DatasourceC3p0 {

    private static WebDriver driver;
    private String brand;
    private Connection conn;
    List<String> links = new ArrayList<String>();
    List<String> names = new ArrayList<String>();

    public void crawl(String url, String category, String brand) {
        this.brand = brand;
        try {
            driver.get(url);//start
            String nextPage = "";
            while(nextPage!=null){
                nextPage = getURLNextPage();
                getURLEachPage();
                for(int i = 0;i<links.size();i++){
//                    getDataEachURL(links.get(i),names.get(i));
                    saveCheckItem(brand, names.get(i), links.get(i));
                }
                
                if(nextPage!=null){
                    driver.get(nextPage);
                }
                
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
//            closeObject(conn);
        }

    }

    //lay url trang sau
    public String getURLNextPage() {
        String url = null;
        List<WebElement> webElements = null;
        try {
            webElements = driver.findElements(By.xpath("//div[@class='page_div_top']//a[@class='page']"));
            if (webElements != null) {
                for (int i = 0; i < webElements.size(); i++) {
                    if ("Trang sau".equals(webElements.get(i).getAttribute("title"))) {
                        url = webElements.get(i).getAttribute("href");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return url;
    }

    //lay du lieu
    public void getURLEachPage() {
        List<WebElement> webElements = null;
        links.clear();
        names.clear();
        try {
            webElements = driver.findElements(By.xpath("//table[@class='view_list_table']//div[@class='name']//a"));
            if (webElements != null) {
                for (int i = 0; i < webElements.size(); i++) {
                    links.add(webElements.get(i).getAttribute("href"));
                    names.add(webElements.get(i).getText());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //lay du lieu chi tiet
    public void getDataEachURL(String brand,String name,String url) {
        List<WebElement> webElements = null;
        List<WebElement> webElements2 = null;
        VatGia vatgia = new VatGia();
        vatgia.setLink(url);
        vatgia.setBrand(brand);
        vatgia.setName(name);
        try {
            driver.get(url);
            Thread.sleep(2000);
//            driver.wait();
            driver.findElement(By.xpath("//a[text()='Thông số kỹ thuật']")).click();//click view cấu hình chi tiết
            Thread.sleep(2000);
            webElements = driver.findElements(By.xpath("//table[@class='technical']//td[@class='name']"));
            webElements2 = driver.findElements(By.xpath("//table[@class='technical']//td[@class='value']"));
            for (int i = 0; i < webElements.size(); i++) {
                String text = webElements.get(i).getText();
                String value = webElements2.get(i).getText();
                if(text.indexOf("Màn hình") >=0){
                    vatgia.setScreenType(value);
                }else if(text.indexOf("Kích thước màn hình") >=0){
                    vatgia.setScreen(value);
                }else if(text.indexOf("Độ phân giải màn hình") >=0){
                    vatgia.setResolution(value);
                }else if(text.indexOf("Số lượng Cores") >=0){
                    vatgia.setCpuType(value);
                }else if(text.indexOf("Bộ vi xử lý") >=0){
                    vatgia.setCpu(value);
                }else if(text.indexOf("Bộ xử lý đồ hoạ") >=0){
                    vatgia.setGpu(value);
                }else if(text.indexOf("Bộ nhớ trong") >=0){
                    vatgia.setStorage(value);
                }else if(text.indexOf("RAM") >=0){
                    vatgia.setRam(value);
                }else if(text.indexOf("Hệ điều hành") >=0){
                    vatgia.setOs(value);
                }else if(text.indexOf("Số sim") >=0){
                    vatgia.setSim(value);
                }else if(text.indexOf("Camera") >=0){
                    vatgia.setBackCamera(value);
                }else if(text.indexOf("Tính năng") >=0){
                    vatgia.setFeature(value);
                }else if(text.indexOf("Pin") >=0){
                    vatgia.setBattery(value);
                }
            }
            //save object
            save(vatgia);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save(VatGia vatgia) {
        PreparedStatement pre = null;
        int i = 1;
        try {
            pre = conn.prepareCall("INSERT INTO VATGIA(BRAND,NAME,LINK,SCREEN,RESOLUTION,CPU,CPU_TYPE,GPU,STORAGE,RAM,OS,SIM,"
                    + "B_CAMERA,BATTERY,FEATURE,SCREEN_TYPE,CREATE_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,getdate())");
            pre.setString(i++, vatgia.getBrand());
            pre.setString(i++, vatgia.getName());
            pre.setString(i++, vatgia.getLink());
            pre.setString(i++, vatgia.getScreen());
            pre.setString(i++, vatgia.getResolution());
            pre.setString(i++, vatgia.getCpu());
            pre.setString(i++, vatgia.getCpuType());
            pre.setString(i++, vatgia.getGpu());
            pre.setString(i++, vatgia.getStorage());
            pre.setString(i++, vatgia.getRam());
            pre.setString(i++, vatgia.getOs());
            pre.setString(i++, vatgia.getSim());
            pre.setString(i++, vatgia.getBackCamera());
            pre.setString(i++, vatgia.getBattery());
            pre.setString(i++, vatgia.getFeature());
            pre.setString(i++, vatgia.getScreenType());
            pre.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(pre);
        }

    }

    public void saveCheckItem(String brand, String model, String link) {
        PreparedStatement pre = null;
        int i = 1;
        try {
            pre = conn.prepareStatement("INSERT INTO VATGIA_CHECK(BRAND,MODEL,LINK,CREATE_DATE) VALUES(?,?,?,getdate())");
            pre.setString(i++, brand);
            pre.setString(i++, model);
            pre.setString(i++, link);
            pre.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(pre);
        }

    }

    public void closeDriver() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        driver.quit();
    }

    public void begin(){
        try{
            conn = getConnection();
            driver = loadDriverNotImage("firefox");
            crawl("http://www.vatgia.com/438/1,405621/mobile-masstel.html", "Mobile", "Masstel");
            crawl("http://www.vatgia.com/438/1,219663/mobile-mobiistar.html","Mobile","Mobiistar");
            crawl("http://www.vatgia.com/438/1,198738/mobile-f-mobile.html","Mobile","F-Mobile");
            crawl("http://www.vatgia.com/438/1,409007/mobile-avio.html","Mobile","Avio");
            crawl("http://www.vatgia.com/438/1,163049/mobile-connspeed.html","Mobile","ConnSpeed");
            crawl("http://www.vatgia.com/438/1,61974/mobile-mobell.html","Mobile","Mobell");
            crawl("http://www.vatgia.com/438/1,226888/mobile-viettel.html","Mobile","Viettel");
            crawl("http://www.vatgia.com/438/1,439604/mobile-oppo.html","Mobile","Oppo");
            crawl("http://www.vatgia.com/438/1,163042/mobile-gionee.html","Mobile","Gionee");
            crawl("http://www.vatgia.com/438/1,162628/mobile-q-mobile.html","Mobile","Q-Mobile");
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            closeObject(conn);
            closeDriver();
        }

    }

    public void getDataAfterCheck(){
        ResultSet rs = null;
        try{
            conn = getConnection();
            driver = loadDriverNotImage("firefox");
            rs = conn.prepareCall("select brand,model,link from VATGIA_CHECK where LINK not in (select LINK from VATGIA) and CREATE_DATE > GETDATE()-1 ").executeQuery();
            while(rs.next()){
                getDataEachURL(rs.getString(1),rs.getString(2),rs.getString(3));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            closeDriver(driver);
            closeObject(conn);
        }
    }

    public void transferDataVatGia(){
        PreparedStatement pre =null;
        ResultSet rs = null;
        Connection conn = null;
        Connection conn1 = null;
        String sql = "insert into vatgia values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://10.74.250.52;user=bcxnk;password=bcxnk;database=XNK");
            conn1 = DriverManager.getConnection("jdbc:sqlserver://localhost;user=sa;password=kimbaobao;database=XNK");
            conn1.setAutoCommit(false);
            pre = conn1.prepareStatement(sql);
            rs = conn.prepareStatement("Select * from vatgia where brand = 'Nokia'").executeQuery();
            rs.setFetchSize(100);
            int index = 1;
            while(rs.next()){
                int i=1;
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.setObject(i++, rs.getObject(i));
                pre.addBatch();
                if(index % 3000 ==0){
                    pre.executeBatch();
                }
                index++;

            }
            pre.executeBatch();
            conn1.commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            closeObject(pre);
            closeObject(rs);
            closeObject(conn);
            closeObject(conn1);
        }
    }

    public static void main(String[] args) {
        VatGiaAction vatgia = new VatGiaAction();
//        vatgia.begin();
        vatgia.getDataAfterCheck();
//        vatgia.transferDataVatGia();
    }
}
