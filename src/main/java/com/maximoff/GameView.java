package com.maximoff;

/*
    Класс для отображения визуальных данных игры
*/

public class GameView {

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

    // Генерация сообщения
    public void displayMessage(String message) {

        System.out.print(message);

    }

    // Очищение игрового экрана добавлением пустых строк
    public void clearScreen() {
        for (int i=0; i<100; i++) {
            System.out.println();
        }
    }

}
