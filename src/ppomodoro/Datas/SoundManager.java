package ppomodoro.Datas;

import java.net.URL;

import java.util.ArrayList;

import javafx.scene.media.AudioClip;

public class SoundManager {
	private static SoundManager singleton = new SoundManager();
	private ArrayList<Sound> resource = new ArrayList<Sound>();
	private ArrayList<String> resourceName = new ArrayList<String>();
	
	// TODO: change clip to dictionaries
	
	public static SoundManager getInstance() {
		return singleton;
	}
	
	public SoundManager() {
		System.out.println();
		
		addNewSound("success", "/ppomodoro/Resources/success.wav");
		addNewSound("ding", "/ppomodoro/Resources/ding.wav");
	}
	
	public void addNewSound(String name, String path) {
		
		resource.add(new Sound(name, getClass().getResource(path)));
		resourceName.add(name);
	}
	
	public void playSound(String name) {
		int index = resourceName.indexOf(name);
		Sound s = resource.get(index);
		s.play();
	}
}

class Sound {
	private AudioClip clip;
	private String name;
	private URL url;
	
	
	public Sound(String name, URL url) {
		this.name = name;
		this.url = url;
		
		this.clip = new AudioClip(this.url.toString());
	}
	
	public void play() {
		this.clip.play();
	}
}