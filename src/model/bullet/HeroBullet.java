package model.bullet;

import controller.GameController;
import controller.gameEnum.Skin;
import model.GameObject;

import javax.swing.*;
import java.awt.*;

public class HeroBullet extends GameObject {
    //子弹图片
    private Image bulletImage;
    private String C_PATH="/images/game/color/bullet/heroBullet2.png";
    private String G_PATH="/images/game/gray/bullet/heroBullet.png";
    //子弹的宽高
    public static final int WIDTH = 12;
    public static final int HEIGHT = 25;

    //伤害
    private int demage=1;

    public HeroBullet(int x, int y) {
        super(x, y, -10);
        if(GameController.getInstance().getSkin() == Skin.COLOR){
            bulletImage=new ImageIcon(getClass().getResource(C_PATH)).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        }else{
            bulletImage=new ImageIcon(getClass().getResource(G_PATH)).getImage().getScaledInstance(WIDTH-4, HEIGHT-5, Image.SCALE_SMOOTH);
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
