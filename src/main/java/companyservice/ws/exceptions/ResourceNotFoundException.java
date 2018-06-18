package companyservice.ws.exceptions;

import companyservice.ws.resources.Resource;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(Resource resource, Long id) {
		super("Could not find " + resource.name() + " with ID " + id);
	}
	
}
