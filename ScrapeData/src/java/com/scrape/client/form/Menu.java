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
public class Menu implements Serializable{
    private String name;
    private List<Function> functions;

    public Menu() {
    }

    public Menu(String name, List<Function> functions) {
        this.name = name;
        this.functions = functions;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
