package ppomodoro.Datas.Exceptions;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class NoXmlFileException extends Exception {
	public NoXmlFileException() {
		super("ERROR\nCan't Create New save data.\nPlease re-check your folder.");
		
		PrintWriter pw;
		try {
			pw = new PrintWriter("error.log");
			printStackTrace(pw);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}	
}
