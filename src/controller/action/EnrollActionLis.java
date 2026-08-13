package controller.action;

import view.GameUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnrollActionLis implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "enrollButton":
                System.out.println("注册页面点击注册");
                String name=GameUI.enrollFrame.getEnrollPanel().getNameTextField().getText();
                String account= GameUI.enrollFrame.getEnrollPanel().getAccountTextField().getText();
                String password=new String(GameUI.enrollFrame.getEnrollPanel().getPasswordField().getPassword());
                if(name.equals("") || account.equals("") || password.equals("")){
                    JOptionPane.showMessageDialog(null,"请填写完整信息","温馨提示",JOptionPane.PLAIN_MESSAGE);
                }
                int result= dao.UserDAO.enroll(name,account,password);
                if(result==2) {
                    JOptionPane.showMessageDialog(null, "注册成功,返回登陆", "温馨提示", JOptionPane.PLAIN_MESSAGE);
                    GameUI.enrollFrame.setVisible(false);
                    GameUI.loginFrame.setVisible(true);
                }else if(result==3){
                    JOptionPane.showMessageDialog(null,"注册失败","温馨提示",JOptionPane.PLAIN_MESSAGE);
                }else if(result==1) {
                    JOptionPane.showMessageDialog(null, "账号已存在", "温馨提示", JOptionPane.PLAIN_MESSAGE);
                }
                break;
            case "loginButton":
                //登录按钮点击事件
                System.out.println("注册页面点击登录");
                GameUI.enrollFrame.setVisible(false);
                GameUI.loginFrame.setVisible(true);
                break;
        }
    }
}
