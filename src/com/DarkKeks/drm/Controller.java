package com.DarkKeks.drm;

import com.DarkKeks.drm.tasks.Task;

import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Controller {

    private static Controller instance = new Controller();

    private Map<Integer, Task> runningTasks;

    private TaskRegistry registry;

    private SocketSender sender;

    private int currentId;

    public Controller() {
        registry = new TaskRegistry();
        runningTasks = new ConcurrentHashMap<>();

        currentId = 0;

        sender = new SocketSender();
        sender.start();
    }

    public void processMessage(byte[] msg) {
        try {
            Message message = new Message(Security.getDecryptedMessage(msg));

            if(!runningTasks.containsKey(message.getId())) {
                Task task = registry.getInstance(currentId, message);
                runningTasks.put(currentId, task);
                task.start();

                currentId++;
            } else {
                receiveResponse(message);
            }
        } catch (GeneralSecurityException e) {
            Log.info("Can't decrypt message");
        } catch (RuntimeException e) {
            Log.error("Can't parse message");
            Log.logException(e);
        }
    }

    public void respondToMessage(int id, Message response) {
        if(runningTasks.containsKey(id)) {
            try {
                sender.requestSend(Security.getEncryptedMessage(response));
            } catch (GeneralSecurityException e) {
                throw new IllegalStateException("Can't encrypt message");
            }
            runningTasks.remove(id);
        } else {
            throw new IllegalStateException("Response to a non-existent or closed task");
        }
    }

    public void newRequest(Task task, Message request) {
        try {
            runningTasks.put(currentId, task);
            sender.requestSend(Security.getEncryptedMessage(request));

            currentId++;
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Can't encrypt message");
        }
    }

    public void receiveResponse(Message response) {
        if(runningTasks.containsKey(response.getId())) {
            Task task = runningTasks.get(response.getId());
            task.addResponse(response);
            task.notify();

            runningTasks.remove(response.getId());
        } else {
            throw new IllegalStateException("Response to a non-existent closed request");
        }
    }

    public static Controller getInstance() {
        return instance;
    }
}
