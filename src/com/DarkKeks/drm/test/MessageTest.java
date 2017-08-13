package com.DarkKeks.drm.test;

import com.DarkKeks.drm.Message;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class MessageTest {
    public ArrayList<String> json, action, destination;
    public static JsonParser parser;

    public static void main(String... args) throws Exception {
        parser = new JsonParser();
        MessageTest test = new MessageTest();
        test.add("{\"message\":{\"action\":\"actionName\",\"params\":[{\"name\":\"count\",\"type\":\"number\",\"value\":42.0},{\"name\":\"message\",\"type\":\"string\",\"value\":\"Hello world!\"},{\"name\":\"color\",\"type\":\"color\",\"value\":{\"r\":255,\"g\":255,\"b\":255}}]},\"destination\":\"127.0.0.1\", \"signature\":\"\"}", "actionName", "127.0.0.1");
        test.run();
    }

    public MessageTest() {
        this.json = new ArrayList<>();
        this.action = new ArrayList<>();
        this.destination = new ArrayList<>();
    }

    public void add(String json, String action, String destination) {
        this.json.add(json);
        this.action.add(action);
        this.destination.add(destination);
    }

    public void run() throws Exception {
        for(int i = 0; i < json.size(); ++i) {
            Message msg = new Message(parser.parse(json.get(i)).getAsJsonObject());
            if(!msg.getAction().equals(action.get(i)))
                throw new Exception("Invalid action");
            if(destination.get(i) != null && !msg.getDestination().equals(destination.get(i)))
                throw new Exception("Invalid destination");
            System.out.println("Signature: " + msg.toJson().get("signature").getAsString());
        }
        System.out.println("Successful test");
    }
}
