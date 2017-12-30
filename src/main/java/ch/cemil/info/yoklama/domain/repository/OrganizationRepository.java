package ch.cemil.info.yoklama.domain.repository;


import ch.cemil.info.yoklama.domain.entity.Organization;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {

    Iterable<Organization> findAll();

    Organization save(Organization add);
}
