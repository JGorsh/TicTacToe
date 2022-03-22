package com.alex.controller;

import com.alex.model.Adapter;
import com.alex.model.Model;
import com.alex.repository.SaveParseInterface;
import com.alex.repository.SaveParseXML;


public class XMLParser {
    public static void main(String[] args)  {

        SaveParseInterface saveParseXML = new SaveParseXML();
        saveParseXML.parse(Model.url);
        Model.listHandler(Adapter.listHandlerAdapter(Model.stepList));
    }


}
