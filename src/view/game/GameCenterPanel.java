package view.game;

import controller.GameController;
import controller.MusicPlayer;
import controller.gameEnum.GameState;
import controller.gameEnum.Skin;
import controller.gameEnum.Type;
import controller.key.GameKeyLis;
import controller.key.MoveKeyLis;
import controller.timer.GameAnimationTimer;
import controller.timer.GameCheckTimer;
import controller.timer.GameCreateTimer;
import controller.timer.GameMoveTimer;
import model.bullet.BossBullet;
import model.bullet.EnemyBullet;
import model.bullet.HeroBullet;
import model.effect.Explosion;
import model.plane.BossPlane;
import model.plane.HeroPlane;
import model.plane.HighEnemyPlane;
import model.plane.LowEnemyPlane;
import model.props.Prop;
import view.GameUI;
import view.game.custom.AboutPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameCenterPanel extends JPanel {
    //游戏待机背景
    private static Image[] backgroundImage = {
            new ImageIcon(GameCenterPanel.class.getResource("/images/game/color/beijing.jpg")).getImage(),
            new ImageIcon(GameCenterPanel.class.getResource("/images/game/gray/beijing.png")).getImage()
    };
    //暂停
    private static Image[] pauseImage = {
            new ImageIcon(GameCenterPanel.class.getResource("/images/game/color/pause.png")).getImage(),
            new ImageIcon(GameCenterPanel.class.getResource("/images/game/gray/pause.png")).getImage()
    };
    //游戏结束
    private static Image[] gameOverImage = {
            new ImageIcon(GameCenterPanel.class.getResource("/images/game/color/gameover.png")).getImage(),
            new ImageIcon(GameCenterPanel.class.getResource("/images/game/gray/gameover.png")).getImage()
    };
    //游戏地图
    private static Image mapImage;
    //彩色游戏地图
    private static final String[] C_MAP={
            "/images/game/color/map/1.jpg",
            "/images/game/color/map/2.jpg",
            "/images/game/color/map/3.jpg",
            "/images/game/color/map/4.jpg",
            "/images/game/color/map/5.jpg",
            "/images/game/color/map/6.jpg",
            "/images/game/color/map/7.jpg",
            "/images/game/color/map/8.jpg",
            "/images/game/color/map/9.jpg",
            "/images/game/color/map/10.jpg",
    };
    //灰色游戏地图
    private static final String[] G_MAP={
            "/images/game/gray/map/1.png"
    };
    //地图滚动偏移量
    private int mapY = 0;
    //每次往下移多少像素
    public static final int SCROLL_SPEED = 1;
    //英雄机
    private HeroPlane heroPlane;
    //英雄机移动键盘监听
    private MoveKeyLis moveKeyLis;
    //核弹键盘监听
    private GameKeyLis gameKeyLis;
    //初级敌机敌机集合
    private List<LowEnemyPlane> lowEnemyPlaneList=new ArrayList<>();
    //高级敌机集合
    private List<HighEnemyPlane> highEnemyPlaneList =new ArrayList<>();
    //爆炸集合
    private List<Explosion> explosionList = new ArrayList<>();
    //boss敌机
    private BossPlane bossPlane;
    //道具集合
    private List<Prop> propList=new ArrayList<>();
    //初级敌机数量
    private int lowPlaneNumber=GameController.getInstance().getLowEnemyMaxNumber();
    //高级敌机数量
    private int hightPlaneNumber=GameController.getInstance().getHighEnemyMaxNumber();
    //boss数量
    private int bossNumber=GameController.bossMaxNumber;
    //英雄机子弹集合
    private List<HeroBullet> heroBulletList=new ArrayList<>();
    //敌机子弹集合
    private List<EnemyBullet> enemyBulletList = new ArrayList<>();
    //boss子弹集合
    private List<BossBullet> bossBulletList = new ArrayList<>();
    //boss是否生成
    private boolean bossSpawned = false;
    //英雄机最后一次爆炸
    private boolean heroDying = false;
    private Explosion heroDeathExplosion = null;
    //倒计时
    private int countdownSeconds;
    //倒计时计时器
    private Timer countdownTimer;
    //帮助面板
    private HelpPanel helpPanel;
    private GameState preHelpState;
    //关于面板
    private AboutPanel aboutPanel;
    private GameState preAboutState;

    private GameMoveTimer gameMoveTimer;
    private GameCreateTimer gameCreateTimer;
    private GameAnimationTimer gameAnimationTimer;
    private GameCheckTimer gameCheckTimer;


    private static final Random r=new Random();

    public GameCenterPanel() {
        setLayout(new BorderLayout());
        //根据主题设计游戏地图
        if(GameController.getInstance().getSkin() == Skin.COLOR){
            mapImage = new ImageIcon(getClass().getResource(C_MAP[r.nextInt(C_MAP.length)])).getImage();
        }else{
            mapImage = new ImageIcon(getClass().getResource(G_MAP[0])).getImage();
        }

        //实例化英雄机移动键盘监听
        moveKeyLis = new MoveKeyLis(this);
        //实例化释放键盘监听
        gameKeyLis = new GameKeyLis(this);
        //聚焦
        setFocusable(true);
        //添加键盘监听
        addKeyListener(moveKeyLis);
        addKeyListener(gameKeyLis);

        helpPanel = new HelpPanel();
        aboutPanel = new AboutPanel();

        JPanel overlayPanel = new JPanel(null);
        overlayPanel.setOpaque(false);
        overlayPanel.add(helpPanel);
        overlayPanel.add(aboutPanel);

        helpPanel.setBounds(0, 0, 400, 600);
        aboutPanel.setBounds(0, 0, 400, 600);

        helpPanel.setVisible(false);
        aboutPanel.setVisible(false);

        add(overlayPanel, BorderLayout.CENTER);

        gameMoveTimer = new GameMoveTimer(this);
        gameCreateTimer = new GameCreateTimer(this);
        gameAnimationTimer = new GameAnimationTimer(this);
        gameCheckTimer = new GameCheckTimer(this);
    }


    public void killHero() {
        if (heroDying || heroPlane == null) return;//防重复触发
        heroDying = true;
        Skin skin = GameController.getInstance().getSkin();
        // 灰色主题+双倍火力用高级英雄机爆炸图其余用普通英雄机爆炸图
        Type t = (skin == Skin.GRAY && heroPlane.isDoubleFire()) ? Type.H_HERO : Type.L_HERO;
        heroDeathExplosion = new Explosion(heroPlane.getX(), heroPlane.getY(), HeroPlane.WIDTH, HeroPlane.HEIGHT, t);
        explosionList.add(heroDeathExplosion);
        onGamePause();
        //只保留爆炸动画定时器继续播放
        gameAnimationTimer.start();
        repaint();
    }

    public void checkWinLose() {
        if (GameController.getInstance().getGameStatus() != GameState.RUNNING) return;
        GameController gc = GameController.getInstance();
        //失败
        if (gc.getOverNumber() > 5) {
            gc.endGame();
            return;
        }
        //过关
        GameInformationPanel gameInformationPanel = GameUI.gameFrame.getGameInformationPanel();
        boolean allSpawned = gameInformationPanel.getNoAppearedLowPlane() == 0 && gameInformationPanel.getNoAppearedHightPlane() == 0;
        boolean noEnemies  = lowEnemyPlaneList.isEmpty() && highEnemyPlaneList.isEmpty();
        boolean bossDone   = (GameController.bossMaxNumber == 0) || (bossSpawned && bossPlane == null);
        if (allSpawned && noEnemies && bossDone) {
            if (gc.getLevel() >= GameController.MAX_LEVEL) {
                showVictory();
            } else {
                gc.setLevel(gc.getLevel()+1);//关数加1
                startCountdown(gc.getLevel());
            }
        }
    }
    private void startCountdown(int nextLevel) {
        MusicPlayer.play("/music/countDown.wav");

        GameController.getInstance().setGameStatus(GameState.COUNTDOWN);
        GameUI.gameFrame.refreshMenuState();
        //暂停游戏
        onGamePause();
        GameUI.gameFrame.getGameInformationPanel().onGamePause();
        GameUI.gameFrame.getGameInformationPanel().getRadarPanel().onGamePause();

        countdownSeconds = 3;
        repaint();
        //倒计时
        countdownTimer = new Timer(1000, e -> {
            countdownSeconds--;
            if (countdownSeconds <= 0) {
                countdownTimer.stop();
                proceedNextLevel();
            } else {
                repaint();
            }
        });
        countdownTimer.start();
    }

    //下一关
    private void proceedNextLevel() {
        GameController gc = GameController.getInstance();
        gc.setOverNumber(0);
        gc.startGame();
    }

    private void showVictory() {
        GameController.getInstance().setGameStatus(GameState.VICTORY);
        onGamePause();
        GameUI.gameFrame.getGameInformationPanel().onGamePause();
        GameUI.gameFrame.getGameInformationPanel().getRadarPanel().onGamePause();

        int score = GameController.getInstance().getScore();
        int res = JOptionPane.showConfirmDialog(GameUI.gameFrame, "恭喜通关！最终得分：" + score, "胜利", JOptionPane.YES_NO_OPTION);
        if (res == 0) {
            GameController.getInstance().restartToInitial();//重新开始游戏
        } else {
            System.exit(0);//直接退出程序
        }
    }

    public void toggleAbout() {
        if (aboutPanel.isVisible()) {
            aboutPanel.setVisible(false);
            requestFocusInWindow();
            if (preAboutState == GameState.RUNNING) {
                GameController.getInstance().continueGame();
            }
        } else {
            helpPanel.setVisible(false);//避免重叠
            preAboutState = GameController.getInstance().getGameStatus();
            if (preAboutState == GameState.RUNNING) {
                GameController.getInstance().pauseGame();
            }
            aboutPanel.setVisible(true);
        }
        repaint();
    }

    public void toggleHelp() {
        if (helpPanel.isVisible()) {
            // 关闭帮助
            helpPanel.setVisible(false);
            requestFocusInWindow();
            // 如果打开前游戏正在运行，就恢复
            if (preHelpState == GameState.RUNNING) {
                GameController.getInstance().continueGame();
            }
        } else {
            // 打开帮助
            aboutPanel.setVisible(false);
            preHelpState = GameController.getInstance().getGameStatus();
            if (preHelpState == GameState.RUNNING) {
                GameController.getInstance().pauseGame();
            }
            helpPanel.setVisible(true);
        }
        repaint();
    }

    public static void reloadMap() {
        if (GameController.getInstance().getSkin() == Skin.COLOR) {
            mapImage = new ImageIcon(GameCenterPanel.class.getResource(C_MAP[r.nextInt(9)+1])).getImage();
        } else {
            mapImage = new ImageIcon(GameCenterPanel.class.getResource(G_MAP[0])).getImage();
        }
    }

    public void onGameStart() {
        bossSpawned = false;
        heroDying = false;
        heroDeathExplosion = null;

        lowPlaneNumber = GameController.getInstance().getLowEnemyMaxNumber();
        hightPlaneNumber = GameController.getInstance().getHighEnemyMaxNumber();
        bossNumber = GameController.bossMaxNumber;

        mapY = 0;//从头开始滚

        //英雄机实例化
        heroPlane =new HeroPlane(getWidth()/2-HeroPlane.WIDTH/2,getHeight()-HeroPlane.HEIGHT);

        //游戏开始时抢回焦点
        requestFocusInWindow();
        repaint();

        // 清空上一关或上一局残留对象
        lowEnemyPlaneList.clear();
        highEnemyPlaneList.clear();
        propList.clear();
        explosionList.clear();
        heroBulletList.clear();
        enemyBulletList.clear();
        bossBulletList.clear();

        gameMoveTimer.start();
        gameCreateTimer.start();
        gameAnimationTimer.start();
        gameCheckTimer.start();
    }

    public void onGamePause() {
        gameMoveTimer.stop();
        gameCreateTimer.stop();
        gameAnimationTimer.stop();
        gameCheckTimer.stop();
    }

    public void onGameContinue() {
        gameMoveTimer.start();
        gameCreateTimer.start();
        gameAnimationTimer.start();
        gameCheckTimer.start();
        requestFocusInWindow();
    }

    public void onGameRestart() {
        onGameEnd();
        // 清空上一局所有实体
        lowEnemyPlaneList.clear();
        highEnemyPlaneList.clear();
        propList.clear();
        bossPlane = null;
        heroPlane = null;
        // 计数归零
        lowPlaneNumber = GameController.getInstance().getLowEnemyMaxNumber();
        hightPlaneNumber = GameController.getInstance().getHighEnemyMaxNumber();
        explosionList.clear();
        heroBulletList.clear();
        enemyBulletList.clear();
        bossBulletList.clear();
        repaint();
    }

    public void onGameEnd() {
        // 停所有 Timer，保留现场让玩家看到死亡画面
        gameMoveTimer.stop();
        gameCreateTimer.stop();
        gameAnimationTimer.stop();
        gameCheckTimer.stop();
    }

    /**
     * @description: 绘制
     * @Param g:
     * @return: void
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //读GameController的状态决定画什么
        if (GameController.getInstance().getGameStatus() == GameState.WAITING) {
            //绘制待机图片
            if(GameController.getInstance().getSkin() == Skin.COLOR){
                g.drawImage(backgroundImage[0], 0, 0, getWidth(), getHeight(), null);
            }else{
                g.drawImage(backgroundImage[1], 0, 0, getWidth(), getHeight(), null);
            }

        }else if(GameController.getInstance().getGameStatus() == GameState.RUNNING){
            //画滚动地图,画两份实现无缝循环
            int imgH = mapImage.getHeight(null);
            //第一份往下偏移mapY
            g.drawImage(mapImage, 0, mapY, getWidth(), imgH, null);
            //第二份紧贴第一份上方，填补空隙
            g.drawImage(mapImage, 0, mapY - imgH, getWidth(), imgH, null);
            //英雄机
            if(heroPlane !=null && !heroDying){
                if (!heroPlane.isInvincible() || heroPlane.isVisible()) {
                    heroPlane.draw(g);
                }
            }
            //低级敌机
            if(lowEnemyPlaneList !=null){
                for(LowEnemyPlane lep:lowEnemyPlaneList) lep.draw(g);
            }
            //高级敌机
            if(highEnemyPlaneList !=null){
                for(HighEnemyPlane hep: highEnemyPlaneList) hep.draw(g);
            }
            //boss
            if(bossPlane !=null){
                bossPlane.draw(g);
                bossPlane.healthBar(g);
            }


            //道具
            if(propList!=null){
                for(Prop pp:propList) pp.draw(g);
            }
            //英雄机子弹
            if (heroBulletList != null) {
                for (HeroBullet b : heroBulletList) b.draw(g);
            }
            //敌机子弹
            if (enemyBulletList != null) {
                for (EnemyBullet b : enemyBulletList) b.draw(g);
            }
            if (bossBulletList !=null){
                for(BossBullet b : bossBulletList) b.draw(g);
            }
            //爆炸
            if(explosionList != null){
                for(Explosion ex:explosionList) ex.draw(g);
            }
        }else if(GameController.getInstance().getGameStatus() == GameState.PAUSE){
            if(GameController.getInstance().getSkin() == Skin.COLOR){
                g.drawImage(pauseImage[0], 0, 0, getWidth(), getHeight(), null);
            }else{
                g.drawImage(pauseImage[1], 0, 0, getWidth(), getHeight(), null);
            }
        }else if(GameController.getInstance().getGameStatus() == GameState.GAME_OVER) {
            if (GameController.getInstance().getSkin() == Skin.COLOR) {
                g.drawImage(gameOverImage[0], 0, 0, getWidth(), getHeight(), null);
            } else {
                g.drawImage(gameOverImage[1], 0, 0, getWidth(), getHeight(), null);
            }
        }else if(GameController.getInstance().getGameStatus() == GameState.COUNTDOWN) {
            if (GameController.getInstance().getGameStatus() == GameState.COUNTDOWN) {
                g.setColor(new Color(0, 0, 0, 160));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.WHITE);
                g.setFont(new Font("微软雅黑", Font.BOLD, 34));
                String text = countdownSeconds + " 秒后进入第 " + GameController.getInstance().getLevel() + " 关";
                int x = (getWidth() - g.getFontMetrics().stringWidth(text)) / 2;
                g.drawString(text, x, getHeight() / 2);
            }
        }
    }

    public HeroPlane getHeroPlane() {
        return heroPlane;
    }

    public List<LowEnemyPlane> getLowEnemyPlaneList() {
        return lowEnemyPlaneList;
    }

    public List<HighEnemyPlane> getHightEnemyPlaneList() {
        return highEnemyPlaneList;
    }

    public void setHightEnemyPlaneList(List<HighEnemyPlane> highEnemyPlaneList) {
        this.highEnemyPlaneList = highEnemyPlaneList;
    }

    public List<Prop> getPropList() {
        return propList;
    }

    public BossPlane getBossPlane() {
        return bossPlane;
    }

    public List<Explosion> getExplosionList() {
        return explosionList;
    }

    public List<HeroBullet> getHeroBulletList() {
        return heroBulletList;
    }

    public List<EnemyBullet> getEnemyBulletList() {
        return enemyBulletList;
    }

    public void setEnemyBulletList(List<EnemyBullet> enemyBulletList) {
        this.enemyBulletList = enemyBulletList;
    }

    public int getBossNumber() {
        return bossNumber;
    }

    public int getHightPlaneNumber() {
        return hightPlaneNumber;
    }

    public void decreaseBoss(){
        bossNumber--;
    }

    public void decreaseHightPlane(int n){
        hightPlaneNumber -= n;
    }

    public void decreaseLowPlane(int n){
        lowPlaneNumber -= n;
    }

    public int getLowPlaneNumber() {
        return lowPlaneNumber;
    }

    public void setBossPlane(BossPlane bossPlane) {
        this.bossPlane = bossPlane;
    }

    public List<BossBullet> getBossBulletList() {
        return bossBulletList;
    }

    public static Image getMapImage() {
        return mapImage;
    }

    public int getMapY() {
        return mapY;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }

    public MoveKeyLis getMoveKeyLis() {
        return moveKeyLis;
    }

    public boolean isBossSpawned() {
        return bossSpawned;
    }

    public void setBossSpawned(boolean bossSpawned) {
        this.bossSpawned = bossSpawned;
    }

    public Explosion getHeroDeathExplosion() {
        return heroDeathExplosion;
    }

    public void setHeroDeathExplosion(Explosion heroDeathExplosion) {
        this.heroDeathExplosion = heroDeathExplosion;
    }
}
