
package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.example.chat_themes.GamingTheme;
import com.example.chat_themes.SchoolTheme;

public class Main {
    public static void main(String[] args) throws IOException {

        SchoolTheme schoolTheme = new SchoolTheme();
        GamingTheme gamingTheme = new GamingTheme();
        ArrayList<MyThread> listaThread = new ArrayList<MyThread>();
        ListaUtenti listaUtenti = new ListaUtenti();
        ServerSocket server = new ServerSocket(3000);
        System.out.println("Server avviato!");
        int idThreads = 1;

        do{
            Socket s = server.accept();
            System.out.println("Un client si è collegato!");
            MyThread t = new MyThread(s, listaUtenti, idThreads, listaThread, schoolTheme, gamingTheme);
            listaThread.add(t);
            t.start();
            idThreads++;
        }while(true);
    }
}