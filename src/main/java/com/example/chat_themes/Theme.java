package com.example.chat_themes;

import java.io.DataOutputStream;
import java.io.IOException;

public class Theme {

    protected ThemeMessages[] listaMsg;
    protected String nameTheme;

    public String getThemeMessage(String code){
        for (ThemeMessages msg : listaMsg) {
            if(msg.getCode().equals(code)){
                return msg.getText();
            }
        }
        if(code.contains("/")){
            return "!txt";
        }else{
            return code;
        }
    }

    public void showText(DataOutputStream out)throws IOException{
        out.writeBytes(nameTheme + "\n");
        out.writeByte(listaMsg.length);
        for (ThemeMessages msg : listaMsg) {
            msg.getMessage(out);
        }
    }

    
}
