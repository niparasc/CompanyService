package companyservice.ws.exceptions.errorresponse;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorMeta {

	private Integer code;
	public Integer getCode() { return code; }
	public void setCode(Integer code) { this.code = code; }

	private String error;
	public String getError() { return error; }
	public void setError(String error) { this.error = error; }

	private String info;
	public String getInfo() { return info; }
	public void setInfo(String info) { this.info = info; }
	
	public ApiErrorMeta(String info) {
		this.info = info;
	}
	
	public ApiErrorMeta(Integer code, String error, String info) {
		this.code = code;
		this.error = error;
		this.info = info;
	}

}
