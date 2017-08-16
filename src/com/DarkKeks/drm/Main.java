package com.DarkKeks.drm;

import com.DarkKeks.drm.tasks.Task;

public class Main extends Task {

    public static void main(String[] args) throws InterruptedException {
        Config.init();
        Config.SERVER_ADDRESS = "localhost";

        Controller.getInstance().run();

        Main task = new Main(new MessageId("0"), new Message());
        task.run();
    }

    public Main(MessageId id, Message msg) {
        super(id, msg);
    }

    @Override
    protected Message respond(Message message) {
        Message response = sendMessage(Message.getBuilder()
                .setAction("message")
                .addParam("from", "string", "decic")
                .addParam("message", "string", "kekek")
                .setDestination("127.0.0.1:" + Controller.getInstance().getSender().getPublicAddress())
                .build());

        System.out.println(response);

        return new Message();
    }
}
