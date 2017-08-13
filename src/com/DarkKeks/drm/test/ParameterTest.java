package com.DarkKeks.drm.test;

import com.DarkKeks.drm.Parameter;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class ParameterTest {
    public ArrayList<String> json, name, type;
    public static JsonParser parser;

    public static void main(String... args) throws Exception {
        parser = new JsonParser();
        ParameterTest test = new ParameterTest();
        test.add("{\"name\":\"count\",\"type\":\"number\",\"value\":42.0}", "count", "number");
        test.add("{\"name\":\"message\",\"type\":\"string\",\"value\":\"Hello wrld!\"}", "message", "string");
        test.add("{\"name\":\"color\",\"type\":\"color\",\"value\":{\"r\":255,\"g\":255,\"b\":255}}", "color", "color");
        test.run();
    }

    public ParameterTest() {
        this.json = new ArrayList<>();
        this.name = new ArrayList<>();
        this.type = new ArrayList<>();
    }

    public void add(String json, String name, String type) {
        this.json.add(json);
        this.name.add(name);
        this.type.add(type);
    }

    public void run() throws Exception {
        for(int i = 0; i < json.size(); ++i) {
            Parameter param = new Parameter(parser.parse(json.get(i)).getAsJsonObject());
            if(!param.getName().equals(name.get(i))) throw new Exception("Invalid name");
            if(!param.getType().equals(type.get(i))) throw new Exception("Invalid type");
            if(!param.toJson().toString().equals(json.get(i))) throw new Exception("Invalid json");
        }
        System.out.println("Successful test");
    }
}
