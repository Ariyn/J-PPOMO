package ppomodoro.Datas;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskManager {
	static TaskManager singleton = null;
	ArrayList<Task> tasks = new ArrayList<Task>();
	
	public TaskManager() {
		this.test();
	}
	
	private void test() {
		this.tasks.add(new Task());
		this.tasks.add(new Task());
		this.tasks.add(new Task());
		
		this.tasks.get(0).setName("test1");
		this.tasks.get(1).setName("test2");
		this.tasks.get(2).setName("test3");
		
		System.out.println(this.tasks);
	}
	
	public static TaskManager getInstance() {
		if(TaskManager.singleton == null) {
			TaskManager.singleton = new TaskManager();
		}
		
		return TaskManager.singleton;
	}
	
	public void addTask(Task task) {
		this.tasks.add(task);
	}
	
	public ArrayList<Task> getTasks() {
		return this.tasks;
	}
	
	public Task getTask(int index) {
		return this.tasks.get(index);
	}
	
	public Task getTask(String taskName) {
		Task retVal = null;
		
		for(Task t:this.tasks) {
			if(t.getName().equals(taskName)) {
				retVal = t;
				break;
			}
		}
		return retVal;
//		return this.tasks.stream().filter(item -> item.getName().equals(taskName)).collect(Collectors.toList()).get(0);
	}
	
	public ObservableList<String> getObservableTaskList() {
		ObservableList<String> ol = null;
		
		ArrayList<String> newArr = new ArrayList<String>();
		for(Task t:this.tasks) {
			newArr.add(t.getName());
		}
		
		ol = FXCollections.observableArrayList(newArr);
		
		return ol;
	}
}
