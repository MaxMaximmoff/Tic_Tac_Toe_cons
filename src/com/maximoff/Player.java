package com.maximoff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Player extends AbstractPlayer {


    public Player(String player_name, String player_mark, int player_id) {
        super(player_name, player_mark, player_id);
    }

    // Выбор игроком следующей ячейки для хода и проверка того можно ли поставить маркер в эту ячейку
    public int[] playerChoice() throws IOException {
        int[] position = new int[2];

        BufferedReader bi = new BufferedReader(
                new InputStreamReader(System.in));

        String[] strNums;

        while(true) {

            System.out.print("Введите координаты x и y через пробел: ");

            strNums = bi.readLine().split(" ");

            try {
                for (int i = 0; i < 2 || i <strNums.length; i++) {
                    position[i] = Integer.parseInt(strNums[i]) - 1;
                }

                if ( 0<=position[0] && position[0]<3 && 0<=position[1] && position[1]<3) {
                    if (this.spaceCheck(GameModel.getMatrix(), position)) {
                        return position;
                    } else {
                        System.out.println("Эта ячейка занята! Выберите другую!");
                        continue;
                    }

                } else {
                    System.out.println("Значение координаты должно быть от 1 до 10!");
                    continue;
                }
            }
            catch(NumberFormatException e)
            {
                System.out.println("Вы должны вводить числа!");
                continue;
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Должно быть два числа с пробелом между ними!");
                continue;
            }
        }
    }

    // Выбор ботом следующей ячейки случайным образом
    // и проверка того можно ли поставить маркер в эту ячейку
    public int[] botChoice() throws IOException {

        Random random1 = new Random();
        Random random2 = new Random();
        int[] position = {};

        while (true){
            int[] pos = {random1.nextInt(3), random2.nextInt(3)};
            if(spaceCheck(GameModel.getMatrix(), pos)) {
                position = pos;
                break;
            } else {
                continue;
            }
        }
        return position;
    }

    public boolean spaceCheck(String[][] matrix, int[] position) {
        if (matrix[position[1]][position[0]].equals("X") ||
                matrix[position[1]][position[0]].equals("O")) {
            return  false;
        }
        return  true;
    }

}
