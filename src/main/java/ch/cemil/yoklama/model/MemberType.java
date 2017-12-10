
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
public class MemberType implements Serializable
{

    private static final long serialVersionUID = -3009157732242241306L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;


    public MemberType() { super(); }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
