package companyservice.ws.exceptions;

import com.google.common.collect.Multimap;

public class ValidationException extends RuntimeException {

	private Multimap<String, String> errors;
	public Multimap<String, String> getErrors() { return errors; }
	
	public ValidationException(Multimap<String, String> errors) {
		super();
		this.errors = errors;
	}
	
}
