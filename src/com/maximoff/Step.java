package com.maximoff;

/*
    Step - Класс описывающий шаг игры
*/

public class Step {
    private int num;
    private int playerId;
    private int stepValue;

    public Step(){}

    public Step(int num, int playerId, int stepValue) {
        this.num = num;
        this.playerId = playerId;
        this.stepValue = stepValue;
    }

    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getStepValue() {
        return stepValue;
    }
    public void setStepValue(int stepValue) {
        this.stepValue = stepValue;
    }

    @Override
    public String toString() {
        return "Step [num=" + num + ", playerId=" + playerId + ", stepValue=" + stepValue + "]";
    }
}
