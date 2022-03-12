package com.alex.controller;

import com.alex.model.Adapter;
import com.alex.model.Model;
import com.alex.repository.ParseXML;

public class ArchiveGame {
    public static void main(String[] args)  {

        ParseXML.parseXML();
        Model.listHandler(Adapter.listHandlerAdapter(Model.stepList));
    }


}
