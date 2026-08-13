package model.bullet;

import controller.GameController;
import controller.gameEnum.Skin;
import model.GameObject;

import javax.swing.*;
import java.awt.*;

public class EnemyBullet extends GameObject {
    //子弹图片
    private Image bulletImage;
    //子弹的宽高
    public static final int WIDTH = 8;
    public static final int HEIGHT = 15;

    private String C_PATH="/images/game/color/bullet/enemyBullet.png";
    private String G_PATH="/images/game/gray/bullet/enemyBullet.png";

    //伤害
    private int demage=1;

    public EnemyBullet(int x, int y) {
        super(x, y, 3);
        if(GameController.getInstance().getSkin() == Skin.COLOR){
            bulletImage=new ImageIcon(getClass().getResource(C_PATH)).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        }else{
            bulletImage=new ImageIcon(getClass().getResource(G_PATH)).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        }
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
