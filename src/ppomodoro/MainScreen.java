package ppomodoro;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainScreen extends Application {
	private static MainScreen singleton;
	public int dataA = 0;
	
	public MainScreen() {
		singleton = this;
	}
	
	public static MainScreen getInstance() {
		return singleton;
	}
	
	@Override
	public void init() throws Exception {
		System.out.println(Thread.currentThread().getName()+": init 호출");
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Scene testScene = this.testFunction();
		
		primaryStage.setTitle("Test App");
		primaryStage.setScene(testScene);
		primaryStage.show();
	}
	
	@Override
	public void stop() throws Exception {
		System.out.println(Thread.currentThread().getName()+": stop 호출");
	}
	
	private Scene testFunction() {
		// Label label = new Label();
		// label.setText("Hello, JavaFX");
		// label.setFont(new Font(50));
		
		// Button button = new Button();
		// button.setText("confirm");
//		 button.setOnAction(event->Platform.exit());
		
		
		// VBox root = new VBox();
		// root.setPrefWidth(480);
		// root.setPrefHeight(800);
		// root.setAlignment(Pos.CENTER);
		// root.setSpacing(20);
		
		// root.getChildren().add(label);
		// root.getChildren().add(button);
		
		Parent root = null;
		
		try {
			root = FXMLLoader.load(getClass().getResource("timer.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scene newScene = new Scene(root);
		
		return newScene;
	}
}
