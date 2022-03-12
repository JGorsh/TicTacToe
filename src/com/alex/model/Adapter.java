package com.alex.model;
import java.util.List;

public class Adapter {

    // адаптер для списка Шагов, преобразует из [11]  в [1]
    public static List<Step> listHandlerAdapter(List<Step> stepList){

        for(Step step : stepList){
            switch(step.getPlayerPosition()){
                case 11:
                    step.setPlayerPosition(1);
                    break;
                case 21:
                    step.setPlayerPosition(2);
                    break;
                case 31:
                    step.setPlayerPosition(3);
                    break;
                case 12:
                    step.setPlayerPosition(4);
                    break;
                case 22:
                    step.setPlayerPosition(5);
                    break;
                case 32:
                    step.setPlayerPosition(6);
                    break;
                case 13:
                    step.setPlayerPosition(7);
                    break;
                case 23:
                    step.setPlayerPosition(8);
                    break;
                case 33:
                    step.setPlayerPosition(9);
                    break;
            }
        }
        return stepList;
    }
}
