package controller.key;

import controller.GameController;
import controller.MusicPlayer;
import controller.gameEnum.GameState;
import controller.gameEnum.Type;
import model.bullet.EnemyBullet;
import model.effect.Explosion;
import model.plane.HighEnemyPlane;
import model.plane.LowEnemyPlane;
import model.props.Prop;
import view.game.GameCenterPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class GameKeyLis extends KeyAdapter {
    private GameCenterPanel gameCenterPanel;

    private boolean bombKeyPressed = false;

    public GameKeyLis(GameCenterPanel gameCenterPanel) {
        this.gameCenterPanel = gameCenterPanel;
    }

    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() != KeyEvent.VK_H) return;
        bombKeyPressed = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        GameState state = GameController.getInstance().getGameStatus();

        switch (code) {
            case KeyEvent.VK_H:
                // 防止核弹连发
                if (bombKeyPressed) return;
                bombKeyPressed = true;
                if (state == GameState.RUNNING) {
                    launchBomb();
                }
                break;
            case KeyEvent.VK_U:
                if (state == GameState.WAITING) {
                    GameController.getInstance().startGame();
                }
                break;
            case KeyEvent.VK_I:
                if (state == GameState.RUNNING) {
                    GameController.getInstance().pauseGame();
                }
                break;
            case KeyEvent.VK_O:
                if (state == GameState.PAUSE) {
                    GameController.getInstance().continueGame();
                }
                break;
        }
    }

    public void launchBomb(){
        if(GameController.getInstance().getBombNumber() == 0) return;

        MusicPlayer.play("/music/atomBoom.wav");

        List<LowEnemyPlane> lowEnemyPlaneList = gameCenterPanel.getLowEnemyPlaneList();
        List<HighEnemyPlane> highEnemyPlaneList = gameCenterPanel.getHightEnemyPlaneList();
        List<EnemyBullet> enemyBulletList = gameCenterPanel.getEnemyBulletList();
        List<Prop> propList = gameCenterPanel.getPropList();

        //加分
        GameController.getInstance().setScore(GameController.getInstance().getScore() + lowEnemyPlaneList.size() + highEnemyPlaneList.size());
        //根据摧毁数减敌机数量
        gameCenterPanel.decreaseLowPlane(lowEnemyPlaneList.size());
        gameCenterPanel.decreaseHightPlane(highEnemyPlaneList.size());
        //爆炸特效
        for(LowEnemyPlane lep : lowEnemyPlaneList){
            gameCenterPanel.getExplosionList().add(new Explosion(lep.getX(), lep.getY(), LowEnemyPlane.WIDTH, LowEnemyPlane.HEIGHT,Type.LOW));
        }
        for(HighEnemyPlane hep : highEnemyPlaneList){
            gameCenterPanel.getExplosionList().add(new Explosion(hep.getX(), hep.getY(), HighEnemyPlane.WIDTH, HighEnemyPlane.HEIGHT, Type.HIGH));
        }
        //清楚敌机列表
        lowEnemyPlaneList.clear();
        highEnemyPlaneList.clear();
        enemyBulletList.clear();
        propList.clear();
        gameCenterPanel.getHeroBulletList().clear();
        //减少核弹数量
        GameController.getInstance().setBombNumber(GameController.getInstance().getBombNumber() - 1);
    }

}
