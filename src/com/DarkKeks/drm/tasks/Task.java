package com.DarkKeks.drm.tasks;

import com.DarkKeks.drm.Controller;
import com.DarkKeks.drm.Message;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Task extends Thread {

    private Queue<Message> messageQueue;

    protected Message msg;
    private int id;

    public Task(int id, Message msg) {
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

    protected Message sendMessage(Message request) {
        Controller.getInstance().newRequest(this, request);
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