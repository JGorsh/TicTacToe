package com.alex.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {

    int id;
    String name;
    String mark;

    public Player() {
    }

    public Player(int id, String name, String mark) {
        this.id = id;
        this.name = name;
        this.mark = mark;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("symbol")
    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }
}
