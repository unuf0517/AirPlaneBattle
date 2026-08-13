package view.start;

import controller.mouse.StartMouseLis;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    private Image startImage=new ImageIcon(getClass().getResource("/images/start/start.png")).getImage();

    public StartPanel(){
        //实例化鼠标监听
        StartMouseLis startMouseLis=new StartMouseLis();
        //添加鼠标监听
        addMouseListener(startMouseLis);
    }

    /**
     * 设置开始页面图片
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //绘制图片
        g.drawImage(startImage,0,0,341,512,null);
    }
}
