package companyservice.ws.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import companyservice.ws.annotations.WithValidator;
import companyservice.ws.resources.BeneficialOwnerResource;
import companyservice.ws.services.BeneficialOwnerService;
import companyservice.ws.validators.BeneficialOwnerValidator;

@CrossOrigin
@RestController
public class BeneficialOwnerController {

	private BeneficialOwnerService service;
	
	@Autowired
	public BeneficialOwnerController(BeneficialOwnerService service) {
		this.service = service;
	}
	
	@PostMapping("/beneficialOwners")
	@WithValidator({BeneficialOwnerValidator.class})
	public ResponseEntity<Void> createBeneficialOwner(@RequestBody BeneficialOwnerResource resource) {
		service.createBeneficialOwner(resource);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/beneficialOwners")
	public ResponseEntity<List<BeneficialOwnerResource>> getBeneficialOwners() {
		return ResponseEntity.ok(service.getBeneficialOwners());
	}
	
}
