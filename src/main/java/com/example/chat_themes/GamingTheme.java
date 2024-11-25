package com.example.chat_themes;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GamingTheme extends Theme {
    
    public GamingTheme(){
        nameTheme = "Gaming";
        listaMsg = new ThemeMessages[] {
            new ThemeMessages("/fllw", "Seguimi"),
            new ThemeMessages("/leave", "Devo andare, ciao!"),
            new ThemeMessages("/redy", "Tutto pronto! Iniziamo!"),
            new ThemeMessages("/wait", "Solo un momento"),
            new ThemeMessages("/gg", "Bella partita"),
            new ThemeMessages("/ll", "LOL"),
            new ThemeMessages("/sry", "Scusa, colpa mia!"),
            new ThemeMessages("/prblm", "Ho problemi di connessione"),
            new ThemeMessages("/thx", "Grazie!"),
            new ThemeMessages("/more", "Facciamone un'altra"),
            new ThemeMessages("/back", "Torno subito")
        };
    }

}
