package com.DarkKeks.drm.tasks;

import com.DarkKeks.drm.Controller;
import com.DarkKeks.drm.Message;
import com.DarkKeks.drm.MessageId;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Task extends Thread {

    private Queue<Message> messageQueue;

    protected Message msg;
    private MessageId id;

    public Task(MessageId id, Message msg) {
        this.id = id;
        this.msg = msg;

        messageQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void run() {
        Message response = respond(msg);
        response.setId(id);
        Controller.getInstance().respondToMessage(id, response);
    }

    protected synchronized Message sendMessage(Message request) {
        Controller.getInstance().newTaskRequest(this, request);
        try {
            while(messageQueue.isEmpty()) {
                wait();
            }
        } catch (InterruptedException e) {}

        return messageQueue.poll();
    }

    public void addResponse(Message response) {
        messageQueue.add(response);
    }

    protected abstract Message respond(Message message);

    protected Message.Builder success(){
        return Message.getBuilder().setAction("success");
    }

    protected Message.Builder invalidParams(){
        return Message.getBuilder().setAction("invalidParams");
    }
}
