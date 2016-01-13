package ppomodoro.notification;

import java.util.ArrayList;

import javafx.stage.Stage;

// Notification Manager is singleton
// this takes care notification init, destroy, control number of notification

public class Manager {
	private static Manager singleton = new Manager();
	private ArrayList<Notification> notiList = new ArrayList<Notification>(); 
	
	public static Manager getInstance() {
		return singleton;
	}
	
	public void notificate(String message) {
		Notification _noti = new Notification();
		notiList.add(_noti);
		
		Stage stage = new Stage();
		try {
			_noti.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		_noti.setMessage(message);
	}
}
