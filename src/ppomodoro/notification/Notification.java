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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Notification extends Application {
//	private static Notification singleton = new Notification();
//	public Notification getInstance() {
//		return singleton;
//	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = null;
		
		try {
			root = FXMLLoader.load(getClass().getResource("noti.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

}
