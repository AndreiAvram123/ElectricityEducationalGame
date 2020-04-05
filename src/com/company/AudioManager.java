package com.company;

import javafx.scene.media.AudioClip;

public class AudioManager {
    private static AudioManager instance;
    private AudioClip mouseHoverSound;

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    private AudioManager() {
        mouseHoverSound = new AudioClip(this.getClass().getResource("sound_button_hover.mp3").toString());
    }

    public void playLevelFinishedSound() {
        AudioClip audioClip = new AudioClip(this.getClass().getResource("guta.mp3").toString());
        audioClip.play();
    }

    public void playButtonHoveSound() {
        mouseHoverSound.play();
    }
}
