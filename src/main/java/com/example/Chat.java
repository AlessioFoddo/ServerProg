package com.example;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Chat {
    private ArrayList<Message> chat;
    private int codice = 1;

    public void addChat(String username, String text){
        chat.add(new Message(username, text, codice));
        codice++;
    }

    public void outChat(DataOutputStream out) throws IOException{
        for (Message msg : chat) {
            msg.showText(out);
        }
    }

    public int getSize(){
        return chat.size();
    }

    public Chat(){
        this.chat = new ArrayList<Message>();
    }
}
