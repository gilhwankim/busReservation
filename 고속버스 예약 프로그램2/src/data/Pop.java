package data;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Pop {
	public Label textLabel;
	public Button okbtn;
	public Stage stage;
	
	public Pop() {
		AnchorPane ac = null;
		stage = new Stage();
		try {
			ac = FXMLLoader.load(getClass().getResource("../fxml/Popup.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		okbtn = (Button)ac.lookup("#okbtn");
		okbtn.setText("Ȯ��");
		okbtn.setOnAction((e)->stage.close());
		textLabel = (Label)ac.lookup("#textLabel");
		Scene scene = new Scene(ac);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toString());
		stage.setScene(scene);
	}
	//�˾� �޼��� ���� 
	public void popupMsg(String label) {
		textLabel.setAlignment(Pos.CENTER);
		textLabel.setText(label);
		stage.show();
	}
}

