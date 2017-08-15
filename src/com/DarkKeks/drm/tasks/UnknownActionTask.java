package com.DarkKeks.drm.tasks;

import com.DarkKeks.drm.Message;
import com.DarkKeks.drm.MessageId;

public class UnknownActionTask extends Task {

    public UnknownActionTask(MessageId id, Message msg) {
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
