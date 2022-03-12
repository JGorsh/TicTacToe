package com.alex.controller;


import com.alex.model.Model;
import com.alex.repository.ParseXML;


public class ArchiveGame {
    public static void main(String[] args)  {
        ParseXML.parseXML();
        Model.listHandler(Model.stepList);
    }


}
