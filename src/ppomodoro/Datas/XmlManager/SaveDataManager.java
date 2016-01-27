package ppomodoro.Datas.XmlManager;

import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ppomodoro.Datas.PpomoTimeData;
import ppomodoro.Datas.UserData;
import ppomodoro.Datas.Exceptions.NoXmlFileException;

public class SaveDataManager extends XmlManager {
	protected static SaveDataManager singleton = new SaveDataManager();
	public boolean isNewFile = false;
	
	public SaveDataManager() {
		setPath("SaveData/testData.xml");
		setOriginalPath("/ppomodoro/Resources/originalSavedata.xml");	
	}
	
	public static SaveDataManager getInstance() {
		return singleton;
	}
	
	public void saveUserData(UserData ud) {
		
	}
	
	//TODO remove history every day?? or keep it??
	
	public void savePpomoLog(ArrayList<PpomoTimeData> ppomoHistory) {
		File saveDirectory = new File(path).getParentFile();
		
		if(saveDirectory.exists() == false)
			saveDirectory.mkdirs();
		
		Element ppomoLog = (Element)xmlDocument.getElementsByTagName("ppomoLog").item(0);
		NodeList nl = ppomoLog.getChildNodes();
		int size = nl.getLength();
		
		for(int i=0; i<size; ++i) {
			ppomoLog.removeChild(nl.item(0));
		}
		
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
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
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
}
