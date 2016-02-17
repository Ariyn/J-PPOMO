package ppomodoro;

import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.application.Platform;

import ppomodoro.Tray.TrayManager;

public class AppMain {
	public static void main(String[] args) {		
		// new Test().testUrl();
		
		System.out.println(Thread.currentThread().getName()+": main 호출");
		
		 try {
			URL iconURL = AppMain.class.getResource("/ppomodoro/Resources/icon128-2x.png");
					
			Image image = ImageIO.read(iconURL);
			
			//only for os x
			com.apple.eawt.Application.getApplication().setDockIconImage(image);
			com.apple.eawt.Application.getApplication().requestUserAttention(false);
		} catch (Exception e) {
		    // Won't work on Windows or Linux.
		}
				
//		Platform.setImplicitExit( false );
		TrayManager.getInstance().addTray();
		javafx.application.Application.launch(MainScreen.class);
	}
}