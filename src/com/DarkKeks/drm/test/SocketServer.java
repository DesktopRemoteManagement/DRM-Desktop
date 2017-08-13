package com.DarkKeks.drm.test;

import com.DarkKeks.drm.*;
import com.google.gson.JsonParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.GeneralSecurityException;

public class SocketServer extends Thread{

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(Config.PORT);

            Log.info("ServerSocket started on port " + ss.getLocalPort());
            while(true){
                Socket socket = ss.accept();

                SocketThread thread = new SocketThread(socket);

                thread.start();
            }
        } catch (IOException e) {
            Log.logException(e);
        }
    }

    public class SocketThread extends Thread {
        private Socket socket;
        private JsonParser parser = new JsonParser();

        public SocketThread(Socket clientSocket) {
            this.socket = clientSocket;

            Log.info("New connection: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
        }

        @Override
        public void run() {
            try(DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream  in  = new DataInputStream(socket.getInputStream())) {

                while(true) {
                    int length = in.readInt();
                    if(length > 0) {
                        byte[] message = new byte[length];
                        in.readFully(message);

                        String decrypted = Security.getDecryptedMessage(message);
                        Message msg = new Message(parser.parse(decrypted).getAsJsonObject());

                        Log.info("Received: " + msg.getAction());

                        msg.addParam(new Parameter("serverChecked", "boolean", true));

                        message = Security.getEncryptedMessage(msg);

                        out.writeInt(message.length);
                        out.write(message);
                    }
                }

            } catch (IOException | GeneralSecurityException e) {
                Log.logException(e);
            }
        }
    }

    public static void main(String[] ar) {
        Config.init();
        new SocketServer().start();
    }
}
