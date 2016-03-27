package ppomodoro.Datas;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class Task {
	String name;
	public Task parent;
	String icon;
	boolean isImage = false;
	double size = 256;
	
	public List<Task> children = new ArrayList<Task>();
	
	public Task() {
		this.setIcon("glyphicons-87-display.png"); 
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
	
	public void setIcon(String icon) {
		this.icon = "/ppomodoro/Resources/glyphicons/"+icon;
		this.isImage = true;
	}
	public void setIcon(String icon, boolean isImage) {
		this.icon = icon;
		this.isImage = isImage;
	}
	
	public Image getIcon() {
		Image retVal = null;
		retVal = new Image(this.icon);
//		if (this.isImage) {
//			new Image(this.icon);
//		} else {
//			
//		}
		
		return retVal;
	}
	
	public ImageView getIconView() {
		Image img = this.getIcon();
		ImageView iv = new ImageView(img);
		Circle clip = new Circle(60, 60, 60);
		iv.setClip(clip);
		iv.setFitWidth(120);
		iv.setFitHeight(120);
		
		iv.setStyle("-fx-background-color: BLACK");
//		iv.setStyle("-fx-border-radius:75px");
		return iv;
	}
}
