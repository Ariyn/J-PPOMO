package ppomodoro.Tray;

import java.awt.AWTException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.stage.Stage;
import ppomodoro.MainScreen;
import ppomodoro.Datas.ProgramManager;

public class TrayManager {
	private static TrayManager singleton = new TrayManager();
	
	private MenuItem timerItem;
	private MenuItem showItem;
	private MenuItem exitItem;
	
	private TrayIcon ti = null;
	
	private ActionListener showListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
//			System.out.println(Platform);
			String cmd = e.getActionCommand();
			
			if(cmd == "show") {
				Platform.runLater(()-> {
					System.out.println("hger");
					try {
						new MainScreen().start(new Stage());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				});
			} else if(cmd == "exit") {
				System.out.println("exit");
				ProgramManager pm = ProgramManager.getInstance();
				
				pm.closeProgram();
				Platform.exit();
				System.exit(0);

			}
			//ti.displaymessage
		}
	};
	
	public void addTray() {
		//stackoverflow.com/questions/14626550/to-hide-javafx-fxml-or-javafx-swing-application-to-system-tray
		
		SystemTray st = SystemTray.getSystemTray();
		Image image = null;
		
		//stackoverflow.com/questions/10123735/get-effective-screen-size-from-java
		
		try {
			//TODO: not allowed to use. need to remove on release version.
            URL url = new URL("http://a4.mzstatic.com/us/r30/Purple1/v4/8a/3a/1f/8a3a1f6c-ebc6-4bbd-bc61-bf27aed94494/icon128-2x.png");
            image = ImageIO.read(url);
        } catch (IOException ex) {
            System.out.println(ex);
        }
		
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
		
		ti = new TrayIcon(image, "Test", pm);
		
		try {
			st.add(ti);
		} catch(AWTException e) {
			System.err.println("can't add tray");
		}
	}
	
	public static TrayManager getInstance() {
		return singleton;
	}
	
	public void setTimerText(String time) {
		timerItem.setLabel(time);
	}
}
