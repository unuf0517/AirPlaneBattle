package model.plane;

import controller.GameController;
import controller.gameEnum.Skin;
import controller.gameEnum.Type;
import model.effect.Explosion;
import view.GameUI;
import javax.swing.*;
import java.awt.*;

public class HeroPlane extends Plane {

    //碰撞后无敌时间
    private int invincibleTime = 0;
    //是否可见，无敌期间闪烁
    private boolean visible = true;

    //是否双倍火力
    private boolean doubleFire = false;
    //双倍火力剩余时间
    private int doubleFireTime = 0;

    //低级英雄机图片
    private static Image[] images;
    //高级英雄机图片
    private static Image[] hImages;

    //彩色初级英雄机图片
    private static final String[] C_PATH={
            "/images/game/color/plane/hero1.png",
            "/images/game/color/plane/hero2.png"
    };
    //彩色高级英雄机图片
    private static final String[] H_C_PATH={
            "/images/game/color/plane/hHero1.png",
            "/images/game/color/plane/hHero2.png"
    };

    //灰色初级英雄机图片
    private static final String[] G_PATH={
            "/images/game/gray/plane/hero1.png",
            "/images/game/gray/plane/hero2.png"
    };
    //灰色高级英雄机图片
    private static final String[] H_G_PATH={
            "/images/game/gray/plane/hHero1.png",
            "/images/game/gray/plane/hHero2.png"
    };

    public static final int WIDTH = 50;
    public static final int HEIGHT = 70;

    public HeroPlane(int x, int y) {
        super(x, y, 5);
        loadImage();
        this.setMaxHp(3);
    }

    /**
     * 根据索引贴图来做到英雄机动效
     *
     * @param g
     */
    public void draw(Graphics g) {
        if(doubleFire){
            g.drawImage(hImages[currenIndex], getX(), getY(), null);
            return;
        }
        g.drawImage(images[currenIndex], getX(), getY(), null);

    }

    //重置皮肤
    public static void reloadImages() {
        images = null;
        hImages = null;
        loadImage();
    }

    //保存图片
    private static void loadImage() {
        if(GameController.getInstance().getSkin() == Skin.COLOR){
            images = new Image[C_PATH.length];
            hImages = new Image[H_C_PATH.length];
            for(int i=0;i<C_PATH.length;i++){
                images[i] = new ImageIcon(HeroPlane.class.getResource(C_PATH[i])).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
                hImages[i] = new ImageIcon(HeroPlane.class.getResource(H_C_PATH[i])).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
            }
        }else{
            images = new Image[G_PATH.length];
            hImages = new Image[H_G_PATH.length];
            for(int i=0;i<G_PATH.length;i++){
                images[i] = new ImageIcon(HeroPlane.class.getResource(G_PATH[i])).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
                hImages[i] = new ImageIcon(HeroPlane.class.getResource(H_G_PATH[i])).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
            }
        }
    }

    //是否无敌
    public boolean isInvincible() {
        return invincibleTime > 0;
    }

    //无敌时间递减
    public void decreaseInvincibleTime() {
        if (invincibleTime > 0) {
            invincibleTime--;
        }
    }

    @Override
    public void decreaseHp(int damage) {
        if(hp>0){
            hp -= damage;

            if (hp > 0) {
                Skin skin = GameController.getInstance().getSkin();
                Type t = (skin == Skin.GRAY && isDoubleFire()) ? Type.H_HERO : Type.L_HERO;
                GameUI.gameFrame.getGameCenterPanel().getExplosionList().add(new Explosion(getX(), getY(), HeroPlane.WIDTH, HeroPlane.HEIGHT, t));
                //回到初始位置
                setX(GameUI.gameFrame.getGameCenterPanel().getWidth() / 2 - HeroPlane.WIDTH / 2);
                setY(GameUI.gameFrame.getGameCenterPanel().getHeight() - HeroPlane.HEIGHT);
                setInvincibleTime(14);
            }

        }

    }

    public int getInvincibleTime() {
        return invincibleTime;
    }

    public void setInvincibleTime(int invincibleTime) {
        this.invincibleTime = invincibleTime;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean v) {
        visible = v;
    }

    public void toggleVisible() {
        visible = !visible;
    }

    public boolean isDoubleFire() {
        return doubleFire;
    }

    public void setDoubleFire(boolean doubleFire) {
        this.doubleFire = doubleFire;
    }

    public void setDoubleFireTime(int doubleFireTime) {
        this.doubleFireTime = doubleFireTime;
    }

    public int getDoubleFireTime() {
        return doubleFireTime;
    }

    public void decreaseDoubleFireTime() {
        if (doubleFireTime > 0) {
            doubleFireTime--;
        }
    }
}
