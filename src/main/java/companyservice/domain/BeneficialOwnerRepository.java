package companyservice.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface BeneficialOwnerRepository extends CrudRepository<BeneficialOwner, Long> {

	BeneficialOwner save(BeneficialOwner beneficialOwner);
    Optional<BeneficialOwner> findById(Long id);
    List<BeneficialOwner> findAll();
    void delete(BeneficialOwner beneficialOwner);
	
}
