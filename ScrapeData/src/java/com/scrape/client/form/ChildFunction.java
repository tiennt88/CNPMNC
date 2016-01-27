/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.client.form;

import java.io.Serializable;

/**
 *
 * @author KeyLove
 */
public class ChildFunction implements Serializable{
    private String name;
    private String action;

    public ChildFunction() {
    }

    public ChildFunction(String name, String action) {
        this.name = name;
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
