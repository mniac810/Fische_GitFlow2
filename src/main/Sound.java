package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[5];

    public Sound(){
        soundURL[0] = getClass().getResource("/Sound/SonicFrontiers.wav");
        soundURL[1] = getClass().getResource("/Sound/CollectItemSoundEffect.wav");
        soundURL[2] = getClass().getResource("/Sound/FishSplashWater.wav");
        soundURL[3] = getClass().getResource("/Sound/WinningSoundEffect.wav");
        soundURL[4] = getClass().getResource("/Sound/DisappointedSE.wav");
    }
    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch (Exception e){
        }
    }
    public void play(){
        clip.start();
    }
    public void setVolume(float desireVolume){
        desireVolume /=100.0;
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = volume.getMaximum() - volume.getMinimum();
        float gain = (range * desireVolume) + volume.getMinimum();
        volume.setValue(gain);
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
