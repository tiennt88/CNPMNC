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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author KeyLove
 */
public class TGDDAction extends DatasourceC3p0 {

    private static WebDriver driver;
    private static Connection conn;
    final private static String web = "TGDD";
    List<String> links = new ArrayList<String>();
    List<String> names = new ArrayList<String>();
    List<String> prices = new ArrayList<String>();
    List<String> promotions = new ArrayList<String>();
    List<String> descriptions = new ArrayList<String>();
    Map<String, String> brands = new HashMap();

    public void getDataTGDD(String type, String brand) {

        try {
            conn = getConnection();
            driver = loadDriver("firefox");
            if ("Mobile".equals(type)) {
                driver.get("https://www.thegioididong.com/dtdd");
            } else if ("Tablet".equals(type)) {
                driver.get("https://www.thegioididong.com/may-tinh-bang");
            } else if ("Laptop".equals(type)) {
                driver.get("https://www.thegioididong.com/laptop");
            }
            Thread.sleep(2000);
            if (brand == null) {
                getDataOfType(type);
            } else {
                brands = getListBrand(type);
                getDataOfBrand(type, brand, brands.get(brand));
            }
            DataDAO d = new DataDAO(conn);
            d.updatePrice(web, type, null);
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
        WebElement a = null;
        a = getElement(driver, "xpath", "//a[text()='Hãng sản xuất']");
        if (a != null) {
//                System.out.println(a.getText());
            Actions action = new Actions(driver);
            try {
                action.moveToElement(a).click().perform();
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TGDDAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        webElements = getElements(driver, "xpath", "//ul[@class='filter']/li[3]/ul//li[not(@class)]//a[not(@class)]");
        if (webElements != null && webElements.size() > 0) {
            for (int i = 0; i < webElements.size(); i++) {
//                System.out.println(webElements.get(i).getText());
//                System.out.println(webElements.get(i).getAttribute("href"));

                  if(webElements.get(i).getAttribute("href").indexOf("apple")>=0){
                       map.put("APPLE", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("samsung")>=0){
                       map.put("SAMSUNG", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("asus")>=0){
                       map.put("ASUS", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("sony")>=0){
                       map.put("SONY", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("nokia")>=0){
                       map.put("NOKIA", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("htc")>=0){
                       map.put("HTC", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("lg")>=0){
                       map.put("LG", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("philips")>=0){
                       map.put("PHILIPS", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("gionee")>=0){
                       map.put("GIONEE", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("lenovo")>=0){
                       map.put("LENOVO", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("mobiistar")>=0){
                       map.put("MOBIISTAR", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("bavapen")>=0){
                       map.put("BAVAPEN", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("q-mobile")>=0){
                       map.put("Q-MOBILE", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("pantech")>=0){
                       map.put("PANTECH", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("dell")>=0){
                       map.put("DELL", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("acer")>=0){
                       map.put("ACER", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("hp")>=0){
                       map.put("HP", webElements.get(i).getAttribute("href"));
                  }
                  if(webElements.get(i).getAttribute("href").indexOf("huawei")>=0){
                       map.put("HUAWEI", webElements.get(i).getAttribute("href"));
                  }
                  
//                if (webElements.get(i).getText().indexOf("Apple") >= 0) {
//                    map.put("APPLE", webElements.get(i).getAttribute("href"));
//                } else if (webElements.get(i).getText().indexOf("Asus") >= 0) {
//                    map.put("ASUS", webElements.get(i).getAttribute("href"));
//                } else if (webElements.get(i).getText().indexOf("HP") >= 0) {
//                    map.put("HP", webElements.get(i).getAttribute("href"));
//                } else {
//                    map.put(webElements.get(i).getText().toUpperCase(), webElements.get(i).getAttribute("href"));
//                }
                
            }
        }
        return map;
    }

    public void getDataOfBrand(String type, String brand, String url) throws Exception {
       
        DataDAO d = new DataDAO(conn);
        driver.get(url);
        Thread.sleep(1000);

        getListModel(type);
        if(driver.getTitle().toLowerCase().indexOf(brand.toLowerCase()) >= 0 || "Apple".equals(brand) || "Nokia".equals(brand)){
            for (int i = 0; i < names.size(); i++) {
                Data item = new Data();
                item.setWeb(web);
                item.setType(type);
                item.setBrand(brand);
                item.setName(names.get(i));
                item.setLink(links.get(i));
                item.setPrice(prices.get(i));
    //            item.setPromotion(promotions.get(i));
    //            item.setDescription(descriptions.get(i));
                d.saveOrUpDateModel(item);
    //            System.out.println(item.getName());
    //            System.out.println(item.getLink());
    //            System.out.println(item.getPrice());
            }
        }else{
            System.out.println("Disable:"+web+","+type+","+brand);
            d.disableLink(web,type,brand);
        }
    }

    public void getListModel(String type) {
        List<WebElement> webElements = null;
        links.clear();
        names.clear();
        prices.clear();
        promotions.clear();
        descriptions.clear();
        WebElement promotion = null;
        WebElement price = null;
        try {
            System.out.println("TGDĐ:" + driver.getTitle());
            webElements = getElements(driver, "css", "#lstprods > li > a > h3");
            if (webElements != null) {
                for (int i = 0; i < webElements.size(); i++) {
                    names.add(webElements.get(i).getText());
//                    int position = i + 1;
//                    promotion = getElement(driver,"xpath", "//section[@class='listproduct']/a[" + position + "]//span[@class='textkm']");
//                    if (promotion != null) {
//                        promotions.add(getText(driver,promotion));
//                    } else {
//                        promotions.add("");
//                    }
                }
            }
            webElements = getElements(driver, "xpath", "//ul[@id='lstprods']/li/a[position()=1]");
            if (webElements != null) {
                for (int i = 0; i < webElements.size(); i++) {
                    links.add(webElements.get(i).getAttribute("href"));
                }
            }
            if("Mobile".equals(type)){
               webElements = getElements(driver, "css", "#lstprods > li > a strong");
            }else{
               webElements = getElements(driver, "xpath", "//ul[@id='lstprods']/li//strong[position()=1]");
            }
            
            if (webElements != null) {
                for (int i = 0; i < webElements.size(); i++) {
                    prices.add(webElements.get(i).getText());
                }
            }

            //test
            for(int i=0; i<names.size();i++){
                System.out.println("-----------"+(i+1)+"--------------");
                System.out.println("name:"+names.get(i));
                System.out.println("link:"+links.get(i));
                System.out.println("price:"+prices.get(i));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void getConfiguration(String type, String brand) {
        PreparedStatement pre = null;
        ResultSet rs = null;

        try {
            driver = loadDriver("firefox");
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

    //    lay du lieu chi tiet
    public void getDataEachURLMobile(Long id, String link) {
        List<WebElement> texts = null;
        List<WebElement> values = null;
        MobileDAO m = new MobileDAO(conn);
        Mobile item = new Mobile();
        item.setMobileDataId(id);
        try {
            driver.get(link);
            Thread.sleep(1000);
            System.out.println("TGDĐ:" + driver.getTitle());

            texts = getElements(driver, "css", "ul.parameter li span");
            values = getElements(driver, "css", "ul.parameter li div");

            if (texts != null && texts.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText().trim();
                    String value = values.get(i).getText().trim();
                    if (text.indexOf("Màn hình:") == 0 || value.indexOf("inch") > 0) {
                        item.setScreen(value);
                    } else if (text.indexOf("CPU:") == 0 || value.indexOf("GHz") > 0) {
                        item.setCpu(value);
                    } else if (text.indexOf("RAM") == 0) {
                        item.setRam(value);
                    } else if (text.indexOf("Hệ điều hành:") == 0) {
                        item.setOs(value);
                    } else if (text.indexOf("Camera sau:") == 0) {
                        item.setBackCamera(value);
                    } else if (text.indexOf("Camera trước:") == 0) {
                        item.setFrontCamera(value);
                    } else if (text.indexOf("Bộ nhớ trong:") == 0) {
                        item.setStorage(value);
                    } else if (text.indexOf("Dung lượng pin") == 0 || value.indexOf("mAh") > 0) {
                        item.setBattery(value);
                    } else if (text.indexOf("SIM") >= 0) {
                        item.setSim(value);
                    }
                }
                //save
                m.saveMobile(item);
            }
            //insert
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getDataEachURLTablet(Long id, String link) {
        List<WebElement> texts = null;
        List<WebElement> values = null;
        TabletDAO m = new TabletDAO(conn);
        Tablet item = new Tablet();
        item.setTabletDataId(id);
        try {
            driver.get(link);
            Thread.sleep(1000);
            System.out.println("TGDĐ:" + driver.getTitle());

            texts = getElements(driver, "css", "ul.parameter li span");
            values = getElements(driver, "css", "ul.parameter li div");

            if (texts != null && texts.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText();
                    String value = values.get(i).getText().trim();
                    if (text.indexOf("Màn hình") == 0 || value.indexOf("inch") > 0) {
                        item.setScreen(value);
                    } else if (text.indexOf("CPU") == 0) {
                        item.setCpu(value);
                    } else if (text.indexOf("RAM") == 0) {
                        item.setRam(value);
                    } else if (text.indexOf("Hệ điều hành") == 0) {
                        item.setOs(value);
                    } else if (text.indexOf("Bộ nhớ trong") == 0) {
                        item.setStorage(value);
                    } else if (text.indexOf("Dung lượng pin") == 0 || value.indexOf("mAh") > 0) {
                        item.setBattery(value);
                    }else if (text.indexOf("Camera sau") == 0) {
                        item.setBackCamera(value);
                    }else if (text.indexOf("Camera trước") == 0) {
                        item.setFrontCamera(value);
                    }else if (text.indexOf("SIM") >= 0) {
                        item.setSim(value);
                    }
                }

                //save
                m.saveTablet(item);
            }

            //insert
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getDataEachURLLaptop(Long id, String link) {
        List<WebElement> texts = null;
        List<WebElement> values = null;
        LaptopDAO l = new LaptopDAO(conn);
        Laptop item = new Laptop();
        item.setLaptopDataId(id);
        try {
            driver.get(link);
            Thread.sleep(1000);
            System.out.println("TGDĐ:" + driver.getTitle());
            texts = getElements(driver, "css", "ul.parameter li span ");
            values = getElements(driver, "css", "ul.parameter li div");

            if (texts != null && texts.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText();
                    String value = values.get(i).getText().trim();
                    if (text.indexOf("CPU") == 0) {
                        item.setCpu(value);
                    } else if (text.indexOf("RAM") == 0) {
                        item.setRam(value);
                    } else if (text.indexOf("Đĩa cứng") == 0 && item.getHdd() != null) {
                        item.setHdd(value);
                    } else if (text.indexOf("Màn hình rộng") == 0) {
                        item.setScreen(value);
                    } else if (text.indexOf("Cảm ứng") == 0) {
                        item.setTouchScreen(value);
                    } else if (text.indexOf("Đồ họa") == 0) {
                        item.setVga(value);
                    } else if (text.indexOf("Đĩa quang") == 0) {
                        item.setDvd(value);
                    } else if (text.indexOf("PIN/Battery")== 0) {
                        item.setBattery(value);
                    } else if (text.indexOf("HĐH kèm theo máy") == 0) {
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
            driver = loadDriver("firefox");
            conn = getConnection();
            DataDAO d = new DataDAO(conn);
            pre = d.getListURL(web, type, brand);
            rs = pre.executeQuery();
            while (rs.next()) {
                getDataOfBrand(rs.getString(1),rs.getString(3),rs.getString(4));
            }
            if(type != null && !"All".equals(type)){
                d.updatePrice(web, type, null);
            }else{
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

        TGDDAction tgdd = new TGDDAction();
        tgdd.getData(null, null);
//        tgdd.getConfiguration("Mobile", null);
//        try{
//            List<WebElement> webElements = null;
//            driver = loadDriver("firefox");
//            driver.manage().window().maximize();
//            driver.get("https://www.thegioididong.com/dtdd-nokia");
//            webElements = getElements(driver, "css", "section.listproduct a figure  h4");
//            if (webElements != null) {
//                for (int i = 0; i < webElements.size(); i++) {
//                    System.out.println(webElements.get(i).getText());
//                }
//            }
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }finally{
//            driver.close();
//        }

    }
}
