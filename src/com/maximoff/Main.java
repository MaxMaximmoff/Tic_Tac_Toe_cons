package com.maximoff;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws Exception {

        GameController gameController = new GameController(
                                             new GameModel(), new GameView());

        gameController.play();

    }
}

