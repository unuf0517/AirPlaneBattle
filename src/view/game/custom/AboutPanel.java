package view.game.custom;

import controller.GameController;
import controller.gameEnum.Skin;

import javax.swing.*;
import java.awt.*;

public class AboutPanel extends JPanel {
    private JTextArea textArea;
    private Image backgroundImage;

    public AboutPanel() {
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(40, 30, 30, 30));

        // ===== 在这里改你自己的内容 =====
        textArea.setText(
                "游戏名称：雷霆战机\n" +
                        "版本：1.0\n" +
                        "作者：zzx\n" +
                        "版权所有 © 2026\n\n" +
                        "本游戏为课程设计作品，仅供学习交流使用。"
        );

        updateTheme();

        add(textArea, BorderLayout.CENTER);
    }

    public void updateTheme() {
        if (GameController.getInstance().getSkin() == Skin.COLOR) {
            textArea.setForeground(Color.WHITE);
            if (backgroundImage == null) {
                backgroundImage = new ImageIcon(getClass().getResource("/images/game/color/InformationImage.jpg")).getImage();
            }
        } else {
            textArea.setForeground(Color.BLACK);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateTheme();
        if (GameController.getInstance().getSkin() == Skin.COLOR && backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(new Color(238, 238, 238));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
