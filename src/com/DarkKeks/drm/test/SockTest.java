package com.DarkKeks.drm.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SockTest {

    public static class MessageGenerator extends Thread {
        @Override
        public void run() {
            try {
                while(true) {
                    sleep(500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class Client extends Thread {
        DataOutputStream out;
        DataInputStream in;

        @Override
        public void run() {
            try {
                Socket socket = new Socket("127.0.0.1", 3123);

                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                while(true) {
                    if(in.available() > 0) {
                        readMessage();
                    } else {
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void readMessage() throws IOException {
            int length = in.readInt();
            byte[] data = new byte[length];
            in.readFully(data);
            System.out.println("[Client] " + new String(data));
        }
    }

    public static void main(String[] args) {
        new Client().start();
    }
}
