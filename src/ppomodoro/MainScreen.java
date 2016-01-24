package ppomodoro;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ppomodoro.Datas.PpomoTimer;
import ppomodoro.Datas.ProgramManager;
import ppomodoro.notification.NotiManager;

public class MainScreen extends Application {
	private FXMLLoader fxml;
	
	private NotiManager m;
	private Controller c;
	private PpomoTimer pt;
	private ProgramManager pm;
	
	private Parent root;
	
	private Stage primaryStage;
	
	
	@Override
	public void init() throws Exception {
		System.out.println(Thread.currentThread().getName()+": init 호출");
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		m = NotiManager.getInstance();
		pt = PpomoTimer.getInstance();
		pm = ProgramManager.getInstance();
		
		this.primaryStage = primaryStage;
		Scene testScene = this.testFunction();
		
		int second = pt.getSecond();
		c.changeTimer(second);
		
		primaryStage.setTitle("PPOMODORO TIMER");
		primaryStage.setScene(testScene);
		
		primaryStage.show();
		primaryStage.requestFocus();
		
		System.out.println("start new window!");
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
	
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	@Override
	public void stop() throws Exception {
		System.out.println(Thread.currentThread().getName()+": stop 호출");
		pm.closeWindow(c);
		c.destroy();
	}
	
	private Scene testFunction() {
		fxml = new FXMLLoader(getClass().getResource("timer.fxml"));
		System.out.println(fxml);
		try {
			root = fxml.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
		}
		
		c = (Controller)fxml.getController();
		c.setMainScreen(this);
		Scene newScene = new Scene(root);
		
		//dzone.com/articles/handling-keyboard-sortcuts
		//stackoverflow.com/questions/14357515/javafx-close-window-on-pressing-esc
		newScene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			KeyCombination keyCombi = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
						
			@Override
			public void handle(KeyEvent event) {
				if(keyCombi.match(event)) {
					System.out.println("same!");
					try {
						stop();
						primaryStage.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}			
		});
		
		return newScene;
	}
}
