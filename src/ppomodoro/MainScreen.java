package ppomodoro;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ppomodoro.Datas.PpomoTimer;
import ppomodoro.Datas.ProgramManager;
import ppomodoro.Datas.SoundManager;
import ppomodoro.notification.Manager;

public class MainScreen extends Application {
	private Manager m;
	private FXMLLoader fxml;
	private Parent root;
	private Controller c;
	private PpomoTimer pt;
	private ProgramManager pm;
	
	
	@Override
	public void init() throws Exception {
		System.out.println(Thread.currentThread().getName()+": init 호출");
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		m = Manager.getInstance();
		pt = PpomoTimer.getInstance();
		pm = ProgramManager.getInstance();
		
		Scene testScene = this.testFunction();
		
		int second = pt.getSecond();
		c.changeTimer(second);
		
		primaryStage.setTitle("Test App");
		primaryStage.setScene(testScene);
		primaryStage.show();
		
		primaryStage.requestFocus();
				
		//java2s.com/Code/Java/JavaFX/Stagecloseevent.htm
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				try {
					stop();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	public void stop() throws Exception {
		System.out.println(Thread.currentThread().getName()+": stop 호출");
		c.destroy();
	}
	
	private Scene testFunction() {
		fxml = new FXMLLoader(getClass().getResource("timer.fxml"));
		try {
			root = fxml.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c = (Controller)fxml.getController();
		c.setMainScreen(this);
		Scene newScene = new Scene(root);
		
		return newScene;
	}
}
