package com.DarkKeks.drm.tasks;

import com.DarkKeks.drm.GUI;
import com.DarkKeks.drm.Message;
import com.DarkKeks.drm.Parameter;

public class ReceiveMessageTask extends Task {

    public ReceiveMessageTask(int id, Message msg) {
        super(id, msg);
    }

    @Override
    protected Message respond(Message message) {
        Parameter from = message.getParams().get("from");
        Parameter msg = message.getParams().get("message");
        if(from == null || msg == null) return invalidParams().build();

        GUI.showMessage(from.getStringContent(),
                        msg.getStringContent());

        return success().build();
    }
}
