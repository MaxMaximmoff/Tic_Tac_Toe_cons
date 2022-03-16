package com.maximoff;

/*
    Контроллер игры
*/

import java.io.IOException;
import java.util.*;

public class GameController {

    final String path = "src/resources/score.txt"; // file name
    final String pathXML = "src/resources/gameplay.xml";

    private GameModel gameModel;
    private GameView gameView;

    // конструктор
    public GameController(GameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    // Основной цикл контроллера
    public void play() throws Exception {

        // Переменная текущего игрока
        Player current_player;
        XmlWriter xmlWriter;

        int mode = gameModel.modeInput();

        // Список игроков с случайно выбранным первым игроком
        ArrayList<Player> players = gameModel.chooseFirst((ArrayList<Player>) gameModel.gameInit(mode));
        // Игрок который будет начинать игру
        current_player = players.get(0);
        // Начинаем подготовку данных для записи в XML
        Gameplay gameplay = new Gameplay(players);
        gameplay.getPlayer1().setPlayerID(1);
        gameplay.getPlayer2().setPlayerID(2);
        int num = 1;

        while (true) {

            gameView.displayBoard(gameModel.getMatrix());
            gameView.displayMessage(String.format("\nОчередь игрока %s (Играет за %s).\n",
                    current_player.getPlayerName(), current_player.getPlayerMark()));

            if(mode==1) {
                int[] player_position = null;
                while (player_position == null) {
                    player_position = current_player.playerChoice();
                }
                // Установка маркера игрока в указанную позицию
                gameModel.placeMarker(current_player.getPlayerMark(), player_position);
                gameplay.addStep(num, current_player.getPlayerID(), StepValueAdapter.directTransfer(player_position));
                num++;
            } else if (mode==2) {
                if(current_player.getPlayerName().equals("Bot")){
                    int[] player_position = null;
                    while (player_position == null) {
                        player_position = current_player.botChoice();
                    }

                    gameModel.placeMarker(current_player.getPlayerMark(), player_position);
                    gameplay.addStep(num, current_player.getPlayerID(), StepValueAdapter.directTransfer(player_position));
                    num++;

                } else {
                    int[] player_position = null;
                    while (player_position == null) {
                        player_position = current_player.playerChoice();
                    }

                    gameModel.placeMarker(current_player.getPlayerMark(), player_position);
                    gameplay.addStep(num, current_player.getPlayerID(), StepValueAdapter.directTransfer(player_position));
                    num++;

                }
            }

            // Проверка завершена ли игра
            if (gameModel.checkGameFinish(current_player)) {
                ScoreFile scoreFile = new ScoreFile(path, current_player.getPlayerName());
                xmlWriter = new XmlWriter(pathXML);
                gameView.displayBoard(gameModel.getMatrix());
                // Если не ничья
                if (!gameModel.fullBoardCheck(gameModel.getMatrix())) {
                    // Пишем рейтинг выигравшего игрока в score.txt
                    scoreFile.addScore();
                    // Добавляем в XML победителя
                    gameplay.setWinner(current_player);
                } else {
                    gameplay.setWinner(new Player("Draw!", "", 3));
                }
                if(!gameModel.replay()) {
                    // Показываем рейтин на экране
                    scoreFile.showScore();
                    xmlWriter.saveGameplay(gameplay);
                    break;
                } else {
                    xmlWriter.saveGameplay(gameplay);
                    // обнуляем переменные
                    current_player = null;
                    players = null;
                    gameplay = null;
                    num=1;
                    gameModel.setMatrix(gameModel.makeMatrix());
                    // выбираем режим игры заново
                    mode = gameModel.modeInput();
                    // Получаем заново список игроков с случайно выбранным первым игроком
                    players = gameModel.chooseFirst((ArrayList<Player>) gameModel.gameInit(mode));
                    // Игрок который будет начинать игру
                    current_player = players.get(0);
                    // Начинаем опять подготовку данных для записи в XML
                    gameplay = new Gameplay(players);
                    gameplay.getPlayer1().setPlayerID(1);
                    gameplay.getPlayer2().setPlayerID(2);
                    continue;
                }

            }

            current_player = gameModel.switchPlayer(players, current_player);
            GameView.clearScreen();

        }
    }
}


