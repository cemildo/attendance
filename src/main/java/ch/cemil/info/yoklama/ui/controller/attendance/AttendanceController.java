package ch.cemil.info.yoklama.ui.controller.attendance;


import ch.cemil.info.yoklama.domain.entity.Activity;
import ch.cemil.info.yoklama.domain.entity.Attendance;
import ch.cemil.info.yoklama.domain.entity.Member;
import ch.cemil.info.yoklama.domain.entity.Organization;
import ch.cemil.info.yoklama.domain.repository.AddressRepository;
import ch.cemil.info.yoklama.services.ActivityService;
import ch.cemil.info.yoklama.services.AttendanceService;
import ch.cemil.info.yoklama.services.OrganizationService;
import ch.cemil.info.yoklama.ui.controller.activity.view.ActivityMember;
import ch.cemil.info.yoklama.ui.controller.activity.view.ActivityMembersWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AttendanceController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private ActivityService activityService;

    @RequestMapping(value = "/organization/{orgId}/activity/{activityId}/attendance")
    public String ShowActivityAttendance(@PathVariable long orgId, @PathVariable long activityId, Model model) {
        Organization organization = organizationService.findOne(orgId);

        Activity activity = activityService.findOne(activityId);

        if(activity == null) {
            activity = new Activity();
        }

        List<Member> members = organization.getMembers();
        ActivityMembersWrapper activityMembers = new ActivityMembersWrapper();
        List<Attendance> attendances = activity.getAttendances();
        members.stream().forEach(member -> {
            Attendance pAttendance = null;
            if(attendances != null && attendances.size() != 0) {
                pAttendance = attendances
                        .stream()
                        .filter(attendance -> attendance.getActivity().getId() == activityId
                                && attendance.getMember().getId() == member.getId()).findFirst().orElse(null);
            }

            ActivityMember aMember = new ActivityMember();
            aMember.setFirstName(member.getFirstName());
            aMember.setLastName(member.getLastName());
            aMember.setId(member.getId());
            aMember.setPresent(pAttendance != null ? pAttendance.isPresent(): false);
            activityMembers.getMembers().add(aMember);
        });
        activityMembers.setActivity(activity);
        activityMembers.setOrganization(organization);
        activityMembers.setAttendance(attendances);
        model.addAttribute("template", "views/organization/attendance/all");
        model.addAttribute("partial", "all");
        model.addAttribute("organization", organization);
        model.addAttribute("activityMembers", activityMembers);

        return "index";
    }

    @RequestMapping(value = "/organization/{orgId}/activity/attendance", method = RequestMethod.POST)
    public String checkAttendance(ActivityMembersWrapper attendees, Model model) {
        Organization organization = attendees.getOrganization();
        Activity activity = attendees.getActivity();
        List<Attendance> attendances = attendees.getAttendance();
        if(attendances.size() == 0) {
            for (Member member : organization.getMembers()) {
                Attendance attendance = new Attendance();
                attendance.setMember(member);
                attendance.setActivity(activity);
                attendance.setOrganizationId(organization.getId());
                attendance.setId((member.getId() != 0) ? member.getId(): 0);
                attendances.add(attendance);
            }
        }

        List<Attendance> list = new ArrayList<>();
        activity.setAttendances(new ArrayList<Attendance>());

        attendees.getMembers().stream().forEach(member -> {
            attendances.stream().forEach(attendance -> {
               if(attendance.getMember().getId() == member.getId()) {
                   attendance.setPresent(member.isPresent());
                   list.add(attendance);
               }
            });
        });

        activity.setAttendances(list);
        activityService.save(activity);
        model.addAttribute("template", "views/organization/attendance/all");
        model.addAttribute("partial", "all");
        return "redirect:/organization/"+ organization.getId() +"/activity/"+ activity.getId()+"/attendance";
    }
}
