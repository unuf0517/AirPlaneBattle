package controller.Timer;

import controller.GameController;
import view.GameUI;

import javax.swing.*;

public class GameInformationTimer {
    private Timer gameInformationTimer;

    public GameInformationTimer(){
        gameInformationTimer = new Timer(500, e -> {
            //得分
            int score = GameController.getInstance().getScore();
            GameUI.gameFrame.getGameInformationPanel().getScoreLabel().setText("得分: "+score);
            //生命值
            int up=GameUI.gameFrame.getGameCenterPanel().getHeroPlane().getHp();
            GameUI.gameFrame.getGameInformationPanel().getHealthLabel().setText("生命值: "+up);
            //关卡
            int level = GameController.getInstance().getLevel();
            GameUI.gameFrame.getGameInformationPanel().getLevelLabel().setText("当前第 "+level+" 关");
            //核弹数
            int bombNumber = GameController.getInstance().getBombNumber();
            GameUI.gameFrame.getGameInformationPanel().getBombNumberLabel().setText("拥有核弹 "+bombNumber+" 枚");
            //越过防线敌机数
            int overNumber=GameController.getInstance().getOverNumber();
            GameUI.gameFrame.getGameInformationPanel().getOverEnemyNumberLabel().setText("越过防线敌机数: "+overNumber+" 架");
            //未出现的敌机数
            int noAppearedPlane = GameUI.gameFrame.getGameInformationPanel().getNoAppearedPlane();
            GameUI.gameFrame.getGameInformationPanel().getEnemyNumberLaber().setText("未出现敌机数:"+noAppearedPlane+"架");
            //低级敌机数
            int lowPlaneNumber = GameUI.gameFrame.getGameCenterPanel().getLowPlaneNumber();
            GameUI.gameFrame.getGameInformationPanel().getLowEnemyNumberLaber().setText("剩余初级敌机:"+lowPlaneNumber+"架");
            //高级敌机数
            int hightPlaneNumber = GameUI.gameFrame.getGameCenterPanel().getHightPlaneNumber();
            GameUI.gameFrame.getGameInformationPanel().getHighEnemyNumberLaber().setText("剩余高级敌机:"+hightPlaneNumber+"架");
            //boss数
            int bossNumber = GameUI.gameFrame.getGameCenterPanel().getBossNumber();
            GameUI.gameFrame.getGameInformationPanel().getBossEnemyNumberLaber().setText("敌机boss:"+bossNumber+"架");
            //属性页面
            GameUI.gameFrame.getGameCenterPanel().repaint();
        });
    }

    public void onGameStart(){
        gameInformationTimer.start();
    }
    //暂停
    public void onGamePause() {
        gameInformationTimer.stop();
    }
    //继续
    public void onGameContinue() {
        gameInformationTimer.start();
    }
    //重新开始
    public void onGameRestart(){
        gameInformationTimer.stop();
    }
}
