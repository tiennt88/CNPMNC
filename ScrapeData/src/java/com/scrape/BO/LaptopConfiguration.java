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
import javax.persistence.UniqueConstraint;

/**
 * LaptopConfiguration generated by hbm2java
 */
@Entity
@Table(name="LAPTOP_CONFIGURATION"
    ,schema="dbo"
    , uniqueConstraints = @UniqueConstraint(columnNames="LAPTOP_DATA_ID") 
)
public class LaptopConfiguration{


     private long id;
     private long laptopDataId;
     private String itemCode;
     private String partno;
     private String model;
     private String screenText;
     private String cpuText;
     private String ramText;
     private String osText;
     private String storageText;
     private String batteryText;
     private String vgaText;
     private String touchscreenText;
     private String dvdText;
     private String screen;
     private String cpu;
     private String chip;
     private String feature;
     private String speed;
     private String storage;
     private String hddType;
     private String ram;
     private String vga;
     private String os;
     private String battery;
     private String touchscreen;
     private String dvd;
     private Date createDate;
     private Date lastUpdate;
     private String approve;

    public LaptopConfiguration() {
    }

	
    public LaptopConfiguration(long id, long laptopDataId) {
        this.id = id;
        this.laptopDataId = laptopDataId;
    }
    public LaptopConfiguration(long id, long laptopDataId, String itemCode, String partno, String model, String screenText, String cpuText, String ramText, String osText, String storageText, String batteryText, String vgaText, String touchscreenText, String dvdText, String screen, String cpu, String chip, String feature, String speed, String storage, String hddType, String ram, String vga, String os, String battery, String touchscreen, String dvd, Date createDate, Date lastUpdate) {
       this.id = id;
       this.laptopDataId = laptopDataId;
       this.itemCode = itemCode;
       this.partno = partno;
       this.model = model;
       this.screenText = screenText;
       this.cpuText = cpuText;
       this.ramText = ramText;
       this.osText = osText;
       this.storageText = storageText;
       this.batteryText = batteryText;
       this.vgaText = vgaText;
       this.touchscreenText = touchscreenText;
       this.dvdText = dvdText;
       this.screen = screen;
       this.cpu = cpu;
       this.chip = chip;
       this.feature = feature;
       this.speed = speed;
       this.storage = storage;
       this.hddType = hddType;
       this.ram = ram;
       this.vga = vga;
       this.os = os;
       this.battery = battery;
       this.touchscreen = touchscreen;
       this.dvd = dvd;
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
    
    @Column(name="LAPTOP_DATA_ID", unique=true, nullable=false, precision=18, scale=0)
    public long getLaptopDataId() {
        return this.laptopDataId;
    }
    
    public void setLaptopDataId(long laptopDataId) {
        this.laptopDataId = laptopDataId;
    }
    
    @Column(name="ITEM_CODE", length=50)
    public String getItemCode() {
        return this.itemCode;
    }
    
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
    
    @Column(name="PARTNO", length=50)
    public String getPartno() {
        return this.partno;
    }
    
    public void setPartno(String partno) {
        this.partno = partno;
    }
    
    @Column(name="MODEL", length=150)
    public String getModel() {
        return this.model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    @Column(name="SCREEN_TEXT", length=250)
    public String getScreenText() {
        return this.screenText;
    }
    
    public void setScreenText(String screenText) {
        this.screenText = screenText;
    }
    
    @Column(name="CPU_TEXT", length=250)
    public String getCpuText() {
        return this.cpuText;
    }
    
    public void setCpuText(String cpuText) {
        this.cpuText = cpuText;
    }
    
    @Column(name="RAM_TEXT", length=250)
    public String getRamText() {
        return this.ramText;
    }
    
    public void setRamText(String ramText) {
        this.ramText = ramText;
    }
    
    @Column(name="OS_TEXT", length=250)
    public String getOsText() {
        return this.osText;
    }
    
    public void setOsText(String osText) {
        this.osText = osText;
    }
    
    @Column(name="STORAGE_TEXT", length=250)
    public String getStorageText() {
        return this.storageText;
    }
    
    public void setStorageText(String storageText) {
        this.storageText = storageText;
    }
    
    @Column(name="BATTERY_TEXT", length=250)
    public String getBatteryText() {
        return this.batteryText;
    }
    
    public void setBatteryText(String batteryText) {
        this.batteryText = batteryText;
    }
    
    @Column(name="VGA_TEXT", length=250)
    public String getVgaText() {
        return this.vgaText;
    }
    
    public void setVgaText(String vgaText) {
        this.vgaText = vgaText;
    }
    
    @Column(name="TOUCHSCREEN_TEXT", length=250)
    public String getTouchscreenText() {
        return this.touchscreenText;
    }
    
    public void setTouchscreenText(String touchscreenText) {
        this.touchscreenText = touchscreenText;
    }
    
    @Column(name="DVD_TEXT", length=250)
    public String getDvdText() {
        return this.dvdText;
    }
    
    public void setDvdText(String dvdText) {
        this.dvdText = dvdText;
    }
    
    @Column(name="SCREEN", length=50)
    public String getScreen() {
        return this.screen;
    }
    
    public void setScreen(String screen) {
        this.screen = screen;
    }
    
    @Column(name="CPU", length=50)
    public String getCpu() {
        return this.cpu;
    }
    
    public void setCpu(String cpu) {
        this.cpu = cpu;
    }
    
    @Column(name="CHIP", length=50)
    public String getChip() {
        return this.chip;
    }
    
    public void setChip(String chip) {
        this.chip = chip;
    }
    
    @Column(name="FEATURE", length=50)
    public String getFeature() {
        return this.feature;
    }
    
    public void setFeature(String feature) {
        this.feature = feature;
    }
    
    @Column(name="SPEED", length=50)
    public String getSpeed() {
        return this.speed;
    }
    
    public void setSpeed(String speed) {
        this.speed = speed;
    }
    
    @Column(name="STORAGE", length=50)
    public String getStorage() {
        return this.storage;
    }
    
    public void setStorage(String storage) {
        this.storage = storage;
    }
    
    @Column(name="HDD_TYPE", length=50)
    public String getHddType() {
        return this.hddType;
    }
    
    public void setHddType(String hddType) {
        this.hddType = hddType;
    }
    
    @Column(name="RAM", length=50)
    public String getRam() {
        return this.ram;
    }
    
    public void setRam(String ram) {
        this.ram = ram;
    }
    
    @Column(name="VGA", length=50)
    public String getVga() {
        return this.vga;
    }
    
    public void setVga(String vga) {
        this.vga = vga;
    }
    
    @Column(name="OS", length=50)
    public String getOs() {
        return this.os;
    }
    
    public void setOs(String os) {
        this.os = os;
    }
    
    @Column(name="BATTERY", length=50)
    public String getBattery() {
        return this.battery;
    }
    
    public void setBattery(String battery) {
        this.battery = battery;
    }
    
    @Column(name="TOUCHSCREEN", length=50)
    public String getTouchscreen() {
        return this.touchscreen;
    }
    
    public void setTouchscreen(String touchscreen) {
        this.touchscreen = touchscreen;
    }
    
    @Column(name="DVD", length=50)
    public String getDvd() {
        return this.dvd;
    }
    
    public void setDvd(String dvd) {
        this.dvd = dvd;
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

    @Column(name="APPROVE", length=1)
    public String getApprove() {
        return this.approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }


}

