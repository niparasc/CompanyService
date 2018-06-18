package companyservice.ws.fixtures;

import companyservice.domain.BeneficialOwner;

public class BeneficialOwnerFixtures {

	public BeneficialOwner createBeneficialOwner() {
		return createBeneficialOwner("Thorin", "Oakenshield");
	}
	
	public BeneficialOwner createBeneficialOwner(String firstName, String lastName) {
		BeneficialOwner larryPage = new BeneficialOwner();
		larryPage.setFirstName(firstName);
		larryPage.setLastName(lastName);
		return larryPage;
	}

}
