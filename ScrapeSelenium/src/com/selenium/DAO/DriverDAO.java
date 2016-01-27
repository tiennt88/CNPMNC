/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.selenium.DAO;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 *
 * @author KeyLove
 */
public class DriverDAO extends DatasourceC3p0{
    
    private WebDriver driver;

    public DriverDAO(WebDriver driver) {
        this.driver = driver;
    }

    public static WebDriver loadDriver(String browser) {
        WebDriver driver = null;
//        String path = System.getProperty("user.dir") + "/Driver/";
          String path =props.getString("pathDriver");

        System.out.println("path:"+path);
        if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("ie")) {
            // Here I am setting up the path for my IEDriver
            System.setProperty("webdriver.ie.driver", path+"IEDriverServer.exe");
            driver = new InternetExplorerDriver();
        } else if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver",path+"chromedriver.exe");
            driver = new ChromeDriver();
        }
        return driver;
    }

    public static void closeDriver(WebDriver driver) {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  List<WebElement> getElements(String type,String path){
        List<WebElement> webElement = null;
        try{
            if("css".equals(type)){
                webElement = driver.findElements(By.cssSelector(path));
            }else{
                webElement = driver.findElements(By.xpath(path));
            }
        }catch(Exception ex){
            System.out.println("ERROR:"+path);
            ex.printStackTrace();
        }
        return webElement;
    }

    public  WebElement getElement(String type,String path){
        WebElement webElement = null;
        try{
            if("css".equals(type)){
                webElement = driver.findElement(By.cssSelector(path));
            }else{
                webElement = driver.findElement(By.xpath(path));
            }
        }catch(Exception ex){
            System.out.println("ERROR:"+path);
            ex.printStackTrace();
        }
        return webElement;
    }

    
}
