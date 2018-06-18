package companyservice.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long> {

	Company save(Company company);
    Optional<Company> findById(Long id);
    Optional<Company> findByName(String name);
    List<Company> findAll();
    void delete(Company company);
	
}
