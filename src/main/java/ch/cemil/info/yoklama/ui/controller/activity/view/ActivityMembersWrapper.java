package ch.cemil.info.yoklama.ui.controller.activity.view;

import ch.cemil.info.yoklama.domain.entity.Activity;
import ch.cemil.info.yoklama.domain.entity.Attendance;
import ch.cemil.info.yoklama.domain.entity.Organization;

import java.util.ArrayList;
import java.util.List;

public class ActivityMembersWrapper {
    List<ActivityMember> members = new ArrayList<>();
    Organization organization;
    Activity activity;
    List<Attendance> attendance;

    public List<Attendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Attendance> attendance) {
        this.attendance = attendance;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }



    public List<ActivityMember> getMembers() {
        return members;
    }

    public void setMembers(List<ActivityMember> members) {
        this.members = members;
    }
}
