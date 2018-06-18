package companyservice.ws.fixtures;

import companyservice.domain.Company;

public class CompanyFixtures {

	public Company createCompany() {
    	Company google = new Company();
    	google.setName("Google");
    	google.setAddress("Googleplex");
    	google.setCity("Mountain View");
    	google.setCountry("USA");
    	google.setEmail("google@google.com");
    	google.setPhone("777-777-7777");
    	return google;
    }

}
