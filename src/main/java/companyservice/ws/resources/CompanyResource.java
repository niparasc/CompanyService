package companyservice.ws.resources;

import java.util.List;

import companyservice.ws.validators.ValidatableResource;

public class CompanyResource implements ValidatableResource {

	private Long id;
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
	private String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	private String address;
	public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }

	private String city;
	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; }

	private String country;
	public String getCountry() { return country; }
	public void setCountry(String country) { this.country = country; }

	private String email;
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	private String phone;
	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }

	private List<BeneficialOwnerResource> beneficialOwners;
	public List<BeneficialOwnerResource> getBeneficialOwners() { return beneficialOwners; }
	public void setBeneficialOwners(List<BeneficialOwnerResource> beneficialOwners) { this.beneficialOwners = beneficialOwners; }

}
