/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.selenium.thread;

import com.selenium.action.FPTShopAction;

/**
 *
 * @author KeyLove
 */
public class FPTShopThread extends Thread{

    public FPTShopThread() {
    }

    @Override
    public void run(){
        FPTShopAction f = new FPTShopAction();
        f.getData(null, null);
        f.getConfiguration("Mobile", null);
        f.getConfiguration("Tablet", null);
        f.getConfiguration("Laptop", null);
    }
}
