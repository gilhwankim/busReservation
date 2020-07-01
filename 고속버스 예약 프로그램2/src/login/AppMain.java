package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AppMain extends Application{
	
	public static Stage Loginstage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Loginstage = primaryStage;
		
		Font.loadFont(getClass().getResourceAsStream("/font/NanumGothi.ttf"), 13);
		Font.loadFont(getClass().getResourceAsStream("/font/NanumGothicBold.ttf"), 13);
		Font.loadFont(getClass().getResourceAsStream("/font/NanumGothicExtraBold.ttf"), 13);
		//첫화면은 로그인으로 바꿔야됨
		Parent root = FXMLLoader.load(getClass().getResource("../fxml/Login.fxml"));
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toString());
		
		primaryStage.setTitle("로그인");
		primaryStage.setScene(scene);
		primaryStage.show();
//		TerminalList tt = new TerminalList();
	}
	
	public static void main(String[] args) {
		launch(args);
		
	
	}
}