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
    private String username;
    private String codice;
    private ArrayList<MyThread> contatti;
    private String password;

    public MyThread(Socket s, ArrayList<MyThread> lista){
        this.s = s;
        this.contatti = lista;
        random = new Random();
        username ="";
        password="";
    }

    public void run() {
        String risposta;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            String scelta;
            scelta = in.readLine();
            boolean fine = false;

            switch (scelta) {
                case "1":

                    do {
                        String u = in.readLine();
                        String p = in.readLine();
                        //controllo utente
                        int name = 0;
                        int psw = 0;
                        for (MyThread utente : contatti) {
                            if(utente.username.equals(u)){
                                name = 1;
                            }   
                            if(utente.password.equals(p)){
                                psw = 1;
                            }
                        }
                        if(name == 1 || psw == 1){
                            out.writeBytes("\nv");
                            fine = true;
                        }else if(name == 0 || psw == 0){
                            out.writeBytes("\n!all");
                        }else if(psw == 0){
                            out.writeBytes("\n!p");
                        }else{
                            out.writeBytes("\n!u");
                        }
                
                    } while (!fine);
                    
                    break;

                case "2":

                        do {
                            username = in.readLine();
                            //controllo utente
                            int name = 0;
                            for (MyThread utente : contatti) {
                                if(utente.username.equals(username)){
                                    name = 1;
                                    }  
                            }
                            if(name == 1){
                                out.writeBytes("\n!u");
                            }else{
                                out.writeBytes("\nv");
                                fine = true;
                            }
                
                        } while (!fine);

                        password = in.readLine();

                        

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