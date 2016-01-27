/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.DAO.util;

import com.scrape.DAO.BaseHibernateDAOMDB;
import com.scrape.common.DojoJSON;
//import com.selenium.action.FPTShopAction;
//import com.selenium.action.MediaMartAction;
//import com.selenium.action.TGDDAction;
//import com.selenium.action.TranAnhAction;
//import com.selenium.action.VienThongAAction;
//import com.selenium.action.ViettelAction;
//import com.selenium.process.RunScrapeData;
//import com.selenium.thread.FPTShopThread;
//import com.selenium.thread.MediaMartThread;
//import com.selenium.thread.TGDDThread;
//import com.selenium.thread.TranAnhThread;
//import com.selenium.thread.VienThongAThread;
//import com.selenium.thread.ViettelThread;

/**
 *
 * @author KeyLove
 */
public class ScrapeDataDAO extends BaseHibernateDAOMDB {

    private DojoJSON jsonDataGrid = new DojoJSON();
    private String web;
    private String type;

    public String prepare(){
        return SUCCESS;
    }

    public String scrapeData() {
        //lay all
//        if ("All".equals(web) && "All".equals(type)) {
//            RunScrapeData.main(null);
//        }
//        //lay web
//        if( "Viettel".equals(web) && "All".equals(type)){
//            ViettelThread t = new ViettelThread();
//            t.start();
//        }
//        if( "TGDD".equals(web) && "All".equals(type)){
//            TGDDThread t = new TGDDThread();
//            t.start();
//        }
//        if( "FPTShop".equals(web) && "All".equals(type)){
//            FPTShopThread t = new FPTShopThread();
//            t.start();
//        }
//        if( "VienThongA".equals(web) && "All".equals(type)){
//            VienThongAThread t = new VienThongAThread();
//            t.start();
//        }
//        if( "TranAnh".equals(web) && "All".equals(type)){
//            TranAnhThread t = new TranAnhThread();
//            t.start();
//        }
//        if( "MediaMart".equals(web) && "All".equals(type)){
//            MediaMartThread t = new MediaMartThread();
//            t.start();
//        }
//        //lay theo type
//        if( "Viettel".equals(web) && !"All".equals(type)){
//            ViettelAction v =new ViettelAction();
//            v.getData(type, null);
//            v.getConfiguration(type, null);
//        }
//
//        if( "TGDD".equals(web) && !"All".equals(type)){
//            TGDDAction t =new TGDDAction();
//            t.getData(type, null);
//            t.getConfiguration(type, null);
//        }
//        if( "FPTShop".equals(web) && !"All".equals(type)){
//            FPTShopAction f =new FPTShopAction();
//            f.getData(type, null);
//            f.getConfiguration(type, null);
//        }
//        if( "VienThongA".equals(web) && !"All".equals(type)){
//            VienThongAAction v =new VienThongAAction();
//            v.getData(type, null);
//            v.getConfiguration(type, null);
//        }
//        if( "TranAnh".equals(web) && !"All".equals(type)){
//            TranAnhAction v =new TranAnhAction();
//            v.getData(type, null);
//            v.getConfiguration(type, null);
//        }
//        if( "MediaMart".equals(web) && !"All".equals(type)){
//            MediaMartAction v =new MediaMartAction();
//            v.getData(type, null);
//            v.getConfiguration(type, null);
//        }
        jsonDataGrid.setLabel("SUCCESS");
        jsonDataGrid.setCustomInfo("Xin vui lòng đợi trong ít phút. Hệ thống đang lấy dữ liệu!");
        return "jsonDataGrid";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }


    public DojoJSON getJsonDataGrid() {
        return jsonDataGrid;
    }

    public void setJsonDataGrid(DojoJSON jsonDataGrid) {
        this.jsonDataGrid = jsonDataGrid;
    }

}
