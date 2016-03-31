package ppomodoro.GTDDetail;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import ppomodoro.Datas.ProgramManager;
import ppomodoro.Datas.Task;

public class Controller implements Initializable, EventHandler<MouseEvent> {
	@FXML Label iconLabel;
	@FXML Label taskName;
	@FXML Label backButtonLabel;
	@FXML HBox headerBox;
	
	Task task;
	Screen s;
	
	ProgramManager pm;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.pm = ProgramManager.getInstance();
		
		ImageView iv = new ImageView(new Image("/ppomodoro/Resources/TriangleArrow-Left.png"));

		iv.setFitWidth(15);
		iv.setFitHeight(15);
		this.backButtonLabel.setGraphic(iv);
		
		this.taskName.setOnMouseClicked(this);
		this.iconLabel.setOnMouseClicked(this);
		this.backButtonLabel.setOnMouseClicked(this);
	}
	
	public void setMainScreen(Screen screen) {
		this.s = screen;
	}
	
	public void setTask(Task task) {
		this.task = task;
	}
	
	public void screenShow() {
		this.taskName.setText(this.task.getName());
		this.iconLabel.setGraphic(this.task.getIconView());
	}
	
	public void MoveScreen(int x, int y) {
		this.s.primaryStage.setX(x);
		this.s.primaryStage.setY(y);
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
					TextInputDialog dialog = new TextInputDialog("walter");
					dialog.setTitle(this.task.getName());
					dialog.setHeaderText("할일의 이름을 바꿉니다.");
					dialog.setContentText("새로운 이름 : ");

					Optional<String> result = dialog.showAndWait();
					result.ifPresent(name -> {
						this.task.setName(name);
					});
					
					Consumer<String> c1 = (x) -> System.out.println(x.toLowerCase());
				    c1.accept("Java2s.com");
					
					Bounds bounds = this.taskName.getBoundsInLocal();
					Bounds screenBounds = this.taskName.localToScene(bounds);
				} else if(id.equals("iconLabel")) {
					this.task.setIcon("glyphicons-124-message-out.png");
				}
			}
		} else if(event.getClickCount() == 1) {
			EventTarget et = event.getTarget();
			Class<?> c = et.getClass();
			
			if( c == Label.class ) {
				String id = ((Label)et).getId();

				if( id.equals("backButtonLabel") ) {
					System.out.println("clicked!");
					this.pm.closeGTDDetailWindow(this.s.primaryStage);
				}
			}
		}
		
		this.screenShow();
	}
}