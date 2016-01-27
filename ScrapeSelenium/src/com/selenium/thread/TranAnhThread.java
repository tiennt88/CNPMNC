/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.selenium.thread;

import com.selenium.action.TranAnhAction;

/**
 *
 * @author KeyLove
 */
public class TranAnhThread extends Thread{

    public TranAnhThread() {
    }

    @Override
    public void run(){
        TranAnhAction v = new TranAnhAction();
        v.getData(null, null);
        v.getConfiguration("Mobile", null);
        v.getConfiguration("Tablet", null);
        v.getConfiguration("Laptop", null);
    }
}
