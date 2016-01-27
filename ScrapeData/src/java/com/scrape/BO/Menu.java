package com.scrape.BO;
// Generated Sep 22, 2015 2:04:27 PM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Menu generated by hbm2java
 */
@Entity
@Table(name="MENU"
    ,schema="dbo"
)
public class Menu {


     private long id;
     private Long parentId;
     private String value;
     private String name;
     private String action;
     private Integer priority;
     private String isactive;
     private String summary;

    public Menu() {
    }

	
    public Menu(long id) {
        this.id = id;
    }
    public Menu(long id, Long parentId, String value, String name, String action, Integer priority, String isactive, String summary) {
       this.id = id;
       this.parentId = parentId;
       this.value = value;
       this.name = name;
       this.action = action;
       this.priority = priority;
       this.isactive = isactive;
       this.summary = summary;
    }
   
     @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", unique=true, nullable=false, precision=10, scale=0)
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Column(name="PARENT_ID", precision=10, scale=0)
    public Long getParentId() {
        return this.parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    @Column(name="VALUE", length=50)
    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    @Column(name="NAME", length=50)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="ACTION", length=50)
    public String getAction() {
        return this.action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    @Column(name="PRIORITY")
    public Integer getPriority() {
        return this.priority;
    }
    
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    
    @Column(name="ISACTIVE", length=1)
    public String getIsactive() {
        return this.isactive;
    }
    
    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }
    
    @Column(name="SUMMARY", length=1)
    public String getSummary() {
        return this.summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }




}

