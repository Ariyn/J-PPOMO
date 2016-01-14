package ppomodoro;

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
import javafx.stage.Stage;

import ppomodoro.Datas.ProgramManager;
import ppomodoro.Resources.*;

public class AppMain {
	private static TrayIcon ti = null;
	
	private static MainScreen ms;
	private final static ActionListener showListener = new ActionListener() {
	
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
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
			} else if(cmd == "exit") {
				System.out.println("exit");
				ProgramManager pm = ProgramManager.getInstance();
				pm.closeProgram();
			}
			
		
//			ti.displayMessage("Some message.",
//                    "Some other message.",
//                    TrayIcon.MessageType.INFO);
		}
	};
	
	public static void main(String[] args) {
		//stackoverflow.com/questions/14626550/to-hide-javafx-fxml-or-javafx-swing-application-to-system-tray
		
		addTray();
//		new Test().testUrl();
		
		System.out.println(Thread.currentThread().getName()+": main 호출");	
		
		javafx.application.Application.launch(MainScreen.class);	
	}
	
	public static void addTray() {
		Platform.setImplicitExit( false );
		
		SystemTray st = SystemTray.getSystemTray();
		java.awt.Image image = null;
		try {
            URL url = new URL("http://www.digitalphotoartistry.com/rose1.jpg");
            image = ImageIO.read(url);
        } catch (IOException ex) {
            System.out.println(ex);
        }
		
		PopupMenu pm = new PopupMenu();
		
		MenuItem showItem = new MenuItem("show");
		showItem.addActionListener(showListener);
		pm.add(showItem);
		
		MenuItem exitItem = new MenuItem("exit");
		exitItem.addActionListener(showListener);
		pm.add(exitItem);
		
		ti = new TrayIcon(image, "Test", pm);
		
		try {
			st.add(ti);
		} catch(AWTException e) {
			System.err.println("can't add tray");
		}
	}
}

class Test {
	@SuppressWarnings("static-access")
	public void testUrl() {
		System.out.println(getClass().getResource("/ppomodoro/Resources/success.wav"));
	}
}

// timer, engine 클래스를 싱글톤으로 만들어서 메인에 스태틱으로 보관.
// 여기서 함수를 넘겨주는게 아니라 스크린 클래스가 직접 싱글톤을 잡도록.
