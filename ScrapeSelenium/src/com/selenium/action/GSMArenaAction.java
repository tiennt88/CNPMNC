/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.selenium.action;

import com.selenium.BO.GSMArena;
import com.selenium.DAO.DatasourceC3p0;
import java.sql.Connection;
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
public class GSMArenaAction extends DatasourceC3p0 {

    private static WebDriver driver;
    private Connection conn;
    List<String> brands = new ArrayList<String>();
    List<String> brandLinks = new ArrayList<String>();
    List<String> links = new ArrayList<String>();
    List<String> names = new ArrayList<String>();

//    lay du lieu chi tiet
    public void getDataEachURL(String brand,String name,String url) {
        List<WebElement> ttl = null;
        List<WebElement> nfo = null;
        GSMArena item = new GSMArena();
        item.setBrand(brand);
        item.setModel(name);
        item.setLink(url);
        try {
            driver.get(url);
            Thread.sleep(2000);
            ttl = driver.findElements(By.xpath("//div[@id='specs-list']//td[@class='ttl']"));
            nfo = driver.findElements(By.xpath("//div[@id='specs-list']//td[@class='nfo']"));
            for (int i = 0; i < ttl.size(); i++) {
                String text = ttl.get(i).getText();
                String value = nfo.get(i).getText();
//                if (text.indexOf("2G") >= 0) {
//                    item._2g = value.trim();
//                    System.out.println(value.trim());
//                } else if (text.indexOf("3G") >= 0) {
//                    item._3g = value.trim();
//                    System.out.println(value.trim());
//                } else if (text.indexOf("4G") >= 0) {
//                    item._4g = value.trim();
//                    System.out.println(value.trim());
//                } else if (text.indexOf("Speed") >= 0) {
//                    item.speed = value.trim();
//                    System.out.println(value.trim());
//                } else if (text.indexOf("GPRS") >= 0) {
//                    item.gprs = value.trim();
//                    System.out.println(value.trim());
//                } else if (text.indexOf("EDGE") >= 0) {
//                    item.edge = value.trim();
//                    System.out.println(value.trim());
//                } else
                if (text.indexOf("Announced") >= 0) {
                    item.announced = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Status") >= 0) {
                    item.status = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Dimensions") >= 0) {
                    item.dimensions = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Weight") >= 0) {
                    item.weight = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Keyboard") >= 0) {
                    item.keybroad = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("SIM") >= 0) {
                    item.sim = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Type") >= 0) {
                    item.type = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Size") >= 0) {
                    item.size = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Resolution") >= 0) {
                    item.resolution = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Multitouch") >= 0) {
                    item.multitouch = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Protection") >= 0) {
                    item.protection = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("OS") >= 0) {
                    item.os = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Chipset") >= 0) {
                    item.chipset = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("CPU") >= 0) {
                    item.cpu = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("GPU") >= 0) {
                    item.gpu = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Card slot") >= 0) {
                    item.cardSlot = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Internal") >= 0) {
                    item._internal = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Primary") >= 0) {
                    item.primary = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Video") >= 0) {
                    item.video = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Secondary") >= 0) {
                    item.secondary = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Alert types") >= 0) {
                    item.alertTypes = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Loudspeaker") >= 0) {
                    item.loudSpeaker = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("3.5mm jack") >= 0) {
                    item.jack = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("WLAN") >= 0) {
                    item.wlan = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Bluetooth") >= 0) {
                    item.bluetooth = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("GPS") >= 0) {
                    item.gps = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("NFC") >= 0) {
                    item.nfc = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Infrared port") >= 0) {
                    item.infraredPort = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Radio") >= 0) {
                    item.radio = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("USB") >= 0) {
                    item.usb = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Sensors") >= 0) {
                    item.sensors = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Messaging") >= 0) {
                    item.messaging = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Browser") >= 0) {
                    item.browser = value.trim();
                    System.out.println(value.trim());
                }  else if (text.indexOf("Java") >= 0) {
                    item.java = value.trim();
                    System.out.println(value.trim());
                } else if (value.indexOf("mAh") >= 0) {
                    item.battery = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Stand-by") >= 0) {
                    item.standBy = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Talk time") >= 0) {
                    item.talkTime = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Music play") >= 0) {
                    item.musicPlay = value.trim();
                    System.out.println(value.trim());
                } else if (text.indexOf("Colors") >= 0) {
                    item.color = value.trim();
                    System.out.println(value.trim());
                }
            }
            //save
             save(item);
            //insert
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void saveCheckItem(String brand, String model, String link) {
        PreparedStatement pre = null;
        int i = 1;
        try {
            pre = conn.prepareStatement("INSERT INTO CHECK_GSMARENA(BRAND,MODEL,LINK,CREATE_DATE) VALUES(?,?,?,getdate())");
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

    public void save(GSMArena gsm) {
        PreparedStatement pre = null;
        int i = 1;
        try {
            pre = conn.prepareStatement("INSERT INTO GSMARENA(BRAND,MODEL,LINK,ANNOUNCED,STATUS,DIMENSIONS,WEIGHT,KEYBOARD,SIM,TYPE,SIZE,RESOLUTION,"
                    + "MULTITOUCH,PROTECTION,OS,CHIPSET,CPU,GPU,CARD_SLOT,INTERNAL,[PRIMARY],VIDEO,SECONDARY,ALERT_TYPES,LOUDSPEAKER,"
                    + "JACK,WLAN,BLUETOOTH,GPS,NFC,INFRARED_PORT,RADIO,USB,SENSORS,MESSAGING,BROWSER,JAVA,BATTERY,STAND_BY,TALK_TIME,MUSIC_PLAY,COLOR) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,"
                    + " ?,?,?,?,?,?,?,?,?,?,"
                    + " ?,?,?,?,?,?,?,?,?,?,"
                    + " ?,?,?,?,?,?,?,?,?,?,?,?) ");
            pre.setString(i++, gsm.getBrand());
            pre.setString(i++, gsm.getModel());
            pre.setString(i++, gsm.getLink());
            pre.setString(i++, gsm.getAnnounced());
            pre.setString(i++, gsm.getStatus());
            pre.setString(i++, gsm.getDimensions());
            pre.setString(i++, gsm.getWeight());
            pre.setString(i++, gsm.getKeybroad());
            pre.setString(i++, gsm.getSim());
            pre.setString(i++, gsm.getType());
            pre.setString(i++, gsm.getSize());
            pre.setString(i++, gsm.getResolution());
            pre.setString(i++, gsm.getMultitouch());
            pre.setString(i++, gsm.getProtection());
            pre.setString(i++, gsm.getOs());
            pre.setString(i++, gsm.getChipset());
            pre.setString(i++, gsm.getCpu());
            pre.setString(i++, gsm.getGpu());
            pre.setString(i++, gsm.getCardSlot());
            pre.setString(i++, gsm.getInternal());
            pre.setString(i++, gsm.getPrimary());
            pre.setString(i++, gsm.getVideo());
            pre.setString(i++, gsm.getSecondary());
            pre.setString(i++, gsm.getAlertTypes());
            pre.setString(i++, gsm.getLoudSpeaker());
            pre.setString(i++, gsm.getJack());
            pre.setString(i++, gsm.getWlan());
            pre.setString(i++, gsm.getBluetooth());
            pre.setString(i++, gsm.getGps());
            pre.setString(i++, gsm.getNfc());
            pre.setString(i++, gsm.getInfraredPort());
            pre.setString(i++, gsm.getRadio());
            pre.setString(i++, gsm.getUsb());
            pre.setString(i++, gsm.getSensors());
            pre.setString(i++, gsm.getMessaging());
            pre.setString(i++, gsm.getBrowser());
            pre.setString(i++, gsm.getJava());
            pre.setString(i++, gsm.getBattery());
            pre.setString(i++, gsm.getStandBy());
            pre.setString(i++, gsm.getTalkTime());
            pre.setString(i++, gsm.getMusicPlay());
            pre.setString(i++, gsm.getColor());

            pre.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(pre);
        }

    }

    public void getListBrand() {
        List<WebElement> webElements = null;
        webElements = driver.findElements(By.cssSelector("div.st-text td:nth-child(even) a"));
        System.out.println(webElements.size());
        if (webElements != null) {
            for (int i = 0; i < webElements.size(); i++) {
                brands.add(webElements.get(i).getText().split(" ")[0].trim());
                brandLinks.add(webElements.get(i).getAttribute("href"));
            }
        }
    }

    public void getListModelOfBrand(String brand, String url) {
        try {
            driver.get(url);
            Thread.sleep(3000);
            String nextPage = "";
            while (nextPage != null) {
                nextPage = getNextPage();
                getListModelEachPage();
                System.out.println(nextPage);
                for (int i = 0; i < links.size(); i++) {
                    saveCheckItem(brand, names.get(i), links.get(i));
                }
                if (nextPage != null) {
                    driver.get(nextPage);
                    Thread.sleep(3000);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getListModelEachPage() {
        List<WebElement> webElements = null;
        links.clear();
        names.clear();
        try {
            webElements = driver.findElements(By.cssSelector("div.makers ul li a"));
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

    public String getNextPage() {
        String url = null;
        List<WebElement> webElements = null;
        try {
            webElements = driver.findElements(By.xpath("//a[@title='Next page']"));
            if (webElements != null && webElements.size() > 0) {
                for (int i = 0; i < webElements.size(); i++) {
                    url = webElements.get(i).getAttribute("href");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return url;
    }

    public void begin() {
        try {
            conn = getConnection();
            driver = loadDriverNotImage("firefox");
            driver.get("http://www.gsmarena.com/makers.php3");
            Thread.sleep(3000);
            System.out.println(driver.getTitle());
            getListBrand();
            for (int i = 0; i < brands.size(); i++) {
                getListModelOfBrand(brands.get(i), brandLinks.get(i));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(conn);
            closeDriver(driver);
        }

    }

    public void getDataAfterCheck(){
        ResultSet rs = null;
        try{
            conn = getConnection();
            driver = loadDriverNotImage("firefox");
            rs = conn.prepareCall("select brand,model,link from CHECK_GSMARENA where LINK not in (select LINK from GSMARENA) and CREATE_DATE > GETDATE()-1 ").executeQuery();
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

    public static void main(String[] args) {
        GSMArenaAction gsm = new GSMArenaAction();
//        gsm.begin();
        gsm.getDataAfterCheck();
//        try {
//            gsm.conn = gsm.getConnection();
//            gsm.loadDriverNotImage("firefox");
//            gsm.getDataEachURL("HTC","model test","http://www.gsmarena.com/htc_one_m9-6891.php");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }finally{
//            gsm.closeDriver();
//            gsm.closeObject(gsm.conn);
//        }

    }
}
