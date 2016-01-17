package ppomodoro.Datas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;

public class ProgramManager {
	private static ProgramManager singleton = new ProgramManager();
	private ArrayList<Application> windowList = new ArrayList<Application>();
	@SuppressWarnings("rawtypes")
	private ArrayList<Class> windowNameList = new ArrayList<Class>();
	
	// count between longBreaks
	private int ppomoCount = 0;
	private int breakCount = 0;
	private int failedCount = 0;
	
	private ArrayList<PpomoTimeData> ppomoHistory = new ArrayList<PpomoTimeData>();
	
	@SuppressWarnings("rawtypes")
	private Map<String, Class> configTypes = new HashMap<String, Class>();
	private Map<String, Object> config = new HashMap<String, Object>();
	
	public ProgramManager() {
		testConfigSet();
		loadConfig();
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
	
	
	public static ProgramManager getInstance() {
		return singleton;
	}
	
	public void openWindow(Application window, @SuppressWarnings("rawtypes") Class c) {
		windowList.add(window);
		windowNameList.add(c);
		System.out.println(window.toString());
	}
	

	public void closeWindow(Application window, @SuppressWarnings("rawtypes") Class c) {
		c.cast(window);
		
		windowList.remove(window);
		windowNameList.remove(c);
	}

	// TODO: save ppomodoro datas to file
	public void closeProgram() {

	}
	
	public void controlPpomodoroSchedule() {
		
	}
	
	public int setPpomoStart() {
		String type = checkPpomoType();
		int minute = -1;
		
		if(type != "error") {
			PpomoTimeData ptd = new PpomoTimeData(type);
			ptd.setTime("start", System.currentTimeMillis());
			
			this.ppomoHistory.add(ptd);
			
			minute = (int) config.get(type+" time");
		} else {
			// TODO: save error log
		}
		
		return minute;
	}
	
	//TODO: need break after failed ppomo too!
	public void setPpomoEnd(boolean result) {
		PpomoTimeData ptd = this.ppomoHistory.get(this.ppomoHistory.size()-1);
		ptd.setTime("end", System.currentTimeMillis());
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

class PpomoTimeData {
	private long[] timeArray = {0,0}; 
	private String[] typeList = {"start", "end"};
	
	private String type; // "ppomo" or "break" or "long break"
	private boolean result = false;
	
	public PpomoTimeData(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public long getTime(String type) {
		long retVal = 0;
		for(int i=0;i<typeList.length;i++) {
			if(typeList[i] == type) {
				retVal = timeArray[i];
				break;
			}
		}
		
		return retVal;
	}
	
	public void setTime(String type, long value) {
		for(int i=0;i<typeList.length;i++) {
			if(typeList[i] == type) {
				timeArray[i] = value;
				break;
			}
		}
	}
	
	public void setResult(boolean success) {
		this.result = success;
	}
	public boolean getResult() {
		return this.result;
	}
	
	
}