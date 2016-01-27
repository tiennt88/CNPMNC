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
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author KeyLove
 */
public class ViettelAction extends DatasourceC3p0 {

    private static WebDriver driver;
    private static Connection conn;
    final private static String web = "Viettel";
    List<String> links = new ArrayList<String>();
    List<String> names = new ArrayList<String>();
    List<String> prices = new ArrayList<String>();
    List<String> promotions = new ArrayList<String>();
    List<String> descriptions = new ArrayList<String>();
    Map<String, String> brands = new HashMap();

    public void getDataViettel(String type, String brand) {

        try {
            conn = getConnection();
            driver = loadDriverNotImage("firefox");
            driver.get("https://viettelstore.vn");
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
        WebElement webElement = null;
        Actions action = new Actions(driver);
        if ("Mobile".equals(type)) {
            webElement = getElement(driver, "xpath", "//div[@class='container']/ul/li[1]/a");
            action.moveToElement(webElement).build().perform();
            webElements = getElements(driver, "xpath", "//ul[@id='divCatMenuHome112']/li[1]//div[@class='col-list']/ul[1]/li/a");
        } else if ("Tablet".equals(type)) {
            webElement = getElement(driver, "xpath", "//div[@class='container']/ul/li[3]/a");
            action.moveToElement(webElement).build().perform();
            webElements = getElements(driver, "xpath", "//ul[@id='divCatMenuHome112']/li[3]//div[@class='col-list']/ul[1]/li/a");
        } else if ("Laptop".equals(type)) {
            webElement = getElement(driver, "xpath", "//div[@class='container']/ul/li[2]/a");
            action.moveToElement(webElement).build().perform();
            webElements = getElements(driver, "xpath", "//ul[@id='divCatMenuHome112']/li[2]//div[@class='col-list']/ul[1]/li/a");
        }
        if (webElements != null) {
            for (int i = 0; i < webElements.size(); i++) {
//                System.out.println(webElements.get(i).getText());
                map.put(webElements.get(i).getText().toUpperCase(), webElements.get(i).getAttribute("href"));
            }
        }
        return map;
    }

    public void getListModel() {
        List<WebElement> webElements = null;
        links.clear();
        names.clear();
        prices.clear();
        promotions.clear();
        descriptions.clear();
        try {
//            System.out.println("VIETTEL:" + driver.getTitle());
            webElements = getElements(driver, "css", "#div_Danh_Sach_San_Pham > div.item.ProductList3Col_item > a");
            if (webElements != null) {
                for (int i = 0; i < webElements.size(); i++) {
                    links.add(webElements.get(i).getAttribute("href"));
                }
            }
            webElements = getElements(driver, "css", "#div_Danh_Sach_San_Pham h2.name");
            if (webElements != null) {
                for (int i = 0; i < webElements.size(); i++) {
                    names.add(webElements.get(i).getText());
                }
            }
            webElements = getElements(driver, "css", "#div_Danh_Sach_San_Pham div.price");
            if (webElements != null) {
                for (int i = 0; i < webElements.size(); i++) {
                    prices.add(webElements.get(i).getText());
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getDataOfBrand(String type, String brand, String url) {
        DataDAO d = new DataDAO(conn);
        try {
            
            driver.get(url);
            Thread.sleep(1000);
//            List<String> listPage = getListPage();
            if(driver.getTitle().toLowerCase().indexOf(brand.toLowerCase()) >= 0 || driver.getTitle().toLowerCase().indexOf("nokia") >= 0 || driver.getTitle().toLowerCase().indexOf("microsoft") >= 0 || driver.getTitle().toLowerCase().indexOf("philip") >= 0){
                getListModel();
                for (int j = 0; j < names.size(); j++) {
                    Data item = new Data();
                    item.setWeb(web);
                    item.setType(type);
                    item.setBrand(brand.toUpperCase());
                    item.setName(names.get(j));
                    item.setLink(links.get(j));
                    item.setPrice(prices.get(j));
    //                item.setPromotion(promotions.get(j));
    //                item.setDescription(descriptions.get(j));
                    d.saveOrUpDateModel(item);
                }
            }else{
                d.disableLink(web,type,brand);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    public List<String> getListPage() {
//        List<String> url = new ArrayList<String>();
//        List<WebElement> webElements = null;
//        try {
//            webElements = driver.findElements(By.cssSelector("ul.fr li a"));
//            if (webElements != null && webElements.size() > 0) {
//                for (int i = 2; i < webElements.size() - 1; i++) {
//                    url.add(webElements.get(i).getAttribute("href"));
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return url;
//    }
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
            
            texts = getElements(driver,"css","div.row.digital > table > tbody > tr > td:nth-child(1)");
            values = getElements(driver,"css","div.row.digital > table > tbody > tr > td:nth-child(2)");

            if (texts != null && texts.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText().trim();
                    String value = values.get(i).getText().trim();
//                    System.out.println(text + ":" + value);
                    if ((text.indexOf("Màn hình") == 0 || value.indexOf("inch") > 0) && item.getScreen() == null) {
                        item.setScreen(value);
                    } else if ((text.indexOf("CPU") == 0 || value.indexOf("GHz") > 0) && item.getCpu() == null) {
                        item.setCpu(value);
                    } else if (text.indexOf("RAM") == 0 && item.getRam() == null) {
                        item.setRam(value);
                    } else if (text.indexOf("Hệ điều hành") == 0 && item.getOs() == null) {
                        item.setOs(value);
                    } else if (text.indexOf("Camera chính") == 0 && item.getBackCamera() == null) {
                        item.setBackCamera(value);
                    } else if (text.indexOf("Camera phụ") == 0 && item.getFrontCamera() == null) {
                        item.setFrontCamera(value);
                    } else if (text.indexOf("Bộ nhớ trong") == 0 && item.getStorage() == null) {
                        item.setStorage(value);
                    } else if ((text.indexOf("Pin") == 0 || value.indexOf("mAh") > 0) && item.getBattery() == null) {
                        item.setBattery(value);
                    } else if (text.indexOf("Số khe cắm sim") == 0) {
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
        TabletDAO m = new TabletDAO(conn);
        Tablet item = new Tablet();
        item.setTabletDataId(id);
        try {
            driver.get(link);
            Thread.sleep(1000);

            texts = getElements(driver,"css","div.row.digital > table > tbody > tr > td:nth-child(1)");
            values = getElements(driver,"css","div.row.digital > table > tbody > tr > td:nth-child(2)");

            if (texts != null && values.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText().trim();
                    String value = values.get(i).getText().trim();
//                    System.out.println(text + ":" + value);
                    if ((text.indexOf("Kích thước màn hình") == 0 || value.indexOf("inch") > 0) && item.getScreen() == null) {
                        item.setScreen(value);
                    } else if ((text.indexOf("CPU") == 0 || value.indexOf("GHz") > 0) && item.getCpu() == null) {
                        item.setCpu(value);
                    } else if (text.indexOf("RAM") == 0 && item.getRam() == null) {
                        item.setRam(value);
                    } else if (text.indexOf("Hệ điều hành") == 0 && item.getOs() == null) {
                        item.setOs(value);
                    } else if (text.indexOf("Camera chính") == 0 && item.getBackCamera() == null) {
                        item.setBackCamera(value);
                    } else if (text.indexOf("Camera trước") == 0 && item.getFrontCamera() == null) {
                        item.setFrontCamera(value);
                    } else if (text.indexOf("Bộ nhớ trong") == 0 && item.getStorage() == null) {
                        item.setStorage(value);
                    } else if ((text.indexOf("Pin") == 0 || value.indexOf("mAh") > 0) && item.getBattery() == null) {
                        item.setBattery(value);
                    } else if (text.indexOf("Số khe cắm sim") == 0) {
                        item.setSim(value);
                    }
                }

                //save
                m.saveTablet(item);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getDataEachURLLaptop(Long id, String link) {
        List<WebElement> texts = null;
        List<WebElement> values = null;
        LaptopDAO m = new LaptopDAO(conn);
        Laptop item = new Laptop();
        WebElement chitiet = null;
        Actions action = new Actions(driver);
        item.setLaptopDataId(id);
        try {
            driver.get(link);
            Thread.sleep(1000);

            texts = getElements(driver,"css","div.row.digital > table > tbody > tr > td:nth-child(1)");
            values = getElements(driver,"css","div.row.digital > table > tbody > tr > td:nth-child(2)");

            if (texts != null && values.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText().trim();
                    String value = values.get(i).getText().trim();
//                    System.out.println(text + ":" + value);
                    if ((text.indexOf("Màn hình") == 0 || value.indexOf("inch") > 0) && item.getScreen() == null) {
                        item.setScreen(value);
                    } else if ((text.indexOf("Bộ vi xử lý") == 0 || value.indexOf("GHz") > 0) && item.getCpu() == null) {
                        item.setCpu(value);
                    } else if (text.indexOf("Bộ nhớ") == 0 && item.getRam() == null) {
                        item.setRam(value);
                    } else if (text.indexOf("Hệ điều hành") == 0 && item.getOs() == null) {
                        item.setOs(value);
                    } else if (text.indexOf("Ổ cứng") == 0 && item.getHdd() == null) {
                        item.setHdd(value);
                    } else if (text.indexOf("Đồ họa") == 0) {
                        item.setVga(value);
                    } else if (text.indexOf("Ổ quang") == 0) {
                        item.setDvd(value);
                    } else if (text.indexOf("Cảm ứng màn hình") == 0) {
                        item.setTouchScreen(value);
                    }
                }

                //save
                m.saveLaptop(item);
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

        ViettelAction viettel = new ViettelAction();
        viettel.getData(null, null);
        viettel.getConfiguration("Laptop", null);
        try {
//            driver = loadDriver("firefox");
//            driver.get("http://viettelstore.vn/oppo-mid8.html?categoryId=010001");
//            List<WebElement> webElements = null;
//            System.out.println("VIETTEL:" + driver.getTitle());
//            webElements = getElements(driver, "css", "#div_Danh_Sach_San_Pham > div.item.ProductList3Col_item > a");
//            if (webElements != null) {
//                for (int i = 0; i < webElements.size(); i++) {
//                    System.out.println(i+"."+webElements.get(i).getAttribute("href"));
//                }
//            }
//            webElements = getElements(driver, "css", "#div_Danh_Sach_San_Pham h2.name");
//            if (webElements != null) {
//                for (int i = 0; i < webElements.size(); i++) {
//                    System.out.println(i+"."+webElements.get(i).getText());
//                }
//            }
//            webElements = getElements(driver, "css", "#div_Danh_Sach_San_Pham div.price");
//            if (webElements != null) {
//                for (int i = 0; i < webElements.size(); i++) {
//                    System.out.println(i+"."+webElements.get(i).getText());
//                }
//            }
//            System.setProperty("webdriver.chrome.driver", "F:\\du an\\Anh Hoai\\ScrapeSelenium\\driver\\chromedriver.exe");
//            driver = new ChromeDriver();
//            driver.manage().window().maximize();
//            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//            driver.get("http://viettelstore.vn/");
//            driver.get("http://viettelstore.vn/may-tinh-bang/ipad-air-2-16gb-wifi-cellular-pid138.html");
//            System.out.println(driver.getTitle());
//            List<WebElement> texts = null;
//            List<WebElement> values = null;
//            Mobile item = new Mobile();
//            texts = getElements(driver,"css","div.row.digital > table > tbody > tr > td:nth-child(1)");
//            values = getElements(driver,"css","div.row.digital > table > tbody > tr > td:nth-child(2)");
//            if (texts != null && texts.size() > 0) {
//                for (int i = 0; i < values.size(); i++) {
//                    String text = texts.get(i).getText().trim();
//                    String value = values.get(i).getText().trim();
//                    System.out.println(text + ":" + value);
//                    if ((text.indexOf("Kích thước màn hình") == 0 || value.indexOf("inch") > 0) && item.getScreen() == null) {
//                        item.setScreen(value);
//                    } else if ((text.indexOf("CPU") == 0 || value.indexOf("GHz") > 0) && item.getCpu() == null) {
//                        item.setCpu(value);
//                    } else if (text.indexOf("RAM") == 0 && item.getRam() == null) {
//                        item.setRam(value);
//                    } else if (text.indexOf("Hệ điều hành") == 0 && item.getOs() == null) {
//                        item.setOs(value);
//                    } else if (text.indexOf("Camera chính") == 0 && item.getBackCamera() == null) {
//                        item.setBackCamera(value);
//                    } else if (text.indexOf("Camera trước") == 0 && item.getFrontCamera() == null) {
//                        item.setFrontCamera(value);
//                    } else if (text.indexOf("Bộ nhớ trong") == 0 && item.getStorage() == null) {
//                        item.setStorage(value);
//                    } else if ((text.indexOf("Pin") == 0 || value.indexOf("mAh") > 0) && item.getBattery() == null) {
//                        item.setBattery(value);
//                    } else if (text.indexOf("Số khe cắm sim") == 0) {
//                        item.setSim(value);
//                    }
//                }
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
//            driver.quit();
        }

    }
}
