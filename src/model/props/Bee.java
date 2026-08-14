package model.props;

import controller.MusicPlayer;
import model.plane.HeroPlane;

import javax.swing.*;
import java.awt.*;

public class Bee extends Prop{
    //小蜜蜂图片
    private Image beeImage=new ImageIcon(getClass().getResource("/images/game/color/props/bee.png")).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);;

    public static final int HEIGHT = 25;

    public Bee(int x,int y){
        super(x,y);
    }

    @Override
    public void apply(HeroPlane hero) {
        if(hero.getMaxHp() == hero.getHp() || hero.getHp()==0) return;
        MusicPlayer.play("/music/getHp.wav");
        hero.setHp(hero.getHp()+1);

    }

    public void draw(Graphics g){
        g.drawImage(beeImage,getX(),getY(),null);
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

}
