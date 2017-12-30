package ch.cemil.info.yoklama.domain.repository;

import ch.cemil.info.yoklama.domain.entity.Address;
import ch.cemil.info.yoklama.domain.entity.Attendance;
import org.springframework.data.repository.CrudRepository;

import javax.print.AttributeException;


public interface AttendanceRepository extends CrudRepository<Attendance, Long> {
    Iterable<Attendance> findByActivityId(long activityId);
}
