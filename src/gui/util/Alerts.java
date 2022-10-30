package gui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {
	
	public static void showAlert(String title, String hearder, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(hearder);
		alert.setContentText(content);
		alert.show();
	}

}
