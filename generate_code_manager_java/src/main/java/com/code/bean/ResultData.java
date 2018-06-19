package com.code.bean;

public class ResultData {
	public ResultData() {
		// TODO Auto-generated constructor stub
	}
	public ResultData(boolean ok,String msg) {
		this.ok=ok;
		this.msg=msg;
	}
	
	private Boolean ok = true;


	public Boolean getOk() {
		return ok;
	}
	public void setOk(Boolean ok) {
		this.ok = ok;
	}
	public ResultData setOk(boolean ok) {
		this.ok = ok;
		return this;
	}
	
	private String msg = "请求成功";

	public String getMsg() {
		return msg;
	}

	public ResultData setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	private Object data;

	public Object getData() {
		return data;
	}
	public ResultData setData(Object data) {
		this.data = data;
		return this;
	}
	
	
}
