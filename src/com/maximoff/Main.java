package com.maximoff;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws Exception {

//        GameController gameController = new GameController(
//                                             new GameModel(), new GameView());
//
//        gameController.play();
        int[] pos = new int[]{0, 0};
        int pos_int = 9;
        int i = 1;
        for (int row=0; row<3; row++) {
            for (int col=0; col<3; col++) {
                if (i==pos_int) {
                    pos[1] = row;
                    pos[0] = col;
                }
                i++;
            }
        }
        System.out.println("col=" + pos[0] + " row=" + pos[1] );
    }
}

