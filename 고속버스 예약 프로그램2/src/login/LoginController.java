package login;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginController implements Initializable{
	
	private @FXML TextField txt_id; //���̵� �ؽ�Ʈ �ʵ�
	private @FXML PasswordField txt_password; //�н����� �ʵ�
	private @FXML Button loginbtn; //�α��� ��ư
	
	private DAO dao = DAO.getinstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		enterFire(txt_id, loginbtn);
		enterFire(txt_password, loginbtn);
	}

	//�α���
	public void GoLogin(ActionEvent event) throws Exception {
		//����ڰ� �Է��� ID, Password�� DAO�� ���� DB�����Ϳ� ���� ����
		int success = dao.GoLogin(txt_id.getText(), txt_password.getText());
		
		if(success==1) {
			System.out.println("�α��� ����");
			//�α��� ���� ���� ����
			dao.loginn.setId(txt_id.getText());
			dao.loginn.setPassword(txt_password.getText());
			
			//�α��� â ���� �� ���� ������ ���
			Stage clo = (Stage)loginbtn.getScene().getWindow();
			clo.close();
			
			Parent root = FXMLLoader.load(getClass().getResource("../fxml/Reservation.fxml"));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/style.css").toString());
			Stage stage = new Stage();
			stage.setTitle("����");
			stage.setScene(scene);
			stage.show();
		} else if(success==0 || success==-1) {
			System.out.println("���̵� �Ǵ� ��й�ȣ�� Ȯ�����ּ���");
		}
	}
	
	//ȸ������ â ����
	public void GoSignup(ActionEvent event)  {
		try {
		Parent root = FXMLLoader.load(getClass().getResource("../fxml/Signup.fxml"));
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toString());
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	//�ؽ�Ʈ�ʵ忡�� ���ʹ����� ��ư ����
	private void enterFire(TextField tf,Button btn) {
		tf.setOnKeyPressed(e -> {
	        if (e.getCode().equals(KeyCode.ENTER)) {
	        	btn.fire();
	        }
	    });
	}
}