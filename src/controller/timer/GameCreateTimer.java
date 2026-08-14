package controller.timer;

import controller.MusicPlayer;
import model.bullet.BossBullet;
import model.bullet.EnemyBullet;
import model.bullet.HeroBullet;
import model.plane.BossPlane;
import model.plane.HeroPlane;
import model.plane.HighEnemyPlane;
import model.plane.LowEnemyPlane;
import model.props.AtomBomb;
import model.props.Bee;
import model.props.DoubleFire;
import model.props.Prop;
import view.GameUI;
import view.game.GameCenterPanel;

import javax.swing.Timer;
import java.util.Random;

public class GameCreateTimer {
    private Timer spawnTimer;
    private Timer bulletTimer;
    private Timer enemyBulletTimer;
    private Timer bossFireTimer;

    private Random r = new Random();

    public GameCreateTimer(GameCenterPanel gameCenterPanel) {
        spawnTimer = new Timer(1500, e -> {
            int noLow = GameUI.gameFrame.getGameInformationPanel().getNoAppearedLowPlane();
            int noHight = GameUI.gameFrame.getGameInformationPanel().getNoAppearedHightPlane();
            int z = r.nextInt(10) + 1;

            if (z <= 7) {
                int x = r.nextInt(gameCenterPanel.getWidth() - LowEnemyPlane.WIDTH);

                if (noLow > 0 && noHight > 0) {
                    if (r.nextBoolean()) {
                        gameCenterPanel.getHightEnemyPlaneList().add(new HighEnemyPlane(x, -HighEnemyPlane.HEIGHT));
                        GameUI.gameFrame.getGameInformationPanel().appearedPlane();
                        GameUI.gameFrame.getGameInformationPanel().appearedHightPlane();
                    } else {
                        gameCenterPanel.getLowEnemyPlaneList().add(new LowEnemyPlane(x, -LowEnemyPlane.HEIGHT));
                        GameUI.gameFrame.getGameInformationPanel().appearedPlane();
                        GameUI.gameFrame.getGameInformationPanel().appearedLowPlane();
                    }
                } else if (noLow == 0 && noHight > 0) {
                    gameCenterPanel.getHightEnemyPlaneList().add(new HighEnemyPlane(x, -HighEnemyPlane.HEIGHT));
                    GameUI.gameFrame.getGameInformationPanel().appearedPlane();
                    GameUI.gameFrame.getGameInformationPanel().appearedHightPlane();
                } else if (noLow > 0 && noHight == 0) {
                    gameCenterPanel.getLowEnemyPlaneList().add(new LowEnemyPlane(x, -LowEnemyPlane.HEIGHT));
                    GameUI.gameFrame.getGameInformationPanel().appearedPlane();
                    GameUI.gameFrame.getGameInformationPanel().appearedLowPlane();
                }
            } else {
                int x = r.nextInt(gameCenterPanel.getWidth() - Prop.WIDTH);
                int type = r.nextInt(3);

                if (type == 0) {
                    gameCenterPanel.getPropList().add(new Bee(x, -Bee.HEIGHT));
                } else if (type == 1) {
                    gameCenterPanel.getPropList().add(new AtomBomb(x, -AtomBomb.HEIGHT));
                } else {
                    gameCenterPanel.getPropList().add(new DoubleFire(x, -DoubleFire.HEIGHT));
                }
            }

            if (noLow + noHight == 0 && !gameCenterPanel.isBossSpawned()) {
                GameUI.gameFrame.getGameInformationPanel().appearedPlane();
                gameCenterPanel.setBossPlane(new BossPlane(
                        gameCenterPanel.getWidth() / 2 - BossPlane.WIDTH / 2,
                        -BossPlane.HEIGHT
                ));
                gameCenterPanel.setBossSpawned(true);
            }
        });

        bulletTimer = new Timer(170, e -> {
            HeroPlane heroPlane = gameCenterPanel.getHeroPlane();
            if (heroPlane == null) return;

            MusicPlayer.play("/music/fire.wav");

            int x = heroPlane.getX() + HeroPlane.WIDTH / 2 - HeroBullet.WIDTH / 2;
            int y = heroPlane.getY();

            if (heroPlane.isDoubleFire()) {
                gameCenterPanel.getHeroBulletList().add(new HeroBullet(x + 18, y));
                gameCenterPanel.getHeroBulletList().add(new HeroBullet(x - 18, y));
            } else {
                gameCenterPanel.getHeroBulletList().add(new HeroBullet(x, y));
            }
        });

        enemyBulletTimer = new Timer(1800, e -> {
            if (gameCenterPanel.getLowEnemyPlaneList().isEmpty()
                    && gameCenterPanel.getHightEnemyPlaneList().isEmpty()) {
                return;
            }

            for (LowEnemyPlane lep : gameCenterPanel.getLowEnemyPlaneList()) {
                gameCenterPanel.getEnemyBulletList().add(new EnemyBullet(
                        lep.getX() + LowEnemyPlane.WIDTH / 2 - EnemyBullet.WIDTH / 2,
                        lep.getY() + LowEnemyPlane.HEIGHT
                ));
            }

            for (HighEnemyPlane hep : gameCenterPanel.getHightEnemyPlaneList()) {
                gameCenterPanel.getEnemyBulletList().add(new EnemyBullet(
                        hep.getX() + HighEnemyPlane.WIDTH / 2 - EnemyBullet.WIDTH / 2 + 15,
                        hep.getY() + HighEnemyPlane.HEIGHT
                ));
                gameCenterPanel.getEnemyBulletList().add(new EnemyBullet(
                        hep.getX() + HighEnemyPlane.WIDTH / 2 - EnemyBullet.WIDTH / 2 - 15,
                        hep.getY() + HighEnemyPlane.HEIGHT
                ));
            }
        });

        bossFireTimer = new Timer(1000, e -> {
            BossPlane bossPlane = gameCenterPanel.getBossPlane();
            if (bossPlane == null) return;

            int cx = bossPlane.getX() + BossPlane.WIDTH / 2;
            int cy = bossPlane.getY() + BossPlane.HEIGHT - 10;
            int speed = 3;
            int count = 4;
            int spread = 120;

            for (int i = 0; i < count; i++) {
                double deg = -spread / 2.0 + (double) (spread * i) / (count - 1);
                double rad = Math.toRadians(deg);
                int vx = (int) Math.round(speed * Math.sin(rad));
                int vy = (int) Math.round(speed * Math.cos(rad));

                gameCenterPanel.getBossBulletList().add(
                        new BossBullet(cx - BossBullet.WIDTH / 2, cy, vx, vy)
                );
            }
        });
    }

    public void start() {
        spawnTimer.start();
        bulletTimer.start();
        enemyBulletTimer.start();
        bossFireTimer.start();
    }

    public void stop() {
        spawnTimer.stop();
        bulletTimer.stop();
        enemyBulletTimer.stop();
        bossFireTimer.stop();
    }
}