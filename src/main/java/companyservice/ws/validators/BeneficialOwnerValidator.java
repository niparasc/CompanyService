package companyservice.ws.validators;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import companyservice.ws.resources.BeneficialOwnerResource;

@Component("BeneficialOwnerValidator")
public class BeneficialOwnerValidator implements Validator {

	@Override
	public Multimap<String, String> validate(ValidatableResource target) {
		BeneficialOwnerResource resource = (BeneficialOwnerResource) target;
		
		Multimap<String, String> errors = LinkedHashMultimap.create();
		
		if (StringUtils.isEmpty(resource.getFirstName())) {
			errors.put("firstName", "First Name is required");
		}

		if (StringUtils.isEmpty(resource.getLastName())) {
			errors.put("lastName", "Last Name is required");
		}
		
		return errors;
	}

}
