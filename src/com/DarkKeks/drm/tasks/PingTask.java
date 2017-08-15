package com.DarkKeks.drm.tasks;

import com.DarkKeks.drm.Message;
import com.DarkKeks.drm.MessageId;

public class PingTask extends Task {

    public PingTask(MessageId id, Message msg) {
        super(id, msg);
    }

    @Override
    protected Message respond(Message message) {
        return Message.getBuilder()
                .setAction("pong")
                .addAll(message.getParams())
                .build();
    }
}
