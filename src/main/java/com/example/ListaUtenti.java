
package com.example;

import java.util.ArrayList;

public class ListaUtenti {

    ArrayList<Utente> lista = new ArrayList();
    
    public void addUtente(Utente u){
        lista.add(u);
    }

    public int getSize(){
        return this.lista.size();
    }

    public Utente getUtente(int i){
        return lista.get(i);
    }

    public Utente presente(String uname){
        for (Utente utente : lista) {
            if(utente.getUsername().equals(uname)){
                return utente;
            }
        }
        return null;
    }

    public ListaUtenti(){
        this.lista = new ArrayList<>();
    }

}



