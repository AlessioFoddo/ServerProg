package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javax.print.DocFlavor.STRING;

public class MyThread extends Thread {
      
    private Socket s;
    private Random random;
    private String codice;
    private ListaUtenti contatti;
    private Utente user;
    private int id;

    public MyThread(Socket s, ListaUtenti lista, int idThreads){
        this.s = s;
        this.contatti = lista;
        random = new Random();
        id = idThreads;
    }

    public void run() {
        String risposta;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            String scelta;
            scelta = in.readLine();
            System.out.println(scelta);
            boolean fine = false;

            switch (scelta) {
                case "1":

                    do {
                        String u = in.readLine();
                        String p = in.readLine();
                        //controllo utente
                        String answer = "";
                        for(int i = 0; i < contatti.getSize(); i++){
                            if((contatti.getUtente(i).getUsername().equals(u)) || (contatti.getUtente(i).getPassword().equals(p))){
                                answer = "v\n";
                                user = contatti.getUtente(i);
                                fine = true;
                                break;
                            }else if(!(contatti.getUtente(i).getUsername().equals(u)) || !(contatti.getUtente(i).getPassword().equals(p))){
                                answer = "!all\n";
                            }else if(!(contatti.getUtente(i).getPassword().equals(p))){
                                answer = "!p\n";
                            }else{
                                answer = "!u\n";
                            }
                        }

                        out.writeBytes(answer);
                        
                
                    } while (!fine);
                    
                    break;

                case "2":
                    String u = in.readLine();
                    System.out.println(u);
                    if(contatti.getSize() == 0){
                        out.writeBytes("v\n");
                    }else{
                        do {
                            //controllo utente
                            int name = 0;
                            for(int i = 0; i < contatti.getSize(); i++){
                                if(contatti.getUtente(i).getUsername().equals(u)){
                                    name = 1;
                                }  
                            }
                            if(name == 1){
                                out.writeBytes("!u\n");
                            }else{
                                out.writeBytes("v\n");
                                fine = true;
                            }
                        
                        } while (!fine);
                    }

                    String psw = in.readLine();
                    user = new Utente(u, psw);
                    contatti.addUtente(user);

                    break;
            
                default:
                    break;
            }
            


            s.close();
        } catch (IOException e) {
            risposta = "!";
        }
    }
    
}