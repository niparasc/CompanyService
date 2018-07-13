package companyservice.ws.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import companyservice.ws.annotations.WithValidator;
import companyservice.ws.resources.CompanyResource;
import companyservice.ws.services.CompanyService;
import companyservice.ws.validators.CompanyValidator;

@CrossOrigin
@RestController
public class CompanyController {

	private CompanyService service;
	
	@Autowired
	public CompanyController(CompanyService service) {
		this.service = service;
	}
	
	@PostMapping("/companies")
	@WithValidator({CompanyValidator.class})
	public ResponseEntity<Void> createCompany(@RequestBody CompanyResource resource) {
		service.createCompany(resource);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/companies")
	public ResponseEntity<List<CompanyResource>> getCompanies() {
		return ResponseEntity.ok(service.getCompanies());
	}
	
	@GetMapping("/companies/{id}")
	public ResponseEntity<CompanyResource> getCompany(@PathVariable Long id) {
		return ResponseEntity.ok(service.getCompany(id));
	}
	
	@PutMapping("/companies/{id}")
	@WithValidator({CompanyValidator.class})
	public ResponseEntity<Void> replaceCompany(@PathVariable Long id, @RequestBody CompanyResource resource) {
		service.replaceCompany(id, resource);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/companies/{id}/beneficialOwners")
	public ResponseEntity<Void> addBeneficialOwners(@PathVariable Long id, @RequestBody Map<String, List<Long>> beneficialOwnerIds) {
		service.addBeneficialOwners(id, beneficialOwnerIds);
		return ResponseEntity.noContent().build();
	}

}
