/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.selenium.thread;

import com.selenium.action.ViettelAction;

/**
 *
 * @author KeyLove
 */
public class ViettelThread extends Thread{

    public ViettelThread() {
    }

    @Override
    public void run(){
        ViettelAction v = new ViettelAction();
        v.getData(null, null);
        v.getConfiguration("Mobile", null);
        v.getConfiguration("Tablet", null);
        v.getConfiguration("Laptop", null);
    }
}
