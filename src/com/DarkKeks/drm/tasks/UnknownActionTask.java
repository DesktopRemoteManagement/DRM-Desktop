package com.DarkKeks.drm.tasks;


import com.DarkKeks.drm.Message;

public class UnknownActionTask extends Task {

    public UnknownActionTask(int id, Message msg) {
        super(id, msg);
    }

    @Override
    protected Message respond(Message message) {
        return Message.getBuilder()
                .setAction("unknownAction")
                .addAll(msg.getParams())
                .build();
    }
}
