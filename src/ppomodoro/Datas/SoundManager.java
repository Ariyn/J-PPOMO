package ppomodoro.Datas;

import java.net.URL;

import javafx.scene.media.AudioClip;

public class SoundManager {
	private static SoundManager singleton = new SoundManager();
	private URL testResource;
	
	// TODO: change clip to dictionaries
	private AudioClip clip;
	
	public static SoundManager getInstance() {
		return singleton;
	}
	
	public SoundManager() {
		System.out.println();
		testResource = getClass().getResource("/ppomodoro/Resources/success.wav");
		clip = new AudioClip(testResource.toString());
		System.out.println("testReource " + testResource);
	}
	
	public void soundEnd() {
		clip.play(1.0);
	}
}
