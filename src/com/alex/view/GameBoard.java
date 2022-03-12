package com.alex.view;

public class GameBoard {

    //отображение игрового поля
    public static void printBoard(char[][] boardView){

        System.out.println("--------------------------------");

        for(char [] row : boardView){
            for(char c : row){
                System.out.print(c);
            }
            System.out.println();
        }
    }
}
