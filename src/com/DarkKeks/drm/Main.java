package com.DarkKeks.drm;

public class Main {

    public static Controller controller;

    public static void main(String[] args) throws InterruptedException {
        MessageId id = new MessageId("127.0.0.1:12345:0");
        for(int i = 0; i < 1000; ++i) {
            System.out.println(id);
            id.increment();
        }
        System.out.println(id);



        //Config.init();
        //Config.loadDefaults();

        //controller = new Controller();
    }
}
