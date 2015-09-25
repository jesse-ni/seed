package com.g.seed.web.exception;


public class DataException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/** @Fields content : TODO (异常的数据) */
	private String data;
	
	public DataException(String detailMessage, String data) {
		super(detailMessage);
		this.data = data;
	}
	
	public DataException(Throwable throwable, String data) {
		super(throwable);
		this.data = data;
	}
	
	public String getMessage() {
		return super.getMessage() + "\ndata:" + String.valueOf(this.data);
	}
}
