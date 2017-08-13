package com.DarkKeks.drm;

import com.DarkKeks.drm.tasks.ReceiveMessageTask;

public class Main {

    public static Controller controller;

    public static void main(String[] args) throws InterruptedException {

        new ReceiveMessageTask(1, Message.getBuilder().addParam("from", "string", "kek").addParam("message", "string", "kok").build()).start();

        Thread.sleep(10000);

        //Config.init();
        //Config.loadDefaults();

        //controller = new Controller();
    }
}
