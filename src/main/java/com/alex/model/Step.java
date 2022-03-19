package com.alex.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Step {

    private Player player;
    private int playerPosition;

    public Step(Player player, int playerPosition) {
        this.player = player;
        this.playerPosition = playerPosition;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(Integer playerPosition) {
        this.playerPosition = playerPosition;
    }

}
