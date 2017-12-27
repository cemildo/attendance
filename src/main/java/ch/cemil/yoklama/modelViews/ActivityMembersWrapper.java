package ch.cemil.yoklama.modelViews;

import java.util.ArrayList;
import java.util.List;

public class ActivityMembersWrapper {
    List<ActivityMembers> members;

    public ActivityMembersWrapper(){
        members = new ArrayList<ActivityMembers>();
    }

    public List<ActivityMembers> getMembers() {
        return members;
    }

    public void setMembers(List<ActivityMembers> members) {
        this.members = members;
    }


}
