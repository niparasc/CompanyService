package companyservice.ws.controllers;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import companyservice.domain.BeneficialOwner;
import companyservice.domain.BeneficialOwnerRepository;
import companyservice.domain.CompanyRepository;
import companyservice.ws.converters.OwnerToOwnerResourceConverter;
import companyservice.ws.fixtures.BeneficialOwnerFixtures;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BeneficialOwnerControllerIntegrationTests {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private CompanyRepository companyRepository;
	@Autowired private BeneficialOwnerRepository beneficialOwnerRepository;
	@Autowired private OwnerToOwnerResourceConverter ownerToOwnerResourceConverter;
	
	private BeneficialOwnerFixtures fixtures = new BeneficialOwnerFixtures();
	
	@Before
	public void before() {
		companyRepository.deleteAll();
		beneficialOwnerRepository.deleteAll();
	}
	
    @Test
    public void createBeneficialOwner() throws Exception {
    	BeneficialOwner owner = fixtures.createBeneficialOwner();
    	
    	this.mockMvc.perform(post("/beneficialOwners")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(ownerToOwnerResourceConverter.convert(owner))))
					.andDo(print())
					.andExpect(status().isNoContent());
    	
    	assertEquals(1, beneficialOwnerRepository.count());
    	BeneficialOwner storedOwner = beneficialOwnerRepository.findAll().get(0);
    	assertEquals(owner.getFirstName(), storedOwner.getFirstName());
    	assertEquals(owner.getLastName(), storedOwner.getLastName());
    }
	
    @Test
    public void getBeneficialOwners() throws Exception {
    	BeneficialOwner owner = fixtures.createBeneficialOwner();
    	beneficialOwnerRepository.save(owner);
    	
    	this.mockMvc.perform(get("/beneficialOwners"))
    				.andDo(print())
    				.andExpect(status().isOk())
    				.andExpect(jsonPath("$[0].id").value(isA(Integer.class)))
    				.andExpect(jsonPath("$[0].firstName").value(owner.getFirstName()))
    				.andExpect(jsonPath("$[0].lastName").value(owner.getLastName()));
    }

}
