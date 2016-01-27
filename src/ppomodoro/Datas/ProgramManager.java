package ppomodoro.Datas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import ppomodoro.MainScreen;

public class ProgramManager {
	private static ProgramManager singleton = new ProgramManager();

	// count between longBreaks
	private int ppomoCount = 0;
	private int breakCount = 0;
	private int failedCount = 0;
	
	private ArrayList<PpomoTimeData> ppomoHistory = new ArrayList<PpomoTimeData>();
	
	private ArrayList<WindowListener> windowListenerList = new ArrayList<WindowListener>();
	private ArrayList<String> opendWindowList = new ArrayList<String>();
	private Map<String, Class<? extends Application>> windowList = new HashMap<String, Class<? extends Application>>();
	
	private UserData ud;
	
	
	//change this variables
	@SuppressWarnings("rawtypes")
	private Map<String, Class> configTypes = new HashMap<String, Class>();
	private Map<String, Object> config = new HashMap<String, Object>();
	
	
	public ProgramManager() {
//		ud = new UserData(xmlDocument);
		
		windowList.put("MainScreen", MainScreen.class);
		
//		savePpomoLog();
//		ppomoLogLoad();
//		
//		testConfigSet();
//		loadConfig();
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
		System.out.println("here");
		
		Platform.runLater(()-> {
			System.out.println("running later");
			try {
				System.out.println(String.format("%s, %d", windowName, opendWindowList.indexOf(windowName)));
				
				if(opendWindowList.indexOf(windowName) == -1) {
					System.out.println("inside here");
					newClass.newInstance().start(new Stage());
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
//		savePpomoLog();
	}
	
	public void controlPpomodoroSchedule() {
		
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
	
	// TODO: calculate ppomo type by ppomodoro history
	// TODO: does this really necessary?
	@SuppressWarnings("unused")
	public String calculatePpomoType() {
		String retVal = "test";
		int ppomoTerm = (int) config.get("long break term");
		int ppomoTimes = 0;
		
		for(PpomoTimeData i:ppomoHistory) {
			
		}
		
		return retVal;
	}
	
	// TODO: save every ppomodoro start and end and middle of ppomodoro
}

class UserData {
	private Document xmlDocument = null;
	public String name = null, email = null, userId = null;
	
	public UserData(Document xmlDocument) {
		this.xmlDocument = xmlDocument;
		
		getUserInfo();
	}
	
	private void getUserInfo() {
		NodeList userNodeList = xmlDocument.getElementsByTagName("user");
		if(1 <= userNodeList.getLength()) {
			Element ppomo = (Element)userNodeList.item(0);
			
			NodeList userName = ppomo.getElementsByTagName("name");
			if(1 <= userName.getLength()) {
				name = userName.item(0).getTextContent();
			}
			
			NodeList emailList = ppomo.getElementsByTagName("email"); 
			if(1 <= emailList.getLength()) {
				email = emailList.item(0).getTextContent();
			}
			
			NodeList userIdList = ppomo.getElementsByTagName("userId");
			if(1 <= userIdList.getLength()) {
				userId = userIdList.item(0).getTextContent();
			}
		}
	}
}