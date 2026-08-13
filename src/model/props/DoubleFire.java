package model.props;

import model.plane.HeroPlane;

import javax.swing.*;
import java.awt.*;

public class DoubleFire extends Prop {
    //图片
    private Image doubleFireImage = new ImageIcon(getClass().getResource("/images/game/color/props/doubleFire.png")).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
    ;

    public static final int HEIGHT = 45;

    public DoubleFire(int x, int y) {
        super(x, y);
    }

    @Override
    public void apply(HeroPlane hero) {
        hero.setDoubleFire(true);
        hero.setDoubleFireTime(40);//15x40 6秒
    }

    public void draw(Graphics g) {
        g.drawImage(doubleFireImage, getX(), getY(), null);
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

}