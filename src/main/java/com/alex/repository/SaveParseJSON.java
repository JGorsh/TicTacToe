package com.alex.repository;

import com.alex.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class SaveParseJSON implements SaveParseInterface{

    // дата игры для уникальности имени файла
    public static SimpleDateFormat formater = new SimpleDateFormat("yyyy_MM_dd  HH_mm_ss");
    public static Date date = new Date();
    public static String dateFile = formater.format(date);


    @Override
    public void save(){

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        Root root = new Root();

        GamePlay gamePlay = new GamePlay();
        gamePlay.players.add(Model.onePlay);
        gamePlay.players.add(Model.twoPlay);
        root.setGamePlay(gamePlay);

        Game game = new Game();
        game.setStepList(Model.stepList);
        gamePlay.setGame(game);

        GameResult gameResult = new GameResult();
        if(Model.winner.equals(Model.firstPlayer)){
            gameResult.setWinner(Model.onePlay);
        }
        else if (Model.winner.equals(Model.secondPlayer)){
            gameResult.setWinner(Model.twoPlay);
        }

        gamePlay.setGameResult(gameResult);

        try {
            objectMapper.writeValue(new File(Model.firstPlayer + " и " + Model.secondPlayer + " " + dateFile + ".json"), root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(String url){
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        JsonNode nodePlayers = null;
        JsonNode nodeStep = null;
        JsonNode nodeGameResult = null;
        try {
            nodePlayers = objectMapper.readTree(new File(url)).get("GamePlay").get("Player");
            nodeStep = objectMapper.readTree(new File(url)).get("GamePlay").get("Game").get("Step");
            nodeGameResult = objectMapper.readTree(new File(url)).get("GamePlay").get("GameResult").get("Player");
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
