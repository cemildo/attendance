package ch.cemil.info.yoklama.services;

import ch.cemil.info.yoklama.domain.entity.Attendance;
import ch.cemil.info.yoklama.domain.entity.Member;
import ch.cemil.info.yoklama.domain.repository.AttendanceRepository;
import ch.cemil.info.yoklama.domain.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> findAll() {
        List<Attendance> attendances = new ArrayList<>();
        attendanceRepository.findAll().forEach(attendances::add);
        return attendances;
    }

    public List<Attendance> findByActivityId(long activityId) {
        List<Attendance> attendances = new ArrayList<>();
        attendanceRepository.findByActivityId(activityId).forEach(attendances::add);
        return attendances;
    }


    public Attendance findOne (long attendanceId){
        return attendanceRepository.findOne(attendanceId);
    }

    public void delete(long orgId){
        attendanceRepository.delete(orgId);
    }

    public Attendance save(Attendance attendance){
        return  attendanceRepository.save(attendance);
    }
}
