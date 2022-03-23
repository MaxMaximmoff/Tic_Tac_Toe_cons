package com.maximoff;

/*
    Класс для запуска игры
*/

public class Main {

    public static void main(String[] args) throws Exception {

        // создаем единственный gameView
        SingletonGV singletonGV = new SingletonGV();
        GameView gameView = singletonGV.getInstance();

        GameController gameController = new GameController(new GameModel(), gameView);

        gameController.play();

    }
}

