package companyservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import companyservice.domain.BeneficialOwner;
import companyservice.domain.Company;
import companyservice.domain.CompanyRepository;

@SpringBootApplication
public class CompanyserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyserviceApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(CompanyRepository repository) {
		return (args) -> {
			
			// populating the db with demo data
			
			Company microsoft = new Company();
			microsoft.setName("Microsoft Corporation");
			microsoft.setAddress("Microsoft Redmon campus");
			microsoft.setCity("Redmond");
			microsoft.setCountry("USA");
			microsoft.setEmail("microsoft@microsoft.com");
			microsoft.setPhone("333-444-5555");
			microsoft.addBeneficialOwner(new BeneficialOwner("Bill", "Gates"));
			repository.save(microsoft);
			
			Company apple = new Company();
			apple.setName("Apple Inc.");
			apple.setAddress("1 Apple Park Way");
			apple.setCity("Cupertino");
			apple.setCountry("USA");
			apple.setEmail("apple@apple.com");
			apple.setPhone("222-333-4444");
			apple.addBeneficialOwner(new BeneficialOwner("Steve", "Jobs"));
			apple.addBeneficialOwner(new BeneficialOwner("Tim", "Cook"));
			repository.save(apple);
		};
	}
	
}
