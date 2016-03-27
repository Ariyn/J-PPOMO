package ppomodoro.GTD;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class CellData {
	@FXML Pane layoutBox;
	@FXML Label taskName;
	
	public CellData() {
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("listCell.fxml"));
		fxml.setController(this);
		
		try {
			fxml.load();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public void init() {
		layoutBox.setPrefHeight(50);
	}
	
	public void setInfo(String string) {
		taskName.setText(string);
	}
	
	public Pane getBox() {
		return this.layoutBox;
	}	
}
