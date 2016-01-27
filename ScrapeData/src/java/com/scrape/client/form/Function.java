/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.client.form;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author KeyLove
 */
public class Function implements Serializable{
    private String name;
    private String action;
    private List<ChildFunction> childFunctions;
    
    public Function() {
    }

    public Function(String name, String action, List<ChildFunction> childFunctions) {
        this.name = name;
        this.action = action;
        this.childFunctions = childFunctions;
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

    public List<ChildFunction> getChildFunctions() {
        return childFunctions;
    }

    public void setChildFunctions(List<ChildFunction> childFunctions) {
        this.childFunctions = childFunctions;
    }


}
