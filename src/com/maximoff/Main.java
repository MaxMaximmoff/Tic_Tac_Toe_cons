package com.maximoff;

/*
    Класс для запуска игры
*/

import java.io.*;


public class Main {

    public static void main(String[] args) throws Exception {

        GameController gameController = new GameController(
                                             new GameModel(), new GameView());

        gameController.play();

    }
}

