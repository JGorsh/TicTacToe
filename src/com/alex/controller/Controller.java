package com.alex.controller;

import com.alex.model.Model;
import com.alex.model.Player;
import com.alex.model.Step;
import com.alex.view.GameBoard;
import com.alex.view.View;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class Controller {

    public static BufferedReader reader;
    public static BufferedWriter writer;

    static {
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));   //открываем поток Reader
            writer = new BufferedWriter(new FileWriter("result.txt", true));  //открываем поток Writer
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        View.printInfo();  // информационное сообщение
        readName(); // Считать имена игроков
        View.printStart(); // информационное сообщение
        writer.write("\n" + "Игра между "+ Model.firstPlayer + " и " + Model.secondPlayer +" началась!" + "\n" + "\n"); //запись инфо сообщения в файл
        startGame(); //запуск игры
        reader.close(); //закрытие потока BufferedReader
        writer.close(); //закрытие потока BufferedWriter
    }

    public static void startGame() throws IOException {

     do {
         if(Model.isRobot(Model.firstPlayer)){
             robotHandler();
         }
         else { firstPlayerHandler();}

         if (!Model.isGameFinished()) {  //проверка на выигрыш
             break;
         }

         if(Model.isRobot(Model.secondPlayer)){
             robotHandler();
         }
         else {secondPlayerHandler();}

         if (!Model.isGameFinished()) {  //проверка на выигрыш
             break;
         }

     }  while(Model.isNext);

     }

     public static void readSymbol(){  // метод для обработки введенного символа позиции
        while(true){
            try {
                Model.position = Integer.parseInt(reader.readLine());
                if (!(Arrays.asList(Model.variantPosition).contains(Model.position))
                        || Model.moveList.contains(Model.position)){  //проверка на верное число от 1 до 9  и не повторное
                    throw new Exception();
                }
                Model.moveList.add(Model.position); // добавляем в массив текущую позицию для проверки
                break;
            } catch (Exception e) {
                View.printErrorMessage();
            }
        }
     }
     // Обработчик первого игрока
    public static void firstPlayerHandler() throws IOException {
        View.printStartFirstPlayer(); //сообщение о ходе первого игрока
        GameBoard.printBoard(Model.boardView); //отображения поля
        readSymbol(); //считывание введенной позиции символа первого игрока
        writer.write(Model.firstPlayer + " сделал ход на " + Model.position + " позицию" + "\n"); //запись хода первого игрока
        Model.choicePosition(Model.boardView, Model.position, Model.firstPlayer); //изменение поля в результате выбора позиции первого игрока
        Model.playerStep = new Step(Model.onePlay, Model.position); // создаем экземпляр класса Step (добавляем текущего игрока и позицию)
        Model.stepList.add(Model.playerStep); // добавляем Step в список
    }

    // Обработчик второго игрока
    public static void secondPlayerHandler() throws IOException {
        View.printStartSecondPlayer(); //сообщение о ходе второго игрока
        GameBoard.printBoard(Model.boardView);//отображения поля
        readSymbol(); //считывание введенной позиции символа второго игрока
        writer.write(Model.secondPlayer + " сделал ход на " + Model.position + " позицию" + "\n"); //запись хода второго игрока
        Model.choicePosition(Model.boardView, Model.position, Model.secondPlayer); //изменение поля в результате выбора позиции второго игрока
        Model.playerStep = new Step(Model.twoPlay, Model.position); // создаем экземпляр класса Step (добавляем текущего игрока и позицию)
        Model.stepList.add(Model.playerStep); // добавляем Step в список
    }

    // Обработчик робота
    public static void robotHandler() throws IOException {
        View.printStartRobot(); //сообщение о ходе  игрока
        GameBoard.printBoard(Model.boardView);//отображения поля

        Random random = new Random();
        while(true){  // проверка робота на повторное число
            try {
                Model.position = random.nextInt(9) + 1;  //вводенной позиции робота
                if (Model.moveList.contains(Model.position)){  //проверка на повторное число
                    throw new Exception();
                }
                Model.moveList.add(Model.position); // добавляем в массив текущую позицию для проверки
                break;
            } catch (Exception e) {
                View.printErrorMessage();
            }
        }
        writer.write(Model.secondPlayer + " сделал ход на " + Model.position + " позицию" + "\n"); //запись хода  игрока
        Model.choicePosition(Model.boardView, Model.position, "robot"); //изменение поля в результате выбора позиции  игрока

        //
        if(Model.isRobot(Model.firstPlayer)){
            Model.playerStep = new Step(Model.onePlay, Model.position); // создаем экземпляр класса Step (добавляем текущего игрока и позицию)
            Model.stepList.add(Model.playerStep); // добавляем Step в список
        }
        else {
            Model.playerStep = new Step(Model.twoPlay, Model.position); // создаем экземпляр класса Step (добавляем текущего игрока и позицию)
            Model.stepList.add(Model.playerStep); // добавляем Step в список
        }
    }

    // считывание и обработка повторного имени
    public static void readName (){
        View.printMessageFirst(); //запрос имени первого игрока
        try {
            Model.firstPlayer = reader.readLine(); //запись в переменную имени первого игрока
            Model.onePlay =  new Player(1, Model.firstPlayer, "X"); // создаем экземпляр первого игрока
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            try {
                View.printMessageSecond();  //запрос имени второго игрока
                Model.secondPlayer = reader.readLine();  //запись в переменную имени второго игрока
                Model.twoPlay =  new Player(2, Model.secondPlayer, "0"); // создаем экземпляр второго игрока
                if (Model.firstPlayer.equals( Model.secondPlayer)) {  // проверка одинакового имени
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                View.printErrorName();
            }
        }
    }
}
