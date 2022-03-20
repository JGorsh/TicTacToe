package com.alex.repository;
import java.net.URL;

public interface SaveParseInterface {

    void saveXML();

    void parseXML(String url);

    void saveJSON();

    void parseJSON(String url);

    void parseHttpJSON(URL url);

}
