package ppomodoro;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;
import ppomodoro.Datas.*;
import ppomodoro.Tray.TrayManager;
import ppomodoro.notification.NotiManager;

public class Controller implements Initializable, TimerTicListener{
	@FXML private Button btn1;
	@FXML private Label timeLabel;
//	@FXML private ProgressIndicator progressPi;
	@FXML private Canvas svgView;
	
//	@FXML private ProgressBar progressBar;
//	@FXML private ImageView svgView;
	
	private int thisPpomoComplete = 0;
	private String thisPpomoType = "";
	
	private MainScreen ms;
	
	private GraphicsContext gc;
	
	private TrayManager tm = TrayManager.getInstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		System.out.println(location.toString());
		
		PpomoTimer.getInstance().addListener(this);
		
		gc = svgView.getGraphicsContext2D();
		
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
		
		
		drawCanvas(second);
	}
	
	private void drawCanvas(int second) {
		if(second%60 == 0) {
			gc.clearRect(0, 0, svgView.getWidth(), svgView.getHeight());
		}
	
		double minutes, seconds;
		
		
		//TODO: change this number as variables.
		// for configuration
		if(thisPpomoType == "ppomo") {
			minutes = (second/60)*14.4;
			seconds = (second%60)*6;
		} else {
			minutes = (second/60)*72;
			seconds = (second%60)*6;
		}
		
		drawCoin();
		
		gc.setStroke(Color.FUCHSIA);
		drawArc(20, 190, seconds);
		
		gc.setStroke(Color.TURQUOISE);
		drawArc(20, 130, minutes);
	}
	
	private void drawCoin() {
		if(thisPpomoType == "ppomo")
			gc.setFill(Color.DEEPPINK);
		else
			gc.setFill(Color.DEEPSKYBLUE);
		
		gc.fillArc(175, 175, 50, 50, 0, 360, ArcType.CHORD);
		
		gc.setFill(Color.ALICEBLUE);
		
		gc.setTextAlign(TextAlignment.CENTER);
		gc.fillText(thisPpomoType, 200, 200);
		
	}
	private void drawArc(int width, int radius, double angle) {
    	int x = 200 - (radius)/2, y = 200 - (radius+width)/2;
    	gc.setLineWidth(width);
    	
		gc.strokeArc(x, y, radius+width, radius+width, 90, -angle, ArcType.OPEN);
		//when lw=20, radius=100, angle start=15, end=30
    }
	
	@Override
	public void timerEnd() {
		SoundManager.getInstance().playSound("success");
		btn1.setText("PPOMO!");
		
		String message;
		if(thisPpomoType == "ppomo")
			message = "뽀모도로가";
		else 
			message = "휴식시간이";
	
		NotiManager.getInstance().notificate(message + " 끝났어요.");
	}

	@Override
	public void timerStart(int completeSecond, String type) {
		// TODO Auto-generated method stub
		thisPpomoComplete = completeSecond;
		thisPpomoType = type;
		
		System.out.println(type);
	}
}