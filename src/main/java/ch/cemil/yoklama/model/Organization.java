package ch.cemil.yoklama.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Organization implements Serializable
{
    private static final long serialVersionUID = -3009157732232241606L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;
    private String email;
    private String phone;
    private String website;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @JsonIgnore
    private Address address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id")
    @JsonIgnore
    private List<Member> members;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id")
    @JsonIgnore
    private List<Activity> activities;

    public List<Activity> getActivities() {
        if(activities == null) {
            activities = new ArrayList<Activity>();
        }
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public Organization(){ super();}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Address getAddress() {
        if(address == null)
            address = new Address();
        return address;
    }

    public void setAddress(Address addresses) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        if(members == null) {
            members = new ArrayList<Member>();
        }
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }


}
