package companyservice.ws.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import companyservice.domain.BeneficialOwner;
import companyservice.ws.resources.BeneficialOwnerResource;

@Component
public class OwnerToOwnerResourceConverter implements Converter<BeneficialOwner, BeneficialOwnerResource> {

	@Override
	public BeneficialOwnerResource convert(BeneficialOwner beneficialOwner) {
		BeneficialOwnerResource resource = new BeneficialOwnerResource();
		resource.setId(beneficialOwner.getId());
		resource.setFirstName(beneficialOwner.getFirstName());
		resource.setLastName(beneficialOwner.getLastName());
		return resource;
	}
}
