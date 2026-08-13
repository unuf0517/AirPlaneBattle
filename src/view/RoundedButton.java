package view;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    private int radius;           // 圆角半径
    private Color normalColor;    // 正常颜色
    private Color hoverColor;     // 鼠标悬停颜色
    private Color pressedColor;   // 按下颜色

    public RoundedButton(String text, int radius, Color normalColor) {
        super(text);
        this.radius = radius;
        this.normalColor = normalColor;
        this.hoverColor = normalColor.brighter();  // 比正常色亮一点
        this.pressedColor = normalColor.darker();  // 比正常色暗一点

        // 关掉 JButton 默认的所有绘制
        setContentAreaFilled(false);   // 不画默认背景
        setBorderPainted(false);       // 不画默认边框
        setFocusPainted(false);        // 不画焦点虚线框
        setOpaque(false);              // 设为透明

        // 文字样式
        setForeground(Color.WHITE);
        setFont(new Font("微软雅黑", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 根据按钮状态切换颜色
        if (getModel().isPressed()) {
            g2.setColor(pressedColor);
        } else if (getModel().isRollover()) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(normalColor);
        }

        // 画圆角矩形背景
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();

        // 父类负责画文字
        super.paintComponent(g);
    }
}
