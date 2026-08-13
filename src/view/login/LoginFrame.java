package view.login;

import javax.swing.*;
import controller.win.WindowLis;

public class LoginFrame extends JFrame {
    private LoginPanel loginPanel=new LoginPanel();

    public LoginFrame(){
        //初始化页面
        initFrame();
        //实例化窗口监听
        WindowLis windowLis=new WindowLis();
        //添加窗口监听
        addWindowListener(windowLis);
        //页面可视化
        setVisible(false);
    }



    private void initFrame(){
        //设置窗口大小
        setSize(340, 450);
        //设置标题
        setTitle("雷霆战机登录");
        //固定窗口大小
        setResizable(false);
        //窗口居中
        setLocationRelativeTo(null);
        //设置窗口关闭操作
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        //添加中间面板
        add(loginPanel);
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public void setLoginPanel(LoginPanel loginPanel) {
        this.loginPanel = loginPanel;
    }
}
