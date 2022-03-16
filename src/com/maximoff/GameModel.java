package com.maximoff;

/*
    Класс логики игры
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class GameModel {

    private static String[][] matrix = null;

    public GameModel(){
        this.matrix = this.makeMatrix();
    };

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

    public static String[][] getMatrix(){
        return matrix;
    }

    public void setMatrix(String[][] matrix){
        this.matrix = matrix;
    }

    // Выбор режима игры
    public int modeInput() throws IOException{

        BufferedReader b = new BufferedReader(
                new InputStreamReader(System.in));
        String mode = "";

        while (!mode.equals("1") && !mode.equals("2")) {
            GameView.displayMessage("Задайте режим игры.\n");
            GameView.displayMessage("Игра с человеком. Введите - 1.\n");
            GameView.displayMessage("Игра с ботом. Введите - 2.\n");
            GameView.displayMessage("Введите число: ");
            mode = b.readLine().toUpperCase();
            if (!mode.equals("1") && !mode.equals("2")) {
                GameView.displayMessage("Вы ввели некорректное значение!\n");
            }
        }

        return Integer.parseInt(mode);
    }

    // Инициализация игры (определение имен игроков и их маркеров)
    public List<Player> gameInit(int mode) throws IOException {

        // Имена игроков
        String player_name1 = "";
        String player_name2 = "";
        String[] player_marks = {};

        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));

        // Игра с человеком
        if(mode==1) {

            // Ввод имени первого игрока
            while(player_name1.equals("")){
                GameView.displayMessage("Введите имя первого игрока: ");
                player_name1 = in.readLine();
                if(player_name1.equals("Bot")) {
                    GameView.displayMessage("Вы не можете использовать имя Bot!\n");
                    player_name1 = "";
                }
            }

            // Выбор маркера игроков
            player_marks = this.playerMarks();

            // Ввод имени второго игрока
            while(player_name2.equals("")){
                GameView.displayMessage("Введите имя второго игрока: ");
                player_name2 = in.readLine();
                if(player_name2.equals("Bot")) {
                    GameView.displayMessage("Вы не можете использовать имя Bot!\n");
                    player_name2 = "";
                }
                else if (player_name2.equals(player_name1)) {
                    GameView.displayMessage("Одинаковые имена недопустимы!\n");
                    player_name2 = "";
                }
            }

        }
        // Игра с ботом
        else if(mode==2) {

            // Ввод имени первого игрока
            while(player_name1.equals("")){
                GameView.displayMessage("Введите имя первого игрока: ");
                player_name1 = in.readLine();
                if(player_name1.equals("Bot")) {
                    GameView.displayMessage("Вы не можете использовать имя Bot!\n");
                    player_name1 = "";
                }
            }

            // Выбор маркера игроков
            player_marks = this.playerMarks();

            // Присвоение имени бота
            player_name2 = "Bot";
        }

        Player player1 = new Player(player_name1, player_marks[0], 1);
        Player player2 = new Player(player_name2, player_marks[1], 2);

        GameView.displayMessage(String.format("\nИгрок %s играет за %s.\n",
                player1.getPlayerName(), player1.getPlayerMark()));
        GameView.displayMessage(String.format("Игрок %s играет за %s.\n",
                player2.getPlayerName(), player2.getPlayerMark()));

        ArrayList<Player> players = new ArrayList<Player>();
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
            GameView.displayMessage("Вы хотите играть за X или O? ");
            mark_first = b.readLine().toUpperCase();
            if (!mark_first.equals("X") && !mark_first.equals("O")) {
                GameView.displayMessage("Вы ввели некорректное значение!\n");
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
    public ArrayList<Player> chooseFirst(ArrayList<Player> players) {

        Random r = new Random();
        Player firstPlayer = players.get(r.nextInt(players.size()));
        GameView.displayMessage(String.format("Игрок %s ходит первым!\n",
                                                firstPlayer.getPlayerName()));

        ArrayList<Player> playersOrdered = new ArrayList<Player>();
        playersOrdered.add(firstPlayer);
        players.forEach((entry) -> {
            if(!entry.equals(firstPlayer)){
                playersOrdered.add(entry);
            }
        });
        return playersOrdered;
    }

    // Установка маркера игрока в указанную позицию
    public void placeMarker(String marker, int[] position) {
        this.matrix[position[1]][position[0]] = marker;
    }

    // Переключение игрока при смены очереди хода
    public Player switchPlayer(ArrayList<Player> players, Player current_player) {

        if(players.get(0).equals(current_player)){
            return players.get(1);
        }
        return players.get(0);
    }

    // Проверка того, завершена ли игра
    public boolean checkGameFinish(Player current_player) {
        if (this.winСheck(matrix, current_player.getPlayerMark())) {
            GameView.clearScreen();
            GameView.displayMessage(String.format("Игрок %s (играл за %s) выиграл!\n",
                    current_player.getPlayerName(), current_player.getPlayerMark()));
            return true;
        }
        if (this.fullBoardCheck(matrix)) {
            GameView.clearScreen();
            GameView.displayMessage("Игра завершилась вничью!\n");
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

    public void playLastGame(String pathXML) throws IOException {


    }
}
