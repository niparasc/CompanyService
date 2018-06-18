package companyservice.ws.services;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import companyservice.domain.BeneficialOwner;
import companyservice.domain.BeneficialOwnerRepository;
import companyservice.domain.Company;
import companyservice.domain.CompanyRepository;
import companyservice.ws.converters.CompanyResourceToCompanyConverter;
import companyservice.ws.converters.CompanyToCompanyResourceConverter;
import companyservice.ws.exceptions.ResourceNotFoundException;
import companyservice.ws.resources.CompanyResource;
import companyservice.ws.resources.Resource;

@Service
public class CompanyService {

	private CompanyRepository companyRepository;
	private BeneficialOwnerRepository beneficialOwnerRepository;
	private CompanyResourceToCompanyConverter companyResourceToCompanyConverter;
	private CompanyToCompanyResourceConverter companyToCompanyResourceConverter;
	
	@Autowired
	public CompanyService(CompanyRepository repository,
						  BeneficialOwnerRepository beneficialOwnerRepository,
						  CompanyResourceToCompanyConverter companyDtoToCompanyConverter,
						  CompanyToCompanyResourceConverter companyToCompanyDtoConverter) {
		this.companyRepository = repository;
		this.beneficialOwnerRepository = beneficialOwnerRepository;
		this.companyResourceToCompanyConverter = companyDtoToCompanyConverter;
		this.companyToCompanyResourceConverter = companyToCompanyDtoConverter;
	}
	
	public void createCompany(CompanyResource resource) {
		companyRepository.save(companyResourceToCompanyConverter.convert(resource));
	}
	
	public List<CompanyResource> getCompanies() {
		return companyRepository.findAll()
							    .stream()
							    .map(companyToCompanyResourceConverter::convert)
							    .collect(Collectors.toList());
	}
	
	public CompanyResource getCompany(Long id) {
		return companyToCompanyResourceConverter.convert(companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Resource.Company, id)));
	}
	
	public void replaceCompany(Long id, CompanyResource resource) {
		Company oldCompany = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Resource.Company, id));
		Company newCompany = companyResourceToCompanyConverter.convert(resource);
		newCompany.setId(oldCompany.getId());
		companyRepository.save(newCompany);
	}
	
	public void addBeneficialOwners(Long id, Map<String, List<Long>> beneficialOwnerIds) {
		Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Resource.Company, id));

		Set<BeneficialOwner> beneficialOwners = 
				beneficialOwnerIds.get("beneficialOwnerIds")
								  .stream()
							      .map(ownerId -> {
								      return beneficialOwnerRepository.findById(ownerId)
																      .orElseThrow(() -> new ResourceNotFoundException(Resource.BeneficialOwner, ownerId));
							      })
							      .collect(Collectors.toSet());
		
		company.getBeneficialOwners().addAll(beneficialOwners);
		companyRepository.save(company);
	}

}
