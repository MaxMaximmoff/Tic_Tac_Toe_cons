package com.maximoff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {

        String path = "./src/resources/score.txt"; // file name

        System.out.println("Добро пожаловать в игру \"Крестики-нолики\"!");

        TicTacToe game = new TicTacToe();

        String[][] matrix;
        matrix = game.makeMatrix();
        // Выбор режима игры
        int mode = game.modeInput();
        // Выбор игровой роли: крестик или нолик
        String[] player_marks;
        String current_player = "";
        String current_player_mark = "";
        String player_name1 = "";
        String player_name2 = "";
        HashMap<String, String> players = null;

        try {
            while (true) {
                // Игра с человеком
                if (mode==1){
                    // инициализация игры
                    if(player_name1.equals("") || player_name2.equals("")) {

                        matrix = game.makeMatrix();

                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

                        while(player_name1.equals("")){
                            System.out.print("Введите имя первого игрока: ");
                            player_name1 = in.readLine();
                            if(player_name1.equals("Bot")) {
                                System.out.println("Вы не можете использовать имя Bot!");
                                player_name1 = "";
                            }
                        }

                        player_marks = game.playerInput();

                        while(player_name2.equals("")){
                            System.out.print("Введите имя второго игрока: ");
                            player_name2 = in.readLine();
                            if(player_name2.equals("Bot")) {
                                System.out.println("Вы не можете использовать имя Bot!");
                                player_name2 = "";
                            }
                            else if (player_name2.equals(player_name1)) {
                                System.out.println("Одинаковые имена недопустимы!");
                                player_name2 = "";
                            }
                        }

                        players = new HashMap<>();
                        players.put(player_name1, player_marks[0]);
                        players.put(player_name2, player_marks[1]);
                        System.out.printf("\nИгрок %s играет за %s.\n", player_name1, player_marks[0]);
                        System.out.printf("Игрок %s играет за %s.\n\n", player_name2, player_marks[1]);
                        current_player_mark = game.chooseFirst();


                        if (current_player_mark.equals(players.get(player_name1))){
                            current_player = player_name1;
                        }else{
                            current_player = player_name2;
                        }

                        game.displayBoard(matrix);
                        System.out.printf("\nОчередь игрока %s (Играет за %s).\n", current_player, current_player_mark);
                    }


                    if (current_player_mark.equals(players.get(player_name1))) {

                        int player1_position[] = null;
                        while (player1_position==null) {
                            player1_position = game.playerChoice(matrix);
                        }

                        // Установка маркера первого игрока в указанную позицию
                        game.placeMarker(matrix, current_player_mark, player1_position);

                    } else {

                        int player2_position[] = null;
                        while (player2_position==null) {
                            player2_position = game.playerChoice(matrix);
                        }

                        // Установка маркера второго игрока в указанную позицию
                        game.placeMarker(matrix, current_player_mark, player2_position);

                    }

                    // Проверка того, завершена ли игра
                    if (game.checkGameFinish(matrix, current_player_mark, current_player)) {
                        ScoreFile scoreFile = new ScoreFile(path, current_player);
                        game.displayBoard(matrix);
                        if (!game.fullBoardCheck(matrix)) {
                            scoreFile.addScore();
                        }
                        if(!game.replay()) {
                            scoreFile.showScore();
                            break;
                        } else {
                            players.remove(player_name1);
                            players.remove(player_name2);
                            player_name1 = "";
                            player_name2 = "";
                            mode = game.modeInput();
                        }
                    } else {
                        game.clearScreen();
                        game.displayBoard(matrix);

                        current_player_mark = game.switchMark(current_player_mark);
                        if (current_player==player_name1) {
                            current_player=player_name2;
                        } else {
                            current_player=player_name1;
                        }
                        System.out.printf("\nОчередь игрока %s (Играет за %s).\n", current_player, current_player_mark);
                    }
                }
                // Игра с ботом
                else if (mode==2){
                    // инициализация игры
                    if(player_name1.equals("") || player_name2.equals("")) {

                        matrix = game.makeMatrix();

                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

                        while(player_name1.equals("")){
                            System.out.print("Введите имя первого игрока: ");
                            player_name1 = in.readLine();
                            if(player_name1.equals("Bot")) {
                                System.out.println("Вы не можете использовать имя Bot!");
                                player_name1 = "";
                            }
                        }

                        player_marks = game.playerInput();

                        player_name2 = "Bot";

                        players = new HashMap<>();
                        players.put(player_name1, player_marks[0]);
                        players.put(player_name2, player_marks[1]);
                        System.out.printf("\nИгрок %s играет за %s.\n", player_name1, player_marks[0]);
                        System.out.printf("Игрок %s играет за %s.\n\n", player_name2, player_marks[1]);
                        current_player_mark = game.chooseFirst();


                        if (current_player_mark.equals(players.get(player_name1))){
                            current_player = player_name1;
                        }else{
                            current_player = player_name2;
                        }

                        game.displayBoard(matrix);
                        System.out.printf("\nОчередь игрока %s (Играет за %s).\n", current_player, current_player_mark);
                    }

                    if (current_player_mark.equals(players.get(player_name1))) {

                        int player1_position[] = null;
                        while (player1_position==null) {
                            player1_position = game.playerChoice(matrix);
                        }

                        // Установка маркера первого игрока в указанную позицию
                        game.placeMarker(matrix, current_player_mark, player1_position);

                    } else {

                        int player2_position[] = null;
                        while (player2_position==null) {
                            player2_position = game.botChoice(matrix);
                        }

                        // Установка маркера бота в указанную позицию
                        game.placeMarker(matrix, current_player_mark, player2_position);

                    }

                    // Проверка того, завершена ли игра
                    if (game.checkGameFinish(matrix, current_player_mark, current_player)) {
                        ScoreFile scoreFile = new ScoreFile(path, current_player);
                        game.displayBoard(matrix);
                        if (!game.fullBoardCheck(matrix)) {
                            scoreFile.addScore();
                        }
                        if(!game.replay()) {
                            scoreFile.showScore();
                            break;
                        } else {
                            players.remove(player_name1);
                            players.remove(player_name2);
                            player_name1 = "";
                            player_name2 = "";
                            mode = game.modeInput();
                        }
                    } else {
                        game.clearScreen();
                        game.displayBoard(matrix);

                        current_player_mark = game.switchMark(current_player_mark);
                        if (current_player==player_name1) {
                            current_player=player_name2;
                        } else {
                            current_player=player_name1;
                        }
                        System.out.printf("\nОчередь игрока %s (Играет за %s).\n", current_player, current_player_mark);
                    }
                }
                else {
                    break;
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
