package ch.cemil.yoklama.Repository;

import ch.cemil.yoklama.model.Member;
import ch.cemil.yoklama.model.Organization;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberRepository extends CrudRepository<Organization, Long> {
}
