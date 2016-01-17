package ppomodoro.notification;

import java.util.ArrayList;

import javafx.stage.Stage;

// Notification Manager is singleton
// this takes care notification init, destroy, control number of notification

//TODO: remove notification a little after
public class NotiManager {
	private static NotiManager singleton = new NotiManager();
	private ArrayList<Notification> notiList = new ArrayList<Notification>(); 
	
	public static NotiManager getInstance() {
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
