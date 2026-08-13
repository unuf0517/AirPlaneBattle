package view;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private int radius = 25;
    public RoundedPanel() {
        setOpaque(false);
        //水平布局，图标和文字间隔
        setLayout(new FlowLayout(FlowLayout.LEFT,10,8)); //水平布局，图标和文字间隔
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0,0,getWidth()-1,getHeight()-1,radius,radius);
        g2.setColor(new Color(230, 230, 230));
        g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,radius,radius);
        g2.dispose();
        super.paintComponent(g);
    }
}
