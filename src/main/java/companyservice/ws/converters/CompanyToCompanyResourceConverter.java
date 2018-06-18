package companyservice.ws.converters;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import companyservice.domain.Company;
import companyservice.ws.resources.CompanyResource;

@Component
public class CompanyToCompanyResourceConverter implements Converter<Company, CompanyResource> {

	private OwnerToOwnerResourceConverter beneficialOwnerConverter;
	
	@Autowired
	public CompanyToCompanyResourceConverter(OwnerToOwnerResourceConverter beneficialOwnerConverter) {
		this.beneficialOwnerConverter = beneficialOwnerConverter;
	}
	
	@Override
	public CompanyResource convert(Company company) {
		CompanyResource resource = new CompanyResource();
		resource.setId(company.getId());
		resource.setName(company.getName());
		resource.setAddress(company.getAddress());
		resource.setCity(company.getCity());
		resource.setCountry(company.getCountry());
		resource.setEmail(company.getEmail());
		resource.setPhone(company.getPhone());
		resource.setBeneficialOwners(company.getBeneficialOwners()
											.stream()
											.map(beneficialOwnerConverter::convert)
											.collect(Collectors.toList()));
		return resource;
	}

}
