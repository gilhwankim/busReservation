package font;

import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FontCheck extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		//���̽��ڽ��� �ڹ�fx�� ��Ʈ�� �� �����ͼ� ����
        ChoiceBox<String> choiceBox = new ChoiceBox<String>();
        List<String> fontList = javafx.scene.text.Font.getFamilies();
        
        for(String fontName : fontList) {
        	choiceBox.getItems().add(fontName);
        }

        VBox vbox1 = new VBox(choiceBox);
        VBox vbox2 = new VBox();
        
        HBox hbox = new HBox(vbox2, vbox1);
        hbox.setSpacing(30.0);
        hbox.setPadding(new Insets(0, 50, 0, 50));
        
        
        vbox1.setAlignment(Pos.CENTER);
        vbox2.setAlignment(Pos.CENTER);
        
        
        Label korLabel = new Label("�����ٶ󸶹�");
        Label engLabel = new Label("abcdefABCD");
        korLabel.setStyle("-fx-font-size: 30;");
        engLabel.setStyle("-fx-font-size: 30;");
        
        
        vbox2.getChildren().add(korLabel);
        vbox2.getChildren().add(engLabel);
      
        //�ڽ����� �� ���� �ѱ۰� �������� ������
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				korLabel.setStyle("-fx-font-family: " + newValue + "; -fx-font-size: 30;");
				engLabel.setStyle("-fx-font-family: " + newValue + "; -fx-font-size: 30;");
			}
		});
        
        Scene scene = new Scene(hbox, 550, 150);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("FontTest");
        primaryStage.show();
		
	}	
	
	public static void main(String[] args) {
		Application.launch(args);		
	}
	
}