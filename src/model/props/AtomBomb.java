package model.props;

import controller.GameController;
import model.plane.HeroPlane;

import javax.swing.*;
import java.awt.*;

public class AtomBomb extends Prop{
    //图片
    private Image atomBombImage=new ImageIcon(getClass().getResource("/images/game/color/props/atomBomb1.png")).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);;
    //核弹宽度
    public static final int HEIGHT = 50;

    public AtomBomb(int x,int y){
        super(x,y);
    }

    @Override
    public void apply(HeroPlane hero) {
        GameController.getInstance().setBombNumber(GameController.getInstance().getBombNumber()+1);
    }

    public void draw(Graphics g){
        g.drawImage(atomBombImage,getX(),getY(),null);
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

}