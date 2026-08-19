package controller.action;

import model.AirplanebattleDO;
import view.GameUI;
import view.game.GameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginActionLis implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "loginButton":
                System.out.println("点击登录");
                String account=GameUI.loginFrame.getLoginPanel().getAccountTextField().getText();
                String password=new String(GameUI.loginFrame.getLoginPanel().getPasswordField().getPassword());


                if(account.equals("") || password.equals("")){
                    JOptionPane.showMessageDialog(null,"请填写完整信息","温馨提示",JOptionPane.PLAIN_MESSAGE);
                }
                AirplanebattleDO airplanebattleDO= dao.UserDAO.login(account,password);
                if (airplanebattleDO != null) {
                    GameUI.loginFrame.setVisible(false);
                    if (GameUI.gameFrame == null){
                        GameUI.gameFrame = new GameFrame();
                    }
                    GameUI.startFrame.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null,"账号或密码错误，请重新输入","温馨提示",JOptionPane.PLAIN_MESSAGE);
                }
                break;
            case "enrollButton":
                System.out.println("点击注册");
                GameUI.loginFrame.setVisible(false);
                GameUI.enrollFrame.setVisible(true);
                break;
        }
    }
}
