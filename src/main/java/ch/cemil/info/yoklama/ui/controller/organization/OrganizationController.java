package ch.cemil.info.yoklama.ui.controller.organization;

import ch.cemil.info.yoklama.domain.entity.*;
import ch.cemil.info.yoklama.services.OrganizationService;
import ch.cemil.info.yoklama.ui.controller.activity.view.ActivityMember;
import ch.cemil.info.yoklama.domain.entity.*;
import ch.cemil.info.yoklama.domain.repository.AddressRepository;
import ch.cemil.info.yoklama.ui.controller.activity.view.ActivityMembersWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.stream.Collectors.toList;


@Controller
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private AddressRepository  adressRepository;

    /*
    @RequestMapping("/")
    public String index(Model model) {
        // entity.addAttribute("template", "views/organization/Index");
        // entity.addAttribute("partial", "all");
        return "redirect:/organization/all";
    }
    */

    @RequestMapping("/organization/all")
    public String findAll(Model model, @AuthenticationPrincipal final UserDetails userDetails) {
        List<Organization> organizations =  organizationService.findAll();
        model.addAttribute("template", "views/organization/all");
        model.addAttribute("partial", "all");
        model.addAttribute("organizations", organizations);
        return "Index";
    }

    @RequestMapping(value = "/organization/add/{id}", method = RequestMethod.GET)
    public String showAddView(Organization organization, Model model, @PathVariable long id) {
        if(id != 0) {
            organization = organizationService.findOne(id);
        }

        model.addAttribute("template", "views/organization/add");
        model.addAttribute("partial", "add");
        model.addAttribute("organization", organization);
        return "Index";
    }

    @RequestMapping(value = "/organization/remove/{id}")
    public String remove(@PathVariable long id, Model model) {
        organizationService.delete(id);
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
            return "Index";
        }

        Organization dbOrganization = organizationService.findOne(organization.getId());

        if(dbOrganization != null) {
            // ASK neden bunlari ayriyetten save etmem gerekiyor burdaki kisa yol ne?
            organization.setId(dbOrganization.getId());
            organization.setActivities(dbOrganization.getActivities());
            organization.setMembers(dbOrganization.getMembers());
        }

        Organization createdOrganization = organizationService.save(organization);

        if(createdOrganization != null) {
            model.addAttribute("status", "successfull");
            if(initialId != 0){
                model.addAttribute("updated", "organization is updated!");
            } else {
                model.addAttribute("created", "New organization is created!");
            }

        } else {
            model.addAttribute("status", "error");
            model.addAttribute("errored", "There is a problem please try again later!");
        }

        return "Index";
    }


    @RequestMapping(value = "/organization/{orgId}/activities")
    public String showActivities(@PathVariable long orgId, Model model) {
        Organization organization = organizationService.findOne(orgId);
        model.addAttribute("template", "views/organization/activity/all");
        model.addAttribute("partial", "all");
        model.addAttribute("organization", organization);
        return "Index";
    }

    @RequestMapping(value = "/organization/{orgId}/activity/{activityId}")
    public String ShowNewActivityView(@PathVariable long orgId,@PathVariable long activityId, Model model) {
        Organization organization = organizationService.findOne(orgId);
        Activity activity = new Activity();
        if(activityId != 0) {
            activity = organization.getActivities().stream().filter(r -> r.getId() == activityId).findFirst().get();
        }
        model.addAttribute("template", "views/organization/activity/add");
        model.addAttribute("partial", "add");
        model.addAttribute("organization", organization);
        model.addAttribute("activity", activity);
        return "Index";
    }


    @RequestMapping(value = "/organization/{orgId}/activity/add", method = RequestMethod.POST)
    public String addNewActivity(@ModelAttribute(value = "member") Activity activity, @PathVariable long orgId) {

        Organization organization = organizationService.findOne(orgId);
        Address updateOrSave = adressRepository.save(activity.getAddress());
        activity.setAddress(updateOrSave);
        if (activity.getId() != 0) {
            List<Activity>  updatedActivities = organization.getActivities().stream()
                    .map(o -> o.getId() == activity.getId() ? activity : o ).collect(toList());
            organization.setActivities(updatedActivities);
        } else {
            organization.getActivities().add(activity);
        }

        organizationService.save(organization);

        return "redirect:/organization/"+ organization.getId() +"/activities";
    }


}
