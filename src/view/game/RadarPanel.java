package view.game;

import javax.swing.*;
import java.awt.*;

public class RadarPanel extends JPanel {
    //雷达图片集合
    private Image[] radarImages=new Image[30];
    //实例化计时器
    private Timer timer;
    //图片刷新速率
    private static final int FRAME_INTERVAL=100;
    //当前显示的帧索引
    private int currentIndex = 0;

    public RadarPanel(){
        loadImages();
        startAnimation();
    }

    //保存图片
    private void loadImages() {
        for (int i = 0; i < 30; i++) {
            radarImages[i] = new ImageIcon(getClass().getResource("/images/game/radar/"+ (i+1) + ".jpg")).getImage();
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
        //timer.start();
    }

    /**
     * 图雷达
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //画当前帧，填满整个面板
        g.drawImage(radarImages[currentIndex], 0, 0, getWidth(), getHeight(), null);
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
    public void onGameRestart(){
        currentIndex=1;
        timer.stop();
    }
}
