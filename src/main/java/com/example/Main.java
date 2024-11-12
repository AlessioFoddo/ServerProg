package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        ArrayList<MyThread> listaThread = new ArrayList();
        ListaUtenti listaUtenti = new ListaUtenti();
        System.out.println("Hello world!");
        ServerSocket server = new ServerSocket(3000);
        System.out.println("Server avviato!");
        int idThreads = 1;

        do{
            Socket s = server.accept();
            System.out.println("Un client si è collegato!");
            MyThread t = new MyThread(s, listaUtenti, idThreads);
            listaThread.add(t);
            t.start();
            idThreads++;
        }while(true);
    }
}