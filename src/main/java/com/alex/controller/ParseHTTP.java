package com.alex.controller;


import com.alex.model.Model;
import com.alex.repository.SaveParse;

import java.net.MalformedURLException;
import java.net.URL;

public class ParseHTTP {

    public static void main(String[] args) throws MalformedURLException {

        SaveParse saveParse = new SaveParse();
        URL url = new URL("https://raw.githubusercontent.com/JGorsh/TicTacToe/JsonSaveParse/1%20%D0%B8%202%202022_03_20%20%2016_32_05.json");
        saveParse.parseHttpJSON(url);
        Model.listHandler(Model.stepList);
    }
}
