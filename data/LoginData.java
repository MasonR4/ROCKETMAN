package data;

public class LoginData {
	private String username;
	private char[] password;
	
	public LoginData(String usr, char[] pwd) {
		username = usr;
		password = pwd;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		String pwd = "";
		for (char c : password) {
			pwd += c;
		}
		return pwd;
	}	
}
