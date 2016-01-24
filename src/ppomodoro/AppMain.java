package ppomodoro;

import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import ppomodoro.Datas.ProgramManager;
import ppomodoro.Tray.TrayManager;

public class AppMain {
	public static void main(String[] args) {		
		// new Test().testUrl();
		
		System.out.println(Thread.currentThread().getName()+": main 호출");
		
		 try {
			URL iconURL = new URL("http://a4.mzstatic.com/us/r30/Purple1/v4/8a/3a/1f/8a3a1f6c-ebc6-4bbd-bc61-bf27aed94494/icon128-2x.png");
			Image image = ImageIO.read(iconURL);
			
			//only for os x
			com.apple.eawt.Application.getApplication().setDockIconImage(image);
			com.apple.eawt.Application.getApplication().requestUserAttention(false);
		} catch (Exception e) {
		    // Won't work on Windows or Linux.
		}
		
		ProgramManager.getInstance();
		
		Platform.setImplicitExit( false );
		TrayManager.getInstance().addTray();
		javafx.application.Application.launch(MainScreen.class);
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
