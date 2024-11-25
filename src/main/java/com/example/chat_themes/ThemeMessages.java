package com.example.chat_themes;

import java.io.DataOutputStream;
import java.io.IOException;

public class ThemeMessages {

    private String code;
    private String text;

    public String getCode(){
        return code;
    }

    public String getText(){
        return text;
    }

    public void getMessage(DataOutputStream out)throws IOException{
        out.writeBytes(code + "\n");
        out.writeBytes(text + "\n");
    }

    public ThemeMessages(String codice, String messaggio){
        this.code = codice;
        this.text = messaggio;
    }
    
}
