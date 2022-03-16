package com.maximoff;

/*
    Класс который переводит координаты ходов из формата [int, int] в int и обратно
*/

public class StepValueAdapter {

    // Прямое преобразование координат в XML
    public static int directTransfer(int[] position) {

        int pos_int = 0;
        int i = 1;
        for (int row=0; row<3; row++) {
            for (int col=0; col<3; col++) {
                if (row==position[1]&&col==position[0]) {
                    pos_int = i;
                }
                i++;
            }
        }
        return pos_int;
    }

    // Обратное преобразование координат из XML
    public static int[] reversTransfer(int pos_int) {

        int[] pos = new int[]{0, 0};
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
        return pos;
    }


}
