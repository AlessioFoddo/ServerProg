package com.example;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ListaChat {

    private Chat lista;
    private String dstUser;
    private int codice = 1;

    public void addChat(String username, String text){
        lista.addChat(username, text);
    }

    public int getSize(){
        return lista.getSize();
    }

    public void mostraChat(DataOutputStream out) throws IOException{
        out.writeBytes(dstUser + "\n");
        out.writeByte(codice);
    }

    public String getDstUser() {
        return dstUser;
    }

    public Chat getChat(){
        return lista;
    }

    public int getCodice() {
        return codice;
    }

    public ListaChat(String name, int code, Chat lista){
        this.lista = lista;
        this.dstUser = name;
        this.codice = code;
    }
    
}
