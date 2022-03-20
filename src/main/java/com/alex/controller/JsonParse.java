package com.alex.controller;

import com.alex.model.Adapter;
import com.alex.model.Model;
import com.alex.repository.SaveParse;
import java.io.IOException;


public class JsonParse {
    public static void main(String[] args) throws IOException {

        SaveParse saveParse = new SaveParse();
        saveParse.parseJSON("robot и alexey 2022_03_20  19_21_46.json");
        Model.listHandler(Adapter.listHandlerAdapter(Model.stepList));
    }
}
