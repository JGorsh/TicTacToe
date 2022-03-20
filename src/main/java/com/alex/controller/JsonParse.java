package com.alex.controller;

import com.alex.model.Model;
import com.alex.repository.SaveParse;



public class JsonParse {
    public static void main(String[] args){

        SaveParse saveParse = new SaveParse();
        saveParse.parseJSON("alexey и nastya 2022_03_20  00_03_46.json");
        Model.listHandler(Model.stepList);
    }
}
