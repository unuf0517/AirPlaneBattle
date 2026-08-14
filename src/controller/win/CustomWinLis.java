package controller.win;

import view.GameUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CustomWinLis extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("关闭自定义窗口");
        GameUI.gameFrame.setVisible(true);
        e.getWindow().dispose();
    }
}
