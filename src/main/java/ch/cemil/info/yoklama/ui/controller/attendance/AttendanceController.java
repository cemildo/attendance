package ch.cemil.info.yoklama.ui.controller.attendance;


import ch.cemil.info.yoklama.apis.amazon.Email;
import ch.cemil.info.yoklama.apis.excel.GoogleExcelCRUD;
import ch.cemil.info.yoklama.domain.entity.Activity;
import ch.cemil.info.yoklama.domain.entity.Attendance;
import ch.cemil.info.yoklama.domain.entity.Member;
import ch.cemil.info.yoklama.domain.entity.Organization;
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
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AttendanceController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private  SpringTemplateEngine templateEngine;

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
                        .filter(att1 -> att1.getActivity().getId() == activityId)
                        .filter(att2 -> att2.getMember() != null ? att2.getMember().getId() == member.getId() : false)
                        .findFirst().orElse(null);
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

        return "Index";
    }

    @RequestMapping(value = "/organization/{orgId}/activity/attendance", method = RequestMethod.POST)
    public String checkAttendance(ActivityMembersWrapper attendees, Model model) {
        try {
            Email sendEmail = new Email();
            Context ctx = new Context();
            ctx.setVariable("name", "Cemil Dogan");
            ctx.setVariable("signature", "KZO TEAM");
            ctx.setVariable("location", "VOLKETSWIL");
            String BODY = templateEngine.process("email/absence", ctx);
            sendEmail.send(BODY);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Organization organization = attendees.getOrganization();
        Activity activity = attendees.getActivity();
        List<Attendance> attendances = attendees.getAttendance();

        final List<ActivityMember> membersWithNoAttendance = new ArrayList<>();
        attendees.getMembers().stream().forEach(member -> {
            List<Attendance> atts = activity.getAttendances()
                     .stream()
                    .filter(a -> a.getMember() !=null ? a.getMember().getId() == member.getId(): false)
                    .collect(Collectors.toList());
            if(atts.size() == 0) {
                membersWithNoAttendance.add(member);
            }
        });

        if(membersWithNoAttendance.size() > 0) {
            for (ActivityMember amember : membersWithNoAttendance) {
                Member member = organization.getMembers()
                        .stream().filter(a -> a.getId() == amember.getId()).findFirst().get();
                Attendance attendance = new Attendance();
                attendance.setMember(member);
                attendance.setActivity(activity);
                attendance.setOrganizationId(organization.getId());
                if(!attendances.contains(attendance)) {
                    attendances.add(attendance);
                }
            }
        }


        List<Attendance> list = new ArrayList<>();

        attendees.getMembers().stream().forEach(member -> {
            attendances.stream().forEach(attendance -> {
               if(attendance.getMember() != null && attendance.getMember().getId() == member.getId()) {
                   attendance.setPresent(member.isPresent());
                   if(!list.contains(attendance)){
                       list.add(attendance);
                   }
               }
            });
        });

        activity.setAttendances(list);
        if(activity.getDate() == null) {
            activity.setDate(new Date());
        }

        activityService.save(activity);
        model.addAttribute("template", "views/organization/attendance/all");
        model.addAttribute("partial", "all");
        return "redirect:/organization/"+ organization.getId() +"/activity/"+ activity.getId()+"/attendance";
    }

    @RequestMapping(value = "/attendance/saveToGoogle/{activityId}")
    public String saveToGoogle (@PathVariable long activityId) {
        GoogleExcelCRUD crud = new GoogleExcelCRUD();
        List<Activity> activities = activityService.findAll();

        List<List<Object>> rows = new ArrayList<>();
        boolean isHeaderSet = false;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.GERMANY);
        Date date = new Date();
        String today = formatter.format(date);
        // find all activities
        for (Activity activity : activities) {
            List<Attendance> attendances = activity.getAttendances() ;

            List<Object> row = new ArrayList<>();
            // set headers only once
            if(!isHeaderSet){
                row.add("");
                List<Member> members = new ArrayList<>();
                for (Attendance attendance : attendances) {
                    Member member = attendance.getMember();
                    if(member != null && !members.contains(member)){
                        row.add(member.getFirstName() + " " + member.getLastName() );
                        members.add(member);
                    }
                }
                rows.add(row);
                isHeaderSet = true;
                row = new ArrayList<>();
            }

            // set attendances
            row.add(today);
            List<Attendance> attendanceCheck = new ArrayList<>();
            for (Attendance attendance : attendances) {
                if(!attendanceCheck.contains(attendance)) {
                    row.add(attendance.isPresent());
                    attendanceCheck.add(attendance);
                }

            }
            rows.add(row);
        }


        crud.setData(rows);
        try {
            crud.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/organization/"+
                activities.get(0).getAttendances().get(0).getOrganizationId()+
                "/activity/"+ activityId +
                "/attendance";
    }
}
