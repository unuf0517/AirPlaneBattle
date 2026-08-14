package view.game;

import controller.GameController;
import controller.action.MenuActionLis;
import controller.gameEnum.GameState;
import controller.win.WindowLis;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    //中心面板
    private GameCenterPanel gameCenterPanel;
    //信息面板
    private GameInformationPanel gameInformationPanel;
    //帮助面板
    private HelpPanel helpPanel;
    //开始游戏菜单子项
    private JMenuItem startItem;
    //暂停游戏菜单子项
    private JMenuItem stopItem;
    //继续游戏菜单子项
    private JMenuItem continueItem;
    //重新游戏菜单子项
    private JMenuItem restartItem;
    //定义的菜单子项
    private JMenuItem customItem;

    public GameFrame(){
        //初始化页面
        initFrame();

        //初始化菜单
        initMenu();

        //实例化窗口监听
        WindowLis windowLis = new WindowLis();
        //窗口添加监听
        addWindowListener(windowLis);
        refreshMenuState();
        //页面可视化
        setVisible(true);
    }

    private void initMenu(){
        //创建菜单栏对象
        JMenuBar menuBar = new JMenuBar();
        //创建菜单对象
        JMenu operationMenu = new JMenu("操作");
        JMenu aboutMenu = new JMenu("关于");
        //创建菜单子项
        startItem = new JMenuItem("开始游戏");
        stopItem = new JMenuItem("暂停游戏");
        continueItem=new JMenuItem("继续游戏");
        restartItem = new JMenuItem("重新开始");
        customItem=new JMenuItem("自定义");
        JMenuItem exitItem = new JMenuItem("退出登录");
        JMenuItem helpItem = new JMenuItem("帮助");
        JMenuItem aboutGameItem = new JMenuItem("关于游戏");

        //实例化动作监听
        MenuActionLis menuActionLis=new MenuActionLis();
        //设置菜单动作指令
        startItem.setActionCommand("start");
        stopItem.setActionCommand("stop");
        continueItem.setActionCommand("continue");
        restartItem.setActionCommand("restart");
        customItem.setActionCommand("custom");
        exitItem.setActionCommand("exit");
        helpItem.setActionCommand("help");
        aboutGameItem.setActionCommand("aboutGame");
        //菜单子项添加动作监听
        startItem.addActionListener(menuActionLis);
        stopItem.addActionListener(menuActionLis);
        continueItem.addActionListener(menuActionLis);
        restartItem.addActionListener(menuActionLis);
        customItem.addActionListener(menuActionLis);
        exitItem.addActionListener(menuActionLis);
        helpItem.addActionListener(menuActionLis);
        aboutGameItem.addActionListener(menuActionLis);

        //菜单添加菜单项
        operationMenu.add(startItem);
        operationMenu.add(stopItem);
        operationMenu.add(continueItem);
        operationMenu.add(restartItem);
        operationMenu.add(customItem);
        operationMenu.add(exitItem);
        aboutMenu.add(helpItem);
        aboutMenu.add(aboutGameItem);
        //菜单栏添加菜单
        menuBar.add(operationMenu);
        menuBar.add(aboutMenu);
        //窗口设置菜单栏
        setJMenuBar(menuBar);
    }

    public void refreshMenuState() {
        GameState s = GameController.getInstance().getGameStatus();
        startItem.setEnabled(s == GameState.WAITING);
        stopItem.setEnabled(s == GameState.RUNNING);
        continueItem.setEnabled(s == GameState.PAUSE);
        restartItem.setEnabled(s != GameState.WAITING);
        customItem.setEnabled(s == GameState.WAITING || s == GameState.GAME_OVER || s == GameState.VICTORY);
    }

    private void initFrame(){
        //设置为边界布局
        setLayout(new BorderLayout());
        //设置窗口大小
        setSize(620, 640);
        //设置标题
        setTitle("雷霆战机");
        //设置窗口固定大小
        setResizable(false);
        //设置窗口居中
        setLocationRelativeTo(null);
        //设置窗口关闭操作
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        //创建中心面板实例
        gameCenterPanel = new GameCenterPanel();
        //添加中心面板
        add(gameCenterPanel, BorderLayout.CENTER);
        //创建信息面板实例
        gameInformationPanel = new GameInformationPanel();
        //添加信息面板
        add(gameInformationPanel, BorderLayout.EAST);
        //添加
    }

    public GameCenterPanel getGameCenterPanel() {
        return gameCenterPanel;
    }

    public GameInformationPanel getGameInformationPanel() {
        return gameInformationPanel;
    }


}
