package controller;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class MusicPlayer {
    private static Clip bgmClip;

   //播放一次音效
    public static void play(String path) {
        try {
            InputStream is = MusicPlayer.class.getResourceAsStream(path);
            if (is == null) return;//文件找不到就不播

            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();

            // 播完自动释放资源
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //循环播放背景音乐
    public static void playBGM(String path) {
        stopBGM();  // 先停掉旧的
        try {
            InputStream is = MusicPlayer.class.getResourceAsStream(path);
            if (is == null) return;

            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            bgmClip = AudioSystem.getClip();
            bgmClip.open(ais);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //停止背景音乐
    public static void stopBGM() {
        if (bgmClip != null) {
            bgmClip.stop();
            bgmClip.close();
            bgmClip = null;
        }
    }
}

