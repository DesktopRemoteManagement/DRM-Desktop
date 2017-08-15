package com.DarkKeks.drm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageId {

    private static final Map<Character, Character> transitions;
    static {
        transitions = new HashMap<>();
        for(char i = '0'; i < '9'; ++i)
            transitions.put(i, (char) (i + 1));
        transitions.put('9', 'a');
        for(char i = 'a'; i < 'f'; ++i)
            transitions.put(i, (char) (i + 1));
        transitions.put('f', '0');
    }

    private ArrayList<Character> value;
    private String addressPart;
    private boolean isShort;

    public MessageId(String id) {
        id = id.toLowerCase();
        if(id.contains(":")) {
            isShort = false;
            int lastSemicolon = id.lastIndexOf(':');
            addressPart = id.substring(0, lastSemicolon);
            id = id.substring(lastSemicolon);
        } else {
            isShort = true;
        }

        int i = 0;
        this.value = new ArrayList<>(id.length());
        for(char c : id.toCharArray()) {
            if((c >= '0' && c <= '9') ||
                    (c >= 'a' && c <= 'f')) {
                this.value.add(i++, c);
            } else {
                throw new IllegalArgumentException("Invalid id string");
            }
        }
    }

    public void increment() {
        int i = 0;
        while(i < value.size() && value.get(i) == 'f'){
            value.set(i, transitions.get(value.get(i)));
            i++;
        }
        if(i >= value.size())
            value.add(transitions.get('0'));
        else
            value.set(i, transitions.get(value.get(i)));
    }

    public boolean isShort() {
        return isShort;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Character c : value) {
            builder.insert(0, c);
        }
        if(isShort) {
            builder.insert(0, ':');
            builder.insert(0, addressPart);
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MessageId && this.toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
