package companyservice.ws.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import companyservice.domain.CompanyRepository;
import companyservice.ws.resources.CompanyResource;

@Component("CompanyValidator")
public class CompanyValidator implements Validator {

	private CompanyRepository companyRepository;
	
	@Autowired
	public CompanyValidator(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}
	
	@Override
	public Multimap<String, String> validate(ValidatableResource target) {
		CompanyResource resource = (CompanyResource) target;
		
		Multimap<String, String> errors = LinkedHashMultimap.create();
		
		if (StringUtils.isEmpty(resource.getName())) {
			errors.put("name", "Name is required");
		}

		if (companyRepository.findByName(resource.getName()).isPresent()) {
			errors.put("name", "The name '" + resource.getName() + "' is taken by another company");
		}
		
		if (StringUtils.isEmpty(resource.getAddress())) {
			errors.put("address", "Address is required");
		}
		
		if (StringUtils.isEmpty(resource.getCity())) {
			errors.put("city", "City is required");
		}
		
		if (StringUtils.isEmpty(resource.getCountry())) {
			errors.put("country", "Country is required");
		}
		
		if (resource.getBeneficialOwners() == null || resource.getBeneficialOwners().isEmpty()) {
			errors.put("beneficialOwners", "At least one beneficial owner is required");
		}
		
		return errors;
	}

}
