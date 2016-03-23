package ppomodoro.GTD;

import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;

public class boxListCell extends ListCell<String>{
	
	@Override
	public void updateItem(String string, boolean empty)
	{
	    super.updateItem(string,empty);
	    if(string != null)
	    {
	        CellData data = new CellData();
	        data.init();
	        data.setInfo(string);
	        setGraphic(data.getBox());
	    }
	}
}
