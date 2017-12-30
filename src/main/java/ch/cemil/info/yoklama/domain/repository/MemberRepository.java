package ch.cemil.info.yoklama.domain.repository;

import ch.cemil.info.yoklama.domain.entity.Member;
import ch.cemil.info.yoklama.domain.entity.Organization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Member save(Member add);
    Member findOne(long memberId);

    @Query(value = "SELECT * FROM member WHERE organization_id = ?1", nativeQuery = true)
    Iterable<Member> findByOrganizationId(long orgId);
}
