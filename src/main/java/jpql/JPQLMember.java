package jpql;

import javax.persistence.*;

@Entity
public class JPQLMember {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String username;
    private int age;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    public MemberType getType() {
        return type;
    }

    public void setType(MemberType type) {
        this.type = type;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "TEAM_ID")
    private JPQLTeam team;

    public void changeTeam(JPQLTeam team) {
        this.team = team;
        this.team.getMembers().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public JPQLTeam getTeam() {
        return team;
    }

    public void setTeam(JPQLTeam team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "JPQLMember{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
