package view.start;

import controller.win.WindowLis;
import view.GameUI;

import javax.swing.*;

public class StartFrame extends JFrame {
    private StartPanel startPanel;

    public StartFrame(){
        //初始化页面
        initFrame();
        //初始化窗口
        initMenu();
        //实例化窗口监听
        WindowLis windowLis=new WindowLis();
        //添加窗口监听
        addWindowListener(windowLis);
        //页面可视化
        setVisible(false);
    }

    private void initMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenuItem backitem = new JMenuItem("返回登录");
        backitem.addActionListener(e -> {
            System.out.println("返回登录");
            GameUI.startFrame.setVisible(false);
            GameUI.loginFrame.setVisible(true);
        });

        menuBar.add(backitem);
        setJMenuBar(menuBar);
    }

    private void initFrame(){
        //设置窗口大小
        setSize(341,542);
        //设置标题
        setTitle("开始游戏");
        //窗口居中
        setLocationRelativeTo(null);
        //设置窗口关闭操作
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        //创建开始面板实例
        startPanel=new StartPanel();
        //添加开始面板实例
        add(startPanel);
    }
}
