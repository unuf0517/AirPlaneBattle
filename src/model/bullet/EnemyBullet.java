package model.bullet;

import controller.GameController;
import controller.gameEnum.Skin;
import model.GameObject;

import javax.swing.*;
import java.awt.*;

public class EnemyBullet extends GameObject {
    //子弹图片
    private static Image bulletImage;
    //子弹的宽高
    public static final int WIDTH = 8;
    public static final int HEIGHT = 15;

    private static String C_PATH="/images/game/color/bullet/enemyBullet.png";
    private static String G_PATH="/images/game/gray/bullet/enemyBullet.png";

    //伤害
    private int demage=1;

    public EnemyBullet(int x, int y) {
        super(x, y, 3);
        loadImage();
    }

    private static void loadImage(){
        if(GameController.getInstance().getSkin() == Skin.COLOR){
            bulletImage=new ImageIcon(EnemyBullet.class.getResource(C_PATH)).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        }else{
            bulletImage=new ImageIcon(EnemyBullet.class.getResource(G_PATH)).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        }
    }

    //重置皮肤
    public static void reloadImages() {
        bulletImage = null;
        loadImage();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(bulletImage, getX(), getY(), null);
    }

    public int getDemage() {
        return demage;
    }

    public void setDemage(int demage) {
        this.demage = demage;
    }
}
