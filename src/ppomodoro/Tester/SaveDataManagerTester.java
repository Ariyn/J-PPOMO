package ppomodoro.Tester;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ppomodoro.Datas.PpomoTimeData;
import ppomodoro.Datas.Exceptions.NoXmlFileException;
import ppomodoro.Datas.XmlManager.SaveDataManager;

public class SaveDataManagerTester {
	int success = -1;
	SaveDataManager sdm = null;
	String path = "SaveData/testData.xml", originalPath = "/ppomodoro/Resources/originalSavedata.xml";
	
	@Before
	public void setup() throws Exception {
		this.sdm = SaveDataManager.getInstance();
		this.success = -1;
		
		this.sdm.setPath(path);
		this.sdm.setOriginalPath(originalPath);
	}
	
	@Test
	public void testPpomoLogLoad() {
		try {
			success = this.sdm.XMLParserCheck();
		} catch (NoXmlFileException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		if(success == SaveDataManager.succeedLoadFile) {
			ArrayList<PpomoTimeData> pl = this.sdm.loadPpomoLog();
		}
	}
	
	@Test
	public void testPpomoLogSave() {
		ArrayList<PpomoTimeData> ph = new ArrayList<PpomoTimeData>();
		PpomoTimeData ptd = new PpomoTimeData("test"); 
		ptd.setTime(ptd.start, 123456789);
		ptd.setTime(ptd.end, 123456789);
		
		ptd.setResult(true);
		
		ph.add(ptd);
		
		
		try {
			success = this.sdm.XMLParserCheck();
		} catch (NoXmlFileException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		if(success == SaveDataManager.succeedLoadFile) {
			this.sdm.savePpomoLog(ph);
		}
	}
	@Test
	public void testPpomoLoadAndSave() {
		try {
			success = this.sdm.XMLParserCheck();
		} catch (NoXmlFileException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		if(success == SaveDataManager.succeedLoadFile) {
			ArrayList<PpomoTimeData> pl = this.sdm.loadPpomoLog();
			pl.clear();
			
			this.sdm.savePpomoLog(pl);
		}
	}
}
