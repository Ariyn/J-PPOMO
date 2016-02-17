package ppomodoro.Datas;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class UserData {
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
