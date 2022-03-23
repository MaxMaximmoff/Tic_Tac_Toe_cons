package com.maximoff;

/*
    Класс Singleton для класса GameView
*/

public class SingletonGV {

    private static GameView single_instance = null;

    public SingletonGV(){}

    public GameView getInstance()
    {
        if (single_instance == null)
            single_instance = new GameView();
        return single_instance;
    }
}