package ppomodoro.Tester;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import ppomodoro.Datas.PpomoTimeData;
import ppomodoro.Datas.XmlManager;
import ppomodoro.Datas.Exceptions.NoXmlFileException;

public class XmlManagerTester {
	private XmlManager xm;
	private int success = -1;
	String path = "SaveData/testData.xml", originalPath = "/ppomodoro/Resources/originalSavedata.xml";
	
	public void removeFile(String path) {
		File remover = new File(path);
		
		if(remover.exists()) {
			for(File i:remover.getParentFile().listFiles()) {
				System.out.println(i);
				i.delete();
			}
			remover.getParentFile().delete();
		}
	}
	
	@Before
	public void setUp() throws Exception {
		this.xm = new XmlManager();
		this.success = -1;
		this.xm.setPath(path);
		this.xm.setOriginalPath(originalPath);
	}
	
	@Test
	public void testCreateFile() {
		removeFile(path);
		
		File f = this.xm.openFile();
		assertTrue(f.exists());
	}
	
	@Test
	public void testReadFile() {
		File f = this.xm.openFile();
		assertTrue(f.exists());
	}
	
	@Test
	public void testXmlParserCheck() throws NoXmlFileException{
		
		try {
			success = this.xm.XMLParserCheck();
		} catch (NoXmlFileException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			System.out.println("wrong");
			throw e;
		}
		System.out.println(success);
		assertTrue(success == XmlManager.succeedLoadFile || success == XmlManager.createNewFile);
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
			success = this.xm.XMLParserCheck();
		} catch (NoXmlFileException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		if(success == XmlManager.succeedLoadFile) {
			this.xm.savePpomoLog(ph);
		}
	}
	
	@Test
	public void testPpomoCreateNewFile() {
		removeFile(path);
		
		try {
			success = this.xm.XMLParserCheck();
		} catch (NoXmlFileException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		assertTrue(success == XmlManager.createNewFile);
	}

	
	@Test
	public void testPpomoLogLoad() {
		try {
			success = this.xm.XMLParserCheck();
		} catch (NoXmlFileException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		if(success == XmlManager.succeedLoadFile) {
			ArrayList<PpomoTimeData> pl = this.xm.loadPpomoLog();
		}
	}

}
