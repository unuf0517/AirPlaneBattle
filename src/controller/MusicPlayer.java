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
}

