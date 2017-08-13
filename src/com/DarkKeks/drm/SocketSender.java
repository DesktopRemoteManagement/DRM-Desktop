package com.DarkKeks.drm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketSender extends Thread{

    private DataInputStream in;
    private DataOutputStream out;

    private final Queue<byte[]> messageQueue;

    public SocketSender() {
        messageQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void run() {
        while(true) {
            try {
                tryConnect();
            } catch (Exception e) {
                Log.info("SocketSender can't connect.");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.info("SocketSender stopped.");
                break;
            }
        }
    }

    private void tryConnect() throws IOException {
        Socket socket = new Socket(Config.SERVER_ADDRESS, Config.PORT);

        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        while(true) {
            if(!messageQueue.isEmpty()) {
                synchronized (messageQueue) {
                    sendMessage(messageQueue.poll());
                }
            }
            if(in.available() > 0) {
                byte[] msg = readMessage();
                if(msg.length > 0){
                    Controller.getInstance().processMessage(readMessage());
                }
            }
        }
    }

    public void requestSend(byte[] message) {
        synchronized (messageQueue) {
            messageQueue.add(message);
        }
    }

    private void sendMessage(byte[] msg) throws IOException {
        out.writeInt(msg.length);
        out.write(msg);
    }

    private byte[] readMessage() throws IOException {
        int length = in.readInt();
        byte[] msg = new byte[length];
        if(length > 0) {
            in.readFully(msg);
        }
        return msg;
    }
}
