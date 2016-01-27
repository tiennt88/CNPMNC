/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.common;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author KeyLove
 */
public class DojoJSON {
  private String identifier;
  private String label;
  private List items;
  private Object customInfo;
  private int numRows;
  private int totalRows;

  public DojoJSON()
  {
    this.items = new ArrayList();
  }

  public Object getCustomInfo() {
    return this.customInfo;
  }

  public void setCustomInfo(Object customInfo) {
    this.customInfo = customInfo;
  }

  public String getIdentifier() {
    return this.identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public List getItems() {
    return this.items;
  }

  public void setItems(List items) {
    this.items = items;
  }

  public String getLabel() {
    return this.label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public int getNumRows() {
    return this.numRows;
  }

  public void setNumRows(int numRows) {
    this.numRows = numRows;
  }

  public int getTotalRows() {
    return this.totalRows;
  }

  public void setTotalRows(int totalRows) {
    this.totalRows = totalRows;
  }
}
