package data;

import java.io.Serializable;

public class LoginData implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
