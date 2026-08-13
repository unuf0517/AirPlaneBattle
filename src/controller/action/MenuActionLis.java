package controller.action;

import controller.GameController;
import controller.gameEnum.GameState;
import view.GameUI;
import view.login.LoginFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuActionLis implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "start":
                System.out.println("开始游戏");
                GameController.getInstance().startGame();
                break;
            case "stop":
                System.out.println("暂停游戏");
                GameController.getInstance().pauseGame();
                break;
            case "continue":
                System.out.println("继续游戏");
                GameController.getInstance().continueGame();
                break;
            case "restart":
                System.out.println("重新开始");
                GameController.getInstance().pauseGame();
                int res = JOptionPane.showConfirmDialog(null, "是否重新开始？", "提示", JOptionPane.YES_NO_OPTION);
                System.out.println(res);
                if (res == 0) {
                    GameController.getInstance().restartGame();
                }else{
                    GameController.getInstance().continueGame();
                }
                break;
            case "custom":
                System.out.println("自定义");
                break;
            case "exit":
                System.out.println("退出登录");
                int res2= JOptionPane.showConfirmDialog(null,"是否退出登录？", "提示", JOptionPane.YES_NO_OPTION);
                if(res2 == 0){
                    GameController.getInstance().resetState();
                    new LoginFrame().setVisible(true);
                }
                break;
            case "help":
                System.out.println("帮助");
                break;
            case "aboutGame":
                System.out.println("关于游戏");
                break;
            default:
                System.out.println("未知操作");
        }
    }
}
