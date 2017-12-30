package ch.cemil.info.yoklama.ui.controller.activity.view;

import ch.cemil.info.yoklama.domain.entity.Attendance;
import ch.cemil.info.yoklama.domain.entity.MeetingType;
import ch.cemil.info.yoklama.domain.entity.Address;

import java.util.Date;
import java.util.List;

public class ActivityView {
    private long id;
    private Date date;
    private String name;
    private String description;
    private Address address;
    private MeetingType meetingType;
    private List<Attendance> attendances;

}
