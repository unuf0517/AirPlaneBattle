package view.game.Custom;

import controller.GameController;
import controller.action.CustomActionLis;
import controller.gameEnum.Skin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomPanel extends JPanel {
    //滑动条
    private JSlider speedSlider;
    private JLabel infoLabel;
    //下拉框
    private JComboBox<String> levelBox;
    //选择按钮
    private JRadioButton colorBtn;
    private JRadioButton grayBtn;
    private PreviewPanel previewPanel;
    private JButton okBtn ;
    private JButton cancelBtn;
    //速度倍率
    private int tempSpeedFactor;
    //关卡
    private int tempLevel = GameController.getInstance().getLevel();
    //主题
    private Skin tempSkin;

    private JLabel enemySpeed;
    private JLabel level;
    private JLabel skin;

    public CustomPanel(){
        //获取当前状态
        tempSpeedFactor = GameController.getInstance().getCustomSpeedFactor();
        tempLevel = GameController.getInstance().getLevel();
        tempSkin = GameController.getInstance().getSkin();

        //设置为自由布局
        setLayout(null);

        enemySpeed = new JLabel("敌机速度:");
        level = new JLabel("选择关卡:");
        skin = new JLabel("皮肤选择:");

        //设置位置
        enemySpeed.setBounds(20, 15, 80, 25);
        level.setBounds(20, 55, 80, 25);
        skin.setBounds(20, 90, 80, 25);

        //添加到面板
        add(enemySpeed);
        add(level);
        add(skin);

        //速度滑块
        speedSlider = new JSlider(1, 10, tempSpeedFactor);
        speedSlider.setBounds(80, 3, 250, 50);
        speedSlider.setMajorTickSpacing(1);
        add(speedSlider);

        //速度条信息
        infoLabel = new JLabel("选择了速度:"+tempSpeedFactor);
        infoLabel.setBounds(340, 15, 250, 25);
        add(infoLabel);

        //关卡选择
        levelBox = new JComboBox<>(new String[]{"第一关", "第二关", "第三关"});
        levelBox.setSelectedIndex(tempLevel - 1);
        levelBox.setBounds(85, 55, 100, 25);
        add(levelBox);

        //给按钮添加标签
        colorBtn = new JRadioButton("经典彩色");
        colorBtn.setBounds(100, 93, 80, 25);
        grayBtn = new JRadioButton("经典灰色");
        grayBtn.setBounds(220, 93, 100, 25);

        //选择主题按钮
        ButtonGroup skinGroup = new ButtonGroup();
        skinGroup.add(colorBtn);
        skinGroup.add(grayBtn);
        selectSkin(tempSkin);

        //添加到面板
        add(colorBtn);
        add(grayBtn);
        //确认按钮
        okBtn = new JButton("确定");
        okBtn.setBounds(100, 250, 80, 30);
        add(okBtn);
        //注册按钮
        cancelBtn = new JButton("取消");
        cancelBtn.setBounds(300, 250, 80, 30);
        add(cancelBtn);
        //实例化动作监听
        CustomActionLis customActionLis = new CustomActionLis(this);
        //添加确认按钮动作监听
        okBtn.addActionListener(customActionLis);
        okBtn.setActionCommand("confirm");
        //添加取消按钮动作监听
        cancelBtn.addActionListener(customActionLis);
        cancelBtn.setActionCommand("cancel");
        //飞机图片预览面板
        previewPanel = new PreviewPanel();
        previewPanel.setBounds(20, 120, 450, 130);
        add(previewPanel);

        bindEvents();
        updateDisplay();
    }

    private void selectSkin(Skin skin) {
        switch (skin) {
            case COLOR: colorBtn.setSelected(true); break;
            case GRAY:   grayBtn.setSelected(true);   break;
        }
    }

    private void bindEvents() {
        // 滑动条：实时更新右侧信息
        speedSlider.addChangeListener(e -> {
            tempSpeedFactor = speedSlider.getValue();
            updateDisplay();
        });

        levelBox.addActionListener(e -> {
            tempLevel = levelBox.getSelectedIndex() + 1;
            updateDisplay();
        });

        // 皮肤单选按钮：实时更新预览
        ActionListener skinLis = e -> {
            if (colorBtn.isSelected()){
                tempSkin = Skin.COLOR;
            }else{
                tempSkin = Skin.GRAY;
            }
            updateDisplay();
        };
        colorBtn.addActionListener(skinLis);
        grayBtn.addActionListener(skinLis);
    }

    private void updateDisplay() {
        infoLabel.setText("选择了速度:" + tempSpeedFactor);
        // 预览面板同步刷新
        previewPanel.setPreviewSkin(tempSkin);
        previewPanel.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(238,238, 238));
        g.fillRect(0,0,getWidth(),getHeight());


    }

    public Skin getTempSkin() {
        return tempSkin;
    }

    public void setTempSkin(Skin tempSkin) {
        this.tempSkin = tempSkin;
    }

    public int getTempLevel() {
        return tempLevel;
    }

    public void setTempLevel(int tempLevel) {
        this.tempLevel = tempLevel;
    }

    public int getTempSpeedFactor() {
        return tempSpeedFactor;
    }

    public void setTempSpeed(int tempSpeedFactor) {
        this.tempSpeedFactor = tempSpeedFactor;
    }
}
