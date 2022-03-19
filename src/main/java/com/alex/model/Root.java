package com.alex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Root{

    GamePlay gamePlay;

    public Root() {
    }

    @JsonProperty("GamePlay")
    public GamePlay getGamePlay() {
        return this.gamePlay; }

    public void setGamePlay(GamePlay gamePlay) {
        this.gamePlay = gamePlay; }

}
