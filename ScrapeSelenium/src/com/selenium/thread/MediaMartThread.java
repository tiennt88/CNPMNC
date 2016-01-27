/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.selenium.thread;

import com.selenium.action.MediaMartAction;

/**
 *
 * @author KeyLove
 */
public class MediaMartThread extends Thread{

    public MediaMartThread() {
    }

    @Override
    public void run(){
        MediaMartAction v = new MediaMartAction();
        v.getData(null, null);
        v.getConfiguration("Mobile", null);
        v.getConfiguration("Tablet", null);
        v.getConfiguration("Laptop", null);
    }
}
