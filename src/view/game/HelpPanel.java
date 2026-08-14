package view.game;

import controller.GameController;
import controller.gameEnum.Skin;

import javax.swing.*;
import java.awt.*;

public class HelpPanel extends JPanel {
    private static final String HELP_TEXT =
            "游戏操作说明：\n\n" +
                    "W：向上移动\n" +
                    "S：向下移动\n" +
                    "A：向左移动\n" +
                    "D：向右移动\n" +
                    "H：发射核弹（清屏）\n" +
                    "U:游戏开始\n"+
                    "I:游戏暂停\n"+
                    "O:游戏继续\n\n"+
                    "道具说明：\n" +
                    "蜜蜂：生命值 +1\n" +
                    "核弹：核弹数量 +1\n" +
                    "双倍火力：切换高级皮肤，子弹双发";

    private JTextArea textArea;
    private Image backgroundImage;

    public HelpPanel() {
        setLayout(new BorderLayout());

        textArea = new JTextArea(HELP_TEXT);
        textArea.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        updateTheme();

        add(textArea, BorderLayout.CENTER);
    }

    //根据当前主题设置字体颜色和背景图
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
