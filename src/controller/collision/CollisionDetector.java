package controller.collision;

import controller.GameController;
import controller.gameEnum.Skin;
import model.bullet.BossBullet;
import model.bullet.EnemyBullet;
import model.bullet.HeroBullet;
import model.effect.Explosion;
import model.plane.BossPlane;
import model.plane.HeroPlane;
import model.plane.LowEnemyPlane;
import model.plane.HighEnemyPlane;
import model.props.Prop;
import view.GameUI;
import view.game.GameCenterPanel;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class CollisionDetector {
    private GameCenterPanel gameCenterPanel;

    public CollisionDetector(GameCenterPanel gameCenterPanel) {
        this.gameCenterPanel = gameCenterPanel;
    }

    public void detect() {
        checkProp();//道具与英雄机
        checkHeroEnemy();//英雄机与敌机
        checkBullet();//子弹碰撞
        checkHeroBulletCollisoin();//英雄机子弹和敌机碰撞
        checkEnemyBulletCollision();//英雄机与敌机子弹碰撞
        checkBossBulletCollision();//Boss子弹与英雄机碰撞
    }

    //道具与英雄机
    public void checkProp(){
        HeroPlane heroPlane=gameCenterPanel.getHeroPlane();
        if(heroPlane==null) return;
        Rectangle heroRte=heroPlane.getRectangle(HeroPlane.WIDTH,HeroPlane.HEIGHT);//创建英雄机矩形
        for(Prop pp : gameCenterPanel.getPropList()){
            Rectangle propRte=pp.getRectangle(Prop.WIDTH,pp.getHeight());//创建道具矩形
            if(heroRte.intersects(propRte)){
                pp.apply(heroPlane);//根据道具类型使用对应方法
                pp.setCollected(true);
            }
        }
        gameCenterPanel.getPropList().removeIf(Prop::isCollected);
    }

    //英雄机与敌机碰撞检测
    public void checkHeroEnemy(){
        HeroPlane hero = gameCenterPanel.getHeroPlane();
        if (hero == null || hero.isInvincible()) return;//无敌时间不可判断

        Rectangle heroRect = hero.getRectangle(HeroPlane.WIDTH, HeroPlane.HEIGHT);

        //=====初级敌机=====
        List<LowEnemyPlane> toRemoveLow = new ArrayList<>();
        for (LowEnemyPlane lep : gameCenterPanel.getLowEnemyPlaneList()) {
            Rectangle epRect = lep.getRectangle(LowEnemyPlane.WIDTH, LowEnemyPlane.HEIGHT);
            if (heroRect.intersects(epRect)) {
                //敌机原地爆炸，创建爆炸图片实例
                gameCenterPanel.getExplosionList().add(new Explosion(lep.getX(), lep.getY(),LowEnemyPlane.WIDTH,LowEnemyPlane.HEIGHT, Explosion.Type.LOW));
                if(GameController.getInstance().getSkin() == Skin.COLOR){
                    //彩色主题下初高级英雄机爆炸特效一致
                    gameCenterPanel.getExplosionList().add(new Explosion(hero.getX(),hero.getY(),HeroPlane.WIDTH,HeroPlane.HEIGHT, Explosion.Type.L_HERO));
                }else{
                    //双倍子弹且灰色主题要用高级特有的爆炸特效
                    if(hero.isDoubleFire()){
                        gameCenterPanel.getExplosionList().add(new Explosion(hero.getX(),hero.getY(),HeroPlane.WIDTH,HeroPlane.HEIGHT, Explosion.Type.H_HERO));
                    }else{
                        gameCenterPanel.getExplosionList().add(new Explosion(hero.getX(),hero.getY(),HeroPlane.WIDTH,HeroPlane.HEIGHT, Explosion.Type.L_HERO));
                    }
                }
                toRemoveLow.add(lep);
                hero.decreaseHp(1);
            }
        }
        gameCenterPanel.getLowEnemyPlaneList().removeAll(toRemoveLow);//for外统一删

        //=====高级敌机=====
        List<HighEnemyPlane> toRemoveHigh = new ArrayList<>();
        for (HighEnemyPlane hep : gameCenterPanel.getHightEnemyPlaneList()) {
            Rectangle epRect = hep.getRectangle(HighEnemyPlane.WIDTH, HighEnemyPlane.HEIGHT);
            if (heroRect.intersects(epRect)) {
                gameCenterPanel.getExplosionList().add(new Explosion(hep.getX(), hep.getY(), HighEnemyPlane.WIDTH, HighEnemyPlane.HEIGHT, Explosion.Type.HIGH));
                if(GameController.getInstance().getSkin() == Skin.COLOR){
                    //彩色主题下初高级英雄机爆炸特效一致
                    gameCenterPanel.getExplosionList().add(new Explosion(hero.getX(),hero.getY(),HeroPlane.WIDTH,HeroPlane.HEIGHT, Explosion.Type.L_HERO));
                }else{
                    //双倍子弹且灰色主题要用高级特有的爆炸特效
                    if(hero.isDoubleFire()){
                        gameCenterPanel.getExplosionList().add(new Explosion(hero.getX(),hero.getY(),HeroPlane.WIDTH,HeroPlane.HEIGHT, Explosion.Type.H_HERO));
                    }else{
                        gameCenterPanel.getExplosionList().add(new Explosion(hero.getX(),hero.getY(),HeroPlane.WIDTH,HeroPlane.HEIGHT, Explosion.Type.L_HERO));
                    }
                }
                toRemoveHigh.add(hep);
                hero.decreaseHp(1);
            }
        }
        gameCenterPanel.getHightEnemyPlaneList().removeAll(toRemoveHigh);

        //=====Boss=====
        BossPlane bossPlane = gameCenterPanel.getBossPlane();
        if (bossPlane != null) {
            Rectangle bRect = bossPlane.getRectangle(BossPlane.WIDTH, BossPlane.HEIGHT);
            if (heroRect.intersects(bRect)) {
                hero.decreaseHp(1);
            }
        }
    }

    //子弹碰撞检测
    public void checkBullet(){
        List<EnemyBullet> enemyBulletList = gameCenterPanel.getEnemyBulletList();
        List<HeroBullet> heroBulletList = gameCenterPanel.getHeroBulletList();
        List<BossBullet> bossBulletList = gameCenterPanel.getBossBulletList();
        if(heroBulletList.isEmpty() || (enemyBulletList.isEmpty() && bossBulletList.isEmpty())) return;

        //创建临时列表保存需要删除的子弹
        List<EnemyBullet> toRemoveEnemy = new ArrayList<>();
        List<HeroBullet> toRemoveHero = new ArrayList<>();
        List<BossBullet> toRemovBoss = new ArrayList<>();

        for(HeroBullet hb : heroBulletList){
            Rectangle hbRect=hb.getRectangle(HeroBullet.WIDTH,HeroBullet.HEIGHT);
            if(toRemoveHero.contains(hb)) continue;//防止重复添加
            for(EnemyBullet eb : enemyBulletList){
                Rectangle ebRect=eb.getRectangle(EnemyBullet.WIDTH,EnemyBullet.HEIGHT);
                if(toRemoveEnemy.contains(eb)) continue;
                if(hbRect.intersects(ebRect)){
                    toRemoveHero.add(hb);
                    toRemoveEnemy.add(eb);
                    break;//一颗子弹只会碰撞一次
                }
            }
            for(BossBullet bb : bossBulletList){
                Rectangle bbRect = bb.getRectangle(BossBullet.WIDTH,BossBullet.HEIGHT);
                if(toRemovBoss.contains(bb)) continue;
                if(hbRect.intersects(bbRect)){
                    toRemoveHero.add(hb);
                    toRemovBoss.add(bb);
                }
            }
        }
        heroBulletList.removeAll(toRemoveHero);
        enemyBulletList.removeAll(toRemoveEnemy);
        bossBulletList.removeAll(toRemovBoss);
    }

    //敌机与子弹碰撞
    public void checkHeroBulletCollisoin(){
        List<HeroBullet> heroBulletList=gameCenterPanel.getHeroBulletList();//英雄机子弹
        List<LowEnemyPlane> lowEnemyPlaneList=gameCenterPanel.getLowEnemyPlaneList();//初级敌机
        List<HighEnemyPlane> highEnemyPlaneList =gameCenterPanel.getHightEnemyPlaneList();//高级敌机
        if(heroBulletList.isEmpty() || (lowEnemyPlaneList.isEmpty() && highEnemyPlaneList.isEmpty() && gameCenterPanel.getBossPlane() == null)) return;
        //临时集合来保存要删除的元素
        List<LowEnemyPlane> toRemoveLow = new ArrayList<>();
        List<HighEnemyPlane> toRemoveHigh = new ArrayList<>();
        List<HeroBullet> toRemoveHeroBullet = new ArrayList<>();

        for(HeroBullet hb : heroBulletList){
            Rectangle hbRect = hb.getRectangle(HeroBullet.WIDTH,HeroBullet.HEIGHT);
            //=====初级敌机=====
            for(LowEnemyPlane lep : lowEnemyPlaneList) {
                Rectangle lepRect = lep.getRectangle(LowEnemyPlane.WIDTH, LowEnemyPlane.HEIGHT);
                if (hbRect.intersects(lepRect)) {
                    lep.decreaseHp(hb.getDemage());
                    toRemoveHeroBullet.add(hb);
                    if(lep.isDead()){
                        GameUI.gameFrame.getGameCenterPanel().decreaseLowPlane(1);
                        GameController.getInstance().setScore(GameController.getInstance().getScore()+1);
                        toRemoveLow.add(lep);
                        gameCenterPanel.getExplosionList().add(new Explosion(lep.getX(), lep.getY(),LowEnemyPlane.WIDTH,LowEnemyPlane.HEIGHT, Explosion.Type.LOW));
                    }
                }
            }

            //=====高级敌机=====
            for (HighEnemyPlane hep : highEnemyPlaneList) {
                Rectangle hepRect = hep.getRectangle(HighEnemyPlane.WIDTH, HighEnemyPlane.HEIGHT);
                if (hbRect.intersects(hepRect)) {
                    hep.decreaseHp(hb.getDemage());
                    toRemoveHeroBullet.add(hb);
                    if (hep.isDead()) {
                        GameUI.gameFrame.getGameCenterPanel().decreaseHightPlane(1);
                        GameController.getInstance().setScore(GameController.getInstance().getScore()+2);
                        toRemoveHigh.add(hep);
                        gameCenterPanel.getExplosionList().add(new Explosion(hep.getX(), hep.getY(), HighEnemyPlane.WIDTH, HighEnemyPlane.HEIGHT, Explosion.Type.HIGH));
                    }
                }
            }

            //=====boss=====
            BossPlane bossPlane=gameCenterPanel.getBossPlane();
            if(bossPlane == null) continue;
            Rectangle bRect = bossPlane.getRectangle(BossPlane.WIDTH,BossPlane.HEIGHT);
            if(hbRect.intersects(bRect)){
                bossPlane.decreaseHp(hb.getDemage());
                toRemoveHeroBullet.add(hb);
                GameController.getInstance().setScore(GameController.getInstance().getScore()+1);//打中一颗扣一滴血加一分
                if(bossPlane.isDead()){
                    //初始化爆炸图片
                    gameCenterPanel.getExplosionList().add(new Explosion(bossPlane.getX(),bossPlane.getY(),BossPlane.WIDTH,BossPlane.HEIGHT, model.effect.Explosion.Type.BOSS));
                    GameUI.gameFrame.getGameCenterPanel().decreaseBoss();
                    gameCenterPanel.setBossPlane(null);
                }
            }
        }
        lowEnemyPlaneList.removeAll(toRemoveLow);
        highEnemyPlaneList.removeAll(toRemoveHigh);
        heroBulletList.removeAll(toRemoveHeroBullet);
    }

    //敌机子弹与英雄机碰撞
    public void checkEnemyBulletCollision(){
        List<EnemyBullet> enemyBulletList = gameCenterPanel.getEnemyBulletList();
        if(enemyBulletList.isEmpty()) return;
        HeroPlane heroPlane = gameCenterPanel.getHeroPlane();
        Rectangle hpRect = heroPlane.getRectangle(HeroPlane.WIDTH,HeroPlane.HEIGHT);
        List<EnemyBullet> toRemoveBullet = new ArrayList<>();
        for(EnemyBullet eb : enemyBulletList){
            Rectangle bulletRect = eb.getRectangle(EnemyBullet.WIDTH,EnemyBullet.HEIGHT);
            if(bulletRect.intersects(hpRect)){
                toRemoveBullet.add(eb);
                heroPlane.decreaseHp(eb.getDemage());
            }
        }
        enemyBulletList.removeAll(toRemoveBullet);
    }

    //Boss子弹与英雄机碰撞
    public void checkBossBulletCollision() {
        List<BossBullet> bossBulletList = gameCenterPanel.getBossBulletList();
        if (bossBulletList.isEmpty()) return;
        HeroPlane heroPlane = gameCenterPanel.getHeroPlane();
        if (heroPlane == null || heroPlane.isInvincible()) return;//无敌期不受伤
        Rectangle hpRect = heroPlane.getRectangle(HeroPlane.WIDTH, HeroPlane.HEIGHT);
        List<BossBullet> toRemoveBoss = new ArrayList<>();
        for (BossBullet bb : bossBulletList) {
            Rectangle bbRect = bb.getRectangle(BossBullet.WIDTH, BossBullet.HEIGHT);
            if (bbRect.intersects(hpRect)) {
                toRemoveBoss.add(bb);
                heroPlane.decreaseHp(bb.getDemage());
            }
        }
        bossBulletList.removeAll(toRemoveBoss);
    }

}
