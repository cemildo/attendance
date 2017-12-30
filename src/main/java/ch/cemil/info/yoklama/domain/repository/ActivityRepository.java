package ch.cemil.info.yoklama.domain.repository;

import ch.cemil.info.yoklama.domain.entity.Activity;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
}
