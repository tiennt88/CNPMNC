/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.selenium.DAO;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.logging.Level;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author KeyLove
 */
public class DatasourceC3p0{
    public static final ResourceBundle props;
    private static final ComboPooledDataSource cpds;
    private static DataSource datasource;
//    private static final Logger log = Logger.getLogger(DatasourceC3p0.class);

    static {
//        log.info("Reading datasource.properties from classpath");
        
        props = ResourceBundle.getBundle("config");
        cpds = new ComboPooledDataSource();
        datasource = setupDataSource();
        
    }

    private static DataSource setupDataSource() {

        try {
            cpds.setDriverClass(props.getString("driverClass"));
        } catch (PropertyVetoException ex) {
            java.util.logging.Logger.getLogger(DatasourceC3p0.class.getName()).log(Level.SEVERE, null, ex);
        }
        cpds.setJdbcUrl(props.getString("db_connection"));
//        cpds.setUser(props.getString("db_user"));
//        cpds.setPassword(props.getString("db_pass"));

        cpds.setInitialPoolSize(new Integer((String) props.getString("initialPoolSize")));
        cpds.setAcquireIncrement(new Integer((String) props.getString("acquireIncrement")));
        cpds.setMaxPoolSize(new Integer((String) props.getString("maxPoolSize")));
        cpds.setMinPoolSize(new Integer((String) props.getString("minPoolSize")));
        cpds.setMaxIdleTime(new Integer((String) props.getString("maxIdleTime")));
        cpds.setMaxIdleTimeExcessConnections(new Integer((String) props.getString("maxIdleTimeExcessConnections")));
        cpds.setAcquireRetryAttempts(new Integer((String) props.getString("acquireRetryAttempts")));
        cpds.setAutoCommitOnClose(true);
    	return cpds;
    }

    public Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    public static void closeObject(Connection obj) {
        try {
            if ((obj != null)
                    && (!obj.isClosed())) {
                if (!obj.getAutoCommit()) {
                    obj.rollback();
                }
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public static void closeObject(CallableStatement obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeObject(Statement obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeObject(ResultSet obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeObject(PreparedStatement obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static WebDriver loadDriver(String browser) {
        WebDriver driver = null;
        if (browser.equalsIgnoreCase("firefox")) {
            FirefoxProfile profile = new FirefoxProfile();
//            profile.setPreference("permissions.default.stylesheet", 2);
//            profile.setPrefeFirefoxProfilerence("permissions.default.image", 2);
            driver  = new FirefoxDriver(profile);
        }else if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", props.getString("pathDriver") + "chromedriver.exe");
            driver = new ChromeDriver();
        }else{
            driver = new HtmlUnitDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
        return driver;
    }

    public static WebDriver loadDriverNotImage(String browser) {
        WebDriver driver = null;
        if (browser.equalsIgnoreCase("firefox")) {
            FirefoxProfile profile = new FirefoxProfile();
//            profile.setPreference("permissions.default.stylesheet", 2);
//            profile.setPreference("permissions.default.image", 2);
            driver  = new FirefoxDriver(profile);
        } else if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", props.getString("pathDriver") + "chromedriver.exe");
            driver = new ChromeDriver();
        }else {
            driver = new HtmlUnitDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
        return driver;
    }

    public static String getText(WebDriver driver, WebElement element){
        String text = "";
        try{
            text = (String) ((JavascriptExecutor) driver).executeScript(
            "return jQuery(arguments[0]).text();", element);
            if(text != null){
                text = text.trim();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return text;
    }

    public void clickAnElement(WebDriver driver,String type, String path) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(path)));
        driver.findElement(By.xpath(path)).click();
    }

    public void clickAnElementByLinkText(WebDriver driver,String linkText) {
          new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
          driver.findElement(By.linkText(linkText)).click();
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

    public static List<WebElement> getElements(WebDriver driver,String type,String path){

        List<WebElement> webElements = null;
        try{
            if("css".equals(type)){
                if(isElementPresent( driver, type, path)){
                    webElements = driver.findElements(By.cssSelector(path));
                }
            }else{
                if(isElementPresent( driver, type, path)){
                    webElements = driver.findElements(By.xpath(path));
                }
            }
        }catch(Exception ex){
//            System.out.println("ERROR:"+path);
//            ex.printStackTrace();
        }
        return webElements;
    }

    public static WebElement getElement(WebDriver driver,String type,String path){
        WebElement webElement = null;
        try{
            if("css".equals(type)){
                if(isElementPresent( driver, type, path)){
                    webElement = driver.findElement(By.cssSelector(path));
                }
            }else{
                if(isElementPresent( driver, type, path)){
                    webElement = driver.findElement(By.xpath(path));
                }
            }
        }catch(Exception ex){
//            System.out.println("ERROR:"+path);
//            ex.printStackTrace();
        }
        return webElement;
    }

    private static long timeout = 10;

    public static boolean isElementPresent(WebDriver driver,String type,String path) {
        boolean isPresent = true;
        waitForLoad(driver);
        //search for elements and check if list is empty
        if("css".equals(type)){
            if (driver.findElements(By.cssSelector(path)).isEmpty()) {
                isPresent = false;
            }
        }else{
            if (driver.findElements(By.xpath(path)).isEmpty()) {
                isPresent = false;
            }
        }
        //rise back implicitly wait time
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        return isPresent;
    }

    public static void waitForLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver wd) {
                //this will tel if page is loaded
                return "complete".equals(((JavascriptExecutor) wd).executeScript("return document.readyState"));
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        //wait for page complete
        wait.until(pageLoadCondition);
        //lower implicitly wait time
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args)  {
        try {
            DatasourceC3p0 test = new DatasourceC3p0();
            Connection conn =  test.getConnection();
            test.loadDriver("chrome");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DatasourceC3p0.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}
