package com.maximoff;

/*
    Gameplay - Класс подготовки данных для записи в XML
*/

import java.util.ArrayList;

public class Gameplay {

    private Player player1;
    private Player player2;
    private Player winner;
    private ArrayList<Step> steps;


    public Gameplay(ArrayList<Player> players) {
        this.player1 = players.get(0);
        this.player2 = players.get(1);
        steps = new ArrayList<Step>();
    }

    public void addStep(int num, int playerId, int stepValue) {

        Step step = new Step(num, playerId, stepValue);
        steps.add(step);
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Gameplay [player1=" + player1.getPlayerName() + ", player2=" +
                player2.getPlayerName() + ", winner=" + winner.getPlayerName() + "]";
    }
}
