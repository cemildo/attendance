
package ch.cemil.yoklama.model;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Activity implements Serializable
{

    private static final long serialVersionUID = -3009157729242241606L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private Date date;
    private String name;

    @OneToOne
    private Address address;

    @OneToOne
    private MeetingType meetingType;

    @OneToMany
    private List<Attendance> attendances;


    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public Activity() { super(); }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        if(address == null) {
            address = new Address();
        }
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public MeetingType getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(MeetingType meetingType) {
        this.meetingType = meetingType;
    }
}

