package com.DarkKeks.drm.tasks;

import com.DarkKeks.drm.Config;
import com.DarkKeks.drm.Message;
import com.DarkKeks.drm.MessageId;
import com.DarkKeks.drm.Parameter;

public class ChangeSecretKeyTask extends Task {

    public ChangeSecretKeyTask(MessageId id, Message msg) {
        super(id, msg);
    }

    @Override
    protected Message respond(Message message) {
        Parameter key = message.getParams().get("key");
        if(key == null) return invalidParams().build();

        Config.SECRET_KEY = key.getStringContent();
        Config.save();

        return success().build();
    }
}
