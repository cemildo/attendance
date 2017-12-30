package ch.cemil.info.yoklama.domain.repository;

import ch.cemil.info.yoklama.domain.entity.Address;
import org.springframework.data.repository.CrudRepository;


public interface AddressRepository extends CrudRepository<Address, Long> {
    Address save(Address add);
}
