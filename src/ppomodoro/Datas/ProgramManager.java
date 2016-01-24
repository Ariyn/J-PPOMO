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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.application.Application;

public class ProgramManager {
	private static ProgramManager singleton = new ProgramManager();
	
	
	private ArrayList<WindowListener> windowListenerList = new ArrayList<WindowListener>();
	// count between longBreaks
	private int ppomoCount = 0;
	private int breakCount = 0;
	private int failedCount = 0;
	
	private ArrayList<PpomoTimeData> ppomoHistory = new ArrayList<PpomoTimeData>();
	
	@SuppressWarnings("rawtypes")
	private Map<String, Class> configTypes = new HashMap<String, Class>();
	private Map<String, Object> config = new HashMap<String, Object>();
	
	private UserData ud;
	private Document xmlDocument;
	
	public ProgramManager() {
		
		XMLParserCheck();
		ud = new UserData(xmlDocument);
		
		
		System.out.println(ud.name);
		System.out.println(ud.email);
		System.out.println(ud.userId);
		
//		savePpomoLog();
		ppomoLogLoad();
		
		testConfigSet();
		loadConfig();
	}
	private void XMLParserCheck() {
		try {
			File file = new File("SaveData/testData.xml");
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild;
			
			docBuild = docBuildFact.newDocumentBuilder();
			xmlDocument = docBuild.parse(file);
			
			xmlDocument.getDocumentElement().normalize();
		} catch (ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			System.out.println("xml document is wrong");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("no xml file");
			e.printStackTrace();
		}
	}
	
	private void ppomoLogLoad() {
		GregorianCalendar today = new GregorianCalendar ();

		int year = today.get ( today.YEAR );
		int month = today.get ( today.MONTH ) + 1;
		int date = today.get ( today.DAY_OF_MONTH );
		
		String todayName = String.format("%d/%d/%d", year, month, date);
		
		NodeList ppomoList = xmlDocument.getElementsByTagName("ppomo");
		for(int i=0; i<ppomoList.getLength(); i++) {
			Element ppomoData = (Element)ppomoList.item(i);
			
			String type = ppomoData.getElementsByTagName("type").item(0).getTextContent();
			long startTime = Long.parseLong(ppomoData.getElementsByTagName("startTime").item(0).getTextContent());
			long endTime = Long.parseLong(ppomoData.getElementsByTagName("endTime").item(0).getTextContent()); 
			
			
			PpomoTimeData t = new PpomoTimeData(type);
			t.setTime("start", startTime);
			t.setTime("end", endTime);
			
			ppomoHistory.add(t);
		}
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
	
	private void saveConfig() {
		
	}
	
	//this save ppomo progress or such things
	private void savePpomoLog() {
		PrintWriter saveData = null;
		File saveDirectory = new File("SaveData");
		
		if(saveDirectory.exists() == false)
			saveDirectory.mkdirs();
		
		
//		private ArrayList<Long> timeList = new ArrayList<Long>(); 
//		private ArrayList<String> typeList = new ArrayList<String>();
//		
//		private String type; // "ppomo" or "break" or "long break"
//		private boolean result = false;
		
		System.out.println(ppomoHistory.size());
		Element ppomoLog = (Element)xmlDocument.getElementsByTagName("ppomoLog").item(0);
		for(PpomoTimeData p:ppomoHistory) {
			Node ppomo = xmlDocument.createElement("ppomo"); 
			
			Node type = (Node)xmlDocument.createElement("type");
			type.setTextContent(p.getType());
			ppomo.appendChild(type);
			
			Node startTime = (Node)xmlDocument.createElement("startTime");
			startTime.setTextContent(Long.toString(p.getTime("start")));
			ppomo.appendChild(startTime);
			
			Node endTime = (Node)xmlDocument.createElement("endTime");
			endTime.setTextContent(Long.toString(p.getTime("end")));
			ppomo.appendChild(endTime);
			
			Node result = (Node)xmlDocument.createElement("result");
			result.setTextContent(Boolean.toString(p.getResult()));
			ppomo.appendChild(result);
			
			ppomoLog.appendChild(ppomo);
		}
		
		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(xmlDocument);
			
			StreamResult result = new StreamResult(new File("SaveData/testData.xml"));
			
			transformer.transform(source, result);
//			saveData = new PrintWriter("SaveData/testData.xml");
//			saveData.write(xmlDocument.toString());
			
//			saveData.close();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	public static ProgramManager getInstance() {
		return singleton;
	}
	
	public void openWindow(WindowListener window) {
		windowListenerList.add(window);
	}
	

	public void closeWindow(WindowListener window) {
		window.closeWindow();
		windowListenerList.remove(window);
	}

	// TODO: save ppomodoro datas to file
	public void closeProgram() {
		savePpomoLog();
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
			System.out.println("error!");
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
	private ArrayList<Long> timeList = new ArrayList<Long>(); 
	private ArrayList<String> typeList = new ArrayList<String>();
	
	private String type; // "ppomo" or "break" or "long break"
	private boolean result = false;
	
	public PpomoTimeData(String type) {
		this.type = type;
		
		this.timeList.add((long) 0);
		this.timeList.add((long) 0);
		
		this.typeList.add("start");
		this.typeList.add("end");
	}
	
	public String getType() {
		return type;
	}
	
	public long getTime(String type) {
		int index = typeList.indexOf(type);
		long retVal = -1;
		
		if(0 <= index)
			retVal = timeList.get(index);
		return retVal;
	}
	
	public void setTime(String type, long value) {
		int index = typeList.indexOf(type);
		timeList.set(index, value);
	}
	
	public void setResult(boolean success) {
		this.result = success;
	}
	public boolean getResult() {
		return this.result;
	}
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