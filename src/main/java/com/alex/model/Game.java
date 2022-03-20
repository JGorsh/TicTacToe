package com.alex.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;

@JsonAutoDetect
public class Game {

    @JsonProperty("Step")
    public List<Step> stepList; // список шагов

    public Game() {
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }


}
