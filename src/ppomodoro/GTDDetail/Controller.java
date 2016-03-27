package ppomodoro.GTDDetail;

import java.net.URL;
import java.util.ResourceBundle;

import com.sun.javafx.scene.control.skin.LabeledText;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ppomodoro.Datas.Task;

public class Controller implements Initializable, EventHandler<MouseEvent> {
	@FXML Label iconLabel;
	@FXML Label taskName;
	
	Task task;
	Screen s;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void setMainScreen(Screen screen) {
		this.s = screen;
	}
	
	public void setTask(Task task) {
		this.task = task;
		
		this.taskName.setOnMouseClicked(this);
		this.iconLabel.setOnMouseClicked(this);
	}
	
	public void screenShow() {
		this.taskName.setText(this.task.getName());
		this.iconLabel.setGraphic(this.task.getIconView());
	}
	
	@Override
	public void handle(MouseEvent event) {
		if(event.getClickCount() == 2){
			EventTarget et = event.getTarget();
			Class<?> c = et.getClass();
			
			if(c == LabeledText.class || c == Label.class) {
				if(c == LabeledText.class)
					et = ((LabeledText)et).getParent();
				String id = ((Label)et).getId();
				
				System.out.println(id);
				if(id.equals("taskName")) {
					this.task.setName("testestsdfsf");
					
					Bounds bounds = this.taskName.getBoundsInLocal();
					Bounds screenBounds = this.taskName.localToScene(bounds);
					
					System.out.println(bounds);
					System.out.println(screenBounds);
					System.out.println(this.task.getName());
				} else if(id.equals("iconLabel")) {
					this.task.setIcon("glyphicons-124-message-out.png");
				}
			}
		}
		
		this.screenShow();
	}
}