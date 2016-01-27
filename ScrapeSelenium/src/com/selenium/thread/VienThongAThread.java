/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.selenium.thread;

import com.selenium.action.VienThongAAction;

/**
 *
 * @author KeyLove
 */
public class VienThongAThread extends Thread{
    public VienThongAThread() {
    }
    @Override
    public void run(){
        VienThongAAction t =new VienThongAAction();
        t.getData(null, null);
        t.getConfiguration("Mobile", null);
        t.getConfiguration("Tablet", null);
        t.getConfiguration("Laptop", null);
    }
}
