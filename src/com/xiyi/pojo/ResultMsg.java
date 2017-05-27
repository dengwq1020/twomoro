package com.xiyi.pojo;

public class ResultMsg<T>{
	boolean success;
	int code;
	String msg;
	T data;
	
	public ResultMsg(){
		this.success = true;
		this.code = 0;
		this.msg = "";
		this.data = null;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
