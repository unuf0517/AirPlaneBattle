//package model.effect;
//
//import controller.GameController;
//import controller.gameEnum.Skin;
//import model.GameObject;
//import model.plane.LowEnemyPlane;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class BossExplosion extends GameObject {
//    private int currentIndex = 0;
//    private boolean finished = false;
//
//    //图片
//    private static Image[] images;
//
//    //彩色初级图片
//    private static final String[] C_PATH={
//            "/images/game/color/plane/enemy2_1.png",
//            "/images/game/color/plane/enemy2_2.png"
//    };
//
//    //灰色初级图片
//    private static final String[] G_PATH={
//            "/images/game/gray/plane/enemy2_1.png"
//    };
//
//    public BossExplosion(int x,int y,int WIDTH,int HEIGHT){
//        super(x,y,0);
//      loadImage(WIDTH,HEIGHT);
//    }
//
//    private static void loadImage(int WIDTH,int HEIGHT) {
//        if (GameController.getInstance().getSkin() == Skin.COLOR) {
//            images = new Image[C_PATH.length];
//            for(int i=0;i<C_PATH.length;i++){
//                images[i]=new ImageIcon(LowEnemyPlane.class.getResource(C_PATH[i])).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
//            }
//        }else{
//            images = new Image[G_PATH.length];
//            for(int i=0;i<G_PATH.length;i++){
//                images[i]=new ImageIcon(LowEnemyPlane.class.getResource(G_PATH[i])).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
//            }
//        }
//    }
//
//    /**
//     * 切换图片
//     */
//    public void nextFrame() {
//        currentIndex++;
//        if (currentIndex >= images.length) {
//            finished = true;
//        }
//    }
//
//    @Override
//    public void draw(Graphics g) {
//        if (!finished) {
//            g.drawImage(images[currentIndex], getX(), getY(), null);
//        }
//    }
//
//    public boolean isFinished() {
//        return finished;
//    }
//}
