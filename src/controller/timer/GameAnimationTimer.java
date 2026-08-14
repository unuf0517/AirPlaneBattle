package controller.timer;

import controller.GameController;
import controller.MusicPlayer;
import model.effect.Explosion;
import model.plane.BossPlane;
import model.plane.HeroPlane;
import model.plane.HighEnemyPlane;
import model.plane.LowEnemyPlane;
import view.game.GameCenterPanel;

import javax.swing.Timer;

public class GameAnimationTimer {
    private Timer animationTimer;

    public GameAnimationTimer(GameCenterPanel gameCenterPanel) {
        animationTimer = new Timer(150, e -> {
            HeroPlane heroPlane = gameCenterPanel.getHeroPlane();

            if (heroPlane != null) {
                heroPlane.startAnimation();

                if (heroPlane.isInvincible()) {
                    heroPlane.toggleVisible();
                    heroPlane.decreaseInvincibleTime();

                    if (heroPlane.getInvincibleTime() == 0) {
                        heroPlane.setVisible(true);
                    }
                }

                if (heroPlane.isDoubleFire()) {
                    heroPlane.decreaseDoubleFireTime();

                    if (heroPlane.getDoubleFireTime() == 0) {
                        heroPlane.setDoubleFire(false);
                    }
                }
            }

            for (LowEnemyPlane lep : gameCenterPanel.getLowEnemyPlaneList()) {
                lep.startAnimation();
            }

            for (HighEnemyPlane hep : gameCenterPanel.getHightEnemyPlaneList()) {
                hep.startAnimation();
            }

            BossPlane bossPlane = gameCenterPanel.getBossPlane();
            if (bossPlane != null) {
                bossPlane.startAnimation();
            }

            for (Explosion es : gameCenterPanel.getExplosionList()) {
                es.nextFrame();
            }

            gameCenterPanel.getExplosionList().removeIf(Explosion::isFinished);

            Explosion heroDeathExplosion = gameCenterPanel.getHeroDeathExplosion();
            if (heroDeathExplosion != null && heroDeathExplosion.isFinished()) {
                gameCenterPanel.setHeroDeathExplosion(null);
                MusicPlayer.play("/music/gameover.wav");
                GameController.getInstance().endGame();
            }

            gameCenterPanel.repaint();
        });
    }

    public void start() {
        animationTimer.start();
    }

    public void stop() {
        animationTimer.stop();
    }
}