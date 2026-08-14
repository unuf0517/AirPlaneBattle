package view.game.custom;

import controller.GameController;
import controller.gameEnum.Skin;

import javax.swing.*;
import java.awt.*;

public class PreviewPanel extends JPanel {
    private Skin previewSkin = GameController.getInstance().getSkin();

    private Image[] lowEnemyImage = {
            new ImageIcon((getClass().getResource("/images/game/color/plane/enemy1_1.png"))).getImage(),
            new ImageIcon((getClass().getResource("/images/game/gray/plane/enemy1_1.png"))).getImage()
    };
    private Image[] highEnemyImage = {
            new ImageIcon((getClass().getResource("/images/game/color/plane/enemy2_1.png"))).getImage(),
            new ImageIcon((getClass().getResource("/images/game/gray/plane/enemy2_1.png"))).getImage()
    };
    private Image[] bossEnemyImage = {
            new ImageIcon((getClass().getResource("/images/game/color/plane/boss1_0.png"))).getImage(),
            new ImageIcon((getClass().getResource("/images/game/gray/plane/boss.png"))).getImage()
    };
    private Image[] heroImage = {
            new ImageIcon((getClass().getResource("/images/game/color/plane/hero1.png"))).getImage(),
            new ImageIcon((getClass().getResource("/images/game/gray/plane/hero1.png"))).getImage()
    };

    public void setPreviewSkin(Skin s) {
        this.previewSkin = s;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 深色背景
        g.setColor(new Color(238,238, 238));
        g.fillRect(0, 0, getWidth(), getHeight());
        if(previewSkin == Skin.COLOR){
            g.drawImage(lowEnemyImage[0],120, 20, 80, 80,null );
            g.drawImage(highEnemyImage[0],220, 20, 80, 85,null );
            g.drawImage(bossEnemyImage[0],320, 20, 80, 80,null );
            g.drawImage(heroImage[0], 20, 30, 65, 85,null );
        }else{
            g.drawImage(lowEnemyImage[1],120, 20, 80, 80,null );
            g.drawImage(highEnemyImage[1],220, 20, 80, 80,null );
            g.drawImage(bossEnemyImage[1],320, 20, 80, 80,null );
            g.drawImage(heroImage[1], 20, 20, 75, 95,null );
        }
    }
}
