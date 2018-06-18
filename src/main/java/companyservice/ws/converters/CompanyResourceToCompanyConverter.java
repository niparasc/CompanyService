package companyservice.ws.converters;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import companyservice.domain.BeneficialOwner;
import companyservice.domain.BeneficialOwnerRepository;
import companyservice.domain.Company;
import companyservice.ws.exceptions.ResourceNotFoundException;
import companyservice.ws.resources.CompanyResource;
import companyservice.ws.resources.Resource;

@Component
public class CompanyResourceToCompanyConverter implements Converter<CompanyResource, Company> {

	private BeneficialOwnerRepository beneficialOwnerRepository;
	
	@Autowired
	public CompanyResourceToCompanyConverter(BeneficialOwnerRepository beneficialOwnerRepository) {
		this.beneficialOwnerRepository = beneficialOwnerRepository;
	}
	
	@Override
	public Company convert(CompanyResource resource) {
		Company company = new Company();
		company.setName(resource.getName());
		company.setAddress(resource.getAddress());
		company.setCity(resource.getCity());
		company.setCountry(resource.getCountry());
		company.setEmail(resource.getEmail());
		company.setPhone(resource.getPhone());
		company.setBeneficialOwners(getBeneficialOwnersFromDb(resource));
		return company;
	}

	private Set<BeneficialOwner> getBeneficialOwnersFromDb(CompanyResource resource) {
		return resource.getBeneficialOwners()
					   .stream()
					   .map(owner -> {
						   return beneficialOwnerRepository.findById(owner.getId())
														   .orElseThrow(() -> new ResourceNotFoundException(Resource.BeneficialOwner,
																   											owner.getId()));
					   })
					   .collect(Collectors.toSet());
	}
	
}
