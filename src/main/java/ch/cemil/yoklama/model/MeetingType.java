
package ch.cemil.yoklama.model;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table
public class MeetingType implements Serializable
{

    private static final long serialVersionUID = -3009157732242242606L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private long name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getName() {
        return name;
    }

    public void setName(long name) {
        this.name = name;
    }
}
