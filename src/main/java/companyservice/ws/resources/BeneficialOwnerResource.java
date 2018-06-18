package companyservice.ws.resources;

import companyservice.ws.validators.ValidatableResource;

public class BeneficialOwnerResource implements ValidatableResource {

	private Long id;
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
	private String firstName;
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	private String lastName;
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	
}
