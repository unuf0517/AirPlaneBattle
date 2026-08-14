package model.plane;

import controller.GameController;
import controller.gameEnum.Skin;

import javax.swing.*;
import java.awt.*;

public class BossPlane extends Plane{

    //水平速度
    private int speedX=2;

    public static final int WIDTH = 120;
    public static final int HEIGHT = 160;

    //图片
    private static Image[] images;

    //彩色初级图片
    private static final String[] C_PATH={
            "/images/game/color/plane/boss1_0.png",
            "/images/game/color/plane/boss1_1.png",
            "/images/game/color/plane/boss1_2.png",
            "/images/game/color/plane/boss1_3.png",
            "/images/game/color/plane/boss1_4.png"
    };

    //灰色初级图片
    private static final String[] G_PATH={
            "/images/game/gray/plane/boss.png"
    };

    public BossPlane(int x,int y){
        super(x,y,1);
        loadImage();
        this.setMaxHp(GameController.bossMaxHp);
    }

    /**
     * 根据索引贴图来做到英雄机动效
     * @param g
     */
    public void draw(Graphics g){
        g.drawImage(images[currenIndex], getX(), getY(), null);
    }

    //重置皮肤
    public static void reloadImages() {
        images = null;
        loadImage();
    }

    private static void loadImage() {
        if (GameController.getInstance().getSkin() == Skin.COLOR) {
            images = new Image[C_PATH.length];
            for(int i=0;i<C_PATH.length;i++){
                images[i]=new ImageIcon(LowEnemyPlane.class.getResource(C_PATH[i])).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
            }
        }else{
            images = new Image[G_PATH.length];
            for(int i=0;i<G_PATH.length;i++){
                images[i]=new ImageIcon(LowEnemyPlane.class.getResource(G_PATH[i])).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
            }
        }
    }

    public void healthBar(Graphics g){

        //在boss上方画血条
        int barW = BossPlane.WIDTH-50;
        int barH = 3;
        int barX = getX();
        int barY = getY()-barH-4;

        //防止血条被顶到面板外面
        if (barY < 0) {
            barY = 0;
        }

        //计算当前血量比例
        int maxHp = GameController.getInstance().getBossMaxHp();
        double ratio = (double)getHp()/maxHp;
        if (ratio < 0)ratio = 0;
        int fillW = (int)(barW * ratio);


        g.setColor(Color.RED);
        g.fillRect(barX, barY, fillW, barH);
    }

    /**
     * 刷新图片索引
     */
    public void startAnimation() {
        if(GameController.getInstance().getSkin() == Skin.COLOR){
            currenIndex=(currenIndex+1)%5;
        }else{
            currenIndex=0;
        }

    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
}