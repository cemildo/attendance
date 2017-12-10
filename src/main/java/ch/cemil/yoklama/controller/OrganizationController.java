package ch.cemil.yoklama.controller;

import ch.cemil.yoklama.Repository.MemberRepository;
import ch.cemil.yoklama.model.Address;
import ch.cemil.yoklama.model.Member;
import ch.cemil.yoklama.model.Organization;
import ch.cemil.yoklama.Repository.AddressRepository;
import ch.cemil.yoklama.Repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @RequestMapping("/")
    public String index(Model model) {
        // model.addAttribute("template", "views/organization/index");
        // model.addAttribute("partial", "all");
        return "redirect:/organization/all";
    }

    @RequestMapping("/organization/all")
    public String findAll(Model model) {
        List<Organization> organizations = (List<Organization>) organizationRepository.findAll();
        model.addAttribute("template", "views/organization/index");
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
        model.addAttribute("template", "views/organization/add");
        model.addAttribute("partial", "add");

        if(organization.getName() == "") {
            model.addAttribute("status", "error");
            model.addAttribute("message", "Please provide a name!");
            return "index";
        }

        Organization createdOrganization = organizationRepository.save(organization);

        if(createdOrganization != null) {
            model.addAttribute("status", "successfull");
            if(organization.getId() != 0){
                model.addAttribute("message", "Organization is updated!");
            } else {
                model.addAttribute("message", "New organization is created!");
            }

        } else {
            model.addAttribute("status", "error");
            model.addAttribute("message", "There is a problem please try again later!");
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
}
