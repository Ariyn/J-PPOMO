package ppomodoro;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import ppomodoro.Datas.*;
import ppomodoro.Tray.TrayManager;

public class Controller implements Initializable, TimerTicListener{
	@FXML private Button btn1;
	@FXML private Label timeLabel;
//	@FXML private ProgressBar progressBar;
	@FXML private ProgressIndicator progressPi;
	
	private int thisPpomoComplete = 0;
	private String thisPpomoType = "";
	
	private MainScreen ms;
	
	private TrayManager tm = TrayManager.getInstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		System.out.println(location.toString());
		
		PpomoTimer.getInstance().addListener(this);
		
		btn1.setText("PPOMO!");
		
		btn1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleBtn1Action(event);
			}
		});
	}
	
	public void destroy() {
//		PpomoTimer.getInstance().removeListener(this);
	}
	
	public void setMainScreen(MainScreen ms) {
		this.ms = ms;
	}
	
	public void changeTimer(int second) {
		this.changeTimer(second/60, second%60);
	}
	public void changeTimer(int minute, int second) {
		this.timeLabel.setText(String.format("%02d", minute) + ":" + String.format("%02d", second));
	}
	
	public void handleBtn1Action(ActionEvent event) {
		PpomoTimer t = PpomoTimer.getInstance();
		System.out.println(t.isRunning());
		
		if(t.isRunning()) {
			t.stopPpomo(false);
			btn1.setText("PPOMO!");
			System.out.println("stop ppomo");
		} else {
			t.startNewPpomo();
			btn1.setText("STOP!");
			System.out.println("start new ppomo");
		}
	}
	
	@Override
	public void timeTic(int second) {
		System.out.println(second);
		
		changeTimer(second/60, second%60);
		tm.setTimerText(String.format("%02d", second/60) + ":" + String.format("%02d", second%60));
		
//		System.out.println(second / (float)thisPpomoComplete);
		progressPi.setProgress(second / (float)thisPpomoComplete);
	}
	
	@Override
	public void timerEnd() {
		SoundManager.getInstance().soundEnd();
		btn1.setText("PPOMO!");
	}

	@Override
	public void timerStart(int completeSecond, String type) {
		// TODO Auto-generated method stub
		thisPpomoComplete = completeSecond;
		thisPpomoType = type;
		
		System.out.println(type);
	}
}