package ppomodoro;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import ppomodoro.Datas.*;

public class Controller implements Initializable, TimerTicListener{
	@FXML private Button btn1;
	@FXML private Label timeLabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		System.out.println(location.toString());
		
		ppomoTimer.getInstance().addListener(this);
		
		btn1.setText("PPOMO!");
		
		btn1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleBtn1Action(event);
			}
		});
	}
	
	public void changeTimer(int second) {
		this.timeLabel.setText(Integer.toString(second));
	}
	public void changeTimer(int minute, int second) {
		this.timeLabel.setText(String.format("%02d", minute) + ":" + String.format("%02d", second));
	}
	
	public void handleBtn1Action(ActionEvent event) {
		ppomoTimer t = ppomoTimer.getInstance();
		
		if(t.isRunning()) {
			t.stopPpomo();
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
	}

	@Override
	public void timerEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void timerStart() {
		// TODO Auto-generated method stub
		
	}
}


