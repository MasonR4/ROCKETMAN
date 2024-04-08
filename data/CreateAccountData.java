package data;

import java.io.Serializable;

public class CreateAccountData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private char[] password;
	
	public CreateAccountData(String usr, char[] pwd) {
		username = usr;
		password = pwd;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return new String(password);
	}
}
