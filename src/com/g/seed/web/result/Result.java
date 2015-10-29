package com.g.seed.web.result;

import java.io.Serializable;

public class Result implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String L = "<L>";
	protected String resultCode = abnormal;
	protected String resultDesc = "default";
	protected transient boolean nonNull = true;
	private static final String abnormal = "0";
	private static final String normal = "1";
	protected transient Object protype;
	private Exception exception = null;
	private static Judge judge = new Judge(){
		@Override
		public boolean isNormal(String code) {
			return normal.equals(code);
		}
		
	};
	
	public Result()
	{
	}
	
	public Result(Exception exception){
		this.exception = exception;
	}
	
	public Result(String resultCode, String resultDesc)
	{
		this.resultCode = resultCode;
		this.resultDesc = resultDesc;
	}
	
	public Exception getException(){
		return this.exception;
	}
	
	public boolean hasException(){
		return this.exception != null;
	}
	
	public String printProtype() {
		return this.protype.toString();
	}
	
	public boolean isNormal()
	{
		return judge.isNormal(resultCode);
	}
	
	public String getResultCode() {
		return this.resultCode;
	}
	
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getResultDesc() {
		return this.resultDesc;
	}
	
	public Object getProtype() {
		return this.protype;
	}
	
	public void setProtype(Object protype) {
		this.protype = protype;
	}
	
	public static interface Judge{
		boolean isNormal(String code);
	}
	
	public static void setJudge(Judge judge){
		Result.judge = judge;
	}
	
}
