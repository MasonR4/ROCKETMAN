package data;

import java.io.Serializable;

public class GenericRequest implements Serializable {
	
	private String msg;
	private Object data;
	
	public GenericRequest(String s) {
		msg = s;
	}
	
	public void setData(Object o) {
		data = o;
	}
	
	public Object getData() {
		return data;
	}
	
	public String getMsg() {
		return msg;
	}
	
}
