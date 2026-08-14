package controller.action;

import controller.GameController;
import view.GameUI;
import view.game.Custom.CustomPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomActionLis implements ActionListener {
    CustomPanel customPanel;
    public CustomActionLis(CustomPanel customPanel){
        this.customPanel = customPanel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "confirm":
                System.out.println("确认");
                GameController gc = GameController.getInstance();
                //写入更改的数据
                gc.setCustomSpeed(customPanel.getTempSpeedFactor());
                GameController.getInstance().setLevel(customPanel.getTempLevel());
                gc.setSkin(customPanel.getTempSkin());
                GameController.applySkin(customPanel.getTempSkin());
                // 显示游戏窗口
                GameUI.gameFrame.setVisible(true);
                //销毁自定义资源
                Window w = SwingUtilities.getWindowAncestor(customPanel);
                if (w != null) w.dispose();
                break;
            case "cancel":
                System.out.println("取消");
                GameUI.gameFrame.setVisible(true);
                Window w1 = SwingUtilities.getWindowAncestor(customPanel);
                if (w1 != null) w1.dispose();//销毁自定义资源
                break;
        }
    }
}
