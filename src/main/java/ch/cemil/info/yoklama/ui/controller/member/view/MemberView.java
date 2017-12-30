package ch.cemil.info.yoklama.ui.controller.member.view;

import ch.cemil.info.yoklama.domain.entity.*;
import ch.cemil.info.yoklama.domain.entity.*;

import java.util.Date;
import java.util.List;

public class MemberView extends Member {
    private long id;
    private String firstName;
    private String lastName;
    private Date joinDate;
    private Date birthday;
    private String email;
    private String phone;
    private String isPresent;
    private boolean status;
    private Address address;
    private MemberType memberType;
    private List<Organization> organizations;
    private List<Attendance> attendances;
}
