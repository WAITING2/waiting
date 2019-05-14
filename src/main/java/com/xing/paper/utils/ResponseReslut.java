package com.xing.paper.utils;

public final class  ResponseReslut {
	public static ResponseReslut SUCCESS = new ResponseReslut("操作成功", true);
	public static ResponseReslut FAILD = new ResponseReslut("操作失败", false);
	
	private String reslut;
	private Boolean success;
	
	
	public ResponseReslut() {
	}
	public ResponseReslut(String reslut, Boolean success) {
		super();
		this.reslut = reslut;
		this.success = success;
	}
	public String getReslut() {
		return reslut;
	}
	public void setReslut(String reslut) {
		this.reslut = reslut;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	
}
