package view;

import view.enroll.EnrollFrame;
import view.game.GameFrame;
import view.login.LoginFrame;
import view.start.StartFrame;

import javax.swing.*;
import java.awt.*;

public class GameUI {
    public static LoginFrame loginFrame;
    public static EnrollFrame enrollFrame;
    public static GameFrame gameFrame;
    public static StartFrame startFrame;
    // 加载图片
    private Image icon = new ImageIcon(getClass().getResource("/images/gameImage.png")).getImage();

    public GameUI() {
        loginFrame = new LoginFrame();
        enrollFrame = new EnrollFrame();
        gameFrame = new GameFrame();
        startFrame=new StartFrame();
        // 设置窗口图标
        loginFrame.setIconImage(icon);
        enrollFrame.setIconImage(icon);
        gameFrame.setIconImage(icon);
        startFrame.setIconImage(icon);
    }
}
