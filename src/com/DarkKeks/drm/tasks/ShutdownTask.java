package com.DarkKeks.drm.tasks;

import com.DarkKeks.drm.Log;
import com.DarkKeks.drm.Message;
import com.DarkKeks.drm.MessageId;

import java.io.IOException;

public class ShutdownTask extends Task {

    public ShutdownTask(MessageId id, Message msg) {
        super(id, msg);
    }

    @Override
    protected Message respond(Message message) {
        try {
            String shutdownCmd = "shutdown -s";
            Runtime.getRuntime().exec(shutdownCmd);
            Log.info("Shutdown Now");
        } catch (IOException e) {
            Log.logException(e);
        }

        return success().build();
    }
}
