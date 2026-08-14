package controller;

import controller.gameEnum.GameState;
import controller.gameEnum.Skin;
import model.bullet.BossBullet;
import model.bullet.EnemyBullet;
import model.bullet.HeroBullet;
import model.effect.Explosion;
import model.plane.BossPlane;
import model.plane.HeroPlane;
import model.plane.HighEnemyPlane;
import model.plane.LowEnemyPlane;
import view.GameUI;
import view.game.GameCenterPanel;
import view.game.GameInformationPanel;

public class GameController {
    //游戏状态
    private GameState gameState = GameState.WAITING;
    //游戏配色
    private Skin skin = Skin.COLOR;
    //血量
    public static int lowEnemyMaxHp;
    public static int highEnemyMaxHp;
    public static int bossMaxHp;
    //初级敌机数量
    private int lowEnemyMaxNumber;
    //高级敌机数量
    private int highEnemyMaxNumber;
    //boss数量
    public static final int bossMaxNumber=1;
    //核弹数量
    private int bombNumber;
    //当前关卡
    private int level = 1;
    //总关数
    public static final int MAX_LEVEL = 3;
    //得分
    private int score;
    //敌机越过防线数
    private int overNumber;
    //自定义速度
    private int customSpeedFactor = 5;

    private final static GameController gameController = new GameController();

    public GameController(){
        setLevel(level);
    }


    public void setLevel(int level) {
        this.level = level;
        switch (level) {
            case 1:
                lowEnemyMaxHp = 2;
                highEnemyMaxHp = 3;
                bossMaxHp = 20;

                lowEnemyMaxNumber=20;
                highEnemyMaxNumber=0;
                break;
            case 2:
                lowEnemyMaxHp = 3;
                highEnemyMaxHp = 5;
                bossMaxHp = 30;

                lowEnemyMaxNumber=10;
                highEnemyMaxNumber=10;
                break;
            case 3:
                lowEnemyMaxHp = 4;
                highEnemyMaxHp = 6;
                bossMaxHp = 40;

                lowEnemyMaxNumber=10;
                highEnemyMaxNumber=20;
                break;
            default:
                setLevel(1);
        }
        // 新生成的敌机会用这些值设 maxHp
    }

    public static void applySkin(Skin s){
        GameController.getInstance().setSkin(s);
        HeroPlane.reloadImages();//英雄机
        LowEnemyPlane.reloadImages();//敌机敌机
        HighEnemyPlane.reloadImages();//高级敌机
        BossPlane.reloadImages();//boss
        HeroBullet.reloadImages();//英雄机子弹
        EnemyBullet.reloadImages();//敌机子弹
        BossBullet.reloadImages();//Boss子弹
        Explosion.reloadImages();//爆炸效果
        GameCenterPanel.reloadMap();//游戏中心
        GameInformationPanel.reloadImage();//信息中心图片
    }

    //恢复到未登录状态
    public void resetState() {
        //停所有Timer
        if (GameUI.gameFrame != null) {
            GameUI.gameFrame.getGameCenterPanel().onGamePause();
            GameUI.gameFrame.getGameInformationPanel().getRadarPanel().onGamePause();
        }
        //状态归零
        gameState = GameState.WAITING;
        GameUI.gameFrame.dispose();
        GameUI.gameFrame = null;
    }


    /**
     * 开始游戏
     */
    public void startGame() {
        overNumber = 0;//开始游戏重置个数
        gameState = GameState.RUNNING;
        GameUI.gameFrame.getGameCenterPanel().onGameStart();//地图、飞机
        GameUI.gameFrame.getGameInformationPanel().onGameStart();
        GameUI.gameFrame.refreshMenuState();
    }

    /**
     * 暂停游戏
     */
    public void pauseGame() {
        gameState = GameState.PAUSE;
        GameUI.gameFrame.getGameCenterPanel().onGamePause();
        GameUI.gameFrame.getGameInformationPanel().onGamePause();
        GameUI.gameFrame.refreshMenuState();
    }

    /**
     * 继续游戏
     */
    public void continueGame() {
        gameState = GameState.RUNNING;
        GameUI.gameFrame.getGameCenterPanel().onGameContinue();
        GameUI.gameFrame.getGameInformationPanel().onGameContinue();
        GameUI.gameFrame.refreshMenuState();
    }

    /**
     * 重新游戏
     */
    public void restartGame() {
        gameState = GameState.WAITING;
        GameUI.gameFrame.getGameCenterPanel().onGameRestart();
        GameUI.gameFrame.getGameInformationPanel().onGameRestart();

        GameUI.gameFrame.refreshMenuState();
    }

    /**
     * 结束游戏
     */
    public void endGame() {
        gameState = GameState.GAME_OVER;
        GameUI.gameFrame.getGameCenterPanel().onGameEnd();
        GameUI.gameFrame.getGameInformationPanel().onGamePause();
        GameUI.gameFrame.getGameInformationPanel().getRadarPanel().onGamePause();
        GameUI.gameFrame.refreshMenuState();
        GameUI.gameFrame.getGameInformationPanel().onGamePause();
    }

    //通关后重新开始
    public void restartToInitial() {
        setLevel(1);
        overNumber = 0;
        customSpeedFactor = 5;
        setSkin(Skin.COLOR);
        applySkin(Skin.COLOR);
        restartGame();
    }

    /**
     *
     * @return 返回GameController对象
     */
    public static GameController getInstance() {
        return gameController;
    }

    public GameState getGameStatus() {
        return gameState;
    }

    public void setGameStatus(GameState gameStatus) {
        this.gameState = gameStatus;
    }

    public int getLowEnemyMaxNumber() {
        return lowEnemyMaxNumber;
    }

    public int getHighEnemyMaxNumber() {
        return highEnemyMaxNumber;
    }

    public int getBombNumber() {
        return bombNumber;
    }

    public void setBombNumber(int bombNumber) {
        this.bombNumber = bombNumber;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getOverNumber() {
        return overNumber;
    }

    public void increaseOverNumber() {
        this.overNumber++;
    }

    public void setOverNumber(int n){
        this.overNumber = n;//过关重置
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public int getLevel() {
        return level;
    }

    public static double getCustomSpeed() {
        return 1.0 + (getInstance().customSpeedFactor - 5) * 0.1;
    }

    public int getCustomSpeedFactor(){
        return  customSpeedFactor;
    }

    public void setCustomSpeed(int customSpeed) {
        this.customSpeedFactor = customSpeed;
    }

    public static int getBossMaxHp() {
        return bossMaxHp;
    }
}
