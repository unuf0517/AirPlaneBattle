package view.enroll;

import controller.win.WindowLis;

import javax.swing.*;

public class EnrollFrame extends JFrame {
    private EnrollPanel enrollPanel=new EnrollPanel();
    public EnrollFrame(){
        //初始化页面
        initFrame();
        //实体化窗口监听
        WindowLis windowLis=new WindowLis();
        //添加窗口监听
        addWindowListener(windowLis);
        setVisible(false);
    }

    /**
     * 初始化页面
     */
    private void initFrame(){
        //设置窗口大小
        setSize(340,450);
        //设置标题
        setTitle("雷霆战机注册");
        //固定窗口大小
        setResizable(false);
        //设置窗口居中
        setLocationRelativeTo(null);
        //设置窗口关闭操作
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        //添加中间面板
        add(enrollPanel);

    }

    public EnrollPanel getEnrollPanel() {
        return enrollPanel;
    }

    public void setEnrollPanel(EnrollPanel enrollPanel) {
        this.enrollPanel = enrollPanel;
    }
}
