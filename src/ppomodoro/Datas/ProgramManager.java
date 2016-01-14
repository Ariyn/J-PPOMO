package ppomodoro.Datas;

import java.util.ArrayList;

import javafx.application.Application;

public class ProgramManager {
	private static ProgramManager singleton = new ProgramManager();
	private ArrayList<Application> windowList = new ArrayList<Application>();
	@SuppressWarnings("rawtypes")
	private ArrayList<Class> windowNameList = new ArrayList<Class>();
	
	public static ProgramManager getInstance() {
		return singleton;
	}
	
	public void openWindow(Application window, @SuppressWarnings("rawtypes") Class c) {
		windowList.add(window);
		windowNameList.add(c);
		System.out.println(window.toString());
	}
	
	public void closeWindow(Application window, @SuppressWarnings("rawtypes") Class c) {
		windowList.remove(window);
		windowNameList.remove(c);
	}
	
	public void closeProgram() {

	}
}
