package data;

import java.io.Serializable;
// i realized too late this class makes me a complete idiot because now we can just pass any other data class inside this one
// or not even use unique class for data and just pass long array or hash-map of all data we need
// i just wanted a small class to use for small things like login success or server closed
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
