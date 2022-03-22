package com.alex.repository;

import com.alex.model.GamePlay;
import com.alex.model.Model;
import com.alex.model.Player;
import com.alex.model.Step;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ParseHTTP implements SaveParseInterface{

    // дата игры для уникальности имени файла
    public static SimpleDateFormat formater = new SimpleDateFormat("yyyy_MM_dd  HH_mm_ss");
    public static Date date = new Date();
    public static String dateFile = formater.format(date);


    @Override
    public void save() {
    }

    @Override
    public void parse(String url) {
        URL url1 = null;
        try {
            url1 = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        HttpURLConnection connection;

        try {
            connection = (HttpURLConnection)url1.openConnection() ;

            //request
            connection.setRequestMethod("GET");
            int status = connection.getResponseCode();

            if(status>299){
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line= bufferedReader.readLine()) !=null){
                    responseContent.append(line);
                }
                bufferedReader.close();
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line= bufferedReader.readLine()) !=null){
                    responseContent.append(line);
                }
                bufferedReader.close();
            }
            //System.out.println(responseContent);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // обработка Jackson полученный String Json из responseContent
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        JsonNode nodePlayers = null;
        JsonNode nodeStep = null;
        JsonNode nodeGameResult = null;
        try {
            nodePlayers = objectMapper.readTree(responseContent.toString()).get("GamePlay").get("Player");
            nodeStep = objectMapper.readTree(responseContent.toString()).get("GamePlay").get("Game").get("Step");
            nodeGameResult = objectMapper.readTree(responseContent.toString()).get("GamePlay").get("GameResult").get("Player");
        } catch (IOException e) {
            e.printStackTrace();
        }
        GamePlay gamePlay = new GamePlay();
        try {
            gamePlay.setPlayers(Arrays.asList(objectMapper.readValue(nodePlayers.toString(), Player[].class)));
            Model.stepList = Arrays.asList(objectMapper.readValue(nodeStep.toString(), Step[].class));
            Model.winnerPlay = objectMapper.readValue(nodeGameResult.toString(),Player.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Model.onePlay = gamePlay.getPlayers().get(0);
        Model.firstPlayer = Model.onePlay.getName();
        Model.twoPlay = gamePlay.getPlayers().get(1);
        Model.secondPlayer = Model.twoPlay.getName();
    }
}
