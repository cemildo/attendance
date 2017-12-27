package ch.cemil.yoklama.modelViews;

import ch.cemil.yoklama.model.Address;
import ch.cemil.yoklama.model.Attendance;
import ch.cemil.yoklama.model.MeetingType;

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
