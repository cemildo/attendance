package ch.cemil.yoklama.modelViews;

import ch.cemil.yoklama.model.Activity;
import ch.cemil.yoklama.model.Member;
import ch.cemil.yoklama.model.Organization;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class ActivityMembers {
    public boolean isPresent=false;
    public long memberId;
    public String firstName;
    public String lastName;

    public boolean isPresent() {
        return isPresent;
    }

    public ActivityMembers(long memberId, String firstName, String lastName){
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }


}