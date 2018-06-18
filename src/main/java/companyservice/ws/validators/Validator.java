package companyservice.ws.validators;

import com.google.common.collect.Multimap;

public interface Validator {

	public Multimap<String, String> validate(ValidatableResource resource);
	
}
