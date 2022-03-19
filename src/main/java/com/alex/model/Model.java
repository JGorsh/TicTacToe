package com.alex.model;

import com.alex.controller.Controller;
import com.alex.repository.SaveParse;
import com.alex.view.GameBoard;
import com.alex.view.View;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Model {


    public static int position;  // позиция которую вводит текущий игрок
    public static char symbol;   // символ Х или 0
    public static String firstPlayer; //имя первого игрока
    public static String secondPlayer; //имя второго игрока
    public static Player onePlay; // объявляем первого игрока
    public static Player twoPlay; // объявляем второго игрока
    public static Player winnerPlay; // победитель
    public static Step playerStep; // объявляем переменную позиции игрока
    public static String winner; // победитель
    public static GameResult gameResult = new GameResult();
    public static int countFirst;  //количество побед первого игрока
    public static int countSecond;  //количество побед второго игрока
    public static boolean isNext = true; // флаг результата
    public static String answer;  // ответ запроса о продолжении игры
    public static List<Step> stepList = new ArrayList<>(); // список шагов
    public static ArrayList<Integer> moveList = new ArrayList<>(); // для записи позиций и счета количества ходов
    public static Integer[] variantPosition = {1, 2, 3, 4, 5, 6, 7, 8, 9}; // для обработки исключений
    public static String url = "alex и timur 2022_03_15  17_32_58.xml"; //url парсинга
    public static char[][] boardView = {
            {'|', '1', '|', '2', '|', '3', '|'},
            {' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'|', '4', '|', '5', '|', '6', '|'},
            {' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'|', '7', '|', '8', '|', '9', '|'}
    }; // форма поля

    //метод проверки массива на соответствие выиграшных вариантов
    public static Boolean checkProgress(char symbol) {
        if ((boardView[0][1] == symbol && boardView[0][3] == symbol && boardView[0][5] == symbol) ||
                (boardView[2][1] == symbol && boardView[2][3] == symbol && boardView[2][5] == symbol) ||
                (boardView[4][1] == symbol && boardView[4][3] == symbol && boardView[4][5] == symbol) ||

                (boardView[0][1] == symbol && boardView[2][1] == symbol && boardView[4][1] == symbol) ||
                (boardView[0][3] == symbol && boardView[2][3] == symbol && boardView[4][3] == symbol) ||
                (boardView[0][5] == symbol && boardView[2][5] == symbol && boardView[4][5] == symbol) ||

                (boardView[0][1] == symbol && boardView[2][3] == symbol && boardView[4][5] == symbol) ||
                (boardView[0][5] == symbol && boardView[2][3] == symbol && boardView[4][1] == symbol)) {

            return true;
        } else return false;
    }

    // метод обработки запроса на повторную игру
    public static void request() throws IOException {
        SaveParse saveParse = new SaveParse();
        answer = Controller.reader.readLine();
        if (answer.equals("y")) {
            saveParse.saveXML();// сохранение в XML
            saveParse.saveJSON();
            initBoard();
            Controller.writer.write("\n" + "--------------------------------" + "\n");
            Controller.startGame();
        } else {
            System.out.println("Конец игры!");
            Controller.writer.write("\n" + "Результат:" + "\n" + firstPlayer + " " + countFirst + "\n" + secondPlayer + " " + countSecond + "\n");
            saveParse.saveXML();// сохранение в XML
            saveParse.saveJSON();
            isNext = false;
        }
    }

    //метод проверки и обработки выигрыша или ничьей
    public static Boolean isGameFinished() throws IOException {

        if (checkProgress('X')) {
            countFirst++;
            GameBoard.printBoard(boardView);
            winner = firstPlayer;
            View.printWinMessage(firstPlayer);
            request();
            return isNext;
        }

        if (checkProgress('0')) {
            countSecond++;
            GameBoard.printBoard(boardView);
            winner = secondPlayer;
            View.printWinMessage(secondPlayer);
            request();
            return isNext;
        }

        if (moveList.size() == 9) {
            GameBoard.printBoard(boardView);
            winner = "Ничья!";
            View.printDrawMessage();
            request();
            return isNext;
        }

        return true;
    }

    //метод обработки выбора позиции
    public static void choicePosition(char[][] boardView, int position, String user) {

        if (user.equals(firstPlayer)) {
            symbol = 'X';
        } else if (user.equals(secondPlayer)) {
            symbol = '0';
        }

        switch (position) {
            case 1:
                boardView[0][1] = symbol;
                break;
            case 2:
                boardView[0][3] = symbol;
                break;
            case 3:
                boardView[0][5] = symbol;
                break;
            case 4:
                boardView[2][1] = symbol;
                break;
            case 5:
                boardView[2][3] = symbol;
                break;
            case 6:
                boardView[2][5] = symbol;
                break;
            case 7:
                boardView[4][1] = symbol;
                break;
            case 8:
                boardView[4][3] = symbol;
                break;
            case 9:
                boardView[4][5] = symbol;
                break;
        }
    }

    //сброс переменных при выборе: продолжить игру
    public static void initBoard() {
        char[][] boardViewClean = {
                {'|', '1', '|', '2', '|', '3', '|'},
                {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {'|', '4', '|', '5', '|', '6', '|'},
                {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {'|', '7', '|', '8', '|', '9', '|'}
        };
        boardView = boardViewClean;
        moveList = new ArrayList<>();
        Model.stepList = new ArrayList<>();
    }

    //проверка на робота
    public static boolean isRobot(String player) {
        if (player.equals("robot")) {
            return true;
        } else return false;
    }

    //обработка списка Step и вывод полученного результата
    public static void listHandler(List<Step> stepList){
        for(Step step : stepList){
            choicePosition(boardView, step.getPlayerPosition(), step.getPlayer().getName());
            GameBoard.printBoard(boardView);
        }
        System.out.println();
        if (Model.winnerPlay==null){
            System.out.println("Ничья!!!");
        }
        else{
            System.out.println("Player " + Model.winnerPlay.getId() + " -> " + Model.winnerPlay.getName() + " is winner as " + Model.winnerPlay.getMark()+ "!");
        }

    }
}
