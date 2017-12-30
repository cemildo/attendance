package ch.cemil.info.yoklama.ui.controller.member;

import ch.cemil.info.yoklama.domain.entity.Member;
import ch.cemil.info.yoklama.domain.entity.Organization;
import ch.cemil.info.yoklama.services.MemberService;
import ch.cemil.info.yoklama.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Controller
public class MemberController {

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(value = "/organization/{orgId}/members")
    public String showMembers(@PathVariable long orgId, Model model) {
        Organization organization = organizationService.findOne(orgId);
        model.addAttribute("template", "views/organization/member/all");
        model.addAttribute("partial", "all");
        model.addAttribute("organization", organization);
        return "index";
    }

    @RequestMapping(value = "/organization/{orgId}/member/{memberId}")
    public String ShowNewMemberView(@PathVariable long orgId,@PathVariable long memberId, Model model) {
        Organization organization = organizationService.findOne(orgId);
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

        Organization organization = organizationService.findOne(orgId);
        if (member.getId() != 0) {
            List<Member> updatedMembers = organization.getMembers().stream()
                    .map(o -> o.getId() == member.getId() ? member : o ).collect(toList());

            organization.setMembers(updatedMembers);
        } else {
            organization.getMembers().add(member);
        }

        organizationService.save(organization);

        return "redirect:/organization/"+ organization.getId() +"/members";
    }



    @RequestMapping(value = "/organization/{oid}/remove/{memberId}")
    public String removeMember(@PathVariable long oid, @PathVariable long memberId) {

        Organization organization = organizationService.findOne(oid);
        organization.setMembers(organization.getMembers().stream().filter(r -> r.getId() != memberId).collect(toList()));
        Organization createdOrganization = organizationService.save(organization);

        return "redirect:/organization/"+ organization.getId() +"/members";
    }

}
