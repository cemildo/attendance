package ch.cemil.yoklama.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table
public class Attendance implements Serializable {

    private static final long serialVersionUID = -3009157733248241626L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private Date date;

    private boolean isPresent;

    @ManyToOne
    @JoinColumn(name = "attendances_id", updatable = false, insertable = false)
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "attendances_id", updatable = false, insertable = false)
    private Member member;

    Attendance(){}

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}