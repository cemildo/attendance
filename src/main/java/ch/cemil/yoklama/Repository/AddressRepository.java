package ch.cemil.yoklama.Repository;

import ch.cemil.yoklama.model.Address;
import ch.cemil.yoklama.model.Organization;
import org.springframework.data.repository.CrudRepository;


public interface AddressRepository extends CrudRepository<Address, Long> {
    Address save(Address add);
}
