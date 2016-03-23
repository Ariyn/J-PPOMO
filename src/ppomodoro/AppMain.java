package ppomodoro;

import java.io.File;
//import java.awt.Image;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import javafx.application.Platform;
//import javafx.scene.image.Image;
import ppomodoro.GTD.Screen;
import ppomodoro.Tray.TrayManager;

public class AppMain {
	public static String iconURL = "/ppomodoro/Resources/icon128-2x.png";
	public static void main(String[] args) throws IOException {		
		// new Test().testUrl();
		
		System.out.println(Thread.currentThread().getName()+": main �샇異�");
		
//		 try {
//			URL iconURL = AppMain.class.getResource("\\Resources\\icon128-2x.png");
//					
//			Image image = ImageIO.read(iconURL);
//			//only for os x
//			com.apple.eawt.Application.getApplication().setDockIconImage(image);
//			com.apple.eawt.Application.getApplication().requestUserAttention(false);
//		} catch (Exception e) {
//		    // Won't work on Windows or Linux.
//			System.out.println(e);
//		}
				
//		Platform.setImplicitExit( false );
//		TrayManager.getInstance().addTray();
//		javafx.application.Application.launch(MainScreen.class);
		javafx.application.Application.launch(Screen.class);
	}
}