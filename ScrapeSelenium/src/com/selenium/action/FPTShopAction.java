/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.selenium.action;

import com.selenium.BO.Data;
import com.selenium.BO.Laptop;
import com.selenium.BO.Mobile;
import com.selenium.BO.Tablet;
import com.selenium.DAO.DataDAO;
import com.selenium.DAO.DatasourceC3p0;
import com.selenium.DAO.LaptopDAO;
import com.selenium.DAO.MobileDAO;
import com.selenium.DAO.TabletDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author KeyLove
 */
public class FPTShopAction extends DatasourceC3p0 {

    private static Connection conn;
    private static WebDriver driver;
    final private static String web = "FPTShop";
    List<String> links = new ArrayList<String>();
    List<String> names = new ArrayList<String>();
    List<String> prices = new ArrayList<String>();
    List<String> promotions = new ArrayList<String>();
    List<String> descriptions = new ArrayList<String>();
    Map<String, String> brands = new HashMap();

    public void getDataFPTShop(String type, String brand) {

        try {
            conn = getConnection();
            driver = loadDriverNotImage("firefox");
            driver.get("http://fptshop.com.vn/");
            Thread.sleep(2000);
            if (brand == null) {
                getDataOfType(type);
            } else {
                brands = getListBrand(type);
                getDataOfBrand(type, brand, brands.get(brand));
            }
            DataDAO d = new DataDAO(conn);
            d.updatePrice("FPTShop", type, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(conn);
            closeDriver(driver);
        }
    }

    public void getDataOfType(String type) throws Exception {
        brands = getListBrand(type);
        for (String key : brands.keySet()) {
            String value = brands.get(key);
            getDataOfBrand(type, key, value);
        }
    }

    public Map getListBrand(String type) {
        Map map = new HashMap();
        List<WebElement> webElements = null;
        if ("Mobile".equals(type)) {
            webElements = getElements(driver, "xpath", "//div[@class='fshop-nav']/ul/li[position()=1]//ul[@class='fshop-nsm-list fshop-nsm-triple']//a");
        } else if ("Tablet".equals(type)) {
            webElements = getElements(driver, "xpath", "//div[@class='fshop-nav']/ul/li[position()=2]//ul[@class='fshop-nsm-list fshop-nsm-triple']//a");
        } else if ("Laptop".equals(type)) {
            webElements = getElements(driver, "xpath", "//div[@class='fshop-nav']/ul/li[position()=4]//div[@class='fshop-nsm-col fshop-nsm-col35'][1]//a");
        }

        if (webElements != null) {
            for (int i = 0; i < webElements.size(); i++) {
                if (webElements.get(i).getAttribute("title").indexOf("Apple") >= 0) {
                    map.put("APPLE", webElements.get(i).getAttribute("href"));
                } else {
                    map.put(webElements.get(i).getAttribute("title").toUpperCase(), webElements.get(i).getAttribute("href"));
                }
            }
        }
        return map;
    }

    public void getDataOfBrand(String type, String brand, String url) throws Exception {
        WebElement webElement = null;
        DataDAO d = new DataDAO(conn);
        driver.get(url);
        Thread.sleep(1000);
        //check link
        if(driver.getTitle().toLowerCase().indexOf(brand.toLowerCase()) >= 0){
            webElement = getViewMore();
            int click = 1;
            while (webElement != null) {
                try {
                    click++;
                    if(click >3) break;
                    webElement.click();
                } catch (Exception ex) {
    //                ex.printStackTrace();
                }
                webElement = getViewMore();
            }

            getListModel();

            for (int i = 0; i < names.size(); i++) {
                Data item = new Data();
                item.setWeb(web);
                item.setType(type);
                item.setBrand(brand);
                item.setName(names.get(i));
                item.setLink(links.get(i));
                item.setPrice(prices.get(i));
                item.setPromotion(promotions.get(i));
    //            item.setDescription(descriptions.get(i));
                d.saveOrUpDateModel(item);
            }
        }else{
            d.disableLink(web,type,brand);
        }
        
    }

    public WebElement getViewMore() {
        WebElement webElement = null;
        webElement = getElement(driver, "xpath", "//a[@class='p-more view-more']");
        return webElement;
    }

    public void getListModel() {
        List<WebElement> webElements = null;
        List<WebElement> webElements2 = null;
        WebElement promotion = null;
        WebElement descrition = null;
        names.clear();
        links.clear();
        prices.clear();
        promotions.clear();
        descriptions.clear();

        System.out.println("FPTShop:" + driver.getTitle());

        webElements = getElements(driver, "xpath", "//div[@class='p-name']//a");
        if (webElements != null) {
            for (int i = 0; i < webElements.size(); i++) {
                names.add(webElements.get(i).getText());
                links.add(webElements.get(i).getAttribute("href"));
                //add promotion
                int position = i + 3;
                promotion = getElement(driver, "xpath", "//div[@id='category-products']/div[" + position + "]//div[@class='best-seller-order']");
//                promotion = getElement(driver,"xpath", "//*[@id='listproductpage']/div[2]/div[2]/div[2]/div/div/ul/li[" + position + "]/section/div/a/div[2]/div[1]");
                if (promotion != null) {
                    promotions.add(getText(driver, promotion));
                } else {
                    promotions.add("");
                }
//                descrition = getElement(driver,"xpath", "//div[@class='productlist']//ul[@class='clearfix list-product-hot']/li[" + position + "]//div[@class='description']//div[@class='hightlight-des']");
//                if(descrition !=null){
//                    Actions action = new Actions(driver);
//                    action.moveToElement(descrition).build().perform();
//                    descriptions.add(descrition.getText());
//                }else{
//                    descriptions.add("");
//                }
//                System.out.println(i+1);
//                System.out.println("names:"+names.get(i));
//                System.out.println("promotions:"+promotions.get(i));
                
            }
        }
        webElements2 = getElements(driver, "css", "div.p-price");
        if (webElements2 != null) {
            for (int i = 0; i < webElements2.size(); i++) {
                prices.add(webElements2.get(i).getText());
//                System.out.println(i+1);
//                System.out.println("prices:"+prices.get(i));
            }
        }




    }

    public void getConfiguration(String type, String brand) {
        PreparedStatement pre = null;
        ResultSet rs = null;

        try {
            driver = loadDriverNotImage("firefox");
            conn = getConnection();
            DataDAO d = new DataDAO(conn);
            pre = d.getListNew(web, type, brand);
            rs = pre.executeQuery();
            while (rs.next()) {
                if ("Mobile".equals(rs.getString(3))) {
                    getDataEachURLMobile(rs.getLong(1), rs.getString(2));
                } else if ("Tablet".equals(rs.getString(3))) {
                    getDataEachURLTablet(rs.getLong(1), rs.getString(2));
                } else if ("Laptop".equals(rs.getString(3))) {
                    getDataEachURLLaptop(rs.getLong(1), rs.getString(2));
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(rs);
            closeObject(pre);
            closeObject(conn);
            closeDriver(driver);
        }
    }

    public void getDataEachURLMobile(Long id, String link) {
        List<WebElement> texts = null;
        List<WebElement> values = null;
        MobileDAO m = new MobileDAO(conn);
        Mobile item = new Mobile();
        item.setMobileDataId(id);
        WebElement thongso = null;
        try {
            driver.get(link + "#thong-so-ky-thuat");

            Thread.sleep(1000);
            thongso = getElement(driver,"css","a.detail-specific-more");
            if(thongso!=null){
                try{
                    thongso.click();
                }catch(Exception ex){
                    System.out.println("error when click thong so ky thuat:"+ ex.getMessage());
                }
            }
//            if(thongso!=null){
//               Actions action = new Actions(driver);
//               action.moveToElement(thongso).build().perform();
//               action.moveToElement(thongso).click().perform();
//            }
            System.out.println("FPTShop:" + driver.getTitle());

            texts = getElements(driver, "xpath", "//div[@class='left detail-col-left53']//ul/li//label");
            values = getElements(driver, "xpath", "//div[@class='left detail-col-left53']//ul/li//span");
            if (texts != null && texts.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText().trim();
                    String value = values.get(i).getText().trim();
                    if (text.indexOf("Màn hình :") == 0 || value.indexOf("inch") > 0) {
                        item.setScreen(value);
                    } else if (text.indexOf("CPU :") == 0) {
                        item.setCpu(value);
                    } else if (text.indexOf("RAM :") == 0) {
                        item.setRam(value);
                    } else if (text.indexOf("Hệ điều hành :") == 0) {
                        item.setOs(value);
                    } else if (text.indexOf("Camera sau :") == 0) {
                        item.setBackCamera(value);
                    } else if (text.indexOf("Camera trước :") == 0) {
                        item.setFrontCamera(value);
                    } else if (text.indexOf("Bộ nhớ trong :") == 0) {
                        item.setStorage(value);
                    } else if (text.indexOf("Dung lượng pin :") == 0 || value.indexOf("mAh") > 0) {
                        item.setBattery(value);
                    } else if (text.indexOf("Khe cắm sim :") == 0) {
                        item.setSim(value);
                    }
                }
                //save
                m.saveMobile(item);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getDataEachURLTablet(Long id, String link) {
        List<WebElement> texts = null;
        List<WebElement> values = null;
        TabletDAO t = new TabletDAO(conn);
        Tablet item = new Tablet();
        item.setTabletDataId(id);
        try {
            driver.get(link + "#thong-so-ky-thuat");
            Thread.sleep(1000);
            WebElement thongso = getElement(driver,"css","a.detail-specific-more");
            if(thongso!=null){
                try{
                    thongso.click();
                }catch(Exception ex){
                    System.out.println("error when click thong so ky thuat:"+ ex.getMessage());
                }
            }
            System.out.println("FPTShop:" + driver.getTitle());

            texts = getElements(driver, "xpath", "//div[@class='left detail-col-left53']//ul/li//label");
            values = getElements(driver, "xpath", "//div[@class='left detail-col-left53']//ul/li//span");
            if (texts != null && texts.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText();
                    String value = values.get(i).getText().trim();
                    if (text.indexOf("Màn hình") == 0 || value.indexOf("inch") > 0) {
                        item.setScreen(value);
                    } else if (text.indexOf("Vi xử lý CPU") == 0) {
                        item.setCpu(value);
                    } else if (text.indexOf("RAM") == 0) {
                        item.setRam(value);
                    } else if (text.indexOf("Hệ điều hành") == 0) {
                        item.setOs(value);
                    } else if (text.indexOf("Camera sau") == 0) {
                        item.setBackCamera(value);
                    } else if (text.indexOf("Camera trước") == 0) {
                        item.setFrontCamera(value);
                    } else if (text.indexOf("Bộ nhớ trong") == 0) {
                        item.setStorage(value);
                    } else if (text.indexOf("Dung lượng pin") == 0 || value.indexOf("mAh") > 0) {
                        item.setBattery(value);
                    } else if (text.indexOf("Hỗ trợ SIM") == 0) {
                        item.setSim(value);
                    }
                }
                t.saveTablet(item);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getDataEachURLLaptop(Long id, String link) {
        List<WebElement> texts = null;
        List<WebElement> values = null;
        Laptop item = new Laptop();
        LaptopDAO l = new LaptopDAO(conn);
        item.setLaptopDataId(id);
        try {
            driver.get(link + "#thong-so-ky-thuat");
            Thread.sleep(1000);
            WebElement thongso = getElement(driver,"css","a.detail-specific-more");
            if(thongso!=null){
                try{
                    thongso.click();
                }catch(Exception ex){
                    System.out.println("error when click thong so ky thuat:"+ ex.getMessage());
                }
            }
            System.out.println("FPTShop:" + driver.getTitle());

            texts = getElements(driver, "xpath", "//div[@class='left detail-col-left53']//ul/li//label");
            values = getElements(driver, "xpath", "//div[@class='left detail-col-left53']//ul/li//span");
            if (texts != null && texts.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText();
                    String value = values.get(i).getText().trim();
                    if (text.indexOf("Kích thước màn hình") >= 0) {
                        item.setScreen(value);
                    } else if (text.indexOf("Cảm ứng") >= 0) {
                        item.setTouchScreen(value);
                    } else if (text.indexOf("Công nghệ CPU") >= 0) {
                        item.setCpu(value);
                    } else if (text.indexOf("Loại CPU") >= 0) {
                        item.setCpu(item.getCpu() + " " + value);
                    } else if (text.indexOf("Tốc độ") >= 0 && item.getSpeed() != null) {
                        item.setSpeed(value);
                        item.setCpu(item.getCpu() + " " + value);
                    } else if (text.indexOf("Dung lượng RAM") >= 0) {
                        item.setRam(value);
                    } else if (text.indexOf("Loại ổ đĩa") >= 0) {
                        item.setHddType(value);
                    } else if (text.indexOf("Dung lượng ổ đĩa") >= 0) {
                        item.setHdd(value);
                    } else if (text.indexOf("Bộ nhớ đồ họa") >= 0) {
                        item.setVga(value);
                    } else if (text.indexOf("Hệ điều hành") >= 0) {
                        item.setOs(value);
                    }
                }
                l.saveLaptop(item);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getData(String type, String brand) {
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            driver = loadDriverNotImage("firefox");
            conn = getConnection();
            DataDAO d = new DataDAO(conn);
            pre = d.getListURL(web, type, brand);
            rs = pre.executeQuery();
            while (rs.next()) {
                getDataOfBrand(rs.getString(1), rs.getString(3), rs.getString(4));
            }
            if (type != null && !"All".equals(type)) {
                d.updatePrice(web, type, null);
            } else {
                d.updatePrice(web, "Mobile", null);
                d.updatePrice(web, "Tablet", null);
                d.updatePrice(web, "Laptop", null);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(rs);
            closeObject(pre);
            closeObject(conn);
            closeDriver(driver);
        }
    }

    public static void main(String[] args) {
        FPTShopAction fpt = new FPTShopAction();
//        fpt.getData(null, null);
        fpt.getConfiguration("Mobile", null);
//        fpt.getConfiguration("Tablet", null);
//        fpt.getConfiguration("Laptop", null);
//        driver = loadDriver("firefox");
//        try {
//            fpt.conn = fpt.getConnection();
//        } catch (SQLException ex) {
//            Logger.getLogger(FPTShopAction.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        fpt.getDataEachURLMobile(0l, "http://fptshop.com.vn/dien-thoai/samsung-galaxy-s6");
        
//        try {
//            driver = loadDriver("firefox");
//            driver.get("http://fptshop.com.vn/dien-thoai/samsung");
//            WebElement webElement = null;
//            webElement = fpt.getViewMore();
//            int click = 1;
//            while (webElement != null) {
//                click++;
//                if (click > 3) {
//                    break;
//                }
//                try {
//                    webElement.click();
//                } catch (Exception ex) {
////                    ex.printStackTrace();
//                }
//                webElement = fpt.getViewMore();
//            }
//
//            fpt.getListModel();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            driver.close();
//        }
    }
}
