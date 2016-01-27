
import com.selenium.DAO.DatasourceC3p0;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KeyLove
 */
public class vote extends DatasourceC3p0 {
    private static WebDriver driver;
    public static void vote(){
        WebElement checkbox = null;
        WebElement vote = null;
        try{
            driver = loadDriver("firefox");
            
            driver.get("http://chungta.vn/tin-tuc/cong-nghe/binh-chon-fpt-tech-awards-2014-39086.html");
            Thread.sleep(1000);
            checkbox = getElement(driver, "xpath", "//*[@id='_i83']");
            if(checkbox != null){
                checkbox.click();
            }
            
            vote = getElement(driver, "xpath", "//*[@id='boxthamdoykien']/div[2]/div/div[2]/table/tbody/tr[10]/td/button");
            if(vote != null){
                vote.click();
            }
            driver.manage().deleteAllCookies();
            Thread.sleep(1000);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            driver.close();
        }
    }

    public static void main(String[] args) {
        
        for(int i=1; i<100;i++){
            vote();
        }
        
    }
}
