package com.alex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameResult {

    @JsonProperty("Player")
    public Player winner; // победитель

    public GameResult() {
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
