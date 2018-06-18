package companyservice.ws.exceptions.errorresponse;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

	private ApiErrorMeta meta;
	public ApiErrorMeta getMeta() { return meta; }
	public void setMeta(ApiErrorMeta meta) { this.meta = meta; }

	private Object response;
	public Object getResponse() { return response; }
	public void setResponse(Object response) { this.response = response; }
	
	public ApiError(ApiErrorMeta meta) {
		this.meta = meta;
	}
	
	public ApiError(ApiErrorMeta meta, Object response) {
		this.meta = meta;
		this.response = response;
	}

}
