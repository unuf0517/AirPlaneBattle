package controller.key;

import java.awt.event.KeyAdapter;
import model.plane.HeroPlane;
import view.game.GameCenterPanel;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class MoveKeyLis extends KeyAdapter {
    private GameCenterPanel gameCenterPanel;
    //按住的键集合
    private Set<Integer> pressedKeys = new HashSet<>();

    public MoveKeyLis(GameCenterPanel gameCenterPanel) {
        this.gameCenterPanel = gameCenterPanel;
    }

    /**
     * 记录按下的按键
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    /**
     * 删除松开的按键
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    /**
     * 根据按下的按钮移动
     */
    public void updateMovement() {
        HeroPlane hero = gameCenterPanel.getHeroPlane();
        int gameCenterPanelHeight= gameCenterPanel.getHeight();
        int gameCenterPanelWidth=gameCenterPanel.getWidth();
        int heroPlaneHeight=HeroPlane.HEIGHT;
        int heroPlaneWidth=HeroPlane.WIDTH;

        if (hero == null) return;
        //上
        if (pressedKeys.contains(KeyEvent.VK_W) || pressedKeys.contains(KeyEvent.VK_UP)) {
            hero.setY(hero.getY() - hero.getSpeedY());
            if(hero.getY()<0) hero.setY(0);//Y不会小于0，无法超过上边界
        }
        //下
        if (pressedKeys.contains(KeyEvent.VK_S) || pressedKeys.contains(KeyEvent.VK_DOWN)) {
            hero.setY(hero.getY() + hero.getSpeedY());
            if(hero.getY()+heroPlaneHeight>gameCenterPanelHeight) hero.setY(gameCenterPanelHeight-heroPlaneHeight);
        }
        //左
        if (pressedKeys.contains(KeyEvent.VK_A) || pressedKeys.contains(KeyEvent.VK_LEFT)) {
            hero.setX(hero.getX() - hero.getSpeedY());
            if(hero.getX()<0) hero.setX(0);
        }
        //右
        if (pressedKeys.contains(KeyEvent.VK_D) || pressedKeys.contains(KeyEvent.VK_RIGHT)) {
            hero.setX(hero.getX() + hero.getSpeedY());
            if(hero.getX()+heroPlaneWidth>gameCenterPanelWidth) hero.setX(gameCenterPanelWidth-heroPlaneWidth);
        }
    }
}
