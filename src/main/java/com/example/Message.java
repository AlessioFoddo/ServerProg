package com.example;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class Message {
    private String username;
    private String text;
    private int codice;

    public void showText(DataOutputStream out) throws IOException{
        out.writeBytes(username + "\n");
        out.writeBytes(text + "\n");
        out.writeBytes(codice + "\n");
    }

    public Message(String name, String text, int code){
        this.username = name;
        this.text = text;
        this.codice = code;
    }

}
