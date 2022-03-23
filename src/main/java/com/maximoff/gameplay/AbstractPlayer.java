package com.maximoff.gameplay;

/*
    Абстрактный класс игрока
 */

import java.io.IOException;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "symbol"
})
@Generated("me")


public abstract class AbstractPlayer {

    @JsonProperty("id")
    private  int player_id;

    @JsonProperty("name")
    private  String player_name;

    @JsonProperty("symbol")
    private  String player_mark;


    public AbstractPlayer() {
    }

    /**
     * @param player_id
     * @param player_name
     * @param player_mark
     */
    public AbstractPlayer(int player_id, String player_name, String player_mark) {
        this.player_name = player_name;
        this.player_mark = player_mark;
        this.player_id = player_id;
    }

    @JsonProperty("id")
    public int getPlayerID() {
        return player_id;
    }

    @JsonProperty("id")
    public void setPlayerID(int player_id) {
        this.player_id = player_id;
    }

    @JsonProperty("name")
    public String getPlayerName() {
        return player_name;
    }

    @JsonProperty("name")
    public void setPlayerName(String player_name) {
        this.player_name = player_name;
    }

    @JsonProperty("symbol")
    public String getPlayerMark() {
        return player_mark;
    }

    @JsonProperty("symbol")
    public void setPlayerMark(String player_mark) {
        this.player_mark = player_mark;
    }

    // выбор координат x и y
    abstract int[] playerChoice() throws IOException;

}
