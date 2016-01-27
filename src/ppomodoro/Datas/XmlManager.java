package ppomodoro.Datas;

import java.io.*;

import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import org.xml.sax.SAXException;

import ppomodoro.Datas.Exceptions.NoXmlFileException;

public class XmlManager {
	private static XmlManager singleton = new XmlManager();
	private Document xmlDocument;
	
	private String path = null;
	private URL originalPath = null;
	
	public static int succeedLoadFile = 1, createNewFile = 2, failedLoadFile = 3;
	
	public XmlManager() {
		
	}
	
	public static XmlManager getInstance() {
		return singleton;
	}
	
	//TODO Change this to static file opener
	// if file already opend, then don't open new file
	public File openFile() {
		File file = new File(path);
		
		if(file.exists() == false) {
			file.getParentFile().mkdirs();
			
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return file;
	}
	public File openOriginalFile() {
		File file = null;
		try {
			file = new File(originalPath.toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return file;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	public void setOriginalPath(String path) {
//		"/ppomodoro/Resources/originalSavedata.xml"
		this.originalPath = getClass().getResource(path);
	}
	
	public int XMLParserCheck() throws NoXmlFileException {
		File file = openFile();
		
		int success = XMLParserCheck(file, 1);
		if(success == failedLoadFile) {
			throw new NoXmlFileException(); 
		}
		
		return success;
	}
	
	private int XMLParserCheck(File file, int count) {
		int retVal = succeedLoadFile;
		
		if(2 < count) {
			System.out.println("Count "+count);
			retVal = failedLoadFile;
		} else {
			DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = null;
			
			try {
				docBuild = docBuildFact.newDocumentBuilder();
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				xmlDocument = docBuild.parse(file);
				xmlDocument.getDocumentElement().normalize();
			} catch (SAXException | IOException e) {
				this.createNewOriginalFile(file);
				
				int _retVal = XMLParserCheck(file, count + 1);
				if(_retVal == succeedLoadFile) {
					retVal = createNewFile;
				} else {
					retVal = _retVal;
				}
			}
		}
		
		return retVal;
	}
	
	private void createNewOriginalFile(File file) {
		File originalSaveData = openOriginalFile();
		String path = file.getPath();
		
		try {
			PrintWriter pw = new PrintWriter(path);
			BufferedReader br = new BufferedReader(new FileReader(originalSaveData));
			String line = br.readLine();;
			
			while(line != null) {
				pw.println(line);
				line = br.readLine();
			}
			
			pw.close();
			br.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("no!");
			e.printStackTrace();
		}
	}

	public ArrayList<PpomoTimeData> loadPpomoLog() {
		ArrayList<PpomoTimeData> retVal = new ArrayList<PpomoTimeData>();
		GregorianCalendar today = new GregorianCalendar ();
		
		int year = today.get ( GregorianCalendar.YEAR );
		int month = today.get ( GregorianCalendar.MONTH ) + 1;
		int date = today.get ( GregorianCalendar.DAY_OF_MONTH );
		
		String todayName = String.format("%d/%d/%d", year, month, date);
		
		NodeList ppomoList = xmlDocument.getElementsByTagName("ppomo");
		for(int i=0; i<ppomoList.getLength(); i++) {
			Element ppomoData = (Element)ppomoList.item(i);
			
			String type = ppomoData.getElementsByTagName("type").item(0).getTextContent();
			long startTime = Long.parseLong(ppomoData.getElementsByTagName("startTime").item(0).getTextContent());
			long endTime = Long.parseLong(ppomoData.getElementsByTagName("endTime").item(0).getTextContent()); 
			
			PpomoTimeData t = new PpomoTimeData(type);
			t.setTime(t.start, startTime);
			t.setTime(t.end, endTime);
			
			retVal.add(t);
		}
		
		return retVal;
	}
//	
//	private void testConfigSet() {
//		// TODO: don't remove this function and check validation of config file
//		// which equals config and configTypes
//		
//		configTypes.put("ppomo time", Integer.class);
//		configTypes.put("break time", Integer.class);
//		configTypes.put("long break time", Integer.class);
//		
//		configTypes.put("long break term", Integer.class);
//		
//		config.put("ppomo time", 25);
//		config.put("break time", 5);
//		config.put("long break time", 30);
//		
//		config.put("long break term", 4);
//	}
//
//	// TODO: need to load from xml file
//	private boolean loadConfig() {
//		boolean error = false;
//		
//		for(String key:config.keySet()) {
//			if(config.get(key).getClass() != configTypes.get(key)) {
//				error = true;
//			}
//			
//		}
//		
//		return error;
//	}
//	
//private void saveConfig() {
//		
//	}
//	
	//this save ppomo progress or such things
	public void savePpomoLog(ArrayList<PpomoTimeData> ppomoHistory) {
		File saveDirectory = new File("SaveData");
		
		if(saveDirectory.exists() == false)
			saveDirectory.mkdirs();
		
		
//		private ArrayList<Long> timeList = new ArrayList<Long>(); 
//		private ArrayList<String> typeList = new ArrayList<String>();
//		
//		private String type; // "ppomo" or "break" or "long break"
//		private boolean result = false;
		
//		System.out.println(ppomoHistory.size());
		Element ppomoLog = (Element)xmlDocument.getElementsByTagName("ppomoLog").item(0);
		for(PpomoTimeData p:ppomoHistory) {
			Node ppomo = xmlDocument.createElement("ppomo"); 
			
			Node type = (Node)xmlDocument.createElement("type");
			type.setTextContent(p.getType());
			ppomo.appendChild(type);
			
			Node startTime = (Node)xmlDocument.createElement("startTime");
			startTime.setTextContent(Long.toString(p.getTime(p.start)));
			ppomo.appendChild(startTime);
			
			Node endTime = (Node)xmlDocument.createElement("endTime");
			endTime.setTextContent(Long.toString(p.getTime(p.end)));
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
}
