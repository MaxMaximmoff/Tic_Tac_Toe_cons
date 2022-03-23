package com.maximoff.gameplay;

/*
    Step - Класс описывающий шаг игры
*/

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "num",
        "playerId",
        "text"
})
@Generated("me")


public class Step {

    @JsonProperty("num")
    private int num;

    @JsonProperty("playerId")
    private int playerId;

    @JsonProperty("text")
    private int stepValue;

    public Step(){}

    /**
     * @param num
     * @param playerId
     * @param stepValue
     */
    public Step(int num, int playerId, int stepValue) {
        this.num = num;
        this.playerId = playerId;
        this.stepValue = stepValue;
    }

    @JsonProperty("num")
    public int getNum() {
        return num;
    }

    @JsonProperty("num")
    public void setNum(int num) {
        this.num = num;
    }

    @JsonProperty("playerId")
    public int getPlayerId() {
        return playerId;
    }

    @JsonProperty("playerId")
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @JsonProperty("text")
    public int getStepValue() {
        return stepValue;
    }

    @JsonProperty("text")
    public void setStepValue(int stepValue) {
        this.stepValue = stepValue;
    }

    @Override
    public String toString() {
        return "Step [num=" + num + ", playerId=" + playerId + ", stepValue=" + stepValue + "]";
    }
}
