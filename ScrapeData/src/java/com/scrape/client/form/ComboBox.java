/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.client.form;

/**
 *
 * @author KeyLove
 */
public class ComboBox {
    private String value;
    private String name;

    public ComboBox() {
    }

    public ComboBox(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
