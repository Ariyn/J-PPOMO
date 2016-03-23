package ppomodoro.notification;

import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.ArrayList;

import javafx.stage.Stage;
import ppomodoro.Datas.SoundManager;

// Notification Manager is singleton
// this takes care notification init, destroy, control number of notification

//TODO: remove notification a little after
//TODO: doesn't reset index number when user close the noti window;
public class NotiManager {
	private static NotiManager singleton = null;
	private ArrayList<Notification> notiList = new ArrayList<Notification>();
	
	private Notification[] notiPositionList = {null, null, null, null, null};
	private int location = -1;
	
	private Rectangle taskBarSize;
	private DisplayMode dm;
	
	public NotiManager() {
		taskBarSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
	}
	
	public static NotiManager getInstance() {
		if(singleton == null)
			singleton = new NotiManager();
		
		return singleton;
	}
	
	public boolean notificate(String message) {
		boolean retVal;
		Notification _noti = new Notification();
		notiList.add(_noti);
		
		location = (location + 1)%5;
		System.out.println(location);
		
		try {
			Notification prevNoti = notiPositionList[location];
			
			notiPositionList[location] = null;
			
			prevNoti.stop();
			notiList.remove(prevNoti);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
		notiPositionList[location] = _noti;
		Stage stage = new Stage();
		
		try {
			// large 240x100
			// margin 10
			stage.setX(dm.getWidth()-240 - 10);
			stage.setY(location*(110)+taskBarSize.getMinY()+10);
			
			
			_noti.start(stage);
			_noti.setMessage(message);
			
			SoundManager.getInstance().playSound("ding");
			retVal = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			retVal = false;
		}
		
		try {
//			com.apple.eawt.Application.getApplication().requestUserAttention(true);
		} catch (Exception e) {
			
		}
		
		return retVal;
	}
	
	public void setBadge(String arg) {
//		com.apple.eawt.Application.getApplication().setDockIconBadge(arg);
	}
}
