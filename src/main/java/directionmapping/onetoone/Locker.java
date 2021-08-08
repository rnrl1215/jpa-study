package directionmapping.onetoone;

import javax.persistence.*;

@Entity
public class Locker {
    @Id @GeneratedValue
    @Column(name = "OTO_LOCKER_ID")
    private Long id;
    private String name;

    @OneToOne(mappedBy = "locker")
    private OneToOenMember member;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
