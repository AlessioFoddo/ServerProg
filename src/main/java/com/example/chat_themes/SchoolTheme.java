package com.example.chat_themes;

import java.io.DataOutputStream;
import java.io.IOException;

import javax.xml.crypto.Data;

public class SchoolTheme extends Theme {

    public SchoolTheme(){
        nameTheme = "School";
        listaMsg = new ThemeMessages[] {
            new ThemeMessages("/ask", "Posso fare una domanda?"),
            new ThemeMessages("/help", "Puoi aiutarmi?"),
            new ThemeMessages("/done", "Ho finito il compito!"),
            new ThemeMessages("/rpt", "Puoi ripetere, per favore?"),
            new ThemeMessages("/late", "Scusa per il ritardo!"),
            new ThemeMessages("/hmwrk", "Quali sono i compiti per casa?"),
            new ThemeMessages("/proj", "Quando dobbiamo consegnare il progetto?"),
            new ThemeMessages("/abs", "Ero assente, cosa abbiamo fatto?"),
            new ThemeMessages("/brk", "Facciamo una pausa?"),
            new ThemeMessages("/thx", "Grazie per l'aiuto!"),
            new ThemeMessages("/bye", "A domani, ciao!")
        };
    }

}
