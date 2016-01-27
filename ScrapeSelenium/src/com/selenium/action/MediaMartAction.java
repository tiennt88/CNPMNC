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
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author KeyLove
 */
public class MediaMartAction extends DatasourceC3p0 {

    private static WebDriver driver;
    private static Connection conn;
    final private static String web = "MediaMart";
    List<String> links = new ArrayList<String>();
    List<String> names = new ArrayList<String>();
    List<String> prices = new ArrayList<String>();
    List<String> promotions = new ArrayList<String>();
    List<String> descriptions = new ArrayList<String>();


    public void getListModel() {
        List<WebElement> webElements = null;
        links.clear();
        names.clear();
        prices.clear();
        promotions.clear();
        descriptions.clear();
        WebElement promotion = null;
        try {
            System.out.println("MediaMart:" + driver.getTitle());
            webElements = getElements(driver, "css", "ul.ls.temp-pro-item p.name a") ;

            if (webElements != null) {
                for (int i = 0; i < webElements.size(); i++) {
                    links.add(webElements.get(i).getAttribute("href"));
                    names.add(webElements.get(i).getText());
                    int position = i + 1;

                    List<WebElement> webElements1 = getElements(driver, "css", "#ajax-temp-pro > div.wrap-right > div.temp-pro.temp-pro-list > ul.ls.temp-pro-item > li:nth-child("+position+") > div.price > div.price-number-small span");
                    if(webElements1!= null){
                        String price = "";
                        for (int j = 0; j < webElements1.size()-1; j++) {
                            String str = webElements1.get(j).getAttribute("class");
                            if(str != null){
                                char lastChar = str.charAt(str.length() - 1);
                                if('t' == lastChar) lastChar = '.';
                                price  = price + lastChar;
                            }
                        }
                        price.replace("t", ".");
                        if(price.length()>=3){
                            price = price + ".000";
                        }
                        prices.add(price);
                    }
                    
//                    promotion = getElement(driver, "xpath", "//div[@id='div_Danh_Sach_San_Pham']/section[" + position + "]//ul[@class='ul_hover_pro']");
//                    if (promotion != null) {
////                        promotions.add(promotion.getText());
//                        promotions.add(getText(driver,promotion));
//                    } else {
//                        promotions.add("");
//                    }
                }
            }
//            webElements = webElements = getElements(driver, "css", "div.price div.price-number-small") ;
//            if (webElements != null) {
//                for (int i = 0; i < webElements.size(); i++) {
//                    prices.add(webElements.get(i).getText());
//                }
//            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getDataOfBrand(String type, String brand, String url) {
        DataDAO d = new DataDAO(conn);
        try {
            driver.get(url);
            Thread.sleep(1000);
            if(driver.getTitle().toLowerCase().indexOf(brand.toLowerCase()) >= 0 || driver.getTitle().toLowerCase().indexOf("qmobile") >= 0){
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

    //                System.out.println(item.getName());
    //                System.out.println(item.getLink());
    //                System.out.println(item.getPrice());
    //                System.out.println("--------"+j+"-------");
                }
            }else{
                d.disableLink(web,type,brand);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

    //    lay du lieu chi tiet
    public void getDataEachURLMobile(Long id, String link) {
        List<WebElement> texts = null;
        List<WebElement> values = null;
        MobileDAO m = new MobileDAO(conn);
        Mobile item = new Mobile();
        WebElement chitiet = null;
        Actions action = new Actions(driver);
        item.setMobileDataId(id);
        try {
            driver.get(link);
            Thread.sleep(1000);
            System.out.println("MediaMart:" + driver.getTitle());
            try {
                chitiet = getElement(driver, "xpath", "//a[@rel='#p-introduct-spec']");
                if (chitiet != null) {
                    action.moveToElement(chitiet).click().perform();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            

            texts = getElements(driver, "xpath", "//div[@class='p-introduct-spec-input']//td[not(@rowspan) and position() mod 2 = 1]");
            values = getElements(driver, "xpath", "//div[@class='p-introduct-spec-input']//td[not(@rowspan) and position() mod 2 = 0]");
            if (texts != null && texts.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText().trim();
                    String value = values.get(i).getText().trim();
//                    System.out.println(text + ":" + value);
                    if ((text.indexOf("Kích thước") == 0 || value.indexOf("inch") > 0 || value.indexOf("\"") > 0 ) && item.getScreen() == null) {
                        item.setScreen(value);
                    }else if ( text.indexOf("Bộ xử lý") == 0  || text.indexOf("GHz") > 0 || text.indexOf("CPU") == 0) {
                        item.setCpu(value);
                    }
                    else if (value.indexOf("RAM") > 0 || text.indexOf("RAM") == 0 ) {
                        item.setRam(value);
                    }
                    else if (text.indexOf("Hệ điều hành") == 0 && item.getOs() == null) {
                        item.setOs(value);
                    }
                    else if ((text.indexOf("Camera chính") == 0 || text.indexOf("Camera sau") == 0) && item.getBackCamera() == null) {
                        item.setBackCamera(value);
                    }else if ((value.indexOf("Camera chính") == 0 || value.indexOf("Camera sau") == 0) && item.getBackCamera() == null) {
                        item.setBackCamera(text);
                    } 
                    else if ((text.indexOf("Camera phụ") == 0 || text.indexOf("Camera trước") == 0) && item.getFrontCamera() == null) {
                        item.setFrontCamera(value);
                    } else if (text.indexOf("Bộ nhớ trong") == 0 && item.getStorage() == null) {
                        item.setStorage(value);
//                        item.setRam(value);
                    } else if ((text.indexOf("Pin chuẩn") == 0 || value.indexOf("mAh") > 0) && item.getBattery() == null) {
                        item.setBattery(value);
                    } else if (text.indexOf("Mạng 2G") == 0 || text.indexOf("Hỗ trợ đa SIM") == 0) {
                        item.setSim(value);
                    }
                }

//                System.out.println(item.getScreen());
//                System.out.println(item.getCpu());
//                System.out.println(item.getRam());
//                System.out.println(item.getOs());
//                System.out.println(item.getStorage());
//                System.out.println(item.getBackCamera());
//                System.out.println(item.getFrontCamera());
//                System.out.println(item.getBattery());
//                System.out.println(item.getSim());

                //save
                m.saveMobile(item);
            }else{
                texts = getElements(driver, "css", "div.p-introduct-spec-full div.productatribute-info span.productatribute-name");
                values = getElements(driver, "css", "div.p-introduct-spec-full div.productatribute-info span.productatributevalue-name");
                if (texts != null && texts.size() > 0) {
                    int size = texts.size() <= values.size() ? texts.size() : values.size();
                    for (int i = 0; i < size; i++) {
                        String text = texts.get(i).getText().trim();
                        String value = values.get(i).getText().trim();
    //                    System.out.println(text + ":" + value);
                        if ((text.indexOf("Kích thước") == 0 || value.indexOf("inch") > 0 || value.indexOf("\"") > 0) && item.getScreen() == null) {
                            item.setScreen(value);
                        }else if ( text.indexOf("Lõi vi xử lý") == 0  ) {
                            item.setCpu(value);
                        }else if (value.indexOf("RAM") > 0 || text.indexOf("RAM") == 0 ) {
                            item.setRam(value);
                        }
                        else if (text.indexOf("Hệ điều hành") == 0 && item.getOs() == null) {
                            item.setOs(value);
                        } else if (text.indexOf("Máy ảnh") == 0 && item.getBackCamera() == null) {
                            item.setBackCamera(value);
                        } else if (text.indexOf("Máy ảnh phụ") == 0 && item.getFrontCamera() == null) {
                            item.setFrontCamera(value);
                        } else if (text.indexOf("Bộ nhớ trong") == 0 && item.getStorage() == null) {
                            item.setStorage(value);
//                            item.setRam(value);
                        } else if ((text.indexOf("Dung lượng pin") == 0 || value.indexOf("mAh") > 0) && item.getBattery() == null) {
                            item.setBattery(value);
                        } else if (text.indexOf("Hỗ trợ đa SIM") == 0) {
                            item.setSim(value);
                        }
                    }

//                    System.out.println(item.getScreen());
//                    System.out.println(item.getCpu());
//                    System.out.println(item.getRam());
//                    System.out.println(item.getOs());
//                    System.out.println(item.getStorage());
//                    System.out.println(item.getBackCamera());
//                    System.out.println(item.getFrontCamera());
//                    System.out.println(item.getBattery());
//                    System.out.println(item.getSim());

                    //save
                    m.saveMobile(item);
                }

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
        WebElement chitiet = null;
        Actions action = new Actions(driver);
        item.setTabletDataId(id);
        try {
            driver.get(link);
            Thread.sleep(1000);
            System.out.println("MediaMart:" + driver.getTitle());
            try {
                chitiet = getElement(driver, "xpath", "//a[@rel='#p-introduct-spec']");
                if (chitiet != null) {
                    action.moveToElement(chitiet).click().perform();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            texts = getElements(driver, "xpath", "//div[@class='p-introduct-spec-input']//td[not(@rowspan) and position() mod 2 = 1]");
            values = getElements(driver, "xpath", "//div[@class='p-introduct-spec-input']//td[not(@rowspan) and position() mod 2 = 0]");

            if (texts != null && texts.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText().trim();
                    String value = values.get(i).getText().trim();
//                    System.out.println(text + ":" + value);
                    if (text.indexOf("Kích thước") == 0 || text.indexOf("Màn hình rộng") == 0 || value.indexOf("inch") > 0 || value.indexOf("\"") > 0) {
                        item.setScreen(value);
                    } else if (text.indexOf("Số nhân") == 0) {
                        item.setCpu(value);
                    } else if (text.indexOf("Bộ xử lý") == 0 || value.indexOf("GHz") > 0 || text.indexOf("Tốc độ CPU") == 0) {
                        item.setCpu(value + ". " + item.getCpu());
                    }else if (value.indexOf("RAM") > 0 || text.indexOf("RAM") == 0 ) {
                        item.setRam(value);
                    }
                    else if (text.indexOf("Hệ điều hành") == 0) {
                        item.setOs(value);
                    } else if (text.indexOf("Camera chính") == 0) {
                        item.setBackCamera(value);
                    } else if (text.indexOf("Camera phụ") == 0 || text.indexOf("Camera trước") == 0) {
                        item.setFrontCamera(value);
                    } else if (text.indexOf("Bộ nhớ trong") == 0) {
//                        item.setRam(value);
                        item.setStorage(value);
                    } else if (text.indexOf("Pin chuẩn") == 0 || value.indexOf("mAh") > 0 || text.indexOf("Dung lượng pin") == 0) {
                        item.setBattery(value);
                    } else if (text.indexOf("Hỗ trợ sim") == 0) {
                        item.setSim(value);
                    }else if (value.indexOf("Camera sau") == 0) {
                        item.setBackCamera(text);
                    }else if (value.indexOf("Hệ điều hành") == 0) {
                        item.setOs(text);
                    }
                    if (text.indexOf("Bộ nhớ trong") == 0 && item.getStorage() == null) {
                        item.setStorage(value);
                    }
                    if (value.indexOf("Bộ nhớ trong") == 0 && item.getStorage() == null) {
                        item.setStorage(text);
                    }
                }
//                System.out.println(item.getScreen());
//                System.out.println(item.getCpu());
//                System.out.println(item.getRam());
//                System.out.println(item.getOs());
//                System.out.println(item.getBackCamera());
//                System.out.println(item.getFrontCamera());
//                System.out.println(item.getStorage());
//                System.out.println(item.getBattery());
//                System.out.println(item.getSim());
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
            System.out.println("MediaMart:" + driver.getTitle());
            try {
                chitiet = getElement(driver, "xpath", "//a[@rel='#p-introduct-spec']");
                if (chitiet != null) {
                    action.moveToElement(chitiet).click().perform();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            texts = getElements(driver, "xpath", "//div[@class='p-introduct-spec-input']//th[@class='label']");
            values = getElements(driver, "xpath", "//div[@class='p-introduct-spec-input']//td[@class='data last']");
            if (texts != null && texts.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText().trim();
                    String value = values.get(i).getText().trim();
//                    System.out.println(text + ":" + value);
                    if ((text.indexOf("Kích cỡ màn hình") == 0 || value.indexOf("inch") > 0 || value.indexOf("\"") > 0) && item.getScreen() == null) {
                        item.setScreen(value);
                    } else if ((text.indexOf("Bộ vi xử lý") == 0 || value.indexOf("GHz") > 0) && item.getCpu() == null) {
                        item.setCpu(value);
                    } else if ((text.indexOf("Bộ nhớ Ram") == 0 || text.indexOf("RAM") >= 0 || value.indexOf("RAM") >= 0) && item.getRam() == null) {
                        item.setRam(value);
                    } else if (text.indexOf("Hệ điều hành") == 0 && item.getOs() == null) {
                        item.setOs(value);
                    } else if (text.indexOf("Ổ đĩa cứng") == 0 && item.getHdd() == null) {
                        item.setHdd(value);
                    } else if (text.indexOf("Card màn hình") == 0) {
                        item.setVga(value);
                    } else if (text.indexOf("Ổ đĩa quang") == 0) {
                        item.setDvd(value);
                    } else if (text.indexOf("Loại màn hình") == 0) {
                        item.setTouchScreen(value);
                    }
                }
//                System.out.println(item.getScreen());
//                System.out.println(item.getCpu());
//                System.out.println(item.getRam());
//                System.out.println(item.getOs());
//                System.out.println(item.getHdd());
//                System.out.println(item.getVga());
//                System.out.println(item.getDvd());
//                System.out.println(item.getTouchScreen());
                //save
                m.saveLaptop(item);
            }else{
                texts = getElements(driver, "xpath", "//div[@class='p-introduct-spec-input']//td[not(@colspan) and position() mod 2 = 1]");
                values = getElements(driver, "xpath", "//div[@class='p-introduct-spec-input']//td[position() mod 2 = 0]");
                if (texts != null && texts.size() > 0) {
                    boolean os = false;
                    int dem = 1;
                    int size = texts.size() <= values.size() ? texts.size() : values.size();
                    for (int i = 0; i < size; i++) {
                        String text = texts.get(i).getText().trim();
                        String value = values.get(i).getText().trim();
    //                    System.out.println(text + ":" + value);
                        if ((text.indexOf("Màn hình") == 0 || value.indexOf("inch") > 0 || value.indexOf("\"") > 0 || text.indexOf("Kích cỡ màn hình") == 0) && item.getScreen() == null) {
                            item.setScreen(value);
                            item.setTouchScreen(value);
                        }else if (text.indexOf("Tên bộ vi xử lý") == 0 || text.indexOf("Bộ vi xử lý") == 0  ) {
                            item.setCpu(value);
                        }else if ((text.indexOf("Tốc độ") == 0 || value.indexOf("GHz") > 0) ) {
                            if(item.getCpu() != null){
                                item.setCpu(item.getCpu() +". "+ value);
                            }else{
                                item.setCpu(value);
                            }
                        } else if (text.indexOf("Bộ nhớ trong") == 0 || text.indexOf("Bộ nhớ RAM") == 0 || text.indexOf("RAM") >= 0 || value.indexOf("RAM") >= 0) {
                            item.setRam(value);
                        } else if (text.indexOf("Hệ điều hành") == 0) {
                            item.setOs(value);
                            os = true;
                        } else if (text.indexOf("Hệ điều hành") == 0 && os == false) {
                            item.setOs(value);
                        }  else if (text.indexOf("Ổ cứng") == 0 || text.indexOf("Dung lượng ổ cứng") == 0 || text.indexOf("Ổ đĩa cứng") == 0) {
                            item.setHdd(value);
                        } else if (text.indexOf("Bộ xử lý") == 0 || text.indexOf("Card đồ họa") == 0 || text.indexOf("VGA") == 0) {
                            item.setVga(value);
                        } else if (text.indexOf("Ổ quang") == 0 || text.indexOf("Ổ đĩa quang") == 0 || value.indexOf("DVD") >= 0) {
                            item.setDvd(value);
                        }else if (text.indexOf("Dung lượng") == 0 && (text.indexOf("Dung lượng tối đa") != 0 || text.indexOf("Dung lượng ổ cứng") != 0)) {
                            if(dem == 1){
                                item.setRam(value);
                            }else if(dem==2){
                                item.setHdd(value);
                            }
                            dem++;
                        }
                    }
//                    System.out.println(item.getScreen());
//                    System.out.println(item.getCpu());
//                    System.out.println(item.getRam());
//                    System.out.println(item.getOs());
//                    System.out.println(item.getHdd());
//                    System.out.println(item.getVga());
//                    System.out.println(item.getDvd());
//                    System.out.println(item.getTouchScreen());
                    //save
                    m.saveLaptop(item);
                }
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
        MediaMartAction m = new MediaMartAction();
        System.out.println("sdfd\"".indexOf("\""));
//        m.getData("Laptop", null);
//        try {
//            driver = loadDriverNotImage("firefox");
//            m.getDataEachURLLaptop(-1l, "http://mediamart.vn/laptop-dell/laptop-dell-ins-5447-xyc9n1-vga-2g.htm");
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            driver.close();
//        }

    }
}
