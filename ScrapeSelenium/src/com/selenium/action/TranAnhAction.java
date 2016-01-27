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
public class TranAnhAction extends DatasourceC3p0 {

    private static WebDriver driver;
    private static Connection conn;
    final private static String web = "TranAnh";
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
            System.out.println("TranAnh:" + driver.getTitle());
            webElements = getElements(driver, "css", "li.pb_name a") ;

            if (webElements != null) {
                for (int i = 0; i < webElements.size(); i++) {
                    links.add(webElements.get(i).getAttribute("href"));
                    names.add(webElements.get(i).getText());
//                    int position = i + 1;
//                    promotion = getElement(driver, "xpath", "//div[@id='div_Danh_Sach_San_Pham']/section[" + position + "]//ul[@class='ul_hover_pro']");
//                    if (promotion != null) {
////                        promotions.add(promotion.getText());
//                        promotions.add(getText(driver,promotion));
//                    } else {
//                        promotions.add("");
//                    }
                }
            }
            webElements = webElements = getElements(driver, "css", "span.giaban a") ;
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
        try {
            driver.get(url);
            Thread.sleep(1000);

            getListModel();
            DataDAO d = new DataDAO(conn);
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
        WebElement chitiet = null;
        Actions action = new Actions(driver);
        item.setMobileDataId(id);
        try {
            driver.get(link);
            Thread.sleep(1000);
            System.out.println("TranAnh:" + driver.getTitle());
            try {
                chitiet = getElement(driver, "css", "a#a_showchitiet");
                if (chitiet != null) {
                    action.moveToElement(chitiet).click().perform();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            

            texts = getElements(driver, "xpath", "//div[@class='ltlmota']/table/tbody//td[position() mod 2 = 1]");
            values = getElements(driver, "xpath", "//div[@class='ltlmota']/table/tbody//td[position() mod 2 = 0]");
            if (texts != null && texts.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText().trim();
                    String value = values.get(i).getText().trim();
//                    System.out.println(text + ":" + value);
                    if ((text.indexOf("Kích thước màn hình") == 0 || text.indexOf("Màn hình rộng") == 0 || value.indexOf("inch") > 0 ) && item.getScreen() == null) {
                        item.setScreen(value);
                    }else if ( text.indexOf("Bộ vi xử lý") == 0  || text.indexOf("Tốc độ CPU") == 0 || (text.indexOf("Bộ xử lý") == 0 && item.getCpu() == null )) {
                        item.setCpu(value);
                    } else if (text.indexOf("RAM") == 0 || value.indexOf("RAM") == 0) {
                        item.setRam(value);
                    } else if (text.indexOf("Hệ điều hành") == 0 && item.getOs() == null) {
                        item.setOs(value);
                    } else if ( (text.indexOf("Máy ảnh") == 0 && text.indexOf("Máy ảnh phụ") != 0) || (text.indexOf("Camera") == 0 && (text.indexOf("Camera phụ") != 0 && text.indexOf("Camera trước") != 0))) {
                        item.setBackCamera(value);
                    } else if (text.indexOf("Camera phụ") == 0 || text.indexOf("Camera trước") == 0 || text.indexOf("Máy ảnh phụ") == 0) {
                        item.setFrontCamera(value);
                    } else if (text.indexOf("Bộ nhớ trong") == 0 && item.getStorage() == null) {
                        item.setStorage(value);
                    } else if (((text.indexOf("Pin") == 0 && text.indexOf("Pin có thể tháo rời") != 0) || text.indexOf("Dung lượng pin") == 0 || value.indexOf("mAh") > 0) && item.getBattery() == null) {
                        item.setBattery(value);
                    } else if (text.indexOf("Số sim") == 0 || text.indexOf("2 Sim 2 Sóng") == 0 || text.indexOf("Hỗ trợ đa sim") == 0) {
                        item.setSim(value);
                    }
                }

//                System.out.println(item.getScreen());
//                System.out.println(item.getCpu());
//                System.out.println(item.getRam());
//                System.out.println(item.getOs());
//                System.out.println(item.getStorage());
//                System.out.println(item.getBackCamera());
//                System.out.println(item.getBattery());
//                System.out.println(item.getSim());

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
        WebElement chitiet = null;
        Actions action = new Actions(driver);
        item.setTabletDataId(id);
        try {
            driver.get(link);
            Thread.sleep(1000);
            System.out.println("TranAnh:" + driver.getTitle());
            try {
                chitiet = getElement(driver, "css", "a#a_showchitiet");
                if (chitiet != null) {
                    action.moveToElement(chitiet).click().perform();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            texts = getElements(driver, "xpath", "//div[@class='ltlmota']/table/tbody//td[position() mod 2 = 1]");
            values = getElements(driver, "xpath", "//div[@class='ltlmota']/table/tbody//td[position() mod 2 = 0]");
            if (texts != null && texts.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText().trim();
                    String value = values.get(i).getText().trim();
//                    System.out.println(text + ":" + value);
                    if ((text.indexOf("Màn hình") == 0 || value.indexOf("inch") > 0) && item.getScreen() == null) {
                        item.setScreen(value);
                    } else if ((text.indexOf("Vi xử lý") == 0 || value.indexOf("GHz") > 0) && item.getCpu() == null) {
                        item.setCpu(value);
                    } else if (text.indexOf("RAM") == 0 && item.getRam() == null) {
                        item.setRam(value);
                    } else if (text.indexOf("Hệ điều hành") == 0 && item.getOs() == null) {
                        item.setOs(value);
                    } else if (text.indexOf("Camera sau") == 0 && item.getBackCamera() == null) {
                        item.setBackCamera(value);
                    } else if (text.indexOf("Camera trước") == 0 && item.getFrontCamera() == null) {
                        item.setFrontCamera(value);
                    } else if (text.indexOf("Bộ nhớ trong") == 0 && item.getStorage() == null) {
                        item.setStorage(value);
                    } else if ((text.indexOf("Pin:") == 0 || value.indexOf("mAh") > 0) && item.getBattery() == null) {
                        item.setBattery(value);
                    } else if (text.indexOf("Sim:") == 0) {
                        item.setSim(value);
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
            System.out.println("TranAnh:" + driver.getTitle());
            try {
                chitiet = getElement(driver, "css", "a#a_showchitiet");
                if (chitiet != null) {
                    action.moveToElement(chitiet).click().perform();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            texts = getElements(driver, "xpath", "//div[@class='ltlmota']/table/tbody//td[position() mod 2 = 1]");
            values = getElements(driver, "xpath", "//div[@class='ltlmota']/table/tbody//td[position() mod 2 = 0]");
            if (texts != null && texts.size() > 0) {
                int size = texts.size() <= values.size() ? texts.size() : values.size();
                for (int i = 0; i < size; i++) {
                    String text = texts.get(i).getText().trim();
                    String value = values.get(i).getText().trim();
//                    System.out.println(text + ":" + value);
                    if ((text.indexOf("Kích cỡ màn hình") == 0 || value.indexOf("inch") > 0) && item.getScreen() == null) {
                        item.setScreen(value);
                    } else if ((text.indexOf("Bộ vi xử lý") == 0 || value.indexOf("GHz") > 0) && item.getCpu() == null) {
                        item.setCpu(value);
                    } else if (text.indexOf("Bộ nhớ trong") == 0 && item.getRam() == null) {
                        item.setRam(value);
                    } else if (text.indexOf("Hệ điều hành") == 0 && item.getOs() == null) {
                        item.setOs(value);
                    } else if (text.indexOf("Ổ đĩa cứng") == 0 && item.getHdd() == null) {
                        item.setHdd(value);
                    } else if (text.indexOf("Bộ vi xử lý đồ họa") == 0) {
                        item.setVga(value);
                    } else if (text.indexOf("Ổ quang học") == 0) {
                        item.setDvd(value);
                    } else if (text.indexOf("Màn hình cảm ứng") == 0) {
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
        TranAnhAction TranAnh = new TranAnhAction();
        TranAnh.getConfiguration("Mobile", null);

//        try {
//            driver = loadDriver("firefox");
//            TranAnh.getDataEachURLMobile(-1l,"http://www.trananh.vn/dien-thoai/dien-thoai-samsung-galaxy-alpha-gold-p186539c338" );
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            driver.close();
//        }

    }
}
