/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.selenium.process;

import com.selenium.DAO.DatasourceC3p0;
import com.selenium.thread.FPTShopThread;
import com.selenium.thread.TGDDThread;
import com.selenium.thread.TranAnhThread;
import com.selenium.thread.VienThongAThread;
import com.selenium.thread.ViettelThread;
import com.selenium.thread.MediaMartThread;
import java.sql.CallableStatement;
import java.sql.Connection;

/**
 *
 * @author KeyLove
 */
public class RunScrapeData extends DatasourceC3p0 {

    public static void main(String[] args) {
        FPTShopThread t1 = new FPTShopThread();
        TGDDThread t2 = new TGDDThread();
        ViettelThread t3 = new ViettelThread();
//        VienThongAThread t4  = new VienThongAThread();
        TranAnhThread t5 = new TranAnhThread();
        MediaMartThread t6 = new MediaMartThread();
        try{
            
            t2.start();
            t3.start();
            t5.start();
            t2.join();
            t3.join();
            t5.join();

            t1.start();
            t6.start();
//            t4.start();
            t1.join();
            t6.join();
//            t4.join();
            
            RunScrapeData run = new RunScrapeData();
            run.runAnalysisMobile();
            run.runAnalysisTablet();
            run.runAnalysisLaptop();
            run.runUpdateFTG();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        //chay emai thong bao
        
    }

    public void runAnalysisMobile(){
        CallableStatement cs = null;
        Connection conn = null;
        try{
            conn = getConnection();
            cs = conn.prepareCall("{call MOBILE_ANALYSIS(?)}");
            cs.setString(1, "All");
            cs.execute();
            getConnection().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(cs);
            closeObject(conn);
        }
    }
    public void runAnalysisTablet(){
        CallableStatement cs = null;
        Connection conn = null;
        try{
            conn = getConnection();
            cs = conn.prepareCall("{call TABLET_ANALYSIS(?)}");
            cs.setString(1, "All");
            cs.execute();
            getConnection().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(cs);
            closeObject(conn);
        }
    }

    public void runAnalysisLaptop(){
        CallableStatement cs = null;
        Connection conn = null;
        try{
            conn = getConnection();
            cs = conn.prepareCall("{call LAPTOP_ANALYSIS(?)}");
            cs.setString(1, "All");
            cs.execute();
            getConnection().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(cs);
            closeObject(conn);
        }
    }

    public void runUpdateFTG(){
        CallableStatement cs = null;
        Connection conn = null;
        try{
            conn = getConnection();
            cs = conn.prepareCall("{call UPDATE_FTG()}");
            cs.execute();
            getConnection().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeObject(cs);
            closeObject(conn);
        }
    }
}
