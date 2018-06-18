package companyservice.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class BeneficialOwner {

	@Id
	@GeneratedValue
	private Long id;
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	@ManyToMany(mappedBy = "beneficialOwners")
	private Set<Company> companies = new HashSet<Company>();
	public Set<Company> getCompanies() { return companies; }
	public void setCompanies(Set<Company> companies) { this.companies = companies; }

	public void addCompany(Company company) {
		companies.add(company);
		company.getBeneficialOwners().add(this);
	}
	
	private String firstName;
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	private String lastName;
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }

	public BeneficialOwner() {}
	
	public BeneficialOwner(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
}
