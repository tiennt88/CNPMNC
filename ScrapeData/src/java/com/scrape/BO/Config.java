package com.scrape.BO;
// Generated Aug 28, 2015 1:41:29 PM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Config generated by hbm2java
 */
@Entity
@Table(name="CONFIG"
    ,schema="dbo"
)
public class Config{


     private long id;
     private String type;
     private String value;
     private String name;
     private Integer priority;
     private String usefor;
     private Character isactive;

    public Config() {
    }

	
    public Config(long id) {
        this.id = id;
    }
    public Config(long id, String type, String value, String name, Integer priority, String usefor, Character isactive) {
       this.id = id;
       this.type = type;
       this.value = value;
       this.name = name;
       this.priority = priority;
       this.usefor = usefor;
       this.isactive = isactive;
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
    
    @Column(name="TYPE", length=50)
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
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
    
    @Column(name="PRIORITY")
    public Integer getPriority() {
        return this.priority;
    }
    
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    
    @Column(name="USEFOR", length=50)
    public String getUsefor() {
        return this.usefor;
    }
    
    public void setUsefor(String usefor) {
        this.usefor = usefor;
    }
    
    @Column(name="ISACTIVE", length=1)
    public Character getIsactive() {
        return this.isactive;
    }
    
    public void setIsactive(Character isactive) {
        this.isactive = isactive;
    }




}


