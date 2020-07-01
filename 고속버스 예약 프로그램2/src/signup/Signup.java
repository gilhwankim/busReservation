package signup;

public class Signup {

	String txtid;
	String txtpassword;
	String name;
	String tel;
	
	public Signup(String txtid, String txtpassword, String name, String tel) {
		this.txtid = txtid;
		this.txtpassword = txtpassword;
		this.name = name;
		this.tel = tel;
	}

	public String getTxtid() {
		return txtid;
	}

	public void setTxtid(String txtid) {
		this.txtid = txtid;
	}

	public String getTxtpassword() {
		return txtpassword;
	}

	public void setTxtpassword(String txtpassword) {
		this.txtpassword = txtpassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}	
}