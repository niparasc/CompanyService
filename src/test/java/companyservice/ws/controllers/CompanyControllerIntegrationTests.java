package companyservice.ws.controllers;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;

import companyservice.domain.BeneficialOwner;
import companyservice.domain.BeneficialOwnerRepository;
import companyservice.domain.Company;
import companyservice.domain.CompanyRepository;
import companyservice.ws.converters.CompanyToCompanyResourceConverter;
import companyservice.ws.exceptions.errorresponse.ApiResponseCodes;
import companyservice.ws.fixtures.BeneficialOwnerFixtures;
import companyservice.ws.fixtures.CompanyFixtures;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerIntegrationTests {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private CompanyRepository companyRepository;
	@Autowired private BeneficialOwnerRepository beneficialOwnerRepository;
	@Autowired private CompanyToCompanyResourceConverter companyToCompanyResourceConverter;
	
	private CompanyFixtures companyFixtures = new CompanyFixtures();
	private BeneficialOwnerFixtures beneficialOwnerFixtures = new BeneficialOwnerFixtures();
	
	@Before
	public void before() {
		companyRepository.deleteAll();
		beneficialOwnerRepository.deleteAll();
	}
	
    @Test
    public void createCompany() throws Exception {
    	BeneficialOwner owner = beneficialOwnerFixtures.createBeneficialOwner();
    	beneficialOwnerRepository.save(owner);
    	BeneficialOwner storedOwner = beneficialOwnerRepository.findAll().get(0);
    	
    	Company company = companyFixtures.createCompany();
    	company.setBeneficialOwners(ImmutableSet.of(storedOwner));
    	
    	this.mockMvc.perform(post("/companies")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(companyToCompanyResourceConverter.convert(company))))
					.andDo(print())
					.andExpect(status().isNoContent());
    	
    	assertEquals(1, companyRepository.count());
    	Company storedCompany = companyRepository.findAll().get(0);
    	assertEquals(company.getName(), storedCompany.getName());
    }
    
    @Test
    public void createCompanyValidation() throws Exception {
    	Company company = companyFixtures.createCompany();
    	
    	this.mockMvc.perform(post("/companies")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(companyToCompanyResourceConverter.convert(company))))
					.andDo(print())
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.meta.code").value(ApiResponseCodes.VALIDATION_ERROR.getCode()));
    }
    
    @Test
    public void getCompanies() throws Exception {
    	BeneficialOwner owner = beneficialOwnerFixtures.createBeneficialOwner();
    	Company company = companyFixtures.createCompany();
    	company.addBeneficialOwner(owner);
    	companyRepository.save(company);

    	this.mockMvc.perform(get("/companies"))
    				.andDo(print())
    				.andExpect(status().isOk())
    				.andExpect(jsonPath("$[0].id").value(isA(Integer.class)))
    				.andExpect(jsonPath("$[0].name").value(company.getName()))
    				.andExpect(jsonPath("$[0].beneficialOwners[0].firstName").value(owner.getFirstName()));
    }
    
    @Test
    public void getCompany() throws Exception {
    	BeneficialOwner owner = beneficialOwnerFixtures.createBeneficialOwner();
    	Company company = companyFixtures.createCompany();
    	company.addBeneficialOwner(owner);
    	companyRepository.save(company);
    	Company storedCompany = companyRepository.findAll().get(0);

    	this.mockMvc.perform(get("/companies/{id}", storedCompany.getId()))
    				.andDo(print())
    				.andExpect(status().isOk())
    				.andExpect(jsonPath("$.id").value(isA(Integer.class)))
    				.andExpect(jsonPath("$.name").value(storedCompany.getName()))
    				.andExpect(jsonPath("$.beneficialOwners[0].firstName").value(owner.getFirstName()));
    }
    
    @Test
    public void getCompanyNotFound() throws Exception {
    	Long id = 1L;
    	this.mockMvc.perform(get("/companies/{id}", id))
							.andDo(print())
							.andExpect(status().isNotFound())
							.andExpect(jsonPath("$.meta.info").value("Could not find Company with ID " + id));
    }
    
    @Test
    public void replaceCompany() throws Exception {
    	BeneficialOwner owner = beneficialOwnerFixtures.createBeneficialOwner();
    	Company company = companyFixtures.createCompany();
    	company.addBeneficialOwner(owner);
    	companyRepository.save(company);
    	Company storedCompany = companyRepository.findAll().get(0);
    	
    	storedCompany.setName("New Name");
    	
    	this.mockMvc.perform(put("/companies/{id}", storedCompany.getId())
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(companyToCompanyResourceConverter.convert(storedCompany))))
					.andDo(print())
					.andExpect(status().isNoContent());
    	
    	storedCompany = companyRepository.findAll().get(0);
    	assertEquals("New Name", storedCompany.getName());
    }
    
    @Test
    public void replaceCompanyValidation() throws Exception {
    	BeneficialOwner owner = beneficialOwnerFixtures.createBeneficialOwner();
    	Company company = companyFixtures.createCompany();
    	company.addBeneficialOwner(owner);
    	companyRepository.save(company);
    	Company storedCompany = companyRepository.findAll().get(0);
    	
    	storedCompany.setName(null);
    	
    	this.mockMvc.perform(put("/companies/{id}", storedCompany.getId())
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(companyToCompanyResourceConverter.convert(storedCompany))))
					.andDo(print())
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.meta.code").value(ApiResponseCodes.VALIDATION_ERROR.getCode()));
    }
    
    @Test
    public void addBeneficialOwners() throws Exception {
    	BeneficialOwner thorin = beneficialOwnerFixtures.createBeneficialOwner("Thorin", "Oakenshield");
    	Company company = companyFixtures.createCompany();
    	company.addBeneficialOwner(thorin);
    	companyRepository.save(company);
    	
    	BeneficialOwner bilbo = beneficialOwnerFixtures.createBeneficialOwner("Bilbo", "Baggins");
    	beneficialOwnerRepository.save(bilbo);
    	BeneficialOwner frodo = beneficialOwnerFixtures.createBeneficialOwner("Frodo", "Baggins");
    	beneficialOwnerRepository.save(frodo);
    	
    	BeneficialOwner storedThorin = beneficialOwnerRepository.findAll().stream().filter(owner -> owner.getFirstName().equals(thorin.getFirstName())).findFirst().get();
    	BeneficialOwner storedBilbo = beneficialOwnerRepository.findAll().stream().filter(owner -> owner.getFirstName().equals(bilbo.getFirstName())).findFirst().get();
    	BeneficialOwner storedFrodo = beneficialOwnerRepository.findAll().stream().filter(owner -> owner.getFirstName().equals(frodo.getFirstName())).findFirst().get();
    	
    	Map<String, List<Long>> beneficialOwnerIds = new HashMap<String, List<Long>>();
    	beneficialOwnerIds.put("beneficialOwnerIds", Arrays.asList(storedBilbo.getId(), storedFrodo.getId()));
    	
    	Company storedCompany = companyRepository.findAll().get(0);
    	
    	this.mockMvc.perform(post("/companies/{id}/beneficialOwners", storedCompany.getId())
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(beneficialOwnerIds)))
					.andDo(print())
					.andExpect(status().isNoContent());
    	
    	storedCompany = companyRepository.findAll().get(0);
    	assertEquals(3, storedCompany.getBeneficialOwners().size());
    	List<Long> storedIDs = storedCompany.getBeneficialOwners().stream().map(owner -> owner.getId()).collect(Collectors.toList());
    	assertTrue(storedIDs.contains(storedThorin.getId()) &&
    			   storedIDs.contains(storedBilbo.getId()) &&
    			   storedIDs.contains(storedFrodo.getId()));
    }

}
