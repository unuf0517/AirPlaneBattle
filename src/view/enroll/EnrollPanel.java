package view.enroll;

import controller.action.EnrollActionLis;
import view.RoundedButton;
import view.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class EnrollPanel extends JPanel {
    //字体样式
    private final Font FONT = new Font("微软雅黑", Font.BOLD, 14);
    //昵称输入框外壳
    private RoundedPanel namePanel=new RoundedPanel();
    //昵称文本框
    private JTextField nameTextField=new JTextField();
    //账号输入框外壳
    private RoundedPanel accountPanel=new RoundedPanel();
    //账号文本框
    private JTextField accountTextField=new JTextField();
    //密码输入框外壳
    private RoundedPanel passwordPanel=new RoundedPanel();
    //密码文本框
    private JPasswordField passwordField=new JPasswordField();
    //注册按钮
    private RoundedButton enrollBtn=new RoundedButton("SIGN UP", 35, new Color(24, 144, 255));
    //登录按钮
    private JButton loginBtn=new JButton("LOGIN");
    //注册页面飞机图片,创建图片实例
    private Image backgroundImage = new ImageIcon(getClass().getResource("/images/loginAndEnroll/enrollbackground.png")).getImage();

    public EnrollPanel(){
        setLayout(null);
        addModule();
    }

    private void addModule(){
        //-----昵称部分-----
        //圆角框的位置和大小
        namePanel.setBounds(52, 190, 220, 35);
        namePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 6));
        //加载图标
        ImageIcon nameImageIcon = new ImageIcon(getClass().getResource("/images/loginAndEnroll/name.png"));
        Image nameImage = nameImageIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        JLabel nameIcon = new JLabel(new ImageIcon(nameImage));
        //账号输入框
        nameTextField.setBorder(BorderFactory.createEmptyBorder());
        nameTextField.setPreferredSize(new Dimension(170, 26));
        nameTextField.setFont(FONT);
        nameTextField.setOpaque(false);

        namePanel.add(nameIcon);
        namePanel.add(nameTextField);
        add(namePanel);

        //-----账号部分-----
        //圆角框的位置和大小
        accountPanel.setBounds(52, 240, 220, 35);
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

        //-----密码部分-----
        passwordPanel.setBounds(52, 290, 220, 35);
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

        //注册按钮
        enrollBtn.setBounds(85, 345, 150, 40);
        enrollBtn.setFont(FONT);
        add(enrollBtn);

        //登录按钮
        loginBtn.setBounds(255, 393, 80, 15);
        loginBtn.setFont(new Font("微软雅黑", Font.BOLD,11));
        //不画背景
        loginBtn.setContentAreaFilled(false);
        //不画边框
        loginBtn.setBorderPainted(false);
        //不画焦点虚线框
        loginBtn.setFocusPainted(false);
        // 蓝色字体
        loginBtn.setForeground(new Color(24, 144, 255));
        //透明
        loginBtn.setOpaque(false);
        add(loginBtn);

        //实例化动作监听
        EnrollActionLis enrollActionLis = new EnrollActionLis();
        //添加注册按钮动作监听
        enrollBtn.addActionListener(enrollActionLis);
        enrollBtn.setActionCommand("enrollButton");
        //添加登录按钮动作监听
        loginBtn.addActionListener(enrollActionLis);
        loginBtn.setActionCommand("loginButton");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //绘制图片
        g.drawImage(backgroundImage,35,25,240,165,null);
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public void setNameTextField(JTextField nameTextField) {
        this.nameTextField = nameTextField;
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
}
