package controller.timer;

import controller.GameController;
import model.bullet.BossBullet;
import model.bullet.EnemyBullet;
import model.bullet.HeroBullet;
import model.plane.BossPlane;
import model.plane.HighEnemyPlane;
import model.plane.LowEnemyPlane;
import model.props.Prop;
import view.GameUI;
import view.game.GameCenterPanel;

import javax.swing.Timer;

public class GameMoveTimer {
    private Timer scrollTimer;
    private Timer moveTimer;
    private Timer lowAndHightEnemyTimer;
    private Timer bossTimer;
    private Timer propMoveTimer;
    private Timer bulletMoveTimer;
    private Timer enemyBulletMoveTimer;
    private Timer bossBulletMoveTimer;

    public GameMoveTimer(GameCenterPanel gameCenterPanel) {
        scrollTimer = new Timer(30, e -> {
            gameCenterPanel.setMapY(gameCenterPanel.getMapY() + GameCenterPanel.SCROLL_SPEED);

            if (gameCenterPanel.getMapY() >= GameCenterPanel.getMapImage().getHeight(null)) {
                gameCenterPanel.setMapY(0);
            }

            gameCenterPanel.repaint();
        });

        moveTimer = new Timer(15, e -> {
            gameCenterPanel.getMoveKeyLis().updateMovement();
            gameCenterPanel.repaint();
        });

        lowAndHightEnemyTimer = new Timer(15, e -> {
            int mapH = gameCenterPanel.getHeight();

            for (LowEnemyPlane enp : gameCenterPanel.getLowEnemyPlaneList()) {
                enp.updateMove();
            }

            for (HighEnemyPlane hep : gameCenterPanel.getHightEnemyPlaneList()) {
                hep.updateMove();
            }

            if (gameCenterPanel.getHightEnemyPlaneList().removeIf(hep -> hep.getY() > mapH)) {
                gameCenterPanel.decreaseHightPlane(1);
                GameController.getInstance().increaseOverNumber();
            }

            if (gameCenterPanel.getLowEnemyPlaneList().removeIf(enp -> enp.getY() > mapH)) {
                gameCenterPanel.decreaseLowPlane(1);
                GameController.getInstance().increaseOverNumber();
            }

            gameCenterPanel.repaint();
        });

        bossTimer = new Timer(50, e -> {
            BossPlane bossPlane = gameCenterPanel.getBossPlane();
            if (bossPlane == null) return;

            bossPlane.setX(bossPlane.getX() + bossPlane.getSpeedX());
            bossPlane.updateMove();

            if (bossPlane.getX() >= gameCenterPanel.getWidth() - BossPlane.WIDTH) {
                bossPlane.setX(gameCenterPanel.getWidth() - BossPlane.WIDTH);
                bossPlane.setSpeedX(-Math.abs(bossPlane.getSpeedX()));
            }

            if (bossPlane.getX() <= 0) {
                bossPlane.setX(0);
                bossPlane.setSpeedX(Math.abs(bossPlane.getSpeedX()));
            }
        });

        propMoveTimer = new Timer(30, e -> {
            int mapH = gameCenterPanel.getHeight();
            int mapW = gameCenterPanel.getWidth();

            for (Prop pp : gameCenterPanel.getPropList()) {
                pp.updateMove();
                pp.setX(pp.getX() + pp.getSpeedX());

                if (pp.getX() <= 0) {
                    pp.setX(0);
                    pp.setSpeedX(Math.abs(pp.getSpeedX()));
                }

                if (pp.getX() + Prop.WIDTH >= mapW) {
                    pp.setX(mapW - Prop.WIDTH);
                    pp.setSpeedX(-Math.abs(pp.getSpeedX()));
                }
            }

            gameCenterPanel.getPropList().removeIf(pp -> pp.getY() > mapH);
            gameCenterPanel.repaint();
        });

        bulletMoveTimer = new Timer(15, e -> {
            for (HeroBullet b : gameCenterPanel.getHeroBulletList()) {
                b.updateMove();
            }

            gameCenterPanel.getHeroBulletList().removeIf(b -> b.getY() < -HeroBullet.HEIGHT);
            gameCenterPanel.repaint();
        });

        enemyBulletMoveTimer = new Timer(20, e -> {
            for (EnemyBullet b : gameCenterPanel.getEnemyBulletList()) {
                b.updateMove();
            }

            gameCenterPanel.getEnemyBulletList().removeIf(b -> b.getY() > GameUI.gameFrame.getHeight());
            gameCenterPanel.repaint();
        });

        bossBulletMoveTimer = new Timer(25, e -> {
            int mapW = gameCenterPanel.getWidth();
            int mapH = gameCenterPanel.getHeight();

            for (BossBullet b : gameCenterPanel.getBossBulletList()) {
                b.updateMove();
                b.setX(b.getX() + b.getSpeedX());
                b.nextFrame();

                if (b.getX() <= 0) {
                    b.setSpeedX(Math.abs(b.getSpeedX()));
                    b.setX(0);
                } else if (b.getX() + BossBullet.WIDTH >= mapW) {
                    b.setSpeedX(-Math.abs(b.getSpeedX()));
                    b.setX(mapW - BossBullet.WIDTH);
                }
            }

            gameCenterPanel.getBossBulletList().removeIf(b -> b.getY() > mapH);
            gameCenterPanel.repaint();
        });
    }

    public void start() {
        scrollTimer.start();
        moveTimer.start();
        lowAndHightEnemyTimer.start();
        bossTimer.start();
        propMoveTimer.start();
        bulletMoveTimer.start();
        enemyBulletMoveTimer.start();
        bossBulletMoveTimer.start();
    }

    public void stop() {
        scrollTimer.stop();
        moveTimer.stop();
        lowAndHightEnemyTimer.stop();
        bossTimer.stop();
        propMoveTimer.stop();
        bulletMoveTimer.stop();
        enemyBulletMoveTimer.stop();
        bossBulletMoveTimer.stop();
    }
}
