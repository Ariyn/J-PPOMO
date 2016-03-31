package ppomodoro.GTD;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import jdk.nashorn.internal.codegen.Label;
import ppomodoro.Datas.ProgramManager;
import ppomodoro.Datas.Task;
import ppomodoro.Datas.TaskManager;

public class Controller implements Initializable {
	static Screen screen;
	@FXML private ListView<String> listView;
	@FXML private HBox headerBox;
	@FXML Button addTaskButton;
	
	ProgramManager pm = ProgramManager.getInstance();
	ObservableList<String> oList = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		final Screen s = this.screen;
		
		
		listView.setItems(TaskManager.getInstance().getObservableTaskList());
		listView.setCellFactory(p-> new boxListCell());

		addTaskButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					System.out.println("new event!");
				}
			}
		});
	}
	
	public void openDetailWindow() {
		
	}
	
	public void setMainScreen(Screen s) {
		Controller.screen = s;
	}
	
	public void removeFocus() {
		headerBox.requestFocus();
	}
}