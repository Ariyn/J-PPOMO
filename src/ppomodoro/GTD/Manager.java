package ppomodoro.GTD;

import java.util.ArrayList;
import java.util.List;

import ppomodoro.Datas.Task;

public class Manager {
	static Manager singleton;
	List<Task> taskList = new ArrayList<Task>();
	
	public Manager() {
		
	}
	public Manager getInstance() {
		if(Manager.singleton == null) {
			Manager.singleton = new Manager();
		}
		
		return Manager.singleton;
	}
	
	public void addNewTask(Task task) {
		this.taskList.add(task);
	}
	
	public void addNewTask(String name) {
		
	}

	public void addNewTask(String name, Task parent) {
		
	}
}
