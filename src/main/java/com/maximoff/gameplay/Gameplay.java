package com.maximoff.gameplay;

/*
    Класс Gameplay для хранения игроков, игры с шагами, результата игры
 */

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Player",
    "Game",
    "GameResult"
})
@Generated("me")


public class Gameplay {

    @JsonProperty("Player")
    private List<Player> players = null;
    @JsonProperty("Game")
    private Game game;
    @JsonProperty("GameResult")
    private GameResult gameResult;

    public Gameplay() {}

    /**
     * @param game
     * @param gameResult
     * @param player
     */
    public Gameplay(List<Player> players, Game game, GameResult gameResult) {
        super();
        this.players = players;
        this.game = game;
        this.gameResult = gameResult;
    }

    @JsonProperty("Player")
    public List<Player> getPlayers() {
        return players;
    }

    @JsonProperty("Player")
    public void setPlayer(List<Player> players) {
        this.players = players;
    }

    @JsonProperty("Game")
    public Game getGame() {
        return game;
    }

    @JsonProperty("Game")
    public void setGame(Game game) {
        this.game = game;
    }

    @JsonProperty("GameResult")
    public GameResult getGameResult() {
        return gameResult;
    }

    @JsonProperty("GameResult")
    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

}
