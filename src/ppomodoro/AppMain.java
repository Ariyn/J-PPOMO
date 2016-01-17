package ppomodoro;

import javafx.application.Platform;
import ppomodoro.Tray.TrayManager;

public class AppMain {
	public static void main(String[] args) {		
		// new Test().testUrl();
		
		System.out.println(Thread.currentThread().getName()+": main 호출");	
		
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
