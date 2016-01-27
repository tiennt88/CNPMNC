package com.scrape.BO;
// Generated May 17, 2015 3:47:49 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TabletData generated by hbm2java
 */
@Entity
@Table(name="TABLET_DATA"
    ,schema="dbo"
)
public class TabletData{


     private long id;
     private String web;
     private String type;
     private String brand;
     private String name;
     private String link;
     private String price;
     private Long priceNumber;
     private String promotion;
     private String description;
     private String isactive;
     private Date createDate;
     private Date lastUpdate;

    public TabletData() {
    }

	
    public TabletData(long id) {
        this.id = id;
    }
    public TabletData(long id, String web, String type, String brand, String name, String link, String price, Long priceNumber, String promotion, String description, String isactive, Date createDate, Date lastUpdate) {
       this.id = id;
       this.web = web;
       this.type = type;
       this.brand = brand;
       this.name = name;
       this.link = link;
       this.price = price;
       this.priceNumber = priceNumber;
       this.promotion = promotion;
       this.description = description;
       this.isactive = isactive;
       this.createDate = createDate;
       this.lastUpdate = lastUpdate;
    }
   
     @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", unique=true, nullable=false, precision=18, scale=0)
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Column(name="WEB", length=50)
    public String getWeb() {
        return this.web;
    }
    
    public void setWeb(String web) {
        this.web = web;
    }
    
    @Column(name="TYPE", length=50)
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="BRAND", length=50)
    public String getBrand() {
        return this.brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    @Column(name="NAME", length=150)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="LINK", length=250)
    public String getLink() {
        return this.link;
    }
    
    public void setLink(String link) {
        this.link = link;
    }
    
    @Column(name="PRICE", length=50)
    public String getPrice() {
        return this.price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
    
    @Column(name="PRICE_NUMBER", precision=10, scale=0)
    public Long getPriceNumber() {
        return this.priceNumber;
    }
    
    public void setPriceNumber(Long priceNumber) {
        this.priceNumber = priceNumber;
    }
    
    @Column(name="PROMOTION", length=500)
    public String getPromotion() {
        return this.promotion;
    }
    
    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }
    
    @Column(name="DESCRIPTION", length=500)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="ISACTIVE", length=1)
    public String getIsactive() {
        return this.isactive;
    }
    
    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_DATE", length=23)
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LAST_UPDATE", length=23)
    public Date getLastUpdate() {
        return this.lastUpdate;
    }
    
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }




}

