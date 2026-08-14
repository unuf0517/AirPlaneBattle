package view.game.Custom;

import controller.win.CustomWinLis;

import javax.swing.*;

public class CustomFrame extends JFrame {

    private CustomPanel customPanel;

    public CustomFrame() {
        initFrame();
        setVisible(true);
        //实例化窗口监听
        CustomWinLis customWinLis = new CustomWinLis();
        //添加窗口监听
        addWindowListener(customWinLis);
    }

    private void initFrame(){
        //设置窗口大小
        setSize(540, 355);
        //设置标题
        setTitle("自定义");
        //设置窗口固定大小
        setResizable(false);
        //设置窗口居中
        setLocationRelativeTo(null);
        //设置窗口关闭操作
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        customPanel = new CustomPanel();
        add(customPanel);
    }

    public CustomPanel getCustomPanel() {
        return customPanel;
    }
}

