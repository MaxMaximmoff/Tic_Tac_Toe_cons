package com.maximoff;

/*
    Класс логики игры
*/

import com.maximoff.gameplay.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class GameModel {

    final String path = "src/main/resources/score.txt";
    final String pathJson = "src/main/resources/gameplay.json";
    final String pathXML = "src/main/resources/gameplay.xml";

    private final GameView gameView;

    private static String[][] matrix = null;

    public GameModel(){

        SingletonGV singletonGV = new SingletonGV();
        this.gameView = singletonGV.getInstance();
        matrix = this.makeMatrix();
    }

    // Создание двумерной матрицы 3х3
    public String[][] makeMatrix() {

        String[][] matrix = new String[3][3];

        for (int row=0; row<3; row++) {
            for (int col=0; col<3; col++) {
                if (matrix[row][col]==null) {
                    matrix[row][col] = "";
                }
            }
        }
        return matrix;
    }

    // Создание двумерной матрицы 3х3 c заполненными цифрами 1-9
    public String[][] makeNamberMatrix() {

        String[][] matrix = new String[3][3];
        int i = 1;
        for (int row=0; row<3; row++) {
            for (int col=0; col<3; col++) {
                if (matrix[row][col]==null) {
                    matrix[row][col] = Integer.toString(i);
                }
                i++;
            }
        }
        return matrix;
    }

    public static String[][] getMatrix(){
        return matrix;
    }

    public void setMatrix(String[][] matrix){
        this.matrix = matrix;
    }

    // Выбор режима игры
    public int modeInput() throws Exception {

        BufferedReader b = new BufferedReader(
                new InputStreamReader(System.in));
        String mode = "";


        while(true){

            while (!mode.equals("1") && !mode.equals("2") && !mode.equals("3") && !mode.equals("4")) {
                gameView.displayMessage("Задайте режим игры.\n\n");
                gameView.displayMessage("Игра с человеком.          ->  Введите - 1.\n");
                gameView.displayMessage("Игра с ботом.              ->  Введите - 2.\n");
                gameView.displayMessage("Показать последнюю игру \n");
                gameView.displayMessage("на экране из XML или Json  ->  Введите - 3.\n");
                gameView.displayMessage("Выйти из игры              ->  Введите - 4.\n");
                gameView.displayMessage("\nВведите число: ");
                mode = b.readLine().toUpperCase();
                if (!mode.equals("1") && !mode.equals("2") && !mode.equals("3") && !mode.equals("4")) {
                    gameView.displayMessage("Вы ввели некорректное значение!\n");
                }
            }

            if (mode.equals("1") || mode.equals("2")) {
                break;
            }


            if (mode.equals("3")) {
                String decision = "";

                while(!decision.equals("x")&& !decision.equals("j")){
                    gameView.displayMessage("\nВыберите источник:\n");
                    gameView.displayMessage("Воспроизведение из XML  -> Введите - x\n");
                    gameView.displayMessage("Воспроизведение из Json -> Введите - j\n");

                    gameView.displayMessage("Введите букву: ");
                    decision = b.readLine().toLowerCase();
                    gameView.displayMessage("\n");
                    if (!decision.equals("x")&& !decision.equals("j")) {
                        gameView.displayMessage("Вы ввели некорректное значение!\n");
                    }
                }
                // Выбор источника для воспроизведения
                if(decision.equals("x")) {
                    ParsWriteFile parsWriteFile = new ParsWriteXml();
                    this.playLastGame(parsWriteFile, pathXML);
                }
                else if (decision.equals("j")) {
                    ParsWriteFile parsWriteFile = new ParsWriteJson();
                    this.playLastGame(parsWriteFile, pathJson);
                }
                mode = "";
            }

            if (mode.equals("4")) {
                // Показываем рейтин на экране
                ScoreFile scoreFile = new ScoreFile(path);
                scoreFile.showScore();
                gameView.displayMessage("\nВыход из игры...");
                System.exit(0); // Выход из игры
            }

        }

        return Integer.parseInt(mode);
    }

    // Инициализация игры (определение имен игроков и их маркеров)
    public List<Player> gameInit(int mode) throws Exception {

        // Имена игроков
        String player_name1 = "";
        String player_name2 = "";
        String[] player_marks = {};

        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));


        switch (mode) {
            // Игра с человеком
            case 1: {
                // Ввод имени первого игрока
                while(player_name1.equals("")){
                    gameView.displayMessage("\nВведите имя первого игрока: ");
                    player_name1 = in.readLine();
                    if(player_name1.equals("Bot")) {
                        gameView.displayMessage("Вы не можете использовать имя Bot!\n");
                        player_name1 = "";
                    }
                }

                // Выбор маркера игроков
                player_marks = this.playerMarks();

                // Ввод имени второго игрока
                while(player_name2.equals("")){
                    gameView.displayMessage("Введите имя второго игрока: ");
                    player_name2 = in.readLine();
                    if(player_name2.equals("Bot")) {
                        gameView.displayMessage("Вы не можете использовать имя Bot!\n");
                        player_name2 = "";
                    }
                    else if (player_name2.equals(player_name1)) {
                        gameView.displayMessage("Одинаковые имена недопустимы!\n");
                        player_name2 = "";
                    }
                }
            }
                break;
            // Игра с ботом
            case 2: {
                // Ввод имени первого игрока
                while(player_name1.equals("")){
                    gameView.displayMessage("\nВведите имя первого игрока: ");
                    player_name1 = in.readLine();
                    if(player_name1.equals("Bot")) {
                        gameView.displayMessage("Вы не можете использовать имя Bot!\n");
                        player_name1 = "";
                    }
                }
                // Выбор маркера игроков
                player_marks = this.playerMarks();
                // Присвоение имени бота
                player_name2 = "Bot";
            }
                break;
            default:
                break;
        }

        Player player1 = new Player(1, player_name1, player_marks[0]);
        Player player2 = new Player(2, player_name2, player_marks[1]);

        gameView.displayMessage(String.format("\nИгрок %s играет за %s.\n",
                player1.getPlayerName(), player1.getPlayerMark()));
        gameView.displayMessage(String.format("Игрок %s играет за %s.\n",
                player2.getPlayerName(), player2.getPlayerMark()));

        List<Player> players = new ArrayList<Player>();
        players.add(player1);
        players.add(player2);

        return players;
    }

    // Выбор маркера игроков
    public String[] playerMarks() throws IOException{

        String mark_first = "";
        String mark_second = "";

        BufferedReader b = new BufferedReader(
                new InputStreamReader(System.in));

        while (!mark_first.equals("X") && !mark_first.equals("O")) {
            gameView.displayMessage("Вы хотите играть за X или O? ");
            mark_first = b.readLine().toUpperCase();
            if (!mark_first.equals("X") && !mark_first.equals("O")) {
                gameView.displayMessage("Вы ввели некорректное значение!\n");
            }
        }

        if (mark_first.equals("X")) {
            mark_second = "O";
        } else {
            mark_second = "X";
        }

        return new String[] {mark_first, mark_second};

    }

    // Определение случайным образом очередности игроков
    public List<Player> chooseFirst(ArrayList<Player> players) {

        Random r = new Random();
        Player firstPlayer = players.get(r.nextInt(players.size()));
        gameView.displayMessage(String.format("Игрок %s ходит первым!\n",
                                                firstPlayer.getPlayerName()));

        List<Player> playersOrdered = new ArrayList<Player>();
        playersOrdered.add(firstPlayer);
        players.forEach((entry) -> {
            if(!entry.equals(firstPlayer)){
                playersOrdered.add(entry);
            }
        });
        playersOrdered.get(0).setPlayerID(1);
        playersOrdered.get(1).setPlayerID(2);
        return playersOrdered;
    }

    // Установка маркера игрока в указанную позицию
    public void placeMarker(String marker, int[] position) {
        this.matrix[position[1]][position[0]] = marker;
    }

    // Переключение игрока при смены очереди хода
    public Player switchPlayer(List<Player> players, Player current_player) {

        if(players.get(0).equals(current_player)){
            return players.get(1);
        }
        return players.get(0);
    }

    // Проверка того, завершена ли игра
    public boolean checkGameFinish(Player current_player) {
        if (this.winСheck(matrix, current_player.getPlayerMark())) {
            gameView.clearScreen();
            gameView.displayMessage(String.format("Игрок %s (играл за %s) выиграл!\n",
                    current_player.getPlayerName(), current_player.getPlayerMark()));
            return true;
        }
        if (this.fullBoardCheck(matrix)) {
            gameView.clearScreen();
            gameView.displayMessage("Игра завершилась вничью!\n");
            return true;
        }
        return false;
    }

    // Проверка выиграл ли игрок с указанным маркером игру
    public boolean winСheck(String[][] matrix, String mark) {

        int l = matrix.length;

        String[] filled_line = new String[]{mark, mark, mark};

        if(Arrays.equals(getDiagonal1(matrix), filled_line)) {
            return true;
        }
        if(Arrays.equals(getDiagonal2(matrix), filled_line)) {
            return true;
        }
        for (int i=0; i<l; i++) {

            if(Arrays.equals(getRow(matrix, i), filled_line) ||
                    Arrays.equals(getColumn(matrix, i), filled_line)) {
                return true;
            }
        }
        return false;
    }

    //Определяет заполнена ли доска полностью маркерами X и O
    public boolean fullBoardCheck(String[][] matrix){

        Set<String> board = new HashSet<String>();

        String[] oneDArray = new String[matrix.length * matrix.length];
        for(int i = 0; i < matrix.length; i++)
        {
            for(int s = 0; s < matrix.length; s++)
            {
                oneDArray[(i * matrix.length) + s] = matrix[i][s];
            }
        }

        board.addAll(Arrays.asList(oneDArray));

        if (board.size()==2 && board.contains("X") && board.contains("O")
                && (!winСheck(matrix, "X") && !winСheck(matrix, "O")))
            return true;

        return false;

    }

    // Предложение продолжить или выйти из игры
    public boolean replay() {

        String decision = "";
        Scanner sc = new Scanner(System.in);

        while(!decision.equals("y") && !decision.equals("n")){
            System.out.print("Вы бы хотели поиграть еще раз? Напишите \"y\" или \"n\": ");
            decision = sc.nextLine().toLowerCase();
            System.out.println();
            if (!decision.equals("y") && !decision.equals("n")) {
                System.out.println("Вы ввели некорректное значение!");
            }
        }
        if(decision.equals("y")) return true;
        return false;
    }

    // Получение столбца матрицы
    private String[] getColumn(String[][] array, int index){
        String[] column = new String[array[0].length];
        for(int i=0; i<column.length; i++){
            column[i] = array[i][index];
        }
        return column;
    }

    // Получение ряда матрицы
    private String[] getRow(String[][] array, int index){
        String[] row = new String[array[0].length];
        for(int i=0; i<row.length; i++){
            row[i] = array[index][i];
        }
        return row;
    }

    // Получение первой главной диагонали матрицы
    private String[] getDiagonal1(String[][] array){
        String[] diagonal = new String[array[0].length];
        for(int i=0; i<diagonal.length; i++){
            diagonal[i] = array[i][i];
        }
        return diagonal;
    }

    // Получение второй главной диагонали матрицы
    private String[] getDiagonal2(String[][] array){
        String[] diagonal = new String[array[0].length];
        for(int i=0; i<diagonal.length; i++){
            diagonal[i] = array[array[0].length-1-i][i];
        }
        return diagonal;
    }

    public void playLastGame(ParsWriteFile parsWriteFile, String path) throws Exception {

        try {

            // Создаем пронумерованное поле игры
            this.setMatrix(this.makeNamberMatrix());

            Gameplay gameplay = parsWriteFile.parsFile(path);

            List<Player> players = gameplay.getPlayers();

            List<Step> steps = gameplay.getGame().getSteps();

            Player winner = gameplay.getGameResult().getPlayer();

            gameView.displayMessage("\nPlayer " + players.get(0).getPlayerID() + " -> "
                    + players.get(0).getPlayerName() + " is Player1 as \'"
                    + players.get(0).getPlayerMark() + "\'!\n");
            gameView.displayMessage("Player " + players.get(1).getPlayerID() + " -> "
                    + players.get(1).getPlayerName() + " is Player2 as \'"
                    + players.get(1).getPlayerMark() + "\'!\n\n");

            for (Step step : steps) {
                gameView.displayBoard(this.getMatrix());
                gameView.displayMessage("\n");
                if (step.getPlayerId() == players.get(0).getPlayerID()) {
                    this.placeMarker(players.get(0).getPlayerMark(),
                            StepValueAdapter.reversTransfer(step.getStepValue()));
                    gameView.displayMessage(String.format("\nХод игрока %s (играет за %s) ход - %d\n",
                            players.get(0).getPlayerName(),
                            players.get(0).getPlayerMark(),
                            step.getStepValue()));
                } else {
                    this.placeMarker(players.get(1).getPlayerMark(),
                            StepValueAdapter.reversTransfer(step.getStepValue()));
                    gameView.displayMessage(String.format("\nХод игрока %s (играет за %s) ход - %d\n",
                            players.get(1).getPlayerName(),
                            players.get(1).getPlayerMark(),
                            step.getStepValue()));
                }
            }

            gameView.displayBoard(this.getMatrix());

            if (gameplay.getGameResult().getPlayer() == null) {
                gameView.displayMessage("\n\tDraw! - Ничья!\n\n");
            } else {
                gameView.displayMessage("\n\tPlayer " + winner.getPlayerID() + " -> "
                        + winner.getPlayerName() + " is winner as "
                        + "\'" + winner.getPlayerMark() + "\'!\n\n");
            }

            // Инициализируем матрицу
            this.setMatrix(this.makeMatrix());

        } catch (NullPointerException e) {
            System.out.println("Ошибка! Нет данных\n");
        }

    }
}
