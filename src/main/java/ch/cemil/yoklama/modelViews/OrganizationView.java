package ch.cemil.yoklama.modelViews;

import ch.cemil.yoklama.model.Activity;
import ch.cemil.yoklama.model.Address;
import ch.cemil.yoklama.model.Member;

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
