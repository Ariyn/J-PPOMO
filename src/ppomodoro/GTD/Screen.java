package ppomodoro.GTD;

import java.io.IOException;
import java.net.URL;
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
import javafx.stage.StageStyle;
import ppomodoro.Datas.PpomoTimer;
import ppomodoro.Datas.ProgramManager;
import ppomodoro.notification.NotiManager;

public class Screen extends Application{
	Stage primaryStage;
	FXMLLoader fxml;
	
	private NotiManager m;
	private Controller c;
	private PpomoTimer pt;
	private ProgramManager pm;
	
	private Parent root;
	
	@Override
	public void init() throws Exception {
		System.out.println(Thread.currentThread().getName()+": init GTD screen");
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		this.primaryStage = arg0;
		Scene testScene = this.testFunction();
		
//		primaryStage.initStyle(StageStyle.UNDECORATED);
		
		primaryStage.setTitle("PPOMODORO TIMER");
		primaryStage.setScene(testScene);
		this.primaryStage.show();

//		m = NotiManager.getInstance();
//		pt = PpomoTimer.getInstance();
//		pm = ProgramManager.getInstance();
//		
//		this.primaryStage = primaryStage;
//		Scene testScene = this.testFunction();
		
	}
	
	private Scene testFunction() {
		fxml = new FXMLLoader(getClass().getResource("gtdList.fxml"));
		
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