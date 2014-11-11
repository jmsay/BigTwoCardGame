package controller;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicController {

	private AudioInputStream levelClearStream;
	private AudioInputStream introStream;
	private AudioInputStream highScoreStream;
	private AudioInputStream clickStream;

	private Clip levelClear;
	private Clip intro;
	private Clip winner;
	private Clip click;
	
	
	
	public MusicController() {
		try {
			levelClearStream = AudioSystem.getAudioInputStream(new File("sounds/levelClear.wav"));
			levelClear = AudioSystem.getClip();
			levelClear.open(levelClearStream);
			
			introStream = AudioSystem.getAudioInputStream(new File("sounds/intro.wav"));
			intro = AudioSystem.getClip();
			intro.open(introStream);
			

			highScoreStream = AudioSystem.getAudioInputStream(new File("sounds/winner.wav"));
			winner = AudioSystem.getClip();
			winner.open(highScoreStream);
			

			clickStream = AudioSystem.getAudioInputStream(new File("sounds/click.wav"));
			click = AudioSystem.getClip();
			click.open(clickStream);

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void playlevelClear() {
		levelClear.setFramePosition(0);
		levelClear.start();
	}

	public void playIntro() {
		intro.setFramePosition(0);
		//intro.start();
		//intro.loop(30);
	}
	public void stopIntro(){
		intro.stop();
	}
	
	public void playHighScore() {
		winner.setFramePosition(0);
		winner.start();
	}
	
	public void playClick() {
		click.setFramePosition(0);
		click.start();
	}
	
	public void clearMusic() {
		click.stop();
		winner.stop();
		levelClear.stop();
	}
}
