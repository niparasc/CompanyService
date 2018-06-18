package companyservice.ws.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import companyservice.domain.BeneficialOwner;
import companyservice.ws.resources.BeneficialOwnerResource;

@Component
public class OwnerResourceToOwnerConverter implements Converter<BeneficialOwnerResource, BeneficialOwner> {

	@Override
	public BeneficialOwner convert(BeneficialOwnerResource resource) {
		BeneficialOwner owner = new BeneficialOwner();
		owner.setFirstName(resource.getFirstName());
		owner.setLastName(resource.getLastName());
		return owner;
	}
}
