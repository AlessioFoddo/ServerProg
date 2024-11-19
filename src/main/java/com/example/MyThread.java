
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
    private ListaUtenti utenti;
    private ListaUtenti listaChat;
    private ArrayList<MyThread> Threads;
    private Utente user;
    private int id;
    BufferedReader in;
    DataOutputStream out;

    public MyThread(Socket s, ListaUtenti lista, int idThreads, ArrayList<MyThread> list) throws IOException{
        this.s = s;
        this.utenti = lista;
        this.listaChat = new ListaUtenti();
        this.Threads = list;
        id = idThreads;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new DataOutputStream(s.getOutputStream());
    }

    public void run() {
        String risposta;
        try {
            String scelta;
            boolean fine = false;

            do{

                scelta = in.readLine();
                System.out.println(scelta);
    
                switch (scelta) {
                    case "L_I": //caso LOG IN
    
                        fine = logIn(in, out);
                        break;
    
                    case "S_U": //caso SIGN UP
                        
                        fine = signUp(in, out);
                        break;
                
                    default:

                        fine = false;
                        break;
                }

            }while(!fine);

            System.out.println("CIAO");

            do{

                scelta = in.readLine();
                System.out.println(scelta);
    
                switch (scelta) {
                    case "Chat":
                        String uname = in.readLine(); // Nome del destinatario
                        Utente userChat = utenti.presente(uname);

                        if (userChat != null) {
                            out.writeBytes("u_v\n");
                            listaChat.addUtente(userChat);
                        } else {
                            out.writeBytes("!u\n"); // Destinatario non trovato
                            break;
                        }

                        boolean endChat = false;
                        while (!endChat) {
                            String testo = in.readLine();
                            if (testo.equals("end")) {
                                endChat = true;
                                break;
                            }

                            for (MyThread th : Threads) {
                                if (th.getUser() != null && th.getUser().getUsername().equals(uname)) {
                                    th.receivedText(user.getUsername(), testo);
                                    break;
                                }
                            }
                        }
                        fine = false;
                        break;
    
                    case "Members": //caso SIGN UP
                        for(int i  = 0; i < utenti.getSize(); i++){
                            int idThread = 0;
                            for (MyThread th : Threads) {
                                if(th != null && th.getUser().getUsername().equals(utenti.getUtente(i).getUsername())){
                                    idThread = th.getterId();
                                }
                            }
                            out.writeBytes("Thread: " + idThread + ", " + utenti.getUtente(i).getUsername() + "\n");
                        }
                        out.writeBytes("end\n");
                        fine = false;
                        break;

                    case "Out": //caso uscita
                        user = null;
                        for(int i = 0; i < Threads.size(); i++){
                            if(Threads.get(i).getId() == id){
                                Threads.remove(i);
                                break;
                            }
                        }
                        fine = true;
                        break;
                
                    default:
                        fine = false;
                        break;
                }

            }while(!fine);

            s.close();
        } catch (IOException e) {
            risposta = "!";
        }
    }

    private boolean logIn(BufferedReader in, DataOutputStream out) throws IOException{
        boolean fine = false;
        if(utenti.getSize() == 0){
            out.writeBytes("noU\n");
            signUp(in, out);
            return true;
        }else{
            out.writeBytes("fw\n");
        }
        String u = in.readLine();
        System.out.println(u);
        out.writeBytes("username ricevuto\n");
        String p = in.readLine();
        System.out.println(p);
        //controllo utente
        String answer = "";
        int name_miss = 0;
        for(int i = 0; i < utenti.getSize(); i++){
            if((utenti.getUtente(i).getUsername().equals(u)) && (utenti.getUtente(i).getPassword().equals(p))){
                answer = "v\n";
                user = utenti.getUtente(i);
                fine = true;
                break;
            }else if(!(utenti.getUtente(i).getUsername().equals(u)) && !(utenti.getUtente(i).getPassword().equals(p))){
                answer = "!all\n";
            }else if(!(utenti.getUtente(i).getPassword().equals(p))){
                answer = "!p\n";
                break;
            }else{
                name_miss = 1;
            }
        }
        System.out.println(answer);
        if((answer.equals("!all\n")) && name_miss == 1){
            answer = "!u\n";
        }else if(name_miss == 1){
            answer = "!u\n";
        }else if(answer.equals("!all\n")){
            answer = "!all\n";
        }
        System.out.println("risposta: " + answer);
        out.writeBytes(answer);
        return fine;
    }

    private boolean signUp(BufferedReader in, DataOutputStream out) throws IOException{
        boolean fine = false;
        String u = in.readLine();
        System.out.println(u);
        if(utenti.getSize() == 0){
            out.writeBytes("v\n");
            fine = true;
        }else{
            do { 
                //controllo username utente
                int name = 0;
                for(int i = 0; i < utenti.getSize(); i++){
                    if(utenti.getUtente(i).getUsername().equals(u)){
                        name = 1;
                    }  
                }
                if(name == 1){
                    out.writeBytes("!u\n");
                    fine = false;
                    return fine;
                }else{
                    out.writeBytes("v\n");
                    fine = true;
                }
            
            } while (!fine);
        }
        String psw = in.readLine();
        System.out.println(psw);
        user = new Utente(u, psw);
        utenti.addUtente(user);
        return fine;
    }

    public void receivedText(String name, String text) throws IOException{
        out.writeBytes("msg\n");
        out.writeBytes(name + "\n");
        out.writeBytes(text + "\n");
    }

    public Utente getUser(){
        return user;
    }

    public int getterId(){
        return id;
    }

}