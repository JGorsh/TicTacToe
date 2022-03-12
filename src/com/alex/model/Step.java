package com.alex.model;


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

    @Override
    public String toString() {
        return "Step{" +
                "player=" + player +
                ", playerPosition=" + playerPosition +
                '}';
    }
}