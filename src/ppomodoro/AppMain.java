package ppomodoro;

import java.awt.AWTException;
import java.awt.Robot;

public class AppMain {

	public AppMain() {
		System.out.println(Thread.currentThread().getName() +": AppMain 호출");
//		AppMain.main = new MainScreen();
	}
	
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName()+": main 호출");
		javafx.application.Application.launch(MainScreen.class);	
	}
}


// timer, engine 클래스를 싱글톤으로 만들어서 메인에 스태틱으로 보관.
// 여기서 함수를 넘겨주는게 아니라 스크린 클래스가 직접 싱글톤을 잡도록.
