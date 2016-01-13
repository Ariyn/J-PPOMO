package ppomodoro.notification;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Notification extends Application {
//	private static Notification singleton = new Notification();
//	public Notification getInstance() {
//		return singleton;
//	}
	
	private Parent root;
	private FXMLLoader fxml = null;
	private Controller c;
	private Stage ps;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		    
		try {
			fxml = new FXMLLoader(getClass().getResource("noti.fxml"));
			root = fxml.load();
			
			c = fxml.getController();
			c.setApp(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ps = primaryStage;
		
		//stackoverflow.com/questions/3680221/how-can-i-get-the-monitor-size-in-java
		// figure out width of main monitor
		int width = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();

		Scene newScene = new Scene(root);
		newScene.setFill(null);
		
		primaryStage.setTitle("Test App");
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setScene(newScene);
		
		// TODO: change this to scale-able
		// small, medium, large
		primaryStage.setX(width-240);
		
		primaryStage.show();
	}
	
	public void stop() throws Exception {
		System.out.println("test");
		ps.close();
	}
	
	public void setMessage(String message) {
		c.setMessage(message);
	}
}
