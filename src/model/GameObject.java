package model;

import java.awt.*;

public abstract class GameObject {
    private int x, y;
    private int speedY;

    //速度倍率
    private double speedFactor = 1.0;
    //方向累加器
    private double yAccumulator = 0;

    public GameObject(int x, int y, int speedY) {
        this.x = x;
        this.y = y;
        this.speedY = speedY;
    }

    public abstract void draw(Graphics g);

    public Rectangle getRectangle(int width,int heigth){
        return new Rectangle(x,y,width,heigth);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public double getSpeedFactor() {
        return speedFactor;
    }

    public void setSpeedFactor(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    public void updateMove(){
        yAccumulator += getSpeedY() * speedFactor;
        if(Math.abs(yAccumulator) >= 1.0) {
            int moveY = (int) yAccumulator;//取整数部分
            setY(getY() + moveY);
            yAccumulator -= moveY;//保留小数，凑到整数再移动
        }
    }
}

