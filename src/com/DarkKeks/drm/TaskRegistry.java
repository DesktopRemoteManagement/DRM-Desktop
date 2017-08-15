package com.DarkKeks.drm;

import com.DarkKeks.drm.tasks.*;

import java.util.HashMap;

public class TaskRegistry {

    private HashMap<String, TaskFactory> tasks;

    public TaskRegistry() {
        tasks = new HashMap<>();

        add("shutdown", ShutdownTask::new);
        add("changeSecretKey", ChangeSecretKeyTask::new);
        add("consoleCommand", ConsoleCommandTask::new);
        add("ping", PingTask::new);
        add("message", ReceiveMessageTask::new);
    }

    public Task getInstance(MessageId id, Message msg) {
        if(tasks.containsKey(msg.getAction())) {
            return tasks.get(msg.getAction()).newInstance(id, msg);
        } else {
            return new UnknownActionTask(id, msg);
        }
    }

    private void add(String action, TaskFactory factory) {
        tasks.put(action, factory);
    }

    private interface TaskFactory {
        Task newInstance(MessageId id, Message msg);
    }
}
