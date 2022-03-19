package com.alex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


public class GamePlay {

    @JsonProperty("Player")
    public List<Player> players = new ArrayList<>();
    public Game game;
    public GameResult gameResult;

    public GamePlay() {
    }

    @JsonProperty("Game")
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @JsonProperty("GameResult")
    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
