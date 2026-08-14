package view.game;

import model.GameObject;
import model.plane.BossPlane;
import model.plane.HeroPlane;
import model.plane.HighEnemyPlane;
import model.plane.LowEnemyPlane;
import view.GameUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RadarPanel extends JPanel {
    //雷达图片集合
    private Image[] radarImages = new Image[30];
    //实例化计时器
    private Timer timer;
    //图片刷新速率
    private static final int FRAME_INTERVAL = 100;
    //当前显示的帧索引
    private int currentIndex = 0;

    public RadarPanel() {
        loadImages();
        startAnimation();
    }

    //保存图片
    private void loadImages() {
        for (int i = 0; i < 30; i++) {
            radarImages[i] = new ImageIcon(getClass().getResource("/images/game/radar/" + (i + 1) + ".jpg")).getImage();
        }
    }

    /**
     * 刷新图片索引
     */
    private void startAnimation() {
        timer = new Timer(FRAME_INTERVAL, e -> {
            //索引+1，到30回0（取模实现循环）
            currentIndex = (currentIndex + 1) % 30;
            repaint();
        });
    }

    /**
     * 画雷达
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //画当前帧，填满整个面板
        g.drawImage(radarImages[currentIndex], 0, 0, getWidth(), getHeight(), null);
        //画敌机预警红点
        drawEnemyDots(g);
    }

    /**
     * 绘制敌机红点
     */
    private void drawEnemyDots(Graphics g) {
        if (GameUI.gameFrame == null) return;

        GameCenterPanel center = GameUI.gameFrame.getGameCenterPanel();
        HeroPlane hero = center.getHeroPlane();
        if (hero == null) return;

        //雷达中心
        int radarCenterX = getWidth()/2;
        int radarCenterY = getHeight()/2;
        //雷达有效半径
        int radarRadius = Math.min(getWidth(),getHeight())/2-6;
        //游戏区宽度作为扫描半径
        int scanRadius = center.getWidth();

        //红点闪烁：和雷达动画同步，亮一帧暗一帧
        if (currentIndex % 2 != 0) return;

        g.setColor(Color.RED);

        List<LowEnemyPlane> lows = center.getLowEnemyPlaneList();
        for (LowEnemyPlane p : lows) {
            drawDot(g,hero, p,radarCenterX,radarCenterY,radarRadius,scanRadius);
        }

        List<HighEnemyPlane> highs = center.getHightEnemyPlaneList();
        for (HighEnemyPlane p : highs) {
            drawDot(g,hero, p,radarCenterX,radarCenterY,radarRadius,scanRadius);
        }

        BossPlane boss = center.getBossPlane();
        if (boss != null) {
            drawDot(g, hero, boss, radarCenterX, radarCenterY, radarRadius, scanRadius);
        }
    }

    /**
     * 画单个红点
     */
    private void drawDot(Graphics g, HeroPlane hero, GameObject enemy,int radarCenterX,int radarCenterY,int radarRadius,int scanRadius) {
        int dx = enemy.getX() - hero.getX();
        int dy = enemy.getY() - hero.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        //只显示在扫描半径内的敌机
        if (distance > scanRadius) return;

        double angle = Math.atan2(dy,dx);
        double scale = distance/scanRadius;

        int dotX = radarCenterX + (int)(Math.cos(angle)*scale*radarRadius);
        int dotY = radarCenterY + (int)(Math.sin(angle)*scale*radarRadius);

        //红点大小 6x6
        g.fillOval(dotX-3,dotY-3,6,6);
    }

    public Timer getTimer() {
        return timer;
    }

    //开始
    public void onGameStart() {
        timer.start();
    }

    //暂停
    public void onGamePause() {
        timer.stop();
    }

    //继续
    public void onGameContinue() {
        timer.start();
    }

    //重新开始
    public void onGameRestart() {
        currentIndex = 1;
        timer.stop();
    }
}

