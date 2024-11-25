package com.example;

import java.util.ArrayList;

public class Utente {
    
    private String username;
    private String password;
    private ArrayList<ListaChat> listaChat;

    public ArrayList<ListaChat> getListaChat(){
        return listaChat;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Utente(String uname, String psw){
        this.username = uname;
        this.password = psw;
        this.listaChat = new ArrayList<ListaChat>();;
    }

}

