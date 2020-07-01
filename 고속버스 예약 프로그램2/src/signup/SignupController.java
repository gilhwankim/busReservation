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

	private @FXML TextField signup_txtid; //아이디 텍스트 필드
	private @FXML PasswordField signup_txtpassword; //비밀번호 필드
	private @FXML TextField signup_name; //이름 텍스트 필드
	private @FXML TextField signup_tel; // 연락처 텍스트 필드
//	@FXML Button signup_okbtn; //회원가입 완료 버튼 // onaction=#signup
	private @FXML Button signup_canclebtn; //취소 버튼
	DAO dao = DAO.getinstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	//회원가입 버튼 onAction
	
	public void signup(ActionEvent event) throws Exception {
		//빈칸 검증
		if(signup_txtid.getText().equals("")||signup_txtpassword.getText().equals("")||signup_name.getText().equals("")||signup_tel.getText().equals("")) {
			System.out.println("빈 칸을 입력해주세요.");
			return;
		}
		
		//ID 존재유무 검증
		if(dao.alreadyId(signup_txtid.getText()).equals(signup_txtid.getText())) {
			System.out.println("이미 존재하는 아이디 입니다.");
			return;
		} else if(dao.alreadyId(signup_txtid.getText()).equals("")) {
			//DB입력
			dao.insertGuestInfo(signup_txtid.getText(),signup_txtpassword.getText(),signup_name.getText(),signup_tel.getText());	
		} 
		
		//닫힘
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}
	
	public void signupcancel(ActionEvent event) throws Exception{
		//취소 누르면 창 꺼짐
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}

}