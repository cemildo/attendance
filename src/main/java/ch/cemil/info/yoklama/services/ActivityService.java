package ch.cemil.info.yoklama.services;

import ch.cemil.info.yoklama.domain.entity.Activity;
import ch.cemil.info.yoklama.domain.entity.Address;
import ch.cemil.info.yoklama.domain.repository.ActivityRepository;
import ch.cemil.info.yoklama.domain.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> findAll() {
        List<Activity> activities = new ArrayList<>();
        activityRepository.findAll().forEach(activities::add);
        return activities;
    }

    public Activity findOne (long activityId){
        return activityRepository.findOne(activityId);
    }

    public void delete(long orgId){
        activityRepository.delete(orgId);
    }

    public Activity save(Activity activity){
        return  activityRepository.save(activity);
    }
}
