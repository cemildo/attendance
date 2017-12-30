package ch.cemil.info.yoklama.ui.controller.activity.view;


public class ActivityMember {


    public boolean present;
    public long id;
    public long activtyId;
    public long organizationId;
    public long attendanceId;
    public String firstName;
    public String lastName;

    public ActivityMember(){}

    public long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getActivtyId() {
        return activtyId;
    }

    public void setActivtyId(long activtyId) {
        this.activtyId = activtyId;
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



}