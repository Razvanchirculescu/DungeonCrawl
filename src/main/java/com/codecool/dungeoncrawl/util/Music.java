package com.codecool.dungeoncrawl.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Music {
    Clip clip;

    public Music(String musicLocation) {
        try {
            File musicPath = new File(musicLocation);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
            } else System.out.println("Can't find file!");

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void stop() {
        clip.stop();
        clip.setFramePosition(0);
    }
}
