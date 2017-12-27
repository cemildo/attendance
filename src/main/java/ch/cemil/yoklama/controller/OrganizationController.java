package ch.cemil.yoklama.controller;

import ch.cemil.yoklama.Repository.MemberRepository;
import ch.cemil.yoklama.model.Activity;
import ch.cemil.yoklama.model.Address;
import ch.cemil.yoklama.model.Member;
import ch.cemil.yoklama.model.Organization;
import ch.cemil.yoklama.Repository.AddressRepository;
import ch.cemil.yoklama.Repository.OrganizationRepository;
import ch.cemil.yoklama.modelViews.ActivityMembers;
import ch.cemil.yoklama.modelViews.ActivityMembersWrapper;
import ch.cemil.yoklama.modelViews.MemberView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@Controller
public class OrganizationController {
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private AddressRepository  adressRepository;

    @RequestMapping("/")
    public String index(Model model) {
        // model.addAttribute("template", "views/organization/index");
        // model.addAttribute("partial", "all");
        return "redirect:/organization/all";
    }

    @RequestMapping("/organization/all")
    public String findAll(Model model) {
        List<Organization> organizations = (List<Organization>) organizationRepository.findAll();
        model.addAttribute("template", "views/organization/all");
        model.addAttribute("partial", "all");
        model.addAttribute("organizations", organizations);
        return "index";
    }

    @RequestMapping(value = "/organization/add/{id}", method = RequestMethod.GET)
    public String showAddView(Organization organization, Model model, @PathVariable long id) {
        if(id != 0) {
            organization = organizationRepository.findOne(id);
        }

        model.addAttribute("template", "views/organization/add");
        model.addAttribute("partial", "add");
        model.addAttribute("organization", organization);
        return "index";
    }

    @RequestMapping(value = "/organization/remove/{id}")
    public String remove(@PathVariable long id, Model model) {
        organizationRepository.delete(id);
        return "redirect:/organization/all";
    }


    @RequestMapping(value = "/organization/add", method = RequestMethod.POST)
    public String add(@ModelAttribute Organization organization, Model model) {
        final long initialId = organization.getId();
        model.addAttribute("template", "views/organization/add");
        model.addAttribute("partial", "add");
        if(organization.getName() == "") {
            model.addAttribute("status", "error");
            model.addAttribute("message", "Please provide a name!");
            return "index";
        }

        Organization dbOrganization = organizationRepository.findOne(organization.getId());

        if(dbOrganization != null) {
            // ASK neden bunlari ayriyetten save etmem gerekiyor burdaki kisa yol ne?
            organization.setId(dbOrganization.getId());
            organization.setActivities(dbOrganization.getActivities());
            organization.setMembers(dbOrganization.getMembers());
        }

        Organization createdOrganization = organizationRepository.save(organization);

        if(createdOrganization != null) {
            model.addAttribute("status", "successfull");
            if(initialId != 0){
                model.addAttribute("updated", "Organization is updated!");
            } else {
                model.addAttribute("created", "New organization is created!");
            }

        } else {
            model.addAttribute("status", "error");
            model.addAttribute("errored", "There is a problem please try again later!");
        }

        return "index";
    }

    @RequestMapping(value = "/organization/{orgId}/members")
    public String showMembers(@PathVariable long orgId, Model model) {
        Organization organization = organizationRepository.findOne(orgId);
        model.addAttribute("template", "views/organization/member/all");
        model.addAttribute("partial", "all");
        model.addAttribute("organization", organization);
        return "index";
    }

    @RequestMapping(value = "/organization/{orgId}/member/{memberId}")
    public String ShowNewMemberView(@PathVariable long orgId,@PathVariable long memberId, Model model) {
        Organization organization = organizationRepository.findOne(orgId);
        Member member = new Member();
        if(memberId != 0) {
            member = organization.getMembers().stream().filter(r -> r.getId() == memberId).findFirst().get();
        }
        model.addAttribute("template", "views/organization/member/add");
        model.addAttribute("partial", "add");
        model.addAttribute("organization", organization);
        model.addAttribute("member", member);
        return "index";
    }

    @RequestMapping(value = "/organization/{orgId}/member/add", method = RequestMethod.POST)
    public String addNewMember(@ModelAttribute(value = "member") Member member, @PathVariable long orgId) {

        Organization organization = organizationRepository.findOne(orgId);
        if (member.getId() != 0) {
            List<Member>  updatedMembers = organization.getMembers().stream()
                    .map(o -> o.getId() == member.getId() ? member : o ).collect(toList());

            organization.setMembers(updatedMembers);
        } else {
            organization.getMembers().add(member);
        }

        organizationRepository.save(organization);

        return "redirect:/organization/"+ organization.getId() +"/members";
    }



    @RequestMapping(value = "/organization/{oid}/remove/{memberId}")
    public String removeMember(@PathVariable long oid, @PathVariable long memberId) {

        Organization organization = organizationRepository.findOne(oid);
        organization.setMembers(organization.getMembers().stream().filter(r -> r.getId() != memberId).collect(toList()));
        Organization createdOrganization = organizationRepository.save(organization);

        return "redirect:/organization/"+ organization.getId() +"/members";
    }

    @RequestMapping(value = "/organization/{orgId}/activities")
    public String showActivities(@PathVariable long orgId, Model model) {
        Organization organization = organizationRepository.findOne(orgId);
        model.addAttribute("template", "views/organization/activity/all");
        model.addAttribute("partial", "all");
        model.addAttribute("organization", organization);
        return "index";
    }

    @RequestMapping(value = "/organization/{orgId}/activity/{activityId}")
    public String ShowNewActivityView(@PathVariable long orgId,@PathVariable long activityId, Model model) {
        Organization organization = organizationRepository.findOne(orgId);
        Activity activity = new Activity();
        if(activityId != 0) {
            activity = organization.getActivities().stream().filter(r -> r.getId() == activityId).findFirst().get();
        }
        model.addAttribute("template", "views/organization/activity/add");
        model.addAttribute("partial", "add");
        model.addAttribute("organization", organization);
        model.addAttribute("activity", activity);
        return "index";
    }


    @RequestMapping(value = "/organization/{orgId}/activity/add", method = RequestMethod.POST)
    public String addNewActivity(@ModelAttribute(value = "member") Activity activity, @PathVariable long orgId) {

        Organization organization = organizationRepository.findOne(orgId);
        Address updateOrSave = adressRepository.save(activity.getAddress());
        activity.setAddress(updateOrSave);
        if (activity.getId() != 0) {
            List<Activity>  updatedActivities = organization.getActivities().stream()
                    .map(o -> o.getId() == activity.getId() ? activity : o ).collect(toList());
            organization.setActivities(updatedActivities);
        } else {
            organization.getActivities().add(activity);
        }

        organizationRepository.save(organization);

        return "redirect:/organization/"+ organization.getId() +"/activities";
    }

    @RequestMapping(value = "/organization/{orgId}/activity/{activityId}/attendance")
    public String ShowActivityAttendance(@PathVariable long orgId,@PathVariable long activityId, Model model) {
        Organization organization = organizationRepository.findOne(orgId);
        List<Member> members = organizationRepository.findOne(orgId).getMembers();
        ActivityMembersWrapper wrapper = new ActivityMembersWrapper();

        List<ActivityMembers> activityMembers = members
                .stream()
                .map(member -> new ActivityMembers(member.getId(), member.getFirstName(), member.getLastName()))
                .collect(toList());

        wrapper.setMembers(activityMembers);

        Activity activity = new Activity();

        if(activityId != 0) {
            activity = organization.getActivities().stream().filter(r -> r.getId() == activityId).findFirst().get();
        }

        model.addAttribute("template", "views/organization/attendance/all");
        model.addAttribute("partial", "all");
        model.addAttribute("organization", organization);
        model.addAttribute("activity", activity);
       // model.addAttribute("members", activityMembers);
        model.addAttribute("wrapper", wrapper);

        return "index";
    }

    @RequestMapping(value = "/organization/{orgId}/activity/attendance", method = RequestMethod.POST)
    public String checkAttendance(@ModelAttribute ActivityMembersWrapper wrapper, @PathVariable long orgId, Model model) {

        Organization organization = organizationRepository.findOne(orgId);
        model.addAttribute("template", "views/organization/attendance/all");
        model.addAttribute("partial", "all");
        return "index";
    }

}
