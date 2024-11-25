
package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import com.example.chat_themes.GamingTheme;
import com.example.chat_themes.SchoolTheme;

public class MyThread extends Thread {
      
    private Socket s;
    private ListaUtenti utenti;
    private ArrayList<MyThread> Threads;
    private SchoolTheme schoolTheme;
    private GamingTheme gamingTheme;
    private int chatCodice;
    private Utente user;
    private int id;
    BufferedReader in;
    DataOutputStream out;

    public MyThread(Socket s, ListaUtenti lista, int idThreads, ArrayList<MyThread> list, SchoolTheme st, GamingTheme gt) throws IOException{
        this.s = s;
        this.utenti = lista;
        this.Threads = list;
        this.schoolTheme = st;
        this.gamingTheme = gt;
        this.chatCodice = 1;
        this.id = idThreads;
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

            do{

                scelta = in.readLine();
    
                switch (scelta) {
                    case "Chat":
                        out.writeByte(user.getListaChat().size());
                        if(user.getListaChat().size() == 0){
                            String uname = in.readLine(); // Nome del destinatario
                            String theme = in.readLine();
                            Utente userChat = utenti.presente(uname);
                            if (userChat != null) {
                                Chat chat = new Chat(theme);
                                ListaChat newChat = createListaChat(uname, chat);
                                MyThread dstThread = getUserThread(uname);
                                dstThread.createListaChat(user.getUsername(), chat);
                                out.writeBytes("u_v\n");
                                String msg;
                                boolean rightTheme = false;
                                do {
                                    String testo = in.readLine();
                                    msg = controlloTematica(chat, testo);
                                    if(msg.equals("!txt")){
                                        out.writeBytes(msg + "\n");
                                        rightTheme = false;
                                    }else{
                                        rightTheme = true;
                                    }
                                } while (!rightTheme);
                                newChat.addChat(user.getUsername(), msg);
                                dstThread.receivedText(user.getUsername());
                            } else {
                                out.writeBytes("!u\n"); // Destinatario non trovato
                                break;
                            }
                        }else{
                            out.writeByte(user.getListaChat().size()); 
                            for (ListaChat chat : user.getListaChat()) {
                                chat.mostraChat(out);
                            }
                            boolean controllo = true;
                            do{
                                String answer = in.readLine();
                                switch (answer) {
                                    case "new":
                                        String uname = in.readLine(); // Nome del destinatario
                                        String theme = in.readLine();
                                        Utente userChat = utenti.presente(uname);
                                        if (userChat != null) {
                                            Chat chat = new Chat(theme);
                                            ListaChat newChat = createListaChat(uname, chat);
                                            MyThread dstThread = getUserThread(uname);
                                            dstThread.createListaChat(user.getUsername(), chat);
                                            out.writeBytes("u_v\n");
                                            String msg;
                                            boolean rightTheme = false;
                                            do {
                                                String testo = in.readLine();
                                                msg = controlloTematica(chat, testo);
                                                if(msg.equals("!txt")){
                                                    out.writeBytes(msg + "\n");
                                                    rightTheme = false;
                                                }else{
                                                    rightTheme = true;
                                                }
                                            } while (!rightTheme);
                                            newChat.addChat(user.getUsername(), msg);
                                            dstThread.receivedText(user.getUsername());
                                            controllo = false;
                                        } else {
                                            out.writeBytes("!u\n"); // Destinatario non trovato
                                            controllo = true;
                                            break;
                                        }
                                        break;

                                    case "exit":
                                        controllo = false;
                                        break;

                                    case "theme":
                                        String chosedTheme = in.readLine();
                                        switch (chosedTheme) {
                                            case "SL":
                                                schoolTheme.showText(out);
                                                break;
                                        
                                            case "GM":
                                                gamingTheme.showText(out);
                                                break;
                                        }
                                        break;
                                
                                    default:
                                        try {
                                            int code = Integer.parseInt(answer);
                                            ListaChat conversazione = null;
                                            for (ListaChat chat : user.getListaChat()) {
                                                if(chat.getCodice() == code){
                                                    conversazione = chat;
                                                    controllo = false;
                                                    break;
                                                }else{
                                                    controllo = true;
                                                }
                                            }
                                            if(conversazione != null){
                                                out.writeBytes("u_v\n");
                                                out.writeByte(conversazione.getSize());
                                                conversazione.getChat().outChat(out);
                                                String msg;
                                                boolean rightTheme = false;
                                                do {
                                                    String testo = in.readLine();
                                                    msg = controlloTematica(conversazione.getChat(), testo);
                                                    if(msg.equals("!txt")){
                                                        out.writeBytes(msg + "\n");
                                                        rightTheme = false;
                                                    }else{
                                                        rightTheme = true;
                                                    }
                                                } while (!rightTheme);
                                                conversazione.addChat(user.getUsername(), msg);
                                                getUserThread(conversazione.getDstUser()).receivedText(user.getUsername());;
                                                controllo = false;
                                            }else{
                                                out.writeBytes("!u\n");
                                                controllo = true;
                                            }
                                        } catch (Exception e) {
                                            out.writeBytes("!u\n");
                                            controllo = true;
                                        }
                                        
                                        break;
                                }
                            }while(controllo);
                        }
    
                    case "Members":
                        for(int i  = 0; i < utenti.getSize(); i++){
                            out.writeBytes(utenti.getUtente(i).getUsername() + "\n");
                        }
                        out.writeBytes("end\n");
                        fine = false;
                        break;

                    case "Out":
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
        String p = in.readLine();
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
        if((answer.equals("!all\n")) && name_miss == 1){
            answer = "!u\n";
        }else if(name_miss == 1){
            answer = "!u\n";
        }else if(answer.equals("!all\n")){
            answer = "!all\n";
        }
        out.writeBytes(answer);
        return fine;
    }

    private boolean signUp(BufferedReader in, DataOutputStream out) throws IOException{
        boolean fine = false;
        String u = in.readLine();
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
        user = new Utente(u, psw);
        utenti.addUtente(user);
        return fine;
    }

    public void receivedText(String name) throws IOException{
        out.writeBytes("msg\n");
        out.writeBytes(name + "\n");
    }

    public Utente getUser(){
        return user;
    }

    public int getterId(){
        return id;
    }

    public MyThread getUserThread( String uname ){
        for (int i = 0; i < utenti.getSize(); i++){
            for (MyThread th : Threads) {
                if (th.getUser() != null && th.getUser().getUsername().equals(uname)) {
                    return th;
                }
            }
        }
        return null;
    }

    public ListaChat createListaChat(String name, Chat chat){
        ListaChat lista = new ListaChat(name, chatCodice, chat);
        user.getListaChat().add(lista);
        chatCodice++;
        return lista;
    }

    public String controlloTematica(Chat chat, String text){
        switch (chat.getTheme()) {
            case "SL":
                return schoolTheme.getThemeMessage(text);
        
            case "GM":
                return schoolTheme.getThemeMessage(text);

            default:
                return text;
        }
    }

    public BufferedReader getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

}