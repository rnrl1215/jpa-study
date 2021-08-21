package lazy;


import javax.persistence.*;

// 꼭넣어야 한다.
// JPA가 로딩 될때 해당 어노테이션을 보고 인식한다.
@Entity
//@Table(name = "USER") // 테이블명 지정
public class Member9 {

    public Member9() {
    }

    @Id //PK 설정
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String username;

    public Member9(String member) {
        this.username = member;
    }

    // id 에서 객체로 변환

    public Long getId() {
        return id;
    }

    //@Column(name = "TEAM_ID")
    //private Long teamId;

    // Member 입장에서 Team 의 연관관계를 볼때
    // Member 는 N Team 은 1 이기 때문에
    // manytoone 을 써준다.

    // 프록시 객체로 조회한다.
    //@ManyToOne(fetch = FetchType.LAZY) 늦은 로딩
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEAM_ID")
    private Team9 team;

    public Team9 getTeam() {
        return team;
    }

    public void setTeam(Team9 team) {
        this.team = team;
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




}
