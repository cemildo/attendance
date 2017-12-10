package ch.cemil.yoklama.Repository;


import ch.cemil.yoklama.model.Address;
import ch.cemil.yoklama.model.Member;
import ch.cemil.yoklama.model.Organization;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Query;
import java.util.List;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {

    Iterable<Organization> findAll();

    Organization save(Organization add);
}
