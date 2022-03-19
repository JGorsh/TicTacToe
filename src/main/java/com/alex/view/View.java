package com.alex.view;


import com.alex.model.Game;
import com.alex.model.GamePlay;
import com.alex.model.Model;

public class View {

    public static void printMessageFirst(){

        System.out.println("Имя игрока должно быть уникальным!");
        System.out.println("Введите имя первого ирока:");
    }

    public static void printMessageSecond(){

        System.out.println("Имя игрока должно быть уникальным!");
        System.out.println("Введите имя второго ирока:");
    }

    public static void printInfo(){
        System.out.println("Если хотите сыграть с роботом введите имя игрока: robot");
    }

    public static void printStart(){
        System.out.println("Игра началась!");
    }

    public static void printStartFirstPlayer(){
        System.out.println("--------------------------------");
        System.out.println(Model.firstPlayer + " Твой ход!");
        System.out.println("Введите номер позиции [1-9]");
       //System.out.println(GamePlay.onePlay);
       //System.out.println(GamePlay.twoPlay);
       //System.out.println(Game.stepList);
    }
    public static void printStartRobot(){
        System.out.println("--------------------------------");
        System.out.println("robot" + " Твой ход!");
        System.out.println("Введите номер позиции [1-9]");
    }

    public static void printStartSecondPlayer(){
        System.out.println("--------------------------------");
        System.out.println(Model.secondPlayer + " Твой ход!");
        System.out.println("Введите номер позиции [1-9]");
    }

    public static void printWinMessage(String name){
        System.out.println("--------------------------------");
        System.out.println("ПОЗДРАВЛЯЮ "+ name + " ВЫ ВЫИГРАЛИ!!!");
        System.out.println("ВАШ СЧЕТ: ");
        System.out.println(Model.firstPlayer + " " + Model.countFirst);
        System.out.println(Model.secondPlayer + " " + Model.countSecond);
        System.out.println("ХОТИТЕ ПРОДОЛЖИТЬ ИГРУ? (Если ДА - введите 'y' / Если НЕТ - введите любой символ)");
    }

    public static void printDrawMessage(){
        System.out.println("--------------------------------");
        System.out.println("НИЧЬЯ!!!");
        System.out.println("ВАШ СЧЕТ: ");
        System.out.println(Model.firstPlayer + " " + Model.countFirst);
        System.out.println(Model.secondPlayer + " " + Model.countSecond);
        System.out.println("ХОТИТЕ ПРОДОЛЖИТЬ ИГРУ? (Если ДА - введите 'y' / Если НЕТ - введите любой символ)");
    }

    public static void printErrorMessage(){
        System.out.println("Вы ввели неверный символ!!!");
        System.out.println("Повторите ввод");
        System.out.println("Введите номер позиции [1-9]");
    }

    public static void printErrorName(){
        System.out.println("ВЫ ВВЕЛИ ОДИНАКОВОЕ ИМЯ!!!");
        System.out.println("Повторите ввод");
    }
}
