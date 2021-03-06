package com.maximoff;

/*
    Контроллер игры
*/

import com.maximoff.gameplay.*;
import java.util.*;

public class GameController {

    final String path = "src/main/resources/score.txt";
    final String pathJson = "src/main/resources/gameplay.json";
    final String pathXML = "src/main/resources/gameplay.xml";

    private final GameModel gameModel;
    private final GameView gameView;

    // конструктор
    public GameController(GameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    // Основной цикл контроллера
    public void play() throws Exception {

        // Переменная текущего игрока
        Player current_player;

        gameView.displayMessage("\nДобро пожаловать в игру крестики-нолики!!!\n\n");

        // Выбор режима игры
        int mode = gameModel.modeInput();
        // Инициализируем матрицу
        gameModel.setMatrix(gameModel.makeMatrix());
        // Список игроков с случайно выбранным первым игроком
        List<Player> players = gameModel.chooseFirst((ArrayList<Player>) gameModel.gameInit(mode));
        // Игрок который будет начинать игру
        current_player = players.get(0);
        // Начинаем подготовку данных для записи в XML и Json
        Gameplay gameplay = new Gameplay();
        gameplay.setPlayer(players);

        int num = 1;
        List<Step> steps = new ArrayList<>();

        while (true) {

            gameView.displayBoard(gameModel.getMatrix());
            gameView.displayMessage(String.format("\nОчередь игрока %s (Играет за %s).\n",
                    current_player.getPlayerName(), current_player.getPlayerMark()));

            if (mode == 1) {
                int[] player_position = null;
                while (player_position == null) {
                    player_position = current_player.playerChoice();
                }
                // Установка маркера игрока в указанную позицию
                gameModel.placeMarker(current_player.getPlayerMark(), player_position);
                // Добавляем данные шага в массив
                steps.add(new Step(num, current_player.getPlayerID(), StepValueAdapter.directTransfer(player_position)));
                num++;

            } else if (mode == 2) {
                if (current_player.getPlayerName().equals("Bot")) {
                    int[] player_position = null;
                    while (player_position == null) {
                        player_position = current_player.botChoice();
                    }

                    gameModel.placeMarker(current_player.getPlayerMark(), player_position);
                    // Добавляем данные шага в массив
                    steps.add(new Step(num, current_player.getPlayerID(), StepValueAdapter.directTransfer(player_position)));
                    num++;

                } else {
                    int[] player_position = null;
                    while (player_position == null) {
                        player_position = current_player.playerChoice();
                    }

                    gameModel.placeMarker(current_player.getPlayerMark(), player_position);
                    // Добавляем данные шага в массив
                    steps.add(new Step(num, current_player.getPlayerID(), StepValueAdapter.directTransfer(player_position)));
                    num++;

                }
            }

            // Проверка завершена ли игра
            if (gameModel.checkGameFinish(current_player)) {
                ScoreFile scoreFile = new ScoreFile(path);
                // Отображаем матрицу на экране
                gameView.displayBoard(gameModel.getMatrix());
                // Если не ничья
                if (!gameModel.fullBoardCheck(gameModel.getMatrix())) {
                    // Пишем рейтинг выигравшего игрока в score.txt
                    scoreFile.addScore(current_player.getPlayerName());
                    // Добавляем данные в gameplay и пишем в XML и JSON
                    gameplay.setGame(new Game(steps));
                    gameplay.setGameResult(new GameResult(current_player));
                    ParsWriteFile parsWriteFile = new ParsWriteXml();
                    parsWriteFile.writeFile(pathXML, gameplay);
                    parsWriteFile = new ParsWriteJson();
                    parsWriteFile.writeFile(pathJson, gameplay);

                    gameView.displayMessage("\n\tPlayer " + current_player.getPlayerID() + " -> "
                            + current_player.getPlayerName() + " is winner as "
                            + "\'" + current_player.getPlayerMark() + "\'!\n\n");

                } else {
                    // Добавляем данные в gameplay и пишем в XML и JSON
                    gameplay.setGame(new Game(steps));
                    gameplay.setGameResult(new GameResult(null));
                    ParsWriteFile parsWriteFile = new ParsWriteXml();
                    parsWriteFile.writeFile(pathXML, gameplay);
                    parsWriteFile = new ParsWriteJson();
                    parsWriteFile.writeFile(pathJson, gameplay);

                    gameView.displayMessage("\n\tDraw! - Ничья!\n\n");
                }

                // инициализируем num
                num = 1;

                // Инициализируем матрицу
                gameModel.setMatrix(gameModel.makeMatrix());

                // выбираем режим игры заново
                mode = gameModel.modeInput();

                // Список игроков с случайно выбранным первым игроком
                players = gameModel.chooseFirst((ArrayList<Player>) gameModel.gameInit(mode));

                // Игрок который будет начинать игру
                current_player = players.get(0);

                // Начинаем подготовку данных для записи в XML и Json
                gameplay = new Gameplay();
                gameplay.setPlayer(players);
                steps = new ArrayList<>();
                continue;

            }

            // Переключаем игрока на противоположного
            current_player = gameModel.switchPlayer(players, current_player);
            // Очистка экрана
            gameView.clearScreen();
        }
    }
}


