/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.selenium.thread;

import com.selenium.action.TGDDAction;

/**
 *
 * @author KeyLove
 */
public class TGDDThread extends Thread{

    public TGDDThread() {
    }

    @Override
    public void run(){
        TGDDAction t =new TGDDAction();
        t.getData(null, null);
        t.getConfiguration("Mobile", null);
        t.getConfiguration("Tablet", null);
        t.getConfiguration("Laptop", null);

    }
}
