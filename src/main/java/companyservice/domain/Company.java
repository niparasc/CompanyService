package companyservice.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Company {

	@Id
	@GeneratedValue
	private Long id;
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
	@Column(unique = true)
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

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
	private Set<BeneficialOwner> beneficialOwners = new HashSet<BeneficialOwner>();
	public Set<BeneficialOwner> getBeneficialOwners() { return beneficialOwners; }
	public void setBeneficialOwners(Set<BeneficialOwner> beneficialOwners) { this.beneficialOwners = beneficialOwners; }

	public void addBeneficialOwner(BeneficialOwner beneficialOwner) {
		beneficialOwners.add(beneficialOwner);
		beneficialOwner.getCompanies().add(this);
	}
	
}
