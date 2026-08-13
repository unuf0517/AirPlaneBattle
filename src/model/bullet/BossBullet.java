package model.bullet;

import controller.GameController;
import controller.gameEnum.Skin;
import model.GameObject;

import javax.swing.*;
import java.awt.*;

public class BossBullet extends GameObject {
    //子弹图片
    private Image[] bulletImage;
    //子弹的宽高
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    private String[] C_PATH = {
            "/images/game/color/bullet/bossBullet/1.png",
            "/images/game/color/bullet/bossBullet/2.png",
            "/images/game/color/bullet/bossBullet/3.png",
            "/images/game/color/bullet/bossBullet/4.png",
            "/images/game/color/bullet/bossBullet/5.png",
            "/images/game/color/bullet/bossBullet/6.png",
            "/images/game/color/bullet/bossBullet/7.png",
            "/images/game/color/bullet/bossBullet/8.png",
            "/images/game/color/bullet/bossBullet/9.png",
            "/images/game/color/bullet/bossBullet/10.png",
            "/images/game/color/bullet/bossBullet/11.png",
            "/images/game/color/bullet/bossBullet/12.png"
    };
    private String[] G_PATH = {
            "/images/game/gray/bullet/bossBullet/1.png",
            "/images/game/gray/bullet/bossBullet/2.png",
            "/images/game/gray/bullet/bossBullet/3.png",
            "/images/game/gray/bullet/bossBullet/4.png",
            "/images/game/gray/bullet/bossBullet/5.png",
            "/images/game/gray/bullet/bossBullet/6.png",
            "/images/game/gray/bullet/bossBullet/7.png",
            "/images/game/gray/bullet/bossBullet/8.png",
            "/images/game/gray/bullet/bossBullet/9.png",
            "/images/game/gray/bullet/bossBullet/10.png",
            "/images/game/gray/bullet/bossBullet/11.png",
            "/images/game/gray/bullet/bossBullet/12.png"
    };


    //伤害
    private int demage=1;

    private int currentIndex = 1;

    private int speedX;

    public BossBullet(int x, int y,int speedX,int speedY) {
        super(x, y, speedY);
        this.speedX=speedX;
        loadImage();

    }

    private void loadImage(){
        if(GameController.getInstance().getSkin() == Skin.COLOR){
            bulletImage = new Image[C_PATH.length];
            for(int i=0;i<C_PATH.length;i++){
                bulletImage[i] = new ImageIcon(BossBullet.class.getResource(C_PATH[i])).getImage().getScaledInstance(WIDTH,HEIGHT, Image.SCALE_SMOOTH);
            }
        }else{
            bulletImage = new Image[G_PATH.length];
            for(int i=0;i<G_PATH.length;i++){
                bulletImage[i] = new ImageIcon(BossBullet.class.getResource(G_PATH[i])).getImage().getScaledInstance(WIDTH,HEIGHT, Image.SCALE_SMOOTH);
            }
        }
    }


    /**
     * 切换图片
     */
    public void nextFrame() {
        currentIndex = (currentIndex + 1)%12;
    }
    @Override
    public void draw(Graphics g) {
        g.drawImage(bulletImage[currentIndex], getX(), getY(), null);
    }

    public int getDemage() {
        return demage;
    }

    public void setDemage(int demage) {
        this.demage = demage;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
}
