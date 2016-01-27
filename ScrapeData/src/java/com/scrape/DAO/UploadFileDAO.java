/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.DAO;

import com.scrape.DAO.laptop.LaptopDataDAO;
import com.scrape.DAO.mobile.MobileDataDAO;
import com.scrape.DAO.tablet.TabletDataDAO;
import com.scrape.common.DojoJSON;
import java.io.File;


/**
 *
 * @author KeyLove
 */
public class UploadFileDAO extends BaseHibernateDAOMDB{
    private String type;
    private File fileUpload;
    private DojoJSON jsonDataGrid = new DojoJSON();
    
    public UploadFileDAO() {
    }
    
    public String prepare(){
        if (getRequest().getParameter("type") != null) {
            type = getRequest().getParameter("type").toString();
        }
        return SUCCESS;
    }

    public String onUpload(){
        if(type != null){
           if("Mobile".equals(type)){
               MobileDataDAO m = new MobileDataDAO();
               jsonDataGrid.setCustomInfo(m.upload(fileUpload));
           }else if("Tablet".equals(type)){
               TabletDataDAO t = new TabletDataDAO();
               jsonDataGrid.setCustomInfo(t.upload(fileUpload));
           }else if("Laptop".equals(type)){
               LaptopDataDAO l = new LaptopDataDAO();
               jsonDataGrid.setCustomInfo(l.upload(fileUpload));
           }
        }
        return "jsonDataGrid";
    }

    public File getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(File fileUpload) {
        this.fileUpload = fileUpload;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DojoJSON getJsonDataGrid() {
        return jsonDataGrid;
    }

    public void setJsonDataGrid(DojoJSON jsonDataGrid) {
        this.jsonDataGrid = jsonDataGrid;
    }

}
