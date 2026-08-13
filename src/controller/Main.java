package controller;

import view.GameUI;

public class Main {
    public static void main(String[] args) {
        new GameUI();
        GameController gameController = GameController.getInstance();
    }
}
