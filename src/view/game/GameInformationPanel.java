package view.game;

import controller.GameController;
import controller.Timer.GameInformationTimer;
import controller.gameEnum.Skin;

import javax.swing.*;
import java.awt.*;

public class GameInformationPanel extends JPanel {
    //创建背景图片实例
    private Image backgroundImage = new ImageIcon(getClass().getResource("/images/game/color/InformationImage.jpg")).getImage();
    private static JLabel scoreLabel=new JLabel("得分: *");
    private static JLabel healthLabel=new JLabel("生命值: *");
    private static JLabel levelLabel=new JLabel("当前第 * 关");
    private static JLabel bombNumberLabel=new JLabel("拥有核弹 * 枚");
    private static JLabel overEnemyNumberLabel=new JLabel("越过防线敌机数: * 架");
    private static JLabel enemyNumberLaber=new JLabel("未出现敌机数:");
    private static JLabel lowEnemyNumberLaber=new JLabel("剩余初级敌机:*架");
    private static JLabel highEnemyNumberLaber=new JLabel("剩余高级敌机:*架");
    private static JLabel bossEnemyNumberLaber=new JLabel("敌机boss:*架");
    private static Image lowEnemyImage;
    private static Image highEnemyImage;
    private static Image bossEnemyImage;
    //彩色飞机地址
    private static String[] C_PATH={
            "/images/game/color/plane/enemy1_1.png",
            "/images/game/color/plane/enemy2_1.png",
            "/images/game/color/plane/boss1_1.png"

    };
    //灰色飞机地址
    private static String[] G_PATH={
            "/images/game/gray/plane/enemy1_1.png",
            "/images/game/gray/plane/enemy2_1.png",
            "/images/game/gray/plane/boss.png"
    };
    private final Font FONT = new Font("微软雅黑", Font.PLAIN, 14);

    //未生成的低级敌机数
    private int noAppearedLowPlane = GameController.getInstance().getLowEnemyMaxNumber();
    //未生成的高级敌机数
    private int noAppearedHightPlane = GameController.getInstance().getHighEnemyMaxNumber();
    //未出现的敌机数量
    private int noAppearedPlane= noAppearedLowPlane + noAppearedHightPlane +1;

    //信息面板更新
    private GameInformationTimer gameInformationTimer = new GameInformationTimer();

    //雷达面板
    private RadarPanel radarPanel = new RadarPanel();

    public GameInformationPanel(){
        loadImage();
        //设置为自由布局
        setLayout(null);
        //设置面板大小
        setPreferredSize(new Dimension(220, 620));

        setLabel();

        //雷达面板
        radarPanel.setBounds(35, 430, 150, 150);
        add(radarPanel);
    }

    public void setLabel(){
        //设置固定JLabel位置
        scoreLabel.setBounds(10,10,180,30);
        healthLabel.setBounds(10,40,180,30);
        levelLabel.setBounds(10,70,180,30);
        bombNumberLabel.setBounds(10,100,180,30);
        overEnemyNumberLabel.setBounds(10,130,180,30);
        enemyNumberLaber.setBounds(10,160,180,30);

        lowEnemyNumberLaber.setBounds(80,215,180,30);
        highEnemyNumberLaber.setBounds(80,295,180,30);
        bossEnemyNumberLaber.setBounds(80,375,180,30);
        //设置字体
        scoreLabel.setFont(FONT);
        healthLabel.setFont(FONT);
        levelLabel.setFont(FONT);
        bombNumberLabel.setFont(FONT);
        overEnemyNumberLabel.setFont(FONT);
        enemyNumberLaber.setFont(FONT);
        lowEnemyNumberLaber.setFont(FONT);
        highEnemyNumberLaber.setFont(FONT);
        bossEnemyNumberLaber.setFont(FONT);

        //设置字体颜色
        //根据底色选择字体颜色防止字迹不清
        Color color = GameController.getInstance().getSkin() == Skin.COLOR ? Color.WHITE : Color.BLACK;
        scoreLabel.setForeground(color);
        healthLabel.setForeground(color);
        levelLabel.setForeground(color);
        bombNumberLabel.setForeground(color);
        overEnemyNumberLabel.setForeground(color);
        enemyNumberLaber.setForeground(color);
        lowEnemyNumberLaber.setForeground(color);
        highEnemyNumberLaber.setForeground(color);
        bossEnemyNumberLaber.setForeground(color);

        //添加到面板
        add(scoreLabel);
        add(healthLabel);
        add(levelLabel);
        add(bombNumberLabel);
        add(overEnemyNumberLabel);
        add(enemyNumberLaber);
        add(lowEnemyNumberLaber);
        add(highEnemyNumberLaber);
        add(bossEnemyNumberLaber);
    }

    public static void loadImage(){
        //根据主题色设置图片
        if(GameController.getInstance().getSkin() == Skin.COLOR){
            lowEnemyImage = new ImageIcon((GameInformationPanel.class.getResource(C_PATH[0]))).getImage();
            highEnemyImage = new ImageIcon((GameInformationPanel.class.getResource(C_PATH[1]))).getImage();
            bossEnemyImage = new ImageIcon((GameInformationPanel.class.getResource(C_PATH[2]))).getImage();
        }else{
            lowEnemyImage = new ImageIcon((GameInformationPanel.class.getResource(G_PATH[0]))).getImage();
            highEnemyImage = new ImageIcon((GameInformationPanel.class.getResource(G_PATH[1]))).getImage();
            bossEnemyImage = new ImageIcon((GameInformationPanel.class.getResource(G_PATH[2]))).getImage();
        }
    }
    //更新主题
    public static void reloadImage(){
        loadImage();
        //设置字体颜色
        //根据底色选择字体颜色防止字迹不清
        Color color = GameController.getInstance().getSkin() == Skin.COLOR ? Color.WHITE : Color.BLACK;
        scoreLabel.setForeground(color);
        healthLabel.setForeground(color);
        levelLabel.setForeground(color);
        bombNumberLabel.setForeground(color);
        overEnemyNumberLabel.setForeground(color);
        enemyNumberLaber.setForeground(color);
        lowEnemyNumberLaber.setForeground(color);
        highEnemyNumberLaber.setForeground(color);
        bossEnemyNumberLaber.setForeground(color);
    }


    /**
     * @description: 绘制
     * @Param g:
     * @return: void
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //绘制图片
        if (GameController.getInstance().getSkin() == Skin.GRAY) {
            g.setColor(new Color(238,238, 238));
            g.fillRect(0,0,getWidth(),getHeight());
        } else {
            g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),null);
        }
        g.drawImage(lowEnemyImage,10,215,35,35,null);
        g.drawImage(highEnemyImage,10,290,45,45,null);
        g.drawImage(bossEnemyImage,10,360,55,55,null);

    }



    public void onGameStart(){
        //重置飞机数量计数器
        noAppearedLowPlane = GameController.getInstance().getLowEnemyMaxNumber();
        noAppearedHightPlane = GameController.getInstance().getHighEnemyMaxNumber();
        noAppearedPlane = noAppearedLowPlane + noAppearedHightPlane + 1;

        radarPanel.onGameStart();
        gameInformationTimer.onGameStart();
    }
    //暂停
    public void onGamePause() {
        radarPanel.onGamePause();
        gameInformationTimer.onGamePause();
    }
    //继续
    public void onGameContinue(){
        radarPanel.onGameContinue();
        gameInformationTimer.onGameContinue();
    }
    //重新开始
    public void onGameRestart(){
        radarPanel.onGameRestart();
        gameInformationTimer.onGameRestart();
        //重置飞机数量计数器
        noAppearedLowPlane = GameController.getInstance().getLowEnemyMaxNumber();
        noAppearedHightPlane = GameController.getInstance().getHighEnemyMaxNumber();
        noAppearedPlane = noAppearedLowPlane + noAppearedHightPlane + 1;
    }

    public int getNoAppearedPlane() {
        return noAppearedPlane;
    }

    public void appearedPlane() {
        if(noAppearedPlane>0){
            noAppearedPlane--;
        }
    }

    public int getNoAppearedLowPlane() {
        return noAppearedLowPlane;
    }

    public void appearedLowPlane() {
        if(noAppearedLowPlane > 0){
            noAppearedLowPlane--;
        }
    }

    public int getNoAppearedHightPlane() {
        return noAppearedHightPlane;
    }

    public void appearedHightPlane() {
        if(noAppearedHightPlane > 0){
            noAppearedHightPlane--;
        }
    }

    public RadarPanel getRadarPanel() {
        return radarPanel;
    }

    public void setRadarPanel(RadarPanel radarPanel) {
        this.radarPanel = radarPanel;
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public JLabel getHealthLabel() {
        return healthLabel;
    }

    public JLabel getLevelLabel() {
        return levelLabel;
    }

    public JLabel getBombNumberLabel() {
        return bombNumberLabel;
    }

    public JLabel getOverEnemyNumberLabel() {
        return overEnemyNumberLabel;
    }

    public JLabel getEnemyNumberLaber() {
        return enemyNumberLaber;
    }

    public JLabel getLowEnemyNumberLaber() {
        return lowEnemyNumberLaber;
    }

    public JLabel getHighEnemyNumberLaber() {
        return highEnemyNumberLaber;
    }

    public JLabel getBossEnemyNumberLaber() {
        return bossEnemyNumberLaber;
    }
}
