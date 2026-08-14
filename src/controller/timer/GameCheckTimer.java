package controller.timer;

import controller.collision.CollisionDetector;
import view.game.GameCenterPanel;

import javax.swing.Timer;

public class GameCheckTimer {
    private Timer collisionTimer;

    public GameCheckTimer(GameCenterPanel gameCenterPanel) {
        CollisionDetector collisionDetector = new CollisionDetector(gameCenterPanel);
        collisionTimer = new Timer(16, e -> {
            collisionDetector.detect();
            gameCenterPanel.checkWinLose();
        });
    }

    public void start() {
        collisionTimer.start();
    }

    public void stop() {
        collisionTimer.stop();
    }
}