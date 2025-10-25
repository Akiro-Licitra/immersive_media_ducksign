package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[25];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/footstep.wav");
		soundURL[1] = getClass().getResource("/sound/splash.wav");
		soundURL[2] = getClass().getResource("/sound/duck_overworld.wav");
		soundURL[3] = getClass().getResource("/sound/damage.wav");
		soundURL[4] = getClass().getResource("/sound/achievement.wav");
		soundURL[5] = getClass().getResource("/sound/click1.wav");
		soundURL[6] = getClass().getResource("/sound/click2.wav");
		soundURL[7] = getClass().getResource("/sound/click3.wav");
	}
	
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		}catch(Exception e) {
		}
	}
	
	
	public void play() {
		clip.start();
	}
	
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	
	public void stop() {
		clip.stop();
	}
}
