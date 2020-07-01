package signup;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.DAO;

public class SignupController implements Initializable{

	private @FXML TextField signup_txtid; //���̵� �ؽ�Ʈ �ʵ�
	private @FXML PasswordField signup_txtpassword; //��й�ȣ �ʵ�
	private @FXML TextField signup_name; //�̸� �ؽ�Ʈ �ʵ�
	private @FXML TextField signup_tel; // ����ó �ؽ�Ʈ �ʵ�
//	@FXML Button signup_okbtn; //ȸ������ �Ϸ� ��ư // onaction=#signup
	private @FXML Button signup_canclebtn; //��� ��ư
	DAO dao = DAO.getinstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	//ȸ������ ��ư onAction
	
	public void signup(ActionEvent event) throws Exception {
		//��ĭ ����
		if(signup_txtid.getText().equals("")||signup_txtpassword.getText().equals("")||signup_name.getText().equals("")||signup_tel.getText().equals("")) {
			System.out.println("�� ĭ�� �Է����ּ���.");
			return;
		}
		
		//ID �������� ����
		if(dao.alreadyId(signup_txtid.getText()).equals(signup_txtid.getText())) {
			System.out.println("�̹� �����ϴ� ���̵� �Դϴ�.");
			return;
		} else if(dao.alreadyId(signup_txtid.getText()).equals("")) {
			//DB�Է�
			dao.insertGuestInfo(signup_txtid.getText(),signup_txtpassword.getText(),signup_name.getText(),signup_tel.getText());	
		} 
		
		//����
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}
	
	public void signupcancel(ActionEvent event) throws Exception{
		//��� ������ â ����
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}

}