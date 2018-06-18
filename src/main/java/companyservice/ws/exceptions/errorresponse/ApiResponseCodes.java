package companyservice.ws.exceptions.errorresponse;

public enum ApiResponseCodes {

	VALIDATION_ERROR(1000, "The system cannot process the request due to validation errors");
	
	private final int code;
	public int getCode() { return code; }
	
	private final String info;
	public String getInfo() { return info; }
	
	ApiResponseCodes(int code, String info) {
		this.code = code;
		this.info = info;
	}

}
