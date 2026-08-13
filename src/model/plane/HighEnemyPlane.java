package model.plane;

import controller.GameController;
import controller.gameEnum.Skin;

import javax.swing.*;
import java.awt.*;

public class HighEnemyPlane extends Plane{

    public static final int WIDTH = 55;
    public static final int HEIGHT = 55;

    //图片
    private static Image[] images;

    //彩色图片
    private static final String[] C_PATH={
            "/images/game/color/plane/enemy2_1.png",
            "/images/game/color/plane/enemy2_2.png"
    };

    //灰色图片
    private static final String[] G_PATH={
            "/images/game/gray/plane/enemy2_1.png"
    };

    public HighEnemyPlane(int x, int y){
        super(x,y,1);
        loadImage();
        this.setMaxHp(GameController.highEnemyMaxHp);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(images[currenIndex], getX(), getY(), null);
    }

    //重置皮肤
    public static void reloadImages() {
        images = null;
        loadImage();
    }

    private static void loadImage() {
        if (GameController.getInstance().getSkin() == Skin.COLOR) {
            images = new Image[C_PATH.length];
            for(int i=0;i<C_PATH.length;i++){
                images[i]=new ImageIcon(LowEnemyPlane.class.getResource(C_PATH[i])).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
            }
        }else{
            images = new Image[G_PATH.length];
            for(int i=0;i<G_PATH.length;i++){
                images[i]=new ImageIcon(LowEnemyPlane.class.getResource(G_PATH[i])).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
            }
        }
    }

    /**
     * 刷新图片索引
     */
    public void startAnimation() {
        if(GameController.getInstance().getSkin() == Skin.COLOR){
            currenIndex=(currenIndex+1)%2;
        }else{
            currenIndex=0;
        }

    }

}
