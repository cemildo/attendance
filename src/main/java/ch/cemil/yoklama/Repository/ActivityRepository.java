package ch.cemil.yoklama.Repository;

import ch.cemil.yoklama.model.Activity;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
}
