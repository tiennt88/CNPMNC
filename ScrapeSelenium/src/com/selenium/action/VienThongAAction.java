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
public class VienThongAAction extends DatasourceC3p0 {

    private static WebDriver driver;
    private static Connection conn;
    final private static String web = "VienThongA";
    List<String> links = new ArrayList<String>();
    List<String> names = new ArrayList<String>();
    List<String> prices = new ArrayList<String>();
    List<String> promotions = new ArrayList<String>();
    List<String> descriptions = new ArrayList<String>();
    Map<String, String> brands = new HashMap();

    public void getDataVienThongA(String type, String brand) {

        try {
            conn = getConnection();
            driver = loadDriverNotImage("firefox");
            if ("Mobile".equals(type)) {
                driver.get("http://www.vienthonga.vn/dien-thoai-smartphones");
            } else if ("Tablet".equals(type)) {
                driver.get("http://www.vienthonga.vn/may-tinh-bang");
            } else if ("Laptop".equals(type)) {
                driver.get("http://www.vienthonga.vn/laptop");
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
        if (brands != null && brands.size() > 0) {
            for (String key : brands.keySet()) {
                String value = brands.get(key);
                getDataOfBrand(type, key, value);
            }
        }
    }

    public Map getListBrand(String type) {
        Map map = new HashMap();
        List<WebElement> webElements = null;
        WebElement a = null;
        a = getElement(driver, "xpath", "//span[text()='Thương hiệu']");
        if (a != null) {
//                System.out.println(a.getText());
            Actions action = new Actions(driver);
            action.moveToElement(a).build().perform();
        }

        webElements = getElements(driver, "xpath", "//ul[@id='content_product_more_filters_brandSelectBoxItOptions']/li");
        if (webElements != null) {
            for (int i = 1; i < webElements.size(); i++) {
//                System.out.println(webElements.get(i).getText()+":http://www.vienthonga.vn"+webElements.get(i).getAttribute("data-val"));
                if (webElements.get(i).getText().indexOf("iPad") >= 0) {
                    map.put("Apple", "http://www.vienthonga.vn" + webElements.get(i).getAttribute("data-val"));
                } else if (webElements.get(i).getText().indexOf("Q-mobile") >= 0) {
                    map.put("Q-MOBILE", "http://www.vienthonga.vn" + webElements.get(i).getAttribute("data-val"));
                } else if (webElements.get(i).getText().indexOf("Mobiistar") >= 0) {
                    map.put("MOBIISTAR", "http://www.vienthonga.vn" + webElements.get(i).getAttribute("data-val"));
                } else {
                    map.put(webElements.get(i).getText().toUpperCase().replace("MÁY TÍNH BẢNG ", "").replace("LAPTOP ", ""), "http://www.vienthonga.vn" + webElements.get(i).getAttribute("data-val"));
                }
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
//        WebElement promotion = null;
//        WebElement description = null;
        WebElement price = null;
        try {
            System.out.println("VIENTHONGA:" + driver.getTitle());
            webElements = getElements(driver, "css", "a.product-link");
//            webElements = getElements(driver,"css", "a.link-product:nth-child(even) ");
            if (webElements != null) {
                for (int i = 0; i < webElements.size(); i++) {
                    links.add(webElements.get(i).getAttribute("href"));
                    int position = i + 1;
                    price = getElement(driver, "css", "//*[@id='cateProductContent']/div[@class='masonry-item']["+position+"] / div / div[@class='product-overlay.animate'] / div[@class='overlay-price']");
//
                    if (price != null) {
                        prices.add(getText(driver, price));
                    } else {
                        prices.add("");
                    }

                    System.out.println((i+1)+"."+"links:"+links.get(i));
                    System.out.println((i+1)+"."+"prices:"+prices.get(i));
                }

            }
            webElements = getElements(driver, "css", "div.name");
             if (webElements != null) {
                for (int i = 0; i < webElements.size(); i++) {
                    names.add(webElements.get(i).getText());
                    System.out.println((i+1)+"."+"names:"+names.get(i));
                }
            }

//             webElements = getElements(driver, "css", "div.price");
//             if (webElements != null) {
//                for (int i = 0; i < webElements.size(); i++) {
//                    prices.add(webElements.get(i).getText());
//                    System.out.println("price:"+prices.get(i));
//                }
//            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getDataOfBrand(String type, String brand, String url) {
        try {
            driver.get(url);
            Thread.sleep(1000);

            WebElement webElement = null;
            webElement = getViewMore();
            int click = 1;
            while (webElement != null) {
                click++;
                if (click > 3) {
                    break;
                }
                try {
                    webElement.click();
                } catch (Exception ex) {
//                    ex.printStackTrace();
                }
                webElement = getViewMore();

            }
            Thread.sleep(1000);
            getListModel();
            DataDAO d = new DataDAO(conn);
            for (int j = 0; j < names.size(); j++) {
                Data item = new Data();
                item.setWeb(web);
                item.setType(type);
                item.setBrand(brand);
                item.setName(names.get(j));
                item.setLink(links.get(j));
                item.setPrice(prices.get(j));
//                item.setPromotion(promotions.get(j));
//                item.setDescription(descriptions.get(j));
                d.saveOrUpDateModel(item);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public WebElement getViewMore() {
        WebElement webElement = null;
        webElement = getElement(driver, "css", "#CategoryPager > button:nth-child(1)");
        return webElement;
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
            pre = d.getListNew("VienThongA", type, brand);
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
        WebElement chitiet = null;
        item.setMobileDataId(id);
        try {
            driver.get(link);
            Thread.sleep(1000);
            System.out.println("VIENTHONGA:" + driver.getTitle());
            chitiet = getElement(driver, "css", "#thongsokythuat > div > div.xem-chi-tiet.text-center.animate > button > span.plus");
            if (chitiet != null) {
                try {
                    chitiet.click();
                } catch (Exception ex) {
//                    ex.printStackTrace();
                }
                Thread.sleep(1000);
            }
            texts = getElements(driver, "css", "#thongsokythuat th");
            values = getElements(driver, "css", "#thongsokythuat td");
            if (values != null && values.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText().trim();
                    String value = values.get(i).getText().trim();
//                    System.out.println(text + ":" + value);
                    if (value.indexOf("inch") > 0 && item.getScreen() == null) {
                        item.setScreen(value);
                    } else if ((text.indexOf("CPU") == 0 || value.indexOf("GHz") > 0) && item.getCpu() == null) {
                        item.setCpu(value);
                    } else if (text.indexOf("Hệ Điều Hành") == 0 && item.getOs() == null) {
                        item.setOs(value);
                    } else if (text.indexOf("Camera chính") == 0 && item.getBackCamera() == null) {
                        item.setBackCamera(value);
                    } else if (text.indexOf("Camera Phụ") == 0 && item.getFrontCamera() == null) {
                        item.setFrontCamera(value);
                    } else if (text.indexOf("Bộ Nhớ Trong") == 0 && item.getStorage() == null) {
                        item.setStorage(value);
                        item.setRam(value);
                    } else if ((text.indexOf("Loại Pin") == 0 || value.indexOf("mAh") > 0) && item.getBattery() == null) {
                        item.setBattery(value);
                    } else if (text.indexOf("SIM") == 0) {
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

    //    lay du lieu chi tiet
    public void getDataEachURLTablet(Long id, String link) {
        List<WebElement> texts = null;
        List<WebElement> values = null;
        TabletDAO m = new TabletDAO(conn);
        Tablet item = new Tablet();
        WebElement chitiet = null;
        item.setTabletDataId(id);
        try {
            driver.get(link);
            Thread.sleep(1000);
            System.out.println("VIENTHONGA:" + driver.getTitle());
            chitiet = getElement(driver, "css", "#thongsokythuat > div > div.xem-chi-tiet.text-center.animate > button > span.plus");
            if (chitiet != null) {
                try {
                    chitiet.click();
                } catch (Exception ex) {
//                    ex.printStackTrace();
                }
                Thread.sleep(1000);
            }
            texts = getElements(driver, "css", "#thongsokythuat th");
            values = getElements(driver, "css", "#thongsokythuat td");
            if (values != null && values.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText().trim();
                    String value = values.get(i).getText().trim();
//                    System.out.println(text + ":" + value);
                    if (value.indexOf("inch") > 0 && item.getScreen() == null) {
                        item.setScreen(value);
                    } else if ((text.indexOf("CPU") == 0 || value.indexOf("GHz") > 0) && item.getCpu() == null) {
                        item.setCpu(value);
                    } else if (text.indexOf("Hệ Điều Hành") == 0 && item.getOs() == null) {
                        item.setOs(value);
                    } else if (text.indexOf("Camera chính") == 0 && item.getBackCamera() == null) {
                        item.setBackCamera(value);
                    } else if (text.indexOf("Camera Phụ") == 0 && item.getFrontCamera() == null) {
                        item.setFrontCamera(value);
                    } else if (text.indexOf("Bộ Nhớ Trong") == 0 && item.getStorage() == null) {
                        item.setStorage(value);
                        item.setRam(value);
                    } else if ((text.indexOf("Loại Pin") == 0 || value.indexOf("mAh") > 0) && item.getBattery() == null) {
                        item.setBattery(value);
                    } else if (text.indexOf("SIM") == 0) {
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
        item.setLaptopDataId(id);
        try {
            driver.get(link);
            Thread.sleep(1000);
            System.out.println("VIENTHONGA:" + driver.getTitle());
            chitiet = getElement(driver, "css", "#thongsokythuat > div > div.xem-chi-tiet.text-center.animate > button > span.plus");
            if (chitiet != null) {
                try {
                    chitiet.click();
                } catch (Exception ex) {
//                    ex.printStackTrace();
                }
                Thread.sleep(1000);
            }
            texts = getElements(driver, "css", "#thongsokythuat th");
            values = getElements(driver, "css", "#thongsokythuat td");
            if (values != null && values.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText().trim();
                    String value = values.get(i).getText().trim();
//                    System.out.println(text + ":" + value);
                    if (value.indexOf("inch") > 0 && item.getScreen() == null) {
                        item.setScreen(value);
                    } else if ((text.indexOf("CPU") == 0 || value.indexOf("GHz") > 0) && item.getCpu() == null) {
                        item.setCpu(value);
                    } else if (text.indexOf("RAM") == 0 && item.getRam() == null) {
                        item.setRam(value);
                    } else if (text.indexOf("Hệ Điều Hành") == 0 && item.getOs() == null) {
                        item.setOs(value);
                    } else if (text.indexOf("Ổ cứng") >= 0 && item.getHdd() == null) {
                        item.setHdd(value);
                    } else if (text.indexOf("Đồ họa") == 0) {
                        item.setVga(value);
                    } else if (text.indexOf("Đĩa quang") == 0) {
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
        VienThongAAction v = new VienThongAAction();
//        v.getData(null,null);
//        v.getConfiguration("Laptop", null);

        try {
            driver = loadDriverNotImage("firefox");
            driver.get("http://www.vienthonga.vn/dien-thoai-smartphones/apple-iphone/");
            WebElement webElement = null;
            webElement = getElement(driver, "xpath", "//*[@id='cateProductContent']//div[@class='product'][15]");
            System.out.println(webElement.getText());
//            webElement = getElement(driver, "xpath", "//*[@id='cateProductContent']//div[@class='product'][1]//div[@class='overlay-price']");
//            System.out.println(webElement.getText());
//                List<WebElement> list = null;
//                list = getElements(driver, "css", "#cateProductContent div.product");
//                for(int i=0;i<list.size();i++){
//                    System.out.println(list.get(i).getText());
//                }
//            System.out.println(getElement(driver, "css", "#cateProductContent > div:nth-child(16) > div > div.product-overlay.animate > div.overlay-price"));
//            System.out.println(getElement(driver, "css", "#cateProductContent > div:nth-child(17) > div > div.product-overlay.animate > div.overlay-price").getText());
//            System.out.println(getElement(driver, "css", "#cateProductContent > div:nth-child(18) > div > div.product-overlay.animate > div.overlay-price").getText());

//            webElement = v.getViewMore();
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
//                webElement = v.getViewMore();
//            }
//
//            v.getListModel();

//            driver.get("https://vienthonga.vn/samsung-galaxy-tab-4-7.0.html");
//            List<WebElement> texts = null;
//            List<WebElement> values = null;
//            WebElement chitiet = null;
//            Thread.sleep(1000);
//            System.out.println("VIENTHONGA:" + driver.getTitle());
//            chitiet = getElement(driver, "css", "#thongsokythuat > div > div.xem-chi-tiet.text-center.animate > button > span.plus");
//            if (chitiet != null) {
//                try {
//                    chitiet.click();
//                } catch (Exception ex) {
////                    ex.printStackTrace();
//                }
//                Thread.sleep(1000);
//            }
//            texts = getElements(driver, "css", "#thongsokythuat th");
//            values = getElements(driver, "css", "#thongsokythuat td");
//            if (values != null && values.size() > 0) {
//                int size = texts.size() <= values.size() ? texts.size() : values.size();
//                for (int i = 0; i < size; i++) {
//                    String text = texts.get(i).getText().trim();
//                    String value = values.get(i).getText().trim();
//                    System.out.println(text+"-"+value);
//                }
//
//                //save
//            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            driver.close();
        }

    }
}
