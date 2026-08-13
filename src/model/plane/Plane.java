package model.plane;

import model.GameObject;

public abstract class Plane extends GameObject {
    //飞机的喷火帧
    protected int currenIndex=0;
    //当前血量
    protected int hp;
    //满血
    protected int maxHp;

    public Plane(int x,int y,int speedY){
        super(x,y,speedY);
    }

    /**
     * 刷新图片索引
     */
    public void startAnimation() {
        currenIndex=(currenIndex+1)%2;
    }



    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
        hp=maxHp;
    }

    public boolean isDead(){
        return hp<=0;
    }

    public void decreaseHp(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        }
    }
}
