package view.login;

import controller.action.LoginActionLis;
import view.RoundedButton;
import view.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    //字体样式
    private final Font FONT = new Font("微软雅黑", Font.BOLD, 14);
    //账号输入框外壳
    private RoundedPanel accountPanel=new RoundedPanel();
    //账号文本框
    private JTextField accountTextField=new JTextField();
    //密码输入框外壳
    private RoundedPanel passwordPanel=new RoundedPanel();
    //密码文本框
    private JPasswordField passwordField=new JPasswordField();
    //登录按钮
    private RoundedButton loginBtn=new RoundedButton("LOGIN", 35, new Color(24, 144, 255));
    //注册按钮
    private JButton enrollBtn=new JButton("sign up");
    //注册询问标签
    private JLabel askLabel=new JLabel("Don't have an account?");
    //登录页面飞机图片,创建图片实例
    private Image backgroundImage = new ImageIcon(getClass().getResource("/images/loginAndEnroll/loginbackground.png")).getImage();

    public LoginPanel(){
        //设置自由布局
        setLayout(null);

        addModule();

    }


    private void addModule(){

        //账号部分
        //圆角框的位置和大小
        accountPanel.setBounds(52, 215, 220, 35);
        accountPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 6));
        //加载图标
        ImageIcon accountImageIcon = new ImageIcon(getClass().getResource("/images/loginAndEnroll/account.png"));
        Image accountImage = accountImageIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        JLabel accountIcon = new JLabel(new ImageIcon(accountImage));
        //账号输入框
        accountTextField.setBorder(BorderFactory.createEmptyBorder());
        accountTextField.setPreferredSize(new Dimension(170, 26));
        accountTextField.setFont(FONT);
        accountTextField.setOpaque(false);

        accountPanel.add(accountIcon);
        accountPanel.add(accountTextField);
        add(accountPanel);

        //密码部分
        passwordPanel.setBounds(52, 270, 220, 35);
        passwordPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 6));
        //加载密码图标
        ImageIcon pwdImageIcon = new ImageIcon(getClass().getResource("/images/loginAndEnroll/pwd.png"));
        Image pwdImage = pwdImageIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        JLabel passwordIcon = new JLabel(new ImageIcon(pwdImage));

        //密码输入框
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        passwordField.setPreferredSize(new Dimension(170, 26));
        passwordField.setFont(FONT);
        passwordField.setOpaque(false);

        passwordPanel.add(passwordIcon);
        passwordPanel.add(passwordField);
        add(passwordPanel);

        //登录按钮
        loginBtn.setBounds(85, 330, 150, 40);
        loginBtn.setFont(FONT);
        add(loginBtn);
        add(enrollBtn);
        //注册按钮
        enrollBtn.setBounds(190, 384, 80, 15);
        enrollBtn.setFont(new Font("微软雅黑", Font.BOLD,11));

        //不画背景
        enrollBtn.setContentAreaFilled(false);
        //不画边框
        enrollBtn.setBorderPainted(false);
        //不画焦点虚线框
        enrollBtn.setFocusPainted(false);
        // 蓝色字体
        enrollBtn.setForeground(new Color(24, 144, 255));
        //透明
        enrollBtn.setOpaque(false);

        //实例化动作监听
        LoginActionLis loginActionLis = new LoginActionLis();
        //添加登录按钮动作监听
        loginBtn.addActionListener(loginActionLis);
        loginBtn.setActionCommand("loginButton");
        //添加注册按钮动作监听
        enrollBtn.addActionListener(loginActionLis);
        enrollBtn.setActionCommand("enrollButton");

        //询问标签
        askLabel.setBounds(65,380,124,25);
        askLabel.setFont(new Font("微软雅黑", Font.PLAIN, 11));
        add(askLabel);


    }

    /**
     * 设置注册页面图片
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //绘制图片
        g.drawImage(backgroundImage,35,30,240,165,null);
    }

    public RoundedPanel getAccountPanel() {
        return accountPanel;
    }

    public void setAccountPanel(RoundedPanel accountPanel) {
        this.accountPanel = accountPanel;
    }

    public JTextField getAccountTextField() {
        return accountTextField;
    }

    public void setAccountTextField(JTextField accountTextField) {
        this.accountTextField = accountTextField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public RoundedButton getLoginBtn() {
        return loginBtn;
    }

    public void setLoginBtn(RoundedButton loginBtn) {
        this.loginBtn = loginBtn;
    }

    public JButton getEnrollBtn() {
        return enrollBtn;
    }

    public void setEnrollBtn(JButton enrollBtn) {
        this.enrollBtn = enrollBtn;
    }
}
