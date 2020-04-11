package com.company;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * AudioManager class is a singleton that is responsible for
 * playing audio throughout the lifetime of the application
 *
 */
public class AudioManager {
    private static AudioManager instance;
    private AudioClip mouseHoverSound;
    private MediaPlayer mediaPlayer;
    private Media backgroundMusic;

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    private AudioManager() {
        mouseHoverSound = new AudioClip(this.getClass().getResource("sound_button_hover.mp3").toString());
        backgroundMusic = new Media(this.getClass().getResource("music.mp3").toString());
        mediaPlayer = new MediaPlayer(backgroundMusic);
    }

    public void playBackgroundMusic() {
        mediaPlayer.play();
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }


    public void playLevelFinishedSound() {
//        AudioClip audioClip = new AudioClip(this.getClass().getResource("guta.mp3").toString());
//        audioClip.play();
    }

    /**
     * Call this method to play a sound on button hover
     */
    public void playButtonHoverSound() {
        mouseHoverSound.play();
    }
}
