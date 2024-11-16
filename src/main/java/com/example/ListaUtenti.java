
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

    public ListaUtenti(){
        this.lista = new ArrayList<>();
    }

}



