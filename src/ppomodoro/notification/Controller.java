package ppomodoro.notification;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class Controller implements Initializable{
	@FXML private VBox mainVBox;
	@FXML private Label messageLabel;
	
	private Application app;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		mainVBox.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				try {
					app.stop();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}
	
	//stackoverflow.com/questions/12361600/javafx-fxml-communication-between-application-and-controller-classes
	public void setApp(Application a) {
		app = a;
	}
	
	public void setMessage(String message) {
		messageLabel.setText(message);
	}
}