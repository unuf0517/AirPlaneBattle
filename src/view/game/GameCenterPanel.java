package view.game;

import controller.GameController;
import controller.collision.CollisionDetector;
import controller.gameEnum.GameState;
import controller.gameEnum.Skin;
import controller.gameEnum.Type;
import controller.key.GameKeyLis;
import controller.key.MoveKeyLis;
import model.bullet.BossBullet;
import model.bullet.EnemyBullet;
import model.bullet.HeroBullet;
import model.effect.Explosion;
import model.plane.BossPlane;
import model.plane.HeroPlane;
import model.plane.HighEnemyPlane;
import model.plane.LowEnemyPlane;
import model.props.AtomBomb;
import model.props.Bee;
import model.props.DoubleFire;
import model.props.Prop;
import view.GameUI;
import view.game.Custom.AboutPanel;

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
    private static final int SCROLL_SPEED = 1;
    //滚动定时器
    private Timer scrollTimer;
    //英雄机动效
    private Timer animationTimer;
    //英雄机移动定时器
    private Timer moveTimer;
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
    //初高级敌机移动
    private Timer lowAndHightEnemyTimer;
    //敌机、道具生成
    private Timer spawnTimer;
    //boss敌机移动
    private Timer bossTimer;
    //道具移动
    private Timer propMoveTimer;
    //道具集合
    private List<Prop> propList=new ArrayList<>();
    //碰撞检测计数器
    private Timer collisionTimer;
    //初级敌机数量
    private int lowPlaneNumber=GameController.getInstance().getLowEnemyMaxNumber();
    //高级敌机数量
    private int hightPlaneNumber=GameController.getInstance().getHighEnemyMaxNumber();
    //boss数量
    private int bossNumber=GameController.bossMaxNumber;
    //英雄机子弹集合
    private List<HeroBullet> heroBulletList=new ArrayList<>();
    //英雄机子弹发射计时器
    private Timer bulletTimer;
    //英雄机子弹移动计时器
    private Timer bulletMoveTimer;
    //敌机子弹集合
    private List<EnemyBullet> enemyBulletList = new ArrayList<>();
    //boss子弹集合
    private List<BossBullet> bossBulletList = new ArrayList<>();
    //boss子弹发射计时器
    private Timer bossFireTimer;
    //boss子弹计时器
    private Timer bossBulletMoveTimer;
    //敌机子弹发射计时器
    private Timer enemyBulletTimer;
    //敌机子弹移动计时器
    private Timer enemyBulletMoveTimer;
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


    private static final Random r=new Random();

    public GameCenterPanel() {
        setLayout(new BorderLayout());
        //根据主题设计游戏地图
        if(GameController.getInstance().getSkin() == Skin.COLOR){
            mapImage = new ImageIcon(getClass().getResource(C_MAP[r.nextInt(C_MAP.length)])).getImage();
        }else{
            mapImage = new ImageIcon(getClass().getResource(G_MAP[0])).getImage();
        }
        //地图滚动
        scrollTimer = new Timer(30, e -> {
            mapY += SCROLL_SPEED;
            //滚够一张图的高度就归零，实现无缝循环
            if (mapY >= mapImage.getHeight(null)) {
                mapY = 0;
            }
            repaint();
        });

        //飞机动效
        animationTimer=new Timer(150, e ->{
            if (heroPlane != null) {
                heroPlane.startAnimation();
                if(heroPlane.isInvincible()){//是否处于无敌状态
                    heroPlane.toggleVisible();//切换可见性达到闪烁效果
                    heroPlane.decreaseInvincibleTime();
                    if (heroPlane.getInvincibleTime() == 0) {
                        heroPlane.setVisible(true);//无敌结束确保恢复可见
                    }
                }
                if(heroPlane.isDoubleFire()){
                    heroPlane.decreaseDoubleFireTime();
                    if(heroPlane.getDoubleFireTime() == 0){
                        heroPlane.setDoubleFire(false);
                    }
                }
            }
            for (LowEnemyPlane lep : lowEnemyPlaneList) lep.startAnimation();
            for (HighEnemyPlane hep : highEnemyPlaneList) hep.startAnimation();
            if (bossPlane !=null) bossPlane.startAnimation();
            for (Explosion es : explosionList) es.nextFrame();
            explosionList.removeIf(Explosion::isFinished);//播放完爆炸图片就释放
            if (heroDeathExplosion != null && heroDeathExplosion.isFinished()) {
                heroDeathExplosion = null;
                GameController.getInstance().endGame();
            }
            repaint();
        });

        //实例化英雄机移动键盘监听
        moveKeyLis = new MoveKeyLis(this);
        //实例化释放键盘监听
        gameKeyLis = new GameKeyLis(this);
        //聚焦
        setFocusable(true);
        //添加键盘监听
        addKeyListener(moveKeyLis);
        addKeyListener(gameKeyLis);

        //更新英雄机移动
        moveTimer=new Timer(15,e->{
            moveKeyLis.updateMovement();
            repaint();
        });

        //初高级敌机移动
        lowAndHightEnemyTimer = new Timer(15, e -> {
            int mapH = getHeight();
            for (LowEnemyPlane enp : lowEnemyPlaneList) {
                enp.updateMove();//下落
            }
            for (HighEnemyPlane hep : highEnemyPlaneList){
                hep.updateMove();
            }

            //超过边界删除并且记录
            if(highEnemyPlaneList.removeIf(hep -> hep.getY() > mapH)){
                hightPlaneNumber--;
                GameController.getInstance().increaseOverNumber();
            }
            if(lowEnemyPlaneList.removeIf(enp -> enp.getY() > mapH)){
                lowPlaneNumber--;
                GameController.getInstance().increaseOverNumber();
            }
            repaint();
        });

        //初级高级敌机、道具生成
        spawnTimer = new Timer(1500, e -> {
            int noLow = GameUI.gameFrame.getGameInformationPanel().getNoAppearedLowPlane();
            int noHight = GameUI.gameFrame.getGameInformationPanel().getNoAppearedHightPlane();
            int z = r.nextInt(10)+1;
            if(z<=7){//70%的概率生成敌机
                int x = r.nextInt(getWidth()-LowEnemyPlane.WIDTH);//随机横坐标
                if(noLow>0 && noHight>0){
                    if(r.nextBoolean()){
                        highEnemyPlaneList.add(new HighEnemyPlane(x,-HighEnemyPlane.HEIGHT));
                        GameUI.gameFrame.getGameInformationPanel().appearedPlane();
                        GameUI.gameFrame.getGameInformationPanel().appearedHightPlane();
                    }else{
                        lowEnemyPlaneList.add(new LowEnemyPlane(x, -LowEnemyPlane.HEIGHT));//从屏幕上方外生成
                        GameUI.gameFrame.getGameInformationPanel().appearedPlane();
                        GameUI.gameFrame.getGameInformationPanel().appearedLowPlane();
                    }
                }else if(noLow == 0 && (noHight > 0)){
                    highEnemyPlaneList.add(new HighEnemyPlane(x,-HighEnemyPlane.HEIGHT));
                    GameUI.gameFrame.getGameInformationPanel().appearedPlane();
                    GameUI.gameFrame.getGameInformationPanel().appearedHightPlane();
                }else if(noLow>0 && noHight==0){
                    lowEnemyPlaneList.add(new LowEnemyPlane(x, -LowEnemyPlane.HEIGHT));//从屏幕上方外生成
                    GameUI.gameFrame.getGameInformationPanel().appearedPlane();
                    GameUI.gameFrame.getGameInformationPanel().appearedLowPlane();
                }
            }else{
                int x= r.nextInt(getWidth()-Prop.WIDTH);
                propList.add(switch (r.nextInt(3)){
                    case 0 -> new Bee(x,-Bee.HEIGHT);
                    case 1 -> new AtomBomb(x,-AtomBomb.HEIGHT);
                    default -> new DoubleFire(x,-DoubleFire.HEIGHT);
                });
            }

            if(noLow + noHight ==0 && !bossSpawned){
                //实例化boss
                GameUI.gameFrame.getGameInformationPanel().appearedPlane();
                bossPlane = new BossPlane(getWidth()/2-BossPlane.WIDTH/2,-BossPlane.HEIGHT);
                bossSpawned = true;
            }
        });

        //-----boss-----
        //移动
        bossTimer = new Timer(30,e -> {
            if(bossPlane==null) return;

           bossPlane.setX(bossPlane.getX() + bossPlane.getSpeedX());
           bossPlane.updateMove();
           if(bossPlane.getX() >= getWidth() - BossPlane.WIDTH){
               bossPlane.setX(getWidth() - BossPlane.WIDTH);
               bossPlane.setSpeedX(- Math.abs(bossPlane.getSpeedX()));
           }
           if(bossPlane.getX() <= 0){
               bossPlane.setX(0);
               bossPlane.setSpeedX(Math.abs(bossPlane.getSpeedX()));
           }
        });

        //道具移动
        propMoveTimer=new Timer(30,e->{
            int mapH=getHeight();
            int mapW=getWidth();
            for(Prop pp:propList){
                pp.updateMove(); //向下降落
                pp.setX(pp.getX()+pp.getSpeedX());
                if (pp.getX() <= 0) {
                    pp.setX(0);                              // 修正到边界内
                    pp.setSpeedX(Math.abs(pp.getSpeedX()));  // 强制向右
                }
                if (pp.getX()+Prop.WIDTH>=mapW) {
                    pp.setX(mapW-Prop.WIDTH);            // 修正到边界内
                    pp.setSpeedX(-Math.abs(pp.getSpeedX()));  // 强制向左
                }
            }
            propList.removeIf(pp -> pp.getY() > mapH);
            repaint();
        });
        //实例化碰撞检测对象
        CollisionDetector collisionDetector=new CollisionDetector(this);
        collisionTimer = new Timer(16, e -> {
            collisionDetector.detect();
            checkWinLose();
        });
        //英雄机子弹生成
        bulletTimer=new Timer(170,e->{
            if(heroPlane==null) return;
            int x=heroPlane.getX()+HeroPlane.WIDTH/2-HeroBullet.WIDTH/2;
            int y=heroPlane.getY();
            if(heroPlane.isDoubleFire()){
                heroBulletList.add(new HeroBullet(x+18,y));
                heroBulletList.add(new HeroBullet(x-18,y));
            }else{
                heroBulletList.add(new HeroBullet(x,y));
            }
        });
        //英雄机子弹移动
        bulletMoveTimer=new Timer(15,e->{
            for(HeroBullet b : heroBulletList) {
                b.updateMove();
            }
            heroBulletList.removeIf(b -> b.getY() < -HeroBullet.HEIGHT);
            repaint();
        });
        //敌机子弹发射
        enemyBulletTimer=new Timer(1800,e->{
            if(lowEnemyPlaneList.isEmpty() && highEnemyPlaneList.isEmpty()) return;
            if(!lowEnemyPlaneList.isEmpty()){
                for(LowEnemyPlane lep:lowEnemyPlaneList){
                    enemyBulletList.add(new EnemyBullet(lep.getX()+LowEnemyPlane.WIDTH/2-EnemyBullet.WIDTH/2,lep.getY()+LowEnemyPlane.HEIGHT));
                }
            }
            if(!highEnemyPlaneList.isEmpty()){
                for(HighEnemyPlane hep : highEnemyPlaneList){
                    enemyBulletList.add(new EnemyBullet(hep.getX()+ HighEnemyPlane.WIDTH/2-EnemyBullet.WIDTH/2+15,hep.getY()+ HighEnemyPlane.HEIGHT));
                    enemyBulletList.add(new EnemyBullet(hep.getX()+ HighEnemyPlane.WIDTH/2-EnemyBullet.WIDTH/2-15,hep.getY()+ HighEnemyPlane.HEIGHT));
                }
            }
        });
        //敌机子弹移动
        enemyBulletMoveTimer=new Timer(20,e->{
            for (EnemyBullet b : enemyBulletList) {
                b.updateMove();
            }
            enemyBulletList.removeIf(b -> b.getY() > GameUI.gameFrame.getHeight());
            repaint();
        });
        //boss子弹生成
        bossFireTimer=new Timer(1000, e ->{
            if (bossPlane == null) return;

            int cx = bossPlane.getX() + BossPlane.WIDTH / 2;
            int cy = bossPlane.getY() + BossPlane.HEIGHT - 10;
            int speed = 3, count = 4, spread = 120;

            for (int i = 0; i < count; i++) {
                double deg = -spread / 2.0 + (double) (spread * i) / (count - 1);
                double rad = Math.toRadians(deg);
                int vx = (int) Math.round(speed * Math.sin(rad));
                int vy = (int) Math.round(speed * Math.cos(rad));
                bossBulletList.add(new BossBullet(cx - BossBullet.WIDTH / 2, cy, vx, vy));
            }

        });
        //boss子弹移动
        bossBulletMoveTimer=new Timer(25,e -> {
            int mapW = getWidth();
            int mapH = getHeight();
            for (BossBullet b : bossBulletList) {
                b.updateMove();
                b.setX(b.getX() + b.getSpeedX());
                b.nextFrame();
                // 弹墙反弹
                if (b.getX() <= 0) {
                    b.setSpeedX(Math.abs(b.getSpeedX()));   // 强制向右
                    b.setX(0);
                } else if (b.getX() + BossBullet.WIDTH >= mapW) {
                    b.setSpeedX(-Math.abs(b.getSpeedX()));  // 强制向左
                    b.setX(mapW - BossBullet.WIDTH);
                }
            }
            bossBulletList.removeIf(b -> b.getY() > mapH);
            repaint();
        });

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
        animationTimer.start();
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
        //雷达
        scrollTimer.start();
        //英雄机实例化
        heroPlane =new HeroPlane(getWidth()/2-HeroPlane.WIDTH/2,getHeight()-HeroPlane.HEIGHT);
        //所有飞机动效
        animationTimer.start();
        //英雄机移动
        moveTimer.start();
        //游戏开始时抢回焦点
        requestFocusInWindow();
        repaint();
        //初高级敌机生成
        spawnTimer.start();
        //初高敌机移动
        lowAndHightEnemyTimer.start();
        //boss移动
        bossTimer.start();
        //清掉上一局的残留
        lowEnemyPlaneList.clear();//初级敌机
        highEnemyPlaneList.clear(); //高级敌机
        propList.clear();//道具
        explosionList.clear();//爆炸特效
        heroBulletList.clear();//英雄机子弹
        enemyBulletList.clear();//敌机子弹
        bossBulletList.clear();//boss子弹
        bossPlane = null;
        //道具移动
        propMoveTimer.start();
        //碰撞检测
        collisionTimer.start();
        //英雄机子弹
        bulletTimer.start();
        bulletMoveTimer.start();
        //敌机子弹
        enemyBulletTimer.start();
        enemyBulletMoveTimer.start();
        bossFireTimer.start();
        bossBulletMoveTimer.start();
    }

    public void onGamePause() {
        scrollTimer.stop();
        animationTimer.stop();
        moveTimer.stop();
        lowAndHightEnemyTimer.stop();
        spawnTimer.stop();
        bossTimer.stop();
        propMoveTimer.stop();
        collisionTimer.stop();
        bulletTimer.stop();
        bulletMoveTimer.stop();
        enemyBulletTimer.stop();
        enemyBulletMoveTimer.stop();
        bossFireTimer.stop();
        bossBulletMoveTimer.stop();
    }

    public void onGameContinue() {
        scrollTimer.start();
        animationTimer.start();
        moveTimer.start();
        spawnTimer.start();
        lowAndHightEnemyTimer.start();
        bossTimer.start();
        propMoveTimer.start();
        collisionTimer.start();
        bulletTimer.start();
        bulletMoveTimer.start();
        enemyBulletTimer.start();
        enemyBulletMoveTimer.start();
        bossFireTimer.start();
        bossBulletMoveTimer.start();
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
        scrollTimer.stop();
        animationTimer.stop();
        moveTimer.stop();
        lowAndHightEnemyTimer.stop();
        spawnTimer.stop();
        bossTimer.stop();
        propMoveTimer.stop();
        collisionTimer.stop();
        bulletTimer.stop();
        bulletMoveTimer.stop();
        enemyBulletTimer.stop();
        enemyBulletMoveTimer.stop();
        bossFireTimer.stop();
        bossBulletMoveTimer.stop();
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
            if(bossPlane !=null) bossPlane.draw(g);
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

    public void setHeroPlane(HeroPlane heroPlane) {
        this.heroPlane = heroPlane;
    }

    public List<LowEnemyPlane> getLowEnemyPlaneList() {
        return lowEnemyPlaneList;
    }

    public void setLowEnemyPlaneList(List<LowEnemyPlane> lowEnemyPlaneList) {
        this.lowEnemyPlaneList = lowEnemyPlaneList;
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

    public void setPropList(List<Prop> propList) {
        this.propList = propList;
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

    public void setHeroBulletList(List<HeroBullet> heroBulletList) {
        this.heroBulletList = heroBulletList;
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

    public void setBossNumber(int bossNumber) {
        this.bossNumber = bossNumber;
    }

    public int getHightPlaneNumber() {
        return hightPlaneNumber;
    }

    public void setHightPlaneNumber(int hightPlaneNumber) {
        this.hightPlaneNumber = hightPlaneNumber;
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

    public void setLowPlaneNumber(int lowPlaneNumber) {
        this.lowPlaneNumber = lowPlaneNumber;
    }

    public void setBossPlane(BossPlane bossPlane) {
        this.bossPlane = bossPlane;
    }

    public List<BossBullet> getBossBulletList() {
        return bossBulletList;
    }
}
