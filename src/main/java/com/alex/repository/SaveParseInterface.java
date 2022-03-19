package com.alex.repository;

import com.alex.model.Game;
import com.alex.model.GamePlay;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface SaveParseInterface {

    void saveXML();

    void parseXML(String url);

    void saveJSON() throws IOException;

    void parseJSON(String url);

}
