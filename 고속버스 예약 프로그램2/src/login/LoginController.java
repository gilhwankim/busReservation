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
	
	private @FXML TextField txt_id; //아이디 텍스트 필드
	private @FXML PasswordField txt_password; //패스워드 필드
	private @FXML Button loginbtn; //로그인 버튼
	
	private DAO dao = DAO.getinstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		enterFire(txt_id, loginbtn);
		enterFire(txt_password, loginbtn);
	}

	//로그인
	public void GoLogin(ActionEvent event) throws Exception {
		//사용자가 입력한 ID, Password를 DAO로 보내 DB데이터와 비교해 검증
		int success = dao.GoLogin(txt_id.getText(), txt_password.getText());
		
		if(success==1) {
			System.out.println("로그인 성공");
			//로그인 유저 정보 저장
			dao.loginn.setId(txt_id.getText());
			dao.loginn.setPassword(txt_password.getText());
			
			//로그인 창 종료 후 예약 페이지 출력
			Stage clo = (Stage)loginbtn.getScene().getWindow();
			clo.close();
			
			Parent root = FXMLLoader.load(getClass().getResource("../fxml/Reservation.fxml"));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/style.css").toString());
			Stage stage = new Stage();
			stage.setTitle("예약");
			stage.setScene(scene);
			stage.show();
		} else if(success==0 || success==-1) {
			System.out.println("아이디 또는 비밀번호를 확인해주세요");
		}
	}
	
	//회원가입 창 띄우기
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
	//텍스트필드에서 엔터누르면 버튼 실행
	private void enterFire(TextField tf,Button btn) {
		tf.setOnKeyPressed(e -> {
	        if (e.getCode().equals(KeyCode.ENTER)) {
	        	btn.fire();
	        }
	    });
	}
}