package ppomodoro.GTD;

import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ppomodoro.Datas.ProgramManager;
import ppomodoro.Datas.Task;
import ppomodoro.Datas.TaskManager;

public class boxListCell extends ListCell<String>{
	Task task;
	TaskManager tm = TaskManager.getInstance();
	boolean notInit = true;
	
	public void handlerInit() {
		final Task _task = this.task;
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		    	ProgramManager pm = ProgramManager.getInstance();
		    	System.out.println("clicked");
		        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 2){
		            	// show new ppomodoro window
		            	System.out.println("double clicked");
		            	Stage ps = Controller.screen.primaryStage;
		            	pm.openGTDDetailWindow(ps, _task, ps.getX()+ps.getWidth(), ps.getY()+40);
		            }
		        }
		    }
		});
		
		this.notInit = false;
	}
	
	@Override
	public void updateItem(String text, boolean empty){
		super.updateItem(text,empty);
		
		this.task = this.tm.getTask(text);
		
	    if(this.task != null)
	    {
	    	if(this.notInit) {
				this.handlerInit();
			}
	    	
	        CellData data = new CellData();
	        data.init();
	        data.setInfo(this.task.getName());
	        setGraphic(data.getBox());
	        
	    }
	}
}
