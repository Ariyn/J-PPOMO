package ppomodoro.GTD;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import jdk.nashorn.internal.codegen.Label;

public class Controller implements Initializable {
	Screen screen;
	@FXML private ListView<String> listView;
	
	ObservableList<String> oList = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		oList.add("test");
		oList.add("test2");
		oList.add("test3");
		
		listView.setItems(oList);
		listView.setCellFactory(new Callback<ListView<String>, javafx.scene.control.ListCell<String>>(){
			@Override
			public ListCell<String> call(ListView<String> listView) {
				return new boxListCell();
			}
		});
	}
	
	public void setMainScreen(Screen s) {
		this.screen = s;
	}

}
