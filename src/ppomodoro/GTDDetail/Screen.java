package ppomodoro.GTDDetail;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ppomodoro.PpomodoroWindowInterface;
import ppomodoro.Datas.PpomoTimer;
import ppomodoro.Datas.ProgramManager;
import ppomodoro.Datas.Task;
import ppomodoro.notification.NotiManager;

public class Screen extends Application implements PpomodoroWindowInterface {
	Stage primaryStage;
	FXMLLoader fxml;
	
	Controller c;
	NotiManager m;
	PpomoTimer pt;
	ProgramManager pm;
	
	Parent root;
	
	@Override
	public void init() throws Exception {
		System.out.println(Thread.currentThread().getName()+": init GTD Detail screen");
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage = arg0;
		
		fxml = new FXMLLoader(getClass().getResource("GTDDetail.fxml"));
		
		try {
			root = fxml.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
		}
		
		c = (Controller)fxml.getController();
		c.setMainScreen(this);
		Scene newScene = new Scene(root);
		
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(newScene);
		
		primaryStage.xProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				
			}
		});
		
		arg0.show();
	}
	
	@Override
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	public void setTask(Task task) {
		this.c.setTask(task);
		this.c.screenShow();
	}
}
