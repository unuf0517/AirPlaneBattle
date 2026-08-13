package controller.mouse;

import view.GameUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartMouseLis extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX()+","+e.getY());

        //根据开始游戏按钮的坐标构建出举行
        Rectangle startRect=new Rectangle(101,386,126,35);
        if(startRect.contains(e.getPoint())){
            GameUI.gameFrame.setVisible(true);
            GameUI.startFrame.setVisible(false);
        }
    }
}
