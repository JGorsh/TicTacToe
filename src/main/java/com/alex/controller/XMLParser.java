package com.alex.controller;

import com.alex.model.Adapter;
import com.alex.model.Model;
import com.alex.repository.SaveParse;


public class XMLParser {
    public static void main(String[] args)  {

        SaveParse saveParse = new SaveParse();
        saveParse.parseXML(Model.url);
        Model.listHandler(Adapter.listHandlerAdapter(Model.stepList));
    }


}
