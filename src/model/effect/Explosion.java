package model.effect;

import controller.GameController;
import controller.gameEnum.Skin;
import model.GameObject;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class Explosion extends GameObject {
    public enum Type{
        L_HERO,
        H_HERO,
        LOW,
        HIGH,
        BOSS
    }

    //彩色主题下的爆炸图片
    private static final Map<Type,String[]> COLOR_PATH = new EnumMap<>(Type.class){
        {
            String[] small={
                    "/images/game/color/explosionEffect/1.png",
                    "/images/game/color/explosionEffect/2.png",
                    "/images/game/color/explosionEffect/3.png",
                    "/images/game/color/explosionEffect/4.png",
                    "/images/game/color/explosionEffect/5.png"};
            put(Type.L_HERO, small);
            put(Type.H_HERO, small);
            put(Type.LOW, small);
            put(Type.HIGH, small);
            put(Type.BOSS, new String[]{
                    "/images/game/color/explosionEffect/bossEffect/1.png",
                    "/images/game/color/explosionEffect/bossEffect/2.png",
                    "/images/game/color/explosionEffect/bossEffect/3.png",
                    "/images/game/color/explosionEffect/bossEffect/4.png",
            });
        }

    };

    //灰色主题下的爆炸图片
    private static final Map<Type,String[]> GRAY_PATH = new EnumMap<>(Type.class){
        {
            put(Type.L_HERO,new String[]{
                    "/images/game/gray/explosionEffect/heroPlane/l/1.png",
                    "/images/game/gray/explosionEffect/heroPlane/l/2.png",
                    "/images/game/gray/explosionEffect/heroPlane/l/3.png",
                    "/images/game/gray/explosionEffect/heroPlane/l/4.png",
            });
            put(Type.H_HERO,new String[]{
                    "/images/game/gray/explosionEffect/heroPlane/h/1.png",
                    "/images/game/gray/explosionEffect/heroPlane/h/2.png",
                    "/images/game/gray/explosionEffect/heroPlane/h/3.png",
                    "/images/game/gray/explosionEffect/heroPlane/h/4.png"
            });
            put(Type.LOW,new String[]{
                    "/images/game/gray/explosionEffect/lowEnemy/1.png",
                    "/images/game/gray/explosionEffect/lowEnemy/2.png",
                    "/images/game/gray/explosionEffect/lowEnemy/3.png",
                    "/images/game/gray/explosionEffect/lowEnemy/4.png"
            });
            put(Type.HIGH,new String[]{
                    "/images/game/gray/explosionEffect/hightEnemy/1.png",
                    "/images/game/gray/explosionEffect/hightEnemy/2.png",
                    "/images/game/gray/explosionEffect/hightEnemy/3.png",
                    "/images/game/gray/explosionEffect/hightEnemy/4.png",
            });
            put(Type.BOSS,new String[]{
                    "/images/game/gray/explosionEffect/boss/1.png",
                    "/images/game/gray/explosionEffect/boss/2.png",
                    "/images/game/gray/explosionEffect/boss/3.png",
                    "/images/game/gray/explosionEffect/boss/4.png",
                    "/images/game/gray/explosionEffect/boss/5.png",
                    "/images/game/gray/explosionEffect/boss/6.png",
            });
        }
    };
    //保存现主题图片
    private static final Map<Type, Image[]> FRAMES = new EnumMap<>(Type.class);

    //爆炸图片
    private Image[] effectImages;

    private int currentIndex = 0;
    private boolean finished = false;

    private static int WIDTH;
    private static int HEIGHT;

    private Type type;

    public Explosion(int x, int y,int WIDTH,int HEIGHT,Type type) {
        super(x, y, 0);
        this.WIDTH=WIDTH;
        this.HEIGHT=HEIGHT;
        this.type=type;
        loadImage();
        choicImages();

    }

    private void choicImages(){
        effectImages = FRAMES.get(type);
    }

    //改变主题重新加载图片
    public static void reloadImages(){
        loadImage();
    }

    private static void loadImage(){
        Skin s = GameController.getInstance().getSkin();
        for(Type t : Type.values()){
            //获取每种飞机对应的爆炸照片
            String[] path = (s == Skin.GRAY) ? GRAY_PATH.get(t) : COLOR_PATH.get(t);
            Image[] images = new Image[path.length];
            for(int i=0;i< path.length;i++){
                images[i] = new ImageIcon(Explosion.class.getResource(path[i])).getImage().getScaledInstance(WIDTH,HEIGHT,Image.SCALE_SMOOTH);
            }
            FRAMES.put(t, images);
        }
    }

    /**
     * 切换图片
     */
    public void nextFrame() {
        currentIndex++;
        if (currentIndex >= effectImages.length) {
            finished = true;
        }
    }

    @Override
    public void draw(Graphics g) {
        if (!finished) {
            g.drawImage(effectImages[currentIndex], getX(), getY(), null);
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
