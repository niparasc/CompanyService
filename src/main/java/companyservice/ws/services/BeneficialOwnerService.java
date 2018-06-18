package companyservice.ws.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import companyservice.domain.BeneficialOwnerRepository;
import companyservice.ws.converters.OwnerResourceToOwnerConverter;
import companyservice.ws.converters.OwnerToOwnerResourceConverter;
import companyservice.ws.resources.BeneficialOwnerResource;

@Service
public class BeneficialOwnerService {

	private BeneficialOwnerRepository repository;
	private OwnerResourceToOwnerConverter ownerResourceToOwnerConverter;
	private OwnerToOwnerResourceConverter ownerToOwnerResourceConverter;
	
	@Autowired
	public BeneficialOwnerService(BeneficialOwnerRepository repository,
								  OwnerResourceToOwnerConverter ownerResourceToOwnerConverter,
								  OwnerToOwnerResourceConverter ownerToOwnerResourceConverter) {
		this.repository = repository;
		this.ownerResourceToOwnerConverter = ownerResourceToOwnerConverter;
		this.ownerToOwnerResourceConverter = ownerToOwnerResourceConverter;
	}
	
	public void createBeneficialOwner(BeneficialOwnerResource resource) {
		repository.save(ownerResourceToOwnerConverter.convert(resource));
	}

	public List<BeneficialOwnerResource> getBeneficialOwners() {
		return repository.findAll()
						 .stream()
						 .map(ownerToOwnerResourceConverter::convert)
						 .collect(Collectors.toList());
	}

}
