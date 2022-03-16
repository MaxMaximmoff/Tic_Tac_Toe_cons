package com.maximoff;

/*
    Абстрактный класс игрока
 */

import java.io.IOException;

public abstract class AbstractPlayer {

    private  String player_mark;
    private  String player_name;
    private  int player_id;

    public AbstractPlayer(String player_name, String player_mark, int player_id) {
        this.player_name = player_name;
        this.player_mark = player_mark;
        this.player_id = player_id;
    }

    public String getPlayerName() {
        return player_name;
    }

    public String getPlayerMark() {
        return player_mark;
    }

    public int getPlayerID() {
        return player_id;
    }

    public void setPlayerID(int player_id) {
        this.player_id = player_id;
    }

    // выбор координат x и y
    abstract int[] playerChoice() throws IOException;

}
