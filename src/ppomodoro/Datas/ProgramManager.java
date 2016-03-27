package ppomodoro.Datas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.Marshaller.Listener;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import ppomodoro.MainScreen;
import ppomodoro.PpomodoroWindowInterface;
import ppomodoro.Datas.Exceptions.NoXmlFileException;
import ppomodoro.Datas.XmlManager.SaveDataManager;
import ppomodoro.Datas.XmlManager.XmlManager;
import ppomodoro.GTDDetail.Screen;


//TODO: save every ppomodoro start and end and middle of ppomodoro
public class ProgramManager {
	private static ProgramManager singleton = new ProgramManager();

	private SaveDataManager sdm = null;
	
	// count between longBreaks
	private int ppomoCount = 0;
	private int breakCount = 0;
	private int failedCount = 0;
	
	// TODO really?? here?? not in ppomoTimer???
	private ArrayList<PpomoTimeData> ppomoHistory = null;
	
	private ArrayList<WindowListener> windowListenerList = new ArrayList<WindowListener>();
	private ArrayList<String> opendWindowList = new ArrayList<String>();
	private Map<String, Class<? extends Application>> windowList = new HashMap<String, Class<? extends Application>>();
	
	//TODO change this variables
	@SuppressWarnings("rawtypes")
	private Map<String, Class> configTypes = new HashMap<String, Class>();
	private Map<String, Object> config = new HashMap<String, Object>();
	
	
	public ProgramManager() {
//		ud = new UserData(xmlDocument);
		
		// TODO change this as xml
		windowList.put("MainScreen", MainScreen.class);
		windowList.put("GTDList", ppomodoro.GTD.Screen.class);
		windowList.put("GTDDetail", ppomodoro.GTDDetail.Screen.class);
		
		addmitExitListener();
		
		sdm = SaveDataManager.getInstance();
		try {
			int d = sdm.XMLParserCheck();
			
			if(d == XmlManager.createNewFile) {
				sdm.isNewFile = true;
			}
		} catch (NoXmlFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ppomoHistory = sdm.loadPpomoLog();
//		System.out.println("work here");
		
		
		testConfigSet();
		loadConfig();
	}
	
	private void addmitExitListener() {
		Runtime.getRuntime().addShutdownHook(new Thread( () -> {
			System.out.println("done!");
			closeProgram();
		}));
	}
	
	public static ProgramManager getInstance() {
		return singleton;
	}
	
	public void addListener(WindowListener window) {
		windowListenerList.add(window);
		opendWindowList.add(window.getName());
	}
	public void openWindow(String windowName) {
		Class<? extends Application> newClass = this.windowList.get(windowName);

		_openWindow(newClass, windowName, -1, -1);
	}
	
	public void openWindow(Stage parent, String windowName) {
		Class<? extends Application> newClass = this.windowList.get(windowName);

		_openWindow(newClass, windowName, parent.getX()+parent.getWidth(), parent.getY());
	}
	public void openWindow(Stage parent, String windowName, double x, double y) {
		Class<? extends Application> newClass = this.windowList.get(windowName);

		_openWindow(newClass, windowName, x, y);
	}
	public void openGTDDetailWindow(Stage parent, Task task, double x, double y) {
		String windowName = "GTDDetail";
		Class<? extends Application> newClass = this.windowList.get(windowName);

		Platform.runLater(()-> {
			System.out.println("running later");
			
			try {
				System.out.println(String.format("%s, %d", windowName, opendWindowList.indexOf(windowName)));
				
				if(opendWindowList.indexOf(windowName) == -1) {
					System.out.println("inside here");
					Stage s = new Stage();
					if(x != -1)
						s.setX(x);
					if(y != -1)
						s.setY(y);
					
					ppomodoro.GTDDetail.Screen screen = (Screen) newClass.newInstance();
					screen.start(s);
					screen.setTask(task);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}
	private void _openWindow(Class<? extends Application> newClass, String windowName, double locationX, double locationY) {
		
		Platform.runLater(()-> {
			System.out.println("running later");
			
			try {
				System.out.println(String.format("%s, %d", windowName, opendWindowList.indexOf(windowName)));
				
				if(opendWindowList.indexOf(windowName) == -1) {
					System.out.println("inside here");
					Stage s = new Stage();
					if(locationX != -1)
						s.setX(locationX);
					if(locationY != -1)
						s.setY(locationY);
					
					newClass.newInstance().start(s);					
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}
	

	public void closeWindow(WindowListener window) {
		window.closeWindow();
		windowListenerList.remove(window);
		opendWindowList.remove(window.getName());
	}

	// TODO: save ppomodoro datas to file
	public void closeProgram() {
		sdm.savePpomoLog(ppomoHistory);
	}
	
	
	// TODO: what is this function for??
	public void controlPpomodoroSchedule() {
		
	}
	
	private void testConfigSet() {
	// TODO: don't remove this function and check validation of config file
	// which equals config and configTypes
	
	configTypes.put("ppomo time", Integer.class);
	configTypes.put("break time", Integer.class);
	configTypes.put("long break time", Integer.class);
	
	configTypes.put("long break term", Integer.class);
	
	config.put("ppomo time", 25);
	config.put("break time", 5);
	config.put("long break time", 30);
	
	config.put("long break term", 4);
}

// TODO: need to load from xml file
private boolean loadConfig() {
	boolean error = false;
	
	for(String key:config.keySet()) {
		if(config.get(key).getClass() != configTypes.get(key)) {
			error = true;
		}
	}
	
	return error;
}
	
	public int setPpomoStart() {
		String type = checkPpomoType();
		int minute = -1;
		
		if(type != "error") {
			PpomoTimeData ptd = new PpomoTimeData(type);
			ptd.setTime(ptd.start, System.currentTimeMillis());
				
			this.ppomoHistory.add(ptd);
			
			minute = (int) config.get(type+" time");
		} else {
			// TODO: save error log
			System.out.println("error!");
		}
		
		return minute;
	}
	
	//TODO: need break after failed ppomo too!
	public void setPpomoEnd(boolean result) {
		PpomoTimeData ptd = this.ppomoHistory.get(this.ppomoHistory.size()-1);
		ptd.setTime(ptd.end, System.currentTimeMillis());
		ptd.setResult(result);
		
		if(result) {
			String type = ptd.getType();
			if(type == "ppomo")
				ppomoCount += 1;
			else if(type == "break")
				breakCount += 1;
			else if(type == "long break") {
				ppomoCount = 0;
				breakCount = 0;
				failedCount = 0;
			}
		} else {
			//TODO: what about failed break time???    not now.
			failedCount += 1;
		}
	}
	
	public String getThisPpomoType() {
		return ppomoHistory.get(ppomoHistory.size()-1).getType();
	}

	// TODO: check ppomo time or break time here.
	public String checkPpomoType() {
		String retVal = "";
		
		if(this.ppomoCount == this.breakCount) {
			retVal = "ppomo";
		} else if(this.ppomoCount == this.breakCount + 1) {
			retVal = "break";
			
			if(this.ppomoCount == (int)this.config.get("long break term")) {
				retVal = "long break";
			}
		} else {
			retVal = "error";
		}
		return retVal;
	}
}