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
 * LaptopPrice generated by hbm2java
 */
@Entity
@Table(name="LAPTOP_PRICE"
    ,schema="dbo"
)
public class LaptopPrice{


     private long id;
     private long laptopDataId;
     private String price;
     private Long priceNumber;
     private Date createDate;
     private Date lastUpdate;

    public LaptopPrice() {
    }

	
    public LaptopPrice(long id, long laptopDataId) {
        this.id = id;
        this.laptopDataId = laptopDataId;
    }
    public LaptopPrice(long id, long laptopDataId, String price, Long priceNumber, Date createDate, Date lastUpdate) {
       this.id = id;
       this.laptopDataId = laptopDataId;
       this.price = price;
       this.priceNumber = priceNumber;
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
    
    @Column(name="LAPTOP_DATA_ID", nullable=false, precision=18, scale=0)
    public long getLaptopDataId() {
        return this.laptopDataId;
    }
    
    public void setLaptopDataId(long laptopDataId) {
        this.laptopDataId = laptopDataId;
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


