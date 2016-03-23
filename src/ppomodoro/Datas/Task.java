package ppomodoro.Datas;

import java.util.ArrayList;
import java.util.List;

public class Task {
	String name;
	public Task parent;
	
	public List<Task> children = new ArrayList<Task>();
	
	public Task() {
		
	}
	public Task(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}	
}