package com.alex.controller;

import com.alex.model.Adapter;
import com.alex.model.Model;
import com.alex.repository.SaveParse;
import java.io.IOException;


public class JsonParse {
    public static void main(String[] args) throws IOException {

        SaveParse saveParse = new SaveParse();
        saveParse.parseJSON("alexey и nastya 2022_03_20  00_03_46.json");
        Model.listHandler(Model.stepList);
    }
}
