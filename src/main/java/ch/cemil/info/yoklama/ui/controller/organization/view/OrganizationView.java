package ch.cemil.info.yoklama.ui.controller.organization.view;

import ch.cemil.info.yoklama.domain.entity.Activity;
import ch.cemil.info.yoklama.domain.entity.Member;
import ch.cemil.info.yoklama.domain.entity.Address;

import java.util.List;

public class OrganizationView {
    private long id;
    private String name;
    private String email;
    private String phone;
    private String website;
    private Address address;
    private List<Member> members;
    private List<Activity> activities;
}
