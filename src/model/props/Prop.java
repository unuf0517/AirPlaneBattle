package model.props;

import model.GameObject;
import model.plane.HeroPlane;


public abstract class Prop extends GameObject {
    private int speedX=3;
    //道具的宽一致
    public static final int WIDTH = 30;

    private boolean isCollected;

    public Prop(int x, int y) {
        super(x, y, 2);
    }

    // 道具核心：被英雄机吃到时触发效果
    public abstract void apply(HeroPlane hero);

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public abstract int getHeight();

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }
}
