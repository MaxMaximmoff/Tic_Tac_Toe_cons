package com.maximoff;

/*
    Класс для игры крестики-нолики 3x3
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TicTacToe {

    private static String path = "./src/resources/score.txt"; // file name

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

    // Генерация игрового поля
    public void displayBoard(String[][] matrix) {

        String row_mx = "";

        System.out.println("     1    2    3   ");
        System.out.println("   |----|----|----|");

        for (int row=0; row<3; row++) {
            String row_index = String.format("%2d |", row + 1);
            row_mx = row_index;
            for (int col=0; col<3; col++) {
                row_mx = row_mx + String.format(" %-3s|", matrix[row][col]);
            }
            System.out.println(row_mx);
            System.out.println("   |----|----|----|");
        }
    }

    // Получение столбца матрицы
    public static String[] getColumn(String[][] array, int index){
        String[] column = new String[array[0].length];
        for(int i=0; i<column.length; i++){
            column[i] = array[i][index];
        }
        return column;
    }

    // Получение ряда матрицы
    public static String[] getRow(String[][] array, int index){
        String[] row = new String[array[0].length];
        for(int i=0; i<row.length; i++){
            row[i] = array[index][i];
        }
        return row;
    }

    // Получение первой главной диагонали матрицы
    public static String[] getDiagonal1(String[][] array){
        String[] diagonal = new String[array[0].length];
        for(int i=0; i<diagonal.length; i++){
            diagonal[i] = array[i][i];
        }
        return diagonal;
    }

    // Получение второй главной диагонали матрицы
    public static String[] getDiagonal2(String[][] array){
        String[] diagonal = new String[array[0].length];
        for(int i=0; i<diagonal.length; i++){
            diagonal[i] = array[array[0].length-1-i][i];
        }
        return diagonal;
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

    // Выбор игровой роли: крестик или нолик
    public int modeInput() throws IOException{

        BufferedReader b = new BufferedReader(
                new InputStreamReader(System.in));
        String mode = "";

        while (!mode.equals("1") && !mode.equals("2")) {
            System.out.println("Введите с кем вы хотите играть.");
            System.out.print("С человеком - 1, с ботом - 2: ");
            mode = b.readLine().toUpperCase();
            if (!mode.equals("1") && !mode.equals("2")) {
                System.out.println("Вы ввели некорректное значение!");
            }
        }

        return Integer.parseInt(mode);
    }

    // Выбор игровой роли: крестик или нолик
    public String[] playerInput() throws IOException{

        String player_first = "";
        String player_second = "";

        BufferedReader b = new BufferedReader(
                new InputStreamReader(System.in));

        while (!player_first.equals("X") && !player_first.equals("O")) {
                System.out.print("Вы хотите играть за X или O? ");
                player_first = b.readLine().toUpperCase();
                if (!player_first.equals("X") && !player_first.equals("O")) {
                    System.out.println("Вы ввели некорректное значение!");
                }
        }

        if (player_first.equals("X")) {
            player_second = "O";
        } else {
            player_second = "X";
        }

//        System.out.printf("Бот будет играть за %s\n", player_second);
        return new String[] {player_first, player_second};

    }

    // Установка маркера игрока в указанную позицию
    public void placeMarker(String[][] matrix, String marker, int[] position) {
        matrix[position[1]][position[0]] = marker;
    }

    // Определение случайным образом игрока, который будет ходить первым
    public String chooseFirst() {
        String[] player_marks = {"X", "O"};
        Random random = new Random();
        return  player_marks[random.nextInt(2)];
    }

    // Определение пуста ли ячейка в указанной позиции
    public boolean spaceCheck(String[][] matrix, int[] position) {
        if (matrix[position[1]][position[0]].equals("X") ||
                matrix[position[1]][position[0]].equals("O")) {
            return  false;
        }
        return  true;
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

        if (board.size()==2 && board.contains("X") && board.contains("O")) return true;

        return false;

    }

    // Выбор игроком следующей ячейки для хода и проверка того можно ли поставить маркер в эту ячейку
    public int[] playerChoice(String[][] matrix) throws IOException {
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
                    if (spaceCheck(matrix, position)) {
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

    // Выбор ботом следующей ячейки случайным образом и проверка того можно ли поставить маркер в эту ячейку
    public int[] botChoice(String[][] matrix) throws IOException {

        Random random1 = new Random();
        Random random2 = new Random();
        int[] position = {};

        while (true){
            int[] pos = {random1.nextInt(3), random2.nextInt(3)};
            if(spaceCheck(matrix, pos)) {
                position = pos;
                break;
            } else {
                continue;
            }
        }
        return position;
    }

    // Выбор ботом следующей ячейки случайным образом и проверка того можно ли поставить маркер в эту ячейку
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

    // Очищение игрового экрана добавлением пустых строк
    public static void clearScreen() {
        for (int i=0; i<100; i++) {
            System.out.println();
        }
    }

    // Переключение роли игрока для смены очереди для хода
    public String switchMark(String mark) {
        if (mark.equals("X")) return "O";
        return "X";
    }

    // Проверка того, завершена ли игра
    public boolean checkGameFinish(String[][] matrix, String mark, String player) {
        if (this.winСheck(matrix, mark)) {
            clearScreen();
            System.out.printf("Игрок %s (играл за %s) выиграл!\n", player, mark);
            return true;
        }
        if (this.fullBoardCheck(matrix)) {
            clearScreen();
            System.out.println("Игра завершилась вничью!");
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {

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
                        clearScreen();
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
                        clearScreen();
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
