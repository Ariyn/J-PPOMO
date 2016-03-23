package ppomodoro.Tray;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import ppomodoro.AppMain;
import ppomodoro.Datas.ProgramManager;

public class TrayManager {
	private static TrayManager singleton = new TrayManager();
	
	private MenuItem timerItem;
	private MenuItem showItem;
	private MenuItem exitItem;
	
	private TrayIcon ti = null;
	private ProgramManager pm = ProgramManager.getInstance();
	
	private ActionListener showListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
//			System.out.println(Platform);
			String cmd = e.getActionCommand();
			
			if(cmd == "show") {
				System.out.println("works here");
				pm.openWindow("MainScreen");
			} else if(cmd == "exit") {
				System.out.println("exit");		
				Platform.exit();
				System.exit(0);
			}
			//ti.displaymessage
		}
	};
	
	public void addTray() {
		//stackoverflow.com/questions/14626550/to-hide-javafx-fxml-or-javafx-swing-application-to-system-tray
		
		SystemTray st = SystemTray.getSystemTray();
		//stackoverflow.com/questions/10123735/get-effective-screen-size-from-java
		
		PopupMenu pm = new PopupMenu();
		
		showItem = new MenuItem("show");
		showItem.addActionListener(showListener);
		pm.add(showItem);
		
		exitItem = new MenuItem("exit");
		exitItem.addActionListener(showListener);
		pm.add(exitItem);
		
		timerItem = new MenuItem("00:00");
		timerItem.setEnabled(false);
		
		pm.add(timerItem);
		
		try {
			//TODO: not allowed to use. need to remove on release version.
            URL url = getClass().getResource(AppMain.iconURL);
            System.out.println(url);
            System.out.println(getClass());

            ti = new TrayIcon(ImageIO.read(url), "Test", pm);
            ti.setImageAutoSize(true);
            
            st.add(ti);
			System.out.println(ti);
        } catch (IOException | AWTException ex) {
            System.out.println(ex);
        }
	}
	
	public static TrayManager getInstance() {
		return singleton;
	}
	
	public void setTimerText(String time) {
		timerItem.setLabel(time);
	}
}
